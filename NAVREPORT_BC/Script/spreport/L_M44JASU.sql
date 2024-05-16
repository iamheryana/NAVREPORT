-- Function: l_m44jasu(character varying, character varying)

-- DROP FUNCTION l_m44jasu(character varying, character varying);

CREATE OR REPLACE FUNCTION l_m44jasu(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying, 
                OutKeterangan character varying,  
                OutPerusahaan character varying,  
                OutJenisAsuransi text,  
                OutPremiPegawai numeric,
                OutPremiPerusahaan numeric,
                OutFlgDppt1 character varying,  
                OutKdDppt1 character varying,  
                OutFlgDppt2 character varying,  
                OutKdDppt2 character varying,  
                OutNmDppt character varying,
                OutJnsDppt character varying,
		        OutVersion integer,
		        OutCreated_by character varying,
		        OutCreated_on timestamp without time zone,
		        OutUpdated_by character varying,
		        OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	m44.kode as OutKode, m44.keterangan as OutKeterangan, m44.perusahaan as OutPerusahaan,
            (case when m44.jenisasuransi='1' then 'Pensiun' else 'Non Pensiun' end) as OutJenisAsuransi, 
	    m44.premipegawai as OutPremiPegawai, m44.premiperusahaan as OutPremiPerusahaan,
            m44.flgdppt1 as OutFlgDppt1, m44.kddppt1 as OutKdDppt1, m44.flgdppt2 as OutFlgDppt2, 
            m44.kddppt2 as OutKdDppt2, m03.nmdppt as OutNmDppt, m03.jndppt as OutJnsDppt,
            m44.version as OutVersion, m44.created_by as OutCreated_by,
		    m44.created_on as OutCreated_on, m44.updated_by as OutUpdated_by, m44.updated_on as OutUpdated_on
	FROM m44jasu m44
    inner join m03dppt m03 on m03.m03_id=m44.m03_id
	WHERE (m44.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*
select * from l_m44jasu('','zzz')
*/
