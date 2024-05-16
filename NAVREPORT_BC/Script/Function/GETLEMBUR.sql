
CREATE OR REPLACE FUNCTION public.getlembur (in l_nip varchar, in l_tglproses date) RETURNS numeric AS
$BODY$

-- create cursor, trus looping 
DECLARE l_MinTglPosting date;
	l_Sum  		DECIMAL(5,2);
	l_FZ2FlgPrsLbr  varchar(1);
	l_MaxTglPosting date ;  
-- 
BEGIN
   SELECT SUBSTRING(StringFlag,4,1) INTO l_FZ2FlgPrsLbr 
   FROM FZ2FLDA ;
   --- Jika pakai flag lembur maka hitung lembur dari range lembur
   IF l_FZ2FlgPrsLbr='L' THEN 
	SELECT M47.TglAwal,
	       M47.TglAkhir
	INTO l_MinTglPosting, l_MaxTglPosting 
	FROM M47PRLR M47
	WHERE TglProses=l_TglProses ;
   ELSE
	l_MaxTglPosting=l_TglProses ;
	--
        SELECT TglPosting 
        INTO l_MinTglPosting
	FROM S05PSTD 
	WHERE Nip=l_Nip and TglPosting < l_TglProses AND FlagTHR = ' ' ;--<>''*'' 
        -- 
        l_MinTglPosting=l_MinTglPosting::date + 1  ;
   END IF; 
   --*
   --- Jika Tanggal Awal sama dengan tanggal 
   IF l_MinTglPosting IS NULL THEN 
      l_MinTglPosting='1980-01-01';
   END IF; 
   --/
   -- 
   IF l_MinTglPosting IS NOT NULL THEN 
        SELECT coalesce(Sum(TtlLemb),0) INTO l_Sum
        FROM S04LEMB
        WHERE NIP=l_NIP AND TglPayr BETWEEN l_MinTglPosting AND l_MaxTglPosting ; 
   ELSE 
       l_Sum=0 ;
   --*
   END IF ; 
   RETURN l_sum ;
END ; 

$BODY$
LANGUAGE 'plpgsql'
GO
