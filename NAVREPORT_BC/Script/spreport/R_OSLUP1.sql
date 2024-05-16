/**************************************************************
Name sprocs	: R_OSLUP1
Create by	: Deni
Date		: 14-02-2014
Description	: Proses Laporan Summary O/S Piutang yang sudah lunas 
***************************************************************/
DROP FUNCTION R_OSLUP1(  l_Periode      DATE,
                         l_PeriodeTo	DATE,
                         l_NIPFr		VARCHAR(10),
                         l_NIPTo		VARCHAR(10),
                         l_JnPiuFr      VARCHAR(4),
                         l_JnPiuTo      VARCHAR(4),
                         l_UkerFr       VARCHAR(8),
                         l_UkerTo       VARCHAR(8),  
                         l_UserId       INT);
--
CREATE FUNCTION R_OSLUP1(l_Periode      DATE,
                         l_PeriodeTo	DATE,
                         l_NIPFr		VARCHAR(10),
                         l_NIPTo		VARCHAR(10),
                         l_JnPiuFr      VARCHAR(4),
                         l_JnPiuTo      VARCHAR(4),
                         l_UkerFr       VARCHAR(8),
                         l_UkerTo       VARCHAR(8),  
                         l_UserId       INT)
--
RETURNS TABLE (
		OUTNIP              VARCHAR(10),
		OUTNama             VARCHAR(25),
		OUTKdUker           VARCHAR(8),
		OUTJnsPiut          VARCHAR(4),
		OUTTglDoku          DATE,
		OUTTglAngs          DATE,
		OUTPiutang          DECIMAL(15,2),
		OUTBunga            DECIMAL(15,2))
--
AS $$
--
BEGIN
    RETURN QUERY
    SELECT T05.NIP, M15.Nama, M15.KdUKer, T05.KdJnsP, T05.TgDoku, X07.PrdAngs,
           T05.Piutang, T05.Bunga
    FROM T05HPIU T05 
    INNER JOIN M15PEGA M15 ON M15.NIP=T05.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)
               ) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    INNER JOIN (SELECT NIP, KdJnsP, TgPiut, MAX(TgDoku) :: DATE AS TgDoku  
                FROM T07BAYP GROUP BY NIP, KdJnsP, TgPiut
               ) T07 ON T07.NIP=T05.NIP AND T07.KdJnsP=T05.kdJnsP AND T07.TgPiut=T05.TgDoku
    LEFT JOIN (SELECT NIP, KdJnsP, TgPiut, MAX(TgDoku) :: DATE AS PrdAngs  
                FROM T07BAYP GROUP BY NIP, KdJnsP, TgPiut
               ) X07 ON X07.NIP=T05.NIP AND X07.kdJnsP=T05.KdJnsP AND X07.TgPiut=T05.TgDoku 
    WHERE (T05.Piutang+T05.Bunga)=(T05.BayPokPay+T05.BayBngPay+T05.BayPokLgs+T05.BayBngLgs) AND 
          T07.TgDoku BETWEEN l_Periode AND l_PeriodeTo AND 
          (T05.NIP BETWEEN  l_NIPFr AND l_NIPTo) AND
          (T05.KdJnsP BETWEEN l_JnPiuFr AND l_JnPiuTo) AND
          (M15.KdUKer BETWEEN l_UkerFr AND l_UkerTo) ;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_OSLUP1(l_Periode      DATE,
                         l_PeriodeTo	DATE,
                         l_NIPFr		VARCHAR(10),
                         l_NIPTo		VARCHAR(10),
                         l_JnPiuFr      VARCHAR(4),
                         l_JnPiuTo      VARCHAR(4),
                         l_UkerFr       VARCHAR(8),
                         l_UkerTo       VARCHAR(8),  
                         l_UserId       INT)

select * from R_OSLUP1('2010-01-01','2013-12-31', ' ','ZZZ', ' ','ZZZ', ' ','ZZZ', 1)
*/



