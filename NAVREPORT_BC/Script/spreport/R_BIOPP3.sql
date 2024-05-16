/****************************************
Name sprocs	: R_BIOPP3
Create by	: Deni 
Date		: 04-12-2013
Description	: Laporan Pegawai - Persiapan Pensiun
CATATAN KHUSUS : RANGE SECURITY DIKIRIM TAPI NGGAK DIPAKE (SESUAI SDR) 
*****************************************/
DROP FUNCTION  R_BIOPP3   (l_NIPFr	 VARCHAR(10),
					l_NIPTo     VARCHAR(10),
					l_mypass    VARCHAR(128), 
					l_sts       VARCHAR(1),
					l_tglstatus DATE,
                    l_UserId    INT); 
--
CREATE FUNCTION  R_BIOPP3   (l_NIPFr VARCHAR(10),
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
        OUTTanggal      DATE, 
        OUTSkJnsD       VARCHAR(10), 
        OUTSkJrsn       VARCHAR(10), 
        OUTSkLmbD       VARCHAR(10), 
        OUTSkLksD       VARCHAR(10), 
        OUTBulanTrng    DECIMAL(3,0), 
        OUTHariTrng     DECIMAL(7,4), 
        OUTHasil        VARCHAR(10), 
        OUTNilai        VARCHAR(2), 
        OUTTglMasuk     DATE, 
        OUTTglkeluar    DATE)
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT  VP3.NIP, VP3.Nama, VP3.NamaKecil, VP3.JnsKlmn, VP3.Tingkatan, VP3.Status, VP3.AnakTtgg, 
            VP3.TglLahir, VP3.TempatLahir, VP3.KdArea, VP3.KetArea, VP3.KdCaba, VP3.KetCaba, 
            VP3.KdUUsa, VP3.KetUUsa, VP3.KdUKer, VP3.KetUker, VP3.KdGlng, VP3.KetGlng, 
            VP3.KdKJab, VP3.KetKJab, VP3.KdJaba, VP3.KetJaba, VP3.KdKlas, VP3.KetKlas, 
            VP3.Tanggal, VP3.SkJnsD, VP3.SkJrsn, VP3.SkLmbD, VP3.SkLksD, VP3.BulanTrng, 
            VP3.HariTrng, VP3.Hasil, VP3.Nilai, VP3.TglMasuk, VP3.Tglkeluar
    FROM V_BIOPP3 VP3
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON VP3.KdGlng=VSL.Gol_Kode AND VP3.KdCaba=VSL.Cab_Kode
    WHERE (VP3.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          ((l_sts = 'S') or 
           (l_sts = 'A' and ((VP3.TglMasuk IS NOT NULL) AND VP3.TglMasuk<=l_TglStatus) and  
                            (VP3.TglKeluar is null or COALESCE(VP3.TglKeluar,l_TglStatus)>=l_TglStatus)) or 
           (l_sts = 'K' and (VP3.TglMasuk IS NOT NULL) AND (VP3.TglKeluar IS NOT NULL) AND 
                            VP3.TglKeluar<=l_TglStatus));
    --   
END;
$$ LANGUAGE plpgsql ;

/*
SELECT * FROM R_BIOPP3 (' ','ZZ',
            'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',
            'A','2013-01-01',1)
*/
