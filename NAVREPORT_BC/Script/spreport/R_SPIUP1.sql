/**************************************************************
Name sprocs	: R_SPIUP1
Create by	: Deni
Date		: 03-09-2002
Description	: Proses Laporan Summary O/S Piutang Pegawai
Call From	: spiuS1
***************************************************************/
DROP FUNCTION R_SPIUP1(l_Periode	DATE,
                        l_NIPFr     VARCHAR(10),
                        l_NIPTo     VARCHAR(10),
                        l_JnPiuFr	VARCHAR(4),
                        l_JnPiuTo	VARCHAR(4),
                        l_UkerFr	VARCHAR(8),
                        l_UkerTo	VARCHAR(8),
                        l_Pil       VARCHAR(1),  
                        l_UserId    INT);
--
CREATE FUNCTION R_SPIUP1(l_Periode	DATE,
                        l_NIPFr     VARCHAR(10),
                        l_NIPTo     VARCHAR(10),
                        l_JnPiuFr	VARCHAR(4),
                        l_JnPiuTo	VARCHAR(4),
                        l_UkerFr	VARCHAR(8),
                        l_UkerTo	VARCHAR(8),
                        l_Pil       VARCHAR(1),  --> PIL = 'N' NIP : 'P' PIUTANG
                        l_UserId    INT)
--
RETURNS TABLE (
		OUTPilihan          VARCHAR(1),
		OUTNIP              VARCHAR(10),
		OUTNama             VARCHAR(25),
		OUTKdUker           VARCHAR(8),
		OUTJnsPiut          VARCHAR(4),
		OUTTglDoku          DATE,
		OUTTglCicil         DATE,
		OUTPokok            DECIMAL(15,2),
		OUTBunga            DECIMAL(15,2),
		OUTTglMaks          DATE,
		OUTJmlAngs          DECIMAL(4,0))
--
AS $$
--
BEGIN
    RETURN QUERY
    SELECT  l_Pil AS Pilihan, T05.NIP, M15.Nama, M15.KdUKer, T05.KdJnsP, T05.TgDoku, 
            T07.TgCicil AS TgCicil, T05.Piutang-T05.BayPokPay-T05.BayPokLgs AS Pokok,
            T05.Bunga-T05.BayBngPay-T05.BayBngLgs AS Bunga, T06.TglMax, 
            COALESCE(T06.JmlAng,0) :: DECIMAL(4,0) AS JmlAng
    FROM T05HPIU T05
    INNER JOIN M15PEGA M15 ON T05.NIP=M15.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)
               ) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    LEFT JOIN  (SELECT NIP, KdJnsP, TgPiut, MAX(TgDoku) :: DATE AS TgCicil  
                FROM T07BAYP GROUP BY NIP, KdJnsP, TgPiut
               ) T07 ON T07.NIP=T05.NIP AND T07.KdJnsP=T05.kdJnsP AND T07.TgPiut=T05.TgDoku
    LEFT JOIN  (SELECT T06.NIP, T06.KdJnsP, T06.TgDoku, MAX(T06.PrdAngs) :: DATE AS TglMax, COUNT(T06.NIP) :: DECIMAL(4,0) AS JmlAng
                    FROM T06DPIU T06 GROUP BY T06.NIP, T06.KdJnsP, T06.TgDoku
               ) T06 ON T06.NIP=T05.NIP AND T06.KdJnsP=T05.kdJnsP AND T06.TgDoku=T05.TgDoku
    WHERE T05.TgDoku<=l_Periode AND
          (T05.NIP    BETWEEN l_NIPFr AND l_NIPTo) AND
          (T05.KdJnsP BETWEEN l_JnPiuFr AND l_JnPiuTo) AND
          (M15.KdUKer BETWEEN l_UkerFr AND l_UkerTo) AND
          (T05.Piutang+T05.Bunga <> T05.BayPokPay+T05.BayBngPay+T05.BayPokLgs+T05.BayBngLgs) ;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_SPIUP1(l_Periode	DATE,
                        l_NIPFr     VARCHAR(10),
                        l_NIPTo     VARCHAR(10),
                        l_JnPiuFr	VARCHAR(4),
                        l_JnPiuTo	VARCHAR(4),
                        l_UkerFr	VARCHAR(8),
                        l_UkerTo	VARCHAR(8),
                        l_Pil       VARCHAR(1),  --> PIL = 'N' NIP : 'P' PIUTANG
                        l_UserId    INT)

select * from R_SPIUP1('2013-12-31', ' ','ZZZ', ' ','ZZZ', ' ','ZZZ', 'N', 1)
*/


