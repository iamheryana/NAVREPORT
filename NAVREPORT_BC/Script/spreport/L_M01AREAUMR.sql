CREATE OR REPLACE FUNCTION l_m01areaumr(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying,
		OutKeterangan character varying, 
		OutSingkatan character varying, 
		OutUMR character varying,  
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	m01.kode as OutKode,
		m01.keterangan as OutKeterangan, 
		m01.singkatan as OutSingkatan, 
		m01.umr as OutUMR,
		m01.version as OutVersion,
		m01.created_by as OutCreated_by,
		m01.created_on as OutCreated_on,
		m01.updated_by as OutUpdated_by,
		m01.updated_on as OutUpdated_on
	FROM m01area m01
	WHERE (m01.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from m01area 

select * from l_m01areaumr('','ZZZ')

*/