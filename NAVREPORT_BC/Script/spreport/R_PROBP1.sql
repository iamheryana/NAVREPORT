/********************************************
Name sprocs	: R_PROBP1
Create by	: deni
Date		: 19-08-2009
Description	: Laporan Akhir Masa Probation
*****************************************/
DROP FUNCTION  R_PROBP1   (
                    l_NIPFr		VARCHAR(10),
					l_NIPTo		VARCHAR(10),
					l_UkerFr	VARCHAR(8),
					l_UkerTo	VARCHAR(8),
					l_TglFr  	DATE,
					l_TglTo  	DATE,
					l_Proba		INT,
                    l_UserId    INT);
--
CREATE FUNCTION  R_PROBP1   (
                    l_NIPFr		VARCHAR(10),
					l_NIPTo		VARCHAR(10),
					l_UkerFr	VARCHAR(8),
					l_UkerTo	VARCHAR(8),
					l_TglFr  	DATE,
					l_TglTo  	DATE,
					l_Proba		INT,
                    l_UserId    INT)
--
RETURNS TABLE (OUTNIP VARCHAR(10), 
		OUTNAMA VARCHAR(25),
		OUTTglMasuk DATE, 
		OUTTglProba DATE, 
		OUTKdJaba VARCHAR(4), 
		OUTNmJaba VARCHAR(20), 
		OUTKdCaba VARCHAR(4), 
		OUTNmCaba VARCHAR(40),
		OUTKdUKer VARCHAR(8), 
		OUTNmUKer VARCHAR(20)) 		
AS $$
--
BEGIN 
    RETURN QUERY 
    SELECT	M15.NIP,M15.Nama,M15.TglMasuk,
        ((M15.TglMasuk + (l_Proba ::VARCHAR(3) || ' Months') :: interval) - '1 day' :: interval) :: DATE  AS TglProba,
        M15.KdJaba,M04.Keterangan AS NmJaba,
        M15.KdCaba,M08.NmCaba,
        M15.KdUker,M17.Keterangan AS NmUker
    FROM M15PEGA M15
    INNER JOIN M08HCAB M08 ON M15.KdCaba = M08.KdCaba
    INNER JOIN M17UKER M17 ON M15.KdUker = M17.KdUker
    INNER JOIN M04HJAB M04 ON M15.KdJaba = M04.Kode
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          (M15.KdUker BETWEEN l_UkerFr AND l_UkerTo) AND
          (M15.TglMasuk BETWEEN l_TglFr AND l_TglTo);
END;
$$ LANGUAGE plpgsql ;

--

/*
SELECT * FROM R_PROBP1(' ', 'ZZZ', '  ', 'ZZZ', '2013-01-01', '2013-01-31', 3, 1) 
*/


