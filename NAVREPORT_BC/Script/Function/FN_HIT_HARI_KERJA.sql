
CREATE OR REPLACE FUNCTION public.fn_hit_hari_kerja (in l_tglproses date, in l_m15prdtetap date, in l_m15nip varchar, in l_m15tglmasuk date, in l_mnhari numeric) RETURNS numeric AS
$BODY$
DECLARE
	l_T18HariKerja 	VARCHAR(1);
	l_T18JmlHari	DECIMAL(4,0);
	l_M15TglKeluar	DATE;	
	l_tanggal_prs	INT;
	l_bulan_prs		INT;
	l_tahun_prs		INT;
	l_tanggal_msk	INT;
	l_bulan_msk		INT;
	l_tahun_msk		INT;
	l_tanggal_klr	INT;
	l_bulan_klr		INT;
	l_tahun_klr		INT;
	l_NIP			VARCHAR(10);
	
BEGIN

	
	l_tanggal_prs 	:= extract(day from l_TglProses);
	l_bulan_prs 	:= extract(month from l_TglProses);
	l_tahun_prs 	:= extract(year from l_TglProses);
	
	l_tanggal_msk 	:= extract(day from l_M15TglMasuk);
	l_bulan_msk 	:= extract(month from l_M15TglMasuk);
	l_tahun_msk 	:= extract(year from l_M15TglMasuk);
	
	
	
	IF l_tanggal_prs=l_tanggal_msk and l_bulan_prs=l_bulan_msk and l_tahun_prs=l_tahun_msk THEN
		SELECT NIP INTO l_NIP FROM T18HKER WHERE NIP = l_M15NIP AND InOut ='M';
		IF l_NIP IS NOT NULL THEN
			SELECT HKerja INTO l_T18JmlHari FROM T18HKER WHERE NIP=l_M15NIP AND InOut='M';
		END IF;
	END IF;

	l_tanggal_klr 	:= extract(day from l_M15TglKeluar);
	l_bulan_klr 	:= extract(month from l_M15TglKeluar);
	l_tahun_klr 	:= extract(year from l_M15TglKeluar);
	
	IF l_tanggal_prs=l_tanggal_klr and l_bulan_prs=l_bulan_klr and l_tahun_prs=l_tahun_klr THEN
		SELECT NIP INTO l_NIP FROM T18HKER WHERE NIP = l_M15NIP AND InOut ='K';
		IF l_NIP IS NOT NULL THEN
			SELECT HKerja INTO l_T18JmlHari FROM T18HKER WHERE NIP=l_M15NIP AND InOut='M';
			SELECT COALESCE(l_T18JmlHari,0) + HKerja INTO l_T18JmlHari FROM T18HKER WHERE NIP=l_M15NIP AND InOut='K';
		END IF;
	END IF;
	

	-- ELSE
	-- orang baru gak ada di parm hari kerja 
	-- orang keluar gak ada di parm hari kerja 
	-- bukan orang baru
	-- bukan orang keluar
    -- Gaji Pokok
	SELECT NIP INTO l_NIP FROM T18HKER WHERE NIP = l_M15NIP;
	IF (l_tanggal_prs=l_tanggal_msk and l_bulan_prs=l_bulan_msk and l_tahun_prs=l_tahun_msk AND l_NIP IS NOT NULL)
	OR (l_tanggal_prs=l_tanggal_klr and l_bulan_prs=l_bulan_klr and l_tahun_prs=l_tahun_klr AND l_NIP IS NULL)
	OR ((l_tanggal_prs<>l_tanggal_msk and l_bulan_prs<>l_bulan_msk and l_tahun_prs<>l_tahun_msk) AND (l_tanggal_prs<>l_tanggal_klr and l_bulan_prs<>l_bulan_klr and l_tahun_prs<>l_tahun_klr))
	THEN
		l_T18JmlHari:=l_mnhari;
	END IF;
	
	-- Mengembalikan Jml Hari kerja 
	-- edit by syam 27-07-201, jika Null Ambil dari l_mnhari
	RETURN COALESCE(l_T18JmlHari,l_mnhari) ;
end;
$BODY$
LANGUAGE 'plpgsql'
GO
