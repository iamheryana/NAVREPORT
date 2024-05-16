/**************************
	Listing Pegawai Beserta Keluarga 
	Created by 		: Peggy 
	Created Date	: 10 12 2007 
***************************/
DROP FUNCTION  R_PBKLP2   (l_NIPFr	 VARCHAR(10),
					l_NIPTo	 VARCHAR(10),
                    l_UkerFr VARCHAR(8),
                    l_UkerTo VARCHAR(8),
                    l_JabFr	 VARCHAR(4),
                    l_JabTo	 VARCHAR(4),
                    l_UserId INT4);
--
CREATE FUNCTION  R_PBKLP2   (l_NIPFr VARCHAR(10),
					l_NIPTo	 VARCHAR(10),
                    l_UkerFr VARCHAR(8),
                    l_UkerTo VARCHAR(8),
                    l_JabFr	 VARCHAR(4),
                    l_JabTo	 VARCHAR(4),
                    l_UserId INT4)
--
RETURNS TABLE (OUTRID   VARCHAR(1),
        OUTNIP          VARCHAR(10), 
        OUTNIPKel       VARCHAR(10), 
		OUTNAMA         VARCHAR(25),
		OUTTgllahir     DATE, 
        OUTUmur         INT4,
		OUTJnskel       VARCHAR(15), 
		OUTHubungan     VARCHAR(15), 
		OUTTglMasuk     DATE, 
        OUTMasaKerja    INT4,
		OUTKetJabatan   VARCHAR(30), 
		OUTKetUker      VARCHAR(30), 
		OUTKetnmcaba    VARCHAR(30))
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT  PEG.RID, PEG.NIP, PEG.NIPKel, PEG.Nama, PEG.TglLahir, PEG.Umur, PEG.JnsKel, PEG.Hubungan, 
            PEG.TglMasuk, PEG.MasaKerja, PEG.KetJabatan, PEG.KetUker, PEG.NmCaba
    FROM 
    (--PEG
        SELECT 	'A' :: VARCHAR AS RID, M15.NIP, M15.NIP :: VARCHAR AS NIPKel, M15.Nama, M15.TGLLAHIR,
                extract(year from age(current_date,M15.TglLahir)) :: INT4 as umur,
                ' ' :: VARCHAR AS JnsKel, ' ' :: VARCHAR AS Hubungan, M15.TglMasuk,
                extract(year from age(current_date,M15.TglMasuk)) :: INT4 as masakerja,
                M04.KETERANGAN :: VARCHAR AS KetJabatan, M17.KETERANGAN :: VARCHAR AS KetUker, M08.NMCABA		
        FROM M15PEGA M15 
        INNER JOIN M17UKER M17 ON M17.KDUKER=M15.KDUKER
        INNER JOIN M08HCAB M08 ON M08.KDCABA=M15.KDCABA
        INNER JOIN M04HJAB M04 ON M04.KODE=M15.KDJABA
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                    FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
        LEFT JOIN S08MUTA S08 ON M15.NIP=S08.NIP 
        WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
              (M15.KDUKER BETWEEN l_UkerFr AND l_UkerTo) AND
              (M15.KDJABA BETWEEN l_JabFr AND l_JabTo) AND
              S08.KDCABA IS NULL
        ---------
        UNION ALL
        ---------
        SELECT 	'B' :: VARCHAR AS RID, M15.NIP, M15.NIP :: VARCHAR AS NIPKel, M15.Nama, M15.TGLLAHIR,
                extract(year from age(current_date,M15.TglLahir)) :: INT4 as umur,
                ' ' :: VARCHAR AS JnsKel, ' ' :: VARCHAR AS Hubungan, M15.TglMasuk,
                extract(year from age(current_date,M15.TglMasuk)) :: INT4 as masakerja,
                M04.KETERANGAN :: VARCHAR AS KetJabatan, M17.KETERANGAN :: VARCHAR AS KetUker, M08.NMCABA		
        FROM M15PEGA M15 
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                    FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
        INNER JOIN S08MUTA S08 ON M15.NIP=S08.NIP AND S08.TGLEFF=(SELECT MAX(S.TGLEFF) FROM S08MUTA S WHERE S.NIP=M15.NIP)
        INNER JOIN M17UKER M17 ON M17.KDUKER=S08.KDUKER
        INNER JOIN M08HCAB M08 ON M08.KDCABA=S08.KDCABA
        INNER JOIN M04HJAB M04 ON M04.KODE=S08.KDJABA
        WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
              (M15.KDUKER BETWEEN l_UkerFr AND l_UkerTo) AND
              (M15.KDJABA BETWEEN l_JabFr AND l_JabTo)
        ---------
        UNION ALL
        ---------
        SELECT 	'C' :: VARCHAR AS RID, M15.NIP, ' ' :: VARCHAR AS NIPKel, M23.Nama, M23.TGLLAHIR,
                extract(year from age(current_date,M23.TglLahir)) :: INT as umur,
                (CASE WHEN M23.JNSKLMN='L' THEN 'Laki-laki' 
                    WHEN M23.JNSKLMN='P' THEN 'Perempuan' ELSE '' END) :: VARCHAR AS JnsKel,
                (CASE WHEN M23.HUBUNGAN='1' THEN 'Pasangan' 
                      WHEN M23.HUBUNGAN='2' THEN 'Anak' 
                      WHEN M23.HUBUNGAN='3' THEN 'Ayah' 
                      WHEN M23.HUBUNGAN='4' THEN 'Ibu' 
                      ELSE 'Lain-lain' END) :: VARCHAR AS Hubungan,
                NULL :: DATE AS TglMasuk,
                0 :: INT AS MasaKerja,
                ' ' :: VARCHAR AS KetJabatan,
                ' ' :: VARCHAR AS KetUker,
                ' '	:: VARCHAR AS NmCaba	
        FROM M15PEGA M15 
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                    FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
        INNER JOIN M23KLRG M23 ON M15.NIP = M23.NIP
        WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
              (M15.KDUKER BETWEEN l_UkerFr AND l_UkerTo) AND
              (M15.KDJABA BETWEEN l_JabFr AND l_JabTo) AND
              (M15.TglKeluar IS NULL OR M15.TglKeluar >= CURRENT_DATE) 
    ) PEG ;
    --   
END;
$$ LANGUAGE plpgsql ;

/*
select * from R_PBKLP2(' ', 'ZZZ', ' ', 'ZZZ', ' ', 'ZZZ', 1)
*/