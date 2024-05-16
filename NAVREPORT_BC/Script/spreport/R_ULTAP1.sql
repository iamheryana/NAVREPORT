/******************************************************
Name sprocs	: R_ULTAP1
Create by	: Yudi, 21/11/2002
Description	: Proses Cari Periode Ulang Tahun Pegawai 
Call From	: ULTAS1
Sub sprocs	: 
by peggy 2006 06 07 nambahin status aktif / keluar / all 
********************************************************/
DROP FUNCTION R_ULTAP1 (
                	l_P_TGLFR   INT, 
					l_P_BLNFR   INT, 
					l_P_TGLTO   INT, 
					l_P_BLNTO   INT,
					l_sts       VARCHAR(1),
					l_tglstatus DATE,
                    l_UserId    INT);
--
CREATE FUNCTION R_ULTAP1 (
                	l_P_TGLFR   INT, 
					l_P_BLNFR   INT, 
					l_P_TGLTO   INT, 
					l_P_BLNTO   INT,
					l_sts       VARCHAR(1),
					l_tglstatus DATE,
                    l_UserId    INT)

RETURNS TABLE ( OUTNIP      VARCHAR(10), 
                OUTNAMA     VARCHAR(25),
                OUTJnsklmn  VARCHAR(1),
                OUTTgllahir date,
                OUTUsia     INT, 
                OUTThnLhr   INT, 
                OUTBlnLhr   INT, 
                OUTTglLhr   INT, 
                OUTTglmasuk date,
                OUTMasaKerja INT, 								
                OUTKdCaba   VARCHAR(4), 
                OUTSkCaba   VARCHAR(10),
                OUTKdUUsa   VARCHAR(4), 
                OUTAbbrUUsa VARCHAR(10),
                OUTKdUKer   VARCHAR(8), 
                OUTAbbrUKer VARCHAR(10),
                OUTKdJaba   VARCHAR(4), 
                OUTM04Keterangan VARCHAR(20)) 
AS $$
--
DECLARE l_P_PERIODEFR VARCHAR(4); 
	l_P_PERIODETO VARCHAR(4);

BEGIN 
	l_P_PERIODEFR := RIGHT('0'||LTRIM(l_P_BLNFR::VARCHAR(2)),2)||RIGHT('0'||LTRIM(l_P_TGLFR::VARCHAR(2)),2);
	l_P_PERIODETO := RIGHT('0'||LTRIM(l_P_BLNTO::VARCHAR(2)),2)||RIGHT('0'||LTRIM(l_P_TGLTO::VARCHAR(2)),2);
    --
	RETURN QUERY 
	SELECT M15.NIP, M15.Nama, M15.JnsKlmn, M15.TglLahir,
           EXTRACT(YEAR FROM AGE(CURRENT_DATE,M15.TglLahir)) :: INT AS Usia,
		   EXTRACT(YEAR FROM TglLahir) :: int AS ThnLhr, 
		   EXTRACT(MONTH FROM TglLahir) :: int AS BlnLhr,
		   EXTRACT(DAY FROM TglLahir)  :: int AS TglLhr, 
		   M15.TglMasuk,
           EXTRACT(YEAR FROM AGE(CURRENT_DATE,M15.TglMasuk)) :: INT as MasaKerja,
		   M15.KdCaba, M08.SkCaba, M15.KdUUsa, M02.Singkatan AS AbbrUUsa,
		   M15.KdUKer, M17.Singkatan AS AbbrUKer,M15.KdJaba, M04.Keterangan
	FROM M15PEGA M15
	INNER JOIN M08HCAB M08 ON M08.KdCaba = M15.KdCaba
	INNER JOIN M02UUSA M02 ON M02.Kode = M15.KdUUsa
	INNER JOIN M17UKER M17 ON M17.KdUker = M15.KdUKer
	INNER JOIN M04HJAB M04 ON M04.Kode = M15.KdJaba 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
	WHERE (TO_CHAR(M15.TglLahir,'MMDD') BETWEEN l_P_PERIODEFR AND l_P_PERIODETO) AND 
          ( l_sts = 'S' OR 
           (l_sts = 'K' AND M15.TglMasuk IS NOT NULL AND M15.TglKeluar IS NOT NULL AND M15.TglKeluar <= l_TglStatus) OR 
           (l_sts = 'A' AND M15.TglMasuk IS NOT NULL AND M15.TglMasuk <= l_TglStatus AND
                               (M15.TglKeluar IS NULL OR M15.TglKeluar >= l_TglStatus))) ;
END;
--
$$ LANGUAGE plpgsql ;

/* TESTING...

select * from R_ULTAP1(1, 1, 31, 12,  'S','2013-11-08', 1)

select year(current_date)
*/
