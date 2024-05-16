-- Function: l_m19hslg(character varying, character varying)

-- DROP FUNCTION l_m19hslg(character varying, character varying);

CREATE OR REPLACE FUNCTION l_m19hslg(IN l_kodeFr character varying, IN l_kodeTo character varying)
RETURNS TABLE(  OutTipeLap text,
		OutFlgDppt text, 
		OutNomFormat numeric,
		OutKeterangan character varying,
		OutKdDppt character varying,
		OutNmDppt character varying,
		OutFlgAngs text,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	case when m19.tipelap='1' then 'Slip Gaji'
		     when m19.tipelap='2' then 'Arsip Gaji'
		     when m19.tipelap='3' then 'Kartu Slip' end as OutTipeLap, 
		case when m19.flgdppt='D' then 'Pendapatan' else 'Potongan' end as OutFlgDppt,
		m19.nomformat as OutNomFormat, m19.keterangan as OutKeterangan,
		m20.kddppt as OutKdDppt, m03.nmdppt as OutNmDppt,	
		case when m20.flgangs=' ' then 'Non'
		     when m20.flgangs='A' then 'Pokok'
		     when m20.flgangs='B' then 'Bunga' end as OutFlgAngs,
		m19.version as OutVersion, m19.created_by as OutCreated_by,
		m19.created_on as OutCreated_on, m19.updated_by as OutUpdated_by, m19.updated_on as OutUpdated_on
	FROM m19hslg m19
	INNER JOIN m20dslg m20 on m20.m19_id=m19.m19_id 
	INNER JOIN m03dppt m03 on m03.m03_id=m20.m03_id
	WHERE (m19.tipelap between l_kodeFr and l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from m19hslg 
select * from m20dslg 
select * from m03dppt 
select * from l_m19hslg(' ','ZZZ')

*/
