/********************************************
Name sprocs	: R_BIOPP4
Author		: Deni
Create on	: 25-06-2003
Description	: Listing Absensi Pegawai
Call From	: Main Proc
by peggy 2006 06 07 nambahin status aktif / keluar / all 
*****************************************/
DROP FUNCTION  R_BIOPP4   (l_NIPFr	 VARCHAR(10),
					l_NIPTo     VARCHAR(10),
					l_Period    VARCHAR(4),
					l_mypass    VARCHAR(128), 
					l_sts       VARCHAR(1),
					l_tglstatus DATE,
					l_UserId    INT);
--
CREATE FUNCTION  R_BIOPP4   (l_NIPFr VARCHAR(10),
					l_NIPTo     VARCHAR(10),
					l_Period    VARCHAR(4),
					l_mypass    VARCHAR(128), 
					l_sts       VARCHAR(1),
					l_tglstatus DATE,
					l_UserId    INT)
--
RETURNS TABLE ( OUTNIP          VARCHAR(10), 
                OUTNAMA         VARCHAR(25),
                OUTNamakecil    VARCHAR(10),
                OUTKdarea       VARCHAR(4),
                OUTKetArea      VARCHAR(25),
                OUTKdcaba       VARCHAR(4),
                OUTNmCaba       VARCHAR(25),
                OUTKdUUsa       VARCHAR(4),
                OUTKetUUsa      VARCHAR(25),
                OUTKdUker       VARCHAR(4),
                OUTKetUker      VARCHAR(25),
                OUTKdGolg       VARCHAR(4),
                OUTKetGolg      VARCHAR(25),
                OUTKdKeljab     VARCHAR(4),
                OUTKetKeljab    VARCHAR(25),
                OUTKdJaba       VARCHAR(4),
                OUTKetJaba      VARCHAR(25),
                OUTKdKlas       VARCHAR(4),
                OUTKetKlas      VARCHAR(25),
                OUTKdAbsn       VARCHAR(4),
                OUTKetAbsn      VARCHAR(25),
                OUTHari01       DECIMAL(7,4),
                OUTHari02       DECIMAL(7,4),
                OUTHari03       DECIMAL(7,4),
                OUTHari04       DECIMAL(7,4),
                OUTHari05       DECIMAL(7,4),
                OUTHari06       DECIMAL(7,4),
                OUTHari07       DECIMAL(7,4),
                OUTHari08       DECIMAL(7,4),
                OUTHari09       DECIMAL(7,4),
                OUTHari10       DECIMAL(7,4),
                OUTHari11       DECIMAL(7,4),
                OUTHari12       DECIMAL(7,4),
                OUTJam01        DECIMAL(7,2),
                OUTJam02        DECIMAL(7,2),
                OUTJam03        DECIMAL(7,2),
                OUTJam04        DECIMAL(7,2),
                OUTJam05        DECIMAL(7,2),
                OUTJam06        DECIMAL(7,2),
                OUTJam07        DECIMAL(7,2),
                OUTJam08        DECIMAL(7,2),
                OUTJam09        DECIMAL(7,2),
                OUTJam10        DECIMAL(7,2),
                OUTJam11        DECIMAL(7,2),
                OUTJam12        DECIMAL(7,2))
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT 	M15.NIP, M15.Nama, M15.NamaKecil, M15.KdArea, M01.Keterangan AS KetArea, M15.KdCaba, M08.NmCaba,
            M15.KdUUsa, M02.Keterangan AS KetUsa, M15.KdUKer, M17.Keterangan AS KetUker, M15.KdGlng, 
            M12.Keterangan AS KetGolg, M15.KdKJab, M06.Keterangan AS Ketkeljab, M15.KdJaba, 
            M04.Keterangan AS Ketjab, M15.KdKlas, M10.Keterangan AS Ketklas, S06.KdAbsn, S06.SkAbsn,
            S06.Hari01, S06.Hari02, S06.Hari03, S06.Hari04, S06.Hari05, S06.Hari06, 
            S06.Hari07, S06.Hari08, S06.Hari09, S06.Hari10, S06.Hari11, S06.Hari12, 
            S06.Jam01, S06.Jam02, S06.Jam03, S06.Jam04, S06.Jam05, S06.Jam06, 
            S06.Jam07, S06.Jam08, S06.Jam09, S06.Jam10, S06.Jam11, S06.Jam12
    FROM M15PEGA M15
    INNER JOIN M01AREA M01 ON M15.KdArea = M01.Kode
    INNER JOIN M08HCAB M08 ON M15.KdCaba = M08.KdCaba
    INNER JOIN M02UUSA M02 ON M15.KdUUsa = M02.Kode
    INNER JOIN M17UKER M17 ON M15.KdUKer = M17.KdUker
    INNER JOIN M12HGOL M12 ON M15.KdGlng = M12.Kode
    INNER JOIN M04HJAB M04 ON M15.KdJaba = M04.Kode
    INNER JOIN M06HKJB M06 ON M15.KdKJab = M06.Kode
    INNER JOIN M10KLAS M10 ON M15.KdKlas = M10.Kode
    INNER JOIN
    ( --S06
        SELECT  S06.NIP, S06.KdAbsn, S06.SkAbsn,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='01' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari01,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='02' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari02,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='03' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari03,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='04' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari04,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='05' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari05,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='06' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari06,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='07' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari07,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='08' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari08,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='09' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari09,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='10' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari10,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='11' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari11,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='12' THEN S06.HariAbsn ELSE 0 END) :: DECIMAL(7,4) AS Hari12,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='01' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam01,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='02' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam02,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='03' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam03,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='04' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam04,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='05' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam05,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='06' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam06,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='07' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam07,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='08' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam08,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='09' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam09,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='10' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam10,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='11' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam11,
                SUM(CASE WHEN TO_CHAR(S06.Tanggal,'MM')='12' THEN S06.JamAbsn ELSE 0 END) :: DECIMAL(7,2) AS Jam12
        FROM S06ABSH S06
        WHERE TO_CHAR(S06.Tanggal,'YYYY')=l_Period 
        GROUP BY S06.NIP, S06.KdAbsn, S06.SkAbsn
    ) S06 ON M15.NIP = S06.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          ((l_sts = 'S') or 
           (l_sts = 'A' and ((M15.TglMasuk IS NOT NULL) AND M15.TglMasuk<=l_tglstatus) and  
                            (M15.TglKeluar is null or COALESCE(M15.TglKeluar,l_tglstatus)>=l_tglstatus)) or 
           (l_sts = 'K' and (M15.TglMasuk IS NOT NULL) AND (M15.TglKeluar IS NOT NULL) AND 
                            M15.TglKeluar<=l_tglstatus));
    --   
END;
$$ LANGUAGE plpgsql ;


/* 
SELECT * FROM R_BIOPP4 (' ','ZZ','2012',
            'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',
            'A','2013-01-01',1)
*/