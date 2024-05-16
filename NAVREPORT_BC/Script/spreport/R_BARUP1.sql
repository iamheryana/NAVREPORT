 /****************************************
Name sprocs	: R_BARUP1
Description	: DAFTAR PEGAWAI BARU (PAYROLL) 
Call From	: baruS2 
*****************************************/
DROP FUNCTION R_BARUP1(l_ProsesFr DATE, l_ProsesTo DATE, l_UserId INT);	
--
CREATE FUNCTION R_BARUP1(l_ProsesFr	DATE,
                        l_ProsesTo	DATE,	
                        l_UserId    INT)
--
RETURNS TABLE ( OUTNIP          VARCHAR(10), 
                OUTNAMA         VARCHAR(25),
                OUTJnsKelamin   VARCHAR(1),
                OUTTglmasuk     DATE,
                OUTTingkatan    VARCHAR(10),
                OUTUmur         INT,
                OUTTgllahir     DATE,
                OUTKPA          VARCHAR(11),
                OUTKdcaba       VARCHAR(4),
                OUTKetcaba      VARCHAR(20),
                OUTKeljabatan   VARCHAR(20),
                OUTJabatan      VARCHAR(20),
                OUTKdGolg       VARCHAR(4),
                OUTKetGolg      VARCHAR(20),
                OUTUnitUsaha    VARCHAR(20),
                OUTKlas         VARCHAR(4),
                OUTKetklas      VARCHAR(20),
                OUTUnitkerja    VARCHAR(20))
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT M15.NIP, M15.Nama, M15.JnsKlmn, M15.TglMasuk, 
          (CASE WHEN M15.Tingkatan = '1' THEN 'SD' 
                WHEN M15.Tingkatan = '2' THEN 'SLTP' 
                WHEN M15.Tingkatan = '3' THEN 'SLTA' 
                WHEN M15.Tingkatan = '4' THEN 'AKADEMI' 
                WHEN M15.Tingkatan = '5' THEN 'STRATA-1' 
                WHEN M15.Tingkatan = '6' THEN 'STRATA-2' 
                WHEN M15.Tingkatan = '7' THEN 'STRATA-2' 
                WHEN M15.Tingkatan = '0' THEN 'LAINNYA' 
                ELSE ' ' END)  :: VARCHAR AS Tingkatan,
           extract(year from age(current_date,M15.TglLahir)) :: INT as umur,
           M15.TglLahir, M15.KPA, M15.KdCaba,M08.SkCaba, M06.Singkatan AS Keljabatan, 
           M04.Singkatan AS Jabatan, M15.KdGlng, M12.Singkatan AS Golongan, M02.Singkatan AS UnitUsaha,
           M10.Kode AS Klas, M10.Singkatan AS Ketklas, M17.Singkatan AS Unitkerja
    FROM M15PEGA M15
    INNER JOIN M08HCAB M08 ON M15.KdCaba = M08.KdCaba
    INNER JOIN M06HKJB M06 ON M15.KdKJab = M06.Kode
    INNER JOIN M12HGOL M12 ON M15.KdGlng = M12.Kode
    INNER JOIN M04HJAB M04 ON M15.KdJaba = M04.Kode
    INNER JOIN M02UUSA M02 ON M15.KdUUsa = M02.Kode
    INNER JOIN M10KLAS M10 ON M15.KdKlas = M10.Kode
    INNER JOIN M17UKER M17 ON M15.KdUKer = M17.KdUker
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.TglMasuk BETWEEN l_ProsesFr AND l_ProsesTo);
    --   
END;
$$ LANGUAGE plpgsql ;

/*
select * from R_BARUP1('2000-01-01' :: DATE, '2012-12-31' :: DATE, 1)
*/
