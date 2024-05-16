/****************************************
Name sprocs	: R_MSKPP1
Create by	: PEGGY 
Date		: 25-08-2008
Description	: Proses Untuk Report Masa Kerja Pegawai pl#77
Call From	: mskpS1 (CW5.5)
CATATAN KHUSUS : RANGE SECURITY DIKIRIM TAPI NGGAK DIPAKE (SESUAI SDR) 
*****************************************/
DROP FUNCTION R_MSKPP1 (l_tanggal           date, l_masakerja       varchar, 
                        l_masakerjaharifr   int4, l_masakerjaharito int4, 
                        l_masakerjablnfr    int4, l_masakerjablnto  int4, 
                        l_masakerjathnfr    int4, l_masakerjathnto  int4, 
                        l_nipfr varchar, l_nipto varchar, l_UserId INT4);
--
CREATE FUNCTION R_MSKPP1 (
                      l_Tanggal 			DATE,
					  l_MasaKerja			VARCHAR(1), 
					  l_MasaKerjaHariFr     INT4, 
					  l_MasaKerjaHariTo     INT4, 
					  l_MasaKerjaBlnFr		INT4, 
					  l_MasaKerjaBlnTo		INT4, 
					  l_MasaKerjaThnFr		INT4, 
					  l_MasaKerjaThnTo		INT4, 
					  l_NIPFr				VARCHAR(10), 
					  l_NIPTo				VARCHAR(10), 
                      l_UserId              INT4)
--
RETURNS TABLE ( OUTNIP          VARCHAR(10), 
                OUTNAMA         VARCHAR(25),
                OUTTglmasuk     DATE,
                OUTMasakerjathn INT4, 
                OUTMasakerjabln INT4, 
                OUTMasakerjahr  INT4)
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT Q1.NIP, Q1.Nama, Q1.TglMasuk, Q1.MasaKerjaThn, Q1.MasaKerjaBln, Q1.MasaKerjaHr
    FROM 
    (--Q1
    SELECT  Q.NIP, Q.Nama, Q.TglMasuk, 
            (CASE WHEN l_MasaKerja = 'T' AND 
                                    Q.MasaKerjaThn BETWEEN l_MasaKerjaThnFr AND l_MasaKerjaThnTo THEN 
                                    Q.MasaKerjaThn 
                                ELSE 0 END) AS MasaKerjaThn, 
            (CASE WHEN (l_MasaKerja ='B' AND 
                                    Q.MasaKerjaBln BETWEEN l_MasaKerjaBlnFr AND l_MasaKerjaBlnTo AND 
                                    Q.MasaKerjaThn = 0) OR 
                                     (l_MasaKerja = 'T' AND 
                                    Q.MasaKerjaThn BETWEEN l_MasaKerjaThnFr AND l_MasaKerjaThnTo)	THEN 
                                    Q.MasaKerjaBln 
                                ELSE 0 END) AS MasaKerjaBln, 
            (CASE WHEN (l_MasaKerja ='H' AND 
                                    Q.MasaKerjaHari BETWEEN l_MasaKerjaHariFr AND l_MasaKerjaHariTo AND 
                                    Q.MasaKerjaBln = 0 AND Q.MasaKerjaThn = 0) OR 
                                      (l_MasaKerja ='B' AND 
                                    Q.MasaKerjaBln BETWEEN l_MasaKerjaBlnFr AND l_MasaKerjaBlnTo AND 
                                    Q.MasaKerjaThn = 0) OR 
                                     (l_MasaKerja = 'T' AND 
                                    Q.MasaKerjaThn BETWEEN l_MasaKerjaThnFr AND l_MasaKerjaThnTo) THEN 
                                    Q.MasaKerjaHari
                                ELSE 0 END) AS MasaKerjaHr
    FROM 
    (--Q
    SELECT M15.NIP, M15.Nama, M15.TglMasuk,	
           extract(year from age(l_Tanggal,M15.TglMasuk)) :: INT as MasaKerjaThn,
           extract(month from age(l_Tanggal,M15.TglMasuk)) :: INT as MasaKerjaBln,
           extract(day from age(l_Tanggal,M15.TglMasuk)) :: INT as MasaKerjaHari
    FROM M15PEGA M15 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND 
           M15.TglMasuk <= l_Tanggal AND 
          (M15.TglKeluar IS NULL OR 
          (M15.TglKeluar IS NOT NULL AND M15.TglKeluar > l_Tanggal))
    ) Q 
    ) Q1 
    WHERE NOT (Q1.MasaKerjaThn = 0 AND Q1.MasaKerjaBln = 0 AND Q1.MasaKerjaHr = 0) ;
END;
$$ LANGUAGE plpgsql ;

/*
select * from r_mskpp1('2012-07-01', 'H', 1, 31, 1, 12, 1, 99, '', 'ZZ', 1)
*/


