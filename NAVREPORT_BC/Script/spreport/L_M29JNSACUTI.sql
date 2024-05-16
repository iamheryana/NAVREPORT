CREATE OR REPLACE FUNCTION l_m29jnsacuti(IN l_kodeFr character varying, IN l_kodeTo character varying)
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
	SELECT  m29.kode as OutKode, m29.nama as OutKeterangan, m29.singkatan as OutSingkatan, m29.jnscuti as OutJnsCuti,
        m29.version as OutVersion, m29.created_by as OutCreated_by, m29.created_on as OutCreated_on, m29.updated_by as OutUpdated_by,
		m29.updated_on as OutUpdated_on
	FROM  m29jnsa m29
	WHERE (m29.kode BETWEEN l_kodeFr AND l_kodeTo); 
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