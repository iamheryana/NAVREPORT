CREATE OR REPLACE FUNCTION l_m24mutr(IN l_kodeFr character varying, IN l_kodeTo character varying)
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
	SELECT  m24.kdmutr as OutKode, m24.nama as OutKeterangan, m24.singkatan as OutSingkatan, m24.version as OutVersion, 
		m24.created_by as OutCreated_by, m24.created_on as OutCreated_on, m24.updated_by as OutUpdated_by,
		m24.updated_on as OutUpdated_on
	FROM  m24mutr m24
	WHERE (m24.kdmutr BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

DROP FUNCTION l_m29jnsa(character varying, character varying);

select * from m29jnsa 

SELECT * FROM l_m29jnsa('.','ZZZ')

*/