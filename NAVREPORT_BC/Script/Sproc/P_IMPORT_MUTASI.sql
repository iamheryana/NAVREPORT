/**************************************************************
Name sprocs	: P_IMPORT_MUTASI
Create by	: Deni
Date		: 14-02-2014
Description	: Proses imprt data Mutasi Karir dari excel
***************************************************************/
DROP FUNCTION p_import_mutasi (l_proses_id VARCHAR, l_jnsdata VARCHAR) ;
--
CREATE FUNCTION p_import_mutasi (l_proses_id VARCHAR, l_jnsdata VARCHAR) 
RETURNS VARCHAR 
--
AS $$ 
--
DECLARE vResult VARCHAR(100);
--
BEGIN 
    vResult := 'NA';
    --
    DELETE FROM TMP04MUTASI 
    WHERE CREATED_ON < CURRENT_DATE - interval '1 day' ;
    -------------------    
    UPDATE TMP04MUTASI 
    SET status_validasi = NULL
    WHERE proses_id=l_proses_id ;
    -------------------
    --Validasi pendukung, untuk memudahkan users
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Format tanggal dokumen harus YYYY-MM-DD'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
          FN_ISDATE(TMP.tglefektif)=0 ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'NIP harus berisi huruf uppercase'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.NIP<>UPPER(TMP.NIP);
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Alasan Mutasi harus berisi huruf uppercase'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.alasan_mutasi<>UPPER(TMP.alasan_mutasi);
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'NIP tidak terdefinisi di Master Pegawai'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND M15.NIP IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'NIP tidak aktif (pegawai sudah keluar)'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND 
          TO_DATE(TMP.tglefektif,'YYYY-MM-DD') > COALESCE(M15.tglkeluar,TO_DATE('2999-12-31','YYYY-MM-DD'))
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Kode alasan mutasi tidak terdefinisi'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN M24MUTR M24 ON M24.kdmutr=TMP.alasan_mutasi
    WHERE TMP.proses_id=l_proses_id AND M24.kdmutr IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Kode alasan mutasi tidak boleh AFPR'
    WHERE TMP.proses_id=l_proses_id AND TMP.alasan_mutasi='AFPR' AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Penilaian tidak boleh diisi selain BS, BK, CP, KG, KS, TP'
    WHERE TMP.proses_id=l_proses_id AND TMP.penilaian NOT IN ('BS','BK','CP','KG','KS','TP') AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Gaji pokok diisi, Kode valuta harus terdefinisi, '
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN M40CURR M40 ON M40.kdcurr=TMP.valuta
    WHERE TMP.proses_id=l_proses_id AND M40.kdcurr IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND 
          TO_NUMBER(TMP.gaji_pokok,'9999999999999')>0 AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Tanggal efektif lebih kecil daripada tanggal masuk pegawai'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.tglefektif,'YYYY-MM-DD') < COALESCE(M15.tglmasuk,TO_DATE('2000-01-01','YYYY-MM-DD'))
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Tanggal efektif lebih kecil daripada tanggal payroll terakhir'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.tglefektif,'YYYY-MM-DD') < COALESCE(M15.tglpayr,TO_DATE('2000-01-01','YYYY-MM-DD'))
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Data sudah terdefinisi di Transaksi Mutasi Karir'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    INNER JOIN T10MUTA T10 ON T10.NIP=TMP.NIP AND TO_DATE(TMP.tglefektif,'YYYY-MM-DD')=T10.tgleff
    WHERE TMP.proses_id=l_proses_id 
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND TMP.status_validasi IS NULL  ;
    ------------------->>>>
    --jika pilihan ALL, checking master2 terkait
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Klasifikasi pegawai tidak terdefinisi di master'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN M10KLAS M10 ON M10.kode=TMP.klas_pegawai
    WHERE TMP.proses_id=l_proses_id AND M10.kode IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND l_jnsdata='A' AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Kode cabang tidak terdefinisi di master'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN M08HCAB M08 ON M08.kdcaba=TMP.cabang
    WHERE TMP.proses_id=l_proses_id AND M08.kdcaba IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND l_jnsdata='A' AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Kode unit usaha tidak terdefinisi di master'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN M02UUSA M02 ON M02.kode=TMP.unit_usaha
    WHERE TMP.proses_id=l_proses_id AND M02.kode IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND l_jnsdata='A' AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Kode unit kerja tidak terdefinisi di master'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN M17UKER M17 ON M17.kduker=TMP.unit_kerja
    WHERE TMP.proses_id=l_proses_id AND M17.kduker IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND l_jnsdata='A' AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Kode golongan tidak terdefinisi di master'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN M12HGOL M12 ON M12.kode=TMP.golongan
    WHERE TMP.proses_id=l_proses_id AND M12.kode IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND l_jnsdata='A' AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Kode jabatan tidak terdefinisi di master'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN M04HJAB M04 ON M04.kode=TMP.jabatan
    WHERE TMP.proses_id=l_proses_id AND M04.kode IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND l_jnsdata='A' AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Kode area tidak terdefinisi di master'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN M01AREA M01 ON M01.kode=TMP.kdarea
    WHERE TMP.proses_id=l_proses_id AND M01.kode IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND l_jnsdata='A' AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP04MUTASI TMP
    SET status_validasi = 'Kelompok jabatan tidak terdefinisi di master'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp04_id
    FROM TMP04MUTASI TMP
    LEFT JOIN M06HKJB M06 ON M06.kode=TMP.kel_jabatan
    WHERE TMP.proses_id=l_proses_id AND M06.kode IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp04_id=Q1.tmp04_id AND l_jnsdata='A' AND TMP.status_validasi IS NULL  ;
    -------------------
    --insert data ke tabel jika lolos validasi
    IF l_jnsdata='A' THEN
        INSERT INTO T10MUTA(nip, tgleff, nosk, kdmutr, keterangan, gajipokok, kdcurr, penilaian,
                            kdklas, kdcaba, kduusa, kduker, kdglng, kdjaba, kdarea, kdkjab,
                            m04_id, m06_id, m08_id, m12_id, masakerja, prn,
                            version, created_by, created_on, updated_by, updated_on)
        SELECT  TMP.nip, TO_DATE(TMP.tglefektif,'YYYY-MM-DD'), TMP.no_sk, TMP.alasan_mutasi, TMP.keterangan, 
                TO_NUMBER(TMP.gaji_pokok,'9999999999999'), TMP.valuta, TMP.penilaian,
                TMP. klas_pegawai, TMP.cabang, TMP.unit_usaha, TMP.unit_kerja, 
                TMP.golongan, TMP.jabatan, TMP.kdarea, TMP.kel_jabatan,
                M04.m04_id, M06.m06_id, M08.m08_id, M12.m12_id, 0, 0, 
                0, 'UPLOAD_DAT', TMP.created_on, TMP.updated_by, TMP.updated_on 
        FROM TMP04MUTASI TMP
        INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
        INNER JOIN M01AREA M01 ON M01.kode=TMP.kdarea
        INNER JOIN M02UUSA M02 ON M02.kode=TMP.unit_usaha
        INNER JOIN M04HJAB M04 ON M04.kode=TMP.jabatan
        INNER JOIN M06HKJB M06 ON M06.kode=TMP.kel_jabatan
        INNER JOIN M08HCAB M08 ON M08.kdcaba=TMP.cabang
        INNER JOIN M10KLAS M10 ON M10.kode=TMP.klas_pegawai
        INNER JOIN M12HGOL M12 ON M12.kode=TMP.golongan
        INNER JOIN M17UKER M17 ON M17.kduker=TMP.unit_kerja
        WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  ;
    ELSE
        INSERT INTO T10MUTA(nip, tgleff, nosk, kdmutr, keterangan, gajipokok, kdcurr, penilaian,
                            kdklas, kdcaba, kduusa, kduker, kdglng, kdjaba, kdarea, kdkjab,
                            m04_id, m06_id, m08_id, m12_id, masakerja, prn,
                            version, created_by, created_on, updated_by, updated_on)
        SELECT  TMP.nip, TO_DATE(TMP.tglefektif,'YYYY-MM-DD'), TMP.no_sk, TMP.alasan_mutasi, TMP.keterangan, 
                TO_NUMBER(TMP.gaji_pokok,'9999999999999'), TMP.valuta, TMP.penilaian,
                Q1. kdklas, Q1.kdcaba, Q1.kduusa, Q1.kduker, 
                Q1.kdglng, Q1.kdjaba, Q1.kdarea, Q1.kdkjab,
                M04.m04_id, M06.m06_id, M08.m08_id, M12.m12_id, 0, 0, 
                0, 'UPLOAD_DAT', TMP.created_on, TMP.updated_by, TMP.updated_on 
        FROM TMP04MUTASI TMP
        INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
        INNER JOIN (select  nip, kdklas, kdcaba, kduusa, kduker, kdglng, kdjaba, kdarea, kdkjab,
                            max(tgleff) as tgleff 
                    from t10muta
                    group by nip, kdklas, kdcaba, kduusa, kduker, kdglng, kdjaba, kdarea, kdkjab
                    ) Q1 ON Q1.NIP=TMP.NIP
        INNER JOIN M01AREA M01 ON M01.kode=Q1.kdarea
        INNER JOIN M02UUSA M02 ON M02.kode=Q1.kduusa
        INNER JOIN M04HJAB M04 ON M04.kode=Q1.kdjaba
        INNER JOIN M06HKJB M06 ON M06.kode=Q1.kdkjab
        INNER JOIN M08HCAB M08 ON M08.kdcaba=Q1.kdcaba
        INNER JOIN M10KLAS M10 ON M10.kode=Q1.kdklas
        INNER JOIN M12HGOL M12 ON M12.kode=Q1.kdglng
        INNER JOIN M17UKER M17 ON M17.kduker=Q1.kduker
        WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  ;
    END IF ;
    --
    RETURN(vResult);
END ; 
--
$$ LANGUAGE plpgsql ;


/* 
update TMP04MUTASI set status_validasi=null
select * from TMP04MUTASI where proses_id='1395045802701'
SELECT * FROM p_import_mutasi('1395045802701','A'); 
*/
