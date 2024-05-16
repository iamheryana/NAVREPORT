/****************************************
Name sprocs	: R_SPENP1
Create by	: Deni
Date		: 07-02-2014
Description	: Summary of Pensiun
*****************************************/
DROP FUNCTION R_SPENP1 (  l_Periode       DATE,
                            l_FCab          VARCHAR(4),
                            l_TCab          VARCHAR(4),
                            l_PerCab        VARCHAR(1),
                            l_mcJudulCab	VARCHAR(4),
                            l_Mypass		VARCHAR(128),
                            l_UserId		INT);

CREATE FUNCTION R_SPENP1 (  l_Periode       DATE,
                            l_FCab          VARCHAR(4),
                            l_TCab          VARCHAR(4),
                            l_PerCab        VARCHAR(1),
                            l_mcJudulCab	VARCHAR(4),
                            l_Mypass		VARCHAR(128),
                            l_UserId		INT)
--
RETURNS TABLE (
		OUTNIP              VARCHAR(10),
        OUTNoAstek          VARCHAR(15),
		OUTNama             VARCHAR(25),
		OUTStatus           VARCHAR(2),
		OUTKdJaba           VARCHAR(4),
		OUTKetJabatan       VARCHAR(25),
		OUTJKKPersen        INT,
		OUTKdCaba           VARCHAR(4),
		OUTNmCabang         VARCHAR(25),
		OUTPindahCab        VARCHAR(1),
		OUTJKKNilai         DECIMAL(15,2),
		OUTJKMNilai         DECIMAL(15,2),
		OUTGajiPokok        DECIMAL(15,2),
		OUTJPKNilai         DECIMAL(15,2),
		OUTJHTNilai         DECIMAL(15,2))
--
AS $$
--
DECLARE l_Var_Round     VARCHAR(1);
		l_V_Round       INT;
		l_Pgwi          DECIMAL(15,2);
		l_Perush		DECIMAL(15,2);
		l_PerushJKM     DECIMAL(15,2);
		l_PindahCab 	VARCHAR(1);
		l_Cabang		VARCHAR(4);
		l_NmCabang      VARCHAR(40);
--
BEGIN
    SELECT SUBSTRING(STRINGFLAG,2,1) INTO l_PindahCab
    FROM FZ2FLDA; 
    --
    SELECT KdCaba, NmCaba INTO l_cabang, l_nmcabang
    FROM M08HCAB 
    WHERE KdCaba=l_mcJudulCab; 
    --
    SELECT VarRound,(CASE WHEN VarRound='2' THEN -1
                          WHEN VarRound='3' THEN -2
                          WHEN VarRound='4' THEN -3
                     ELSE 1 END) AS VRound
    INTO l_Var_Round, l_V_Round
    FROM FZ1FLDA;
    -- 
    SELECT Pgwi, Perush  INTO l_Pgwi, l_Perush
    FROM M18JAMS WHERE Progrm='JHT' ;
    --
    SELECT Perush INTO l_PerushJKM
    FROM M18JAMS WHERE Progrm='JKM' ;
    -- 
    RETURN QUERY
    SELECT  Q1.NIP, Q1.NoAstek, Q1.Nama, Q1.Status, Q1.KdJaba, Q1.KetJabatan, Q1.JKK_Persen, 
            Q1.Cabang, Q1.NmCabang, Q1.PindahCab,  
            SUM(JKK_Nilai) AS JKK_Nilai, 
            SUM(JKM_Nilai) AS JKM_Nilai, 
            SUM(GajiPokok) AS GajiPokok, 
            SUM(JPK_Nilai) AS JPK_Nilai, 
            SUM(JHT_Nilai) AS JHT_Nilai
    FROM 
    (--Q1
    SELECT  Q.NIP, Q.NoAstek, Q.Nama, Q.Status, Q.KdJaba, Q.KetJabatan, Q.JKK_Persen, 
            Q.Cabang, Q.NmCabang, Q.PindahCab,
            CASE WHEN Q.JKK = 'Y' AND Q.KdDppt = 'JKK' THEN Q.Nilai ELSE 0 END AS JKK_Nilai, 
            CASE WHEN Q.JKM = 'Y' AND Q.KdDppt = 'JKM' THEN Q.Nilai ELSE 0 END AS JKM_Nilai, 
            CASE WHEN Q.KdDppt NOT IN ('JKK','JKM','JHT','JPK') AND Q.M03Status='1' THEN 
                      Q.Nilai ELSE 0 END AS GajiPokok, 
            CASE WHEN Q.JHT = 'Y' AND Q.KdDppt = 'JHT' THEN ABS(Q.Nilai) ELSE 0 END AS JPK_Nilai,
            CASE WHEN Q.JHT = 'Y' AND Q.KdDppt = 'JHT' AND l_Pgwi <> 0 THEN  
                      COALESCE(fn_VRound((ABS(Q.Nilai)*(l_Pgwi+l_Perush))/l_Pgwi),0) - ABS(Q.Nilai) 
                 WHEN Q.JHT = 'Y' AND Q.KdDppt = 'JKM' AND l_Pgwi = 0 THEN 
                      COALESCE(fn_VRound((ABS(Q.Nilai)*(l_Pgwi+l_Perush))/l_PerushJKM),0) 
                 ELSE 0 END AS JHT_Nilai
    FROM 
    (--Q
    SELECT  S02.NIP, M15.KPA AS NoAstek, M15.Nama, '' :: VARCHAR(2) AS Status, '' :: VARCHAR(4) AS KdJaba, 
            '' :: VARCHAR(25) AS KetJabatan, M15.JKK, M15.JKM, M15.JPK, M15.JHT, S02.FlgDpPt, S02.KdDpPt, 
            M03.Status AS M03Status, 0 AS JKK_Persen, l_PindahCab AS PindahCab,
           COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai,
           CASE WHEN l_PerCab = 'T' THEN 
                     CASE WHEN l_PindahCab = '0' THEN '' ELSE l_Cabang END 
                 ELSE S01.KdCaba END AS Cabang, 
           CASE WHEN l_PerCab = 'T' THEN 
                     CASE WHEN l_PindahCab = '0' THEN '' ELSE l_NmCabang END 
                 ELSE M08.NmCaba END AS NmCabang 
    FROM S02DGAJ S02
    INNER JOIN M15PEGA M15 ON S02.NIP=M15.NIP
    INNER JOIN S01HGAJ S01 ON S01.NIP = S02.NIP AND S01.TglPayr = S02.TglPayr
    INNER JOIN M08HCAB M08 ON S01.KdCaba = M08.KdCaba 
    INNER JOIN M03DPPT M03 ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE TO_CHAR(S02.TglPayr,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM') AND
          (S01.KdCaba BETWEEN l_FCab AND l_TCab) AND
          M15.KPA<>' ' AND S02.FlgDpPt='D' AND M15.TGLKPA IS NOT NULL AND 
          TO_CHAR(M15.TglKPA,'YYYYMM') <= TO_CHAR(l_Periode,'YYYYMM')
    ) Q 
    ) Q1 
    GROUP BY Q1.NIP, Q1.NoAstek, Q1.Nama, Q1.Status, Q1.KdJaba, Q1.KetJabatan, Q1.JKK_Persen, 
             Q1.Cabang, Q1.NmCabang, Q1.PindahCab;
    --
END;
--
$$ LANGUAGE plpgsql ;


/* 
CREATE FUNCTION R_SPENP1 (  l_Periode       DATE,
                            l_FCab          VARCHAR(4),
                            l_TCab          VARCHAR(4),
                            l_PerCab        VARCHAR(1),
                            l_mcJudulCab	VARCHAR(4),
                            l_Mypass		VARCHAR(128),
                            l_UserId		INT)

select * from R_SPENP1('2013-11-08',' ','ZZZ','T','001','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)
*/







