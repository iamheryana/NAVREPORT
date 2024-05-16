CREATE OR REPLACE FUNCTION l_m08hcab(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKdCabang character varying,
		OutNama character varying, 
		OutSingkatan character varying,
		OutKacaba character varying,
		OutAlamat character varying,
		OutKota character varying,
		OutNpwp character varying,
		OutNpp character varying,
        OutSbacc character varying,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT m08.kdcaba as OutKdCabang, m08.nmcaba as OutNama, m08.skcaba as OutSingkatan, 
		   m08.kacaba as OutKacaba, m08.alcaba as OutAlamat, m08.kota as OutKota, 
		   m08.npwp as OutNpwp, m08.npp as OutNpp, m08.noaccount as OutSbAcc,
           m08.version as OutVersion,
		   m08.created_by as OutCreated_by, m08.created_on as OutCreated_on, m08.updated_by as OutUpdated_by,
		   m08.updated_on as OutUpdated_on
	FROM m08hcab m08
	WHERE (m08.kdcaba BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

select * from l_m08hcab('.','ZZZ')

*/
