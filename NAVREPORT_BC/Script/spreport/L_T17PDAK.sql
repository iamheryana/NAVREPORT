-- Function: l_t17pdak(character varying, character varying, integer)

-- DROP FUNCTION l_t17pdak(character varying, character varying, integer);

CREATE OR REPLACE FUNCTION l_t17pdak(IN l_kodeFr character varying, IN l_kodeTo character varying, IN l_userid integer)
RETURNS TABLE(  OutNip character varying,
		OutNama character varying, 
		OutLevelakm numeric,
		OutFlgDppt text,
		OutKdDppt character varying,
		OutSkDppt character varying,
		OutPersen numeric,
		OutFlag text,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	t17.nip as OutNip, m15.nama as OutNama, t17.levelakm as OutLevelakm, 
		case when t17.flgdppt='D' then 'Pendapatan' else 'Potongan' end as OutFlgDppt,
		t17.kddppt as OutKdDppt, m03.skdppt as OutSkDppt, t17.persen as OutPersen,
		case when t17.flag=1 then 'Ya' else 'Tidak' end as OutFlag,
		t17.version as OutVersion, t17.created_by as OutCreated_by,
		t17.created_on as OutCreated_on, t17.updated_by as OutUpdated_by, t17.updated_on as OutUpdated_on
	FROM t17pdak t17
	INNER JOIN m15pega m15 on m15.nip=t17.nip
	INNER JOIN m03dppt m03 on m03.m03_id=t17.m03_id
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (t17.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t17pdak 
select * from m03dppt 
select * from l_t17pdak('','zzz')

*/
