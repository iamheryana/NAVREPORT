
CREATE OR REPLACE FUNCTION public.fn_evaltunjanak (in l_fz1tunjank numeric, in l_fz1nltunjanak numeric, in l_m15tunjanak varchar, in l_m15jnsklmn varchar, in l_m15status varchar, in l_m15anakttgg int4) RETURNS varchar AS
$BODY$ 

DECLARE l_OK VARCHAR(1)  ; 
-----
BEGIN

l_OK := 'T'  ; 

IF (COALESCE(l_FZ1TunjAnk,0)<>0 OR COALESCE(l_FZ1NlTunjAnak,0)<>0) AND 
	l_M15TunjAnak='Y' AND 
	((l_M15JnsKlmn='L' AND (l_M15Status='K' OR l_M15Status='D')) OR 
	(l_M15JnsKlmn='P' AND l_M15Status='X')) AND l_M15AnakTtgg > 0 THEN 

	BEGIN 
	  l_OK = 'Y' ;
	END ;
END IF; 

RETURN l_OK ;

END ;
$BODY$
LANGUAGE 'plpgsql'
GO
