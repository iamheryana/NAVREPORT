CREATE OR REPLACE FUNCTION l_m32jmed(IN l_kodeFr character varying, IN l_kodeTo character varying)
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
	SELECT  m32.kode as OutKode, m32.keterangan as OutKeterangan, m32.singkatan as OutSingkatan, m32.version as OutVersion, 
		m32.created_by as OutCreated_by, m32.created_on as OutCreated_on, m32.updated_by as OutUpdated_by,
		m32.updated_on as OutUpdated_on
	FROM  m32jmed m32
	WHERE (m32.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

DROP FUNCTION l_m32jnsa(character varying, character varying);

select * from m32jnsa 

SELECT * FROM l_m32jnsa('.','ZZZ')

*/