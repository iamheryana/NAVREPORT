/****************************************
Name sprocs	: R_CASHP1
Create by	: Herz
Date		: 10-09-2001
Description	: Proses Untuk Report Uang Tunai
*****************************************/
DROP FUNCTION R_CASHP1 (
            l_Periode	DATE,
            l_FCab      VARCHAR(4),
            l_TCab      VARCHAR(4),
			l_CtkRekap	VARCHAR(1), 
			l_MyPass	VARCHAR(128),
			l_Usr_Id	INT);
--
CREATE FUNCTION R_CASHP1 (
            l_Periode	DATE,
            l_FCab      VARCHAR(4),
            l_TCab      VARCHAR(4),
			l_CtkRekap	VARCHAR(1), 
			l_MyPass	VARCHAR(128),
			l_Usr_Id	INT)
--
RETURNS TABLE (
        OUTTpRpt    	VARCHAR(1),
		OUTArea         VARCHAR(4),
		OUTNmArea       VARCHAR(10),
		OUTCabang       VARCHAR(4) ,
		OUTNmCabang     VARCHAR(10),
		OUTUUsa         VARCHAR(4) ,
		OUTNmUUsa       VARCHAR(10),
		OUTUKer         VARCHAR(8) ,
		OUTNmUker       VARCHAR(10),	
		OUTNIP          VARCHAR(10),
		OUTNama         VARCHAR(25),
		OUTTakeHomePay	INT,
		OUTLbr100000	INT,
		OUTLbr50000 	INT,
		OUTLbr20000 	INT,
		OUTLbr10000     INT,
		OUTLbr5000  	INT,
		OUTLbr2000  	INT,
		OUTLbr1000  	INT,
		OUTLbr500   	INT,
		OUTLbr200   	INT,
		OUTLbr100   	INT,
		OUTLbr50    	INT)
-- 
AS $$ 
--
BEGIN 
    RETURN QUERY 
    SELECT  Q1.CtkRpt, Q1.KdArea, Q1.NmArea, Q1.KdCaba, Q1.NmCabang, Q1.KdUUsa, Q1.NmUUsa,
            Q1.KdUKer, Q1.NmUker, Q1.NIP, Q1.Nama,
            SUM(Q1.Nilai) :: INT AS Nilai,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,100000)  :: INT AS Lbr100000,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,50000) :: INT AS Lbr50000,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,20000) :: INT AS Lbr20000,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,10000) :: INT AS Lbr10000,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,5000) :: INT AS Lbr5000,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,2000) :: INT AS Lbr2000,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,1000) :: INT AS Lbr1000,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,500) :: INT AS Lbr500,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,200) :: INT AS Lbr200,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,100) :: INT AS Lbr100,
            Fn_GetLembarUang(SUM(Q1.Nilai) :: INT,50) :: INT AS Lbr50
    FROM
    (--
    SELECT 	l_CtkRekap AS CtkRpt, S01.KdArea, M01.Singkatan AS NmArea, S01.KdCaba, M08.SkCaba AS NmCabang,
            S01.KdUUsa, M02.Singkatan AS NmUUsa, S01.KdUKer, M17.Singkatan AS NmUker,
            (CASE WHEN l_CtkRekap='D' THEN S01.NIP ELSE ' ' END) AS NIP, 
            CASE WHEN l_CtkRekap='D' THEN S01.Nama ELSE ' ' END AS Nama,
            COALESCE(Fn_Kpusat(S01.NIP, S01.EncTakeHomePay, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM S01HGAJ S01
    INNER JOIN M01AREA M01 ON S01.KdArea =M01.Kode
    INNER JOIN M08HCAB M08 ON S01.KdCaba=M08.KdCaba
    INNER JOIN M02UUSA M02 ON S01.KdUUsa=M02.Kode
    INNER JOIN M17UKER M17 ON S01.KdUKer=M17.KdUker 
    INNER JOIN M15PEGA M15 ON S01.NIP=M15.NIP
    INNER JOIN (SELECT * FROM fn_SECLOGIN(l_Usr_Id)) VSL ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
    WHERE --(S01.KdCaba BETWEEN l_FCab AND l_TCab) AND 
          S01.TglPayr=l_Periode AND COALESCE(S01.BankRef,' ')=' '
    ) Q1
    GROUP BY Q1.CtkRpt, Q1.KdArea, Q1.NmArea, Q1.KdCaba, Q1.NmCabang, Q1.KdUUsa, Q1.NmUUsa,
             Q1.KdUKer, Q1.NmUker, Q1.NIP, Q1.Nama ;
--
END ; 
$$ LANGUAGE plpgsql ;

/******* END OF PROC *****/

/*
SELECT * FROM R_CASHP1 ('2010-12-15', ' ', 'ZZZ', 'R', 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1);
SELECT * FROM R_CASHP1 ('2010-02-01', ' ', 'ZZZ', 'D', 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1);
*/
