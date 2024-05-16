CREATE OR REPLACE FUNCTION l_m17uker(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying,
		OutKeterangan character varying, 
		OutSingkatan character varying, 
        OutSbacc character varying,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	m17.kduker as OutKode,
		m17.keterangan as OutKeterangan, 
		m17.singkatan as OutSingkatan,
        m17.noaccount as OutSbAcc, 
		m17.version as OutVersion,
		m17.created_by as OutCreated_by,
		m17.created_on as OutCreated_on,
		m17.updated_by as OutUpdated_by,
		m17.updated_on as OutUpdated_on
	FROM m17uker m17
	WHERE (m17.kduker BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
