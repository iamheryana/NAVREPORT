/******************************************
Name sprocs	: P_Hitung_Pjk_TypeB
Create by	: Wati
Date		: 19-06-2003
Description	: Proses Hitung Pajak B (untuk semua Jenis)
Call From	: Main sprocs
*****************************************/
DROP FUNCTION  P_Hitung_Pjk_TypeB(	l_JnsPajak  VARCHAR(1),
						l_TglProses DATE,
						l_TglAkhir  DATE,  
						l_NIP       VARCHAR(10), 
						l_MyPass    VARCHAR(128),
						l_UserID    VARCHAR(12),
						l_FlagTHR   VARCHAR(1),
						l_Pesangon  VARCHAR(1),
						l_S01_ID	INT) ;
--
CREATE FUNCTION  P_Hitung_Pjk_TypeB(	l_JnsPajak  VARCHAR(1),
						l_TglProses DATE,
						l_TglAkhir  DATE,  
						l_NIP       VARCHAR(10), 
						l_MyPass    VARCHAR(128),
						l_UserID    VARCHAR(12),
						l_FlagTHR   VARCHAR(1),
						l_Pesangon  VARCHAR(1),
						l_S01_ID	INT) 
RETURNS VOID 
/*
JIKA l_Pesangon='Y' --> Dari Pajak Pesangon
     l_Pesangon='M' --> Dari Mantan Pegawai
     ELSE	   --> ....
	 	
*/

AS $$ 
DECLARE l_M10JnsPajak                 VARCHAR(1);
        l_IncomeCurrentMonthGross     DECIMAL(15,2);   l_IncomeCurrentMonthTotal     DECIMAL(15,2);
        l_TunjanganPajakPMTD          DECIMAL(15,2);   l_TunjanganPajakPYTD          DECIMAL(15,2);
        l_GrossIncomeCurrentMonthPMTD DECIMAL(15,2);   l_TaxableIncomePMTD           DECIMAL(15,2);
        l_TaxPMTD                     DECIMAL(15,2);   l_GrossIncomePYTD             DECIMAL(15,2);
        l_TaxableIncomePYTD           DECIMAL(15,2);   l_TaxAllReadyPaidPYTD         DECIMAL(15,2);
        l_TaxCurrentMonthGross        DECIMAL(15,2);   l_TaxCurrentMonthTotal        DECIMAL(15,2);
        l_TunjanganPajakCurrentMonth  DECIMAL(15,2);   l_TunjanganPajakCurrent       DECIMAL(15,2);
        l_PotonganPajakCurrentMonth   DECIMAL(15,2);   l_GrossIncomeCurrentMonth     DECIMAL(15,2);
        l_TaxableIncomeCurrentMonth   DECIMAL(15,2);   l_GrossIncomeCurrent          DECIMAL(15,2);
        l_TaxableIncomeCurrent        DECIMAL(15,2);   l_TaxCurrent                  DECIMAL(15,2);
        l_M03Singkatan                VARCHAR(10);
        l_M03UsComp                   VARCHAR(1);      l_M03Kolom                    VARCHAR(2);
        l_M03NoAcc                    VARCHAR(10);     l_M03Status                   VARCHAR(1);
        l_M03Persen                   DECIMAL(5,2);    l_M03Nilai                    DECIMAL(15,2);
	l_M03NoLyr                    INT;             l_M03KdCurr                   VARCHAR(4);
	l_M03ID        INT;
BEGIN 
-- Main Data
SELECT TBL4.JnsPajak,
	TBL4.IncomeCurrentMonthGross,
	TBL4.IncomeCurrentMonthTotal,
	TBL4.TunjanganPajakPMTD,
	TBL4.TunjanganPajakPYTD,
	TBL4.GrossIncomeCurrentMonthPMTD,
	TBL4.TaxableIncomePMTD,
	TBL4.TaxPMTD,
	TBL4.GrossIncomePYTD,
	TBL4.TaxableIncomePYTD,
	TBL4.TaxAllReadyPaidPYTD,
	TBL4.TaxCurrentMonthGross,
	TBL4.TaxCurrentMonthTotal,
	TBL4.TunjanganPajakCurrentMonth,
	fn_Vround(TBL4.TunjanganPajakCurrent),
	TBL4.PotonganPajakCurrentMonth,
	TBL4.GrossIncomeCurrentMonth,
	TBL4.TaxableIncomeCurrentMonth,
	TBL4.GrossIncomeCurrent,
	TBL4.TaxableIncomeCurrent,
	fn_Vround(TBL4.TaxCurrent)
INTO  l_M10JnsPajak,
	l_IncomeCurrentMonthGross,
	l_IncomeCurrentMonthTotal,
	l_TunjanganPajakPMTD,
	l_TunjanganPajakPYTD, 
	l_GrossIncomeCurrentMonthPMTD,
	l_TaxableIncomePMTD,
	l_TaxPMTD,
	l_GrossIncomePYTD,
	l_TaxableIncomePYTD,
	l_TaxAllReadyPaidPYTD,
	l_TaxCurrentMonthGross,
	l_TaxCurrentMonthTotal,
	l_TunjanganPajakCurrentMonth,
	l_TunjanganPajakCurrent,
	l_PotonganPajakCurrentMonth,
	l_GrossIncomeCurrentMonth,
	l_TaxableIncomeCurrentMonth,
	l_GrossIncomeCurrent,
	l_TaxableIncomeCurrent,
	l_TaxCurrent
FROM
(
SELECT TBL3.NIP, TBL3.PersenPKP, TBL3.JnsPajak, TBL3.PersenPj1,
       TBL3.IncomeCurrentMonthGross, TBL3.IncomeCurrentMonthTotal,
       TBL3.TunjanganPajakPMTD, TBL3.TunjanganPajakPYTD,
       TBL3.GrossIncomeCurrentMonthPMTD, TBL3.TaxableIncomePMTD, TBL3.TaxPMTD,
       TBL3.GrossIncomePYTD, TBL3.TaxableIncomePYTD, TBL3.TaxAllReadyPaidPYTD,
       TBL3.TaxCurrentMonthGross, TBL3.TaxCurrentMonthTotal, TBL3.TunjanganPajakCurrentMonth,
       TBL3.TunjanganPajakCurrent, TBL3.PotonganPajakCurrentMonth, TBL3.GrossIncomeCurrentMonth,
       -- j
       FLOOR(((TBL3.GrossIncomeCurrentMonth*TBL3.PersenPKP)/100)/1000)*1000 AS TaxableIncomeCurrentMonth,
       -- n
       (TBL3.GrossIncomeCurrentMonth-TBL3.GrossIncomeCurrentMonthPMTD)+TBL3.GrossIncomePYTD AS GrossIncomeCurrent, /* sebelum edit 2011-06-06 */
--        GrossIncomeCurrent=(TBL3.GrossIncomeCurrentMonth-TBL3.GrossIncomeCurrentMonthPMTD), 
       -- o
      (FLOOR(((TBL3.GrossIncomeCurrentMonth*TBL3.PersenPKP)/100)/1000)*1000)-TBL3.TaxableIncomePMTD AS TaxableIncomeCurrent,  /* sebelum edit 2011-06-06 */
--        TaxableIncomeCurrent=(FLOOR((((TBL3.GrossIncomeCurrentMonth-TBL3.GrossIncomePYTD)*TBL3.PersenPKP)/100)/1000)*1000)-TBL3.TaxableIncomePMTD,
       -- p
       TBL3.PotonganPajakCurrentMonth-TBL3.TaxPMTD AS TaxCurrent
FROM
(
SELECT TBL2.NIP,TBL2.PersenPKP,TBL2.JnsPajak,TBL2.PersenPj1,
       TBL2.IncomeCurrentMonthGross,TBL2.IncomeCurrentMonthTotal,
       TBL2.TunjanganPajakPMTD,TBL2.TunjanganPajakPYTD,
       TBL2.GrossIncomeCurrentMonthPMTD,TBL2.TaxableIncomePMTD,TBL2.TaxPMTD,
       TBL2.GrossIncomePYTD,TBL2.TaxableIncomePYTD,TBL2.TaxAllReadyPaidPYTD,
       TBL2.TaxCurrentMonthGross,TBL2.TaxCurrentMonthTotal,TBL2.TunjanganPajakCurrentMonth,
       -- g3
       (TBL2.TunjanganPajakCurrentMonth-TBL2.TunjanganPajakPMTD) AS TunjanganPajakCurrent,
       -- h
       (TBL2.TaxCurrentMonthGross+TBL2.TunjanganPajakCurrentMonth) AS PotonganPajakCurrentMonth,
       -- i
       (TBL2.IncomeCurrentMonthTotal+TBL2.TunjanganPajakCurrentMonth) AS GrossIncomeCurrentMonth   /* sebelum edit 2011-06-06 */    
-- 	   GrossIncomeCurrentMonth=(TBL2.IncomeCurrentMonthTotal+TBL2.TunjanganPajakCurrentMonth+TBL2.GrossIncomePYTD)       
FROM
(
SELECT TBL1.NIP,TBL1.PersenPKP,TBL1.JnsPajak,TBL1.PersenPj1,
       TBL1.IncomeCurrentMonthGross,TBL1.IncomeCurrentMonthTotal,
       TBL1.TunjanganPajakPMTD,TBL1.TunjanganPajakPYTD,
       TBL1.GrossIncomeCurrentMonthPMTD,TBL1.TaxableIncomePMTD,TBL1.TaxPMTD,
       TBL1.GrossIncomePYTD,TBL1.TaxableIncomePYTD,TBL1.TaxAllReadyPaidPYTD,
       TBL1.TaxCurrentMonthGross,TBL1.TaxCurrentMonthTotal,
       -- g
       CASE WHEN (TBL1.IncomeCurrentMonthTotal>0 AND l_JnsPajak='N')      
	       THEN TBL1.TaxCurrentMonthTotal-TBL1.TaxCurrentMonthGross
	       ELSE 0 END AS TunjanganPajakCurrentMonth              
FROM
(
SELECT TBL.NIP,TBL.PersenPKP,TBL.JnsPajak,TBL.PersenPj1,
       TBL.IncomeCurrentMonthGross,TBL.IncomeCurrentMonthTotal,
       TBL.TunjanganPajakPMTD,TBL.TunjanganPajakPYTD,
       TBL.GrossIncomeCurrentMonthPMTD,TBL.TaxableIncomePMTD,TBL.TaxPMTD,
       TBL.GrossIncomePYTD,TBL.TaxableIncomePYTD,TBL.TaxAllReadyPaidPYTD,
       -- C 
       CASE WHEN TBL.IncomeCurrentMonthGross>0  
	 THEN fn_TaxCalculationB(TBL.JnsPajak,TBL.IncomeCurrentMonthGross,TBL.NIP,TBL.PersenPKP,TBL.PersenPJ1)
	 ELSE 0 END AS TaxCurrentMonthGross,
       -- f
       CASE WHEN (TBL.IncomeCurrentMonthTotal>0 AND l_JnsPajak='N')
	 THEN fn_TaxCalculationB_Netto(TBL.JnsPajak,TBL.IncomeCurrentMonthTotal,TBL.NIP,TBL.PersenPKP,TBL.PersenPJ1)
	 ELSE 0 END AS TaxCurrentMonthTotal
FROM
(
--                           Jika dari Pesangon maka Jns_pajak pantek jadi 'X', Mantan pegawai ='Y'
SELECT S01.NIP,M41.PersenPKP,
       CASE l_Pesangon WHEN 'Y' THEN 'X' 
		       WHEN 'M' THEN 'Y'   
		       ELSE M10.JnsPajak END AS JnsPajak, 
       M41.PersenPj1,
       -- Q1.1 (a)
       COALESCE(Q1.TotalGross,0) AS IncomeCurrentMonthGross,
       -- Q2.1 (c)
       COALESCE(Q2.TotalIncome,0) AS IncomeCurrentMonthTotal,
       -- Q3.4 (g1)
       COALESCE(fn_KPusat(Q3.NIP,Q3.EncBonusCurrent,l_MyPass) ::DECIMAL(15,2),0) AS TunjanganPajakPMTD,
       -- Q4.4 (g2)
       COALESCE(fn_KPusat(Q4.NIP,Q4.EncVariPYTD,l_MyPass) ::DECIMAL(15,2),0)
		+COALESCE(fn_KPusat(Q4.NIP,Q4.EncBonusPYTD,l_MyPass) ::DECIMAL(15,2),0) AS TunjanganPajakPYTD,
       -- Q3.1 (k)
       COALESCE(fn_KPusat(Q3.NIP,Q3.EncIncomeTaxTtl,l_MyPass) ::DECIMAL(15,2),0) AS GrossIncomeCurrentMonthPMTD,
       -- Q3.2 (l)
       COALESCE(fn_KPusat(Q3.NIP,Q3.EncVariCurrent,l_MyPass) ::DECIMAL(15,2),0) AS TaxableIncomePMTD,
       -- Q3.3 (m)
       COALESCE(fn_KPusat(Q3.NIP,Q3.EncPjBnsCurrent,l_MyPass) ::DECIMAL(15,2),0) AS TaxPMTD,  /* sebelum edit 2011-06-06 */
--        TaxPMTD=COALESCE(fn_KPusat(Q4.NIP,Q4.EncPjBnsCurrent,l_MyPass) ::DECIMAL(15,2),0),  
	   -- Q4.1 (q)
       COALESCE(fn_KPusat(Q4.NIP,Q4.EncGrossIncPYTD,l_MyPass) ::DECIMAL(15,2),0)
		+COALESCE(fn_KPusat(Q4.NIP,Q4.EncGrossIncCurrent,l_MyPass) ::DECIMAL(15,2),0) AS GrossIncomePYTD,  /* sebelum edit 2011-06-06 */
-- 	   GrossIncomePYTD=COALESCE(fn_KPusat(Q4.NIP,Q4.EncGrossIncCurrent,l_MyPass) ::DECIMAL(15,2),0),
       -- Q4.2 (r)
       COALESCE(fn_KPusat(Q4.NIP,Q4.EncTaxableIncPYTD,l_MyPass) ::DECIMAL(15,2),0)
		+COALESCE(fn_KPusat(Q4.NIP,Q4.EncTaxableIncCurrent,l_MyPass) ::DECIMAL(15,2),0) AS TaxableIncomePYTD,
       -- Q4.3 (s)
       COALESCE(fn_KPusat(Q4.NIP,Q4.EncIncomeTaxAPaid,l_MyPass) ::DECIMAL(15,2),0)
		+COALESCE(fn_KPusat(Q4.NIP,Q4.EncIncomeTaxCurrent,l_MyPass) ::DECIMAL(15,2),0) AS TaxAllReadyPaidPYTD   
FROM S01HGAJ S01 
-- ambil kode KdKlas
INNER JOIN M15PEGA M15
ON M15.NIP=S01.NIP
-- Ambil Jns Pajak
INNER JOIN M10KLAS M10
ON M10.Kode=M15.KdKlas
--
INNER JOIN M41JPJK M41
ON M41.Kode=M10.JnsPajak

-- Detail Penggajian
LEFT JOIN 
(SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS TotalIncome
FROM S02DGAJ S02
WHERE S02.NIP=l_NIP AND EXTRACT(MONTH FROM S02.TglPayr)=EXTRACT(MONTH FROM l_TglProses) AND 
      EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S02.FlgDpPt='D' AND S02.KdDpPt<>'PJK'
--
GROUP BY S02.NIP
) Q2
ON Q2.NIP=S01.NIP

-- Detail Penggajian Untuk Total Gross
LEFT JOIN
(
SELECT S02.NIP,SUM(fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS TotalGross
FROM S02DGAJ S02
--
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt 
-- 
INNER JOIN M15PEGA M15
ON M15.NIP=S02.NIP
--
WHERE S02.NIP=l_NIP AND S02.FlgDpPt='D' AND S02.KdDpPt<>'PJK' AND
--      EXTRACT(MONTH FROM S02.TglPayr)=EXTRACT(MONTH FROM l_TglProses) AND EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND /* sebelum edit 2011-06-06*/        
--      EXTRACT(MONTH FROM S02.TglPayr)<=EXTRACT(MONTH FROM l_TglProses) AND EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND /* sebelum edit 2012-10-31*/        
		/*Edit Tgl 2012-12-18 l_Pesangon = M --> Mantan Pegawai */
		((CASE WHEN l_Pesangon='M' THEN EXTRACT(MONTH FROM S02.TglPayr) END) = EXTRACT(MONTH FROM l_TglProses) OR 
		(CASE WHEN l_Pesangon<>'M' THEN EXTRACT(MONTH FROM S02.TglPayr) END) <= EXTRACT(MONTH FROM l_TglProses)) 
		AND EXTRACT(YEAR FROM S02.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND
      ((CASE WHEN M03.Pajak='D' THEN M15.Pajak ELSE M03.Pajak END)='G')
--
GROUP BY S02.NIP
) Q1
ON Q1.NIP=S01.NIP

-- Detail S0BLSTX Berdasarka Bulan yang sama
LEFT JOIN 
(SELECT *
FROM S0BLSTX S0B
WHERE S0B.NIP=l_NIP AND EXTRACT(MONTH FROM S0B.TglPayr)=EXTRACT(MONTH FROM l_TglProses) AND S0B.TglPayr=l_TglAkhir
) Q3
ON Q3.NIP=S01.NIP 

-- Detail S0BLSTX berdasarkan Tahun yang sama
LEFT JOIN 
(SELECT *
FROM S0BLSTX S0B
WHERE S0B.NIP=l_NIP AND EXTRACT(YEAR FROM S0B.TglPayr)=EXTRACT(YEAR FROM l_TglProses) AND S0B.TglPayr=l_TglAkhir
) Q4
ON Q4.NIP=S01.NIP 
--
WHERE S01.NIP=l_NIP AND S01.TglPayr=l_TglProses 

) TBL

) TBL1

) TBL2

) TBL3

) TBL4 ;
--*

-- Add Record S0BLSTX
   DELETE FROM S0BLSTX
   WHERE NIP=l_NIP AND TglPayr=l_TglProses ;
   --
   INSERT INTO S0BLSTX(TglPayr, NIP, JnsPajak, EncGrossIncPYTD, EncGrossIncCurrent, EncVariPYTD, EncVariCurrent, EncOccSupport,
                       EncPTKP, EncIncomeTaxCurrent, EncIncomeTaxAPaid, EncTaxableIncCurrent, EncTaxableIncPYTD, EncBonusCurrent,
                       EncBonusPYTD, EncPjBnsCurrent, EncPjBnsPYTD, EncIncomeTaxTtl, FlagTHR,
                       VERSION, Created_by,  Created_On)
                VALUES(l_Tglproses, l_NIP, l_M10JnsPajak, fn_kCabang(l_NIP,CAST(l_GrossIncomePYTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_GrossIncomeCurrent as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_TunjanganPajakPYTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_TaxableIncomeCurrentMonth as VARCHAR(17)),l_MyPass), fn_kcabang(l_NIP, '0',l_MyPass),
                       fn_kcabang(l_NIP, '0',l_MyPass), fn_kCabang(l_NIP,CAST(l_TaxCurrent as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_TaxAllReadyPaidPYTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_TaxableIncomeCurrent as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_TaxableIncomePYTD as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_TunjanganPajakCurrentMonth as VARCHAR(17)),l_MyPass),
                       fn_kCabang(l_NIP,CAST(l_TunjanganPajakCurrent as VARCHAR(17)),l_MyPass), fn_kCabang(l_NIP,CAST(l_PotonganPajakCurrentMonth as VARCHAR(17)),l_MyPass), fn_kcabang(l_NIP, '0',l_MyPass), fn_kCabang(l_NIP,CAST(l_GrossIncomeCurrentMonth as VARCHAR(17)),l_MyPass), l_FlagTHR,
                       0,     l_UserID,      CURRENT_DATE);

-- Upd. Header Penggajian(S01HGAJ) 
   UPDATE S01HGAJ
   SET EncGrossIncomeNBYTD = fn_kCabang(l_NIP,(l_GrossIncomeCurrentMonth :: VARCHAR(17)),l_MyPass),
       EncEYTI             = fn_kCabang(l_NIP,(l_TaxableIncomeCurrentMonth :: VARCHAR(17)),l_MyPass),
       EncEYIT             = fn_kCabang(l_NIP,(l_TaxCurrent :: VARCHAR(17)),l_MyPass),
       EncGrossIncomeBYTD  = fn_kCabang(l_NIP,(l_TaxPMTD :: VARCHAR(17)),l_MyPass),
       EncTaxTotal         = fn_kCabang(l_NIP,(l_PotonganPajakCurrentMonth :: VARCHAR(17)),l_MyPass),
       EncTaxPesangonRp    = CASE WHEN l_M10JnsPajak='X' THEN fn_kCabang(l_NIP,(l_TaxCurrent :: VARCHAR(17)),l_MyPass) ELSE fn_kCabang(l_NIP,'0',l_MyPass) END
   WHERE NIP=l_NIP AND TglPayr=l_TglProses;
 
-- Add Record di Detail Penggajian
  SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
         fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
  from  P_M03AllField ('P', 'PJK' ) fx
  INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
	  l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;    
   --
   DELETE FROM S02DGAJ
   WHERE TglPayr=l_TglProses AND NIP=l_NIP AND FlgDpPt='P' AND KdDpPt='PJK';
   --
   INSERT INTO S02DGAJ(TglPayr,    NIP, FlgDpPt, KdDpPt,FlgAngs,Singkatan,     Nilai,KdCurr,NilaiVal,Kolom,NoAcc,UsComp,NoLyr,FlgNonGL,EncNilai,                                             EncNilaiVal,                                          S01_ID)
               VALUES (l_TglProses,l_NIP,'P',    'PJK', ' ',    l_M03Singkatan,0,    'IDR' ,0,       ' ',  ' ',  'K',   0,    0,       fn_KCabang(l_NIP,l_TaxCurrent ::VARCHAR(17),l_MyPass),fn_KCabang(l_NIP,l_TaxCurrent::VARCHAR(17),l_MyPass), l_S01_ID);

   IF COALESCE(l_TunjanganPajakCurrent,0)>0 THEN 
      BEGIN
        -- Delete Detail Penggajian 
        DELETE FROM S02DGAJ
        WHERE TglPayr=l_TglProses AND NIP=l_NIP AND FlgDpPt='D' AND KdDpPt='PJK';
        ---  
	SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
	       fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
	from  P_M03AllField ('D', 'PJK' ) fx
	INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
	        l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;    
			 	           
        -- Add Record Detail Penggajian
        INSERT INTO S02DGAJ(TglPayr,    NIP,  FlgDpPt,KdDpPt,FlgAngs,Singkatan,     Nilai,KdCurr,NilaiVal,Kolom,NoAcc,UsComp,NoLyr,FlgNonGL,EncNilai,                                                        EncNilaiVal,                                                    S01_ID)
                    VALUES (l_TglProses,l_NIP,'D',    'PJK', ' ',    l_M03Singkatan,0,    'IDR' ,0,       ' ',  ' ',  'K',   0,    0,       fn_KCabang(l_NIP,l_TunjanganPajakCurrent ::VARCHAR(17),l_MyPass),fn_KCabang(l_NIP,l_TunjanganPajakCurrent::VARCHAR(17),l_MyPass),l_S01_ID);
        -- 
      END;
END IF; 
END ;
$$ LANGUAGE plpgsql ;


