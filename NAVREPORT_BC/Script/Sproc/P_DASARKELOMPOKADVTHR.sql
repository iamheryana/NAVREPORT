
DROP FUNCTION p_dasarkelompokadvthr(IN l_tglproses date, IN l_nip character varying, IN l_flagkhusus character varying, IN l_gajirp numeric, IN l_mnhari numeric, IN l_prs_harian character varying, IN l_hkerja numeric, IN l_mypass character varying, INOUT l_total_fix numeric);

---
CREATE FUNCTION p_dasarkelompokadvthr(IN l_tglproses date, IN l_nip character varying, IN l_flagkhusus character varying, IN l_gajirp numeric, IN l_mnhari numeric, IN l_prs_harian character varying, IN l_hkerja numeric, IN l_mypass character varying, INOUT l_total_fix numeric)
  RETURNS numeric AS
$$
--
DECLARE l_M15kdCaba  VARCHAR(4);
	l_M15KdGlng  VARCHAR(4);
	l_M15kdJaba  VARCHAR(4);
	l_M15kdKJab  VARCHAR(4);
	l_M15kdArea  VARCHAR(4);
	l_M15kdUUsa  VARCHAR(4);
	l_M15kdUKer  VARCHAR(8);

DECLARE l_Kondisi VARCHAR(4000);
	l_TotNilai DECIMAL(15,2);

BEGIN 
CREATE TEMP TABLE l_TEMP(Level1	VARCHAR(1) , 
			Kode1	VARCHAR(8) , 
			Level2	VARCHAR(1) , 
			Kode2	VARCHAR(8) , 
			FlgDppt VARCHAR(1) , 
			KdDppt  VARCHAR(4) , 
			Persen	DECIMAL(5,2), 
			Nilai	DECIMAL(15,2), 
			KdCurr	VARCHAR(4) ,
			Trans	VARCHAR(1) ,
			NoUrut	SERIAL) ON COMMIT DROP ;  
--
SELECT M15.KdCaba,  M15.KdGlng,  M15.KdJaba,  M15.KdKJab,  M15.KdArea,  M15.KdUUsa,  M15.KdUKer 
INTO   l_M15kdCaba, l_M15KdGlng, l_M15kdJaba, l_M15kdKJab, l_M15kdArea, l_M15kdUUsa, l_M15kdUKer
FROM M15PEGA M15
WHERE NIP = l_NIP ;
--
INSERT INTO l_TEMP 
SELECT M64.LEVEL1, M64.KODE1,
	M64.LEVEL2, M64.KODE2, 
	M65.FlgDppt, M65.KdDppt, 
	M65.Persen, M65.NILAI, M65.KDCURR, M65.Trans    
FROM M64ADVH M64
LEFT JOIN M65ADVD M65 
ON M64.Level1 = M65.Level1 AND M64.Kode1 = M65.Kode1 AND 
   M64.Level2 = M65.Level2 AND M64.Kode2 = M65.Kode2 
LEFT JOIN M03DPPT M03
ON M65.FlgDppt = M03.FlgDppt AND M65.KdDppt = M03.KdDppt 
WHERE (l_M15KDAREA = CASE WHEN M64.Level1 = '1' THEN M64.Kode1 ELSE '########' END OR 
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
--
l_Kondisi := 'M46.FlgDpPt IS NULL' ;
---*
/***********************
-- Execute l_SQL
************************/
l_TotNilai := 0 ;
--
EXECUTE '
SELECT SUM(fn_NilaiPend_Pot($1,$2,M09.FlgDpPt,M09.KdDpPt,M09.Persen,M09.Nilai,
       CASE WHEN M03.JnDpPt=''B'' THEN 1 ELSE $3 END,$4,''T'',$5,CASE WHEN (M09.Nilai=0 AND M09.Persen=0) THEN M03.KdCurr ELSE M09.KdCurr END)) * (FN_GETPROP($2,$3,$1)/$3)
--
FROM l_TEMP M09
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=M09.FlgDpPt AND M03.KdDpPt= M09.KdDpPt
LEFT JOIN M46PPKH M46
ON M46.FlgDpPt=M09.FlgDpPt AND M46.KdDpPt= M09.KdDpPt
WHERE M03.STATUS=''1'' AND M09.FlgDpPt=''D'' AND M09.TRANS <> ''Y'' AND ' || l_Kondisi ||' 
GROUP BY M09.FlgDpPt;'
INTO l_TotNilai 
USING l_TglProses, l_NIP, l_mnHari, l_GajiRp, l_MyPass ;

l_Total_Fix := COALESCE(l_Total_Fix,0)+COALESCE(l_TotNilai,0); 
END ; 

$$  LANGUAGE plpgsql
