/****************************************
Name sprocs	: R_PEPPP1
Create by	: Deni
Date		: 14-02-2014
Description	: Proses Untuk Report Pembayaran Piutang Pegawai
*****************************************/
DROP FUNCTION R_PEPPP1  (	l_PeriodeFr	DATE,
                        l_PeriodeTo     DATE,
                        l_NIPFr         VARCHAR(10),
                        l_NIPTo         VARCHAR(10),
                        l_Kolom1		VARCHAR(4),
                        l_Kolom2		VARCHAR(4),
                        l_Kolom3		VARCHAR(4),
                        l_Kolom4		VARCHAR(4),
                        l_Kolom5		VARCHAR(4),
                        l_Kolom6        VARCHAR(4),
                        l_Kolom7		VARCHAR(4),
                        l_Kolom8		VARCHAR(4),
                        l_Kolom9		VARCHAR(4),
                        l_Kolom10       VARCHAR(4),
                        l_UserId        INT);
--
CREATE FUNCTION R_PEPPP1  (	l_PeriodeFr	DATE,
                        l_PeriodeTo     DATE,
                        l_NIPFr         VARCHAR(10),
                        l_NIPTo         VARCHAR(10),
                        l_Kolom1		VARCHAR(4),
                        l_Kolom2		VARCHAR(4),
                        l_Kolom3		VARCHAR(4),
                        l_Kolom4		VARCHAR(4),
                        l_Kolom5		VARCHAR(4),
                        l_Kolom6        VARCHAR(4),
                        l_Kolom7		VARCHAR(4),
                        l_Kolom8		VARCHAR(4),
                        l_Kolom9		VARCHAR(4),
                        l_Kolom10       VARCHAR(4),
                        l_UserId        INT)
--
RETURNS TABLE (
		OUTNIP              VARCHAR(10),
		OUTNama             VARCHAR(25),
		OUTPiutang1         DECIMAL(15,2),
		OUTPiutang2         DECIMAL(15,2),
		OUTPiutang3         DECIMAL(15,2),
		OUTPiutang4         DECIMAL(15,2),
		OUTPiutang5         DECIMAL(15,2),
		OUTPiutang6         DECIMAL(15,2),
		OUTPiutang7         DECIMAL(15,2),
		OUTPiutang8         DECIMAL(15,2),
		OUTPiutang9         DECIMAL(15,2),
		OUTPiutang10        DECIMAL(15,2),
		OUTBunga1           DECIMAL(15,2),
		OUTBunga2           DECIMAL(15,2),
		OUTBunga3           DECIMAL(15,2),
		OUTBunga4           DECIMAL(15,2),
		OUTBunga5           DECIMAL(15,2),
		OUTBunga6           DECIMAL(15,2),
		OUTBunga7           DECIMAL(15,2),
		OUTBunga8           DECIMAL(15,2),
		OUTBunga9           DECIMAL(15,2),
		OUTBunga10          DECIMAL(15,2))
--
AS $$
--
BEGIN
    RETURN QUERY
    SELECT  T07.NIP, M15.Nama, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom1 THEN T07.BayPokok ELSE 0 END) :: DECIMAL(15,2) AS Piutang1, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom2 THEN T07.BayPokok ELSE 0 END) :: DECIMAL(15,2) AS Piutang2, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom3 THEN T07.BayPokok ELSE 0 END) :: DECIMAL(15,2) AS Piutang3, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom4 THEN T07.BayPokok ELSE 0 END) :: DECIMAL(15,2) AS Piutang4, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom5 THEN T07.BayPokok ELSE 0 END) :: DECIMAL(15,2) AS Piutang5, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom6 THEN T07.BayPokok ELSE 0 END) :: DECIMAL(15,2) AS Piutang6, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom7 THEN T07.BayPokok ELSE 0 END) :: DECIMAL(15,2) AS Piutang7, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom8 THEN T07.BayPokok ELSE 0 END) :: DECIMAL(15,2) AS Piutang8, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom9 THEN T07.BayPokok ELSE 0 END) :: DECIMAL(15,2) AS Piutang9, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom10 THEN T07.BayPokok ELSE 0 END) :: DECIMAL(15,2) AS Piutang10, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom1 THEN T07.BayBunga ELSE 0 END) :: DECIMAL(15,2) AS Bunga1, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom2 THEN T07.BayBunga ELSE 0 END) :: DECIMAL(15,2) AS Bunga2, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom3 THEN T07.BayBunga ELSE 0 END) :: DECIMAL(15,2) AS Bunga3, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom4 THEN T07.BayBunga ELSE 0 END) :: DECIMAL(15,2) AS Bunga4, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom5 THEN T07.BayBunga ELSE 0 END) :: DECIMAL(15,2) AS Bunga5, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom6 THEN T07.BayBunga ELSE 0 END) :: DECIMAL(15,2) AS Bunga6, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom7 THEN T07.BayBunga ELSE 0 END) :: DECIMAL(15,2) AS Bunga7, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom8 THEN T07.BayBunga ELSE 0 END) :: DECIMAL(15,2) AS Bunga8, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom9 THEN T07.BayBunga ELSE 0 END) :: DECIMAL(15,2) AS Bunga9, 
            SUM(CASE WHEN T07.KdJnsP=l_Kolom10 THEN T07.BayBunga ELSE 0 END) :: DECIMAL(15,2) AS Bunga10 
    FROM T07BAYP T07
    INNER JOIN M15PEGA M15 ON T07.NIP=M15.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)
               ) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (T07.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
          (T07.TgDoku BETWEEN l_PeriodeFr AND l_PeriodeTo)
    GROUP BY T07.NIP, M15.Nama ;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_PEPPP1  (	l_PeriodeFr	DATE,
                        l_PeriodeTo     DATE,
                        l_NIPFr         VARCHAR(10),
                        l_NIPTo         VARCHAR(10),
                        l_Kolom1		VARCHAR(4),
                        l_Kolom2		VARCHAR(4),
                        l_Kolom3		VARCHAR(4),
                        l_Kolom4		VARCHAR(4),
                        l_Kolom5		VARCHAR(4),
                        l_Kolom6        VARCHAR(4),
                        l_Kolom7		VARCHAR(4),
                        l_Kolom8		VARCHAR(4),
                        l_Kolom9		VARCHAR(4),
                        l_Kolom10       VARCHAR(4),
                        l_UserId        INT)

select * from R_PEPPP1('2013-01-01','2013-12-31', ' ', 'ZZZ',
                        ' ', 'A', 'MEDA','01A', 'a999','DENI','JNS1', 'JNS2', 'JNS3', 'JNS4', 1)
*/



