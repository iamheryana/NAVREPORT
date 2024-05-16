DROP TRIGGER IF EXISTS TI_T12DTRN ON T12DTRN CASCADE  ; 
DROP TRIGGER IF EXISTS TD_T12DTRN ON T12DTRN CASCADE  ; 
DROP TRIGGER IF EXISTS TU_T12DTRN ON T12DTRN CASCADE  ; 
DROP FUNCTION IF EXISTS  FTA_T12DTRN() CASCADE; 


CREATE OR REPLACE FUNCTION FTA_T12DTRN()
  RETURNS TRIGGER AS
$$

    BEGIN
	   
	IF (TG_OP = 'INSERT') THEN
		--
		UPDATE T11HTRN T11U
		SET Recs=COALESCE(T12.DTL,0)
		FROM 
		(
		SELECT T11.TgDocu, T11.KdJnsD, T11.KdJrsn, COUNT(T12.TgDocu) AS DTL
		FROM T11HTRN T11
		LEFT JOIN T12DTRN T12
		ON T12.TgDocu=T11.TgDocu AND T12.KdJnsD=T11.KdJnsD AND T12.KdJrsn=T11.KdJrsn
		WHERE NEW.TgDocu=T11.TgDocu AND NEW.KdJnsD=T11.KdJnsD AND NEW.KdJrsn=T11.KdJrsn
		GROUP BY T11.TgDocu, T11.KdJnsD, T11.KdJrsn		
		) T12
		--
		WHERE T12.TgDocu=T11U.TgDocu AND T12.KdJnsD=T11U.KdJnsD AND T12.KdJrsn=T11U.KdJrsn; 

		--
		----- Isi Summary Training
		INSERT INTO S07TRNG
		SELECT NEW.TgDocu,NEW.NIP,
			   NEW.KdJnsD,M25.Singkatan,
			   NEW.KdJrsn,M26.Singkatan,
			   T11.KdLmbD,M27.Singkatan,
			   T11.KdLksD,M28.Singkatan,
			   T11.BulanTrng,T11.HariTrng,NEW.Hasil,NEW.Nilai,NEW.BiayaSendiri,T11.Biaya,INS.NoIjaz,
			   T11.version, T11.created_by, T11.created_on, T11.updated_by, T11.updated_on
		---
		FROM T11HTRN T11
		--
		INNER JOIN M25JNSD M25
		ON M25.Kode = NEW.KdJnsD
		--
		INNER JOIN M26JRSN M26
		ON M26.Kode = NEW.KdJrsn
		--
		INNER JOIN M27LMBD M27
		ON M27.Kode = T11.KdLmbD
		--
		INNER JOIN M28LKSD M28
		ON M28.Kode = T11.KdLksD
		---
		LEFT JOIN S07TRNG S07
		ON S07.Tanggal=NEW.TgDocu AND S07.NIP=NEW.NIP AND S07.KdJnsd=NEW.KdJnsD AND S07.KdJrsn=NEW.KdJrsn
		---
		WHERE S07.NIP IS NULL AND T11.TgDocu = NEW.TgDocu AND T11.KdJnsD = NEW.KdJnsD AND T11.KdJrsn = NEW.KdJrsn;
	
		--
		RETURN NEW;
	ELSIF (TG_OP = 'UPDATE') THEN
		----- DELETE Summary Training
		DELETE FROM S07TRNG
		WHERE S07.Tanggal=OLD.TgDocu AND S07.NIP=OLD.NIP AND S07.KdJnsd=OLD.KdJnsD AND S07.KdJrsn=OLD.KdJrsn AND S07.NIP IS NOT NULL;	
		----- Isi Summary Training
		INSERT INTO S07TRNG
		SELECT NEW.TgDocu,NEW.NIP,
			   NEW.KdJnsD,M25.Singkatan,
			   NEW.KdJrsn,M26.Singkatan,
			   T11.KdLmbD,M27.Singkatan,
			   T11.KdLksD,M28.Singkatan,
			   T11.BulanTrng,T11.HariTrng,NEW.Hasil,NEW.Nilai,NEW.BiayaSendiri,T11.Biaya,INS.NoIjaz,
			   T11.version, T11.created_by, T11.created_on, T11.updated_by, T11.updated_on
		---
		FROM T11HTRN T11
		--
		INNER JOIN M25JNSD M25
		ON M25.Kode = NEW.KdJnsD
		--
		INNER JOIN M26JRSN M26
		ON M26.Kode = NEW.KdJrsn
		--
		INNER JOIN M27LMBD M27
		ON M27.Kode = T11.KdLmbD
		--
		INNER JOIN M28LKSD M28
		ON M28.Kode = T11.KdLksD
		---
		LEFT JOIN S07TRNG S07
		ON S07.Tanggal=NEW.TgDocu AND S07.NIP=NEW.NIP AND S07.KdJnsd=NEW.KdJnsD AND S07.KdJrsn=NEW.KdJrsn
		---
		WHERE S07.NIP IS NULL AND T11.TgDocu = NEW.TgDocu AND T11.KdJnsD = NEW.KdJnsD AND T11.KdJrsn = NEW.KdJrsn;	
		--
		RETURN NEW;
	ELSIF (TG_OP = 'DELETE') THEN
		--
		UPDATE T11HTRN T11U
		SET Recs=COALESCE(T12.DTL,0)
		FROM 
		(
		SELECT T11.TgDocu, T11.KdJnsD, T11.KdJrsn, COUNT(T12.TgDocu) AS DTL
		FROM T11HTRN T11
		LEFT JOIN T12DTRN T12
		ON T12.TgDocu=T11.TgDocu AND T12.KdJnsD=T11.KdJnsD AND T12.KdJrsn=T11.KdJrsn
		WHERE OLD.TgDocu=T11.TgDocu AND OLD.KdJnsD=T11.KdJnsD AND OLD.KdJrsn=T11.KdJrsn
		GROUP BY T11.TgDocu, T11.KdJnsD, T11.KdJrsn		
		) T12
		--
		WHERE T12.TgDocu=T11U.TgDocu AND T12.KdJnsD=T11U.KdJnsD AND T12.KdJrsn=T11U.KdJrsn; 

		----- DELETE Summary Training
		DELETE FROM S07TRNG
		WHERE S07.Tanggal=OLD.TgDocu AND S07.NIP=OLD.NIP AND S07.KdJnsd=OLD.KdJnsD AND S07.KdJrsn=OLD.KdJrsn AND S07.NIP IS NOT NULL;	
		--
		RETURN OLD;
	END IF; 
    END;	
$$ LANGUAGE plpgsql;

-- Trigger Insert
CREATE TRIGGER TI_T12DTRN
AFTER INSERT ON T12DTRN
    FOR EACH ROW EXECUTE PROCEDURE FTA_T12DTRN();

-- Trigger Update
CREATE TRIGGER TU_T12DTRN
AFTER UPDATE ON T12DTRN
    FOR EACH ROW EXECUTE PROCEDURE FTA_T12DTRN();    

-- Trigger Delete
CREATE TRIGGER TD_T12DTRN
AFTER DELETE ON T12DTRN
    FOR EACH ROW EXECUTE PROCEDURE FTA_T12DTRN();    		
-------------------------------------------------------------
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TI_T12DTRN' AND type = 'TR')
-- -- DROP TRIGGER TI_T12DTRN
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TD_T12DTRN' AND type = 'TR')
-- -- DROP TRIGGER TD_T12DTRN
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TU_T12DTRN' AND type = 'TR')
-- -- DROP TRIGGER TU_T12DTRN
-- -- GO
-- -- --
-- -- /******
-- -- INSERT 
-- -- *******/
-- -- CREATE TRIGGER TI_T12DTRN
-- -- ON T12DTRN WITH ENCRYPTION FOR INSERT 
-- -- AS
-- -- --
-- -- UPDATE T11HTRN
-- -- SET Recs=ISNULL(Recs,0)+1
-- -- FROM INSERTED I,T11HTRN T11
-- -- WHERE I.TgDocu=T11.TgDocu AND I.KdJnsD=T11.KdJnsD AND I.KdJrsn=T11.KdJrsn
-- -- --
-- -- ----- Isi Summary Training
-- -- INSERT INTO S07TRNG
-- -- SELECT INS.TgDocu,INS.NIP,
-- -- 	   INS.KdJnsD,M25.Singkatan,
-- -- 	   INS.KdJrsn,M26.Singkatan,
-- -- 	   T11.KdLmbD,M27.Singkatan,
-- -- 	   T11.KdLksD,M28.Singkatan,
-- -- 	   T11.BulanTrng,T11.HariTrng,INS.Hasil,INS.Nilai,INS.BiayaSendiri,T11.Biaya,INS.NoIjaz,T11.UserID,CONVERT(CHAR(10),GETDATE(),121),CONVERT(CHAR(19),GETDATE(),120)
-- -- ---
-- -- FROM INSERTED INS
-- -- --
-- -- INNER JOIN T11HTRN T11
-- -- ON T11.TgDocu = INS.TgDocu AND T11.KdJnsD = INS.KdJnsD AND T11.KdJrsn = INS.KdJrsn
-- -- --
-- -- INNER JOIN M25JNSD M25
-- -- ON M25.Kode = INS.KdJnsD
-- -- --
-- -- INNER JOIN M26JRSN M26
-- -- ON M26.Kode = INS.KdJrsn
-- -- --
-- -- INNER JOIN M27LMBD M27
-- -- ON M27.Kode = T11.KdLmbD
-- -- --
-- -- INNER JOIN M28LKSD M28
-- -- ON M28.Kode = T11.KdLksD
-- -- ---
-- -- LEFT JOIN S07TRNG S07
-- -- ON S07.Tanggal=INS.TgDocu AND S07.NIP=INS.NIP AND S07.KdJnsd=INS.KdJnsD AND S07.KdJrsn=INS.KdJrsn
-- -- ---
-- -- WHERE S07.NIP IS NULL
-- -- 
-- -- GO
-- -- 
-- -- /******
-- -- DELETE
-- -- *******/
-- -- CREATE TRIGGER TD_T12DTRN
-- -- ON T12DTRN WITH ENCRYPTION FOR DELETE
-- -- AS
-- -- --
-- -- UPDATE T11HTRN
-- -- SET Recs=ISNULL(Recs,0)-1
-- -- FROM DELETED D,T11HTRN T11
-- -- WHERE D.TgDocu=T11.TgDocu AND D.KdJnsD=T11.KdJnsD AND D.KdJrsn=T11.KdJrsn
-- -- 
-- -- ----- DELETE Summary Training
-- -- DELETE S07TRNG
-- -- FROM DELETED DEL
-- -- ---
-- -- LEFT JOIN S07TRNG S07
-- -- ON S07.Tanggal=DEL.TgDocu AND S07.NIP=DEL.NIP AND S07.KdJnsd=DEL.KdJnsD AND S07.KdJrsn=DEL.KdJrsn
-- -- ---
-- -- WHERE S07.NIP IS NOT NULL
-- -- GO
-- -- 
-- -- /*****
-- -- UPDATE
-- -- ******/
-- -- CREATE TRIGGER TU_T12DTRN
-- -- ON T12DTRN WITH ENCRYPTION FOR UPDATE
-- -- AS
-- -- --
-- -- 
-- -- ----- DELETE Summary Training
-- -- DELETE S07TRNG
-- -- FROM DELETED DEL
-- -- ---
-- -- LEFT JOIN S07TRNG S07
-- -- ON S07.Tanggal=DEL.TgDocu AND S07.NIP=DEL.NIP AND S07.KdJnsd=DEL.KdJnsD AND S07.KdJrsn=DEL.KdJrsn
-- -- ---
-- -- WHERE S07.NIP IS NOT NULL
-- -- 
-- -- ----- Isi Summary Training
-- -- INSERT INTO S07TRNG
-- -- SELECT INS.TgDocu,INS.NIP,
-- -- 	   INS.KdJnsD,M25.Singkatan,
-- -- 	   INS.KdJrsn,M26.Singkatan,
-- -- 	   T11.KdLmbD,M27.Singkatan,
-- -- 	   T11.KdLksD,M28.Singkatan,
-- -- 	   T11.BulanTrng,T11.HariTrng,INS.Hasil,INS.Nilai,INS.BiayaSendiri,T11.Biaya,INS.NoIjaz,T11.UserID,CONVERT(CHAR(10),GETDATE(),121),CONVERT(CHAR(19),GETDATE(),120)
-- -- ---
-- -- FROM INSERTED INS
-- -- --
-- -- INNER JOIN T11HTRN T11
-- -- ON T11.TgDocu = INS.TgDocu AND T11.KdJnsD = INS.KdJnsD AND T11.KdJrsn = INS.KdJrsn
-- -- --
-- -- INNER JOIN M25JNSD M25
-- -- ON M25.Kode = INS.KdJnsD
-- -- --
-- -- INNER JOIN M26JRSN M26
-- -- ON M26.Kode = INS.KdJrsn
-- -- --
-- -- INNER JOIN M27LMBD M27
-- -- ON M27.Kode = T11.KdLmbD
-- -- --
-- -- INNER JOIN M28LKSD M28
-- -- ON M28.Kode = T11.KdLksD
-- -- ---
-- -- LEFT JOIN S07TRNG S07
-- -- ON S07.Tanggal=INS.TgDocu AND S07.NIP=INS.NIP AND S07.KdJnsd=INS.KdJnsD AND S07.KdJrsn=INS.KdJrsn
-- -- ---
-- -- WHERE S07.NIP IS NULL
-- -- 
-- -- 
-- -- 
-- -- GO
