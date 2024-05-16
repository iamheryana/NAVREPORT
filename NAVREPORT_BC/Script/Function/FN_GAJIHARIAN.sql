
CREATE OR REPLACE FUNCTION public.fn_gajiharian (in l_tglproses date, in l_s05tglakhir date, in l_m15nip varchar, in l_m15prdtetap date, in l_m15gajiperc numeric, in l_m15gajitetap numeric, in l_hkerja numeric, in l_fz1flgggol varchar, in l_m15tglmasuk date, in l_m15kdglng varchar) RETURNS numeric AS
$BODY$ 

DECLARE l_Gaji			DECIMAL(15,2) ;
DECLARE l_POTGAJI		DECIMAL(3,1)  ;  	 
DECLARE l_PLUSGAJI		DECIMAL(3,1)  ;  	 
DECLARE l_MasaKerja		DECIMAL(5,0)  ;

BEGIN

-- udah di p_payhp1.sql 
-- -- nambah dari tr harian  by peggy 2006 06 07 
-- SELECT l_POTGAJI=ISNULL(SUM(JMLABSN),0) FROM T02ABSN 
-- WHERE NIP = l_M15NIP AND FLGDPPT = ''D'' AND KDDPPT =''BSAL'' AND 
--       TANGGAL BETWEEN dateadd(day,1,l_S05TGLAKHIR) AND l_TGLPROSES AND FLAG = 1 
-- 
-- -- by peggy 2006 08 11 : sekarang bisa nambah dan kurang khusus bsal 
-- SELECT l_PLUSGAJI=ISNULL(SUM(JMLABSN),0) FROM T02ABSN 
-- WHERE NIP = l_M15NIP AND FLGDPPT = ''D'' AND KDDPPT =''BSAL'' AND 
--       TANGGAL BETWEEN dateadd(day,1,l_S05TGLAKHIR) AND l_TGLPROSES AND FLAG = 0
-- 
-- remark by peggy 2007 01 19 : nambahin mekanisme gaji golongan 
-- --   IF l_TglProses<l_M15PrdTetap
-- --      BEGIN 
-- -- --        SET l_Gaji=l_M15GajiPerc*(l_HKerja-l_POTGAJI)
-- --         SET l_Gaji=l_M15GajiPerc * (l_HKerja)
-- --      END 
-- --   ELSE IF l_TglProses>=l_M15PrdTetap
-- --      BEGIN 
-- -- --        SET l_Gaji=l_M15GajiTetap*(l_Hkerja -l_POTGAJI)
-- --         SET l_Gaji=l_M15GajiTetap * (l_Hkerja)
-- --      END 
-- -- 


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
     END ; 
     -- Gaji Golongan (Pending)     
  ELSIF l_TglProses<l_M15PrdTetap THEN 
     l_Gaji := l_M15GajiPerc ;
  ELSIF l_TglProses>=l_M15PrdTetap THEN 
     l_Gaji := l_M15GajiTetap ;
  END IF; 
  l_Gaji := l_Gaji * (l_Hkerja) ;

  -- Mengembalikan Nilai Gaji Pokok
  RETURN l_Gaji ; 
END ; 

$BODY$
LANGUAGE 'plpgsql'
GO
