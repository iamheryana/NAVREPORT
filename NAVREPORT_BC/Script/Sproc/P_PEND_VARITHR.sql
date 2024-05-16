/******************************************
Name sprocs	: P_Pend_VariTHR
Create by	: Wati
Date		: 19-06-2003
Description	: Proses Hitung Pendapatan/Potongan Variable THR
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE FUNCTION P_Pend_VariTHR(l_TglProses  DATE,
					  l_TglAkhir   DATE, 
					  l_NIP        VARCHAR(10),
					  l_GajiRp     DECIMAL(15,2),
					  l_MyPass     VARCHAR(128),
					  INOUT l_TotalFix   DECIMAL(15,2))
AS $$ 
BEGIN 
--
SELECT COALESCE(l_TotalFix,0)+COALESCE(SUM(fn_NilaiPend_Pot(l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,TBL.Persen,TBL.Nilai,TBL.Hari,l_GajiRp,CASE WHEN TBL.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,TBL.KdCurr)),0)
INTO l_TotalFix
FROM 
(
SELECT T03.NIP,T03.FlgDpPt,T03.KdDpPt,CASE WHEN (T03.Nilai=0 AND T03.Persen=0) THEN M03.KdCurr ELSE T03.KdCurr END AS KdCurr,T03.Persen,T03.Nilai,M03.JnDpPt,T03.PrdMulai,T03.PrdSd,
       fn_Hit_Hari_Vari(M03.JnDpPt,T03.PrdMulai,l_TglAkhir,l_TglProses,T03.PrdSd) AS Hari,
       T03.Flag	
FROM T03VARI T03
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=T03.FlgDpPt AND M03.KdDpPt= T03.KdDpPt
LEFT JOIN M46PPKH M46
ON M46.FlgDpPt=T03.FlgDpPt AND M46.KdDpPt= T03.KdDpPt
WHERE T03.NIP=l_NIP AND M46.FlgDpPt IS NULL AND T03.PrdMulai<=l_TglProses AND
-- BY PEGGY 2006 12 18 
--      T03.FlgDpPt='D' AND M03.Kolom='1' 
      T03.FlgDpPt='D' AND M03.Status='1' 
) TBL 
--
GROUP BY TBL.NIP ; 

END; 
$$ LANGUAGE plpgsql ;
/*
	  SELECT P_Pend_VariTHR ('2003-01-20',
			   '2002-12-20', 
			   'TEST',
			   2000000,
			   'Copyright, 1988 (c) Microsoft Corporation, All rights reserved');
*/
