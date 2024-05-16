/****************************************
Name sprocs	: P_BDTXP2
Create by	: Herz
Date		: 01-09-2004
Description	: Proses Untuk Dowload B,Trans. ke TXT
Call From	: Main Proc
*****************************************/
DROP FUNCTION P_BDTXP2 (l_TglFr  		DATE,
				l_TglTo  	DATE,
				l_TglTrans	DATE,
				l_BankFr	VARCHAR(4),
				l_BankTo	VARCHAR(4),
				l_mypass	VARCHAR(128),
				l_kdLDA		VARCHAR(2),
				l_kdUsaFr	VARCHAR(4),
				l_kdUsaTo    	VARCHAR(4),
				l_Usr_Id	INT);
--
CREATE FUNCTION P_BDTXP2  (l_TglFr  		DATE,
				l_TglTo  	DATE,
				l_TglTrans	DATE,
				l_BankFr	VARCHAR(4),
				l_BankTo	VARCHAR(4),
				l_mypass	VARCHAR(128),
				l_kdLDA		VARCHAR(2),
				l_kdUsaFr	VARCHAR(4),
				l_kdUsaTo    	VARCHAR(4),
				l_Usr_Id	INT)
RETURNS TABLE (TXT CHAR(70)) 
AS $$
--
DECLARE l_query 	VARCHAR(4000);
	l_FZ2Cabang 	VARCHAR(13);
	l_FZ2NoRekening	VARCHAR(10);
	l_S01DTL	DECIMAL(5);
	l_TakeHome	DECIMAL(15,2);    
	l_TakeHomeSTR   VARCHAR(17);
	l_Tgl		VARCHAR(2);
	l_Nilai		VARCHAR(17);
	l_Ctr		VARCHAR(5);
	l_Bln		VARCHAR(2);	    
	l_Created      	INT;
	l_LineTXT	VARCHAR(4000);
	l_NmFileTmp	VARCHAR(128);
	l_NmFileTmpTXT	VARCHAR(128);
	l_TableName1    VARCHAR(8);

	l_01    	VARCHAR(2);
	l_11MF    	VARCHAR(4);	
BEGIN 		
--- Buat Table Temporay Permanen
--SELECT * FROM P_CREATE_TEMP_ONLIST ('W0E') INTO l_TableName1, l_Created;--,	      
--
--IF l_Created=0 THEN 
--   RETURN;
--END IF;    
--
CREATE TEMP TABLE l_TEMP_DTL 
(  NIP	    	VARCHAR(10),
   Nama     	VARCHAR(25),
   BANKCODE   	VARCHAR(10),
   BANKNAME     VARCHAR(40),
   BRANCH       VARCHAR(20),
   Amount       DECIMAL(15),
   ACCNO        VARCHAR(20),
   BENEFICLARY  VARCHAR(25),
   KdCaba    	VARCHAR(10)
) ON COMMIT DROP ;
---
-- Tampung ke Temp File
CREATE TEMP TABLE l_TEMPS01
(  TglPayr  	DATE,
   NIP	    	VARCHAR(10),
   TakeHomPay  DECIMAL(15,2),
   KdCaba    	VARCHAR(10)
) ON COMMIT DROP ;

---------------------- ISI DATA HEADER ke Temporary-----------------------------

DELETE FROM l_TEMPS01;
--
INSERT INTO l_TEMPS01
SELECT S01.TglPayr,S01.NIP,
       FN_KPUSAT(S01.NIP,S01.EncTakeHomePay,l_mypass) ::DECIMAL(15,2) as NILAI , 
	S01.KdCaba
FROM S01HGAJ S01
--
INNER JOIN M15PEGA M15
ON M15.NIP=S01.NIP
--
INNER JOIN 
(
SELECT * FROM fn_SECLOGIN(l_Usr_Id)
) VSL 
ON S01.Kdglng=VSL.GOL_KODE AND S01.Kdcaba=VSL.CAB_KODE
--
WHERE --(S01.KdCaba BETWEEN l_FCab AND l_TCab) AND
      --(S01.KdGlng BETWEEN l_FGol AND l_TGol) AND     
      (S01.BankRef<>' '  AND (S01.BankRef BETWEEN l_BankFr AND l_BankTo)) AND 
--      (M15.BankRef<>' '  AND (M15.BankRef BETWEEN l_BankFr AND l_BankTo)) AND 
      (S01.TglPayr  BETWEEN l_TglFr AND l_TglTo) AND 
      (S01.KdUUsa BETWEEN l_kdUsaFr AND l_kdUsaTo);       

---------------------- ISI DATA DETAIL ke Temporary-----------------------------
DELETE FROM l_TEMP_DTL; 
--
INSERT INTO l_TEMP_DTL
SELECT fn_ConNIP(M15.NIP),M15.Nama,M14.Singkatan as BANKCODE,M14.Keterangan as BANKNAME,M14.Cabang as BRANCH,
       SUM(COALESCE(S01.TakeHomPay,0)) as Amount,
       X01.Rkbnk as ACCNO,
       X01.AccHolder as BENEFICLARY, 
	S01.KdCaba
---
FROM l_TEMPS01 S01
--
INNER JOIN S01HGAJ X01
ON S01.TGLPAYR=X01.TGLPAYR AND S01.NIP = X01.NIP 
--
LEFT JOIN  M15PEGA M15
ON S01.NIP=M15.NIP
--
LEFT JOIN M14BANK M14
ON M14.Kode=X01.BankRef
---
GROUP BY M15.NIP,M15.Nama,M14.Singkatan,M14.Keterangan,M14.Cabang,
       X01.Rkbnk,X01.AccHolder,S01.KdCaba;
/************************************* BUAT DATA TXT  *************************/
---
SELECT  SUBSTR(FZ2.Cabang,1,13),
	SUBSTR(FZ2.NoRekening,1,10)
INTO    l_FZ2Cabang,
	l_FZ2NoRekening
FROM FZ2FLDA FZ2
WHERE Kode=l_kdLDA;
---
-- Ambil Counter Dtl dan Take Home Pay
SELECT COUNT(NIP),
       SUM(Amount)
INTO   l_S01DTL,
       l_TakeHome
FROM l_TEMP_DTL S01; 
---
-- Buat Data Variable
l_Tgl  :=RIGHT('00'||LTRIM(EXTRACT(DAY FROM l_TglTrans)::VARCHAR(2)),2);
l_Nilai:=RIGHT('00000000000000000'||LTRIM(l_TakeHome::VARCHAR(17)),17);
l_Bln  :=RIGHT('00'||LTRIM(EXTRACT(MONTH FROM l_TglTrans)::VARCHAR(2)),2);
l_Ctr  :=RIGHT('00000'||LTRIM(l_S01DTL::VARCHAR(5)),5);
l_01   :='01'; 
l_11MF :='11MF'; 

--ISI DARI HEADER
-- EXECUTE 'INSERT INTO '||l_TableName1||' (txt) SELECT 00000000000||$1||$2||$8||$3||$9||$4||$5||$6||(EXTRACT(YEAR FROM $7),4)'
--    USING l_FZ2Cabang, l_Tgl, l_FZ2NoRekening, l_Ctr, l_Nilai, l_Bln, l_TglTrans, l_01, l_11mf;
-- -----
-- ---
-- --ISI DARI DETAIL
-- EXECUTE 'INSERT INTO '||l_TableName1||' SELECT ''0''||SUBSTR(DTL.ACCNO,1,10)||RIGHT(''0000000000000''||LTRIM(DTL.Amount::VARCHAR(13)),13)||''00''||DTL.NIP||DTL.BENEFICLARY||''         ''
-- FROM l_TEMP_DTL DTL ORDER BY DTL.KDCABA;';
---
---
--SELECT l_query=' SET NOCOUNT ON SELECT * FROM '+l_TableName1
--
--EXECUTE (l_query)
---
--
RETURN QUERY 
SELECT Q1.TXT :: CHAR(70) 
FROM 
(
SELECT ('00000000000'::VARCHAR(11)||l_FZ2Cabang||l_Tgl||'01'::VARCHAR(2)||l_FZ2NoRekening||'11MF' ::VARCHAR(4)||l_Ctr||l_Nilai||l_Bln||(EXTRACT(YEAR FROM l_TglTrans)::varchar(4))) :: CHAR(70) AS TXT

UNION ALL 
-----
---
(SELECT ('0'::VARCHAR(1)||SUBSTR(DTL.ACCNO,1,10)||RIGHT('0000000000000'||LTRIM(DTL.Amount::VARCHAR(13)),13)||'00'::VARCHAR(2)||DTL.NIP||DTL.BENEFICLARY||'         ') :: CHAR(70)  AS TXT
FROM l_TEMP_DTL DTL 
ORDER BY DTL.KdCaba)
) Q1;

--
DROP TABLE l_TEMP_DTL;
DROP TABLE l_TEMPS01;
--RETURN l_TableName1; 
END;
$$ LANGUAGE plpgsql ;
/*
DECLARE l_TableName1 	VARCHAR(8)
select  P_BDTXP2 ('2013-11-01'::DATE, '2013-12-31'::DATE, '2013-12-31'::DATE, ' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved','01',' ','ZZZZ',1); 
*/
