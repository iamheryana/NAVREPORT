
--DROP FUNCTION l_m03dppt(character varying,character varying)

CREATE OR REPLACE FUNCTION l_m03dppt(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutFlgdppt text,
		OutKode character varying,
		OutKeterangan character varying, 
		OutSingkatan character varying, 
		OutJnsdppt text,
		OutPersen numeric,
		OutNilai numeric,
		OutKdcurr character varying,
		OutPendTetap text,
		OutKomponenLembur text,
		OutSPT character varying,
		OutSistemPajak text,
		OutNoAcc character varying,
		OutPayrollKhusus text,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	(case when m03.flgdppt='D' then 'Pendapatan' else 'Potongan' end) as OutFlgdppt,
		m03.kddppt as OutKode,
		m03.nmdppt as OutKeterangan, 
		m03.skdppt as OutSingkatan, 
		(case when m03.jndppt='H' then 'Harian' else 'Bulanan' end) as OutJnsdppt,
		m03.persen as OutPersen,
		m03.nilai as OutNilai,
		m03.kdcurr as OutKdcurr,
		(case when m03.status='1' then 'Ya' else 'Tidak' end) as OutPendTetap,
		(case when m03.nolayar=1 then 'Ya' else 'Tidak' end) as OutKomponenLembur,
		m03.kolom as OutSPT,
		(case when m03.pajak='D' then 'Default Pegawai'
		      when m03.pajak='N' then 'Netto'
		      when m03.pajak='G' then 'Gross'
		      when m03.pajak='R' then 'Netto (Gross-Up)'
		 end) as OutSistemPajak,
		m03.noacc as OutNoAcc,
		(case when m03.flag='1' then 'Ya' else 'Tidak' end) as OutPayrollKhusus,
		m03.version as OutVersion,
		m03.created_by as OutCreated_by,
		m03.created_on as OutCreated_on,
		m03.updated_by as OutUpdated_by,
		m03.updated_on as OutUpdated_on
	FROM m03dppt m03
	WHERE (m03.kddppt BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from m03dppt
*/

