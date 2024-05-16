/****************************************
Name sprocs	: R_DUTKP1
Create by	: Deni
Date		: 12-01-2005
Description	: Proses Untuk Daftar Upah dan TK ke Excel
Call From	: Main Proc
*****************************************/
DROP FUNCTION R_DUTKP1 (l_Periode   DATE,
						l_Mypass	VARCHAR(128),
						l_UserId	INT);
--
CREATE FUNCTION R_DUTKP1 (l_Periode DATE,
						l_Mypass	VARCHAR(128),
						l_UserId	INT)
--
RETURNS TABLE (
        OUTNoAstek          VARCHAR(15),
		OUTNama             VARCHAR(25),
		OUTJnsKelamin       VARCHAR(1),
		OUTTglLahir         DATE,
		OUTTglKPA           DATE,
		OUTPersenJKM        DECIMAL(6,3),
		OUTPersenJPK        DECIMAL(6,3),
		OUTPersenJPKK       DECIMAL(6,3),
		OUTPersenJHT        DECIMAL(6,3),
		OUTNilaiJKK         DECIMAL(15,2),
		OUTNilaiJKM         DECIMAL(15,2),
		OUTNilaJKP          DECIMAL(15,2),
		OUTNilaiJHT         DECIMAL(15,2),
		OUTGajiPokok        DECIMAL(15,2))
--
AS $$
--
DECLARE	ml_Pgwi         DECIMAL(6,3);
		ml_Perush		DECIMAL(6,3);
		ml_PerushJKM	DECIMAL(6,3);
        ml_PgwiJPK      DECIMAL(6,3);
        ml_PgwikJPK     DECIMAL(6,3);
		ml_JKK_Persen	DECIMAL(6,3);
--
BEGIN 
    SELECT Pgwi, Perush  INTO ml_Pgwi, ml_Perush
    FROM M18JAMS WHERE Progrm='JHT' ;
    --
    SELECT Pgwi, Pgwik  INTO ml_PgwiJPK, ml_PgwikJPK
    FROM M18JAMS WHERE Progrm='JPK' ;
    --
    SELECT Perush INTO ml_PerushJKM
    FROM M18JAMS WHERE Progrm='JKM' ;
    --
    RETURN QUERY
    SELECT  Q1.NoAstek, Q1.Nama, Q1.JnsKlmn, Q1.TglLahir, Q1.TglKPA, 
            Q1.PersenJKM, Q1.PersenJPK, Q1.PersenJPKK, Q1.PersenJHT,
            SUM(Q1.JKK_Nilai) AS JKK_Nilai, 
            SUM(Q1.JKM_Nilai) AS JKM_Nilai, 
            SUM(Q1.JPK_Nilai) AS JPK_Nilai, 
            SUM(Q1.JHT_Nilai) AS JHT_Nilai,
            SUM(Q1.GajiPokok) AS GajiPokok 
    FROM 
    (	
    SELECT  Q.NoAstek, Q.Nama, Q.JnsKlmn, Q.TglLahir, Q.TglKPA, 
            COALESCE(ml_PerushJKM,0) AS PersenJKM,
            COALESCE(ml_PgwiJPK,0) AS PersenJPK,
            COALESCE(ml_PgwikJPK,0) AS PersenJPKK,
            COALESCE(ml_Pgwi,0) + COALESCE(ml_Perush,0) AS PersenJHT,
            CASE WHEN Q.JKK = 'Y' AND Q.KdDppt = 'JKK' THEN Q.Nilai ELSE 0 END AS JKK_Nilai, 
            CASE WHEN (Q.JHT = 'Y' AND Q.KdDppt = 'JHT' AND ml_Pgwi <> 0) THEN  
                                -- JHT YANG DIBAYAR COMPANY : UNTUK COVER 2 % DAN 3,7 % 
                                COALESCE(fn_VRound((ABS(Q.Nilai)*(ml_Pgwi+ml_Perush))/ml_Pgwi),0)
                 WHEN (Q.JHT = 'Y' AND Q.KdDppt = 'JKM' AND ml_Pgwi = 0) THEN 
                                -- JHT YANG DIBAYAR COMPANY : UNTUK COVER 0 % DAN 5,7 % : nilai ambil dari jkm dan diitung balik 
                               COALESCE(fn_VRound((ABS(Q.Nilai)*(ml_Pgwi+ml_Perush))/ml_PerushJKM),0) 
                 ELSE 0 END AS JHT_Nilai, 
            CASE WHEN Q.JKM = 'Y' AND Q.KdDppt = 'JKM' THEN Q.Nilai ELSE 0 END AS JKM_Nilai,
            CASE WHEN Q.JPK = 'Y' AND Q.KdDppt = 'JPK' THEN ABS(Q.Nilai) ELSE 0 END AS JPK_Nilai, 
            CASE WHEN Q.KdDppt NOT IN ('JKK','JKM','JHT','JPK') AND Q.M03Status='1' THEN 
                      Q.Nilai ELSE 0 END AS GajiPokok, 
            CASE WHEN Q.FlgDpPt='D' AND Q.KdDpPt='JKK' AND Q.Nilai>0 THEN 
                      COALESCE((SELECT Perush FROM M18JAMS WHERE Progrm='JKK' AND LevelJKK=Q.M15Level),0)
                 ELSE 0 END AS JKK_Persen
    FROM
    (		
    SELECT  M15.KPA AS NoAstek, M15.Nama, M15.JnsKlmn, M15.TglLahir, M15.TglKPA, M15.KdUUsa,
            M15.JKK, M15.JKM, M15.JHT, M15.JPK, S02.FlgDpPt, S02.KdDpPt, M03.Status AS M03Status,
            CASE WHEN M15.Level > '0' THEN M15.Level ELSE M02.Kelompok END :: INT AS M15Level,
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) ::DECIMAL(15,2),0) AS Nilai
    FROM S02DGAJ S02
    INNER JOIN M15PEGA M15 ON S02.NIP=M15.NIP
    INNER JOIN M02UUSA M02 ON M15.KdUUsa = M02.Kode
    INNER JOIN M04HJAB M04 ON M04.Kode=M15.kdjaba
    INNER JOIN M03DPPT M03 ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE S02.FlgDpPt='D' AND M15.KPA <> ' ' AND M15.TGLKPA IS NOT NULL AND 
            TO_CHAR(M15.TglKPA, 'YYYYMM')<= TO_CHAR(l_Periode,'YYYYMM') AND
            TO_CHAR(S02.TglPayr,'YYYYMM') = TO_CHAR(l_Periode,'YYYYMM')  
    ) Q 
    ) Q1 
    GROUP BY Q1.NoAstek, Q1.Nama, Q1.JnsKlmn, Q1.TglLahir, Q1.TglKPA,
             Q1.PersenJKM, Q1.PersenJPK, Q1.PersenJPKK, Q1.PersenJHT ;
    --
END;
--
$$ LANGUAGE plpgsql ;


/* 

CREATE FUNCTION R_DUTKP1 (l_Periode     DATE,
						l_Mypass		VARCHAR(128),
						l_UserId		INT)

select * from R_DUTKP1('2013-11-08','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)
*/


