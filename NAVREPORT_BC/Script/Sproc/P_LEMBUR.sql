/******************************************
Name sprocs	: P_Lembur
Create by	: wati
Date		: 17-06-2003
Updated Date    : 12-09-2003
Description	: Proses Lembur
Call From	: Main sprocs
*****************************************/
DROP FUNCTION  P_Lembur(l_TglProses  DATE,
				     l_TglAkhir   DATE,
				     l_FlagKhusus VARCHAR(1),
				     l_NIP        VARCHAR(10),
				     l_BerhakLemb INT,
				     l_KdArea     VARCHAR(4),
				     l_KdCaba     VARCHAR(4),
				     l_KdUUsa     VARCHAR(4),
				     l_KdUker     VARCHAR(8), 
				     l_KdJaba     VARCHAR(4),
				     l_Nama       VARCHAR(25),
				     l_UserID     VARCHAR(12),  
				     l_MyPass     VARCHAR(128),
				     l_S01_ID     INT) ;
--
CREATE FUNCTION  P_Lembur(l_TglProses  DATE,
				     l_TglAkhir   DATE,
				     l_FlagKhusus VARCHAR(1),
				     l_NIP        VARCHAR(10),
				     l_BerhakLemb INT,
				     l_KdArea     VARCHAR(4),
				     l_KdCaba     VARCHAR(4),
				     l_KdUUsa     VARCHAR(4),
				     l_KdUker     VARCHAR(8), 
				     l_KdJaba     VARCHAR(4),
				     l_Nama       VARCHAR(25),
				     l_UserID     VARCHAR(12),  
				     l_MyPass     VARCHAR(128),
				     l_S01_ID     INT) 
RETURNS VOID 
AS $$

DECLARE l_FZ1PersentasiLembur	 DECIMAL(5,2);	
        l_FZ1LimWrk1             INT;        	l_FZ1LimLbr1             INT;
        l_FZ1LimLbr2             INT;        
	l_FZ1Pembilang		 DECIMAL(15,2); l_FZ1Penyebut		 REAL;
        l_M47TglAwal             DATE;       	l_M47TglAkhir            DATE;
        l_VarIncome              DECIMAL(15,2); l_FixIncome              DECIMAL(15,2);
        l_Pendapatan             DECIMAL(15,2); l_UMR                    DECIMAL(15,2);
        l_DasarLembur            DECIMAL(15,2); l_RID_Dasar              INT;
        l_T01JamLemb             DECIMAL(4,2);  l_T01JmlUMkn             DECIMAL(15,2);
        l_T01FlgLemb             VARCHAR(1);    l_T01Flag                INT;
        l_TotJamLembur           DECIMAL(7,2);  l_FZ1MaxJmlLemb          DECIMAL(3,0);
        l_TotNilLembur           DECIMAL(15,2); l_LembK1                 DECIMAL(4,2);
        l_LembK2                 DECIMAL(4,2);  l_LembL1                 DECIMAL(4,2);
        l_LembL2                 DECIMAL(4,2);  l_LembL3                 DECIMAL(4,2);
        l_M01Keterangan          VARCHAR(20);   l_M08NmCaba              VARCHAR(40);
        l_M02Keterangan          VARCHAR(20);   l_M17Keterangan          VARCHAR(20);
        l_M04Singkatan           VARCHAR(10);   l_LOOP_T01               REFCURSOR; 
        l_FZ1RteLbr1             INT;        	l_FZ1RteLbr2             INT;
        l_M03Singkatan           VARCHAR(10);   l_M03UsComp              VARCHAR(1);
        l_M03Kolom               VARCHAR(2);    l_M03NoAcc               VARCHAR(10);
        l_M03Status              VARCHAR(1);    l_M03Persen              DECIMAL(5,2);
        l_M03Nilai               DECIMAL(15,2);	l_M03NoLyr               INT;
	l_M03ID        		 INT;
	l_M03KdCurr              VARCHAR(4);    l_UangMakanVal           DECIMAL(15,2);
        l_TotNilLemburVal        DECIMAL(15,2); l_T01Tanggal             DATE;
	l_M01UMR	 	 VARCHAR(4);    l_AkumulasiJamLbr        DECIMAL(7,2);
        l_SelisihJamLbr          DECIMAL(5,2);  l_JamLbrBayar            DECIMAL(7,2);
        l_Pendek                 VARCHAR(1);    l_M10Harian              INT;
        l_FZ1PembilangB          DECIMAL(3,0);  l_FZ1PenyebutB	         REAL;
	l_FZ1PembilangH          DECIMAL(3,0);  l_FZ1PenyebutH	         REAL;
	l_VarIncome1		 DECIMAL(15,2);	l_FixIncome1		 DECIMAL(15,2);
	l_FZ2FlgPrsLbr		 VARCHAR(1);
	l_FZ1Kode		 VARCHAR(2); 
	l_FZ1LimLIst1 		 INT;
	l_FZ1RateLIst1		 Decimal(5,2); 
	l_FZ1RateLIst2		 Decimal(5,2);  l_SqlTeks		 VARCHAR(1000); 
        l_LembI1                 DECIMAL(4,2);  l_LembI2                 DECIMAL(4,2);

DECLARE l_GAJI			DECIMAL(15,2);
      
BEGIN 
SELECT PersentasiLembur,	
	LimWrk1,         LimLbr1,        LimLbr2,
	RteLbr1,         RteLbr2,
	Pembilang,       Penyebut,
	PembilangH,      PenyebutH,
	HariKerja,       Kode,
	LimLIst1,        RateLIst1,      RateLIst2
INTO l_FZ1PersentasiLembur,
	l_FZ1LimWrk1,    l_FZ1LimLbr1,   l_FZ1LimLbr2, 
	l_FZ1RteLbr1,    l_FZ1RteLbr2,
	l_FZ1PembilangB, l_FZ1PenyebutB,
	l_FZ1PembilangH, l_FZ1PenyebutH,
	l_FZ1MaxJmlLemb, l_FZ1KODE,
	l_FZ1LimLIst1,   l_FZ1RateLIst1, l_FZ1RateLIst2
FROM FZ1FLDA;

l_LembK1 := 0; l_LembK2 := 0; 
l_LembL1 := 0; l_LembL2 := 0; l_LembL3 := 0;
l_AkumulasiJamLbr:=0 ;

-- Parameter Lembur dari M47PRLR
SELECT TglAwal,      TglAkhir
INTO   l_M47TglAwal, l_M47TglAkhir
FROM M47PRLR
WHERE TglProses=l_TglProses ;
--WHERE TglProses<=l_TglProses

-- Ambil Flag Harian/Bulanan Pegawai
SELECT M10.Harian
INTO   l_M10Harian
FROM M15PEGA M15
INNER JOIN M10KLAS M10
ON M10.Kode=M15.KdKlas
WHERE M15.NIP=l_NIP;

-- Nilai UMR,Keterangan Area
-- C1
l_UMR := 0;
--
SELECT M01.Keterangan,  M01.UMR 	
INTO   l_M01Keterangan, l_M01UMR
FROM M01AREA M01
WHERE Kode=l_KdArea; 
--
SELECT M21.UMR
INTO   l_UMR 
FROM M21UMRE M21
WHERE M21.Daerah=l_M01UMR AND TglMulai<=l_TglProses; 
--
-- Keterangan Cabang
SELECT NmCaba 
INTO   l_M08NmCaba
FROM M08HCAB
WHERE KdCaba=l_KdCaba;

-- Keterangan Unit Usaha
SELECT Keterangan 
INTO   l_M02Keterangan
FROM M02UUSA
WHERE Kode=l_KdUUsa;

-- Keterangan Unit Kerja
SELECT Keterangan 
INTO   l_M17Keterangan
FROM M17UKER
WHERE KdUker=l_KdUker;

SELECT Singkatan 
INTO   l_M04Singkatan 
FROM M04HJAB
WHERE Kode=l_KdJaba;

l_FixIncome  := 0;
l_VarIncome  := 0;
l_VarIncome1 := 0;
l_FixIncome1 := 0;

-- Jika di detail belum ada basic salary
IF (SELECT TglPayr FROM S02DGAJ WHERE TglPayr=l_TglProses AND NIP=l_NIP AND FlgDpPt='D' AND KdDpPt='BSAL') IS NULL THEN 
   BEGIN
     --
     SELECT P_dsLembur(l_TglProses,
                       l_NIP      ,
                       l_UserID   ,
                       l_MyPass) 
     INTO l_VarIncome1, l_FixIncome1;
     --  
   END;
END IF;    
--*

-- PEGAWAI BULANAN 
IF l_M10HARIAN = 0 THEN 
BEGIN 
	-- Pendapatan Kolom=1 (FixIncome)
	-- A1
	SELECT COALESCE(TBL.FixIncome,0) 
	INTO l_FixIncome
	FROM
	(
	SELECT S02.NIP,SUM(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
	FROM S02DGAJ S02
-- BY PEGGY 2006 12 18 : PENDAPATAN TETAP DASAR M03.STATUS DAN BUKAN KOLOM 1 LAGI 
	INNER JOIN M03DPPT M03 
	ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
	WHERE S02.NIP=l_NIP AND M03.STATUS = '1' AND S02.TglPayr=l_TglProses AND S02.NoLyr=1
-- 	WHERE S02.NIP=l_NIP AND S02.Kolom='1' AND S02.TglPayr=l_TglProses AND S02.NoLyr=1
	GROUP BY S02.NIP
	) TBL;
	--
	l_FixIncome := COALESCE(l_FixIncome,0)+COALESCE(l_FixIncome1,0);
	--
	-- Pendatatan Kolom=3-6 (VarIncome)
	--
	SELECT COALESCE(TBL.VarIncome,0)
	INTO l_VarIncome
	FROM
	(
	SELECT S02.NIP,SUM(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS VarIncome
	FROM S02DGAJ S02
	INNER JOIN M03DPPT M03 
	ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
--	WHERE S02.NIP=l_NIP AND (S02.Kolom BETWEEN 3 AND 6) AND S02.TglPayr=l_TglProses AND S02.NoLyr=1 AND
--	     (S02.KdDpPt NOT IN ('JHT','JPK','JKK','JKM'))  
	WHERE S02.NIP=l_NIP AND M03.STATUS = '0' AND S02.TglPayr=l_TglProses AND S02.NoLyr=1 AND
	     (S02.KdDpPt NOT IN ('JHT','JPK','JKK','JKM'))  
	GROUP BY S02.NIP
	) TBL;
	--
	l_VarIncome := COALESCE(l_VarIncome,0)+COALESCE(l_VarIncome1,0); 
	--
	-- B1
	l_Pendapatan := ((l_FixIncome+l_VarIncome)*l_FZ1PersentasiLembur)/100; 
END ; 
ELSE 
-- PEGAWAI HARIAN 
BEGIN 
	-- Pendapatan Kolom=1 (FixIncome)
	-- A1
	--
	SELECT CASE WHEN l_TglProses<M15.PrdTetap 
			THEN fn_KPusat(l_NIP,M15.EncGAJIPERC,l_MyPass) ::DECIMAL(15,2)
		    WHEN l_TglProses>=M15.PrdTetap
			THEN fn_KPusat(l_NIP,M15.EncGAJITETAP,l_MyPass) ::DECIMAL(15,2)
			END 
	INTO l_GAJI 			
	FROM M15PEGA M15	
	WHERE M15.NIP = l_NIP ; 

	SELECT COALESCE(TBL.FixIncome,0)
	INTO l_FixIncome
	FROM
	(
	SELECT S02.NIP,SUM(l_GAJI) AS FixIncome
	FROM S02DGAJ S02
	INNER JOIN M15PEGA M15
	ON M15.NIP = S02.NIP 
	INNER JOIN M03DPPT M03  
	ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
-- BY PEGGY 2006 12 18 : PENDAPATAN TETAP DASAR M03.STATUS DAN BUKAN KOLOM 1 LAGI 
	WHERE S02.NIP=l_NIP AND M03.Status='1' AND S02.TglPayr=l_TglProses AND S02.NoLyr=1
--	WHERE S02.NIP=l_NIP AND S02.Kolom='1' AND S02.TglPayr=l_TglProses AND S02.NoLyr=1
		AND S02.FLGDPPT + S02.KDDPPT = 'DBSAL' 
	GROUP BY S02.NIP
	UNION ALL 
	SELECT S02.NIP,SUM(M03.NILAI) AS FixIncome
	FROM S02DGAJ S02
	INNER JOIN M03DPPT M03 
	ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
-- BY PEGGY 2006 12 18 : PENDAPATAN TETAP DASAR M03.STATUS DAN BUKAN KOLOM 1 LAGI 
	WHERE S02.NIP=l_NIP AND M03.Status='1' AND S02.TglPayr=l_TglProses AND S02.NoLyr=1
--	WHERE S02.NIP=l_NIP AND S02.Kolom='1' AND S02.TglPayr=l_TglProses AND S02.NoLyr=1
		AND S02.FLGDPPT + S02.KDDPPT <> 'DBSAL' AND M03.JNDPPT = 'H'
	GROUP BY S02.NIP

	) TBL;
	--
	l_FixIncome := COALESCE(l_FixIncome,0)+COALESCE(l_FixIncome1,0);
	--
	-- Pendatatan Kolom=3-6 (VarIncome)
	--
	SELECT COALESCE(TBL.VarIncome,0)
	INTO   l_VarIncome 
	FROM
	(
	SELECT S02.NIP,SUM(M03.NILAI) AS VarIncome
	FROM S02DGAJ S02
	INNER JOIN M03DPPT M03 
	ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
--	WHERE S02.NIP=l_NIP AND (S02.Kolom BETWEEN 3 AND 6) AND S02.TglPayr=l_TglProses AND S02.NoLyr=1 AND
	WHERE S02.NIP=l_NIP AND M03.Status='0' AND S02.TglPayr=l_TglProses AND S02.NoLyr=1 AND
	     (S02.KdDpPt NOT IN ('JHT','JPK','JKK','JKM'))  
		AND M03.JNDPPT = 'H' 
	GROUP BY S02.NIP
	) TBL;
	--
	l_VarIncome := COALESCE(l_VarIncome,0)+COALESCE(l_VarIncome1,0);
	--
	-- B1
	l_Pendapatan := ((l_FixIncome+l_VarIncome)*l_FZ1PersentasiLembur)/100;
	
	-- umr dibagi 25 
	l_UMR := l_UMR / 25 ;
END ; 
END IF; 

-- Nilai Maximum
IF (l_FixIncome>=l_Pendapatan) AND (l_FixIncome>=l_UMR) THEN 
   BEGIN
      l_DasarLembur := l_FixIncome;
      l_RID_Dasar   := 1;
   END; 
ELSIF l_Pendapatan>=l_UMR THEN 
   BEGIN
      l_DasarLembur := l_Pendapatan;
      l_RID_Dasar   := 2;
   END;
ELSE
   BEGIN   
     l_DasarLembur  := l_UMR;
     l_RID_Dasar    := 3;
   END;
END IF;    

-- New by Suhe Jika di FZ2FLDA ada flag 'L' Maka proses lembur sama dengan Proses Khusus
SELECT SUBSTR(StringFlag,4,1)
INTO l_FZ2FlgPrsLbr
FROM FZ2FLDA
WHERE Kode=l_FZ1KODE; 
----
-- ******************************** PROSES KHUSUS **************************************
IF l_FlagKhusus='Y' THEN 
   BEGIN
     ---Hapus Dulu data S04LEMB sebelum diisi dengan S04 Baru dari T01
     DELETE FROM S04LEMB
     WHERE NIP=l_NIP AND (TglPayr BETWEEN l_M47TglAwal AND l_M47TglAkhir); 		
     ----
     l_SqlTeks := 'SELECT T01.JamLemb,T01.JmlUMkn,T01.FlgLemb,T01.Flag,T01.TgDocu
		   FROM T01LEMB T01
		   LEFT JOIN M46PPKH M46
		   ON M46.FlgDpPt=''D'' AND M46.KdDpPt=''LEMB''
		   WHERE T01.NIP=l_NIP AND M46.FlgDpPt IS NOT NULL AND
		         T01.TgDocu >= l_M47TglAwal AND T01.TgDocu <= l_M47TglAkhir';
   END;
ELSIF  l_FZ2FlgPrsLbr='L' THEN 
-- ******************************** PROSES BULANAN (RANGE LEMBUR) **************************************
   BEGIN
     ---Hapus Dulu data S04LEMB sebelum diisi dengan S04 Baru dari T01
     DELETE FROM S04LEMB
     WHERE NIP=l_NIP AND (TglPayr BETWEEN l_M47TglAwal AND l_M47TglAkhir);		
     ----
     l_SqlTeks := 'SELECT T01.JamLemb,T01.JmlUMkn,T01.FlgLemb,T01.Flag,T01.TgDocu
	           FROM T01LEMB T01
	           LEFT JOIN M46PPKH M46
	           ON M46.FlgDpPt=''D'' AND M46.KdDpPt=''LEMB''
	           WHERE T01.NIP=l_NIP AND M46.FlgDpPt IS NULL AND
		         T01.TgDocu >= l_M47TglAwal AND T01.TgDocu <= l_M47TglAkhir';
   END;
ELSE
-- ******************************** PROSES BULANAN (RANGE PAYROLL)**************************************
   BEGIN
     ---Hapus Dulu data S04LEMB sebelum diisi dengan S04 Baru dari T01
     DELETE FROM S04LEMB
     WHERE NIP=l_NIP AND TglPayr>l_TglAkhir AND TglPayr<=l_TglProses;
     ----
     l_SqlTeks := 'SELECT T01.JamLemb,T01.JmlUMkn,T01.FlgLemb,T01.Flag,T01.TgDocu
		   FROM T01LEMB T01
		   LEFT JOIN M46PPKH M46
		   ON M46.FlgDpPt=''D'' AND M46.KdDpPt=''LEMB''
		   WHERE T01.NIP=l_NIP AND M46.FlgDpPt IS NULL AND
		         T01.TgDocu > l_TglAkhir AND T01.TgDocu <= l_TglProses';
   END;
END IF;    
--*
--*****************************  Mulai Proses Data ************************
     OPEN l_LOOP_T01 FOR EXECUTE l_SqlTeks; 
     LOOP 
	FETCH l_LOOP_T01 INTO l_T01JamLemb,l_T01JmlUMkn,l_T01FlgLemb,l_T01Flag,l_T01Tanggal;
	IF NOT FOUND THEN
	   EXIT ;
	END IF;
     
        IF l_FZ1MaxJmlLemb=0 OR l_BerhakLemb=0 THEN 
            BEGIN
               l_JamLbrBayar  := 0;
               l_TotNilLembur := 0;
            END;
         ELSE
            BEGIN  
              -- Akumulasi Jam Lembur
              l_AkumulasiJamLbr := COALESCE(l_AkumulasiJamLbr,0)+l_T01JamLemb ;

              -- Check Akumulasi
              IF l_AkumulasiJamLbr<=l_FZ1MaxJmlLemb THEN 
                 BEGIN
                   l_JamLbrBayar := l_T01JamLemb;
                 END;
              ELSE
                 BEGIN  
                   l_SelisihJamLbr := l_AkumulasiJamLbr-l_FZ1MaxJmlLemb;
                   --
                   IF l_T01JamLemb-l_SelisihJamLbr>0 THEN 
                      BEGIN
                         l_JamLbrBayar := l_T01JamLemb-l_SelisihJamLbr;
                      END;
                   ELSE
                      BEGIN
                         l_JamLbrBayar := 0; 
                      END;    
                   END IF;    
                 END;
              END IF; 
            END;
         END IF;      

         -- Check Total Lembur
	 l_TotJamLembur := 0;
	 l_LembK1       := 0;
	 l_LembK2       := 0;
	 l_LembL1       := 0;
	 l_LembL2       := 0;
	 l_LembL3       := 0;
	 l_Pendek       :='0';
	 l_LembI1       := 0;
	 l_LembI2       := 0;
	 
         IF l_JamLbrBayar>0 THEN 
            BEGIN
              SELECT TJL.Pendek,
                     TJL.TotJamLembur,
                     TJL.LembK1,
                     TJL.LembK2,
                     TJL.LembL1,
                     TJL.LembL2,
                     TJL.LembL3,
                     TJL.LembI1,
                     TJL.LembI2
              INTO   l_Pendek,
                     l_TotJamLembur,
                     l_LembK1,
                     l_LembK2,
                     l_LembL1,
                     l_LembL2,
                     l_LembL3, 
                     l_LembI1,
                     l_LembI2
              FROM tf_TotJamLembur(l_JamLbrBayar,l_T01FlgLemb,l_T01Flag) as TJL; 
              
              -- Pegawai Harian/Bulanan
              IF l_M10Harian=1 THEN 
                 BEGIN
                    l_TotNilLembur := fn_VRound((l_DasarLembur*l_TotJamLembur*l_FZ1PembilangH)/l_FZ1PenyebutH);
                 END;
              ELSE
                 BEGIN
                    l_TotNilLembur := fn_VRound((l_DasarLembur*l_TotJamLembur*l_FZ1PembilangB)/l_FZ1PenyebutB);
                 END;
              END IF;    
            END;
         ELSE
            BEGIN 
               l_TotNilLembur := 0;
            END;
         END IF;     
         -- Update Summary  
         IF (SELECT NIP FROM S04LEMB WHERE NIP=l_NIP AND TglPayr=l_T01Tanggal) IS NULL THEN 
            BEGIN
              INSERT INTO S04LEMB(TglPayr,     NIP,  Nama,  KdArea,  KdCaba,  KdUUsa,  KdUker,  Area,           Caba,       UUsa,           Uker,           KdJaba,  Skjaba,        LembK1,  LembK2,  LembL1,  LembL2,  LembL3,  TtlLemb,     Nilai                      ,Perhitungan1, Perhitungan2, Perhitungan3, DasarLembur, Pendek,   PostId,  PostDate,     UpdTime,     LembI1, LembI2,   VERSION, CREATED_BY, CREATED_ON,   UPDATED_BY, UPDATED_ON) 
                          VALUES (l_T01Tanggal,l_NIP,l_Nama,l_KdArea,l_KdCaba,l_KdUUsa,l_KdUker,l_M01Keterangan,l_M08NmCaba,l_M02Keterangan,l_M17Keterangan,l_KdJaba,l_M04Singkatan,l_LembK1,l_LembK2,l_LembL1,l_LembL2,l_LembL3,l_T01JamLemb,l_TotNilLembur+l_T01JmlUMkn,l_FixIncome  ,l_Pendapatan, l_UMR,        l_RID_Dasar, l_Pendek, l_UserID,CURRENT_DATE, CURRENT_TIME,l_LembI1,l_lembI2,0      , l_userid,   current_time, null,       null);
            END;
         END IF;     

         -- Ambil Nilai Master Pandapatan dan Potongan U/Nilai Lembur
--         IF l_TotNilLembur>0
--            BEGIN 
	      SELECT  P_M03AllField ('D', 'LEMB' )
	      INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
	              l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr, l_M03ID;
        
              -- Get Kurs
              l_TotNilLemburVal := fn_Vround(l_TotNilLembur/fn_GetKurs(l_M03KdCurr,l_TglProses)); 

              -- Insert/Update Nilai Lembur
              IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='LEMB') IS NOT NULL THEN 
                 BEGIN
                   UPDATE S02DGAJ
                   SET EncNilaiVal = fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)  +l_TotNilLemburVal) ::VARCHAR(17),l_MyPass),
                       EncNilai    = fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilai,   l_MyPass) :: Decimal(15,2) +l_TotNilLembur)    ::VARCHAR(17),l_MyPass)
                   WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='LEMB';	
                 END; 
              ELSE
                 BEGIN
                   INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                  ,Nilai,KdCurr     ,EncNilaiVal                                                  ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                                VALUES(l_TglProses,l_NIP,'D'    ,'LEMB',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_TotNilLembur :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_TotNilLemburVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0,        l_S01_ID);
                 END;
              END IF;    
--            END

         -- Jika Uang Makan Lembur=0, tdk perlu di create Detail Uang Makan Lembur
         IF l_T01JmlUMkn<>0 THEN 
            BEGIN
              -- Ambil Nilai Master Pandapatan dan Potongan U/Uang Makan
	       SELECT  P_M03AllField ('D', 'JUMK' )
	       INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
		       l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr, l_M03ID ;

              -- Get Kurs          
              l_UangMakanVal := ROUND(l_T01JmlUMkn/fn_GetKurs(l_M03KdCurr,l_TglProses),2);

              -- Insert/Update Nilai Lembur
              IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='JUMK') IS NOT NULL THEN 
                 BEGIN
                   UPDATE S02DGAJ
                   SET EncNilaiVal = (fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_UangMakanVal) ::VARCHAR(17),l_MyPass),
                       EncNilai    = (fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilai, l_MyPass) :: Decimal(15,2))+l_T01JmlUMkn) :: VARCHAR(17)), l_MyPass)
                   WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='JUMK';	
                END;
              ELSE
                BEGIN
                   INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                ,Nilai,KdCurr     ,EncNilaiVal                                               ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                                VALUES(l_TglProses,l_NIP,'D'    ,'JUMK',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_T01JmlUMkn :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_UangMakanVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);
                END;
              END IF;   
            END;
         END IF;            
END LOOP;
CLOSE l_LOOP_T01;
END;
$$ LANGUAGE plpgsql ; 
/*
EXEC P_Lembur l_TglProses='2004-06-20',
              l_TglAkhir='2004-05-21',
              l_FlagKhusus='T',
              l_NIP='SUHE',
              l_BerhakLemb=1,
              l_KdArea='JKT',
              l_KdCaba='CB1',
              l_KdUUsa='U001',
              l_KdUker='UK1', 
              l_KdJaba='JB1',
              l_Nama='SUHE',
              l_UserID='WET',  
              l_MyPass='Copyright, 1988 (c) Microsoft Corporation, All rights reserved'


*/
