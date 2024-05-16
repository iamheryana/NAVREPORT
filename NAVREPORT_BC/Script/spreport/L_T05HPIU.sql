-- Function: l_t05hpiu(character varying, character varying, integer)

-- DROP FUNCTION l_t05hpiu(character varying, character varying, integer);

CREATE OR REPLACE FUNCTION l_t05hpiu(IN l_kodeFr character varying, IN l_kodeTo character varying, IN l_userid integer)
RETURNS TABLE(  OutNip character varying,
		OutNama character varying, 
		OutKdJnsp character varying, 
		OutTgDocu date, 
		OutNoRef character varying,  
		OutPiutang numeric,
		OutBunga numeric, 
		OutPrdAngs date, 
		OutJmlAngs numeric,
		OutJmlBunga numeric,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	t05.nip as OutNip, m15.nama as OutNama, 
		t05.kdjnsp as OutKdJnsp, t05.tgdoku as OutTgDoku,
		t05.noref as OutNoRef, t05.piutang as OutPiutang, t05.bunga as OutBunga,
		t06.prdangs as OutPrdAngs, t06.jmlangs as OutJmlAngs, t06.jmlbunga as OutJmlBunga,
		t05.version as OutVersion, t05.created_by as OutCreated_by,
		t05.created_on as OutCreated_on, t05.updated_by as OutUpdated_by, t05.updated_on as OutUpdated_on
	FROM t05hpiu t05
	INNER JOIN m15pega m15 on m15.nip=t05.nip
	INNER JOIN t06dpiu t06 on t06.t05_id=t05.t05_id
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (t05.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t05hpiu 
select * from t06dpiu 
select * from l_t05hpiu('','zzz')

*/
