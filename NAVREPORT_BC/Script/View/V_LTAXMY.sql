/******************************************************************
Name sprocs	: V_LTAXMY
Description	: VIEW SUMMARY PAJAK TGL TERAKHIR PROSES PER NIP PER PERIODE 
Call From	: P_PRE1P1.SQL : PP 1721 A1 
*******************************************************************/ 
DROP VIEW V_LTAXMY;

--
CREATE VIEW V_LTAXMY
AS 

SELECT TMP.PERIODE, TMP.NIP, MAX(TMP.TGLPAYR) AS TGLPAYR 
FROM
( --TMP
SELECT S03.TGLPAYR, DATE_TRUNC('month',S03.TGLPAYR) AS PERIODE, 
	--PERIODE = YEAR(S03.TGLPAYR), BULAN=RIGHT(('00' + RTRIM(CONVERT(CHAR(7),MONTH(S03.TGLPAYR)))),2),  
	M15.NIP  
from S03LTAX S03
--
INNER JOIN M15PEGA M15
ON M15.NIP=S03.NIP
--
INNER JOIN M10KLAS M10
ON M10.Kode=M15.KdKlas
--
INNER JOIN M41JPJK M41 
ON M10.JnsPajak = M41.Kode
--
INNER JOIN M04HJAB M04
On M15.KdJaba = M04.Kode
--
WHERE M41.JnsForm1721 = 'A1' 
--M10.Harian=0 --AND 
--	S03.tglpayr =(select max(s.tglpayr) from S03LTAX s where s.nip=m15.nip AND YEAR(s.tglpayr)=@PERIODE) 
) AS TMP
GROUP BY TMP.PERIODE, TMP.NIP;



/* 
select * from V_LTAXMY where PERIODE = 201201 and nip='00987'
select * from M41JPJK 
*/ 
