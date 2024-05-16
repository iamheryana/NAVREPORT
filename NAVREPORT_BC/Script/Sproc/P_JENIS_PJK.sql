/****************************************
Name sprocs	: P_Jenis_Pjk
Create by	: Wati
Date		: 18-03-2003
Description	: Check Jenis Pajak
Call From	: Sub Routine
Sub sprocs	: -
*****************************************/
DROP FUNCTION P_Jenis_Pjk(l_TglProses  DATE,
					l_NIP	    VARCHAR(10),
					OUT l_JnsPajak   VARCHAR(1) );
--
CREATE FUNCTION P_Jenis_Pjk(l_TglProses  DATE,
					l_NIP	    VARCHAR(10),
					OUT l_JnsPajak   VARCHAR(1) )

AS $$
--

DECLARE l_Gross            INT;          l_GrossUp          INT; 
        l_Netto            INT;          

BEGIN 
-- Jenis Pajak
l_Gross    := 0 ;
l_Netto    := 0 ;
l_GrossUp  := 0 ;
l_JnsPajak := ' ';

l_gross := CASE WHEN (SELECT COUNT(*)
                         FROM
                        (SELECT CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.pajak END AS Pajak
                         FROM S02DGAJ S02
                         INNER JOIN M03DPPT M03
                         ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt
                         INNER JOIN  M15PEGA M15
                         ON M15.NIP=S02.NIP
                         --
                         WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND S02.NIP=l_NIP AND
                        (CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.pajak end)='G') TBL)<>0
                   THEN 1 ELSE 0 END;
--
l_Netto := CASE WHEN (SELECT COUNT(*)
                         FROM
                        (SELECT CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.pajak END AS Pajak
                         FROM S02DGAJ S02
                         INNER JOIN M03DPPT M03
                         ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt
                         INNER JOIN  M15PEGA M15
                         ON M15.NIP=S02.NIP
                         --
                         WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND S02.NIP=l_NIP AND
                        (CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.pajak end)='N') TBL)<>0
                   THEN 1 ELSE 0 END;
--
l_GrossUp := CASE WHEN (SELECT COUNT(*)
                           FROM
                          (SELECT CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.pajak END AS Pajak
                           FROM S02DGAJ S02
                           INNER JOIN M03DPPT M03
                           ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt
                           INNER JOIN  M15PEGA M15
                           ON M15.NIP=S02.NIP
                           --
                           WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND S02.NIP=l_NIP AND
                          (CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.pajak end)='R') TBL)<>0
                     THEN 1 ELSE 0 END;

IF l_Gross=1 AND l_Netto=0 AND l_GrossUp=0 THEN 
   l_JnsPajak := 'G' ; 
ELSIF l_Gross=0 AND l_Netto=1 AND l_GrossUp=0 THEN
   l_JnsPajak := 'N' ; 
ELSIF l_Gross=0 AND l_Netto=0 AND l_GrossUp=1 THEN 
   l_JnsPajak := 'R' ;
ELSIF l_Gross=1 AND l_Netto=0 AND l_GrossUp=1 THEN -- kombinasi gross dan gross up -- by peggy 2007 01 19 
   l_JnsPajak := 'U' ;
ELSE
   l_JnsPajak := 'K';
END IF;
END ; 
$$ LANGUAGE plpgsql ;
/*
SELECT  P_Jenis_Pjk ('2004-03-20',
		 '1');
*/
