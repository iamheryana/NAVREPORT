-- Function: l_m64advh(character varying, character varying, character varying, character varying);

-- DROP FUNCTION l_m64advh(character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION l_m64advh(IN l_level1Fr character varying, IN l_level1To character varying, IN l_level2Fr character varying, IN l_level2To character varying)
  RETURNS TABLE(Outlevel1 text,
		OutKode1 character varying, 
		Outlevel2 text, 
		OutKode2 character varying, 
		OutFlgDppt text,
		OutKdDppt character varying, 
		OutNMDppt character varying,
		OutPersen numeric,
		OutNilai numeric,
		OutKdCurr character varying,
		OutTrans text,
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	case when m64.level1='1' then 'Area' 
		     when m64.level1='2' then 'Cabang' 
		     when m64.level1='3' then 'Unit Usaha' 
		     when m64.level1='4' then 'Unit Kerja' 
		     when m64.level1='5' then 'Golongan' 
		     when m64.level1='6' then 'Kelompok Jabatan' 
		     when m64.level1='7' then 'Jabatan' end as Outlevel1, 	
		m64.kode1 as OutKode1, 
		case when m64.level2='1' then 'Area' 
		     when m64.level2='2' then 'Cabang' 
		     when m64.level2='3' then 'Unit Usaha' 
		     when m64.level2='4' then 'Unit Kerja' 
		     when m64.level2='5' then 'Golongan' 
		     when m64.level2='6' then 'Kelompok Jabatan' 
		     when m64.level2='7' then 'Jabatan' 
		     when m64.level2='8' then 'None' end as Outlevel2, 			
		m64.kode2 as OutKode2, 
		case when m65.flgdppt='D' then 'Pendapatan' else 'Potongan' end as OutFlgDppt,
		m65.kddppt as OutKdDppt, m03.nmdppt as OutNMDppt,
		m65.persen as OutPersen, m65.nilai as OutNilai, m65.kdcurr as OutKdCurr, 
		case when m65.trans='Y' then 'Ya' else 'Tidak' end as OutTrans,
		m64.version as OutVersion, m64.created_by as OutCreated_by,
		m64.created_on as OutCreated_on, m64.updated_by as OutUpdated_by, m64.updated_on as OutUpdated_on
	FROM m64advh m64
	INNER JOIN m65advd m65 on m65.m64_id=m64.m64_id
	INNER JOIN m03dppt m03 on m03.m03_id=m65.m03_id
	WHERE (m64.level1 BETWEEN l_level1Fr AND l_level1To) and (m64.level2 BETWEEN l_level2Fr AND l_level2To); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


/*
select * from m64advh 
select * from m65advd 

select * from l_m64advh('','ZZ','','ZZ');


*/
