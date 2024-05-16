/****************************************
Name sProcs	: R_RDPTP1
Create by	: DENI
Date		: 19-02-2014
Description	: Laporan Rekonsiliasi Pendapatan Tetap 
*****************************************/
DROP FUNCTION R_RDPTP1 (l_mdPeriode 	DATE,
                        l_mcNIPFr       VARCHAR(10),
                        l_mcNIPTo       VARCHAR(10),
                        l_mcUUsaFr      VARCHAR(4),
                        l_mcUUsaTo      VARCHAR(4),
                        l_Mypass        VARCHAR(128),
                        l_UserId        INT);
--
CREATE FUNCTION R_RDPTP1 (l_mdPeriode 	DATE,
                        l_mcNIPFr       VARCHAR(10),
                        l_mcNIPTo       VARCHAR(10),
                        l_mcUUsaFr      VARCHAR(4),
                        l_mcUUsaTo      VARCHAR(4),
                        l_Mypass        VARCHAR(128),
                        l_UserId        INT)
--
RETURNS TABLE (
        KdUUsa          VARCHAR(4) ,
        SkUUsa          VARCHAR(10) ,
		NIP             VARCHAR(10),
		Nama            VARCHAR(25),
        TglMasuk        DATE,
        TglKeluar       DATE,
		HariKerja       DECIMAL(3,0),
		GapokSekarang   DECIMAL(15,2),
		M03Keterangan   VARCHAR(25),
		KdPdpt          VARCHAR(1),
        PeriodeLalu     DATE,
		GajiBlnIni      DECIMAL(15,2),
		GajiBlnLalu     DECIMAL(15,2),
		Mutasi          DECIMAL(15,2),
		Saldo           DECIMAL(15,2))
--
AS $$
--
DECLARE l_FZ1FlgGGol    VARCHAR(1);
		l_mdPeriodeLalu	DATE ;
--
BEGIN
    --
    SELECT l_mdPeriode - INTERVAL '1 months' INTO l_mdPeriodeLalu;
    SELECT FlgGGol INTO l_FZ1FlgGGol FROM FZ1FLDA;
    --
    RETURN QUERY
    SELECT  Q3.KdUUsa, Q3.Keterangan, Q3.NIP, Q3.Nama, Q3.TglMasuk, Q3.TglKeluar, Q3.HariKerja, 
            Q3.GaPokSekarang, Q3.NmDpPt, Q3.KdDpPt, Q3.PrdLalu, Q3.GajiBlnIni, Q3.GajiBlnLalu, 
            Q3.Mutasi, Q3.Saldo
    FROM
    (--Q3
    SELECT  Q1.KdUUsa, Q1.Keterangan, ' ' :: VARCHAR(10) AS NIP, ' ' :: VARCHAR(25) AS Nama, 
            NULL :: DATE AS TglMasuk, NULL :: DATE AS TglKeluar, 
            0 :: DECIMAL(3,0) AS hariKerja, 0 :: DECIMAL(15,2) AS GaPokSekarang,
            0 :: DECIMAL(15,2) AS GajiBlnIni, 0 :: DECIMAL(15,2) AS GajiBlnLalu, 
            SUM(Q1.Saldo) AS Mutasi,
            ' ' :: VARCHAR(25) AS NmDpPt, ' ' :: VARCHAR(1) AS KdDpPt, 
            SUM(Q1.Saldo) AS Saldo,
            l_mdPeriodeLalu AS PrdLalu
    FROM            
    (--Q1
    SELECT  S01.KdUUsa, M02.Keterangan,
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Saldo
    FROM S02DGAJ S02 
    INNER JOIN S01HGAJ S01 ON S01.TGLPAYR=S02.TglPayr AND S01.NIP=S02.NIP 
    INNER JOIN M03DPPT M03 ON S02.FLGDPPT=M03.FLGDPPT AND S02.KDDPPT=M03.KDDPPT 
    INNER JOIN M02UUSA M02 ON S01.KdUUsa=M02.Kode
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
             FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE  TO_CHAR(S02.TGLPAYR,'YYYYMM')=TO_CHAR(l_mdPeriodeLalu,'YYYYMM') AND 
           S02.FLGDPPT='D' AND M03.Status='1' AND S01.FlagTHR=' ' AND 
           S01.NIP BETWEEN l_mcNIPFR AND l_mcNIPTO AND 
           S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo
    ) Q1
    GROUP BY Q1.KdUUsa, Q1.Keterangan
    --------
    UNION ALL 
    ----
    SELECT  Q2.KdUUsa, Q2.Keterangan, Q2.NIP, Q2.Nama, Q2.TglMasuk, Q2.TglKeluar, Q2.HariKerja, 
            Q2.GaPokSekarang, Q2.GajiBlnIni, Q2.GajiBlnLalu, 
            Q2.GajiBlnIni-Q2.GajiBlnLalu AS Mutasi,
            Q2.NmDpPt, Q2.KdDpPt, 
            0 :: DECIMAL(15,2) AS Saldo,
            l_mdPeriodeLalu AS PrdLalu
    FROM 
    (--Q2
    SELECT  Q1.kduusa, Q1.Keterangan, Q1.NIP, Q1.Nama, Q1.TglMasuk, Q1.TglKeluar, 
            CASE WHEN Q1.KdDpPt='BSAL' AND TO_CHAR(S01.TglPayr,'YYYYMM')=TO_CHAR(l_mdPeriode,'YYYYMM') THEN S01.Hari ELSE 0 END :: DECIMAL(3,0) AS hariKerja,
    		Q1.GaPokSekarang, Q1.NmDpPt, Q1.KdDpPt, 
            SUM(Q1.GajiBlnIni) AS GajiBlnIni, 
            SUM(Q1.GajiBlnLalu) AS GajiBlnLalu 
    FROM
    (--Q1
    SELECT  QA.kduusa, QA.Keterangan, QA.NIP, QA.Nama, QA.TglMasuk, QA.TglKeluar, 
            COALESCE(fn_GajiPokokRPT(l_FZ1FlgGGol, l_mdPeriode, QA.PrdTetap, QA.GajiPerc, QA.GajiTetap, QA.NIP, QA.TglMasuk, QA.KdGlng) :: DECIMAL(15,2),0) AS GaPokSekarang,
            QA.Nilai AS GajiBlnIni,
            0 :: DECIMAL(15,2) AS GajiBlnLalu,
            QA.NmDpPt, QA.KdDpPt
    FROM
    (--QA
    SELECT  M15.KdUUsa, M02.Keterangan, M15.NIP, M15.Nama, M15.TglMasuk, M15.TglKeluar, 
            M15.PrdTetap, M15.KdGlng, S02.TGLPAYR, M03.NmDpPt, S02.KdDpPt, 
            COALESCE(Fn_Kpusat(M15.NIP,M15.EncGajiPerc, l_Mypass) :: DECIMAL(15,2),0) AS GajiPerc,
            COALESCE(Fn_Kpusat(M15.NIP,M15.EncGajiTetap, l_Mypass) :: DECIMAL(15,2),0) AS GajiTetap,
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM M15PEGA M15 
    INNER JOIN S02DGAJ S02 ON S02.NIP=M15.NIP AND S02.FLGDPPT='D' AND
                              TO_CHAR(S02.TGLPAYR,'YYYYMM')=TO_CHAR(l_mdPeriode,'YYYYMM')
    INNER JOIN S01HGAJ S01 ON S01.TGLPAYR=S02.TGLPAYR AND S01.NIP=S02.NIP 
    INNER JOIN M02UUSA M02 ON M15.KdUUsa=M02.Kode
    INNER JOIN M03DPPT M03 ON S02.FLGDPPT=M03.FLGDPPT AND S02.KDDPPT=M03.KDDPPT 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
             FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.NIP BETWEEN l_mcNIPFR AND l_mcNIPTO) AND 
          (S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND
          M03.Status='1' AND S01.FlagTHR=' ' 
    )QA
    --------
    UNION ALL 
    --------
    SELECT  QB.kduusa, QB.Keterangan, QB.NIP, QB.Nama, QB.TglMasuk, QB.TglKeluar, 
            COALESCE(fn_GajiPokokRPT(l_FZ1FlgGGol, l_mdPeriode, QB.PrdTetap, QB.GajiPerc, QB.GajiTetap, QB.NIP, QB.TglMasuk, QB.KdGlng) :: DECIMAL(15,2),0) AS GaPokSekarang,
            0 :: DECIMAL(15,2) AS GajiBlnIni,
            QB.Nilai AS GajiBlnLalu,
            QB.NmDpPt, QB.KdDpPt
    FROM
    (--QB
    SELECT  M15.KdUUsa, M02.Keterangan, M15.NIP, M15.Nama, M15.TglMasuk, M15.TglKeluar, 
            M15.PrdTetap, M15.KdGlng, S02.TGLPAYR, M03.NmDpPt, S02.KdDpPt,  
            COALESCE(Fn_Kpusat(M15.NIP,M15.EncGajiPerc, l_Mypass) :: DECIMAL(15,2),0) AS GajiPerc,
            COALESCE(Fn_Kpusat(M15.NIP,M15.EncGajiTetap, l_Mypass) :: DECIMAL(15,2),0) AS GajiTetap,
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM M15PEGA M15 
    INNER JOIN S02DGAJ S02 ON S02.NIP=M15.NIP AND S02.FLGDPPT='D' AND
                              TO_CHAR(S02.TGLPAYR,'YYYYMM')=TO_CHAR(l_mdPeriodeLalu,'YYYYMM') 
    INNER JOIN S01HGAJ S01 ON S01.TGLPAYR=S02.TGLPAYR AND S01.NIP=S02.NIP 
    INNER JOIN M02UUSA M02 ON M15.KdUUsa=M02.Kode
    INNER JOIN M03DPPT M03 ON S02.FLGDPPT=M03.FLGDPPT AND S02.KDDPPT=M03.KDDPPT 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
             FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.NIP BETWEEN l_mcNIPFR AND l_mcNIPTO) AND 
          (S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND
          M03.Status='1' AND S01.FlagTHR=' ' 
    )QB 
    )Q1
    INNER JOIN S01HGAJ S01 ON TO_CHAR(S01.TGLPAYR,'YYYYMM')=TO_CHAR(l_mdPeriode,'YYYYMM') AND 
                              S01.NIP=Q1.NIP 
    GROUP BY Q1.kduusa, Q1.Keterangan, Q1.NIP, Q1.Nama, Q1.TglMasuk, Q1.TglKeluar, 
             S01.TglPayr, S01.Hari, Q1.GaPokSekarang, Q1.NmDpPt, Q1.KdDpPt
    )Q2
    WHERE Q2.GajiBlnIni<>Q2.GajiBlnLalu
    )Q3 ;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
DROP FUNCTION R_RDPTP1 (l_mdPeriode 	DATE,
                        l_mcNIPFr       VARCHAR(10),
                        l_mcNIPTo       VARCHAR(10),
                        l_mcUUsaFr      VARCHAR(4),
                        l_mcUUsaTo      VARCHAR(4),
                        l_Mypass        VARCHAR(128),
                        l_UserId        INT);

select * from R_RDPTP1('2013-02-01',' ','ZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)
*/
