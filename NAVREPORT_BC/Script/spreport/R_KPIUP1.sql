/********************************************
Name sprocs	: R_KPIUP1
Create by	: Deni
Date		: 14-02-2014
Description	: Kartu Piutang Pegawai
Call From	: Main Proc
*****************************************/
DROP FUNCTION R_KPIUP1(l_mcNIPFr      VARCHAR(10),
                        l_mcNIPTo       VARCHAR(10),
                        l_mdPeriodeFr	DATE,
                        l_mdPeriodeTo	DATE,
                        l_mcCabaFr      VARCHAR(4),
                        l_mcCabaTo      VARCHAR(4),
                        l_mcUUsaFr      VARCHAR(4),
                        l_mcUUsaTo      VARCHAR(4),
                        l_mcUkerFr      VARCHAR(8),
                        l_mcUkerTo      VARCHAR(8),
                        l_UserId        INT);
--
CREATE FUNCTION R_KPIUP1(l_mcNIPFr      VARCHAR(10),
                        l_mcNIPTo       VARCHAR(10),
                        l_mdPeriodeFr	DATE,
                        l_mdPeriodeTo	DATE,
                        l_mcCabaFr      VARCHAR(4),
                        l_mcCabaTo      VARCHAR(4),
                        l_mcUUsaFr      VARCHAR(4),
                        l_mcUUsaTo      VARCHAR(4),
                        l_mcUkerFr      VARCHAR(8),
                        l_mcUkerTo      VARCHAR(8),
                        l_UserId        INT)
--
RETURNS TABLE (
		OUTNIP              VARCHAR(10),
		OUTNama             VARCHAR(25),
		OUTTglPayr          DATE,
		OUTKdCaba           VARCHAR(4),
		OUTNamaCabang       VARCHAR(40),
		OUTKdUUsa           VARCHAR(4),
		OUTNamaUUsa         VARCHAR(25),
		OUTKdUker           VARCHAR(8),
		OUTNamaUker         VARCHAR(25),
		OUTJnsPiut          VARCHAR(4),
		OUTNamaPiut         VARCHAR(25),
		OUTTglDoku          DATE,
		OUTPiutang          DECIMAL(15,2),
		OUTBunga            DECIMAL(15,2),
		OUTTglBayar         DATE,
		OUTByrPokok         DECIMAL(15,2),
		OUTByrBunga         DECIMAL(15,2),
		OUTComp             VARCHAR(1))
--
AS $$
--
BEGIN
    RETURN QUERY
    SELECT  M15.NIP, M15.Nama, M15.TglPayr, M15.KdCaba, M08.NmCaba, M15.KdUUsa, M02.Keterangan AS KetUUsa,
            M15.KdUKer, M17.Keterangan AS KetUker, T05.KdJnsP, M22.NmPiut, T05.TgDoku, T05.Piutang, T05.Bunga, 
            T07.TgDoku AS TglBay, 
            COALESCE(T07.BayPokok,0) :: DECIMAL(15,2) AS BayPokok,
            COALESCE(T07.BayBunga,0) :: DECIMAL(15,2) AS BayBunga, 
            COALESCE(T07.Comp,' ') :: VARCHAR(1) AS BayComp
    FROM M15PEGA M15                                  
    INNER JOIN M08HCAB M08 ON M15.kdCaba=M08.kdCaba                        
    INNER JOIN M02UUSA M02 ON M15.KdUUsa=M02.Kode               
    INNER JOIN M17UKER M17 ON M15.KdUKer=M17.KdUker        
    INNER JOIN T05HPIU T05 ON M15.NIP=T05.NIP
    INNER JOIN M22JNSP M22 ON T05.KdJnsP=M22.KdJnsP   
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    LEFT JOIN T07BAYP T07 ON T05.NIP=T07.NIP AND T05.KdJnsP=T07.KdJnsP AND T05.TgDoku=T07.TgPiut                         
    WHERE (M15.NIP BETWEEN l_mcNIPFr AND l_mcNIPTo) AND
          (M15.KdCaba BETWEEN l_mcCabaFr AND l_mcCabaTo) AND
          (T05.TgDoku BETWEEN l_mdPeriodeFr AND l_mdPeriodeTo) AND
          (M15.KdUKer BETWEEN l_mcUkerFr AND l_mcUkerTo) AND
          (M15.KdUUsa BETWEEN l_mcUUsaFr AND l_mcUUsaTo) ;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_KPIUP1(l_mcNIPFr      VARCHAR(10),
                        l_mcNIPTo       VARCHAR(10),
                        l_mdPeriodeFr	DATE,
                        l_mdPeriodeTo	DATE,
                        l_mcCabaFr      VARCHAR(4),
                        l_mcCabaTo      VARCHAR(4),
                        l_mcUUsaFr      VARCHAR(4),
                        l_mcUUsaTo      VARCHAR(4),
                        l_mcUkerFr      VARCHAR(8),
                        l_mcUkerTo      VARCHAR(8),
                        l_UserId        INT)

select * from R_KPIUP1(' ','ZZZ', '2013-01-01','2013-12-31', ' ','ZZZ', ' ','ZZZ', ' ','ZZZ', 1)
*/

