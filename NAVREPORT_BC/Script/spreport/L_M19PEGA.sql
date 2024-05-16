-- Function: l_m15pega(character varying, character varying, integer)

-- DROP FUNCTION l_m15pega(character varying, character varying, integer);

CREATE OR REPLACE FUNCTION l_m15pega(IN l_nipFr character varying, IN l_nipTo character varying, IN l_userid integer)
  RETURNS TABLE(OutNip character varying, 
		OutNama character varying,
		OutNamaKecil character varying, 
		OutAlamat character varying, 
		OutKelurahan character varying, 
		OutKecamatan character varying, 
		OutKota character varying, 
		OutKodePos character varying, 
		OutTelepon character varying, 
		OutAgama text, 
		OutKewarganegaraan character varying, 
		OutSukuBangsa character varying, 
		OutNpwp character varying, 
		OutJnsKlmn text, 
		OutTglMasuk date, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT  m15.nip as OutNip, m15.nama as OutNama, m15.namakecil as OutNamaKecil, m15.alamat as OutAlamat, m15.kelurahan as OutKelurahan, 
		m15.kecamatan as OutKecamatan, m15.kota as OutKota, m15.kodepos as OutKodePos, m15.telpon as OutTelepon, 
		(case 	when m15.agama='1' then 'Islam'
			when m15.agama='2' then 'Kristen'
			when m15.agama='3' then 'Katolik'
			when m15.agama='4' then 'Budha'
			when m15.agama='5' then 'Hindu'
			when m15.agama='6' then 'Lainnya' end) as OutAgama, 
		m15.kewarganegaraan as OutKewarganegaraan, m15.sukubangsa as OutSukuBangsa, m15.npwp as OutNpwp, 
		(case when m15.jnsklmn='L' then 'Laki-laki' else 'Perempuan' end) as OutJnsKlmn, m15.tglmasuk as OutTglMasuk,
		m15.version as OutVersion, m15.created_by as OutCreated_by, m15.created_on as OutCreated_on, m15.updated_by as OutUpdated_by,
		m15.updated_on as OutUpdated_on
	FROM  m15pega m15
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (m15.nip BETWEEN l_nipFr AND l_nipTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

DROP FUNCTION l_m15pega(character varying, character varying);

select * from m15pega 

SELECT * FROM l_m15pega('.','ZZZ')

*/
