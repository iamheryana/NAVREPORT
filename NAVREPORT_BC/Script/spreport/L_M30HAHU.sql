CREATE OR REPLACE FUNCTION l_m30hahu(IN l_kodeFr character varying, IN l_kodeTo character varying)
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
	SELECT  m30.kdharg as OutKode, m30.nama as OutKeterangan, m30.singkatan as OutSingkatan, m30.version as OutVersion, 
		m30.created_by as OutCreated_by, m30.created_on as OutCreated_on, m30.updated_by as OutUpdated_by,
		m30.updated_on as OutUpdated_on
	FROM  m30hahu m30
	WHERE (m30.kdharg BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

DROP FUNCTION l_m30jnsa(character varying, character varying);

select * from m30jnsa 

SELECT * FROM l_m30jnsa('.','ZZZ')

*/