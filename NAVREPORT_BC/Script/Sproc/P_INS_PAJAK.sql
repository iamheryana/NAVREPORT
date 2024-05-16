/****************************************
Name sprocs	: P_Ins_Pajak
Create by	: Wati
Date		: 16-04-2003
Description	: Untuk Masukkn Data S01, S02,S03,S0E dari perhitungan Pajak Gross
Call From	: Sub Routine
Sub sprocs	: -
*****************************************/
DROP FUNCTION P_Ins_Pajak(l_TglProses    DATE,
					l_NIP	      VARCHAR(10),
					l_JnsPeg      VARCHAR(1), 	
					l_FlagTHR     VARCHAR(1),
					l_Curr_Inc    DECIMAL(15,2),
					l_Curr_Kolom8 DECIMAL(15,2),
					l_UserID      VARCHAR(12), 	 
					l_MyPass      VARCHAR(128),
					l_P_Harian    VARCHAR(1),
					l_HKerja      DECIMAL(4,1),
					l_AkhirBln    INT,
					l_System      VARCHAR(1),
					l_S01_ID      INT);
--
CREATE FUNCTION P_Ins_Pajak(l_TglProses    DATE,
					l_NIP	      VARCHAR(10),
					l_JnsPeg      VARCHAR(1), 	
					l_FlagTHR     VARCHAR(1),
					l_Curr_Inc    DECIMAL(15,2),
					l_Curr_Kolom8 DECIMAL(15,2),
					l_UserID      VARCHAR(12), 	 
					l_MyPass      VARCHAR(128),
					l_P_Harian    VARCHAR(1),
					l_HKerja      DECIMAL(4,1),
					l_AkhirBln    INT,
					l_System      VARCHAR(1),
					l_S01_ID      INT)
RETURNS VOID
AS $$
--
/*
System	'G' =Gross, 'R'=Gross Up
*/

-- Parameter
DECLARE	l_TglPayr          DATE;        	l_KdCaba           VARCHAR(4);
	l_NewKdCaba        VARCHAR(4);         	l_OldKdCaba        VARCHAR(4);
	l_Tahun            DECIMAL(4);      	l_Bulan            VARCHAR(2);
	l_QDesc 	   VARCHAR(4000);  	l_QPara 	   VARCHAR(4000); 
	l_FixIncomePYTD    DECIMAL(15,2);   	l_OLDFixIncomePYTD DECIMAL(15,2);
	l_FixIncome        DECIMAL(15,2);   	l_VarIncome        DECIMAL(15,2);
	l_VarIncomePYTD    DECIMAL(15,2);   	l_OLDVarIncomePYTD DECIMAL(15,2);       
	l_Col2YTD          DECIMAL(15,2);   	l_OLDCol2YTD       DECIMAL(15,2);
	l_Col3YTD          DECIMAL(15,2);   	l_OLDCol3YTD       DECIMAL(15,2);
	l_Col4YTD          DECIMAL(15,2);   	l_OLDCol4YTD       DECIMAL(15,2);
	l_Col5YTD          DECIMAL(15,2);   	l_OLDCol5YTD       DECIMAL(15,2);
	l_Col6YTD          DECIMAL(15,2);   	l_OLDCol6YTD       DECIMAL(15,2);
	l_Kolom2           DECIMAL(15,2);   	l_Kolom3           DECIMAL(15,2);
	l_Kolom4           DECIMAL(15,2);   	l_Kolom5           DECIMAL(15,2);
	l_Kolom6           DECIMAL(15,2);
	l_Kolom12          DECIMAL(15,2);   	l_Col12PYTD        DECIMAL(15,2);
	l_OLDCol12PYTD     DECIMAL(15,2);   	l_OccSupport1      DECIMAL(15,2);
	l_OLDOccSupport1   DECIMAL(15,2);   	l_EGIYNB           DECIMAL(15,2);
	l_PTKP		   DECIMAL(15,2);   	l_EYTI	           DECIMAL(15,2);
	l_EYIT		   DECIMAL(15,2);   	l_YTDIT	           DECIMAL(15,2);
	l_IncTaxAPaidNB	   DECIMAL(15,2);   	l_OLDIncTaxAPaidNB DECIMAL(15,2);
	l_Kolom8           DECIMAL(15,2);   	l_Col8PYTD         DECIMAL(15,2);
	l_OLDCol8PYTD      DECIMAL(15,2);   	l_OccSupport2      DECIMAL(15,2);
	l_OLDOccSupport2   DECIMAL(15,2);   	l_EYITT            DECIMAL(15,2);
	l_OccSupport1PYTD  DECIMAL(15,2);   	l_OccSupport2PYTD  DECIMAL(15,2);
	l_IncTaxAPaidB     DECIMAL(15,2);   	l_OLDIncTaxAPaidB  DECIMAL(15,2);
	l_M03Singkatan     VARCHAR(10);	   	l_PoTPajak         DECIMAL(15,2);
	l_M03KdCurr	   VARCHAR(4);         	l_FZ1NlOccSupp     DECIMAL(15,2);
	l_FZ1PrOccSupp     DECIMAL(5,2);    	l_FZ1PTKPPay	   DECIMAL(15,2);	
	l_FZ1PTKPDep	   DECIMAL(15,2);   	l_GrsIncYTD	   DECIMAL(15,2);
	l_BlnProses        INT;         	l_JmlBulan         INT;
	l_PrdAwal          DATE;        	l_FZ2FlgCabang     VARCHAR(1);
	l_TaxAPaidUMP      DECIMAL(15,2);   	l_NilUMP           DECIMAL(15,2); 
	l_TaxUMP_PYTD      DECIMAL(15,2);   	l_FlgDLL           VARCHAR(1);
	l_TaxUMP           DECIMAL(15,2);   	l_Hari             INT;
	l_KdUMP            VARCHAR(4);         	l_FlgStruk         VARCHAR(1);
	l_TaxUMPYTD        DECIMAL(15,2);
	l_FZ1Kode	   VARCHAR(2);	
	l_FZ1MaxBruto 	   DECIMAL(15,2);   	l_FZ1PendDP        DECIMAL(15,2);
	l_ADAOPBL 	   DECIMAL(5,0);	l_TglMaxCabBeda	   DATE;
	l_TglProsesPertamaThnini DATE;  	l_BlnProsesOccSupp INT; 
	l_FlgDTPNPWP	   VARCHAR(1);   	l_FlgDTPTdkNPWP	   VARCHAR(1); 
	l_M15NPWP  	   VARCHAR(20);		l_IncDP2009	   DECIMAL(15,2); 
	l_DTPCURRMONTH	   DECIMAL(15,2);   	l_NUMNPWP	   DECIMAL(20,0); 
	l_xproses 	   INT;
	l_BLNKRG 	   INT;   
declare l_tglbfr DATE ;

BEGIN 
l_BLNKRG := 0;

-- Parameter LDA
SELECT NlOccSupp,      PrOccSupp,      PTKPPay,      PTKPDep,      Kode,      MaxBruto,      PendDP
INTO   l_FZ1NlOccSupp, l_FZ1PrOccSupp, l_FZ1PTKPPay, l_FZ1PTKPDep, l_FZ1Kode, l_FZ1MaxBruto, l_FZ1PendDP
FROM FZ1FLDA;

-- Flag Cabang
SELECT CASE SUBSTR(StringFlag,2,1) WHEN '1' THEN 'Y' ELSE 'T' END,
	SUBSTR(STRINGFLAG,12,1),
	SUBSTR(STRINGFLAG,13,1)
INTO l_FZ2FlgCabang, 
	l_FlgDTPNPWP, 
	l_FlgDTPTdkNPWP 
FROM FZ2FLDA
WHERE Kode=l_FZ1Kode;
--
SELECT TglMasuk,  NPWP 
INTO   l_PrdAwal, l_M15NPWP
FROM M15PEGA 
WHERE NIP=l_NIP;

--DTP 
IF l_FZ2FlgCabang='Y' THEN 
	SELECT M08.FlgDTPNPWP, M08.FlgDTPTdkNPWP
	INTO   l_FlgDTPNPWP,   l_FlgDTPTdkNPWP
	FROM M08HCAB M08
	INNER JOIN M15PEGA M15 
	ON M08.KdCaba = M15.KdCaba 
	WHERE M15.NIP = l_NIP ;
END IF; 
--

-- by peggy 2007 01 15 -- 
--
SELECT TglPosting
INTO l_Tglbfr
FROM S05PSTD
WHERE NIP=l_NIP AND FlagTHR=''
ORDER BY NIP,TglPosting ;  

-- by peggy 2009 10 06 --  COVERING 2X PROCESS UTK DTP 
--
SELECT COUNT(*)
INTO l_XPROSES
FROM S05PSTD
WHERE NIP=l_NIP AND 
	EXTRACT(YEAR FROM TGLPOSTING) = EXTRACT(YEAR FROM l_TglProses) AND 
	EXTRACT(MONTH FROM TGLPOSTING) = EXTRACT(MONTH FROM l_TglProses) ;
-- 
SELECT COALESCE(Fn_Kpusat(NIP,EncTaxUMP,l_MyPass) ::DECIMAL(15,2),0)
INTO l_DTPCURRMONTH
FROM S03LTAX 
WHERE NIP=l_NIP AND 
	EXTRACT(YEAR FROM TGLPAYR) = EXTRACT(YEAR FROM l_TglProses) AND 
	EXTRACT(MONTH FROM TGLPAYR) = EXTRACT(MONTH FROM l_TglProses) ;

/*----------------*/
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
	   l_BLNKRG    := 1;
	END;
     END IF; 
-- gak ada kolom 1 atau belum proses payroll           
     IF l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND (l_TglBfr IS NULL OR 
	EXTRACT(MONTH FROM l_TglProses) <> EXTRACT(MONTH FROM l_TglBfr))  THEN 
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
	   l_BlnProses := EXTRACT(MONTH FROM l_TglProses);
  	   l_JmlBulan  := 12;
	   l_BLNKRG    := 1;
	END;
     END IF; 
-- gak ada kolom 1 atau belum proses payroll       
     IF l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND (l_TglBfr IS NULL OR 
	EXTRACT(MONTH FROM l_TglProses) <> EXTRACT(MONTH FROM l_TglBfr))  THEN 
	BEGIN 
	   l_BlnProses := COALESCE(EXTRACT(MONTH FROM l_Tglbfr),EXTRACT(MONTH FROM l_TglProses));
	   l_JmlBulan  := 12;
	   l_BLNKRG    := 0;
	END;
      END IF;
   END;
 END IF; 
/*----------------*/
/*
-- Periode Penggajian
IF EXTRACT(YEAR FROM l_PrdAwal)=EXTRACT(YEAR FROM l_TglProses)
   BEGIN
-- BY PEGGY 2006 03 07 KALAU ADA OPBL PAJAK HARUS DIHITUNG FULL 12 BULAN DONG 
--    SET l_ADAOPBL = 0 
--    SELECT l_ADAOPBL = COUNT(*) FROM S03LTAX WHERE TGLPAYR <= l_TGLPROSES AND NIP = l_NIP 
--    IF l_ADAOPBL = 0 
--      BEGIN 
-- by peggy 2007 01 15 : kalau prosesnya hanya kolom8 aja dan belum proses payroll, 
-- 	jangan disetahunkan gross income, 
-- 	tapi kalau ada kolom8 atau sudah proses payroll, mekanisme sekarang udah betul 

-- ada kolom 1 atau sudah proses payroll 
     IF l_Curr_Inc <> 0 OR 
        (l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND l_TglBfr IS NOT NULL AND 
		EXTRACT(MONTH FROM l_TglProses) = EXTRACT(MONTH FROM l_TglBfr))  
	BEGIN 
	     SELECT l_BlnProses=(EXTRACT(MONTH FROM l_TglProses)-EXTRACT(MONTH FROM l_PrdAwal))+1,
	            l_JmlBulan =(12-(EXTRACT(MONTH FROM l_PrdAwal)))+1
	END 
-- gak ada kolom 1 atau belum proses payroll           
     IF l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND (l_TglBfr IS NULL OR 
		EXTRACT(MONTH FROM l_TglProses) <> EXTRACT(MONTH FROM l_TglBfr))  
	BEGIN 
	     SELECT l_BlnProses=(COALESCE(EXTRACT(MONTH FROM l_Tglbfr),EXTRACT(MONTH FROM l_prdawal))-EXTRACT(MONTH FROM l_PrdAwal))+1,
	            l_JmlBulan =(12-(EXTRACT(MONTH FROM l_PrdAwal)))+1
	END 
   END
ELSE
   BEGIN
-- by peggy 2007 01 15 : kalau prosesnya hanya kolom8 aja dan belum proses payroll, 
-- 	jangan disetahunkan gross income, 
-- 	tapi kalau ada kolom8 atau sudah proses payroll, mekanisme sekarang udah betul 

-- ada kolom 1 atau sudah proses payroll 
     IF l_Curr_Inc <> 0 OR 
        (l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND l_TglBfr IS NOT NULL AND 
		EXTRACT(MONTH FROM l_TglProses) = EXTRACT(MONTH FROM l_TglBfr))  
	begin 
	     SELECT l_BlnProses=EXTRACT(MONTH FROM l_TglProses),
	            l_JmlBulan =12
	end     
-- gak ada kolom 1 atau belum proses payroll       
     IF l_Curr_Inc = 0 AND l_FlagTHR<>' ' AND (l_TglBfr IS NULL OR 
		EXTRACT(MONTH FROM l_TglProses) <> EXTRACT(MONTH FROM l_TglBfr))  
	BEGIN 
	     SELECT l_BlnProses=COALESCE(EXTRACT(MONTH FROM l_Tglbfr),EXTRACT(MONTH FROM l_TglProses)),
	            l_JmlBulan =12
	END 
   END 
*/

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

-- -- tglproses = 20-11-2008, prdawal = 01-11-2008 
-- -- tglproses = 20-11-2008, proses pertama = 20-01-2008 
-- SELECT l_BlnProsesOccSupp=CASE WHEN l_FZ2FlgCabang = 'Y' THEN (EXTRACT(MONTH FROM l_TglProses) -
-- 						  CASE WHEN l_TglMaxCabBeda IS NOT NULL THEN 
-- 										EXTRACT(MONTH FROM l_TglMaxCabBeda) 
-- 							   WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal) = EXTRACT(YEAR FROM l_TglProses) THEN 
-- 										(EXTRACT(MONTH FROM l_PrdAwal) - 1)  -- 11 - (11 - 1)  = 1 
-- 							   WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal)<> EXTRACT(YEAR FROM l_TglProses) 
-- 									AND l_TglProsesPertamaThnini IS NOT NULL THEN 
-- 										(EXTRACT(MONTH FROM l_TglProsesPertamaThnini) - 1) -- 11 - (1 - 1)
-- 							   ELSE 
-- 									0
-- 						  END ) 
-- 						 ELSE 
-- 							l_BlnProses 
-- 						 END 

l_BlnProsesOccSupp := CASE WHEN l_FZ2FlgCabang = 'Y' THEN (EXTRACT(MONTH FROM l_TglProses) -
				  CASE WHEN l_TglMaxCabBeda IS NOT NULL THEN 
						EXTRACT(MONTH FROM l_TglMaxCabBeda) 
					   WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal) = EXTRACT(YEAR FROM l_TglProses) THEN 
						--(EXTRACT(MONTH FROM l_PrdAwal) - 1) -- sebelum edit tgl 19-08-2011
						(EXTRACT(MONTH FROM l_PrdAwal) - l_BLNKRG) 
					   WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal)<> EXTRACT(YEAR FROM l_TglProses) 
						AND l_TglProsesPertamaThnini IS NOT NULL THEN 
						--(EXTRACT(MONTH FROM l_TglProsesPertamaThnini) - 1)  
						(EXTRACT(MONTH FROM l_TglProsesPertamaThnini) - l_BLNKRG )  
					   ELSE 
						0
				  END)
				 ELSE 
					l_BlnProses 
				 END;
-- 
-- -- /* =========================================
-- -- -- sebelum edit 18-08-2011
-- SELECT l_BlnProsesOccSupp=CASE WHEN l_FZ2FlgCabang = 'Y' THEN (EXTRACT(MONTH FROM l_TglProses) -
-- 						  CASE WHEN l_TglMaxCabBeda IS NOT NULL THEN 
-- 										EXTRACT(MONTH FROM l_TglMaxCabBeda) 
-- 							   WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal) = EXTRACT(YEAR FROM l_TglProses) THEN 
-- 										(EXTRACT(MONTH FROM l_PrdAwal) - 1) 
-- 							   WHEN l_TglMaxCabBeda IS NULL AND EXTRACT(YEAR FROM l_PrdAwal)<> EXTRACT(YEAR FROM l_TglProses) 
-- 									AND l_TglProsesPertamaThnini IS NOT NULL THEN 
-- -- 										(EXTRACT(MONTH FROM l_TglProsesPertamaThnini) - 1)  
-- 										(EXTRACT(MONTH FROM l_TglProsesPertamaThnini) - l_BLNKRG )  
-- 							   ELSE 
-- 									0
-- 						  END)
-- 						 ELSE 
-- 							l_BlnProses 
-- 						 END 
-- -- 
-- -- ============================================ */
-- 

-- Main Data
SELECT QXX.TglPayr,
       QXX.NIP,
       QXX.KdCaba,
       COALESCE(QXX.NewKdCaba,' '),
       QXX.OldKdCaba,
       -- Gross Income YTD
       QXX.GIYTD,
       -- FixIncome  
	COALESCE(QXX.FixIncome,0),
	COALESCE(QXX.FixIncomePYTD,0),
	COALESCE(QXX.OLDFixIncomePYTD,0),
       -- VariIncome 
	COALESCE(QXX.VarIncome,0),
	COALESCE(QXX.VarIncomePYTD,0),
	COALESCE(QXX.OLDVarIncomePYTD,0),
       -- Kolom2
	COALESCE(QXX.Kolom2,0),
	COALESCE(QXX.Col2YTD,0),
	COALESCE(QXX.OLDCol2YTD,0),
       -- Kolom3 
	COALESCE(QXX.Kolom3,0),
	COALESCE(QXX.Col3YTD,0),
	COALESCE(QXX.OLDCol3YTD,0),
       -- Kolom4
	COALESCE(QXX.Kolom4,0),
	COALESCE(QXX.Col4YTD,0),
	COALESCE(QXX.OLDCol4YTD,0),
       -- Kolom5
	COALESCE(QXX.Kolom5,0),
	COALESCE(QXX.Col5YTD,0),
	COALESCE(QXX.OLDCol5YTD,0),
       -- Kolom6
	COALESCE(QXX.Kolom6,0),
	COALESCE(QXX.Col6YTD,0),
	COALESCE(QXX.OLDCol6YTD,0),
       -- Kolom12
	COALESCE(QXX.Kolom12,0),
	COALESCE(QXX.Col12PYTD,0),
	COALESCE(QXX.OLDCol12PYTD,0),
       -- Kolom8/Bonus
	COALESCE(QXX.Kolom8,0),
	COALESCE(QXX.Col8PYTD,0),
	COALESCE(QXX.OLDCol8PYTD,0),
       -- OccSupport1
	ROUND(COALESCE(QXX.OccSupport1,0), 0),
	COALESCE(QXX.OldOccSupport1,0),
	COALESCE(QXX.OccSupport1PYTD,0),
	
       -- OccSupport2
-- hasil edit OccSupport1PYTD dihapus karena membuat nilai jadi double
       CASE WHEN ROUND(COALESCE(QXX.OccSupport1,0),0)+COALESCE(QXX.OldOccSupport1,0) +
		   ((COALESCE(QXX.Kolom8,0) + COALESCE(QXX.Col8PYTD,0) + COALESCE(QXX.OLDCol8PYTD,0)) * l_FZ1PrOccSupp/100 ) > (l_BlnProses*l_FZ1NlOccSupp) 
	   THEN (ROUND(COALESCE(QXX.OccSupport1,0),0)+COALESCE(QXX.OldOccSupport1,0) - (l_BlnProses*l_FZ1NlOccSupp)) *-1 
	   ELSE ROUND(COALESCE(QXX.OccSupport2,0),0) END,

-- -- before edit 2011-07-04
--        l_OccSupport2      = CASE WHEN ROUND(COALESCE(QXX.OccSupport1,0),0)+COALESCE(QXX.OldOccSupport1,0)+COALESCE(QXX.OccSupport1PYTD,0) +
-- 						   ((COALESCE(QXX.Kolom8,0) + COALESCE(QXX.Col8PYTD,0) + COALESCE(QXX.OLDCol8PYTD,0)) * l_FZ1PrOccSupp/100 ) > (l_BlnProses*l_FZ1NlOccSupp) 
-- 						   THEN (ROUND(COALESCE(QXX.OccSupport1,0),0)+COALESCE(QXX.OldOccSupport1,0)+COALESCE(QXX.OccSupport1PYTD,0) + COALESCE(QXX.OccSupport2PYTD,0) - (l_BlnProses*l_FZ1NlOccSupp)) *-1 
-- 						   ELSE ROUND(COALESCE(QXX.OccSupport2,0),0) END,

        COALESCE(QXX.OldOccSupport2,0),
        COALESCE(QXX.OccSupport2PYTD,0),
       -- hasil Perhitungan
	COALESCE(QXX.EGIYNB,0),
	COALESCE(QXX.PTKP,0),
	COALESCE(QXX.EYTI,0),
	COALESCE(QXX.EYIT,0),
	COALESCE(QXX.YTDIT,0),
	COALESCE(QXX.EYITT,0),
       -- Tax Allready Paid Non Bonus 
	COALESCE(QXX.IncTaxAPaidNB,0),
	COALESCE(QXX.OLDIncTaxAPaidNB,0),
       -- Tax Allready Paid Bonus 
	COALESCE(QXX.LastIncTaxAPaidB,0),
	COALESCE(QXX.OLDIncTaxAPaidB,0)
INTO  l_TglPayr,
	l_NIP,
	l_KdCaba,
	l_NewKdCaba,
	l_OldKdCaba, 
	-- Gross Income YTD
	l_GrsIncYTD,
	-- FixIncome  
	l_FixIncome,
	l_FixIncomePYTD,
	l_OLDFixIncomePYTD,
	-- VariIncome 
	l_VarIncome,
	l_VarIncomePYTD,
	l_OLDVarIncomePYTD,
	-- Kolom2
	l_Kolom2,  
	l_Col2YTD,
	l_OLDCol2YTD,
	-- Kolom3 
	l_Kolom3,
	l_Col3YTD,
	l_OLDCol3YTD,
	-- Kolom4
	l_Kolom4,  
	l_Col4YTD,
	l_OLDCol4YTD,
	-- Kolom5
	l_Kolom5,  
	l_Col5YTD,
	l_OLDCol5YTD,
	-- Kolom6
	l_Kolom6,
	l_Col6YTD,
	l_OLDCol6YTD,
	-- Kolom12
	l_Kolom12,
	l_Col12PYTD,
	l_OLDCol12PYTD,
	-- Kolom8/Bonus
	l_Kolom8,
	l_Col8PYTD,
	l_OLDCol8PYTD,
	-- OccSupport1
	l_OccSupport1,
	l_OLDOccSupport1,
	l_OccSupport1PYTD,
	l_OccSupport2,
	l_OLDOccSupport2,
	l_OccSupport2PYTD,
	-- hasil Perhitungan
	l_EGIYNB,
	l_PTKP,
	l_EYTI,
	l_EYIT,
	l_YTDIT,
	l_EYITT,	
	-- Tax Allready Paid Non Bonus 
	l_IncTaxAPaidNB,
	l_OLDIncTaxAPaidNB,
	-- Tax Allready Paid Bonus 
	l_IncTaxAPaidB,
	l_OLDIncTaxAPaidB
FROM
( 
--
SELECT QZZ.*,  
       TBL5.EGIYNB,TBL5.YTDT,TBL5.EYIT,TBL5.EYTI,
       TBL5.BonusYTD,TBL5.OccSupport1,TBL5.OccSupport2,TBL5.PTKP,TBL5.GIYTD,
       TBL5.OLDOccSupport1 AS A, TBL5.OLDOccSupport2 AS B,
       TBL5.YTDIT,TBL5.EYTIT,TBL5.EYITT,
       TBL5.IncTaxAPaidNB,TBL5.LastIncTaxAPaidB,
       -- Yearly Income Tax Bonus
       TBL5.EYITT-TBL5.EYIT AS YITBns, 
       TBL5.PJKOPBL 
FROM
(
SELECT TBL4.NIP,TBL4.EGIYNB,TBL4.YTDT,TBL4.EYIT,TBL4.EYTI,
       TBL4.BonusYTD,TBL4.OccSupport1,TBL4.OccSupport2,TBL4.PTKP,TBL4.GIYTD,
       TBL4.OLDOccSupport1, TBL4.OLDOccSupport2,
       TBL4.YTDIT,TBL4.EYTIT,  
       TBL4.IncTaxAPaidNB,TBL4.LastIncTaxAPaidB,
       -- Estimate Yearly Income Tax Total
       CASE WHEN TBL4.EYTIT <=0 THEN 0
	  ELSE fn_GetTax(TBL4.NIP,FLOOR(TBL4.EYTIT/1000)*1000,'') END AS EYITT, 
       TBL4.PJKOPBL 
FROM
(
SELECT TBL3.NIP,TBL3.EGIYNB,TBL3.YTDT,TBL3.EYIT,TBL3.EYTI,
       TBL3.BonusYTD,TBL3.OccSupport1,TBL3.OccSupport2,
       TBL3.OLDOccSupport1, TBL3.OLDOccSupport2,
       TBL3.PTKP,TBL3.GIYTD,
       TBL3.IncTaxAPaidNB,TBL3.LastIncTaxAPaidB,

       -- Year To Date Income Tax
       ROUND(CASE WHEN TBL3.EYIT=0 THEN 0
-- modi untuk opbl 
--                  ELSE CASE WHEN TBL3.DiSetahunkan='Y' THEN (TBL3.EYIT*l_BlnProses)/l_JmlBulan
--P                  ELSE CASE WHEN TBL3.DiSetahunkan='Y' THEN ((TBL3.EYIT-TBL3.PJKNB)*l_BlnProsesO)/l_JmlBulanO
                  ELSE CASE WHEN TBL3.DiSetahunkan='Y' THEN ((TBL3.EYIT- TBL3.PJKOPBL)*l_BlnProses)/l_JmlBulan
                            ELSE TBL3.EYIT - TBL3.PJKOPBL END                       
             END,0) AS YTDIT,
       -- Estimate Yearly taxable Income Total
       (TBL3.EGIYNB+TBL3.BonusYTD)-TBL3.OccSupport2-TBL3.OLDOccSupport2-TBL3.PTKP AS EYTIT,
       TBL3.PJKOPBL 
FROM
(
SELECT TBL2.NIP,TBL2.DiSetahunkan,TBL2.EGIYNB,TBL2.YTDT,
       TBL2.BonusYTD,TBL2.OccSupport1,TBL2.OccSupport2,
       TBL2.OLDOccSupport1, TBL2.OLDOccSupport2,
       TBL2.PTKP,TBL2.GIYTD,
       TBL2.IncTaxAPaidNB,TBL2.LastIncTaxAPaidB,
	   -- modi opbl lagi 24 09 2008 
       -- Estimasi Yearly Taxable Income
       CASE WHEN (TBL2.EGIYNB-TBL2.PTKP) < 0 THEN 
		(FLOOR((ABS(TBL2.EGIYNB-TBL2.PTKP))/1000)*1000)*-1
	    ELSE FLOOR((TBL2.EGIYNB-TBL2.PTKP)/1000)*1000 END AS EYTI,
       -- Estimasi Yearly Income Tax
       CASE WHEN (TBL2.EGIYNB-TBL2.PTKP)<=0 THEN 0
            ELSE fn_GetTax(TBL2.NIP,FLOOR((TBL2.EGIYNB-TBL2.PTKP)/1000)*1000,'') END AS EYIT, 
       COALESCE(fn_cariopbl('19',l_nip,l_TglProses,l_mypass),0) AS PJKOPBL 
FROM
(
SELECT TBL1.TglPayr,TBL1.NIP,TBL1.KdCaba,
       TBL1.BonusYTD,TBL1.OccSupport1,TBL1.OccSupport2,
       TBL1.OLDOccSupport1, TBL1.OLDOccSupport2,
       TBL1.GIYTD,
       TBL1.IncTaxAPaidNB,TBL1.LastIncTaxAPaidB,
       -- Year To Date Total 
	   -- modi opbl : inc ytd ditambahin egiynb dari opbl 
	   -- modi opbl lagi 24 09 2008 : gak nambahin opbl di sini tapi pindah ke eyit 
--       YTDT=fn_cariopbl('16',l_nip,l_TglProses,l_mypass) + 
       TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD AS YTDT,

       -- Estimasi Gross Income Yearly Non Bonus
	   -- modi opbl : egiynb ditambahin egiynb dari opbl 
	   -- modi opbl lagi 24 09 2008 : gak nambahin opbl di sini tapi pindah ke eyit 
--       EGIYNB=fn_cariopbl('16',l_nip,l_TglProses,l_mypass) + 
       CASE WHEN TBL1.TglKeluar IS NOT NULL AND TBL1.TglKeluar=l_TglProses THEN
                 CASE WHEN (TBL1.FlagMati=1 OR TBL1.Kewarganegaraan<>'INDONESIA') THEN 
                      ((TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD)*l_JmlBulan)/l_BlnProses
                      ELSE TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD END
	 ELSE ((TBL1.GIYTD-TBL1.OccSupport1-TBL1.OLDOccSupport1+TBL1.Kolom12YTD)*l_JmlBulan)/l_BlnProses END  
	   + COALESCE(fn_cariopbl('16B',l_nip,l_TglProses,l_mypass),0) AS EGIYNB, 

       -- Flag diSetahunkan 
       CASE WHEN (TBL1.TglKeluar IS NOT NULL AND TBL1.TglKeluar=l_TglProses) THEN
		 CASE WHEN (TBL1.FlagMati=1 OR TBL1.Kewarganegaraan<>'INDONESIA') THEN 'Y'
		      ELSE 'T' END
		 ELSE 'Y' END AS DiSetahunkan,
       -- PTKP
       fn_GetPTKP(TBL1.NIP,TBL1.JnsKlmn,l_FZ1PTKPPay,l_FZ1PTKPDep,TBL1.StsPjk) AS PTKP
FROM
(
--
SELECT S01.TglPayr,S01.NIP,S01.KdCaba,M15.TglKeluar,
       M16.FlagMati,M15.Kewarganegaraan,M15.JnsKlmn,M15.StsPjk,

                          /*****************************
                            PERHITUNGAN PAJAK NON BONUS
                           ******************************/
       -- Gross Income YTD = Current Income + Income Sebelumnya + Income Pindah Cabang
       COALESCE(l_Curr_Inc,0)+COALESCE(Fn_Kpusat(Q1.NIP,Q1.EncFixIncomePYTD,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncFixIncome,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncVarIncome,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncVarIncomePYTD,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncOLDFixIncomePYTD,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncOLDVarIncomePYTD,l_MyPass) ::DECIMAL(15,2),0) AS GIYTD,

       -- Bonus YTD = Current Bonus + Bonus Sebelumnya + Bonus Pindah Cabang
       COALESCE(l_Curr_Kolom8,0)+	COALESCE(Fn_Kpusat(Q1.NIP,Q1.EncKolom8,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(Fn_Kpusat(Q1.NIP,Q1.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(Fn_Kpusat(Q1.NIP,Q1.EncOLDCol8PYTD,l_MyPass) ::DECIMAL(15,2),0) AS BonusYTD, 
--			   + COALESCE(fn_cariopbl('8',l_nip,l_TglProses,l_mypass),0), 


       -- OccSupport1
       CASE WHEN l_P_Harian<>'Y' THEN ROUND(CASE WHEN (((l_FZ1PrOccSupp * (COALESCE(l_Curr_Inc,0)+COALESCE(Q1.KomponenOccSupport,0)))/100)
--COALESCE(Fn_Kpusat(Q1.NIP,Q1.EncFixIncomePYTD,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncFixIncome,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncVarIncome,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncVarIncomePYTD,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncOLDFixIncomePYTD,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncOLDVarIncomePYTD,l_MyPass) ::DECIMAL(15,2),0)))/100)
                                   < l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
                              THEN ((l_FZ1PrOccSupp *(COALESCE(l_Curr_Inc,0)+COALESCE(Q1.KomponenOccSupport,0)))/100)
--COALESCE(Fn_Kpusat(Q1.NIP,Q1.EncFixIncomePYTD,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncFixIncome,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncVarIncome,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncVarIncomePYTD,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncOLDFixIncomePYTD,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncOLDVarIncomePYTD,l_MyPass) ::DECIMAL(15,2),0)))/100)
                              ELSE (l_BlnProsesOccSupp*l_FZ1NlOccSupp)  END, 0)
                        ELSE 0 END AS OccSupport1,

       -- OccSupport2
-- 		OccSupport2=CASE WHEN l_P_Harian<>'Y' THEN Q1.KomponenOccSupport2
-- 					else 0 end,
       CASE WHEN l_P_Harian<>'Y' THEN 
                        ROUND(CASE WHEN ((COALESCE(l_Curr_Inc,0)+COALESCE(Q1.KomponenOccSupport,0)
                                      +                           
				   (COALESCE(l_Curr_Kolom8,0)+COALESCE(Q1.KomponenOccSupport2,0))) * l_FZ1PrOccSupp)/100 
                                      <
                                     (l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
                              THEN 
                                   ((COALESCE(l_Curr_Inc,0)+COALESCE(Q1.KomponenOccSupport,0)
                                      +                           
				   (COALESCE(l_Curr_Kolom8,0)+COALESCE(Q1.KomponenOccSupport2,0))) * l_FZ1PrOccSupp)/100 
                                ELSE
                                     (l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
                              END,0)
                           -
                           ROUND((CASE WHEN ((l_FZ1PrOccSupp *(COALESCE(l_Curr_Inc,0)+COALESCE(Q1.KomponenOccSupport,0)))/100)
                                  <  
                                 (l_BlnProsesOccSupp*l_FZ1NlOccSupp)   
                               --  
                               THEN  
                                ((l_FZ1PrOccSupp *(COALESCE(l_Curr_Inc,0)+COALESCE(Q1.KomponenOccSupport,0)))/100)
                               ELSE                     
                                (l_BlnProsesOccSupp*l_FZ1NlOccSupp)    
                          END),0)
		ELSE 0 END AS OccSupport2,
	   COALESCE(Q1.xOLDOccSupport1,0) AS OLDOccSupport1, 
	   COALESCE(Q1.xOLDOccSupport2,0) AS OLDOccSupport2, 

       -- Iuran THT Year To date
       COALESCE(Fn_Kpusat(Q1.NIP,Q1.EncKolom12,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncCol12PYTD,l_MyPass) ::DECIMAL(15,2)+Fn_Kpusat(Q1.NIP,Q1.EncOLDCol12PYTD,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(Q3.Kolom12,0) AS Kolom12YTD,
       -- Tax Allready Paid   
       COALESCE(Fn_Kpusat(Q1.NIP,Q1.EncYTDIT,l_MyPass) ::DECIMAL(15,2),0) AS IncTaxAPaidNB,        
       -- Tax Allready Paid Bonus 
       COALESCE(Fn_Kpusat(Q1.NIP,Q1.EncEYITT,l_MyPass) ::DECIMAL(15,2)-Fn_Kpusat(Q1.NIP,Q1.EncEYIT,l_MyPass) ::DECIMAL(15,2),0) AS LastIncTaxAPaidB
--	   					+ COALESCE(fn_cariopbl('19B',l_nip,l_TglProses,l_mypass),0) 
-- -- -- 	   					+ CASE WHEN Q1.NIP IS NULL THEN 
-- -- -- 									COALESCE(fn_cariopbl('19B',l_nip,l_TglProses,l_mypass),0) 
-- -- -- 								ELSE 0 END 

--- SUMBER  DATA 
--
-- FROM S01HGAJ S01 
FROM V_HGA2 S01 
--LEFT JOIN ke Pajak Sebelumnya (Q1)
LEFT JOIN 
(
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
		END AS xOldOccSupport2,
	-- SYSTEM TIDAK PINDAH CABANG 
	CASE WHEN l_FZ2FlgCabang ='T' THEN 
			COALESCE(Fn_Kpusat(S03.NIP,S03.EncFixIncomePYTD,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncFixIncome,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncVarIncome,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncVarIncomePYTD,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncOLDFixIncomePYTD,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncOLDVarIncomePYTD,l_MyPass) ::DECIMAL(15,2),0)
	-- SYSTEM PINDAH CABANG DAN PINDAH PADA PROSES INI 
		 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba <> COALESCE(S03.KdCaba,M15.KdCaba) THEN 
			0
	-- SYSTEM PINDAH CABANG DAN TIDAK PINDAH CABANG PADA PROSES INI 
		 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba = COALESCE(S03.KdCaba,M15.KdCaba) THEN 
			COALESCE(Fn_Kpusat(S03.NIP,S03.EncFixIncomePYTD,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncFixIncome,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncVarIncome,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncVarIncomePYTD,l_MyPass) ::DECIMAL(15,2),0)
		END AS KomponenOccSupport,
	-- SYSTEM TIDAK PINDAH CABANG 
	CASE WHEN l_FZ2FlgCabang ='T' THEN 
			(COALESCE(Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncKolom8,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncOLDCol8PYTD,l_MyPass) ::DECIMAL(15,2),0))
	-- SYSTEM PINDAH CABANG DAN PINDAH PADA PROSES INI 
		 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba <> COALESCE(S03.KdCaba,M15.KdCaba) THEN 
			0
	-- SYSTEM PINDAH CABANG DAN TIDAK PINDAH CABANG PADA PROSES INI 
		 WHEN l_FZ2FlgCabang = 'Y' AND M15.KdCaba = COALESCE(S03.KdCaba,M15.KdCaba) THEN 
			(COALESCE(Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2)+
			Fn_Kpusat(S03.NIP,S03.EncKolom8,l_MyPass) ::DECIMAL(15,2),0))
		END AS KomponenOccSupport2
--FROM S03LTAX S03
FROM V_LTAX S03
--
INNER JOIN M15PEGA M15
ON M15.NIP=S03.NIP
--
WHERE S03.TglPayr=(Select Max(S.TglPayr) FROM V_LTAX S WHERE S.TglPayr<l_TglProses AND S.NIP=l_NIP) AND 
      S03.NIP=l_NIP AND EXTRACT(YEAR FROM S03.TglPayr)=EXTRACT(YEAR FROM l_TglProses) 
	  AND S03.TglPayr >= M15.TglMasuk -- ini supaya oldinctaxapaidnb gak keambil 
-- ini emang beda ama v_ltax yang di bawah karena kalau tgl masuk = tgl payroll gak keitung 
) Q1
ON Q1.NIP=S01.NIP

-- Left Join untuk Kolom 12 Iuran THT (Q3)
LEFT JOIN 
(
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Kolom12
FROM S02DGAJ S02
--
WHERE S02.TglPayr=l_TglProses AND S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom='12'
--
GROUP BY S02.TglPayr,S02.NIP
) Q3
--
ON Q3.NIP=S01.NIP
-- Inner ke Master Pegawai
INNER JOIN M15PEGA M15
ON M15.NIP=S01.NIP
--
-- INNER Join M16ALKL
LEFT JOIN M16ALKL M16
ON M16.Kode=M15.KdTerr
WHERE S01.TglPayr=l_TglProses AND S01.NIP=l_NIP 
--
) TBL1
--
) TBL2
--
) TBL3
--
) TBL4
--
) TBL5
--
LEFT JOIN 
--
( SELECT S01.TglPayr,S01.NIP,S01.KdCaba,
       COALESCE(M15.KdCaba,' ') AS NewKdCaba,
       CASE WHEN COALESCE(QX1.KdCaba,' ')=' ' THEN M15.KdCaba ELSE QX1.KdCaba END AS OldKdCaba,
       -- Fix Income
       COALESCE(QX5.FixIncome,0) AS FixIncome,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncFixIncomePYTD,l_MyPass) ::DECIMAL(15,2),0)+COALESCE(fn_KPusat(QX1.NIP,QX1.EncFixIncome,l_MyPass) ::DECIMAL(15,2),0) AS FixIncomePYTD,       
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDFixIncomePYTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDFixIncomePYTD,
       -- Var Income
       COALESCE(QX6.VarIncome,0) AS VarIncome,
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
       COALESCE(l_Curr_Kolom8,0) AS Kolom8,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncKolom8,l_MyPass) ::DECIMAL(15,2),0)
		+ COALESCE(fn_KPusat(QX1.NIP,QX1.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2),0) AS Col8PYTD,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDCol8PYTD,l_MyPass) ::DECIMAL(15,2),0) AS OLDCol8PYTD,
--			   + COALESCE(fn_cariopbl('8',l_nip,l_TglProses,l_mypass),0), --EDIT
       -- OccSupport1
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2),0) AS OLDOccSupport1,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOccSupport1,l_MyPass) ::DECIMAL(15,2),0)
		+ COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2),0) AS OccSupport1PYTD,
       -- OccSupport2
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2),0) AS OLDOccSupport2,
       COALESCE(fn_KPusat(QX1.NIP,QX1.EncOccSupport2,l_MyPass) ::DECIMAL(15,2),0)
		+ COALESCE(fn_KPusat(QX1.NIP,QX1.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2),0) AS OccSupport2PYTD,
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
(
SELECT S03.*
FROM V_LTAX S03
--
INNER JOIN M15PEGA M15
ON M15.NIP=S03.NIP
--
WHERE S03.TglPayr=(Select Max(S.TglPayr) FROM V_LTAX S WHERE S.TglPayr<l_TglProses AND S.NIP=l_NIP) AND 
      S03.NIP=l_NIP AND EXTRACT(YEAR FROM S03.TglPayr)=EXTRACT(YEAR FROM l_TglProses)
      AND S03.TglPayr >= M15.TglMasuk -- ini supaya oldinctaxapaidnb gak keambil 
-- tadinya > tapi kalau tgl masuk = tgl payroll gak keitung -- keliatannya bukan karena ini koqz 
) QX1
ON QX1.NIP=S01.NIP

-- Left Join untuk Kolom 12 Iuran THT (Q3)
LEFT JOIN 
(
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
(
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
(
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
(
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
(
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
(
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
(
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
(
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
ON TBL5.NIP=QZZ.NIP

) QXX ;

--
-- Call Pindah Cabang 
SELECT PPC.l_FixIncomePYTD    ,
	PPC.l_OLDFixIncomePYTD ,
	PPC.l_VarIncomePYTD    ,
	PPC.l_OLDVarIncomePYTD ,
	PPC.l_Col2YTD          ,
	PPC.l_OLDCol2YTD       ,
	PPC.l_Col3YTD          ,
	PPC.l_OLDCol3YTD       ,
	PPC.l_Col4YTD          ,
	PPC.l_OLDCol4YTD       ,
	PPC.l_Col5YTD          ,
	PPC.l_OLDCol5YTD       ,
	PPC.l_Col6YTD 	       ,
	PPC.l_OLDCol6YTD       ,
	PPC.l_Col12PYTD        ,
	PPC.l_OLDCol12PYTD     ,
	PPC.l_Col8PYTD 	       ,
	PPC.l_OLDCol8PYTD      ,
	PPC.l_OLDOccSupport1   ,
	PPC.l_OLDOccSupport2   ,
	PPC.l_IncTaxAPaidNB    ,
	PPC.l_OLDIncTaxAPaidNB ,
	PPC.l_IncTaxAPaidB     ,
	PPC.l_OLDIncTaxAPaidB  ,
	PPC.l_TaxUMPYTD   

		    
FROM P_Pindah_Cabang(l_NewKdCaba,
			l_OldKdCaba, 
			l_FZ2FlgCabang,
			-- FixIncome  
			l_FixIncome ,
			l_FixIncomePYTD,
			l_OLDFixIncomePYTD,
			-- VariIncome 
			l_VarIncome,
			l_VarIncomePYTD,
			l_OLDVarIncomePYTD,
			-- Kolom2
			l_Kolom2,  
			l_Col2YTD,
			l_OLDCol2YTD,
			-- Kolom3 
			l_Kolom3,
			l_Col3YTD,
			l_OLDCol3YTD,
			-- Kolom4
			l_Kolom4 ,  
			l_Col4YTD,
			l_OLDCol4YTD,
			-- Kolom5
			l_Kolom5,  
			l_Col5YTD,
			l_OLDCol5YTD,
			-- Kolom6
			l_Kolom6,
			l_Col6YTD,
			l_OLDCol6YTD,
			-- Kolom12
			l_Col12PYTD,
			l_OLDCol12PYTD,
			-- Kolom8/Bonus
			l_Col8PYTD,
			l_OLDCol8PYTD,
			-- OccSupport1
			l_OccSupport1PYTD, 
			l_OLDOccSupport1,
			-- OccSupport2
			l_OccSupport2PYTD,
			l_OLDOccSupport2,
			-- Tax Allready Paid Non Bonus 
			l_IncTaxAPaidNB,
			l_OLDIncTaxAPaidNB,
			-- Tax Allready Paid Bonus 
			l_IncTaxAPaidB,
			l_OLDIncTaxAPaidB,
			-- UMP
			l_TaxUMP,
			l_TaxAPaidUMP,
			l_TaxUMP_PYTD,  
			l_TaxUMPYTD
			) PPC
INTO l_FixIncomePYTD    ,
	l_OLDFixIncomePYTD ,
	l_VarIncomePYTD    ,
	l_OLDVarIncomePYTD ,
	l_Col2YTD          ,
	l_OLDCol2YTD       ,
	l_Col3YTD          ,
	l_OLDCol3YTD       ,
	l_Col4YTD          ,
	l_OLDCol4YTD       ,
	l_Col5YTD          ,
	l_OLDCol5YTD       ,
	l_Col6YTD 	       ,
	l_OLDCol6YTD       ,
	l_Col12PYTD        ,
	l_OLDCol12PYTD     ,
	l_Col8PYTD 	       ,
	l_OLDCol8PYTD      ,
	l_OLDOccSupport1   ,
	l_OLDOccSupport2   ,
	l_IncTaxAPaidNB    ,
	l_OLDIncTaxAPaidNB ,
	l_IncTaxAPaidB     ,
	l_OLDIncTaxAPaidB  ,
	l_TaxUMPYTD 	; 				

--by suhe	
l_PotPajak=(l_YTDIT-l_IncTaxAPaidNB-l_OLDIncTaxAPaidNB)+(l_EYITT-l_EYIT-l_IncTaxAPaidB-l_OLDIncTaxAPaidB);

/*
-- Hitung Nilai UMP
-- Jika Periode 06/2003 maka pajakDP dasar UMP
IF CONVERT(VARCHAR(6),l_TglProses,112)<'200307'
   BEGIN
	 EXEC P_Hitung_UMP 	l_TglProses,
    		  			l_NIP,
                  		l_PTKP, 
                  		l_P_Harian,
                  		l_HKerja,
                  		l_PotPajak,
                  		l_AkhirBln,
                  		l_TaxAPaidUMP OUTPUT,
                  		l_NilUMP      OUTPUT,
                  		l_TaxUMP_PYTD OUTPUT,
                  		l_FlgDLL      OUTPUT,
                  		l_TaxUMP      OUTPUT,
                  		l_Hari        OUTPUT,       
                  		l_KdUMP       OUTPUT,
                  		l_FlgStruk    OUTPUT,                  
		  				l_MyPass

   END
ELSE -- Jika Bulan Juli'03 berlaku PajakDP dasar Pendapatan DP dsn Max Bruto
   BEGIN
      EXEC P_Hitung_UMP2	l_TglProses,
    		        		l_NIP,
                    		l_PTKP,
                    		l_P_Harian,
                    		l_HKerja,
                    		l_PotPajak,
                    		l_AkhirBln,
                    		l_TaxAPaidUMP OUTPUT,
                    		l_TaxUMP_PYTD OUTPUT,
                    		l_FlgDLL      OUTPUT,
                    		l_TaxUMP      OUTPUT,
                    		l_Hari        OUTPUT,   
							l_FZ1MaxBruto ,
							l_FZ1PendDP   ,
			 	    		l_MyPass

   END
--*
--Jika Pindah Cabang (UMP)
       SELECT l_TaxUMPYTD       = CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') 
                                   THEN (COALESCE(l_TaxUMP,0)-COALESCE(l_TaxAPaidUMP,0))
                                 ELSE 
                                   (COALESCE(l_TaxUMP_PYTD,0)+(COALESCE(l_TaxUMP,0)-COALESCE(l_TaxAPaidUMP,0))) 
                                 END 
---

-- Nilai Potongan Pajak Dipotong dengan UMP-nya by Suhe 15-08-2003
   SET  l_PotPajak=fn_Vround(l_PotPajak-(COALESCE(l_TaxUMP,0)-COALESCE(l_TaxAPaidUMP,0)))

*/
SELECT SUM(COALESCE(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2),0))
INTO l_IncDP2009
FROM S02DGAJ S02
--
WHERE DATE_TRUNC('month',l_TglProses) = DATE_TRUNC('month',S02.TglPayr) AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom BETWEEN '1' AND '8' ; 
--
l_NUMNPWP := CASE WHEN TRIM(BOTH FROM l_M15NPWP) ~ '^[0-9]+$' = TRUE THEN 
			TRIM(BOTH FROM l_M15NPWP) ::DECIMAL(20,0) 
		ELSE 
			0 
		END ;
--
--IF CONVERT(VARCHAR(6),l_TglProses,112) BETWEEN '200902' AND '200911' AND 
IF	((l_FlgDTPNPWP = 'Y' AND l_NUMNPWP <> 0) OR  -- DTPNPWP = Y DAN NIP ADA NPWP 
	(l_FlgDTPTdkNPWP = 'Y' AND l_NUMNPWP = 0)) -- DTPTdkNPWP = Y DAN NIP tidak ADA NPWP  
	AND 
--	(l_IncDP2009 <= l_FZ1MaxBruto) AND 
-- ADA PAJAK ATAU PROSES > 1 DALAM 1 BULAN 
	(l_PotPajak > 0 OR (l_PotPajak <= 0 AND l_XPROSES > 0 AND l_DTPCURRMONTH > 0))  THEN 
   BEGIN

	SELECT FX.l_TaxAPaidUMP, FX.l_TAXUMP, FX.l_TAXUMPYTD
	INTO   l_TaxAPaidUMP,    l_TAXUMP,    l_TAXUMPYTD
	FROM
	(
	 SELECT P_Hitung_UMP2009(l_TglProses,
				l_NIP,
				l_M15NPWP,
				l_FlgDTPNPWP, 
				l_FlgDTPTdkNPWP, 
				l_PotPajak,
				l_IncDP2009,
				l_FZ1MaxBruto,
				l_MyPass,
				l_S01_ID) AS FX 
	) AS FXX ; 
   END;
END IF; 
-- 
IF (l_FZ2FlgCabang ='T')  THEN 
   BEGIN
	SELECT COALESCE(Q.DP,0) 
	INTO l_TAXUMPYTD
	FROM
	(
	SELECT fn_KPusat(S03.NIP,S03.EncTaxUMPYTD,l_MyPass) ::DECIMAL(15,2) AS DP 
	FROM S03LTAX S03
	INNER JOIN 
	(
	SELECT S03.NIP, MAX(S03.TglPayr) AS TglPayr
	FROM S03LTAX S03 
	WHERE S03.NIP=l_NIP AND l_TglProses > S03.TglPayr AND EXTRACT(YEAR FROM l_TglProses) = EXTRACT(YEAR FROM S03.TglPayr) 
	GROUP BY S03.NIP 
	) X03 
	ON S03.NIP = X03.NIP AND S03.TglPayr = X03.TglPayr 
	) Q ;
   END;
END IF; 
IF (l_FZ2FlgCabang ='Y') THEN 
   BEGIN
	SELECT COALESCE(Q.DP,0)
	INTO l_TAXUMPYTD
	FROM
	(
	SELECT fn_KPusat(S03.NIP,S03.EncTaxUMPYTD,l_MyPass) ::DECIMAL(15,2) AS DP 
	FROM S03LTAX S03
	INNER JOIN 
	(
	SELECT S03.NIP, MAX(S03.TglPayr) AS TglPayr
	FROM S03LTAX S03 
	WHERE S03.NIP=l_NIP AND l_TglProses > S03.TglPayr AND EXTRACT(YEAR FROM l_TglProses) = EXTRACT(YEAR FROM S03.TglPayr) 
		AND S03.KDCABA = l_NEWKDCABA 
	GROUP BY S03.NIP 
	) X03 
	ON S03.NIP = X03.NIP AND S03.TglPayr = X03.TglPayr 
	WHERE S03.KDCABA = l_NEWKDCABA 
	) Q ;
   END;
END IF ; 
--Jika Pindah Cabang (UMP)
--       SELECT l_TaxUMPYTD = COALESCE(l_TaxUMPYTD,0) + COALESCE(l_TaxUMP,0)
   l_TaxUMPYTD := COALESCE(l_TaxUMPYTD,0)  + COALESCE(l_TaxUMP,0) - COALESCE(l_TaxAPaidUMP,0);

-- Insert Record 
   --
   DELETE FROM S03LTAX
   WHERE NIP=l_NIP AND TglPayr=l_TglPayr;
   --
-- menambahkan value 0 untuk field yang gak pake Enc- dan menambahkan Enc untuk TotPot
   INSERT INTO S03LTAX(TglPayr, NIP, KdCaba, NilUMP, TaxUMP, TaxAPaidUMP, TaxUMPYTD, MaxBruto, PendDP,
		       EncFixIncome, EncFixIncomePYTD, EncOLDFixIncomePYTD, EncVarIncome, EncVarIncomePYTD, 
                       EncOLDVarIncomePYTD, EncCol2YTD, EncOLDCol2YTD, EncCol3YTD, EncOLDCol3YTD, EncCol4YTD, EncOLDCol4YTD, EncCol5YTD,
                       EncOLDCol5YTD, EncCol6YTD, EncOLDCol6YTD, EncKolom12, EncCol12PYTD, EncOLDCol12PYTD, EncOccSupport1, EncOLDOccSupport1,
                       EncEGIYNB, EncPTKP, EncEYTI, EncEYIT, EncYTDIT, EncIncTaxAPaidNB, EncOLDIncTaxAPaidNB, EncKolom8, EncCol8PYTD, EncOLDCol8PYTD,
                       EncOccSupport2, EncOLDOccSupport2, EncEYITT, EncIncTaxAPaidB, EncOLDIncTaxAPaidB, EncTotPot, FlagTHR,
                       Harian, FlgStruk, FlgPdLL, kdUMP, EncNilUMP, EncTaxUMP, EncTaxAPaidUMP, EncTaxUMPYTD, Hari, EncMaxBruto, EncPendDP,
                       VERSION, Created_by,  Created_On,  WS)
               VALUES (l_TglPayr, l_NIP, l_KdCaba, 0, 0, 0, 0, 0, 0, fn_kCabang(l_NIP,CAST(l_FixIncome as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_FixIncomePYTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDFixIncomePYTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_VarIncome as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_VarIncomePYTD as VARCHAR(17)),l_MyPass),
                       fn_kCabang(l_NIP,CAST(l_OLDVarIncomePYTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_Col2YTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDCol2YTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_Col3YTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDCol3YTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_Col4YTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDCol4YTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_Col5YTD as VARCHAR(17)),l_MyPass),
                       fn_kCabang(l_NIP,CAST(l_OLDCol5YTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_Col6YTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDCol6YTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_Kolom12 as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_Col12PYTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDCol12PYTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OccSupport1 as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDOccSupport1 as VARCHAR(17)),l_MyPass),
                       fn_kCabang(l_NIP,CAST(l_EGIYNB as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_PTKP as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_EYTI as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_EYIT as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_YTDIT as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_IncTaxAPaidNB as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDIncTaxAPaidNB as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_Kolom8 as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_Col8PYTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDCol8PYTD as VARCHAR(17)),l_MyPass),
                       fn_kCabang(l_NIP,CAST(l_OccSupport2 as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDOccSupport2 as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_EYITT as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_IncTaxAPaidB as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_OLDIncTaxAPaidB as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,'0',l_MyPass), l_FlagTHR ,
                       l_P_Harian, l_FlgStruk, l_FlgDLL, l_KdUMP, fn_kCabang(l_NIP,CAST(l_NilUMP as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_taxUMP as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_TaxAPaidUMP as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_TaxUMPYTD as VARCHAR(17)),l_MyPass), l_Hari, fn_kCabang(l_NIP,CAST(l_FZ1MaxBruto as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_FZ1PendDP as VARCHAR(17)),l_MyPass), 
                       0,     l_UserID,      CURRENT_DATE, ' ');

   -- Upd. S01HGAJ 
   UPDATE S01HGAJ
   SET EncGrossIncomeNBYTD = fn_kCabang(l_NIP,(l_GrsIncYTD :: VARCHAR(17)),l_MyPass),
       EncOccSupport1      = fn_kCabang(l_NIP,(l_OccSupport1 :: VARCHAR(17)),l_MyPass),
       EncCol12YTD         = fn_kCabang(l_NIP,((l_Col12PYTD+l_Kolom12+l_OldCol12PYTD) :: VARCHAR(17)),l_MyPass),
       EncEGIYNB           = fn_kCabang(l_NIP,(l_EGIYNB :: VARCHAR(17)),l_MyPass),
       EncPTKP             = fn_kCabang(l_NIP,(l_PTKP :: VARCHAR(17)),l_MyPass),
       EncEYTI             = fn_kCabang(l_NIP,(l_EYTI :: VARCHAR(17)),l_MyPass),
       EncEYIT             = fn_kCabang(l_NIP,(l_EYIT :: VARCHAR(17)),l_MyPass),
       EncYTDIT            = fn_kCabang(l_NIP,(l_YTDIT :: VARCHAR(17)),l_MyPass),
       EncIncTaxAPaidNB    = fn_kCabang(l_NIP,((l_IncTaxAPaidNB+l_OLDIncTaxAPaidNB) :: VARCHAR(17)),l_MyPass),
       EncGrossIncomeBYTD  = fn_kCabang(l_NIP,((l_Kolom8+l_Col8PYTD+l_OldCol8PYTD) :: VARCHAR(17)),l_MyPass),
       EncOccSupport2      = fn_kCabang(l_NIP,(l_OccSupport2 :: VARCHAR(17)),l_MyPass),
       EncTaxTotal         = fn_kCabang(l_NIP,(l_EYITT :: VARCHAR(17)),l_MyPass),
       EncIncTaxAPaidB     = fn_kCabang(l_NIP,((l_IncTaxAPaidB+l_OLDIncTaxAPaidB) :: VARCHAR(17)),l_MyPass)
   WHERE NIP=l_NIP AND TglPayr=l_TglPayr;

   -- S02DGAJ
   -- Jika GROSS Maka Isi ke S02 Hanya 'D' - 'PJK' 
   -- Ambil singkatan dan currenci dr M03DPPT
      --
      SELECT SkDpPt,         KdCurr  
      INTO   l_M03Singkatan, l_M03KdCurr
      FROM M03DPPT
      WHERE FlgDpPt='P' AND KdDpPt='PJK';
      --
      DELETE FROM S02DGAJ
      WHERE TglPayr=l_TglPayr AND NIP=l_NIP AND FlgDpPt='P' AND KdDpPt='PJK';
      --
      INSERT INTO S02DGAJ(TglPayr,  NIP,  FlgDpPt,KdDpPt,FlgAngs,Singkatan,    Nilai,KdCurr,NilaiVal,Kolom,NoAcc,UsComp,NoLyr,FlgNonGL,EncNilai,                                            EncNilaiVal,                                        S01_ID)
                  VALUES (l_TglPayr,l_NIP,'P',    'PJK', ' ',    l_M03Singkatan,0,   'IDR' ,0,       ' ',  ' ',  'K',   0,    0,       fn_KCabang(l_NIP,l_PotPajak :: VARCHAR(17),l_MyPass),fn_KCabang(l_NIP,l_PotPajak :: VARCHAR(17),l_MyPass),l_S01_ID);

  -- Jika Gross Up harus ditimbulkan Potongan Pajak dibayar perusahaan
  IF l_System='R' THEN 
     BEGIN
      SELECT SkDpPt,         KdCurr  
      INTO   l_M03Singkatan, l_M03KdCurr
      FROM M03DPPT
      WHERE FlgDpPt='P' AND KdDpPt='RPJK';

      -- Nilainya di Reverse dulu 
      l_PotPajak := l_PotPajak*-1;
      --
      DELETE FROM S02DGAJ
      WHERE TglPayr=l_TglPayr AND NIP=l_NIP AND FlgDpPt='P' AND KdDpPt='RPJK';
      --
      INSERT INTO S02DGAJ(TglPayr,  NIP,  FlgDpPt,KdDpPt,FlgAngs,Singkatan,    Nilai,KdCurr,NilaiVal,Kolom,NoAcc,UsComp,NoLyr,FlgNonGL,EncNilai,                                            EncNilaiVal,                                       S01_ID)
                  VALUES (l_TglPayr,l_NIP,'P',    'RPJK', ' ',   l_M03Singkatan,0,   'IDR' ,0,       ' ',  ' ',  'K',   0,    0,       fn_KCabang(l_NIP,l_PotPajak ::VARCHAR(17),l_MyPass),fn_KCabang(l_NIP,l_PotPajak::VARCHAR(17),l_MyPass),l_S01_ID);
       
     END;
  END IF; 

  --* 
END;

$$ LANGUAGE plpgsql ;

/*
Declare l_Tpajak DECIMAL(15,2),
        l_Err1   INT
SELECT l_TPajak=0,
       l_Err1=0 
EXEC P_Ins_Pajak l_TglProses   ='2003-01-20',
    		 l_NIP	      = '05',
		 l_JnsPajak    = 'G', 	
		 l_FlagTHR     = '0',
                 l_Curr_Inc    = 3000000,
                 l_Curr_Kolom8 = 0,
                 l_PotPajak    = l_TPajak OUTPUT,
                 l_FlagUpd     = 'Y',  
		 l_UserID      = 'Wati', 	 
		 l_MyPass      = 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',
                 l_Gagal       = l_Err1 OUTPUT  
*/
