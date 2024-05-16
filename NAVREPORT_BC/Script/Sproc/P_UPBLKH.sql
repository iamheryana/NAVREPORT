/****************************************
Name sprocs	: P_UPBLKH
Create by	: Herz
Date		: 17-10-2001
Description	: Proses Unpost Payroll Khusus dari S.P.Bulanan/Khusus
Call From	: Main sprocs
Sub sprocs	: -
*****************************************/
DROP FUNCTION P_UPBLKH(l_TglProses	DATE,
			l_LokasiFr	VARCHAR(4),
			l_LokasiTo	VARCHAR(4),
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
			l_Payroll	VARCHAR(1));
--
CREATE FUNCTION P_UPBLKH(l_TglProses	DATE,
			l_LokasiFr	VARCHAR(4),
			l_LokasiTo	VARCHAR(4),
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
			l_Payroll	VARCHAR(1)) 
RETURNS VOID 
AS $$ 
---
/*
l_Payroll='Y'= Proses Harian/Bulanan
		 'T' =Selain Bulanan/Harian
*/
--
DECLARE	l_Tgl_Akhir 	DATE; 		l_FZ1FlgPiut	VARCHAR(1);
	l_VarRound	INT;  	  	l_mnHari	DECIMAL(4,1);
	l_M15NIP	VARCHAR(10); 	l_M15TglKeluar	DATE;
	l_M15KdCaba	VARCHAR(4);  	l_M15KdKlas	VARCHAR(4);
	l_M15PrdTetap	DATE; 		l_M15KdArea	VARCHAR(4);
	l_M15KdUUsa	VARCHAR(4);  	l_M15KdUKer	VARCHAR(8);
	l_FZ1FlgLemb	VARCHAR(1);  	l_V_Round	INT;
	l_Prs_OK	VARCHAR(1);
	l_FlagM		VARCHAR(1);  	l_FlgGGol	VARCHAR(1);
	l_Err_Rid	INT;	  	l_S01LastJPjk	VARCHAR(1);
	l_FZ1TunjIst	DECIMAL(5,2); 	l_Harian	INT;
	l_T19FlagProses	VARCHAR(1); 	l_PrdAngs	DATE;
	l_T05NIP	VARCHAR(10); 	l_T01TgDocu	DATE;
	l_S08KdKlas	VARCHAR(4);  	l_Skip_Del	INT;
	l_T10TglEff	DATE; 		l_TglDocu	DATE;
	l_T08TgDocu	DATE; 		l_T12NIP	VARCHAR(10);
	l_FZ1TunjAnk	DECIMAL(5,2); 	l_Kontrak	INT;
	l_T05KdJnsP	VARCHAR(4);  	l_T10KdMutr	VARCHAR(4);
	l_S08KdCaba	VARCHAR(4);  	l_T08NIP	VARCHAR(10);
	l_T08KdAbsn	VARCHAR(4);  	l_T12TgDocu	DATE;
	l_FZ1FlgGlng	VARCHAR(1); 	l_JnsPajak	VARCHAR(1);
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
	l_Tgl_AkhirX    DATE;
	l_T08HariAbsn	DECIMAL(3,1);
	l_LOOP_T12	REFCURSOR;
	l_T08CutiTahun	DECIMAL(4,0);	l_FZ1Personalia VARCHAR(1);
	l_T07NIP	VARCHAR(10);	
	l_LOOP_T07	REFCURSOR;	l_LOOP_M15	REFCURSOR;
	l_LOOP_T01	REFCURSOR;	l_LOOP_T10	REFCURSOR;
	l_LOOP_T08	REFCURSOR;
	l_mlAdaMutasi	INT;
	l_S08KdMutr	VARCHAR(4);
	l_Pakai		DECIMAL(15,2);
	l_PakaiKel	DECIMAL(15,2);
	l_mlProp	INT;
	l_t20Jatah	DECIMAL(15,2);
	l_T20JatahKel	DECIMAL(15,2);
	l_S05FlagTHR 	VARCHAR(1);
	l_M47TglAkhir 	DATE;
	l_M47TglAwal	DATE;
	l_BeliModulAsuransi VARCHAR(1); 
	l_adas01	VARCHAR(1);
	l_LemburAkhir 	DATE;
	l_LemburAwal	DATE;
		
-- SET ENVEROMENT
--Ambil Var di FZ1FLDA
BEGIN 
SELECT FlgPiut,      FlgLemb,      
	CASE WHEN VarRound = '1' THEN 0
	     WHEN VarRound = '2' THEN -1
     	     WHEN VarRound = '3' THEN -2
     	     ELSE -3 END,   
	TunjIst,      TunjAnk,      Personalia 
INTO   l_FZ1FlgPiut, l_FZ1FlgLemb, 
	l_V_Round, 
	l_FZ1TunjIst, l_FZ1TunjAnk, l_FZ1Personalia
FROM FZ1FLDA; 

-- Beli Modul Asuransi
SELECT SUBSTR(StringFlag,6,1)
INTO   l_BeliModulAsuransi
FROM FZ2FLDA;

-- Parameter Lembur dari M47PRLR
SELECT TglAwal,	     TglAkhir
INTO   l_M47TglAwal, l_M47TglAkhir
FROM M47PRLR
WHERE TglProses<=l_TglProses ; 

-- Ambil Nilai Rounding Data !!
-- SELECT CASE l_VarRound
--      	          WHEN '1' THEN 0
--      	          WHEN '2' THEN -1
--      	          WHEN '3' THEN -2
--      	          ELSE -3 END   
-- INTO l_V_Round ; 
--
--Ambil Jumlah Hari
SELECT CASE WHEN EXTRACT(MONTH FROM l_TglProses) = 1 THEN  Hari01
						WHEN EXTRACT(MONTH FROM l_TglProses) = 2 THEN  Hari02
						WHEN EXTRACT(MONTH FROM l_TglProses) = 3 THEN  Hari03
						WHEN EXTRACT(MONTH FROM l_TglProses) = 4 THEN  Hari04
						WHEN EXTRACT(MONTH FROM l_TglProses) = 5 THEN  Hari05
						WHEN EXTRACT(MONTH FROM l_TglProses) = 6 THEN  Hari06
						WHEN EXTRACT(MONTH FROM l_TglProses) = 7 THEN  Hari07
						WHEN EXTRACT(MONTH FROM l_TglProses) = 8 THEN  Hari08
						WHEN EXTRACT(MONTH FROM l_TglProses) = 9 THEN  Hari09
						WHEN EXTRACT(MONTH FROM l_TglProses) = 10 THEN Hari10
						WHEN EXTRACT(MONTH FROM l_TglProses) = 11 THEN Hari11
						WHEN EXTRACT(MONTH FROM l_TglProses) = 12 THEN Hari12  END 
INTO l_mnHari	
FROM FZ1FLDA ; 
--
l_Tgl_Akhir := NULL ; 
---
SELECT TglPosting,  FlagTHR
INTO   l_Tgl_Akhir, l_S05FlagTHR
FROM S05PSTD
WHERE NIP=l_M15NIP 
ORDER BY NIP,TglPosting ;
--

-- Looping Master Karyawan
OPEN l_LOOP_M15 FOR SELECT NIP,Nama,TglKeluar,KdCaba,KdKlas,PrdTetap,KdArea,KdUUsa,KdUKer
			FROM M15PEGA
			WHERE (NIP BETWEEN l_NIPFr AND l_NIPTo) AND
			      (KdCaba BETWEEN l_LokasiFr AND l_LokasiTo);
LOOP 
   FETCH l_LOOP_M15 INTO l_M15NIP,l_M15Nama,l_M15TglKeluar,l_M15KdCaba,l_M15KdKlas,l_M15PrdTetap,l_M15KdArea,l_M15KdUUsa,l_M15KdUKer ;
   IF NOT FOUND THEN
      EXIT ;
   END IF;

   l_Prs_OK := 'T' ;         
--
   --Mulai Proses Karyawan !!!!
   IF l_Prs_OK='T' THEN         
      BEGIN
        --
        l_Harian   := 0 ;
	l_Kontrak  := 0 ;
	l_JnsPajak := ' ';
        --        
        SELECT Harian,   Kontrak,   JnsPajak
	INTO   l_Harian, l_Kontrak, l_JnsPajak
        FROM M10KLAS
        WHERE Kode=l_M15kdKlas ; 
        --
        -- Clear Summary File_1
        -- Hapus File S01 dan S02
        SELECT JnsPajak
        INTO   l_S01LastJPjk  
        FROM S01HGAJ
	WHERE TglPayr=l_TglProses AND NIP=l_M15NIP ; 

	l_ADAS01 := ' ' ;
	IF (SELECT NIP FROM S01HGAJ WHERE TglPayr=l_TglProses AND NIP=l_M15NIP) IS NULL  THEN 
	   l_ADAS01 := '1' ;
	ELSE 
	   l_ADAS01 := ' ' ;
	END IF; 
--         --
	
	IF l_ADAS01 = '1' THEN 
	BEGIN 
	   DELETE FROM S02DGAJ
	   WHERE TglPayr=l_TglProses AND NIP=l_M15NIP ; 
	   --
	   DELETE FROM S01HGAJ
	   WHERE TglPayr=l_TglProses AND NIP=l_M15NIP ; 
	        -- 
	        -- Clear Summary Pindah Cabang
	-- by peggy 2007 01 16 : unpost proses thr/khusus jangan cabut summary per cabang 
	   IF l_Payroll = 'Y' THEN 
	   BEGIN 
		IF EXTRACT(MONTH FROM l_TglProses)=1 THEN 
		   BEGIN
		      DELETE FROM S0ESFPC
		      WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ; 
		   END ;  
		ELSIF EXTRACT(MONTH FROM l_TglProses)=2 THEN 
		   BEGIN
		      UPDATE  S0ESFPC
		      SET Cab02=' '
		      WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ; 
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
		      WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND  NIP=l_M15NIP ;
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
	         END IF; 
	
	   --/
	   --Clear S03LTAX 
	   IF l_S01LastJPjk=' ' THEN 
	      BEGIN
	         DELETE FROM S03LTAX
	         WHERE TglPayr=l_TglProses AND  NIP=l_M15NIP AND KdCaba=l_M15kdCaba ; 
	      END ;
	   ELSE
	      BEGIN
	         DELETE FROM S0BLSTX
	         WHERE TglPayr=l_TglProses AND  NIP=l_M15NIP AND JnsPajak=l_S01LastJPjk ; 
	      END ;
	   END IF ;   
	   --
	   IF l_TglProses=l_M15TglKeluar THEN 
	      BEGIN
	        SELECT FlagProses
	        INTO l_T19FlagProses
	        FROM T19PESA
	        WHERE TglKeluar=l_TglProses AND NIP=l_M15NIP ; 
	     --
	        IF l_T19FlagProses IS NOT NULL THEN 
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
	    END IF ;    
	        --/
	        -- END ; Of Clear Summary File_1
	        --
  	    -- Hapus File S05
	    DELETE FROM S05PSTD
  	    WHERE NIP=l_M15NIP AND  TglPosting=l_TglProses ;
	    --
	    l_Tgl_Akhir := NULL ;
	    --
	    SELECT TglPosting,  FlagTHR 	
	    INTO   l_Tgl_Akhir, l_S05FlagTHR
	    FROM S05PSTD
	    WHERE NIP=l_M15NIP 
	    ORDER BY NIP,TglPosting ; 
	        --
	        -- Jika ada Modul Asuransi
-- -- 	           DELETE S0FPDAP FROM S0FPDAP
-- -- 	           INNER JOIN T22KPAS ON T22KPAS.NIP=S0FPDAP.NIP
-- -- 	           WHERE S0FPDAP.NIP=l_M15NIP AND S0FPDAP.Periode BETWEEN DATEADD(DAY,1,l_Tgl_Akhir) AND l_TglProses 
-- -- 	                 AND S0FPDAP.Hubungan='' AND l_BeliModulAsuransi='Y'
	        --
	        -- Clear Summary File_2
	        -- Delete File T05-T06-T07
	    IF l_FZ1FlgPiut='Y' THEN 
	       BEGIN
	         --
		 IF l_Tgl_Akhir IS NOT NULL THEN 
		    BEGIN
		      l_Tgl_Akhir1 := l_Tgl_Akhir + INTERVAL '1 day' ;
		    END ;
		 ELSE
		    BEGIN
		      l_Tgl_akhir1 := NULL ; 
		    END ;
		 END IF; 
		 --/      
		 --
		OPEN l_LOOP_T07 FOR SELECT T07.NIP
					     FROM T07BAYP T07
					     WHERE T07.NIP=l_M15NIP AND (T07.TgDoku BETWEEN l_Tgl_Akhir1 AND l_TglProses) 
					     AND Comp='P';
		LOOP 
		   FETCH l_LOOP_T07 INTO l_T07NIP;
		   IF NOT FOUND THEN
		      EXIT ;
		   END IF;
		   --
		   DELETE FROM T07BAYP
		   WHERE CURRENT OF l_LOOP_T07 ; 
		   -- 
 		END LOOP;
		CLOSE l_LOOP_T07;		           
	      --
	      END ; 
	   END IF; 
	--/
	        -- Delete Summary Lembur
	   IF l_FZ1FlgLemb='Y' THEN 
	   BEGIN
	      --
	      IF l_Tgl_Akhir IS NOT NULL THEN 
		 BEGIN
		    l_Tgl_Akhir1 := l_Tgl_Akhir + INTERVAL '1 day ';  
		 END ;
	      ELSE
		 BEGIN
		    l_Tgl_akhir1 := NULL ; 
		 END ;
	      END IF ; 	 
	      -- 
	      -- Jika proses bukan dari payroll bulanan/harian lembur dihapus dari range lembur	
	      IF l_Payroll='Y' THEN 
	         BEGIN
		   l_LemburAwal := l_Tgl_Akhir1; 
		   l_LemburAkhir:= l_TglProses ;       
	         END ;
	      ELSE
	         BEGIN
		   l_LemburAwal := l_M47TglAwal; 
		   l_LemburAkhir:= l_M47TglAkhir ;       
	         END ;	
	      END IF ; 
	      
	      OPEN l_LOOP_T01 FOR SELECT TgDocu
				  FROM T01LEMB
				  WHERE NIP=l_M15NIP AND (TgDocu BETWEEN l_LemburAwal AND l_LemburAkhir);
	      LOOP 
		FETCH l_LOOP_T01 INTO l_T01TgDocu;
		IF NOT FOUND THEN
		   EXIT ;
		END IF;
		--
		DELETE FROM S04LEMB
		WHERE TglPayr=l_T01TgDocu AND NIP=l_M15NIP AND KdArea=l_M15kdArea AND KdCaba=l_M15kdCaba AND  
			KdUUsa=l_M15kdUUsa AND  KdUker=l_M15kdUker ;
		--
	      END LOOP;
	      CLOSE l_LOOP_T01;
	     --
	   END ;
	   END IF ; 
	END ;
	END IF ;  -- if payroll = y
	
	         /********************** PROSES HRD ********************************/
	       -- Jika Ada modul HRD maka Lakukan Unpost HRD
	-- by peggy 2007 01 16 : unpost proses thr/khusus jangan cabut MUTASI DAN MEDICAL 
	   IF l_FZ1Personalia='Y'  AND l_Payroll = 'Y' THEN 
--        IF l_FZ1Personalia='Y' 
	   BEGIN  
	      --
	      l_mlAdaMutasi := 0 ;
	      -- 
	      OPEN l_LOOP_T10 FOR SELECT TglEff,KdMutr 
				  FROM T10MUTA
				  WHERE NIP=l_M15NIP AND TglEff>COALESCE(l_Tgl_Akhir,'01-01-1980'::DATE) AND TglEff<=l_TglProses;
	      LOOP 
	         FETCH l_LOOP_T10 INTO l_T10TglEff,l_T10KdMutr ;
	         IF NOT FOUND THEN
		    EXIT ;
	         END IF;
	         --
	         DELETE FROM S08MUTA
	         WHERE NIP=l_M15NIP AND TglEff=l_T10TglEff AND KdMutr=l_T10KdMutr AND KdMutr NOT IN (' ','AFPR') ;
	          --
	         l_mlAdaMutasi := 1 ;
	      --
	      END LOOP;
	      CLOSE l_LOOP_T10;

	    --
	    -- Jika Ada mutasi Harus dibalikkin ke Semula
	      IF l_mlAdaMutasi=1 THEN 
	      BEGIN
		 -- Ambil Posisi S08MUTA Terakhir
		 SELECT KdMutr,      KdKlas,      KdCaba,      KdUUsa,      KdUKer,      KdGlng,      KdJaba,      KdArea,      KdKJab,      EncGajiPokok
		 INTO   l_S08KdMutr, l_S08KdKlas, l_S08KdCaba, l_S08KdUUsa, l_S08KdUKer, l_S08KdGlng, l_S08KdJaba, l_S08KdArea, l_S08KdKJab, l_S08Gaji
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
		 END IF ; 
		 --/
		 OPEN l_LOOP_T21 FOR SELECT T21.Tahun,T21.JenisMedical,T21.NIP 
				     FROM T21PMEP T21 
				     WHERE T21.NIP=l_M15NIP AND T21.Tahun=EXTRACT(YEAR FROM l_TglProses) AND 
					  (T21.Tanggal>COALESCE(l_Tgl_akhir,'01-01-1980'::DATE) AND T21.Tanggal<=l_TglProses);
		 LOOP 
		    FETCH l_LOOP_T21 INTO l_T21Tahun,l_T21JenisMedical,l_T21NIP;
		    IF NOT FOUND THEN
			EXIT ;
		    END IF;
		    --
		    l_Jatah       := 0 ;
		    l_JatahKel    := 0 ;
		    l_T20Jatah    := 0 ;	
		    l_T20JatahKel := 0 ;
		      --
		    SELECT Plafon,     PlafonKel
		    INTO   l_T20Jatah, l_T20JatahKel
		    FROM T20JTMJ T20
		    WHERE T20.Tahun=l_T21Tahun AND T20.JenisMedical=l_T21JenisMedical AND T20.KelJab=l_S08KdKJab ; 
		    --
		    -- Check Dulu jika pemakaian=jatahnya maka tdk boleh update jatah medical
		    --- by suhe
		    SELECT SUM(CASE T2X.FlagKel WHEN 0 THEN Pemakaian  ELSE 0 END),
			   SUM(CASE T2X.FlagKel WHEN 1 THEN Pemakaian  ELSE 0 END)
		    INTO   l_Pakai,
			   l_PakaiKel
		    FROM T21PMEP T2X
		    WHERE T2X.NIP = l_T21NIP AND T2X.Tahun = l_T21Tahun AND T2X.JenisMedical=l_T21JenisMedical ;
		    ---
		    SELECT JatahMedical, JatahMedicalKel		     	
		    INTO   l_Jatah,      l_JatahKel
		    FROM S0CMEDH 
		    WHERE NIP=l_T21NIP AND Tahun=l_T21Tahun AND JenisMedical=l_T21JenisMedical ; 
		    --- Jika pegawai proporsional tdk di update
		    SELECT CASE l_TglProses - M15X.TglMasuk <=360 WHEN true THEN 1 ELSE 0 END 
		    INTO   l_mlProp 
		    FROM M15PEGA M15X		
		    WHERE M15X.NIP=l_T21NIP ; 
		    ---
		    IF (l_Jatah-l_Pakai)+(l_JatahKel-l_PakaiKel)<>0 AND l_mlProp=0 THEN 
		    BEGIN
			UPDATE S0CMEDH
			SET JatahMedical   =COALESCE(l_T20Jatah,0),
			    JatahMedicalKel=COALESCE(l_T20JatahKel,0)
			WHERE NIP=l_T21NIP AND Tahun=l_T21Tahun AND JenisMedical=l_T21JenisMedical ; 
		    END ; 
		    END IF ; 
		END LOOP;
		CLOSE l_LOOP_T21;
	   END ;
	   END IF ; -- if ada muatsi 
	       --
  	   -- Jika unpost dari Proses payroll/Harian maka update tgl. payroll	
	  IF l_Payroll='Y' THEN 
	  BEGIN
	     SELECT TglPosting,  FlagTHR
	     INTO   l_Tgl_Akhir, l_S05FlagTHR
	     FROM S05PSTD
	     WHERE NIP=l_M15NIP  AND FlagTHR=' '
	     ORDER BY NIP,TglPosting ; 
	     --
	     UPDATE M15PEGA
	     SET TglPayr=l_Tgl_Akhir
	     WHERE NIP=l_M15NIP ; 
	     ---WHERE Current Of l_LOOP_M15
	  END ;
	  END IF ;
	--
	END ; 
	END IF ;--/ AKHIR IF l_ADAS01
	--
   END ;  
   END IF ; --/ Akhir Prs_OK 

   END ;  
   END IF ; -- NGGAK TAU DARI MANA TAPI BISA 
END LOOP ; 
CLOSE l_LOOP_M15;
END ; 
$$ LANGUAGE plpgsql ;
--


/*
DECLARE l_TblName VARCHAR(8) , l_mlValid INT
exec P_UPBLKH 	     '2006-05-28', ' ', 'ZZZZ', '19', '19', 'Y'
exec P_UPBLKH 	     '2004-08-15',
		     ' ',
		     'ZZZZ',
		     'AZI0000254',
		     'AZI0000254'
*/
