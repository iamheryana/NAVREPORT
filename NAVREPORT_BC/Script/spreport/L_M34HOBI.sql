CREATE OR REPLACE FUNCTION l_m34hobi(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying, 
		OutKeterangan character varying,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT  m34.kode as OutKode, m34.keterangan as OutKeterangan, m34.version as OutVersion, 
		m34.created_by as OutCreated_by, m34.created_on as OutCreated_on, m34.updated_by as OutUpdated_by,
		m34.updated_on as OutUpdated_on
	FROM  m34hobi m34
	WHERE (m34.kode BETWEEN l_kodeFr AND l_kodeTo); 
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
