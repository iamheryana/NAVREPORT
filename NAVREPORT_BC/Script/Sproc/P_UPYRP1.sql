/****************************************
Name sprocs	: P_UPYRP1
Create by	: Rudy
Date		: 17-10-2001,15-09-2003 (Modified by Suhe)
Description	: Proses Unpost Payroll
Call From	: Main sprocs
Sub sprocs	: -
updated by peggy 2006 09 23 : nambahin range parameter uker. 
*****************************************/
-- DROP FUNCTION P_UPYRP1(l_TglProses		DATE,
-- 					l_LokasiFr	VARCHAR(4),
-- 					l_LokasiTo	VARCHAR(4),
-- 					l_ukerFr   	VARCHAR(8),
-- 					l_ukerTo   	VARCHAR(8), 
-- 					l_NIPFr		VARCHAR(10),
-- 					l_NIPTo	 	VARCHAR(10),
-- 					l_FGOL      	VARCHAR(4), 
-- 					l_TGOL      	VARCHAR(4),      
-- 					l_FCAB      	VARCHAR(4),
-- 					l_TCAB      	VARCHAR(4),
-- 					l_UserID	VARCHAR(12),
-- 					OUT l_TableName1 VARCHAR(8),
-- 					OUT l_mlValid 	INT) ;
--
DROP FUNCTION P_UPYRP1(l_TglProses		DATE,
			l_LokasiFr	VARCHAR(4),
			l_LokasiTo	VARCHAR(4),
			l_ukerFr   	VARCHAR(8),
			l_ukerTo   	VARCHAR(8), 
			l_NIPFr		VARCHAR(10),
			l_NIPTo	 	VARCHAR(10),
			l_UserID	VARCHAR(12),
			OUT l_TableName1 VARCHAR(8),
			OUT l_mlValid 	INT, 
			l_Usr_ID	INT);
--					
CREATE FUNCTION P_UPYRP1(l_TglProses		DATE,
			l_LokasiFr	VARCHAR(4),
			l_LokasiTo	VARCHAR(4),
			l_ukerFr   	VARCHAR(8),
			l_ukerTo   	VARCHAR(8), 
			l_NIPFr		VARCHAR(10),
			l_NIPTo	 	VARCHAR(10),
			l_UserID	VARCHAR(12),
			OUT l_TableName1 VARCHAR(8),
			OUT l_mlValid 	INT, 
			l_Usr_ID	INT)
					
AS $$ 
--
--
DECLARE	l_Tgl_Akhir	DATE; 		l_FZ1FlgPiut	VARCHAR(1);
	l_VarRound	INT;  	  	l_mnHari	DECIMAL(4,1);
	l_M15NIP	VARCHAR(10); 	l_M15KdCaba	VARCHAR(4);  	l_M15KdKlas	VARCHAR(4);
	l_M15PrdTetap	DATE; 		l_M15KdArea	VARCHAR(4);
	l_M15KdUUsa	VARCHAR(4);  	l_M15KdUKer	VARCHAR(8);
	l_FZ1FlgLemb	VARCHAR(1);  
	l_FlagTHR	VARCHAR(1);  	l_Prs_OK	VARCHAR(1);
	l_FlagM		VARCHAR(1);  	l_FlgGGol	VARCHAR(1);
	l_Err_Rid	INT;	  	l_S01LastJPjk	VARCHAR(1);
	l_FZ1TunjIst	DECIMAL(5,2); 	l_Harian	INT;
	l_T19FlagProses	VARCHAR(1);  	l_PrdAngs	DATE;
	l_T05NIP	VARCHAR(10); 	l_T01TgDocu	DATE;
	l_S08KdKlas	VARCHAR(4);  	l_Skip_Del	INT;
	l_T10TglEff	DATE; 		l_TglDocu	DATE;
	l_T08TgDocu	DATE; 		l_T12NIP	VARCHAR(10);
	l_FZ1TunjAnk	DECIMAL(5,2); 	l_Kontrak	INT;
	l_T05KdJnsP	VARCHAR(4);  	l_T10KdMutr	VARCHAR(4);
	l_S08KdCaba	VARCHAR(4);  	l_T08NIP	VARCHAR(10);
	l_T08KdAbsn	VARCHAR(4);  	l_T12TgDocu	DATE;
	l_FZ1FlgGlng	VARCHAR(1);  	l_JnsPajak	VARCHAR(1);
	l_T05TgDoku	DATE; 		l_T06PrdAngs	DATE;
	l_S08KdUUsa	VARCHAR(4);  	l_T12KdJnsD	VARCHAR(4);
	l_S08KdUKer	VARCHAR(8);  	l_T12KdJrsn	VARCHAR(4);
	l_S08KdGlng	VARCHAR(4);  	l_S08KdJaba	VARCHAR(4);
	l_S08KdArea	VARCHAR(4);  	l_S08KdKJab	VARCHAR(4);
	l_S08Gaji	VARCHAR(100); 	l_M15Nama	VARCHAR(25);
	l_Created	INT;      	l_Nilai		DECIMAL(15,2);
	l_Nilai1	DECIMAL(15,2); 	l_LOOP_T06 	REFCURSOR;
	l_Tgl_Akhir1	DATE; 		l_LOOP_T21	REFCURSOR;
	l_T21Tahun	DECIMAL(4,0);  	l_T21JenisMedical VARCHAR(4);
	l_Jatah		DECIMAL(15,2); 	l_T21NIP	VARCHAR(10);
	l_JatahKel	DECIMAL(15,2); 	l_M29JnsCuti	INT;
	l_Tgl_AkhirX    DATE; 		l_DataValid    	VARCHAR(1);
	l_M15TglMasuk   DATE; 		l_M15TglKeluar  DATE;
	l_LOOP_M15      REFCURSOR;
	l_T08HariAbsn	DECIMAL(3,1);
	l_T08CutiTahun	DECIMAL(4,0);
	l_LOOP_T12	REFCURSOR;	l_FZ1Personalia VARCHAR(1);
	l_T07NIP	VARCHAR(10);
	l_T07KdJnsP	VARCHAR(4);
	l_T07TgPiut	DATE;
	l_T07TgDoku	DATE;
	l_LOOP_T07	REFCURSOR; 	l_LOOP_T01	REFCURSOR;	
	l_LOOP_T08	REFCURSOR; 	l_LOOP_T10	REFCURSOR;
	l_mlAdaMutasi	INT;
	l_S08KdMutr	VARCHAR(4); 
	l_Pakai		DECIMAL(15,2);
	l_PakaiKel	DECIMAL(15,2);
	l_mlProp	INT;
	l_T20Jatah	DECIMAL(15,2);
	l_T20JatahKel	DECIMAL(15,2);			
	l_FZ2RNDTHPPOT	VARCHAR(1);     l_FZ2THPROUND	 VARCHAR(1);
	
BEGIN 
---		
-- Panggil SProc untuk Bentuk File Validasi 
SELECT OUTNAME, OUTCREATED FROM P_CREATE_TEMP_ONLIST ('W0B') INTO l_TableName1, l_Created;--,	  
			   		--	  l_TableName1 OUTPUT,
					--	  l_Created    OUTPUT
--
IF l_Created=0 THEN 
   RETURN;
END IF; 
--*
l_mlValid := 1;
-- SET ENVEROMENT
--Ambil Var di FZ1FLDA
SELECT  FlgPiut, 	FlgLemb, 	TunjIst, 	TunjAnk, 	Personalia
INTO 	l_FZ1FlgPiut, 	l_FZ1FlgLemb, 	l_FZ1TunjIst, 	l_FZ1TunjAnk, 	l_FZ1Personalia
FROM FZ1FLDA;
--
--Ambil Jumlah Hari
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
						WHEN 12 THEN Hari12  END
INTO l_mnHari
FROM FZ1FLDA; 
--
SELECT 	SUBSTR(StringFlag,8,1), SUBSTR(StringFlag,9,1)
INTO 	l_FZ2RNDTHPPOT, 	l_FZ2THPROUND
FROM FZ2FLDA;
--
-- Looping Master Karyawan
OPEN l_LOOP_M15 FOR SELECT M15.NIP, M15.Nama, M15.TglKeluar, M15.KdCaba, M15.KdKlas, M15.PrdTetap, M15.KdArea, 
				M15.KdUUsa, M15.KdUKer, M15.TglMasuk, M15.TglKeluar
			FROM M15PEGA M15
			INNER JOIN (SELECT * FROM fn_SECLOGIN(l_usr_id)) VSL 
			ON M15.kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
			WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
			      (M15.KdCaba BETWEEN l_LokasiFr AND l_LokasiTo) AND
--			      (M15.KdGlng BETWEEN l_FGOL AND l_TGOL) and 
				(M15.kduker between l_ukerfr and l_ukerto )
			ORDER BY M15.NIP ; 
--
LOOP
FETCH l_LOOP_M15 
INTO l_M15NIP,l_M15Nama,l_M15TglKeluar,l_M15KdCaba,l_M15KdKlas,l_M15PrdTetap,l_M15KdArea,l_M15KdUUsa,l_M15KdUKer,l_M15TglMasuk,l_M15TglKeluar;
--
BEGIN
   IF NOT FOUND THEN
	EXIT ;
   END IF;

--  IF l_M15KdCaba BETWEEN l_FCAB AND l_TCAB THEN 
--     BEGIN
       -- Ambil Tgl proses Akhir di S05 
        l_Tgl_Akhir := NULL;
	l_FlagTHR   := ' ';

       SELECT TglPosting, FlagTHR
	INTO l_Tgl_Akhir, l_FlagTHR
       FROM S05PSTD       
       WHERE NIP=l_M15NIP --AND FlagTHR=' ' -- diremark karena unpost payroll sehrsnya ngecek apa ada proses khusus juga 
       ORDER BY NIP,TglPosting DESC;
       
       -- Validasi Data     
       l_DataValid := '0'; 
       SELECT * FROM fn_Validasi_Unpost(l_FlagTHR, l_M15NIP, l_TglProses, l_M15TglMasuk, l_M15TglKeluar, l_Tgl_Akhir, 'Y')
       INTO l_DataValid;
					
       IF l_DataValid<>'0' THEN 
          BEGIN
            l_mlValid := l_DataValid:: int;--0;
            l_Prs_OK  := 'T';         
            PERFORM P_CLSTEMP (l_M15NIP, l_M15Nama,l_Tgl_Akhir,l_DataValid,l_TableName1) ; 
          END ;      
       ELSE
          BEGIN 
            --Mulai Proses Karyawan !!!!
            l_Harian  := 0 ;
	    l_Kontrak  := 0; 
	    l_JnsPajak :=' ';
            --        
            SELECT Harian,    Kontrak,   JnsPajak
	    INTO   l_Kontrak, l_Kontrak, l_JnsPajak
            FROM M10KLAS
            WHERE Kode=l_M15kdKlas ; 
             -- Pembulatan THP CORE 
            IF l_FZ2RNDTHPPOT = 'Y' AND COALESCE(l_JnsPajak ,' ')=' ' AND
		(l_M15TglKeluar IS NULL OR 
		(l_M15TglKeluar IS NOT NULL AND l_TglProses <> l_M15TglKeluar)) THEN 
                BEGIN
			PERFORM P_UNRND_THP (l_M15NIP, l_TglProses) ;
                END ; 
 	    END IF; 
-- sodetan modi oi lawang 
             -- Pembulatan THP 
            IF l_M15TglKeluar is null or (l_M15TglKeluar is not null and l_TglProses <> l_M15TglKeluar) THEN 
               BEGIN
			PERFORM P_OIL_UNRND_THP (l_M15NIP, l_TglProses) ; 
               END ; 
	    END IF ; 
            -- Clear Summary File_1
            -- Hapus File S01 dan S02
            SELECT JnsPajak
	    INTO l_S01LastJPjk
            FROM S01HGAJ
            WHERE TglPayr=l_TglProses AND NIP=l_M15NIP ; 
            --
            DELETE FROM S02DGAJ
            WHERE TglPayr=l_TglProses AND NIP=l_M15NIP ;
            --
            DELETE FROM S01HGAJ
            WHERE TglPayr=l_TglProses AND NIP=l_M15NIP ;

            -- Clear Summary Pindah Cabang
            IF EXTRACT(MONTH FROM l_TglProses)=1 THEN 
               BEGIN
                 DELETE FROM S0ESFPC 
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ;
               END ;
            ELSIF EXTRACT(MONTH FROM l_TglProses)=2 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab02=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses )AND  NIP=l_M15NIP ; 
	       END ;           
            ELSIF EXTRACT(MONTH FROM l_TglProses)=3 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab03=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ; 
     	       END ;  
            ELSIF EXTRACT(MONTH FROM l_TglProses)=4 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab04=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP;
     	       END ;            
            ELSIF EXTRACT(MONTH FROM l_TglProses)=5 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab05=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ;
	       END ;            
            ELSIF EXTRACT(MONTH FROM l_TglProses)=6 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab06=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ; 
      	       END ;           
            ELSIF EXTRACT(MONTH FROM l_TglProses)=7 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab07=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ;
	       END ;
            ELSIF EXTRACT(MONTH FROM l_TglProses)=8 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab08=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ; 
	       END ;
            ELSIF EXTRACT(MONTH FROM l_TglProses)=9 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab09=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ; 
               END ; 
            ELSIF EXTRACT(MONTH FROM l_TglProses)=10 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab10=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ; 
	       END ;
            ELSIF EXTRACT(MONTH FROM l_TglProses)=11 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab11=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ; 
	       END ;
            ELSIF EXTRACT(MONTH FROM l_TglProses)=12 THEN 
               BEGIN
                 UPDATE  S0ESFPC
                 SET Cab12=' '
                 WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ;
               END ;
            END IF ; 
			--*
            --Clear S03LTAX 
            IF l_S01LastJPjk=' ' THEN 
               BEGIN
                 DELETE FROM S03LTAX
                 WHERE TglPayr=l_TglProses AND  NIP=l_M15NIP ; 
               END ; 
            ELSE
               BEGIN
                 DELETE FROM S0BLSTX
                 WHERE TglPayr=l_TglProses AND  NIP=l_M15NIP AND JnsPajak=l_S01LastJPjk ; 
               END; 
	    END IF ; 
            --* 
            --
            IF l_TglProses=l_M15TglKeluar THEN 
               BEGIN
                 SELECT FlagProses
		 INTO l_T19FlagProses
                 FROM T19PESA
                 WHERE TglKeluar=l_TglProses AND NIP=l_M15NIP ; 
                 --
                 IF l_T19FlagProses IS NOT NULL  THEN 
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
               END ;
	    END IF ; --END IF TGL KELUAR 
            --*
            -- End Of Clear Summary File_1
            --
            -- Hapus File S05
            DELETE FROM S05PSTD
            WHERE NIP=l_M15NIP AND  TglPosting=l_TglProses ; 
            --
            -- Ambil Tanggal Akhir Posting 
            l_Tgl_Akhir := NULL ; 
  
            SELECT TglPosting,  FlagTHR
	    INTO   l_Tgl_Akhir, l_FlagTHR
            FROM S05PSTD
            WHERE NIP=l_M15NIP AND FlagTHR=' '
            ORDER BY NIP,TglPosting ; 
            --
            -- Clear Summary File_2
            -- Delete File T05-T06-T07
            IF l_FZ1FlgPiut='Y' THEN  
               BEGIN
                 IF l_Tgl_Akhir IS NOT NULL THEN 
                    BEGIN
                      l_Tgl_Akhir1 := l_Tgl_Akhir + INTERVAL '1 day' ;  
                    END; 
                 ELSE
                    BEGIN
                      l_Tgl_akhir1 := null ;
                    END;
	         END IF; 
                 --*      
                 --  
	         OPEN l_LOOP_T07 FOR SELECT T07.NIP,T07.KdJnsP,T07.TgPiut,T07.TgDoku FROM T07BAYP T07
				 WHERE T07.NIP=l_M15NIP AND (T07.TgDoku BETWEEN l_Tgl_Akhir1 AND l_TglProses) AND Comp='P';
	--
	         LOOP 
		    FETCH l_LOOP_T07 INTO l_T07NIP,l_T07KdJnsP,l_T07TgPiut,l_T07TgDoku ;
		    IF NOT FOUND THEN
			EXIT ;
		    END IF;
                 --
                     --
                    DELETE FROM T07BAYP
                    WHERE CURRENT OF l_LOOP_T07 ;
                     --WHERE NIP=l_T07NIP AND KdJnsP=l_T07KdJnsP AND TgPiut=l_T07TgPiut AND TgDoku=l_T07TgDoku  AND Comp='P'
                 --*
 	         END LOOP ; 
                 CLOSE l_LOOP_T07;
                 --
	       END ;
	    END IF ;    -- END PIUTANG 
            --*
            -- Delete Summary Lembur
			       
	   IF l_FZ1FlgLemb='Y' THEN 
               BEGIN
                 IF l_Tgl_Akhir IS NOT NULL THEN 
                    BEGIN
                      l_Tgl_Akhir1 := l_Tgl_Akhir + INTERVAL '1 day' ;  
                    END ; 
                 ELSE
                    BEGIN
                      l_Tgl_akhir1:= NULL;
                    END;
	         END IF ;	
                 --*      
		 OPEN l_LOOP_T01 FOR SELECT TgDocu FROM T01LEMB WHERE NIP=l_M15NIP AND (TgDocu BETWEEN l_Tgl_Akhir1 AND l_TglProses);
		 loop 
			fetch l_LOOP_T01 into l_T01TgDocu;
			if not found then
				exit ;
			end if;
			   --
			DELETE FROM S04LEMB
			WHERE TglPayr=l_T01TgDocu AND NIP=l_M15NIP AND KdArea=l_M15kdArea AND KdCaba=l_M15kdCaba AND  
				 KdUUsa=l_M15kdUUsa AND  KdUker=l_M15kdUker ;
			--
		 end loop;
		 close l_LOOP_T01;				
 --*  
               --
               END ; 
            --*
	   END IF ; -- END LEMBUR 
		-------------SAMPE SINI
			-- Jika Ada Modul HRD maka Lakukan Unpost HRD
 
           IF l_FZ1Personalia='Y' THEN 
              BEGIN
            --
              l_mlAdaMutasi := 0; 
            --
	      OPEN l_LOOP_T10 FOR SELECT TglEff,KdMutr FROM T10MUTA WHERE NIP=l_M15NIP AND TglEff>l_Tgl_Akhir AND TglEff IS NOT NULL AND TglEff<=l_TglProses;
	      LOOP 
		FETCH l_LOOP_T10 INTO l_T10TglEff,l_T10KdMutr ;
		IF NOT FOUND THEN
			EXIT ;
		END IF;		
				
		DELETE FROM S08MUTA
		WHERE NIP=l_M15NIP AND TglEff=l_T10TglEff AND KdMutr=l_T10KdMutr AND KdMutr NOT IN (' ','AFPR') ;
		--
		l_mlAdaMutasi := 1 ;
              --				
	      END LOOP;
	      CLOSE l_LOOP_T10;			
            --
              IF l_mlAdaMutasi=1 THEN 
                 BEGIN  
                  -- Ambil Posisi S08MUTA Terakhir
                    SELECT KdMutr,      KdKlas,      KdCaba,      KdUUsa,      KdUKer,      
		  	   KdGlng,      KdJaba,      KdArea,      KdKJab,      EncGajiPokok
	 	    INTO   l_S08KdMutr, l_S08KdKlas, l_S08KdCaba, l_S08KdUUsa, l_S08KdUKer, 
			   l_S08KdGlng, l_S08KdJaba, l_S08KdArea, l_S08KdKJab, l_S08Gaji
                    FROM S08MUTA
                    WHERE NIP=l_M15NIP AND TglEff<=l_TglProses
                    ORDER BY NIP,TglEff,KdMutr ;
                  --
                    IF l_S08KdMutr<>' ' THEN 
                       BEGIN
		          -- Update Data baru ke M15Pega
		          UPDATE M15PEGA
	                  SET kdKlas=l_S08kdKlas,
			      kdCaba=l_S08KdCaba,
			      kdUUsa=l_S08kdUUsa,
			      kdUker=l_S08kdUker,
			      kdGlng=l_S08kdGlng,
			      kdJaba=l_S08kdJaba,
			      KdArea=l_S08kdArea,
			      kdKJab=l_S08KdKJab,
	                      EncGajiTetap=l_S08Gaji
	                  WHERE NIP=l_M15NIP ;
                        END ;
		    END IF; 
                  --*
	
	            -- Update Summ medical 
		OPEN l_LOOP_T21 FOR SELECT T21.Tahun,T21.JenisMedical,T21.NIP FROM T21PMEP T21 WHERE T21.NIP=l_M15NIP AND T21.Tahun=EXTRACT(YEAR FROM l_TglProses) AND (T21.Tanggal>l_Tgl_akhir AND Tgl_akhir IS NOT NULL AND T21.Tanggal<=l_TglProses);
		LOOP 
		  FETCH l_LOOP_T21 INTO l_T21Tahun,l_T21JenisMedical,l_T21NIP;
			IF NOT FOUND THEN
				EXIT ;
			END IF;
		--
		      l_Jatah := 0 ; 
		      l_JatahKel := 0 ;
		      l_T20Jatah := 0 ; -- by peggy 2005 03 15 : karena error null di jatah keluarga
		      l_T20JatahKel := 0 ;-- by peggy 2005 03 15 
	              --
	              SELECT Plafon,     PlafonKel
		      INTO   l_T20Jatah, l_T20JatahKel
	              FROM T20JTMJ T20
	              WHERE T20.Tahun=l_T21Tahun AND  T20.JenisMedical=l_T21JenisMedical AND T20.KelJab=l_S08KdKJab ;
	              --
		      -- Check Dulu jika pemakaian=jatahnya maka tdk boleh update jatah medical
		      --- by suhe
		      SELECT  SUM(CASE WHEN T2X.FlagKel=0 THEN Pemakaian  ELSE 0 END),
			      SUM(CASE WHEN T2X.FlagKel=1 THEN Pemakaian  ELSE 0 END)
		      INTO    l_Pakai, 
			      l_PakaiKel
		      FROM T21PMEP T2X
	              WHERE T2X.NIP = l_T21NIP AND T2X.Tahun = l_T21Tahun AND T2X.JenisMedical=l_T21JenisMedical ; 
	              ---
		      SELECT JatahMedical, JatahMedicalKel 
		      INTO   l_Jatah,      l_JatahKel						 
		      FROM S0CMEDH 
	              WHERE NIP=l_T21NIP AND Tahun=l_T21Tahun AND JenisMedical=l_T21JenisMedical ; 
				  
		      --- Jika pegawai proporsional tdk di update
		      SELECT CASE WHEN l_TglProses - M15X.TglMasuk <=360 THEN 1 ELSE 0 END
		      INTO   l_mlProp
		      FROM M15PEGA M15X		
		      WHERE M15X.NIP=l_T21NIP ;
		      ---
	              IF (l_Jatah-l_Pakai)+(l_JatahKel-l_PakaiKel)<>0 AND l_mlProp=0 THEN 
		         BEGIN
		              UPDATE S0CMEDH
		              SET JatahMedical   =COALESCE(l_T20Jatah,0),
				  JatahMedicalKel=COALESCE(l_T20JatahKel,0)
		              WHERE NIP=l_T21NIP AND Tahun=l_T21Tahun AND JenisMedical=l_T21JenisMedical                   ; 
	                 END ;   
	 	      END IF; 
	              ---/ 
		END LOOP;
		CLOSE l_LOOP_T21;		
	            -- 

                 --
		 END ; 
            --* 
	      END IF ; -- ADA MUTASI 
/*
    	    --Clear data History Absen
	    --
        SET l_LOOP_T08=CURSOR FOR 
	    SELECT TgDocu,NIP,KdAbsn,HariAbsn,CutiTahun
	    FROM T08ABSN
	    WHERE NIP=l_M15NIP AND TgDocu>COALESCE(l_Tgl_akhir,'' '') AND TgDocu<=l_TglProses
	    --
	    OPEN l_LOOP_T08
	    FETCH NEXT FROM l_LOOP_T08
	    INTO l_T08TgDocu,l_T08NIP,l_T08KdAbsn,l_T08HariAbsn,l_T08CutiTahun
	    --
	    WHILE l_l_FETCH_STATUS=0
	    BEGIN
              --
	      -- Hapus S06
 	      DELETE FROM S06ABSH
	      WHERE Tanggal=l_T08TgDocu AND NIP=l_M15NIP AND  KdAbsn=l_T08KdAbsn
              --
              SELECT l_M29JnsCuti=0
              --
              SELECT l_M29JnsCuti= JnsCuti 
              FROM M29JNSA
              WHERE Kode=l_T08KdAbsn
              --
              IF l_M29JnsCuti<>0
                 BEGIN  
		   -- Hapus S0A
                   UPDATE S0ADCUT
                   SET Pemakaian=Pemakaian-l_T08HariAbsn                                  
		   WHERE NIP=l_T08NIP AND Tahun=l_T08CutiTahun AND BlnTgl = SUBSTR(CONVERT(VARCHAR(6),l_T08TgDocu,112),5,2)+SUBSTR(CONVERT(VARCHAR(6),l_T08TgDocu,112),3,2) AND KdCuti=l_T08KdAbsn
                   --
                 END
	      --*
              --
              FETCH NEXT FROM l_LOOP_T08
 	      INTO l_T08TgDocu,l_T08NIP,l_T08KdAbsn,l_T08HariAbsn,l_T08CutiTahun
            END	
	    --*
	    CLOSE l_LOOP_T08
	    DEALLOCATE l_LOOP_T08
	    --
	    -- Clear Data History Training
            SET l_LOOP_T12 = CURSOR FOR
            SELECT T12.TgDocu,T12.KdJnsD,T12.KdJrsn,T12.NIP
            FROM T12DTRN T12
            WHERE T12.NIP = l_M15NIP AND T12.TgDocu BETWEEN DATEADD(DAY,1,COALESCE(l_Tgl_Akhir,'' '')) AND l_TglProses
            --
            OPEN l_LOOP_T12
            FETCH NEXT FROM l_LOOP_T12
            INTO l_T12TgDocu,l_T12KdJnsD,l_T12KdJrsn,l_T12NIP
            --
            WHILE l_l_FETCH_STATUS=0
               BEGIN
                 --
                 DELETE FROM S07TRNG 
                 WHERE Tanggal=l_T12TgDocu AND NIP=l_T12NIP AND KdJnsd=l_T12KdJnsD AND KdJrsn=l_T12KdJrsn
            	 --
                 FETCH NEXT FROM l_LOOP_T12
	         INTO l_T12TgDocu,l_T12KdJnsD,l_T12KdJrsn,l_T12NIP
               END
            --*
            CLOSE l_LOOP_T12
            DEALLOCATE l_LOOP_T12
*/
            --
           END ;
	   END IF ; 
      --* Akhir Jika Ada Modul HRD
      -- End Of Clear Summary File_2
      --
           UPDATE M15PEGA
           SET TglPayr=l_Tgl_Akhir
           WHERE NIP=l_M15NIP  ;

--------------------Existing Emp : Update M. Pegawai : Sts Pjk Januari doankz -------
-- autojaya pl#9 
-- -- 		IF EXTRACT(MONTH FROM l_TglProses) = 1 
-- -- 		BEGIN 
	    UPDATE M15PEGA 
	    SET StsPjk 	 = T1.StsPjk,
	        Status 	 = T1.Status, 
		AnakTtgg = T1.AnakTtgg 
	    FROM 
	    (-- T1
	    SELECT  M62.NIP, M62.Status, M62.StsPjk, M62.AnakTtgg 
	    FROM M62EFST M62
	    INNER JOIN 
	    (--q2
	     SELECT NIP, MAX(TglEfektif) AS TglEfektif
	     FROM M62EFST
	     WHERE NIP = l_M15NIP AND TglEfektif <= l_Tgl_Akhir + INTERVAL '1 day' 
	     GROUP BY NIP
	    ) Q2
 	    ON Q2.NIP=M62.NIP AND Q2.TglEfektif = M62.TglEfektif 
	    ) T1
	    INNER JOIN  M15PEGA M15 
	    ON M15.NIP = T1.NIP ;
-- -- 		END 
-- -- 		ELSE 
-- -- 		BEGIN 
-- -- 			UPDATE M15PEGA 
-- -- 			SET Status 	 = T1.Status, 
-- -- 				AnakTtgg = T1.AnakTtgg 
-- -- 			FROM 
-- -- 			(-- T1
-- -- 			SELECT  NIP        = M62.NIP, 
-- -- 					Status     = M62.Status, 
-- -- 					AnakTtgg   = M62.AnakTtgg 
-- -- 			FROM M62EFST M62
-- -- 			INNER JOIN 
-- -- 			(--q2
-- -- 			SELECT NIP,TglEfektif=MAX(TglEfektif)
-- -- 			FROM M62EFST
-- -- 			WHERE NIP = l_M15NIP AND TglEfektif <= DATEADD(DAY,1,COALESCE(l_Tgl_Akhir,' ')) 
-- -- 			GROUP BY NIP
-- -- 			) Q2
-- -- 			ON Q2.NIP=M62.NIP AND Q2.TglEfektif = M62.TglEfektif 
-- -- 			) T1
-- -- 			INNER JOIN  M15PEGA M15 
-- -- 			ON M15.NIP = T1.NIP 
-- -- 		END 
		
---------------------New Emp : create tabel efektif status ---------------------------
		DELETE FROM M62EFST M62
		USING M15PEGA M15 
		WHERE M62.NIP = M15.NIP AND M62.TglEfektif = M15.TglMasuk AND 
		      M15.NIP = l_M15NIP AND 
		      M15.TglMasuk BETWEEN (COALESCE(l_Tgl_Akhir,'01-01-1980'::DATE) + INTERVAL '1 day')  AND l_TglProses ;
	   END ; 
	 --* Akhir Prs_OK 
       END IF ;
--     END ; 
--  END IF ; -- AKHIR RANGE CABANG 
   --

END ; 
END LOOP;
CLOSE l_LOOP_M15;	 
--
END ; 
--END ; 
$$ LANGUAGE plpgsql ; 


/*
SELECT l_mlValid, l_TableName1
FROM  P_UPYRP1 ('2013-11-26' :: DATE, '', 'ZZZZ', '', 'ZZZZZZZZ', 'PHW-001', 'PHW-001','ok',62);
SELECT * FROM S05PSTD WHERE NIP = 'PHW-001'
SELECT * FROM fn_SECLOGIN(62)
SELECT KDCABA, KDGLNG, M08_ID, M12_ID FROM M15PEGA WHERE NIP ='PHW-001' -- A T 
*/
