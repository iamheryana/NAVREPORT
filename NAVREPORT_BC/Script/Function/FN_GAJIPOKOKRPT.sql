/****************************************
Name sprocs	: fn_GajiPokokRPT
Create by	: PEGGY
Date		: 15-11-2007
Description	: Proses Untuk Hitung Gaji Pokok
Call From	: rekonsiliasi pendapatan tetap 
*****************************************/
CREATE OR REPLACE FUNCTION fn_GajiPokokRPT(l_FZ1FlgGGol  	VARCHAR(1),
						l_TglProses   	DATE,  
						l_M15PrdTetap 	DATE, 	
						l_M15GajiPerc 	DECIMAL(15,2),
						l_M15GajiTetap 	DECIMAL(15,2),
						l_M15NIP	VARCHAR(10),			     
						l_M15TglMasuk   DATE,
						l_M15KdGlng	VARCHAR(4))                            
			
RETURNS DECIMAL(15,2)

AS $$ 

DECLARE l_Gaji		DECIMAL(15,2);
	l_MasaKerja	DECIMAL(5,0);
	l_M15TglKeluar	DATE;

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
     END ;
     -- Gaji Golongan (Pending)     
  ELSIF l_TglProses<l_M15PrdTetap THEN 
     l_Gaji := l_M15GajiPerc ; 
  ELSIF l_TglProses>=l_M15PrdTetap THEN 
     l_Gaji := l_M15GajiTetap ; 
  END IF;      
  --*
  -- Mengembalikan Nilai Gaji Pokok
  RETURN l_Gaji ; 
END ; 
$$ language 'plpgsql' ;  
/*
 SELECT fn_GajiPokokRPT('T', 
                            		date '2008-10-25',
                            		date '2007-01-01',
                            		25000000.00,
                        			25000000.00,
                        			'0001908',
                            		date '2007-01-01',
									'G1') 
*/ 
