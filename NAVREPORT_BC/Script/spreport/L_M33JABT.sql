CREATE OR REPLACE FUNCTION l_m33jabt(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying,
		OutKeterangan text, 
		OutNama character varying, 
		OutJabatan character varying, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 

	SELECT	m33.kode as OutKode,

		(case 	when m33.keterangan is null or LTRIM(m33.keterangan)='' then '00.000.000.0-000.000' 
			else substring(m33.keterangan,1,2)||'.'||substring(m33.keterangan,3,3)||'.'||substring(m33.keterangan,6,3)
			     ||'.'||substring(m33.keterangan,9,1)||'-'||substring(m33.keterangan,10,3)||'.'||substring(m33.keterangan,13,3)
			end) as OutKeterangan, 
		m33.nama as OutNama, 
		m33.jabatan as OutJabatan, 
		m33.version as OutVersion,
		m33.created_by as OutCreated_by,
		m33.created_on as OutCreated_on,
		m33.updated_by as OutUpdated_by,
		m33.updated_on as OutUpdated_on
	FROM m33jabt m33
	WHERE (m33.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*
select * from m33jabt where kode='01' 

update m33jabt set keterangan = '' ""000000000000000"" "000000000000000" "000000000000000"
where kode='V'
*/
-- 