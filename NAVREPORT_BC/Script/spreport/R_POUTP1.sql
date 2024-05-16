/**************************
	Laporan Pegawai Keluar 
	Created by 		: Peggy
	Created Date	: 16 12 2008 
***************************/
DROP FUNCTION R_POUTP1 (l_mdPeriodeFrom	DATE, 
                    l_mdPeriodeTo	DATE, 
				    l_mcAlasanFrom	VARCHAR(4), 
                    l_mcAlasanTo	VARCHAR(4),
                    l_UserId        INT4);
--
CREATE FUNCTION R_POUTP1 (l_mdPeriodeFrom 	DATE, 
				    l_mdPeriodeTo 	DATE, 
				    l_mcAlasanFrom	VARCHAR(4),
				    l_mcAlasanTo	VARCHAR(4),
                    l_UserId        INT4)
--
RETURNS TABLE ( OUTNIP          VARCHAR(10), 
                OUTNAMA         VARCHAR(25),
                OUTJnsklmn      VARCHAR(1),
                OUTTglKeluar    date,
                OUTM16keterangan VARCHAR(20),
                OUTTgllahir     date,
                OUTTglMasuk     date,
                OUTKpa          VARCHAR(11),
                OUTSkCaba       VARCHAR(10),
                OUTSkUUsa       VARCHAR(10),
                OUTSkUKer       VARCHAR(10),
                OUTSkGlng       VARCHAR(10),
                OUTSkKJab       VARCHAR(10),
                OUTSkJaba       VARCHAR(10),
                OUTKdKlas       VARCHAR(4),
                OUTSkKlas       VARCHAR(10),
                OUTUsia         INT,		
                OUTYOS          DECIMAL(7,2))		
AS $$
--
BEGIN
    RETURN QUERY 
    SELECT  M15.NIP, M15.NAMA, M15.JnsKlmn, M15.TglKeluar, M16.Keterangan, M15.TglLahir, M15.TglMasuk, 
            M15.KPA, M08.SkCaba, M02.Singkatan AS SkUUsa, M17.Singkatan AS SkUKer, M12.Singkatan AS SkGlng,
            M06.Singkatan AS SkKJab, M04.Singkatan AS SkJaba, M15.KdKlas, M10.Singkatan AS SkKlas, 
            extract(year from age(M15.TglKeluar,M15.TglLahir)) :: INT as umur,
            (M15.TglKeluar - M15.TglMasuk) :: decimal (7,2) / 365 :: decimal (7,2) as YOS 
    FROM M15PEGA M15
    INNER JOIN M16ALKL M16 ON M15.KdTerr=M16.Kode
    INNER JOIN M10KLAS M10 ON M15.KdKlas=M10.Kode
    INNER JOIN M02UUSA M02 ON M15.KdUUSa = M02.Kode    
    INNER JOIN M04HJAB M04 ON M15.KDJaba = M04.Kode 
    INNER JOIN M06HKJB M06 ON M15.KDKJab = M06.Kode 
    INNER JOIN M08HCAB M08 ON M15.KDCaba = M08.KdCaba 
    INNER JOIN M12HGOL M12 ON M15.KDGLNG = M12.Kode 
    INNER JOIN M17UKER M17 ON M15.KdUker = M17.KdUKer 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE M15.TglKeluar BETWEEN l_mdPeriodeFrom AND l_mdPeriodeTo AND
          M15.KdTerr BETWEEN l_mcAlasanFrom and l_mcAlasanTO ;
    --
END;
$$ LANGUAGE plpgsql ;

/*
SELECT * FROM  R_POUTP1('2013-01-01' ::DATE, '2013-11-30'::DATE, ' ', 'Z',1) 
*/

