CREATE OR REPLACE FUNCTION l_m49kurs(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying,
		OutKeterangan character varying, 
		OutPeriode date, 
		OutKurs numeric,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	m49.kdcurr as OutKode,
		m40.ktcurr as OutKeterangan, 
		m49.periode as OutPeriode, 
		m49.kurs as OutKurs,
		m49.version as OutVersion,
		m49.created_by as OutCreated_by,
		m49.created_on as OutCreated_on,
		m49.updated_by as OutUpdated_by,
		m49.updated_on as OutUpdated_on
	FROM m49kurs m49
	INNER JOIN m40curr m40 ON m40.kdcurr=m49.kdcurr 		
	WHERE (m49.kdcurr BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from m40curr
select * from m49kurs
*/