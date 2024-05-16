/****************************************
Name sprocs	: R_DMUPP1
Create by	: Deni
Date		: 30-01-2014
Description	: Daftar Mutasi Upah
*****************************************/
DROP FUNCTION R_DMUPP1  (	l_mdPeriode DATE,
                            l_Mypass    VARCHAR(128),
                            l_UserId    INT);
--
CREATE FUNCTION R_DMUPP1  (	l_mdPeriode DATE,
                            l_Mypass    VARCHAR(128),
                            l_UserId    INT)
--
RETURNS TABLE (OUTNIP   VARCHAR(10),
		OUTNoAstek      VARCHAR(15),
		OUTNama         VARCHAR(25),
		OUTKini         DECIMAL(15,2),
		OUTLalu         DECIMAL(15,2),
		OUTTandaTangan  VARCHAR(25),
		OUTJabatan	VARCHAR(25),
		OUTNPP		VARCHAR(8))
--
AS $$
--
DECLARE	ml_periodeBfr   DATE ;
	l_tandatangan 	VARCHAR(25);
	l_jabatan 	VARCHAR(25);
	l_npp 		VARCHAR(8);
--
BEGIN 
    --
    SELECT NPP INTO l_npp
    FROM FZ1FLDA;
    --
    SELECT Nama, Jabatan INTO l_tandatangan, l_jabatan
    FROM M33JABT WHERE Kode='04';
    --	
    ml_periodeBfr := l_mdPeriode - INTERVAL '1 month';
    --
    RETURN QUERY
    SELECT Q1.NIP, Q1.KPA, Q1.Nama, Q1.NilKini, Q1.NilLalu, l_tandatangan, l_jabatan, l_npp
    FROM
    ( -- Q1
        SELECT Q.NIP, Q.KPA, Q.Nama, 
               SUM(Q.PdTetapKini) AS NilKini, 
               SUM(Q.PdTetapLalu) AS NilLalu 
        FROM
        ( --Q
            SELECT  S01.NIP, M15.KPA, M15.Nama,
                    COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) ::DECIMAL(15,2),0) AS PdTetapKini,
                    0 AS PdTetapLalu
            FROM S01HGAJ S01
            INNER JOIN M15PEGA M15 ON S01.NIP = M15.NIP	
            INNER JOIN S02DGAJ S02 ON S01.TglPayr = S02.TglPayr AND S01.NIP = S02.NIP
            INNER JOIN M03DPPT M03 ON S02.FlgDpPt = M03.FlgDpPt AND S02.KdDpPt = M03.KdDpPt
            INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                        FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
            WHERE M03.Status = '1' AND M15.TGLKPA IS NOT NULL AND
                  TO_CHAR(S01.TglPayr,'YYYYMM') = TO_CHAR(l_mdPeriode,'YYYYMM') AND 
                  TO_CHAR(M15.TglKPA,'YYYYMM') <= TO_CHAR(l_mdPeriode,'YYYYMM') AND 
                  TO_CHAR(M15.TglKPA,'YYYYMM') <= TO_CHAR(S01.TglPayr,'YYYYMM')  
            ---------
            UNION ALL
            ---------
            SELECT  S01.NIP, M15.KPA, M15.Nama,
                    0 AS PdTetapKini,
                    COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) ::DECIMAL(15,2),0) AS PdTetapLalu
            FROM S01HGAJ S01
            INNER JOIN M15PEGA M15 ON S01.NIP = M15.NIP	
            INNER JOIN S02DGAJ S02 ON S01.TglPayr = S02.TglPayr AND S01.NIP = S02.NIP
            INNER JOIN M03DPPT M03 ON S02.FlgDpPt = M03.FlgDpPt AND S02.KdDpPt = M03.KdDpPt
            INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                        FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
            WHERE M03.Status = '1' AND M15.TGLKPA IS NOT NULL AND
                  TO_CHAR(S01.TglPayr,'YYYYMM') = TO_CHAR(ml_periodeBfr,'YYYYMM') AND 
                  TO_CHAR(M15.TglKPA,'YYYYMM') <= TO_CHAR(ml_periodeBfr,'YYYYMM') AND 
                  TO_CHAR(M15.TglKPA,'YYYYMM') <= TO_CHAR(S01.TglPayr,'YYYYMM')  
        ) Q
        GROUP BY Q.NIP, Q.KPA, Q.Nama	
    ) Q1
    WHERE Q1.NilKini<>Q1.NilLalu AND Q1.NilKini>0 AND Q1.NilLalu>0 
;
    --
END;
--
$$ LANGUAGE plpgsql ;

/* 

select * from R_DMUPP1('2014-01-01','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)

*/