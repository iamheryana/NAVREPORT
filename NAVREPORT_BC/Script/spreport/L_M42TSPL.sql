CREATE OR REPLACE FUNCTION l_m42tspl(IN l_kodeFr character varying, IN l_kodeTo character varying)
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
	SELECT  m42.kode as OutKode, m42.keterangan as OutKeterangan, m42.singkatan as OutSingkatan, m42.version as OutVersion, 
		m42.created_by as OutCreated_by, m42.created_on as OutCreated_on, m42.updated_by as OutUpdated_by,
		m42.updated_on as OutUpdated_on
	FROM  m42tspl m42
	WHERE (m42.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

DROP FUNCTION l_m42jnsa(character varying, character varying);

select * from m42jnsa 

SELECT * FROM l_m42jnsa('.','ZZZ')

*/
