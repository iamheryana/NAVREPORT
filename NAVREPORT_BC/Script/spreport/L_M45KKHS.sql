-- Function: l_m45kkhs(character varying, character varying, integer)

-- DROP FUNCTION l_m45kkhs(character varying, character varying, integer);

CREATE OR REPLACE FUNCTION l_m45kkhs(IN l_kodeFr character varying, IN l_kodeTo character varying, IN l_userid integer)
  RETURNS TABLE(OutNip character varying, 
		OutNama character varying, 
                OutKeterangan character varying,  
		        OutVersion integer,
		        OutCreated_by character varying,
		        OutCreated_on timestamp without time zone,
		        OutUpdated_by character varying,
		        OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	m45.nip as OutNip, m15.nama as OutNama, m45.keterangan as OutKeterangan,  
            m45.version as OutVersion, m45.created_by as OutCreated_by,
		    m45.created_on as OutCreated_on, m45.updated_by as OutUpdated_by, m45.updated_on as OutUpdated_on
	FROM m45kkhs m45
	INNER JOIN m15pega m15 on m15.nip=m45.nip
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (m45.nip BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

select * from l_m45kkhs('','zzz');
*/
