/****************************************
Name sprocs	: P_Hitung_UMP20092009
Create by	: Wati
Date		: 25-07-2003
Description	: Hitung UMP
Call From	: Sub Routine
Sub sprocs	: -
*****************************************/
DROP FUNCTION P_Hitung_UMP2009(l_TglProses   DATE,
					    l_NIP	  VARCHAR(10),
					    l_NPWP        VARCHAR(20), 
					    l_FlgDTPNPWP  VARCHAR(1), 
				 	    l_FlgDTPTdkNPWP VARCHAR(1), 
					    l_PotPajak	  DECIMAL(15,2),
					    l_IncDP2009   DECIMAL(15,2),
					    l_FZ1MaxBruto DECIMAL(15,2),
					    OUT l_TaxAPaidUMP DECIMAL(15,2),
					    OUT l_TAXUMP      DECIMAL(15,2),
					    OUT l_TAXUMPYTD   DECIMAL(15,2),
					    l_MyPass      VARCHAR(128),
					    l_S01_ID	  INT);
--
CREATE FUNCTION P_Hitung_UMP2009(l_TglProses   DATE,
					    l_NIP	  VARCHAR(10),
					    l_NPWP        VARCHAR(20), 
					    l_FlgDTPNPWP  VARCHAR(1), 
				 	    l_FlgDTPTdkNPWP VARCHAR(1), 
					    l_PotPajak	  DECIMAL(15,2),
					    l_IncDP2009   DECIMAL(15,2),
					    l_FZ1MaxBruto DECIMAL(15,2),
					    OUT l_TaxAPaidUMP DECIMAL(15,2),
					    OUT l_TAXUMP      DECIMAL(15,2),
					    OUT l_TAXUMPYTD   DECIMAL(15,2),
					    l_MyPass      VARCHAR(128),
					    l_S01_ID	  INT)
AS $$ 
--                         
DECLARE l_PersenPJK   	DECIMAL(6,2);
        l_TglAkhir    	DATE;
	l_FXTAX	 	DECIMAL(6,2);
	l_ALLDTP	DECIMAL(15,2);
	l_S02DTP	DECIMAL(15,2);
	l_DP		DECIMAL(15,2);
        l_M03Singkatan 	VARCHAR(10);
	l_M03KdCurr	VARCHAR(4);
	l_NUMNPWP	DECIMAL(20,0);

BEGIN 
-- TglAkhir
l_TglAkhir := ' ';

SELECT TglPosting
INTO   l_TglAkhir
FROM S05PSTD
WHERE NIP=l_NIP  AND TglPosting<l_TglProses
ORDER BY NIP,TglPosting;

-- FAKTOR PENGALI PAJAK 
SELECT FaktorXPajak 
INTO l_FXTAX 
FROM FZ2FLDA LIMIT 1 ;

-- NPWP 
l_NUMNPWP := CASE WHEN TRIM(BOTH FROM l_NPWP) ~ '^[0-9]+$' = T THEN 
			TRIM(BOTH FROM l_NPWP) ::DECIMAL(20,0) 
		ELSE 
			0 
		END ;
--
-- INIT 
l_DP := 0; 
l_ALLDTP := 0;

SELECT SUM(COALESCE(Q.DP,0))
INTO l_DP 
FROM
(
SELECT DP=fn_KPusat(S03.NIP,S03.EncTaxUMP,l_MyPass) ::DECIMAL(15,2)
FROM S03LTAX S03
WHERE S03.NIP=l_NIP AND DATE_TRUNC('month',l_TglProses) = DATE_TRUNC('month',S03.TglPayr)
) Q ;
--
-- SELECT l_TAXUMPYTD = COALESCE(Q.DP,0)
-- FROM
-- (
-- SELECT CONVERT(DECIMAL(15,2),fn_KPusat(S03.NIP,S03.EncTaxUMPYTD,l_MyPass)) AS DP 
-- FROM S03LTAX S03
-- INNER JOIN 
-- (
-- SELECT S03.NIP, TglPayr = MAX(S03.TglPayr) 
-- FROM S03LTAX S03 
-- WHERE S03.NIP=l_NIP AND l_TglProses > S03.TglPayr AND YEAR(l_TglProses) = YEAR(S03.TglPayr) 
-- --WHERE S03.NIP=l_NIP AND CONVERT(VARCHAR(6),l_TglProses,112) = CONVERT(VARCHAR(6),S03.TglPayr,112)
-- GROUP BY S03.NIP 
-- ) X03 
-- ON S03.NIP = X03.NIP AND S03.TglPayr = X03.TglPayr 
-- ) Q 

--
SELECT SkDpPt,         KdCurr  
INTO   l_M03Singkatan, l_M03KdCurr
FROM M03DPPT
WHERE FlgDpPt='P' AND KdDpPt='PDTP';
--
IF (l_IncDP2009 <= l_FZ1MaxBruto) THEN 
BEGIN 
	IF 	l_NUMNPWP <> 0  -- NIP ADA NPWP 
		OR 
		(l_FlgDTPTdkNPWP = 'Y' AND l_NUMNPWP = 0) THEN  -- DTPTdkNPWP = Y DAN NIP tidak ADA NPWP  
	BEGIN 
		l_ALLDTP := l_PotPajak ;	
	END ;
	END IF; 
	IF 	l_NUMNPWP = 0  THEN -- NIP TIDAK ADA NPWP 
	BEGIN 
		l_ALLDTP := ROUND((l_PotPajak * 100) / l_FXTAX, 0) ;
	END ;
	END IF; 
	-- 
	l_TaxAPaidUMP := COALESCE(l_DP,0); 
	l_TAXUMP      := l_ALLDTP + COALESCE(l_DP,0) ;--, 
--		   l_TAXUMPYTD = l_TAXUMPYTD + l_TAXUMP - COALESCE(l_DP,0) 

	l_S02DTP := CASE WHEN (l_TAXUMP - COALESCE(l_DP,0)) > 0 THEN 
				(l_TAXUMP - COALESCE(l_DP,0)) * (-1) 
			  ELSE 
				(l_TAXUMP - COALESCE(l_DP,0))
			END  ; 
	
	DELETE FROM S02DGAJ
	WHERE TglPayr=l_TglProses AND NIP=l_NIP AND FlgDpPt='P' AND KdDpPt='PDTP';
	--
	INSERT INTO S02DGAJ(TglPayr,    NIP,  FlgDpPt,KdDpPt,FlgAngs,Singkatan,    Nilai,KdCurr,NilaiVal,Kolom,NoAcc,UsComp,NoLyr,FlgNonGL,EncNilai,                                         EncNilaiVal,                                     S01_ID)
	          VALUES   (l_TglProses,l_NIP,'P',    'PDTP', ' ',   l_M03Singkatan,0,   'IDR' ,0,       ' ',  ' ',  'K',   0,    0,       fn_KCabang(l_NIP,l_S02DTP ::VARCHAR(17),l_MyPass),fn_KCabang(l_NIP,l_S02DTP ::VARCHAR(17),l_MyPass),l_S01_ID);
END ;  
ELSE 
BEGIN 
	l_S02DTP := COALESCE(l_DP,0);

	IF l_S02DTP <> 0 THEN 
	BEGIN 	
		DELETE FROM S02DGAJ
		WHERE TglPayr=l_TglProses AND NIP=l_NIP AND FlgDpPt='P' AND KdDpPt='PDTP';
		--
		INSERT INTO S02DGAJ(TglPayr,    NIP,  FlgDpPt,KdDpPt,FlgAngs,Singkatan,     Nilai,KdCurr,NilaiVal,Kolom,NoAcc,UsComp,NoLyr,FlgNonGL,EncNilai,                                         EncNilaiVal,                                     S01_ID)
		          VALUES   (l_TglProses,l_NIP,'P',    'PDTP', ' ',   l_M03Singkatan,0,   'IDR'  ,0,       ' ',  ' ',  'K',   0,    0,       fn_KCabang(l_NIP,l_S02DTP ::VARCHAR(17),l_MyPass),fn_KCabang(l_NIP,l_S02DTP ::VARCHAR(17),l_MyPass),l_S01_ID);
	END ;
	END IF; 

	l_TaxAPaidUMP := COALESCE(l_DP,0); 
	l_TAXUMP = 0;--, 
--		   l_TAXUMPYTD = l_TAXUMPYTD + l_TAXUMP - COALESCE(l_DP,0) 
END;
END IF;
END;  

$$ LANGUAGE plpgsql ;

/*
EXEC        P_Hitung_UMP2009 '2003-01-30',
    		         'HERZ',
                         2880000, 
                         'Y',
                         15,
                         18750,
                         1,
                         l_TaxAPaidUMP  OUTPUT,
                         l_NilUMP       OUTPUT,
                         l_TaxUMP_PYTD  OUTPUT,
                         l_FlgDLL       OUTPUT,
                         l_TaxUMP       OUTPUT,
                         l_Hari         OUTPUT,   
                         l_KdUMR        OUTPUT,
                         l_FlgStruk     OUTPUT,                      
			 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
--
SELECT                   TaxAPaidUMP=l_TaxAPaidUMP,
                         NilUMP=l_NilUMP     ,
                         TaxUMP_PYTD=l_TaxUMP_PYTD,
                         FlgDLL=l_FlgDLL     ,
                         TaxUMP=l_TaxUMP     ,
                         Hari=l_Hari       ,
                         KdUMR=l_KdUMR      ,
                         FlgStruk=l_FlgStruk   

*/
