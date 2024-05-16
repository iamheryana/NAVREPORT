/**************************************************************
Name sprocs	: R_OSRAP1
Create by	: Deni
Date		: 14-02-2014
Description	: Proses Laporan Summary O/S Piutang yang Belum Ada Angsuran
***************************************************************/
DROP FUNCTION R_OSRAP1(l_Periode	DATE,
                         l_NIPFr    VARCHAR(10),
                         l_NIPTo    VARCHAR(10),
                         l_JnPiuFr	VARCHAR(4),
                         l_JnPiuTo	VARCHAR(4),
                         l_UkerFr	VARCHAR(8),
                         l_UkerTo	VARCHAR(8),  
                         l_UserId   INT);
--
CREATE FUNCTION R_OSRAP1(l_Periode	DATE,
                         l_NIPFr    VARCHAR(10),
                         l_NIPTo    VARCHAR(10),
                         l_JnPiuFr	VARCHAR(4),
                         l_JnPiuTo	VARCHAR(4),
                         l_UkerFr	VARCHAR(8),
                         l_UkerTo	VARCHAR(8),  
                         l_UserId   INT)
--
RETURNS TABLE (
		OUTNIP              VARCHAR(10),
		OUTNama             VARCHAR(25),
		OUTKdUker           VARCHAR(8),
		OUTJnsPiut          VARCHAR(4),
		OUTTglDoku          DATE,
		OUTTglAngs          DATE,
		OUTPokok            DECIMAL(15,2),
		OUTBunga            DECIMAL(15,2))
--
AS $$
--
BEGIN
    RETURN QUERY
    SELECT T05.NIP, M15.Nama, M15.KdUKer, T05.KdJnsP, T05.TgDoku, T06.PrdAngs,
           T05.Piutang-(T05.BayPokPay+T05.BayPokLgs) :: DECIMAL(15,2) AS Pokok,
           T05.Bunga-(T05.BayBngPay+T05.BayBngLgs) :: DECIMAL(15,2) AS Bunga
    FROM T05HPIU T05 
    INNER JOIN M15PEGA M15 ON M15.NIP=T05.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)
               ) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    LEFT JOIN  (SELECT T06.NIP, T06.KdJnsP, T06.TgDoku, MAX(T06.PrdAngs) :: DATE AS PrdAngs
                    FROM T06DPIU T06 GROUP BY T06.NIP, T06.KdJnsP, T06.TgDoku
               ) T06 ON T06.NIP=T05.NIP AND T06.KdJnsP=T05.kdJnsP AND T06.TgDoku=T05.TgDoku
    WHERE (T05.Piutang+T05.Bunga) > (T05.BayPokPay+T05.BayBngPay+T05.BayPokLgs+T05.BayBngLgs) AND 
          T05.TgDoku <= l_Periode AND (T06.PrdAngs IS NULL OR T06.PrdAngs < l_Periode) AND 
          (T05.NIP BETWEEN  l_NIPFr AND l_NIPTo) AND
          (T05.KdJnsP BETWEEN l_JnPiuFr AND l_JnPiuTo) AND
          (M15.KdUKer BETWEEN l_UkerFr AND l_UkerTo) ;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_OSRAP1(l_Periode	DATE,
                         l_NIPFr    VARCHAR(10),
                         l_NIPTo    VARCHAR(10),
                         l_JnPiuFr	VARCHAR(4),
                         l_JnPiuTo	VARCHAR(4),
                         l_UkerFr	VARCHAR(8),
                         l_UkerTo	VARCHAR(8),  
                         l_UserId   INT)

select * from R_OSRAP1('2013-12-31', ' ','ZZZ', ' ','ZZZ', ' ','ZZZ', 1)
*/



