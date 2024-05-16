DROP TRIGGER IF EXISTS TI_T11HTRN ON T11HTRN CASCADE  ; 
DROP TRIGGER IF EXISTS TD_T11HTRN ON T11HTRN CASCADE  ; 
DROP TRIGGER IF EXISTS TU_T11HTRN ON T11HTRN CASCADE  ; 
DROP FUNCTION IF EXISTS  FTA_T11HTRN() CASCADE; 


CREATE OR REPLACE FUNCTION FTA_T11HTRN()
  RETURNS TRIGGER AS
$$

    BEGIN
	   
	IF (TG_OP = 'UPDATE') THEN
		UPDATE S07TRNG
		SET KdLmbD	=NEW.KdLmbD,
		    SkLmbD	=M27.Singkatan,
		    KdLksD	=NEW.KdLksD,
		    SkLksD	=M28.Singkatan,
		    BulanTrng	=NEW.BulanTrng,
		    HariTrng	=NEW.HariTrng,
		    Biaya	=NEW.Biaya
		---
		FROM S07TRNG S07
		---
		INNER JOIN M27LMBD M27
		ON M27.Kode = NEW.KdLmbD
		--
		INNER JOIN M28LKSD M28
		ON M28.Kode = NEW.KdLksD;	
		--
		RETURN NEW;
	END IF; 
    END;	
$$ LANGUAGE plpgsql;

-- Trigger Insert
CREATE TRIGGER TI_T11HTRN
AFTER INSERT ON T11HTRN
    FOR EACH ROW EXECUTE PROCEDURE FTA_T11HTRN();

-- Trigger Update
CREATE TRIGGER TU_T11HTRN
AFTER UPDATE ON T11HTRN
    FOR EACH ROW EXECUTE PROCEDURE FTA_T11HTRN();    

-- Trigger Delete
CREATE TRIGGER TD_T11HTRN
AFTER DELETE ON T11HTRN
    FOR EACH ROW EXECUTE PROCEDURE FTA_T11HTRN();    	

---------------
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TI_T11HTRN' AND type = 'TR')
-- -- DROP TRIGGER TI_T11HTRN
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TD_T11HTRN' AND type = 'TR')
-- -- DROP TRIGGER TD_T11HTRN
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TU_T11HTRN' AND type = 'TR')
-- -- DROP TRIGGER TU_T11HTRN
-- -- GO
-- -- --
-- -- /*****
-- -- UPDATE
-- -- ******/
-- -- CREATE TRIGGER TU_T11HTRN
-- -- ON T11HTRN WITH ENCRYPTION FOR UPDATE
-- -- AS
-- -- --
-- -- UPDATE S07TRNG
-- -- SET KdLmbD		=INS.KdLmbD,
-- -- 	SkLmbD		=M27.Singkatan,
-- -- 	KdLksD		=INS.KdLksD,
-- -- 	SkLksD		=M28.Singkatan,
-- -- 	BulanTrng	=INS.BulanTrng,
-- -- 	HariTrng	=INS.HariTrng,
-- -- 	Biaya		=INS.Biaya
-- -- ---
-- -- FROM S07TRNG S07
-- -- ---
-- -- INNER JOIN INSERTED INS
-- -- ON INS.TgDocu=S07.Tanggal AND INS.KdJnsD=S07.KdJnsd AND INS.KdJrsn=S07.KdJrsn
-- -- --- 
-- -- INNER JOIN M27LMBD M27
-- -- ON M27.Kode = INS.KdLmbD
-- -- --
-- -- INNER JOIN M28LKSD M28
-- -- ON M28.Kode = INS.KdLksD
-- -- ---
-- -- GO
