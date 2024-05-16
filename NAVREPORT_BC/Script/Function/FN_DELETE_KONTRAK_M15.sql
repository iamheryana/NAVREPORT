
CREATE OR REPLACE FUNCTION public.fn_delete_kontrak_m15 (in l_nip varchar) RETURNS varchar AS
$BODY$ 
declare Result varchar(100);
BEGIN 
UPDATE M15PEGA 
SET TTLKONTRAK = (select count(*) from t16pkon where nip = l_nip),
    TGLKONTRAKAKHIR = (select max(tglakhir) from t16pkon where nip = l_nip)
WHERE NIP = l_nip; 
return(Result);

END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
