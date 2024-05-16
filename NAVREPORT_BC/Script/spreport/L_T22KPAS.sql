-- Function: l_t22kpas(character varying, character varying)

-- DROP FUNCTION l_t22kpas(character varying, character varying);

CREATE OR REPLACE FUNCTION l_t22kpas(IN l_kodeFr character varying, IN l_kodeTo character varying)
RETURNS TABLE(  OutNip character varying,
		OutNama character varying, 
		OutKode character varying,
		OutNoPeserta character varying, 
		OutTglMasuk date, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	t22.nip as OutNip, m15.nama as OutNama, t22.kode as OutKode,
		t22.nopeserta as OutNoPeserta, t22.tanggalmasuk as OutTglMasuk,
		t22.version as OutVersion, t22.created_by as OutCreated_by,
		t22.created_on as OutCreated_on, t22.updated_by as OutUpdated_by, t22.updated_on as OutUpdated_on
	FROM t22kpas t22
	INNER JOIN m15pega m15 on m15.nip=t22.nip
	WHERE (t22.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t22kpas 
select * from l_t22kpas('','zzz')

*/
