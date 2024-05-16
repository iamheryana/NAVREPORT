/****************************************
Name sprocs	: R_LEMKP1
Create by	: Deni
Date		: 02-04-2014
Description	: Lembur per Unit Kerja
Call From	: Main Sprocs
*****************************************/
DROP FUNCTION R_LEMKP1 ( l_Tahun    VARCHAR(4),
						 l_UkerFr   VARCHAR(4),
						 l_UkerTo   VARCHAR(4),
						 l_NIPFr    VARCHAR(4),
						 l_NIPTo    VARCHAR(4),
                         l_UserId    INT);
--                            
CREATE FUNCTION R_LEMKP1 ( l_Tahun  VARCHAR(4),
						 l_UkerFr   VARCHAR(4),
						 l_UkerTo   VARCHAR(4),
						 l_NIPFr    VARCHAR(4),
						 l_NIPTo    VARCHAR(4),
                         l_UserId    INT)
--
RETURNS TABLE ( 
        uker         varchar(4), 
        nmuker       varchar(40), 
        nip          varchar(10), 
        nama         varchar(25), 
        jam1         numeric(5,2), 
        jam2         numeric(5,2), 
        jam3         numeric(5,2), 
        jam4         numeric(5,2), 
        jam5         numeric(5,2), 
        jam6         numeric(5,2), 
        jam7         numeric(5,2), 
        jam8         numeric(5,2), 
        jam9         numeric(5,2), 
        jam10        numeric(5,2), 
        jam11        numeric(5,2), 
        jam12        numeric(5,2), 
        totaljam     numeric(5,2), 
        nilai1       numeric(15,2), 
        nilai2       numeric(15,2), 
        nilai3       numeric(15,2), 
        nilai4       numeric(15,2), 
        nilai5       numeric(15,2), 
        nilai6       numeric(15,2), 
        nilai7       numeric(15,2), 
        nilai8       numeric(15,2), 
        nilai9       numeric(15,2), 
        nilai10      numeric(15,2), 
        nilai11      numeric(15,2), 
        nilai12      numeric(15,2),
        totalnilai   numeric(15,2))
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT Q1.KdUker, Q1.Keterangan, Q1.NIP, Q1.Nama, 
           SUM(CASE WHEN Q1.Bulan = '01' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam01, 
           SUM(CASE WHEN Q1.Bulan = '02' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam02, 
           SUM(CASE WHEN Q1.Bulan = '03' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam03, 
           SUM(CASE WHEN Q1.Bulan = '04' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam04, 
           SUM(CASE WHEN Q1.Bulan = '05' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam05, 
           SUM(CASE WHEN Q1.Bulan = '06' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam06, 
           SUM(CASE WHEN Q1.Bulan = '07' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam07, 
           SUM(CASE WHEN Q1.Bulan = '08' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam08, 
           SUM(CASE WHEN Q1.Bulan = '09' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam09, 
           SUM(CASE WHEN Q1.Bulan = '10' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam10, 
           SUM(CASE WHEN Q1.Bulan = '11' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam11, 
           SUM(CASE WHEN Q1.Bulan = '12' THEN (Q1.TtlLemb) ELSE 0 END) AS Jam12, 
           SUM(Q1.TtlLemb) AS TotalJam, 
           SUM(CASE WHEN Q1.Bulan = '01' THEN (Q1.Nilai) ELSE 0 END) AS Nilai01, 
           SUM(CASE WHEN Q1.Bulan = '02' THEN (Q1.Nilai) ELSE 0 END) AS Nilai02, 
           SUM(CASE WHEN Q1.Bulan = '03' THEN (Q1.Nilai) ELSE 0 END) AS Nilai03, 
           SUM(CASE WHEN Q1.Bulan = '04' THEN (Q1.Nilai) ELSE 0 END) AS Nilai04, 
           SUM(CASE WHEN Q1.Bulan = '05' THEN (Q1.Nilai) ELSE 0 END) AS Nilai05, 
           SUM(CASE WHEN Q1.Bulan = '06' THEN (Q1.Nilai) ELSE 0 END) AS Nilai06, 
           SUM(CASE WHEN Q1.Bulan = '07' THEN (Q1.Nilai) ELSE 0 END) AS Nilai07, 
           SUM(CASE WHEN Q1.Bulan = '08' THEN (Q1.Nilai) ELSE 0 END) AS Nilai08, 
           SUM(CASE WHEN Q1.Bulan = '09' THEN (Q1.Nilai) ELSE 0 END) AS Nilai09, 
           SUM(CASE WHEN Q1.Bulan = '10' THEN (Q1.Nilai) ELSE 0 END) AS Nilai10, 
           SUM(CASE WHEN Q1.Bulan = '11' THEN (Q1.Nilai) ELSE 0 END) AS Nilai11, 
           SUM(CASE WHEN Q1.Bulan = '12' THEN (Q1.Nilai) ELSE 0 END) AS Nilai12,
           SUM(Q1.Nilai) AS TotalNilai
    FROM
    (--Q1
    SELECT  S04.KdUker, M17.Keterangan, S04.NIP, M15.Nama, S04.TtlLemb, S04.Nilai,  
            TO_CHAR(S04.TglPayr,'MM') :: VARCHAR(2) AS Bulan
    FROM  S04LEMB S04 
    INNER JOIN M17UKER M17 ON S04.KdUker=M17.KdUker
    INNER JOIN M15PEGA M15 ON M15.NIP=S04.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND S04.KdCaba=VSL.Cab_Kode
    WHERE TO_CHAR(S04.TglPayr,'YYYY')=l_Tahun AND (S04.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          (S04.KdUker BETWEEN l_UkerFr AND l_UkerTo)
    ) Q1      
    GROUP BY Q1.KdUker, Q1.Keterangan, Q1.NIP, Q1.Nama ;
    --
END;
$$ LANGUAGE plpgsql ;
--
/*
SELECT * FROM R_LEMKP1('2012','','ZZZZ','','ZZZZ',1) 
*/
