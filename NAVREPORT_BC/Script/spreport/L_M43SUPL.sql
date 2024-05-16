CREATE OR REPLACE FUNCTION l_m43supl(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character varying, 
		OutKeterangan character varying,
		OutAlamat character varying, 
		OUtKota character varying, 
		OutKodePos character varying, 
		OutNoTelpon character varying, 
		OutTipeSupplier character varying, 
		OutVersion integer,
		OutCreated_by character varying,
		OutCreated_on timestamp without time zone,
		OutUpdated_by character varying,
		OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT  m43.kode as OutKode, m43.keterangan as OutKeterangan, m43.alamat as OutAlamat, 
        m43.kota as OUtKota, m43.kodepos as OutKodePos, m43.notelpon as OutNoTelpon, m43.tipesupplier as OutTipeSupplier,
        m43.version as OutVersion, m43.created_by as OutCreated_by, m43.created_on as OutCreated_on, m43.updated_by as OutUpdated_by,
		m43.updated_on as OutUpdated_on
	FROM  m43supl m43
	WHERE (m43.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*

DROP FUNCTION l_m43jnsa(character varying, character varying);

select * from m43jnsa 

SELECT * FROM l_m43jnsa('.','ZZZ')

*/
