
CREATE OR REPLACE FUNCTION public.getpiut (in l_nip varchar, in l_tglproses date) RETURNS numeric AS
$BODY$ 
DECLARE l_TotPiut DECIMAL(15,2);
	l_ByrPiut DECIMAL(15,2);
	l_SisPiut DECIMAL(15,2);

BEGIN
   -- 
   l_SisPiut=0 ;
   l_TotPiut=0 ; 
   l_ByrPiut=0 ;
   --
   SELECT SUM(Piutang+Bunga)
   INTO l_TotPiut
   FROM T05Hpiu 
   WHERE Nip=l_Nip and TgDoku <= l_TglProses ; 
   --
   SELECT SUM(BayPokok+BayBunga)
   INTO l_ByrPiut 
   FROM T07BAYP
   WHERE Nip=l_Nip and  TgDoku<= l_TglProses ;
   --   
   l_SisPiut=COALESCE(l_TotPiut,0)-COALESCE(l_ByrPiut,0) ;
   --	   
   RETURN l_SisPiut ;
END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
