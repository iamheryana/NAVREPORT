
CREATE OR REPLACE FUNCTION public.fn_hit_hari_vari (in l_jndppt bpchar, in l_prdmulai date, in l_tglakhir date, in l_tglproses date, in l_prdsd date) RETURNS numeric AS
$BODY$ 

DECLARE l_Hari DECIMAL(5,0) ;

BEGIN

  IF l_JnDpPt='H' THEN 
     BEGIN
       IF l_PrdMulai>l_TglAkhir THEN 
          BEGIN
            IF l_PrdSd>=l_TglProses THEN 
               BEGIN
                 l_Hari := l_TglProses - l_PrdMulai + 1; 
               END ;
            ELSIF l_PrdSd<l_TglProses THEN 
               BEGIN 
                 l_Hari := l_PrdSd - l_PrdMulai + 1 ;
               END ;
	    END IF; 
          END ; 
       ELSIF l_PrdMulai<=l_TglAkhir AND l_PrdSd>l_TglAkhir THEN 
          BEGIN
            IF l_PrdSd>=l_TglProses THEN 
               BEGIN
                 l_Hari := l_TglProses - l_TglAkhir ;
               END ;
            ELSE 
               BEGIN
                 l_Hari := l_PrdSd - l_TglAkhir ;
               END ;
	    END IF; 
          END ;
	END IF ; 
     END ;
  ELSE
     IF l_PrdSd>l_TglAkhir THEN 
        BEGIN
          l_Hari := 1 ;
        END ;
     ELSE
        BEGIN  
          l_Hari := 0 ;
        END ;
     END IF; 
  END IF ; 
  RETURN l_Hari;
END;
$BODY$
LANGUAGE 'plpgsql'
GO
