/*********************************************
 Program Name : Tri_M23KLRG
 Create By    : PEGGY 16 01 2006
 Updated By   : 
 Call From    : 
 sub Program  : 
 Description  : - Update T21PMEP KALO GANTI NAMA KELUARGA 

**********************************************/
DROP TRIGGER IF EXISTS TU_M23KLRG ON M23KLRG CASCADE  ; 
DROP FUNCTION IF EXISTS  FTA_M23KLRG () CASCADE; 
------------------------------
CREATE OR REPLACE FUNCTION FTA_M23KLRG() RETURNS TRIGGER AS $$
    BEGIN

	UPDATE T21PMEP 
	SET NamaKel = NEW.Nama 
	WHERE NIP = NEW.NIP AND NAMAKEL = OLD.Nama; 
	IF NOT FOUND THEN RETURN NULL; END IF;

	RETURN NEW;
    END;	
$$ LANGUAGE plpgsql;
---------------------
-- Trigger Update
CREATE TRIGGER TU_M23KLRG
AFTER UPDATE ON M23KLRG
    FOR EACH ROW EXECUTE PROCEDURE FTA_M23KLRG();
-- End Trigger 
