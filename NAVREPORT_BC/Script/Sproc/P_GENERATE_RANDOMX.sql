
CREATE OR REPLACE FUNCTION public.p_generate_randomx () RETURNS varchar AS
$BODY$ 
--=========================================================== 	
DECLARE l_NAMEOUT VARCHAR(8); 
        l_SEED 	  NUMERIC(20,8) ; 
BEGIN 
 
l_SEED := extract(month from  current_DATE) * 100000 + extract(seconds from  current_time) * 1000 + extract(milliseconds from  current_time) / 100000 ;
l_NAMEOUT := SUBSTR('TX' || l_SEED::varchar(15),1,8) ; --SUBSTR(''##'' || l_SEED::varchar(15),1,8) ; POSTGRES TIDAK BISA CREATE TABLE PAKAI ''#'' 

l_NAMEOUT := replace(l_NAMEOUT, ',', '0');

RETURN l_NAMEOUT ;
END ; 

$BODY$
LANGUAGE 'plpgsql'
GO
