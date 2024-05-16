/****************************************
Name sprocs	: fn_Get_LUNAS
Create by	: Peggy
Date		: 12-03-2008
Description	: Function mencari Nilai pelunasan pph 
Call From	: Main sprocs
l_NmBulanFr = blank maka tidak pindah cabang 
*****************************************/
CREATE OR REPLACE FUNCTION fn_Get_LUNAS(l_NIP 		VARCHAR(10),
					l_Tahun 	INT,
					l_TglProses	DATE,
					l_Kolom		INT,
					l_MyPass  	VARCHAR(128), 
					l_NmBulanFr 	VARCHAR(9))
			
RETURNS DECIMAL(15,2)

AS $$ 

DECLARE l_Tgl		DATE;	
	l_NilKolom	DECIMAL(15,2);
	l_M15Pajak	VARCHAR(1);
	l_NilKolom1	DECIMAL(15,2);
	l_NilKolom2   	DECIMAL(15,2);
	l_BulanFr	int ;
		  
BEGIN
--
   l_NilKolom  :=0 ;
   l_NilKolom1 :=0 ;
   l_NilKolom2 :=0 ; 	
   -- 
   IF l_Kolom=24 THEN 
      BEGIN
          --
	   CASE l_NmBulanFR
	                     WHEN ' ' THEN l_BulanFr := 1 ;
	                     WHEN 'Januari' THEN l_BulanFr := 1 ;
	                     WHEN 'Februari' THEN l_BulanFr := 2 ;
	                     WHEN 'Maret' THEN l_BulanFr := 3 ;
	                     WHEN 'April' THEN l_BulanFr := 4 ;
	                     WHEN 'Mei' THEN l_BulanFr := 5 ;
	                     WHEN 'Juni' THEN l_BulanFr := 6 ;
	                     WHEN 'Juli' THEN l_BulanFr := 7 ;
	                     WHEN 'Agustus' THEN l_BulanFr := 8 ;
	                     WHEN 'September' THEN l_BulanFr := 9 ;
	                     WHEN 'Oktober' THEN l_BulanFr := 10 ;
	                     WHEN 'November' THEN l_BulanFr := 11 ;
	                     WHEN 'Desember' THEN l_BulanFr := 12 ;
	                     END CASE ; 

          SELECT SUM(COALESCE((Fn_Kpusat(T26.NIP,T26.EncPelunasan,l_MyPass)::DECIMAL(15,2)),0))
          INTO l_NilKolom1 
          FROM T26PPHL T26 
          WHERE NIP=l_NIP AND EXTRACT(YEAR FROM Periode)=EXTRACT(YEAR FROM l_TglProses) AND EXTRACT(MONTH FROM Periode) BETWEEN l_BulanFr AND EXTRACT(MONTH FROM l_TglProses) ; 
          --
          l_NilKolom := COALESCE(l_NilKolom1,0); 
      END ;
   END IF; 
   --/ 
   -- Mengembalikan Nilai 
   RETURN l_NilKolom ; 
END ; 
$$ language 'plpgsql' ;  
--
/*
SELECT fn_Get_LUNAS(VARCHAR '198712001',2013,DATE '2013-05-25',24,VARCHAR 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved', VARCHAR 'April')
--SELECT dbo.fn_Get_LUNAS('41',2007,'2007-01-24',24,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved')

SELECT periode, NIP, ((Fn_Kpusat(T26.NIP,T26.EncPelunasan,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'))::DECIMAL(15,2))
FROM T26PPHL T26 
WHERE NIP='41' AND YEAR(Periode)=2008
SELECT fn_kcabang(VARCHAR '198712001',CHAR '1000000','Copyright, 1988 (c) Microsoft Corporation, All rights reserved')

"6UofGzYTgdyxQR"

          SELECT SUM(COALESCE((Fn_Kpusat(T26.NIP,T26.EncPelunasan,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved')::DECIMAL(15,2)),0))
          FROM T26PPHL T26 
          WHERE NIP='198712001' AND EXTRACT(YEAR FROM Periode)=EXTRACT(YEAR FROM date '2013-05-25') AND EXTRACT(MONTH FROM Periode) BETWEEN 5 AND EXTRACT(MONTH FROM date '2013-05-25') ; 
*/
--
