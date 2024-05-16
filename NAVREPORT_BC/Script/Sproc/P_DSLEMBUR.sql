/******************************************
Name sprocs	: P_dsLembur
Create by	: Suhe
Date		: 12-04-2004
Description	: Hitung Dasar Lembur
Call From	: P_LEMBUR
update      : perapihan source, pendapatan variabel adalah komponen lembur dan bukan pendapatan tetap 
*****************************************/
DROP FUNCTION  P_dsLembur(l_TglProses        DATE,                      
					l_NIP             VARCHAR(10),
					l_UserID          VARCHAR(12),  
					l_MyPass          VARCHAR(128),
					l_usr_id 	  INTEGER,
					OUT l_VarIncome	  DECIMAL(15,2),
					OUT l_FixIncome   DECIMAL(15,2) );
--
CREATE FUNCTION  P_dsLembur(l_TglProses        DATE,                      
					l_NIP             VARCHAR(10),
					l_UserID          VARCHAR(12),  
					l_MyPass          VARCHAR(128),
					l_usr_id 	  INTEGER,
					OUT l_VarIncome	  DECIMAL(15,2),
					OUT l_FixIncome   DECIMAL(15,2) )

AS $$

DECLARE l_S05FlagTHR 	VARCHAR(1);     l_S05TglAkhir   DATE;
    	l_FZ1FlgPiut 	VARCHAR(1);
    	l_VarRound   	VARCHAR(1);     l_V_Round    	INT;
    	l_FZ1FlgLemb 	VARCHAR(1);     l_FZ1TunjAnk 	DECIMAL(15,2);  
    	l_FZ1FlgKJab 	VARCHAR(1);     l_FZ1Caba    	VARCHAR(1);      
    	l_FZ1TglTHRL 	DATE;      	l_FZ1TglTHRN 	DATE;
    	l_FZ1TglHakL	DATE;      	l_FZ1TglHakN 	DATE;
    	l_M15PrdAwl  	DATE;      	l_M15TglKeluar  DATE;     	l_M15kdCaba    VARCHAR(4);
    	l_M15kdKlas  	VARCHAR(4);     l_M15TglMasuk   DATE;     	l_M15KdGlng    VARCHAR(4);
    	l_M15MasaKerja  INT;            l_M15kdCurr  	VARCHAR(4);     l_M15PrdTetap  DATE;
        l_M15GajiTetap  DECIMAL(15,2);  l_M15GajiPerc   DECIMAL(15,2);	l_M15PJK       VARCHAR(1);
    	l_M15TunjIst 	VARCHAR(1);     l_M15TunjAnak   VARCHAR(1);
    	l_M15kdJaba    	VARCHAR(4);
    	l_M15kdKJab  	VARCHAR(4);      
    	l_FZ1TunjIst   	DECIMAL(5,2);
    	l_FZ1FlgTHR  	VARCHAR(1);    	l_FZ1IntvPajak  DECIMAL(9,0); 
        l_FZ1PrOccSupp 	DECIMAL(5,2);
    	l_FZ1NlOccSupp  DECIMAL(15,2);  
        l_mnKurs     	DECIMAL(10,2);  l_FZ1PTKPPay 	DECIMAL(15,2);
    	l_FZ1PTKPDep 	DECIMAL(15,2);
    	l_M15PrdAwal    DATE;       	l_Sumber 	VARCHAR(6);
    	l_FZ1FlgGGol    VARCHAR(1);
        l_FZ1Personalia VARCHAR(1);     l_Test          VARCHAR(10);
        l_Gaji          DECIMAL(15,2);
        l_GajiPokok     DECIMAL(15,2);  l_T18HariKerja  VARCHAR(1);
        l_M10JnsPajak   VARCHAR(1);         
        l_FZ1FlgGlng    VARCHAR(1);     l_FZ1FlgJaba    VARCHAR(1);
        l_FZ1JumlahMangkir INT;      	l_FlgCabang     VARCHAR(1);
        l_mnHari        DECIMAL(4,1);         
        l_GajiRp       	DECIMAL(15,2);  l_GajiVal       DECIMAL(15,2);
        l_M03KdDppt    	VARCHAR(4);     l_M03Singkatan  VARCHAR(10);
        l_M03UsComp    	VARCHAR(1);     l_M03Kolom   	VARCHAR(2);
        l_M03NoAcc     	VARCHAR(10);    l_M03Status     VARCHAR(1);
        l_M03Persen   	DECIMAL(5,2);   l_M03Nilai      DECIMAL(15,2);
        l_M03NoLyr     	INT;        	l_M03KdCurr     VARCHAR(4);
        l_M03Pajak     	VARCHAR(1);     l_M03Flag_N  	VARCHAR(1);
	l_M03ID     	INT; 
        l_M03Flag_R    	VARCHAR(1);     l_TunjIstri     DECIMAL(15,2);
        l_FZ2PrdRapel  	DATE;       	l_FZ2THRharian  VARCHAR(1);        
        l_FZ1JKK       	VARCHAR(1);     l_FZ1JHT        VARCHAR(1);
        l_FZ1JKM       	VARCHAR(1);     l_FZ1JPK        VARCHAR(1);
        l_M15JKK       	VARCHAR(1);     l_M15Level      VARCHAR(1);
        l_M15JHT       	VARCHAR(1);     l_M15JKM        VARCHAR(1);
        l_M15JPK       	VARCHAR(1);     l_Level         VARCHAR(1); 
        l_FZ1IuranSPSI 	DECIMAL(5,2);   l_M15TglSPSI    DATE;
        l_NilaiSPSI    	DECIMAL(15,2);  l_NilaiSPSIVal  DECIMAL(15,2);
        l_NilaiPesangon DECIMAL(15,2);  l_NilaiPesangonVal DECIMAL(15,2);
    	l_JmlAbsen	DECIMAL(4,1);
	l_FlagKhusus 	VARCHAR(1);	
BEGIN 
l_FlagKhusus := 'T';
-- Mengambil Jumlah Hari di FZ1FlDA
l_mnHari := 1;
--
SELECT CASE EXTRACT(MONTH FROM l_TglProses) 
		WHEN 1 THEN  Hari01
		WHEN 2 THEN  Hari02
		WHEN 3 THEN  Hari03
		WHEN 4 THEN  Hari04
		WHEN 5 THEN  Hari05
		WHEN 6 THEN  Hari06
		WHEN 7 THEN  Hari07
		WHEN 8 THEN  Hari08
		WHEN 9 THEN  Hari09
		WHEN 10 THEN Hari10
		WHEN 11 THEN Hari11
		WHEN 12 THEN Hari12  END,
	FlgPiut, FlgLemb, FlgGGol,
	VarRound,
	TunjIst, TunjAnk,
	FlgGlng, FlgKJab, FlgJaba, FlgCaba,
	JumlahMangkir,
	TglTHRL, TglTHRN, TglHakL, TglHakN, FlgTHR,
	IntvPajak,
	PrOccSupp, NlOccSuppp,
	PTKPPay, PTKPDep,
	Personalia,
	JKK, JHT, JKM, JPK,
	IuranSPSI   
INTO    l_mnHari,
	l_FZ1FlgPiut, l_FZ1FlgLemb, l_FZ1FlgGGol,
	l_VarRound,
	l_FZ1TunjIst, l_FZ1TunjAnk,
	l_FZ1FlgGlng, l_FZ1FlgKJab, l_FZ1FlgJaba, l_FZ1Caba,
	l_FZ1JumlahMangkir,
	l_FZ1TglTHRL, l_FZ1TglTHRN, l_FZ1TglHakL, l_FZ1TglHakN, l_FZ1FlgTHR,
	l_FZ1IntvPajak,
	l_FZ1PrOccSupp, l_FZ1NlOccSupp,
	l_FZ1PTKPPay, l_FZ1PTKPDep,
	l_FZ1Personalia,
	l_FZ1JKK, l_FZ1JHT, l_FZ1JKM, l_FZ1JPK, 
	l_FZ1IuranSPSI   
FROM FZ1FLDA; 
--

---
l_S05TglAkhir := ' ';
l_S05FlagTHR  := ' ';
--
-- Ambil Tgl.Akhir Proses Payroll
SELECT TglPosting,    FlagTHR
INTO   l_S05TglAkhir, l_S05FlagTHR
FROM S05PSTD
WHERE NIP=l_NIP 
ORDER BY NIP,TglPosting;

l_JmlAbsen := 0; 
--
l_JmlAbsen := COALESCE(fn_JmlAbsn(l_NIP,l_S05TglAkhir,l_TglProses),0); 
-- Jika Nilai Absensi hari kerjanya ada maka hari kerja diambil dari Absensi
IF l_JmlAbsen<>999.9 THEN 
    BEGIN
       l_mnHari := l_JmlAbsen; 
    END;
END IF; 

SELECT  PrdTetap,
        fn_Kpusat(M15.NIP,M15.EncGajiPerc,l_MyPass) ::DECIMAL(15,2),
        fn_Kpusat(M15.NIP,M15.EncGajiTetap,l_MyPass) ::DECIMAL(15,2),
        TglMasuk,
        KdGlng,
	kdCaba,
	kdKJab,
	kdJaba,
	kdCurr,
	kdKlas
INTO    l_M15PrdTetap,
        l_M15GajiPerc,
        l_M15GajiTetap,
        l_M15TglMasuk,
        l_M15KdGlng,
	l_M15kdCaba,
	l_M15kdKJab,
	l_M15kdJaba,
	l_M15kdCurr,
	l_M15kdKlas	
FROM M15PEGA M15
WHERE NIP=l_NIP;
--
SELECT fn_GajiPokok(l_FZ1FlgGGol,
		   l_TglProses,
		   l_M15PrdTetap,
		   l_M15GajiPerc,
		   l_M15GajiTetap,
		   l_NIP,
		   l_M15TglMasuk,
		   l_mnHari,
		   l_M15KdGlng) 
INTO l_GajiPokok; 								   
--
-- Nilai Kurs
l_mnKurs := 1;
l_mnKurs := fn_GetKurs(l_M15kdCurr,l_TglProses); 
-- Jenis Pajak
SELECT JnsPajak
INTO   l_M10JnsPajak
FROM  M10KLAS 
WHERE Kode=l_M15kdKlas;

-- Ambil Nilai yg diperlukan u/Insert S02DGAJ
l_GajiRp  := fn_Vround(l_GajiPokok*l_mnKurs);
l_GajiVal := fn_Vround(l_GajiPokok);
--
SELECT  P_M03AllField ('D', 'BSAL' )
INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
	l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr, l_M03ID ;

l_FixIncome := 0;
l_VarIncome := 0;

-- BY PEGGY 2006 12 18 : PENDAPATAN TETAP DASAR M03STATUS DAN BUKAN KOLOM 1 
--IF l_M03Kolom='1' AND l_M03NoLyr=1
IF l_M03Status='1' AND l_M03NoLyr=1 THEN 
   BEGIN
     l_FixIncome := l_GajiRp;
   END;
END IF; 
--
--IF (l_M03Kolom BETWEEN 3 AND 6) AND l_M03NoLyr=1
IF l_M03Status='0' AND l_M03NoLyr=1 THEN 
   BEGIN
     l_VarIncome := l_GajiRp;
   END;
END IF; 
---
END;
$$ LANGUAGE plpgsql ;
 
/*
EXEC P_dsLembur l_TglProses='2004-01-20',
              l_TglAkhir='2003-12-20',
              l_FlagKhusus='T',
              l_NIP='02',
              l_BerhakLemb=1,
              l_KdArea='A006',
              l_KdCaba='SMGA',
              l_KdUUsa='U002',
              l_KdUker='2021-021', 
              l_KdJaba='ACCT',
              l_Nama='FARID',
              l_UserID='WET',  
              l_MyPass='Copyright, 1988 (c) Microsoft Corporation, All rights reserved'


*/
