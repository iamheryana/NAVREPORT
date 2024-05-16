/******************************************
Name sprocs	: P_Hitung_Pjk_TypeA
Create by	: Wati
Date		: 19-06-2003
Description	: Proses Hitung Asuransi
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Hitung_Pjk_TypeA(	l_JnsPajak	VARCHAR(1),
						l_TglProses	DATE,	
						l_M15NIP	VARCHAR(10),
						l_MyPass     	VARCHAR(128),
						l_UserID	VARCHAR(12),
						l_FlagTHR	VARCHAR(1),
						l_Curr_Income	DECIMAL(15,2),
						l_Curr_Kolom8	DECIMAL(15,2),
						l_P_Harian      VARCHAR(1),
						l_HKerja        DECIMAL(4,1),
						l_AkhirBln      INT,
						l_S01_ID	INT);
--
CREATE FUNCTION P_Hitung_Pjk_TypeA(	l_JnsPajak	VARCHAR(1),
						l_TglProses	DATE,	
						l_M15NIP	VARCHAR(10),
						l_MyPass     	VARCHAR(128),
						l_UserID	VARCHAR(12),
						l_FlagTHR	VARCHAR(1),
						l_Curr_Income	DECIMAL(15,2),
						l_Curr_Kolom8	DECIMAL(15,2),
						l_P_Harian      VARCHAR(1),
						l_HKerja        DECIMAL(4,1),
						l_AkhirBln      INT,
						l_S01_ID	INT) 
RETURNS VOID 
AS $$
--
BEGIN 
-- Jika Pajak Netto
IF l_JnsPajak='N'  THEN 
   BEGIN
     PERFORM P_Hit_Pajak_Netto(l_TglProses,
	                      l_M15NIP,
                              l_FlagTHR,
                              'A',
                              l_MyPass,
                              l_UserID,
                              l_P_Harian,
                              l_HKerja,
                              l_AkhirBln,
	                      l_Curr_Income,
			      l_S01_ID);
   END;
   -- Jika Pajak Gross 
ELSIF l_JnsPajak='G' THEN 
   BEGIN
     PERFORM P_Ins_Pajak(l_TglProses,
                        l_M15NIP,
                        'A', 	
                        l_FlagTHR,
                        l_Curr_Income,
                        l_Curr_Kolom8,
	              	l_UserID, 	 
	              	l_MyPass,
                        l_P_Harian,
                        l_HKerja,
                        l_AkhirBln,
		      	'G',
		        l_S01_ID);

   END;
-- Jika Pajak Gross Up
ELSIF l_JnsPajak='R' THEN 
   BEGIN
     PERFORM P_Ins_Pajak(l_TglProses,
                        l_M15NIP,
                        'A', 	
                        l_FlagTHR,
                        l_Curr_Income,
                        l_Curr_Kolom8,
	              	l_UserID, 	 
	              	l_MyPass,
                        l_P_Harian,
                        l_HKerja,
                        l_AkhirBln,
		      	'R',
		        l_S01_ID);
   END;
-- Jika Pajak Kombinasi gross dan gross up --by peggy 2007 01 19 
ELSIF l_JnsPajak='U' THEN 
   BEGIN
     PERFORM P_Pjk_KombinasiGrsUp(l_TglProses,
				 l_M15NIP,  
				  'A',
				  l_FlagTHR,
				  l_UserID,
				  l_MyPass,
				  l_P_Harian,
				  l_HKerja,
				  l_AkhirBln,
				  l_S01_ID);
  END ;
-- Jika Pajak Kombinasi 
ELSIF l_JnsPajak='K' THEN 
   BEGIN
     PERFORM P_Pjk_Kombinasi(l_TglProses,
                            l_M15NIP,  
                            'A',
                            l_FlagTHR,
                            l_UserID,
                            l_MyPass,
                            l_P_Harian,
                            l_HKerja,
                            l_AkhirBln,
			    l_S01_ID);

  END;
END IF;
END ;
--*
$$ LANGUAGE plpgsql ;


