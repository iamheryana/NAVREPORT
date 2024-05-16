/****************************************
Name sprocs	: R_PPENP1
Create by	: Deni 
Date		: 04-12-2013
Description	: Laporan Pegawai - Persiapan Pensiun
CATATAN KHUSUS : RANGE SECURITY DIKIRIM TAPI NGGAK DIPAKE (SESUAI SDR) 
*****************************************/
DROP FUNCTION R_PPENP1 (l_tahun  VARCHAR(4),  
                        l_Usia   INT, 
                        l_UserId INT);
--
CREATE FUNCTION R_PPENP1 (
                      l_tahun   VARCHAR(4),
					  l_Usia    INT, 
                      l_UserId  INT)
--
RETURNS TABLE ( OUTNIP          VARCHAR(10), 
                OUTNAMA         VARCHAR(25),
                OUTJnsKelamin   VARCHAR(80),
                OUTTgllahir     DATE,
                OUTTglmasuk     DATE,
                OUTUmur         INT,
                OUTMasaKerja    INT,
                OUTMMLahir      VARCHAR(2),
                OUTDDLahir      VARCHAR(2),
                OUTKdcaba       VARCHAR(4),
                OUTKetcaba      VARCHAR(40),
                OUTKdgolg       VARCHAR(4),
                OUTTglkeluar    DATE,
                OUTUnitUsaha    VARCHAR(20),
                OUTUnitKerja    VARCHAR(20),
                OUTJabatan      VARCHAR(20))
AS $$
--
DECLARE l_periode DATE;
--
BEGIN 
    --
	l_periode := to_date(l_tahun||'12-31','YYYY-MM-DD') ;
	RETURN QUERY 
    --
    SELECT  Q1.NIP, Q1.Nama, Q1.JnsKlmn, Q1.TglLahir, Q1.TglMasuk, Q1.umur, Q1.masakerja, Q1.MMLahir, Q1.DDLahir,
            Q1.KdCaba, Q1.SkCaba, Q1.KdGlng, Q1.TglKeluar, Q1.unitusaha, Q1.unitkerja, Q1.jabatan
    FROM        
    (
    SELECT  M15.NIP, M15.Nama, M15.JnsKlmn, M15.TglLahir, M15.TglMasuk, 
            extract(year from age(current_date,M15.TglLahir)) :: INT as umur,
            extract(year from age(current_date,M15.TglMasuk)) :: INT as masakerja,
            to_char(M15.TglLahir, 'MM') :: VARCHAR AS MMLahir,
            to_char(M15.TglLahir, 'DD') :: VARCHAR AS DDLahir,
            M15.KdCaba, M08.SkCaba, M15.KdGlng, M15.TglKeluar,
            M02.Singkatan as unitusaha,
            M17.Singkatan as unitkerja,
            M04.Keterangan as jabatan
    FROM M15PEGA M15
    INNER JOIN M08HCAB M08 ON M15.KdCaba = M08.KdCaba
    INNER JOIN M02UUSA M02 ON M15.KdUUsa = M02.Kode
    INNER JOIN M17UKER M17 ON M15.KdUKer = M17.KdUker
    INNER JOIN M04HJAB M04 ON M15.KdJaba = M04.Kode
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    ) Q1
    WHERE (Q1.TglKeluar IS NULL OR Q1.TglKeluar > l_periode) AND Q1.masakerja >= l_Usia ;
    --   
END;
$$ LANGUAGE plpgsql ;

/* 
select * from R_PPENP1('2013', 23, 1)
*/

