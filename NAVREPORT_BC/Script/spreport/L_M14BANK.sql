CREATE OR REPLACE FUNCTION l_m14bank(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying,
		OutKeterangan character varying, 
        OutCabang character varying,
		OutSingkatan character varying, 
        OutBiayaBank numeric,  
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	m14.kode as OutKode,
		m14.keterangan as OutKeterangan,
        m14.cabang as OutCabang,
		m14.singkatan as OutSingkatan, 
        m14.biayabank as OutBiayaBank,
		m14.version as OutVersion,
		m14.created_by as OutCreated_by,
		m14.created_on as OutCreated_on,
		m14.updated_by as OutUpdated_by,
		m14.updated_on as OutUpdated_on
	FROM m14bank m14
	WHERE (m14.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
