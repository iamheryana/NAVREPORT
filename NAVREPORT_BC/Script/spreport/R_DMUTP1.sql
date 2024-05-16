/****************************************
Name sprocs	: R_DMUTP1
Create by	: Deni
Date		: 03-09-2002
Description	: Daftar Upah dan Mutasi Kerja per Nama Pegawai
Call From	: dmutS1
*****************************************/
DROP FUNCTION R_DMUTP1(l_Tahun      DECIMAL(4),
                        l_NIPFr     VARCHAR(10),
                        l_NIPTo     VARCHAR(10),
                        l_KPAFr     VARCHAR(11),
                        l_KPATo     VARCHAR(11),
						l_Mypass	VARCHAR(128),
						l_UserId	INT);
--
CREATE FUNCTION R_DMUTP1 (l_Tahun	DECIMAL(4),
                        l_NIPFr     VARCHAR(10),
                        l_NIPTo     VARCHAR(10),
                        l_KPAFr     VARCHAR(11),
                        l_KPATo     VARCHAR(11),
						l_Mypass	VARCHAR(128),
						l_UserId	INT)
--
RETURNS TABLE (OUTKdUker    VARCHAR(8),
		OUTNIP              VARCHAR(10),
		OUTKetUker          VARCHAR(40),
		OUTNama             VARCHAR(25),
		OUTStatus           VARCHAR(1),
		OUTSex              VARCHAR(1),
		OUTNoKPA            VARCHAR(15),
		OUTNilaiTotal       DECIMAL(15,2),
		OUTNilai01          DECIMAL(15,2),
		OUTNilai02          DECIMAL(15,2),
		OUTNilai03          DECIMAL(15,2),
		OUTNilai04          DECIMAL(15,2),
		OUTNilai05          DECIMAL(15,2),
		OUTNilai06          DECIMAL(15,2),
		OUTNilai07          DECIMAL(15,2),
		OUTNilai08          DECIMAL(15,2),
		OUTNilai09          DECIMAL(15,2),
		OUTNilai10          DECIMAL(15,2),
		OUTNilai11          DECIMAL(15,2),
		OUTNilai12          DECIMAL(15,2))
--
AS $$
--
BEGIN 
    RETURN QUERY
    SELECT 	Q1.KdUKer, Q1.NIP, Q1.KetUker, Q1.Nama, Q1.Status, Q1.Sex, Q1.NoKPA, 
            SUM(Q1.Nilai) AS Nilai,
            SUM(CASE WHEN Q1.BULAN = 1 THEN Q1.Nilai ELSE 0 END)  AS BLN01, 
            SUM(CASE WHEN Q1.BULAN = 2 THEN Q1.Nilai ELSE 0 END)  AS BLN02, 
            SUM(CASE WHEN Q1.BULAN = 3 THEN Q1.Nilai ELSE 0 END)  AS BLN03, 
            SUM(CASE WHEN Q1.BULAN = 4 THEN Q1.Nilai ELSE 0 END)  AS BLN04, 
            SUM(CASE WHEN Q1.BULAN = 5 THEN Q1.Nilai ELSE 0 END)  AS BLN05, 
            SUM(CASE WHEN Q1.BULAN = 6 THEN Q1.Nilai ELSE 0 END)  AS BLN06, 
            SUM(CASE WHEN Q1.BULAN = 7 THEN Q1.Nilai ELSE 0 END)  AS BLN07, 
            SUM(CASE WHEN Q1.BULAN = 8 THEN Q1.Nilai ELSE 0 END)  AS BLN08, 
            SUM(CASE WHEN Q1.BULAN = 9 THEN Q1.Nilai ELSE 0 END)  AS BLN09, 
            SUM(CASE WHEN Q1.BULAN = 10 THEN Q1.Nilai ELSE 0 END) AS BLN10, 
            SUM(CASE WHEN Q1.BULAN = 11 THEN Q1.Nilai ELSE 0 END) AS BLN11, 
            SUM(CASE WHEN Q1.BULAN = 12 THEN Q1.Nilai ELSE 0 END) AS BLN12 
    FROM
    (--Q1
    SELECT 	S01.KdUKer, M17.Keterangan AS KetUker, S02.NIP, S01.Nama, ' ' :: VARCHAR(1) AS Status, 
            S01.JnsKlmn :: VARCHAR(1) AS Sex, S01.KPA AS NoKPA, EXTRACT(MONTH FROM S02.TglPayr) :: DECIMAL(2) AS BULAN, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM M19HSLG M19
    INNER JOIN M20DSLG M20 ON M19.TipeLap=M20.TipeLap AND M19.FlgDpPt=M20.FlgDpPt AND M19.NomFormat=M20.NomFormat	
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt=M20.FlgDpPt AND M03.KdDpPt=M20.KdDpPt
    INNER JOIN S02DGAJ S02 ON S02.FlgDpPt=M19.FlgDpPt AND S02.KdDpPt=M20.KdDpPt AND S02.FlgAngs=M20.FlgAngs
    INNER JOIN S01HGAJ S01 ON S01.TglPayr=S02.TglPayr AND S01.NIP=S02.NIP 
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.kdUker
    INNER JOIN M15PEGA M15 ON S01.NIP = M15.NIP 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE M19.TipeLap='1' AND M03.Status='1' AND M19.FlgDpPt<>'P' AND S01.KPA<>' ' AND 
          EXTRACT(YEAR FROM S01.TglPayr)=l_Tahun AND
          (S01.KPA BETWEEN l_KPAFr AND l_KPATo)	AND (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          TO_CHAR(S01.TglPayr,'YYYYMM')>=TO_CHAR(M15.TglKPA,'YYYYMM')
    ) Q1 
    GROUP BY Q1.KdUKer, Q1.NIP, Q1.KetUker, Q1.Nama, Q1.Status, Q1.Sex, Q1.NoKPA ;
    --
END;
--
$$ LANGUAGE plpgsql ;


/* 

select * from R_DMUTP1(2013,'','ZZZ','','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)

*/


