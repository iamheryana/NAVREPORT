
CREATE OR REPLACE FUNCTION public.fn_getprop (in l_nip varchar, in l_mnhari numeric, in l_tglproses date) RETURNS numeric AS
$BODY$ 
DECLARE l_jmlkerja	INT;
	l_t18harikerja 	VARCHAR(1);
	l_m15tglkeluar 	DATE;
	l_m15tglmasuk 	DATE;
	l_T18JmlHari 	DECIMAL(4,0);
	l_prdTglProses 	VARCHAR(6);
	l_prdTglMasuk 	VARCHAR(6);
	l_prdTglKeluar 	VARCHAR(6);

BEGIN

-- cari dulu jml hari untuk pegawai baru atau keluar karena nantinya dihitung proporsional 
-- peggy 2006 06 12 
  -- Check Table Hari Kerja
  IF EXISTS(SELECT NIP FROM T18HKER WHERE NIP=l_NIP AND InOut='M') THEN 
     l_T18HariKerja :='Y';
  ELSE
     l_T18HariKerja :='T';
  END IF;    
  --*

  -- Ambil tglKeluar
  SELECT TglKeluar, TGLMASUK 
  INTO l_M15TglKeluar, l_M15TGLMASUK
  FROM M15PEGA
  WHERE NIP=l_NIP ; 

  l_prdTglProses := EXTRACT(YEAR FROM  l_TglProses) :: VARCHAR(4) || RIGHT('00' || EXTRACT(MONTH FROM l_TglProses):: VARCHAR(2), 2); 
  l_prdTglMasuk  := EXTRACT(YEAR FROM  l_M15TGLMASUK) :: VARCHAR(4) || RIGHT('00' || EXTRACT(MONTH FROM l_M15TGLMASUK):: VARCHAR(2), 2); 
  l_prdTglKeluar := EXTRACT(YEAR FROM  l_M15TglKeluar) :: VARCHAR(4) || RIGHT('00' || EXTRACT(MONTH FROM l_M15TglKeluar):: VARCHAR(2), 2); 
    
  ---Jika Pegawai Keluar maka gajinya proporsional
  IF l_M15TGLKELUAR IS NOT NULL AND l_prdTglProses=l_prdTglKeluar THEN 
     BEGIN
       -- Check Table Hari Kerja
       IF EXISTS(SELECT NIP FROM T18HKER WHERE NIP=l_NIP AND InOut='K') THEN 
          l_T18HariKerja := 'Y' ; 
       ELSE
          l_T18HariKerja := 'T' ; 
       END IF;   
       --*        
     END ; 
  END IF; 
  --*  
  
  -- Gaji Proporsional untuk Pegawai Baru
  IF (l_TglProses - l_M15TglMasuk) < 28 AND l_T18HariKerja='Y' THEN  
     BEGIN
       SELECT HKerja
       FROM T18HKER      
       INTO l_T18JmlHari
       WHERE NIP=l_NIP AND InOut='M';     
     END ;
   -- Gaji Proporsional untuk Pegawai keluar
  ELSIF l_M15TGLKELUAR IS NOT NULL AND l_prdTglProses=l_prdTglKeluar AND l_T18HariKerja='Y' THEN
     BEGIN
       SELECT HKerja
       FROM T18HKER      
       INTO l_T18JmlHari    
       WHERE NIP=l_NIP AND InOut='K' ;
     END;
  ELSE 
     BEGIN
       l_T18JmlHari := l_MNHARI ;
     END;
--SELECT  l_T18JmlHari= DATEDIFF(DAY,l_M15TglMasuk,l_TglProses)     
  END IF ; 

   RETURN l_t18jmlhari ;
END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
