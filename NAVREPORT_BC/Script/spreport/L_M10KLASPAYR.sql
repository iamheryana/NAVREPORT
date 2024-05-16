CREATE OR REPLACE FUNCTION l_m10klaspayr(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying, 
		OutKeterangan character varying,
		OutSingkatan character varying, 
		OutLmlPgwBlnll numeric,  
		OutHarian text,  
		OutKontrak text,
		OutJnsPajak character,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT m10.kode as OutKode, m10.keterangan as OutKeterangan, m10.singkatan as OutSingkatan, 
		   m10.jmlpgwblnll as OutLmlPgwBlnll, (case when m10.harian=0 then 'Tidak' else 'Ya' end) as OutHarian, 
		   (case when m10.kontrak=0 then 'Tidak' else 'Ya' end) as OutKontrak, 
		m10.jnspajak as OutJnsPajak, m10.version as OutVersion, 
		   m10.created_by as OutCreated_by, m10.created_on as OutCreated_on, m10.updated_by as OutUpdated_by,
		   m10.updated_on as OutUpdated_on
	FROM m10klas m10
	WHERE (m10.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

DROP FUNCTION l_m10klaspayr(character varying, character varying);

select * from m10klas 

SELECT * FROM l_m10klaspayr('.','ZZZ')

*/
