/********************************************
Name sprocs	: V_HGA2
Create by	: Peggy 
Date		: 12-05-2008
Description	: Script S01HGAJ + T25OPBL 
Call From	: P_INS_PAJAK 
Sub sprocs	: -
*****************************************/

CREATE VIEW V_HGA2 
AS

SELECT TglPayr, NIP, Nama, 
		KdKlas, KdArea, KdCaba, KdUUsa, KdUKer, KdGlng, KdKJab, KdJaba, 
		StsPjk, CommDate, TermDate, JnsKlmn, StsSip, TotAnak, KPA, 
		AccHolder, RkBnk, BankRef, NoAcc, NoSpt, TakeHomePay, TotInc, TotDed, 
		GrossIncomeNBYTD, OccSupport1, Col12YTD, EGIYNB, PTKP, EYTI, EYIT, YTDIT, 
		IncTaxAPaidNB, GrossIncomeBYTD, OccSupport2, TaxTotal, IncTaxAPaidB, FlagTHR, 
		Hari, AkmHari, FlagM, FlagH, JnsPajak, TaxPesangonRp, Recs, Created_By, Updated_By, Updated_On, 
		EncTakeHomePay, EncTotInc, EncTotDed, EncGrossIncomeNBYTD, EncOccSupport1, 
		EncCol12YTD, EncEGIYNB, EncPTKP, EncEYTI, EncEYIT, EncYTDIT, EncIncTaxAPaidNB, 
		EncGrossIncomeBYTD, EncOccSupport2, EncTaxTotal, EncIncTaxAPaidB, EncTaxPesangonRp 
FROM S01HGAJ 

UNION ALL 

SELECT  T25.PrevDate as TglPayr, 
		T25.NIP, 
		COALESCE(S01.Nama, M15.Nama) AS Nama,
		COALESCE(S01.KdKlas, M15.KdKlas) AS KdKlas, 
		COALESCE(S01.KdArea, M15.KdArea) AS KdArea, 
		COALESCE(S01.KdCaba, M15.KdCaba) AS KdCaba, 
		COALESCE(S01.KdUUsa, M15.KdUUsa) AS KdUUsa, 
		COALESCE(S01.KdUKer, M15.KdUKer) AS KdUKer, 
		COALESCE(S01.KdGlng, M15.KdGlng) AS KdGlng, 
		COALESCE(S01.KdKJab, M15.KdKJab) AS KdKJab, 
		COALESCE(S01.KdJaba, M15.KdJaba) AS KdJaba, 
		COALESCE(S01.StsPjk, M15.StsPjk) AS StsPjk, 
		COALESCE(S01.CommDate, M15.TglMasuk) AS CommDate, 
		COALESCE(S01.TermDate, M15.TglKeluar) AS TermDate, 
		COALESCE(S01.JnsKlmn, M15.JnsKlmn) AS JnsKlmn, 
		COALESCE(S01.StsSip, M15.Status) AS StsSip, 
		COALESCE(S01.TotAnak, M15.AnakTtgg) AS TotAnak, 
		COALESCE(S01.KPA, M15.KPA) AS KPA, 
		COALESCE(S01.AccHolder, M15.AccHolder) AS AccHolder, 
		COALESCE(S01.RkBnk, M15.RkBnk) AS RkBnk, 
		COALESCE(S01.BankRef, M15.BankRef) AS BankRef, 
		COALESCE(S01.NoAcc, ' ') AS NoAcc, 
		COALESCE(S01.NoSpt, ' ') AS NoSpt, 
		0 AS TakeHomePay, 0 AS TotInc, 0 AS TotDed, 
		0 AS GrossIncomeNBYTD, 0 AS OccSupport1, 0 AS Col12YTD, 
		0 AS EGIYNB,
		0 AS PTKP,
		0 AS EYTI, 0 AS EYIT, 0 AS YTDIT, 0 AS IncTaxAPaidNB, 
		0 AS GrossIncomeBYTD, 0 AS OccSupport2, 0 AS TaxTotal, 0 AS IncTaxAPaidB, 
		' ' AS FlagTHR, 0 AS Hari, 0 AS AkmHari, '' AS FlagM, '' AS FlagH,
		COALESCE(S01.JnsPajak, M10.JnsPajak) AS JnsPajak, 
		0 AS TaxPesangonRp, 0 AS Recs, 
		T25.Created_By AS Created_By, 
		T25.Updated_By AS Updated_By, 
		T25.Updated_On AS Updated_On, 
		' ' AS EncTakeHomePay, 
		' ' AS EncTotInc, 
		' ' AS EncTotDed, 
		T25.EncS01GrossIncomeNBYTD AS EncGrossIncomeNBYTD, 
		T25.EncS01OccSupport1 AS EncOccSupport1, 
		T25.EncS01Col12YTD AS EncCol12YTD, 
		T25.EncEGIYNB AS EncEGIYNB, 
		T25.EncPTKP AS EncPTKP, 
		T25.EncEYTI AS EncEYTI, 
		T25.EncEYIT AS EncEYIT, 
		T25.EncYTDIT AS EncYTDIT, 
		T25.EncS01OLDIncTaxAPaidNB AS EncIncTaxAPaidNB, 
		T25.EncS01GrossIncomeBYTD AS EncGrossIncomeBYTD, 
		' ' AS EncOccSupport2, 
		T25.EncS01TaxTotal AS EncTaxTotal, 
		T25.EncS01OLDIncTaxAPaidB AS EncIncTaxAPaidB, 
		' ' AS EncTaxPesangonRp 
FROM T25OPBP T25 
 JOIN M15PEGA M15
ON T25.NIP = M15.NIP 
LEFT JOIN 
(SELECT S01.NIP, LEAST(TGLPAYR) AS TGLPAYR   FROM S01HGAJ S01  GROUP BY S01.NIP,TGLPAYR) X01 
ON X01.NIP = T25.NIP 
LEFT JOIN S01HGAJ S01 
ON X01.NIP = S01.NIP AND X01.Tglpayr = S01.TglPayr 
LEFT JOIN M10KLAS M10 
ON M10.KODE = COALESCE(S01.KdKlas, M15.KdKlas)
WHERE EXTRACT (YEAR FROM(COALESCE(X01.TGLPAYR,CURRENT_DATE))) = EXTRACT (YEAR FROM M15.TGLMASUK) and t25.flgimpl = 'T'

UNION ALL 
 
SELECT  T25.PrevDate AS TglPayr, 
		T25.NIP, 
		COALESCE(S01.Nama, M15.Nama) AS Nama,
		COALESCE(S01.KdKlas, M15.KdKlas) AS KdKlas, 
		COALESCE(S01.KdArea, M15.KdArea) AS KdArea, 
		COALESCE(S01.KdCaba, M15.KdCaba) AS KdCaba, 
		COALESCE(S01.KdUUsa, M15.KdUUsa) AS KdUUsa, 
		COALESCE(S01.KdUKer, M15.KdUKer) AS KdUKer, 
		COALESCE(S01.KdGlng, M15.KdGlng) AS KdGlng, 
		COALESCE(S01.KdKJab, M15.KdKJab) AS KdKJab, 
		COALESCE(S01.KdJaba, M15.KdJaba) AS KdJaba, 
		COALESCE(S01.StsPjk, M15.StsPjk) AS StsPjk, 
		COALESCE(S01.CommDate, M15.TglMasuk) AS CommDate, 
		COALESCE(S01.TermDate, M15.TglKeluar) AS TermDate, 
		COALESCE(S01.JnsKlmn, M15.JnsKlmn) AS JnsKlmn, 
		COALESCE(S01.StsSip, M15.Status) AS StsSip, 
		COALESCE(S01.TotAnak, M15.AnakTtgg) AS TotAnak, 
		COALESCE(S01.KPA, M15.KPA) AS KPA, 
		COALESCE(S01.AccHolder, M15.AccHolder) AS AccHolder, 
		COALESCE(S01.RkBnk, M15.RkBnk) AS RkBnk, 
		COALESCE(S01.BankRef, M15.BankRef) AS BankRef, 
		COALESCE(S01.NoAcc, ' ') AS NoAcc, 
		COALESCE(S01.NoSpt, ' ') AS NoSpt, 
		0 AS TakeHomePay, 0 AS TotInc, 0 AS TotDed, 
		0 AS GrossIncomeNBYTD, 0 AS OccSupport1, 0 AS Col12YTD, 
		0 AS EGIYNB, 0 AS PTKP, 0 AS EYTI, 0 AS EYIT, 0 AS YTDIT, 0 AS IncTaxAPaidNB, 
		0 AS GrossIncomeBYTD, 0 AS OccSupport2, 0 AS TaxTotal, 0 AS IncTaxAPaidB, 
		' ' AS FlagTHR, 0 AS Hari, 0 AS AkmHari, '' AS FlagM, '' AS FlagH,
		COALESCE(S01.JnsPajak, M10.JnsPajak) AS JnsPajak, 
		0 AS TaxPesangonRp, 0 AS Recs, 
		T25.Created_By AS Created_By, T25.Updated_By AS Updated_By, T25.Updated_On AS Updated_On, 
		' ' AS EncTakeHomePay, ' ' AS EncTotInc, ' ' AS EncTotDed, 
		T25.EncS01GrossIncomeNBYTD AS EncGrossIncomeNBYTD, 
		T25.EncS01OccSupport1 AS EncOccSupport1, 
		T25.EncS01Col12YTD AS EncCol12YTD, 
		T25.EncEGIYNB AS EncEGIYNB, 
		T25.EncPTKP AS EncPTKP, 
		T25.EncEYTI AS EncEYTI, 
		T25.EncEYIT AS EncEYIT, 
		T25.EncYTDIT AS EncYTDIT, 
		T25.EncS01OLDIncTaxAPaidNB AS EncIncTaxAPaidNB, 
		T25.EncS01GrossIncomeBYTD AS EncGrossIncomeBYTD, 
		' ' AS EncOccSupport2, 
		T25.EncS01TaxTotal AS EncTaxTotal, 
		T25.EncS01OLDIncTaxAPaidB AS EncIncTaxAPaidB, 
		' ' AS EncTaxPesangonRp 
FROM T25OPBP T25 JOIN M15PEGA M15 ON T25.NIP = M15.NIP 
LEFT JOIN ( SELECT S01.NIP, MAX(TGLPAYR) AS TGLPAYR FROM S01HGAJ S01 GROUP BY S01.NIP ) X01 
ON X01.NIP = T25.NIP LEFT JOIN S01HGAJ S01 
ON X01.NIP = S01.NIP AND X01.Tglpayr = S01.TglPayr 
LEFT JOIN M10KLAS M10 ON M10.KODE = COALESCE(S01.KdKlas, M15.KdKlas)
WHERE EXTRACT (YEAR FROM COALESCE(X01.TGLPAYR,CURRENT_DATE)) = (SELECT GREATEST (EXTRACT (YEAR FROM IMPLPD)) FROM FZ1FLDA) and t25.flgimpl = 'Y'


/*
SELECT * FROM V_HGA2
*/