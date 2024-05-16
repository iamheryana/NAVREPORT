-- Function: l_t19pesa(character varying, character varying, integer)

-- DROP FUNCTION l_t19pesa(character varying, character varying, integer);

CREATE OR REPLACE FUNCTION l_t19pesa(IN l_kodeFr character varying, IN l_kodeTo character varying, IN l_userid integer)
RETURNS TABLE(  OutNip character varying,
		OutNama character varying,  
		OutTglKeluar date,
		OutTglPayr date,
		OutTglMasuk date,  
		OutNilaiPesangon numeric, 
		OutKdCurr character varying,
		OutFlagProses character varying, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	t19.nip as OutNip, m15.nama as OutNama,  
		t19.tglkeluar as OutTglKeluar, m15.tglpayr as OutTglPayr, m15.tglmasuk as OutTglMasuk,
		t19.nilaipesangon as OutNilaiPesangon, t19.kdcurr as OutKdCurr, t19.flagproses as OutFlagProses,
		t19.version as OutVersion, t19.created_by as OutCreated_by,
		t19.created_on as OutCreated_on, t19.updated_by as OutUpdated_by, t19.updated_on as OutUpdated_on
	FROM t19pesa t19
	INNER JOIN m15pega m15 on m15.nip=t19.nip 
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (t19.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from t19pesa  
select * from m03dppt 
select * from l_t19pesa('','zzz', 1)

*/
