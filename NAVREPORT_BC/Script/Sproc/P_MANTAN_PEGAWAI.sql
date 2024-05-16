/****************************************
Name sprocs	: P_Mantan_Pegawai
Create by	: Wati
Date		: 05-07-2003
Description	: Proses Mantan Pegawai
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Mantan_Pegawai(l_NIP       VARCHAR(10),
						l_TglAkhir  DATE,
						l_TglProses DATE,
						l_GajiRp    DECIMAL(15,2),
						l_mnHari    DECIMAL(4,1),
						l_MyPass    VARCHAR(128), 
						l_S01_ID    INT);
--
CREATE FUNCTION P_Mantan_Pegawai(l_NIP       VARCHAR(10),
						l_TglAkhir  DATE,
						l_TglProses DATE,
						l_GajiRp    DECIMAL(15,2),
						l_mnHari    DECIMAL(4,1),
						l_MyPass    VARCHAR(128), 
						l_S01_ID    INT)

RETURNS VOID 
AS $$
BEGIN 
-- Insert Detail Penggajian
INSERT INTO S02DGAJ(TglPayr   ,NIP    ,FlgDpPt    ,KdDpPt    ,FlgAngs,Singkatan ,EncNilai,
                    Nilai,KdCurr    ,EncNilaiVal,
                    NilaiVal,Kolom    ,NoAcc    ,UsComp,    NoLyr      ,FlgNonGL, S01_ID)
            SELECT  l_TglProses,TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,' '    ,TBL.SkDpPt,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                    0    ,TBL.KdCurr,fn_kcabang(TBL.NIP,'0' ,l_MyPass),
                    0       ,TBL.Kolom,TBL.NoAcc,TBL.UsComp,TBL.NoLayar,0      , l_S01_ID
FROM 
(SELECT DISTINCT T23.NIP,T23.FlgDpPt,T23.KdDpPt,M03.SkDpPt,
		 CASE WHEN T23.Nilai=0 THEN M03.KdCurr ELSE T23.KdCurr END AS KdCurr,
                 M03.Kolom,M03.NoAcc,M03.UsComp,M03.NoLayar
FROM T23MPEG T23
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=T23.FlgDpPt AND M03.KdDpPt= T23.KdDpPt
WHERE T23.NIP=l_NIP AND (l_TglAkhir IS NULL OR T23.Tanggal>l_TglAkhir) AND T23.Tanggal<=l_TglProses
) TBL 
-- 
LEFT JOIN S02DGAJ S02
ON S02.TglPayr=l_TglProses AND S02.NIP=TBL.NIP AND S02.FlgDpPt=TBL.FlgDpPt AND S02.KdDpPt=TBL.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
WHERE S02.TglPayr IS NULL AND S02.NIP IS NULL AND S02.FlgDpPt IS NULL AND S02.KdDpPt IS NULL AND S02.FlgAngs IS NULL;

-- Update Detail Penggajian Untuk Nilai<>0 
UPDATE S02DGAJ S02U
SET EncNilai    =fn_kcabang(TBL1.NIP,(fn_kPusat(TBL1.NIP,S02U.EncNilai,l_MyPass)    ::DECIMAL(15,2)+TBL1.Nilai)    ::VARCHAR(17),l_MyPass),
    EncNilaiVal =fn_kcabang(TBL1.NIP,(fn_kPusat(TBL1.NIP,S02U.EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+TBL1.NilaiVal) ::VARCHAR(17),l_MyPass)
FROM 
(
SELECT l_TglProses AS TglProses,
	TBL.NIP,TBL.FlgDpPt,TBL.KdDpPt,
	fn_Vround(TBL.Nilai) AS Nilai,
        fn_Vround(TBL.Nilai/fn_GetKurs(TBL.KdCurr,l_TglProses)) AS NilaiVal
FROM 
(
SELECT T23.NIP,T23.FlgDpPt,T23.KdDpPt,
       CASE WHEN T23.Nilai=0 THEN M03.KdCurr ELSE T23.KdCurr END AS KdCurr,
       SUM(fn_NilaiPend_Pot(l_TglProses,T23.NIP,T23.FlgDpPt,T23.KdDpPt,0,T23.Nilai,
-- BY PEGGY 2006 11 22 : MANTAN PEGAWAI GAK PERLU PROPORSIONAL 
                 CASE WHEN M03.JnDpPt='B' THEN 1 ELSE l_mnHari END,l_GajiRp,'T',l_MyPass,
                 CASE WHEN T23.Nilai=0 THEN M03.KdCurr ELSE T23.KdCurr END)) AS NILAI
--* CASE WHEN M03.JnDpPt='B' THEN 1 ELSE (FN_GETPROP(T23.NIP,l_MNHARI,l_TGLPROSES)/l_mnHari) END

FROM T23MPEG T23
INNER JOIN M03DPPT M03
ON M03.FlgDpPt=T23.FlgDpPt AND M03.KdDpPt= T23.KdDpPt
WHERE T23.NIP=l_NIP AND (l_TglAkhir IS NULL OR T23.Tanggal>l_TglAkhir) AND T23.Tanggal<=l_TglProses
GROUP BY T23.NIP,T23.FlgDpPt,T23.KdDpPt,CASE WHEN T23.Nilai=0 THEN M03.KdCurr ELSE T23.KdCurr END
) TBL 
--
) TBL1 
--
INNER JOIN S02DGAJ S02 
ON S02.TglPayr=TBL1.TglProses AND S02.NIP=l_NIP AND S02.FlgDpPt=TBL1.FlgDpPt AND S02.KdDpPt=TBL1.KdDpPt AND COALESCE(S02.FlgAngs,' ')=' '
WHERE S02.TglPayr=S02U.TglPayr AND S02.NIP=S02U.NIP AND S02.FlgDpPt=S02U.FlgDpPt AND S02.KdDpPt=S02U.KdDpPt AND 
	COALESCE(S02.FlgAngs,' ')=COALESCE(S02U.FlgAngs,' ');
    

END;
$$ LANGUAGE plpgsql ;


