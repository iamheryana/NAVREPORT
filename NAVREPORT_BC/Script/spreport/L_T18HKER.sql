-- Function: l_t18hker(character varying, character varying, integer)

-- DROP FUNCTION l_t18hker(character varying, character varying, integer);

CREATE OR REPLACE FUNCTION l_t18hker(IN l_kodeFr character varying, IN l_kodeTo character varying, IN l_userid integer)
RETURNS TABLE(OutStatus text,
		OutNip character varying,
		OutNama character varying, 
		OutHKerja numeric,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	case when t18.inout='K' then 'Keluar' else 'Masuk' end as OutStatus,
		t18.nip as OutNip, m15.nama as OutNama, t18.hkerja as OutHKerja,
		t18.version as OutVersion, t18.created_by as OutCreated_by,
		t18.created_on as OutCreated_on, t18.updated_by as OutUpdated_by, t18.updated_on as OutUpdated_on
	FROM t18hker t18
	INNER JOIN m15pega m15 on m15.nip=t18.nip
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (t18.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t18hker 
select * from l_t18hker('','zzz')

*/
