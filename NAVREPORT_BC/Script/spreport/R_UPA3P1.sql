/****************************************
Name sprocs	: R_UPA3P1
Create by	: Deni
Date		: 14-02-2014
Description	: Proses Untuk Report Daftar Upah dan Mutasi Kerja (ASTEK)
*****************************************/
DROP FUNCTION R_UPA3P1( l_Tahun     VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
						l_KPAFr     VARCHAR(11),
						l_KPATo     VARCHAR(11),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT);
--
CREATE FUNCTION R_UPA3P1(l_Tahun	VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
						l_KPAFr     VARCHAR(11),
						l_KPATo     VARCHAR(11),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT)
--
RETURNS TABLE (
		OUTRID              VARCHAR(1),
		OUTNIP              VARCHAR(10),
		OUTNama             VARCHAR(25),
		OUTKdUker           VARCHAR(8),
		OUTNamaUker         VARCHAR(25),
		OUTStatus           TEXT,
        OUTJnsKelamin       VARCHAR(1),
        OUTKPA              VARCHAR(15),
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
DECLARE l_KetJKM            VARCHAR(15);
        l_KetJHT            VARCHAR(15);
		l_Pgwi              DECIMAL(6,3);
		l_Perush			DECIMAL(6,3);
		l_PerushJKM         DECIMAL(6,3);
--
BEGIN
    SELECT Perush||'%' :: VARCHAR(15) INTO l_KetJKM FROM M18JAMS WHERE Progrm='JKM' AND LevelJKK=0;
    SELECT Pgwi, Perush, (Pgwi+Perush)||'%' :: VARCHAR(15) INTO l_Pgwi, l_Perush, l_KetJHT FROM M18JAMS WHERE Progrm='JHT';
    SELECT Perush INTO l_PerushJKM FROM M18JAMS WHERE  Progrm='JKM';
    --
    RETURN QUERY
    SELECT  Q1.RID, Q1.NIP, Q1.Nama, Q1.KdUKer, Q1.NmUker, Q1.Status, Q1.JnsKlmn, Q1.KPA,  
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
    ----------------------------------------- RID = '1' -----------------------------------------------
    SELECT  '1' :: VARCHAR(1) AS RID, S02.NIP, S01.Nama, S01.KdUKer, M17.Keterangan AS NmUker, 
            S01.StsSip||'-'||S01.TotAnak AS Status, S01.JnsKlmn, S01.KPA, 
            TO_CHAR(S02.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM M15PEGA M15 
    INNER JOIN S01HGAJ S01 ON M15.NIP=S01.NIP 
    INNER JOIN S02DGAJ S02 ON S01.NIP=S02.NIP AND S01.TglPayr=S02.TglPayr
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.KdUker
    INNER JOIN M20DSLG M20 ON S02.FlgDpPt=M20.FlgDpPt AND S02.KdDpPt=M20.KdDpPt
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE S02.FlgDpPt='D' AND M03.Status='1' AND M20.TipeLap='1' AND S01.KPA<>' ' AND 
          TO_CHAR(S01.TGLPAYR,'YYYY') = l_Tahun AND 
          (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          (S01.KPA BETWEEN l_KPAFr AND l_KPATo) AND
          TO_CHAR(S01.TglPayr,'YYYYMM')>=TO_CHAR(M15.TglKPA,'YYYYMM')
    ---------      
    UNION ALL
    ----------------------------------------- RID = '2' -----------------------------------------------
    SELECT  '2' :: VARCHAR(1) AS RID, ' ' :: VARCHAR(10) AS NIP, 'JKK' :: VARCHAR(25) AS Nama, 
            ' ' :: VARCHAR(8) AS KdUKer, ' ' :: VARCHAR(25) AS NmUker, 
            ' ' :: VARCHAR(5) AS Status, ' ' :: VARCHAR(1) AS JnsKlmn, ' ' :: VARCHAR(15) AS KPA, 
            TO_CHAR(S02.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM S01HGAJ S01
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP 
    INNER JOIN S02DGAJ S02 ON S01.NIP=S02.NIP AND S01.TglPayr=S02.TglPayr
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.KdUker
    INNER JOIN M20DSLG M20 ON S02.FlgDpPt=M20.FlgDpPt AND S02.KdDpPt=M20.KdDpPt
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE S02.FlgDpPt='D' AND S02.KDDpPt='JKK' AND M20.TipeLap='1' AND S01.KPA<>' ' AND
          TO_CHAR(S01.TGLPAYR,'YYYY') = l_Tahun AND 
          (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          (S01.KPA BETWEEN l_KPAFr AND l_KPATo)
    ---------      
    UNION ALL
    ----------------------------------------- RID = '3' -----------------------------------------------
    SELECT  '3' :: VARCHAR(1) AS RID, ' ' :: VARCHAR(10) AS NIP, 'JKM '||l_KetJKM  :: VARCHAR(25) AS Nama, 
            ' ' :: VARCHAR(8) AS KdUKer, ' ' :: VARCHAR(25) AS NmUker, 
            ' ' :: VARCHAR(5) AS Status, ' ' :: VARCHAR(1) AS JnsKlmn, ' ' :: VARCHAR(15) AS KPA, 
            TO_CHAR(S02.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM S01HGAJ S01
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP 
    INNER JOIN S02DGAJ S02 ON S01.NIP=S02.NIP AND S01.TglPayr=S02.TglPayr
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.KdUker
    INNER JOIN M20DSLG M20 ON S02.FlgDpPt=M20.FlgDpPt AND S02.KdDpPt=M20.KdDpPt
    INNER JOIN M18JAMS M18 ON M18.Progrm='JKM' AND LevelJKK=0 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE S02.FlgDpPt='D' AND S02.KDDpPt='JKM' AND M20.TipeLap='1' AND S01.KPA<>' ' AND 
          TO_CHAR(S01.TGLPAYR,'YYYY') = l_TAHUN AND 
          (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          (S01.KPA BETWEEN l_KPAFr AND l_KPATo)
    ---------      
    UNION ALL
    ----------------------------------------- RID = '4' -----------------------------------------------
    SELECT  '4' :: VARCHAR(1) AS RID, ' ' :: VARCHAR(10) AS NIP, 'JHT '||l_KetJHT  :: VARCHAR(25) AS Nama, 
            ' ' :: VARCHAR(8) AS KdUKer, ' ' :: VARCHAR(25) AS NmUker, 
            ' ' :: VARCHAR(5) AS Status, ' ' :: VARCHAR(1) AS JnsKlmn, ' ' :: VARCHAR(15) AS KPA, 
            TO_CHAR(S02.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            fn_vround((ABS(COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0)) * (COALESCE(l_Pgwi,0)+COALESCE(l_Perush,0))) / 
             COALESCE(l_PerushJKM,1)) AS Nilai
	FROM S01HGAJ S01
	INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP 
	INNER JOIN S02DGAJ S02 ON S01.NIP=S02.NIP AND S01.TglPayr=S02.TglPayr
	INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt
	INNER JOIN M17UKER M17 ON M17.KdUker=S01.KdUker
    INNER JOIN M20DSLG M20 ON S02.FlgDpPt=M20.FlgDpPt AND S02.KdDpPt=M20.KdDpPt
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE S02.FlgDpPt='D' AND S02.KDDpPt='JKM' AND M15.JHT='Y' AND M20.TipeLap='1' AND S01.KPA<>' ' AND 
          TO_CHAR(S01.TGLPAYR,'YYYY') = l_TAHUN AND 
          (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          (S01.KPA BETWEEN l_KPAFr AND l_KPATo) 
    ---------      
    UNION ALL
    ----------------------------------------- RID = '5' -----------------------------------------------
    SELECT  '5' :: VARCHAR(1) AS RID, ' ' :: VARCHAR(10) AS NIP, 'JPK' :: VARCHAR(25) AS Nama, 
            ' ' :: VARCHAR(8) AS KdUKer, ' ' :: VARCHAR(25) AS NmUker, 
            ' ' :: VARCHAR(5) AS Status, ' ' :: VARCHAR(1) AS JnsKlmn, ' ' :: VARCHAR(15) AS KPA, 
            TO_CHAR(S02.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM S01HGAJ S01
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP 
    INNER JOIN S02DGAJ S02 ON S01.NIP=S02.NIP AND S01.TglPayr=S02.TglPayr
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.KdUker
    INNER JOIN M20DSLG M20 ON S02.FlgDpPt=M20.FlgDpPt AND S02.KdDpPt=M20.KdDpPt
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE S02.FlgDpPt='D' AND S02.KDDpPt='JPK' AND M20.TipeLap='1' AND S01.KPA<>' ' AND 
          TO_CHAR(S01.TGLPAYR,'YYYY') = l_TAHUN AND 
          (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          (S01.KPA BETWEEN l_KPAFr AND l_KPATo)  
    ) Q1
    GROUP BY  Q1.RID, Q1.NIP, Q1.Nama, Q1.KdUKer, Q1.NmUker, Q1.Status, Q1.JnsKlmn, Q1.KPA ;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_UPA3P1(l_Tahun	VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
						l_KPAFr     VARCHAR(11),
						l_KPATo     VARCHAR(11),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT)

select * from R_UPA3P1('2013',' ','ZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)
*/



