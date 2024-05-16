/****************************************
Name sprocs	: P_Pend_HarianTHR
Create by	: Wati
Date		: 02-07-2003
Description	: Proses Hitung Pendapatan/Potongan harian THR
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Pend_HarianTHR(l_TglProses  DATE,
					     l_TglAkhir   DATE,
					     l_NIP        VARCHAR(10),
					     l_GajiRp     DECIMAL(15,2),
					     l_MyPass     VARCHAR(128),
					     INOUT l_TotalFix   DECIMAL(15,2)) ;
--
CREATE FUNCTION P_Pend_HarianTHR(l_TglProses  DATE,
					     l_TglAkhir   DATE,
					     l_NIP        VARCHAR(10),
					     l_GajiRp     DECIMAL(15,2),
					     l_MyPass     VARCHAR(128),
					     INOUT l_TotalFix   DECIMAL(15,2)) 

AS $$ 
DECLARE l_FZ1FlgGlng   	VARCHAR(1);         
	l_FZ1FlgJaba   	VARCHAR(1);
	l_FZ1FlgKJab 	VARCHAR(1);        	
	l_FZ1Caba    	VARCHAR(1);      
	l_mlDasarKelompok   	VARCHAR(1);         
	l_mlDasarKelompokAdv	VARCHAR(1);         

BEGIN 
SELECT	FlgGlng,      FlgKJab,      FlgJaba,      FlgCaba
INTO    l_FZ1FlgGlng, l_FZ1FlgKJab, l_FZ1FlgJaba, l_FZ1Caba
FROM FZ1FLDA ; 

--l_mlDasarKelompokAdv := SUBSTR((SELECT StringFlag FROM FZ2FLDA LIMIT 1),10,1) ;   

SELECT SUBSTR(StringFlag,10,1)  
INTO   l_mlDasarKelompokAdv 
FROM FZ2FLDA LIMIT 1; 

l_mlDasarKelompok := CASE WHEN l_FZ1FlgGlng = 'Y' OR l_FZ1FlgKJab = 'Y' OR 
				 l_FZ1FlgJaba = 'Y' OR l_FZ1Caba = 'Y' THEN 
				'Y' 
			ELSE 
				'T' 
			END; 

SELECT COALESCE(l_TotalFix,0)+(COALESCE(SUM(TBL.NILAI),0) ) 
INTO l_TotalFix
FROM 
(--TBL 
SELECT T02.NIP,             
    CASE WHEN l_mlDasarKelompok = 'Y' THEN 
			fn_NilaiPend_PotFlgTr(l_TglProses,T02.NIP,T02.FlgDpPt,T02.KdDpPt,0,0,T02.JmlAbsn,l_GajiRp,CASE WHEN T02.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,M03.KdCurr) / 
	-- KALAU D TRAL DIBAGI 25 
				(CASE WHEN T02.FLGDPPT || T02.KDDPPT <> 'DTRAL' 
					THEN 1
					ELSE 25 
					END)  
		ELSE 
			0 
		END +
			fn_NilaiPend_PotAdvFlgTr(l_TglProses,T02.NIP,T02.FlgDpPt,T02.KdDpPt,0,0,T02.JmlAbsn,l_GajiRp,CASE WHEN T02.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,M03.KdCurr) / 
	-- KALAU D TRAL DIBAGI 25 
				(CASE WHEN T02.FLGDPPT || T02.KDDPPT <> 'DTRAL' 
					THEN 1
					ELSE 25 
					END)  AS Nilai 
FROM T02ABSN T02
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=T02.FlgDpPt AND M03.KdDpPt= T02.KdDpPt
LEFT JOIN M46PPKH M46
ON M46.FlgDpPt=T02.FlgDpPt AND M46.KdDpPt= T02.KdDpPt
WHERE T02.NIP=l_NIP AND M46.FlgDpPt IS NULL AND
      T02.Tanggal>l_TglAkhir AND T02.Tanggal<=l_TglProses AND
      T02.FlgDpPt='D' AND M03.Status='1' 
) TBL ; 

END ; 
$$ LANGUAGE plpgsql ;


/*

SELECT P_Pend_HarianTHR ('2003-01-20'::DATE,
                      '2002-12-20'::DATE,
                      'TEST',
                      2000000,
                      'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') ;

*/
