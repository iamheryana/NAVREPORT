/****************************************
Name sprocs	: P_OIL_UNRND_THP
Create by	: PEGGY 
Date		: 18-12-2006
Description	: Proses Perhitungan Pembulatan THP PT OI LWG 
Call From	: P_PAYRP1
*****************************************/
DROP FUNCTION P_OIL_UNRND_THP(l_m15NIP        VARCHAR(10),
			   l_TglProses  DATE) ;
--
CREATE FUNCTION P_OIL_UNRND_THP(l_m15NIP        VARCHAR(10),
			   l_TglProses  DATE) 
RETURNS VOID 
AS $$
---- Program ini hanya untuk otsuka lawang jadi tidak melakukan apa-apa
BEGIN 
END;
$$ LANGUAGE plpgsql ;
