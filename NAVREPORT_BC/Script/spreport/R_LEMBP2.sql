/****************************************
Name sprocs	: R_LEMBP2
Create by	: wati
Date		: 16-09-2003
Description	: Lembur
Call From	: Main Sprocs
*****************************************/
DROP FUNCTION  R_LEMBP2  (l_Periode DATE,
			  l_cabFr   VARCHAR(4), 
		          l_cabTo   VARCHAR(4),
			  l_hrkerja  INT,
                          l_UserId  INT);
--
CREATE FUNCTION  R_LEMBP2  (l_Periode 	DATE, 
			    l_cabFr 	VARCHAR(4), 
			    l_cabTo 	VARCHAR(4),
			    l_hrkerja  INT,
                            l_UserId  	INT)
--                        
RETURNS TABLE(OUTNIP	VARCHAR(10),
		OUTNama         VARCHAR(25),
		OUTTglPayr      DATE,
		OUTKdArea       VARCHAR(4),
		OUTNmArea       VARCHAR(20),
		OUTKdCaba       VARCHAR(4),
		OUTNmCaba       VARCHAR(20),
		OUTKdUUsa       VARCHAR(4),
		OUTNmUUsa       VARCHAR(20),
		OUTLembK1       DECIMAL(15,2),
		OUTLembK2       DECIMAL(15,2),
		OUTKdUker       VARCHAR(8),
		OUTNmUker       VARCHAR(20),
		OUTKdJaba       VARCHAR(4),
		OUTNmJaba       VARCHAR(20),
		OUTPendek       VARCHAR(1),
		OUTLembL1       DECIMAL(15,2),
		OUTLembL2       DECIMAL(15,2),
		OUTLembL3       DECIMAL(15,2),
		OUTTtlLemb      DECIMAL(15,2),
		OUTNilai        DECIMAL(15,2),
		OUTSelisihLemb	DECIMAL(15,2),
		OUTJamKonversi	DECIMAL(15,2),
		OUTTetap        DECIMAL(15,2),
		OUTVariable     DECIMAL(15,2),
		OUTUMR          DECIMAL(15,2),
		OUTDasarlembur 	VARCHAR(1),
		OUTLembI1       DECIMAL(15,2),
		OUTLembI2       DECIMAL(15,2))
AS $$
--
DECLARE l_MaxJamLbr     DECIMAL(4,0);
        l_FZ1RateLbr1	DECIMAL(5,2);	
        l_FZ1RateLbr2	DECIMAL(5,2);
        l_FZ1RateLbr3	DECIMAL(5,2);	 
        l_FZ1RateWrk1	DECIMAL(5,2);	
        l_FZ1RateWrk2	DECIMAL(5,2);
        l_FZ1RateList1	DECIMAL(5,2);
        l_FZ1RateList2	DECIMAL(5,2);
--
BEGIN 
    SELECT HariKerja, RateLbr1,RateLbr2, RateLbr3, RateWrk1, RateWrk2, RateList1, RateList2
    INTO l_MaxJamLbr, l_FZ1RateLbr1, l_FZ1RateLbr2, l_FZ1RateLbr3, l_FZ1RateWrk1, l_FZ1RateWrk2, 
         l_FZ1RateList1, l_FZ1RateList2
    FROM FZ1FLDA
    ORDER BY kode LIMIT 1 ; 
    --
    RETURN QUERY
    SELECT  TBL.NIP, TBL.Nama, TBL.TglPayr, TBL.KdArea, M01.Keterangan AS KetArea, TBL.KdCaba, M08.NmCaba, 
            TBL.KdUUsa, M02.Keterangan AS KetUUsa, TBL.LembK1, TBL.LembK2, TBL.KdUKer, M17.Keterangan AS KetUker,
            TBL.KdJaba, M04.Singkatan AS KetJaba, TBL.Pendek,
            TBL.LembL1+TBL.LembI1 AS LembL1,
            TBL.LembL2+TBL.LembI2 AS LembL2,
            TBL.LembL3, TBL.TtlLemb, TBL.Nilai,
            (CASE WHEN TBL1.AkmLemb>=l_MaxJamLbr THEN TBL1.AkmLemb-l_MaxJamLbr ELSE 0 END) AS SelisihLemb,
            (TBL.LembK1*l_FZ1RateWrk1)+
            (TBL.LembK2*l_FZ1RateWrk2)+
            (TBL.LembL1*l_FZ1RateLbr1)+
            (TBL.LembL2*l_FZ1RateLbr2)+
            (TBL.LembL3*l_FZ1RateLbr3)+
            (TBL.LembI1*l_FZ1RateList1)+
            (TBL.LembI2*l_FZ1RateList2) AS JamKonversi,
            TBL.HitTetap, TBL.HitVariable, TBL.HitUMR, TBL.DasarLembur, TBL.LembI1, TBL.LembI2         
    FROM 
    (SELECT S04.NIP, S04.Nama, S04.TglPayr, S04.KdArea, S04.KdCaba, S04.KdUUsa, S04.KdUker,
            S04.KdJaba, S04.Pendek, S04.DasarLembur,
            SUM(S04.LembK1) AS LembK1,
            SUM(S04.LembK2) AS LembK2,
            SUM(S04.LembL1) AS LembL1,
            SUM(S04.LembL2) AS LembL2,
            SUM(S04.LembL3) AS LembL3, 
            SUM(S04.LembI1) AS LembI1,
            SUM(S04.LembI2) AS LembI2,
            SUM(S04.TtlLemb) AS TtlLemb,
            SUM(S04.Nilai) AS Nilai,
            SUM(S04.Perhitungan1) AS HitTetap,
            SUM(S04.Perhitungan2) AS HitVariable,
            SUM(S04.Perhitungan3) AS HitUMR
    FROM  S04LEMB S04
    INNER JOIN M15PEGA M15 ON S04.NIP=M15.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND S04.KdCaba=VSL.Cab_Kode
    WHERE TO_CHAR(S04.TglPayr,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM') AND (S04.KdCaba BETWEEN l_cabFr AND l_cabTo)
    GROUP BY S04.NIP, S04.Nama, S04.TglPayr, S04.KdArea, S04.KdCaba, S04.KdUUsa, S04.KdUker,
            S04.KdJaba, S04.Pendek,  S04.DasarLembur
    ) TBL
    INNER JOIN M01AREA M01 ON TBL.KdArea = M01.Kode
    INNER JOIN M02UUSA M02 ON TBL.KdUUsa = M02.Kode
    INNER JOIN M17UKER M17 ON TBL.KdUker = M17.KdUker
    INNER JOIN M08HCAB M08 ON TBL.Kdcaba = M08.KdCaba 
    INNER JOIN M04HJAB M04 ON TBL.KdJaba = M04.Kode
    --Left Joint Total Lembur
    LEFT JOIN
    (--TBL1
    SELECT S04.NIP, SUM(S04.TtlLemb) AS AkmLemb
    FROM  S04LEMB S04
    INNER JOIN M15PEGA M15 ON S04.NIP=M15.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND S04.KdCaba=VSL.Cab_Kode
    WHERE TO_CHAR(S04.TglPayr,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM') AND (S04.KdCaba BETWEEN l_cabFr AND l_cabTo)
    GROUP BY S04.NIP
    ) TBL1
    ON TBL1.NIP=TBL.NIP;
    --
END;
--
$$ LANGUAGE plpgsql ;

/******* END OF PROC *****/
/*
SELECT * FROM R_LEMBP2('2012-01-02','','ZZZZ',1) 
*/




