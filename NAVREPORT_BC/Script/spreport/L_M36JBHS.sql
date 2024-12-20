CREATE OR REPLACE FUNCTION l_m36jbhs(IN l_kodeFr character varying, IN l_kodeTo character varying)
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
	SELECT  m36.kode as OutKode, m36.keterangan as OutKeterangan, m36.version as OutVersion, 
		m36.created_by as OutCreated_by, m36.created_on as OutCreated_on, m36.updated_by as OutUpdated_by,
		m36.updated_on as OutUpdated_on
	FROM  m36jbhs m36
	WHERE (m36.kode BETWEEN l_kodeFr AND l_kodeTo); 
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
