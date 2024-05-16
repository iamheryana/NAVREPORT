
CREATE OR REPLACE FUNCTION public.insertbarang (in barangid int4, in kode bpchar, in nama bpchar, in tglmasuk date, in kategori int4) RETURNS SETOF zzz_barang AS
$BODY$
INSERT INTO zzz_barang(id, kode, nama, tglmasuk, kategori) VALUES ($1, $2, $3, $4, $5) RETURNING *;
$BODY$
LANGUAGE 'sql'
GO
