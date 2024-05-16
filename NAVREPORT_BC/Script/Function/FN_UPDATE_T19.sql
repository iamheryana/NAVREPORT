
CREATE OR REPLACE FUNCTION public.fn_update_t19 (in l_nip varchar) RETURNS void AS
$BODY$ 

BEGIN 
DELETE FROM T19PESA WHERE NIP = l_NIP; 
END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
