/****************************************
Name sprocs	: fn_Get_PREN
Create by	: Herz
Date		: 01-09-2003
Description	: Function mencari Nilai SPT 1721-A1 Sebelumnya
Call From	: Main sprocs
*****************************************/
DROP FUNCTION fn_Get_PREN(l_NIP 	VARCHAR(10),
			l_Tahun 	INT,
			l_TglProses	DATE,
			l_Kolom		INT,
			l_MyPass  	VARCHAR(128));

CREATE FUNCTION fn_Get_PREN(l_NIP 	VARCHAR(10),
                            l_Tahun 	INT,
			    l_TglProses	DATE,
			    l_Kolom	INT,
			    l_MyPass  	VARCHAR(128))
			
RETURNS DECIMAL(15,2)
AS $$ 

--
  DECLARE l_Tgl		DATE;
	  l_NilKolom	DECIMAL(15,2);
	  l_M15Pajak	VARCHAR(1);
          l_NilKolom1	DECIMAL(15,2);
          l_NilKolom2   DECIMAL(15,2); 
	  l_TglMasuk 	DATE; 
	  l_pc		VARCHAR(1);  
   --
BEGIN
   -- pindah cabangkah ? 
   SELECT SUBSTRING(StringFlag,2,1) 
   INTO l_pc
   FROM FZ2FLDA LIMIT 1;
   -- 
   -- Mencari Tanggal Sebelumnya
   IF l_pc = '1' THEN 
	   SELECT TMP.TglPayr
	   INTO l_Tgl
	   FROM
	   (--TMP
	   SELECT M15.NIP,S03.TglPayr,M15.Pajak 
	   from S03LTAX S03	
	   --
	   INNER JOIN M15PEGA M15
	   ON M15.NIP=S03.NIP
	   ---
	   INNER JOIN M10KLAS M10
	   ON M10.Kode=M15.KdKlas
	   --  
	   INNER JOIN M41JPJK M41 
	   ON M10.JnsPajak = M41.Kode
	   --
	   WHERE S03.NIP=l_NIP AND M41.JnsForm1721 = 'A1' AND --M10.Harian=0 AND 
	      COALESCE((SELECT S031.kdCaba
		      FROM S03LTAX S031
			  --
		      INNER JOIN S01HGAJ S01
		      ON S01.TglPayr=S031.TglPayr AND S01.NIP=S031.NIP AND S01.FlagH =' ' 
			  --
		      WHERE S031.NIP = S03.NIP AND S031.TglPayr> S03.TglPayr AND 
			    EXTRACT(YEAR FROM S031.TglPayr)=l_Tahun 
		      ORDER BY S031.TglPayr
		      LIMIT 1 
		      ),'') <> S03.kdCaba
	   ) AS TMP
	   --
	   WHERE EXTRACT(MONTH FROM TMP.TglPayr)<EXTRACT(MONTH FROM l_TglProses) AND 
		 EXTRACT(YEAR FROM TMP.TglPayr)=l_Tahun;
   END IF; 
--
-- kalo l_tgl null dan pegawai baru maka 
   IF l_TGL IS NULL THEN 
	   SELECT TglMasuk 
	   INTO l_TglMasuk
	   FROM M15PEGA WHERE NIP = l_NIP; 
	   
	   IF EXTRACT(YEAR FROM l_TglMasuk) = l_Tahun THEN 
	 	 SELECT TMP.TglPayr
	 	 INTO l_Tgl
		 FROM V_LTAX TMP 
		 WHERE TMP.NIP = l_NIP AND TMP.TglPayr <= l_TglMasuk; 
	   END IF; 
   END IF; 
   -- 
   l_NilKolom:=0;
   l_NilKolom1:=0;
   l_NilKolom2:=0;	
   -- 
   IF l_Kolom=15 THEN 
        SELECT (Fn_Kpusat(S03.NIP,S03.EncFixIncomePYTD,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncOLDFixIncomePYTD,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncFixIncome,l_MyPass) ::DECIMAL(15,2)) +
                         (Fn_Kpusat(S03.NIP,S03.EncVarIncomePYTD,l_MyPass) ::DECIMAL(15,2) + 
                         Fn_Kpusat(S03.NIP,S03.EncOLDVarIncomePYTD,l_MyPass) ::DECIMAL(15,2) + 
                         Fn_Kpusat(S03.NIP,S03.EncVarIncome,l_MyPass) ::DECIMAL(15,2)) +
                         (Fn_Kpusat(S03.NIP,S03.EncCol2YTD,l_MyPass) ::DECIMAL(15,2) + 
                         Fn_Kpusat(S03.NIP,S03.EncOLDCol2YTD,l_MyPass) ::DECIMAL(15,2)) +
--                         (Col3YTD+OLDCol3YTD)+
                         (Fn_Kpusat(S03.NIP,S03.EncCol4YTD,l_MyPass) ::DECIMAL(15,2) + 
                         Fn_Kpusat(S03.NIP,S03.EncOLDCol4YTD,l_MyPass) ::DECIMAL(15,2)) +
--                         (Col5YTD+OLDCol5YTD)+
                         (Fn_Kpusat(S03.NIP,S03.EncCol6YTD,l_MyPass) ::DECIMAL(15,2) + 
                         Fn_Kpusat(S03.NIP,S03.EncOLDCol6YTD,l_MyPass) ::DECIMAL(15,2)) +
                         (Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2) + 
                         Fn_Kpusat(S03.NIP,S03.EncOLDCol8PYTD,l_MyPass) ::DECIMAL(15,2) + 
                         Fn_Kpusat(S03.NIP,S03.EncKolom8,l_MyPass) ::DECIMAL(15,2)) -
                          Fn_Kpusat(S03.NIP,S03.EncOccSupport1,l_MyPass) ::DECIMAL(15,2) -
                          Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_MyPass) ::DECIMAL(15,2) +
                         (Fn_Kpusat(S03.NIP,S03.EncCol12PYTD,l_MyPass) ::DECIMAL(15,2) + 
                         Fn_Kpusat(S03.NIP,S03.EncOLDCol12PYTD,l_MyPass) ::DECIMAL(15,2) + 
                         Fn_Kpusat(S03.NIP,S03.EncKolom12,l_MyPass) ::DECIMAL(15,2)) + 
-- opbl 
--						 (CASE WHEN EXTRACT(YEAR FROM S03.TglPayr) = (SELECT EXTRACT(YEAR FROM TglMasuk) FROM M15PEGA WHERE NIP = l_NIP) AND EXISTS(SELECT NIP FROM T25OPBP WHERE NIP = l_NIP) THEN 
			(CASE WHEN EXTRACT(YEAR FROM l_TglMasuk) = l_Tahun AND 
					l_Tgl <= (SELECT TglMasuk FROM M15PEGA WHERE NIP = l_NIP) AND 
					(SELECT COUNT(NIP) FROM T25OPBP WHERE NIP = l_NIP) > 0 THEN 
-- -- --		                          Fn_Kpusat(S03.NIP,S03.EncEGIYNB,l_MyPass) ::DECIMAL(15,2) 
-- -- --							  ELSE 0 END) 
-- -- --						 - (CASE WHEN EXTRACT(YEAR FROM S03.TglPayr) = (SELECT EXTRACT(YEAR FROM TglMasuk) FROM M15PEGA WHERE NIP = l_NIP) AND EXISTS(SELECT NIP FROM T25OPBP WHERE NIP = l_NIP) THEN 
					    COALESCE(fn_cariopbl('16NB',S03.NIP,S03.TglPayr,l_mypass),0) 
--								    COALESCE(fn_cariopbl('16NB',S03.NIP,S03.TglPayr,l_mypass),0) 
--								   - COALESCE(fn_cariopbl('8R',S03.NIP,S03.TglPayr,l_mypass),0) 
-- -- 						 	   WHEN l_pc = '0' AND l_Tgl <= (SELECT TglMasuk FROM M15PEGA WHERE NIP = l_NIP) AND EXISTS(SELECT NIP FROM T25OPBP WHERE NIP = l_NIP) THEN 
-- -- 				                          Fn_Kpusat(S03.NIP,S03.EncEGIYNB,l_MyPass) ::DECIMAL(15,2) 
				ELSE 0 END) 
						
--        FROM S03LTAX S03
	INTO l_NilKolom
	FROM V_LTAX S03 
        INNER JOIN M15PEGA M15 
	ON S03.NIP = M15.NIP 
	WHERE S03.NIP=l_NIP AND S03.TglPayr=l_Tgl; 
   ELSIF l_Kolom=20 THEN 
          SELECT (Fn_Kpusat(S03.NIP,S03.EncYTDIT,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncEYITT,l_MyPass) ::DECIMAL(15,2)) - 
			Fn_Kpusat(S03.NIP,S03.EncEYIT,l_MyPass) ::DECIMAL(15,2) 
-- -- 						 	- (CASE WHEN l_Tgl <= (SELECT TglMasuk FROM M15PEGA WHERE NIP = l_NIP) AND EXISTS(SELECT NIP FROM T25OPBP WHERE NIP = l_NIP) THEN 
-- -- 								    COALESCE(fn_cariopbl('19',S03.NIP,S03.TglPayr,l_mypass),0) 
-- -- 							  ELSE 0 END) 
--          FROM S03LTAX S03
          INTO l_NilKolom 
 	  FROM V_LTAX S03 
          WHERE NIP=l_NIP AND TglPayr=l_Tgl;
   ELSIF l_Kolom=21 THEN 
          --
--          SELECT l_NilKolom1=YTDIT
          SELECT Fn_Kpusat(S03.NIP,S03.EncEYITT,l_MyPass) ::DECIMAL(15,2)
          INTO l_NilKolom1
          FROM S03LTAX S03
          WHERE NIP=l_NIP AND TglPayr=l_TglProses;
          --
          SELECT (Fn_Kpusat(S03.NIP,S03.EncYTDIT,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncEYITT,l_MyPass) ::DECIMAL(15,2)) - 
			Fn_Kpusat(S03.NIP,S03.EncEYIT,l_MyPass) ::DECIMAL(15,2)
          INTO l_NilKolom2 
          FROM S03LTAX S03
          WHERE NIP=l_NIP AND TglPayr=l_Tgl;
          --
          l_NilKolom:=COALESCE(l_NilKolom1,0)-COALESCE(l_NilKolom2,0);
   END IF; 
   --/ 
   -- Mengembalikan Nilai 
   RETURN l_NilKolom; 
END;
$$ LANGUAGE plpgsql ;

--
/*
SELECT fn_Get_PREN('4220100694',2005,'2005-09-25',21,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved')
SELECT fn_Get_PREN('41',2008,'2008-12-20',15,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved')

SELECT (Fn_Kpusat(T26.NIP,T26.EncPelunasan,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved')))
FROM T26PPHL T26 
WHERE NIP='41' AND EXTRACT(YEAR FROM Periode)=2008

*/
--
