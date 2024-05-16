/**************************************************************
Name sprocs	: P_IMPORT_VARIABLE
Create by	: Deni
Date		: 14-02-2014
Description	: Proses imprt data variable dari excel
***************************************************************/
DROP FUNCTION p_import_variable (l_proses_id VARCHAR) ;
--
CREATE FUNCTION p_import_variable (l_proses_id VARCHAR) 
RETURNS VARCHAR 
--
AS $$ 
DECLARE vResult VARCHAR(100);
--
BEGIN 
    vResult := 'NA';
    --
    DELETE FROM TMP01VARI 
    WHERE CREATED_ON < CURRENT_DATE - interval '1 day' ;
    --
    UPDATE TMP01VARI 
    SET status_validasi = NULL
    WHERE proses_id=l_proses_id ;
    -------------------
    --Validasi pendukung, untuk memudahkan users
    UPDATE TMP01VARI TMP
    SET status_validasi = 'NIP harus berisi huruf uppercase'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.NIP<>UPPER(TMP.NIP);
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Flag pendapatan/potongan harus diisi D atau P (uppercase)'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.flgdppt NOT IN ('D','P') and TMP.flgdppt<>UPPER(TMP.flgdppt);
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Kode Currency harus diisi uppercase'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.kdcurr<>UPPER(TMP.kdcurr);
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Karena nilai diisi maka kode currency tidak boleh kosong'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND COALESCE(TMP.nilai,'0')<>'0' AND COALESCE(TMP.kdcurr,'') IS NULL ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Nilai format tanpa decimal (max 13 digit)'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
          (LENGTH(TMP.nilai)>13 OR FN_ISNUMERIC(TMP.nilai)=false) ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Persen harus format decimal point 2 (max 99.99)'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
         (SUBSTRING(TMP.persen,LENGTH(TMP.persen)-2,1)<>'.' OR
          FN_ISNUMERIC(SUBSTRING(TMP.persen,LENGTH(TMP.persen)-1,2))=false OR
          FN_ISNUMERIC(SUBSTRING(TMP.persen,1,LENGTH(TMP.persen)-3))=false);
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Format tanggal periode mulai harus YYYY-MM-DD'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
          FN_ISDATE(TMP.prdmulai)=0 ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Format tanggal periode s/d harus YYYY-MM-DD'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
          FN_ISDATE(TMP.prdsd)=0 ;
    -- Validasi inti sesuai bussiness rules
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'NIP tidak terdefinisi di Master Pegawai'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp01_id
    FROM TMP01VARI TMP
    LEFT JOIN M15PEGA M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND M15.NIP IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp01_id=Q1.tmp01_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'NIP tidak aktif (pegawai sudah keluar)'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp01_id
    FROM TMP01VARI TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND 
          TO_DATE(TMP.prdmulai,'YYYY-MM-DD') > COALESCE(M15.tglkeluar,TO_DATE('2999-12-31','YYYY-MM-DD'))
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp01_id=Q1.tmp01_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Kode pendapatan/potongan harus terdefinisi di Tabel Pend/Pot'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp01_id
    FROM TMP01VARI TMP
    LEFT JOIN M03DPPT M03 ON M03.flgdppt=TMP.flgdppt AND M03.kddppt=TMP.kddppt
    WHERE TMP.proses_id=l_proses_id AND M03.flgdppt IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp01_id=Q1.tmp01_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Kode pendapatan/potongan computer defined'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp01_id
    FROM TMP01VARI TMP
    INNER JOIN M03DPPT M03 ON M03.flgdppt=TMP.flgdppt AND M03.kddppt=TMP.kddppt
    WHERE TMP.proses_id=l_proses_id AND M03.uscomp='K'
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp01_id=Q1.tmp01_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Kode currency harus terdefinisi di Tabel Currency'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp01_id
    FROM TMP01VARI TMP
    LEFT JOIN M40CURR M40 ON M40.kdcurr=TMP.kdcurr
    WHERE TMP.proses_id=l_proses_id AND M40.kdcurr IS NULL
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp01_id=Q1.tmp01_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Range tanggal periode salah'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND 
          TO_DATE(TMP.prdmulai,'YYYY-MM-DD') > TO_DATE(TMP.prdsd,'YYYY-MM-DD');
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Tanggal mulai lebih kecil daripada tanggal masuk'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp01_id
    FROM TMP01VARI TMP
    INNER JOIN M15PEGA M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.prdmulai,'YYYY-MM-DD') < M15.tglmasuk
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp01_id=Q1.tmp01_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Tanggal periode s/d lebih besar daripada tanggal keluar'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp01_id
    FROM TMP01VARI TMP
    INNER JOIN M15PEGA M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.prdsd,'YYYY-MM-DD') > COALESCE(M15.tglkeluar,TO_DATE('2999-12-31','YYYY-MM-DD'))
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp01_id=Q1.tmp01_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Tanggal transaksi lebih kecil daripada tanggal payroll'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp01_id
    FROM TMP01VARI TMP
    INNER JOIN M15PEGA M15 ON M15.NIP=TMP.NIP
    WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.prdmulai,'YYYY-MM-DD') < COALESCE(M15.tglpayr,TO_DATE('2000-01-01','YYYY-MM-DD'))
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp01_id=Q1.tmp01_id AND TMP.status_validasi IS NULL  ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Nilai atau persen harus diisi salah satu'
    WHERE ((COALESCE(TMP.nilai,'0')<>'0' AND COALESCE(TMP.persen,'0')<>'0') OR 
           (COALESCE(TMP.nilai,'0')='0' AND COALESCE(TMP.persen,'0')='0')) AND 
          TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL ;
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Pembayaran dimuka hanya berlaku untuk tipe pendapatan'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.bayardimuka='Y' AND TMP.flgdppt='P';
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Kolom Bayar Dimuka harus diisi Y atau kosong'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.bayardimuka NOT IN (' ','Y');
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Kolom Flag harus diisi Y atau kosong'
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  AND TMP.flag NOT IN (' ','Y');
    -------------------
    UPDATE TMP01VARI TMP
    SET status_validasi = 'Data sudah terdefinisi di Tabel Pend/Pot Variable'
    FROM 
    (
    SELECT TMP.proses_id, TMP.tmp01_id
    FROM TMP01VARI TMP
    INNER JOIN T03VARI T03 ON T03.NIP=TMP.NIP AND T03.flgdppt=TMP.flgdppt AND T03.kddppt=TMP.kddppt 
    WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.prdmulai,'YYYY-MM-DD')=T03.prdmulai AND 
          TO_DATE(TMP.prdsd,'YYYY-MM-DD')=T03.prdsd
    ) Q1
    WHERE TMP.proses_id=Q1.proses_id AND TMP.tmp01_id=Q1.tmp01_id AND TMP.status_validasi IS NULL  ;
    -------------------
    --insert data ke tabel jika lolos validasi
    INSERT INTO t03vari(nip, flgdppt, kddppt, kdcurr, prdmulai, prdsd, nilai, persen, 
                bayardimuka, flag, m03_id, version, created_by, created_on, updated_by, updated_on)
    SELECT  TMP.nip, TMP.flgdppt, TMP.kddppt, TMP.kdcurr, 
            TO_DATE(TMP.prdmulai,'YYYY-MM-DD'), 
            TO_DATE(TMP.prdsd,'YYYY-MM-DD'), 
            TO_NUMBER(TMP.nilai,'9999999999999'), 
            TO_NUMBER(TMP.persen,'99.99'), 
            CASE WHEN TMP.bayardimuka='Y' THEN 1 ELSE 0 END, 
            CASE WHEN TMP.flag='Y' THEN 1 ELSE 0 END, 
            M03.m03_id, 0, 'UPLOAD_DAT', TMP.created_on, TMP.updated_by, TMP.updated_on 
    FROM TMP01VARI TMP
    INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
    INNER JOIN M03DPPT M03 ON M03.flgdppt=TMP.flgdppt AND M03.kddppt=TMP.kddppt
    WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi IS NULL  ;
    --
    RETURN(vResult);
END ; 
--
$$ LANGUAGE plpgsql ;
 
--select TMP.prdsd, SUBSTRING(TMP.prdsd,7,4) from TMP01VARI tmp
--select * from p_validasi_upload('1385108364748')
--select * from TMP01VARI WHERE proses_id='1385108364748'
--UPDATE TMP01VARI SET status_validasi IS NULL 




