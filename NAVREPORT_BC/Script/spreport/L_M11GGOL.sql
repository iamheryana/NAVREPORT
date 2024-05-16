CREATE OR REPLACE FUNCTION l_m11ggol(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying,
		OutKeterangan character varying, 
		OutFrtempo integer, 
		OutGajipokok numeric,
		OutKdcurr character varying,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	m11.kode as OutKode,
		m12.keterangan as OutKeterangan, 
		m11.frtempo as OutFrtempo, 
		m11.gajipokok as OutGajipokok,
		m11.kdcurr as OutKdcurr,
		m11.version as OutVersion,
		m11.created_by as OutCreated_by,
		m11.created_on as OutCreated_on,
		m11.updated_by as OutUpdated_by,
		m11.updated_on as OutUpdated_on
	FROM m11ggol m11
	INNER JOIN m12hgol m12 ON m12.m12_id=m11.m12_id
	INNER JOIN m40curr m40 ON m40.kdcurr=m11.kdcurr 		
	WHERE (m11.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from m40curr
select * from m11ggol
select * from m12hgol
*/