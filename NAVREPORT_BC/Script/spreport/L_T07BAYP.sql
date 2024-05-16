-- Function: l_t07bayp(character varying, character varying, integer)

-- DROP FUNCTION l_t07bayp(character varying, character varying, integer);

CREATE OR REPLACE FUNCTION l_t07bayp(IN l_kodeFr character varying, IN l_kodeTo character varying, IN l_userid integer)
RETURNS TABLE(  OutNip character varying,
		OutNama character varying,  
		OutKdJnsp character varying,  
		OuTgPiut date,
		OutTgDoku date, 
		OutBayPokok numeric, 
		OutBayBunga numeric,
		OutComp character varying, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	t07.nip as OutNip, m15.nama as OutNama, t07.kdjnsp as OutKdJnsp,
		t07.tgpiut as OuTgPiut, t07.tgdoku as OutTgDoku, 
		t07.baypokok as OutBayPokok, t07.baybunga as OutBayBunga, t07.comp as OutComp,
		t07.version as OutVersion, t07.created_by as OutCreated_by,
		t07.created_on as OutCreated_on, t07.updated_by as OutUpdated_by, t07.updated_on as OutUpdated_on
	FROM t07bayp t07
	INNER JOIN m15pega m15 on m15.nip=t07.nip 
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (t07.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t07bayp 
select * from m03dppt 
select * from l_t07bayp('','zzz')

*/
