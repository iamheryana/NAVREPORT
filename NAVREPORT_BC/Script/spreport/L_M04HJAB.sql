CREATE OR REPLACE FUNCTION l_m04hjab(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying, 
		OutKeterangan character varying,
		OutSingkatan character varying, 
		OutKdKjab character varying,  
		OutFlgStruk character varying,  
		OutTglSuspend date,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT m04.kode as OutKode, m04.keterangan as OutKeterangan, m04.singkatan as OutSingkatan, 
		   m04.kdkjab as OutKdKjab, m04.flgstruk as OutFlgStruk, m04.tglsuspend as OutTglSuspend, m04.version as OutVersion,
		   m04.created_by as OutCreated_by, m04.created_on as OutCreated_on, m04.updated_by as OutUpdated_by,
		   m04.updated_on as OutUpdated_on
	FROM m04hjab m04
	WHERE (m04.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

DROP FUNCTION l_m04hjab(character varying, character varying);

SELECT * FROM l_m04hjab('.','ZZZ')

*/