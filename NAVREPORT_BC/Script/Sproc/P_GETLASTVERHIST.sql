
CREATE OR REPLACE FUNCTION public.p_getlastverhist () RETURNS varchar AS
$BODY$
declare l_ver varchar(20) ;
begin
 l_ver:=' ' ; 
if (select count(VER) from verhist)>0 then 
   l_ver:=(select VER as VER from VerHist where DATE = (select max(DATE) as DT from VerHist));
end if; 
return l_ver; 
end ; 
$BODY$
LANGUAGE 'plpgsql'
GO
