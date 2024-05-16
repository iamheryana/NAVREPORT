/****************************************
Name sprocs	: P_InstOtsu
Create by	: Chak Suhe
Date		: 03-02-2005
Description	: Proses Incentive PT. Otsuka
Call From	: P_PAYRP1
*****************************************/
DROP FUNCTION P_InstOtsu(l_NIP        VARCHAR(10),
					l_TglProses  DATE,
					l_TglAkhir   DATE,                       
					l_GajiRp     DECIMAL(15,2),
					l_TglMasuk   DATE,
					l_KdGlng     VARCHAR(4),
					l_MyPass     VARCHAR(128),
					l_S01_ID     INT);
--
CREATE FUNCTION P_InstOtsu(l_NIP        VARCHAR(10),
					l_TglProses  DATE,
					l_TglAkhir   DATE,                       
					l_GajiRp     DECIMAL(15,2),
					l_TglMasuk   DATE,
					l_KdGlng     VARCHAR(4),
					l_MyPass     VARCHAR(128),
					l_S01_ID     INT)
					
RETURNS VOID 
AS $$
---- Program ini hanya untuk otsuka jadi tidak melakukan apa-apa
BEGIN

END ;

$$ LANGUAGE plpgsql ;
