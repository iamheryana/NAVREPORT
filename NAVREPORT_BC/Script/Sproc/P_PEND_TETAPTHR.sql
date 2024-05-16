/****************************************
Name sprocs	: P_Pend_TetapTHR
Create by	: Wati
Date		: 02-07-2003
Description	: Proses Hitung Pendapatan/Potongan Tetap THR
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE FUNCTION P_Pend_TetapTHR(l_TglProses  DATE,
					    l_NIP        VARCHAR(10),
					    l_GajiRp     DECIMAL(15,2),
					    l_mnHari     DECIMAL(4,1), 
					    l_MyPass     VARCHAR(128),
					    INOUT l_TotalFix   DECIMAL(15,2)) 
AS $$ 
BEGIN 
	SELECT l_TotalFix+((COALESCE(SUM(fn_NilaiPend_Pot(l_TglProses,T04.NIP,T04.FlgDpPt,T04.KdDpPt,T04.Persen,T04.Nilai,
			 CASE WHEN M03.JnDpPt='B' THEN 1
			      ELSE l_mnHari END,l_GajiRp,CASE WHEN T04.Flag='1' THEN 'Y' ELSE 'T' END ,l_MyPass,CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END)),0)) * ( FN_GETPROP(T04.NIP,l_MNHARI,l_TGLPROSES)/l_mnHari) )
	INTO l_TotalFix
	FROM T04TTAP T04
	INNER JOIN M03DPPT M03
	ON M03.FlgDpPt=T04.FlgDpPt AND M03.KdDpPt= T04.KdDpPt
	LEFT JOIN M46PPKH M46
	ON M46.FlgDpPt=T04.FlgDpPt AND M46.KdDpPt= T04.KdDpPt
	-- BY PEGGY 2006 12 18 
	--WHERE T04.NIP=l_NIP AND M46.FlgDpPt IS NULL AND T04.FlgDpPt='D' AND M03.Kolom='1'
	WHERE T04.NIP=l_NIP AND M46.FlgDpPt IS NULL AND T04.FlgDpPt='D' AND M03.Status='1'
	GROUP BY T04.NIP ; 
END ; 
--
$$ LANGUAGE plpgsql ;

/*
select P_Pend_TetapTHR    ( '2003-01-27',
                         '03',
                         5000000,
                         20, 
                         'Copyright, 1988 (c) Microsoft Corporation, All rights reserved');
                         
*/