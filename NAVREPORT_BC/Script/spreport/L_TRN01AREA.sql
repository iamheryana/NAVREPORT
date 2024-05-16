
CREATE OR REPLACE FUNCTION public.l_trn01area (in l_kodefr varchar, in l_kodeto varchar, in outkodemaster varchar, in outketerangan varchar, in outsingkatan varchar, in outversion int4, in outcreated_by varchar, in outcreated_on timestamp, in outupdated_by varchar, in outupdated_on timestamp) RETURNS SETOF record AS
$BODY$ 
BEGIN
	RETURN QUERY
	SELECT	trn01.kodemaster as outkodemaster,
		trn01.keterangan as outketerangan, 
		trn01.singkatan as outsingkatan, 
		trn01.version as outversion,
		trn01.created_by as OutCreated_by,
		trn01.created_on as OutCreated_on,
		trn01.updated_by as OutUpdated_by,
		trn01.updated_on as OutUpdated_on
	FROM trn01area trn01
	WHERE (trn01.kodemaster BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
