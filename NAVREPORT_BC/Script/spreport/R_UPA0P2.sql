/****************************************
Name sprocs	: R_UPA0P2
Create by	: Deni
Date		: 14-02-2014
Description	: Proses Untuk Report Daftar Upah dan Mutasi Kerja (SubReport)
              Isinya khusus Akumulasi JKK/JKM/JHT/JPK
Call From	: Main Proc
*****************************************/
DROP FUNCTION R_UPA0P2 (l_Tahun     VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
						l_KPAFr     VARCHAR(11),
						l_KPATo     VARCHAR(11),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT);
--
CREATE FUNCTION R_UPA0P2 (l_Tahun   VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
						l_KPAFr     VARCHAR(11),
						l_KPATo     VARCHAR(11),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT)
--
RETURNS TABLE (
		OUTKdDpPt           VARCHAR(4),
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
DECLARE l_Nilai         DECIMAL(15,2);
		l_Pgwi          DECIMAL(6,3);
		l_Perush		DECIMAL(6,3);
		l_PerushJKM     DECIMAL(6,3);
--
BEGIN
    -- 
    SELECT Pgwi, Perush INTO l_Pgwi, l_Perush FROM M18JAMS WHERE Progrm='JHT' ;
    SELECT Perush INTO l_PerushJKM FROM M18JAMS WHERE Progrm='JKM' ;
    -- 
    RETURN QUERY
    SELECT Q1.KdDpPt,
           SUM(CASE WHEN Q1.Bulan = '01' THEN Q1.Nilai ELSE 0 END) AS BLN01,
           SUM(CASE WHEN Q1.Bulan = '02' THEN Q1.Nilai ELSE 0 END) AS BLN02,
           SUM(CASE WHEN Q1.Bulan = '03' THEN Q1.Nilai ELSE 0 END) AS BLN03,
           SUM(CASE WHEN Q1.Bulan = '04' THEN Q1.Nilai ELSE 0 END) AS BLN04,
           SUM(CASE WHEN Q1.Bulan = '05' THEN Q1.Nilai ELSE 0 END) AS BLN05,
           SUM(CASE WHEN Q1.Bulan = '06' THEN Q1.Nilai ELSE 0 END) AS BLN06,
           SUM(CASE WHEN Q1.Bulan = '07' THEN Q1.Nilai ELSE 0 END) AS BLN07,
           SUM(CASE WHEN Q1.Bulan = '08' THEN Q1.Nilai ELSE 0 END) AS BLN08,
           SUM(CASE WHEN Q1.Bulan = '09' THEN Q1.Nilai ELSE 0 END) AS BLN09,
           SUM(CASE WHEN Q1.Bulan = '10' THEN Q1.Nilai ELSE 0 END) AS BLN10,
           SUM(CASE WHEN Q1.Bulan = '11' THEN Q1.Nilai ELSE 0 END) AS BLN11,
           SUM(CASE WHEN Q1.Bulan = '12' THEN Q1.Nilai ELSE 0 END) AS BLN12
    FROM
    (--Q1
    SELECT  CASE WHEN S02.KdDpPt='JKM' AND M15.JHT='Y' THEN 'JHT' ELSE S02.KdDpPt END :: VARCHAR(4) AS KdDpPt, 
            TO_CHAR(S02.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            CASE WHEN S02.KdDpPt='JKM' AND M15.JHT='Y' THEN
                 fn_vround((ABS(COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0)) * (COALESCE(l_Pgwi,0)+COALESCE(l_Perush,0))) / 
                 COALESCE(l_PerushJKM,1))
            ELSE        
                 COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass):: DECIMAL(15,2),0)
            END AS Nilai
    FROM S02DGAJ S02
    INNER JOIN S01HGAJ S01 ON S01.TglPayr=S02.TglPayr AND S01.NIP=S02.NIP	
    INNER JOIN M15PEGA M15 ON S01.NIP = M15.NIP 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE TO_CHAR(S01.TglPayr,'YYYY')=l_Tahun AND S01.KPA<>' ' AND  
          S01.NIP BETWEEN l_NIPFr AND l_NIPTo	AND
          S01.KPA BETWEEN l_KPAFr AND l_KPATo	AND
          TO_CHAR(S01.TglPayr,'YYYYMM')>=TO_CHAR(M15.TglKPA,'YYYYMM') AND
          ((S02.FlgDpPt='D' AND S02.KdDpPt IN ('JKK','JKM')) OR S02.KdDpPt='JPK')
    ) Q1
    GROUP BY Q1.KdDpPt ;
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_UPA0P2 (l_Tahun	VARCHAR(4),
						l_NIPFr     VARCHAR(10),
						l_NIPTo     VARCHAR(10),
						l_KPAFr     VARCHAR(11),
						l_KPATo     VARCHAR(11),
                        l_Mypass	VARCHAR(128),
                        l_UserId	INT)

select * from R_UPA0P2('2013',' ','ZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)
*/




