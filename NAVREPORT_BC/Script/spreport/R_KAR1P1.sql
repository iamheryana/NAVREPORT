/****************************************
Name sprocs	: R_KAR1P1
Create by	: Deni
Date		: 02-10-2001
Description	: Proses Untuk Report Kartu Slip cabang+Jabatan
*****************************************/
DROP FUNCTION R_KAR1P1(l_PrdFr 	DATE,
                        l_PrdTo     DATE,
                        l_JabFr     VARCHAR(4),
                        l_JabTo     VARCHAR(4),
                        l_FCab      VARCHAR(4),
                        l_TCab      VARCHAR(4),
                        l_Mypass    VARCHAR(128),
                        l_UserId    INT);
--
CREATE FUNCTION R_KAR1P1(l_PrdFr 	DATE,
                        l_PrdTo     DATE,
                        l_JabFr     VARCHAR(4),
                        l_JabTo     VARCHAR(4),
                        l_FCab      VARCHAR(4),
                        l_TCab      VARCHAR(4),
                        l_Mypass    VARCHAR(128),
                        l_UserId    INT)
--
RETURNS TABLE (
		kdCabang	VARCHAR(4)   ,
		NmCabang	VARCHAR(20)  ,
		KdJaba		VARCHAR(4)   ,
		NmJaba		VARCHAR(20)  ,
		FlgDppt     VARCHAR(1)   ,
		NoFormat	DECIMAL(3,0),
		Keterangan	VARCHAR(20)  ,
		Nilai		DECIMAL(15,2))
--
AS $$
--
BEGIN
    RETURN QUERY
    SELECT Q1.kdCaba, Q1.NmCaba, Q1.KdJaba, Q1.NmJaba, Q1.FlgDpPt, Q1.NomFormat, Q1.Keterangan, 
           SUM(Q1.Nilai) AS Nilai
    FROM
    (--Q1
    SELECT  S01.kdCaba, M08.NmCaba, S01.KdJaba, M04.Keterangan AS NmJaba, S02.FlgDpPt, M19.NomFormat, M19.Keterangan, 
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai
    FROM M19HSLG M19
    INNER JOIN M20DSLG M20 ON M19.TipeLap=M20.TipeLap AND M19.FlgDpPt=M20.FlgDpPt AND M19.NomFormat=M20.NomFormat
    INNER JOIN S02DGAJ S02 ON S02.FlgDpPt=M19.FlgDpPt AND S02.KdDpPt=M20.KdDpPt AND S02.FlgAngs=M20.FlgAngs
    INNER JOIN S01HGAJ S01 ON S02.TglPayr=S01.TglPayr AND S02.NIP=S01.NIP
    INNER JOIN M08HCAB M08 ON M08.KdCaba=S01.kdCaba
    INNER JOIN M04HJAB M04 ON M04.Kode =S01.kdJaba
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE M19.TipeLap='3' AND
          (S01.kdCaba BETWEEN l_FCab AND l_TCab) AND
          (S01.KdJaba BETWEEN l_JabFr AND l_JabTo) AND		
          (S01.TglPayr BETWEEN l_PrdFr AND l_PrdTo)
    ) Q1      
    GROUP BY Q1.kdCaba, Q1.NmCaba, Q1.KdJaba, Q1.NmJaba, Q1.FlgDpPt, Q1.NomFormat, Q1.Keterangan;
END;
--
$$ LANGUAGE plpgsql ;
--

/*
CREATE FUNCTION R_KAR1P1(l_PrdFr 	DATE,
                        l_PrdTo     DATE,
                        l_JabFr     VARCHAR(4),
                        l_JabTo     VARCHAR(4),
                        l_FCab      VARCHAR(4),
                        l_TCab      VARCHAR(4),
                        l_Mypass    VARCHAR(128),
                        l_UserId    INT)

select * from R_KAR1P1('2013-01-01','2013-12-31',' ','ZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)

*/