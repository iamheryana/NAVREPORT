-----------BELUM KELAR
DROP TRIGGER IF EXISTS TI_T08ABSN ON T08ABSN CASCADE  ; 
DROP TRIGGER IF EXISTS TD_T08ABSN ON T08ABSN CASCADE  ; 
DROP TRIGGER IF EXISTS TU_T08ABSN ON T08ABSN CASCADE  ; 
DROP FUNCTION IF EXISTS  FTA_T08ABSN() CASCADE; 


CREATE OR REPLACE FUNCTION FTA_T08ABSN()
  RETURNS TRIGGER AS
$$
DECLARE l_S09_ID INTEGER; 
    BEGIN
	   
	IF (TG_OP = 'INSERT') THEN
		UPDATE S09HCUT S09U
		SET Pemakaian = COALESCE(PKI.Pakai,0)
		FROM
		(
		SELECT  T08.NIP,T08.CutiTahun,SUM((T08.JamAbsn/8)+T08.HariAbsn) AS Pakai
		FROM T08ABSN T08
		INNER JOIN M29JNSA M29
		ON M29.Kode=T08.KdAbsn
		WHERE M29.JnsCuti=1 
		GROUP BY T08.NIP,T08.CutiTahun
		) PKI
		INNER JOIN S09HCUT S09
		ON S09.NIP=PKI.NIP AND S09.Tahun=PKI.CutiTahun
		--
		WHERE S09U.NIP=NEW.NIP AND S09U.Tahun=NEW.CutiTahun
		RETURNING S09U.S09_ID INTO l_S09_ID; 	
----------- Summary Absensi ------------------------------
---------------------------- Update Data Summary Absensi
		UPDATE S06ABSH S06U
		SET JamAbsn  = COALESCE(S06U.JamAbsn,0)  + NEW.JamAbsn,
		    HariAbsn = COALESCE(S06U.HariAbsn,0) + NEW.HariAbsn
		WHERE S06U.Tanggal=NEW.TgDocu AND S06U.NIP=NEW.NIP AND S06U.KdAbsn=NEW.KdAbsn;
----
--------------------------  Isi Data Summary Absensi
		INSERT INTO S06ABSH(tanggal, nip, kdabsn, skabsn, jamabsn, hariabsn, postid, 
				    postdate, updtime, version, created_by, created_on, updated_by, updated_on)
		---
		VALUES(NEW.TgDocu,NEW.NIP,NEW.KdAbsn,
			(SELECT M29.Singkatan FROM M29JNSA M29 WHERE M29.Kode=NEW.KdAbsn),
			NEW.JamAbsn,NEW.HariAbsn,
			NEW.CREATED_BY, COALESCE(NEW.updated_on,NEW.created_on),COALESCE(NEW.updated_on,NEW.created_on), 
			NEW.VERSION, NEW.CREATED_BY, NEW.CREATED_ON, NEW.UPDATED_BY, NEW.UPDATED_ON) ; 
----
------------------------------- Summary Pemakaian Cuti ---------------------------------
----Update Summary Cuti
		UPDATE S0ADCUT S0AU
		SET Pemakaian = COALESCE(S0AU.Pemakaian,0) + INS.Pakai
		---
		FROM 
		(--INS
		SELECT NEW.TgDocu,NEW.NIP,NEW.KdAbsn,NEW.CutiTahun,
			   ROUND(NEW.JamAbsn/8,1)+NEW.HariAbsn AS PAKAI
		--FROM NEW 
		)INS
		---
		INNER JOIN M29JNSA M29
		ON M29.Kode=INS.KdAbsn
		---
		LEFT  JOIN S0ADCUT S0A
		ON S0A.NIP=INS.NIP AND S0A.Tahun=INS.CutiTahun AND 
		   S0A.BlnTgl = EXTRACT(MONTH FROM INS.TgDocu):: CHAR(2) || SUBSTR(EXTRACT(YEAR FROM INS.TgDocu):: CHAR(4) ,3,2) :: CHAR(2) AND 
		   S0A.KdCuti=INS.KdAbsn
		WHERE S0A.NIP IS NOT NULL AND M29.JnsCuti=1 AND 
		      S0A.NIP=S0AU.NIP AND S0A.Tahun=S0AU.Tahun AND 
		      S0A.BlnTgl = S0AU.BlnTgl AND 
		      S0A.KdCuti=S0AU.KdCuti; 
----
------------------  Isi Summary Cuti --------------
		INSERT INTO S0ADCUT(nip, tahun, blntgl, kdcuti, pemakaian, s09_id, version, 
            created_by, created_on, updated_by, updated_on)
		SELECT Q.NIP, Q.CutiTahun, Q.BlnThn, Q.KdAbsn, SUM(Q.Pakai), l_S09_ID, 0, 
			NEW.created_by, NEW.created_on, NEW.updated_by, NEW.updated_on 
		FROM 
		(
		SELECT INS.NIP, INS.CutiTahun, INS.BlnThn, INS.KdAbsn, INS.Pakai 
		FROM
		(
		SELECT NEW.NIP,NEW.CutiTahun,
		       EXTRACT(MONTH FROM NEW.TgDocu):: CHAR(2) || SUBSTR(EXTRACT(YEAR FROM NEW.TgDocu):: CHAR(4) ,3,2) :: CHAR(2) AS BlnThn,
		       NEW.KdAbsn,
		       ROUND(NEW.JamAbsn/8,1)+NEW.HariAbsn AS PAKAI 
		) INS 
		----
		--FROM NEW INS
		---
		INNER JOIN M29JNSA M29
		ON M29.Kode=INS.KdAbsn
		---
		LEFT  JOIN S0ADCUT S0A
		ON S0A.NIP=INS.NIP AND S0A.Tahun=INS.CutiTahun AND 
		   S0A.BlnTgl = INS.BlnThn AND 
		   S0A.KdCuti = INS.KdAbsn
		WHERE S0A.NIP IS NULL AND M29.JnsCuti=1
		) Q
		GROUP BY Q.NIP, Q.CutiTahun, Q.BlnThn, Q.KdAbsn; 
		----
		RETURN NEW;
		
	ELSIF (TG_OP = 'UPDATE') THEN		
		UPDATE S09HCUT S09U
		SET Pemakaian = COALESCE(PKI.Pakai,0)
		FROM
		(
		SELECT  T08.NIP,T08.CutiTahun,SUM((T08.JamAbsn/8)+T08.HariAbsn) AS Pakai
		FROM T08ABSN T08
		INNER JOIN M29JNSA M29
		ON M29.Kode=T08.KdAbsn
		WHERE M29.JnsCuti=1 
		GROUP BY T08.NIP,T08.CutiTahun
		) PKI
		INNER JOIN S09HCUT S09
		ON S09.NIP=PKI.NIP AND S09.Tahun=PKI.CutiTahun
		--
		WHERE S09U.NIP=NEW.NIP AND S09U.Tahun=NEW.CutiTahun
		RETURNING S09U.S09_ID INTO l_S09_ID; 	


		----------- Summary Absensi ------------------------------
		---------- Delete Summary Absensi ------------------------------
		----
		DELETE
		FROM S06ABSH S06
		WHERE S06.Tanggal=OLD.TgDocu AND S06.NIP=OLD.NIP AND S06.KdAbsn=OLD.KdAbsn;
		----
		----------- Isi Ulang Summary Absensi ------------------------------
		---------------------------- Update Data Summary Absensi
		UPDATE S06ABSH S06U
		SET JamAbsn  = COALESCE(S06U.JamAbsn,0) + INS.Jam,
			HariAbsn = COALESCE(S06U.HariAbsn,0)+ INS.Hari
		---
		FROM 
		(--INS
		SELECT NEW.TgDocu,NEW.NIP,NEW.KdAbsn,NEW.JamAbsn AS JAM, NEW.HariAbsn AS HARI
		--FROM NEW INS 
		)INS
		---
		LEFT  JOIN S06ABSH S06
		ON S06.Tanggal=INS.TgDocu AND S06.NIP=INS.NIP AND S06.KdAbsn=INS.KdAbsn
		WHERE S06.NIP IS NOT NULL AND 
			S06.Tanggal=S06U.Tanggal AND S06.NIP=S06U.NIP AND S06.KdAbsn=S06U.KdAbsn;
		----
		--------------------------  Isi Data Summary Absensi
		INSERT INTO S06ABSH(tanggal, nip, kdabsn, skabsn, jamabsn, hariabsn, postid, 
				    postdate, updtime, version, created_by, created_on, updated_by, updated_on)

		SELECT INS.TgDocu,INS.NIP,INS.KdAbsn,M29.Singkatan,INS.JamAbsn,INS.HariAbsn,
			INS.Userid, INS.POSTDATE, INS.POSTTIME,
			INS.VERSION, INS.CREATED_BY, INS.CREATED_ON, INS.UPDATED_BY, INS.UPDATED_ON
		FROM 	
		(
		SELECT NEW.TgDocu,NEW.NIP,NEW.KdAbsn,NEW.JamAbsn,NEW.HariAbsn,
			NEW.CREATED_BY AS USERID, COALESCE(NEW.updated_on,NEW.created_on) AS POSTDATE,
			COALESCE(NEW.updated_on,NEW.created_on) AS POSTTIME, 
			NEW.VERSION, NEW.CREATED_BY, NEW.CREATED_ON, NEW.UPDATED_BY, NEW.UPDATED_ON
		--FROM NEW INS
		) INS 
		---
		INNER JOIN M29JNSA M29
		ON M29.Kode=INS.KdAbsn
		---
		LEFT  JOIN S06ABSH S06
		ON S06.Tanggal=INS.TgDocu AND S06.NIP=INS.NIP AND S06.KdAbsn=INS.KdAbsn
		WHERE S06.NIP IS NULL;
		----
		------------------ Update Summary Cuti
		----Update(-)Summary Cuti
		UPDATE S0ADCUT S0AU
		SET Pemakaian = COALESCE(S0AU.Pemakaian,0) - DEL.Pakai
		---
		FROM 
		(--DEL
		SELECT OLD.TgDocu,OLD.NIP,OLD.KdAbsn,OLD.CutiTahun,
		       ROUND(OLD.JamAbsn/8,1)+OLD.HariAbsn AS PAKAI 
		--FROM OLD 
		)DEL
		---
		INNER JOIN M29JNSA M29
		ON M29.Kode=DEL.KdAbsn
		---
		LEFT  JOIN S0ADCUT S0A
		ON S0A.NIP=DEL.NIP AND S0A.Tahun=DEL.CutiTahun AND 
		   S0A.BlnTgl = EXTRACT(MONTH FROM DEL.TgDocu):: CHAR(2) || SUBSTR(EXTRACT(YEAR FROM DEL.TgDocu):: CHAR(4) ,3,2) :: CHAR(2) AND 
		   S0A.KdCuti=DEL.KdAbsn
		WHERE S0A.NIP IS NOT NULL AND M29.JnsCuti=1 AND 
		      S0A.NIP=S0AU.NIP AND S0A.Tahun=S0AU.Tahun AND 
		      S0A.BlnTgl = S0AU.BlnTgl AND 
		      S0A.KdCuti=S0AU.KdCuti;
		----         
		------------------------------- Summary Pemakaian Cuti ---------------------------------
		----Update Summary Cuti
		UPDATE S0ADCUT S0AU
		SET Pemakaian = COALESCE(S0AU.Pemakaian,0) + INS.Pakai
		---
		FROM 
		(--INS
		SELECT NEW.TgDocu,NEW.NIP,NEW.KdAbsn,NEW.CutiTahun,
		       ROUND(NEW.JamAbsn/8,1)+NEW.HariAbsn AS PAKAI 
		--FROM NEW 
		)INS
		---
		INNER JOIN M29JNSA M29
		ON M29.Kode=INS.KdAbsn
		---
		LEFT JOIN S0ADCUT S0A
		ON S0A.NIP=INS.NIP AND S0A.Tahun=INS.CutiTahun AND 
		   S0A.BlnTgl = EXTRACT(MONTH FROM INS.TgDocu):: CHAR(2) || SUBSTR(EXTRACT(YEAR FROM INS.TgDocu):: CHAR(4) ,3,2) :: CHAR(2) AND 
		   S0A.KdCuti=INS.KdAbsn
		WHERE S0A.NIP IS NOT NULL AND M29.JnsCuti=1 AND 
		      S0A.NIP=S0AU.NIP AND S0A.Tahun=S0AU.Tahun AND 
		      S0A.BlnTgl = S0AU.BlnTgl AND 
		      S0A.KdCuti=S0AU.KdCuti;
		----
		------------------  Isi Summary Cuti --------------
		INSERT INTO S0ADCUT(nip, tahun, blntgl, kdcuti, pemakaian, s09_id, version, 
					created_by, created_on, updated_by, updated_on)	
		SELECT INS.NIP,INS.CutiTahun,INS.BlnTgl,INS.KdAbsn,INS.PAKAI,l_S09_ID, 0, 
			NEW.created_by, NEW.created_on, NEW.updated_by, NEW.updated_on 
		FROM
		(
		SELECT NEW.NIP,NEW.CutiTahun,
			EXTRACT(MONTH FROM NEW.TgDocu):: CHAR(2) || SUBSTR(EXTRACT(YEAR FROM NEW.TgDocu):: CHAR(4) ,3,2) :: CHAR(2) AS BlnTgl,
			NEW.KdAbsn,
			   ROUND(NEW.JamAbsn/8,1)+NEW.HariAbsn AS PAKAI 
		----
		--FROM NEW INS
		) INS 
		---
		INNER JOIN M29JNSA M29
		ON M29.Kode=INS.KdAbsn
		---
		LEFT  JOIN S0ADCUT S0A
		ON S0A.NIP=INS.NIP AND S0A.Tahun=INS.CutiTahun AND 
		   S0A.BlnTgl = INS.BlnTgl AND 
		   S0A.KdCuti=INS.KdAbsn
		WHERE S0A.NIP IS NULL AND M29.JnsCuti=1;
		--
		RETURN NEW;
	ELSIF (TG_OP = 'DELETE') THEN	
		UPDATE S09HCUT S09U
		SET Pemakaian = COALESCE(PKI.Pakai,0)
		FROM
		(
		SELECT  T08.NIP,T08.CutiTahun,SUM((T08.JamAbsn/8)+T08.HariAbsn) AS Pakai
		FROM T08ABSN T08
		INNER JOIN M29JNSA M29
		ON M29.Kode=T08.KdAbsn
		WHERE M29.JnsCuti=1 
		GROUP BY T08.NIP,T08.CutiTahun
		) PKI
		INNER JOIN S09HCUT S09
		ON S09.NIP=PKI.NIP AND S09.Tahun=PKI.CutiTahun
		--
		WHERE S09U.NIP=OLD.NIP AND S09U.Tahun=OLD.CutiTahun; 					
		----------- Summary Absensi ------------------------------
		---------- Delete Summary Absensi ------------------------------
		----
		DELETE FROM S06ABSH S06
		WHERE S06.Tanggal=DEL.TgDocu AND S06.NIP=DEL.NIP AND S06.KdAbsn=DEL.KdAbsn;
		----
		------------------ Update Summary Cuti
		----Update(-)Summary Cuti
		UPDATE S0ADCUT
		SET Pemakaian = COALESCE(Pemakaian,0) - DEL.Pakai
		---
		FROM 
		(--DEL
		SELECT TgDocu,NIP,KdAbsn,CutiTahun,
		       ROUND(JamAbsn/8,1)+HariAbsn AS PAKAI 
		FROM OLD 
		)DEL
		---
		INNER JOIN M29JNSA M29
		ON M29.Kode=DEL.KdAbsn
		---
		LEFT  JOIN S0ADCUT S0A
		ON S0A.NIP=DEL.NIP AND S0A.Tahun=DEL.CutiTahun AND 
		   S0A.BlnTgl = EXTRACT(MONTH FROM DEL.TgDocu):: CHAR(2) || SUBSTR(EXTRACT(YEAR FROM DEL.TgDocu):: CHAR(4) ,3,2) :: CHAR(2) AND 
		   S0A.KdCuti=DEL.KdAbsn
		WHERE S0A.NIP IS NOT NULL AND M29.JnsCuti=1;

		--- Hapus Summ Cuti yang nilainya nol
		DELETE FROM S0ADCUT
		WHERE NIP=OLD.NIP AND Pemakaian<=0;		
		--
		RETURN OLD;
	END IF; 
    END;	
$$ LANGUAGE plpgsql;

-- Trigger Insert
CREATE TRIGGER TI_T08ABSN
AFTER INSERT ON T08ABSN
    FOR EACH ROW EXECUTE PROCEDURE FTA_T08ABSN();

-- Trigger Update
CREATE TRIGGER TU_T08ABSN
AFTER UPDATE ON T08ABSN
    FOR EACH ROW EXECUTE PROCEDURE FTA_T08ABSN();    

-- Trigger Delete
CREATE TRIGGER TD_T08ABSN
AFTER DELETE ON T08ABSN
    FOR EACH ROW EXECUTE PROCEDURE FTA_T08ABSN();    

/*****
DELETE
******/
-- CREATE TRIGGER TD_T08ABSN
-- ON T08ABSN WITH ENCRYPTION FOR DELETE
-- AS
-- --
-- DECLARE @NIP		CHAR(10),
-- 		@CutiTahun	DECIMAL(4,0)
-- --
-- SELECT 	@NIP=NIP,
-- 		@CutiTahun=CutiTahun
-- FROM DELETED
-- --
-- UPDATE S09HCUT
-- SET Pemakaian = COALESCE(PKI.Pakai,0)
-- FROM S09HCUT S09
-- LEFT JOIN
-- 	(
-- 	SELECT  NIP,CutiTahun,Pakai=SUM((JamAbsn/8)+HariAbsn)
-- 	  FROM T08ABSN T08
-- 	 INNER JOIN M29JNSA M29
-- 	    ON M29.Kode=T08.KdAbsn
-- 	 WHERE M29.JnsCuti=1 
-- 	 GROUP BY NIP,CutiTahun
--     )PKI
-- ON S09.NIP=PKI.NIP AND S09.Tahun=PKI.CutiTahun
-- --
-- WHERE S09.NIP=@NIP AND S09.Tahun=@CutiTahun
-- ---
-- ---------- Delete Summary Absensi ------------------------------
-- ----
-- DELETE S06ABSH
-- FROM S06ABSH S06
-- INNER JOIN DELETED DEL
-- ON S06.Tanggal=DEL.TgDocu AND S06.NIP=DEL.NIP AND S06.KdAbsn=DEL.KdAbsn
-- ----
-- ------------------ Update Summary Cuti
-- ----Update(-)Summary Cuti
-- UPDATE S0ADCUT
-- SET Pemakaian = COALESCE(Pemakaian,0) - DEL.Pakai
-- ---
-- FROM 
-- (--DEL
-- SELECT TgDocu,NIP,KdAbsn,CutiTahun,
-- 	   Pakai=ROUND(JamAbsn/8,1)+HariAbsn
-- FROM DELETED 
-- )DEL
-- ---
-- INNER JOIN M29JNSA M29
-- ON M29.Kode=DEL.KdAbsn
-- ---
-- LEFT  JOIN S0ADCUT S0A
-- ON S0A.NIP=DEL.NIP AND S0A.Tahun=DEL.CutiTahun AND 
--    S0A.BlnTgl = SUBSTRING(CONVERT(CHAR(6),DEL.TgDocu,112),5,2)+SUBSTRING(CONVERT(CHAR(6),DEL.TgDocu,112),3,2) AND 
--    S0A.KdCuti=DEL.KdAbsn
-- WHERE S0A.NIP IS NOT NULL AND M29.JnsCuti=1
-- ----         
-- --- Hapus Summ Cuti yang nilainya nol
-- DELETE S0ADCUT
-- WHERE NIP=@NIP AND Pemakaian<=0
-- 
-- GO
-- 
-- /*****
-- UPDATE
-- ******/
-- CREATE TRIGGER TU_T08ABSN
-- ON T08ABSN WITH ENCRYPTION FOR UPDATE
-- AS
-- --
-- DECLARE @NIP		CHAR(10),
-- 		@CutiTahun	DECIMAL(4,0)
-- --
-- SELECT 	@NIP=NIP,
-- 		@CutiTahun=CutiTahun
-- FROM INSERTED
-- --
-- UPDATE S09HCUT
-- SET Pemakaian = COALESCE(PKI.Pakai,0)
-- FROM
-- (
-- SELECT  NIP,CutiTahun,Pakai=SUM((JamAbsn/8)+HariAbsn)
-- FROM T08ABSN T08
-- INNER JOIN M29JNSA M29
-- ON M29.Kode=T08.KdAbsn
-- WHERE M29.JnsCuti=1 
-- GROUP BY NIP,CutiTahun
-- )PKI
-- INNER JOIN S09HCUT S09
-- ON S09.NIP=PKI.NIP AND S09.Tahun=PKI.CutiTahun
-- --
-- WHERE S09.NIP=@NIP AND S09.Tahun=@CutiTahun
-- ----------- Summary Absensi ------------------------------
-- ---------- Delete Summary Absensi ------------------------------
-- ----
-- DELETE S06ABSH
-- FROM S06ABSH S06
-- INNER JOIN DELETED DEL
-- ON S06.Tanggal=DEL.TgDocu AND S06.NIP=DEL.NIP AND S06.KdAbsn=DEL.KdAbsn
-- ----
-- ----------- Isi Ulang Summary Absensi ------------------------------
-- ---------------------------- Update Data Summary Absensi
-- UPDATE S06ABSH 
-- SET JamAbsn  = COALESCE(JamAbsn,0) + INS.Jam,
-- 	HariAbsn = COALESCE(HariAbsn,0)+ INS.Hari
-- ---
-- FROM 
-- (--INS
-- SELECT INS.TgDocu,INS.NIP,INS.KdAbsn,Jam=INS.JamAbsn,Hari=INS.HariAbsn
-- FROM INSERTED INS 
-- )INS
-- ---
-- LEFT  JOIN S06ABSH S06
-- ON S06.Tanggal=INS.TgDocu AND S06.NIP=INS.NIP AND S06.KdAbsn=INS.KdAbsn
-- WHERE S06.NIP IS NOT NULL
-- ----
-- --------------------------  Isi Data Summary Absensi
-- INSERT INTO S06ABSH
-- ---
-- SELECT INS.TgDocu,INS.NIP,INS.KdAbsn,M29.Singkatan,INS.JamAbsn,INS.HariAbsn,INS.Userid,CONVERT(CHAR(10),GETDATE(),121),CONVERT(CHAR(19),GETDATE(),120)
-- FROM INSERTED INS
-- ---
-- INNER JOIN M29JNSA M29
-- ON M29.Kode=INS.KdAbsn
-- ---
-- LEFT  JOIN S06ABSH S06
-- ON S06.Tanggal=INS.TgDocu AND S06.NIP=INS.NIP AND S06.KdAbsn=INS.KdAbsn
-- WHERE S06.NIP IS NULL
-- ----
-- ------------------ Update Summary Cuti
-- ----Update(-)Summary Cuti
-- UPDATE S0ADCUT
-- SET Pemakaian = COALESCE(Pemakaian,0) - DEL.Pakai
-- ---
-- FROM 
-- (--DEL
-- SELECT TgDocu,NIP,KdAbsn,CutiTahun,
-- 	   Pakai=ROUND(JamAbsn/8,1)+HariAbsn
-- FROM DELETED 
-- )DEL
-- ---
-- INNER JOIN M29JNSA M29
-- ON M29.Kode=DEL.KdAbsn
-- ---
-- LEFT  JOIN S0ADCUT S0A
-- ON S0A.NIP=DEL.NIP AND S0A.Tahun=DEL.CutiTahun AND 
--    S0A.BlnTgl = SUBSTRING(CONVERT(CHAR(6),DEL.TgDocu,112),5,2)+SUBSTRING(CONVERT(CHAR(6),DEL.TgDocu,112),3,2) AND 
--    S0A.KdCuti=DEL.KdAbsn
-- WHERE S0A.NIP IS NOT NULL AND M29.JnsCuti=1
-- ----         
-- ------------------------------- Summary Pemakaian Cuti ---------------------------------
-- ----Update Summary Cuti
-- UPDATE S0ADCUT
-- SET Pemakaian = COALESCE(Pemakaian,0) + INS.Pakai
-- ---
-- FROM 
-- (--INS
-- SELECT TgDocu,NIP,KdAbsn,CutiTahun,
-- 	   Pakai=ROUND(JamAbsn/8,1)+HariAbsn
-- FROM INSERTED 
-- )INS
-- ---
-- INNER JOIN M29JNSA M29
-- ON M29.Kode=INS.KdAbsn
-- ---
-- LEFT JOIN S0ADCUT S0A
-- ON S0A.NIP=INS.NIP AND S0A.Tahun=INS.CutiTahun AND 
--    S0A.BlnTgl = SUBSTRING(CONVERT(CHAR(6),INS.TgDocu,112),5,2)+SUBSTRING(CONVERT(CHAR(6),INS.TgDocu,112),3,2) AND 
--    S0A.KdCuti=INS.KdAbsn
-- WHERE S0A.NIP IS NOT NULL AND M29.JnsCuti=1
-- ----
-- ------------------  Isi Summary Cuti --------------
-- INSERT INTO S0ADCUT
-- SELECT INS.NIP,INS.CutiTahun,SUBSTRING(CONVERT(CHAR(6),INS.TgDocu,112),5,2)+SUBSTRING(CONVERT(CHAR(6),INS.TgDocu,112),3,2),INS.KdAbsn,
-- 	   Pakai=ROUND(INS.JamAbsn/8,1)+INS.HariAbsn
-- ----
-- FROM INSERTED INS
-- ---
-- INNER JOIN M29JNSA M29
-- ON M29.Kode=INS.KdAbsn
-- ---
-- LEFT  JOIN S0ADCUT S0A
-- ON S0A.NIP=INS.NIP AND S0A.Tahun=INS.CutiTahun AND 
--    S0A.BlnTgl = SUBSTRING(CONVERT(CHAR(6),INS.TgDocu,112),5,2)+SUBSTRING(CONVERT(CHAR(6),INS.TgDocu,112),3,2) AND 
--    S0A.KdCuti=INS.KdAbsn
-- WHERE S0A.NIP IS NULL AND M29.JnsCuti=1
-- ----
-- GO


