
CREATE OR REPLACE FUNCTION public.fn_baypiut (in l_nip varchar, in l_kdjnsp varchar, in l_tgdoku date, in l_jenis varchar, in l_angspkk numeric, in l_angsbga numeric) RETURNS numeric AS
$BODY$ 
DECLARE 	l_Nilai	DECIMAL(15,2);	
		l_SisaP	DECIMAL(15,2);	
		l_SisaB	DECIMAL(15,2);	

BEGIN
   --
   --
   SELECT Piutang-(BayPokPay+BayPokLgs),
	  Bunga -(BayBngPay+BayBngLgs)
   FROM T05HPIU T05
   INTO l_SisaP, l_SisaB 
   WHERE T05.NIP=l_NIP AND T05.KdJnsP=l_KdJnsP AND T05.TgDoku=l_TgDoku ; 
   --
   l_Nilai := 0 ;
   --   
   IF l_Jenis='A' THEN 
      BEGIN
        IF l_SisaP>= l_AngsPkk THEN 
           BEGIN
		l_Nilai := l_AngsPkk ;
           END ;
        ELSE
           BEGIN
		l_Nilai := l_SisaP ;
           END ; 
        --*      
	END IF ; 
      END ; 
   ELSIF l_Jenis='B' THEN
      BEGIN
        IF l_SisaB>= l_AngsBga THEN 
           BEGIN
		l_Nilai := l_AngsBga ; 
           END ;
        ELSE
           BEGIN
		l_Nilai := l_SisaB ; 
           END ; 
        --*         
	END IF ; 
      END ; 
   --*
   END IF ; 
   RETURN l_Nilai ; 
END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
