/******************************************
Name sprocs	: P_Pend_Vari
Create by	: Wati
Date		: 19-06-2003
Description	: Proses Hitung Pendapatan/Potongan Variable THR
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Pend_Vari(l_TglProses  DATE,
			l_TglAkhir   DATE, 
			l_FlagKhusus CHAR(1),
			l_NIP        CHAR(10),
			l_GajiRp     DECIMAL(15,2),
			l_Prs_Harian CHAR(1), 
			l_MyPass     VARCHAR(128),
			l_S01_ID     INT);   
--
CREATE FUNCTION P_Pend_Vari(l_TglProses  DATE,
			l_TglAkhir   DATE, 
			l_FlagKhusus CHAR(1),
			l_NIP        CHAR(10),
			l_GajiRp     DECIMAL(15,2),
			l_Prs_Harian CHAR(1), 
			l_MyPass     VARCHAR(128),
			l_S01_ID    INT)   
RETURNS VOID 
AS $$ 

DECLARE l_PotAdvance    DECIMAL(15,2);
        l_PotAdvanceVal DECIMAL(15,2);
        l_M03Singkatan  CHAR(10);
        l_M03UsComp     CHAR(1);
        l_M03Kolom      CHAR(2);
        l_M03NoAcc      CHAR(10);
        l_M03Status     CHAR(1);
        l_M03Persen     DECIMAL(5,2);
        l_M03Nilai      DECIMAL(15,2);
	l_M03NoLyr      INT;
	l_M03KdCurr     CHAR(4);
	l_M03ID         INT;
BEGIN 
-- Jika Proses Khusus
IF l_FlagKhusus='Y' THEN  
   BEGIN
     -- Insert Detail Penggajian Untuk Nilai=0
     INSERT INTO S02DGAJ(TglPayr   ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                         Nilai,KdCurr    ,EncNilaiVal,
                         NilaiVal,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL,     S01_ID)
                 SELECT  l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,' '    ,TBL.SkDpPt,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0    ,TBL.KdCurr,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0       ,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,TBL.FlgNonGL, TBL.S01_ID
     FROM 
     (SELECT DISTINCT T03.NIP,T03.FlgDpPt,T03.KdDpPt,M03.SkDpPt,CASE WHEN (T03.Nilai=0 AND T03.Persen=0) THEN M03.KdCurr ELSE T03.KdCurr END AS KdCurr,
                      M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar,0 AS FlgNonGL, l_S01_ID AS S01_ID --CASE WHEN T03.FlgDpPt='D' AND T03.BayarDimuka=1 THEN 1 ELSE 0 END
     FROM T03VARI T03
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T03.FlgDpPt AND M03.KdDpPt= T03.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T03.FlgDpPt AND M46.KdDpPt= T03.KdDpPt
     WHERE T03.NIP=l_NIP AND M46.FlgDpPt IS NOT NULL AND T03.PrdMulai<=l_TglProses AND
           (fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) <> 0)
     ) TBL 
     -- 
     LEFT JOIN S02DGAJ S02
     ON S02.TglPayr=l_TglProses AND S02.NIP=TBL.NIP AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
     WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL; 

     -- Update Detail Penggajian Untuk Nilai<>0 
--     UPDATE S02DGAJ
--     SET EncNilai    =fn_kcabang(TBL2.NIP,(fn_kPusat(TBL2.NIP,EncNilai,l_MyPass) ::DECIMAL(15,2) + TBL2.NilaiVari) ::VARCHAR(17),l_MyPass),
--         EncNilaiVal =fn_kcabang(TBL2.NIP,(fn_kPusat(TBL2.NIP,EncNilaiVal,l_MyPass)::DECIMAL(15,2) + TBL2.NilaiVariVal) ::VARCHAR(17),l_MyPass)
--     FROM 
     UPDATE S02DGAJ S02
     SET EncNilai    =fn_kcabang(TBL2.NIP,(S02ENCNILAI+TBL2.Nilai) :: VARCHAR(17),l_MyPass),
         EncNilaiVal =fn_kcabang(TBL2.NIP,(S02ENCNILAIVAL+TBL2.NilaiVal) :: VARCHAR(17),l_MyPass)
     FROM 
    (--TBL2 
     SELECT TBL2.TglProses, TBL2.NIP, TBL2.FlgDpPt, TBL2.KdDpPt, TBL2.Nilai, TBL2.NilaiVal,
            fn_kPusat(TBL2.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAI, 
            fn_kPusat(TBL2.NIP,S02.EncNilaiVal,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAIVAL
     FROM
     (
     SELECT TBL1.TglProses,TBL1.NIP,TBL1.FlgDpPt,TBL1.KdDpPt,
	    fn_Vround(COALESCE(TBL1.NilaiVari,0)) AS Nilai,
	    TBL1.KdCurr, 
            COALESCE(fn_Vround((TBL1.NilaiVari/fn_GetKurs(TBL1.KdCurr,TBL1.TglProses))),0) AS NilaiVal
     FROM
     (
     SELECT l_TglProses AS TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,TBL.KdCurr,
            SUM(fn_NilaiPend_Pot(l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,TBL.Persen,TBL.Nilai,TBL.Hari,l_GajiRp,CASE WHEN TBL.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,TBL.KdCurr)) AS NilaiVari
     FROM 
     (
     SELECT T03.NIP,T03.FlgDpPt,T03.KdDpPt,CASE WHEN (T03.Nilai=0 AND T03.Persen=0) THEN M03.KdCurr ELSE T03.KdCurr END AS KdCurr,
            T03.Persen,T03.Nilai,M03.JnDpPt,T03.PrdMulai,T03.PrdSd,
            fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) AS Hari,
	    T03.Flag
     FROM T03VARI T03
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T03.FlgDpPt AND M03.KdDpPt= T03.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T03.FlgDpPt AND M46.KdDpPt= T03.KdDpPt
     WHERE T03.NIP=l_NIP AND M46.FlgDpPt IS NOT NULL AND T03.PrdMulai<=l_TglProses AND
           (fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) <> 0)
     ) TBL 
     --
     GROUP BY TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,TBL.KdCurr
     ) TBL1 
     --
     ) TBL2 
     --
     INNER JOIN S02DGAJ S02 
     ON S02.TglPayr=TBL2.TglProses AND S02.NIP=l_NIP AND S02.FlgDpPt=TBL2.FlgDpPt AND S02.KdDpPt=TBL2.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
     ) TBL2
      WHERE S02.TglPayr=TBL2.TglProses AND S02.NIP=TBL2.NIP  AND S02.FlgDpPt=TBL2.FlgDpPt AND S02.KdDpPt=TBL2.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' ';    

     -- Hitungan Potongan Advance
     SELECT fn_Vround(TBL3.Advance)
     INTO l_PotAdvance
     FROM 
     (
     SELECT TBL.NIP,
            SUM(fn_NilaiPend_Pot(l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,TBL.Persen,TBL.Nilai,TBL.Hari,l_GajiRp,CASE WHEN TBL.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,TBL.KdCurr)) AS Advance
     FROM 
     (
     SELECT T03.NIP,T03.FlgDpPt,T03.KdDpPt,CASE WHEN (T03.Nilai=0 AND T03.Persen=0) THEN M03.KdCurr ELSE T03.KdCurr END AS KdCurr,
            T03.Persen,T03.Nilai,M03.JnDpPt,T03.PrdMulai,T03.PrdSd,
            fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) AS Hari,
	    T03.Flag 
     FROM T03VARI T03
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T03.FlgDpPt AND M03.KdDpPt= T03.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T03.FlgDpPt AND M46.KdDpPt= T03.KdDpPt
     WHERE T03.NIP=l_NIP AND T03.PrdMulai<=l_TglProses AND T03.FlgDpPt='D' AND T03.BayarDimuka=1 AND 
           M46.FlgDpPt IS NOT NULL AND (fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) <> 0)
     ) TBL 
     --
     GROUP BY TBL.NIP
     --
     ) TBL3 ; 

     -- Jika Nilai Potongan Advance <> 0
     IF l_PotAdvance<>0 THEN 
        BEGIN
          -- Ambil Data dari Master Pendapatan/Potongan (M03DPPT    
	  SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
		 fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
	  from  P_M03AllField ('P', 'PADV' ) fx
	  INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
		  l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;   		      
          -- Ambil Nilai Kurs
          l_PotAdvanceVal := fn_Vround(l_PotAdvance/fn_GetKurs(l_M03KdCurr,l_TglProses)); 
          -- Update Detail Penggajian           
          IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV') IS NOT NULL THEN 
             BEGIN
               UPDATE S02DGAJ
               SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_PotAdvanceVal) ::VARCHAR(17),l_MyPass),
                   EncNilai    =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)+l_PotAdvance) ::VARCHAR(17),l_MyPass)
               WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV';	
             END;
          ELSE
             BEGIN
               INSERT INTO S02DGAJ(TglPayr   ,NIP ,FlgDpPt,  KdDpPt,FlgAngs,Singkatan ,    EncNilai                                             ,Nilai,KdCurr     ,EncNilaiVal                                             ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                            VALUES(l_TglProses,l_NIP,'P'    ,'PADV',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_PotAdvance :: CHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_PotAdvanceVal :: CHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID); 
             END;
          END IF;
        END;
     END IF; 
   END;
-- Jika Proses Bulanan
ELSIF l_FlagKhusus='T' THEN 
   BEGIN
     -- Insert Detail Penggajian Untuk Nilai=0
     INSERT INTO S02DGAJ(TglPayr   ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                         Nilai,KdCurr    ,EncNilaiVal,
                         NilaiVal,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL, S01_ID)
                 SELECT  l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,' '    ,TBL.SkDpPt,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0    ,TBL.KdCurr,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0       ,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,TBL.FlgNonGL, TBL.S01_ID
     FROM 
     (SELECT DISTINCT T03.NIP,T03.FlgDpPt,T03.KdDpPt,M03.SkDpPt,CASE WHEN (T03.Nilai=0 AND T03.Persen=0) THEN M03.KdCurr ELSE T03.KdCurr END AS KdCurr,
                      M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar,0 AS FlgNonGL, l_S01_ID AS S01_ID --CASE WHEN T03.FlgDpPt='D' AND T03.BayarDimuka=1 THEN 1 ELSE 0 END
     FROM T03VARI T03
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T03.FlgDpPt AND M03.KdDpPt= T03.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T03.FlgDpPt AND M46.KdDpPt= T03.KdDpPt
     WHERE T03.NIP=l_NIP AND M46.FlgDpPt IS NULL AND T03.PrdMulai<=l_TglProses AND
           (fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) <> 0)
     ) TBL 
     -- 
     LEFT JOIN S02DGAJ S02
     ON S02.TglPayr=l_TglProses AND S02.NIP=TBL.NIP AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
     WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL ;

     -- Update Detail Penggajian Untuk Nilai<>0 
--     UPDATE S02DGAJ
--     SET EncNilai    =fn_kcabang(TBL2.NIP,(fn_kPusat(TBL2.NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)+TBL2.NilaiVari) ::VARCHAR(17),l_MyPass),
--         EncNilaiVal =fn_kcabang(TBL2.NIP,(fn_kPusat(TBL2.NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+TBL2.NilaiVariVal) ::VARCHAR(17),l_MyPass)
--     FROM 
     UPDATE S02DGAJ S02
     SET EncNilai    =fn_kcabang(TBL2.NIP,(S02ENCNILAI+TBL2.Nilai) :: VARCHAR(17),l_MyPass),
         EncNilaiVal =fn_kcabang(TBL2.NIP,(S02ENCNILAIVAL+TBL2.NilaiVal) :: VARCHAR(17),l_MyPass)
     FROM 
    (--TBL2 
     SELECT TBL2.TglProses, TBL2.NIP, TBL2.FlgDpPt, TBL2.KdDpPt, TBL2.Nilai, TBL2.NilaiVal, 
            fn_kPusat(TBL2.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAI, 
            fn_kPusat(TBL2.NIP,S02.EncNilaiVal,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAIVAL
     FROM
     (
     SELECT TBL1.TglProses,TBL1.NIP,TBL1.FlgDpPt,TBL1.KdDpPt,fn_Vround(TBL1.NilaiVari) AS Nilai,TBL1.KdCurr,
            fn_Vround((TBL1.NilaiVari/fn_GetKurs(TBL1.KdCurr,TBL1.TglProses))) AS NilaiVal
     FROM
     (
     SELECT l_TglProses AS TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,TBL.KdCurr,
            SUM(fn_NilaiPend_Pot(l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,TBL.Persen,TBL.Nilai,TBL.Hari,l_GajiRp,CASE WHEN TBL.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,TBL.KdCurr)) AS NilaiVari
     FROM 
     (
     SELECT T03.NIP,T03.FlgDpPt,T03.KdDpPt,CASE WHEN (T03.Nilai=0 AND T03.Persen=0) THEN M03.KdCurr ELSE T03.KdCurr END AS KdCurr,
            T03.Persen,T03.Nilai,M03.JnDpPt,T03.PrdMulai,T03.PrdSd,
            fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) AS Hari,
	    T03.Flag 
     FROM T03VARI T03
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T03.FlgDpPt AND M03.KdDpPt= T03.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T03.FlgDpPt AND M46.KdDpPt= T03.KdDpPt
     WHERE T03.NIP=l_NIP AND M46.FlgDpPt IS NULL AND T03.PrdMulai<=l_TglProses AND
           (fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) <> 0)

     ) TBL 
     --
     GROUP BY TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,TBL.KdCurr
     ) TBL1 
     --
     ) TBL2 
     --
     INNER JOIN S02DGAJ S02 
     ON S02.TglPayr=TBL2.TglProses AND S02.NIP=l_NIP AND S02.FlgDpPt=TBL2.FlgDpPt AND S02.KdDpPt=TBL2.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
     ) TBL2
     WHERE S02.TglPayr=TBL2.TglProses AND S02.NIP=TBL2.NIP  AND S02.FlgDpPt=TBL2.FlgDpPt AND S02.KdDpPt=TBL2.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' ';       

     -- Hitungan Potongan Advance
     SELECT fn_Vround(TBL3.Advance)
     INTO l_PotAdvance
     FROM 
     (
     SELECT TBL.NIP,
            SUM(fn_NilaiPend_Pot(l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,TBL.Persen,TBL.Nilai,TBL.Hari,l_GajiRp,CASE WHEN TBL.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,TBL.KdCurr)) AS Advance
     FROM 
     (
     SELECT T03.NIP,T03.FlgDpPt,T03.KdDpPt,CASE WHEN (T03.Nilai=0 AND T03.Persen=0) THEN M03.KdCurr ELSE T03.KdCurr END AS KdCurr,
	    T03.Persen,T03.Nilai,M03.JnDpPt,T03.PrdMulai,T03.PrdSd,
            fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) AS Hari,
	    T03.Flag
     FROM T03VARI T03
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T03.FlgDpPt AND M03.KdDpPt= T03.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T03.FlgDpPt AND M46.KdDpPt= T03.KdDpPt
     WHERE T03.NIP=l_NIP AND T03.PrdMulai<=l_TglProses AND T03.FlgDpPt='D' AND T03.BayarDimuka=1 AND 
           M46.FlgDpPt IS NULL AND (fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) <> 0)
     ) TBL 
     --
     GROUP BY TBL.NIP
     --
     ) TBL3 ; 

     -- Jika Nilai Potongan Advance <> 0
     IF l_PotAdvance<>0 THEN 
        BEGIN
          -- Ambil Data dari Master Pendapatan/Potongan (M03DPPT
	   SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
		  fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
	   from  P_M03AllField ('P', 'PADV' ) fx
	   INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
		   l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;   		        
          -- Ambil Nilai Kurs
          l_PotAdvanceVal := fn_Vround(l_PotAdvance/fn_GetKurs(l_M03KdCurr,l_TglProses));
          -- Update Detail Penggajian           
          IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV') IS NOT NULL THEN 
             BEGIN
               UPDATE S02DGAJ
               SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_PotAdvanceVal)  ::VARCHAR(17),l_MyPass),
                   EncNilai    =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)+l_PotAdvance) ::VARCHAR(17),l_MyPass)
               WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV';	
             END;
          ELSE
             BEGIN
               INSERT INTO S02DGAJ(TglPayr    ,NIP ,FlgDpPt, KdDpPt,FlgAngs,Singkatan ,    EncNilai                                             ,Nilai,KdCurr     ,EncNilaiVal                                             ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                            VALUES(l_TglProses,l_NIP,'P'    ,'PADV',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_PotAdvance :: CHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_PotAdvanceVal :: CHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0,        l_S01_ID);
             END;
          END IF; 
        END; 
     END IF;    
   END; 
END IF ; 
END;
$$ LANGUAGE plpgsql ;
