/******************************************
Name sprocs	: P_Hit_JHT
Create by	: Wati
Date		: 19-06-2003
Description	: Proses Hitung JHT
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Hit_JHT(l_TglProses  DATE,
				     l_NIP        VARCHAR(10),
				     l_MyPass     VARCHAR(128),
				     l_S01_ID     INT);
--    
CREATE FUNCTION P_Hit_JHT(l_TglProses  DATE,
				     l_NIP        VARCHAR(10),
				     l_MyPass     VARCHAR(128),
				     l_S01_ID     INT)      

RETURNS VOID 
AS $$
DECLARE l_PersenJHT    DECIMAL(7,2);
        l_NilaiJHT     DECIMAL(15,2);
        l_NilaiJHTVal  DECIMAL(15,2);
        l_M03Singkatan VARCHAR(10);
        l_M03UsComp    VARCHAR(1);
        l_M03Kolom     VARCHAR(2);
        l_M03NoAcc     VARCHAR(10);
        l_M03Status    VARCHAR(1);
        l_M03Persen    DECIMAL(5,2);
        l_M03Nilai     DECIMAL(15,2);
	l_M03NoLyr     INT;
	l_M03KdCurr    VARCHAR(4);
	l_M03ID        INT;
	l_M10Harian    INT;

BEGIN 
SELECT Pgwi
INTO   l_PersenJHT
FROM M18JAMS 
WHERE Progrm='JHT';

-- HARIAN MODI BY PEGGY 20060505 
SELECT M10.Harian
INTO   l_M10Harian
FROM M15PEGA M15 
INNER JOIN M10KLAS M10 
ON M15.KDKLAS = M10.KODE 
WHERE M15.NIP = l_NIP; 

-- Hitung FixIncome
SELECT COALESCE(fn_Vround(((TBL.FixIncome*l_PersenJHT)/100) * -1),0)
INTO   l_NilaiJHT
FROM
(
SELECT S02.NIP,SUM(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
FROM S02DGAJ S02
-- BY PEGGY 2006 12 18 
INNER JOIN M03DPPT M03 
ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
--WHERE NIP=l_NIP AND Kolom='1' AND --S02.TglPayr=l_TglProses
WHERE NIP=l_NIP AND M03.STATUS = '1' AND 
--WHERE NIP=l_NIP AND Kolom='1' AND 
-- BULANAN AMBIL TGLPAYROLL = TGL PROSES SCREEN PARAMETER 
	((S02.TglPayr=l_TglProses AND l_M10HARIAN = 0) OR 
-- HARIAN AMBIL TGLPAYROLL DLM BLN THN SAMA DENGAN TGL PROSES SCREEN PARAMETER 
	(DATE_TRUNC('month',S02.TglPayr) = DATE_TRUNC('month',l_TglProses) AND l_M10HARIAN = 1)) 
GROUP BY S02.NIP
) TBL;

-- Ambil Nilai Master Pandapatan dan Potongan
SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
       fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
from  P_M03AllField ('D', 'JHT' ) fx
INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
	l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;    
	

-- Get Kurs
l_NilaiJHTVal := COALESCE(fn_Vround(l_NilaiJHT/fn_GetKurs(l_M03KdCurr,l_TglProses)),0);

-- Insert dan Update untuk Pendapatan JHT
IF (SELECT COUNT(NIP) FROM S02DGAJ WHERE NIP=l_NIP AND TglPayr=l_TglProses AND 
          FlgDpPt='D' AND KdDpPt='JHT' AND COALESCE(FlgAngs,' ')=' ') > 0 THEN 
   BEGIN
     UPDATE S02DGAJ
     SET EncNilaiVal = fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_NilaiJHTVal) ::VARCHAR(17),l_MyPass),
         EncNilai    = fn_kcabang(l_NIP,(fn_kpusat(l_NIP, EncNilai,l_MyPass) :: Decimal(15,2)+l_NilaiJHT) :: VARCHAR(17),l_MyPass)
     WHERE NIP=l_NIP AND TglPayr=l_TglProses AND FlgDpPt='	D' AND KdDpPt='JHT' AND COALESCE(FlgAngs,' ')=' '; 
   END;
ELSE
   BEGIN
     INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                              ,Nilai,KdCurr     ,EncNilaiVal                                              ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                  VALUES(l_TglProses,l_NIP,'D'    ,'JHT', ' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_NilaiJHT :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_NilaiJHTVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);	 
   END;
END IF; 

END;
$$ LANGUAGE plpgsql ;

/*
EXEC P_Hit_JHT '2003-01-20',
               'TEST',
               'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
*/
