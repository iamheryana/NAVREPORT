/****************************************
Name sprocs	: R_GTCUP1
Create by	: Peggy
Date		: 19-05-2008
Description	: Proses Untuk Report Gaji Tunai per Cabang dan Unit Usaha    qqqq
Note	    : l_mcCtkRekap='1'  Detail per NIP
                           '2'  Detail per Cabang dan Unit Usaha      
                           '3'  Detail per Cabang                  
*****************************************/
DROP FUNCTION R_GTCUP1 (
            l_mdPeriodeFr	DATE,
			l_mdPeriodeTo	DATE,
            l_FCab          VARCHAR(4),
            l_TCab          VARCHAR(4),
			l_mcUUsaFr      VARCHAR(4),
			l_mcUUsaTo      VARCHAR(4),
			l_mcCtkRekap	VARCHAR(1), 
			l_MyPass        VARCHAR(128),
			l_Usr_Id        INT);
--
CREATE FUNCTION R_GTCUP1 (
            l_mdPeriodeFr   DATE,
			l_mdPeriodeTo	DATE,
            l_FCab          VARCHAR(4),
            l_TCab          VARCHAR(4),
			l_mcUUsaFr      VARCHAR(4),
			l_mcUUsaTo      VARCHAR(4),
			l_mcCtkRekap	VARCHAR(1), 
			l_MyPass        VARCHAR(128),
			l_Usr_Id        INT)
--
RETURNS TABLE (OUTKdCaba VARCHAR(4), 
            OUTNmCaba VARCHAR(80), 
            OUTKdUUsa VARCHAR(4), 
            OUTNmUUsa VARCHAR(20), 
            OUTNIP	  VARCHAR(10), 
            OUTNama   VARCHAR(25),
            OUTCtr    BIGINT,  
            OUTNilai  DECIMAL(15,2)) 
-- 
AS $$ 
--
BEGIN 
    RETURN QUERY 
    SELECT Q1.KdCaba, Q1.NmCaba, Q1.KdUUsa, Q1.NmUUsa, Q1.NIP, Q1.Nama, 
        COUNT(*) AS CTR,  
        SUM(Q1.Nilai) AS Nilai
    FROM 
    (--Q1 
    SELECT 	S01.KdCaba, 
            CASE WHEN l_mcCtkRekap IN ('1','3') THEN M08.NmCaba ELSE M08.SkCaba END AS NmCaba, 
            CASE WHEN l_mcCtkRekap IN ('1','2') THEN S01.KdUUsa ELSE ' ' END AS KdUUsa, 
            CASE WHEN l_mcCtkRekap IN ('1') THEN M02.Singkatan ELSE 
                      (CASE WHEN l_mcCtkRekap IN ('2') THEN M02.Keterangan ELSE ' ' END) END AS NmUUsa,
            CASE WHEN l_mcCtkRekap IN ('1') THEN S01.NIP ELSE ' ' END AS NIP,
            CASE WHEN l_mcCtkRekap IN ('1') THEN S01.Nama ELSE ' ' END AS Nama,
            COALESCE(fn_kpusat(S01.NIP,S01.EncTakeHomePay,l_MyPass) ::DECIMAL(15,2),0) AS Nilai
    FROM S01HGAJ S01
    INNER JOIN M08HCAB M08 ON M08.KdCaba = S01.KdCaba 
    INNER JOIN M02UUSA M02 ON M02.Kode = S01.KdUUsa 
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT * FROM fn_SECLOGIN(l_Usr_Id)) VSL ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
    WHERE (S01.TglPayr BETWEEN l_mdPeriodeFr AND l_mdPeriodeTo) AND 
          (S01.KdCaba BETWEEN l_FCab AND l_TCab) AND
          (S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND COALESCE(S01.BankRef,' ')=' '
    ) Q1
    GROUP BY Q1.KdCaba, Q1.NmCaba, Q1.KdUUsa, Q1.NmUUsa, Q1.NIP, Q1.Nama;
--
END ; 
$$ LANGUAGE plpgsql ;
/******* END OF PROC *****/

/*
SELECT * FROM R_GTCUP1('2010-01-01','2010-01-31','','ZZZ','','ZZZZ','1','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1);

SELECT * FROM R_GTCUP1('2010-01-01','2010-01-31','','ZZZ','','ZZZZ','2','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1);

SELECT * FROM R_GTCUP1('2010-01-01','2010-01-31','','ZZZ','','ZZZZ','3','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1);

*/
