/****************************************
Name sprocs	: R_BTUUP1
Create by	: Frans
Date		: 02-03-2007
Description	: Proses Untuk Report Transfer PER UNIT KERJA
Call From	: Main Proc
Sub sprocs	: -
*****************************************/
DROP FUNCTION R_BTUUP1 (l_mdProsesFr  	DATE,
			l_mdProsesTo  	DATE,
			l_mdPeriode	DATE,
			l_mcBankFr	VARCHAR(4),
			l_mcBankTo	VARCHAR(4),
			l_mcUUsaFr	VARCHAR(4),
			l_mcUUsaTo	VARCHAR(4),
			l_mypass	VARCHAR(128),
			l_Usr_Id	INT);
--			
CREATE FUNCTION R_BTUUP1 (l_mdProsesFr  DATE,
			l_mdProsesTo  	DATE,
			l_mdPeriode	DATE,
			l_mcBankFr	VARCHAR(4),
			l_mcBankTo	VARCHAR(4),
			l_mcUUsaFr	VARCHAR(4),
			l_mcUUsaTo	VARCHAR(4),
			l_mypass	VARCHAR(128),
			l_Usr_Id	INT)
RETURNS TABLE(OUTUUsa		VARCHAR(8) ,
		OUTNmUUsa	VARCHAR(20),	
		OUTNIP		VARCHAR(10),
		OUTNama		VARCHAR(25),
		OUTTakeHomePay	DECIMAL(15,2),
		OUTBiayaBank	DECIMAL(15,2),				
		OUTJumlahBayar	DECIMAL(15,2),
		OUTSingkatanBank VARCHAR(10) ,
		OUTNmBank	VARCHAR(40),
		OUTNamaAu	VARCHAR(25),
		OUTTTD 		VARCHAR(30))

AS $$ 
BEGIN 
--
CREATE TEMP TABLE l_TEMP(Bank		VARCHAR(4),
			 UUsa		VARCHAR(8) ,
			 NmUUsa		VARCHAR(20),	
			 NIP		VARCHAR(10),
			 Nama		VARCHAR(25),
			 TakeHomePay	DECIMAL(15,2),
			 BiayaBank	DECIMAL(15,2),				
			 JumlahBayar	DECIMAL(15,2),
			 SingkatanBank	VARCHAR(10) ,
			 NmBank		VARCHAR(40)) ON COMMIT DROP ;          
--
INSERT INTO l_TEMP(Bank, UUsa, NmUUsa, NIP, Nama, TakeHomePay, BiayaBank, 
				  SingkatanBank, NmBank,JumlahBayar)
SELECT Q1.BankRef, Q1.KdUUsa, Q1.NamaUUsa, Q1.NIP, Q1.Nama, SUM(Q1.T) AS TakeHomePay, Q1.BiayaBank, 
	Q1.SingkatanBank, Q1.NamaBank, Q1.JumlahBayar
FROM
(--Q1
	SELECT S01.BankRef, S01.KdUUsa, ' ' ::VARCHAR(20) AS NamaUUsa, S01.NIP, S01.Nama, 
	(FN_KPUSAT(S01.NIP,S01.EncTakeHomePay,l_mypass) ::DECIMAL(15,2)) AS T,
	0 AS BiayaBank, ' ' ::VARCHAR(10) AS SingkatanBank, ' ' ::VARCHAR(40) AS NamaBank,
	0 AS JumlahBayar, KdCaba, KdGlng, TglPayr
	FROM S01HGAJ S01
)Q1
WHERE  Q1.BankRef BETWEEN l_mcBankFr AND l_mcBankTo AND
	Q1.TglPayr BETWEEN l_mdProsesFr AND l_mdProsesTo AND
	Q1.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo
GROUP BY Q1.BankRef, Q1.KdUUsa, Q1.NamaUUsa, Q1.BiayaBank, Q1.SingkatanBank, Q1.NamaBank,
	   Q1.JumlahBayar, Q1.NIP, Q1.Nama;

UPDATE l_TEMP
SET BiayaBank=M14.BiayaBank,
    NmBank=M14.Keterangan,
    SingkatanBank=M14.Singkatan,
    JumlahBayar	= TMP.TakeHomePay+M14.BiayaBank
FROM l_TEMP TMP
LEFT JOIN M14BANK M14 ON TMP.Bank=M14.Kode;

UPDATE l_TEMP
SET NmUUsa=M02.Keterangan
FROM l_TEMP TMP
LEFT JOIN M02UUSA M02 ON TMP.UUsa=M02.Kode;

--
--Yang Dimuculkan hanya yang ada bank-nya
RETURN QUERY 
SELECT T.UUsa, T.NmUUsa, T.NIP, T.Nama, T.TakeHomePay, T.BiayaBank, T.JumlahBayar, 
	   T.SingkatanBank, T.NmBank,
	   (SELECT M33.Nama FROM M33JABT M33 LIMIT 1) AS NamaAu,-- WHERE Kode='01'), 
 	   (SELECT FZ2.TTD FROM FZ2FLDA FZ2 LIMIT 1) AS TTD
FROM l_TEMP T

WHERE T.Bank<>' '; 
-- 
END;
$$ LANGUAGE plpgsql ;
/******* END OF PROC *****/

/*
SELECT * FROM R_BTUUP1('2013-01-01' ::DATE,'2013-01-31' ::DATE,'2013-01-01' ::DATE,'','ZZZZ','','ZZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1);
*/




