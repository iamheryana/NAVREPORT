/**************************
	Listing Pegawai Beserta Keluarga 
	Created by 		: Peggy 
	Created Date	: 10 12 2007 
***************************/
DROP FUNCTION  R_PBKLP1   (l_NIPFr	 VARCHAR(10),
					l_NIPTo	 VARCHAR(10),
					l_sts    VARCHAR(1),
                    l_UserId INT4);
--
CREATE FUNCTION  R_PBKLP1   (l_NIPFr VARCHAR(10),
					l_NIPTo	 VARCHAR(10),
					l_sts    VARCHAR(1),
                    l_UserId INT4)
--
RETURNS TABLE (OUTNIP   VARCHAR(10), 
		OUTNAMA         VARCHAR(25),
		OUTNmkeluarga   VARCHAR(25), 
		OUTJnskel       VARCHAR(1), 
		OUTHubungan     VARCHAR(1), 
		OUTAnakke       VARCHAR(1), 
		OUTTgllahir     DATE, 
		OUTTmplahir     VARCHAR(10), 
		OUTKeterangan   VARCHAR(30), 
		OUTUpdateby     VARCHAR(15), 
		OUTTglupdate    TIMESTAMP,
        OUTTglefektif   DATE)
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT M15.NIP, M15.Nama, COALESCE(M23.Nama,'') :: VARCHAR AS NamaKel, 
           COALESCE(M23.JnsKlmn, 'L') :: VARCHAR AS JnsKlmn, 
           COALESCE(M23.Hubungan, '') :: VARCHAR AS Hubungan, 
           COALESCE(M23.AnakKe,'N') :: VARCHAR AS AnakKe, 
           COALESCE(M23.TglLahir, '1900-01-01') :: DATE AS TglLahir, 
           COALESCE(M23.TmpLahir,'') :: VARCHAR AS TmpLahir, 
           COALESCE(M23.Keterangan, '') :: VARCHAR AS Keterangan, 
           COALESCE(M23.updated_by, '') :: VARCHAR AS UserId, 
           COALESCE(M23.updated_on, '1900-01-01') :: TIMESTAMP AS UpdDate, 
           COALESCE(M23.TglEfektif, '1900-01-01') :: DATE AS TglEfektif 
    FROM M15PEGA M15 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    LEFT JOIN M23KLRG M23 ON M15.NIP = M23.NIP
    WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          ((l_sts = 'S') or 
           (l_sts = 'A' and ((M15.TglMasuk IS NOT NULL) AND M15.TglMasuk<=CURRENT_DATE) and  
                            (M15.TglKeluar is null or COALESCE(M15.TglKeluar,CURRENT_DATE)>=CURRENT_DATE)) or 
           (l_sts = 'K' and (M15.TglMasuk IS NOT NULL) AND (M15.TglKeluar IS NOT NULL) AND 
                            M15.TglKeluar<=CURRENT_DATE));
    --   
END;
$$ LANGUAGE plpgsql ;

/*
select * from R_PBKLP1(' ', 'ZZZ',  'A', 1)

*/