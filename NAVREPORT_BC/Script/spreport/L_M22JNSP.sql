CREATE OR REPLACE FUNCTION l_m22jnsp(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying,
		OutKeterangan character varying, 
		OutSingkatan character varying,  
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	m22.kdjnsp as OutKode,
		m22.nmpiut as OutKeterangan, 
		m22.skpiut as OutSingkatan,  
		m22.version as OutVersion,
		m22.created_by as OutCreated_by,
		m22.created_on as OutCreated_on,
		m22.updated_by as OutUpdated_by,
		m22.updated_on as OutUpdated_on
	FROM m22jnsp m22
	WHERE (m22.kdjnsp BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*
select * from m22jnsp 

select * from l_m22jnsp('','ZZ')

*/
