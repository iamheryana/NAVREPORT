DROP TRIGGER IF EXISTS TI_T06DPIU ON T06DPIU CASCADE  ; 
DROP TRIGGER IF EXISTS TD_T06DPIU ON T06DPIU CASCADE  ; 
DROP TRIGGER IF EXISTS TU_T06DPIU ON T06DPIU CASCADE  ; 
DROP FUNCTION IF EXISTS  FTA_T06DPIU() CASCADE; 


CREATE OR REPLACE FUNCTION FTA_T06DPIU()
  RETURNS TRIGGER AS
$$

    BEGIN
	   
	IF (TG_OP = 'INSERT') THEN
		UPDATE T05HPIU T05U
		SET TtlAngsPok=COALESCE(T06.JmlAngs,0),
		    TtlAngsBun=COALESCE(T06.JmlBunga,0),
		    Recs      =COALESCE(T06.DTL,0)
		--
		FROM 
		(
		SELECT T05.NIP,T05.KdJnsP,T05.TgDoku,
			SUM(T06.JmlAngs) AS JmlAngs,
			SUM(T06.JmlBunga) AS JmlBunga,
			COUNT(T06.NIP) AS DTL
		FROM T05HPIU T05 
		LEFT JOIN T06DPIU T06 
		ON T05.NIP=T06.NIP AND T05.KdJnsP=T06.KdJnsP AND T05.TgDoku=T06.TgDoku
		WHERE NEW.NIP=T05.NIP AND NEW.KdJnsP=T05.KdJnsP AND NEW.TgDoku=T05.TgDoku
		GROUP BY T05.NIP,T05.KdJnsP,T05.TgDoku
		) T06
		--
		WHERE T05U.NIP=T06.NIP AND T05U.KdJnsP=T06.kdJnsP AND T05U.TgDoku=T06.TgDoku; 

		--
		RETURN NEW;
	ELSIF (TG_OP = 'UPDATE') THEN
		UPDATE T05HPIU T05U
		SET TtlAngsPok=COALESCE(T06.JmlAngs,0),
		    TtlAngsBun=COALESCE(T06.JmlBunga,0),
		    Recs      =COALESCE(T06.DTL,0)
		--
		FROM 
		(
		SELECT T05.NIP,T05.KdJnsP,T05.TgDoku,
			SUM(T06.JmlAngs) AS JmlAngs,
			SUM(T06.JmlBunga) AS JmlBunga,
			COUNT(T06.NIP) AS DTL
		FROM T05HPIU T05 
		LEFT JOIN T06DPIU T06 
		ON T05.NIP=T06.NIP AND T05.KdJnsP=T06.KdJnsP AND T05.TgDoku=T06.TgDoku
		WHERE OLD.NIP=T05.NIP AND OLD.KdJnsP=T05.KdJnsP AND OLD.TgDoku=T05.TgDoku
		GROUP BY T05.NIP,T05.KdJnsP,T05.TgDoku
		) T06
		--
		WHERE T05U.NIP=T06.NIP AND T05U.KdJnsP=T06.kdJnsP AND T05U.TgDoku=T06.TgDoku; 
	
		--
		RETURN NEW;
	ELSIF (TG_OP = 'DELETE') THEN
		UPDATE T05HPIU T05U
		SET TtlAngsPok=COALESCE(T06.JmlAngs,0),
		    TtlAngsBun=COALESCE(T06.JmlBunga,0),
		    Recs      =COALESCE(T06.DTL,0)
		--
		FROM 
		(
		SELECT T05.NIP,T05.KdJnsP,T05.TgDoku,
			SUM(T06.JmlAngs) AS JmlAngs,
			SUM(T06.JmlBunga) AS JmlBunga,
			COUNT(T06.NIP) AS DTL
		FROM T05HPIU T05 
		LEFT JOIN T06DPIU T06 
		ON T05.NIP=T06.NIP AND T05.KdJnsP=T06.KdJnsP AND T05.TgDoku=T06.TgDoku
		WHERE OLD.NIP=T05.NIP AND OLD.KdJnsP=T05.KdJnsP AND OLD.TgDoku=T05.TgDoku
		GROUP BY T05.NIP,T05.KdJnsP,T05.TgDoku
		) T06
		--
		WHERE T05U.NIP=T06.NIP AND T05U.KdJnsP=T06.kdJnsP AND T05U.TgDoku=T06.TgDoku; 	
		--
		RETURN OLD;
	END IF; 
    END;	
$$ LANGUAGE plpgsql;

-- Trigger Insert
CREATE TRIGGER TI_T06DPIU
AFTER INSERT ON T06DPIU
    FOR EACH ROW EXECUTE PROCEDURE FTA_T06DPIU();

-- Trigger Update
CREATE TRIGGER TU_T06DPIU
AFTER UPDATE ON T06DPIU
    FOR EACH ROW EXECUTE PROCEDURE FTA_T06DPIU();    

-- Trigger Delete
CREATE TRIGGER TD_T06DPIU
AFTER DELETE ON T06DPIU
    FOR EACH ROW EXECUTE PROCEDURE FTA_T06DPIU();    		
-------------------------------------------------------------------

-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TI_T06DPIU' AND type = 'TR')
-- -- DROP TRIGGER TI_T06DPIU
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TD_T06DPIU' AND type = 'TR')
-- -- DROP TRIGGER TD_T06DPIU
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TU_T06DPIU' AND type = 'TR')
-- -- DROP TRIGGER TU_T06DPIU
-- -- GO
-- -- 
-- -- /*****
-- -- INSERT
-- -- ******/
-- -- CREATE TRIGGER TI_T06DPIU
-- -- ON T06DPIU WITH ENCRYPTION FOR INSERT 
-- -- AS
-- -- --
-- -- DECLARE @NIP		CHAR(10),
-- -- 	@KdJnsP 	CHAR(4),
-- -- 	@TgDoku 	DATETIME
-- -- --
-- -- SELECT  @NIP=NIP,
-- -- 	@KdJnsP=kdJnsP,
-- -- 	@TgDoku=TgDoku
-- -- FROM INSERTED
-- -- --
-- -- UPDATE T05HPIU
-- -- SET TtlAngsPok=ISNULL(T06.JmlAngs,0),
-- --     TtlAngsBun=ISNULL(T06.JmlBunga,0),
-- --     Recs      =ISNULL(T06.DTL,0)
-- -- --
-- -- FROM T05HPIU T05
-- -- --
-- -- LEFT JOIN
-- -- (
-- -- SELECT NIP,KdJnsP,TgDoku,JmlAngs=SUM(JmlAngs),JmlBunga=SUM(JmlBunga),DTL=COUNT(NIP)
-- -- FROM T06DPIU
-- -- GROUP BY NIP,KdJnsP,TgDoku
-- -- ) T06
-- -- --
-- -- ON T05.NIP=T06.NIP AND T05.KdJnsP=T06.KdJnsP AND T05.TgDoku=T06.TgDoku
-- -- --
-- -- WHERE @NIP=T05.NIP AND @KdJnsP=T05.kdJnsP AND @TgDoku=T05.TgDoku
-- -- --
-- -- GO
-- -- 
-- -- /*****
-- -- DELETE
-- -- ******/
-- -- CREATE TRIGGER TD_T06DPIU
-- -- ON T06DPIU WITH ENCRYPTION FOR DELETE
-- -- AS
-- -- --
-- -- DECLARE @NIP		CHAR(10),
-- -- 	@KdJnsP 	CHAR(4),
-- -- 	@TgDoku 	DATETIME
-- -- --
-- -- SELECT  @NIP=NIP,
-- -- 	@KdJnsP=kdJnsP,
-- -- 	@TgDoku=TgDoku
-- -- FROM DELETED
-- -- --
-- -- UPDATE T05HPIU
-- -- SET TtlAngsPok=ISNULL(T06.JmlAngs,0),
-- --     TtlAngsBun=ISNULL(T06.JmlBunga,0),
-- --     Recs      =ISNULL(T06.DTL,0)
-- -- --
-- -- FROM T05HPIU T05
-- -- --
-- -- LEFT JOIN
-- -- (
-- -- SELECT NIP,KdJnsP,TgDoku,JmlAngs=SUM(JmlAngs),JmlBunga=SUM(JmlBunga),DTL=COUNT(NIP)
-- -- FROM T06DPIU
-- -- GROUP BY NIP,KdJnsP,TgDoku
-- -- ) T06
-- -- --
-- -- ON T06.NIP=T05.NIP AND T05.KdJnsP=T06.KdJnsP AND T05.TgDoku=T06.TgDoku
-- -- --
-- -- WHERE @NIP=T05.NIP AND @KdJnsP=T05.kdJnsP AND @TgDoku=T05.TgDoku
-- -- --
-- -- GO
-- -- 
-- -- /*****
-- -- UPDATE
-- -- ******/
-- -- CREATE TRIGGER TU_T06DPIU
-- -- ON T06DPIU WITH ENCRYPTION FOR UPDATE
-- -- AS
-- -- --
-- -- DECLARE @NIP		CHAR(10),
-- -- 	@KdJnsP 	CHAR(4),
-- -- 	@TgDoku 	DATETIME
-- -- --
-- -- SELECT  @NIP=NIP,
-- -- 	@KdJnsP=kdJnsP,
-- -- 	@TgDoku=TgDoku
-- -- FROM DELETED
-- -- --
-- -- UPDATE T05HPIU
-- -- SET TtlAngsPok=T06.JmlAngs,
-- --     TtlAngsBun=T06.JmlBunga,
-- --     Recs      =T06.DTL
-- -- FROM
-- -- (
-- -- SELECT NIP,KdJnsP,TgDoku,JmlAngs=SUM(JmlAngs),JmlBunga=SUM(JmlBunga),DTL=COUNT(NIP)
-- -- FROM T06DPIU
-- -- GROUP BY NIP,KdJnsP,TgDoku
-- -- ) T06
-- -- --
-- -- INNER JOIN T05HPIU T05
-- -- ON T06.NIP=T05.NIP AND T05.KdJnsP=T06.KdJnsP AND T05.TgDoku=T06.TgDoku
-- -- --
-- -- WHERE @NIP=T05.NIP AND @KdJnsP=T05.kdJnsP AND @TgDoku=T05.TgDoku
-- -- --
-- -- 
-- -- GO
