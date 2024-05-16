-- Function: l_t26pphl(date, date)

-- DROP FUNCTION l_t26pphl(date, date);

CREATE OR REPLACE FUNCTION l_t26pphl(IN l_periodeFr date, IN l_periodeTo date)
  RETURNS TABLE(OutPeriode date,
		Outnip character varying, 
		OutNama character varying, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	t26.periode as OutPeriode,
		t26.nip as Outnip,
		m15.nama as OutNama,
		t26.version as OutVersion,
		t26.created_by as OutCreated_by,
		t26.created_on as OutCreated_on,
		t26.updated_by as OutUpdated_by,
		t26.updated_on as OutUpdated_on
	FROM t26pphl t26
	INNER JOIN m15pega m15 on m15.nip=t26.nip
	WHERE (t26.periode BETWEEN l_periodeFr AND l_periodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t26pphl 
select * from m15pega 

select * from l_t26pphl('2000-01-01','2013-12-31')


*/