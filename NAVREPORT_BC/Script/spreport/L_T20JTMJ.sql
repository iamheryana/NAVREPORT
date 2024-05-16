CREATE OR REPLACE FUNCTION l_t20jtmj(IN l_kodeFr numeric, IN l_kodeTo numeric)
RETURNS TABLE( OutTahun numeric, 
               OutJenisMedical character varying, 
               OutKelJab character varying,
               OutPlafon numeric, 
               OutPlafonKel numeric, 
               OutKeterangan character varying, 
               OutFlagKasus integer,
		       OutVersion integer,
		       OutCreated_by character varying,
		       OutCreated_on timestamp without time zone,
		       OutUpdated_by character varying,
		       OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT t20.tahun as OutTahun, t20.jenismedical as OutJenisMedical, t20.keljab as OutKelJab,
        t20.plafon as OutPlafon, t20.plafonkel as OutPlafonKel, t20.keterangan as OutKeterangan, t20.flagkasus as OutFlagKasus, 
		t20.version as OutVersion, t20.created_by as OutCreated_by,
		t20.created_on as OutCreated_on, t20.updated_by as OutUpdated_by, t20.updated_on as OutUpdated_on
	FROM t20jtmj t20
	WHERE (t20.tahun BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
