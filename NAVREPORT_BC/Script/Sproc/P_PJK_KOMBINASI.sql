/****************************************
Name sprocs	: P_Pjk_Kombinasi
Create by	: Wati
Date		: 08-05-2003
Description	: Untuk Masukkan Data S01, S02,S03,S0E dari perhitungan Pajak Kombinasi
Call From	: Sub Routine
Sub sprocs	: -
*****************************************/
CREATE OR REPLACE FUNCTION P_Pjk_Kombinasi(l_TglProses   DATE,
					    l_NIP         VARCHAR(10),  
					    l_JnsPeg      VARCHAR(1), 
					    l_FlagTHR     VARCHAR(1),
					    l_UserID      VARCHAR(12),
		  		            l_MyPass      VARCHAR(128),
					    l_P_Harian    VARCHAR(1),
					    l_HKerja      DECIMAL(4,1),
					    l_AkhirBln    INT, 
					    l_S01_ID      INT)
RETURNS VOID 
AS $$
-- Parameter
DECLARE l_TglPayr          DATE;            l_KdCaba           VARCHAR(4);
        l_NewKdCaba    	   VARCHAR(4);      l_OldKdCaba        VARCHAR(4);
        l_GIYTDG       	   DECIMAL(15,2);   l_GIYTDNB          DECIMAL(15,2);
        l_BonusYTDG    	   DECIMAL(15,2);   l_BonusYTDB        DECIMAL(15,2);
        l_Kolom12YTDG      DECIMAL(15,2);   l_Kolom12YTDNB     DECIMAL(15,2);
        l_PajakGross       DECIMAL(15,2);   l_FZ1IntvPajak     DECIMAL(9,0);
        l_KaliLoop         INT;		    l_GIYTDTotal       DECIMAL(15,2);   
        l_Pot_Pajak        DECIMAL(15,2);   l_Pot_Pajak_Old    DECIMAL(15,2);
        l_Selisih          DECIMAL(15,2);   l_PotPajak         DECIMAL(15,2);
        l_Tunjangan_PjkNB  DECIMAL(15,2);   l_Tunjangan_PjkB   DECIMAL(15,2);
        l_Tunjangan_Total  DECIMAL(15,2);   l_Pajak_Gross      DECIMAL(15,2);
        l_FixIncome        DECIMAL(15,2);   l_VarIncome        DECIMAL(15,2);
        l_FixIncomePYTD    DECIMAL(15,2);   l_OLDFixIncomePYTD DECIMAL(15,2);
        l_VarIncomePYTD    DECIMAL(15,2);   l_OLDVarIncomePYTD DECIMAL(15,2);       
        l_Col2YTD          DECIMAL(15,2);   l_OLDCol2YTD       DECIMAL(15,2);
        l_Col3YTD          DECIMAL(15,2);   l_OLDCol3YTD       DECIMAL(15,2);
        l_Col4YTD          DECIMAL(15,2);   l_OLDCol4YTD       DECIMAL(15,2);
        l_Col5YTD          DECIMAL(15,2);   l_OLDCol5YTD       DECIMAL(15,2);
        l_Col6YTD          DECIMAL(15,2);   l_OLDCol6YTD       DECIMAL(15,2);
        l_Kolom2           DECIMAL(15,2);   l_Kolom3           DECIMAL(15,2);
        l_Kolom4           DECIMAL(15,2);   l_Kolom5           DECIMAL(15,2);
        l_Kolom6           DECIMAL(15,2);
        l_Kolom12          DECIMAL(15,2);   l_Col12PYTD        DECIMAL(15,2);
        l_OLDCol12PYTD     DECIMAL(15,2);   l_OccSupport1      DECIMAL(15,2);
        l_OLDOccSupport1   DECIMAL(15,2);   l_EGIYNB           DECIMAL(15,2);
        l_PTKP	  	   DECIMAL(15,2);   l_EYTI	       DECIMAL(15,2);
        l_EYIT	  	   DECIMAL(15,2);   l_YTDIT	       DECIMAL(15,2);
        l_IncTaxAPaidNB	   DECIMAL(15,2);   l_OLDIncTaxAPaidNB DECIMAL(15,2);
        l_Kolom8           DECIMAL(15,2);   l_Col8PYTD         DECIMAL(15,2);
        l_OLDCol8PYTD      DECIMAL(15,2);   l_OccSupport2      DECIMAL(15,2);
        l_OLDOccSupport2   DECIMAL(15,2);   l_EYITT            DECIMAL(15,2);
        l_OccSupport1PYTD  DECIMAL(15,2);   l_OccSupport2PYTD  DECIMAL(15,2);
        l_IncTaxAPaidB     DECIMAL(15,2);   l_OLDIncTaxAPaidB  DECIMAL(15,2);
        l_M03Singkatan     VARCHAR(10);
	l_M03KdCurr	   VARCHAR(4);      l_FZ1NlOccSupp     DECIMAL(15,2);
	l_FZ1PrOccSupp     DECIMAL(5,2);    l_FZ1PTKPPay       DECIMAL(15,2);	
	l_FZ1PTKPDep	   DECIMAL(15,2);   l_GrsIncYTD	       DECIMAL(15,2);
        l_CurrTaxNB        DECIMAL(15,2);   l_CurrTaxB         DECIMAL(15,2);
        l_YITBonus         DECIMAL(15,2);   
        l_FZ2FlgCabang     VARCHAR(1);    
        l_TunjanganYTD     DECIMAL(15,2);   l_TunjanganCurr    DECIMAL(15,2);
        l_Kolom2_NB        DECIMAL(15,2);   l_Kolom2_B         DECIMAL(15,2);
        l_TaxAPaidUMP      DECIMAL(15,2);   l_NilUMP           DECIMAL(15,2); 
        l_TaxUMP_PYTD      DECIMAL(15,2);   l_FlgDLL           VARCHAR(1);
        l_TaxUMP           DECIMAL(15,2);   l_Hari             INT;
        l_KdUMP            VARCHAR(4);      l_FlgStruk         VARCHAR(1);
        l_TaxUMPYTD        DECIMAL(15,2);
	l_FZ1MaxBruto	   DECIMAL(15,2);   l_FZ1PendDP        DECIMAL(15,2);
	l_PjkPrsh	   DECIMAL(15,2);   l_PjkGross	       DECIMAL(15,2);
	l_PotPajakNew      DECIMAL(15,2);
        l_CurrIncome       DECIMAL(15,2);
	l_KOccSuppG	   DECIMAL(15,2);   l_KOccSuppN	       DECIMAL(15,2); 
	l_KOccSupp2G	   DECIMAL(15,2);   l_KOccSupp2N       DECIMAL(15,2); 
	l_M15KdCaba	   VARCHAR(4); 	    l_TglMaxCabBeda    DATE;
	l_TglProsesPertamaThnini DATE; 
	l_KomponenOccSupport1 DECIMAL(15,2); 
	l_KomponenOccSupport2 DECIMAL(15,2); 
	l_FZ1Kode	   VARCHAR(2);	    l_PrdAwal	       DATE; 		
	l_FlgDTPNPWP	   VARCHAR(1);      l_FlgDTPTdkNPWP    VARCHAR(1); 
	l_M15NPWP 	   VARCHAR(20);	    l_IncDP2009	       DECIMAL(15,2); 
	l_DTPCURRMONTH	   DECIMAL(15,2);   l_NUMNPWP	       DECIMAL(20,0);
	l_xproses 	   INT;		    l_ADAOPBL	       DECIMAL(5);		
DECLARE l_S03COL2YTD DECIMAL(15,2) ;
--
BEGIN 
  SELECT IntvPajak,      MaxBruto,      PendDp,      Kode
  INTO   l_FZ1IntvPajak, l_FZ1MaxBruto, l_FZ1PendDP, l_FZ1Kode
  FROM FZ1FLDA ;   
--
l_ADAOPBL := 0; 
SELECT COUNT(*) 
INTO l_ADAOPBL
FROM T25OPBP 
WHERE NIP=l_NIP AND EXTRACT(YEAR FROM TANGGAL)=EXTRACT(YEAR FROM l_TglProses);
--
-- Flag Cabang
SELECT CASE SUBSTR(StringFlag,2,1) WHEN '1' THEN 'Y' ELSE 'T' END,
	SUBSTR(STRINGFLAG,12,1),
	SUBSTR(STRINGFLAG,13,1)
INTO   l_FZ2FlgCabang, 
       l_FlgDTPNPWP, 
       l_FlgDTPTdkNPWP
FROM FZ2FLDA
WHERE Kode=l_FZ1Kode ; 

-- Kode Cabang
SELECT KdCaba,      TglMasuk,  NPWP 
INTO   l_M15KdCaba, l_PrdAwal, l_M15NPWP
FROM M15PEGA 
WHERE NIP =l_NIP ;

-- CARI TGL CAB BEDA SEBELUM CABANG SEKARANG
SELECT MAX(S01.TGLPAYR) 
INTO l_TglMaxCabBeda 
FROM S01HGAJ S01 
INNER JOIN M15PEGA M15
ON S01.NIP = M15.NIP 
WHERE S01.KDCABA <> M15.KDCABA AND M15.NIP = l_NIP AND 
	EXTRACT(YEAR FROM S01.TGLPAYR) = EXTRACT(YEAR FROM l_TglProses) ;
--
SELECT MIN(S01.TGLPAYR) 
INTO l_TglProsesPertamaThnini
FROM S01HGAJ S01 
WHERE S01.NIP = l_NIP AND EXTRACT(YEAR FROM S01.TGLPAYR) = EXTRACT(YEAR FROM l_TglProses) ;
--
-- cari old k

-- cari old k
IF l_TglMaxCabBeda IS NOT NULL THEN 
BEGIN 
	SELECT fn_KPusat(NIP,EncCol2YTD,l_MyPass) ::DECIMAL(15,2) + 
	       fn_KPusat(NIP,EncOLDCol2YTD,l_MyPass) ::DECIMAL(15,2) 
	INTO l_S03Col2YTD
	FROM S03LTAX 
	WHERE TglPayr <= l_TglMaxCabBeda AND NIP=l_NIP AND EXTRACT(YEAR FROM TglPayr) = EXTRACT(YEAR FROM l_TglProses) 
	ORDER BY TglPayr DESC LIMIT 1 ;
END ; 
--
ELSE 
BEGIN 
	l_S03Col2YTD := 0 ; 
END ;
END IF ; 
--
l_TglMaxCabBeda := CASE WHEN l_FZ2FlgCabang = 'Y' THEN 
			CASE WHEN l_TglMaxCabBeda IS NOT NULL THEN l_TglMaxCabBeda + INTERVAL '1 day' 
			  WHEN l_TglMaxCabBeda IS NULL AND 
				   l_TglProsesPertamaThnini IS NOT NULL THEN l_TglProsesPertamaThnini 
			  WHEN l_TglMaxCabBeda IS NULL AND 
				   l_TglProsesPertamaThnini IS NULL AND 
				   EXTRACT(YEAR FROM l_PrdAwal)= EXTRACT(YEAR FROM l_TglProses) THEN l_PrdAwal
			  ELSE EXTRACT(YEAR FROM l_TglProses) :: VARCHAR(4) || '-01-01' 
			 END 
			  ELSE EXTRACT(YEAR FROM l_TglProses) :: VARCHAR(4) || '-01-01'
			END ; 

-- by peggy 2009 10 06 --  COVERING 2X PROCESS UTK DTP 
--
SELECT COUNT(*)
INTO  l_XPROSES
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

-- Main Data
SELECT 
       -- Gross Income YTD 
       COALESCE(Q1.FixIncome,0)+COALESCE(Q2.VarIncome,0),

       -- Step II
       COALESCE(Q1.FixIncome,0)+COALESCE(Q2.VarIncome,0)+
                COALESCE(Q5.FixIncome,0)+COALESCE(Q6.VarIncome,0),
       -- Bonus YTD 
       COALESCE(Q4.Bonus,0) + 
		COALESCE(fn_KPusat(S01.NIP,X01.EncGrossIncomeBYTD,l_MyPass) ::DECIMAL(15,2),0),

       -- Step III
       COALESCE(Q4.Bonus,0) +
		COALESCE(Q8.Bonus,0) + COALESCE(fn_KPusat(S01.NIP,X01.EncGrossIncomeBYTD,l_MyPass) ::DECIMAL(15,2),0),
       -- Iuran THT Year To date
       COALESCE(Q3.Kolom12,0) +
		 COALESCE(fn_KPusat(S01.NIP,Q13.EncCol12PYTD,l_MyPass) ::DECIMAL(15,2),0), 
       -- step II  
       COALESCE(Q3.Kolom12,0)+COALESCE(Q7.Kolom12,0),
       -- FixIncome
       COALESCE(Q9.FixIncome,0),
       COALESCE(Q10.VarIncome,0),
       COALESCE(Q11.Bonus,0),
       COALESCE(Q12.Kolom12,0)
INTO  
       -- Gross Income YTD 
       l_GIYTDG,

       -- Step II
       l_GIYTDNB, 
       -- Bonus YTD 
       l_BonusYTDG,

       -- Step III
       l_BonusYTDB,
       -- Iuran THT Year To date
       l_Kolom12YTDG,  
       -- step II  
       l_Kolom12YTDNB,
       -- FixIncome
       l_FixIncome,
       l_VarIncome,
       l_Kolom8,
       l_Kolom12
--FROM S01HGAJ S01 
FROM V_HGA2 S01 
INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
----------
LEFT JOIN 
( --X01
SELECT V.* 
FROM V_HGA2 V
INNER JOIN M15PEGA M15 ON M15.NIP=V.NIP
WHERE V.TglPayr < M15.TglMasuk AND EXTRACT(YEAR FROM V.TglPayr)=EXTRACT(YEAR FROM l_TglProses)
) X01 ON S01.NIP=X01.NIP AND X01.TglPayr < M15.TglMasuk 
---------- Left Join untuk Kolom 12 Iuran THT (Q3)
LEFT JOIN 
(--Q1
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND 
      S02.NIP=l_NIP AND S02.FlgDpPt='D' AND S02.Kolom='1' AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='G')
GROUP BY S02.NIP
) Q1 ON Q1.NIP=S01.NIP
----------
LEFT JOIN 
(--Q2
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS VarIncome
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND (S02.Kolom BETWEEN 3 AND 6) AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='G')
GROUP BY S02.NIP
) Q2 ON Q2.NIP=S01.NIP
----------
LEFT JOIN 
(--Q3
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Kolom12
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='12' AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='G')
GROUP BY S02.NIP
) Q3 ON Q3.NIP=S01.NIP
----------
LEFT JOIN 
(--Q4
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Bonus
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='8' AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='G')
GROUP BY S02.NIP
) Q4 ON Q4.NIP=S01.NIP
----------- Fix Income YTD Netto 
LEFT JOIN 
(--Q5
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='1' AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='N')
GROUP BY S02.NIP
) Q5 ON Q5.NIP=S01.NIP
---------- Vari Income YTD Netto 
LEFT JOIN 
(--Q6
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS VarIncome
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND (S02.Kolom BETWEEN 3 AND 6) AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='N')
GROUP BY S02.NIP
) Q6 ON Q6.NIP=S01.NIP
-- Kolom 12 YTD Netto
LEFT JOIN 
(--Q7
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Kolom12
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='12' AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='N')
GROUP BY S02.NIP
) Q7 ON Q7.NIP=S01.NIP
----------- Bonus Netto
LEFT JOIN 
(--Q8
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Bonus
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr<=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='8' AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='N')
GROUP BY S02.NIP
) Q8 ON Q8.NIP=S01.NIP
-----------
LEFT JOIN 
(--Q9
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='1' 
GROUP BY S02.NIP
) Q9 ON Q9.NIP=S01.NIP
------------
LEFT JOIN 
(--Q10
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS VarIncome
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom BETWEEN 3 AND 6
GROUP BY S02.NIP
) Q10 ON Q10.NIP=S01.NIP
-------------
LEFT JOIN 
(--Q11
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Bonus
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='8' 
GROUP BY S02.NIP
) Q11 ON Q11.NIP=S01.NIP
-----------
LEFT JOIN 
(--Q12
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Kolom12
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
INNER JOIN M15PEGA M15 ON M15.NIP=S02.NIP
WHERE EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.TglPayr=l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='12' 
GROUP BY S02.NIP
) Q12 ON Q12.NIP=S01.NIP
------------
LEFT JOIN
(--Q13
SELECT *
FROM V_LTAX S03
WHERE S03.TglPayr=(Select Max(S.TglPayr) FROM V_LTAX S WHERE S.TglPayr<l_TglProses AND S.NIP=l_NIP) AND 
      S03.NIP=l_NIP AND EXTRACT(YEAR FROM S03.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND l_ADAOPBL<>0 
) Q13
ON Q13.NIP=S01.NIP
--
WHERE S01.NIP=l_NIP AND S01.TglPayr=l_TglProses AND 
	  ((X01.TglPayr IS NOT NULL AND EXTRACT(YEAR FROM X01.TglPayr)=EXTRACT(YEAR FROM l_TglProses)) OR 
		X01.TglPayr IS NULL) ; 

--print l_BonusYTDB	  
-- OCC SUPPORT 
SELECT 
       -- Komponen Occ Support Gross YTD 
       COALESCE(Q1.FixIncome,0)+COALESCE(Q2.VarIncome,0) +
		COALESCE(fn_KPusat(S01.NIP,Q13.EncFixIncome,l_MyPass) ::DECIMAL(15,2),0) +
		COALESCE(fn_KPusat(S01.NIP,Q13.EncVarIncome,l_MyPass) ::DECIMAL(15,2),0),
       -- Step II
       COALESCE(Q1.FixIncome,0)+COALESCE(Q2.VarIncome,0)+ 
                   COALESCE(Q5.FixIncome,0)+COALESCE(Q6.VarIncome,0),--
       -- Bonus YTD 
       COALESCE(Q4.Bonus,0), 
       -- Step III
       COALESCE(Q4.Bonus,0)+COALESCE(Q8.Bonus,0)
INTO  
       -- Komponen Occ Support Gross YTD 
       l_KOccSuppG,
       -- Step II
       l_KOccSuppN,--
       -- Bonus YTD 
       l_KOccSupp2G, 
       -- Step III
       l_KOccSupp2N
--
--FROM S01HGAJ S01 
FROM V_HGA2 S01 
-- 
INNER JOIN M15PEGA M15
ON M15.NIP=S01.NIP
--
LEFT JOIN 
(
SELECT V.* 
FROM V_HGA2 V
-- 
INNER JOIN M15PEGA M15
ON M15.NIP=V.NIP
--
WHERE V.TglPayr < M15.TglMasuk AND EXTRACT(YEAR FROM V.TglPayr)=EXTRACT(YEAR FROM l_TglProses)
) X01 
ON S01.NIP=X01.NIP AND X01.TglPayr < M15.TglMasuk 
--
-- Left Join untuk Kolom 12 Iuran THT (Q3)
LEFT JOIN 
(--Q1
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
FROM S02DGAJ S02
--
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
-- 
INNER JOIN M15PEGA M15
ON M15.NIP=S02.NIP
--
WHERE S02.TglPayr BETWEEN l_TglMaxCabBeda AND l_TglProses AND 
      S02.NIP=l_NIP AND S02.FlgDpPt='D' AND S02.Kolom='1' AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='G')
--
GROUP BY S02.NIP
) Q1
--
ON Q1.NIP=S01.NIP
--
LEFT JOIN 
(--Q2
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS VarIncome
FROM S02DGAJ S02
--
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
-- 
INNER JOIN M15PEGA M15
ON M15.NIP=S02.NIP
--
WHERE S02.TglPayr BETWEEN l_TglMaxCabBeda AND l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND (S02.Kolom BETWEEN 3 AND 6) AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='G')
--
GROUP BY S02.NIP
) Q2
--
ON Q2.NIP=S01.NIP
--
--
LEFT JOIN 
(
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Bonus
FROM S02DGAJ S02
--
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
-- 
INNER JOIN M15PEGA M15
ON M15.NIP=S02.NIP
--
WHERE S02.TglPayr BETWEEN l_TglMaxCabBeda AND l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='8' AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='G')
--
GROUP BY S02.NIP
) Q4
--
ON Q4.NIP=S01.NIP
-- Fix Income YTD Netto 
LEFT JOIN 
(
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
FROM S02DGAJ S02
--
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
-- 
INNER JOIN M15PEGA M15
ON M15.NIP=S02.NIP
-- 
WHERE S02.TglPayr BETWEEN l_TglMaxCabBeda AND l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='1' AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='N')
--
GROUP BY S02.NIP
) Q5
ON Q5.NIP=S01.NIP
-- Vari Income YTD Netto 
LEFT JOIN 
(--Q6
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS VarIncome
FROM S02DGAJ S02
--
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
-- 
INNER JOIN M15PEGA M15
ON M15.NIP=S02.NIP
--
WHERE S02.TglPayr BETWEEN l_TglMaxCabBeda AND l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND (S02.Kolom BETWEEN 3 AND 6) AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='N')
--
GROUP BY S02.NIP
) Q6
--
ON Q6.NIP=S01.NIP
--
-- Bonus Netto
LEFT JOIN 
(
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS Bonus
FROM S02DGAJ S02
--
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
-- 
INNER JOIN M15PEGA M15
ON M15.NIP=S02.NIP
--
WHERE S02.TglPayr BETWEEN l_TglMaxCabBeda AND l_TglProses AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND S02.Kolom='8' AND 
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='N')
--
GROUP BY S02.NIP
) Q8
--
ON Q8.NIP=S01.NIP
------------
LEFT JOIN
(--Q13
SELECT *
FROM V_LTAX S03
WHERE S03.TglPayr=(Select Max(S.TglPayr) FROM V_LTAX S WHERE S.TglPayr<l_TglProses AND S.NIP=l_NIP) AND 
      S03.NIP=l_NIP AND EXTRACT(YEAR FROM S03.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND l_ADAOPBL<>0 
) Q13
ON Q13.NIP=S01.NIP
--
WHERE S01.NIP=l_NIP AND S01.TglPayr=l_TglProses AND 
	  ((X01.TglPayr IS NOT NULL AND EXTRACT(YEAR FROM X01.TglPayr)=EXTRACT(YEAR FROM l_TglProses)) OR 
		X01.TglPayr IS NULL) ;

---
/******************************************************************************************************/
-- Step I
-- hitung gross doankz (k1 s/d k6 dan k8 gross) 
--- 
   l_currincome := l_fixincome ;--+ l_varincome 

   SELECT P_Kombinasi_Gross(l_TglProses,
                            l_NIP,  
                            l_GIYTDG,
                            l_BonusYTDG,
                            l_Kolom12YTDG,
			    l_UserID, 	 
			    l_MyPass,
                            l_P_Harian, 
			    l_currincome, 
			    l_flagthr, 
			    l_KOccSuppG, 
			    l_KOccSupp2G) INTO l_PotPajak; 

   l_PajakGross := COALESCE(l_PotPajak,0);

/*****************************************************************************************************/
-- Step II
-- hitung netto k1 s/d k6 dan GROSS k8 

l_KaliLoop      := 0;
l_PotPajak      := 0;
l_Pot_Pajak_Old := 0;
l_Selisih       := l_FZ1IntvPajak+1;
l_Kolom2_NB     := 0;
 
WHILE (l_Selisih > l_FZ1IntvPajak) AND (l_KaliLoop <= 20) LOOP 
BEGIN
  --
   l_GIYTDTotal := l_GIYTDNB+l_Kolom2_NB;
   l_KomponenOccSupport1 := l_KOccSuppN+l_Kolom2_NB  - l_S03COL2YTD ;
-- 
   SELECT P_Kombinasi_Gross(l_TglProses,
                            l_NIP,  
                            l_GIYTDTotal,
--                          l_BonusYTDG, --
			    l_BonusYTDB,--
                            l_Kolom12YTDNB,
			    l_UserID, 	 
			    l_MyPass,
                            l_P_Harian, 
			    l_currincome, 
			    l_flagthr,  
			    l_KomponenOccSupport1, 
			    l_KOccSupp2G ) INTO l_PotPajak; 

   l_KaliLoop      := l_KaliLoop + 1;
   l_Selisih       := ABS(l_PotPajak-l_PajakGross-l_Pot_Pajak_Old);
   l_Pot_Pajak_Old := l_PotPajak-l_PajakGross;
   l_Kolom2_NB     := l_PotPajak-l_PajakGross;

END;
END LOOP; 
---
---
  l_Tunjangan_PjkNB := COALESCE(l_PotPajak,0)-l_PajakGross;
  l_Tunjangan_Total := COALESCE(l_PotPajak,0);
  l_Kolom2_NB       := COALESCE(l_PotPajak,0)-l_PajakGross;

--
-- /****************************************************************************************************/
-- 05-03-2010 : sementara diremark (ADIRA), kombinasi bonus kolom 8 netto, udah diantispasi oleh step 2 
-- STEP III
-- hitung netto k1 s/d k6 dan k8 
-- 
-- SELECT l_KaliLoop=0,
--        l_PotPajak=0,
--        l_Pot_Pajak_Old=0,
--        l_Selisih=l_FZ1IntvPajak+1,
--        l_Kolom2_B=0
-- 
-- WHILE (l_Selisih > l_FZ1IntvPajak) AND (l_KaliLoop <= 20)
-- BEGIN
--   --
--    SELECT l_GIYTDTotal=l_GIYTDNB+l_Tunjangan_PjkNB+l_Kolom2_B
--    SELECT l_KomponenOccSupport1=l_KOccSuppN+l_Tunjangan_PjkNB+l_Kolom2_B - l_S03COL2YTD 
-- 
--    EXEC P_Kombinasi_Netto l_TglProses,
--                           l_NIP,  
--                           l_GIYTDTotal,
--                           l_BonusYTDB,
--                           l_Kolom12YTDNB,
--                           l_PotPajak OUTPUT,
--                           l_Kolom2_B,
-- 		             	  l_MyPass,
--                           l_P_Harian, 
-- 						  l_currincome, 
-- 						  l_flagthr,
-- 						  l_KomponenOccSupport1, 
-- 						  l_KOccSupp2N 
-- 
--    SELECT l_KaliLoop = l_KaliLoop + 1
--    SELECT l_Selisih=ABS(l_PotPajak-l_Tunjangan_Total-l_Pot_Pajak_Old),
--           l_Pot_Pajak_Old=l_PotPajak-l_Tunjangan_Total,
--           l_Kolom2_B=l_PotPajak-l_Tunjangan_Total
-- --PRINT l_TUNJANGAN_TOTAL -- INILAH PROBLEMNYA TUNJANGAN TOTAL DARI GROSS DOANK = 12942 
-- -- TAPI DI P_KOMBINASI_NETTO, YTDIT UDAH KETAMBAHAN DARI K2 BONUS, = 13217
-- 
-- --
-- END
-- -- 
-- SELECT l_Tunjangan_PjkB=COALESCE(l_PotPajak,0)-COALESCE(l_Tunjangan_Total,0)

   l_Tunjangan_PjkB := 0;

/************************************************************************************************/
-- Step IV

   l_GIYTDTotal          := l_GIYTDNB+l_Tunjangan_PjkNB+l_Tunjangan_PjkB;
   l_KomponenOccSupport1 := l_KOccSuppN+l_Tunjangan_PjkNB+l_Tunjangan_PjkB - l_S03COL2YTD;
-- Function
SELECT 	TglPayr,
	NIP,
	KdCaba,
	NewKdCaba,
	OldKdCaba,
	GrsIncYTD,
	FixIncomePYTD,
	OLDFixIncomePYTD,
	VarIncomePYTD,
	OLDVarIncomePYTD,
	Col2YTD,
	OLDCol2YTD,
	Col3YTD,
	OLDCol3YTD,
	Col4YTD,
	OLDCol4YTD,
	Col5YTD,
	OLDCol5YTD,
	Col6YTD,
	OLDCol6YTD,
	Col12PYTD,
	OLDCol12PYTD,
	Col8PYTD,
	OLDCol8PYTD,
	OccSupport1,
	OldOccSupport1,
	OccSupport1PYTD,
	OccSupport2,
	OLDOccSupport2,
	OccSupport2PYTD,
	EGIYNB,
	PTKP,
	EYTI,
	EYIT,
	YTDIT,
	EYITT,
	IncTaxAPaidNB,
	OLDIncTaxAPaidNB,
	IncTaxAPaidB,
	OLDIncTaxAPaidB,
	CurrTaxNB,
	CurrTaxB,
	YITBonus,
	PotPajak,
	TunjanganYTD,
	TunjanganCurr,
	Kolom2,	 
	Kolom3, 
	Kolom4,	 
	Kolom5,	 
	Kolom6,	 
	Kolom12, 
	Kolom8
INTO l_TglPayr,
	l_NIP,
	l_KdCaba,
	l_NewKdCaba,
	l_OldKdCaba,
	l_GrsIncYTD,
	l_FixIncomePYTD,
	l_OLDFixIncomePYTD,
	l_VarIncomePYTD,
	l_OLDVarIncomePYTD,
	l_Col2YTD,
	l_OLDCol2YTD,
	l_Col3YTD,
	l_OLDCol3YTD,
	l_Col4YTD,
	l_OLDCol4YTD,
	l_Col5YTD,
	l_OLDCol5YTD,
	l_Col6YTD,
	l_OLDCol6YTD,
	l_Col12PYTD,
	l_OLDCol12PYTD,
	l_Col8PYTD,
	l_OLDCol8PYTD,
	l_OccSupport1,
	l_OLDOccSupport1,
	l_OccSupport1PYTD,
	l_OccSupport2,
	l_OLDOccSupport2,
	l_OccSupport2PYTD,
	l_EGIYNB,
	l_PTKP,
	l_EYTI,
	l_EYIT,
	l_YTDIT,
	l_EYITT,
	l_IncTaxAPaidNB,
	l_OLDIncTaxAPaidNB,
	l_IncTaxAPaidB,
	l_OLDIncTaxAPaidB,
	l_CurrTaxNB,
	l_CurrTaxB,
	l_YITBonus,
	l_PotPajak,
	l_TunjanganYTD,
	l_Tunjangancurr,
	l_Kolom2,	 
	l_Kolom3,	 
	l_Kolom4,	 
	l_Kolom5,	 
	l_Kolom12,	 
	l_Kolom8
FROM tf_Kombinasi_Update(l_TglProses,
			l_NIP,  
			l_GIYTDTotal,
			l_BonusYTDB,
			l_Kolom12YTDNB,
			l_Tunjangan_PjkNB,
			l_Tunjangan_PjkB,
			l_UserID, 	 
			l_MyPass,
			l_P_Harian, 
			l_currincome, 
			l_flagthr,
			l_KomponenOccSupport1, 
			l_KOccSupp2N);
-- 
--isi tunjangan pajak
   -- Tunjangan Pajak
   SELECT SkDpPt,         KdCurr  
   INTO   l_M03Singkatan, l_M03KdCurr
   FROM M03DPPT
   WHERE FlgDpPt='D' AND KdDpPt='PJK';
   --
   DELETE FROM S02DGAJ
   WHERE TglPayr=l_TglPayr AND NIP=l_NIP AND FlgDpPt='D' AND KdDpPt='PJK';
      --
   INSERT INTO S02DGAJ(TglPayr,  NIP,  FlgDpPt,KdDpPt,FlgAngs,Singkatan,    Nilai,KdCurr,NilaiVal,Kolom,NoAcc,UsComp,NoLyr,FlgNonGL,EncNilai,                                                  EncNilaiVal,                                          S01_ID)
               VALUES (l_TglPayr,l_NIP,'D',    'PJK', ' ',    l_M03Singkatan,0,   'IDR' ,0,       '2',  ' ',  'K',   0,    0,       fn_KCabang(l_NIP,l_TunjanganCurr ::VARCHAR(17),l_MyPass),fn_KCabang(l_NIP,l_TunjanganCurr::VARCHAR(17),l_MyPass),l_S01_ID); 
/*
--************** HTIUNG UMP ****************
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

*/
--*
-- Flag Cabang
SELECT CASE SUBSTR(StringFlag,2,1) WHEN '1' THEN 'Y' ELSE 'T' END
INTO l_FZ2FlgCabang
FROM FZ2FLDA;

-- Pindah Cabang
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
	l_TaxUMPYTD 			    
FROM (SELECT P_Pindah_Cabang(l_NewKdCaba,
				l_OldKdCaba,
				l_FZ2FlgCabang,
				l_FixIncome,
				l_VarIncome        ,
				l_TunjanganCurr,  
				l_Kolom3           ,
				l_Kolom4           ,  
				l_Kolom5           ,  
				l_Kolom6           ,
				l_OccSupport1PYTD  ,
				l_OccSupport2PYTD  ,
				l_TaxUMP           ,
				l_TaxAPaidUMP      ,
				l_TaxUMP_PYTD) AS PPC 
	) AS FX ;

--
SELECT SUM(COALESCE(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2),0))
INTO l_IncDP2009
FROM S02DGAJ S02
--
WHERE DATE_TRUNC('month',l_TglProses) = DATE_TRUNC('month',S02.TglPayr) AND 
      S02.NIP=l_NIP AND s02.FlgDpPt='D' AND
      S02.Kolom BETWEEN 1 AND 8 ;
--
l_NUMNPWP := CASE WHEN TRIM(BOTH FROM l_M15NPWP) ~ '^[0-9]+$' = T THEN 
			TRIM(BOTH FROM l_M15NPWP) ::DECIMAL(20,0) 
		ELSE 
			0 
		END ;
--
--IF CONVERT(VARCHAR(6),l_TglProses,112) BETWEEN '200902' AND '200911' AND 
--
IF	((l_FlgDTPNPWP = 'Y' AND l_NUMNPWP <> 0) OR  -- DTPNPWP = Y DAN NIP ADA NPWP 
	(l_FlgDTPTdkNPWP = 'Y' AND l_NUMNPWP = 0)) -- DTPTdkNPWP = Y DAN NIP tidak ADA NPWP  
	AND 
--	(l_IncDP2009 <= l_FZ1MaxBruto) AND 
--	l_TunjanganCurr > 0 
--	l_potpajak > 0 
-- ADA PAJAK ATAU PROSES > 1 DALAM 1 BULAN 
	(l_PotPajak > 0 OR (l_PotPajak <= 0 AND l_XPROSES > 0 AND l_DTPCURRMONTH > 0)) THEN 
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

 
IF (l_FZ2FlgCabang ='T')  THEN 
   BEGIN
	SELECT  COALESCE(Q.DP,0) 
        INTO    l_TAXUMPYTD 
	FROM
	(
	SELECT fn_KPusat(S03.NIP,S03.EncTaxUMPYTD,l_MyPass) ::DECIMAL(15,2) AS DP 
	FROM S03LTAX S03
	INNER JOIN 
	(
	SELECT S03.NIP, TglPayr = MAX(S03.TglPayr) 
	FROM S03LTAX S03 
	WHERE S03.NIP=l_NIP AND l_TglProses > S03.TglPayr AND EXTRACT(YEAR FROM l_TglProses) = EXTRACT(YEAR FROM S03.TglPayr) 
	GROUP BY S03.NIP 
	) X03 
	ON S03.NIP = X03.NIP AND S03.TglPayr = X03.TglPayr 
	) Q ;
   END;
END IF; 

IF	(l_FZ2FlgCabang ='Y') THEN 
   BEGIN
	SELECT COALESCE(Q.DP,0) 
        INTO   l_TAXUMPYTD 
	FROM
	(
	SELECT fn_KPusat(S03.NIP,S03.EncTaxUMPYTD,l_MyPass) ::DECIMAL(15,2) AS DP 
	FROM S03LTAX S03
	INNER JOIN 
	(
	SELECT S03.NIP, TglPayr = MAX(S03.TglPayr) 
	FROM S03LTAX S03 
	WHERE S03.NIP=l_NIP AND l_TglProses > S03.TglPayr AND EXTRACT(YEAR FROM l_TglProses) = EXTRACT(YEAR FROM S03.TglPayr) 
		AND S03.KDCABA = l_NEWKDCABA 
	GROUP BY S03.NIP 
	) X03 
	ON S03.NIP = X03.NIP AND S03.TglPayr = X03.TglPayr 
	WHERE S03.KDCABA = l_NEWKDCABA 
	) Q ;
   END;
END IF; 

--Jika Pindah Cabang (UMP)
--       SELECT l_TaxUMPYTD = COALESCE(l_TaxUMPYTD,0) + COALESCE(l_TaxUMP,0)
   l_TaxUMPYTD := COALESCE(l_TaxUMPYTD,0)  + COALESCE(l_TaxUMP,0) - COALESCE(l_TaxAPaidUMP,0);


-- Update S03LTAX
   DELETE FROM S03LTAX
   WHERE NIP=l_NIP AND TglPayr=l_TglPayr;
   --
-- menambahkan value 0 untuk field yg gak pake Enc dan plus Enc untuk TotPot
   INSERT INTO S03LTAX(TglPayr, NIP, KdCaba, NilUMP, TaxUMP, TaxAPaidUMP, TaxUMPYTD, MaxBruto, PendDP,
		       EncFixIncome, EncFixIncomePYTD, EncOLDFixIncomePYTD, EncVarIncome, EncVarIncomePYTD, 
                       EncOLDVarIncomePYTD, EncCol2YTD, EncOLDCol2YTD, EncCol3YTD, EncOLDCol3YTD, EncCol4YTD, EncOLDCol4YTD, EncCol5YTD,
                       EncOLDCol5YTD, EncCol6YTD, EncOLDCol6YTD, EncKolom12, EncCol12PYTD, EncOLDCol12PYTD, EncOccSupport1, EncOLDOccSupport1,
                       EncEGIYNB, EncPTKP, EncEYTI, EncEYIT, EncYTDIT, EncIncTaxAPaidNB, EncOLDIncTaxAPaidNB, EncKolom8, EncCol8PYTD, EncOLDCol8PYTD,
                       EncOccSupport2, EncOLDOccSupport2, EncEYITT, EncIncTaxAPaidB, EncOLDIncTaxAPaidB, EncTotPot, FlagTHR,
                       Harian, FlgStruk, FlgPdLL, kdUMP, EncNilUMP, EncTaxUMP, EncTaxAPaidUMP, EncTaxUMPYTD, Hari, EncMaxBruto, EncPendDP,
                       VERSION, Created_by,  Created_On,  WS)
               VALUES (l_TglPayr, l_NIP, l_KdCaba, 0, 0, 0, 0, 0, 0, fn_kCabang(l_NIP,(l_FixIncome :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_FixIncomePYTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDFixIncomePYTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_VarIncome :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_VarIncomePYTD :: VARCHAR(17)),l_MyPass),
                       fn_kCabang(l_NIP,(l_OLDVarIncomePYTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_Col2YTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDCol2YTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_Col3YTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDCol3YTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_Col4YTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDCol4YTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_Col5YTD :: VARCHAR(17)),l_MyPass),
                       fn_kCabang(l_NIP,(l_OLDCol5YTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_Col6YTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDCol6YTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_Kolom12 :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_Col12PYTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDCol12PYTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OccSupport1 :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDOccSupport1 :: VARCHAR(17)),l_MyPass),
                       fn_kCabang(l_NIP,(l_EGIYNB :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_PTKP :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_EYTI :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_EYIT :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_YTDIT :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_IncTaxAPaidNB :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDIncTaxAPaidNB :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_Kolom8 :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_Col8PYTD :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDCol8PYTD :: VARCHAR(17)),l_MyPass),
                       fn_kCabang(l_NIP,(l_OccSupport2 :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDOccSupport2 :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_EYITT :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_IncTaxAPaidB :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_OLDIncTaxAPaidB :: VARCHAR(17)),l_MyPass),  fn_kCabang(l_NIP,'0',l_MyPass)     ,l_FlagTHR ,
                       l_P_Harian, l_FlgStruk, l_FlgDLL, l_KdUMP, fn_kCabang(l_NIP,(l_NilUMP :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_taxUMP :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_TaxAPaidUMP :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_TaxUMPYTD :: VARCHAR(17)),l_MyPass), l_Hari, fn_kCabang(l_NIP,(l_FZ1MaxBruto :: VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,(l_FZ1PendDP :: VARCHAR(17)),l_MyPass), 
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

-- Nilai Potongan Pajak di kurangi dengan pajak DP by suhe 15-08-2003
--    SET l_PotPajakNew=fn_Vround(l_PotPajak-(COALESCE(l_TaxUMP,0)-COALESCE(l_TaxAPaidUMP,0)))
    l_PotPajakNew := l_PotPajak ;

   -- Potongan Pajak
   SELECT SkDpPt,         KdCurr  
   INTO   l_M03Singkatan, l_M03KdCurr
   FROM M03DPPT
   WHERE FlgDpPt='P' AND KdDpPt='PJK';
   --
   DELETE FROM S02DGAJ
   WHERE TglPayr=l_TglPayr AND NIP=l_NIP AND FlgDpPt='P' AND KdDpPt='PJK';
   --
   INSERT INTO S02DGAJ(TglPayr,  NIP,  FlgDpPt,KdDpPt,FlgAngs,Singkatan,    Nilai,KdCurr,NilaiVal,Kolom,NoAcc,UsComp,NoLyr,FlgNonGL,EncNilai,                                             EncNilaiVal,                                          S01_ID)
               VALUES (l_TglPayr,l_NIP,'P',    'PJK', ' ',    l_M03Singkatan,0,   'IDR' ,0,       ' ',  ' ',  'K',   0,    0,       fn_KCabang(l_NIP,l_PotPajakNew::VARCHAR(17),l_MyPass),fn_KCabang(l_NIP,l_PotPajakNew::VARCHAR(17),l_MyPass),l_S01_ID);


/*
Hitung Pajak dibayar Perusahaan akibat pajak DP

PajakGross= PPh yang harus di potong - Tunjangan pajak
--
IF PajakGross > PPh DP
   Pajak dibayar Perusahaan=0
ELSE
   Pajak dibayar Perusahaan=PPh DP - Pajak GRoss
END
*/

/*
SET l_PjkGross=l_PotPajak-l_TunjanganCurr
--
IF l_PjkGross > (COALESCE(l_TaxUMP,0)-COALESCE(l_TaxAPaidUMP,0))
   BEGIN  
     SET l_PjkPrsh=0
   END
ELSE
   BEGIN
     SET l_PjkPrsh=(COALESCE(l_TaxUMP,0)-COALESCE(l_TaxAPaidUMP,0))-l_PjkGross
   END
--* 
--
--- Jika Karyawan ada PPh DP maka Harus Dibentuk Pajak Dibayar Perusahaan
IF (COALESCE(l_TaxUMP,0)-COALESCE(l_TaxAPaidUMP,0))<>0
  BEGIN
     SELECT l_M03Singkatan=SkDpPt,
            l_M03KdCurr   =KdCurr  FROM M03DPPT
            WHERE FlgDpPt='P' AND KdDpPt='RPJK'
     --
     DELETE FROM S02DGAJ
     WHERE TglPayr=l_TglPayr AND NIP=l_NIP AND FlgDpPt='P' AND KdDpPt='RPJK'
     --
     INSERT INTO S02DGAJ(TglPayr, NIP, FlgDpPt,KdDpPt,FlgAngs,Singkatan,    Nilai,KdCurr,    NilaiVal,Kolom,NoAcc,UsComp,NoLyr,FlgNonGL,EncNilai,                                                EncNilaiVal)
                 VALUES (l_TglPayr,l_NIP,'P',    'RPJK', ' ',    l_M03Singkatan,0,    'IDR' ,0,       ' ',  ' ',  'K',   0,    0,       fn_KCabang(l_NIP,CONVERT(VARCHAR(17),l_PjkPrsh),l_MyPass),fn_KCabang(l_NIP,CONVERT(VARCHAR(17),l_PjkPrsh),l_MyPass))
  END
*/
--*
END ;
$$ LANGUAGE plpgsql ;
/*
EXEC  P_Pjk_Kombinasi   l_TglProses='2003-03-20',
                        l_NIP      ='010',
                        l_JnsPeg   ='A',
                        l_FlagTHR  =' ',
                      l_UserID   ='Suhe',
	  		            l_MyPass   ='Copyright, 1988 (c) Microsoft Corporation, All rights reserved',
                        l_P_Harian ='T',
                        l_HKerja   =20,
                        l_AkhirBln =1

*/


/*
DECLARE l_TblName VARCHAR(8), 
        l_mlValid INT
--
EXEC P_PAYRP1   l_TglProses  ='2009-01-31',
		        l_UkerFr   ='',
		        l_UkerTo   ='ZZZZ',
		        l_LokasiFr   ='',
		        l_LokasiTo   ='ZZZZ',
		        l_NIPFr  ='05739',
		        l_NIPTo  ='05739',
    		    l_FGOL   =' ',
                l_TGOL   ='ZZZ',
                l_FCAB   = ' ',
                l_TCAB   = 'ZZZ',    
		        l_J_THR  ='L',
		        l_P_THR  ='I',
		        l_FlagKhusus='Y',
		        l_KodeLDA='01',
		        l_UserID ='suhe',
		        l_Gagal  =0,
		        l_TableName1 = l_TblName OUTPUT,
		        l_mlValid  =l_mlValid OUTPUT,
                l_MyPass   ='Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
--
SELECT l_TblName , CAST(l_mlValid AS VARCHAR(1))
rollback
*/