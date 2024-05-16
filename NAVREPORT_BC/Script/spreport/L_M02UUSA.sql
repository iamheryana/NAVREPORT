drop FUNCTION l_m02uusa(IN l_kodeFr character varying, IN l_kodeTo character varying);

CREATE OR REPLACE FUNCTION l_m02uusa(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying,
		OutKeterangan character varying, 
		OutSingkatan character varying,
        OutLvlJkk character varying,
        OutSbacc character varying,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	m02.kode as OutKode,
		m02.keterangan as OutKeterangan, 
		m02.singkatan as OutSingkatan,
        CASE WHEN m02.kelompok IS NULL OR trim(m02.kelompok) = '' THEN 'ALL' ELSE m02.kelompok END as OutLvlJkk,
        m02.noaccount as OutSbAcc,
		m02.version as OutVersion,
		m02.created_by as OutCreated_by,
		m02.created_on as OutCreated_on,
		m02.updated_by as OutUpdated_by,
		m02.updated_on as OutUpdated_on
	FROM m02uusa m02
	WHERE (m02.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
