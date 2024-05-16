/****************************************
Name sprocs	: P_Netto1
Create by	: Wati
Date		: 26-05-2003
Description	: Untuk iterasi
Call From	: Sub Routine
Sub sprocs	: -
*****************************************/
DROP FUNCTION P_Netto1 (l_TglProses    DATE,
					l_NIP          VARCHAR(10),       
					l_GIYTD        DECIMAL(15,2),
					l_Bonus        DECIMAL(15,2),
					l_Kolom12      DECIMAL(15,2),
					OUT l_PajakYTDNB   DECIMAL(15,2),
					OUT l_PajakYTDB    DECIMAL(15,2), 
					l_MyPass       VARCHAR(128),
					l_P_Harian     VARCHAR(1),
					l_curr_inc     DECIMAL(15,2), 
					l_FlagTHR      VARCHAR(1), 
					l_KomponenOccSupport DECIMAL(15,2), 
					l_KomponenOccSupport2 DECIMAL(15,2));
--
CREATE FUNCTION P_Netto1 (l_TglProses    DATE,
					l_NIP          VARCHAR(10),       
					l_GIYTD        DECIMAL(15,2),
					l_Bonus        DECIMAL(15,2),
					l_Kolom12      DECIMAL(15,2),
					OUT l_PajakYTDNB   DECIMAL(15,2),
					OUT l_PajakYTDB    DECIMAL(15,2), 
					l_MyPass       VARCHAR(128),
					l_P_Harian     VARCHAR(1),
					l_curr_inc     DECIMAL(15,2), 
					l_FlagTHR      VARCHAR(1), 
					l_KomponenOccSupport DECIMAL(15,2), 
					l_KomponenOccSupport2 DECIMAL(15,2))

AS $$
-- Parameter
DECLARE l_TglPayr         DATE;        	l_KdCaba           VARCHAR(4);
	l_NewKdCaba       VARCHAR(4);   l_OldKdCaba        VARCHAR(4);
	l_Tahun           DECIMAL(4);   l_Bulan            VARCHAR(2);
	l_FZ1NlOccSupp    DECIMAL(15,2);
	l_FZ1PrOccSupp    DECIMAL(5,2); l_FZ1PTKPPay	   DECIMAL(15,2);	
	l_FZ1PTKPDep	  DECIMAL(15,2);l_GrsIncYTD	   DECIMAL(15,2);
	l_BlnProses       INT;          l_JmlBulan         INT;
	l_PrdAwal         DATE;         l_FZ2FlgCabang     VARCHAR(1);
	l_FZ1Kode	  VARCHAR(2); 
	l_ADAOPBL 	  DECIMAL(5,0);  
	l_TglMaxCabBeda	  DATE;
	l_TglProsesPertamaThnini DATE;  l_BlnProsesOccSupp INT;
DECLARE l_TglBfr DATE ;
 
BEGIN 
-- Parameter LDA
SELECT NlOccSupp,      PrOccSupp,      PTKPPay,      PTKPDep,      Kode
INTO   l_FZ1NlOccSupp, l_FZ1PrOccSupp, l_FZ1PTKPPay, l_FZ1PTKPDep, l_FZ1Kode
FROM FZ1FLDA ;

-- Flag Cabang
SELECT CASE SUBSTR(StringFlag,2,1) WHEN '1' THEN 'Y' ELSE 'T' END
INTO l_FZ2FlgCabang
FROM FZ2FLDA
WHERE Kode=l_FZ1Kode ;

-- Periode awal diambil dari tanggal masuk
l_BlnProses := 0;
l_JmlBulan  := 0;
--
SELECT TglMasuk
INTO   l_PrdAwal
FROM M15PEGA 
WHERE NIP=l_NIP ;

--
SELECT TglPosting
INTO   l_Tglbfr
FROM S05PSTD
WHERE NIP=l_NIP AND FlagTHR=''
ORDER BY NIP,TglPosting  ;

-- -- -- Periode Penggajian
-- -- IF EXTRACT(YEAR FROM l_PrdAwal)=EXTRACT(YEAR FROM l_TglProses)
-- --    BEGIN
-- --      SELECT l_BlnProses=(EXTRACT(MONTH FROM l_TglProses)-EXTRACT(MONTH FROM l_PrdAwal))+1,
-- --             l_JmlBulan =(12-(EXTRACT(MONTH FROM l_PrdAwal)))+1
-- --    END
-- -- ELSE
-- --    BEGIN
-- --      SELECT l_BlnProses=EXTRACT(MONTH FROM l_TglProses),
-- --             l_JmlBulan =12
-- --    END 

-- by peggy 2007 01 15 : kalau prosesnya hanya kolom8 aja dan belum proses payroll, 
-- 	jangan disetahunkan gross income, 
-- 	tapi kalau ada kolom8 atau sudah proses payroll, mekanisme sekarang udah betul 
--
-- Periode Penggajian
IF EXTRACT(YEAR FROM l_PrdAwal)=EXTRACT(YEAR FROM l_TglProses) THEN 
   BEGIN
-- ada kolom 1 atau sudah proses payroll 
     IF l_Curr_Inc <> 0 OR 
        (l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND l_TglBfr IS NOT NULL AND 
		EXTRACT(MONTH FROM l_TglProses) = EXTRACT(MONTH FROM l_TglBfr)) THEN 
	 BEGIN 
	     l_BlnProses := (EXTRACT(MONTH FROM l_TglProses)-EXTRACT(MONTH FROM l_PrdAwal))+1;
	     l_JmlBulan  := (12-(EXTRACT(MONTH FROM l_PrdAwal)))+1;
	 END ;
     END IF; 
-- gak ada kolom 1 atau belum proses payroll           
     IF l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND (l_TglBfr IS NULL OR 
		EXTRACT(MONTH FROM l_TglProses) <> EXTRACT(MONTH FROM l_TglBfr)) THEN 
	 BEGIN 
	     l_BlnProses := (COALESCE(EXTRACT(MONTH FROM l_Tglbfr),EXTRACT(MONTH FROM l_PrdAwal))-EXTRACT(MONTH FROM l_PrdAwal))+1;
	     l_JmlBulan  := (12-(EXTRACT(MONTH FROM l_PrdAwal)))+1;
	 END; 
      END IF; 
   END;
ELSE
   BEGIN
-- ada kolom 1 atau sudah proses payroll 
     IF l_Curr_Inc <> 0 OR 
        (l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND l_TglBfr IS NOT NULL AND 
		EXTRACT(MONTH FROM l_TglProses) = EXTRACT(MONTH FROM l_TglBfr)) THEN 
	 BEGIN 
	     l_BlnProses := EXTRACT(MONTH FROM l_TglProses);
	     l_JmlBulan  := 12;
	 END;
     END IF;      
-- gak ada kolom 1 atau belum proses payroll       
     IF l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND (l_TglBfr IS NULL OR 
		EXTRACT(MONTH FROM l_TglProses) <> EXTRACT(MONTH FROM l_TglBfr)) THEN 
	 BEGIN 
	     l_BlnProses := COALESCE(EXTRACT(MONTH FROM l_Tglbfr),EXTRACT(MONTH FROM l_TglProses));
	     l_JmlBulan  := 12;
	 END;
      END IF; 
   END;
END IF;  


--bln proses occ support 
SELECT MAX(S01.TGLPAYR) 
INTO   l_TglMaxCabBeda 
FROM S01HGAJ S01 
INNER JOIN M15PEGA M15
ON S01.NIP = M15.NIP 
WHERE S01.KDCABA <> M15.KDCABA AND M15.NIP = l_NIP AND 
	EXTRACT(YEAR FROM S01.TGLPAYR) = EXTRACT(YEAR FROM l_TglProses);
--
SELECT MIN(S01.TGLPAYR) 
INTO   l_TglProsesPertamaThnini
FROM S01HGAJ S01 
WHERE S01.NIP = l_NIP AND EXTRACT(YEAR FROM S01.TGLPAYR) = EXTRACT(YEAR FROM l_TglProses);
--
-- -- SET l_TglMaxCabBeda = CASE WHEN l_TglMaxCabBeda IS NOT NULL THEN l_TglMaxCabBeda 
-- -- 						  WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal)= EXTRACT(YEAR FROM l_TglProses) THEN l_PrdAwal 
-- -- 						  ELSE l_TglProsesPertamaThnini
-- -- 					 END 
--
l_BlnProsesOccSupp := CASE WHEN l_FZ2FlgCabang = 'Y' THEN (EXTRACT(MONTH FROM l_TglProses) -
						  CASE WHEN l_TglMaxCabBeda IS NOT NULL THEN 
								EXTRACT(MONTH FROM l_TglMaxCabBeda) 
							WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal) = EXTRACT(YEAR FROM l_TglProses) THEN 
								(EXTRACT(MONTH FROM l_PrdAwal) - 1)
							WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal)<> EXTRACT(YEAR FROM l_TglProses) 
								AND l_TglProsesPertamaThnini IS NOT NULL THEN 
								(EXTRACT(MONTH FROM l_TglProsesPertamaThnini) - 1 ) 
							ELSE 
								0
						  END)
			 ELSE 
				l_BlnProses 
			 END ;

--Main Data
SELECT TBL6.YTDIT,   TBL6.YITBonus
INTO   l_PajakYTDNB, l_PajakYTDB
FROM
(
SELECT TBL5.TglPayr,TBL5.NIP,TBL5.KdCaba,TBL5.TglKeluar,TBL5.FlagMati,
	TBL5.Kewarganegaraan,TBL5.JnsKlmn,TBL5.StsPjk,
	TBL5.GIYTD,TBL5.BonusYTD,TBL5.OccSupport1,TBL5.OccSupport2,TBL5.Kolom12YTD,
	TBL5.OLDOccSupport1, TBL5.OLDOccSupport2,
	TBL5.YTDT,TBL5.EGIYNB,TBL5.Disetahunkan,TBL5.PTKP,
	TBL5.EYTI,TBL5.EYIT,TBL5.YTDIT,TBL5.EYTIT,TBL5.EYITT,
	-- Yearly Income Tax Bonus 
	YITBonus=TBL5.EYITT-TBL5.EYIT
FROM
(
SELECT TBL4.TglPayr,TBL4.NIP,TBL4.KdCaba,TBL4.TglKeluar,TBL4.FlagMati,
	TBL4.Kewarganegaraan,TBL4.JnsKlmn,TBL4.StsPjk,
	TBL4.GIYTD,TBL4.BonusYTD,TBL4.OccSupport1,TBL4.OccSupport2,TBL4.Kolom12YTD,
	TBL4.OLDOccSupport1, TBL4.OLDOccSupport2,
	TBL4.YTDT,TBL4.EGIYNB,TBL4.Disetahunkan,TBL4.PTKP,
	TBL4.EYTI,TBL4.EYIT,TBL4.YTDIT,TBL4.EYTIT,

	-- Estimate Yearly Income Tax Total
	EYITT=CASE WHEN TBL4.EYTIT <=0 THEN 0
		 ELSE fn_GetTax(TBL4.NIP,FLOOR(TBL4.EYTIT/1000)*1000,'') END
	--						- COALESCE(fn_cariopbl('19B',l_nip,l_TglProses,l_mypass),0)
FROM
(
SELECT TBL3.TglPayr,TBL3.NIP,TBL3.KdCaba,TBL3.TglKeluar,TBL3.FlagMati,
	TBL3.Kewarganegaraan,TBL3.JnsKlmn,TBL3.StsPjk,
	TBL3.GIYTD,TBL3.BonusYTD,TBL3.OccSupport1,TBL3.OccSupport2,TBL3.Kolom12YTD,
	TBL3.OLDOccSupport1, TBL3.OLDOccSupport2,
	TBL3.YTDT,TBL3.EGIYNB,TBL3.Disetahunkan,TBL3.PTKP,
	TBL3.EYTI,TBL3.EYIT,

		-- Year To Date Income Tax
--              YTDIT=ROUND(CASE WHEN TBL3.EYIT=0 THEN 0
--                                      ELSE CASE WHEN TBL3.DiSetahunkan='Y' THEN (TBL3.EYIT*l_BlnProses)/l_JmlBulan
--                                                ELSE TBL3.EYIT END
--                                      END,0),
	YTDIT=ROUND(CASE WHEN TBL3.EYIT=0 THEN 0
-- modi untuk opbl 
--                  ELSE CASE WHEN TBL3.DiSetahunkan='Y' THEN (TBL3.EYIT*l_BlnProses)/l_JmlBulan
			  ELSE CASE WHEN TBL3.DiSetahunkan='Y' THEN ((TBL3.EYIT-TBL3.PJKOPBL)*l_BlnProses)/l_JmlBulan
				    ELSE TBL3.EYIT - TBL3.PJKOPBL END                      
			END,0),
		
		-- Estimate Yearly taxable Income Total
	EYTIT=(TBL3.EGIYNB+TBL3.BonusYTD)-TBL3.OccSupport2-TBL3.OLDOccSupport2-TBL3.PTKP,
	TBL3.PJKOPBL 
FROM
(
SELECT TBL2.TglPayr,TBL2.NIP,TBL2.KdCaba,TBL2.TglKeluar,TBL2.FlagMati,
	TBL2.Kewarganegaraan,TBL2.JnsKlmn,TBL2.StsPjk,
	TBL2.GIYTD,TBL2.BonusYTD,TBL2.OccSupport1,TBL2.OccSupport2,
	TBL2.OLDOccSupport1, TBL2.OLDOccSupport2,
	TBL2.Kolom12YTD,
	TBL2.YTDT,TBL2.EGIYNB,TBL2.Disetahunkan,TBL2.PTKP,
	-- Estimasi Yearly Taxable Income
	EYTI=FLOOR((TBL2.EGIYNB-TBL2.PTKP)/1000)*1000, 
	-- Estimasi Yearly Income Tax
	EYIT=CASE WHEN (TBL2.EGIYNB-TBL2.PTKP)<=0 THEN 0
		      ELSE fn_GetTax(TBL2.NIP,FLOOR((TBL2.EGIYNB-TBL2.PTKP)/1000)*1000,'') END,
	--					- COALESCE(fn_cariopbl('19NB',l_nip,l_TglProses,l_mypass),0) 
	PJKOPBL = COALESCE(fn_cariopbl('19',l_nip,l_TglProses,l_mypass),0) 
FROM
(
--
SELECT TBL1.TglPayr,TBL1.NIP,TBL1.KdCaba,TBL1.TglKeluar,TBL1.FlagMati,
	TBL1.Kewarganegaraan,TBL1.JnsKlmn,TBL1.StsPjk,
	TBL1.GIYTD,TBL1.BonusYTD,TBL1.OccSupport1,TBL1.OccSupport2,
	TBL1.OLDOccSupport1, TBL1.OLDOccSupport2,
	TBL1.Kolom12YTD,

	-- Year To Date Total
	YTDT=TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD,

	-- Estimasi Gross Income Yearly Non Bonus
	EGIYNB=CASE WHEN TBL1.TglKeluar<>' ' AND TBL1.TglKeluar=l_TglProses THEN
		       CASE WHEN (TBL1.FlagMati=1 OR TBL1.Kewarganegaraan<>'INDONESIA') THEN 
				((((TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD)-l_PajakYTDB)*l_JmlBulan)/l_BlnProses)+l_PajakYTDB
		       ELSE TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD END
		       ELSE ((((TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD)-l_PajakYTDB)*l_JmlBulan)/l_BlnProses)+l_PajakYTDB END
					+ fn_cariopbl('16B',l_nip,l_TglProses,l_mypass) 
	,--  							+ fn_cariopbl('2',l_nip,l_TglProses,l_mypass), 
	-- Flag diSetahunkan 
	DiSetahunkan=CASE WHEN (TBL1.TglKeluar<>' ' AND TBL1.TglKeluar=l_TglProses) THEN
				     CASE WHEN (TBL1.FlagMati=1 OR TBL1.Kewarganegaraan<>'INDONESIA') THEN 'Y'
				      ELSE 'T' END
			      ELSE 'Y' END,

	-- PTKP
	PTKP=fn_GetPTKP(TBL1.NIP,TBL1.JnsKlmn,l_FZ1PTKPPay,l_FZ1PTKPDep,TBL1.StsPjk)
FROM
(
SELECT S01.TglPayr,S01.NIP,S01.KdCaba,TglKeluar=COALESCE(M15.TglKeluar,' '),
       M16.FlagMati,M15.Kewarganegaraan,M15.JnsKlmn,M15.StsPjk,

                          /*****************************
                            PERHITUNGAN PAJAK NON BONUS
                           ******************************/
       -- Gross Income YTD = Current Income + Income Sebelumnya + Income Pindah Cabang
       GIYTD=COALESCE(l_GIYTD,0),

       -- Bonus YTD = Current Bonus + Bonus Sebelumnya + Bonus Pindah Cabang
       BonusYTD=COALESCE(l_Bonus,0)   
,--			   + COALESCE(fn_cariopbl('8',l_nip,l_TglProses,l_mypass),0), 

       -- OccSupport1
       OccSupport1=CASE WHEN l_P_Harian<>'Y' THEN ROUND(CASE WHEN (((l_FZ1PrOccSupp * (COALESCE(l_KomponenOccSupport,0)))/100)
                                   < l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
                              THEN ((l_FZ1PrOccSupp *(COALESCE(l_KomponenOccSupport,0)))/100)
                              ELSE (l_BlnProsesOccSupp*l_FZ1NlOccSupp) END, 0)
                        ELSE 0 END,

       -- OccSupport2
       OccSupport2=CASE WHEN l_P_Harian<>'Y' THEN 
                             ROUND(CASE WHEN ((COALESCE(l_KomponenOccSupport+l_KomponenOccSupport2,0)) * l_FZ1PrOccSupp)/100 
                                        < (l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
                                   THEN ((COALESCE(l_KomponenOccSupport+l_KomponenOccSupport2,0)) * l_FZ1PrOccSupp)/100 
                                   ELSE (l_BlnProsesOccSupp*l_FZ1NlOccSupp) END,0)
                             -
                             ROUND(CASE WHEN (((l_FZ1PrOccSupp * (COALESCE(l_KomponenOccSupport,0)))/100)
                                               < l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
                                        THEN ((l_FZ1PrOccSupp *(COALESCE(l_KomponenOccSupport,0)))/100)
                                        ELSE (l_BlnProsesOccSupp*l_FZ1NlOccSupp) END, 0)
                        ELSE 0 END,
	   OLDOccSupport1 = COALESCE(Q1.xOLDOccSupport1,0), 
	   OLDOccSupport2 = COALESCE(Q1.xOLDOccSupport2,0), 

       -- Iuran THT Year To date
       Kolom12YTD=COALESCE(l_Kolom12,0)
--- SUMBER  DATA 
--
--FROM S01HGAJ S01 
FROM V_HGA2 S01 
-- Inner ke Master Pegawai
INNER JOIN M15PEGA M15
ON M15.NIP=S01.NIP
--LEFT JOIN ke Pajak Sebelumnya (Q1)
LEFT JOIN 
(
SELECT S03.*, 
		xOldOccSupport1 = -- SYSTEM TIDAK PINDAH CABANG 
				CASE WHEN l_FZ2FlgCabang ='T' THEN 
						COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2),0)
				-- SYSTEM PINDAH CABANG DAN PINDAH PADA PROSES INI 
					 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba <> COALESCE(S03.KdCaba,M15.KdCaba) THEN 
						COALESCE(Fn_Kpusat(S03.NIP,S03.EncOccSupport1,l_MyPass) ::DECIMAL(15,2),0) +
						COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2),0)							
				-- SYSTEM PINDAH CABANG DAN TIDAK PINDAH CABANG PADA PROSES INI 
					 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba = COALESCE(S03.KdCaba,M15.KdCaba) THEN 
					COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2),0)
					END,
		xOldOccSupport2 = -- SYSTEM TIDAK PINDAH CABANG 
				CASE WHEN l_FZ2FlgCabang ='T' THEN 
						COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2),0)
				-- SYSTEM PINDAH CABANG DAN PINDAH PADA PROSES INI 
					 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba <> COALESCE(S03.KdCaba,M15.KdCaba) THEN 
						COALESCE(Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_MyPass) ::DECIMAL(15,2),0) +  
						COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2),0)
				-- SYSTEM PINDAH CABANG DAN TIDAK PINDAH CABANG PADA PROSES INI 
					 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba = COALESCE(S03.KdCaba,M15.KdCaba) THEN 
						COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2),0)
					END
FROM V_LTAX S03
--
INNER JOIN M15PEGA M15
ON M15.NIP=S03.NIP
--
WHERE S03.TglPayr=(Select Max(S.TglPayr) FROM V_LTAX S WHERE S.TglPayr<l_TglProses AND S.NIP=l_NIP) AND 
      S03.NIP=l_NIP AND EXTRACT(YEAR FROM S03.TglPayr)=EXTRACT(YEAR FROM l_TglProses) 
	  AND S03.TglPayr > M15.TglMasuk -- ini supaya oldinctaxapaidnb gak keambil 
) Q1
ON Q1.NIP=S01.NIP
-- INNER Join M16ALKL
LEFT JOIN M16ALKL M16
ON M16.Kode=M15.KdTerr
WHERE S01.TglPayr=l_TglProses AND S01.NIP=l_NIP
--
) TBL1

) TBL2

) TBL3

) TBL4

) TBL5

) TBL6; 
END ;
$$ LANGUAGE plpgsql ;
/*
  EXEC P_Netto1 l_TglProses,    
	        	l_NIP,       
                l_GIYTD_Total,
                l_BonusYTD,
                l_Kolom12YTD,
                l_PajakYTDNB OUTPUT,
                l_PajakYTDB OUTPUT,
				l_MyPass,
                l_P_Harian
  -
DECLARE              l_PajakYTDNB   DECIMAL(15,2),
                     l_PajakYTDB    DECIMAL(15,2),
		     l_Total	   DECIMAL(15,2)	
		     
--
SELECT l_PajakYTDNB=0,
       l_PajakYTDB=0,
	l_Total=3500000+l_PajakYTDNB+l_PajakYTDB
--
--
EXEC P_Netto1 	'2003-02-20',
        		'010',
		        l_Total,
                70000,
                0,
                l_PajakYTDNB OUTPUT,
                l_PajakYTDB OUTPUT,
                'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',     
                'T' 

SELECT l_PajakYTDNB,l_PajakYTDB,l_PajakYTDNB,l_PajakYTDB


*/

