/************************************************
Name sprocs	: FN_CARITGLPAYRPRDPRD 
Create by	: PEGGY
Date		: 19-12-2006
Description	: Function CARI TGL TERAKHIR PAYROLL per periode 
Call From	: laporan jamsostek 
*************************************************/
CREATE OR REPLACE FUNCTION FN_CARITGLPAYRPRD (l_NIP        VARCHAR(10),
					      l_Periode    DATE)
RETURNS DATE

AS $$ 
-----
DECLARE l_TGL DATE; l_TGLNEXT DATE ;

BEGIN
	l_TGLNEXT := l_PERIODE + INTERVAL '1 month' ; -- DATEADD(MM, 1, l_PERIODE) ;
	l_TGLNEXT := EXTRACT(YEAR FROM  l_Periode) :: varchar(4) || '-' || right('00'  || (extract(month from l_Periode) + 1):: varchar(2), 2) || '-' || ('01') ;
	
	SELECT MAX(S05.TGLPOSTING) 
	INTO l_TGL
	FROM S05PSTD S05
	WHERE S05.NIP = l_NIP AND S05.TGLPOSTING < l_TGLNEXT ;
	
	RETURN l_TGL ;
END ;
$$ language 'plpgsql' ; 

---
/*
SELECT FN_CARITGLPAYRPRD('199107001', '2012-05-11')

select * from s05pstd 
*/
