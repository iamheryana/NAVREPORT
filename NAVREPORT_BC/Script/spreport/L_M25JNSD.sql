CREATE OR REPLACE FUNCTION l_m25jnsd(IN l_kodeFr character varying, IN l_kodeTo character varying)
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
	SELECT  m25.kode as OutKode, m25.nama as OutKeterangan, m25.singkatan as OutSingkatan, m25.version as OutVersion, 
		m25.created_by as OutCreated_by, m25.created_on as OutCreated_on, m25.updated_by as OutUpdated_by,
		m25.updated_on as OutUpdated_on
	FROM  m25jnsd m25
	WHERE (m25.kode BETWEEN l_kodeFr AND l_kodeTo); 
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