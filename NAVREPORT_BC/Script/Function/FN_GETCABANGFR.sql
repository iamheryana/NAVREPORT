/****************************************
Name sprocs	: fn_GetCabangFR
Create by	: Suhe
Date		: 19-08-2003
Description	: Untuk Dapat Bulan From dari SPT 1721 A1 per Cabang
Call From	: P_PRENP1
Sub sprocs	: 
*****************************************/
CREATE OR REPLACE FUNCTION fn_GetCabangFR(l_CabFr	VARCHAR(4),
					l_MonthTo	INT,
					l_Tahun		INT,
					l_NIP		VARCHAR(10))	 
RETURNS VARCHAR(9)

AS $$ 

   --
   DECLARE 
	   l_Loop	INT ;	
           l_Cab01 	VARCHAR(4) ;
	   l_Cab02 	VARCHAR(4) ; 
	   l_Cab03 	VARCHAR(4) ;
	   l_Cab04 	VARCHAR(4) ;
	   l_Cab05 	VARCHAR(4) ;
	   l_Cab06 	VARCHAR(4) ;
	   l_Cab07 	VARCHAR(4) ;
	   l_Cab08 	VARCHAR(4) ;
	   l_Cab09 	VARCHAR(4) ;
	   l_Cab10 	VARCHAR(4) ;
	   l_Cab11 	VARCHAR(4) ;
	   l_Cab12 	VARCHAR(4) ;
	   l_Cabang	VARCHAR(4) ;	   
	   l_BulanFR 	INT ;
	   l_NmBulanFr	VARCHAR(9) ;
	   l_IndBulan	INT ;		

--
BEGIN
    -- Ambil Posisi Pindah Cabang
    SELECT Cab01, Cab02, Cab03, Cab04, Cab05, Cab06,
	   Cab07, Cab08, Cab09, Cab10, Cab11, Cab12
    INTO l_Cab01, l_Cab02, l_Cab03, l_Cab04, l_Cab05, l_Cab06,
	 l_Cab07, l_Cab08, l_Cab09, l_Cab10, l_Cab11, l_Cab12
    FROM S0ESFPC
    WHERE tahun=l_Tahun AND NIP=l_NIP ;
    --
    l_Loop    := 1 ;
    l_BulanFR := 0 ;

    WHILE l_Loop <= l_MonthTo LOOP 
      BEGIN
        --
         CASE l_Loop
             WHEN 1 THEN l_Cabang := l_Cab01 ;
             WHEN 2 THEN l_Cabang := l_Cab02 ;
             WHEN 3 THEN l_Cabang := l_Cab03 ;
             WHEN 4 THEN l_Cabang := l_Cab04 ;
             WHEN 5 THEN l_Cabang := l_Cab05 ;
             WHEN 6 THEN l_Cabang := l_Cab06 ;
             WHEN 7 THEN l_Cabang := l_Cab07 ;  
             WHEN 8 THEN l_Cabang := l_Cab08 ;
             WHEN 9 THEN l_Cabang := l_Cab09 ;
             WHEN 10 THEN l_Cabang := l_Cab10 ;
             WHEN 11 THEN l_Cabang := l_Cab11 ;
             WHEN 12 THEN l_Cabang := l_Cab12 ;
         END CASE ; 
         --
        IF l_Cabang<>l_CabFr THEN 
           BEGIN
             l_BulanFR := 0 ;
           END ; 
        END IF; 
        --* 
        --
        IF l_Cabang=l_CabFr AND l_BulanFR=0 THEN 
           BEGIN
              l_BulanFR := l_Loop ; 
           END ;
        END IF ; 
        --*
        --
        l_Loop := l_Loop + 1 ; 
      END ;
   END LOOP ; 
   --*
   --
   l_NmBulanFr := 'TEST' ;
   --
   CASE l_BulanFR
     WHEN 0 THEN l_IndBulan := 1 ;
     WHEN 1 THEN l_IndBulan := 1 ;
     WHEN 2 THEN l_IndBulan := 2 ;
     WHEN 3 THEN l_IndBulan := 3 ;
     WHEN 4 THEN l_IndBulan := 4 ;
     WHEN 5 THEN l_IndBulan := 5 ;
     WHEN 6 THEN l_IndBulan := 6 ;
     WHEN 7 THEN l_IndBulan := 7 ;
     WHEN 8 THEN l_IndBulan := 8 ;
     WHEN 9 THEN l_IndBulan := 9 ;
     WHEN 10 THEN l_IndBulan := 10 ;
     WHEN 11 THEN l_IndBulan := 11 ;
     WHEN 12 THEN l_IndBulan := 12 ;
     END CASE ; 
  
   RETURN l_IndBulan ;
END ;

$$ language 'plpgsql' ;

/*
SELECT fn_GetCabangFR('C',11,2012,'201206004')

SELECT fn_GetCabangFR('B',6,2013,'123456789')

SELECT * FROM S0ESFPC WHERE NIP ='201206004' 
SELECT * FROM S0ESFPC WHERE NIP ='123456789' 
*/




