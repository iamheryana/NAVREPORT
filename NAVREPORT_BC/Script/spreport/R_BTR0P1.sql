/****************************************
Name sprocs	: R_BTR0P1
Create by	: Herz
Date		: 10-09-2001
Description	: Proses Untuk Report Transfer PER PEGAWAI
Call From	: Main Proc
Sub sprocs	: -
Update by	: Yudi's --> 26/09/02
*****************************************/
DROP FUNCTION R_BTR0P1 (l_TglFr  	DATE,
			l_TglTo  	DATE,
			l_Periode	DATE,
			l_BankFr	VARCHAR(4),
			l_BankTo	VARCHAR(4),
			l_CtkRekap	VARCHAR(1),
			l_mypass	VARCHAR(128),
			l_Usr_Id	INT);

CREATE FUNCTION R_BTR0P1 (l_TglFr  	DATE,
			l_TglTo  	DATE,
			l_Periode	DATE,
			l_BankFr	VARCHAR(4),
			l_BankTo	VARCHAR(4),
			l_CtkRekap	VARCHAR(1),
			l_mypass	VARCHAR(128),
			l_Usr_Id	INT)

RETURNS TABLE(OUTTpRpt    	VARCHAR(1),
		OUTBank		VARCHAR(4),
		OUTNmBank	VARCHAR(40),
		OUTCbBank	VARCHAR(20),
		OUTArea		VARCHAR(4),
		OUTNmArea	VARCHAR(10),
		OUTCabang	VARCHAR(4),
		OUTNmCabang	VARCHAR(10),
		OUTUUsa		VARCHAR(4),
		OUTNmUUsa	VARCHAR(10),
		OUTUKer		VARCHAR(4),
		OUTNmUker	VARCHAR(10),	
		OUTNIP		VARCHAR(10),
		OUTNama		VARCHAR(25),
		OUTRkBnk	VARCHAR(20),
		OUTAccHolder	VARCHAR(25),
		OUTTakeHomePay	DECIMAL(15,2),
		OUTnamabank	VARCHAR(40),
		OUTcabangbank	VARCHAR(20),
		OUTkotabank  	VARCHAR(25))         
	 
AS $$ 
--
DECLARE l_TpRpt		VARCHAR(1);
	l_Para		VARCHAR(1000);
	l_LineTXT	VARCHAR(1000);

BEGIN 		
-- Table Temp
--
CREATE TEMP TABLE l_TEMP (
         TpRpt    	VARCHAR(1),
         Bank		VARCHAR(4),
	 NmBank		VARCHAR(40),
	 CbBank		VARCHAR(20),
	 Area		VARCHAR(4),
	 NmArea		VARCHAR(10),
	 Cabang		VARCHAR(4),
	 NmCabang	VARCHAR(10),
	 UUsa		VARCHAR(4),
	 NmUUsa		VARCHAR(10),
	 UKer		VARCHAR(4),
	 NmUker		VARCHAR(10),	
	 NIP		VARCHAR(10),
	 Nama		VARCHAR(25),
	 RkBnk		VARCHAR(20),
	 AccHolder	VARCHAR(25),
	 TakeHomePay	DECIMAL(15,2)) ON COMMIT DROP ; 
--
l_TpRPT := 'D'; 
--
--Mulai Proses data dari S01HGAJ ke temp file
--
INSERT INTO l_TEMP(TpRpt ,Bank   ,Area   ,Cabang,UUsa,UKer,NIP    ,Nama    ,RkBnk    ,AccHolder ,TakeHomePay)
           SELECT l_TpRpt,BankRef,' '    ,' '   ,' ' ,' ' ,S01.NIP,S01.Nama,S01.RkBnk,S01.AccHolder,SUM(FN_KPUSAT(S01.NIP,S01.EncTakeHomePay,l_mypass) ::DECIMAL(15,2))
	   FROM S01HGAJ S01
	   INNER JOIN 
	   (
	    SELECT * FROM fn_SECLOGIN(l_Usr_Id)
	   ) VSL 
	   ON S01.Kdglng=VSL.GOL_KODE AND S01.Kdcaba=VSL.CAB_KODE
	   WHERE S01.BankRef BETWEEN l_BankFr AND l_BankTo AND
	   S01.TglPayr  BETWEEN l_TglFr AND l_TglTo  AND
	   (l_TpRpt||S01.BankRef||S01.KdArea||S01.KdCaba||S01.KdUUsa||S01.KdUKer||S01.NIP)
	   NOT IN 
	   (SELECT W01.TpRpt||W01.Bank||W01.Area||W01.Cabang||W01.UUsa||W01.UKer||W01.NIP FROM l_TEMP W01)
	    --
	   GROUP BY S01.BankRef,S01.KdArea,S01.KdCaba,S01.KdUUsa,S01.KdUKer,S01.NIP,S01.Nama,S01.RkBnk,S01.AccHolder;
--
--
UPDATE l_TEMP
SET NmBank=M14.Keterangan,
    CbBank=M14.Cabang	
FROM l_TEMP TMP
LEFT JOIN M14BANK M14 ON TMP.Bank=M14.Kode;
--
--Yang Dimuculkan hanya yang ada bank-nya
RETURN QUERY 
SELECT T.* ,
	(SELECT namabank FROM FZ2FLDA LIMIT 1) AS namabank, 
 	(SELECT cabang FROM FZ2FLDA LIMIT 1) AS cabangbank, 
	(SELECT kotabank FROM FZ2FLDA LIMIT 1) AS kotabank  
FROM l_TEMP T
WHERE T.Bank<>' ';

END;
$$ LANGUAGE plpgsql ;

/******* END OF PROC *****/

/*
SELECT * FROM R_BTR0P1('2013-01-20'::DATE,'2013-04-28'::DATE,'2013-01-31'::DATE,'','ZZZZ','Y','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1);
*/




