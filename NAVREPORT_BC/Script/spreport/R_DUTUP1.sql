/****************************************
Name sprocs	: R_DUTUP1
Create by	: Deni
Date		: 19-11-2007
Description	: DAFTAR UPAH dan TENAGA KERJA per UNIT USAHA
Call From	: Main Proc
*****************************************/
DROP FUNCTION R_DUTUP1 (l_Periode       DATE,
						l_mcUUsaFr      VARCHAR(4),
						l_mcUUsaTo      VARCHAR(4),
						l_Laporan       VARCHAR(1),
						l_Mypass		VARCHAR(128),
						l_UserId		INT);
--
CREATE FUNCTION R_DUTUP1 (l_Periode     DATE,
						l_mcUUsaFr      VARCHAR(4),
						l_mcUUsaTo      VARCHAR(4),
						l_Laporan       VARCHAR(1),
						l_Mypass		VARCHAR(128),
						l_UserId		INT)
--
RETURNS TABLE (
		OUTKdUUsa           VARCHAR(4),
		OUTNmUUsa           VARCHAR(25),
		OUTNIP              VARCHAR(10),
		OUTNama             VARCHAR(25),
		OUTPersenJKK        DECIMAL(6,3),
		OUTJmlKaryawan      DECIMAL(6,0),
		OUTNilaiJKK         DECIMAL(6,3),
		OUTNilaiJKM         DECIMAL(15,2),
		OUTGajiPokok        DECIMAL(15,2),
		OUTNilaJPK          DECIMAL(15,2),
		OUTNilaiJHT2        DECIMAL(15,2),
		OUTNilaiJHT37       DECIMAL(15,2),
		OUTml_Pgwi	    DECIMAL(15,2),
		OUTml_Perush        DECIMAL(15,2),
		OUTml_PgwiJPK       DECIMAL(15,2),
		OUTml_PgwikJPK      DECIMAL(15,2),
		OUTml_PerushJKM     DECIMAL(15,2))
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
    SELECT COALESCE(Perush,0) INTO ml_PerushJKM
    FROM M18JAMS WHERE Progrm='JKM' ;
    --
    RETURN QUERY
    SELECT  Q3.KdUUsa, Q3.NmUUsa, Q3.NIP, Q3.Nama, Q3.JKK_Persen,
            COUNT(*) :: DECIMAL(6,0) AS JumlahKaryawan, 
            SUM(Q3.JKK_Nilai) AS JKK_Nilai, 
            SUM(Q3.JKM_Nilai) AS JKM_Nilai, 
            SUM(Q3.GajiPokok) AS GajiPokok, 
            SUM(Q3.JPK_Nilai) AS JPK_Nilai, 
            SUM(Q3.JHT_Nilai2) AS JHT_Nilai2, 
            SUM(Q3.JHT_Nilai37) AS JHT_Nilai37,
	    Q3.ml_Pgwi, Q3.ml_Perush, Q3.ml_PgwiJPK, Q3.ml_PgwikJPK, Q3.ml_PerushJKM            
    FROM
    (
    SELECT  Q2.KdUUsa, Q2.NmUUsa, 
            (CASE WHEN l_Laporan = 'D' THEN Q2.NIP ELSE '' END) AS NIP, 
            (CASE WHEN l_Laporan = 'D' THEN Q2.Nama ELSE '' END) AS Nama, 
            Q2.JKK_Nilai, Q2.JKM_Nilai,	Q2.GajiPokok, Q2.JPK_Nilai,
            Q2.JHT_Nilai2, Q2.JHT_Nilai37, Q2.JKK_Persen,
            COALESCE(ml_Pgwi,0) AS ml_Pgwi,
            COALESCE(ml_Perush,0) AS ml_Perush,
            COALESCE(ml_PgwiJPK,0) AS ml_PgwiJPK,
            COALESCE(ml_PgwikJPK,0) AS ml_PgwikJPK,
            COALESCE(ml_PerushJKM,0) AS ml_PerushJKM
    FROM
    (
    SELECT Q1.KdUUsa, Q1.NmUUsa, Q1.NIP, Q1.Nama,
            SUM(Q1.JKK_Nilai) AS JKK_Nilai, 
            SUM(Q1.JKM_Nilai) AS JKM_Nilai, 
            SUM(Q1.GajiPokok) AS GajiPokok, 
            SUM(Q1.JPK_Nilai) AS JPK_Nilai, 
            SUM(Q1.JHT_Nilai2) AS JHT_Nilai2, 
            SUM(Q1.JHT_NilaiPerush) AS JHT_Nilai37, 
            SUM(Q1.JKK_Persen) AS JKK_Persen
    FROM 
    (	
    SELECT Q.KdUUsa, Q.NmUUsa, Q.NIP, Q.Nama,
            COALESCE(ml_PerushJKM,0) AS PersenJKM,
            COALESCE(ml_PgwiJPK,0) AS PersenJPK,
            COALESCE(ml_PgwikJPK,0) AS PersenJPKK,
            COALESCE(ml_Pgwi,0) + COALESCE(ml_Perush,0) AS PersenJHT,
            CASE WHEN Q.JKK = 'Y' AND Q.KdDppt = 'JKK' THEN Q.Nilai ELSE 0 END AS JKK_Nilai, 
            CASE WHEN Q.JKM = 'Y' AND Q.KdDppt = 'JKM' THEN Q.Nilai ELSE 0 END AS JKM_Nilai,
            CASE WHEN Q.KdDppt NOT IN ('JKK','JKM','JHT','JPK') AND Q.M03Status='1' THEN 
                      Q.Nilai ELSE 0 END AS GajiPokok, 
            CASE WHEN Q.JPK = 'Y' AND Q.KdDppt = 'JPK' THEN ABS(Q.Nilai) ELSE 0 END AS JPK_Nilai, 
            CASE WHEN Q.JHT = 'Y' AND Q.KdDppt = 'JHT' THEN ABS(Q.Nilai) ELSE 0 END AS JHT_Nilai2, 
            CASE WHEN (Q.JHT = 'Y' AND Q.KdDppt = 'JHT' AND ml_Pgwi <> 0) THEN  
                                -- JHT YANG DIBAYAR COMPANY : UNTUK COVER 2 % DAN 3,7 % 
                                COALESCE(fn_VRound((ABS(Q.Nilai)*(ml_Pgwi+ml_Perush))/ml_Pgwi),0)
                 WHEN (Q.JHT = 'Y' AND Q.KdDppt = 'JKM' AND ml_Pgwi = 0) THEN 
                                -- JHT YANG DIBAYAR COMPANY : UNTUK COVER 0 % DAN 5,7 % : nilai ambil dari jkm dan diitung balik 
                               COALESCE(fn_VRound((ABS(Q.Nilai)*(ml_Pgwi+ml_Perush))/ml_PerushJKM),0) 
                 ELSE 0 END AS JHT_NilaiPerush, 
            CASE WHEN Q.FlgDpPt='D' AND Q.KdDpPt='JKK' AND Q.Nilai>0 THEN 
                      COALESCE((SELECT Perush FROM M18JAMS WHERE Progrm='JKK' AND LevelJKK=Q.M15Level),0)
                 ELSE 0 END AS JKK_Persen
    FROM 
    (	
    SELECT  S01.KdUUsa, M02.Keterangan AS NmUUsa, S02.NIP, M15.Nama, M15.JKK, M15.JKM, M15.JPK, 
            M15.JHT, S02.FlgDpPt, S02.KdDpPt, M03.Status  AS M03Status,
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) ::DECIMAL(15,2),0) AS Nilai,
            CASE WHEN M15.Level > '0' THEN M15.Level ELSE M02.Kelompok END :: INT AS M15Level
    FROM S02DGAJ S02
    INNER JOIN S01HGAJ S01 ON S02.TglPayr = S01.TglPayr AND S02.NIP = S01.NIP
    INNER JOIN M02UUSA M02 ON S01.KdUUsa = M02.Kode
    INNER JOIN M15PEGA M15 ON S02.NIP=M15.NIP
    INNER JOIN M04HJAB M04 ON M04.Kode=M15.kdjaba
    INNER JOIN M03DPPT M03 ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE S02.FlgDpPt='D' AND M15.KPA <> ' ' AND M15.TGLKPA IS NOT NULL AND 
          TO_CHAR(M15.TglKPA, 'YYYYMM')<= TO_CHAR(l_Periode,'YYYYMM') AND
          TO_CHAR(S02.TglPayr,'YYYYMM') = TO_CHAR(l_Periode,'YYYYMM') AND 
          (S01.KdUUSa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) 
    ) Q
    ) Q1 
    GROUP BY Q1.KdUUsa, Q1.NmUUsa, Q1.NIP, Q1.Nama
    ) Q2 
    ) Q3 
    GROUP BY Q3.KdUUsa, Q3.NmUUsa, Q3.NIP, Q3.Nama, Q3.JKK_Persen,
	     Q3.ml_Pgwi, Q3.ml_Perush, Q3.ml_PgwiJPK, Q3.ml_PgwikJPK, Q3.ml_PerushJKM ;
    --
END;
--
$$ LANGUAGE plpgsql ;


/* 

CREATE FUNCTION R_DUTUP1 (l_Periode     DATE,
						l_mcUUsaFr      VARCHAR(4),
						l_mcUUsaTo      VARCHAR(4),
						l_Laporan       VARCHAR(1),
						l_Mypass		VARCHAR(128),
						l_UserId		INT)

select * from R_DUTUP1('2013-11-08',' ','ZZZ','D','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)
*/

