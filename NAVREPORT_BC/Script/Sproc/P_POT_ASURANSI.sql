/******************************************
Name sprocs	: P_Pot_Asuransi
Create by	: Wati
Date		: 19-06-2003
Description	: Proses Hitung Asuransi
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE FUNCTION P_Pot_Asuransi(l_TglProses  DATE,
					  l_NIP        VARCHAR(10),
					  l_MyPass     VARCHAR(128),
					  l_S01_ID     INT)      
RETURNS VOID 
AS $$
DECLARE l_FixIncome          	DECIMAL(15,2);
        l_LOOP_T22           	REFCURSOR;
        l_Asuransi           	DECIMAL(15,2);
        l_AsuransiVal        	DECIMAL(15,2); 
        l_M44JenisAsuransi   	VARCHAR(1);
        l_M44PremiPegawai    	DECIMAL(6,3);
        l_M44PremiPerusahaan 	DECIMAL(6,3);
        l_M44FlgDpPt1        	VARCHAR(1);
        l_M44KdDpPt1         	VARCHAR(4);
        l_M44FlgDpPt2        	VARCHAR(1);
        l_M44KdDpPt2         	VARCHAR(4);
        l_M03Singkatan  	VARCHAR(10);
        l_M03UsComp     	VARCHAR(1);
        l_M03Kolom     		VARCHAR(2);
        l_M03NoAcc      	VARCHAR(10);
        l_M03Status     	VARCHAR(1);
        l_M03Persen     	DECIMAL(5,2);
        l_M03Nilai      	DECIMAL(15,2);
	l_M03NoLyr     		INT;
	l_M03KdCurr     	VARCHAR(4);
BEGIN 	
-- Hitung FixIncome
SELECT COALESCE(TBL.FixIncome,0)
INTO   l_FixIncome 
FROM
(
SELECT S02.NIP,FixIncome=SUM(fn_KPusat(l_NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2))
FROM S02DGAJ S02
INNER JOIN M03DPPT M03 
ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
WHERE S02.NIP=l_NIP AND M03.STATUS = '1' AND S02.TglPayr=l_TglProses 
GROUP BY S02.NIP
) TBL;

-- Looping T22KPAS Inner Join M44JASU

OPEN l_LOOP_T22 FOR EXECUTE('SELECT M44.JenisAsuransi,M44.PremiPegawai,M44.PremiPerusahaan,
				    M44.FlgDpPt1,M44.KdDpPt1,M44.FlgDpPt2,M44.KdDpPt2
			     FROM T22KPAS T22
			     INNER JOIN M44JASU M44
			     ON M44.Kode=T22.Kode
			     WHERE T22.NIP=l_NIP AND COALESCE(T22.NoPeserta,'' '')<>'' '' AND COALESCE(T22.TanggalMasuk,'' '')<=l_Tglproses');
<<LOOP_M15>> 
LOOP 
   FETCH l_LOOP_T22 INTO l_M44JenisAsuransi,l_M44PremiPegawai,l_M44PremiPerusahaan,l_M44FlgDpPt1,l_M44KdDpPt1,l_M44FlgDpPt2,l_M44KdDpPt2;
   IF NOT FOUND THEN
      EXIT ;
   END IF;
   -- Check Jenis Asuransi
    IF l_M44JenisAsuransi='1' THEN 
       BEGIN
         -- asuransi Pensiunan
         l_Asuransi := COALESCE(fn_Vround((l_FixIncome*l_M44PremiPegawai)/100),0); 
 
         -- Ambil Nilai Master Pandapatan dan Potongan
	 SELECT  P_M03AllField (l_M44FlgDpPt1, l_M44KdDpPt1 )
	 INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
		 l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;         
        -- Get Kurs
         l_AsuransiVal := COALESCE(fn_Vround(l_Asuransi/fn_GetKurs(l_M03KdCurr,l_TglProses)),0);
 
        -- Insert/Update Asuransi Pensiunan
        IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt=l_M44FlgDpPt1 AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_M44KdDpPt1) IS NOT NULL THEN 
           BEGIN
             UPDATE S02DGAJ
             SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_AsuransiVal) ::VARCHAR(17),l_MyPass),
                 EncNilai    =fn_kcabang(l_NIP,(fn_kpusat(l_NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_Asuransi) :: VARCHAR(17),l_MyPass)
             WHERE TglPayr=l_TglProses AND FlgDpPt=l_M44FlgDpPt1 AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_M44KdDpPt1;
           END;
        ELSE
           BEGIN
             INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,      KdDpPt,      FlgAngs,Singkatan ,    EncNilai                                              ,Nilai,KdCurr     ,EncNilaiVal                                              ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                          VALUES(l_TglProses,l_NIP,l_M44FlgDpPt1,l_M44KdDpPt1,' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_Asuransi :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_AsuransiVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);	 
           END;
        END IF; 
       END;     
    ELSE
       BEGIN
         -- Asuransi Selain Pensiunan 
         l_Asuransi := COALESCE(fn_Vround(((l_FixIncome*l_M44PremiPerusahaan)/100)+((l_FixIncome*l_M44PremiPegawai)/100)),0);
 
         -- Ambil Nilai Pendapatan Untuk Asuransi Selain Pensiunan
	 SELECT  P_M03AllField (l_M44FlgDpPt1, l_M44KdDpPt1 )
	 INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
		 l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;
        -- Get Kurs
         l_AsuransiVal := COALESCE(fn_Vround(l_Asuransi/fn_GetKurs(l_M03KdCurr,l_TglProses)),0);
 
        -- Insert/Update Asuransi Selain Pensiunan untuk Pendapatan
         IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt=l_M44FlgDpPt1 AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_M44KdDpPt1) IS NOT NULL THEN 
            BEGIN
              UPDATE S02DGAJ
              SET EncNilaiVal =fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_AsuransiVal) ::VARCHAR(17),l_MyPass),
                  EncNilai=fn_kcabang(l_NIP,(fn_kpusat(l_NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_Asuransi) :: VARCHAR(17),l_MyPass)
              WHERE TglPayr=l_TglProses AND FlgDpPt=l_M44FlgDpPt1 AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_M44KdDpPt1;
            END; 
         ELSE
            BEGIN
              INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,      KdDpPt,      FlgAngs,Singkatan ,    EncNilai                                              ,Nilai,KdCurr     ,EncNilaiVal                                              ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                           VALUES(l_TglProses,l_NIP,l_M44FlgDpPt1,l_M44KdDpPt1,' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_Asuransi :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_AsuransiVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID); 
            END;
         END IF;          

         -- Ambil Nilai untuk Potongan
	 SELECT  P_M03AllField (l_M44FlgDpPt2, l_M44KdDpPt2 )
	 INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
		 l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;
         -- Get Kurs
         l_AsuransiVal := COALESCE(fn_Vround(l_Asuransi/fn_GetKurs(l_M03KdCurr,l_TglProses)),0); 
 
        -- Insert/Update Asuransi Selain Pensiunan untuk Potongan
         IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt=l_M44FlgDpPt2 AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_M44KdDpPt2) IS NOT NULL THEN 
            BEGIN
              UPDATE S02DGAJ
              SET EncNilaiVal = fn_kcabang(l_NIP,(fn_kpusat(l_NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_AsuransiVal) ::VARCHAR(17),l_MyPass),
                  EncNilai    = fn_kcabang(l_NIP,(fn_kpusat(l_NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_Asuransi) :: VARCHAR(17),l_MyPass)
              WHERE TglPayr=l_TglProses AND FlgDpPt=l_M44FlgDpPt2 AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_NIP AND KdDpPt=l_M44KdDpPt2;
            END;
         ELSE
            BEGIN 
              INSERT INTO S02DGAJ(TglPayr    ,NIP  ,FlgDpPt,      KdDpPt,      FlgAngs,Singkatan ,    EncNilai                                              ,Nilai,KdCurr     ,EncNilaiVal                                              ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                           VALUES(l_TglProses,l_NIP,l_M44FlgDpPt2,l_M44KdDpPt2,' '    ,l_M03Singkatan,fn_kcabang(l_NIP,(l_Asuransi :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_NIP,(l_AsuransiVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);	 
            END;
         END IF;    
       END; 
    END IF; 
END LOOP;
CLOSE l_LOOP_T22;
END;
$$ LANGUAGE plpgsql ;


/*
EXEC P_Pot_Asuransi '2003-01-20',
                    'TEST',
                    'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
*/
