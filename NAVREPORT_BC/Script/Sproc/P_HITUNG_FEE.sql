/****************************************
Name sprocs	: P_Hitung_Fee
Create by	: Deni
Date		: 09/09/2002
Description	: Proses Hitung Fee 
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Hitung_Fee(l_TglFr	DATE,
		         l_TglTo	DATE,
			 l_MyPass	VARCHAR(128));
--
CREATE FUNCTION P_Hitung_Fee(l_TglFr	DATE,
		         l_TglTo	DATE,
			 l_MyPass	VARCHAR(128))
RETURNS VOID 
AS $$
--
BEGIN 
-- Ini Untuk Core enggak Ngapa-Ngapain. ,,,,,
END;
$$ LANGUAGE plpgsql ;

/*
EXEC P_Hitung_Fee l_TglFr='2000-01-01',
		  l_TglTo='2002-12-31'

delete from O04DGAJ
delete from O03HGAJ

select * from O03HGAJ
select * from O04DGAJ
*/
