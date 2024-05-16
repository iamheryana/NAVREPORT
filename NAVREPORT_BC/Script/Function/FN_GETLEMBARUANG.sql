DROP function FN_GETLEMBARUANG (l_Uang INT, l_Lembar INT);

CREATE function FN_GETLEMBARUANG (l_Uang INT, l_Lembar INT)
--
RETURNS INT AS
$$
--
DECLARE l_Jumlah INT;
--
BEGIN
   --
   IF l_Lembar NOT IN (50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000) THEN
       l_Jumlah := 0 ;  
   ELSE
       IF l_Lembar <= 100000 THEN 
          l_Jumlah := l_Uang/100000 ;
          l_Uang   := l_Uang%100000 ; 
       END IF ;
       --
       IF l_Lembar <= 50000 THEN 
          l_Jumlah := l_Uang/50000 ;
          l_Uang   := l_Uang%50000 ; 
       END IF ;
       --
       IF l_Lembar <= 20000 THEN 
          l_Jumlah := l_Uang/20000 ;
          l_Uang   := l_Uang%20000 ; 
       END IF ;
       --
       IF l_Lembar <= 10000 THEN 
          l_Jumlah := l_Uang/10000 ;
          l_Uang   := l_Uang%10000 ; 
       END IF ;
       --
       IF l_Lembar <= 5000 THEN 
          l_Jumlah := l_Uang/5000 ;
          l_Uang   := l_Uang%5000 ; 
       END IF ;
       --
       IF l_Lembar <= 2000 THEN 
          l_Jumlah := l_Uang/2000 ;
          l_Uang   := l_Uang%2000 ; 
       END IF ;
       --
       IF l_Lembar <= 1000 THEN 
          l_Jumlah := l_Uang/1000 ;
          l_Uang   := l_Uang%1000 ; 
       END IF ;
       --
       IF l_Lembar <= 500 THEN 
          l_Jumlah := l_Uang/500 ;
          l_Uang   := l_Uang%500 ; 
       END IF ;
       --
       IF l_Lembar <= 200 THEN 
          l_Jumlah := l_Uang/200 ;
          l_Uang   := l_Uang%200 ; 
       END IF ;
       --
       IF l_Lembar <= 100 THEN 
          l_Jumlah := l_Uang/100 ;
          l_Uang   := l_Uang%100 ; 
       END IF ;
       --
       IF l_Lembar <= 50 THEN 
          l_Jumlah := l_Uang/50 ;
          l_Uang   := l_Uang%50 ; 
       END IF ;
   END IF ;    
   --
   RETURN COALESCE(l_Jumlah,0) ;
   --
END ;
--
$$
language plpgsql;

/*
SELECT  * FROM FN_GETLEMBARUANG (987654320, 100000) ;
SELECT  * FROM FN_GETLEMBARUANG (987654320, 50000) ;
SELECT  * FROM FN_GETLEMBARUANG (987654320, 20000) ;
SELECT  * FROM FN_GETLEMBARUANG (987654320, 10000) ;
SELECT  * FROM FN_GETLEMBARUANG (987654320, 5000) ;
SELECT  * FROM FN_GETLEMBARUANG (987654320, 2000) ;
SELECT  * FROM FN_GETLEMBARUANG (987654320, 1000) ;
SELECT  * FROM FN_GETLEMBARUANG (987654320, 500) ;
SELECT  * FROM FN_GETLEMBARUANG (987654320, 200) ;
SELECT  * FROM FN_GETLEMBARUANG (987654320, 100) ;
SELECT  * FROM FN_GETLEMBARUANG (987654320, 50) ;
*/
