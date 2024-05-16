 /****************************************
Name sprocs	: R_LEMNP2
Create by	: Deni
Date		: 06-09-2002
Description	: Laporan Lembur per NIP
Call From	: lemnS1
*****************************************/
DROP FUNCTION  R_LEMNP2(l_TglFr	DATE,
				     l_TglTo	DATE,	
				     l_NIPFr	VARCHAR(10),
				     l_NIPTo	VARCHAR(10),
                     l_JnsLap   VARCHAR(1),
                     l_hrkerja  INT,
                     l_UserId   INT);
--
CREATE FUNCTION  R_LEMNP2(l_TglFr	DATE,
				     l_TglTo	DATE,	
				     l_NIPFr	VARCHAR(10),
				     l_NIPTo	VARCHAR(10),
                     l_JnsLap   VARCHAR(1),
		     l_hrkerja  INT,
                     l_UserId   INT)
--
RETURNS TABLE (OUTNIP       VARCHAR(10),
		OUTTanggal          DATE,
		OUTKdArea           VARCHAR(4),
		OUTKdCaba           VARCHAR(4),
		OUTKdUUsa           VARCHAR(4),
		OUTKdUker           VARCHAR(8),
		OUTKdJaba           VARCHAR(4),
		OUTKdGlng           VARCHAR(4),
		OUTNama             VARCHAR(25),
		OUTNmArea           VARCHAR(20),
		OUTNmCaba           VARCHAR(20),
		OUTNmUUsa           VARCHAR(20),
		OUTNmUker           VARCHAR(20),
		OUTNmJaba           VARCHAR(20),
		OUTNmGlng           VARCHAR(20),
		OUTLembK1           DECIMAL(15,2),
		OUTLembK2           DECIMAL(15,2),
		OUTLembL1           DECIMAL(15,2),
		OUTLembL2           DECIMAL(15,2),
		OUTLembL3           DECIMAL(15,2),
		OUTTtlLemb          DECIMAL(15,2),
		OUTTtlLbrFor		DECIMAL(15,2),
		OUTSelisih          DECIMAL(15,2),
		OUTNilai            DECIMAL(15,2),
		OUTPerhitungan1 	DECIMAL(15,2),
		OUTPerhitungan2 	DECIMAL(15,2),
		OUTPerhitungan3 	DECIMAL(15,2),
		OUTPendek           VARCHAR(1),
		OUTDasarlembur 		VARCHAR(1),
		OUTLembI1           DECIMAL(15,2),
		OUTLembI2           DECIMAL(15,2))
--
AS $$
--
DECLARE	ml_MaxLembur	DECIMAL(15,2);
        ml_FZ1RateLbr1	DECIMAL(5,2);	
        ml_FZ1RateLbr2	DECIMAL(5,2);
        ml_FZ1RateLbr3	DECIMAL(5,2);	 
        ml_FZ1RateList1	DECIMAL(5,2);	
        ml_FZ1RateList2	DECIMAL(5,2);
        ml_FZ1RateWrk1	DECIMAL(5,2);	
        ml_FZ1RateWrk2	DECIMAL(5,2);
        ml_AkumulasiJam DECIMAL(7,2);   
        ml_OldNIP       VARCHAR(10);
--
BEGIN 
    --
    SELECT  HariKerja, RateLbr1, RateLbr2, RateLbr3, RateWrk1, RateWrk2, RateList1, RateList2
    INTO ml_MaxLembur, ml_FZ1RateLbr1, ml_FZ1RateLbr2, ml_FZ1RateLbr3, ml_FZ1RateWrk1, 
         ml_FZ1RateWrk2, ml_FZ1RateList1, ml_FZ1RateList2
    FROM FZ1FLDA 
    ORDER BY kode LIMIT 1 ; 
    --
    IF l_JnsLap='D' THEN        
        RETURN QUERY
        SELECT 	S04.NIP, S04.TglPayr, S04.KdArea, S04.KdCaba, S04.KdUUsa, S04.KdUker, 
                S04.KdJaba, M15.KdGlng, S04.Nama, M01.Keterangan AS NmArea, M08.NmCaba, M02.Keterangan AS NmUUsa, 
                M17.Keterangan AS NmUker, M04.Singkatan AS NmJaba, M12.KETERANGAN AS KetGolg, 
                S04.LembK1, S04.LembK2, S04.LembL1, S04.LembL2, S04.LembL3, S04.TtlLemb, 
                (S04.LembK1*ml_FZ1RateWrk1)+
                (S04.LembK2*ml_FZ1RateWrk2)+
                (S04.LembL1*ml_FZ1RateLbr1)+
                (S04.LembL2*ml_FZ1RateLbr2)+
                (S04.LembL3*ml_FZ1RateLbr3)+
                (S04.LembI1*ml_FZ1RateList1)+
                (S04.LembI2*ml_FZ1RateList2) :: DECIMAL(15,2) AS TtlKonversi,
                S04.TtlLemb-ml_MaxLembur :: DECIMAL(15,2) AS Selisih,
                S04.Nilai, S04.Perhitungan1, S04.Perhitungan2, S04.Perhitungan3, 
                S04.Pendek, S04.DasarLembur, S04.LembI1, S04.LembI2
        FROM S04LEMB S04 
        INNER JOIN M01AREA M01 ON S04.KdArea=M01.Kode
        INNER JOIN M02UUSA M02 ON S04.KdUUsa=M02.Kode
        INNER JOIN M17UKER M17 ON S04.KdUker=M17.KdUker
        INNER JOIN M08HCAB M08 ON S04.Kdcaba=M08.KdCaba 
        INNER JOIN M04HJAB M04 ON S04.KdJaba=M04.Kode
        INNER JOIN M15PEGA M15 ON S04.NIP=M15.NIP
        INNER JOIN M12HGOL M12 ON M12.KODE=M15.KDGLNG 
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                    FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND S04.KdCaba=VSL.Cab_Kode
        WHERE (S04.TglPayr BETWEEN l_TglFr AND l_TglTo) AND
              (S04.NIP     BETWEEN l_NIPFr AND l_NIPTo) ;
    ELSE      
        RETURN QUERY
        SELECT 	S04.NIP, NULL :: DATE AS TglPayr, S04.KdArea, S04.KdCaba, S04.KdUUsa, S04.KdUker, 
                S04.KdJaba, M15.KdGlng, S04.Nama, M01.Keterangan AS NmArea, M08.NmCaba, M02.Keterangan AS NmUUsa, 
                M17.Keterangan AS NmUker, M04.Singkatan AS NmJaba, M12.KETERANGAN AS KetGolg, 
                SUM(S04.LembK1) :: DECIMAL(15,2) AS LembK1, 
                SUM(S04.LembK2) :: DECIMAL(15,2) AS LembK2,
                SUM(S04.LembL1) :: DECIMAL(15,2) AS LembL1, 
                SUM(S04.LembL2) :: DECIMAL(15,2) AS LembL2, 
                SUM(S04.LembL3) :: DECIMAL(15,2) AS LembL3, 
                SUM(S04.TtlLemb) :: DECIMAL(15,2) AS TtlLemb,
                SUM((S04.LembK1*ml_FZ1RateWrk1)+
                    (S04.LembK2*ml_FZ1RateWrk2)+
                    (S04.LembL1*ml_FZ1RateLbr1)+
                    (S04.LembL2*ml_FZ1RateLbr2)+
                    (S04.LembL3*ml_FZ1RateLbr3)+
                    (S04.LembI1*ml_FZ1RateList1)+
                    (S04.LembI2*ml_FZ1RateList2)) :: DECIMAL(15,2) AS TtlKonversi,
                (CASE WHEN SUM(S04.TtlLemb)-ml_MaxLembur > 0 THEN  SUM(S04.TtlLemb)-ml_MaxLembur ELSE 0 END) :: DECIMAL(15,2) AS Selisih,
                SUM(S04.Nilai) :: DECIMAL(15,2) AS Nilai, 
                SUM(S04.Perhitungan1) :: DECIMAL(15,2) AS Perhitungan1, 
                SUM(S04.Perhitungan2) :: DECIMAL(15,2) AS Perhitungan2, 
                SUM(S04.Perhitungan3) :: DECIMAL(15,2) AS Perhitungan3,
                S04.Pendek, S04.DasarLembur, S04.LembI1, S04.LembI2
        FROM S04LEMB S04 
        INNER JOIN M01AREA M01 ON S04.KdArea=M01.Kode
        INNER JOIN M02UUSA M02 ON S04.KdUUsa=M02.Kode
        INNER JOIN M17UKER M17 ON S04.KdUker=M17.KdUker
        INNER JOIN M08HCAB M08 ON S04.Kdcaba=M08.KdCaba 
        INNER JOIN M04HJAB M04 ON S04.KdJaba=M04.Kode
        INNER JOIN M15PEGA M15 ON S04.NIP=M15.NIP
        INNER JOIN M12HGOL M12 ON M12.KODE=M15.KdGlng    
        INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                    FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND S04.KdCaba=VSL.Cab_Kode
        WHERE (S04.TglPayr BETWEEN l_TglFr AND l_TglTo) AND
              (S04.NIP     BETWEEN l_NIPFr AND l_NIPTo) 
        GROUP BY S04.NIP, S04.KdArea, S04.KdCaba, S04.KdUUsa, S04.KdUker, S04.KdJaba, M15.KdGlng, 
                 S04.Nama, M01.Keterangan, M08.NmCaba, M02.Keterangan, M17.Keterangan, M04.Singkatan, M12.KETERANGAN,
                 ml_MaxLembur, S04.Pendek, S04.DasarLembur, S04.LembI1, S04.LembI2 ;
    END IF ;       
    --
END;
$$ LANGUAGE plpgsql ;
--
       
--
/*
SELECT * FROM R_LEMNP2 ('2012-01-01', '2012-01-31', '', '200601001', 'R',1)
SELECT * FROM R_LEMNP2 ('2012-01-01', '2012-01-31', '','200601001', 'D',1)


*/
