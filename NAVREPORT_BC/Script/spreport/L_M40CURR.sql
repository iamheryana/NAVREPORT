CREATE OR REPLACE FUNCTION l_m40curr(IN l_kodeFr character varying, IN l_kodeTo character varying)
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

	SELECT	m40.kdcurr as OutKode,
		m40.ktcurr as OutKeterangan, 
		m40.skcurr as OutSingkatan, 
		m40.version as OutVersion,
		m40.created_by as OutCreated_by,
		m40.created_on as OutCreated_on,
		m40.updated_by as OutUpdated_by,
		m40.updated_on as OutUpdated_on
	FROM m40curr m40
	WHERE (m40.kdcurr BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from m40curr
*/