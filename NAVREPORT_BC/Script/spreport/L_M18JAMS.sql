-- Function: l_m18jams(character varying, character varying)

-- DROP FUNCTION l_m18jams(character varying, character varying);

CREATE OR REPLACE FUNCTION l_m18jams(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutProgrm character varying, 
		OutLevelJKK integer, 
		OutPerush numeric,
		OutPgwi numeric,
		OutPgwik numeric,
		OutUpahMax numeric,
		OutNoAcc character varying,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY  
	SELECT	m18.progrm as OutProgrm, 
		m18.leveljkk as OutLevelJKK,
		m18.perush  as OutPerush,
		m18.pgwi  as OutPgwi,
		m18.pgwik  as OutPgwik,
		m18.upahmax  as OutUpahMax,
		m18.noacc  as OutNoAcc,
		m18.version as OutVersion,
		m18.created_by as OutCreated_by,
		m18.created_on as OutCreated_on,
		m18.updated_by as OutUpdated_by,
		m18.updated_on as OutUpdated_on
	FROM m18jams m18 
	WHERE (m18.progrm BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from m18jams  

select * from l_m18jams(' ','ZZZ')


*/