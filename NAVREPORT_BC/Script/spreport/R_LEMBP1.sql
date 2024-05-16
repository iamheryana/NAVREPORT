/****************************************
Name sprocs	: P_LEMBP1
Create by	: Hermansyah
Date		: 06-09-2002
Description	: Lembur
Call From	: Main Sprocs
*****************************************/
--
DROP FUNCTION R_LEMBP1 (l_periode   date, 
						 l_cabFr    VARCHAR(4),
						 l_cabTo    VARCHAR(4),
						 l_hrkerja  INT,
                         l_UserId    INT);
--                            
CREATE FUNCTION R_LEMBP1(l_periode  date, 
						 l_cabFr    VARCHAR(4),
						 l_cabTo    VARCHAR(4),
						 l_hrkerja  INT,
                         l_UserId    INT)
--
RETURNS TABLE ( 
        outarea         varchar(4), 
        outnmarea       varchar(40), 
        outcabang       varchar(4), 
        outnmcabang     varchar(40), 
        outuusa         varchar(4), 
        outnmuusa       varchar(40), 
        outuker         varchar(4), 
        outnmuker       varchar(40), 
        outlembk1       numeric(5,2), 
        outlembk2       numeric(5,2), 
        outlembl1       numeric(5,2), 
        outlembl2       numeric(5,2), 
        outlembl3       numeric(5,2), 
        outttllemb      numeric(5,2), 
        outnilai        numeric(5,2), 
        outselisihlemb  numeric(5,2))
AS $$
--
DECLARE l_HariKerja DECIMAL(4,0);
--
BEGIN 
    SELECT HariKerja INTO l_HariKerja FROM FZ1FLDA ORDER BY kode LIMIT 1 ; 
    --
	RETURN QUERY 
    SELECT  S04.KdArea, M01.Keterangan AS KetArea, S04.KdCaba, M08.NmCaba, S04.KdUUsa, M02.Keterangan AS KetUUsa, 
            S04.KdUker, M17.Keterangan AS KetUker,
            SUM(S04.LembK1) AS LembK1 ,SUM(S04.LembK2) AS LembK2,
            SUM(S04.LembL1) AS LembL1, SUM(S04.LembL2) AS LembL2,
            SUM(S04.LembL3) AS LembL3, SUM(S04.TtlLemb) AS TtlLemb,
            SUM(S04.Nilai) AS Nilai, SUM(S04.TtlLemb)-l_HariKerja AS TtlLemb
    FROM  S04LEMB S04 
    INNER JOIN M01AREA M01 ON S04.KdArea=M01.Kode
    INNER JOIN M02UUSA M02 ON S04.KdUUsa=M02.Kode
    INNER JOIN M17UKER M17 ON S04.KdUker=M17.KdUker
    INNER JOIN M08HCAB M08 ON S04.Kdcaba=M08.KdCaba 
    INNER JOIN M15PEGA M15 ON M15.NIP=S04.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND S04.KdCaba=VSL.Cab_Kode
    WHERE TO_CHAR(S04.TglPayr,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM') AND (S04.KdCaba BETWEEN l_cabFr AND l_cabTo)
    GROUP BY S04.KdArea, M01.Keterangan, S04.KdCaba, M08.NmCaba, S04.KdUUsa, M02.Keterangan, 
             S04.KdUker, M17.Keterangan, l_HariKerja ;
    --
END;
$$ LANGUAGE plpgsql ;
--
/*
SELECT * FROM R_LEMBP1('2012-01-02','','ZZZZ',22,1) 

SELECT HariKerja  FROM FZ1FLDA ORDER BY kode LIMIT 1 ; 
*/
