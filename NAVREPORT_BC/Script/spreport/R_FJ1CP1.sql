/****************************************
Name sprocs	: R_FJ1CP1
Create by	: Deni
Date		: 28-02-2003
Description	: Query daftar Tenaga Kerja Keluar
Call From	: Main Proc
*****************************************/
DROP FUNCTION R_FJ1CP1(l_Periode        DATE,
                        l_FCab          VARCHAR(4),
                        l_TCab          VARCHAR(4),
    					l_Judul         VARCHAR(1),
						l_mcJudulCab	VARCHAR(4),
						l_Mypass		VARCHAR(128),
						l_UserId		INT);
--
CREATE FUNCTION R_FJ1CP1 (  l_Periode       DATE,
                            l_FCab          VARCHAR(4),
                            l_TCab          VARCHAR(4),
                            l_Judul         VARCHAR(1),
                            l_mcJudulCab	VARCHAR(4),
                            l_Mypass		VARCHAR(128),
                            l_UserId		INT)
--
RETURNS TABLE (
		OUTNIP              VARCHAR(10),
		OUTNama             VARCHAR(25),
        OUTNoAstek          VARCHAR(15),
		OUTTglLahir         DATE,
		OUTJnsKlmn          VARCHAR(1),
		OUTGaji             DECIMAL(15,2),
		OUTKdCaba           VARCHAR(4),
		OUTNmCaba           VARCHAR(20),
		OUTTandatangan      VARCHAR(25),
		OUTJabatan          VARCHAR(25),
		OUTNPP              VARCHAR(8))
--
AS $$
--
DECLARE l_mcPindah          VARCHAR(1); 
		l_KdCabaJudul       VARCHAR(4); 
		l_NmCabaJudul       VARCHAR(40); 
		l_TTanganJudul      VARCHAR(25);
		l_JabatanJudul      VARCHAR(25);
		l_NPPJudul          VARCHAR(8);
        l_PeriodeBfr        DATE;
-- 
BEGIN
    SELECT SUBSTRING(STRINGFLAG,2,1), l_Periode-INTERVAL '1 month' INTO l_mcPindah, l_PeriodeBfr
    FROM FZ2FLDA; 
    -- 
    IF l_mcPindah = '1' AND l_Judul = 'T' THEN
        SELECT KdCaba, NmCaba, NamaJamsostek, JabatanJamSostek, NPP 
        INTO l_KdCabaJudul, l_NmCabaJudul, l_TTanganJudul, l_JabatanJudul, l_NPPJudul
        FROM M08HCAB 
        WHERE KdCaba=l_mcJudulCab; 
    END IF; 
    --
    IF l_mcPindah = '0' AND l_Judul = 'T' THEN
        SELECT ' ' as kdcaba, ' ' as nmcaba, Nama, Jabatan, Kode
        INTO l_KdCabaJudul, l_NmCabaJudul, l_TTanganJudul, l_JabatanJudul
        FROM M33JABT 
        WHERE Kode = '04';
        --
        SELECT NPP INTO l_NPPJudul FROM FZ1FLDA ;
    END IF; 
    --
    RETURN QUERY
    SELECT  Q1.NIP, Q1.KPA, Q1.Nama, Q1.TglLahir, Q1.JnsKlmn, Q1.Gaji,
            (CASE WHEN l_Judul='Y' THEN Q1.KdCaba  ELSE l_KdCabaJudul END) AS KdCaba, 
            (CASE WHEN l_Judul='Y' THEN M08.NmCaba ELSE l_NmCabaJudul END) AS NmCaba, 
            (CASE WHEN l_Judul='Y' THEN M08.NamaJamsostek ELSE l_TTanganJudul END) AS TandaTangan, 
            (CASE WHEN l_Judul='Y' THEN M08.JabatanJamsostek ELSE l_JabatanJudul END) AS Jabatan, 
            (CASE WHEN l_Judul='Y' THEN M08.NPP ELSE l_NPPJudul END) AS NPP
    FROM 
    ( 
    SELECT M15.NIP, M15.KPA, M15.Nama, M15.TglLahir, M15.JnsKlmn, S01.KdCaba, 
           SUM(COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) ::DECIMAL(15,2),0)) AS Gaji
    FROM M15PEGA M15 
    INNER JOIN S02DGAJ S02 ON TO_CHAR(S02.TGLPAYR,'YYYYMM') = TO_CHAR(l_Periode,'YYYYMM') AND 
                              S02.NIP = M15.NIP AND S02.FLGDPPT = 'D' 
    INNER JOIN M03DPPT M03 ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
    INNER JOIN S01HGAJ S01 ON S01.TglPayr = S02.TglPayr AND S01.NIP = S02.NIP 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE  TO_CHAR(M15.TglKeluar,'YYYYMM') = TO_CHAR(l_Periode,'YYYYMM') AND
           (M15.KdCaba BETWEEN l_FCab AND l_TCab) AND 
           M15.KPA <> ' ' AND M15.TGLKPA IS NOT NULL AND M03.Status = '1' 
    GROUP BY M15.NIP, M15.KPA, M15.Nama, M15.TglLahir, M15.JnsKlmn, S01.KdCaba
    --------- 
    UNION ALL 
    --------- 
    -- PINDAH CABANG 
    SELECT M15.NIP, M15.KPA, M15.Nama, M15.TglLahir, M15.JnsKlmn, X01.KdCaba, 
           SUM(COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) ::DECIMAL(15,2),0)) AS Gaji
    FROM M15PEGA M15 
    INNER JOIN S02DGAJ S02 ON TO_CHAR(S02.TGLPAYR,'YYYYMM') = TO_CHAR(l_Periode,'YYYYMM') AND 
                              S02.NIP = M15.NIP AND S02.FLGDPPT = 'D' 
    INNER JOIN M03DPPT M03 ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
    INNER JOIN S01HGAJ S01 ON S01.TglPayr = S02.TglPayr AND S01.NIP = S02.NIP 
    INNER JOIN 
    (
     SELECT S01.NIP, MAX(S01.TglPayr) :: DATE AS TglPayr
     FROM S01HGAJ S01 
     WHERE TO_CHAR(S01.TGLPAYR,'YYYYMM') <= TO_CHAR(l_PeriodeBfr,'YYYYMM')
     GROUP BY S01.NIP
    ) Q1 ON Q1.NIP = M15.NIP 
    INNER JOIN S01HGAJ X01 ON X01.NIP = M15.NIP AND X01.TglPayr = Q1.TglPayr 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.TglKeluar IS NULL OR
           M15.TglKeluar IS NOT NULL AND TO_CHAR(M15.TglKeluar,'YYYYMM') <> TO_CHAR(l_Periode,'YYYYMM')) AND
          M15.KPA <> ' ' AND M15.TglKPA IS NOT NULL AND M03.Status = '1' AND l_mcPindah = '1' AND 
          (M15.KdCaba BETWEEN l_FCab AND l_TCab) AND 
          ((l_Judul = 'Y' AND S01.KdCaba <> X01.KdCaba) OR 
           (l_Judul = 'T' AND S01.KdCaba <> X01.KdCaba) AND (S01.KdCaba BETWEEN l_FCab AND l_TCab)) 
    GROUP BY M15.NIP, M15.KPA, M15.Nama, M15.TglLahir, M15.JnsKlmn, X01.KdCaba 
    ) Q1 
    INNER JOIN M08HCAB M08 ON M08.KdCaba = Q1.KdCaba ;
    --
END;
--
$$ LANGUAGE plpgsql ;


/* 
CREATE FUNCTION R_FJ1CP1 (  l_Periode       DATE,
                            l_FCab          VARCHAR(4),
                            l_TCab          VARCHAR(4),
                            l_mlCetak       VARCHAR(1),
                            l_mcJudulCab	VARCHAR(4),
                            l_Mypass		VARCHAR(128),
                            l_UserId		INT)

select * from R_FJ1CP1('2013-11-08',' ','ZZZ','Y','001','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)

*/

