/**********************************************************************
Name sprocs	: R_KAUCP1
Create by	: YUDI
Date		: 04-09-2002
Description	: Proses Laporan Kartu Slip per Unit Usaha dan Cabang
***********************************************************************/
DROP FUNCTION R_KAUCP1(l_Tahun    VARCHAR(4),
                    l_NIPFr     VARCHAR(10),
                    l_NIPTo     VARCHAR(10),
                    l_UUsaFr	VARCHAR(4),
                    l_UUsaTo	VARCHAR(4),
                    l_FCab      VARCHAR(4),
                    l_TCab      VARCHAR(4),
                    l_Mypass	VARCHAR(128),
                    l_UserId	INT);
--
CREATE FUNCTION R_KAUCP1(l_Tahun    VARCHAR(4),
                    l_NIPFr     VARCHAR(10),
                    l_NIPTo     VARCHAR(10),
                    l_UUsaFr	VARCHAR(4),
                    l_UUsaTo	VARCHAR(4),
                    l_FCab      VARCHAR(4),
                    l_TCab      VARCHAR(4),
                    l_Mypass	VARCHAR(128),
                    l_UserId	INT)
--
RETURNS TABLE (
        Tahun		VARCHAR(4),
        NIP			VARCHAR(10), 
        KdUUsa		VARCHAR(4) ,
        SkUUsa		VARCHAR(10) ,	
        KetUUsa		VARCHAR(20) ,
        KdCaba		VARCHAR(8) ,
        SkCaba		VARCHAR(10) ,
        FlgDpPt		VARCHAR(1) ,
        No_Format	DECIMAL(3,0),
        Keterangan	VARCHAR(30) ,	     	
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
    SELECT  l_Tahun AS Tahun, Q1.NIP, Q1.kdUUsa, Q1.SkUUsa, Q1.KetUUsa, Q1.KdCaba, Q1.SkCaba, 
            Q1.FlgDpPt, Q1.NomFormat, Q1.Keterangan,
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
    SELECT  S01.NIP, S01.kdUUsa, M02.Singkatan AS SkUUsa, M02.Keterangan AS KetUUsa, 
            S01.KdCaba, M08.SkCaba, S02.FlgDpPt, M19.NomFormat, M19.Keterangan, 
            TO_CHAR(S02.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM M19HSLG M19
    INNER JOIN M20DSLG M20 ON M19.TipeLap=M20.TipeLap AND M19.FlgDpPt=M20.FlgDpPt AND M19.NomFormat=M20.NomFormat
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt=M20.FlgDpPt AND M03.KdDpPt=M20.KdDpPt
    INNER JOIN S02DGAJ S02 ON S02.FlgDpPt=M19.FlgDpPt AND S02.KdDpPt=M20.KdDpPt AND S02.FlgAngs=M20.FlgAngs
    INNER JOIN S01HGAJ S01 ON S02.TglPayr=S01.TglPayr AND S02.NIP=S01.NIP
    INNER JOIN M02UUSA M02 ON M02.Kode=S01.kdUUsa
    INNER JOIN M08HCAB M08 ON S01.KdCaba=M08.KdCaba
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE   M19.TipeLap='3' AND TO_CHAR(S01.TglPayr,'YYYY')=l_Tahun AND
            S01.KdCaba BETWEEN l_FCab AND l_TCab AND
            S01.NIP BETWEEN l_NIPFr AND l_NIPTo AND
            S01.kdUUsa BETWEEN l_UUsaFr AND l_UUsaTo
    ) Q1      
    GROUP BY l_Tahun, Q1.NIP, Q1.kdUUsa, Q1.SkUUsa, Q1.KetUUsa, Q1.KdCaba, Q1.SkCaba, 
             Q1.FlgDpPt, Q1.NomFormat, Q1.Keterangan ;
--      
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_KAUCP1(l_Tahun    VARCHAR(4),
                    l_NIPFr     VARCHAR(10),
                    l_NIPTo     VARCHAR(10),
                    l_UUsaFr	VARCHAR(4),
                    l_UUsaTo	VARCHAR(4),
                    l_FCab      VARCHAR(4),
                    l_TCab      VARCHAR(4),
                    l_Mypass	VARCHAR(128),
                    l_UserId	INT)

select * from R_KAUCP1('2013',' ','ZZZ',' ','ZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)
*/

