/****************************************
Name sprocs : P_UTHR
Create by   : Rudy
Date        : 04-12-2001
Description : Proses Unpost THR
Call From   : Main sprocs
Sub sprocs  : -
*****************************************/
--
DROP FUNCTION P_UTHR(l_TglProses    DATE,
			l_LokasiFr     	VARCHAR(4),
			l_LokasiTo     	VARCHAR(4),
			l_NIPFr    	VARCHAR(10),
			l_NIPTo    	VARCHAR(10),
			l_UserID   	VARCHAR(12),
			OUT l_TableName1   VARCHAR(8),
			OUT l_mlValid  	INT,
			l_Usr_ID   	INT);
--
CREATE FUNCTION P_UTHR(l_TglProses    DATE,
			l_LokasiFr     	VARCHAR(4),
			l_LokasiTo     	VARCHAR(4),
			l_NIPFr    	VARCHAR(10),
			l_NIPTo    	VARCHAR(10),
			l_UserID   	VARCHAR(12),
			OUT l_TableName1   VARCHAR(8),
			OUT l_mlValid  	INT,
			l_Usr_ID   	INT)

AS $$ 
--
DECLARE l_FZ1FlgPiut VARCHAR(1);    l_FZ1FlgLemb VARCHAR(1);
	l_FZ1TunjIst DECIMAL(5,2);  l_FZ1TunjAnk DECIMAL(5,2);
	l_FZ1FlgGlng VARCHAR(1);    l_VarRound   INT;
	l_mnHari     DECIMAL(4,1);  l_Tgl_Akhir      DATE;
	l_M15NIP     VARCHAR(10);   l_M15TglKeluar   DATE;
	l_M15KdCaba  VARCHAR(4);    l_M15KdKlas  VARCHAR(4);
	l_M15PrdTetap    DATE;      l_M15KdArea  VARCHAR(4);
	l_M15KdUUsa  VARCHAR(4);    l_M15KdUKer  VARCHAR(8);
	l_V_Round    INT;           l_FlagTHR    VARCHAR(1);
	l_Prs_OK     VARCHAR(1);    l_FlagM      VARCHAR(1);
	l_FlgGGol    VARCHAR(1);    l_Err_Rid    INT;
	l_LastJPjk   VARCHAR(1);    l_Harian     INT;    
	l_PrdAngs    DATE;   	    l_Kontrak    INT;
	l_JnsPajak   VARCHAR(1);    l_S05TglPosting  DATE;
	l_S05FlagTHR VARCHAR(1);    l_T01TgDocu      DATE;
	l_Awal       DATE;          l_Akhir          DATE;
	l_Created    INT;           l_M15Nama    VARCHAR(25);
	l_LOOP_M15   REFCURSOR;    l_LOOP_T01   REFCURSOR;
	l_DataValid  VARCHAR(2);           l_M15TglMasuk    DATE;
	l_M47TglAwal	DATE;
	l_M47TglAkhir	DATE; 
	l_T19FlagProses	VARCHAR(1);

BEGIN         
---- Setting Nilai Awal
l_FZ1FlgPiut  := '';  l_FZ1FlgLemb  := '';
l_FZ1TunjIst  := 0;   l_FZ1TunjAnk  := 0;
l_FZ1FlgGlng  := '';  l_VarRound    := 0;
l_mnHari      := 0;   l_M15NIP      := '';
l_M15KdCaba   := '';  l_M15KdKlas   := '';
l_M15KdArea   := '';  l_M15KdUUsa   := '';
l_M15KdUKer   := '';  l_V_Round     := 0;
l_FlagTHR     := '';  l_Prs_OK      := '';
l_FlagM       := '';  l_FlgGGol     := '';
l_Err_Rid     := 0;   l_LastJPjk    := '';
l_Harian      := 0;   l_Kontrak     := 0;
l_JnsPajak    := '';  l_S05FlagTHR  := ''; 
l_T19FlagProses:='';

-- Panggil SProc untuk Bentuk File Validasi 
SELECT * FROM P_CREATE_TEMP_ONLIST ('W0B') INTO l_TableName1, l_Created;--,	  
--EXEC P_CREATE_TEMP_ONLIST 'W0B',      
--              l_TableName1 OUTPUT,
--              l_Created    OUTPUT
--
IF l_Created=0 THEN 
   RETURN; 
END IF ; 
--*
l_mlValid := 1; 
-- SET ENVEROMENT
--Ambil Var di FZ1FLDA
SELECT CASE EXTRACT(MONTH FROM l_TglProses) WHEN 1 THEN  Hari01
				WHEN 2 THEN  Hari02
				WHEN 3 THEN  Hari03
				WHEN 4 THEN  Hari04
				WHEN 5 THEN  Hari05
				WHEN 6 THEN  Hari06
				WHEN 7 THEN  Hari07
				WHEN 8 THEN  Hari08
				WHEN 9 THEN  Hari09
				WHEN 10 THEN Hari10
				WHEN 11 THEN Hari11
				WHEN 12 THEN Hari12  END,
		FlgPiut,     FlgLemb,      VarRound,   TunjIst,      TunjAnk
INTO l_mnHari, l_FZ1FlgPiut, l_FZ1FlgLemb, l_VarRound, l_FZ1TunjIst, l_FZ1TunjAnk   
FROM FZ1FLDA ;
--
-- Ambil Nilai Rounding Data !!
l_V_Round := CASE l_VarRound
                  WHEN '1' THEN -1
                  WHEN '2' THEN -2
                  WHEN '3' THEN -3
                  ELSE 1000
               END ; 

-- Mengelola Range Cabang+Golongan dengan Security-nya
/*
IF l_FCAB>=l_LokasiFr AND l_TCAB<=l_LokasiTo
   BEGIN
      SELECT l_LokasiFr=l_FCAB,
         l_LokasiTo=l_TCAB    
   END  
ELSE IF l_FCAB<l_LokasiFr AND l_TCAB<=l_LokasiTo
   BEGIN
      SELECT l_LokasiTo=l_TCAB    
   END  
ELSE IF l_FCAB>=l_LokasiFr AND l_TCAB>l_LokasiTo
   BEGIN
      SELECT l_LokasiFr=l_FCAB
   END  
ELSE IF l_FCAB<l_LokasiFr AND l_TCAB>l_LokasiTo
   BEGIN
      SELECT l_LokasiFr=l_FCAB
   END  
--
*/

-- Parameter Lembur dari M47PRLR
SELECT TglAwal,      TglAkhir
INTO   l_M47TglAwal, l_M47TglAkhir
FROM M47PRLR
WHERE TglProses<=l_TglProses ; 

-- Looping Master Karyawan
OPEN l_LOOP_M15 FOR SELECT M15.NIP, M15.Nama, M15.TglKeluar, M15.KdCaba, M15.KdKlas, M15.PrdTetap, M15.KdArea,
				 M15.KdUUsa, M15.KdUKer, M15.TglMasuk
			FROM M15PEGA M15
			INNER JOIN (SELECT * FROM fn_SECLOGIN(l_usr_id)) VSL 
			ON M15.kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
			WHERE ( M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
			      ( M15.KdCaba BETWEEN l_LokasiFr AND l_LokasiTo);-- AND
--			      (KdGlng BETWEEN l_FGOL AND l_TGOL);
LOOP 
	FETCH l_LOOP_M15 
	INTO l_M15NIP,l_M15Nama,l_M15TglKeluar,l_M15KdCaba,l_M15KdKlas,l_M15PrdTetap,l_M15KdArea,l_M15KdUUsa,l_M15KdUKer,l_M15Tglmasuk ; 

	IF NOT FOUND THEN
		EXIT ;
	END IF;

--        IF l_M15KdCaba BETWEEN l_FCAB AND l_TCAB THEN 
--        BEGIN
	--
		l_Tgl_Akhir := NULL; 
		l_FlagTHR   := ' ';

		SELECT TglPosting,  FlagTHR
		INTO   l_Tgl_Akhir, l_FlagTHR  
		FROM S05PSTD 
		WHERE NIP = l_M15NIP
		ORDER BY NIP,TglPosting DESC ;

	---------------------------------------------------
	 -- Validasi Data     
		select * FROM fn_Validasi_Unpost(l_FlagTHR, l_M15NIP, l_TglProses, l_M15TglMasuk, l_M15TglKeluar, l_Tgl_Akhir, 'T') 
		INTO l_DataValid ;
	  
		IF l_DataValid<>'0' THEN 
		    BEGIN
		      l_mlValid := 0 ; 
		      l_Prs_OK  := 'T' ;
		      PERFORM P_CLSTEMP(l_M15NIP, l_M15Nama, l_Tgl_Akhir,l_DataValid,l_TableName1) ; 
		    END ;        
		ELSE 
		    BEGIN
		      -- Clear Summary File_1
		      -- Hapus File S01 dan S02
		      l_LastJPjk := '' ;
		      --
		      SELECT JnsPajak
		      INTO   l_LastJPjk
		      FROM S01HGAJ
		      WHERE TglPayr = l_TglProses AND NIP = l_M15NIP ; 
		      --
		      DELETE FROM S02DGAJ
		      WHERE TglPayr = l_TglProses AND NIP = l_M15NIP ; 
		      --
		      DELETE FROM S01HGAJ
		      WHERE TglPayr = l_TglProses AND NIP = l_M15NIP ;
		      --
		      ---- Clear S03LTAX ---------
		      IF l_LastJPjk=' ' THEN 
			 BEGIN
			    DELETE FROM S03LTAX
			    WHERE TglPayr = l_TglProses AND  NIP = l_M15NIP AND KdCaba = l_M15kdCaba ; 
			 END ; 
		      ELSE
			BEGIN
			    DELETE FROM S0BLSTX
			    WHERE TglPayr = l_TglProses AND NIP = l_M15NIP AND JnsPajak = l_LastJPjk ; 
			END;
		      END IF; 
		      --* 
		      --
		      -- Delete Summary Lembur
		      IF l_FZ1FlgLemb = 'Y' THEN 
			 BEGIN
			   --
			    SELECT TglAwal,TglAkhir
			    INTO   l_Awal, l_Akhir
			    FROM M47PRLR
			    WHERE TglProses = l_TglProses ; 
			    ---
			    OPEN l_LOOP_T01 FOR SELECT TgDocu FROM T01LEMB WHERE NIP = l_M15NIP AND (TgDocu BETWEEN l_M47TglAwal AND l_M47TglAkhir);
			    LOOP 
				FETCH l_LOOP_T01 INTO l_T01TgDocu;
				IF NOT FOUND THEN
					EXIT ;
				END IF;

				 DELETE FROM S04LEMB
				 WHERE TglPayr = l_T01TgDocu AND NIP = l_M15NIP AND KdArea = l_M15kdArea AND KdCaba = l_M15kdCaba AND  
				       KdUUsa = l_M15kdUUsa AND  KdUker = l_M15kdUker ;

			    END LOOP;
			    CLOSE l_LOOP_T01;
			   --
			  END ; 
		       END IF ; 
		      --*
  		      IF l_TglProses=l_M15TglKeluar THEN 
		         BEGIN
			   SELECT FlagProses
			   INTO l_T19FlagProses 
			   FROM T19PESA
			   WHERE TglKeluar=l_TglProses AND NIP=l_M15NIP ;
			 --
			   IF COALESCE(l_T19FlagProses,' ')<>' ' THEN 
			      BEGIN
			        DELETE FROM S0BLSTX
			        WHERE  TglPayr=l_TglProses AND NIP=l_M15NIP AND JnsPajak='X' ; 
			      --
			        UPDATE T19PESA
			        SET FlagProses=' '
			        WHERE TglKeluar=l_TglProses AND NIP=l_M15NIP ;   
			      --
			      END ;
			   END IF ; 
		       --
			  END;              
		      --
		        END IF ; 
		      -- 
		        DELETE FROM S05PSTD
		        WHERE NIP = l_M15NIP AND TglPosting = l_TglProses ; 
		      --Ambil Lagi Posisi Terakhir Posting
		        l_Tgl_Akhir := NULL ; 
		      --
		        SELECT TglPosting,  FlagTHR
		        INTO   l_Tgl_Akhir, l_FlagTHR
		        FROM S05PSTD 
		        WHERE NIP = l_M15NIP
		        ORDER BY NIP,TglPosting ; 

/*
	      -- Update Posting Terakhir Master Pegawai 
	  IF l_FlagTHR=' '
	     BEGIN	
		  UPDATE M15PEGA
		  SET TglPayr=l_Tgl_Akhir
		  WHERE NIP=l_M15NIP
	     END
	  --/
*/
	      --        
		    END ; 
		END IF;     
       --
--        END ;  -- End Validasi Sec. Cabang
--     END IF ;   
  --*
   --* Akhir Prs_OK 
   --
END LOOP;
CLOSE l_LOOP_M15;
--
END ;
--
$$ LANGUAGE plpgsql ; 

/*
SELECT l_mlValid, l_TableName1
FROM  P_UTHR('2013-12-20' :: DATE, ' ', 'ZZZZ', 'PHW-001', 'PHW-001','ok',1);

SELECT l_Gagal, l_TableName1, l_mlValid
FROM P_PTHR ('2013-12-20' :: DATE, ' ', 'ZZZZ', 'PHW-001', 'PHW-001',
'L', 'I', '01', 'suhe', 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1); 	
SELECT * FROM S05PSTD WHERE NIP = 'PHW-001'
*/
