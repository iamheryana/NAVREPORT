/****************************************
Name sprocs	: P_Pend_RapelGaji
Create by	: wati
Date		: 17-06-2003
Description	: Proses Pendapatan Rapel Gaji
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Pend_RapelGaji(l_TglProses	  DATE,
					    l_FZ2PrdRapel DATE,
					    l_GajiRp      DECIMAL(15,2),
					    l_NIP         VARCHAR(10),
					    l_MyPass      VARCHAR(128),
					    l_S01_ID      INT);      
--
CREATE FUNCTION P_Pend_RapelGaji(l_TglProses	  DATE,
					    l_FZ2PrdRapel DATE,
					    l_GajiRp      DECIMAL(15,2),
					    l_NIP         VARCHAR(10),
					    l_MyPass      VARCHAR(128),
					    l_S01_ID      INT)      

RETURNS VOID 
AS $$

DECLARE l_RapelGaji     DECIMAL(15,2);
        l_RapelGajiVal  DECIMAL(15,2);
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
--
BEGIN 
l_RapelGaji := 0; 
---
SELECT fn_Vround(TBL.RapelGaji)
INTO   l_RapelGaji
FROM
(--TBL
SELECT S02.NIP,SUM(l_GajiRp-(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass)) ::DECIMAL(15,2)) AS RapelGaji
FROM S02DGAJ S02
WHERE NIP=l_Nip AND KdDpPt='BSAL' AND
      (DATE_TRUNC('month',S02.TglPayr) >= DATE_TRUNC('month',l_FZ2PrdRapel)) AND
      (DATE_TRUNC('month',S02.TglPayr) < DATE_TRUNC('month',l_TglProses))
GROUP BY S02.NIP
) TBL;

-- Ambil Nilai Master Pandapatan dan Potongan
SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
	fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
from  P_M03AllField ('D', 'RPLG' ) fx
INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
	l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;  
-- Get Kurs
l_RapelGajiVal := COALESCE(fn_Vround(l_RapelGaji/fn_GetKurs(l_M03KdCurr,l_TglProses)),0); 
---
IF l_RapelGaji<>0 THEN 
   BEGIN
	-- Insert/Update Rapel Gaji
	IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='RPLG') IS NOT NULL THEN 
	   BEGIN
	     UPDATE S02DGAJ
	     SET KdCurr=l_M03KdCurr,
	         EncNilaiVal =fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_RapelGajiVal) ::VARCHAR(17),l_MyPass),
	         EncNilai=fn_kcabang(l_NIP,(fn_kpusat(l_NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_RapelGaji :: VARCHAR(17)), l_MyPass)
	     WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='RPLG';	
	   END; 
	ELSE
	   BEGIN
	     INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                               ,Nilai,KdCurr     ,EncNilaiVal                                               ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
	                  VALUES(l_TglProses,l_NIP,'D'    ,'RPLG',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_RapelGaji :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_RapelGajiVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);	 
	   END; 
	END IF;    
   END;
END IF;    
END;
$$ LANGUAGE plpgsql ;       
/*
exec P_Pend_RapelGaji '2003-05-20',
                      '2003-01-20',
                      6000000,
                      '03',
                      'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
*/
