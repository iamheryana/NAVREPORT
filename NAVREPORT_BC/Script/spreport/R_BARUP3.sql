 /****************************************
Name sprocs	: R_BARUP3
Description	: DAFTAR PEGAWAI BARU (PAYROLL) 
Call From	: baruS2 
*****************************************/
DROP FUNCTION R_BARUP3(l_Periode DATE, l_PeriodeBfr	DATE, l_Cetak VARCHAR(1), l_UserId INT);
--
CREATE FUNCTION R_BARUP3(l_Periode      DATE,
                        l_PeriodeBfr    DATE,	
                        l_Cetak         VARCHAR(1),
                        l_UserId        INT)
--
RETURNS TABLE ( OUTKdCaba       VARCHAR(4), 
                OUTNmCaba       VARCHAR(25),
                OUTNIP          VARCHAR(10),
                OUTNama         VARCHAR(25),
                OUTJnsKlmn      VARCHAR(1),
                OUTTglmasuk     DATE,
                OUTTingkatan    VARCHAR(1),
                OUTPendidikan   VARCHAR(10),
                OUTAgama        VARCHAR(10),
                OUTNPWP         VARCHAR(20),
                OUTStspjk       VARCHAR(2),
                OUTKPA          VARCHAR(11),
                OUTTglKPA       DATE,
                OUTKetUsaha     VARCHAR(20),
                OUTKduker       VARCHAR(4),
                OUTKetUker      VARCHAR(20),
                OUTKdGolg       VARCHAR(4),
                OUTKetGolg      VARCHAR(20),
                OUTKetJaba      VARCHAR(20),
                OUTKdlkas       VARCHAR(4))
--
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT (CASE WHEN l_Cetak='Y' THEN Q1.KdCaba ELSE ' ' END) :: VARCHAR AS KdCaba, 
           (CASE WHEN l_Cetak='Y' THEN M08.NmCaba ELSE ' ' END) :: VARCHAR AS NmCaba, 
           Q1.NIP, Q1.Nama, Q1.JnsKlmn, Q1.TglMasuk, Q1.Tingkatan, Q1.KetPendidikan, Q1.Agama, 
           Q1.NPWP, Q1.StsPjk, Q1.KPA, Q1.TglKPA, Q1.SkUUsa, Q1.KdUKer, Q1.SkUKer, 
           Q1.KdGlng, Q1.SkGlng, Q1.SkJaba, Q1.KdKlas 
    FROM 
    ( -- Q1 
    SELECT 	M15.NIP, M15.Nama, M15.JnsKlmn, M15.TglMasuk, M15.Tingkatan, 
            (CASE WHEN M15.Tingkatan = '1' THEN 'SD' 
                 WHEN M15.Tingkatan = '2' THEN 'SLTP' 
                 WHEN M15.Tingkatan = '3' THEN 'SLTA' 
                 WHEN M15.Tingkatan = '4' THEN 'AKADEMI' 
                 WHEN M15.Tingkatan = '5' THEN 'STRATA-1' 
                 WHEN M15.Tingkatan = '6' THEN 'STRATA-2' 
                 WHEN M15.Tingkatan = '7' THEN 'STRATA-2' 
                 WHEN M15.Tingkatan = '0' THEN 'LAINNYA' 
                ELSE ' ' END)  :: VARCHAR AS KetPendidikan,
            (CASE WHEN M15.AGAMA = '1' THEN 'Islam' 
                 WHEN M15.AGAMA = '2' THEN 'Kristen' 
                 WHEN M15.AGAMA = '3' THEN 'Katolik' 
                 WHEN M15.AGAMA = '4' THEN 'Budha' 
                 WHEN M15.AGAMA = '5' THEN 'Hindu' 
                 WHEN M15.AGAMA = '6' THEN 'Lainnya' 
                 ELSE ' ' END)  :: VARCHAR AS Agama,
            CASE WHEN TRIM(BOTH FROM M15.NPWP) ~ '^[0-9]+$' = TRUE THEN
                CASE WHEN (CASE WHEN M15.NPWP=' '  THEN '0' ELSE M15.NPWP END) :: DECIMAL(20)=0 
                    THEN '00.000.000.0-000.000' 
                     ELSE 
                    SUBSTR(M15.NPWP,1,2)||'.'|| SUBSTR(M15.NPWP,3,3)||'.'||SUBSTR(M15.NPWP,6,3)||'.'||SUBSTR(M15.NPWP,9,1)||'-'||SUBSTR(M15.NPWP,10,3)||'.'||SUBSTR(M15.NPWP,13,3)
                END				
            ELSE      
                '00.000.000.0-000.000'
            END :: VARCHAR(20) AS NPWP,
            M15.StsPjk, M15.KPA, M15.TglKPA, M02.Singkatan AS SkUUsa, 
            M15.KdUKer, M17.Singkatan AS SkUKer, 
            M15.KdGlng, M12.Singkatan AS SkGlng, 			
            M04.Singkatan AS SkJaba, M15.KdKlas, M15.KdCaba
    FROM M15PEGA M15
    INNER JOIN M17UKER M17 ON M17.KdUker=M15.kdUker
    INNER JOIN M04HJAB M04 ON M04.Kode=M15.KdJaba
    INNER JOIN M02UUSA M02 ON M02.Kode=M15.KdUUsa
    INNER JOIN M12HGOL M12 ON M12.Kode=M15.KdGlng
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE TO_CHAR(M15.TglMasuk,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM') 
    --------- 
    UNION ALL 
    --------- 
    SELECT 	DISTINCT M15.NIP, M15.Nama, M15.JnsKlmn, M15.TglMasuk, M15.Tingkatan, 
            (CASE WHEN M15.Tingkatan = '1' THEN 'SD' 
                 WHEN M15.Tingkatan = '2' THEN 'SLTP' 
                 WHEN M15.Tingkatan = '3' THEN 'SLTA' 
                 WHEN M15.Tingkatan = '4' THEN 'AKADEMI' 
                 WHEN M15.Tingkatan = '5' THEN 'STRATA-1' 
                 WHEN M15.Tingkatan = '6' THEN 'STRATA-2' 
                 WHEN M15.Tingkatan = '7' THEN 'STRATA-2' 
                 WHEN M15.Tingkatan = '0' THEN 'LAINNYA' 
                ELSE ' ' END)  :: VARCHAR AS KetPendidikan,
            (CASE WHEN M15.AGAMA = '1' THEN 'Islam' 
                 WHEN M15.AGAMA = '2' THEN 'Kristen' 
                 WHEN M15.AGAMA = '3' THEN 'Katolik' 
                 WHEN M15.AGAMA = '4' THEN 'Budha' 
                 WHEN M15.AGAMA = '5' THEN 'Hindu' 
                 WHEN M15.AGAMA = '6' THEN 'Lainnya' 
                 ELSE ' ' END)  :: VARCHAR AS Agama,
            M15.NPWP, M15.StsPjk, M15.KPA, M15.TglKPA, M02.Singkatan AS SkUUsa,
            X01.KdUKer, M17.Singkatan AS SkUKer, 
            X01.KdGlng, M12.Singkatan AS SkGlng, 
            M04.Singkatan AS SkJaba, M15.KdKlas, X01.KdCaba  
    FROM M15PEGA M15
    INNER JOIN 
    (    SELECT S01.NIP, MAX(S01.TglPayr) AS TglPayr 
         FROM S01HGAJ S01 
         WHERE TO_CHAR(S01.TGLPAYR,'YYYYMM') <= TO_CHAR(l_PeriodeBfr,'YYYYMM')
         GROUP BY S01.NIP
    ) Q1 ON Q1.NIP = M15.NIP 
    INNER JOIN S01HGAJ S01 ON S01.NIP=M15.NIP AND S01.TglPayr=Q1.TglPayr 
    INNER JOIN S01HGAJ X01 ON X01.NIP=M15.NIP AND TO_CHAR(X01.TGLPAYR,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM')
    INNER JOIN M17UKER M17 ON M17.KdUker=X01.kdUker
    INNER JOIN M04HJAB M04 ON M04.Kode=X01.KdJaba
    INNER JOIN M02UUSA M02 ON M02.Kode=X01.KdUUsa
    INNER JOIN M12HGOL M12 ON M12.Kode=X01.KdGlng
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE S01.KdCaba <> X01.KdCaba AND
          TO_CHAR(M15.TglMasuk,'YYYYMM') <> TO_CHAR(l_Periode,'YYYYMM') AND 
          TO_CHAR(X01.TGLPAYR,'YYYYMM') = TO_CHAR(l_Periode,'YYYYMM')
    ) Q1 
    INNER JOIN M08HCAB M08 ON Q1.KdCaba = M08.KdCaba ;
    --   
END;
$$ LANGUAGE plpgsql ;
--
/*
SELECT * FROM R_BARUP3( '2012-10-01' :: DATE,
                        '2012-12-01' :: DATE, 'Y', 1)	
*/

