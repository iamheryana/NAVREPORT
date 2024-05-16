/****************************************
Name sprocs	: P_Pend_Harian
Create by	: Wati
Date		: 20-06-2003
Description	: Proses Hitung Pendapatan/Potongan harian
Call From	: Main sprocs
updated by peggy 2006 12 18 
fix income dari status = '1' --> pendapatan tetap = true 
Perhitungan D TRAL  = nilai tunjangan transport (nilai d tral) * jml hari  /25 
tr harian --> + pendapatan dasar kelompok, flag trans = Y ( peggy 2007 04 26) 
*****************************************/
DROP FUNCTION P_Pend_Harian(l_TglProses  DATE,
					  l_FlagKhusus VARCHAR(1),
					  l_TglAkhir   DATE,
					  l_NIP        VARCHAR(10),
					  l_GajiRp     DECIMAL(15,2),
					  l_JmlMangkir REAL, 
					  l_MyPass     VARCHAR(128),
					  l_S01_ID     INT) ;
--
CREATE FUNCTION P_Pend_Harian(l_TglProses  DATE,
					  l_FlagKhusus VARCHAR(1),
					  l_TglAkhir   DATE,
					  l_NIP        VARCHAR(10),
					  l_GajiRp     DECIMAL(15,2),
					  l_JmlMangkir REAL, 
					  l_MyPass     VARCHAR(128),
					  l_S01_ID     INT) 
RETURNS VOID 
AS $$ 

DECLARE l_FixIncome  		DECIMAL(15,2);
        l_VarIncome  		DECIMAL(15,2);
        l_Pendapatan 		DECIMAL(15,2);
        l_M03Singkatan  	VARCHAR(10);
        l_M03UsComp     	VARCHAR(1);
        l_M03Kolom      	VARCHAR(2);
        l_M03NoAcc      	VARCHAR(10);
        l_M03Status     	VARCHAR(1);
        l_M03Persen     	DECIMAL(5,2);
        l_M03Nilai      	DECIMAL(15,2);
	l_M03NoLyr      	INT;
	l_M03KdCurr     	VARCHAR(4);
	l_M03ID        		INT;
        l_NilaiMangkir  	DECIMAL(15,2);
        l_NilaiMangkirVal 	DECIMAL(15,2);
        l_PotAdvance    	DECIMAL(15,2);
        l_PotAdvanceVal 	DECIMAL(15,2);
	l_AdaMangkir		INT; 
	l_FZ1FlgGlng   		VARCHAR(1);         
	l_FZ1FlgJaba   		VARCHAR(1);
	l_FZ1FlgKJab 		VARCHAR(1);        	
	l_FZ1Caba    		VARCHAR(1);      
	l_mlDasarKelompok   	VARCHAR(1);         
	l_mlDasarKelompokAdv	VARCHAR(1);        

BEGIN 
SELECT FlgGlng,      FlgKJab,      FlgJaba,      FlgCaba
INTO   l_FZ1FlgGlng, l_FZ1FlgKJab, l_FZ1FlgJaba, l_FZ1Caba
FROM FZ1FLDA; 

l_mlDasarKelompok := CASE WHEN l_FZ1FlgGlng = 'Y' OR l_FZ1FlgKJab = 'Y' OR 
			       l_FZ1FlgJaba = 'Y' OR l_FZ1Caba = 'Y' THEN 
				'Y' 
			  ELSE 
				'T' 
			  END ; 

l_mlDasarKelompokAdv := (SELECT SUBSTR(StringFlag,10,1) FROM FZ2FLDA LIMIT 1) ; 

-- Jika Proses Khusus
IF l_FlagKhusus='Y' THEN 
   BEGIN
        -- Insert Detail Penggajian Untuk Nilai=0
     INSERT INTO S02DGAJ(TglPayr   ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                         Nilai,KdCurr    ,EncNilaiVal,
                         NilaiVal,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL, S01_ID)
                 SELECT  l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,' '    ,TBL.SkDpPt,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0    ,TBL.KdCurr,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0       ,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,TBL.FlgNonGL, TBL.S01_ID
     FROM 
     (SELECT DISTINCT T02.NIP,T02.FlgDpPt,T02.KdDpPt,M03.SkDpPt,M03.KdCurr,
                      M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar,0 AS FlgNonGL, l_S01_ID AS S01_ID--CASE WHEN T02.FlgDpPt='D' AND T02.BayarDimuka=1 THEN 1 ELSE 0 END
     FROM T02ABSN T02
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T02.FlgDpPt AND M03.KdDpPt= T02.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T02.FlgDpPt AND M46.KdDpPt= T02.KdDpPt
     WHERE T02.NIP=l_NIP AND M46.FlgDpPt IS NOT NULL AND T02.KdDpPt<>'MGKR' AND
           T02.Tanggal>l_TglAkhir AND T02.Tanggal<=l_TglProses) TBL 
     -- 
     LEFT JOIN S02DGAJ S02
     ON S02.TglPayr=l_TglProses AND S02.NIP=TBL.NIP AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
     WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL ;

     -- Update Detail Penggajian Untuk Nilai<>0 
     UPDATE S02DGAJ S02
     SET EncNilai    =fn_kcabang(TBL2.NIP,(S02ENCNILAI+TBL2.Nilai) :: VARCHAR(17),l_MyPass),
         EncNilaiVal =fn_kcabang(TBL2.NIP,(S02ENCNILAIVAL+TBL2.NilaiVal) :: VARCHAR(17),l_MyPass)
     FROM 
    (--TBL2 
     SELECT TBL1.TglProses, TBL1.NIP, TBL1.FlgDpPt, TBL1.KdDpPt, TBL1.Nilai, TBL1.NilaiVal, 
            fn_kPusat(TBL1.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAI, 
            fn_kPusat(TBL1.NIP,S02.EncNilaiVal,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAIVAL
     FROM
--
--     UPDATE S02DGAJ 
--     SET EncNilai    =fn_kcabang(TBL1.NIP,(fn_kPusat(TBL1.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)+TBL1.Nilai) ::VARCHAR(17),l_MyPass),
--         EncNilaiVal =fn_kcabang(TBL1.NIP,(fn_kPusat(TBL1.NIP,S02.EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+TBL1.NilaiVal) ::VARCHAR(17),l_MyPass)
--     FROM 
    (
     SELECT l_TglProses AS TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,fn_Vround(COALESCE(TBL.Nilai,0) ::DECIMAL(15,2)) AS Nilai,
            COALESCE(fn_Vround(TBL.Nilai ::DECIMAL(15,2)/fn_GetKurs(TBL.KdCurr,l_TglProses) ::DECIMAL(15,2)),0) AS NilaiVal
     FROM 
    (--T02
     SELECT T02.NIP,T02.FlgDpPt,T02.KdDpPt,T02.KdCurr,
            SUM(T02.Nilai) AS Nilai
     FROM 
    (
     SELECT T02.NIP,T02.FlgDpPt,T02.KdDpPt,M03.KdCurr,
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
     WHERE T02.NIP=l_NIP AND M46.FlgDpPt IS NOT NULL AND T02.KdDpPt<>'MGKR' AND
         T02.Tanggal>l_TglAkhir AND T02.Tanggal<=l_TglProses 
--     GROUP BY T02.NIP,T02.FlgDpPt,T02.KdDpPt,M03.kdCurr
    ) T02 
     GROUP BY T02.NIP,T02.FlgDpPt,T02.KdDpPt,T02.kdCurr
    --
    ) TBL 
    --
    ) TBL1 
     --
     INNER JOIN S02DGAJ S02 
     ON S02.TglPayr=TBL1.TglProses AND S02.NIP=l_NIP AND S02.FlgDpPt=TBL1.FlgDpPt AND S02.KdDpPt=TBL1.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
   ) TBL2
    WHERE S02.TglPayr=TBL2.TglProses AND S02.NIP=TBL2.NIP  AND S02.FlgDpPt=TBL2.FlgDpPt AND S02.KdDpPt=TBL2.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' ';    

     ------------ M A N G K I R ------------- 
     -- Pendapatan/Potongan Mangkir
     SELECT COALESCE(TBL.FixIncome,0)
     INTO l_FixIncome
     FROM
    (
     SELECT S02.NIP,SUM(COALESCE(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2),0)) AS FixIncome
     FROM S02DGAJ S02
     INNER JOIN M03DPPT M03
     ON S02.FlgDpPt= M03.FlgDpPt AND S02.KdDpPt=M03.KdDpPt
-- BY PEGGY 2006 12 18 
--      WHERE S02.NIP=l_NIP AND S02.Kolom='1' AND S02.TglPayr=l_TglProses
     WHERE S02.NIP=l_NIP AND M03.Status='1' AND S02.TglPayr=l_TglProses
     GROUP BY S02.NIP
    ) TBL;

    -- Pendatatan Kolom=3-6 (VarIncome)
     SELECT COALESCE(TBL.VarIncome,0)
     INTO l_VarIncome
     FROM
    (
     SELECT S02.NIP,SUM(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS VarIncome
     FROM S02DGAJ S02
     WHERE S02.NIP=l_NIP AND (S02.Kolom BETWEEN '3' AND '6') AND S02.TglPayr=l_TglProses
     GROUP BY S02.NIP
    ) TBL;

     -- Nilai Mangkir
     l_AdaMangkir := 0; 
     -- 
     SELECT 1,
            fn_Vround(TBL.NilaiMangkir ::DECIMAL(15,2)) 
     INTO   l_AdaMangkir,
	    l_NilaiMangkir
     FROM
    (
     SELECT SUM((T02.JmlAbsn ::DECIMAL(5,0)/l_JmlMangkir)*(l_FixIncome+l_VarIncome)) AS NilaiMangkir
     FROM T02ABSN T02
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T02.FlgDpPt AND M03.KdDpPt= T02.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T02.FlgDpPt AND M46.KdDpPt= T02.KdDpPt
     WHERE T02.NIP=l_NIP AND M46.FlgDpPt IS NOT NULL AND T02.KdDpPt='MGKR' AND
           T02.FlgDpPt='P' AND T02.Tanggal>l_TglAkhir AND T02.Tanggal<=l_TglProses 
     GROUP BY T02.NIP,T02.JmlAbsn
    ) TBL ;

     -- Ambil Nilai Master Pandapatan dan Potongan U/Nilai Mangkir
     SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
	    fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
     from  P_M03AllField ('P', 'MGKR' ) fx
     INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
	     l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;    
     
     l_NilaiMangkirVal := fn_Vround(l_NilaiMangkir ::DECIMAL(15,2)/fn_GetKurs(l_M03KdCurr,l_TglProses) ::DECIMAL(15,2));

     -- Insert/Update Nilai Potongan Mangkir
     IF  l_AdaMangkir=1 THEN 
         BEGIN
         IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='MGKR') IS NOT NULL THEN 
            BEGIN
              UPDATE S02DGAJ
              SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_NilaiMangkirVal) ::VARCHAR(17),l_MyPass),
                  EncNilai=fn_kcabang(l_NIP,((fn_kpusat(l_NIP,EncNilai, l_MyPass) :: Decimal(15,2))+l_NilaiMangkir :: VARCHAR(17)), l_MyPass)
              WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='MGKR';
           END;
        ELSE
           BEGIN
              INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                  ,Nilai,KdCurr     ,EncNilaiVal                                                  ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                           VALUES(l_TglProses,l_NIP,'P'    ,'MGKR',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_NilaiMangkir :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_NilaiMangkirVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);	 
           END;   
        END IF;
        END; 
     END IF;    
     --* 
     -- Potongan Advance
     SELECT fn_Vround(TBL.Advance)  ::DECIMAL(15,2)
     INTO   l_PotAdvance
     FROM 
     (
     SELECT T02.NIP,
            SUM(fn_NilaiPend_Pot(l_TglProses,T02.NIP,T02.FlgDpPt,T02.KdDpPt,0,0,T02.JmlAbsn,l_GajiRp,CASE WHEN T02.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,M03.KdCurr)) AS Advance
     FROM T02ABSN T02
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T02.FlgDpPt AND M03.KdDpPt= T02.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T02.FlgDpPt AND M46.KdDpPt= T02.KdDpPt
     WHERE T02.NIP=l_NIP AND T02.FlgDpPt='D' AND T02. BayarDimuka=1 AND M46.FlgDpPt IS NOT NULL 
           AND T02.Tanggal>l_TglAkhir AND T02.Tanggal<=l_TglProses 
     GROUP BY T02.NIP
     ) TBL ; 
     -- Jika Nilai Potongan Advance <> 0
     IF l_PotAdvance<>0 THEN 
        BEGIN
          -- Ambil Data dari Master Pendapatan/Potongan (M03DPPT
	  SELECT  P_M03AllField ('P', 'PADV' )
	  INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
		  l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr, l_M03ID ;          
          -- Ambil Nilai Kurs
          l_PotAdvanceVal := fn_Vround(l_PotAdvance ::DECIMAL(15,2)/fn_GetKurs(l_M03KdCurr,l_TglProses) ::DECIMAL(15,2));
          -- Update Detail Penggajian           
          IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV') IS NOT NULL THEN 
             BEGIN
               UPDATE S02DGAJ
               SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_PotAdvanceVal) ::VARCHAR(17),l_MyPass),
                   EncNilai    =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)+l_PotAdvance) ::VARCHAR(17),l_MyPass)
               WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV';
             END;
          ELSE
             BEGIN
               INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                ,Nilai,KdCurr    ,EncNilaiVal                                                 ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                            VALUES(l_TglProses,l_NIP,'P'    ,'PADV',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_PotAdvance :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_PotAdvanceVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0,        l_S01_ID); 
             END;
          END IF; 
        END;
     END IF;              
   END;
-- Jika Proses Bulanan/Harian
ELSE
   BEGIN
        -- Insert Detail Penggajian Untuk Nilai=0
     INSERT INTO S02DGAJ(TglPayr   ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                         Nilai,KdCurr    ,EncNilaiVal,
                         NilaiVal,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL,    S01_ID)
                 SELECT  l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,' '    ,TBL.SkDpPt,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0    ,TBL.KdCurr,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0       ,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,TBL.FlgNonGL,TBL.S01_ID
     FROM 
     (SELECT DISTINCT T02.NIP,T02.FlgDpPt,T02.KdDpPt,M03.SkDpPt,M03.KdCurr,
                      M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar,0 AS FlgNonGL, l_S01_ID AS S01_ID--CASE WHEN T02.FlgDpPt='D' AND T02.BayarDimuka=1 THEN 1 ELSE 0 END
     FROM T02ABSN T02
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T02.FlgDpPt AND M03.KdDpPt= T02.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T02.FlgDpPt AND M46.KdDpPt= T02.KdDpPt
     WHERE T02.NIP=l_NIP AND M46.FlgDpPt IS NULL AND T02.KdDpPt<>'MGKR' AND
           T02.Tanggal>l_TglAkhir AND T02.Tanggal<=l_TglProses) TBL 
     -- 
     LEFT JOIN S02DGAJ S02
     ON S02.TglPayr=l_TglProses AND S02.NIP=TBL.NIP AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
     WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL ;

     -- Update Detail Penggajian Untuk Nilai<>0 
     UPDATE S02DGAJ S02
     SET EncNilai    =fn_kcabang(TBL2.NIP,(S02ENCNILAI+TBL2.Nilai) :: VARCHAR(17),l_MyPass),
         EncNilaiVal =fn_kcabang(TBL2.NIP,(S02ENCNILAIVAL+TBL2.NilaiVal) :: VARCHAR(17),l_MyPass)
     FROM 
    (--TBL2 
     SELECT TBL1.TglProses, TBL1.NIP, TBL1.FlgDpPt, TBL1.KdDpPt, TBL1.Nilai, TBL1.NilaiVal, 
            fn_kPusat(TBL1.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAI, 
            fn_kPusat(TBL1.NIP,S02.EncNilaiVal,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAIVAL
     FROM
     
--     UPDATE S02DGAJ
--     SET EncNilai    =fn_kcabang(TBL1.NIP,(fn_kPusat(TBL1.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)+TBL1.Nilai) ::VARCHAR(17),l_MyPass),
--         EncNilaiVal =fn_kcabang(TBL1.NIP,(fn_kPusat(TBL1.NIP,S02.EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+TBL1.NilaiVal) ::VARCHAR(17),l_MyPass)
--     FROM 
    (
     SELECT l_TglProses AS TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,fn_Vround(TBL.Nilai) ::DECIMAL(15,2) AS Nilai,
            fn_Vround(TBL.Nilai ::DECIMAL(15,2)/fn_GetKurs(TBL.KdCurr,l_TglProses) ::DECIMAL(15,2)) AS NilaiVal
     FROM 
    (
     SELECT T02.NIP,T02.FlgDpPt,T02.KdDpPt,T02.KdCurr,
            SUM(T02.NILAI) AS Nilai
     FROM 
    (-- T02 
     SELECT T02.NIP,T02.FlgDpPt,T02.KdDpPt,M03.KdCurr,
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
     WHERE T02.NIP=l_NIP AND M46.FlgDpPt IS NULL AND T02.KdDpPt<>'MGKR' AND
           T02.Tanggal>l_TglAkhir AND T02.Tanggal<=l_TglProses 
    ) T02
    --
     GROUP BY T02.NIP,T02.FlgDpPt,T02.KdDpPt,T02.kdCurr
    ) TBL 
    --
    ) TBL1 
     --
     INNER JOIN S02DGAJ S02 
     ON S02.TglPayr=TBL1.TglProses AND S02.NIP=l_NIP AND S02.FlgDpPt=TBL1.FlgDpPt AND S02.KdDpPt=TBL1.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
    ) TBL2
    WHERE S02.TglPayr=TBL2.TglProses AND S02.NIP=TBL2.NIP  AND S02.FlgDpPt=TBL2.FlgDpPt AND S02.KdDpPt=TBL2.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' ';    
     

     ------------ M A N G K I R ------------- 
     l_FixIncome := 0;
     l_VarIncome := 0; 
     -- Pendapatan/Potongan Mangkir
     SELECT SUM(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) 
     INTO   l_FixIncome
     FROM S02DGAJ S02
     INNER JOIN M03DPPT M03
     ON S02.FlgDpPt= M03.FlgDpPt AND S02.KdDpPt=M03.KdDpPt
-- BY PEGGY 2006 12 18 
--      WHERE S02.NIP=l_NIP AND S02.Kolom='1' AND S02.TglPayr=l_TglProses
     WHERE S02.NIP=l_NIP AND M03.Status='1' AND S02.TglPayr=l_TglProses
     GROUP BY S02.NIP;
     --
     -- Pendapatan Kolom=3-6 (VarIncome)
     SELECT SUM(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) 
     INTO   l_VarIncome
     FROM S02DGAJ S02
     WHERE S02.NIP=l_NIP AND (S02.Kolom BETWEEN '3' AND '6') AND S02.TglPayr=l_TglProses
     GROUP BY S02.NIP;
     -- Nilai Mangkir
     l_AdaMangkir := 0; 
     -- 
     SELECT 1,
            fn_Vround(COALESCE(TBL.NilaiMangkir,0) ::DECIMAL(15,2)) 
     INTO   l_AdaMangkir,
            l_NilaiMangkir
     FROM
    (
     SELECT SUM((T02.JmlAbsn::DECIMAL(5,0)/l_JmlMangkir)*(l_FixIncome+l_VarIncome)) AS NilaiMangkir
     FROM T02ABSN T02
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T02.FlgDpPt AND M03.KdDpPt= T02.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T02.FlgDpPt AND M46.KdDpPt= T02.KdDpPt
     WHERE T02.NIP=l_NIP AND M46.FlgDpPt IS NULL AND T02.KdDpPt='MGKR' AND
           T02.FlgDpPt='P' AND T02.Tanggal>l_TglAkhir AND T02.Tanggal<=l_TglProses 
     GROUP BY T02.NIP,T02.JmlAbsn
    ) TBL ;

     -- Ambil Nilai Master Pandapatan dan Potongan U/Nilai Lembur  
     SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
	    fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
     from  P_M03AllField ('P', 'MGKR' ) fx
     INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
	     l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;    
			 	   
     l_NilaiMangkirVal := fn_Vround(l_NilaiMangkir ::DECIMAL(15,2)/fn_GetKurs(l_M03KdCurr,l_TglProses) ::DECIMAL(15,2));
     
     -- Insert/Update Nilai Mangkir
     IF l_AdaMangkir=1 THEN 
        BEGIN    
          IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='MGKR') IS NOT NULL THEN 
             BEGIN
               UPDATE S02DGAJ
               SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_NilaiMangkirVal) ::VARCHAR(17),l_MyPass),
                   EncNilai=fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilai, l_MyPass) :: Decimal(15,2)+l_NilaiMangkir) :: VARCHAR(17), l_MyPass)
               WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='MGKR';
             END;
          ELSE
             BEGIN
                INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                  ,Nilai,KdCurr     ,EncNilaiVal                                                  ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                             VALUES(l_TglProses,l_NIP,'P'    ,'MGKR',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_NilaiMangkir :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_NilaiMangkirVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0,        l_S01_ID	); 
             END;
          END IF; 
        END;
     END IF; 
     --*  

     -- Potongan Advance
     SELECT fn_Vround(TBL.Advance)  ::DECIMAL(15,2)
     INTO   l_PotAdvance
     FROM 
     (
     SELECT T02.NIP,
            SUM(fn_NilaiPend_Pot(l_TglProses,T02.NIP,T02.FlgDpPt,T02.KdDpPt,0,0,T02.JmlAbsn,l_GajiRp,CASE WHEN T02.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,M03.KdCurr)) AS Advance
     FROM T02ABSN T02
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T02.FlgDpPt AND M03.KdDpPt= T02.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T02.FlgDpPt AND M46.KdDpPt= T02.KdDpPt
     WHERE T02.NIP=l_NIP AND T02.FlgDpPt='D' AND T02. BayarDimuka=1 AND M46.FlgDpPt IS NULL 
           AND T02.Tanggal>l_TglAkhir AND T02.Tanggal<=l_TglProses 
     GROUP BY T02.NIP
     ) TBL ; 
     -- Jika Nilai Potongan Advance <> 0
     IF l_PotAdvance<>0 THEN 
        BEGIN
          -- Ambil Data dari Master Pendapatan/Potongan (M03DPPT
	  SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
		 fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
	  from  P_M03AllField ('P', 'PADV' ) fx
	  INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
		  l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;    
          -- Ambil Nilai Kurs
          l_PotAdvanceVal := fn_Vround(l_PotAdvance ::DECIMAL(15,2)/fn_GetKurs(l_M03KdCurr,l_TglProses) ::DECIMAL(15,2));
          -- Update Detail Penggajian           
          IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV') IS NOT NULL THEN 
             BEGIN
               UPDATE S02DGAJ
               SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_PotAdvanceVal) ::VARCHAR(17),l_MyPass),
                   EncNilai    =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)+l_PotAdvance) ::VARCHAR(17),l_MyPass)
               WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV';
             END; 
          ELSE
             BEGIN
               INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                ,Nilai,KdCurr     ,EncNilaiVal                                                ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                            VALUES(l_TglProses,l_NIP,'P'    ,'PADV',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_PotAdvance :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_PotAdvanceVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);
             END;
          END IF;    
        END;
     END IF; 
   END; 
END IF; 
END ;    
$$ LANGUAGE plpgsql ;

/*

EXEC        P_Pend_Harian l_TglProses  ='2003-01-20',
                          l_FlagKhusus ='T',
                          l_TglAkhir   ='2002-12-20',
                          l_NIP        ='TEST',
                          l_GajiRp     =2000000,
                          l_JmlMangkir =22,
                          l_MyPass     ='Copyright, 1988 (c) Microsoft Corporation, All rights reserved'


 
select * from s02dgaj
where nip='TEST'

delete from s02dgaj
where nip='TEST' AND kdDppt<>'BSAL'

*/
