CREATE OR REPLACE FUNCTION l_m16alkl(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying, 
		OutKeterangan character varying,
		OutSingkatan character varying, 
		OutFlagMati text,  
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT  m16.kode as OutKode, m16.keterangan as OutKeterangan, m16.singkatan as OutSingkatan, 
		(case when  m16.flagmati=0 then 'Tidak' else 'Ya' end) as OutFlagMati, 
		m16.version as OutVersion, 
		m16.created_by as OutCreated_by, m16.created_on as OutCreated_on, m16.updated_by as OutUpdated_by,
		m16.updated_on as OutUpdated_on
	FROM  m16alkl m16
	WHERE (m16.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

DROP FUNCTION l_m16alkl(character varying, character varying);

select * from m16alkl 

SELECT * FROM l_m16alkl('.','ZZZ')

*/
