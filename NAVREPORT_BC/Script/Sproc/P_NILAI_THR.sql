/****************************************
Name sprocs	: P_Nilai_THR
Create by	: wati
Date		: 17-06-2003
Description	: Proses Nilai THR
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Nilai_THR(l_TglProses    DATE,                       
					l_TglTHR       DATE,
					l_TglMasuk     DATE,
					l_NIP          VARCHAR(10),
					l_FZ2THRHarian VARCHAR(1),
					l_Pendapatan   DECIMAL(15,2),
					l_BlnTHR       DECIMAL(5,2),
					l_KdDpPt       VARCHAR(4),
					l_MyPass       VARCHAR(128),
					l_S01_ID	INT);
--
CREATE FUNCTION P_Nilai_THR(l_TglProses    DATE,                       
					l_TglTHR       DATE,
					l_TglMasuk     DATE,
					l_NIP          VARCHAR(10),
					l_FZ2THRHarian VARCHAR(1),
					l_Pendapatan   DECIMAL(15,2),
					l_BlnTHR       DECIMAL(5,2),
					l_KdDpPt       VARCHAR(4),
					l_MyPass       VARCHAR(128),
					l_S01_ID	INT)
RETURNS VOID
AS $$ 
DECLARE l_Proporsional  DECIMAL(10,4);
        l_NilaiTHR      DECIMAL(15,2);
        l_NilaiTHRVal   DECIMAL(15,2);
        l_mnKurs        DECIMAL(10,2);
        l_M03Singkatan  VARCHAR(10);
        l_M03UsComp     VARCHAR(1);
        l_M03Kolom      VARCHAR(2);
        l_M03NoAcc      VARCHAR(10);
        l_M03Status     VARCHAR(1);
        l_M03Persen     DECIMAL(5,2);
        l_M03Nilai      DECIMAL(15,2);
	l_M03NoLyr      INT; 
	l_M03KdCurr     VARCHAR(4);
	l_M03ID        INT;
BEGIN   
-- Check 
IF (l_TglMasuk + INTERVAL '12 MONTHS') > l_TglTHR THEN 
--IF DATEDIFF(MONTH,l_TglMasuk,l_TglTHR)<12
   BEGIN
     IF l_FZ2THRHarian='Y' THEN 
        BEGIN
          l_Proporsional := ((l_TglTHR - l_TglMasuk)/365 );--CONVERT(DECIMAL(4,0),(DATEDIFF(DAY,l_TglMasuk,l_TglTHR)))/365 ;
        END;
     ELSE
        BEGIN
          l_Proporsional := EXTRACT(MONTH FROM AGE(l_TglTHR, l_TglMasuk)) / 12;
        END;
     END IF;  
   END;
ELSE 
   BEGIN
     l_Proporsional := 1 ;
   END ;
END IF; 
-- Ambil Nilai Master Pandapatan dan Potongan
SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
	fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
from  P_M03AllField ('D', l_KdDpPt ) fx
INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
	l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ; 
-- Ambil Nilai Kurs,Hitung Nilai THR Rp & Original Currency
l_NilaiTHR    := COALESCE(fn_Vround(l_Proporsional*l_BlnTHR*l_Pendapatan),0) ;
l_NilaiTHRVal := COALESCE(fn_Vround((l_Proporsional*l_BlnTHR*l_Pendapatan)/fn_GetKurs(l_M03KdCurr,l_TglProses)),0) ;

-- Insert/UpDATERapel Gaji
IF (SELECT count(NIP) FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_KdDpPt) > 0 THEN 
   BEGIN
     UPDATE S02DGAJ
     SET KdCurr=l_M03KdCurr,
	 EncNilaiVal =fn_kcabang(l_NIP,(((fn_kpusat(l_NIP,EncNilaiVal,l_MyPass)) :: DECIMAL(15,2))+l_NilaiTHRVal) :: VARCHAR(17),l_MyPass),
	 EncNilai=fn_kcabang(l_NIP,((fn_kpusat(l_NIP, EncNilai, l_MyPass)) :: Decimal(15,2)+l_NilaiTHR) :: VARCHAR(17), l_MyPass)
     WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_KdDpPt ;
   END;
ELSE
   BEGIN
     INSERT INTO S02DGAJ(TglPayr   ,NIP ,FlgDpPt,KdDpPt ,FlgAngs,Singkatan ,   EncNilai                                                  ,Nilai,KdCurr    ,EncNilaiVal                                               ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                  VALUES(l_TglProses,l_NIP,'D'    ,l_KdDpPt,' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_NilaiTHR :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_NilaiTHRVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0,        l_S01_ID) ;  
   END;
END IF; 
END ; 
$$ LANGUAGE plpgsql ;     
/*
EXEC P_Nilai_THR '2003-05-20',                       
                 '2003-01-20',
                 '2002-01-02',
                 '03',
                  Y,
                  1000000,
                  1,
                  'THR',
                  'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
*/ 
