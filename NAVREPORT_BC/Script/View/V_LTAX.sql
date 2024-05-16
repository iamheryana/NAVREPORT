/********************************************
Name sprocs	: V_LTAX
Create by	: Peggy 
Date		: 12-05-2008
Description	: Script S03LTAX + T25OPBL 
Call From	: P_INS_PAJAK 
Sub sprocs	: -
*****************************************/

CREATE VIEW V_LTAX 

AS

SELECT TglPayr, NIP, KdCaba, 
		FixIncomePYTD, OLDFixIncomePYTD, FixIncome, 
		VarIncomePYTD, OLDVarIncomePYTD, VarIncome, 
		Col2YTD, OLDCol2YTD, Col3YTD, OLDCol3YTD, Col4YTD, OLDCol4YTD, 
		Col5YTD, OLDCol5YTD, Col6YTD, OLDCol6YTD, 
		Col12PYTD, OLDCol12PYTD, Kolom12, 
		OccSupport1, OLDOccSupport1, 
		EGIYNB, PTKP, EYTI, EYIT, YTDIT, 
		IncTaxAPaidNB, OLDIncTaxAPaidNB, 
		Col8PYTD, OLDCol8PYTD, Kolom8, 
		OccSupport2, OLDOccSupport2, 
		EYITT, IncTaxAPaidB, OLDIncTaxAPaidB, TotPot, FlagTHR, 
		Created_By, Updated_By, Updated_On, WS, Harian, FlgStruk, FlgPdLL, 
		kdUMP, NilUMP, TaxUMP, TaxAPaidUMP, TaxUMPYTD, Hari, MaxBruto, PendDP, 
		EncFixIncomePYTD, EncOLDFixIncomePYTD, EncFixIncome, 
		EncVarIncomePYTD, EncOLDVarIncomePYTD, EncVarIncome, 
		EncCol2YTD, EncOLDCol2YTD, EncCol3YTD, EncOLDCol3YTD, 
		EncCol4YTD, EncOLDCol4YTD, EncCol5YTD, EncOLDCol5YTD, 
		EncCol6YTD, EncOLDCol6YTD, 
		EncCol12PYTD, EncOLDCol12PYTD, EncKolom12, 	
		EncOccSupport1, EncOLDOccSupport1, 
		EncEGIYNB, EncPTKP, EncEYTI, EncEYIT, EncYTDIT, 
		EncIncTaxAPaidNB, EncOLDIncTaxAPaidNB, 
		EncCol8PYTD, EncOLDCol8PYTD, EncKolom8, 
		EncOccSupport2, EncOLDOccSupport2, 
		EncEYITT, EncIncTaxAPaidB, EncOLDIncTaxAPaidB, EncTotPot, 
		EncNilUMP, EncTaxUMP, EncTaxAPaidUMP, EncTaxUMPYTD, EncMaxBruto, EncPendDP 
FROM S03LTAX 
UNION ALL 
SELECT  T25.PrevDate AS TglPayr, T25.NIP, 
		COALESCE(S03.KdCaba, M15.KdCaba) AS KdCaba , 
		0 AS FixIncomePYTD,0 AS OLDFixIncomePYTD, 0 AS FixIncome, 
		0 AS VarIncomePYTD, 0 AS OLDVarIncomePYTD, 0 AS VarIncome, 
		0 AS Col2YTD, 0 AS OLDCol2YTD, 0 AS Col3YTD, 0 AS OLDCol3YTD, 
		0 AS Col4YTD, 0 AS OLDCol4YTD, 0 AS Col5YTD, 0 AS OLDCol5YTD, 
		0 AS Col6YTD, 0 AS OLDCol6YTD, 0 AS Col12PYTD, 0 AS OLDCol12PYTD, 0 AS Kolom12, 
		0 AS OccSupport1, 0 AS OLDOccSupport1,0 AS EGIYNB,0 AS PTKP,0 AS EYTI, 0 AS EYIT, 0 AS YTDIT, 
		0 AS IncTaxAPaidNB, 0 AS OLDIncTaxAPaidNB, 
		0 AS Col8PYTD, 0 AS OLDCol8PYTD, 0 AS Kolom8, 
		0 AS OccSupport2, 0 AS OLDOccSupport2,0 AS EYITT, 
		0 AS IncTaxAPaidB, 0 AS OLDIncTaxAPaidB, 
		0 AS TotPot, ' ' AS FlagTHR, 
		T25.Created_By AS Userid, T25.Updated_By AS UpdDate, T25.Updated_On AS UpdTime, ' ' AS WS, 
		' ' AS Harian, ' ' AS FlgStruk, ' ' AS FlgPdLL, 
		' '  AS kdUMP, 0 AS NilUMP, 0 AS TaxUMP, 0 AS TaxAPaidUMP, 0 AS TaxUMPYTD, 0 AS Hari, 
		0 AS MaxBruto , 0 AS PendDP, 
		T25.EncFixIncomePYTD AS EncFixIncomePYTD, 
		T25.EncOLDFixIncomePYTD AS EncOLDFixIncomePYTD, 
		T25.EncFixIncome AS EncFixIncome, 
		T25.EncVarIncomePYTD AS EncVarIncomePYTD, 
		T25.EncOLDVarIncomePYTD AS EncOLDVarIncomePYTD, 
		T25.EncVarIncome AS EncVarIncome, 
		T25.EncCol2YTD AS EncCol2YTD, 
		T25.EncOLDCol2YTD AS EncOLDCol2YTD, 
		T25.EncCol3YTD AS EncCol3YTD, 
		T25.EncOLDCol3YTD AS EncOLDCol3YTD, 
		T25.EncCol4YTD AS EncCol4YTD, 
		T25.EncOLDCol4YTD AS EncOLDCol4YTD, 
		T25.EncCol5YTD AS EncCol5YTD, 
		T25.EncOLDCol5YTD AS EncOLDCol5YTD, 
		T25.EncCol6YTD AS EncCol6YTD, 
		T25.EncOLDCol6YTD AS EncOLDCol6YTD, 
		T25.EncCol12PYTD AS EncCol12PYTD, 
		T25.EncOLDCol12PYTD AS EncOLDCol12PYTD, 
		T25.EncKolom12 AS EncKolom12, 
		T25.EncOccSupport1 AS EncOccSupport1, 
		T25.EncOLDOccSupport1 AS EncOLDOccSupport1, 
		T25.EncEGIYNB AS EncEGIYNB, 
		T25.EncPTKP AS EncPTKP, 
		T25.EncEYTI AS EncEYTI, 
		T25.EncEYIT AS EncEYIT, 
		T25.EncYTDIT AS EncYTDIT, 
		T25.EncIncTaxAPaidNB AS EncIncTaxAPaidNB, 
		T25.EncOLDIncTaxAPaidNB AS EncOLDIncTaxAPaidNB, 
		T25.EncCol8PYTD AS EncCol8PYTD, 
		T25.EncOLDCol8PYTD AS EncOLDCol8PYTD, 
		T25.EncKolom8 AS EncKolom8, 
		T25.EncOccSupport2 AS EncOccSupport2, 
		T25.EncOLDOccSupport2 AS EncOLDOccSupport2, 
		T25.EncEYITT AS EncEYITT, 
		T25.EncIncTaxAPaidB AS EncIncTaxAPaidB, 
		T25.EncOLDIncTaxAPaidB AS EncOLDIncTaxAPaidB, 
		' ' AS EncTotPot, 
		' ' AS EncNilUMP, 
		' ' AS EncTaxUMP, 
		' ' AS EncTaxAPaidUMP, 
		' ' AS EncTaxUMPYTD, 
		' ' AS EncMaxBruto, 
		' ' AS EncPendDP 
FROM T25OPBP T25 
INNER JOIN M15PEGA M15
ON T25.NIP = M15.NIP 
LEFT JOIN 
( SELECT S03.*  FROM S03LTAX S03 LEFT JOIN ( SELECT S03.NIP,  LEAST(TGLPAYR) AS TGLPAYR  FROM S03LTAX S03  GROUP BY S03.NIP,TGLPAYR ) X01 ON X01.NIP = S03.NIP AND X01.TGLPAYR = S03.TGLPAYR) S03 
ON S03.NIP = T25.NIP 
LEFT JOIN ( SELECT S03.NIP,  GREATEST(TGLPAYR) AS TGLPAYR FROM S03LTAX S03  GROUP BY S03.NIP,TGLPAYR ) X01 
ON X01.NIP = S03.NIP;



/*
SELECT * FROM V_LTAX
*/