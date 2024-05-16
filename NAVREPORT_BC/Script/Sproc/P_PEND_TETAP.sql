/****************************************
Name sprocs	: P_Pend_Tetap
Create by	: Wati
Date		: 18-06-2003
Description	: Proses Hitung Pendapatan/Potongan Tetap                  
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Pend_Tetap(l_TglProses  DATE,
			 l_FlagKhusus VARCHAR(1),
			 l_NIP        VARCHAR(10),
			 l_GajiRp     DECIMAL(15,2),
			 l_mnHari     DECIMAL(4,1),
			 l_Prs_Harian VARCHAR(1),
			 l_HKerja     DECIMAL(4,1),				 
			 l_MyPass     VARCHAR(128),
			 l_S01_ID     INT); 
--
CREATE FUNCTION P_Pend_Tetap(l_TglProses  DATE,
			l_FlagKhusus VARCHAR(1),
			l_NIP        VARCHAR(10),
			l_GajiRp     DECIMAL(15,2),
			l_mnHari     DECIMAL(4,1),
			l_Prs_Harian VARCHAR(1),
			l_HKerja     DECIMAL(4,1),				 
			l_MyPass     VARCHAR(128),
			l_S01_ID     INT) 
			RETURNS VOID 
AS $$ 
DECLARE l_PotAdvance    DECIMAL(15,2);
        l_PotAdvanceVal DECIMAL(15,2);
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
	l_M03ID         INT;

BEGIN 
IF l_FlagKhusus='Y' THEN 
   BEGIN
     -- Insert Detail Penggajian Untuk Nilai=0
     INSERT INTO S02DGAJ(TglPayr   ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                         Nilai,KdCurr    ,EncNilaiVal,
                         NilaiVal,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL,     S01_ID)
                 SELECT  l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,' '    ,TBL.SkDpPt,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0    ,TBL.KdCurr,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0       ,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,TBL.FlgNonGL, TBL.S01_ID
     FROM 
     (--TBL
	 SELECT DISTINCT T04.NIP,T04.FlgDpPt,T04.KdDpPt,M03.SkDpPt,
			 CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END AS KdCurr,
                         M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar,0 AS FlgNonGL, l_S01_ID AS S01_ID--CASE WHEN T04.FlgDpPt='D' AND T04.BayarDimuka=1 THEN 1 ELSE 0 END
     FROM T04TTAP T04
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T04.FlgDpPt AND M03.KdDpPt= T04.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T04.FlgDpPt AND M46.KdDpPt= T04.KdDpPt
     WHERE T04.NIP=l_NIP AND M46.FlgDpPt IS NOT NULL) TBL 
     -- 
     LEFT JOIN S02DGAJ S02
     ON S02.TglPayr=l_TglProses AND S02.NIP=TBL.NIP AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
     WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL ;

     -- Update Detail Penggajian Untuk Nilai<>0 
     UPDATE S02DGAJ S02
     SET EncNilai    =fn_kcabang(TBL1.NIP,(S02ENCNILAI+TBL1.Nilai) :: VARCHAR(17),l_MyPass),
         EncNilaiVal =fn_kcabang(TBL1.NIP,(S02ENCNILAIVAL+TBL1.NilaiVal) :: VARCHAR(17),l_MyPass)
     FROM 
    (--TBL2 
     SELECT TBL1.TglProses, TBL1.NIP, TBL1.FlgDpPt, TBL1.KdDpPt, TBL1.Nilai, TBL1.NilaiVal, 
            fn_kPusat(TBL1.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAI, 
            fn_kPusat(TBL1.NIP,S02.EncNilaiVal,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAIVAL
     FROM
    (--TBL1
     SELECT l_TglProses AS TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,fn_Vround(COALESCE(TBL.Nilai,0)) AS Nilai,
            COALESCE(fn_Vround(TBL.Nilai/fn_GetKurs(TBL.KdCurr,l_TglProses)),0) AS NilaiVal
     FROM 
    (--TBL
     SELECT T04.NIP,T04.FlgDpPt,T04.KdDpPt,
	    CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END AS KdCurr,
            SUM(fn_NilaiPend_Pot(l_TglProses,T04.NIP,T04.FlgDpPt,T04.KdDpPt,T04.Persen,T04.Nilai,
                 CASE WHEN M03.JnDpPt='B' THEN 1
                      ELSE
                         CASE WHEN l_Prs_Harian='Y' THEN l_HKerja
                         ELSE
                           l_mnHari
                         END 
                      END,l_GajiRp,CASE WHEN T04.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END)) AS Nilai
     FROM T04TTAP T04
     -- 
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T04.FlgDpPt AND M03.KdDpPt= T04.KdDpPt
     --- 
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T04.FlgDpPt AND M46.KdDpPt= T04.KdDpPt
     ---
      WHERE T04.NIP=l_NIP AND M46.FlgDpPt IS NOT NULL
      GROUP BY T04.NIP,T04.FlgDpPt,T04.KdDpPt,CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END
    ) TBL 
    --
    ) TBL1 
     --
     INNER JOIN S02DGAJ S02 
     ON S02.TglPayr=TBL1.TglProses AND S02.NIP=l_NIP AND S02.FlgDpPt=TBL1.FlgDpPt AND S02.KdDpPt=TBL1.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
    ) TBL2
    WHERE S02.TglPayr=TBL2.TglProses AND S02.NIP=TBL2.NIP  AND S02.FlgDpPt=TBL2.FlgDpPt AND S02.KdDpPt=TBL2.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' ';    
    
     -- Hitungan Potongan Advance 
     SELECT fn_Vround(TBL.Advance)
     INTO l_PotAdvance
     FROM 
     (--TBL
     SELECT  T04.NIP,SUM(fn_NilaiPend_Pot(l_TglProses,T04.NIP,T04.FlgDpPt,T04.KdDpPt,T04.Persen,T04.Nilai,
						CASE WHEN M03.JnDpPt='B' THEN 1
						      ELSE
							 CASE WHEN l_Prs_Harian='Y' THEN l_HKerja
							 ELSE
							   l_mnHari
							 END 
						      END,
						l_GajiRp,CASE WHEN T04.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,
						CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END)) 
		      AS Advance
     FROM T04TTAP T04
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T04.FlgDpPt AND M03.KdDpPt= T04.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T04.FlgDpPt AND M46.KdDpPt= T04.KdDpPt
     WHERE T04.NIP=l_NIP AND T04.FlgDpPt='D'AND T04.BayarDimuka=1 AND M46.FlgDpPt IS NOT NULL 
     GROUP BY T04.NIP
     ) TBL ;

     -- Jika Nilai Potongan Advance <> 0
     IF l_PotAdvance<>0 THEN 
        BEGIN
          -- Ambil Data dari Master Pendapatan/Potongan (M03DPPT
	  SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
	         fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
	  from  P_M03AllField ('P', 'PADV' ) FX 
	  INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
		  l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr, l_M03ID ;         
          -- Ambil Nilai Kurs
          l_PotAdvanceVal := fn_Vround(l_PotAdvance/fn_GetKurs(l_M03KdCurr,l_TglProses));
          -- Update Detail Penggajian           
          IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV') IS NOT NULL THEN 
             BEGIN
               UPDATE S02DGAJ
               SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2) + l_PotAdvanceVal)  ::VARCHAR(17),l_MyPass),
                   EncNilai    =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilai,l_MyPass)::DECIMAL(15,2) + l_PotAdvance) ::VARCHAR(17),l_MyPass)
               WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV'; 
             END; 
          ELSE
             BEGIN
               INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                ,Nilai,KdCurr     ,EncNilaiVal                                                ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                            VALUES(l_TglProses,l_NIP,'P'    ,'PADV',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_PotAdvance :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_PotAdvanceVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0,        l_S01_ID);	 
             END;
          END IF; 
        END; 
     END IF; 
   END; 
ELSE
   BEGIN     
     -- Insert Detail Penggajian Untuk Nilai=0
     INSERT INTO S02DGAJ(TglPayr   ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                         Nilai,KdCurr    ,EncNilaiVal,
                         NilaiVal,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL,     S01_ID)
                 SELECT  l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,' '    ,TBL.SkDpPt,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0    ,TBL.KdCurr,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                         0       ,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,TBL.FlgNonGL, TBL.S01_ID
     FROM 
     (SELECT DISTINCT T04.NIP,T04.FlgDpPt,T04.KdDpPt,M03.SkDpPt,CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END AS KdCurr,
                      M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar,0 AS FlgNonGL, l_S01_ID AS S01_ID --CASE WHEN T04.FlgDpPt='D' AND T04.BayarDimuka=1 THEN 1 ELSE 0 END
     FROM T04TTAP T04
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T04.FlgDpPt AND M03.KdDpPt= T04.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T04.FlgDpPt AND M46.KdDpPt= T04.KdDpPt
     WHERE T04.NIP=l_NIP AND M46.FlgDpPt IS NULL
     ) TBL 
     -- 
     LEFT JOIN S02DGAJ S02
     ON S02.TglPayr=l_TglProses AND S02.NIP=TBL.NIP AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
     WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL ; 

     -- Update Detail Penggajian Untuk Nilai<>0 
     UPDATE S02DGAJ S02
     SET EncNilai    =fn_kcabang(TBL2.NIP,(TBL2.S02ENCNILAI+TBL2.Nilai) ::VARCHAR(17),l_MyPass),
         EncNilaiVal =fn_kcabang(TBL2.NIP,(TBL2.S02ENCNILAIVAL+TBL2.NilaiVal) ::VARCHAR(17),l_MyPass)
     FROM 
    (--TBL2 
     SELECT TBL1.TglProses, TBL1.NIP, TBL1.FlgDpPt, TBL1.KdDpPt, TBL1.Nilai, TBL1.NilaiVal, 
            fn_kPusat(TBL1.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAI, 
            fn_kPusat(TBL1.NIP,S02.EncNilaiVal,l_MyPass) ::DECIMAL(15,2) AS S02ENCNILAIVAL
     FROM     
    (
     SELECT l_TglProses AS TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,fn_Vround(TBL.Nilai) AS Nilai,
            fn_Vround(TBL.Nilai/fn_GetKurs(TBL.KdCurr,l_TglProses)) AS NilaiVal
     FROM 
    (
     SELECT T04.NIP,T04.FlgDpPt,T04.KdDpPt,CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END AS KdCurr,
            (SUM(fn_NilaiPend_Pot(l_TglProses,T04.NIP,T04.FlgDpPt,T04.KdDpPt,T04.Persen,T04.Nilai,
				 CASE WHEN M03.JnDpPt='B' THEN 1
				      ELSE
					 CASE WHEN l_Prs_Harian='Y' THEN l_HKerja
					 ELSE
					   l_mnHari
					 END 
				      END,l_GajiRp,CASE WHEN T04.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,
				CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END))) 
				* (CASE WHEN l_MNHARI <> 0 THEN (FN_GETPROP(T04.NIP,l_MNHARI,l_TGLPROSES)/l_mnHari) ELSE 1 END ) 
				AS Nilai
     FROM T04TTAP T04
     --
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T04.FlgDpPt AND M03.KdDpPt= T04.KdDpPt
     --
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T04.FlgDpPt AND M46.KdDpPt= T04.KdDpPt
     --  
     WHERE T04.NIP=l_NIP AND M46.FlgDpPt IS NULL
     GROUP BY T04.NIP,T04.FlgDpPt,T04.KdDpPt,CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END
    ) TBL 
    --
    ) TBL1 
     --
     INNER JOIN S02DGAJ S02 
     ON S02.TglPayr=TBL1.TglProses AND S02.NIP=l_NIP  AND S02.FlgDpPt=TBL1.FlgDpPt AND S02.KdDpPt=TBL1.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
    ) TBL2 
    WHERE S02.TglPayr=TBL2.TglProses AND S02.NIP=TBL2.NIP  AND S02.FlgDpPt=TBL2.FlgDpPt AND S02.KdDpPt=TBL2.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' ';
    
     -- Hitungan Potongan Advance
     SELECT fn_Vround(TBL.Advance) 
     INTO l_PotAdvance
     FROM 
     (
     SELECT  T04.NIP,SUM(fn_NilaiPend_Pot(l_TglProses,T04.NIP,T04.FlgDpPt,T04.KdDpPt,T04.Persen,T04.Nilai,
					 CASE WHEN M03.JnDpPt='B' THEN 1
					      ELSE
						 CASE WHEN l_Prs_Harian='Y' THEN l_HKerja
						 ELSE
						   l_mnHari
						 END 
					      END,
					  l_GajiRp,CASE WHEN T04.Flag='1' THEN 'Y' ELSE 'T' END,l_MyPass,
					  CASE WHEN (T04.Nilai=0 AND T04.Persen=0) THEN M03.KdCurr ELSE T04.KdCurr END)) AS Advance
     FROM T04TTAP T04
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=T04.FlgDpPt AND M03.KdDpPt= T04.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=T04.FlgDpPt AND M46.KdDpPt= T04.KdDpPt
     WHERE T04.NIP=l_NIP AND T04.FlgDpPt='D'AND T04.BayarDimuka=1 AND M46.FlgDpPt IS NULL 
     GROUP BY T04.NIP
     ) TBL ; 

     -- Jika Nilai Potongan Advance <> 0
     IF l_PotAdvance<>0 THEN 
        BEGIN
          -- Ambil Data dari Master Pendapatan/Potongan (M03DPPT
	  SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
	         fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
	  from  P_M03AllField ('P', 'PADV' ) FX           
	  INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
		  l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr, l_M03ID ;          
          -- Ambil Nilai Kurs
          l_PotAdvanceVal := fn_Vround(l_PotAdvance/fn_GetKurs(l_M03KdCurr,l_TglProses)) ;
          -- Update Detail Penggajian           
          IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV') IS NOT NULL THEN 
             BEGIN
               UPDATE S02DGAJ

               SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_PotAdvanceVal)  ::VARCHAR(17),l_MyPass),
                   EncNilai    =fn_kcabang(l_NIP,(fn_kPusat(NIP,EncNilai,l_MyPass)::DECIMAL(15,2)+l_PotAdvance) ::VARCHAR(17),l_MyPass)
               WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt='PADV';
             END;
          ELSE
             BEGIN
               INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                ,Nilai,KdCurr     ,EncNilaiVal                                                ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,    FlgNonGL, S01_ID)
                            VALUES(l_TglProses,l_NIP,'P'    ,'PADV',' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_PotAdvance :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_PotAdvanceVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0      , l_S01_ID); 
             END;
          END IF;    
        END; 
     END IF;    
   END;
END IF; 
END;    
$$ LANGUAGE plpgsql ;
-- End Of Program
/*
EXEC P_Pend_Tetap        l_TglProses  ='2003-12-20',
                         l_FlagKhusus ='T',
                         l_NIP        ='03',
                         l_GajiRp     = 5000000,
                         l_mnHari     = 20, 
                         l_Prs_Harian = 'T',
			 l_HKerja     = 0,				 
                         l_MyPass     ='Copyright, 1988 (c) Microsoft Corporation, All rights reserved'

SELECt * from
s02dgaj where nip='06' AND tglpayr='2003-07-27'

--DELETE FROM S02DGAJ WHERE NIP='TEST' AND KDDPPT<>'BSAL'


 00:00:00.000                                T    03         5000000.00        20   T    0           Copyright, 1988 (c) Microsoft Corporation, All rights reserved

*/
