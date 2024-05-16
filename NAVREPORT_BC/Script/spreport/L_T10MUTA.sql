-- DROP FUNCTION l_t10muta(character varying, character varying, integer)

CREATE OR REPLACE FUNCTION l_t10muta(IN l_kodefr character varying, IN l_kodeto character varying, IN l_userid integer)
RETURNS TABLE( OutTglEff date, 
               OutNip character varying,
         	   OutNama character varying, 
               OutNoSK  character varying, 
               OutKdMutr character varying,  
               OutKdKlas character varying,  
               OutKdCaba character varying,  
               OutKdUusa character varying,  
               OutKdUker character varying,  
               OutKdGlng character varying,  
               OutKdJaba character varying,  
               OutKdArea character varying,  
               OutKdKjab character varying, 
               OutPrdPelks date,  
               OutGajiPokok numeric, 
               OutKdCurr character varying, 
               OutPenilaian character varying,  
               OutMasaKerja integer, 
               OutPrn integer, 
               OutKeterangan2 character varying,  
               OutKeterangan3 character varying,  
               OutKeterangan4 character varying,  
               OutKeterangan5 character varying, 
		       OutKeterangan character varying,
		       OutVersion integer,
		       OutCreated_by character varying,
		       OutCreated_on timestamp without time zone,
		       OutUpdated_by character varying,
		       OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT t10.tgleff as OutTglEff, t10.nip as OutNip, m15.nama as OutNama, t10.nosk as OutNoSK,
        t10.kdmutr as OutKdMutr, t10.kdklas as OutKdKlas, t10.kdcaba as OutKdCaba, t10.kduusa as OutKdUusa, 
        t10.kduker as OutKdUker, t10.kdglng as OutKdGlng, t10.kdjaba as OutKdJaba, t10.kdarea as OutKdArea, 
        t10.kdkjab as OutKdKjab, t10.prdpelks as OutPrdPelks, t10.gajipokok as OutGajiPokok, t10.kdcurr as OutKdCurr, 
        t10.penilaian as OutPenilaian, t10.masakerja as OutMasaKerja, t10.prn as OutPrn, t10.keterangan2 as OutKeterangan2,
        t10.keterangan3 as OutKeterangan3, keterangan4 as OutKeterangan4, t10.keterangan5 as OutKeterangan5,
		t10.keterangan as OutKeterangan, t10.version as OutVersion, t10.created_by as OutCreated_by,
		t10.created_on as OutCreated_on, t10.updated_by as OutUpdated_by, t10.updated_on as OutUpdated_on
	FROM t10muta t10
	INNER JOIN m15pega m15 on m15.nip=t10.nip
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
		FROM fn_SECLOGIN(l_userid)) VSL ON m15.KdGlng=VSL.Gol_Kode AND m15.KdCaba=VSL.Cab_Kode
	WHERE (t10.nip BETWEEN l_kodefr AND l_kodeto); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
