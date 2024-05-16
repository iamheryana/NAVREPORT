/********************************************
Name sprocs	: R_BIOPP1
Create by	: wati
Date		: 29-05-2003
Description	: Listing Data Pegawai
Call From	: Main Proc
Sub sprocs	: -
by peggy 2006 06 07 nambahin status aktif / keluar / all 
*****************************************/
DROP FUNCTION  R_BIOPP1  (l_NIPFr	VARCHAR(10),
                        l_NIPTo     VARCHAR(10),
                        l_mypass    VARCHAR(128), 
                        l_sts       VARCHAR(1),
                        l_tglstatus DATE,
                        l_UserId    INT);
--
CREATE FUNCTION  R_BIOPP1 (l_NIPFr  VARCHAR(10),
                        l_NIPTo     VARCHAR(10),
                        l_mypass    VARCHAR(128), 
                        l_sts       VARCHAR(1),
                        l_tglstatus DATE,
                        l_UserId    INT)
--
RETURNS TABLE (OUTNIP   VARCHAR(10), 
		OUTNAMA         VARCHAR(25),
		OUTKdCaba       VARCHAR(4), 
		OUTKdGlng       VARCHAR(4),
		OUTNamaKecil    VARCHAR(10),
		OUTAlamat       VARCHAR(80),
		OUTKelurahan    VARCHAR(20),
		OUTKecamatan    VARCHAR(20),
		OUTKota         VARCHAR(25),
		OUTTelpon       VARCHAR(15),
		OUTAgama        VARCHAR(1),
		OUTKewarganegaraan VARCHAR(20),
		OUTSukubangsa   VARCHAR(20),
		OUTNoktp        VARCHAR(40),
		OUTNpwp         VARCHAR(20),
		OUTTingkatan    VARCHAR(1),
		OUTStatus       VARCHAR(1),
		OUTAnakttgg     integer,
		OUTJnsklmn      VARCHAR(1),
		OUTStspjk       VARCHAR(2),
		OUTTgllahir     date,
		OUTTempatlahir  VARCHAR(25),
		OUTNamakontak   VARCHAR(25),
		OUTAlamatkontak VARCHAR(40),
		OUTKotakontak   VARCHAR(25),
		OUTTelponkontak VARCHAR(15),
		OUTTglmasuk     date,
		OUTAccholder    VARCHAR(25),
		OUTBankref      VARCHAR(4),
		OUTRkbnk        VARCHAR(20),
		OUTKpa          VARCHAR(11),
		OUTTglkpa       date,
		OUTNodanapen    VARCHAR(10),
		OUTTgldanapen   date,
		OUTNokop        VARCHAR(10),
		OUTTglkop       date,
		OUTNospsi       VARCHAR(10),
		OUTTglspsi      date,
		OUTNoydtp       VARCHAR(10),
		OUTTglydtp      date,
		OUTEncGajiPerc  DECIMAL(15,2),
		OUTEncGajiTetap DECIMAL(15,2),		
		OUTPrdawl       date,
		OUTPrdtetap     date,
		OUTTunjist      VARCHAR(1),
		OUTTunjanak     VARCHAR(1),
		OUTThr          VARCHAR(1),
		OUTBlnthr       DECIMAL(5,2),
		OUTJkk          VARCHAR(1),
		OUTLevel        VARCHAR(1),
		OUTJht          VARCHAR(1),
		OUTJkm          VARCHAR(1),
		OUTJpk          VARCHAR(1),
		OUTM23nama      VARCHAR(25),
		OUTM23jnsklmn   VARCHAR(1),
		OUTM23hubungan  VARCHAR(1),
		OUTM23tgllahir  date,
		OUTM23tmplahir  VARCHAR(10),
		OUTM23pendidikan VARCHAR(10),
		OUTM01keterangan VARCHAR(20),
		OUTM08nmcaba     VARCHAR(40),
		OUTM02keterangan VARCHAR(20),  
		OUTM12keterangan VARCHAR(20),
		OUTM04keterangan VARCHAR(20),
		OUTM06keterangan VARCHAR(20),
		OUTM10keterangan VARCHAR(20)) 
--
AS $$
BEGIN
    RETURN QUERY 
    SELECT  M15.NIP, M15.Nama, M15.KdCaba, M15.KdGlng, M15.NamaKecil, M15.Alamat, M15.Kelurahan, M15.Kecamatan, 
            M15.Kota, M15.Telpon, M15.Agama, M15.Kewarganegaraan, M15.SukuBangsa, M15.NoKTP, 
            CASE WHEN TRIM(BOTH FROM M15.NPWP) ~ '^[0-9]+$' = TRUE THEN
                CASE WHEN (CASE WHEN M15.NPWP=' '  THEN '0' ELSE M15.NPWP END) :: DECIMAL(20)=0 
                    THEN '00.000.000.0-000.000' 
                     ELSE 
                    SUBSTR(M15.NPWP,1,2)||'.'|| SUBSTR(M15.NPWP,3,3)||'.'||SUBSTR(M15.NPWP,6,3)||'.'||SUBSTR(M15.NPWP,9,1)||'-'||SUBSTR(M15.NPWP,10,3)||'.'||SUBSTR(M15.NPWP,13,3)
                END				
            ELSE      
                '00.000.000.0-000.000'
            END :: VARCHAR(20) AS NPWP,
            M15.Tingkatan, M15.Status, M15.AnakTtgg, M15.JnsKlmn, M15.StsPjk, M15.TglLahir, 
            M15.TempatLahir, M15.NamaKontak, M15.AlamatKontak, M15.KotaKontak, M15.TelponKontak, 
            M15.TglMasuk, M15.AccHolder, M15.BankRef, M15.Rkbnk, M15.KPA, M15.TglKPA, M15.NoDanaPen, 
            M15.TglDanaPen, M15.NoKop, M15.TglKop, M15.NoSPSI, M15.TglSPSI, M15.NoYDTP, M15.TglYDTP, 
            fn_kPusat(M15.NIP,M15.EncGajiPerc,l_mypass) :: DECIMAL(15,2) AS EncGajiPerc,
            fn_kPusat(M15.NIP,M15.EncGajiTetap,l_mypass) :: DECIMAL(15,2) AS EncGajiTetap,
            M15.PrdAwl, M15.PrdTetap, M15.TunjIst, M15.TunjAnak, M15.THR, M15.BlnTHR, M15.JKK, M15.Level,
            M15.JHT, M15.JKM, M15.JPK, 
            M23.Nama AS M23Nama, M23.JnsKlmn AS M23JnsKlmn, M23.Hubungan, M23.TglLahir AS M23TglLahir, M23.TmpLahir, M23.Pendidikan,
            M01.Keterangan AS M01Keterangan, M08.NmCaba, M02.Keterangan AS M02Keterangan, 
            M12.Keterangan AS M12Keterangan, M04.Keterangan AS M04Keterangan, 
            M06.Keterangan AS M06Keterangan, M10.Keterangan AS M10Keterangan
    FROM M15PEGA M15
    INNER JOIN M23KLRG M23 ON M15.NIP = M23.NIP
    INNER JOIN M01AREA M01 ON M15.KdArea = M01.Kode
    INNER JOIN M08HCAB M08 ON M15.KdCaba = M08.KdCaba
    INNER JOIN M02UUSA M02 ON M15.KdUUsa = M02.Kode
    INNER JOIN M12HGOL M12 ON M15.KdGlng = M12.Kode
    INNER JOIN M04HJAB M04 ON M15.KdJaba = M04.Kode
    INNER JOIN M06HKJB M06 ON M15.KdKJab = M06.Kode
    INNER JOIN M10KLAS M10 ON M15.KdKlas = M10.Kode
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          ((l_sts = 'S') or 
          (l_sts = 'A' and ((M15.TglMasuk IS NOT NULL) AND M15.TglMasuk<=l_TglStatus) and  
                       (M15.TglKeluar is null or COALESCE(M15.TglKeluar,l_TglStatus)>=l_TglStatus)) or 
          (l_sts = 'K' and (M15.TglMasuk IS NOT NULL) AND (M15.TglKeluar IS NOT NULL) AND 
                       M15.TglKeluar<=l_TglStatus));
END;
$$ LANGUAGE plpgsql ;

/*
SELECT * FROM R_BIOPP1 (' ','ZZ',1,
            'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',
            'A','2013-01-01')

*/

