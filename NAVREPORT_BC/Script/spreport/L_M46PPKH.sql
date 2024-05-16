CREATE OR REPLACE FUNCTION l_m46ppkh(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutFlgdppt text,
		OutKode character varying,
		OutKeterangan character varying, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	(case when m03.flgdppt='D' then 'Pendapatan' else 'Potongan' end) as OutFlgdppt,
		m03.kddppt as OutKode,
		m03.nmdppt as OutKeterangan, 
		m46.version as OutVersion,
		m46.created_by as OutCreated_by,
		m46.created_on as OutCreated_on,
		m46.updated_by as OutUpdated_by,
		m46.updated_on as OutUpdated_on
	FROM m46ppkh m46
	INNER JOIN m03dppt m03 ON m03.m03_id=m46.m03_id		
	WHERE (m03.kddppt BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from m03dppt
select * from m46ppkh
*/