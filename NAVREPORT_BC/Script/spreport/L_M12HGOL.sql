CREATE OR REPLACE FUNCTION l_m12hgol(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying, 
		OutKeterangan character varying,
		OutSingkatan character varying, 
		OutMinGol numeric,  
		OutMidGol numeric,  
		OutMaxGol numeric,  
		OutTglSuspend date,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT m12.kode as OutKode, m12.keterangan as OutKeterangan, m12.singkatan as OutSingkatan, 
		   m12.mingol as OutMinGol, m12.midgol as OutMidGol, m12.maxgol as OutMaxGol, m12.tglsuspend as OutTglSuspend, m12.version as OutVersion,
		   m12.created_by as OutCreated_by, m12.created_on as OutCreated_on, m12.updated_by as OutUpdated_by,
		   m12.updated_on as OutUpdated_on
	FROM m12hgol m12
	WHERE (m12.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*

DROP FUNCTION l_m12hgol(character varying, character varying);

SELECT * FROM l_m12hgol('.','ZZZ')

*/