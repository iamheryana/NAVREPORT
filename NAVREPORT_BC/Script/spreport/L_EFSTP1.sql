/**************************
	Listing Efektif Status
	Created by 		: Frans
	Created Date	: 18 Oktober 2007
***************************/
-- drop function 
CREATE or replace function L_EFSTP1	(l_mcNIPFr		varchar(10),
						 l_mcNIPTo		varchar(10),
						 l_mdTglFr		date,
						 l_mdTglTo		date, 
						 l_mcFGOL		varchar(4),
						 l_mcTGOL		varchar(4),
						 l_mcFCAB		varchar(4),
						 l_mcTCAB		varchar(4))
                    RETURNS table (
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
				Outupdated_on timestamp 
				) 
AS $$ 
BEGIN
RETURN QUERY SELECT M62.NIP as OutNip, M15.Nama as OutNama, M62.TglEfektif as OutTglEfektif, M62.Status as OutStatus, M62.StsPjk as OutStsPjk, M62.AnakTtgg as OutAnakTtgg, 
		M62.version as OutVersion, M62.created_by as OutCreated_by, M62.created_on as OutCreated_on, M62.updated_by as OutUpdated_By, M62.updated_on as OutUpdated_on
FROM M62EFST M62
INNER JOIN M15PEGA M15 
ON M15.NIP = M62.NIP
WHERE (M62.NIP BETWEEN l_mcNIPFr AND l_mcNIPTo) AND
	  (M62.TglEfektif BETWEEN l_mdTglFr AND l_mdTglTo) AND 
	  M15.KdCaba BETWEEN l_mcFCAB and l_mcTCAB AND 
	  M15.KdGlng BETWEEN l_mcFGOL and l_mcTGOL ; 
END ; 
$$ LANGUAGE plpgsql ;  
/*

Select L_EFSTP1(' ','ZZZZZZZZZZ','2009-01-01','2013-12-31',' ','ZZZZ',' ','ZZZZ') ; 

*/