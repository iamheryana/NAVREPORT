/****************************************
Name sprocs	: R_BTUKP1
Create by	: Frans
Date		: 02-03-2007
Description	: Proses Untuk Report Transfer PER UNIT KERJA
Call From	: Main Proc
Sub sprocs	: -
*****************************************/
DROP FUNCTION R_BTUKP1 (l_mdProsesFr  		DATE,
			  l_mdProsesTo  	DATE,
			  l_mdPeriode		DATE,
			  l_mcBankFr		VARCHAR(4),
			  l_mcBankTo		VARCHAR(4),
			  l_mcUkerFr		VARCHAR(8),
			  l_mcUkerTo		VARCHAR(8),
			  l_mcKdComp      	VARCHAR(4),
			  l_mypass		VARCHAR(128),
			  l_Usr_Id		INT);
--			  
CREATE FUNCTION R_BTUKP1 (l_mdProsesFr  	DATE,
			  l_mdProsesTo  	DATE,
			  l_mdPeriode		DATE,
			  l_mcBankFr		VARCHAR(4),
			  l_mcBankTo		VARCHAR(4),
			  l_mcUkerFr		VARCHAR(8),
			  l_mcUkerTo		VARCHAR(8),
			  l_mcKdComp      	VARCHAR(4),
			  l_mypass		VARCHAR(128),
			  l_Usr_Id		INT)

RETURNS TABLE (OUTUKer		VARCHAR(8) ,
		OUTNmUker	VARCHAR(20),	
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
--
BEGIN 
CREATE TEMP TABLE l_TEMP (Bank		VARCHAR(4),
			 UKer		VARCHAR(8) ,
			 NmUker		VARCHAR(20),	
			 NIP		VARCHAR(10),
			 Nama		VARCHAR(25),
			 TakeHomePay	DECIMAL(15,2),
			 BiayaBank	DECIMAL(15,2),				
			 JumlahBayar	DECIMAL(15,2),
			 SingkatanBank	VARCHAR(10) ,
			 NmBank		VARCHAR(40)) ON COMMIT DROP ; 
--
INSERT INTO l_TEMP(Bank, UKer, NmUker, NIP, Nama, TakeHomePay, BiayaBank, 
				  SingkatanBank, NmBank,JumlahBayar)
SELECT Q1.BankRef, Q1.KdUker, Q1.NamaUker, Q1.NIP, Q1.Nama, SUM(Q1.T) AS TakeHomePay, Q1.BiayaBank, 
	Q1.SingkatanBank, Q1.NamaBank, Q1.JumlahBayar
FROM
(--Q1
	SELECT S01.BankRef, S01.KdUker, ' ' ::VARCHAR(20) AS NamaUker, S01.NIP, S01.Nama, 
		   (FN_KPUSAT(S01.NIP,S01.EncTakeHomePay,l_mypass) ::DECIMAL(15,2)) AS T,
		   0 AS BiayaBank, ' ' ::VARCHAR(10) AS SingkatanBank, ' '::VARCHAR(40) AS NamaBank,
		   0 AS JumlahBayar, KdCaba, KdGlng, TglPayr, EncTakeHomePay
	FROM S01HGAJ S01
	INNER JOIN 
	(
	SELECT * FROM fn_SECLOGIN(l_Usr_Id)
	) VSL 
	ON S01.Kdglng=VSL.GOL_KODE AND S01.Kdcaba=VSL.CAB_KODE
)Q1
WHERE   Q1.BankRef BETWEEN l_mcBankFr AND l_mcBankTo AND
	Q1.TglPayr BETWEEN l_mdProsesFr AND l_mdProsesTo AND
	Q1.KdUker BETWEEN l_mcUkerFr AND l_mcUkerTo
GROUP BY Q1.BankRef, Q1.KdUKer, Q1.NamaUker, Q1.BiayaBank, Q1.SingkatanBank, Q1.NamaBank,
	   Q1.JumlahBayar, Q1.NIP, Q1.Nama;

UPDATE l_TEMP
SET BiayaBank=M14.BiayaBank,
    NmBank=M14.Keterangan,
    SingkatanBank=M14.Singkatan,
    JumlahBayar	= TMP.TakeHomePay+M14.BiayaBank
FROM l_TEMP TMP
LEFT JOIN M14BANK M14 ON TMP.Bank=M14.Kode;

UPDATE l_TEMP
SET NmUker=M17.Keterangan
FROM l_TEMP TMP
LEFT JOIN M17UKER M17 ON TMP.Uker=M17.KdUker;

--
--Yang Dimuculkan hanya yang ada bank-nya
RETURN QUERY 
SELECT T.Uker, T.NmUker, T.NIP, T.Nama, T.TakeHomePay, T.BiayaBank, T.JumlahBayar, 
	   T.SingkatanBank, T.NmBank,
	   (SELECT M33.Nama FROM M33JABT M33 WHERE M33.Kode='01') AS NamaAu, 
 	   (SELECT FZ2.TTD FROM FZ2FLDA FZ2 
	    INNER JOIN FZ1FLDA FZ1 
	    ON FZ1.Kode = FZ2.Kode
	    WHERE FZ1.Kode = COALESCE(l_mcKdComp,' ')) AS TTD 
FROM l_TEMP T

WHERE T.Bank<>' ';
-- 

END;
$$ LANGUAGE plpgsql ;
/******* END OF PROC *****/

/*
select * from R_BTUKP1('2013-01-01'::Date, '2013-01-31'::Date, '2013-01-31'::Date, '', 'ZZZZ', '', 'ZZZZ', '01', 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1)
*/




