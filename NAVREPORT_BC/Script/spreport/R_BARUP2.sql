 /****************************************
Name sprocs	: R_BARUP2
Description	: DAFTAR PEGAWAI BARU (PAYROLL) 
Call From	: baruS2 
*****************************************/
DROP FUNCTION R_BARUP2(l_ProsesFr DATE, l_ProsesTo DATE, l_UserId INT);	
--
CREATE FUNCTION R_BARUP2(l_ProsesFr	DATE,
                        l_ProsesTo	DATE,	
                        l_UserId    INT)
--
RETURNS TABLE ( OUTNIP          VARCHAR(10), 
                OUTNAMA         VARCHAR(25),
                OUTJnsKelamin   VARCHAR(1),
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
                OUTKdlkas       VARCHAR(4),
                OUTKethkjb      VARCHAR(20))
--
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT 	M15.NIP,M15.Nama,M15.JnsKlmn, M15.TglMasuk, M15.Tingkatan, 
            (CASE WHEN M15.Tingkatan = '1' THEN 'SD' 
                 WHEN M15.Tingkatan = '2' THEN 'SLTP' 
                 WHEN M15.Tingkatan = '3' THEN 'SLTA' 
                 WHEN M15.Tingkatan = '4' THEN 'AKADEMI' 
                 WHEN M15.Tingkatan = '5' THEN 'STRATA-1' 
                 WHEN M15.Tingkatan = '6' THEN 'STRATA-2' 
                 WHEN M15.Tingkatan = '7' THEN 'STRATA-2' 
                 WHEN M15.Tingkatan = '0' THEN 'LAINNYA' 
             ELSE ' ' END) :: VARCHAR AS KetPendidikan,
            (CASE WHEN M15.AGAMA = '1' THEN 'Islam' 
                 WHEN M15.AGAMA = '2' THEN 'Kristen' 
                 WHEN M15.AGAMA = '3' THEN 'Katolik' 
                 WHEN M15.AGAMA = '4' THEN 'Budha' 
                 WHEN M15.AGAMA = '5' THEN 'Hindu' 
                 WHEN M15.AGAMA = '6' THEN 'Lainnya' 
             ELSE ' ' END) :: VARCHAR AS Agama,
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
            M15.KdUKer, M17.Singkatan AS SkUKer, M15.KdGlng, M12.Singkatan AS SkGlng, 			
            M04.Singkatan AS SkJaba, M15.KdKlas, M06.Singkatan AS SkHKJb  
    FROM M15PEGA M15
    INNER JOIN M17UKER M17 ON M17.KdUker=M15.kdUker
    INNER JOIN M04HJAB M04 ON M04.Kode=M15.KdJaba
    INNER JOIN M06HKJB M06 ON M06.Kode=M15.KdKJab
    INNER JOIN M02UUSA M02 ON M02.Kode=M15.KdUUsa
    INNER JOIN M12HGOL M12 ON M12.Kode=M15.KdGlng
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.TglMasuk BETWEEN l_ProsesFr AND l_ProsesTo) ;
    --   
END;
$$ LANGUAGE plpgsql ;

/*
select * from R_BARUP2('2000-01-01' :: DATE, '2012-12-31' :: DATE, 1)
*/
