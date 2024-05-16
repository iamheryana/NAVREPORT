DROP TRIGGER IF EXISTS TI_T21PMEP ON T21PMEP CASCADE  ; 
DROP TRIGGER IF EXISTS TD_T21PMEP ON T21PMEP CASCADE  ; 
DROP TRIGGER IF EXISTS TU_T21PMEP ON T21PMEP CASCADE  ; 
DROP FUNCTION IF EXISTS  FTA_T21PMEP() CASCADE; 


CREATE OR REPLACE FUNCTION FTA_T21PMEP()
  RETURNS TRIGGER AS
$$
DECLARE l_KdKJab	VARCHAR(4);
	l_tglmasuk	DATE;
	l_Plafon	DECIMAL(15,2);
	l_PlafonKel	DECIMAL(15,2);

    BEGIN
	IF (TG_OP = 'INSERT') THEN
		SELECT KdKJab,  tglmasuk
		INTO  l_KdKJab ,l_tglmasuk
		FROM M15PEGA
		WHERE NIP = NEW.NIP; 
		--

		if EXTRACT(YEAR FROM l_tglmasuk) = NEW.tahun THEN 
			SELECT (((12-EXTRACT(month FROM l_tglmasuk)+1) * Plafon) / 12) ::decimal(15,2),   
			       ((12-month(l_tglmasuk)+1) * Plafonkel)/12 
			INTO   l_Plafon, 
			       l_PlafonKel
			FROM T20JTMJ
			WHERE Tahun = NEW.Tahun AND JenisMedical = NEW.JenisMedical AND KelJab = l_KdKJab;
		else
			SELECT Plafon, PlafonKel
			INTO   l_Plafon, l_PlafonKel
			FROM T20JTMJ
			WHERE Tahun = NEW.Tahun AND JenisMedical = NEW.JenisMedical AND KelJab = l_KdKJab;
		end if; 	
		--
		-- Isi Data ke S0CMEDH
		IF (SELECT COUNT(NIP) FROM S0CMEDH WHERE NIP=NEW.NIP AND Tahun=NEW.Tahun AND JenisMedical=NEW.JenisMedical) = 0 THEN 
		   IF l_FlagKel = 0 THEN 
			INSERT INTO S0CMEDH(NIP    ,Tahun    ,JenisMedical    ,JatahMedical,JatahMedicalKel,Pemakaian    ,PemakaianKel,PemakaianKelOvr,version, created_by, created_on,     updated_by, updated_on)
				     VALUES(NEW.NIP,NEW.Tahun,NEW.JenisMedical,l_Plafon    ,l_PlafonKel    ,NEW.Pemakaian,           0,              0,0      , 'COMPUTER', localtimestamp, 'COMPUTER', localtimestamp);
		   ELSE
			INSERT INTO S0CMEDH(NIP    ,Tahun    ,JenisMedical    ,JatahMedical,JatahMedicalKel,Pemakaian,PemakaianKel ,PemakaianKelOvr,version, created_by, created_on,     updated_by,     updated_on)
				     VALUES(NEW.NIP,NEW.Tahun,NEW.JenisMedical,l_Plafon    ,l_PlafonKel    ,0        ,NEW.Pemakaian,              0,0      , 'COMPUTER', localtimestamp, 'COMPUTER', localtimestamp);
		   END IF; 
		ELSE
		   IF l_FlagKel = 0 THEN 
			UPDATE S0CMEDH
			SET Pemakaian = COALESCE(Pemakaian,0) + NEW.Pemakaian
			WHERE NIP=NEW.NIP AND Tahun=NEW.Tahun AND JenisMedical=NEW.JenisMedical;
		   ELSE
			UPDATE S0CMEDH
			SET PemakaianKel = COALESCE(PemakaianKel,0) + NEW.Pemakaian
			WHERE NIP=NEW.NIP AND Tahun=NEW.Tahun AND JenisMedical=NEW.JenisMedical;
		   END IF;  
		END IF;    
		--*
		-- Isi Data atau Update ke S0DMEDD
		IF (SELECT COUNT(NIP) FROM S0DMEDD WHERE NIP = NEW.NIP AND Tanggal = NEW.Tanggal AND 
		    Tahun = NEW.Tahun AND JenisMedical = NEW.JenisMedical) = 0 THEN 
		    
			INSERT INTO S0DMEDD(NIP    ,Tahun    ,JenisMedical    ,Tanggal    ,Pemakaian    ,Keterangan)
				     VALUES(NEW.NIP,NEW.Tahun,NEW.JenisMedical,NEW.Tanggal,NEW.Pemakaian,NEW.Keterangan);
		ELSE
			UPDATE S0DMEDD
			SET Pemakaian = COALESCE(Pemakaian,0) + NEW.Pemakaian
			WHERE NIP=NEW.NIP AND Tahun=NEW.Tahun AND JenisMedical=NEW.JenisMedical AND Tanggal=NEW.Tanggal;
		END IF; 
		--
		-- remark by peggy -- aneh create piutangnya 
		-- -- -- crt piutang 
		-- -- IF EXISTS (SELECT NIP FROM T27PMEP 
		-- --                WHERE NIP = l_NIP AND Tanggal = l_Tanggal AND 
		-- -- 					Tahun=l_Tahun AND JenisMedical=l_JenisMedical AND 
		-- -- 					FlagKel=l_FlagKel AND NamaKel=l_NamaKel AND KdSupl=l_KdSupl)  
		-- --    BEGIN
		-- -- -- create kode jenis piutang dasar jenis medical kalau belum ade 
		-- -- 	 IF NOT EXISTS (SELECT KdJnsP FROM M22JNSP WHERE KdJnsP = l_JenisMedical) 
		-- -- 	 BEGIN 
		-- -- 		SELECT 	l_KetJmed=Keterangan,
		-- -- 				l_SkJmed=Singkatan
		-- -- 		FROM M32JMED 
		-- -- 		WHERE Kode = l_JenisMedical
		-- -- 
		-- -- 	    INSERT INTO M22JNSP(KdJnsP,       NmPiut,                  SkPiut, UserId,         UpdDate,                        UpdTime,Ws)
		-- -- 	   		          VALUES(l_JenisMedical,SUBSTRING(l_KetJmed,1,20),l_SkJmed,'AUTO BY COMP',CONVERT(VARCHAR(10),GETDATE(),121),CONVERT(VARCHAR(19),GETDATE(),120),' ')
		-- --      END 
		-- -- 
		-- -- 	 IF NOT EXISTS (SELECT NIP FROM T05HPIU WHERE NIP=l_NIP AND KdJnsP = l_JenisMedical AND TGDOKU = l_TANGGAL ) 
		-- -- 	 BEGIN 
		-- -- 		  SELECT l_TglDocu = TglAngs1, l_SisPokok = NlMed2Piut
		-- -- 		  FROM T27PMEP 
		-- --           WHERE NIP = l_NIP AND Tanggal = l_Tanggal AND 
		-- -- 				Tahun=l_Tahun AND JenisMedical=l_JenisMedical AND 
		-- -- 				FlagKel=l_FlagKel AND NamaKel=l_NamaKel AND KdSupl=l_KdSupl
		-- -- 
		-- -- 	    INSERT INTO T05HPIU(NIP, KdJnsP,       TgDoku, NoRef,     Piutang,    Bunga, TtlAngsPok, TtlAngsBun, BayPokPay, BayBngPay, BayPokLgs, BayBngLgs, PrsBunga, JnsBunga, Recs, UserId,UpdDate,UpdTime,Ws)
		-- -- 	   		         VALUES(l_NIP,l_JenisMedical,l_Tanggal,'MEDICAL',l_SisPokok, 0    , 0         , 0         , 0        , 0        , 0        , 0        , 0       ,'M'      , 0   , 'AUTO BY COMP',CONVERT(VARCHAR(10),GETDATE(),121),CONVERT(VARCHAR(19),GETDATE(),120),' ')
		-- --         
		-- -- --
		-- -- 		  SELECT l_JmlBulan = 5 
		-- -- 
		-- --           SELECT l_BayPokok=FLOOR(l_SisPokok/l_JmlBulan),
		-- -- 				 l_Loop	  =1
		-- --           --
		-- -- 		  WHILE l_Loop<= l_JmlBulan 
		-- --             BEGIN
		-- --               --
		-- --               -- Jika Loop  Terakhir Maka Update Sisanya 
		-- --               IF l_Loop=l_JmlBulan
		-- --                  BEGIN
		-- --                     SELECT  l_BayPokok=l_SisPokok
		-- --                  END 
		-- --               --*
		-- --               -- Mulai Update data
		-- --               IF EXISTS (SELECT NIP FROM T06DPIU WHERE NIP=l_NIP AND KdJnsP=l_JenisMedical AND TgDoku=l_Tanggal AND PrdAngs=l_TglDocu)
		-- --                  BEGIN
		-- --                     UPDATE T06DPIU
		-- --                     SET JmlAngs=COALESCE(JmlAngs,0)+l_BayPokok
		-- -- 					WHERE NIP=l_NIP AND KdJnsP=l_JenisMedical AND TgDoku=l_Tanggal AND PrdAngs=l_TglDocu
		-- --                  END 
		-- --               ELSE
		-- --                  BEGIN
		-- --                     INSERT INTO T06DPIU(NIP ,KdJnsP     ,TgDoku  ,PrdAngs ,JmlAngs  ,JmlBunga)
		-- --                                  VALUES(l_NIP,l_JenisMedical,l_Tanggal,l_TglDocu,l_BayPokok,0)
		-- --                  END
		-- --               --*              
		-- --               --
		-- --               SELECT l_Loop = l_Loop +1,
		-- -- 					 l_TglDocu=DATEADD(MONTH,1,l_TglDocu),
		-- -- 					 l_SisPokok =l_SisPokok - l_BayPokok 
		-- --               --
		-- --             END
		-- --           --*
		-- --           --
		-- -- 		DELETE T27PMEP 
		-- --        	WHERE NIP = l_NIP AND Tanggal = l_Tanggal AND 
		-- -- 			Tahun=l_Tahun AND JenisMedical=l_JenisMedical AND 
		-- -- 			FlagKel=l_FlagKel AND NamaKel=l_NamaKel AND KdSupl=l_KdSupl  
		-- -- 	 END 
		-- --    END	
		----
		RETURN NEW;
		
	ELSIF (TG_OP = 'UPDATE') THEN		
		-- Update Data ke S0CMEDH
		IF (SELECT count(NIP) FROM S0CMEDH WHERE NIP=OLD.NIP AND Tahun=OLD.Tahun AND JenisMedical=OLD.JenisMedical) <> 0 THEN 
			IF OLD.FlagKel = 0 THEN 
				  UPDATE S0CMEDH
				  SET Pemakaian = COALESCE(Pemakaian,0) - OLD.Pemakaian
				  WHERE NIP=OLD.NIP AND Tahun=OLD.Tahun AND JenisMedical=OLD.JenisMedical;
			ELSE
				  UPDATE S0CMEDH
				  SET PemakaianKel = COALESCE(PemakaianKel,0) - OLD.Pemakaian
				  WHERE NIP=OLD.NIP AND Tahun=OLD.Tahun AND JenisMedical=OLD.JenisMedical;
			END IF; 
		END IF; 

		-- Update ke S0DMEDD
		IF (SELECT COUNT(NIP) FROM S0DMEDD 
		    WHERE NIP = OLD.NIP AND Tanggal = OLD.Tanggal AND 
			 Tahun = OLD.Tahun AND JenisMedical = OLD.JenisMedical) <> 0 THEN 

			UPDATE S0DMEDD
			SET Pemakaian = COALESCE(Pemakaian,0) - OLD.Pemakaian
			WHERE NIP=OLD.NIP AND Tahun=OLD.Tahun AND JenisMedical=OLD.JenisMedical AND Tanggal=OLD.Tanggal;
		END IF;    
		--*
		-- Hapus Data di S0DMEDD
		IF (SELECT COUNT(NIP) FROM S0DMEDD 
		    WHERE NIP = OLD.NIP AND Tanggal = OLD.Tanggal AND 
			  Tahun = OLD.Tahun AND JenisMedical = OLD.JenisMedical AND
			   Pemakaian = 0) <> 0 THEN 
			   
		     DELETE FROM S0DMEDD
		     WHERE NIP=OLD.NIP AND Tahun=OLD.Tahun AND JenisMedical=OLD.JenisMedical AND Tanggal = OLD.Tanggal;
		END IF;    
		--*
		--- Delete S0CMEDH jika sudah tdk ada pemakaian
		DELETE --FROM S0CMEDH
		FROM S0CMEDH S0C
		WHERE NOT EXISTS (SELECT 1 FROM S0DMEDD S0D 
				  WHERE S0D.NIP=S0C.NIP AND S0D.Tahun=S0C.Tahun AND S0D.JenisMedical=S0C.JenisMedical) AND 
		---
		      S0C.NIP=OLD.NIP AND S0C.Tahun=OLD.Tahun AND S0C.JenisMedical=OLD.JenisMedical AND 
		      S0C.Pemakaian+S0C.PemakaianKel+S0C.PemakaianKelOvr<=0 AND S0D.NIP IS NULL;
		--
		-- REMARK BY PEGGY -- PIUTANG KELIATANNYA SALAH 
		-- -- -- delete piutang 
		-- -- IF EXISTS (SELECT NIP FROM T05HPIU WHERE NIP=OLD.NIP AND KdJnsP = OLD.JenisMedical AND TGDOKU = OLD.TANGGAL ) 
		-- -- BEGIN 
		-- -- 	DELETE T06DPIU 
		-- -- 	WHERE NIP=OLD.NIP AND KdJnsP = OLD.JenisMedical AND TGDOKU = OLD.TANGGAL
		-- -- 
		-- -- 	DELETE T05HPIU 
		-- -- 	WHERE NIP=OLD.NIP AND KdJnsP = OLD.JenisMedical AND TGDOKU = OLD.TANGGAL
		-- -- 
		-- -- END 	
		--
		RETURN NEW;
	ELSIF (TG_OP = 'DELETE') THEN	

		IF COALESCE(NEW.KDSUPL,' ') > ' ' THEN  -- HANYA KALAU ADA DATA YANG DIUPDATE DARI T21PMEP 
		-- SOALNYA TRIGGER INI JUGA JALAN KALAU NAMA KELUARGA DI TABEL KELUARGA DIUPDATE 
		BEGIN 
		-- Update Data ke S0CMEDH
		IF (SELECT COUNT(NIP) FROM S0CMEDH WHERE NIP=OLD.NIP AND Tahun=OLD.Tahun AND JenisMedical=OLD.JenisMedical) > 0 THEN 
			  IF OLD.FlagKel = 0 THEN 
				  UPDATE S0CMEDH
				  SET Pemakaian = COALESCE(Pemakaian,0) - OLD.Pemakaian
				  WHERE NIP=OLD.NIP AND Tahun=OLD.Tahun AND JenisMedical=OLD.JenisMedical;
			  ELSE
				  UPDATE S0CMEDH
				  SET PemakaianKel = COALESCE(PemakaianKel,0) - OLD.Pemakaian
				  WHERE NIP=OLD.NIP AND Tahun=OLD.Tahun AND JenisMedical=OLD.JenisMedical;
			  END IF; 
			 --*		
		END IF;    
		--*
		-- Update ke S0DMEDD
		IF (SELECT COUNT(NIP) FROM S0DMEDD 
		    WHERE NIP = OLD.NIP AND Tanggal = OLD.Tanggal AND 
			  Tahun = OLD.Tahun AND JenisMedical = OLD.JenisMedical) > 0 THEN 

			UPDATE S0DMEDD
			SET Pemakaian = COALESCE(Pemakaian,0) - OLD.Pemakaian
			WHERE NIP=OLD.NIP AND Tahun=OLD.Tahun AND JenisMedical=OLD.JenisMedical AND Tanggal=OLD.Tanggal;
		END IF;    
		--*
		-- Hapus Data di S0DMEDD
		IF (SELECT COUNT(NIP) FROM S0DMEDD 
		    WHERE NIP = OLD.NIP AND Tanggal = OLD.Tanggal AND 
			  Tahun = OLD.Tahun AND JenisMedical = OLD.JenisMedical AND
			  Pemakaian = 0) > 0 THEN 

		     DELETE FROM S0DMEDD
		     WHERE NIP=OLD.NIP AND Tahun=OLD.Tahun AND JenisMedical=OLD.JenisMedical AND Tanggal = OLD.Tanggal;
		END IF ;    
		--*
		--
		--Insert Ulang
		--
		-- SELECT 	OLD.NIP=NIP,
		-- 		OLD.Tahun=Tahun,
		-- 		OLD.JenisMedical=JenisMedical,
		-- 		OLD.Tanggal=Tanggal,
		-- 		OLD.FlagKel=FlagKel,
		-- 		OLD.Pemakaian=Pemakaian,
		-- 		OLD.Keterangan=Keterangan
		-- FROM INSERTED
		--
		SELECT KdKJab,  tglmasuk
		INTO  l_KdKJab ,l_tglmasuk
		FROM M15PEGA
		WHERE NIP = NEW.NIP;
				
		if EXTRACT(YEAR FROM l_tglmasuk) = NEW.tahun THEN 
			SELECT (((12-EXTRACT(month FROM l_tglmasuk)+1) * NEW.Plafon) / 12) ::decimal(15,2),   
			       ((12-month(l_tglmasuk)+1) * NEW.Plafonkel)/12 
			INTO   l_Plafon, 
			       l_PlafonKel
			FROM T20JTMJ
			WHERE Tahun = NEW.Tahun AND JenisMedical = NEW.JenisMedical AND KelJab = l_KdKJab;
		--	select OLD.plafon =(convert(decimal(15,2),((12-month(OLD.tglmasuk)+1))) * OLD.plafon)/12
		--	select OLD.plafonkel =(((12-month(OLD.tglmasuk)+1)) * OLD.plafonkel)/12
		else
			SELECT Plafon,   PlafonKel
			INTO   l_Plafon, l_PlafonKel
			FROM T20JTMJ
			WHERE Tahun = NEW.Tahun AND JenisMedical = NEW.JenisMedical AND KelJab = l_KdKJab;
		END IF; 
		--
		-- Isi Data ke S0CMEDH
		IF (SELECT COUNT(NIP) FROM S0CMEDH WHERE NIP=NEW.NIP AND Tahun=NEW.Tahun AND JenisMedical=NEW.JenisMedical) > 0 THEN 
			IF NEW.FlagKel = 0 THEN 
				  UPDATE S0CMEDH
				  SET /*JatahMedical = OLD.Plafon,
					  JatahMedicalKel = OLD.PlafonKel,*/
					  Pemakaian = COALESCE(Pemakaian,0) + NEW.Pemakaian
				  WHERE NIP=NEW.NIP AND Tahun=NEW.Tahun AND JenisMedical=NEW.JenisMedical;
			ELSE
				  UPDATE S0CMEDH
				  SET /*JatahMedical = OLD.Plafon,
					  JatahMedicalKel = OLD.PlafonKel,*/
					  PemakaianKel = COALESCE(PemakaianKel,0) + NEW.Pemakaian
				  WHERE NIP=NEW.NIP AND Tahun=NEW.Tahun AND JenisMedical=NEW.JenisMedical;
			END IF; 
			 --*		
		END IF;    
		--*
		-- Isi Data atau Update ke S0DMEDD
		IF (SELECT COUNT(NIP) FROM S0DMEDD 
		    WHERE NIP = NEW.NIP AND Tanggal = NEW.Tanggal AND 
			  Tahun = NEW.Tahun AND JenisMedical = NEW.JenisMedical) = 0 THEN 
		     INSERT INTO S0DMEDD(NIP ,Tahun ,JenisMedical ,Tanggal ,Pemakaian ,Keterangan)
				  VALUES(NEW.NIP,NEW.Tahun,NEW.JenisMedical,NEW.Tanggal,NEW.Pemakaian,NEW.Keterangan);
		ELSE
		     UPDATE S0DMEDD
		     SET Pemakaian = COALESCE(Pemakaian,0) + NEW.Pemakaian
		     WHERE NIP=NEW.NIP AND Tahun=NEW.Tahun AND JenisMedical=NEW.JenisMedical AND Tanggal=NEW.Tanggal;
		END IF; 
		END ; 
		END IF; 
		--
		RETURN OLD;
	END IF; 
	
    END;	
$$ LANGUAGE plpgsql;

-- Trigger Insert
CREATE TRIGGER TI_T21PMEP
AFTER INSERT ON T21PMEP
    FOR EACH ROW EXECUTE PROCEDURE FTA_T21PMEP();

-- Trigger Update
CREATE TRIGGER TU_T21PMEP
AFTER UPDATE ON T21PMEP
    FOR EACH ROW EXECUTE PROCEDURE FTA_T21PMEP();    

-- Trigger Delete
CREATE TRIGGER TD_T21PMEP
AFTER DELETE ON T21PMEP
    FOR EACH ROW EXECUTE PROCEDURE FTA_T21PMEP();    

--------------------------------------------------------------------------------------------------------
--- ms sql script 
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TI_T21PMEP' AND type = 'TR')
-- -- DROP TRIGGER TI_T21PMEP
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TD_T21PMEP' AND type = 'TR')
-- -- DROP TRIGGER TD_T21PMEP
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TU_T21PMEP' AND type = 'TR')
-- -- DROP TRIGGER TU_T21PMEP
-- -- GO
-- -- 
-- -- 
-- -- /*****
-- -- INSERT
-- -- ******/
-- -- CREATE TRIGGER TI_T21PMEP
-- -- ON T21PMEP WITH ENCRYPTION FOR INSERT 
-- -- AS
-- -- --
-- -- DECLARE @NIP			CHAR(10),
-- -- 		@Tahun			DECIMAL(4,0),
-- -- 		@JenisMedical	CHAR(4),
-- -- 		@Tanggal 		DATETIME,
-- -- 		@FlagKel		TINYINT,
-- -- 		@Pemakaian  	DECIMAL(15,2),
-- -- 		@Keterangan 	CHAR(30),
-- -- 		@KdKJab			CHAR(4),
-- -- 		@Plafon			DECIMAL(15,2),
-- -- 		@PlafonKel		DECIMAL(15,2),
-- -- 		@tglmasuk		datetime,
-- -- 		@NamaKel		CHAR(25), 
-- -- 		@KdSupl 		CHAR(4), 
-- -- 		@Ketjmed		CHAR(30), 
-- -- 		@Skjmed			CHAR(10), 
-- -- 		@TglAngs1		DATETIME
-- -- 
-- -- DECLARE		@BayPokok	DECIMAL(15,2),
-- -- 			@BayBunga	DECIMAL(15,2),
-- -- 			@SisPokok	DECIMAL(15,2),
-- -- 			@Loop		INT,
-- -- 			@TglDocu	DATETIME, 
-- -- 			@JmlBulan   INT	
-- -- --
-- -- SELECT 	@NIP=NIP,
-- -- 		@Tahun=Tahun,
-- -- 		@JenisMedical=JenisMedical,
-- -- 		@Tanggal=Tanggal,
-- -- 		@FlagKel=FlagKel,
-- -- 		@Pemakaian=Pemakaian,
-- -- 		@Keterangan=Keterangan,
-- -- 		@NamaKel=NamaKel, 
-- -- 		@KdSupl=KdSupl 
-- -- FROM INSERTED
-- -- --
-- -- SELECT @KdKJab = KdKJab,@tglmasuk=tglmasuk
-- -- FROM M15PEGA
-- -- WHERE NIP = @NIP
-- -- --
-- -- 
-- -- if year(@tglmasuk) =@tahun 
-- -- 	begin
-- -- 		SELECT @Plafon = Plafon, @PlafonKel = PlafonKel
-- -- 		FROM T20JTMJ
-- -- 		WHERE Tahun = @Tahun AND JenisMedical = @JenisMedical AND KelJab = @KdKJab
-- -- 		select @plafon =(convert(decimal(15,2),((12-month(@tglmasuk)+1))) * @plafon)/12
-- -- 		select @plafonkel =(((12-month(@tglmasuk)+1)) * @plafonkel)/12
-- -- 	end
-- -- else
-- -- 	SELECT @Plafon = Plafon, @PlafonKel = PlafonKel
-- -- 	FROM T20JTMJ
-- -- 	WHERE Tahun = @Tahun AND JenisMedical = @JenisMedical AND KelJab = @KdKJab
-- -- --
-- -- -- Isi Data ke S0CMEDH
-- -- IF NOT EXISTS (SELECT NIP FROM S0CMEDH WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical)
-- --    BEGIN
-- -- 	 IF @FlagKel = 0
-- -- 		BEGIN
-- --           INSERT INTO S0CMEDH(NIP ,Tahun ,JenisMedical ,JatahMedical ,JatahMedicalKel,Pemakaian ,PemakaianKel,PemakaianKelOvr,UserId ,UpdDate		                  ,UpdTime,Ws)
-- -- 	      		       VALUES(@NIP,@Tahun,@JenisMedical,@Plafon      ,@PlafonKel     ,@Pemakaian,           0,              0,'COMPUTER',CONVERT(CHAR(10),GETDATE(),121),CONVERT(CHAR(19),GETDATE(),120),' ')
-- -- 		END
-- --      ELSE
-- -- 		BEGIN
-- --           INSERT INTO S0CMEDH(NIP ,Tahun ,JenisMedical ,JatahMedical ,JatahMedicalKel,Pemakaian,PemakaianKel,PemakaianKelOvr,UserId ,UpdDate		                  ,UpdTime,Ws)
-- -- 	      		       VALUES(@NIP,@Tahun,@JenisMedical,@Plafon      ,@PlafonKel     ,0        ,@Pemakaian  ,              0,'COMPUTER',CONVERT(CHAR(10),GETDATE(),121),CONVERT(CHAR(19),GETDATE(),120),' ')
-- -- 		END
-- -- 	 --*		
-- --    END
-- -- ELSE
-- --    BEGIN
-- -- 	 IF @FlagKel = 0
-- -- 		BEGIN
-- -- 		  UPDATE S0CMEDH
-- -- 		  SET Pemakaian = ISNULL(Pemakaian,0) + @Pemakaian
-- -- 		  WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical
-- -- 		END
-- --      ELSE
-- -- 		BEGIN
-- -- 		  UPDATE S0CMEDH
-- -- 		  SET PemakaianKel = ISNULL(PemakaianKel,0) + @Pemakaian
-- -- 		  WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical
-- -- 		END
-- -- 	 --*		
-- --    END
-- -- --*
-- -- -- Isi Data atau Update ke S0DMEDD
-- -- IF NOT EXISTS (SELECT NIP FROM S0DMEDD 
-- --                WHERE NIP = @NIP AND Tanggal = @Tanggal AND 
-- -- 					 Tahun = @Tahun AND JenisMedical = @JenisMedical)
-- --    BEGIN
-- --      INSERT INTO S0DMEDD(NIP ,Tahun ,JenisMedical ,Tanggal ,Pemakaian ,Keterangan)
-- --    		          VALUES(@NIP,@Tahun,@JenisMedical,@Tanggal,@Pemakaian,@Keterangan)
-- --    END
-- -- ELSE
-- --    BEGIN
-- --      UPDATE S0DMEDD
-- -- 	 SET Pemakaian = ISNULL(Pemakaian,0) + @Pemakaian
-- --    	 WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical AND Tanggal=@Tanggal
-- --    END
-- -- --*
-- -- --
-- -- -- crt piutang 
-- -- IF EXISTS (SELECT NIP FROM T27PMEP 
-- --                WHERE NIP = @NIP AND Tanggal = @Tanggal AND 
-- -- 					Tahun=@Tahun AND JenisMedical=@JenisMedical AND 
-- -- 					FlagKel=@FlagKel AND NamaKel=@NamaKel AND KdSupl=@KdSupl)  
-- --    BEGIN
-- -- -- create kode jenis piutang dasar jenis medical kalau belum ade 
-- -- 	 IF NOT EXISTS (SELECT KdJnsP FROM M22JNSP WHERE KdJnsP = @JenisMedical) 
-- -- 	 BEGIN 
-- -- 		SELECT 	@KetJmed=Keterangan,
-- -- 				@SkJmed=Singkatan
-- -- 		FROM M32JMED 
-- -- 		WHERE Kode = @JenisMedical
-- -- 
-- -- 	    INSERT INTO M22JNSP(KdJnsP,       NmPiut,                  SkPiut, UserId,         UpdDate,                        UpdTime,Ws)
-- -- 	   		          VALUES(@JenisMedical,SUBSTRING(@KetJmed,1,20),@SkJmed,'AUTO BY COMP',CONVERT(CHAR(10),GETDATE(),121),CONVERT(CHAR(19),GETDATE(),120),' ')
-- --      END 
-- -- 
-- -- 	 IF NOT EXISTS (SELECT NIP FROM T05HPIU WHERE NIP=@NIP AND KdJnsP = @JenisMedical AND TGDOKU = @TANGGAL ) 
-- -- 	 BEGIN 
-- -- 		  SELECT @TglDocu = TglAngs1, @SisPokok = NlMed2Piut
-- -- 		  FROM T27PMEP 
-- --           WHERE NIP = @NIP AND Tanggal = @Tanggal AND 
-- -- 				Tahun=@Tahun AND JenisMedical=@JenisMedical AND 
-- -- 				FlagKel=@FlagKel AND NamaKel=@NamaKel AND KdSupl=@KdSupl
-- -- 
-- -- 	    INSERT INTO T05HPIU(NIP, KdJnsP,       TgDoku, NoRef,     Piutang,    Bunga, TtlAngsPok, TtlAngsBun, BayPokPay, BayBngPay, BayPokLgs, BayBngLgs, PrsBunga, JnsBunga, Recs, UserId,UpdDate,UpdTime,Ws)
-- -- 	   		         VALUES(@NIP,@JenisMedical,@Tanggal,'MEDICAL',@SisPokok, 0    , 0         , 0         , 0        , 0        , 0        , 0        , 0       ,'M'      , 0   , 'AUTO BY COMP',CONVERT(CHAR(10),GETDATE(),121),CONVERT(CHAR(19),GETDATE(),120),' ')
-- --         
-- -- --
-- -- 		  SELECT @JmlBulan = 5 
-- -- 
-- --           SELECT @BayPokok=FLOOR(@SisPokok/@JmlBulan),
-- -- 				 @Loop	  =1
-- --           --
-- -- 		  WHILE @Loop<= @JmlBulan 
-- --             BEGIN
-- --               --
-- --               -- Jika Loop  Terakhir Maka Update Sisanya 
-- --               IF @Loop=@JmlBulan
-- --                  BEGIN
-- --                     SELECT  @BayPokok=@SisPokok
-- --                  END 
-- --               --*
-- --               -- Mulai Update data
-- --               IF EXISTS (SELECT NIP FROM T06DPIU WHERE NIP=@NIP AND KdJnsP=@JenisMedical AND TgDoku=@Tanggal AND PrdAngs=@TglDocu)
-- --                  BEGIN
-- --                     UPDATE T06DPIU
-- --                     SET JmlAngs=ISNULL(JmlAngs,0)+@BayPokok
-- -- 					WHERE NIP=@NIP AND KdJnsP=@JenisMedical AND TgDoku=@Tanggal AND PrdAngs=@TglDocu
-- --                  END 
-- --               ELSE
-- --                  BEGIN
-- --                     INSERT INTO T06DPIU(NIP ,KdJnsP     ,TgDoku  ,PrdAngs ,JmlAngs  ,JmlBunga)
-- --                                  VALUES(@NIP,@JenisMedical,@Tanggal,@TglDocu,@BayPokok,0)
-- --                  END
-- --               --*              
-- --               --
-- --               SELECT @Loop = @Loop +1,
-- -- 					 @TglDocu=DATEADD(MONTH,1,@TglDocu),
-- -- 					 @SisPokok =@SisPokok - @BayPokok 
-- --               --
-- --             END
-- --           --*
-- --           --
-- -- 		DELETE T27PMEP 
-- --        	WHERE NIP = @NIP AND Tanggal = @Tanggal AND 
-- -- 			Tahun=@Tahun AND JenisMedical=@JenisMedical AND 
-- -- 			FlagKel=@FlagKel AND NamaKel=@NamaKel AND KdSupl=@KdSupl  
-- -- 	 END 
-- --    END
-- -- 
-- -- 
-- -- 
-- -- GO
-- -- 
-- -- 
-- -- /*****
-- -- DELETE
-- -- ******/
-- -- CREATE TRIGGER TD_T21PMEP
-- -- ON T21PMEP WITH ENCRYPTION FOR DELETE
-- -- AS
-- -- --
-- -- DECLARE @NIP			CHAR(10),
-- -- 		@Tahun			DECIMAL(4,0),
-- -- 		@JenisMedical	CHAR(4),
-- -- 		@Tanggal 		DATETIME,
-- -- 		@FlagKel		TINYINT,
-- -- 		@Pemakaian  	DECIMAL(15,2)
-- -- --
-- -- SELECT 	@NIP=NIP,
-- -- 		@Tahun=Tahun,
-- -- 		@JenisMedical=JenisMedical,
-- -- 		@Tanggal=Tanggal,
-- -- 		@FlagKel=FlagKel,
-- -- 		@Pemakaian=Pemakaian
-- -- FROM DELETED
-- -- --
-- -- -- Update Data ke S0CMEDH
-- -- IF EXISTS (SELECT NIP FROM S0CMEDH WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical)
-- --    BEGIN
-- -- 	 IF @FlagKel = 0
-- -- 		BEGIN
-- -- 		  UPDATE S0CMEDH
-- -- 		  SET Pemakaian = ISNULL(Pemakaian,0) - @Pemakaian
-- -- 		  WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical
-- -- 		END
-- --      ELSE
-- -- 		BEGIN
-- -- 		  UPDATE S0CMEDH
-- -- 		  SET PemakaianKel = ISNULL(PemakaianKel,0) - @Pemakaian
-- -- 		  WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical
-- -- 		END
-- -- 	 --*		
-- --    END
-- -- --*
-- -- 
-- -- -- Update ke S0DMEDD
-- -- IF EXISTS (SELECT NIP FROM S0DMEDD 
-- --            WHERE NIP = @NIP AND Tanggal = @Tanggal AND 
-- -- 			 	 Tahun = @Tahun AND JenisMedical = @JenisMedical)
-- --    BEGIN
-- --      UPDATE S0DMEDD
-- -- 	 SET Pemakaian = ISNULL(Pemakaian,0) - @Pemakaian
-- --    	 WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical AND Tanggal=@Tanggal
-- --    END
-- -- --*
-- -- -- Hapus Data di S0DMEDD
-- -- IF EXISTS (SELECT NIP FROM S0DMEDD 
-- --            WHERE NIP = @NIP AND Tanggal = @Tanggal AND 
-- -- 			 	 Tahun = @Tahun AND JenisMedical = @JenisMedical AND
-- -- 				 Pemakaian = 0)
-- --    BEGIN
-- --      DELETE S0DMEDD
-- --      WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical AND Tanggal = @Tanggal
-- --    END
-- -- --*
-- -- --- Delete S0CMEDH jika sudah tdk ada pemakaian
-- -- DELETE S0CMEDH
-- -- FROM S0CMEDH S0C
-- -- LEFT JOIN S0DMEDD S0D
-- -- ON S0D.NIP=S0C.NIP AND S0D.Tahun=S0C.Tahun AND S0D.JenisMedical=S0C.JenisMedical
-- -- ---
-- -- WHERE S0C.NIP=@NIP AND S0C.Tahun=@Tahun AND S0C.JenisMedical=@JenisMedical AND 
-- -- 	  S0C.Pemakaian+S0C.PemakaianKel+S0C.PemakaianKelOvr<=0 AND S0D.NIP IS NULL
-- -- --
-- -- -- delete piutang 
-- -- IF EXISTS (SELECT NIP FROM T05HPIU WHERE NIP=@NIP AND KdJnsP = @JenisMedical AND TGDOKU = @TANGGAL ) 
-- -- BEGIN 
-- -- 	DELETE T06DPIU 
-- -- 	WHERE NIP=@NIP AND KdJnsP = @JenisMedical AND TGDOKU = @TANGGAL
-- -- 
-- -- 	DELETE T05HPIU 
-- -- 	WHERE NIP=@NIP AND KdJnsP = @JenisMedical AND TGDOKU = @TANGGAL
-- -- 
-- -- END 
-- -- 
-- -- 
-- -- GO
-- -- 
-- -- /*****
-- -- UPDATE
-- -- ******/
-- -- CREATE TRIGGER TU_T21PMEP
-- -- ON T21PMEP WITH ENCRYPTION FOR UPDATE
-- -- AS
-- -- --
-- -- DECLARE @NIP			CHAR(10),
-- -- 		@Tahun			DECIMAL(4,0),
-- -- 		@JenisMedical	CHAR(4),
-- -- 		@Tanggal 		DATETIME,
-- -- 		@FlagKel		TINYINT,
-- -- 		@Pemakaian  	DECIMAL(15,2),
-- -- 		@Keterangan 	CHAR(30),
-- -- 		@KdKJab			CHAR(4),
-- -- 		@tglmasuk		datetime,
-- -- 		@Plafon			DECIMAL(15,2),
-- -- 		@PlafonKel		DECIMAL(15,2)
-- -- --
-- -- DECLARE 	@DT		DECIMAL(10,0) 
-- -- --
-- -- SELECT 	@NIP=NIP,
-- -- 		@Tahun=Tahun,
-- -- 		@JenisMedical=JenisMedical,
-- -- 		@Tanggal=Tanggal,
-- -- 		@FlagKel=FlagKel,
-- -- 		@Pemakaian=Pemakaian
-- -- FROM DELETED
-- -- --
-- -- SELECT @DT=COUNT(*) FROM DELETED 
-- -- --
-- -- IF ISNULL(@DT,0) > 0 -- HANYA KALAU ADA DATA YANG DIUPDATE DARI T21PMEP 
-- -- -- SOALNYA TRIGGER INI JUGA JALAN KALAU NAMA KELUARGA DI TABEL KELUARGA DIUPDATE 
-- -- BEGIN 
-- -- -- Update Data ke S0CMEDH
-- -- IF EXISTS (SELECT NIP FROM S0CMEDH WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical)
-- --    BEGIN
-- -- 	 IF @FlagKel = 0
-- -- 		BEGIN
-- -- 		  UPDATE S0CMEDH
-- -- 		  SET Pemakaian = ISNULL(Pemakaian,0) - @Pemakaian
-- -- 		  WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical
-- -- 		END
-- --      ELSE
-- -- 		BEGIN
-- -- 		  UPDATE S0CMEDH
-- -- 		  SET PemakaianKel = ISNULL(PemakaianKel,0) - @Pemakaian
-- -- 		  WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical
-- -- 		END
-- -- 	 --*		
-- --    END
-- -- --*
-- -- -- Update ke S0DMEDD
-- -- IF EXISTS (SELECT NIP FROM S0DMEDD 
-- --            WHERE NIP = @NIP AND Tanggal = @Tanggal AND 
-- -- 			 	 Tahun = @Tahun AND JenisMedical = @JenisMedical)
-- --    BEGIN
-- --      UPDATE S0DMEDD
-- -- 	 SET Pemakaian = ISNULL(Pemakaian,0) - @Pemakaian
-- --    	 WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical AND Tanggal=@Tanggal
-- --    END
-- -- --*
-- -- -- Hapus Data di S0DMEDD
-- -- IF EXISTS (SELECT NIP FROM S0DMEDD 
-- --            WHERE NIP = @NIP AND Tanggal = @Tanggal AND 
-- -- 			 	 Tahun = @Tahun AND JenisMedical = @JenisMedical AND
-- -- 				 Pemakaian = 0)
-- --    BEGIN
-- --      DELETE S0DMEDD
-- --      WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical AND Tanggal = @Tanggal
-- --    END
-- -- --*
-- -- --
-- -- --Insert Ulang
-- -- --
-- -- SELECT 	@NIP=NIP,
-- -- 		@Tahun=Tahun,
-- -- 		@JenisMedical=JenisMedical,
-- -- 		@Tanggal=Tanggal,
-- -- 		@FlagKel=FlagKel,
-- -- 		@Pemakaian=Pemakaian,
-- -- 		@Keterangan=Keterangan
-- -- FROM INSERTED
-- -- --
-- -- SELECT @KdKJab = KdKJab,@tglmasuk=tglmasuk
-- -- FROM M15PEGA
-- -- WHERE NIP = @NIP
-- -- --
-- -- if year(@tglmasuk) =@tahun 
-- -- 	begin
-- -- 		SELECT @Plafon = Plafon, @PlafonKel = PlafonKel
-- -- 		FROM T20JTMJ
-- -- 		WHERE Tahun = @Tahun AND JenisMedical = @JenisMedical AND KelJab = @KdKJab
-- -- 		select @plafon =(convert(decimal(15,2),((12-month(@tglmasuk)+1))) * @plafon)/12
-- -- 		select @plafonkel =(((12-month(@tglmasuk)+1)) * @plafonkel)/12
-- -- 	end
-- -- else
-- -- 	SELECT @Plafon = Plafon, @PlafonKel = PlafonKel
-- -- 	FROM T20JTMJ
-- -- 	WHERE Tahun = @Tahun AND JenisMedical = @JenisMedical AND KelJab = @KdKJab
-- -- 
-- -- --
-- -- -- Isi Data ke S0CMEDH
-- -- IF EXISTS (SELECT NIP FROM S0CMEDH WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical)
-- --    BEGIN
-- -- 	 IF @FlagKel = 0
-- -- 		BEGIN
-- -- 		  UPDATE S0CMEDH
-- -- 		  SET /*JatahMedical = @Plafon,
-- -- 			  JatahMedicalKel = @PlafonKel,*/
-- -- 			  Pemakaian = ISNULL(Pemakaian,0) + @Pemakaian
-- -- 		  WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical
-- -- 		END
-- --      ELSE
-- -- 		BEGIN
-- -- 		  UPDATE S0CMEDH
-- -- 		  SET /*JatahMedical = @Plafon,
-- -- 			  JatahMedicalKel = @PlafonKel,*/
-- -- 			  PemakaianKel = ISNULL(PemakaianKel,0) + @Pemakaian
-- -- 		  WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical
-- -- 		END
-- -- 	 --*		
-- --    END
-- -- --*
-- -- -- Isi Data atau Update ke S0DMEDD
-- -- IF NOT EXISTS (SELECT NIP FROM S0DMEDD 
-- --                WHERE NIP = @NIP AND Tanggal = @Tanggal AND 
-- -- 					 Tahun = @Tahun AND JenisMedical = @JenisMedical)
-- --    BEGIN
-- --      INSERT INTO S0DMEDD(NIP ,Tahun ,JenisMedical ,Tanggal ,Pemakaian ,Keterangan)
-- --    		          VALUES(@NIP,@Tahun,@JenisMedical,@Tanggal,@Pemakaian,@Keterangan)
-- --    END
-- -- ELSE
-- --    BEGIN
-- --      UPDATE S0DMEDD
-- -- 	 SET Pemakaian = ISNULL(Pemakaian,0) + @Pemakaian
-- --    	 WHERE NIP=@NIP AND Tahun=@Tahun AND JenisMedical=@JenisMedical AND Tanggal=@Tanggal
-- --    END
-- -- END 
-- -- --*
-- -- --
-- -- GO
-- -- 
-- -- /* TESTING ...
-- -- INSERT INTO T21PMEP (NIP ,Tahun,JenisMedical,Tanggal     ,FlagKel,NamaKel,KdSupl,Pemakaian,Keterangan             ,UserId,UpdDate,UpdTime,WS)
-- -- 			 VALUES ('03',2002 ,'JM01'        ,'2002-04-19',0      ,' '    ,' '   ,340000   ,'TEST YUDI OFFLINE ...','YUDI','2002-11-20','2002-11-20 10:16:00',' ')
-- -- 
-- -- DELETE T21PMEP
-- -- WHERE NIP = '03' AND Tahun = 2001 AND JenisMedical = 'RI' AND Tanggal = '2001-01-16'
-- -- 
-- -- UPDATE T21PMEP
-- -- SET Pemakaian = 350000
-- -- WHERE NIP = '03' AND Tahun = 2001 AND JenisMedical = 'RI' AND Tanggal = '2001-01-16'
-- -- */
