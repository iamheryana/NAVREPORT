CREATE OR REPLACE FUNCTION l_m21umre(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(Outdaerah character varying,
        OutKeterangan character varying,
		OutSingkatan character varying, 
		OutUmr numeric, 
		OutTglMulai date, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	m21.daerah as Outdaerah, m21.keterangan as OutKeterangan, m21.singkatan as OutSingkatan,
            m21.umr as OutUmr, m21.tglmulai as OutTglMulai,
            m21.version as OutVersion, m21.created_by as OutCreated_by,
		    m21.created_on as OutCreated_on, m21.updated_by as OutUpdated_by, m21.updated_on as OutUpdated_on
	FROM m21umre m21
	WHERE (m21.daerah BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
