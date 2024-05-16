DROP TRIGGER IF EXISTS TI_S02DGAJ ON S02DGAJ CASCADE  ; 
DROP TRIGGER IF EXISTS TD_S02DGAJ ON S02DGAJ CASCADE  ; 
DROP TRIGGER IF EXISTS TU_S02DGAJ ON S02DGAJ CASCADE  ; 
DROP FUNCTION IF EXISTS  FTA_S02DGAJ() CASCADE; 


CREATE OR REPLACE FUNCTION FTA_S02DGAJ()
  RETURNS TRIGGER AS
$$

    BEGIN
	   
	IF (TG_OP = 'INSERT') THEN
		UPDATE S01HGAJ S01U
		SET Recs=COALESCE(S02.DTL,0)
		FROM 
		(
		SELECT S01.tglPayr,S01.NIP, COUNT(S02.tglPayr) AS DTL
		FROM S01HGAJ S01
		LEFT JOIN S02DGAJ S02
		ON S02.tglPayr=S01.tglPayr AND S02.NIP=S01.NIP 
		WHERE NEW.tglPayr=S01.tglPayr AND NEW.NIP=S01.NIP 
		GROUP BY S01.tglPayr,S01.NIP
		) S02
		--
		WHERE S02.tglPayr=S01U.tglPayr AND S02.NIP=S01U.NIP ;	
		--
		RETURN NEW;
	ELSIF (TG_OP = 'UPDATE') THEN
		--
		RETURN NEW;
	ELSIF (TG_OP = 'DELETE') THEN
		UPDATE S01HGAJ S01U
		SET Recs=COALESCE(S02.DTL,0)
		FROM 
		(
		SELECT S01.tglPayr,S01.NIP, COUNT(S02.tglPayr) AS DTL
		FROM S01HGAJ S01
		LEFT JOIN S02DGAJ S02
		ON S02.tglPayr=S01.tglPayr AND S02.NIP=S01.NIP 
		WHERE OLD.tglPayr=S01.tglPayr AND OLD.NIP=S01.NIP 
		GROUP BY S01.tglPayr,S01.NIP
		) S02
		--
		WHERE S02.tglPayr=S01U.tglPayr AND S02.NIP=S01U.NIP ;	
		--
		RETURN OLD;
	END IF; 
    END;	
$$ LANGUAGE plpgsql;

-- Trigger Insert
CREATE TRIGGER TI_S02DGAJ
AFTER INSERT ON S02DGAJ
    FOR EACH ROW EXECUTE PROCEDURE FTA_S02DGAJ();

-- Trigger Update
CREATE TRIGGER TU_S02DGAJ
AFTER UPDATE ON S02DGAJ
    FOR EACH ROW EXECUTE PROCEDURE FTA_S02DGAJ();    

-- Trigger Delete
CREATE TRIGGER TD_S02DGAJ
AFTER DELETE ON S02DGAJ
    FOR EACH ROW EXECUTE PROCEDURE FTA_S02DGAJ();    		
-------------------------------------------------------------------	
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TI_S02DGAJ' AND type = 'TR')
-- -- DROP TRIGGER TI_S02DGAJ
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TD_S02DGAJ' AND type = 'TR')
-- -- DROP TRIGGER TD_S02DGAJ
-- -- GO
-- -- IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'TU_S02DGAJ' AND type = 'TR')
-- -- DROP TRIGGER TU_S02DGAJ
-- -- GO
-- -- --
-- -- /******
-- -- INSERT 
-- -- *******/
-- -- CREATE TRIGGER TI_S02DGAJ
-- -- ON S02DGAJ WITH ENCRYPTION FOR INSERT 
-- -- AS
-- -- --
-- -- DECLARE @TglPayr	DATETIME,
-- -- 	@NIP		CHAR(10),
-- -- 	@FlgDpPt	CHAR(1),
-- -- 	@KdDpPt		CHAR(4),
-- --         @EncNilai	VARCHAR(100),
-- --         @EncNilaiVal	VARCHAR(100),
-- -- 	@EncTotInc	DECIMAL(15,2),
-- --         @EncTotDed	DECIMAL(15,2),
-- --         @EncTakeHomePay	DECIMAL(15,2)
-- -- 
-- -- --
-- -- SELECT @TglPayr    =TglPayr,
-- --        @NIP        =NIP,
-- --        @FlgDpPt    =FlgDpPt,
-- --        @EncNilai   =EncNilai,
-- --        @EncNilaiVal=EncNilaiVal,
-- -- 	@KdDpPt=KdDpPt
-- -- FROM INSERTED
-- -- --
-- -- SELECT @EncTotInc=0,
-- --        @EncTotDed=0,
-- --        @EncTakeHomePay=0
-- -- 
-- -- --
-- -- SET @EncTotInc=CASE WHEN @FlgDpPt='D' THEN CONVERT(DECIMAL(15,2),dbo.fn_KPusat(@NIP,@EncNilai,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved')) ELSE 0 END
-- -- SET @EncTotDed=CASE WHEN @FlgDpPt='P' THEN CONVERT(DECIMAL(15,2),dbo.fn_KPusat(@NIP,@EncNilai,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved')) ELSE 0 END
-- -- SET @EncTakeHomePay=@EncTotInc-@EncTotDed
-- -- --
-- -- UPDATE S01HGAJ
-- -- SET Recs          =S02.DTL
-- -- FROM
-- -- (
-- -- SELECT tglPayr,NIP,DTL=COUNT(NIP)
-- -- FROM S02DGAJ
-- -- WHERE TglPayr=@TglPayr AND NIP=@NIP
-- -- GROUP BY tglPayr,NIP
-- -- )S02
-- -- --
-- -- INNER JOIN S01HGAJ S01
-- -- ON S02.TglPayr=S01.TglPayr AND S02.NIP=S01.NIP
-- -- --
-- -- GO
-- -- 
-- -- /******
-- -- DELETE
-- -- *******/
-- -- CREATE TRIGGER TD_S02DGAJ
-- -- ON S02DGAJ WITH ENCRYPTION FOR DELETE
-- -- AS
-- -- --
-- -- DECLARE @TglPayr	DATETIME,
-- -- 	@NIP		CHAR(10),
-- -- 	@FlgDpPt	CHAR(1),
-- --         @EncNilai	VARCHAR(100),
-- --         @EncNilaiVal	VARCHAR(100),
-- -- 	@EncTotInc	DECIMAL(15,2),
-- --         @EncTotDed	DECIMAL(15,2),
-- --         @EncTakeHomePay	DECIMAL(15,2)
-- -- --
-- -- SELECT @TglPayr    =TglPayr,
-- --        @NIP        =NIP,
-- --        @FlgDpPt    =FlgDpPt,
-- --        @EncNilai   =EncNilai,
-- --        @EncNilaiVal=EncNilaiVal
-- -- FROM DELETED
-- -- --
-- -- SELECT @EncTotInc=0,
-- --        @EncTotDed=0
-- -- --
-- -- SET @EncTotInc=CASE WHEN @FlgDpPt='D' THEN CONVERT(DECIMAL(15,2),dbo.fn_KPusat(@NIP,@EncNilai,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved')) ELSE 0 END
-- -- SET @EncTotDed=CASE WHEN @FlgDpPt='P' THEN CONVERT(DECIMAL(15,2),dbo.fn_KPusat(@NIP,@EncNilai,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved')) ELSE 0 END
-- -- SET @EncTakeHomePay=@EncTotInc-@EncTotDed
-- -- 
-- -- --
-- -- UPDATE S01HGAJ
-- -- SET Recs          =S02.DTL
-- -- FROM
-- -- (
-- -- SELECT tglPayr,NIP,DTL=COUNT(NIP)
-- -- FROM S02DGAJ
-- -- WHERE TglPayr=@TglPayr AND NIP=@NIP
-- -- GROUP BY tglPayr,NIP
-- -- )S02
-- -- --
-- -- INNER JOIN S01HGAJ S01
-- -- ON S02.TglPayr=S01.TglPayr AND S02.NIP=S01.NIP
-- -- --
-- -- GO
