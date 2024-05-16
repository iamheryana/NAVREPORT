/****************************************
Name sprocs	: P_PREAP3_A
Create by	: dender234
Date		: 2013-12-23
Description	: Proses Export SPT 1721-I Bulanan ke CSV
Note		: 
*****************************************/
DROP  FUNCTION P_PREAP3_A  (l_Periode    DATE,
				l_NIPFr      VARCHAR(10),
				l_NIPTo      VARCHAR(10),
				l_Pembetulan NUMERIC(2),
				l_MyPass     VARCHAR(128),
				l_Usr_Id	INT) ;
				  
CREATE FUNCTION P_PREAP3_A  (l_Periode    DATE,
				l_NIPFr      VARCHAR(10),
				l_NIPTo      VARCHAR(10),
				l_Pembetulan NUMERIC(2),
				l_MyPass     VARCHAR(128),
				l_Usr_Id	INT)        
--
RETURNS TABLE (TXT VARCHAR(200)) 
AS $$
--
DECLARE l_Created    	INT;
        l_Nilai		VARCHAR(17);
        l_Amount	Decimal(15);
        l_Jumlah	Decimal(15,2);
        l_LineTXT	VARCHAR(4000);		
	l_FZ1NamaPR	VARCHAR(40);
	l_FZ1NPWPPR	VARCHAR(20);
BEGIN 
--- Buat Table Temporay Permanen
-- EXEC P_CREATE_TEMP_ONLIST 'W0F',      
--               l_TableName1 OUTPUT,
--               l_Created    OUTPUT
-- --
-- IF l_Created=0
--    RETURN

--
SELECT  NamaPR,
	NPWPPR
INTO    l_FZ1NamaPR,
	l_FZ1NPWPPR
FROM FZ1FLDA;
--
CREATE TEMP TABLE l_TEMP (NIP VARCHAR(10)) ON COMMIT DROP ;   
CREATE TEMP TABLE l_TEMP2 (NOURUT DECIMAL(5,0), NIP VARCHAR(10)) ON COMMIT DROP ;   
CREATE TEMP TABLE l_TEMPSPT (MasaPjk     VARCHAR(2),
				TahunPjk    VARCHAR(4),
				Pembetulan  VARCHAR(2),
				NPWP        VARCHAR(15),
				Nama        VARCHAR(25),
				KodePjk     VARCHAR(15),
				Bruto       VARCHAR(17), 
				PPh21       VARCHAR(17),
				KodeNegara  VARCHAR(15)
				) ON COMMIT DROP ;  

INSERT INTO l_TEMP
SELECT Q.NIP
FROM
( --Q
	SELECT Q2.PERIODE, Q2.NIP, SUM(Q2.PPH21) AS PPH21, SUM(Q2.KOLOM14) AS KOLOM14, SUM(Q2.PTKPX) AS PTKPX
	FROM
	(
		SELECT  DATE_TRUNC('month',S01.TGLPAYR) AS PERIODE,
			S01.NIP,  
			0 AS PPH21,
			Fn_Kpusat(S01.NIP,S01.EncEGIYNB,l_MyPass) ::DECIMAL(15,2)+
				Fn_Kpusat(S01.NIP,S01.EncGrossIncomeBYTD,l_MyPass) ::DECIMAL(15,2) AS KOLOM14,   
			Fn_Kpusat(S01.NIP,S01.EncPTKP,l_MyPass) ::DECIMAL(15,2) AS PTKPX
		FROM S01HGAJ S01 
		INNER JOIN S03LTAX S03 ON S03.NIP=S01.NIP AND S03.TGLPAYR=S01.TGLPAYR
		INNER JOIN V_LTAXMY V  ON V.NIP = S01.NIP AND S01.TGLPAYR = V.TGLPAYR  
	 	WHERE  DATE_TRUNC('month',S01.TGLPAYR)=DATE_TRUNC('month',l_Periode)
		---------
		UNION ALL
		---------
		SELECT Q1.PERIODE, Q1.NIP, SUM(Q1.PPH21) AS PPH21,Q1.KOLOM14,Q1.PTKPX
			FROM
			(
				SELECT  DATE_TRUNC('month',S02.TGLPAYR) AS PERIODE,
					S02.NIP,  
					FN_KPUSAT(S02.NIP,S02.ENCNILAI,l_MyPass) ::DECIMAL(15,2) AS PPH21,
					0 AS KOLOM14,
					0 AS PTKPX 
				FROM S02DGAJ S02
				INNER JOIN S03LTAX S03 ON S03.NIP=S02.NIP AND S03.TGLPAYR=S02.TGLPAYR
		  		WHERE S02.FLGDPPT='P' AND S02.KDDPPT='PJK'  
			) Q1
		WHERE Q1.PERIODE=DATE_TRUNC('month',l_periode)
		GROUP BY Q1.NIP,Q1.PERIODE,Q1.KOLOM14,Q1.PTKPX 	 
	) Q2
	GROUP BY Q2.NIP,Q2.PERIODE
) AS Q
WHERE Q.KOLOM14 > Q.PTKPX  AND Q.PPH21<>0;
-- -- --
INSERT INTO l_TEMP2 
SELECT Q2.* 
FROM 
(--Q2
 SELECT COUNT(*) AS NoUrut,M1.NIP
 FROM (SELECT NIP FROM l_TEMP) M1,
 	  (SELECT NIP FROM l_TEMP) M2
 WHERE M1.NIP >= M2.NIP
 GROUP BY M1.NIP
) Q2;
--  

INSERT INTO l_TEMPSPT
SELECT Q.MasaPjk, Q.TahunPjk, Q.Pembetulan, Q.NPWP, Q.Nama, Q.KodePjk, Q.Bruto, Q.PPh21, '' AS KodeNegara
FROM
(--Q 
	SELECT  RIGHT(('00' || RTRIM(EXTRACT(MONTH FROM l_Periode)::VARCHAR(2))),2) AS MasaPjk,
		EXTRACT(YEAR FROM l_Periode) AS TahunPjk,
		l_Pembetulan AS Pembetulan, 
		CASE WHEN (M15.NPWP :: BIGINT)=0 THEN '000000000000000'
                     ELSE RIGHT(('000000000000000' || RTRIM((M15.NPWP))::VARCHAR(2)),15) END AS NPWP, 
		Q1.NIP,M15.NAMA,
		'21-100-01' AS KodePjk,
		SUM(Q1.KOLOM9) AS BRUTO, 
		SUM(Q1.PPH21) AS PPH21,
		SUM(Q1.KOLOM14) AS KOLOM14, 
		SUM(Q1.PTKP) AS PTKP,
		SUM(Q1.TAXUMPYTD) AS TAXUMPYTD,
		M15.KdCaba, 
		M15.KdGlng
	FROM
	( --Q1
	---------SINI 
		/***** HITUNG PENDAPATAN BRUTO *****/
		SELECT  DATE_TRUNC('month',S02.TGLPAYR) AS PERIODE,S02.NIP, --S03.KdCaba,
			FN_KPUSAT(S02.NIP,S02.ENCNILAI,l_MyPass) ::DECIMAL(15,2) AS KOLOM9,
			0 AS PPH21,
			0 AS KOLOM14,
			0 AS PTKP, 
			0 AS TAXUMPYTD
		FROM S02DGAJ S02
		INNER JOIN S03LTAX S03 ON S03.NIP=S02.NIP AND S03.TGLPAYR=S02.TGLPAYR
 		WHERE S02.FLGDPPT='D' AND S02.KDDPPT<>'JHT' AND S02.KDDPPT<>'PESA' AND
		      (S02.NIP BETWEEN l_NIPFr AND l_NIPTo) AND  
		       DATE_TRUNC('month',S02.TGLPAYR)=DATE_TRUNC('month',l_Periode)   
		---------
		UNION ALL
		---------
		/***** HITUNG PPH21 *****/
		SELECT  DATE_TRUNC('month',S02.TGLPAYR) AS PERIODE,S02.NIP, --S03.KdCaba, 
			0 AS KOLOM9,
			FN_KPUSAT(S02.NIP,S02.ENCNILAI,l_MyPass) ::DECIMAL(15,2) AS PPH21,
			0 AS KOLOM14,
			0 AS PTKP,
			0 AS TAXUMPYTD
		FROM S02DGAJ S02 
		INNER JOIN S03LTAX S03 ON S03.NIP=S02.NIP AND S03.TGLPAYR=S02.TGLPAYR
  		WHERE S02.FLGDPPT='P' AND S02.KDDPPT='PJK'AND
		      (S02.NIP BETWEEN l_NIPFr AND l_NIPTo) AND 
		      DATE_TRUNC('month',S02.TGLPAYR)=DATE_TRUNC('month',l_Periode)   
		---------
		UNION ALL
		---------
		/***** HITUNG PTKP & TAXUMPYTD *****/
		SELECT  DATE_TRUNC('month',S03.TGLPAYR) AS PERIODE,S03.NIP, --S03.KdCaba, 
			0 AS KOLOM9,
			0 AS PPH21,
			0 AS KOLOM14,
			Fn_Kpusat(S03.NIP,S03.EncPTKP,l_MyPass) ::DECIMAL(15,2) AS PTKP,
			CASE WHEN COALESCE(Fn_Kpusat(S03.NIP,S03.EncTaxUMPYTD,l_MyPass) ::DECIMAL(15,2),0) > (Fn_Kpusat(S03.NIP,S03.EncYTDIT,l_MyPass) ::DECIMAL(15,2) + (Fn_Kpusat(S03.NIP,S03.EncEYITT,l_MyPass) ::DECIMAL(15,2) - Fn_Kpusat(S03.NIP,S03.EncEYIT,l_MyPass) ::DECIMAL(15,2))) 
				THEN 
					(Fn_Kpusat(S03.NIP,S03.EncYTDIT,l_MyPass) ::DECIMAL(15,2) + 
					(Fn_Kpusat(S03.NIP,S03.EncEYITT,l_MyPass) ::DECIMAL(15,2) -
					Fn_Kpusat(S03.NIP,S03.EncEYIT,l_MyPass) ::DECIMAL(15,2))) 
				ELSE 
					COALESCE(Fn_Kpusat(S03.NIP,S03.EncTaxUMPYTD,l_MyPass) ::DECIMAL(15,2),0) 
				END AS TAXUMPYTD
		FROM S03LTAX S03  
		INNER JOIN V_LTAXMY V  ON V.NIP = S03.NIP AND S03.TGLPAYR = V.TGLPAYR 
	 	WHERE (S03.NIP BETWEEN l_NIPFr AND l_NIPTo) AND 
		      DATE_TRUNC('month',S03.TGLPAYR)=DATE_TRUNC('month',l_Periode)   
		---------
		UNION ALL
		---------
		/***** HITUNG NETTO DISETAHUNKAN (KOLOM 14)*****/
		SELECT  DATE_TRUNC('month',S01.TGLPAYR) AS PERIODE,S01.NIP, --S03.KdCaba, 
			0 AS KOLOM9,
			0 AS PPH21,
			(Fn_Kpusat(S01.NIP,S01.EncEGIYNB,l_MyPass) ::DECIMAL(15,2)) + 
				(Fn_Kpusat(S01.NIP,S01.EncGrossIncomeBYTD,l_MyPass) ::DECIMAL(15,2)) AS KOLOM14,   
			0 AS PTKP,
			0 AS TAXUMPYTD
		FROM S01HGAJ S01 
		INNER JOIN S03LTAX S03 ON S03.NIP=S01.NIP AND S03.TGLPAYR=S01.TGLPAYR
		INNER JOIN V_LTAXMY V  ON V.NIP = S01.NIP AND S01.TGLPAYR = V.TGLPAYR 
	 	WHERE (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) AND 
		      DATE_TRUNC('month',S01.TGLPAYR)=DATE_TRUNC('month',l_Periode) 
	) Q1
	INNER JOIN M15PEGA M15 ON M15.NIP=Q1.NIP
	INNER JOIN 
	(
	SELECT * FROM fn_SECLOGIN(l_Usr_Id)
	) VSL 
	ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE	
	GROUP BY Q1.PERIODE,Q1.NIP,M15.NAMA,M15.NPWP,M15.KdCaba, M15.KdGlng
) Q 
-- Inner Join ke No. Urut
LEFT JOIN l_TEMP2 T ON Q.NIP = T.NIP 
--
-- Kondisi data Utama
WHERE --(Q.KdGlng BETWEEN l_FGOL AND l_TGOL)AND
      --(Q.KdCaba BETWEEN l_FCAB AND l_TCAB) AND  
	Q.KOLOM14 > Q.PTKP  AND Q.PPH21<>0 
ORDER BY NOURUT;
--
--SET l_LineTXT = 'INSERT INTO '+l_TableName1+' SELECT ''Masa Pajak;Tahun Pajak;Pembetulan;NPWP;Nama;Kode Pajak;Jumlah Bruto;Jumlah PPh;Kode Negara;Test OK'''
--
RETURN QUERY 
 SELECT LTRIM(RTRIM('Masa Pajak;Tahun Pajak;Pembetulan;NPWP;Nama;Kode Pajak;Jumlah Bruto;Jumlah PPh;Kode Negara')) :: VARCHAR(200) AS TXT 
 UNION ALL 
 SELECT RTRIM(SPT.MasaPjk)||';'||RTRIM(SPT.TahunPjk)||';'||RTRIM(SPT.Pembetulan)||';'|| 
  RTRIM(SPT.NPWP)||';'||RTRIM(SPT.Nama)||';'||RTRIM(SPT.KodePjk)||';'||RTRIM(SPT.Bruto)||';'||RTRIM(SPT.PPh21)||';'||RTRIM(SPT.KodeNegara) :: VARCHAR(200) AS TXT 
  FROM l_TEMPSPT SPT;
 
/*
' INSERT INTO '+l_TableName1+' SELECT SPT.MasaPjk+'';''+'+
' SPT.TahunPjk+'';''+'+ 
' SPT.Pembetulan+'';''+'+ 
' SPT.NPWP+'';''+'+  
' SPT.Nama+'';''+'+ 
' SPT.KodePjk+'';''+'+ 
' SPT.Bruto+'';''+'+ 
' SPT.PPh21+'';''+'+ 
' SPT.KodeNegara FROM l_TEMPSPT SPT'
*/
END;
$$ LANGUAGE plpgsql ;

/*                                                                                       
SELECT * FROM P_PREAP3_A ( '2013-01-01':: DATE ,' ' ,'ZZ',0,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved' , 1); 

selecT * from s03ltax 
*/
