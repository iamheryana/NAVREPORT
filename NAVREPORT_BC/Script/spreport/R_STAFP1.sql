/****************************************
Name sprocs	: R_STAFP1
Create by	: Deni 
Date		: 04-12-2013
Description	: Laporan Pegawai - Staff List
CATATAN KHUSUS : RANGE SECURITY DIKIRIM TAPI NGGAK DIPAKE (SESUAI SDR) 
*****************************************/
DROP FUNCTION R_STAFP1 (l_Tanggal       DATE, 
                        l_Status        VARCHAR(1), 
                        l_UkerFr        VARCHAR(10),  
                        l_UkerTo        VARCHAR(10), 
				        l_UserId        INT);
--
CREATE FUNCTION R_STAFP1 (
                        l_Tanggal 		DATE,
                        l_Status        VARCHAR(1), 
                        l_UkerFr		VARCHAR(10), 
                        l_UkerTo		VARCHAR(10), 
                        l_UserId        INT)
--
RETURNS TABLE ( OUTNIP          VARCHAR(10), 
                OUTNAMA         VARCHAR(25),
                OUTAlamat       VARCHAR(80),
                OUTKelurahan    VARCHAR(20),
                OUTKecamatan    VARCHAR(20),
                OUTKota         VARCHAR(25),
                OUTTelpon       VARCHAR(15),
                OUTTglmasuk     DATE,
                OUTKdcaba       VARCHAR(4),
                OUTKduker       VARCHAR(4),
                OUTKdgolg       VARCHAR(4),
                OUTTglkeluar    DATE,
                OUTKetUket      VARCHAR(20))
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT  M15.NIP, M15.Nama, M15.Alamat, M15.Kelurahan, M15.Kecamatan, M15.Kota, 
            M15.Telpon, M15.TglMasuk, M15.KdCaba, M15.KdUKer, M15.KdGlng, 
            M15.TglKeluar, M17.Keterangan
    FROM M15PEGA M15
    INNER JOIN M17UKER M17 ON M15.KdUKer = M17.KdUker
--    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
--                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.KdUKer BETWEEN l_UkerFr AND l_UkerTo) AND M15.TglMasuk >= l_Tanggal AND
          ( l_Status = 'S' OR 
           (l_Status = 'K' AND M15.TglMasuk IS NOT NULL AND M15.TglKeluar IS NOT NULL AND M15.TglKeluar <= l_Tanggal) OR 
           (l_Status = 'A' AND M15.TglMasuk IS NOT NULL AND M15.TglMasuk <= l_Tanggal AND
                               (M15.TglKeluar IS NULL OR M15.TglKeluar >= l_Tanggal))) ;
    --   
END;
$$ LANGUAGE plpgsql ;

/*
select * from R_STAFP1('2013-01-01' :: DATE, 'A', ' ', 'ZZZZ', 1)
*/
