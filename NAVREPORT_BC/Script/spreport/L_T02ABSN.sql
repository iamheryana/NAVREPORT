-- Function: l_t02absn(character varying, character varying, integer)

-- DROP FUNCTION l_t02absn(character varying, character varying, integer);

CREATE OR REPLACE FUNCTION l_t02absn(IN l_kodeFr character varying, IN l_kodeTo character varying, IN l_userid integer)
RETURNS TABLE(  OutNip character varying,
		OutNama character varying,  
		OutTanggal date,
		OutFlgDppt text,
		OutKdDppt character varying,
		OutNMDppt character varying, 
		OutJmlAbsn numeric,  
		OutFlag text,
		OutBayardiMuka text,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	t02.nip as OutNip, m15.nama as OutNama,  t02.tanggal as OutTanggal,
		case when t02.flgdppt='D' then 'Pendapatan' else 'Potongan' end as OutFlgDppt,
		t02.kddppt as OutKdDppt, m03.nmdppt as OutNMDppt,
		t02.jmlabsn as OutJmlAbsn,
		case when t02.flag=0 then 'Tidak' else 'Ya' end as OutFlag,
		case when t02.bayardimuka=0 then 'Tidak' else 'Ya' end as OutBayardiMuka,
		t02.version as OutVersion, t02.created_by as OutCreated_by,
		t02.created_on as OutCreated_on, t02.updated_by as OutUpdated_by, t02.updated_on as OutUpdated_on
	FROM t02absn t02
	INNER JOIN m15pega m15 on m15.nip=t02.nip
	INNER JOIN m03dppt m03 on m03.m03_id=t02.m03_id 
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (t02.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t02absn 
select * from m03dppt 
select * from l_t02absn('','zzz')

*/
