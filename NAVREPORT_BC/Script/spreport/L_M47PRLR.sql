-- Function: l_m47prlr(date, date)

-- DROP FUNCTION l_m47prlr(date, date);

CREATE OR REPLACE FUNCTION l_m47prlr(IN l_tglFr date, IN l_tglTo date)
RETURNS TABLE(OutTglProses date,
		OutTglAwal date,
		OutTglAkhir date, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	m47.tglproses as OutTglProses, m47.tglawal as OutTglAwal, m47.tglakhir as OutTglAkhir,
            m47.version as OutVersion, m47.created_by as OutCreated_by,
		    m47.created_on as OutCreated_on, m47.updated_by as OutUpdated_by, m47.updated_on as OutUpdated_on
	FROM m47prlr m47
	WHERE (m47.tglproses BETWEEN l_tglFr AND l_tglTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*

select * from l_m47prlr(null,'2013-11-30')

*/
