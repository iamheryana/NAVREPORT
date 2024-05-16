DROP FUNCTION public.p_validasi_upload (in l_proses_id varchar)
GO
--
CREATE OR REPLACE FUNCTION public.p_validasi_upload (in l_proses_id varchar) RETURNS varchar AS
$BODY$ 
declare vResult varchar(100);
--
BEGIN 
vResult := 'NA';
--
UPDATE tmp01vari 
SET status_validasi = ''
WHERE proses_id=l_proses_id ;
-------------------
--Validasi pendukung, untuk memudahkan users
UPDATE tmp01vari TMP
SET status_validasi = 'NIP harus berisi huruf uppercase'
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' AND TMP.NIP<>upper(TMP.NIP);
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Flag pendapatan/potongan harus diisi D atau P (uppercase)'
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' AND TMP.flgdppt NOT IN ('D','P') and TMP.flgdppt<>UPPER(TMP.flgdppt);
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Kode Currency harus diisi uppercase'
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' AND TMP.kdcurr<>UPPER(TMP.kdcurr);
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Karena nilai diisi maka kode currency tidak boleh kosong'
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' AND COALESCE(TMP.nilai,'0')<>'0' AND COALESCE(TMP.kdcurr,'')='';
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Format tanggal periode mulai harus DD-MM-YYYY'
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' AND (
      length(COALESCE(TMP.prdmulai,''))<>10 OR
      (substring(TMP.prdmulai,1,2) not between '01' and '31') OR
      (substring(TMP.prdmulai,4,2) not between '01' and '12') OR
      (substring(TMP.prdmulai,7,4) not between '1900' and '2999') OR
      (substring(TMP.prdmulai,3,1) <> '-') OR
      (substring(TMP.prdmulai,6,1) <> '-'));
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Format tanggal periode s/d harus DD-MM-YYYY'
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' AND (
      length(COALESCE(TMP.prdsd,''))<>10 OR
      (substring(TMP.prdsd,1,2) not between '01' and '31') OR
      (substring(TMP.prdsd,4,2) not between '01' and '12') OR
      (substring(TMP.prdsd,7,4) not between '1900' and '2999') OR
      (substring(TMP.prdsd,3,1) <> '-') OR
      (substring(TMP.prdsd,6,1) <> '-'));
-- Validasi inti sesuai bussiness rules
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'NIP tidak terdefinisi di Master Pegawai'
FROM 
(
SELECT TMP.proses_id
FROM tmp01vari TMP
LEFT JOIN m15pega M15 ON M15.NIP=TMP.NIP
WHERE TMP.proses_id=l_proses_id AND M15.NIP IS NULL
) Q1
WHERE TMP.proses_id=Q1.proses_id AND TMP.status_validasi='' ;
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Kode pendapatan/potongan harus terdefinisi di Tabel Pend/Pot'
FROM 
(
SELECT TMP.proses_id
FROM tmp01vari TMP
LEFT JOIN m03dppt M03 ON M03.flgdppt=TMP.flgdppt AND M03.kddppt=TMP.kddppt
WHERE TMP.proses_id=l_proses_id AND M03.flgdppt IS NULL
) Q1
WHERE TMP.proses_id=Q1.proses_id AND TMP.status_validasi='' ;
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Kode pendapatan/potongan computer defined'
FROM 
(
SELECT TMP.proses_id
FROM tmp01vari TMP
INNER JOIN m03dppt M03 ON M03.flgdppt=TMP.flgdppt AND M03.kddppt=TMP.kddppt
WHERE TMP.proses_id=l_proses_id AND M03.uscomp='K'
) Q1
WHERE TMP.proses_id=Q1.proses_id AND TMP.status_validasi='' ;
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Kode currency harus terdefinisi di Tabel Currency'
FROM 
(
SELECT TMP.proses_id
FROM tmp01vari TMP
LEFT JOIN m40curr M40 ON M40.kdcurr=TMP.kdcurr
WHERE TMP.proses_id=l_proses_id AND M40.kdcurr IS NULL
) Q1
WHERE TMP.proses_id=Q1.proses_id AND TMP.status_validasi='' ;
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Range tanggal periode salah'
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' AND 
      TO_DATE(TMP.prdmulai,'DD-MM-YYYY') > TO_DATE(TMP.prdsd,'DD-MM-YYYY');
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Tanggal mulai lebih kecil daripada tanggal masuk'
FROM 
(
SELECT TMP.proses_id
FROM tmp01vari TMP
INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.prdmulai,'DD-MM-YYYY') < M15.tglmasuk
) Q1
WHERE TMP.proses_id=Q1.proses_id AND TMP.status_validasi='' ;
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Tanggal periode s/d lebih besar daripada tanggal keluar'
FROM 
(
SELECT TMP.proses_id
FROM tmp01vari TMP
INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.prdsd,'DD-MM-YYYY') > COALESCE(M15.tglkeluar,TO_DATE('31-12-2999','DD-MM-YYYY'))
) Q1
WHERE TMP.proses_id=Q1.proses_id AND TMP.status_validasi='' ;
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Tanggal transaksi lebih kecil daripada tanggal payroll'
FROM 
(
SELECT TMP.proses_id
FROM tmp01vari TMP
INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.prdmulai,'DD-MM-YYYY') < COALESCE(M15.tglpayr,TO_DATE('01-01-2000','DD-MM-YYYY'))
) Q1
WHERE TMP.proses_id=Q1.proses_id AND TMP.status_validasi='' ;
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Nilai atau persen harus diisi salah satu'
WHERE ((COALESCE(TMP.nilai,'0')<>'0' AND COALESCE(TMP.persen,'0')<>'0') OR 
       (COALESCE(TMP.nilai,'0')='0' AND COALESCE(TMP.persen,'0')='0')) AND 
      TMP.proses_id=l_proses_id AND TMP.status_validasi='';
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Pembayaran dimuka hanya berlaku untuk tipe pendapatan'
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' AND TMP.bayardimuka='Y' AND TMP.flgdppt='P';
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Kolom Bayar Dimuka harus diisi Y atau kosong'
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' AND TMP.bayardimuka NOT IN (' ','Y');
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Kolom Flag harus diisi Y atau kosong'
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' AND TMP.flag NOT IN (' ','Y');
-------------------
UPDATE tmp01vari TMP
SET status_validasi = 'Data sudah terdefinisi di Tabel Pend/Pot Variable'
FROM 
(
SELECT TMP.proses_id
FROM tmp01vari TMP
INNER JOIN T03VARI T03 ON T03.NIP=TMP.NIP AND T03.flgdppt=TMP.flgdppt AND T03.kddppt=TMP.kddppt 
WHERE TMP.proses_id=l_proses_id AND TO_DATE(TMP.prdmulai,'DD-MM-YYYY')=T03.prdmulai AND 
      TO_DATE(TMP.prdsd,'DD-MM-YYYY')=T03.prdsd
) Q1
WHERE TMP.proses_id=Q1.proses_id AND TMP.status_validasi='' ;
-------------------
--insert data ke tabel jika lolos validasi
INSERT INTO t03vari(nip, flgdppt, kddppt, prdmulai, prdsd, nilai, kdcurr, persen, 
            bayardimuka, flag, m03_id, version, created_by, created_on)
SELECT  TMP.nip, TMP.flgdppt, TMP.kddppt, 
        TO_DATE(TMP.prdmulai,'DD-MM-YYYY'), 
        TO_DATE(TMP.prdsd,'DD-MM-YYYY'), 
        TO_NUMBER(TMP.nilai,'9999999999999.99'), 
        TMP.kdcurr, 
        TO_NUMBER(TMP.persen,'999.99'), 
        CASE WHEN TMP.bayardimuka='Y' THEN 1 ELSE 0 END, 
        CASE WHEN TMP.flag='Y' THEN 1 ELSE 0 END, 
        M03.m03_id, TMP.version, TMP.created_by, TMP.created_on 
FROM tmp01vari TMP
INNER JOIN m15pega M15 ON M15.NIP=TMP.NIP
INNER JOIN m03dppt M03 ON M03.flgdppt=TMP.flgdppt AND M03.kddppt=TMP.kddppt
WHERE TMP.proses_id=l_proses_id AND TMP.status_validasi='' ;
--
return(vResult);
END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
 

--select TMP.prdsd, substring(TMP.prdsd,7,4) from tmp01vari tmp
--select * from p_validasi_upload('1385108364748')
--select * from tmp01vari WHERE proses_id='1385108364748'
--UPDATE tmp01vari SET status_validasi=''