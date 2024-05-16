
CREATE OR REPLACE FUNCTION public.p_insverhist (in l_pver varchar) RETURNS void AS
$BODY$
begin
if (rtrim(l_pver) > '') then 
  insert into  VerHist (VER) values(l_pver);
else
  RAISE NOTICE 'ERROR: Empty Version Number';	
end if; 
end; 	
  $BODY$
LANGUAGE 'plpgsql'
GO
