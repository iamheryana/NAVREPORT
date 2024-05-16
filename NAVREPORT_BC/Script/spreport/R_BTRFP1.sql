/****************************************
Name sprocs	: R_BTRFP1
Create by	: Herz
Date		: 24 - 08 - 2001
Description	: Proses Untuk Report Transfer
Call From	: Main Proc
Sub sprocs	: -
Update by	: Yudi's  -->  26/09/02
*****************************************/
DROP FUNCTION R_BTRFP1 (l_Periode	DATE,
			l_PeriodeTo	DATE,
			l_BankFr	VARCHAR(4),
			l_BankTo	VARCHAR(4),
			l_CtkRekap	VARCHAR(1),
			l_MyPass	VARCHAR(128),
			l_Usr_Id	INT);
--
CREATE FUNCTION R_BTRFP1  (	l_Periode	DATE,
				l_PeriodeTo	DATE,
				l_BankFr	VARCHAR(4),
				l_BankTo	VARCHAR(4),
				l_CtkRekap	VARCHAR(1),
				l_MyPass	VARCHAR(128),
				l_Usr_Id	INT)

RETURNS TABLE (OUTTpRpt    	VARCHAR(1),
		OUTBank		VARCHAR(4),
		OUTNmBank	VARCHAR(40),
		OUTCbBank	VARCHAR(20),
		OUTArea		VARCHAR(4),
		OUTNmArea	VARCHAR(10),
		OUTCabang	VARCHAR(4),
		OUTNmCabang	VARCHAR(10),
		OUTUUsa		VARCHAR(4),
		OUTNmUUsa	VARCHAR(10),
		OUTUKer		VARCHAR(8),
		OUTNmUker	VARCHAR(20),	
		OUTNIP		VARCHAR(10),
		OUTNama		VARCHAR(25),
		OUTRkBnk	VARCHAR(20),
		OUTAccHolder	VARCHAR(25),
		OUTTakeHomePay	DECIMAL(15,2))         

AS $$ 
--
DECLARE l_TpRpt		VARCHAR(1);
		
BEGIN 
-- Panggil sProc untuk bentuk Table Temp
CREATE TEMP TABLE l_TEMP (TpRpt    	VARCHAR(1),
			  Bank		VARCHAR(4),
			  NmBank	VARCHAR(40),
			  CbBank	VARCHAR(20),
			  Area		VARCHAR(4),
			  NmArea	VARCHAR(10),
			  Cabang	VARCHAR(4),
			  NmCabang	VARCHAR(10),
			  UUsa		VARCHAR(4),
			  NmUUsa	VARCHAR(10),
			  UKer		VARCHAR(8),
			  NmUker	VARCHAR(20),	
			  NIP		VARCHAR(10),
			  Nama		VARCHAR(25),
			  RkBnk		VARCHAR(20),
			  AccHolder	VARCHAR(25),
		 	  TakeHomePay	DECIMAL(15,2)) ON COMMIT DROP ;          
--
--
--Jika Pilih Cetak Rekap Maka dibuat Temp-nya dengan 'R'
IF l_CtkRekap='Y' then 
        l_TpRPT := 'R'; 
      --
      --Mulai Proses data dari S01HGAJ ke temp file
	INSERT INTO l_TEMP(TpRpt ,Bank   ,Area  ,Cabang,UUsa  ,UKer  ,TakeHomePay)
	SELECT l_TpRpt,S01.BankRef,S01.KdArea,S01.KdCaba,S01.KdUUsa,S01.KdUKer,
		SUM(fn_kpusat(S01.NIP,S01.EncTakeHomePay,l_MyPass) ::DECIMAL(15,2))
	FROM S01HGAJ S01
	INNER JOIN 
	(
	SELECT * FROM fn_SECLOGIN(l_Usr_Id)
	) VSL 
	ON S01.Kdglng=VSL.GOL_KODE AND S01.Kdcaba=VSL.CAB_KODE

	WHERE  BankRef BETWEEN l_BankFr AND l_BankTo AND
		TglPayr BETWEEN l_Periode AND l_PeriodeTo AND 
		(l_TpRpt||S01.BankRef||S01.KdArea||S01.KdCaba||S01.KdUUsa||S01.KdUKer)
		NOT IN 
		(SELECT W01.TpRpt||W01.Bank||W01.Area||W01.Cabang||W01.UUsa||W01.UKer FROM l_TEMP W01)
		    --
        GROUP BY S01.BankRef,S01.KdArea,S01.KdCaba,S01.KdUUsa,S01.KdUKer;
     --
ELSE
    l_TpRPT := 'D';
    --Mulai Proses data dari S01HGAJ ke temp file
    INSERT INTO l_TEMP(TpRpt ,Bank   ,Area  ,Cabang,UUsa  ,UKer  ,NIP    ,Nama    ,RkBnk    ,AccHolder    ,TakeHomePay)
    SELECT l_TpRpt,S01.BankRef,S01.KdArea,S01.KdCaba,S01.KdUUsa,S01.KdUKer,S01.NIP,S01.Nama,S01.RkBnk,S01.AccHolder,
        SUM(fn_kpusat(S01.NIP,S01.EncTakeHomePay,l_MyPass) ::DECIMAL(15,2))
    FROM S01HGAJ S01
    INNER JOIN 
    (
    SELECT * FROM fn_SECLOGIN(l_Usr_Id)
    ) VSL 
    ON S01.Kdglng=VSL.GOL_KODE AND S01.Kdcaba=VSL.CAB_KODE

    WHERE  BankRef BETWEEN l_BankFr AND l_BankTo AND
        TglPayr BETWEEN l_Periode AND l_PeriodeTo AND 
        (l_TpRpt||S01.BankRef||S01.KdArea||S01.KdCaba||S01.KdUUsa||S01.KdUKer||S01.NIP)
        NOT IN 
        (SELECT W01.TpRpt||W01.Bank||W01.Area||W01.Cabang||W01.UUsa||W01.UKer||W01.NIP FROM l_TEMP W01)
            --
    GROUP BY S01.BankRef,S01.KdArea,S01.KdCaba,S01.KdUUsa,S01.KdUKer,S01.NIP,S01.Nama,S01.RkBnk,S01.AccHolder;

END IF; 
--*
--
UPDATE l_TEMP
SET NmBank=M14.Keterangan,
    NmArea=M01.Singkatan,
    NmCabang=M08.SkCaba,
    NmUUsa=M02.Singkatan,
    NmUker=M17.Keterangan,
    CbBank=M14.Cabang	
FROM l_TEMP TMP
LEFT JOIN M14BANK M14 
ON TMP.Bank=M14.Kode
LEFT JOIN M01AREA M01 
ON TMP.Area =M01.Kode
LEFT JOIN M08HCAB M08 
ON TMP.Cabang=M08.KdCaba
LEFT JOIN M02UUSA M02 
ON TMP.UUsa=M02.Kode 
LEFT JOIN M17UKER M17 
ON TMP.Uker=M17.KdUker;    
--
RETURN QUERY 
SELECT * FROM l_TEMP
WHERE Bank<>' '; 

END;
$$ LANGUAGE plpgsql ;

/******* END OF PROC *****/


/*
SELECT * FROM R_BTRFP1('2013-01-28'::DATE,'2013-01-28'::DATE,' ','ZZZZ','T','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1) ; 

SELECT * FROM R_BTRFP1('2013-01-28'::DATE,'2013-01-28'::DATE,' ','ZZZZ','Y','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1) ; 

UPDATE S01HGAJ 
SET BANKREF ='BNI'
WHERE NIP ='PHW-001'; 
*/
