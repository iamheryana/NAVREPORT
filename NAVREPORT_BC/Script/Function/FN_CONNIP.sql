/****************************************
Name sprocs	: fn_ConNIP
Create by	: Chak Suhe
Date		: 10-09-2004
Description	: untuk menghilangkan titik di Nip
Call From	: Main sprocs
Sub sprocs	: 
*****************************************/
CREATE OR REPLACE FUNCTION fn_ConNIP(l_Nip VARCHAR(10))

RETURNS VARCHAR(10)

AS $$ 

   DECLARE l_mnLoop  	INT ;
	   l_RNIP	VARCHAR(10) ;
	   l_Isi     	VARCHAR(1) ;

BEGIN
   --
   --
   l_RNIP   := ' ' ;
   l_mnLoop := 1 ;
   --
   WHILE l_mnLoop<=10 LOOP
   BEGIN
     --
     l_Isi=SUBSTRING(l_Nip,l_mnLoop,1) ; 
     --
     IF l_Isi NOT IN ('.') THEN 
       BEGIN
          l_RNIP := RTRIM(l_RNIP) || l_Isi ; 
       END ;
     END IF ; 
     --*
     --
     l_mnLoop := l_mnLoop + 1 ;
     -- 
   END ;
   END LOOP ; 
   --
   RETURN l_RNIP ;
END; 
$$ language 'plpgsql' ; 


/*
SELECT fn_ConNIP('1.ART.0001')

*/