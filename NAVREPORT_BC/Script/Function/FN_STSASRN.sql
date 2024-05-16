/****************************************
Name sprocs	: fn_StsAsrn
Create by	: 
Date		: 29-05-2006
Description	: Mencari status terakhir sebelum tgl pass 
Call From	: Main sprocs
Sub sprocs	: 
*****************************************/
CREATE OR REPLACE  FUNCTION fn_StsAsrn(l_Nip 	VARCHAR(10),
					l_Periode	DATE)

RETURNS VARCHAR(2) 
---
AS $$ 
DECLARE 	l_Sts 	VARCHAR(2) ;

BEGIN
   --
l_sts := (
	  SELECT T.Status 
	  FROM 
	  (
	   SELECT S0F.Status,Periode=MAX(S0F.Periode)
	   FROM V_ASRS S0F 
	   WHERE NIP=l_NIP AND S0F.Periode <= l_Periode AND Hubungan = ' '
	   GROUP BY S0F.Status 
	  ) t 
	  ORDER BY Periode DESC LIMIT 1 
	) ;
return l_sts ;
END ;
$$ language 'plpgsql' ; 
/*
SELECT fn_StsAsrn('18','2006-05-11')
*/
