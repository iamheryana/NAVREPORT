
CREATE OR REPLACE FUNCTION public.fn_nilaipend_potadvflgtr (in l_tglproses date, in l_nip varchar, in l_flgdppt varchar, in l_kddppt varchar, in l_persen numeric, in l_nilai numeric, in l_mnhari numeric, in l_gajirp numeric, in l_reverse varchar, in l_mypass varchar, in l_kdcurr varchar) RETURNS numeric AS
$BODY$ 
  DECLARE l_Flag VARCHAR(1);
          l_M03Singkatan VARCHAR(10);
          l_M03UsComp    VARCHAR(1);
	  l_M03Kolom     VARCHAR(2);
	  l_M03NoAcc     VARCHAR(10);
	  l_M03Status    VARCHAR(1);
	  l_M03Persen    DECIMAL(5,2);
	  l_M03NiLai     DECIMAL(15,2);
	  l_M03NoLyr     INT;
	  l_M03KdCurr    VARCHAR(4);
          l_S02Nilai     DECIMAL(15,2);
          l_mnKurs       DECIMAL(10,2);
          l_NilPend_Pot  DECIMAL(15,2);
          l_NilPend_PotVal DECIMAL(15,2);
          l_GajiVal      DECIMAL(15,2);          
          l_FZ1FlgGlng   VARCHAR(1);         
	  l_FZ1FlgJaba   VARCHAR(1);
     	  l_FZ1FlgKJab 	 VARCHAR(1);        	
	  l_FZ1Caba    	 VARCHAR(1);      
	  l_FZ1FlgAdv    VARCHAR(1);      
	  l_M15kdCaba    VARCHAR(4);
	  l_M15KdGlng    VARCHAR(4);
	  l_M15kdJaba    VARCHAR(4);
	  l_M15kdKJab  	 VARCHAR(4);
	  l_M15kdArea  	 VARCHAR(4);
	  l_M15kdUUsa  	 VARCHAR(4);
	  l_M15kdUKer  	 VARCHAR(8);
	  l_M65Persen    DECIMAL(5,2);
	  l_M65NiLai     DECIMAL(15,2); 
	  l_M65TRANS	 VARCHAR(1);

DECLARE l_CTR INT; 
	l_PUTER INT;
	l_FlgLoncat INT; 
BEGIN    
CREATE TEMP TABLE IF NOT EXISTS l_TEMP2 (Level1	VARCHAR(1), 
			Kode1		VARCHAR(8), 
			Level2		VARCHAR(1), 
			Kode2		VARCHAR(8), 
			M65Persen	DECIMAL(5,2), 
			M65Nilai	DECIMAL(15,2), 
			M65KdCurr	VARCHAR(4),
			M65Trans	VARCHAR(1),
			M03Persen	DECIMAL(5,2), 
			M03Nilai	DECIMAL(15,2), 
			M03KdCurr	VARCHAR(4),
			NoUrut		SERIAL)   ON COMMIT DROP ;  

---- Initialisasi data
l_S02Nilai       := 0;
l_mnKurs         := 0;
l_NilPend_Pot    := 0;
l_NilPend_PotVal := 0;
l_GajiVal        := 0;
l_NilPend_Pot    := 0; 
l_Flgloncat      := 0;
 
SELECT SUBSTR(StringFlag,10,1) 
INTO l_FZ1FlgAdv
FROM FZ2FLDA LIMIT 1 ;

SELECT	FlgGlng,      FlgKJab,      FlgJaba,      FlgCaba
INTO    l_FZ1FlgGlng, l_FZ1FlgKJab, l_FZ1FlgJaba, l_FZ1Caba
FROM FZ1FLDA ;

SELECT M15.KdCaba,  M15.KdGlng,  M15.KdJaba,  M15.KdKJab,  M15.KdArea,  M15.KdUUsa,  M15.KdUKer 
INTO   l_M15kdCaba, l_M15KdGlng, l_M15kdJaba, l_M15kdKJab, l_M15kdArea, l_M15kdUUsa, l_M15kdUKer
FROM M15PEGA M15
WHERE NIP = l_NIP ; 

INSERT INTO l_TEMP2 
SELECT M64.LEVEL1, M64.KODE1,
	M64.LEVEL2, M64.KODE2, 
	M65.Persen, M65.NILAI, M65.KDCURR, M65.Trans,    
	M03.Persen, M03.NILAI, M03.KDCURR   
FROM M64ADVH M64
LEFT JOIN M65ADVD M65 
ON M64.Level1 = M65.Level1 AND M64.Kode1 = M65.Kode1 AND 
   M64.Level2 = M65.Level2 AND M64.Kode2 = M65.Kode2 
LEFT JOIN M03DPPT M03
ON M65.FlgDppt = M03.FlgDppt AND M65.KdDppt = M03.KdDppt 
WHERE M65.FlgDppt = l_FlgDppt AND M65.KdDppt = l_KdDppt  
	AND (l_M15KDAREA = CASE WHEN M64.Level1 = '1' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDCABA = CASE WHEN M64.Level1 = '2' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDUUSA = CASE WHEN M64.Level1 = '3' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDUKER = CASE WHEN M64.Level1 = '4' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDGLNG = CASE WHEN M64.Level1 = '5' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDKJAB = CASE WHEN M64.Level1 = '6' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDJABA = CASE WHEN M64.Level1 = '7' THEN M64.Kode1 ELSE '########' END)
	AND (l_M15KDAREA = CASE WHEN M64.Level2 = '1' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDCABA = CASE WHEN M64.Level2 = '2' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDUUSA = CASE WHEN M64.Level2 = '3' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDUKER = CASE WHEN M64.Level2 = '4' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDGLNG = CASE WHEN M64.Level2 = '5' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDKJAB = CASE WHEN M64.Level2 = '6' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDJABA = CASE WHEN M64.Level2 = '7' THEN M64.Kode2 ELSE '########' END OR 
		 M64.Level2 = '8') ;

SELECT COUNT(*) 
INTO   l_PUTER
FROM l_TEMP2;

 IF l_FZ1FlgAdv='Y' AND (SELECT Level1 FROM l_TEMP2 LIMIT 1) IS NOT NULL  THEN  
    BEGIN
	l_CTR := 1 ; 
	WHILE l_CTR <= l_PUTER LOOP --- 2 
	BEGIN 
	  SELECT M65PERSEN,   M65NILAI,   M65TRANS 
	  INTO   l_M65PERSEN, l_M65NILAI, l_M65TRANS  
	  FROM l_TEMP2
	  WHERE NOURUT = l_CTR ;
	  --
	  IF l_M65TRANS = 'Y' THEN 
	  BEGIN 		
	     l_NilPend_Pot := l_NilPend_Pot+fn_PersenVsNilai(l_TglProses,l_NIP,l_FlgDpPt,l_KdDpPt,l_M65PERSEN,l_M65NILAI,l_mnhari,l_GajiRp,l_reverse,l_MyPass,l_KdCurr);
	  END ;
	  END IF ; 
	  --
	  l_CTR := l_CTR + 1 ; 
	END ;
	END LOOP ;
-- biar gak lupa. napa ye pake goto akhir, padahal di if2 dibawah ini pake ditambahin ama nilpendpot sebelumnya 
-- emang harus goto akhir, biar gak jalanin if yang dibawah (ceritanya else gitu )
--  	  GOTO AKHIR 
	l_FlgLoncat := 1 ; 
    END ;
  END IF ; 

-- ADA DI DASAR KEL CABANG  
--    IF l_FZ1Caba=''Y'' AND (SELECT KdCab FROM M09DCAB WHERE KdCab=l_M15kdCaba) IS NOT NULL AND 
-- 	COALESCE((SELECT TRANS FROM M09DCAB WHERE KDCAB = l_M15KDCABA AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT),''T'') = ''Y'' AND 
-- 	l_Flgloncat = 0 THEN 
-- 	BEGIN
--  	  GOTO AKHIR 
--         END
-- ADA DI DASAR GOLONGAN   
--  IF l_FZ1FlgGlng=''Y'' AND EXISTS(SELECT Kode FROM M13DGOL WHERE Kode=l_M15KdGlng) AND 
-- 	COALESCE((SELECT TRANS FROM M13DGOL WHERE Kode=l_M15KdGlng AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT),''T'') = ''Y'' 
--     BEGIN
--  	  GOTO AKHIR 
--     END
-- ADA DI DASAR KEL KELOMPOK JABATAN 
--  IF l_FZ1FlgKJab=''Y'' AND EXISTS(SELECT Kode FROM M07DKJB WHERE Kode=l_M15kdKJab) AND 
-- 	COALESCE((SELECT TRANS FROM M07DKJB WHERE Kode=l_M15kdKJab AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT),''T'') = ''Y'' 
--     BEGIN
--  	  GOTO AKHIR 
--     END
-- ADA DI DASAR KEL CABANG  
--  IF l_FZ1FlgJaba=''Y'' AND EXISTS(SELECT Kode FROM M05DJAB WHERE Kode=l_M15kdJaba) AND 
-- 	COALESCE((SELECT TRANS FROM M05DJAB WHERE Kode=l_M15kdJaba AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT),''T'') = ''Y'' 
--     BEGIN
--  	  GOTO AKHIR 
--     END

-- ELSE DARI IF PARAMETER PENDAPATAN KELOMPOK = y AND SESUAI KONDISI PEGAWAI AND FLAG TRANS = Y 
-- IF NOT (l_FZ1FlgAdv=''Y'' AND EXISTS(SELECT Level1 FROM l_TEMP2)) AND 

--   IF (NOT (l_FZ1Caba=''Y'' AND EXISTS(SELECT KdCab FROM M09DCAB WHERE KdCab=l_M15kdCaba) AND 
-- 	COALESCE((SELECT TRANS FROM M09DCAB WHERE KDCAB = l_M15KDCABA AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT),''Y'') = ''Y'' ) 
-- 	OR 
--     NOT (l_FZ1FlgGlng=''Y'' AND EXISTS(SELECT Kode FROM M13DGOL WHERE Kode=l_M15KdGlng) AND 
-- 	COALESCE((SELECT TRANS FROM M13DGOL WHERE Kode=l_M15KdGlng AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT),''Y'') = ''Y'')  
--  	OR
--  	NOT (l_FZ1FlgKJab=''Y'' AND EXISTS(SELECT Kode FROM M07DKJB WHERE Kode=l_M15kdKJab) AND 
-- 	COALESCE((SELECT TRANS FROM M07DKJB WHERE Kode=l_M15kdKJab AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT),''Y'') = ''Y'' ) 
-- 	OR  
-- 	NOT (l_FZ1FlgJaba=''Y'' AND EXISTS(SELECT Kode FROM M05DJAB WHERE Kode=l_M15kdJaba) AND 
-- 	COALESCE((SELECT TRANS FROM M05DJAB WHERE Kode=l_M15kdJaba AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT),''Y'') = ''Y'' ) )
--    BEGIN 
-- 	  -- U/mendapatkan Nilai A dan B, dimana Nilai A=Persen dari M03DPPT
-- 	  --                                     Nilai B=Nilai dari M03DPPT 
-- 
-- 	  SELECT l_M03Persen=Persen,
-- 			 l_M03NiLai =NiLai,
-- 			 l_M03KdCurr=KdCurr	
-- 	  FROM  M03DPPT
-- 	  WHERE FlgDpPt=l_FlgDpPt AND KdDpPt=l_KdDpPt
-- 	
-- 	  SELECT l_NilPend_Pot=fn_PersenVsNilai(l_TglProses,l_NIP,l_FlgDpPt,l_KdDpPt,l_M03Persen,l_M03NiLai,l_mnhari,l_GajiRp,l_reverse,l_MyPass,l_M03KdCurr)
--    END 

-- AKHIR: 
   
   RETURN l_NilPend_Pot ;
END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
