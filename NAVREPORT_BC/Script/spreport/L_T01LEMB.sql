-- Function: l_t01lemb(date, date, character varying, character varying, integer)

-- DROP FUNCTION l_t01lemb(date, date, character varying, character varying, integer);

CREATE OR REPLACE FUNCTION l_t01lemb(IN l_tglFr date, IN l_tglTo date, IN l_kodeFr character varying, IN l_kodeTo character varying, IN l_userid integer)
RETURNS TABLE(  OutTgDocu date,
		OutNip character varying,
		OutNama character varying, 
		OutRefDok character varying, 
		OutFlgLemb text,
		OutFlag text, 
		OutJamLemb numeric,
		OutJmlUMkn numeric,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	t01.tgdocu as OutTgDocu,
		t01.nip as OutNip, m15.nama as OutNama, 
		t01.refdok as OutRefDok,
		case when t01.flglemb='L' then 'Lembur'
		     when t01.flglemb='K' then 'Kerja'
		     when t01.flglemb='I' then 'Istimewa' end as OutFlgLemb,
		case when t01.flag=0 then 'Tidak' else 'Ya' end as OutFlag,
		t01.jamlemb as OutJamLemb, t01.jmlumkn as OutJmlUMkn,
		t01.version as OutVersion, t01.created_by as OutCreated_by,
		t01.created_on as OutCreated_on, t01.updated_by as OutUpdated_by, t01.updated_on as OutUpdated_on
	FROM t01lemb t01
	INNER JOIN m15pega m15 on m15.nip=t01.nip
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (t01.tgdocu BETWEEN l_tglFr AND l_tglTo) and (t01.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t01lemb  
select * from l_t01lemb('2010-01-01','2013-01-13','','zzz')

*/
