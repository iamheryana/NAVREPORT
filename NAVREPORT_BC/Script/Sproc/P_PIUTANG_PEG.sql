/******************************************
Name sprocs	: P_Piutang_Peg
Create by	: wati
Date		: 17-06-2003
Description	: Proses Piutang Pegawai
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE FUNCTION P_Piutang_Peg(l_TglProses DATE,
					  l_TglAkhir  DATE,                       
					  l_NIP       VARCHAR(10),                           
					  l_UserID    VARCHAR(12),
					  l_MyPass    VARCHAR(128),
					  l_S01_ID    INT)      
RETURNS VOID 
AS $$

DECLARE l_AngsuranPokok  DECIMAL(15,2);
        l_AngsuranBunga  DECIMAL(15,2);
        l_AngsuranPokokVal  DECIMAL(15,2);
        l_AngsuranBungaVal  DECIMAL(15,2); 
        l_T06KdJnsP      VARCHAR(4);
        l_M03Singkatan   VARCHAR(10);
        l_M03UsComp      VARCHAR(1);
        l_M03Kolom       VARCHAR(2);
        l_M03NoAcc       VARCHAR(10);
        l_M03Status      VARCHAR(1);
        l_M03Persen      DECIMAL(5,2);
        l_M03Nilai       DECIMAL(15,2);
    	l_M03NoLyr       INT;
	l_M03KdCurr      VARCHAR(4);

BEGIN 
-- Insert File Pembayaran (T07BAYP) dari File Piutang (T06DPIU)
INSERT INTO T07BAYP(NIP,    KdJnsP,    TgPiut,    TgDoku,     BayPokok,   BayBunga,    Comp,Keterangan           ,T05_ID    ,VERSION, CREATED_BY, CREATED_ON,   UPDATED_BY, UPDATED_ON)
             SELECT TBL.NIP,TBL.KdJnsP,TBL.TgDoku,TBL.PrdAngs,TBL.JmlAngs,TBL.JmlBunga,'P', 'DARI PROSES PAYROLL',TBL.T05_ID,0      , l_userid,   current_time, null,       null 
	     FROM
	       (--TBL
		SELECT DISTINCT T06.NIP,T06.KdJnsP,T06.TgDoku,T06.PrdAngs,
				fn_bayPiut(T06.NIP,T06.KdJnsP,T06.TgDoku,'A',T06.JmlAngs,T06.JmlBunga) AS JmlAngs, 
				fn_bayPiut(T06.NIP,T06.KdJnsP,T06.TgDoku,'B',T06.JmlAngs,T06.JmlBunga) AS JmlBunga,
				T05.T05_ID
	       FROM T06DPIU T06 
		   ----
	       INNER JOIN T05HPIU T05
	       ON T05.NIP=T06.NIP AND T05.KdJnsP=T06.KdJnsP AND T05.TgDoku=T06.TgDoku
		   ---		
	       WHERE T06.NIP=l_NIP AND T06.PrdAngs > l_TglAkhir AND T06.PrdAngs <= l_TglProses AND
		     T05.Piutang+Bunga<>(BayPokPay+BayBngPay+BayPokLgs+BayBngLgs)
	       ) TBL 
	       --
	       LEFT JOIN 
	       (
	       SELECT * FROM T07BAYP WHERE Comp='P'
	       )T07
	       ON T07.NIP=TBL.NIP AND T07.KdJnsP=TBL.KdJnsP AND T07.TgPiut=TBL.TgDoku AND T07.TgDoku=TBL.PrdAngs 
	       ----
	       WHERE T07.NIP IS NULL AND T07.KdJnsP IS NULL AND T07.TgPiut IS NULL AND T07.TgDoku IS NULL ;

-- jika jenis piutang belum terdefinisi sebagai Potongan maka Isi Data Master Pendpatan/Potongan 
-- Created by Suhe 10-12-2003

INSERT INTO M03DPPT 
--
SELECT 'P' AS FlgDpPt,T07.KdJnsP AS KdDpPt,M22.NmPiut AS NmDpPt,M22.SkPiut AS KdDpPt,'B' AS JnDpPt,
	0 AS Persen,1 AS Nilai,'IDR' AS KdCurr,'U' AS Status,'P' AS UsComp,0 AS NoLayar,' ' AS Kolom,
	'D' AS Pajak,' ' AS NoAcc,' ' AS Flag,
	0 AS VERSION, 'l_UserID' AS CREATED_BY,CURRENT_DATE AS CREATED_ON, NULL AS UPDATED_BY, NULL AS UPDATED_ON 
FROM T07BAYP T07
--
INNER JOIN M22JNSP M22
ON M22.KdJnsP=T07.KdJnsP
--
LEFT JOIN M03DPPT M03
ON M03.FlgDpPt='P' AND M03.KdDpPt=T07.KdJnsP
--
WHERE T07.NIP=l_NIP AND T07.TgDoku > l_TglAkhir AND T07.TgDoku <= l_TglProses AND T07.BayPokok<>0 AND T07.Comp='P' AND
      M03.FlgDpPt IS NULL AND M03.KdDpPt IS NULL; 

-- Insert Blank Detail Penggajian U/ Angsuran Pokok
     INSERT INTO S02DGAJ(TglPayr   ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                         Nilai,KdCurr    ,EncNilaiVal,
                         NilaiVal,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL, S01_ID)
                 SELECT  l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdJnsP,'A'    ,TBL.SkDpPt,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0    ,TBL.KdCurr,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0       ,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,0       , l_S01_ID   
       FROM 
      (SELECT DISTINCT T07.NIP,M03.FlgDpPt,T07.KdJnsP,M03.SkDpPt,M03.KdCurr,
                       M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar
       FROM T07BAYP T07
       INNER JOIN M03DPPT M03
       ON M03.FlgDpPt='P' AND M03.KdDpPt= T07.KdJnsP
       --
       WHERE T07.NIP=l_NIP AND T07.TgDoku > l_TglAkhir AND T07.TgDoku <= l_TglProses AND T07.BayPokok<>0 AND T07.Comp='P'
       ) TBL 
       -- 
       LEFT JOIN S02DGAJ S02
       ON S02.TglPayr=l_TglProses AND S02.NIP=TBL.NIP AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdJnsP AND COALESCE(S02.FlgAngs,' ')='A'
       --
       WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL ; 

-- Insert Detail Penggajian U/ Angsuran Bunga
     INSERT INTO S02DGAJ(TglPayr   ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                         Nilai,KdCurr    ,EncNilaiVal,
                         NilaiVal,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL, S01_ID)
                 SELECT  l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdJnsP,'B'    ,TBL.SkDpPt,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0    ,TBL.KdCurr,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0       ,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,0,        l_S01_ID
       FROM 
      (SELECT DISTINCT T07.NIP,M03.FlgDpPt,T07.KdJnsP,M03.SkDpPt,M03.KdCurr,
                       M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar
       FROM T07BAYP T07
       INNER JOIN M03DPPT M03
       ON M03.FlgDpPt='P' AND M03.KdDpPt= T07.KdJnsP
       WHERE T07.NIP=l_NIP AND T07.TgDoku > l_TglAkhir AND T07.TgDoku <= l_TglProses AND T07.BayBunga<>0 AND T07.Comp='P'
       ) TBL 
       -- 
       LEFT JOIN S02DGAJ S02
       ON S02.TglPayr=l_TglProses AND S02.NIP=TBL.NIP AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdJnsP AND COALESCE(S02.FlgAngs,' ')='B'
       WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL ;

-- Update Detail Penggajian U/ Angsuran Pokok
UPDATE S02DGAJ
   SET EncNilai    =fn_kcabang(TBL1.NIP,(fn_kPusat(TBL1.NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)+TBL1.AngsuranPokok) ::VARCHAR(17),l_MyPass),
       EncNilaiVal =fn_kcabang(TBL1.NIP,(fn_kPusat(TBL1.NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+TBL1.AngsuranPokokVal) ::VARCHAR(17),l_MyPass)
   FROM   
   (
   SELECT TBL.NIP,TBL.KdJnsP,
	  fn_Vround(TBL.AngsuranPokok) AS AngsuranPokok,
	  fn_Vround(TBL.AngsuranPokok/fn_GetKurs(TBL.KdCurr,l_TglProses)) AS AngsuranPokokVal
   FROM 
   (
   SELECT T07.NIP,T07.KdJnsP,
          SUM(T07.BayPokok) AS AngsuranPokok, 
          M03.KdCurr
   FROM T07BAYP T07
   INNER JOIN M03DPPT M03
   ON M03.FlgDpPt='P' AND M03.KdDpPt=T07.KdJnsP
   WHERE T07.NIP=l_NIP AND  T07.TgDoku > l_TglAkhir AND T07.TgDoku <= l_TglProses AND T07.BayPokok<>0 AND T07.Comp='P'
   GROUP BY T07.NIP,T07.KdJnsP,M03.KdCurr
   ) TBL
   --
   ) TBL1
   INNER JOIN S02DGAJ S02 
   ON S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND S02.FlgDpPt='P' AND S02.KdDpPt=TBL1.KdJnsP AND COALESCE(S02.FlgAngs,' ')='A';

-- Update Detail Penggajian U/ Angsuran Bunga
UPDATE S02DGAJ
   SET EncNilai    =fn_kcabang(TBL1.NIP,(fn_kPusat(TBL1.NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)+TBL1.AngsuranPokok) ::VARCHAR(17),l_MyPass),
       EncNilaiVal =fn_kcabang(TBL1.NIP,(fn_kPusat(TBL1.NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+TBL1.AngsuranPokokVal) ::VARCHAR(17),l_MyPass)
   FROM   
   (
   SELECT TBL.NIP,TBL.KdJnsP,
	  fn_Vround(TBL.AngsuranPokok) AS AngsuranPokok,
	  fn_Vround(TBL.AngsuranPokok/fn_GetKurs(TBL.KdCurr,l_TglProses)) AS AngsuranPokokVal
   FROM 
   (
   SELECT T07.NIP,T07.KdJnsP,
          SUM(T07.BayBunga) AS AngsuranPokok,
          M03.KdCurr
   FROM T07BAYP T07
   INNER JOIN M03DPPT M03
   ON M03.FlgDpPt='P' AND M03.KdDpPt=T07.KdJnsP
   WHERE T07.NIP=l_NIP AND T07.TgDoku > l_TglAkhir AND T07.TgDoku <= l_TglProses AND T07.BayBunga<>0 AND T07.Comp='P'
   GROUP BY T07.NIP,T07.KdJnsP,M03.KdCurr
   ) TBL
   --
   ) TBL1
   INNER JOIN S02DGAJ S02 
   ON S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND S02.FlgDpPt='P' AND S02.KdDpPt=TBL1.KdJnsP AND COALESCE(S02.FlgAngs,' ')='B';
-- End Of Program
END;
$$ LANGUAGE plpgsql ;

/*
EXEC P_Piutang_Peg l_TglProses='2003-05-20',
                   l_TglAkhir='2003-02-20',                       
                   l_NIP='03',                           
                   l_UserID='wet',
                   l_MyPass='Copyright, 1988 (c) Microsoft Corporation, All rights reserved'     
*/
