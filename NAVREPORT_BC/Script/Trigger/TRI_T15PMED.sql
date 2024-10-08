DROP TRIGGER IF EXISTS TI_T15PMED ON T15PMED CASCADE  ; 
DROP TRIGGER IF EXISTS TD_T15PMED ON T15PMED CASCADE  ; 
DROP TRIGGER IF EXISTS TU_T15PMED ON T15PMED CASCADE  ; 
DROP FUNCTION IF EXISTS  FTA_T15PMED() CASCADE; 


CREATE OR REPLACE FUNCTION FTA_T15PMED()
  RETURNS TRIGGER AS
$$

    BEGIN
	   
	IF (TG_OP = 'INSERT') THEN
		UPDATE T14JTHM 
		SET Pemakaian = COALESCE(T14.Pemakaian,0)+ NEW.Pemakaian 
		FROM T14JTHM T14
		WHERE T14.NIP=NEW.NIP AND T14.Tahun=NEW.TAHUN;
		--
		RETURN NEW;
	ELSIF (TG_OP = 'UPDATE') THEN
		UPDATE T14JTHM 
		SET Pemakaian = COALESCE(T14.Pemakaian,0)+ OLD.Pemakaian 
		FROM T14JTHM T14
		WHERE T14.NIP=OLD.NIP AND T14.Tahun=OLD.TAHUN;
		--	
		UPDATE T14JTHM 
		SET Pemakaian = COALESCE(T14.Pemakaian,0)+ NEW.Pemakaian 
		FROM T14JTHM T14
		WHERE T14.NIP=NEW.NIP AND T14.Tahun=NEW.TAHUN;		
		--
		RETURN NEW;
	ELSIF (TG_OP = 'DELETE') THEN
		UPDATE T14JTHM 
		SET Pemakaian = COALESCE(T14.Pemakaian,0)+ OLD.Pemakaian 
		FROM T14JTHM T14
		WHERE T14.NIP=OLD.NIP AND T14.Tahun=OLD.TAHUN;
		--
		RETURN OLD;
	END IF; 
    END;	
$$ LANGUAGE plpgsql;

-- Trigger Insert
CREATE TRIGGER TI_T15PMED
AFTER INSERT ON T15PMED
    FOR EACH ROW EXECUTE PROCEDURE FTA_T15PMED();

-- Trigger Update
CREATE TRIGGER TU_T15PMED
AFTER UPDATE ON T15PMED
    FOR EACH ROW EXECUTE PROCEDURE FTA_T15PMED();    

-- Trigger Delete
CREATE TRIGGER TD_T15PMED
AFTER DELETE ON T15PMED
    FOR EACH ROW EXECUTE PROCEDURE FTA_T15PMED();    		
-------------------------------------------------------------------	
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TI_T15PMED' AND type = 'TR')
-- -- DROP TRIGGER TI_T15PMED
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TD_T15PMED' AND type = 'TR')
-- -- DROP TRIGGER TD_T15PMED
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TU_T15PMED' AND type = 'TR')
-- -- DROP TRIGGER TU_T15PMED
-- -- GO
-- -- 
-- -- 
-- -- /*****
-- -- INSERT
-- -- ******/
-- -- CREATE TRIGGER TI_T15PMED
-- -- ON T15PMED WITH ENCRYPTION FOR INSERT 
-- -- AS
-- -- --
-- -- -- Update Data ke T14JTHM 
-- -- UPDATE T14JTHM 
-- -- SET Pemakaian = ISNULL(T14.Pemakaian,0)+ I.Pemakaian 
-- -- FROM INSERTED I,T14JTHM T14
-- -- WHERE T14.NIP=I.NIP AND T14.Tahun=I.TAHUN
-- -- --
-- -- GO
-- -- 
-- -- /*****
-- -- DELETE
-- -- ******/
-- -- CREATE TRIGGER TD_T15PMED
-- -- ON T15PMED WITH ENCRYPTION FOR DELETE
-- -- AS
-- -- --
-- -- -- Update Data ke T14JTHM 
-- -- UPDATE T14JTHM 
-- -- SET Pemakaian = ISNULL(T14.Pemakaian,0)- I.Pemakaian 
-- -- FROM DELETED I,T14JTHM T14
-- -- WHERE T14.NIP=I.NIP AND T14.Tahun=I.TAHUN
-- -- --
-- -- GO
-- -- 
-- -- /*****
-- -- UPDATE
-- -- ******/
-- -- CREATE TRIGGER TU_T15PMED
-- -- ON T15PMED WITH ENCRYPTION FOR UPDATE
-- -- AS
-- -- --
-- -- -- Update Data ke T14JTHM 
-- -- UPDATE T14JTHM 
-- -- SET Pemakaian = ISNULL(T14.Pemakaian,0)- I.Pemakaian 
-- -- FROM DELETED I,T14JTHM T14
-- -- WHERE T14.NIP=I.NIP AND T14.Tahun=I.TAHUN
-- -- --
-- -- -- Insert Ulang
-- -- -- Update Data ke T14JTHM 
-- -- UPDATE T14JTHM 
-- -- SET Pemakaian = ISNULL(T14.Pemakaian,0)+ I.Pemakaian 
-- -- FROM INSERTED I,T14JTHM T14
-- -- WHERE T14.NIP=I.NIP AND T14.Tahun=I.TAHUN
-- -- --
-- -- GO
