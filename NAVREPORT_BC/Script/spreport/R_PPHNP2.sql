/****************************************
Name sprocs	: R_PPHNP2
Create by	: Herz
Date		: 31-07-2003
Description	: Proses Untuk Laporan Perincian PPh Pasal 21-A U/ Pindah Cabang
Call From	: Main Proc
Sub sprocs	: -
*****************************************/
DROP FUNCTION R_PPHNP2 (l_Periode 	DATE,
			l_NIPFr	 	VARCHAR(10),
			l_NIPTo	 	VARCHAR(10),
                        l_Cabang  	VARCHAR(4),
			l_MyPass	VARCHAR(128),
			l_Usr_Id	INT);
--
CREATE FUNCTION R_PPHNP2 (l_Periode 	DATE,
			l_NIPFr	 	VARCHAR(10),
			l_NIPTo	 	VARCHAR(10),
                        l_Cabang  	VARCHAR(4),
			l_MyPass	VARCHAR(128),
			l_Usr_Id	INT)
--
RETURNS TABLE (OUTNIP		VARCHAR(10),
		OUTNama		VARCHAR(25),
		OUTTetap 	DECIMAL(12),
		OUTTunjPjk 	DECIMAL(12),
		OUTTdktetap 	DECIMAL(12),
		OUTTHR 		DECIMAL(12),
		OUTBiayaJabat 	DECIMAL(12),
		OUTAstek 	DECIMAL(12),
		OUTPengBruto	DECIMAL(12),
		OUTPTKP 	DECIMAL(12),
		OUTPKP 		DECIMAL(12),
		OUTPPh 		DECIMAL(12),
		OUTYTD 		DECIMAL(12),
		OUTPYTD 	DECIMAL(12),
		OUTTerutang 	DECIMAL(12),
		OUTDitanggung 	DECIMAL(12))

AS $$
--
DECLARE l_FZ1NamaPR	VARCHAR(40);
--
BEGIN 
SELECT  NamaPR
INTO 	l_FZ1NamaPR
FROM FZ1FLDA;
--
-- Mengola security Cabang
--IF l_Cabang NOT BETWEEN  l_FCAB AND l_TCAB
--   BEGIN
--     SET l_Cabang='XXXX'
--   END
--*

--
RETURN QUERY 
SELECT 	M15.NIP, M15.Nama,
       	Fn_Kpusat(S03.NIP,S03.EncFixIncomePYTD,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncFixIncome,l_MyPass) ::DECIMAL(15,2) AS Tetap,
       	Fn_Kpusat(S03.NIP,S03.EncCol2YTD,l_MyPass) ::DECIMAL(15,2) AS TunjPjk,
	Fn_Kpusat(S03.NIP,S03.EncCol3YTD,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncCol4YTD,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncCol5YTD,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncCol6YTD,l_MyPass) ::DECIMAL(15,2) AS Tdktetap,
	Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncKolom8,l_MyPass) ::DECIMAL(15,2) AS THR
,--				+ CASE WHEN EXTRACT(YEAR FROM S03.TglPayr) = EXTRACT(YEAR FROM M15.TglMasuk) THEN 
--							COALESCE(fn_cariopbl('8R',S03.NIP,M15.TglMasuk,l_mypass),0) 
--						ELSE 0 END,
-- -- 		BiayaJabat=(Fn_Kpusat(S03.NIP,S03.EncOccSupport1,l_MyPass) ::DECIMAL(15,2)-Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2))+(Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_MyPass) ::DECIMAL(15,2)-Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2)),
	Fn_Kpusat(S03.NIP,S03.EncOccSupport1,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_MyPass) ::DECIMAL(15,2) AS BiayaJabat,
	ABS(Fn_Kpusat(S03.NIP,S03.EncCol12PYTD,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncKolom12,l_MyPass) ::DECIMAL(15,2)) AS Astek,
	CASE WHEN M15.Pajak= 'G' THEN
		    (Fn_Kpusat(S03.NIP,S03.EncEGIYNB,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncKolom8,l_MyPass) ::DECIMAL(15,2)) - 
			Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_MyPass) ::DECIMAL(15,2)
		 ELSE
		    ((Fn_Kpusat(S03.NIP,S03.EncEGIYNB,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncKolom8,l_MyPass) ::DECIMAL(15,2)) - 
			Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_MyPass) ::DECIMAL(15,2)) + 
			(Fn_Kpusat(S03.NIP,S03.EncEYITT,l_MyPass) ::DECIMAL(15,2) - 
			Fn_Kpusat(S03.NIP,S03.EncEYIT,l_MyPass) ::DECIMAL(15,2))
		 END AS PengBruto
,--				+ CASE WHEN EXTRACT(YEAR FROM S03.TglPayr) = EXTRACT(YEAR FROM M15.TglMasuk) THEN 
--							COALESCE(fn_cariopbl('8R',S03.NIP,M15.TglMasuk,l_mypass),0) 
--						ELSE 0 END,
	Fn_Kpusat(S03.NIP,S03.EncPTKP,l_MyPass) ::DECIMAL(15,2) AS PTKP,
	FLOOR(((CASE WHEN M15.Pajak= 'G' THEN
		    (Fn_Kpusat(S03.NIP,S03.EncEGIYNB,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncKolom8,l_MyPass) ::DECIMAL(15,2)) - 
			Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_MyPass) ::DECIMAL(15,2)
		 ELSE
		    ((Fn_Kpusat(S03.NIP,S03.EncEGIYNB,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncKolom8,l_MyPass) ::DECIMAL(15,2)) - 
			Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_MyPass) ::DECIMAL(15,2)) + 
			(Fn_Kpusat(S03.NIP,S03.EncEYITT,l_MyPass) ::DECIMAL(15,2) - 
			Fn_Kpusat(S03.NIP,S03.EncEYIT,l_MyPass) ::DECIMAL(15,2))
		 END
)--				+ CASE WHEN EXTRACT(YEAR FROM S03.TglPayr) = EXTRACT(YEAR FROM M15.TglMasuk) THEN 
--							COALESCE(fn_cariopbl('8R',S03.NIP,M15.TglMasuk,l_mypass),0) 
--						ELSE 0 END)
			- Fn_Kpusat(S03.NIP,S03.EncPTKP,l_MyPass) ::DECIMAL(15,2))/1000) * 1000 AS PKP,
	Fn_Kpusat(S03.NIP,S03.EncEYITT,l_MyPass) ::DECIMAL(15,2) AS PPh,
	Fn_Kpusat(S03.NIP,S03.EncYTDIT,l_MyPass) ::DECIMAL(15,2) + 
		(Fn_Kpusat(S03.NIP,S03.EncEYITT,l_MyPass) ::DECIMAL(15,2) - 
		Fn_Kpusat(S03.NIP,S03.EncEYIT,l_MyPass) ::DECIMAL(15,2))
			+ CASE WHEN EXTRACT(YEAR FROM S03.TglPayr) = EXTRACT(YEAR FROM M15.TglMasuk) THEN 
						COALESCE(fn_cariopbl('19NB',S03.NIP,l_Periode,l_mypass),0) 
					ELSE 0 END AS YTD,
	CASE WHEN EXTRACT(MONTH FROM S03.TglPayr)=1 THEN 
			0 
		 ELSE 
--				fn_Get_PREN(M15.NIP,EXTRACT(YEAR FROM S03.TglPayr),S03.TglPayr,20,l_MyPass)
		Fn_Kpusat(S03.NIP,S03.EncIncTaxAPaidNB,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncIncTaxAPaidB,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncOLDIncTaxAPaidNB,l_MyPass) ::DECIMAL(15,2) + 
			Fn_Kpusat(S03.NIP,S03.EncOLDIncTaxAPaidB,l_MyPass) ::DECIMAL(15,2)
--			S03.IncTaxAPaidNB+S03.IncTaxAPaidB 
		 END AS PYTD
,--				+ CASE WHEN EXTRACT(YEAR FROM S03.TglPayr) = EXTRACT(YEAR FROM M15.TglMasuk) THEN 
--							COALESCE(fn_cariopbl('19NB',S03.NIP,l_Periode,l_mypass),0) 
--						ELSE 0 END,
	((Fn_Kpusat(S03.NIP,S03.EncYTDIT,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncEYITT,l_MyPass) ::DECIMAL(15,2)) - 
		Fn_Kpusat(S03.NIP,S03.EncEYIT,l_MyPass) ::DECIMAL(15,2)) -  
		-- fn_Get_PREN(M15.NIP,EXTRACT(YEAR FROM S03.TglPayr),S03.TglPayr,20,l_MyPass),
		(Fn_Kpusat(S03.NIP,S03.EncIncTaxAPaidNB,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncIncTaxAPaidB,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncOLDIncTaxAPaidNB,l_MyPass) ::DECIMAL(15,2) + 
		Fn_Kpusat(S03.NIP,S03.EncOLDIncTaxAPaidB,l_MyPass) ::DECIMAL(15,2)) AS Terutang,
--(S03.YTDIT+(S03.EYITT-S03.EYIT))-(S03.IncTaxAPaidNB+S03.IncTaxAPaidB),
-- 		Ditanggung=CASE WHEN fn_Get_PREN(M15.NIP,EXTRACT(YEAR FROM S03.TglPayr),S03.TglPayr,21,l_MyPass)>0 AND 
-- 							COALESCE(Fn_Kpusat(S03.NIP,S03.EncTaxUMPYTD,l_MyPass) ::DECIMAL(15,2),0) > fn_Get_PREN(M15.NIP,EXTRACT(YEAR FROM S03.TglPayr),S03.TglPayr,21,l_MyPass) THEN 
-- 					 fn_Get_PREN(M15.NIP,EXTRACT(YEAR FROM S03.TglPayr),S03.TglPayr,21,l_MyPass)
-- 				  ELSE 
-- 	 				 COALESCE(Fn_Kpusat(S03.NIP,S03.EncTaxUMPYTD,l_MyPass) ::DECIMAL(15,2),0) 
-- 	   			  END 
	COALESCE(Fn_Kpusat(S03.NIP,S03.EncTaxUMP,l_MyPass) ::DECIMAL(15,2),0) AS Ditanggung 
--COALESCE(S03.TaxUMP,0)

FROM  S03LTAX S03
--
INNER JOIN M15PEGA M15
ON M15.NIP=S03.NIP
--
INNER JOIN M10KLAS M10
ON M10.Kode=M15.KdKlas
--
INNER JOIN M41JPJK M41 
ON M10.JnsPajak = M41.Kode
--
INNER JOIN S01HGAJ S01
ON S01.TglPayr=S03.TglPayr AND S01.NIP=S03.NIP
--
INNER JOIN 
(
SELECT * FROM fn_SECLOGIN(l_Usr_Id)
) VSL 
ON S01.Kdglng=VSL.GOL_KODE AND S01.Kdcaba=VSL.CAB_KODE
--
WHERE (S03.NIP BETWEEN l_NIPFr AND l_NIPTo) AND 
--       M10.Harian=0 AND 
 	   M41.JnsForm1721 = 'A1' AND 	
       S03.tglpayr =(select max(s.tglpayr) 
		     from S03LTAX s 
		     where s.nip=m15.nip AND EXTRACT(YEAR FROM s.tglpayr)=EXTRACT(YEAR FROM l_Periode) AND 
			   EXTRACT(MONTH FROM s.tglpayr)=EXTRACT(MONTH FROM l_Periode));

END;
$$ LANGUAGE plpgsql ;
--//

/*
select * from R_PPHNP2('2013-01-31'::date,' ','Z','ABCD','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1);
*/

