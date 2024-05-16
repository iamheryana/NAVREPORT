/****************************************
Name sprocs	: P_PendAkumulasi
Create by	: wati
Date		: 17-06-2003
Description	: Proses Pendapatan Akumulasi
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_PendAkumulasi (l_TglProses  DATE,
					     l_NIP        VARCHAR(10),
					     l_FlagKhusus VARCHAR(1),
					     l_MyPass     VARCHAR(128),
					     l_S01_ID     INT);
--
CREATE FUNCTION P_PendAkumulasi (l_TglProses  DATE,
					     l_NIP        VARCHAR(10),
					     l_FlagKhusus VARCHAR(1),
					     l_MyPass     VARCHAR(128),
					     l_S01_ID     INT)
RETURNS VOID 
AS $$

DECLARE l_LOOP_T17   	REFCURSOR;
        l_T17FlgDpPt    VARCHAR(1);
        l_T17KdDpPt  	VARCHAR(4);
        l_T17Persen  	DECIMAL(5,2);
        l_Persen     	DECIMAL(5,2);
        l_PendAkumulasi DECIMAL(15,2);
        l_PendAkumulasiVal DECIMAL(15,2);
        l_FixIncome     DECIMAL(15,2);
        l_M03Singkatan  VARCHAR(10);
        l_M03UsComp     VARCHAR(1);
        l_M03Kolom      VARCHAR(2);
        l_M03NoAcc      VARCHAR(10);
        l_M03Status     VARCHAR(1);
        l_M03Persen     DECIMAL(5,2);
        l_M03Nilai      DECIMAL(15,2);
	l_M03NoLyr      INT;
	l_M03KdCurr     VARCHAR(4);

BEGIN 
IF l_FlagKhusus='Y' THEN 
      OPEN l_LOOP_T17 FOR SELECT T17.FlgDpPt,T17.KdDpPt,T17.Persen,M03.SkDpPt,M03.UsComp,M03.NoAcc,
					  M03.Status,M03.Persen,M03.Nilai,M03.NoLayar,M03.KdCurr,M03.Kolom
				   FROM T17PDAK T17
				   INNER JOIN M03DPPT M03
				   ON M03.FlgDpPt=T17.FlgDpPt AND M03.KdDpPt= T17.KdDpPt
				   LEFT JOIN M46PPKH M46
				   ON M46.FlgDpPt=T17.FlgDpPt AND M46.KdDpPt= T17.KdDpPt
				   WHERE T17.NIP=l_NIP AND M46.FlgDpPt IS NOT NULL 
				   ORDER BY T17.LevelAkm;
      LOOP 
         FETCH l_LOOP_T17 INTO l_T17FlgDpPt,l_T17KdDpPt,l_T17Persen,l_M03Singkatan,l_M03UsComp,l_M03NoAcc,
			       l_M03Status,l_M03Persen,l_M03Nilai,l_M03NoLyr,l_M03KdCurr,l_M03Kolom;
	 IF NOT FOUND THEN
	    EXIT ;
	 END IF; 
       -- Pendapatan Tetap dari Detail Penggajian        
       l_FixIncome := 0;
       ---  
       SELECT TBL.FixIncome
       INTO l_FixIncome
       FROM
       (
       SELECT SUM(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
       FROM S02DGAJ S02 
-- BY PEGGY 2006 12 18 : PENDAPATAN TETAP DASAR M03.STATUS DAN BUKAN KOLOM 1 LAGI 
	INNER JOIN M03DPPT M03 
	ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
	WHERE S02.NIP=l_NIP AND M03.STATUS = '1' AND S02.TglPayr=l_TglProses 
--       WHERE NIP=l_NIP AND TglPayr=l_TglProses AND Kolom='1'
       GROUP BY NIP
       ) TBL ;

       -- Persentasi Akumulasi        
       l_Persen           := CASE WHEN l_T17Persen<>0 THEN l_T17Persen ELSE l_M03Persen END;
       l_PendAkumulasi    := fn_Vround((l_FixIncome*l_Persen)/100);
       l_PendAkumulasiVal := fn_Vround(((l_FixIncome*l_Persen)/100)/fn_GetKurs(l_M03KdCurr,l_TglProses));
              
       -- Insert/Update Pendapatan Akumulasi
       IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt=l_T17FlgDpPt AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_T17KdDpPt) IS NOT NULL THEN 
            UPDATE S02DGAJ
            SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_PendAkumulasiVal) ::VARCHAR(17),l_MyPass),
                EncNilai    =fn_kcabang(l_NIP,(fn_kpusat(l_NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_PendAkumulasi) ::VARCHAR(17), l_MyPass)
            WHERE TglPayr=l_TglProses AND FlgDpPt=l_T17FlgDpPt AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_T17KdDpPt;
       ELSE
            INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,     KdDpPt,     FlgAngs,Singkatan ,    EncNilai                                                   ,Nilai,KdCurr     ,EncNilaiVal                                                   ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                         VALUES(l_TglProses,l_NIP,l_T17FlgDpPt,l_T17KdDPPt,' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_PendAkumulasi :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_PendAkumulasiVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);
       END IF; 
    END LOOP;
    CLOSE l_LOOP_T17;
ELSE
      OPEN l_LOOP_T17 FOR SELECT T17.FlgDpPt,T17.KdDpPt,T17.Persen,M03.SkDpPt,M03.UsComp,M03.NoAcc,
				 	  M03.Status,M03.Persen,M03.Nilai,M03.NoLayar,M03.KdCurr,M03.Kolom
				   FROM T17PDAK T17
				   INNER JOIN M03DPPT M03
				   ON M03.FlgDpPt=T17.FlgDpPt AND M03.KdDpPt= T17.KdDpPt
				   LEFT JOIN M46PPKH M46
				   ON M46.FlgDpPt=T17.FlgDpPt AND M46.KdDpPt= T17.KdDpPt
				   WHERE T17.NIP=l_NIP AND M46.FlgDpPt IS NULL 
				   ORDER BY T17.LevelAkm ;
      LOOP 
         FETCH l_LOOP_T17 INTO l_T17FlgDpPt,l_T17KdDpPt,l_T17Persen,l_M03Singkatan,l_M03UsComp,l_M03NoAcc,
				l_M03Status,l_M03Persen,l_M03Nilai,l_M03NoLyr,l_M03KdCurr,l_M03Kolom;
         IF NOT FOUND THEN
            EXIT ;
         END IF;

       -- Pendapatan Tetap dari Detail Penggajian        
       l_FixIncome := 0;
       ---  
       SELECT TBL.FixIncome 
       INTO l_FixIncome 
       FROM
       (
       SELECT SUM(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
       FROM S02DGAJ S02
-- BY PEGGY 2006 12 18 : PENDAPATAN TETAP DASAR M03.STATUS DAN BUKAN KOLOM 1 LAGI 
	INNER JOIN M03DPPT M03 
	ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
	WHERE S02.NIP=l_NIP AND M03.STATUS = '1' AND S02.TglPayr=l_TglProses 
--       WHERE NIP=l_NIP AND TglPayr=l_TglProses AND Kolom='1'
       GROUP BY NIP
       ) TBL ; 

       -- Persentasi Akumulasi        
       l_Persen           := CASE WHEN l_T17Persen<>0 THEN l_T17Persen ELSE l_M03Persen END;
       l_PendAkumulasi    := fn_Vround((l_FixIncome*l_Persen)/100);
       l_PendAkumulasiVal := fn_Vround(((l_FixIncome*l_Persen)/100)/fn_GetKurs(l_M03KdCurr,l_TglProses));
              
       -- Insert/Update Pendapatan Akumulasi
       IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt=l_T17FlgDpPt AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_T17KdDpPt) IS NOT NULL THEN 
            UPDATE S02DGAJ
            SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_PendAkumulasiVal) ::VARCHAR(17),l_MyPass),
                EncNilai=fn_kcabang(l_NIP,(fn_kpusat(l_NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_PendAkumulasi) :: VARCHAR(17), l_MyPass)
            WHERE TglPayr=l_TglProses AND FlgDpPt=l_T17FlgDpPt AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_T17KdDpPt;
       ELSE
            INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,     KdDpPt,     FlgAngs,Singkatan ,    EncNilai                                                   ,Nilai,KdCurr     ,EncNilaiVal                                                   ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                         VALUES(l_TglProses,l_NIP,l_T17FlgDpPt,l_T17KdDPPt,' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_PendAkumulasi :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_PendAkumulasiVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0,        l_S01_ID);
       END IF;    
   END LOOP;
   CLOSE l_LOOP_T17;
END IF; 
END ; 
$$ LANGUAGE plpgsql ;   

/*
EXEC P_PendAkumulasi '2003-01-20',
                     'TEST',
                     'T',
                     'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
*/



