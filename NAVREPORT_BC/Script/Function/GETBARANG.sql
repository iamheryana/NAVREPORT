
CREATE OR REPLACE FUNCTION public.getbarang (in barangid int4, in tglmasuk date) RETURNS SETOF zzz_barang AS
$BODY$
select id,kode,nama,kategori,tglmasuk from zzz_barang where id=$1 and tglmasuk >= $2;
$BODY$
LANGUAGE 'sql'
GO
