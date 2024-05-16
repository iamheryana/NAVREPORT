/****************************************
Name sprocs	: L_CLOSP3
Create by	: Frans
Date		: 26-11-2007
Description	: Proses Tutup Bulan
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE FUNCTION L_CLOSP3(l_Periode  DATE,
                                    l_UserId   INT)
--
RETURNS TABLE (
		    NIP         VARCHAR(10) ,
		    Nama        VARCHAR(25) ,
		    KdCaba      VARCHAR(4) ,
		    NmCaba      VARCHAR(20) ,
		    Akhir       VARCHAR(1) ,
            TglPayr     DATE,
            TglKeluar   DATE,
            Created_By  VARCHAR(15),
            Created_On  TIMESTAMP,
            Updated_By  VARCHAR(15),
            Updated_On  TIMESTAMP)
--
AS $$
--
BEGIN
    RETURN QUERY
    SELECT TBL.NIP,TBL.Nama,TBL.KdCaba,TBL.NmCaba,
           CASE WHEN mValid='H' THEN '1' ELSE '0' END :: VARCHAR(1) AS Akhir,
           TBL.TglPayr, TBL.TglKeluar, TBL.created_by, TBL.created_on, TBL.updated_by, TBL.updated_on
    FROM
    (SELECT M15.NIP, M15.Nama, M15.KdCaba, SUBSTRING(M08.NmCaba,1,20) :: VARCHAR(20) AS NmCaba,
            FN_FILTCLOS(M15.NIP, l_Periode, M10.Harian, M15.TglPayr) :: VARCHAR(1) AS mValid,
            M15.TglPayr, M15.TglKeluar, M15.created_by, M15.created_on, M15.updated_by, M15.updated_on
     FROM M15PEGA M15
     INNER JOIN M08HCAB M08 ON M08.KdCaba=M15.KdCaba
     INNER JOIN M10KLAS M10 ON M10.Kode=M15.KdKlas 
     INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                 FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
     WHERE TO_CHAR(M15.TglMasuk,'YYYYMM')<=TO_CHAR(l_Periode,'YYYYMM') AND 
           (M15.TglKeluar IS NULL OR TO_CHAR(M15.TglKeluar,'YYYYMM')>TO_CHAR(l_Periode,'YYYYMM')) 
    )AS TBL
    --
    WHERE TBL.mValid<>'';
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION L_CLOSP3(l_Periode  DATE,
                         l_UserId   INT)

select * from L_CLOSP3('2013-12-01', 1)
*/
