/****************************************
Name sprocs	: P_DasarKelompok
Create by	: wati
Date		: 16-06-2003
Description	: Proses Hitung Pendapatan/Potongan 
                  Cabang/Golongan/Kel.Jabatan/Jabatan
Call From	: Main sprocs
pendapatan dasar kelompok, flag trans = T ( peggy 2007 04 26) 
*****************************************/
DROP FUNCTION  P_DasarKelompok(l_TglProses  DATE,
					    l_Kode	 VARCHAR(4), 
					    l_NIP        VARCHAR(10), 
					    l_NmTable    VARCHAR(128),
					    l_FlagKhusus VARCHAR(1),
					    l_GajiRp     DECIMAL(15,2),
					    l_mnHari	 DECIMAL(4,1),
					    l_Prs_Harian VARCHAR(1),
					    l_HKerja	 DECIMAL(4,1),	
					    l_MyPass     VARCHAR(128));
--
CREATE FUNCTION  P_DasarKelompok(l_TglProses  DATE,
					    l_Kode	 VARCHAR(4), 
					    l_NIP        VARCHAR(10), 
					    l_NmTable    VARCHAR(128),
					    l_FlagKhusus VARCHAR(1),
					    l_GajiRp     DECIMAL(15,2),
					    l_mnHari	 DECIMAL(4,1),
					    l_Prs_Harian VARCHAR(1),
					    l_HKerja	 DECIMAL(4,1),	
					    l_MyPass     VARCHAR(128))

RETURNS VOID 
AS $$
--

DECLARE l_INSERT  VARCHAR(4000);
        l_UPDATE  VARCHAR(4000);
	l_Para	  VARCHAR(4000);
	l_Kondisi VARCHAR(4000);
BEGIN 
--
IF l_NmTable='M09DCAB' THEN 
   BEGIN
      l_Kondisi:='M09.Trans = ''T'' AND M09.kdCab=$4 AND M46.FlgDpPt ';
   END;
ELSIF l_NmTable='M13DGOL' THEN 
   BEGIN
      l_Kondisi:='M09.Trans = ''T'' AND M09.kode=$4 AND M46.FlgDpPt ';
   END;
ELSIF l_NmTable='M07DKJB' THEN 
   BEGIN
      l_Kondisi:='M09.Trans = ''T'' AND M09.kode=$4 AND M46.FlgDpPt ';
   END;
ELSIF l_NmTable='M05DJAB' THEN 
   BEGIN
      l_Kondisi:='M09.Trans = ''T'' AND M09.kode=$4 AND M46.FlgDpPt ';
   END;
END IF; 
---*
--
l_Kondisi:=CASE WHEN l_FlagKhusus='Y' 
                    THEN l_Kondisi+'IS NOT NULL'
                  ELSE
                    l_Kondisi+'IS NULL'
                  END ;  
--
--
/*******************************************
  Insert Kode baru yang belum terdefinisi
********************************************/
EXECUTE 
'    INSERT INTO S02DGAJ(TglPayr   ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                         Nilai,KdCurr    ,EncNilaiVal,
                         NilaiVal,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL, S01_ID)
                 SELECT  TBL.TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,'' '',TBL.SkDpPt,
                         fn_kcabang(TBL.NIP,''0'',$3) AS EncNilai, 
                         0 AS Nilai   ,TBL.KdCurr,fn_kcabang(TBL.NIP,''0'' ,$3) AS EncNilaiVal,
                         0 AS NilaiVal,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,TBL.FlgNonGL, TBL.S01_ID 
     FROM 
     (SELECT DISTINCT $1 AS TglProses,$2 AS NIP,M09.FlgDpPt,M09.KdDpPt,M03.SkDpPt,
                      CASE WHEN (M09.Nilai=0 AND M09.Persen=0) THEN M03.KdCurr ELSE M09.KdCurr END AS KdCurr,
                      M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar,0 AS FlgNonGL, $5 AS S01_ID 
     FROM  '||l_NmTable||' M09
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=M09.FlgDpPt AND M03.KdDpPt= M09.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=M09.FlgDpPt AND M46.KdDpPt= M09.KdDpPt
     WHERE '||l_Kondisi||') TBL 
     -- 
     LEFT JOIN S02DGAJ S02
     ON S02.TglPayr=$1 AND S02.NIP=$2 AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdDpPt AND COALESCE(S02.FlgAngs,'' '')='' ''
     WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL;'
USING l_TglProses, l_NIP, l_MyPass, l_kode, l_S01_ID;
				    
--
/********************
   Update Data 
*********************/

EXECUTE 'UPDATE S02DGAJ
    fn_kcabang($2,(fn_kPusat($2,EncNilai,$3) ::DECIMAL(15,2) +TBL11.Nilai) ::VARCHAR(17),$3) AS EncNilai,
    fn_kcabang($2,(fn_kPusat($2,EncNilaiVal,$3) ::DECIMAL(15,2)+TBL11.NilaiVal) ::VARCHAR(17),$3) AS EncNilaiVal
FROM 
--
(
--
SELECT $1 AS TglProses,$2 AS NIP,TBL1.FlgDpPt,TBL1.KdDpPt,
       fn_Vround(TBL1.Nilai) AS Nilai,
       fn_Vround(TBL1.Nilai/fn_getkurs(TBL1.kdCurr,$1)) AS NilaiVal 
FROM 
(
SELECT M09.FlgDpPt,M09.KdDpPt,CASE WHEN (M09.Nilai=0 AND M09.Persen=0) THEN M03.KdCurr ELSE M09.KdCurr END AS KdCurr,
       SUM(fn_NilaiPend_Pot($1,$2,M09.FlgDpPt,M09.KdDpPt,M09.Persen,M09.Nilai,
			       CASE WHEN M03.JnDpPt=''B'' THEN 1
			       ELSE
				 CASE WHEN $6=''Y'' THEN $7
				 ELSE
				   $8 
				 END
			       END,l_GajiRp1,''T'',$3,
			       CASE WHEN (M09.Nilai=0 AND M09.Persen=0) THEN M03.KdCurr ELSE M09.KdCurr END)) 
	  * (FN_GETPROP($2,$8,$1)/$8) AS Nilai
FROM '||l_NmTable||' M09
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=M09.FlgDpPt AND M03.KdDpPt= M09.KdDpPt
LEFT JOIN M46PPKH M46
ON M46.FlgDpPt=M09.FlgDpPt AND M46.KdDpPt= M09.KdDpPt
WHERE '||l_Kondisi||' 
GROUP BY M09.FlgDpPt,M09.KdDpPt,CASE WHEN (M09.Nilai=0 AND M09.Persen=0) THEN M03.KdCurr ELSE M09.KdCurr END
) TBL1 
) TBL11
INNER JOIN S02DGAJ S02 
ON S02.TglPayr=$1 AND S02.NIP=$2 AND S02.FlgDpPt=TBL11.FlgDpPt AND S02.KdDpPt=TBL11.KdDpPt;'
USING l_TglProses, l_NIP, l_MyPass, l_kode, l_GajiRP,l_Prs_Harian,l_HKerja,l_mnHari;

END; 
$$ LANGUAGE plpgsql ;


---END OF PROCEDURE
/*
EXEC P_DasarKelompok        '2003-01-03',
			    'AB47',
                            'AIK',
			    'M09DCAB ',
                            'T',
                            150000,
                            22,
			    'Y',
			    3,
                            'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'

select * from s02dgaj
where NIP='08'

delete from s02dgaj where NIP='08' AND kddppt<>'BSAL'
*/
