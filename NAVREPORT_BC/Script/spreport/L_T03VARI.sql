-- Function: l_t03vari(character varying, character varying, integer)

-- DROP FUNCTION l_t03vari(character varying, character varying, integer);

CREATE OR REPLACE FUNCTION l_t03vari(IN l_kodeFr character varying, IN l_kodeTo character varying, IN l_userid integer)
RETURNS TABLE(  OutNip character varying,
		OutNama character varying,  
		OutFlgDppt text,
		OutKdDppt character varying,
		OutNMDppt character varying,
		OutPrdMulai date,
		OutPrdsd date,
		OutPersen numeric,
		OutNilai numeric,
		OutKdCurr character varying,
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
	SELECT	t03.nip as OutNip, m15.nama as OutNama,  
		case when t03.flgdppt='D' then 'Pendapatan' else 'Potongan' end as OutFlgDppt,
		t03.kddppt as OutKdDppt, m03.nmdppt as OutNMDppt,
		t03.prdmulai as OutPrdMulai, t03.prdsd as OutPrdsd,
		t03.persen as OutPersen, t03.nilai as OutNilai, t03.kdcurr as OutKdCurr, 
		case when t03.flag=0 then 'Tidak' else 'Ya' end as OutFlag,
		case when t03.bayardimuka=0 then 'Tidak' else 'Ya' end as OutBayardiMuka,
		t03.version as OutVersion, t03.created_by as OutCreated_by,
		t03.created_on as OutCreated_on, t03.updated_by as OutUpdated_by, t03.updated_on as OutUpdated_on
	FROM t03vari t03
	INNER JOIN m15pega m15 on m15.nip=t03.nip
	INNER JOIN m03dppt m03 on m03.m03_id=t03.m03_id 
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (t03.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t03vari 
select * from m03dppt 
select * from l_t03vari('','zzz')

*/
