/****************************************
Name sprocs	: R_KAR3P1
Create by	: Ubed
Date		: 25-09-2001
Description	: Proses Untuk Report Kartu Slip per Cabang
*****************************************/
DROP FUNCTION R_KAR3P1 (l_Tahun   VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
                        l_FCab      VARCHAR(4),
                        l_TCab      VARCHAR(4),
						l_PendFr    VARCHAR(4),
						l_PendTo	VARCHAR(4),
						l_PotFr     VARCHAR(4),
						l_PotTo     VARCHAR(4),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT) ;
--
CREATE FUNCTION R_KAR3P1 (l_Tahun   VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
                        l_FCab      VARCHAR(4),
                        l_TCab      VARCHAR(4),
						l_PendFr    VARCHAR(4),
						l_PendTo	VARCHAR(4),
						l_PotFr     VARCHAR(4),
						l_PotTo     VARCHAR(4),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT)
--
RETURNS TABLE (
		NIP			VARCHAR(10),
		Nama		VARCHAR(25),
		KdUker		VARCHAR(8),
		NmUker		VARCHAR(20),
		kdCabang	VARCHAR(4),
		NmCabang	VARCHAR(20),
		Status		TEXT,
		JnsKlmn		VARCHAR(1),
		NoKPA		VARCHAR(11),
		Nilai		DECIMAL(15,2),
		BLN01		DECIMAL(15,2), 
		BLN02		DECIMAL(15,2), 
		BLN03		DECIMAL(15,2), 
		BLN04		DECIMAL(15,2), 
		BLN05		DECIMAL(15,2), 
		BLN06		DECIMAL(15,2), 
		BLN07		DECIMAL(15,2), 
		BLN08		DECIMAL(15,2), 
		BLN09		DECIMAL(15,2), 
		BLN10		DECIMAL(15,2), 
		BLN11		DECIMAL(15,2), 
		BLN12		DECIMAL(15,2))
--
AS $$
--
BEGIN
    RETURN QUERY
    SELECT  Q1.NIP, Q1.Nama, Q1.KdUKer, Q1.NmUker, Q1.KdCaba, Q1.NmCaba, Q1.Status, Q1.JnsKlmn, Q1.KPA,  
            SUM(Q1.Nilai) AS Nilai,
            SUM(CASE WHEN Q1.BULAN = '01' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN01, 
            SUM(CASE WHEN Q1.BULAN = '02' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN02, 
            SUM(CASE WHEN Q1.BULAN = '03' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN03, 
            SUM(CASE WHEN Q1.BULAN = '04' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN04, 
            SUM(CASE WHEN Q1.BULAN = '05' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN05, 
            SUM(CASE WHEN Q1.BULAN = '06' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN06, 
            SUM(CASE WHEN Q1.BULAN = '07' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN07, 
            SUM(CASE WHEN Q1.BULAN = '08' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN08, 
            SUM(CASE WHEN Q1.BULAN = '09' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN09, 
            SUM(CASE WHEN Q1.BULAN = '10' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN10, 
            SUM(CASE WHEN Q1.BULAN = '11' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN11, 
            SUM(CASE WHEN Q1.BULAN = '12' THEN (CASE WHEN Q1.FlgDpPt='D' THEN Q1.Nilai ELSE Q1.Nilai*-1 END) ELSE 0 END) AS BLN12 
    FROM
    (--Q1
    SELECT  S01.NIP, S01.Nama, S01.KdUKer, M17.Keterangan AS NmUker, S01.KdCaba, M08.NmCaba,
            S01.StsSip||'-'||S01.TotAnak AS Status, S01.JnsKlmn, S01.KPA, M20.FlgDpPt,
            TO_CHAR(S02.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM M19HSLG M19
    INNER JOIN M20DSLG M20 ON M19.TipeLap=M20.TipeLap AND M19.FlgDpPt=M20.FlgDpPt AND M19.NomFormat=M20.NomFormat
    INNER JOIN S02DGAJ S02 ON S02.FlgDpPt=M19.FlgDpPt AND S02.KdDpPt=M20.KdDpPt AND S02.FlgAngs=M20.FlgAngs
    INNER JOIN S01HGAJ S01 ON S02.TglPayr=S01.TglPayr AND S02.NIP=S01.NIP
    INNER JOIN V_HGAJ VG ON FN_CARITGLPAYR(S02.NIP, S02.TglPayr)=VG.TglPayr AND S02.NIP=VG.NIP
    INNER JOIN M08HCAB M08 ON M08.KdCaba=S01.KdCaba
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.kdUker
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE M19.TipeLap='3' AND TO_CHAR(S01.TglPayr,'YYYY')=l_Tahun AND
          (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          (S01.kdCaba BETWEEN l_FCab AND l_TCab) AND 
          ((M20.FlgDpPt='P' AND (M20.KdDpPt BETWEEN l_PotFr AND l_PotTo)) OR
           (M20.FlgDpPt='D' AND (M20.KdDpPt BETWEEN l_PendFr AND l_PendTo))) 
    )Q1
    GROUP BY Q1.NIP, Q1.Nama, Q1.KdUKer, Q1.NmUker, Q1.KdCaba, Q1.NmCaba, Q1.Status, Q1.JnsKlmn, Q1.KPA;
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_KAR3P1 (l_Tahun   VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
                        l_FCab      VARCHAR(4),
                        l_TCab      VARCHAR(4),
						l_PendFr    VARCHAR(4),
						l_PendTo	VARCHAR(4),
						l_PotFr     VARCHAR(4),
						l_PotTo     VARCHAR(4),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT)

select * from R_KAR3P1('2013',' ','ZZZ',' ','ZZZ',' ','ZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)
*/




