-- Function: l_t23mpeg(character varying, character varying)

-- DROP FUNCTION l_t23mpeg(character varying, character varying);

CREATE OR REPLACE FUNCTION l_t23mpeg(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutTanggal date,
		OutNip character varying, 
		OutNama character varying, 
		OutNilai numeric,
		OutKdcurr character varying,
		OutFlgdppt text,
		OutKddppt character varying,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	
		t23.tanggal as OutTanggal,
		m15.nip as OutNip,
		m15.nama as OutNama,
		t23.nilai as OutNilai, 
		t23.kdcurr as OutKdcurr,
		(case when t23.flgdppt='D' then 'Pendapatan' else 'Potongan' end) as OutFlgdppt, 		
		t23.kddppt as OutKddppt, 
		t23.version as OutVersion,
		t23.created_by as OutCreated_by,
		t23.created_on as OutCreated_on,
		t23.updated_by as OutUpdated_by,
		t23.updated_on as OutUpdated_on
	FROM t23mpeg t23
	INNER JOIN m15pega m15 on m15.nip=t23.nip
	WHERE (m15.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;



/*

select * from l_t23mpeg('.','ZZZ');

*/