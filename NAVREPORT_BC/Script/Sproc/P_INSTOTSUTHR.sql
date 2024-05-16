/****************************************
Name sprocs	: P_InstOtsuTHR
Create by	: peggy
Date		: 03-10-2006
Description	: Proses tunjangan jabatan 
Call From	: P_PTHR
*****************************************/
DROP FUNCTION P_InstOtsuTHR(l_NIP        VARCHAR(10),
				  l_TglProses  DATE,
				  l_TglAkhir   DATE,                       
				  l_GajiRp     DECIMAL(15,2),
				  l_TglMasuk   DATE,
				  l_KdGlng     VARCHAR(4),
				  l_MyPass     VARCHAR(128),
				  INOUT l_TotalFix   DECIMAL(15,2)); 
--
CREATE FUNCTION P_InstOtsuTHR(l_NIP        VARCHAR(10),
				  l_TglProses  DATE,
				  l_TglAkhir   DATE,                       
				  l_GajiRp     DECIMAL(15,2),
				  l_TglMasuk   DATE,
				  l_KdGlng     VARCHAR(4),
				  l_MyPass     VARCHAR(128),
				  INOUT l_TotalFix   DECIMAL(15,2))

AS $$
---- Program ini hanya untuk otsuka jadi tidak melakukan apa-apa
BEGIN

END ;

$$ LANGUAGE plpgsql ;
