
CREATE OR REPLACE FUNCTION public.fn_gajipokok (in l_fz1flgggol varchar, in l_tglproses date, in l_m15prdtetap date, in l_m15gajiperc numeric, in l_m15gajitetap numeric, in l_m15nip varchar, in l_m15tglmasuk date, in l_mnhari numeric, in l_m15kdglng varchar) RETURNS numeric AS
$BODY$ 

DECLARE l_Gaji		DECIMAL(15,2) ;
	l_GajiPokok	DECIMAL(15,2) ;
	l_T18HariKerja 	VARCHAR(1) ;
	l_T18JmlHari	DECIMAL(4,0) ;
	l_MasaKerja	DECIMAL(5,0) ;
	l_M15TglKeluar	DATE ; 

BEGIN
  -- Gaji Pokok
  IF l_FZ1FlgGGol='Y' THEN 
     BEGIN
        -- Create By suhe 25-09-2003 
        l_Gaji := 0 ;
        -- Hitung Masa Kerja hasil pagi
        l_MasaKerja := FLOOR(age( l_TglProses, l_M15TglMasuk )/12) ; 
        --Cari GajiPokok dari M.Gaji Dasar Golongan
        SELECT GajiPokok
        INTO l_Gaji
        FROM M11GGOL
        WHERE Kode=l_M15KdGlng AND FrTempo<=l_MasaKerja ; 
     END;  
     -- Gaji Golongan (Pending)     
  ELSIF l_TglProses<l_M15PrdTetap THEN 
     l_Gaji := l_M15GajiPerc ;
  ELSIF l_TglProses>=l_M15PrdTetap THEN 
     l_Gaji := l_M15GajiTetap ;
  END IF; 
  --*
  -- Check Table Hari Kerja
-- --   IF EXISTS(SELECT NIP FROM T18HKER WHERE NIP=l_M15NIP AND InOut=''M'')
-- --      SELECT l_T18HariKerja=''Y''
-- --   ELSE
-- --      SELECT l_T18HariKerja=''T''
  --*
  -- Ambil tglKeluar
  SELECT COALESCE(TglKeluar,'2099-12-31') 
  INTO l_M15TglKeluar
  FROM M15PEGA
  WHERE NIP=l_M15NIP ;
  ---Jika Pegawai Keluar maka gajinya proporsional
-- --   IF CONVERT(VARCHAR(6),l_TglProses,112)=CONVERT(VARCHAR(6),l_M15TglKeluar,112)
-- --      BEGIN
-- --        -- Check Table Hari Kerja
-- --        IF EXISTS(SELECT NIP FROM T18HKER WHERE NIP=l_M15NIP AND InOut=''K'')
-- --           SELECT l_T18HariKerja=''Y''
-- --        ELSE
-- --          SELECT l_T18HariKerja=''T''
-- --        --*        
-- --      END
  --*  
  -- INIT 
  
   l_GajiPokok := 0 ;
   
  -- Gaji Proporsional untuk Pegawai Baru
-- --   IF (DATEDIFF(DAY,l_M15TglMasuk,l_TglProses) < 28) AND l_T18HariKerja=''Y''
  IF date_trunc('month',l_TglProses)=date_trunc('month',l_M15TglMasuk) AND 
	 EXISTS(SELECT NIP FROM T18HKER WHERE NIP = l_M15NIP AND InOut ='M') THEN 
     BEGIN
       SELECT HKerja
       INTO l_T18JmlHari
       FROM T18HKER      
       WHERE NIP=l_M15NIP AND InOut='M' ;
       -- Gaji Pokok 
       l_GajiPokok := (l_Gaji*l_T18JmlHari)/l_mnHari ; 
     END ;
  END IF ;
   
  -- Gaji Proporsional untuk Pegawai keluar
-- --   ELSE IF CONVERT(VARCHAR(6),l_TglProses,112)=CONVERT(VARCHAR(6),l_M15TglKeluar,112) AND l_T18HariKerja=''Y''
  IF date_trunc('month',l_TglProses)=date_trunc('month',l_M15TglKeluar) AND 
	 EXISTS(SELECT NIP FROM T18HKER WHERE NIP = l_M15NIP AND InOut ='K') THEN 
     BEGIN
       SELECT HKerja
       INTO l_T18JmlHari
       FROM T18HKER      
       WHERE NIP=l_M15NIP AND InOut='K' ; 
       -- Gaji Pokok 
-- --        SELECT l_GajiPokok=l_Gaji*(l_T18JmlHari/l_mnHari)  
       l_GajiPokok := COALESCE(l_GajiPokok,0)+((l_Gaji*l_T18JmlHari)/l_mnHari)  ; 
     END ;
  END IF; 
  
-- --   ELSE
-- orang baru gak ada di parm hari kerja 
  IF (date_trunc('month',l_TglProses)=date_trunc('month',l_M15TglMasuk) AND 
	 NOT EXISTS(SELECT NIP FROM T18HKER WHERE NIP = l_M15NIP)) OR 
-- orang keluar gak ada di parm hari kerja 
     (date_trunc('month',l_TglProses)=date_trunc('month',l_M15TglKeluar) AND 
	 NOT EXISTS(SELECT NIP FROM T18HKER WHERE NIP = l_M15NIP)) OR 
-- bukan orang baru 
	 (date_trunc('month',l_TglProses) <> date_trunc('month',l_M15TglMasuk) AND   
-- bukan orang keluar 
     (date_trunc('month',l_TglProses) <> date_trunc('month',l_M15TglKeluar)))  THEN 
      -- Gaji Pokok
     BEGIN
-- --        SELECT l_GajiPokok=l_Gaji  
       l_GajiPokok := l_GajiPokok + l_Gaji  ; 
      END ;
  ELSE 
	BEGIN 
          l_GajiPokok := l_GajiPokok + l_Gaji ;
	END ; 
  END IF ; 	
  -- Mengembalikan Nilai Gaji Pokok
  RETURN l_GajiPokok ;
END ;
$BODY$
LANGUAGE 'plpgsql'
GO
