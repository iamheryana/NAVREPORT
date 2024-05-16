/****************************************
Name sprocs	: P_OILIncNTransTHR
Create by	: PEGGY 
Date		: 22-12-2006
Description	: Proses Incentive PT. Otsuka LAWANG 
Call From	: P_PAYRP1
*****************************************/
DROP FUNCTION P_OILIncNTransTHR(l_NIP        VARCHAR(10),
					     l_TglProses  DATE,
					     l_TglAkhir   DATE,                       
					     l_GajiRp     DECIMAL(15,2),
				 	     l_TglMasuk   DATE,
					     l_KdGlng	  VARCHAR(4),
					     l_KdCaba	  VARCHAR(4),
					     l_MyPass     VARCHAR(128),
					     INOUT l_Total_Fix  DECIMAL(15,2)) ;
--
CREATE FUNCTION P_OILIncNTransTHR(l_NIP        VARCHAR(10),
					     l_TglProses  DATE,
					     l_TglAkhir   DATE,                       
					     l_GajiRp     DECIMAL(15,2),
				 	     l_TglMasuk   DATE,
					     l_KdGlng	  VARCHAR(4),
					     l_KdCaba	  VARCHAR(4),
					     l_MyPass     VARCHAR(128),
					     INOUT l_Total_Fix  DECIMAL(15,2)) 

--RETURNS DECIMAL(15,2)

AS $$
--- gak ngapa2in untuk core 
--DECLARE	l_Total_Fix	DECIMAL(15,2);
BEGIN
  -- l_Total_Fix := 0; 
   --RETURN l_Total_Fix; 
END ;
$$ LANGUAGE plpgsql ;
