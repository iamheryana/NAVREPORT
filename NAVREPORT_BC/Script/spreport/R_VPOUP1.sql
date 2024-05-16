/******************************************************
Name sprocs	: R_VPOUP1
Create by	: Frans, 20/03/2007
Description	: Proses Validasi Pegawai Keluar
********************************************************/
DROP FUNCTION  R_VPOUP1 (
                	l_mdPeriodeFr 	DATE, 
					l_mdPeriodeTo	DATE,
					l_mcNIPFr		VARCHAR(10),
					l_mcNIPTo		VARCHAR(10),
                    l_UserId        INT) ;
--
CREATE FUNCTION  R_VPOUP1 (
                	l_mdPeriodeFr 	DATE, 
					l_mdPeriodeTo	DATE,
					l_mcNIPFr		VARCHAR(10),
					l_mcNIPTo		VARCHAR(10),
                    l_UserId        INT) 
--
RETURNS TABLE ( OUTNIP          VARCHAR(10), 
                OUTNAMA         VARCHAR(25),
                OUTKdCaba       VARCHAR(4), 		
                OUTnmcaba       VARCHAR(40),	
                OUTKdKlas       VARCHAR(4), 			
                OUTketerangan   VARCHAR(20),  		
                OUTTglmasuk     date,
                OUTTglKeluar    date,
                OUTTglPosting   date,
                OUTFlagTHR      VARCHAR(1))		
--
AS $$
--
BEGIN 
    RETURN QUERY 
    SELECT Q.NIP, Q.Nama, Q.KdCaba, Q.NmCaba, Q.KdKlas, Q.Keterangan, Q.TglMasuk, Q.TglKeluar,
           Q.TglPosting, S05.FlagTHR
    FROM
    ( -- Q
        SELECT M15.NIP, M15.Nama, M15.KdCaba, M08.NmCaba, M15.KdKlas, M10.Keterangan, 
               M15.KdGlng, M15.TglMasuk, M15.TglKeluar, 
               FN_CARITGLPAYR(M15.NIP,M15.TglPayr) AS TglPosting
        FROM M15PEGA M15
        INNER JOIN M08HCAB M08 ON M15.KdCaba = M08.KdCaba
        INNER JOIN M10KLAS M10 ON M15.KdKlas = M10.Kode
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                    FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    ) Q
    INNER JOIN S05PSTD S05 ON Q.NIP = S05.NIP AND Q.TglPosting = S05.TglPosting
    WHERE (Q.NIP BETWEEN l_mcNIPFr AND l_mcNIPTo) AND
          (Q.TglKeluar BETWEEN l_mdPeriodeFr AND l_mdPeriodeTo) AND 
           Q.TglKeluar > Q.TglPosting AND 
           Q.TglPosting IS NOT NULL AND Q.TglKeluar IS NOT NULL;
--
END;
--
$$ LANGUAGE plpgsql ;

/* TESTING...
SELECT * FROM R_VPOUP1 ('2012-01-01', '2013-12-01',' ','ZZZ',1)

SELECT * FROM M15PEGA WHERE TGLKELUAR IS NOT NULL
SELECt * FROM S05PSTD WHERE NIP ='U'
*/
