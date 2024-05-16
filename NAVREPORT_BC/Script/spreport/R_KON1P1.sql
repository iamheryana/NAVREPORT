/**************************
	Laporan Akhir Masa Kontrak 
	Created by 		: Peggy
	Created Date	: 22 04 2008 
***************************/
DROP FUNCTION  R_KON1P1	(l_mdTanggalFrom DATE, l_mdTanggalTo DATE, l_mnKontrak Int, l_UserId INT);
--
CREATE FUNCTION  R_KON1P1	(
                         l_mdTanggalFrom DATE, 
						 l_mdTanggalTo 	DATE, 
						 l_mnKontrak 	Int, 
                         l_UserId       INT)
--
RETURNS TABLE ( OUTNIP              VARCHAR(10), 
                OUTNAMA             VARCHAR(25),
                OUTKdArea           VARCHAR(4),  
                OUTSkArea           VARCHAR(10),
                OUTKdCaba           VARCHAR(4), 
                OUTSkCaba           VARCHAR(10),
                OUTKdUUsa           VARCHAR(4), 
                OUTSkUUsa           VARCHAR(10),
                OUTKdUKer           VARCHAR(8), 
                OUTSkUKer           VARCHAR(10),
                OUTKdGlng           VARCHAR(4), 
                OUTSkGlng           VARCHAR(10),
                OUTKdKJab           VARCHAR(4), 
                OUTSkKJab           VARCHAR(10),
                OUTKdJaba           VARCHAR(4), 
                OUTSkJaba           VARCHAR(10),
                OUTTglMasuk         DATE, 
                OUTTtlKontrak       INT,
                OUTTglKontrakAkhir  DATE) 		
AS $$ 
--
BEGIN 
    --
    RETURN QUERY 
    SELECT  M15.NIP, M15.NAMA, M15.KdArea, M01.Singkatan AS SkArea,
            M15.KdCaba, M08.SkCaba, M15.KdUUsa, M02.Singkatan AS SkUUsa,
            M15.KdUKer, M17.Singkatan AS SkUKer,
            M15.KdGlng, M12.Singkatan AS SkGlng,
            M15.KdKJab, M06.Singkatan AS SkKJab,
            M15.KdJaba, M04.Singkatan AS SkJaba,
            M15.TglMasuk, M15.TtlKontrak, M15.TglKontrakAkhir
    FROM M15PEGA M15
    INNER JOIN M01AREA M01 ON M15.KdArea=M01.Kode
    INNER JOIN M02UUSA M02 ON M15.KdUUSa = M02.Kode
    INNER JOIN M04HJAB M04 ON M15.KDJaba = M04.Kode 
    INNER JOIN M06HKJB M06 ON M15.KDKJab = M06.Kode 
    INNER JOIN M08HCAB M08 ON M15.KDCaba = M08.KdCaba 
    INNER JOIN M12HGOL M12 ON M15.KDGLNG = M12.Kode 
    INNER JOIN M17UKER M17 ON M15.KdUker = M17.KdUKer 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE M15.TglKontrakAkhir BETWEEN l_mdTanggalFrom AND l_mdTanggalTo AND
          ((l_mnKontrak = 1 and M15.TtlKontrak = 1) OR 
           (l_mnKontrak = 2 and M15.TtlKontrak = 2) OR 
           (l_mnKontrak = 3)) ; 
    --
END; 
$$ LANGUAGE plpgsql ;
/*
SELECT * FROM  R_KON1P1('2013-01-01' ::DATE, '2013-12-31'::DATE, 3, 1)
*/
