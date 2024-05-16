
CREATE OR REPLACE FUNCTION public.updatebarang (in barangid int4, in nama bpchar) RETURNS SETOF zzz_barang AS
$BODY$
UPDATE zzz_barang SET nama = $2 WHERE id = $1 RETURNING *;
$BODY$
LANGUAGE 'sql'
GO
