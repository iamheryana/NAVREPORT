/****************************************
Name sprocs	: P_OIL_RND_THP
Create by	: PEGGY 
Date		: 18-12-2006
Description	: Proses Perhitungan Pembulatan THP PT OI LWG 
Call From	: P_PAYRP1
*****************************************/
DROP FUNCTION P_OIL_RND_THP(l_m15NIP       VARCHAR(10),
					   l_TglProses  DATE,
					   l_UserId	VARCHAR(12),
					   l_MyPass     VARCHAR(128)) ;
--
CREATE FUNCTION P_OIL_RND_THP(l_m15NIP       VARCHAR(10),
					   l_TglProses  DATE,
					   l_UserId	VARCHAR(12),
					   l_MyPass     VARCHAR(128)) 
RETURNS VOID 
AS $$ 
---- Program ini hanya untuk otsuka lawang jadi tidak melakukan apa-apa
BEGIN 
END; 

$$ LANGUAGE plpgsql ;
