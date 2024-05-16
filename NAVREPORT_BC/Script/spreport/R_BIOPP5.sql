/****************************************
Name sprocs	: R_BIOPP5
Create by	: Deni 
Date		: 04-12-2013
Description	: Laporan Pegawai - Persiapan Pensiun
CATATAN KHUSUS : RANGE SECURITY DIKIRIM TAPI NGGAK DIPAKE (SESUAI SDR) 
*****************************************/
DROP FUNCTION  R_BIOPP5   (l_NIPFr	 VARCHAR(10),
					l_NIPTo     VARCHAR(10),
					l_mypass    VARCHAR(128), 
					l_sts       VARCHAR(1),
					l_tglstatus DATE,
					l_UserId    INT);
--
CREATE FUNCTION  R_BIOPP5   (l_NIPFr VARCHAR(10),
					l_NIPTo     VARCHAR(10),
					l_mypass    VARCHAR(128), 
					l_sts       VARCHAR(1),
					l_tglstatus DATE,
					l_UserId    INT)
--
RETURNS TABLE (OUTNIP   VARCHAR(10), 
		OUTNAMA         VARCHAR(25),
		OUTNmkecil      VARCHAR(10), 
		OUTJnskelamin   VARCHAR(1),
		OUTTingkatan    VARCHAR(1),
		OUTStatus       VARCHAR(1),
		OUTAnakttg      INT4,
		OUTTgllahir     DATE,
		OUTTmplahir     VARCHAR(4),
		OUTKdarea       VARCHAR(4),
		OUTKetarea      VARCHAR(25),
		OUTKdcaba       VARCHAR(4),
		OUTKetcaba      VARCHAR(25),
		OUTKdusa        VARCHAR(4),
		OUTKetusa       VARCHAR(25),
		OUTKduker       VARCHAR(4),
		OUTKetuker      VARCHAR(25),
		OUTKdgolg       VARCHAR(4),
		OUTKetgolg      VARCHAR(25),
		OUTKdkeljab     VARCHAR(4),
		OUTKetkeljab    VARCHAR(25),
		OUTKdjab        VARCHAR(4),
		OUTKetjab       VARCHAR(25),
		OUTKdklas       VARCHAR(4),
		OUTKetklas      VARCHAR(25),
        OUTJns          VARCHAR(1), 
        OUTTglEff       DATE, 
        OUTKdHarg       VARCHAR(4), 
        OUTKetHarg      VARCHAR(25), 
        OUTNoSK         VARCHAR(25), 
        OUTTglSK        DATE, 
        OUTDurasi       VARCHAR(10), 
        OUTKeterangan   VARCHAR(40), 
        OUTTglMasuk     DATE, 
        OUTTglkeluar    DATE)
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT  VP4.NIP, VP4.Nama, VP4.NamaKecil, VP4.JnsKlmn, VP4.Tingkatan, VP4.Status, VP4.AnakTtgg, 
            VP4.TglLahir, VP4.TempatLahir, VP4.KdArea, VP4.KetArea, VP4.KdCaba, VP4.KetCaba, VP4.KdUUsa, 
            VP4.KetUUsa, VP4.KdUKer, VP4.KetUker, VP4.KdGlng, VP4.KetGlng, VP4.KdKJab, VP4.KetKJab, 
            VP4.KdJaba, VP4.KetJaba, VP4.KdKlas, VP4.KetKlas, VP4.Jns, VP4.TglEff, VP4.KdHarg, 
            VP4.KetHarg, VP4.NoSK, VP4.TglSK, VP4.Durasi, VP4.Keterangan, VP4.TglMasuk, VP4.Tglkeluar
    FROM V_BIOPP4 VP4
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON VP4.KdGlng=VSL.Gol_Kode AND VP4.KdCaba=VSL.Cab_Kode
    WHERE (VP4.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          ((l_sts = 'S') or 
           (l_sts = 'A' and ((VP4.TglMasuk IS NOT NULL) AND VP4.TglMasuk<=l_TglStatus) and  
                            (VP4.TglKeluar is null or COALESCE(VP4.TglKeluar,l_TglStatus)>=l_TglStatus)) or 
           (l_sts = 'K' and (VP4.TglMasuk IS NOT NULL) AND (VP4.TglKeluar IS NOT NULL) AND 
                            VP4.TglKeluar<=l_TglStatus));
    --   
END;
$$ LANGUAGE plpgsql ;
--
/* 
SELECT * FROM R_BIOPP5 (' ','ZZ',
            'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',
            'A','2013-01-01', 1)
*/