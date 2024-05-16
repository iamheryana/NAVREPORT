/****************************************
Name sprocs	: fn_Tunjangan_Keluarga
Create by	: Peggy 
Date		: 18-12-2006
Description	: Proses Untuk Tunjangan Keluarga 
Call From	: Main sprocs
**
***************************************/
DROP FUNCTION public.fn_tunjangan_keluarga (in l_fz1tunjist numeric, in l_fz1nltunjist numeric, in l_gajirp numeric)
GO
--

CREATE FUNCTION fn_Tunjangan_Keluarga (l_FZ1TunjIst   DECIMAL(5,2), 
						  l_FZ1NlTunjIst DECIMAL(15,2), 
						  l_GajiRp       DECIMAL(15,2))
RETURNS DECIMAL(15,2)
AS $$ 

-- core gak ngapa2in 
DECLARE l_TunjIst       DECIMAL(15,2) ;

BEGIN 
  --
  l_TunjIst := 0 ;
  --
  RETURN l_TunjIst ; 
  -- 
END ;

$$ language plpgsql ;

/*
SELECT fn_Tunjangan_Keluarga(1.5, 100000, 5000000) 
*/
