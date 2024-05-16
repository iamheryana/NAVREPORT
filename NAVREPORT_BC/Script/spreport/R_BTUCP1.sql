/************************************************************
Name sprocs	: R_BTUCP1
Create by	: YUDI
Date		: 03-09-2002
Description	: Proses Laporan per Unit Usaha dan Cabang
Call From	: btucS1
Modified by	: Deni 30/06/03
************************************************************/
DROP FUNCTION R_BTUCP1(
            l_Periode	DATE,
			l_PeriodeTo	DATE,
			l_BankFr	VARCHAR(4),
			l_BankTo	VARCHAR(4),
			l_UsaFr		VARCHAR(4),
			l_UsaTo		VARCHAR(4),
			l_FCab		VARCHAR(4),
			l_TCab		VARCHAR(4),
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
			l_AreaFr	VARCHAR(4),
			l_AreaTo	VARCHAR(4),
			l_UkerFr	VARCHAR(8),
			l_UkerTo	VARCHAR(8),
			l_Pil		VARCHAR(1),
			l_MyPass	VARCHAR(128),
			l_Usr_Id	INT);
--
CREATE FUNCTION R_BTUCP1(
            l_Periode	DATE,
			l_PeriodeTo	DATE,
			l_BankFr	VARCHAR(4),
			l_BankTo	VARCHAR(4),
			l_UsaFr		VARCHAR(4),
			l_UsaTo		VARCHAR(4),
			l_FCab		VARCHAR(4),
			l_TCab		VARCHAR(4),
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
			l_AreaFr	VARCHAR(4),
			l_AreaTo	VARCHAR(4),
			l_UkerFr	VARCHAR(8),
			l_UkerTo	VARCHAR(8),
			l_Pil		VARCHAR(1),
			l_MyPass	VARCHAR(128),
			l_Usr_Id	INT)			
/*
PIL = 'D'DETAIL    : 'R'REKAP
      'D=Detail saja, R=Detail+Rekap (deni 30/06/03)'
*/
RETURNS TABLE(OUTTipe		VARCHAR(1),
		OUTKdUUsa	VARCHAR(4),
		OUTSkUUsa	VARCHAR(10), 	
		OUTKdCaba	VARCHAR(4),	
		OUTSkCaba	VARCHAR(10),
		OUTKdBank	VARCHAR(4),
		OUTKetBank	VARCHAR(70),
		OUTSkBank	VARCHAR(10),
		OUTCbBank	VARCHAR(20),
		OUTRkBnk	VARCHAR(20),
		OUTNIP		VARCHAR(10),
		OUTNama		VARCHAR(25),
		OUTKdArea	VARCHAR(4),
		OUTSkArea	VARCHAR(10),
		OUTKdUker	VARCHAR(8),
		OUTSkUker	VARCHAR(20),
		OUTAcc_Holder	VARCHAR(25),
		OUTTakeHomePay	DECIMAL(15,2))  
--
AS $$ 
DECLARE l_S01NIP	VARCHAR(10);
	l_S01Nama	VARCHAR(25);
	l_S01KdArea	VARCHAR(4);
	l_S01KdCaba	VARCHAR(4);
	l_S01KdUUsa	VARCHAR(4);
	l_S01KdUKer	VARCHAR(8);
	l_S01BankRef	VARCHAR(4);
	l_S01RkBnk	VARCHAR(20);
	l_S01AccHolder	VARCHAR(25);
	l_S01TakeHomePay DECIMAL(15,2);
	l_M01Singkatan	VARCHAR(10);
	l_M02Singkatan	VARCHAR(10);
	l_M08SkCaba	VARCHAR(10);
	l_M14Singkatan	VARCHAR(10);
	l_M17Singkatan	VARCHAR(20);
	l_M14Cabang	VARCHAR(20);
	l_M14Keterangan	VARCHAR(40);
	l_LOOP_S01	refcursor; 
--
BEGIN 
CREATE TEMP TABLE l_TEMP (Tipe		VARCHAR(1),
			    KdUUsa	VARCHAR(4),
			    SkUUsa	VARCHAR(10), 	
			    KdCaba	VARCHAR(4),	
			    SkCaba	VARCHAR(10),
			    KdBank	VARCHAR(4),
			    KetBank	VARCHAR(70),
			    SkBank	VARCHAR(10),
			    CbBank	VARCHAR(20),
			    RkBnk	VARCHAR(20),
			    NIP		VARCHAR(10),
			    Nama	VARCHAR(25),
			    KdArea	VARCHAR(4),
			    SkArea	VARCHAR(10),
			    KdUker	VARCHAR(8),
	            SkUker	VARCHAR(20),
			    Acc_Holder	VARCHAR(25),
			    TakeHomePay	DECIMAL(15,2))  ON COMMIT DROP ;  
--
--*
--Mulai Proses data dari S01HGAJ ke temp file
OPEN l_LOOP_S01 FOR 
SELECT 	S01.NIP,S01.Nama,S01.KdArea,S01.KdCaba,S01.KdUUsa,M17.kdUker,S01.BankRef,S01.RkBnk,
	S01.AccHolder,fn_kpusat(S01.NIP,s01.EncTakeHomePay,l_MyPass) ::DECIMAL(15,2),
	M01.Singkatan,M02.Singkatan,M08.SkCaba,M14.Singkatan,
	M17.Singkatan,M14.Cabang,M14.Keterangan
FROM S01HGAJ S01
INNER JOIN M01AREA M01 ON S01.KdArea=M01.Kode
INNER JOIN M02UUSA M02 ON S01.KdUUsa=M02.Kode
INNER JOIN M08HCAB M08 ON S01.KdCaba=M08.KdCaba
INNER JOIN M14BANK M14 ON S01.BankRef=M14.Kode
INNER JOIN M17UKER M17 ON S01.KdUKer=M17.KdUker
INNER JOIN (SELECT * FROM fn_SECLOGIN(l_Usr_Id)) VSL ON S01.Kdglng=VSL.GOL_KODE AND S01.Kdcaba=VSL.CAB_KODE
WHERE S01.TglPayr BETWEEN l_Periode AND l_PeriodeTo AND 
      (S01.BankRef BETWEEN l_BankFr AND l_BankTo) AND 
      (S01.KdUUsa BETWEEN l_UsaFr AND l_UsaTo) AND
      (S01.KdCaba BETWEEN l_FCab AND l_TCab) AND --(S01.KdGlng BETWEEN l_FGol AND l_TGol) AND 
      (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
      (S01.KdUKer BETWEEN l_UkerFr AND l_UkerTo) AND 
      (S01.KdArea BETWEEN l_AreaFr AND l_AreaTo);

<<l_LOOP_S01>> 
LOOP 
FETCH l_LOOP_S01
INTO l_S01NIP,l_S01Nama,l_S01KdArea,l_S01KdCaba,l_S01KdUUsa,l_S01KdUKer,l_S01BankRef,l_S01RkBnk,
     l_S01AccHolder,l_S01TakeHomePay,l_M01Singkatan,l_M02Singkatan,l_M08SkCaba,l_M14Singkatan,
     l_M17Singkatan,l_M14Cabang,l_M14Keterangan;
    --
	IF NOT FOUND THEN
		EXIT ;
	END IF;
    --
    IF l_Pil = 'D' THEN
       -- Update Detail
       IF (SELECT COUNT(*) FROM l_TEMP 
	          WHERE Tipe='D' AND NIP=l_S01NIP AND KdUUsa=l_S01KdUUsa AND KdCaba=l_S01KdCaba AND KdBank=l_S01BankRef AND
	                KdArea=l_S01KdArea AND KdUker=l_S01KdUKer) > 0 THEN 
            UPDATE l_TEMP
            SET TakeHomePay=COALESCE(TakeHomePay,0)+l_S01TakeHomePay
            WHERE Tipe='D' AND NIP=l_S01NIP AND KdUUsa=l_S01KdUUsa AND KdCaba=l_S01KdCaba AND
	          KdBank=l_S01BankRef AND KdArea=l_S01KdArea AND KdUker=l_S01KdUKer;
       ELSE 
            INSERT INTO l_TEMP(Tipe,KdUUsa    ,KdCaba     ,KdBank      ,KdArea     ,KdUker     ,SkUUsa        ,SkCaba     ,SkBank        ,SkArea        ,SkUker        ,NIP     ,Nama     ,RkBnk     ,Acc_Holder    ,TakeHomePay     ,CbBank	 ,KetBank)
                       VALUES('D', l_S01KdUUsa,l_S01KdCaba,l_S01BankRef,l_S01KdArea,l_S01KdUKer,l_M02Singkatan,l_M08SkCaba,l_M14Singkatan,l_M01Singkatan,l_M17Singkatan,l_S01NIP,l_S01Nama,l_S01RkBnk,l_S01AccHolder,l_S01TakeHomePay,l_M14Cabang,RTRIM(l_M14Keterangan)||' Cab. '||RTRIM(l_M14Cabang)); 
       END IF; 
    ELSE       --IF l_Pil = 'R' THEN 
       -- Update Rekap (deni 30/06/03)
       IF (SELECT COUNT(*) FROM l_TEMP 
          WHERE Tipe='R' AND KdUUsa=l_S01KdUUsa AND KdCaba=l_S01KdCaba AND KdBank=l_S01BankRef AND
                KdArea=l_S01KdArea AND KdUker=l_S01KdUKer) > 0 THEN 
             UPDATE l_TEMP
             SET TakeHomePay=COALESCE(TakeHomePay,0)+l_S01TakeHomePay
             WHERE Tipe='R' AND KdUUsa=l_S01KdUUsa AND KdCaba=l_S01KdCaba AND KdBank=l_S01BankRef AND 
             KdArea=l_S01KdArea AND KdUker=l_S01KdUKer; 
       ELSE 
             INSERT INTO l_TEMP(Tipe,KdCaba     ,KdBank      ,KdArea     ,KdUker     ,SkCaba     ,SkBank        ,SkArea        ,SkUker        ,TakeHomePay     ,KdUUsa     ,SkUUsa         ,CbBank     ,KetBank)
                          VALUES('R',l_S01KdCaba,l_S01BankRef,l_S01KdArea,l_S01KdUKer,l_M08SkCaba,l_M14Singkatan,l_M01Singkatan,l_M17Singkatan,l_S01TakeHomePay,l_S01KdUUsa,l_M02Singkatan ,l_M14Cabang,RTRIM(l_M14Keterangan)||' Cab. '||RTRIM(l_M14Cabang)); 
       END IF; 
       --*
    END IF; 
    --*
END LOOP;
CLOSE l_LOOP_S01;
--
RETURN QUERY 
SELECT * FROM l_TEMP
WHERE kdBank<>' '
ORDER BY Tipe,kdbank; 
--* 
--
END;
$$ LANGUAGE plpgsql ;
--
/*
SELECT * FROM R_BTUCP1('2010-01-01' ::DATE,'2010-12-31' ::DATE,' ','ZZZZ','  ','ZZZZ','','ZZZ','','ZZZZZZZZZZ',' ','ZZZZ',' ','ZZZZZZZZ','R','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1); 
SELECT * FROM R_BTUCP1('2010-01-01' ::DATE,'2010-12-31' ::DATE,' ','ZZZZ','  ','ZZZZ','','ZZZ','','ZZZZZZZZZZ',' ','ZZZZ',' ','ZZZZZZZZ','D','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1); 

*/
