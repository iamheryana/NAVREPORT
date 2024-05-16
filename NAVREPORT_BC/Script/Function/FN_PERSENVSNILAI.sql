/****************************************
Name sprocs	: fn_PersenVsNilai
Create by	: 
Date		: 
Description	: 
Call From	: fn_NilaiPend_Pot
*****************************************/
CREATE OR REPLACE FUNCTION fn_PersenVsNilai(l_TglProses  DATE,
					 l_NIP        VARCHAR(10), 
				         l_FlgDpPt   VARCHAR(1),
	                                 l_KdDpPt    VARCHAR(4),
	                                 l_Persen    DECIMAL(5,2),
	                                 l_Nilai     DECIMAL(15,2),
	                                 l_mnHari    DECIMAL(4,1),
	                                 l_GajiRp    DECIMAL(15,2),
	                                 l_Reverse   VARCHAR(1),
	                                 l_MyPass    VARCHAR(128),
	                                 l_KdCurr    VARCHAR(4))
RETURNS DECIMAL(15,2)

AS $$ 
DECLARE   l_GajiVal      DECIMAL(15,2);          
	  l_M03Persen    DECIMAL(5,2);
	  l_M03NiLai     DECIMAL(15,2);
	  l_M03KdCurr    VARCHAR(4);
          l_mnKurs       DECIMAL(10,2);
          l_S02Nilai     DECIMAL(15,2);
          l_NilPend_Pot  DECIMAL(15,2);

BEGIN
  SELECT Persen,      NiLai,      KdCurr	
  INTO   l_M03Persen, l_M03NiLai, l_M03KdCurr	
  FROM  M03DPPT
  WHERE FlgDpPt=l_FlgDpPt AND KdDpPt=l_KdDpPt ;

  -- Gunakan Currency dari M.Pend/Potongan 
  l_GajiVal := ROUND(l_GajiRp/fn_Getkurs(l_KdCurr,l_TglProses),2);
  l_mnKurs  := fn_Getkurs(l_KdCurr,l_TglProses) ;

  -- Check Persen Dan Nominal u/ mendapatkan Nilai C
  IF l_Persen=0 AND l_Nilai=0 THEN 
     BEGIN
       IF l_M03Persen<>0 THEN 
          BEGIN
            l_S02Nilai := (l_GajiVal*l_M03Persen)/100 ;
          END ;
       ELSE
          BEGIN
            l_S02Nilai := l_M03Nilai ;
          END ;
       END IF; 
     END ;
  ELSIF l_Persen<>0 AND l_Nilai=0 THEN 
     BEGIN
       l_S02Nilai := (l_GajiVal*l_Persen)/100 ;
     END ;
  ELSIF l_Persen=0 AND l_Nilai<>0 THEN 
     BEGIN
       l_S02Nilai := l_Nilai ;
     END ;
  END IF; 
  -- Nilai D berikut pengcekan terhadap Reverse 
  l_NilPend_Pot := COALESCE(CASE WHEN l_Reverse='Y' THEN (l_S02Nilai*l_mnHari)*(-1) ELSE (l_S02Nilai*l_mnHari) END,0)*l_mnKurs ;

  RETURN l_NilPend_Pot ; 
END ; 
$$ LANGUAGE plpgsql ;

/*
*/

