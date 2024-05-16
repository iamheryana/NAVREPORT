CREATE OR REPLACE FUNCTION l_m27lmbd(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying, 
		OutKeterangan character varying,
		OutSingkatan character varying, 
		OutKaLembaga character varying, 
		OutAlamat character varying, 
		OutKota character varying, 
		OutTelfon character varying, 
		OutFax character varying, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT  m27.kode as OutKode, m27.nama as OutKeterangan, m27.singkatan as OutSingkatan, m27.kalembaga as OutKaLembaga,
        m27.alamat as OutAlamat, m27.kota as OutKota, m27.telfon as OutTelfon, m27.fax as OutFax, m27.version as OutVersion, 
		m27.created_by as OutCreated_by, m27.created_on as OutCreated_on, m27.updated_by as OutUpdated_by,
		m27.updated_on as OutUpdated_on
	FROM  m27lmbd m27
	WHERE (m27.kode BETWEEN l_kodeFr AND l_kodeTo); 
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