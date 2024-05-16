-- Function: l_t24pdll(character varying, character varying)

-- DROP FUNCTION l_t24pdll(character varying, character varying);

CREATE OR REPLACE FUNCTION l_t24pdll(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(Outnip character varying, 
		OutNama character varying, 
		OutKeterangan character varying,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	t24.nip as Outnip,
		m15.nama as OutNama,
		t24.keterangan as OutKeterangan,
		t24.version as OutVersion,
		t24.created_by as OutCreated_by,
		t24.created_on as OutCreated_on,
		t24.updated_by as OutUpdated_by,
		t24.updated_on as OutUpdated_on
	FROM t24pdll t24
	INNER JOIN m15pega m15 on m15.nip=t24.nip
	WHERE (t24.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t24pdll 
select * from m15pega 

select * from l_t24pdll(' ','ZZZ')


*/