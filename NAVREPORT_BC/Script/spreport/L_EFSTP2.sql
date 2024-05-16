
CREATE OR REPLACE FUNCTION public.l_efstp2 (in l_mcnipfr varchar, in l_mcnipto varchar, in l_mdtglfr date, in l_mdtglto date, in l_mcfgol varchar, in l_mctgol varchar, in l_mcfcab varchar, in l_mctcab varchar, in outnip varchar, in outnama varchar, in outtglefektif date, in outstatus varchar, in outstspjk varchar, in outanakttgg int4, in outversion int4, in outcreatedby varchar, in outcreated_on timestamp, in outupdatedby varchar, in outupdated_on timestamp) RETURNS SETOF record AS
$BODY$ 
BEGIN
CREATE TEMP TABLE IF NOT EXISTS TEMP (
	OutNIP varchar(10),
	OutNama varchar(25),
	OutTglEfektif date, 
	Outstatus varchar(1) ,
	Outstspjk varchar(2) ,
	OutAnakTtgg int, 
	Outversion int, 
	Outcreatedby varchar(15), 
	Outcreated_on timestamp,  
	Outupdatedby varchar(15), 
	Outupdated_on timestamp )
  ON COMMIT drop  ;

insert into temp 
SELECT M62.NIP as OutNip, M15.Nama as OutNama, M62.TglEfektif as OutTglEfektif, M62.Status as OutStatus, M62.StsPjk as OutStsPjk, M62.AnakTtgg as OutAnakTtgg, 
		M62.version as OutVersion, M62.created_by as OutCreated_by, M62.created_on as OutCreated_on, M62.updated_by as OutUpdated_By, M62.updated_on as OutUpdated_on
FROM M62EFST M62
INNER JOIN M15PEGA M15 
ON M15.NIP = M62.NIP
WHERE (M62.NIP BETWEEN l_mcNIPFr AND l_mcNIPTo) AND
	  (M62.TglEfektif BETWEEN l_mdTglFr AND l_mdTglTo) AND 
	  M15.KdCaba BETWEEN l_mcFCAB and l_mcTCAB AND 
	  M15.KdGlng BETWEEN l_mcFGOL and l_mcTGOL ; 

RETURN QUERY SELECT q1.OutNip, q1.OutNama, q1.OutTglEfektif, q1.OutStatus, q1.OutStsPjk, q1.OutAnakTtgg, 
		q1.OutVersion, q1.OutCreatedby, q1.OutCreated_on, q1.OutUpdatedBy, q1.OutUpdated_on 
		FROM TEMP q1 ;

END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
