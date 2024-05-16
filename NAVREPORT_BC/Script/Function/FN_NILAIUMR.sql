/****************************************
Name sprocs	: fn_NilaiUMR
Create by	: Wati
Date		: 24-07-2003
Description	: Proses Nilai UMR
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE FUNCTION fn_NilaiUMR(l_Periode   DATE,  
					l_KodeUMR   VARCHAR(4),
					l_NIP       VARCHAR(10))      
                              
			
RETURNS DECIMAL(15,2)

AS $$ 

  DECLARE l_NilUMR  DECIMAL(15,2) ;
          l_TglPayr DATE ; 
BEGIN
  l_NilUMR := 0 ;
  -- 	 
  SELECT TglPayr
  INTO l_TglPayr
  FROM S03LTAX S03 
  --
  WHERE S03.NIP=l_NIP AND S03.TaxUMP<>0 AND S03.TaxAPaidUMP=0 AND extract(month from S03.TglPayr) = extract(month from l_Periode) AND
       (S03.TglPayr =(SELECT MAX(S.TglPayr) FROM S03LTAX S WHERE S.NIP=l_NIP AND extract(year from  S.TglPayr) = extract(year from  l_Periode) ))  ;
  --
  SELECT UMR
  INTO l_NilUMR 
  FROM M21UMRE
  WHERE Daerah=l_KodeUMR AND 
        TglMulai<=l_TglPayr ; 
  -- Mengembalikan Nilai UMR
  RETURN l_NilUMR ; 
END ; 

$$ language 'plpgsql' ;  

/* 
SELECT fn_NilaiUMR (DATE '2010-10-01', 'J', '1') 
SELECT * FROM M21UMRE
*/ 
