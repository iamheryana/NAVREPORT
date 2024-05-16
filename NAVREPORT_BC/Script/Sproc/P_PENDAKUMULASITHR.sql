/****************************************
Name sprocs	: P_PendAkumulasiTHR
Create by	: wati
Date		: 02-07-2003
Description	: Proses Pendapatan Akumulasi THR
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_PendAkumulasiTHR(l_TglProses  DATE,
					       l_NIP        VARCHAR(10),
					       l_MyPass     VARCHAR(128),
					       INOUT l_TotalFix   DECIMAL(15,2));
--
CREATE FUNCTION P_PendAkumulasiTHR(l_TglProses  DATE,
					       l_NIP        VARCHAR(10),
					       l_MyPass     VARCHAR(128),
					       INOUT l_TotalFix   DECIMAL(15,2))

AS $$ 
DECLARE l_LOOP_T17   REFCURSOR ;
        l_T17Persen  DECIMAL(5,2);
        l_Persen     DECIMAL(5,2);
        l_M03Persen  DECIMAL(5,2);
        l_FixIncome  DECIMAL(15,2);

BEGIN
l_FixIncome := l_totalfix;

OPEN l_LOOP_T17 FOR EXECUTE('SELECT T17.Persen,M03.Persen
				FROM T17PDAK T17
				INNER JOIN M03DPPT M03
				ON M03.FlgDpPt=T17.FlgDpPt AND M03.KdDpPt=T17.KdDpPt
				LEFT JOIN M46PPKH M46
				ON M46.FlgDpPt=T17.FlgDpPt AND M46.KdDpPt=T17.KdDpPt
				WHERE T17.NIP=l_NIP AND M46.FlgDpPt IS NULL AND T17.FlgDpPt=''D'' AND M03.Status=''1''
				ORDER BY T17.LevelAkm ');
LOOP 
   FETCH l_LOOP_T17 INTO l_T17Persen,l_M03Persen;
   IF NOT FOUND THEN
	EXIT ;
   END IF;
   -- Persentasi Akumulasi        
   l_Persen   := CASE WHEN l_T17Persen<>0 THEN l_T17Persen ELSE l_M03Persen END;
   l_TotalFix := COALESCE(l_TotalFix,0)+COALESCE((l_FixIncome*l_Persen)/100,0);
END LOOP;
CLOSE l_LOOP_T17;
END; 
$$ LANGUAGE plpgsql ;
/*
EXEC P_PendAkumulasi '2003-01-20',
                     'TEST',
                     'T',
                     'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
*/



