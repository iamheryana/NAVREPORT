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
    --
    CREATE TEMP TABLE l_TEMP (
                KdCaba		VARCHAR(4),
                KdUUsa		VARCHAR(4),
                NIP         VARCHAR(10),
                Ctr         BIGINT) ON COMMIT DROP ;
    --
    INSERT INTO l_TEMP (KdCaba, KdUUsa, NIP, Ctr)
    SELECT DISTINCT S01.KDCABA, S01.KDUUSA, S01.NIP, 1 :: BIGINT AS Ctr
    FROM S01HGAJ S01
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT * FROM fn_SECLOGIN(1)) VSL ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
    WHERE (S01.TglPayr BETWEEN l_mdPeriodeFr AND l_mdPeriodeTo) AND 
          (S01.KdCaba BETWEEN l_FCab AND l_TCab) AND
          (S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND 
          COALESCE(S01.BankRef,' ')=' ' AND l_mcCtkRekap='1' ;
    --
    INSERT INTO l_TEMP (KdCaba, KdUUsa, NIP, Ctr)
    SELECT Q1.KDCABA, Q1.KDUUSA, ' ' :: VARCHAR(10) AS NIP, COUNT(1) :: BIGINT AS Ctr
    FROM (--Q1
        SELECT DISTINCT S01.KDCABA, S01.KDUUSA, S01.NIP
        FROM S01HGAJ S01
        INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
        INNER JOIN (SELECT * FROM fn_SECLOGIN(1)) VSL ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
        WHERE (S01.TglPayr BETWEEN l_mdPeriodeFr AND l_mdPeriodeTo) AND 
              (S01.KdCaba BETWEEN l_FCab AND l_TCab) AND
              (S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND 
              COALESCE(S01.BankRef,' ')=' ' AND l_mcCtkRekap='2'
        ) Q1
    GROUP BY Q1.KDCABA, Q1.KDUUSA;
    --
    INSERT INTO l_TEMP (KdCaba, KdUUsa, NIP, Ctr)
    SELECT Q1.KDCABA, ' ' :: VARCHAR(4) AS KDUUSA, ' ' :: VARCHAR(10) AS NIP, COUNT(1) :: BIGINT AS Ctr
    FROM (--Q1
        SELECT DISTINCT S01.KDCABA, S01.KDUUSA, S01.NIP
        FROM S01HGAJ S01
        INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
        INNER JOIN (SELECT * FROM fn_SECLOGIN(1)) VSL ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
        WHERE (S01.TglPayr BETWEEN l_mdPeriodeFr AND l_mdPeriodeTo) AND 
              (S01.KdCaba BETWEEN l_FCab AND l_TCab) AND
              (S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND 
              COALESCE(S01.BankRef,' ')=' ' AND l_mcCtkRekap='3'
        ) Q1
    GROUP BY Q1.KDCABA ;
    --
    RETURN QUERY 
    SELECT Q1.KdCaba, Q1.NmCaba, Q1.KdUUsa, Q1.NmUUsa, Q1.NIP, Q1.Nama, 
           COUNT(*) :: BIGINT AS Ctr, SUM(Q1.Nilai) AS Nilai
    FROM 
    (--Q1 
    SELECT 	S01.KdCaba, M08.NmCaba, S01.KdUUsa, M02.Singkatan AS NmUUsa, S01.NIP, S01.Nama, 
            COALESCE(fn_kpusat(S01.NIP,S01.EncTakeHomePay,l_MyPass) :: DECIMAL(15,2),0) AS Nilai
    FROM S01HGAJ S01
    INNER JOIN M08HCAB M08 ON M08.KdCaba = S01.KdCaba 
    INNER JOIN M02UUSA M02 ON M02.Kode = S01.KdUUsa 
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT * FROM fn_SECLOGIN(l_Usr_Id)) VSL ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
    WHERE (S01.TglPayr BETWEEN l_mdPeriodeFr AND l_mdPeriodeTo) AND 
          (S01.KdCaba BETWEEN l_FCab AND l_TCab) AND l_mcCtkRekap='1' AND
          (S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND COALESCE(S01.BankRef,' ')=' '
    ) Q1
    GROUP BY Q1.KdCaba, Q1.NmCaba, Q1.KdUUsa, Q1.NmUUsa, Q1.NIP, Q1.Nama
    ---------
    UNION ALL
    --------- 
    SELECT Q1.KdCaba, Q1.NmCaba, Q1.KdUUsa, Q1.NmUUsa, Q1.NIP, Q1.Nama, 
           Q1.Ctr, SUM(Q1.Nilai) AS Nilai
    FROM 
    (--Q1 
    SELECT 	S01.KdCaba, M08.SkCaba AS NmCaba, S01.KdUUsa, M02.Keterangan AS NmUUsa, 
            ' ' :: VARCHAR(10) AS NIP, ' ' :: VARCHAR(25) AS Nama, X01.Ctr, 
            COALESCE(fn_kpusat(S01.NIP,S01.EncTakeHomePay,l_MyPass) ::DECIMAL(15,2),0) AS Nilai
    FROM S01HGAJ S01
    INNER JOIN l_TEMP X01 ON X01.KDCABA=S01.KDCABA AND X01.KDUUSA=S01.KDUUSA
    INNER JOIN M08HCAB M08 ON M08.KdCaba = S01.KdCaba 
    INNER JOIN M02UUSA M02 ON M02.Kode = S01.KdUUsa 
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT * FROM fn_SECLOGIN(l_Usr_Id)) VSL ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
    WHERE (S01.TglPayr BETWEEN l_mdPeriodeFr AND l_mdPeriodeTo) AND 
          (S01.KdCaba BETWEEN l_FCab AND l_TCab) AND l_mcCtkRekap='2' AND
          (S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND COALESCE(S01.BankRef,' ')=' '
    ) Q1
    GROUP BY Q1.KdCaba, Q1.NmCaba, Q1.KdUUsa, Q1.NmUUsa, Q1.NIP, Q1.Nama, Q1.Ctr
    ---------
    UNION ALL
    ---------
    SELECT Q1.KdCaba, Q1.NmCaba, Q1.KdUUsa, Q1.NmUUsa, Q1.NIP, Q1.Nama, 
           Q1.Ctr, SUM(Q1.Nilai) AS Nilai
    FROM 
    (--Q1 
    SELECT 	S01.KdCaba, M08.NmCaba, ' ' :: VARCHAR(4) AS KdUUsa, ' ' :: VARCHAR(20) AS NmUUsa, 
            ' ' :: VARCHAR(10) AS NIP, ' ' :: VARCHAR(25) AS Nama, X01.Ctr,
            COALESCE(fn_kpusat(S01.NIP,S01.EncTakeHomePay,l_MyPass) ::DECIMAL(15,2),0) AS Nilai
    FROM S01HGAJ S01
    INNER JOIN l_TEMP X01 ON X01.KDCABA=S01.KDCABA
    INNER JOIN M08HCAB M08 ON M08.KdCaba = S01.KdCaba 
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT * FROM fn_SECLOGIN(l_Usr_Id)) VSL ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
    WHERE (S01.TglPayr BETWEEN l_mdPeriodeFr AND l_mdPeriodeTo) AND 
          (S01.KdCaba BETWEEN l_FCab AND l_TCab) AND l_mcCtkRekap='3' AND
          (S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND COALESCE(S01.BankRef,' ')=' '
    ) Q1
    GROUP BY Q1.KdCaba, Q1.NmCaba, Q1.KdUUsa, Q1.NmUUsa, Q1.NIP, Q1.Nama, Q1.Ctr ;
--
END ; 
$$ LANGUAGE plpgsql ;

/******* END OF PROC *****/

/*
SELECT * FROM R_GTCUP1('2010-01-01','2010-12-31','','ZZZ','','ZZZZ','1','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1);

SELECT * FROM R_GTCUP1('2010-01-01','2010-12-31','','ZZZ','','ZZZZ','2','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1);

SELECT * FROM R_GTCUP1('2010-01-01','2010-12-31','','ZZZ','','ZZZZ','3','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1);



SELECT Q1.KDCABA, COUNT(1) :: INT AS CTR
                FROM (SELECT DISTINCT S01.KDCABA, S01.NIP
                      FROM S01HGAJ S01
                      INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
                      INNER JOIN (SELECT * FROM fn_SECLOGIN(1)) VSL ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
                      WHERE --(S01.TglPayr BETWEEN l_mdPeriodeFr AND l_mdPeriodeTo) AND 
                            --(S01.KdCaba BETWEEN l_FCab AND l_TCab) AND
                            --(S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND 
                            COALESCE(S01.BankRef,' ')=' ') Q1
                      GROUP BY Q1.KDCABA
*/