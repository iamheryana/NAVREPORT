/**************************************************************
Name sprocs	: P_IMPORT_LEMBUR
Create by	: Deni
Date		: 14-02-2014
Description	: Proses imprt data harian dari excel
***************************************************************/
DROP FUNCTION p_import_lembur (l_proses_id VARCHAR) ;
--
CREATE FUNCTION p_import_lembur (l_proses_id VARCHAR) 
RETURNS VARCHAR 
--
AS $$ 
--
DECLARE vResult VARCHAR(100);
--
BEGIN 
    vResult := 'NA';
    --
    DELETE FROM TMP03LEMBUR 
    WHERE CREATED_ON < CURRENT_DATE - interval '1 day' ;
    --
    UPDATE TMP03LEMBUR 
    SET status_validasi = NULL
    WHERE proses_id=l_proses_id ;
    -------------------
    --Validasi pendukung, untuk memudahkan users
    UPDATE TMP03LEMBUR TMP
    SET status_validasi = 'Format tanggal dokumen harus YYYY-MM-DD'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
          FN_ISDATE(TMP.tanggal)=0 ;
    -------------------
    UPDATE TMP03LEMBUR TMP
    SET status_validasi = 'NIP harus berisi huruf uppercase'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.NIP<>UPPER(TMP.NIP);
    -------------------
    UPDATE TMP03LEMBUR TMP
    SET status_validasi = 'Kolom Flag Lembur harus diisi K atau L'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.flglembur NOT IN ('K','L');
    -------------------
    UPDATE TMP03LEMBUR TMP
    SET status_validasi = 'Kolom Flag harus diisi Y atau kosong'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.flag NOT IN (' ','Y');
    -------------------
    UPDATE TMP03LEMBUR TMP
    SET status_validasi = 'Kolom jam lembur harus format decimal point 2 (max 99.99)'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
         (SUBSTRING(TMP.jamlembur,LENGTH(TMP.jamlembur)-2,1)<>'.' OR
          FN_ISNUMERIC(SUBSTRING(TMP.jamlembur,LENGTH(TMP.jamlembur)-1,2))=false OR
          FN_ISNUMERIC(SUBSTRING(TMP.jamlembur,1,LENGTH(TMP.jamlembur)-3))=false);
    -------------------
    UPDATE TMP03LEMBUR TMP
    SET status_validasi = 'Jumlah uang makan format tanpa decimal (max 13 digit)'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
          (LENGTH(TMP.jmluangmuka)>13 OR FN_ISNUMERIC(TMP.jmluangmuka)=false) ;
    -------------------
    -- Validasi inti sesuai bussiness rules
    -------------------
    UPDATE TMP03LEMBUR TMP
    SET status_validasi = 'NIP tidak terdefinisi di Master Pegawai'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp03_id
    FROM TMP03LEMBUR TMP
    LEFT JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND M15.NIP IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp03_id=Q1.tmp03_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP03LEMBUR TMP
    SET status_validasi = 'NIP tidak aktif (pegawai sudah keluar)'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp03_id
    FROM TMP03LEMBUR TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND 
          TO_DATE(TMP.tanggal,'YYYY-MM-DD') > COALESCE(M15.tglkeluar,TO_DATE('2999-12-31','YYYY-MM-DD'))
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp03_id=Q1.tmp03_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP03LEMBUR TMP
    SET status_validasi = 'Tanggal transaksi lebih kecil daripada tanggal payroll terakhir'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp03_id
    FROM TMP03LEMBUR TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.tanggal,'YYYY-MM-DD') < COALESCE(M15.tglpayr,TO_DATE('2000-01-01','YYYY-MM-DD'))
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp03_id=Q1.tmp03_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP03LEMBUR TMP
    SET status_validasi = 'Data sudah terdefinisi di Transaksi Lembur'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp03_id
    FROM TMP03LEMBUR TMP
    INNER JOIN T01LEMB T01 ON T01.NIP=TMP.NIP AND TO_DATE(TMP.tanggal,'YYYY-MM-DD')=T01.tgdocu
    WHERE TMP.proses_id=l_proses_id 
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp03_id=Q1.tmp03_id AND TMP.status_validasi IS NULL  ;
    -------------------
    --insert data ke tabel jika lolos validasi
    INSERT INTO T01LEMB(tgdocu, nip, flglemb, refdok, jamlemb, jmlumkn, flag,
                        version, created_by, created_on)
    SELECT  TO_DATE(TMP.tanggal,'YYYY-MM-DD'), TMP.nip, TMP.flglembur, TMP.refdokumen, 
            TO_NUMBER(TMP.jamlembur,'99.99'),
            TO_NUMBER(TMP.jmluangmuka,'9999999999999'),
            CASE WHEN TMP.flag='Y' THEN 1 ELSE 0 END, 
            1, TMP.created_by, TMP.created_on 
    FROM TMP03LEMBUR TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  ;
    --
    RETURN(vResult);
END ; 
--
$$ LANGUAGE plpgsql ;

 


