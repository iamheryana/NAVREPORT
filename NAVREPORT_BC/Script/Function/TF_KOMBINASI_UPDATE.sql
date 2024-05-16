/****************************************
Name sprocs	: tf_Kombinasi_Update
Create by	: Wati
Date		: 08-05-2003
Description	: Untuk Masukkan Data S01, S02,S03,S0E dari perhitungan Pajak
Call From	: Sub Routine
Sub sprocs	: -
*****************************************/
CREATE OR REPLACE FUNCTION tf_Kombinasi_Update(l_TglProses    DATE,
						l_NIP          VARCHAR(10),       
						l_GIYTD        DECIMAL(15,2),
						l_Bonus        DECIMAL(15,2),
						l_Kolom12      DECIMAL(15,2),
						l_TunjanganNB  DECIMAL(15,2),
						l_TunjanganB   DECIMAL(15,2),
						l_UserID       VARCHAR(12), 	 
						l_MyPass       VARCHAR(128),
						l_P_Harian     VARCHAR(1),
						l_curr_inc     DECIMAL(15,2), 
						l_FlagTHR      VARCHAR(1), 
						l_KomponenOccSupport DECIMAL(15,2), 
						l_KomponenOccSupport2 DECIMAL(15,2))

RETURNS TABLE(
        TglPayr  	 DATE,
        NIP      	 VARCHAR(10),
        KdCaba           VARCHAR(4),
        NewKdCaba        VARCHAR(4),
        OldKdCaba        VARCHAR(4), 
        GrsIncYTD        DECIMAL(15,2),
        FixIncomePYTD    DECIMAL(15,2),
        OLDFixIncomePYTD DECIMAL(15,2),
        VarIncomePYTD    DECIMAL(15,2),
        OLDVarIncomePYTD DEcIMAL(15,2),
        Col2YTD          DECIMAL(15,2),
        OLDCol2YTD       DECIMAL(15,2),
        Col3YTD          DECIMAL(15,2),
        OLDCol3YTD       DECIMAL(15,2),
        Col4YTD          DECIMAL(15,2),
        OLDCol4YTD       DECIMAL(15,2),
        Col5YTD          DECIMAL(15,2),
        OLDCol5YTD       DECIMAL(15,2),
        Col6YTD          DECIMAL(15,2),
        OLDCol6YTD       DECIMAL(15,2),
        Col12PYTD        DECIMAL(15,2),
        OLDCol12PYTD     DECIMAL(15,2),
        Col8PYTD         DECIMAL(15,2),
        OLDCol8PYTD      DECIMAL(15,2),
        OccSupport1      DECIMAL(15,2),
        OLDOccSupport1   DECIMAL(15,2),
        OccSupport1PYTD  DECIMAL(15,2),
        OccSupport2      DECIMAL(15,2),
        OLDOccSupport2   DECIMAL(15,2),
        OccSupport2PYTD  DECIMAL(15,2),
        EGIYNB           DECIMAL(15,2),
        PTKP             DECIMAL(15,2),
        EYTI             DECIMAL(15,2),
        EYIT             DECIMAL(15,2),
        YTDIT            DECIMAL(15,2),
        EYITT            DECIMAL(15,2),	
        IncTaxAPaidNB    DECIMAL(15,2),
        OLDIncTaxAPaidNB DECIMAL(15,2),
        IncTaxAPaidB     DECIMAL(15,2),
        OLDIncTaxAPaidB  DECIMAL(15,2),
        CurrTaxNB 	 DECIMAL(15,2),
        CurrTaxB  	 DECIMAL(15,2),
        YITBonus  	 DECIMAL(15,2),
        PotPajak  	 DECIMAL(15,2),
        TunjanganYTD 	 DECIMAL(15,2),
        Tunjangancurr 	 DECIMAL(15,2),
        Kolom2 		 DECIMAL(15,2),
        Kolom3 		 DECIMAL(15,2),
        Kolom4 		 DECIMAL(15,2),
        Kolom5 		 DECIMAL(15,2),
        Kolom6 		 DECIMAL(15,2),
        Kolom12 	 DECIMAL(15,2),
        Kolom8 		 DECIMAL(15,2))

AS $$ 
DECLARE	l_TglPayr          DATE;        	l_KdCaba           VARCHAR(4);
        l_NewKdCaba        VARCHAR(4);         	l_OldKdCaba        VARCHAR(4);
        l_Tahun            DECIMAL(4);      	l_Bulan            VARCHAR(2);
	l_FZ1NlOccSupp     DECIMAL(15,2);
	l_FZ1PrOccSupp     DECIMAL(5,2);    	l_FZ1PTKPPay	   DECIMAL(15,2);	
	l_FZ1PTKPDep	   DECIMAL(15,2);   	l_GrsIncYTD	   DECIMAL(15,2);
        l_BlnProses        INT;         	l_JmlBulan         INT;
        l_PrdAwal          DATE;        	l_FZ2FlgCabang     VARCHAR(1);
        l_QDesc 	   VARCHAR(4000);  	l_QPara 	   VARCHAR(4000); 
        l_FixIncomePYTD    DECIMAL(15,2);   	l_OLDFixIncomePYTD DECIMAL(15,2);
        l_FixIncome        DECIMAL(15,2);   	l_VarIncome        DECIMAL(15,2);
        l_VarIncomePYTD    DECIMAL(15,2);   	l_OLDVarIncomePYTD DECIMAL(15,2);       
        l_Col2YTD          DECIMAL(15,2);   	l_OLDCol2YTD       DECIMAL(15,2);
        l_Col3YTD          DECIMAL(15,2);   	l_OLDCol3YTD       DECIMAL(15,2);
        l_Col4YTD          DECIMAL(15,2);   	l_OLDCol4YTD       DECIMAL(15,2);
        l_Col5YTD          DECIMAL(15,2);   	l_OLDCol5YTD       DECIMAL(15,2);
        l_Col6YTD          DECIMAL(15,2);   	l_OLDCol6YTD       DECIMAL(15,2);
        l_Col12PYTD        DECIMAL(15,2);
        l_OLDCol12PYTD     DECIMAL(15,2);   	l_OccSupport1      DECIMAL(15,2);
        l_OLDOccSupport1   DECIMAL(15,2);   	l_EGIYNB           DECIMAL(15,2);
        l_PTKP		   DECIMAL(15,2);   	l_EYTI	     	   DECIMAL(15,2);
        l_EYIT		   DECIMAL(15,2);   	l_YTDIT	     	   DECIMAL(15,2);
        l_IncTaxAPaidNB	   DECIMAL(15,2);   	l_OLDIncTaxAPaidNB DECIMAL(15,2);
        l_Kolom8           DECIMAL(15,2);   	l_Col8PYTD         DECIMAL(15,2);
        l_OLDCol8PYTD      DECIMAL(15,2);   	l_OccSupport2      DECIMAL(15,2);
        l_OLDOccSupport2   DECIMAL(15,2);   	l_EYITT            DECIMAL(15,2);
        l_IncTaxAPaidB     DECIMAL(15,2);   	l_OLDIncTaxAPaidB  DECIMAL(15,2);
        l_M03Singkatan     VARCHAR(10);   	l_M03KdCurr	   VARCHAR(4);
        l_CurrTaxNB        DECIMAL(15,2);   	l_CurrTaxB         DECIMAL(15,2);
        l_YITBonus         DECIMAL(15,2);   	l_PotPajak         DECIMAL(15,2);
        l_TunjanganYTD     DECIMAL(15,2);   	l_TunjanganCurr    DECIMAL(15,2);
	l_FZ1Kode	   VARCHAR(2);
	l_ADAOPBL 	   DECIMAL(5,0); 
	l_TglMaxCabBeda	   DATE;
	l_TglProsesPertamaThnini DATE;  	l_BlnProsesOccSupp INT;
	l_BLNKRG 	  INT;      
declare l_tglbfr DATE ;

BEGIN
-- Parameter
  l_BLNKRG := 0;
-- Parameter LDA
SELECT NlOccSupp,      PrOccSupp,      PTKPPay,      PTKPDep,      Kode
INTO   l_FZ1NlOccSupp, l_FZ1PrOccSupp, l_FZ1PTKPPay, l_FZ1PTKPDep, l_FZ1Kode
FROM FZ1FLDA ;

-- Flag Cabang
SELECT CASE SUBSTRING(StringFlag,2,1) WHEN '1' THEN 'Y' ELSE 'T' END
INTO l_FZ2FlgCabang
FROM FZ2FLDA
WHERE Kode=l_FZ1Kode;

-- Periode awal diambil dari tanggal masuk 
l_BlnProses := 0;
l_JmlBulan  := 0;

SELECT m15.TglMasuk
INTO l_PrdAwal
FROM M15PEGA m15
WHERE m15.NIP=l_NIP;

-- by peggy 2007 01 15 -- 
--
SELECT s05.TglPosting
INTO l_Tglbfr
FROM S05PSTD s05
WHERE s05.NIP=l_NIP AND s05.FlagTHR=''
ORDER BY s05.NIP,s05.TglPosting  ;

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
	     	   l_BlnProses := (EXTRACT(MONTH FROM l_TglProses)-EXTRACT(MONTH FROM l_PrdAwal))+1 ;
	           l_JmlBulan  := (12-(EXTRACT(MONTH FROM l_PrdAwal)))+1;
		   l_BLNKRG    := 1;
	 	END ;
     END IF; 
-- gak ada kolom 1 atau belum proses payroll           
     IF l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND (l_TglBfr IS NULL OR 
	EXTRACT(MONTH FROM l_TglProses) <> EXTRACT(MONTH FROM l_TglBfr)) THEN 
	BEGIN 
	   l_BlnProses := (COALESCE(EXTRACT(MONTH FROM l_Tglbfr),EXTRACT(MONTH FROM l_prdawal))-EXTRACT(MONTH FROM l_PrdAwal))+1;
	   l_JmlBulan  := (12-(EXTRACT(MONTH FROM l_PrdAwal)))+1;
	   l_BLNKRG    := 0;
	END ;
     END IF; 
   END;
ELSE
   BEGIN
-- ada kolom 1 atau sudah proses payroll 
     IF l_Curr_Inc <> 0 OR 
        (l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND l_TglBfr IS NOT NULL AND 
	EXTRACT(MONTH FROM l_TglProses) = EXTRACT(MONTH FROM l_TglBfr)) THEN 
	BEGIN 
	   l_BlnProses:= EXTRACT(MONTH FROM l_TglProses);
	   l_JmlBulan := 12;
	   l_BLNKRG   := 1;
	END;
     END IF; 
-- gak ada kolom 1 atau belum proses payroll       
     IF l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND (l_TglBfr IS NULL OR 
	EXTRACT(MONTH FROM l_TglProses) <> EXTRACT(MONTH FROM l_TglBfr)) THEN 
	BEGIN 
	   l_BlnProses := COALESCE(EXTRACT(MONTH FROM l_Tglbfr),EXTRACT(MONTH FROM l_TglProses));
	   l_JmlBulan  := 12;
	   l_BLNKRG    := 0;
	END;
     END IF;
   END;
END IF; 

--bln proses occ support 
SELECT MAX(S01.TGLPAYR) 
INTO l_TglMaxCabBeda
FROM S01HGAJ S01 
INNER JOIN M15PEGA M15
ON S01.NIP = M15.NIP 
WHERE S01.KDCABA <> M15.KDCABA AND M15.NIP = l_NIP AND 
	EXTRACT(YEAR FROM S01.TGLPAYR) = EXTRACT(YEAR FROM l_TglProses);
--
SELECT MIN(S01.TGLPAYR) 
INTO l_TglProsesPertamaThnini
FROM S01HGAJ S01 
WHERE S01.NIP = l_NIP AND EXTRACT(YEAR FROM S01.TGLPAYR) = EXTRACT(YEAR FROM l_TglProses);
--
-- -- SET l_TglMaxCabBeda = CASE WHEN l_TglMaxCabBeda IS NOT NULL THEN l_TglMaxCabBeda 
-- -- 						  WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal)= EXTRACT(YEAR FROM l_TglProses) THEN l_PrdAwal 
-- -- 						  ELSE l_TglProsesPertamaThnini
-- -- 					 END 
--
l_BlnProsesOccSupp=CASE WHEN l_FZ2FlgCabang = 'Y' THEN (EXTRACT(MONTH FROM l_TglProses) -
			  CASE WHEN l_TglMaxCabBeda IS NOT NULL THEN 
					EXTRACT(MONTH FROM l_TglMaxCabBeda) 
				   WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal) = EXTRACT(YEAR FROM l_TglProses) THEN 
--										(EXTRACT(MONTH FROM l_PrdAwal) - 1) 
					(EXTRACT(MONTH FROM l_PrdAwal) - l_BLNKRG) 
				   WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal)<> EXTRACT(YEAR FROM l_TglProses) 
					AND l_TglProsesPertamaThnini IS NOT NULL THEN 
-- 										(EXTRACT(MONTH FROM l_TglProsesPertamaThnini) - 1)  
					(EXTRACT(MONTH FROM l_TglProsesPertamaThnini) - l_BLNKRG )  
				   ELSE 
					0
			  END)
			 ELSE 
				l_BlnProses 
			 END ;

--Main Data
RETURN QUERY
SELECT QXX.TglPayr 			 AS TglPayr, 
	QXX.NIP 			 AS NIP, 
	QXX.KdCaba 			 AS KdCaba, 
	QXX.NewKdCaba 			 AS NewKdCaba,
	QXX.OldKdCaba 			 AS OldKdCaba,
	COALESCE(QXX.GIYTD,0) 		 AS GrsIncYTD,
	COALESCE(QXX.FixIncomePYTD,0) 	 AS FixIncomePYTD,
	COALESCE(QXX.OLDFixIncomePYTD,0) AS OLDFixIncomePYTD,
	COALESCE(QXX.VarIncomePYTD,0) 	 AS VarIncomePYTD,
	COALESCE(QXX.OLDVarIncomePYTD,0) AS OLDVarIncomePYTD,
	COALESCE(QXX.Col2YTD,0) 	 AS Col2YTD,
	COALESCE(QXX.OLDCol2YTD,0) 	 AS OLDCol2YTD,
	COALESCE(QXX.Col3YTD,0) 	 AS Col3YTD,
	COALESCE(QXX.OLDCol3YTD,0) 	 AS OLDCol3YTD,
	COALESCE(QXX.Col4YTD,0) 	 AS Col4YTD,
	COALESCE(QXX.OLDCol4YTD,0) 	 AS OLDCol4YTD,
	COALESCE(QXX.Col5YTD,0) 	 AS Col5YTD,
	COALESCE(QXX.OLDCol5YTD,0) 	 AS OLDCol5YTD,
	COALESCE(QXX.Col6YTD,0) 	 AS Col6YTD,
	COALESCE(QXX.OLDCol6YTD,0) 	 AS OLDCol6YTD, 
	COALESCE(QXX.Col12PYTD,0) 	 AS Col12PYTD, 
	COALESCE(QXX.OLDCol12PYTD,0) 	 AS OLDCol12PYTD,
	COALESCE(QXX.Col8PYTD,0) 	 AS Col8PYTD,
	COALESCE(QXX.OLDCol8PYTD,0) 	 AS OLDCol8PYTD,
	COALESCE(QXX.OccSupport1,0) 	 AS OccSupport1,
	COALESCE(QXX.OldOccSupport1,0) 	 AS OLDOccSupport1, 
	COALESCE(QXX.OccSupport1PYTD,0)  AS OccSupport1PYTD, 
	COALESCE(QXX.OccSupport2,0) 	 AS OccSupport2,
	COALESCE(QXX.OldOccSupport2,0) 	 AS OLDOccSupport2,
	COALESCE(QXX.OccSupport2PYTD,0)  AS OccSupport2PYTD,
	COALESCE(QXX.EGIYNB,0)		 AS EGIYNB,
	COALESCE(QXX.PTKP,0) 		 AS PTKP, 
	COALESCE(QXX.EYTI,0) 		 AS EYTI, 
	COALESCE(QXX.EYIT,0) 		 AS EYIT, 
	COALESCE(QXX.YTDIT,0) 		 AS YTDIT,
	COALESCE(QXX.EYITT,0) 		 AS EYITT,
	COALESCE(QXX.TaxAPaidNB,0) 	 AS IncTaxAPaidNB, 
	COALESCE(QXX.OLDIncTaxAPaidNB,0) AS OLDIncTaxAPaidNB,
	COALESCE(QXX.TaxAPaidB,0) 	 AS IncTaxAPaidB,
	COALESCE(QXX.OLDIncTaxAPaidB,0)  AS OLDIncTaxAPaidB,
	COALESCE(QXX.CurrTaxNB,0) 	 AS CurrTaxNB,
	COALESCE(QXX.CurrTaxB,0) 	 AS CurrTaxB,
	COALESCE(QXX.YITBonus,0) 	 AS YITBonus, 
	COALESCE(QXX.PotPajak,0) 	 AS PotPajak,
	COALESCE(QXX.TunjanganYTD,0) 	 AS TunjanganYTD,
	COALESCE(QXX.TunjanganCurr,0) 	 AS Tunjangancurr, 
	COALESCE(QXX.Kolom2,0) 		 AS Kolom2,
	COALESCE(QXX.Kolom3,0) 		 AS Kolom3,
	COALESCE(QXX.Kolom4,0) 	 	 AS Kolom4, 
	COALESCE(QXX.Kolom5,0) 		 AS Kolom5, 
	COALESCE(QXX.Kolom6,0) 		 AS Kolom6, 
	COALESCE(QXX.Kolom12,0) 	 AS Kolom12,
	COALESCE(QXX.Kolom8,0) 		 AS Kolom8
       
FROM
(--QXX 
SELECT TBL6.GIYTD,TBL6.BonusYTD,TBL6.OccSupport1,TBL6.OccSupport2,TBL6.Kolom12YTD,
       TBL6.OLDOccSupport1 AS A, TBL6.OLDOccSupport2 AS B,
       TBL6.YTDT,TBL6.EGIYNB,TBL6.Disetahunkan,TBL6.PTKP,
       TBL6.EYTI,TBL6.EYIT,TBL6.YTDIT,TBL6.EYTIT,TBL6.EYITT,
       TBL6.TaxAPaidNB,TBL6.TaxAPaidB,TBL6.Kolom2YTD,
       TBL6.CurrTaxNB,TBL6.CurrTaxB,TBL6.YITBonus,

       -- Potongan Pajak 
       COALESCE(TBL6.CurrTaxNB+TBL6.CurrTaxB,0) AS PotPajak, 

       -- Tunjangan Pajak YTD
       COALESCE(l_TunjanganNB+l_TunjanganB,0) AS TunjanganYTD,

       -- Tunjangan Pajak Current
       COALESCE(l_TunjanganNB+l_TunjanganB,0)-TBL6.Kolom2YTD AS TunjanganCurr,
       QZZ.* 
FROM
(
SELECT TBL5.NIP,TBL5.KdCaba,
       TBL5.GIYTD,TBL5.BonusYTD,TBL5.OccSupport1,TBL5.OccSupport2,TBL5.Kolom12YTD,
       TBL5.OLDOccSupport1, TBL5.OLDOccSupport2,
       TBL5.YTDT,TBL5.EGIYNB,TBL5.Disetahunkan,TBL5.PTKP,
       TBL5.EYTI,TBL5.EYIT,TBL5.YTDIT,TBL5.EYTIT,TBL5.EYITT,
       TBL5.TaxAPaidNB,TBL5.TaxAPaidB,TBL5.Kolom2YTD,

       -- Current Month Tax NB
       COALESCE(TBL5.YTDIT-TBL5.TaxAPaidNB,0) AS CurrTaxNB,

       -- Current Month Tax Bonus
       COALESCE(TBL5.EYITT-TBL5.EYIT,0)-COALESCE(TBL5.TaxAPaidB,0) AS CurrTaxB,  

       -- Yearly Income Tax Bonus 
       COALESCE(TBL5.EYITT-TBL5.EYIT,0) AS YITBonus
FROM
(--TBL5
SELECT TBL4.TglPayr,TBL4.NIP,TBL4.KdCaba,
       TBL4.GIYTD,TBL4.BonusYTD,TBL4.OccSupport1,TBL4.OccSupport2,TBL4.Kolom12YTD,
       TBL4.OLDOccSupport1, TBL4.OLDOccSupport2,
       TBL4.YTDT,TBL4.EGIYNB,TBL4.Disetahunkan,TBL4.PTKP,
       TBL4.EYTI,TBL4.EYIT,TBL4.YTDIT,TBL4.EYTIT,
       TBL4.TaxAPaidNB,TBL4.TaxAPaidB,TBL4.Kolom2YTD,

       -- Estimate Yearly Income Tax Total
       CASE WHEN TBL4.EYTIT <=0 THEN 0
	  ELSE fn_GetTax(TBL4.NIP,FLOOR(TBL4.EYTIT/1000)*1000,'') END AS EYITT,
--					- COALESCE(fn_cariopbl('19B',l_nip,l_TglProses,l_mypass),0) 
       TBL4.PJKOPBL

FROM
(--TBL4
SELECT TBL3.TglPayr,TBL3.NIP,TBL3.KdCaba,
       TBL3.GIYTD,TBL3.BonusYTD,TBL3.OccSupport1,TBL3.OccSupport2,TBL3.Kolom12YTD,
       TBL3.OLDOccSupport1, TBL3.OLDOccSupport2,
       TBL3.YTDT,TBL3.EGIYNB,TBL3.Disetahunkan,TBL3.PTKP,
       TBL3.EYTI,TBL3.EYIT,
       TBL3.TaxAPaidNB,TBL3.TaxAPaidB,TBL3.Kolom2YTD,

       -- Year To Date Income Tax
--        YTDIT=ROUND(CASE WHEN TBL3.EYIT = 0 THEN 0
--                         ELSE CASE WHEN TBL3.DiSetahunkan='Y' THEN (TBL3.EYIT*l_BlnProses)/l_JmlBulan
--                                   ELSE TBL3.EYIT END
--                         END,0),

       ROUND(CASE WHEN TBL3.EYIT=0 THEN 0
-- modi untuk opbl 
--                  ELSE CASE WHEN TBL3.DiSetahunkan='Y' THEN (TBL3.EYIT*l_BlnProses)/l_JmlBulan
		   -- modi opbl lagi 24 09 2008 
	                  ELSE CASE WHEN TBL3.DiSetahunkan='Y' THEN ((TBL3.EYIT-TBL3.PJKOPBL)*l_BlnProses)/l_JmlBulan
	                            ELSE TBL3.EYIT - TBL3.PJKOPBL END                      
             		END,0) AS YTDIT,

       -- Estimate Yearly taxable Income Total
       (TBL3.EGIYNB+TBL3.BonusYTD)-TBL3.OccSupport2-TBL3.OLDOccSupport2-TBL3.PTKP AS EYTIT,
       TBL3.PJKOPBL
FROM
(--TBL3
SELECT TBL2.TglPayr, TBL2.NIP, TBL2.KdCaba,
       TBL2.GIYTD, TBL2.BonusYTD, TBL2.OccSupport1, TBL2.OccSupport2, TBL2.Kolom12YTD,
       TBL2.OLDOccSupport1, TBL2.OLDOccSupport2,
       TBL2.YTDT,TBL2.EGIYNB,TBL2.Disetahunkan,TBL2.PTKP,
       TBL2.TaxAPaidNB,TBL2.TaxAPaidB,TBL2.Kolom2YTD,
       -- Estimasi Yearly Taxable Income
		-- modi opbl lagi 24 09 2008 
       CASE WHEN (TBL2.EGIYNB-TBL2.PTKP) < 0 THEN 
					(FLOOR((ABS(TBL2.EGIYNB-TBL2.PTKP))/1000)*1000)*-1
                 ELSE FLOOR((TBL2.EGIYNB-TBL2.PTKP)/1000)*1000 END AS EYTI,
       -- Estimasi Yearly Income Tax
       CASE WHEN (TBL2.EGIYNB-TBL2.PTKP)<=0 THEN 0
                 ELSE fn_GetTax(TBL2.NIP,FLOOR((TBL2.EGIYNB-TBL2.PTKP)/1000)*1000,'') END AS EYIT,
--				- COALESCE(fn_cariopbl('19NB',l_nip,l_TglProses,l_mypass),0) 
       COALESCE(fn_cariopbl('19',l_nip,l_TglProses,l_mypass),0) AS PJKOPBL
FROM
(--TBL2
--
SELECT TBL1.TglPayr,TBL1.NIP,TBL1.KdCaba,TBL1.TglKeluar,TBL1.FlagMati,
       TBL1.Kewarganegaraan,TBL1.JnsKlmn,TBL1.StsPjk,
       TBL1.GIYTD,TBL1.BonusYTD,TBL1.OccSupport1,TBL1.OccSupport2,TBL1.Kolom12YTD,
	   TBL1.OLDOccSupport1, TBL1.OLDOccSupport2,
       TBL1.TaxAPaidNB,TBL1.TaxAPaidB,TBL1.Kolom2YTD,
       -- Year To Date Total
       TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD AS YTDT,
       -- Estimasi Gross Income Yearly Non Bonus
       CASE WHEN TBL1.TglKeluar is not null AND TBL1.TglKeluar=l_TglProses THEN
                        CASE WHEN (TBL1.FlagMati=1 OR TBL1.Kewarganegaraan<>'INDONESIA') THEN 
                                  ((((TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD)-l_TunjanganB)*l_JmlBulan)/l_BlnProses)+l_TunjanganB
                        ELSE TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD END
              ELSE ((((TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD)-l_TunjanganB)*l_JmlBulan)/l_BlnProses)+l_TunjanganB END 
				+ fn_cariopbl('16B',l_nip,l_TglProses,l_mypass) AS EGIYNB, 
--				+ fn_cariopbl('2',l_nip,l_TglProses,l_mypass),  

       -- Flag diSetahunkan 
       CASE WHEN (TBL1.TglKeluar is not null AND TBL1.TglKeluar=l_TglProses) THEN
		      CASE WHEN (TBL1.FlagMati=1 OR TBL1.Kewarganegaraan<>'INDONESIA') THEN 'Y'
		      ELSE 'T' END
	    ELSE 'Y' END AS DiSetahunkan,
       -- PTKP
       fn_GetPTKP(TBL1.NIP,TBL1.JnsKlmn,l_FZ1PTKPPay,l_FZ1PTKPDep,TBL1.StsPjk) AS PTKP

FROM
(--TBL1
SELECT S01.TglPayr,S01.NIP,S01.KdCaba,(M15.TglKeluar) as TglKeluar,
       M16.FlagMati,M15.Kewarganegaraan,M15.JnsKlmn,M15.StsPjk,

                          /*****************************
                            PERHITUNGAN PAJAK NON BONUS
                           ******************************/
       -- Gross Income YTD = Current Income + Income Sebelumnya + Income Pindah Cabang
       COALESCE(l_GIYTD,0) AS GIYTD,

       -- Bonus YTD = Current Bonus + Bonus Sebelumnya + Bonus Pindah Cabang
       COALESCE(l_Bonus,0) AS BonusYTD,  
--			   + COALESCE(fn_cariopbl('8',l_nip,l_TglProses,l_mypass),0), 

       -- OccSupport1
       CASE WHEN l_P_Harian<>'Y' THEN 
	     ROUND(CASE WHEN (((l_FZ1PrOccSupp * (COALESCE(l_KomponenOccSupport,0)))/100)
			     < l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
			THEN ((l_FZ1PrOccSupp *(COALESCE(l_KomponenOccSupport,0)))/100)
			 ELSE (l_BlnProsesOccSupp*l_FZ1NlOccSupp) END, 0)
	    ELSE 0 END AS OccSupport1,

       -- OccSupport2
       CASE WHEN l_P_Harian<>'Y' THEN 
		     ROUND(CASE WHEN ((COALESCE(l_KomponenOccSupport+l_KomponenOccSupport2,0)) * l_FZ1PrOccSupp)/100 
				     < (l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
				THEN ((COALESCE(l_KomponenOccSupport+l_KomponenOccSupport2,0)) * l_FZ1PrOccSupp)/100 
				ELSE (l_BlnProsesOccSupp*l_FZ1NlOccSupp) END,0)
		     -
		     ROUND(CASE WHEN (((l_FZ1PrOccSupp * (COALESCE(l_KomponenOccSupport,0)))/100)
				     < l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
				THEN ((l_FZ1PrOccSupp *(COALESCE(l_KomponenOccSupport,0)))/100)
				ELSE (l_BlnProsesOccSupp*l_FZ1NlOccSupp) END, 0)
		ELSE 0 END AS OccSupport2,
/*
       OccSupport1=CASE WHEN l_P_Harian<>'Y' THEN 
                             ROUND(CASE WHEN (((l_FZ1PrOccSupp * (COALESCE(l_KomponenOccSupport,0)))/100)
                                             < l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
                                        THEN ((l_FZ1PrOccSupp *(COALESCE(l_KomponenOccSupport,0)))/100)
                                   ELSE (l_BlnProsesOccSupp*l_FZ1NlOccSupp) END, 0)
                        ELSE 0 END,
*/
	   COALESCE(Q1.xOLDOccSupport1,0) as OLDOccSupport1, 
	   COALESCE(Q1.xOLDOccSupport2,0) as OLDOccSupport2, 

       -- Iuran THT Year To date
       COALESCE(l_Kolom12,0) AS Kolom12YTD,
       -- Tax Allready Paid Non Bonus
       COALESCE(fn_KPusat(Q1.NIP,Q1.EncYTDIT,l_MyPass) ::DECIMAL(15,2),0) AS TaxAPaidNB,
       -- Tax Allready Paid Bonus
       COALESCE(fn_KPusat(Q1.NIP,Q1.EncEYITT,l_MyPass) ::DECIMAL(15,2)-fn_KPusat(Q1.NIP,Q1.EncEYIT,l_MyPass) ::DECIMAL(15,2),0) AS TaxAPaidB,
       -- Tunjangan Pajak
--       Kolom2YTD=COALESCE(Q1.Col2YTD,0)
       COALESCE(fn_KPusat(Q1.NIP,Q1.EncCol2YTD,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(fn_KPusat(Q1.NIP,Q1.EncOLDCol2YTD,l_MyPass) ::DECIMAL(15,2),0) AS Kolom2YTD
--- SUMBER  DATA 
--
--FROM S01HGAJ S01 
FROM V_HGA2 S01 
-- Inner ke Master Pegawai
LEFT JOIN 
(--Q1
SELECT S03.*,
	 -- SYSTEM TIDAK PINDAH CABANG 
	CASE WHEN l_FZ2FlgCabang ='T' THEN 
			COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2),0)
	-- SYSTEM PINDAH CABANG DAN PINDAH PADA PROSES INI 
		 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba <> COALESCE(S03.KdCaba,M15.KdCaba) THEN 
			COALESCE(Fn_Kpusat(S03.NIP,S03.EncOccSupport1,l_MyPass) ::DECIMAL(15,2),0) +
			COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2),0)							
	-- SYSTEM PINDAH CABANG DAN TIDAK PINDAH CABANG PADA PROSES INI 
		 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba = COALESCE(S03.KdCaba,M15.KdCaba) THEN 
		COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2),0)
		END AS xOldOccSupport1,
	 -- SYSTEM TIDAK PINDAH CABANG 
	CASE WHEN l_FZ2FlgCabang ='T' THEN 
			COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2),0)
	-- SYSTEM PINDAH CABANG DAN PINDAH PADA PROSES INI 
		 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba <> COALESCE(S03.KdCaba,M15.KdCaba) THEN 
			COALESCE(Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_MyPass) ::DECIMAL(15,2),0) +  
			COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2),0)
	-- SYSTEM PINDAH CABANG DAN TIDAK PINDAH CABANG PADA PROSES INI 
		 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba = COALESCE(S03.KdCaba,M15.KdCaba) THEN 
			COALESCE(Fn_Kpusat(S03.NIP,S03.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2),0)
		END AS xOldOccSupport2
--FROM S03LTAX S03
FROM V_LTAX S03
--
INNER JOIN M15PEGA M15
ON M15.NIP=S03.NIP
--
WHERE S03.TglPayr=(Select Max(S.TglPayr) FROM V_LTAX S WHERE S.TglPayr<l_TglProses AND S.NIP=l_NIP) AND 
      S03.NIP=l_NIP AND EXTRACT(YEAR FROM S03.TglPayr)=EXTRACT(YEAR FROM l_TglProses) 
	  AND S03.TglPayr >= M15.TglMasuk 
) Q1
ON Q1.NIP=S01.NIP

INNER JOIN M15PEGA M15
ON M15.NIP=S01.NIP
--
-- INNER Join M16ALKL
LEFT JOIN M16ALKL M16
ON M16.Kode=M15.KdTerr
WHERE S01.TglPayr=l_TglProses AND S01.NIP=l_NIP

) TBL1

) TBL2

) TBL3

) TBL4

) TBL5

) TBL6

LEFT JOIN

( 
SELECT S01.TglPayr,S01.NIP,S01.KdCaba,
       M15.KdCaba AS NewKdCaba,
       CASE WHEN COALESCE(QX1.KdCaba,' ')=' ' THEN M15.KdCaba ELSE QX1.KdCaba END AS OldKdCaba,
       -- Fix Income
       COALESCE(QX5.FixIncome,0) AS FixIncome,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncFixIncomePYTD,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(fn_KPusat(QX1.NIP,QX1.EncFixIncome,l_MyPass) ::DECIMAL(15,2),0) AS FixIncomePYTD,       
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDFixIncomePYTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDFixIncomePYTD,
       -- Var Income
       COALESCE(QX6.VarIncome,0) AS VarIncome,--COALESCE(fn_KPusat(QX1.NIP,QX1.EncVarIncome,l_MyPass)),0),
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncVarIncomePYTD,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(fn_KPusat(QX1.NIP,QX1.EncVarIncome,l_MyPass) ::DECIMAL(15,2),0) AS VarIncomePYTD,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDVarIncomePYTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDVarIncomePYTD,
       -- Tunjangan PPH (Kolom2)
       COALESCE(QX11.Col2YTD,0) AS Kolom2,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncCol2YTD,l_MyPass) ::DECIMAL(15,2),0) AS Col2YTD,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDCol2YTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDCol2YTD,
       -- Lembur (Kolom3)
       COALESCE(QX7.Col3YTD,0) AS Kolom3,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncCol3YTD,l_MyPass) ::DECIMAL(15,2),0) AS Col3YTD,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDCol3YTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDCol3YTD,
       -- Honorarium (Kolom4)
       COALESCE(QX8.Col4YTD,0) AS Kolom4,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncCol4YTD,l_MyPass) ::DECIMAL(15,2),0) AS Col4YTD,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDCol4YTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDCol4YTD,
       -- Premi Asuransi (Kolom5)
       COALESCE(QX9.Col5YTD,0) AS Kolom5,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncCol5YTD,l_MyPass) ::DECIMAL(15,2),0) AS Col5YTD,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDCol5YTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDCol5YTD,
       -- Penerimaan Kenikmatan Lain (Kolom6) 
       COALESCE(QX10.Col6YTD,0) AS Kolom6,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncCol6YTD,l_MyPass) ::DECIMAL(15,2),0) AS Col6YTD,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDCol6YTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDCol6YTD,
       -- Iuran THT (Kolom12)
       COALESCE(QX3.Kolom12,0) AS Kolom12,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncKolom12,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(fn_KPusat(QX1.NIP,QX1.EncCol12PYTD,l_MyPass) ::DECIMAL(15,2),0) AS Col12PYTD,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDCol12PYTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDCol12PYTD,
       -- Bonus (Kolom8) 
       --Kolom8  =COALESCE(l_Bonus,0),     BY SUHE
       COALESCE(l_Bonus,0)-(COALESCE(fn_KPusat(QX1.NIP,QX1.EncKolom8,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(fn_KPusat(QX1.NIP,QX1.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDCol8PYTD,l_MyPass) ::DECIMAL(15,2),0)) AS Kolom8,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncKolom8,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(fn_KPusat(QX1.NIP,QX1.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2),0) AS Col8PYTD,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDCol8PYTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDCol8PYTD, 
--			   + COALESCE(fn_cariopbl('8',l_nip,l_TglProses,l_mypass),0), 
       -- OccSupport1
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2),0) AS OLDOccSupport1,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOccSupport1,l_MyPass) ::DECIMAL(15,2),0) AS OccSupport1PYTD, 
       -- OccSupport2
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2),0) AS OLDOccSupport2,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOccSupport2,l_MyPass) ::DECIMAL(15,2),0) AS OccSupport2PYTD,
       -- OldTaxAPaidNB
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOldIncTaxAPaidNB,l_MyPass) ::DECIMAL(15,2),0) AS OLDIncTaxAPaidNB,
       -- OldTaxAPaidB
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOldIncTaxAPaidB,l_MyPass) ::DECIMAL(15,2),0) AS OLDIncTaxAPaidB

--FROM S01HGAJ S01 
FROM V_HGA2 S01 
-- Inner ke Master Pegawai
INNER JOIN M15PEGA M15
ON M15.NIP=S01.NIP
--
--LEFT JOIN ke Pajak Sebelumnya 
LEFT JOIN 
(--QX1
SELECT S03.*
--FROM S03LTAX S03
FROM V_LTAX S03
--
INNER JOIN M15PEGA M15
ON M15.NIP=S03.NIP
--
WHERE S03.TglPayr=(Select Max(S.TglPayr) FROM V_LTAX S WHERE S.TglPayr<l_TglProses AND S.NIP=l_NIP) AND 
      S03.NIP=l_NIP AND EXTRACT(YEAR FROM S03.TglPayr)=EXTRACT(YEAR FROM l_TglProses)
	  AND S03.TglPayr >= M15.TglMasuk 
) QX1
ON QX1.NIP=S01.NIP

-- Left Join untuk Kolom 12 Iuran THT (Q3)
LEFT JOIN 
(--QX3
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Kolom12
FROM S02DGAJ S02
--
WHERE S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom='12'
--
GROUP BY S02.TglPayr,S02.NIP
) QX3
--
ON QX3.NIP=S01.NIP
--
--Left Join ke Pend.Fix 
LEFT JOIN 
(--QX5
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
FROM S02DGAJ S02
--
WHERE S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom='1'
--
GROUP BY S02.TglPayr,S02.NIP
) QX5
ON QX5.NIP=S01.NIP
--
--Left Join ke Variable Income 
LEFT JOIN 
(--QX6
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS VarIncome
FROM S02DGAJ S02
--
WHERE S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom BETWEEN '3' AND '6'
--
GROUP BY S02.TglPayr,S02.NIP
) QX6
ON QX6.NIP=S01.NIP
--
--Left Join ke Tunjagan Lainnya/Kolom 3
LEFT JOIN 
(--QX7
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Col3YTD
FROM S02DGAJ S02
--
WHERE S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom='3'
--
GROUP BY S02.TglPayr,S02.NIP
) QX7
ON QX7.NIP=S01.NIP
--
--Left Join ke Honorarium/Kolom4 
LEFT JOIN 
(--QX8
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Col4YTD
FROM S02DGAJ S02
--
WHERE S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom='4'
--
GROUP BY S02.TglPayr,S02.NIP
) QX8
ON QX8.NIP=S01.NIP
--
--Left Join ke Premi Asuransi yang dibayar Pegawai/Kolom5 
LEFT JOIN 
(--QX9
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Col5YTD
FROM S02DGAJ S02
--
WHERE S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom='5'
--
GROUP BY S02.TglPayr,S02.NIP
) QX9
ON QX9.NIP=S01.NIP
--
--Left Join ke Kolom6 
LEFT JOIN 
(--QX10
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Col6YTD
FROM S02DGAJ S02
--
WHERE S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom='6'
--
GROUP BY S02.TglPayr,S02.NIP
) QX10
ON QX10.NIP=S01.NIP
--
--Left Join Tunjangan Pajak 
LEFT JOIN 
(--QX11
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Col2YTD
FROM S02DGAJ S02
--
WHERE S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom='2'
--
GROUP BY S02.TglPayr,S02.NIP
) QX11
ON QX11.NIP=S01.NIP
---FILTERING Utama
WHERE S01.TglPayr=l_TglProses AND S01.NIP=l_NIP 
) QZZ
--
ON TBL6.NIP=QZZ.NIP

) QXX ; 

  -- RETURN
END ; 
$$ LANGUAGE plpgsql ;