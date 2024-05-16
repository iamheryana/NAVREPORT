
CREATE OR REPLACE FUNCTION public.getbarangx (in barangid int4, in tglmasuk date) RETURNS SETOF zzz_barang AS
$BODY$
--DECLARE 
 --   V_barangid integer;
--begin
select id,kode,nama,kategori,tglmasuk from zzz_barang;
--END;
$BODY$
LANGUAGE 'sql'
GO
