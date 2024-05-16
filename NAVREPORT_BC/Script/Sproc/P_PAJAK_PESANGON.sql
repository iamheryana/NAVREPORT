/****************************************
Name sprocs	: P_Pajak_Pesangon
Create by	: wati
Date		: 18-07-2003
Description	: Proses Hitung Pajak Pesangon
Call From	: Main Proc
Sub sprocs	: -
*****************************************/
DROP FUNCTION P_Pajak_Pesangon(l_TglProses DATE,
					    l_NIP       VARCHAR(10), 
					    l_Income    DECIMAL(15,2),
					    l_MyPass    VARCHAR(128),
					    l_UserID    VARCHAR(12),
					    l_FlagTHR   VARCHAR(1),	
					    l_JnsPajak	VARCHAR(1),
					    l_S01_ID    INT);      
--
CREATE FUNCTION P_Pajak_Pesangon(l_TglProses DATE,
					    l_NIP       VARCHAR(10), 
					    l_Income    DECIMAL(15,2),
					    l_MyPass    VARCHAR(128),
					    l_UserID    VARCHAR(12),
					    l_FlagTHR   VARCHAR(1),	
					    l_JnsPajak	VARCHAR(1),
					    l_S01_ID    INT)      
RETURNS VOID 
AS $$

DECLARE l_TaxCurrentMonth           	DECIMAL(15,2);
        l_TaxableIncomeCurrentMonth 	DECIMAL(15,2);
        l_M41PersenPKP              	DECIMAL(5,2);
        l_M41PersenPJ1              	DECIMAL(5,2);
	l_M41Final			INT;
	l_M41LimitPj1	 	       	DECIMAL(15,2);
	l_M41LimitPj2		       	DECIMAL(15,2);
	l_M41LimitPj3		       	DECIMAL(15,2);
	l_M41LimitPj4		       	DECIMAL(15,2);
	l_M41LimitPj5		       	DECIMAL(15,2);
	l_M41LimitPj6		       	DECIMAL(15,2);
	l_M41LimitPj7		       	DECIMAL(15,2);
        l_M41PersenPJ2              	DECIMAL(5,2);
        l_M41PersenPJ3              	DECIMAL(5,2);
        l_M41PersenPJ4              	DECIMAL(5,2);
        l_M41PersenPJ5              	DECIMAL(5,2);
        l_M41PersenPJ6              	DECIMAL(5,2);
        l_M41PersenPJ7              	DECIMAL(5,2);
	l_M41PTKP			INT;
	l_M41BiayaJabatan	       	INT;
	l_Nilai			   	DECIMAL(15,2);
	l_Nilai1			DECIMAL(15,2);
	l_Nilai2			DECIMAL(15,2);
	l_Nilai3			DECIMAL(15,2);
	l_M03Singkatan		   	VARCHAR(10);
	l_M03KdCurr		   	VARCHAR(4);		
	l_FXTAX				DECIMAL(6,2);
	l_M15NPWP			VARCHAR(20);
	l_NUMNPWP			DECIMAL(20,0);

BEGIN 
   l_TaxCurrentMonth := 0;
   l_TaxableIncomeCurrentMonth := 0; 
   l_M41PersenPKP := 0;
   l_M41PersenPJ1 := 0;

-- Persen Pajak 
/*
SELECT l_M41PersenPKP=PersenPKP,
       l_M41PersenPJ1=PersenPj1
FROM M41JPJK 
WHERE Kode='X'

-- Main Data


SELECT l_TaxCurrentMonth           =fn_TaxCalculationB('X',l_Income,l_NIP,l_M41PersenPKP,l_M41PersenPj1),
       l_TaxableIncomeCurrentMonth =FLOOR(((l_Income*l_M41PersenPKP)/100)/1000)*1000
*/
   --
   SELECT NPWP 
   INTO   l_M15NPWP 
   FROM M15PEGA 
   WHERE  NIP=l_NIP; 
   --
   SELECT Final,
	  PTKP,
	  PersenPKP,
	  LimitPj1,
	  LimitPj2,
	  LimitPj3,
	  LimitPj4,
	  LimitPj5,
	  PersenPj1,
	  PersenPj2,
	  PersenPj3,
	  PersenPj4,
	  PersenPj5,
  	  BiayaJabatan
   INTO   l_M41Final,
	  l_M41PTKP,
	  l_M41PersenPKP,
	  l_M41LimitPj1,
	  l_M41LimitPj2,
	  l_M41LimitPj3,
	  l_M41LimitPj4,
	  l_M41LimitPj5,
	  l_M41PersenPj1,
	  l_M41PersenPj2,
	  l_M41PersenPj3,
	  l_M41PersenPj4,
	  l_M41PersenPj5,
  	  l_M41BiayaJabatan
   FROM M41JPJK
   WHERE Kode = 'X'; 
   --
   l_FXTAX := (SELECT FaktorXPajak FROM FZ2FLDA LIMIT 1) ; 
   -- 
   l_TaxableIncomeCurrentMonth := FLOOR(((l_Income*l_M41PersenPKP)/100)/1000)*1000;
   --
   l_Nilai := l_TaxableIncomeCurrentMonth - l_M41LimitPj1;
   --
   IF l_Nilai <= 0 THEN 
      BEGIN
         l_TaxCurrentMonth := (l_TaxableIncomeCurrentMonth*l_M41PersenPj1)/100; 
      END; 
   ELSE
     BEGIN
         l_TaxCurrentMonth := (l_M41PersenPj1 * l_M41LimitPj1) / 100;
         --
         l_Nilai1 := l_Nilai - (l_M41LimitPj2 - l_M41LimitPj1);
         --
         IF l_Nilai1 <= 0 THEN 
            l_TaxCurrentMonth := l_TaxCurrentMonth + ((l_M41PersenPj2 * l_Nilai) / 100);
         ELSE
            BEGIN
               l_TaxCurrentMonth := l_TaxCurrentMonth + ((l_M41PersenPj2 * (l_M41LimitPj2 - l_M41LimitPj1)) / 100); 
               ---
	       l_Nilai2 := l_Nilai1 - (l_M41LimitPj3 - l_M41LimitPj2);
               -- 
               IF l_Nilai2 <= 0 THEN 
	          l_TaxCurrentMonth := l_TaxCurrentMonth + ((l_M41PersenPj3 * l_Nilai1) / 100) ; 
	       ELSE
	          BEGIN
	             l_TaxCurrentMonth := l_TaxCurrentMonth + ((l_M41PersenPj3 * (l_M41LimitPj3 - l_M41LimitPj2)) / 100);
                     --
	             l_Nilai3 := l_Nilai2 - (l_M41LimitPj4 - l_M41LimitPj3);
                     --
                     IF l_Nilai3 <= 0 THEN 
                        l_TaxCurrentMonth := l_TaxCurrentMonth + ((l_M41PersenPj4 *l_Nilai2) / 100); 
                     ELSE
                        BEGIN
                           l_TaxCurrentMonth := l_TaxCurrentMonth + ((l_M41PersenPj4 * (l_M41LimitPj4 - l_M41LimitPj3)) / 100);
                           --  
                           l_TaxCurrentMonth := l_TaxCurrentMonth + ((l_M41PersenPj5 * l_Nilai3) / 100); 
	                END; 
	             END IF;    
	          END;
	       END IF;    
	    END; 
	 END IF;    
     END ; 
   END IF;    
  -- PERATURAN PAJAK 2009 : GAK PUNYA NPWP MAKA DITAMBAH 20% PAJAKNYA 
   l_NUMNPWP := CASE WHEN TRIM(BOTH FROM l_M15NPWP) ~ '^[0-9]+$' = T THEN 
 			TRIM(BOTH FROM l_M15NPWP) ::DECIMAL(20,0) 
		ELSE 
			0 
		END ;			
   IF l_NUMNPWP = 0 THEN 
      l_TaxCurrentMonth := l_TaxCurrentMonth * l_FXTAX / 100 ; 
   END IF;  

-- Add Record S0BLSTX
   DELETE FROM S0BLSTX
   WHERE NIP=l_NIP AND TglPayr=l_TglProses; 
   --
-- update 30-11-06 lupa kasih fn_kCabang(l_NIP,'0',l_MyPass)
   INSERT INTO S0BLSTX(TglPayr, NIP, JnsPajak, EncGrossIncPYTD, EncGrossIncCurrent, EncVariPYTD, EncVariCurrent, EncOccSupport,
                       EncPTKP, EncIncomeTaxCurrent, EncIncomeTaxAPaid, EncTaxableIncCurrent, EncTaxableIncPYTD, EncBonusCurrent,
                       EncBonusPYTD, EncPjBnsCurrent, EncPjBnsPYTD, EncIncomeTaxTtl, FlagTHR,
                       VERSION, CREATED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON)
                VALUES(l_Tglproses, l_NIP, 'X', fn_kCabang(l_NIP,'0',l_MyPass), fn_kCabang(l_NIP,(l_Income :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,'0',l_MyPass), fn_kCabang(l_NIP,(l_TaxableIncomeCurrentMonth :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,'0',l_MyPass),
                       fn_kCabang(l_NIP,'0',l_MyPass), fn_kCabang(l_NIP,(l_TaxCurrentMonth :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,'0',l_MyPass), fn_kCabang(l_NIP,(l_TaxableIncomeCurrentMonth :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,'0',l_MyPass), fn_kCabang(l_NIP,'0',l_MyPass),
                       fn_kCabang(l_NIP,'0',l_MyPass), fn_kCabang(l_NIP,(l_TaxCurrentMonth :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,'0',l_MyPass), fn_kCabang(l_NIP,(l_Income :: VARCHAR(17)),l_MyPass), l_FlagTHR,
                       0      , l_userid,   current_time, null, null );

-- Update Nilai Potongan Pajak dengan Nilai Pesangon
   SELECT SkDpPt,         KdCurr  
   INTO   l_M03Singkatan, l_M03KdCurr
   FROM M03DPPT
   WHERE FlgDpPt='P' AND KdDpPt='PJK'; 
--
   IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND NIP=l_NIP AND FlgDpPt='P' AND KdDpPt='PJK') IS NOT NULL THEN 
      BEGIN
         UPDATE S02DGAJ
         SET EncNilai   =fn_KCabang(l_NIP,(fn_kPusat(l_NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)+l_TaxCurrentMonth) ::VARCHAR(17),l_MyPass),
             EncNilaiVal=fn_KCabang(l_NIP,(fn_kPusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_TaxCurrentMonth) ::VARCHAR(17),l_MyPass)
         WHERE NIP=l_NIP AND TglPayr=l_TglProses AND FlgDpPt='P' AND KdDpPt='PJK';
      END;
   ELSE
      BEGIN
     --
         INSERT INTO S02DGAJ(TglPayr   , NIP,  FlgDpPt, KdDpPt,FlgAngs,Singkatan      , Nilai, KdCurr, NilaiVal, Kolom, NoAcc, UsComp, NoLyr, FlgNonGL, EncNilai						  , EncNilaiVal                                               , S01_ID)
                     VALUES (l_TglProses,l_NIP,'P'    , 'PJK' ,' '    ,l_M03Singkatan , 0    , 'IDR' , 0 	 , ' '  , ' '  , 'K'   , 0    , 0     , fn_KCabang(l_NIP,l_TaxCurrentMonth ::VARCHAR(17),l_MyPass), fn_KCabang(l_NIP,l_TaxCurrentMonth ::VARCHAR(17),l_MyPass), l_S01_ID);
      END; 
   END IF;    
--*
--Jika System Pajak gross UP maka dibentuk Potongan Pajak ditanggung Perusahaan dengan Minus
/* dari sini*/
   IF l_JnsPajak='R' THEN 
      BEGIN
        -- Nilainya di Reverse dulu 
        l_TaxCurrentMonth := l_TaxCurrentMonth*-1 ;
        --
        UPDATE S02DGAJ
        SET EncNilai   =fn_KCabang(l_NIP,(fn_kPusat(l_NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)+l_TaxCurrentMonth) ::VARCHAR(17),l_MyPass),
            EncNilaiVal=fn_KCabang(l_NIP,(fn_kPusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_TaxCurrentMonth) ::VARCHAR(17),l_MyPass)
        WHERE NIP=l_NIP AND TglPayr=l_TglProses AND FlgDpPt='P' AND KdDpPt='RPJK';      
      END;
  --
--Jika System Pajak NETTO maka dibentuk Pendapatan Tunjangan Pajak
   ELSIF l_JnsPajak='N' THEN 
      BEGIN
    -- Tunjangan Pajak
        UPDATE S02DGAJ
        SET EncNilai   =fn_KCabang(l_NIP, (fn_kPusat(l_NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)+l_TaxCurrentMonth) ::VARCHAR(17),l_MyPass),
            EncNilaiVal=fn_KCabang(l_NIP, (fn_kPusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_TaxCurrentMonth) ::VARCHAR(17),l_MyPass)
        WHERE NIP=l_NIP AND TglPayr=l_TglProses AND FlgDpPt='D' AND KdDpPt='PJK';       
      END;
   END IF;    
--* 

-- Upd. Header Penggajian(S01HGAJ) 
   UPDATE S01HGAJ
   SET EncTaxPesangonRp = fn_kCabang(l_NIP, (l_TaxCurrentMonth :: VARCHAR(17)),l_MyPass)
   WHERE NIP=l_NIP AND TglPayr=l_TglProses;

END;
$$ LANGUAGE plpgsql ;   
/* sampai sini */
/*
EXEC P_Pajak_Pesangon '2003-03-31',
                      '010', 
                      10000000,
   	              'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',
		      'wet',
		      ' '	
*/
