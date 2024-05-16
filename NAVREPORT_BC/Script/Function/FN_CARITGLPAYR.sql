/************************************************
Name sprocs	: FN_CARITGLPAYR
Create by	: PEGGY
Date		: 03-07-2006
Description	: Function CARI TGL TERAKHIR PAYROLL 
Call From	: Main sprocs
*************************************************/
CREATE OR REPLACE FUNCTION FN_CARITGLPAYR (l_NIP        VARCHAR(10),
					   l_Periode    DATE)
RETURNS DATE

AS $$ 
-----
DECLARE l_TGL DATE ; 

BEGIN
	SELECT MAX(S05.TGLPOSTING) 
	INTO l_TGL
	FROM S05PSTD  S05 
	WHERE S05.NIP = l_NIP ; --AND TGLPOSTING <=l_PERIODE 
	RETURN l_TGL ;
END ; 
$$ language 'plpgsql' ;  
---
/*
SELECT FN_CARITGLPAYR('199107001', '2006-05-01')

SELECT * FROM S05PSTD 
*/
