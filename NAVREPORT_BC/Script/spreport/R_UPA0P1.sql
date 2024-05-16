/****************************************
Name sprocs	: R_UPA0P1
Create by	: Deni
Date		: 07-02-2014
Description	: Proses Untuk Report Daftar Upah dan Mutasi Kerja
Call From	: Main Proc
*****************************************/
DROP FUNCTION R_UPA0P1 (l_Tahun     VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
						l_KPAFr     VARCHAR(11),
						l_KPATo     VARCHAR(11),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT);

CREATE FUNCTION R_UPA0P1 (l_Tahun	VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
						l_KPAFr     VARCHAR(11),
						l_KPATo     VARCHAR(11),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT)
--
RETURNS TABLE (
		OUTNIP              VARCHAR(10),
		OUTNama             VARCHAR(25),
		OUTKdUker           VARCHAR(8),
		OUTNamaUker         VARCHAR(25),
		OUTStatus           TEXT,
        OUTJnsKelamin       VARCHAR(1),
        OUTKPA              VARCHAR(15),
		OUTNilai            DECIMAL(15,2),
		OUTBulan01          DECIMAL(15,2),
		OUTBulan02          DECIMAL(15,2),
		OUTBulan03          DECIMAL(15,2),
		OUTBulan04          DECIMAL(15,2),
		OUTBulan05          DECIMAL(15,2),
		OUTBulan06          DECIMAL(15,2),
		OUTBulan07          DECIMAL(15,2),
		OUTBulan08          DECIMAL(15,2),
		OUTBulan09          DECIMAL(15,2),
		OUTBulan10          DECIMAL(15,2),
		OUTBulan11          DECIMAL(15,2),
		OUTBulan12          DECIMAL(15,2))
--
AS $$
--
BEGIN
    RETURN QUERY
    SELECT  Q1.NIP, Q1.Nama, Q1.KdUKer, Q1.NmUker, Q1.Status, Q1.JnsKlmn, Q1.NoKPA,  
            SUM(Q1.Nilai) AS Nilai,
            SUM(CASE WHEN Q1.BULAN = '01' THEN (Q1.Nilai) ELSE 0 END) AS BLN01, 
            SUM(CASE WHEN Q1.BULAN = '02' THEN (Q1.Nilai) ELSE 0 END) AS BLN02, 
            SUM(CASE WHEN Q1.BULAN = '03' THEN (Q1.Nilai) ELSE 0 END) AS BLN03, 
            SUM(CASE WHEN Q1.BULAN = '04' THEN (Q1.Nilai) ELSE 0 END) AS BLN04, 
            SUM(CASE WHEN Q1.BULAN = '05' THEN (Q1.Nilai) ELSE 0 END) AS BLN05, 
            SUM(CASE WHEN Q1.BULAN = '06' THEN (Q1.Nilai) ELSE 0 END) AS BLN06, 
            SUM(CASE WHEN Q1.BULAN = '07' THEN (Q1.Nilai) ELSE 0 END) AS BLN07, 
            SUM(CASE WHEN Q1.BULAN = '08' THEN (Q1.Nilai) ELSE 0 END) AS BLN08, 
            SUM(CASE WHEN Q1.BULAN = '09' THEN (Q1.Nilai) ELSE 0 END) AS BLN09, 
            SUM(CASE WHEN Q1.BULAN = '10' THEN (Q1.Nilai) ELSE 0 END) AS BLN10, 
            SUM(CASE WHEN Q1.BULAN = '11' THEN (Q1.Nilai) ELSE 0 END) AS BLN11, 
            SUM(CASE WHEN Q1.BULAN = '12' THEN (Q1.Nilai) ELSE 0 END) AS BLN12 
    FROM
    (--Q1
    SELECT  S02.NIP, S01.Nama, S01.KdUKer, M17.Keterangan AS NmUker, 
            S01.StsSip||'-'||S01.TotAnak AS Status,
            S01.JnsKlmn, S01.KPA AS NoKPA,
            TO_CHAR(S02.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM M19HSLG M19
    INNER JOIN M20DSLG M20 ON M19.TipeLap=M20.TipeLap AND M19.FlgDpPt=M20.FlgDpPt AND M19.NomFormat=M20.NomFormat	
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt=M20.FlgDpPt AND M03.KdDpPt =M20.KdDpPt
    INNER JOIN S02DGAJ S02 ON S02.FlgDpPt=M19.FlgDpPt AND S02.KdDpPt =M20.KdDpPt  AND S02.FlgAngs=M20.FlgAngs
    INNER JOIN S01HGAJ S01 ON S01.TglPayr=S02.TglPayr AND S01.NIP=S02.NIP	
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.kdUker
    INNER JOIN M15PEGA M15 ON S01.NIP = M15.NIP 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE M19.TipeLap='1' AND M19.FlgDpPt<>'P' AND M03.Status='1'  AND S01.KPA<>' ' AND 
          M15.TGLKPA IS NOT NULL AND TO_CHAR(S01.TglPayr,'YYYY')=l_Tahun AND
          S01.NIP BETWEEN l_NIPFr AND l_NIPTo AND
          S01.KPA BETWEEN l_KPAFr AND l_KPATo AND
          TO_CHAR(S01.TglPayr,'YYYYMM')>=TO_CHAR(M15.TglKPA,'YYYYMM')
    ) Q1 
    GROUP BY Q1.NIP, Q1.Nama, Q1.KdUKer, Q1.NmUker, Q1.Status, Q1.JnsKlmn, Q1.NoKPA;
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_UPA0P1 (l_Tahun	VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
						l_KPAFr     VARCHAR(11),
						l_KPATo     VARCHAR(11),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT)

select * from R_UPA0P1('2013',' ','ZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)
*/
