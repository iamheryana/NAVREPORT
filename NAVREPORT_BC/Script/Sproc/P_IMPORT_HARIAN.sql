/**************************************************************
Name sprocs	: P_IMPORT_HARIAN
Create by	: Deni
Date		: 14-02-2014
Description	: Proses imprt data harian dari excel
***************************************************************/
DROP FUNCTION p_import_harian (l_proses_id VARCHAR) ;
--
CREATE FUNCTION p_import_harian (l_proses_id VARCHAR) 
RETURNS VARCHAR 
--
AS $$ 
--
DECLARE vResult VARCHAR(100);
--
BEGIN 
    vResult := 'NA';
    --
    DELETE FROM TMP02HARIAN 
    WHERE CREATED_ON < CURRENT_DATE - interval '1 day' ;
    --
    UPDATE TMP02HARIAN 
    SET status_validasi = NULL
    WHERE proses_id=l_proses_id ;
    -------------------
    --Validasi pendukung, untuk memudahkan users
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'NIP harus berisi huruf uppercase'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.NIP<>UPPER(TMP.NIP);
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'Flag pendapatan/potongan harus diisi D atau P (uppercase)'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.flgdppt NOT IN ('D','P') and TMP.flgdppt<>UPPER(TMP.flgdppt);
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'Format tanggal dokumen harus YYYY-MM-DD'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
          FN_ISDATE(TMP.tanggal)=0 ;
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'Kolom Bayar Dimuka harus diisi Y atau kosong'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.bayardimuka NOT IN (' ','Y');
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'Kolom flag harus diisi Y atau kosong'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.flag NOT IN (' ','Y');
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'Kolom hari harus format decimal point 1 (contoh 2.5)'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
         (SUBSTRING(TMP.hari,LENGTH(TMP.hari)-1,1)<>'.' OR
          FN_ISNUMERIC(SUBSTRING(TMP.hari,LENGTH(TMP.hari),1))=false OR
          FN_ISNUMERIC(SUBSTRING(TMP.hari,1,LENGTH(TMP.hari)-2))=false);
    -------------------
    -- Validasi inti sesuai bussiness rules
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'NIP tidak terdefinisi di Master Pegawai'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp02_id
    FROM TMP02HARIAN TMP
    LEFT JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND M15.NIP IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp02_id=Q1.tmp02_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'NIP tidak aktif (pegawai sudah keluar)'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp02_id
    FROM TMP02HARIAN TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND 
          TO_DATE(TMP.tanggal,'YYYY-MM-DD') > COALESCE(M15.tglkeluar,TO_DATE('2999-12-31','YYYY-MM-DD'))
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp02_id=Q1.tmp02_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'Kode pendapatan/potongan harus terdefinisi di Tabel Pend/Pot'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp02_id
    FROM TMP02HARIAN TMP
    LEFT JOIN m03dppt M03 ON M03.flgdppt=TMP.flgdppt AND M03.kddppt=TMP.kddppt
    WHERE TMP.proses_id=l_proses_id AND M03.flgdppt IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp02_id=Q1.tmp02_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'Tanggal transaksi lebih kecil daripada tanggal payroll terakhir'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp02_id
    FROM TMP02HARIAN TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.tanggal,'YYYY-MM-DD') < COALESCE(M15.tglpayr,TO_DATE('2000-01-01','YYYY-MM-DD'))
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp02_id=Q1.tmp02_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'Kolom hari harus diisi'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.hari in (' ','0');
    -------------------
    UPDATE TMP02HARIAN TMP
    SET status_validasi = 'Data sudah terdefinisi di Tabel Pend/Pot Harian'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp02_id
    FROM TMP02HARIAN TMP
    INNER JOIN T02ABSN T02 ON T02.NIP=TMP.NIP AND T02.flgdppt=TMP.flgdppt AND T02.kddppt=TMP.kddppt AND
                              TO_DATE(TMP.tanggal,'YYYY-MM-DD')=T02.tanggal
    WHERE TMP.proses_id=l_proses_id 
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp02_id=Q1.tmp02_id AND TMP.status_validasi IS NULL  ;
    -------------------
    --insert data ke tabel jika lolos validasi
    INSERT INTO T02ABSN(nip, tanggal, flgdppt, kddppt, jmlabsn, bayardimuka, flag,
                m03_id, version, created_by, created_on, updated_by, updated_on)
    SELECT  TMP.nip, TO_DATE(TMP.tanggal,'YYYY-MM-DD'), TMP.flgdppt, TMP.kddppt, 
            TO_NUMBER(TMP.hari,'99.9'), 
            CASE WHEN TMP.bayardimuka='Y' THEN 1 ELSE 0 END, 
            CASE WHEN TMP.flag='Y' THEN 1 ELSE 0 END, 
            M03.m03_id, 0, 'UPLOAD_DAT', TMP.created_on, TMP.updated_by, TMP.updated_on 
    FROM TMP02HARIAN TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    INNER JOIN m03dppt M03 ON M03.flgdppt=TMP.flgdppt AND M03.kddppt=TMP.kddppt
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  ;
    --
    RETURN(vResult);
END ; 
--
$$ LANGUAGE plpgsql ;

 



--select TMP.prdsd, SUBSTRING(TMP.prdsd,7,4) from TMP02HARIAN tmp
--select * from p_validasi_upload('1385108364748')
--select * from TMP02HARIAN WHERE proses_id='1385108364748'
--UPDATE TMP02HARIAN SET status_validasi IS NULL 




