/****************************************
Name sprocs	: R_PRE1P1
Create by	: Herz
Date		: 13-02-2003
Description	: Proses Untuk Laporan Pre-printed SPT 1721 A1
Call From	: Main Proc
Sub sprocs	: -
updated by peggy 2006 03 21 
no urut hrs selalu sama siapapun yg nyetak, makanya selalu diproses semua untuk dapet no urut 
terakhir baru di seleksi dasar security 
MODI PERAPIHAN SOURCE (PEGGY 2007 02 15) dan encrypt k14 pake l_mypass 
*****************************************/
DROP FUNCTION R_PRE1P1 (l_Tahun  	INT,
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
			l_mdTglCetak	DATE,
			l_kuasa  	VARCHAR(1),
			l_MyPass 	VARCHAR(128),
			l_mcLunas  	VARCHAR(1),          
			l_Usr_Id	INT);
--
CREATE FUNCTION R_PRE1P1 (l_Tahun  	INT,
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
			l_mdTglCetak	DATE,
			l_kuasa  	VARCHAR(1),
			l_MyPass 	VARCHAR(128),
			l_mcLunas  	VARCHAR(1),          
			l_Usr_Id	INT)
RETURNS TABLE (OUTNourut 	VARCHAR(7), 
		OUTTglCetak 	DATE,
		OUTNIP 		VARCHAR(10),
		OUTNamaPR 	VARCHAR(40),
		OUTAdrsPR 	VARCHAR(40),
		OUTKotaPR 	VARCHAR(25),
		OUTNama 	VARCHAR(25), 
		OUTAlamat 	VARCHAR(80), 
		OUTAlamatNPWP 	VARCHAR(80), 
		OUTKota 	VARCHAR(25), 
		OUTNPWP 	VARCHAR(20), 
		OUTStsSip 	VARCHAR(1), 
		OUTJnsKlmn 	VARCHAR(1), 
		OUTStsPjk 	VARCHAR(1), 
		OUTKdCaba 	VARCHAR(4), 
		OUTKdGlng 	VARCHAR(4), 
		OUTPrdAwl	DATE, 
		OUTPajak 	VARCHAR(1), 
		OUTFrBulan 	VARCHAR(9),
		OUTToBulan 	VARCHAR(9),
		OUTKeterangan 	VARCHAR(20),
		OUTTglPayr 	DATE, 
		OUTFixIncomePYTD DECIMAL(15,2), 
		OUTFixIncome 	DECIMAL(15,2),
		OUTCol2YTD 	DECIMAL(15,2),
		OUTCol3YTD 	DECIMAL(15,2),
		OUTCol4YTD 	DECIMAL(15,2), 
		OUTCol5YTD 	DECIMAL(15,2), 
		OUTCol6YTD 	DECIMAL(15,2), 
		OUTCol12PYTD 	DECIMAL(15,2), 
		OUTKolom12 	DECIMAL(15,2),
		OUTOccSupport1 	DECIMAL(15,2), 
		OUTEGIYNB 	DECIMAL(15,2), 
		OUTPTKP 	DECIMAL(15,2), 
		OUTEYIT 	DECIMAL(15,2), 
		OUTYTDIT 	DECIMAL(15,2), 
		OUTCol8PYTD 	DECIMAL(15,2), 
		OUTKolom8 	DECIMAL(15,2), 
		OUTOccSupport2 	DECIMAL(15,2), 
		OUTEYITT 	DECIMAL(15,2),
		OUTKOLOM14 	DECIMAL(15,2), 
		OUTTaxUMPYTD 	DECIMAL(15,2),
		OUTKolom15 	DECIMAL(15,2), 
		OUTKolom20 	DECIMAL(15,2), 
		OUTKolom24 	DECIMAL(15,2)) 
AS $$
--
DECLARE l_FZ1NamaPR	VARCHAR(40);
	l_FZ1AdrsPR	VARCHAR(40);
	l_FZ1KotaPR	VARCHAR(25);
--
BEGIN 
SELECT 	NamaPR,
	AdrsPR,
	KotaPR
INTO    l_FZ1NamaPR,
	l_FZ1AdrsPR,
	l_FZ1KotaPR
FROM FZ1FLDA;

-- cari no urut dulu 
CREATE TEMP TABLE l_TEMP (NIP VARCHAR(10)) ON COMMIT DROP ; 

CREATE TEMP TABLE l_TEMP2 (NOURUT DECIMAL(5,0), NIP VARCHAR(10)) ON COMMIT DROP ; 

INSERT INTO l_TEMP
SELECT Q.NIP
FROM
( --Q
SELECT V.TAHUN, M15.NIP,
	Fn_Kpusat(S03.NIP,S03.EncPTKP,l_mypass) ::DECIMAL(15,2) AS PTKP,
	(Fn_Kpusat(S03.NIP,S03.EncEYITT,l_mypass) ::DECIMAL(15,2) ) AS PPH21,--(Fn_Kpusat(S03.NIP,S03.EncYTDIT,l_mypass) ::DECIMAL(15,2) +(Fn_Kpusat(S03.NIP,S03.EncEYITT,l_mypass) ::DECIMAL(15,2) -Fn_Kpusat(S03.NIP,S03.EncEYIT,l_mypass) ::DECIMAL(15,2) )),
	-- GROSS DAN GROSS UP : K14 = EGIYNB + COL8PYTD + KOLOM8 
-- -OCC SUPPORT2 
	-- NETTO : K14 = EGIYNB + COL8PYTD + KOLOM8 - OCCSUPPORT2 + EYITT - EYIT  
	Fn_Kpusat(S03.NIP,S03.EncEGIYNB,l_mypass) ::DECIMAL(15,2)  + 
		Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_mypass) ::DECIMAL(15,2)  + 
		Fn_Kpusat(S03.NIP,S03.EncKolom8,l_mypass) ::DECIMAL(15,2)  - 
		Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_mypass) ::DECIMAL(15,2)  + 
		CASE WHEN M15.Pajak='N' THEN 
		   Fn_Kpusat(S03.NIP,S03.EncEYITT,l_mypass) ::DECIMAL(15,2)  - 
		   Fn_Kpusat(S03.NIP,S03.EncEYIT,l_mypass) ::DECIMAL(15,2) 
		 ELSE
		   0 
		 END AS KOLOM14
FROM S03LTAX S03
--
INNER JOIN V_LTAXDT V 
ON S03.TGLPAYR = V.TGLPAYR AND V.NIP = S03.NIP 
--
INNER JOIN M15PEGA M15
ON M15.NIP=S03.NIP
--
WHERE v.tahun = l_tahun 
) AS Q
--WHERE Q.KOLOM14 > Q.PTKP  
WHERE Q.KOLOM14 > Q.PTKP  AND Q.PPH21 <> 0 ;


INSERT INTO l_TEMP2 
SELECT Q2.* 
FROM 
(--Q2
 SELECT COUNT(*) AS NoUrut,M1.NIP
 FROM
 (--M1
  SELECT TMP.NIP
  FROM l_TEMP TMP 
 ) M1,
 (--M2
  SELECT TMP.NIP
  FROM l_TEMP TMP 
 ) M2
--
 WHERE M1.NIP >= M2.NIP 
 GROUP BY M1.NIP
) Q2;
--ON TMP2.NIP=TMP.NIP

--Nourut=RIGHT(('0000000' + RTRIM(CONVERT(VARCHAR(7),TMP2.NOURUT))),7),
--
--SELECT Nourut=T.NOURUT, TglCetak=l_mdTglCetak,
RETURN QUERY 
SELECT RIGHT('0000000' || RTRIM(T.NOURUT::VARCHAR(7)),7) ::VARCHAR(7) AS Nourut, 
	l_mdTglCetak AS TglCetak,
	Q.*
FROM
( --Q
 SELECT M15.NIP,
	l_FZ1NamaPR AS NamaPR,
	l_FZ1AdrsPR AS AdrsPR,
	l_FZ1KotaPR AS KotaPR,  
 	M15.Nama, M15.Alamat, M15.AlamatNPWP, M15.Kota, M15.NPWP, 
 	S01.StsSip AS Status, 
 	M15.JnsKlmn, 
	S01.StsPjk AS StsPjk, 
	M15.KdCaba, M15.KdGlng, M15.PrdAwl, M15.Pajak,
       	CASE WHEN EXTRACT(YEAR FROM M15.PrdAwl)<>l_Tahun THEN 
		'Januari' 
	       ELSE 
		CASE EXTRACT(MONTH FROM M15.PrdAwl) 
		     WHEN 1  THEN 'Januari' 
		     WHEN 2  THEN 'Februari'
		     WHEN 3  THEN 'Maret'
		     WHEN 4  THEN 'April'
		     WHEN 5  THEN 'Mei'
		     WHEN 6  THEN 'Juni'
		     WHEN 7  THEN 'Juli'
		     WHEN 8  THEN 'Agustus'
		     WHEN 9  THEN 'September'
		     WHEN 10 THEN 'Oktober'
		     WHEN 11 THEN 'November'
		     WHEN 12 THEN 'Desember'
		END
	END ::VARCHAR(9) AS FrBulan,
	CASE EXTRACT(MONTH FROM S03.TglPayr) 
		WHEN 1	THEN 'Januari' 
		WHEN 2	THEN 'Februari'
		WHEN 3	THEN 'Maret'
		WHEN 4	THEN 'April'
		WHEN 5	THEN 'Mei'
		WHEN 6	THEN 'Juni'
		WHEN 7	THEN 'Juli'
		WHEN 8	THEN 'Agustus'
		WHEN 9	THEN 'September'
		WHEN 10 THEN 'Oktober'
		WHEN 11 THEN 'November'
		WHEN 12 THEN'Desember'     
        END ::VARCHAR(9) AS ToBulan,
	M04.Keterangan,S03.TglPayr, 
	Fn_Kpusat(S03.NIP,S03.EncFixIncomePYTD,l_mypass) ::DECIMAL(15,2) AS FixIncomePYTD, 
	Fn_Kpusat(S03.NIP,S03.EncFixIncome,l_mypass) ::DECIMAL(15,2) AS FixIncome,
	Fn_Kpusat(S03.NIP,S03.EncCol2YTD,l_mypass) ::DECIMAL(15,2) AS Col2YTD,
	Fn_Kpusat(S03.NIP,S03.EncCol3YTD,l_mypass) ::DECIMAL(15,2) AS Col3YTD,
	Fn_Kpusat(S03.NIP,S03.EncCol4YTD,l_mypass) ::DECIMAL(15,2) AS Col4YTD, 
	Fn_Kpusat(S03.NIP,S03.EncCol5YTD,l_mypass) ::DECIMAL(15,2) AS Col5YTD, 
	Fn_Kpusat(S03.NIP,S03.EncCol6YTD,l_mypass) ::DECIMAL(15,2) AS Col6YTD, 
	ABS(Fn_Kpusat(S03.NIP,S03.EncCol12PYTD,l_mypass) ::DECIMAL(15,2)) AS Col12PYTD, 
	ABS(Fn_Kpusat(S03.NIP,S03.EncKolom12,l_mypass) ::DECIMAL(15,2)) AS Kolom12,
	Fn_Kpusat(S03.NIP,S03.EncOccSupport1,l_mypass) ::DECIMAL(15,2) AS OccSupport1, 
	Fn_Kpusat(S03.NIP,S03.EncEGIYNB,l_mypass) ::DECIMAL(15,2) AS EGIYNB, 
	Fn_Kpusat(S03.NIP,S03.EncPTKP,l_mypass) ::DECIMAL(15,2) AS PTKP, 
	Fn_Kpusat(S03.NIP,S03.EncEYIT,l_mypass) ::DECIMAL(15,2) AS EYIT, 
	Fn_Kpusat(S03.NIP,S03.EncYTDIT,l_mypass) ::DECIMAL(15,2) AS YTDIT, 
	Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_mypass) ::DECIMAL(15,2)   
		   + COALESCE(fn_cariopbl('8R',S03.NIP,S03.TglPayr,l_mypass),0) AS Col8PYTD, 
	Fn_Kpusat(S03.NIP,S03.EncKolom8,l_mypass) ::DECIMAL(15,2) AS Kolom8, 
	Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_mypass) ::DECIMAL(15,2) AS OccSupport2, 
	Fn_Kpusat(S03.NIP,S03.EncEYITT,l_mypass) ::DECIMAL(15,2) AS EYITT,
	-- GROSS DAN GROSS UP : K14 = EGIYNB + COL8PYTD + KOLOM8 
	-- NETTO : K14 = EGIYNB + COL8PYTD + KOLOM8 - OCCSUPPORT2 + EYITT - EYIT  
	Fn_Kpusat(S03.NIP,S03.EncEGIYNB,l_mypass) ::DECIMAL(15,2)  + 
		Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_mypass) ::DECIMAL(15,2)  + 
		Fn_Kpusat(S03.NIP,S03.EncKolom8,l_mypass) ::DECIMAL(15,2)  - 
		Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_mypass) ::DECIMAL(15,2)  + 
		CASE WHEN M15.Pajak='N' THEN 
			Fn_Kpusat(S03.NIP,S03.EncEYITT,l_mypass) ::DECIMAL(15,2)  - 
			Fn_Kpusat(S03.NIP,S03.EncEYIT,l_mypass) ::DECIMAL(15,2) 
	           ELSE
			0 
	           END AS KOLOM14, 
	COALESCE(Fn_Kpusat(S03.NIP,S03.EncTaxUMPYTD,l_mypass) ::DECIMAL(15,2),0) AS TaxUMPYTD,
	CASE WHEN EXTRACT(YEAR FROM M15.PrdAwl)<>l_Tahun OR 
		(EXTRACT(YEAR FROM M15.PrdAwl)=l_Tahun AND NOT EXISTS (SELECT * FROM T25OPBP WHERE NIP = M15.NIP))THEN 
			0
		ELSE 
			fn_Get_PREN(M15.NIP,EXTRACT(YEAR FROM S03.TglPayr)::INT,S03.TglPayr,15::INT,l_MyPass)
		END AS Kolom15, 
	CASE WHEN EXTRACT(YEAR FROM M15.PrdAwl)<>l_Tahun OR 
		(EXTRACT(YEAR FROM M15.PrdAwl)=l_Tahun AND NOT EXISTS (SELECT * FROM T25OPBP WHERE NIP = M15.NIP))THEN 
			0
	        ELSE 
			fn_Get_PREN(M15.NIP,EXTRACT(YEAR FROM S03.TglPayr) ::INT,S03.TglPayr,20::INT,l_MyPass)
		END AS Kolom20, 
	CASE WHEN l_mcLunas = 'Y' THEN 
--			fn_Get_PREN(M15.NIP,EXTRACT(YEAR FROM S03.TglPayr),S03.TglPayr,24,l_MyPass)
			fn_Get_LUNAS(S03.NIP,EXTRACT(YEAR FROM S03.TglPayr)::INT,S03.TglPayr,24::INT,l_MyPass,'')
	       ELSE 
			0
		END AS Kolom24
 FROM S03LTAX S03
--
 INNER JOIN V_LTAXDT V 
 ON S03.TGLPAYR = V.TGLPAYR AND V.NIP = S03.NIP 
--
 INNER JOIN M15PEGA M15
 ON M15.NIP=S03.NIP
--
 INNER JOIN M04HJAB M04
 ON M15.KdJaba = M04.Kode
---
 LEFT JOIN S01HGAJ S01
 ON S01.TglPayr=S03.TglPayr AND S01.NIP=S03.NIP
--
 WHERE (S03.NIP BETWEEN l_NIPFr AND l_NIPTo) AND v.tahun = l_tahun 
) AS Q
--
INNER JOIN l_TEMP2 T 
ON Q.NIP = T.NIP 
-- 
INNER JOIN 
(
SELECT * FROM fn_SECLOGIN(l_Usr_Id)
) VSL 
ON Q.Kdglng=VSL.GOL_KODE AND Q.Kdcaba=VSL.CAB_KODE
-- Kondisi data Utama
WHERE Q.KOLOM14 > Q.PTKP
ORDER BY Q.NIP, Q.TglPayr; 
--
END;
$$ LANGUAGE plpgsql ;
/*
SELECT * FROM R_PRE1P1(2010,' ','Z','2007-01-24'::dATE,'K','Copyright, 1988 (c) Microsoft Corporation, All rights reserved','Y',1);

EXEC  R_PRE1P1  2007, '40', '40', '2007-01-24', ' ', 'ZZZZ', ' ', 'ZZZZ', 'K', 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 'Y' 
*/
