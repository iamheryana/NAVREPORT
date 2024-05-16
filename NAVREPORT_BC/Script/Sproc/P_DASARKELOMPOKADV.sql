/****************************************
Name sprocs	: P_DasarKelompokAdv
Create by	: Peggy
Date		: 27-06-2008
Description	: Proses Hitung Pendapatan/Potongan Kelompok Advance 
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_DasarKelompokAdv(l_TglProses    DATE,
						l_NIP        VARCHAR(10), 
						l_FlagKhusus VARCHAR(1),
						l_GajiRp     DECIMAL(15,2),
						l_mnHari     DECIMAL(4,1),
						l_Prs_Harian VARCHAR(1),
						l_HKerja     DECIMAL(4,1),	
						l_MyPass     VARCHAR(128),
						l_S01_ID     INT);
--
CREATE FUNCTION P_DasarKelompokAdv(l_TglProses    DATE,
						l_NIP        VARCHAR(10), 
						l_FlagKhusus VARCHAR(1),
						l_GajiRp     DECIMAL(15,2),
						l_mnHari     DECIMAL(4,1),
						l_Prs_Harian VARCHAR(1),
						l_HKerja     DECIMAL(4,1),	
						l_MyPass     VARCHAR(128),
						l_S01_ID     INT)
RETURNS VOID 
AS $$ 
--
DECLARE l_M15kdCaba  VARCHAR(4);
	l_M15KdGlng  VARCHAR(4);
	l_M15kdJaba  VARCHAR(4);
	l_M15kdKJab  VARCHAR(4);
	l_M15kdArea  VARCHAR(4);
	l_M15kdUUsa  VARCHAR(4);
	l_M15kdUKer  VARCHAR(8);
--
DECLARE l_INSERT  VARCHAR(4000);
        l_UPDATE  VARCHAR(4000);
	l_Para	  VARCHAR(4000);
	l_Kondisi VARCHAR(4000);

BEGIN 	
CREATE TEMP TABLE l_TEMP (Level1	VARCHAR(1), 
			  Kode1		VARCHAR(8), 
			  Level2	VARCHAR(1), 
			  Kode2		VARCHAR(8), 
			  FlgDppt 	VARCHAR(1), 
			  KdDppt  	VARCHAR(4), 
			  Persen	DECIMAL(5,2), 
			  Nilai		DECIMAL(15,2), 
			  KdCurr	VARCHAR(4),
			  Trans		VARCHAR(1),
			  NoUrut	SERIAL)  ON COMMIT DROP ;          
--
SELECT M15.KdCaba,  M15.KdGlng,  M15.KdJaba,  M15.KdKJab,  M15.KdArea,  M15.KdUUsa,  M15.KdUKer 
INTO   l_M15kdCaba, l_M15KdGlng, l_M15kdJaba, l_M15kdKJab, l_M15kdArea, l_M15kdUUsa, l_M15kdUKer
FROM M15PEGA M15
WHERE NIP = l_NIP ; 
--
INSERT INTO l_TEMP 
SELECT M64.LEVEL1, M64.KODE1,
	M64.LEVEL2, M64.KODE2, 
	M65.FlgDppt, M65.KdDppt, 
	M65.Persen, M65.NILAI, M65.KDCURR, M65.Trans    
FROM M64ADVH M64
LEFT JOIN M65ADVD M65 
ON M64.Level1 = M65.Level1 AND M64.Kode1 = M65.Kode1 AND 
   M64.Level2 = M65.Level2 AND M64.Kode2 = M65.Kode2 
LEFT JOIN M03DPPT M03
ON M65.FlgDppt = M03.FlgDppt AND M65.KdDppt = M03.KdDppt 
WHERE (l_M15KDAREA = CASE WHEN M64.Level1 = '1' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDCABA = CASE WHEN M64.Level1 = '2' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDUUSA = CASE WHEN M64.Level1 = '3' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDUKER = CASE WHEN M64.Level1 = '4' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDGLNG = CASE WHEN M64.Level1 = '5' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDKJAB = CASE WHEN M64.Level1 = '6' THEN M64.Kode1 ELSE '########' END OR 
	     l_M15KDJABA = CASE WHEN M64.Level1 = '7' THEN M64.Kode1 ELSE '########' END)
	AND (l_M15KDAREA = CASE WHEN M64.Level2 = '1' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDCABA = CASE WHEN M64.Level2 = '2' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDUUSA = CASE WHEN M64.Level2 = '3' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDUKER = CASE WHEN M64.Level2 = '4' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDGLNG = CASE WHEN M64.Level2 = '5' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDKJAB = CASE WHEN M64.Level2 = '6' THEN M64.Kode2 ELSE '########' END OR 
	     l_M15KDJABA = CASE WHEN M64.Level2 = '7' THEN M64.Kode2 ELSE '########' END OR 
		 M64.Level2 = '8') ; 
--
l_Kondisi := 'M46.FlgDpPt ';
---*
l_Kondisi := CASE WHEN l_FlagKhusus='Y' 
                    THEN l_Kondisi || ' IS NOT NULL'
                  ELSE
                    l_Kondisi || ' IS NULL' 
                  END  ; 
--
/*******************************************
  Insert Kode baru yang belum terdefinisi
********************************************/
EXECUTE 'INSERT INTO S02DGAJ(TglPayr      ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                             Nilai     ,KdCurr    ,EncNilaiVal,
                             NilaiVal     ,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL,     S01_ID)
                     SELECT  TBL.TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,'' ''  ,TBL.SkDpPt,fn_kcabang(TBL.NIP,''0'',$3) AS EncNilai, 
                             0 AS Nilai,TBL.KdCurr,fn_kcabang(TBL.NIP,''0'' ,$3) AS EncNilaiVal,
                             0 AS NilaiVal,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,TBL.FlgNonGL, TBL.S01_ID
     FROM 
     (SELECT DISTINCT $1 AS TglProses,$2 AS NIP,M09.FlgDpPt,M09.KdDpPt,M03.SkDpPt,
                      CASE WHEN (M09.Nilai=0 AND M09.Persen=0) THEN M03.KdCurr ELSE M09.KdCurr END AS KdCurr,
		      M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar,0 AS FlgNonGL, $8 AS S01_ID
     FROM  l_TEMP M09
     INNER JOIN M03DPPT M03
     ON M03.FlgDpPt=M09.FlgDpPt AND M03.KdDpPt= M09.KdDpPt
     LEFT JOIN M46PPKH M46
     ON M46.FlgDpPt=M09.FlgDpPt AND M46.KdDpPt= M09.KdDpPt
     WHERE ' || l_Kondisi || ' AND M09.TRANS <> ''Y'' 
     ) TBL 
     LEFT JOIN S02DGAJ S02
     ON S02.TglPayr=$1 AND S02.NIP=$2 AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdDpPt AND COALESCE(S02.FlgAngs,'' '')='' ''
     WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL ; '
USING l_TglProses, l_NIP, l_MyPass, l_GajiRP, l_Prs_Harian, l_HKerja, l_mnHari, l_S01_ID;
				    
--
/********************
   Update Data 
*********************/
EXECUTE 'UPDATE S02DGAJ
SET fn_kcabang($2,(fn_kPusat($2,EncNilai,$3)::DECIMAL(15,2)+TBL11.Nilai) :: VARCHAR(17),$3) AS EncNilai,
    fn_kcabang($2,(fn_kPusat($2,EncNilaiVal,$3)::DECIMAL(15,2)+TBL11.NilaiVal):: VARCHAR(17),$3) AS EncNilaiVal
FROM 
(
SELECT $1 AS TglProses,$2 AS NIP,TBL1.FlgDpPt,TBL1.KdDpPt,
       fn_Vround(TBL1.Nilai) AS Nilai,
       fn_Vround(TBL1.Nilai/fn_getkurs(TBL1.kdCurr,$1)) AS NilaiVal 
FROM 
(
SELECT M09.FlgDpPt,M09.KdDpPt,CASE WHEN (M09.Nilai=0 AND M09.Persen=0) THEN M03.KdCurr ELSE M09.KdCurr END AS KdCurr,
       SUM(fn_NilaiPend_Pot($1,$2,M09.FlgDpPt,M09.KdDpPt,M09.Persen,M09.Nilai,
			       CASE WHEN M03.JnDpPt=''B'' THEN 1
			       ELSE
				 CASE WHEN $5=''Y'' THEN $6
				 ELSE
				   $7 
				 END
			       END,$4,''T'',$3,CASE WHEN (M09.Nilai=0 AND M09.Persen=0) THEN M03.KdCurr ELSE M09.KdCurr END)) 
	 * (FN_GETPROP($2,$7,$1)/$7) AS Nilai
FROM l_TEMP M09
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=M09.FlgDpPt AND M03.KdDpPt= M09.KdDpPt
LEFT JOIN M46PPKH M46
ON M46.FlgDpPt=M09.FlgDpPt AND M46.KdDpPt= M09.KdDpPt
WHERE ' || l_Kondisi || ' AND M09.TRANS <> ''Y''
GROUP BY M09.FlgDpPt,M09.KdDpPt,CASE WHEN (M09.Nilai=0 AND M09.Persen=0) THEN M03.KdCurr ELSE M09.KdCurr END
) TBL1 
) TBL11
INNER JOIN S02DGAJ S02 
ON S02.TglPayr=$1 AND S02.NIP=$2 AND S02.FlgDpPt=TBL11.FlgDpPt AND S02.KdDpPt=TBL11.KdDpPt ;'
USING l_TglProses, l_NIP, l_MyPass, l_GajiRP, l_Prs_Harian, l_HKerja, l_mnHari ; 

END; 
$$ LANGUAGE plpgsql ;

---END OF PROCEDURE
/*
EXEC P_DasarKelompokAdv        '2008-06-23',
						    '00013',
                            'T',
                            2000000,
                            22,
			    'Y',
			    3,
                            'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'

select * from s02dgaj
where NIP='00013'

delete from s02dgaj where NIP='08' AND kddppt<>'BSAL'
*/
