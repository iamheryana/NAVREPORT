CREATE OR REPLACE FUNCTION l_m06hkjb(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying,
		OutKeterangan character varying, 
		OutSingkatan character varying, 
		OutTglsuspend date,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	m06.kode as OutKode,
		m06.keterangan as OutKeterangan, 
		m06.singkatan as OutSingkatan, 
		m06.tglsuspend as OutTglsuspend, 
		m06.version as OutVersion,
		m06.created_by as OutCreated_by,
		m06.created_on as OutCreated_on,
		m06.updated_by as OutUpdated_by,
		m06.updated_on as OutUpdated_on
	FROM m06hkjb m06
	WHERE (m06.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
