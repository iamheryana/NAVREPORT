
CREATE OR REPLACE FUNCTION public.fn_nilaipend_pot (in l_tglproses date, in l_nip varchar, in l_flgdppt varchar, in l_kddppt varchar, in l_persen numeric, in l_nilai numeric, in l_mnhari numeric, in l_gajirp numeric, in l_reverse varchar, in l_mypass varchar, in l_kdcurr varchar) RETURNS numeric AS
$BODY$

  DECLARE l_Flag VARCHAR(1);
          l_M03Singkatan VARCHAR(10);
          l_M03UsComp    VARCHAR(1);
	  l_M03Kolom     VARCHAR(2);
	  l_M03NoAcc     VARCHAR(10);
	  l_M03Status    VARCHAR(1);
	  l_M03Persen    DECIMAL(5,2);
	  l_M03NiLai     DECIMAL(15,2);
	  l_M03NoLyr     Int;
	  l_M03KdCurr    VARCHAR(4);
          l_S02Nilai     DECIMAL(15,2);
          l_mnKurs       DECIMAL(10,2);
          l_NilPend_Pot  DECIMAL(15,2);
          l_NilPend_PotVal DECIMAL(15,2);
          l_GajiVal      DECIMAL(15,2);

BEGIN
---- Initialisasi data
  l_S02Nilai       := 0;
  l_mnKurs         := 0;
  l_NilPend_Pot    := 0;
  l_NilPend_PotVal := 0;
  l_GajiVal        := 0;

  
  -- U/mendapatkan Nilai A dan B, dimana Nilai A=Persen dari M03DPPT
  --                                     Nilai B=Nilai dari M03DPPT 
  SELECT Persen,      NiLai,      KdCurr	
  INTO   l_M03Persen, l_M03NiLai, l_M03KdCurr
  FROM  M03DPPT
  WHERE FlgDpPt=l_FlgDpPt AND KdDpPt=l_KdDpPt ; 

  -- Gunakan Currency dari M.Pend/Potongan 
  l_GajiVal := ROUND(l_GajiRp/fn_Getkurs(l_KdCurr,l_TglProses),2) ;
  l_mnKurs  := fn_Getkurs(l_KdCurr,l_TglProses) ;


  -- Check Persen Dan Nominal u/ mendapatkan Nilai C
  IF l_Persen=0 AND l_Nilai=0 THEN 
     BEGIN
       IF l_M03Persen<>0 THEN 
          BEGIN
            l_S02Nilai := (l_GajiVal*l_M03Persen)/100;
          END;  
       ELSE
          BEGIN
            l_S02Nilai := l_M03Nilai;
          END;
       END IF; 
     END;
  ELSIF l_Persen<>0 AND l_Nilai=0 THEN 
     BEGIN
       l_S02Nilai := (l_GajiVal*l_Persen)/100 ;
     END ;
  ELSIF l_Persen=0 AND l_Nilai<>0 THEN 
     BEGIN
       l_S02Nilai=l_Nilai;
     END ;
  END IF; 
  -- Nilai D berikut pengcekan terhadap Reverse 
  l_NilPend_Pot := COALESCE(CASE WHEN l_Reverse='Y' THEN (l_S02Nilai*l_mnHari)*(-1) ELSE (l_S02Nilai*l_mnHari) END,0)*l_mnKurs;

  RETURN l_NilPend_Pot;
END;
$BODY$
LANGUAGE 'plpgsql'
GO
