/****************************************
Name sprocs	: fn_Tunjangan_Anak
Create by	: Wati
Date		: 24-06-2003
Description	: Proses Untuk Tunjangan Anak
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE FUNCTION fn_Tunjangan_Anak ( l_JmlAnak      	INT,
						l_FZ1TunjAnak  	DECIMAL(5,2), 
						l_FZ1NlTunjAnak DECIMAL(15,2), 
						l_GajiRp       	DECIMAL(15,2))
RETURNS DECIMAL(15,2)

AS $$ 
  --
  DECLARE l_TotalAnak 	INT ;             
          l_TunjAnak    DECIMAL(15,2) ;          
          l_NlTunjAnak  DECIMAL(15,2) ;        
  --        

BEGIN 
  IF l_JmlAnak >= 3 THEN 
     BEGIN
       l_TotalAnak := 3; 
     END ;
  ELSE
     BEGIN
       l_TotalAnak := l_JmlAnak ;
     END ;
  END IF; 
  --
  -- Nilai Kurs
-- BY PEGGY 2006 12 18 
--  SELECT l_TunjAnak=(l_TotalAnak*l_FZ1TunjAnak*l_GajiRp)/100
  CASE WHEN l_FZ1TunjAnak <> 0 
	THEN l_TunjAnak := (l_TotalAnak*l_FZ1TunjAnak*l_GajiRp)/100 ; 
	ELSE l_TunjAnak := (l_TotalAnak*l_FZ1NlTunjAnak) ;
  END CASE ; 
  --
  RETURN l_TunjAnak ;
  -- 
END ; 
$$ language 'plpgsql' ;

/*
SELECT fn_Tunjangan_Anak (4,1.5, 100000, 5000000) 