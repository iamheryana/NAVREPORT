DROP TRIGGER IF EXISTS TI_T07BAYP ON T07BAYP CASCADE  ; 
DROP TRIGGER IF EXISTS TD_T07BAYP ON T07BAYP CASCADE  ; 
DROP TRIGGER IF EXISTS TU_T07BAYP ON T07BAYP CASCADE  ; 
DROP FUNCTION IF EXISTS  FTA_T07BAYP() CASCADE; 


CREATE OR REPLACE FUNCTION FTA_T07BAYP()
  RETURNS TRIGGER AS
$$
    BEGIN
	IF (TG_OP = 'INSERT') THEN
		-- Update T05HPIU
		IF NEW.Comp='L' THEN 
		     UPDATE T05HPIU
		     SET BayPokLgs=BayPokLgs+NEW.BayPokok,
			 BayBngLgs=BayBngLgs+NEW.BayBunga
		      WHERE NIP=NEW.NIP AND KdJnsP=NEW.kdJnSp AND TgDoku=NEW.TgPiut ; 
		ELSIF NEW.Comp='P' THEN 
		     ---
		     UPDATE T05HPIU T05U
		     SET BayPokPay = DTL.BayPokok,
			 BayBngPay = DTL.BayBunga
		     FROM T05HPIU T05
		     INNER JOIN
		     (--DTL     
		      SELECT NIP,KdJnsP,TgPiut,BayPokok=SUM(BayPokok),BayBunga=SUM(BayBunga)
		      FROM T07BAYP
		      WHERE Comp='P' AND NIP=NEW.NIP
		      GROUP BY NIP,KdJnsP,TgPiut
		     )DTL  
		     ON DTL.NIP=T05.NIP AND DTL.KdJnsP=T05.KdJnsP AND DTL.TgPiut=T05.TgDoku 
		     WHERE T05U.NIP=T05.NIP AND T05U.KdJnsP=T05.KdJnsP AND T05U.TgDoku=T05.TgDoku ;

		/*
		     UPDATE T05HPIU
		     SET BayPokPay = BayPokPay+NEW.BayPokok,
			 BayBngPay = BayBngPay+NEW.BayBunga
		     WHERE NIP=NEW.NIP AND KdJnsP=NEW.kdJnSp AND TgDoku=NEW.TgPiut
		*/
		END IF;    
		----
		RETURN NEW;
		
	ELSIF (TG_OP = 'UPDATE') THEN	
		-- CABUT OLD 
		-- Update T05HPIU
		IF OLD.Comp='L' THEN 
		     UPDATE T05HPIU
		     SET BayPokLgs=BayPokLgs-OLD.BayPokok,
			 BayBngLgs=BayBngLgs-OLD.BayBunga		
		     WHERE NIP=OLD.NIP AND KdJnsP=OLD.kdJnSp AND TgDoku=OLD.TgPiut;
		ELSIF OLD.Comp='P' THEN 
		     UPDATE T05HPIU
		     SET BayPokPay = BayPokPay-OLD.BayPokok,
			 BayBngPay = BayBngPay-OLD.BayBunga
		     WHERE NIP=OLD.NIP AND KdJnsP=OLD.kdJnSp AND TgDoku=OLD.TgPiut;
		END IF;    
		-- PASANG NEW 
		IF NEW.Comp='L' THEN 
		     UPDATE T05HPIU
		     SET BayPokLgs=BayPokLgs+NEW.BayPokok,
			 BayBngLgs=BayBngLgs+NEW.BayBunga
		      WHERE NIP=NEW.NIP AND KdJnsP=NEW.kdJnSp AND TgDoku=NEW.TgPiut;
		ELSIF NEW.Comp='P' THEN 
		     UPDATE T05HPIU
		     SET BayPokPay = BayPokPay+NEW.BayPokok,
			 BayBngPay = BayBngPay+NEW.BayBunga
		     WHERE NIP=NEW.NIP AND KdJnsP=NEW.kdJnSp AND TgDoku=NEW.TgPiut;
		END IF; 		
		--
		RETURN NEW;
	ELSIF (TG_OP = 'DELETE') THEN	
		-- Update T05HPIU
		IF OLD.Comp='L' THEN 
		     UPDATE T05HPIU
		     SET BayPokLgs=BayPokLgs-OLD.BayPokok,
			 BayBngLgs=BayBngLgs-OLD.BayBunga		
		     WHERE NIP=OLD.NIP AND KdJnsP=OLD.kdJnSp AND TgDoku=OLD.TgPiut;
		ELSIF OLD.Comp='P' THEN 
		     UPDATE T05HPIU
		     SET BayPokPay = BayPokPay-OLD.BayPokok,
			 BayBngPay = BayBngPay-OLD.BayBunga
		     WHERE NIP=OLD.NIP AND KdJnsP=OLD.kdJnSp AND TgDoku=OLD.TgPiut;
		--*
		END IF; 	
		RETURN OLD;
	END IF; 
	
    END;	
$$ LANGUAGE plpgsql;

-- Trigger Insert
CREATE TRIGGER TI_T07BAYP
AFTER INSERT ON T07BAYP
    FOR EACH ROW EXECUTE PROCEDURE FTA_T07BAYP();

-- Trigger Update
CREATE TRIGGER TU_T07BAYP
AFTER UPDATE ON T07BAYP
    FOR EACH ROW EXECUTE PROCEDURE FTA_T07BAYP();    

-- Trigger Delete
CREATE TRIGGER TD_T07BAYP
AFTER DELETE ON T07BAYP
    FOR EACH ROW EXECUTE PROCEDURE FTA_T07BAYP();    


-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TI_T07BAYP' AND type = 'TR')
-- -- DROP TRIGGER TI_T07BAYP
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TD_T07BAYP' AND type = 'TR')
-- -- DROP TRIGGER TD_T07BAYP
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TU_T07BAYP' AND type = 'TR')
-- -- DROP TRIGGER TU_T07BAYP
-- -- GO
-- -- 
-- -- 
-- -- /*****
-- -- INSERT
-- -- ******/
-- -- CREATE TRIGGER TI_T07BAYP
-- -- ON T07BAYP WITH ENCRYPTION FOR INSERT 
-- -- AS
-- -- --
-- -- DECLARE @NIP		CHAR(10),
-- -- 	@kdJnSp		CHAR(4),
-- -- 	@TgPiut 	DATETIME,
-- -- 	@BayPokok  	DECIMAL(15,2),
-- -- 	@BayBunga  	DECIMAL(15,2),
-- -- 	@Comp		CHAR(1)
-- -- --
-- -- SELECT  @NIP=NIP,
-- -- 	@kdJnSp=kdJnSp,
-- -- 	@TgPiut=TgPiut,
-- -- 	@BayPokok=BayPokok,
-- -- 	@BayBunga=BayBunga,
-- -- 	@Comp	=Comp
-- -- FROM INSERTED
-- -- --
-- -- -- Update T05HPIU
-- -- IF @Comp='L'
-- --    BEGIN 
-- --      UPDATE T05HPIU
-- --      SET BayPokLgs=BayPokLgs+@BayPokok,
-- --          BayBngLgs=BayBngLgs+@BayBunga
-- --       WHERE NIP=@NIP AND KdJnsP=@kdJnSp AND TgDoku=@TgPiut
-- --    END
-- -- ELSE IF @Comp='P'
-- --    BEGIN 
-- --      ---
-- --      UPDATE T05HPIU
-- --      SET BayPokPay = DTL.BayPokok,
-- --          BayBngPay = DTL.BayBunga
-- --      FROM 
-- --      T05HPIU T05
-- --      INNER JOIN
-- --      (--DTL     
-- --       SELECT NIP,KdJnsP,TgPiut,BayPokok=SUM(BayPokok),BayBunga=SUM(BayBunga)
-- --       FROM T07BAYP
-- --       WHERE Comp='P' AND NIP=@NIP
-- --       GROUP BY NIP,KdJnsP,TgPiut
-- --      )DTL  
-- --      ON DTL.NIP=T05.NIP AND DTL.KdJnsP=T05.KdJnsP AND DTL.TgPiut=T05.TgDoku
-- -- 
-- -- /*
-- --      UPDATE T05HPIU
-- --      SET BayPokPay = BayPokPay+@BayPokok,
-- --          BayBngPay = BayBngPay+@BayBunga
-- --      WHERE NIP=@NIP AND KdJnsP=@kdJnSp AND TgDoku=@TgPiut
-- -- */
-- --    END
-- -- --*
-- -- --
-- -- GO
-- -- 
-- -- /*****
-- -- DELETE
-- -- ******/
-- -- CREATE TRIGGER TD_T07BAYP
-- -- ON T07BAYP WITH ENCRYPTION FOR DELETE
-- -- AS
-- -- --
-- -- DECLARE @NIP		CHAR(10),
-- -- 	@kdJnSp		CHAR(4),
-- -- 	@TgPiut 	DATETIME,
-- -- 	@BayPokok  	DECIMAL(15,2),
-- -- 	@BayBunga  	DECIMAL(15,2),
-- -- 	@Comp		CHAR(1)
-- -- 
-- -- --
-- -- SELECT  @NIP=NIP,
-- -- 	@kdJnSp=kdJnSp,
-- -- 	@TgPiut=TgPiut,
-- -- 	@BayPokok=BayPokok,
-- -- 	@BayBunga=BayBunga,
-- -- 	@Comp=Comp
-- -- FROM DELETED
-- -- --
-- -- -- Update T05HPIU
-- -- IF @Comp='L'
-- --    BEGIN
-- --      UPDATE T05HPIU
-- --      SET BayPokLgs=BayPokLgs-@BayPokok,
-- --          BayBngLgs=BayBngLgs-@BayBunga		
-- --      WHERE NIP=@NIP AND KdJnsP=@kdJnSp AND TgDoku=@TgPiut
-- --    END
-- -- ELSE IF @Comp='P'
-- --    BEGIN 
-- --      UPDATE T05HPIU
-- --      SET BayPokPay = BayPokPay-@BayPokok,
-- --          BayBngPay = BayBngPay-@BayBunga
-- --      WHERE NIP=@NIP AND KdJnsP=@kdJnSp AND TgDoku=@TgPiut
-- --    END
-- -- --*
-- -- --
-- -- GO
-- -- 
-- -- /*****
-- -- UPDATE
-- -- ******/
-- -- CREATE TRIGGER TU_T07BAYP
-- -- ON T07BAYP WITH ENCRYPTION FOR UPDATE
-- -- AS
-- -- --
-- -- DECLARE @NIP		CHAR(10),
-- -- 	@kdJnSp		CHAR(4),
-- -- 	@TgPiut 	DATETIME,
-- -- 	@BayPokok  	DECIMAL(15,2),
-- -- 	@BayBunga  	DECIMAL(15,2),
-- -- 	@Comp		CHAR(1)
-- -- 	
-- -- --
-- -- SELECT  @NIP=NIP,
-- -- 	@kdJnSp=kdJnSp,
-- -- 	@TgPiut=TgPiut,
-- -- 	@BayPokok=BayPokok,
-- -- 	@BayBunga=BayBunga,
-- -- 	@Comp	=Comp
-- -- FROM DELETED
-- -- --
-- -- -- Update T05HPIU
-- -- -- Update T05HPIU
-- -- IF @Comp='L'
-- --    BEGIN
-- --      UPDATE T05HPIU
-- --      SET BayPokLgs=BayPokLgs-@BayPokok,
-- --          BayBngLgs=BayBngLgs-@BayBunga		
-- --      WHERE NIP=@NIP AND KdJnsP=@kdJnSp AND TgDoku=@TgPiut
-- --    END
-- -- ELSE IF @Comp='P'
-- --    BEGIN 
-- --      UPDATE T05HPIU
-- --      SET BayPokPay = BayPokPay-@BayPokok,
-- --          BayBngPay = BayBngPay-@BayBunga
-- --      WHERE NIP=@NIP AND KdJnsP=@kdJnSp AND TgDoku=@TgPiut
-- --    END
-- -- --*
-- -- --
-- -- --Insert Ulang
-- -- SELECT  @NIP=NIP,
-- -- 	@kdJnSp=kdJnSp,
-- -- 	@TgPiut=TgPiut,
-- -- 	@BayPokok=BayPokok,
-- -- 	@BayBunga=BayBunga
-- -- FROM INSERTED
-- -- --
-- -- -- Update T05HPIU
-- -- IF @Comp='L'
-- --    BEGIN 
-- --      UPDATE T05HPIU
-- --      SET BayPokLgs=BayPokLgs+@BayPokok,
-- --          BayBngLgs=BayBngLgs+@BayBunga
-- --       WHERE NIP=@NIP AND KdJnsP=@kdJnSp AND TgDoku=@TgPiut
-- --    END
-- -- ELSE IF @Comp='P'
-- --    BEGIN 
-- --      UPDATE T05HPIU
-- --      SET BayPokPay = BayPokPay+@BayPokok,
-- --          BayBngPay = BayBngPay+@BayBunga
-- --      WHERE NIP=@NIP AND KdJnsP=@kdJnSp AND TgDoku=@TgPiut
-- --    END
-- -- --*
-- -- --
-- -- GO
