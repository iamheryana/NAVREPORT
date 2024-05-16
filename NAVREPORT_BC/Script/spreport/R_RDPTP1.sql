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
		OUTRID              VARCHAR(1),
		OUTNIP              VARCHAR(10),
		OUTNama             VARCHAR(25),
		OUTKdUker           VARCHAR(8),
		OUTNamaUker         VARCHAR(25),
		OUTStatus           TEXT,
        OUTJnsKelamin       VARCHAR(1),
        OUTKPA              VARCHAR(15),
		OUTBulan01          DECIMAL(15,2),
		OUTBulan02          DECIMAL(15,2),
		OUTBulan03          DECIMAL(15,2),
		OUTBulan04          DECIMAL(15,2),
		OUTBulan05          DECIMAL(15,2),
		OUTBulan06          DECIMAL(15,2),
		OUTBulan07          DECIMAL(15,2),
		OUTBulan08          DECIMAL(15,2),
		OUTBulan09          DECIMAL(15,2),
		OUTBulan10          DECIMAL(15,2),
		OUTBulan11          DECIMAL(15,2),
		OUTBulan12          DECIMAL(15,2))
--
AS $$
--
DECLARE l_FZ1FlgGGol    VARCHAR(1);
		l_mdPeriodeLalu	DATE ;
--
BEGIN
    --
    SELECT FlgGGol INTO l_FZ1FlgGGol FROM FZ1FLDA;
    --
    RETURN QUERY
    SELECT  Q3.KdUUsa, Q3.Keterangan, Q3.NIP, Q3.Nama, Q3.TglMasuk, Q3.TglKeluar, 
            Q3.HariKerja, Q3.GaPokSekarang, Q3.M03Keterangan, Q3.KdDpPt, Q3.PrdLalu,
            SUM(Q3.GajiBlnIni) AS GajiBlnIni, SUM(Q3.GajiBlnLalu) AS GajiBlnLalu, 
            SUM(Q3.Mutasi) AS Mutasi, SUM(Q3.Saldo) AS Saldo
    FROM
    (--Q3
    SELECT  S01.KdUUsa, M02.Keterangan, ' ' :: VARCHAR(10) AS NIP, ' ' :: VARCHAR(25) AS Nama, 
            NULL :: DATE AS TglMasuk, NULL :: DATE AS TglKeluar, 
            0 :: DECIMAL(3,0) AS HariKerja, 0 :: DECIMAL(15,2) AS GaPokSekarang, 
            0 :: DECIMAL(15,2) AS GajiBlnIni, 0 :: DECIMAL(15,2) AS GajiBlnLalu, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Mutasi,
            ' ' :: VARCHAR(25) AS M03Keterangan, ' ' :: VARCHAR(1) AS KdDpPt, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Saldo,
            l_mdPeriodeLalu AS PrdLalu
    FROM S02DGAJ S02 
    INNER JOIN S01HGAJ S01 ON S01.TGLPAYR=S02.TglPayr AND S01.NIP=S02.NIP 
    INNER JOIN M03DPPT M03 ON S02.FLGDPPT=M03.FLGDPPT AND S02.KDDPPT=M03.KDDPPT 
    INNER JOIN M02UUSA M02 ON S02.KdUUsa=M02.Kode
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
             FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE TO_CHAR(S02.TGLPAYR,'YYYYMM')=TO_CHAR(l_mdPeriodeLalu,'YYYYMM') AND 
       S02.FLGDPPT='D' AND M03.Status='1' AND S01.FlagTHR=' ' AND 
       S01.NIP BETWEEN l_mcNIPFR AND l_mcNIPTO AND 
       S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo 
    --------
    UNION ALL 
    --------
    SELECT  Q2.KdUUsa, Q2.Keterangan, Q2.NIP, Q2.Nama, Q2.TglMasuk, Q2.TglKeluar, 
            Q2.HariKerja, Q2.GaPokSekarang, Q2.GajiBlnIni, Q2.GajiBlnLalu, 
            Q2.GajiBlnIni-Q2.GajiBlnLalu AS Mutasi,
            Q2.M03Keterangan, Q2.KdDpPt, 
            0 :: DECIMAL(15,2) AS Saldo,
            l_mdPeriodeLalu AS PrdLalu
    FROM 
    (--Q2
    SELECT  Q1.kduusa, Q1.Keterangan, Q1.NIP, Q1.Nama, Q1.TglMasuk, Q1.TglKeluar, Q1.hariKerja,
            COALESCE(fn_GajiPokokRPT(l_FZ1FlgGGol, l_mdperiode,	Q1.PrdTetap, Q1.GajiPerc, Q1.GajiTetap, Q1.NIP, Q1.TglMasuk, Q1.KdGlng) :: DECIMAL(15,2),0) AS GaPokSekarang,
            COALESCE(CASE WHEN Q1.KetBulan='BLN_INI' THEN Q1.Nilai ELSE 0 END :: DECIMAL(15,2),0) AS GajiBlnIni,
            COALESCE(CASE WHEN Q1.KetBulan<>'BLN_INI' THEN Q1.Nilai ELSE 0 END :: DECIMAL(15,2),0) AS GajiBlnLalu,
            Q1.NmDpPt AS M03Keterangan, Q1.KdDpPt
    FROM
    (--Q1
    SELECT  M15.KdUUsa, M15.NIP, M15.Nama, M15.TglMasuk, M15.TglKeluar, 
            M15.PrdTetap, M15.KdGlng, S02.TGLPAYR, M03.NmDpPt, S02.KdDpPt, M02.Keterangan, 
            CASE WHEN S02.KdDpPt='BSAL' AND TO_CHAR(S02.TGLPAYR,'YYYYMM')=TO_CHAR(l_mdPeriode,'YYYYMM') THEN S01.Hari ELSE 0 END :: DECIMAL(3,0) AS hariKerja, 
            COALESCE(Fn_Kpusat(M15.NIP,M15.EncGajiPerc, l_Mypass) :: DECIMAL(15,2),0) AS GajiPerc,
            COALESCE(Fn_Kpusat(M15.NIP,M15.EncGajiTetap, l_Mypass) :: DECIMAL(15,2),0) AS GajiTetap,
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai,
            CASE WHEN TO_CHAR(S02.TGLPAYR,'YYYYMM')=TO_CHAR(l_mdPeriode,'YYYYMM') THEN 'BLN_INI' ELSE 'BLN_LALU' END :: VARCHAR(10) AS KetBulan 
    FROM M15PEGA M15 
    INNER JOIN S02DGAJ S02 ON S02.NIP=M15.NIP AND S02.FLGDPPT='D' AND
                              (TO_CHAR(S02.TGLPAYR,'YYYYMM')=TO_CHAR(l_mdPeriodeLalu,'YYYYMM') OR 
                               TO_CHAR(S02.TGLPAYR,'YYYYMM')=TO_CHAR(l_mdPeriode,'YYYYMM'))
    INNER JOIN S01HGAJ S01 ON S01.TGLPAYR=S02.TGLPAYR AND S01.NIP=S02.NIP 
    INNER JOIN M02UUSA M02 ON S02.KdUUsa=M02.Kode
    INNER JOIN M03DPPT M03 ON S02.FLGDPPT=M03.FLGDPPT AND S02.KDDPPT=M03.KDDPPT 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
             FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.NIP BETWEEN l_mcNIPFR AND l_mcNIPTO) AND 
          (S01.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) AND
           M03.Status='1' AND S01.FlagTHR=' ' 
    ) Q1
    ) Q2
    ) Q3
    GROUP BY Q3.KdUUsa, Q3.Keterangan, Q3.NIP, Q3.Nama, Q3.TglMasuk, Q3.TglKeluar, 
            Q3.HariKerja, Q3.GaPokSekarang, Q3.M03Keterangan, Q3.KdDpPt, Q3.PrdLalu;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
