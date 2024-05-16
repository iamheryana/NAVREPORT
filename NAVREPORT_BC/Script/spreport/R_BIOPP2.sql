/********************************************
Name sprocs	: R_BIOPP2
Create by	: wati
Date		: 29-05-2003
Description	: Listing Data Pegawai (Karir)
Call From	: Main Proc
Sub sprocs	: -
by peggy 2006 06 07 nambahin status aktif / keluar / all 
*****************************************/
DROP FUNCTION R_BIOPP2 (l_NIPFr VARCHAR(10),
					l_NIPTo     VARCHAR(10),
					l_mypass    VARCHAR(128), 
					l_sts       VARCHAR(1),
					l_tglstatus DATE,
                    l_UserId    INT);  
--
CREATE FUNCTION  R_BIOPP2   (l_NIPFr VARCHAR(10),
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
		OUTKetarea      VARCHAR(20),
		OUTKdcaba       VARCHAR(4),
		OUTKetcaba      VARCHAR(40),
		OUTKdusa        VARCHAR(4),
		OUTKetusa       VARCHAR(20),
		OUTKduker       VARCHAR(4),
		OUTKetuker      VARCHAR(20),
		OUTKdgolg       VARCHAR(4),
		OUTKetgolg      VARCHAR(20),
		OUTKdkeljab     VARCHAR(4),
		OUTKetkeljab    VARCHAR(20),
		OUTKdjab        VARCHAR(4),
		OUTKetjab       VARCHAR(20),
		OUTKdklas       VARCHAR(4),
		OUTKetklas      VARCHAR(20),
		OUTTgleffektif  DATE,
		OUTS08keterangan1   VARCHAR(100),
		OUTNoSK         VARCHAR(25),
		OUTSkMutr       VARCHAR(10),
		OUTPenilaian    VARCHAR(2),
		OUTS08Area      VARCHAR(4),
		OUTS08ketarea   VARCHAR(10),
		OUTS08kdcaba    VARCHAR(4),
		OUTS08ketcaba   VARCHAR(10),
		OUTS08kdusa     VARCHAR(4),
		OUTS08ketusa    VARCHAR(10),
		OUTS08kduker    VARCHAR(4),
		OUTS08ketuker   VARCHAR(10),
		OUTS08golg      VARCHAR(4),
		OUTS08ketgolg   VARCHAR(10),
		OUTS08keljab    VARCHAR(4),
		OUTS08ketkeljab VARCHAR(10),
		OUTS08jaba      VARCHAR(4),
		OUTS08ketjaba   VARCHAR(10),
		OUTgajipokok    DECIMAL(15,2),
		OUTS08keterangan2    VARCHAR(100),
		OUTS08keterangan3    VARCHAR(100),
		OUTS08keterangan4    VARCHAR(100),
		OUTS08keterangan5    VARCHAR(100))
--
AS $$
BEGIN
    RETURN QUERY 
    SELECT  M15.NIP, M15.Nama, M15.NamaKecil, M15.JnsKlmn, M15.Tingkatan, M15.Status, M15.AnakTtgg, 
            M15.TglLahir, M15.TempatLahir, M15.KdArea, M01.Keterangan AS KetArea, M15.KdCaba, 
            M08.NmCaba AS KetCaba,
            M15.KdUUsa, M02.Keterangan AS KetUUsa, M15.KdUKer, M17.Keterangan AS KetUker,
            M15.KdGlng, M12.Keterangan AS KetGlng, M15.KdKJab, M06.Keterangan AS KetKJab,
            M15.KdJaba, M04.Keterangan AS KetJaba, M15.KdKlas, M10.Keterangan AS KetKlas,
            S08.TglEff, S08.Keterangan, S08.NoSK, S08.SkMutr, S08.Penilaian,
            S08.KdArea AS S08Area, S08.SkArea, S08.KdCaba AS S08Caba, S08.SkCaba, 
            S08.KdUUsa AS S08UUsa, S08.SkUUsa, S08.KdUKer AS S08Uker, S08.SkUKer, 
            S08.KdGlng AS S08Glng, S08.SkGlng, S08.KdKJab AS S08KJab, S08.SkKJab, 
            S08.KdJaba AS S08Jaba, S08.SkJaba, 
            fn_kPusat(S08.NIP,S08.EncGajiPokok,l_mypass) :: DECIMAL(15,2) AS GajiPokok,
            S08.KETERANGAN2, S08.KETERANGAN3, S08.KETERANGAN4, S08.KETERANGAN5
    FROM M15PEGA M15
    INNER JOIN M01AREA M01 ON M15.KdArea = M01.Kode
    INNER JOIN M08HCAB M08 ON M15.KdCaba = M08.KdCaba
    INNER JOIN M02UUSA M02 ON M15.KdUUsa = M02.Kode
    INNER JOIN M17UKER M17 ON M15.KdUKer = M17.KdUker
    INNER JOIN M12HGOL M12 ON M15.KdGlng = M12.Kode
    INNER JOIN M04HJAB M04 ON M15.KdJaba = M04.Kode
    INNER JOIN M06HKJB M06 ON M15.KdKJab = M06.Kode
    INNER JOIN M10KLAS M10 ON M15.KdKlas = M10.Kode
    INNER JOIN S08MUTA S08 ON M15.NIP = S08.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          ((l_sts = 'S') or 
           (l_sts = 'A' and ((M15.TglMasuk IS NOT NULL) AND M15.TglMasuk<=l_TglStatus) and  
                            (M15.TglKeluar is null or COALESCE(M15.TglKeluar,l_TglStatus)>=l_TglStatus)) or 
           (l_sts = 'K' and (M15.TglMasuk IS NOT NULL) AND (M15.TglKeluar IS NOT NULL) AND 
                            M15.TglKeluar<=l_TglStatus));
--
END;
$$ LANGUAGE plpgsql ;

/*
SELECT * FROM R_BIOPP2 (' ','ZZ',
            'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',
            'A','2013-01-01', 1)
*/