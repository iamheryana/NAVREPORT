/**************************************************************
Name sprocs	: R_ARS4P1
Create by	: Deni
Date		: 12-02-2014
Description	: Arsip Gaji Totalper Unit Usaha (untuk Solpher)
***************************************************************/
DROP FUNCTION R_ARS4P1(l_Periode	DATE,
                       l_NIPFr      VARCHAR(10),
                       l_NIPTo      VARCHAR(10),
                       l_UUsaFr     VARCHAR(4),
                       l_UUsaTo     VARCHAR(4),
                       l_Mypass     VARCHAR(128),
                       l_UserId     INT) ;
--
CREATE FUNCTION R_ARS4P1(l_Periode	DATE,
                       l_NIPFr      VARCHAR(10),
                       l_NIPTo      VARCHAR(10),
                       l_UUsaFr     VARCHAR(4),
                       l_UUsaTo     VARCHAR(4),
                       l_Mypass     VARCHAR(128),
                       l_UserId     INT)
--
RETURNS TABLE (
            Tahun 	VARCHAR(4) ,
		    Bulan	VARCHAR(2) ,
		    NIP		VARCHAR(10) ,
		    Nama	VARCHAR(25) ,
		    KdUUsa	VARCHAR(4) ,
		    NmUUsa	VARCHAR(20) ,
		    Dpt01	DECIMAL(15,2),
		    Dpt02	DECIMAL(15,2),
		    Dpt03	DECIMAL(15,2),
		    Dpt04	DECIMAL(15,2),
		    Dpt05	DECIMAL(15,2),
		    Dpt06	DECIMAL(15,2),
		    Dpt07	DECIMAL(15,2),
		    Pot01	DECIMAL(15,2),
		    Pot02	DECIMAL(15,2),
		    Pot03	DECIMAL(15,2),
			Pot04	DECIMAL(15,2),
		    Pot05	DECIMAL(15,2),
            Pendapatan1 VARCHAR(20) ,
            Pendapatan2 VARCHAR(20) ,
            Pendapatan3 VARCHAR(20) ,
            Pendapatan4 VARCHAR(20) ,
            Pendapatan5 VARCHAR(20) ,
            Pendapatan6 VARCHAR(20) ,
            Pendapatan7 VARCHAR(20) ,
            Potongan1   VARCHAR(20) ,
            Potongan2   VARCHAR(20) ,
            Potongan3   VARCHAR(20) ,
            Potongan4   VARCHAR(20) ,
            Potongan5   VARCHAR(20))
--
AS $$
--
BEGIN
    --
    CREATE TEMP TABLE l_TEMP1(
                FlDppt          VARCHAR(1),
                NoUrut          INT,
                Keterangan      VARCHAR(20)
                ) ON COMMIT DROP ;      
    
    INSERT INTO l_TEMP1(FlDppt, NoUrut, Keterangan)
    SELECT Q2.flgdppt, Q2.NoUrut, Q2.keterangan
    FROM
    (--Q2
    SELECT Q1.flgdppt, Q1.NoUrut, Q1.keterangan
    FROM
    (--Q1
    select M19.flgdppt, row_number() over (ORDER BY M19.nomformat) as NoUrut, M19.nomformat, M19.keterangan
    from m19hslg M19
    where M19.tipelap='2' and M19.flgdppt='D'
    ) Q1
    WHERE Q1.NoUrut <= 7
    ---------
    UNION ALL
    ---------
    SELECT Q0.flgdppt, Q0.NoUrut, Q0.keterangan
    FROM
    (--Q0
    select M19.flgdppt, row_number() over (ORDER BY M19.nomformat) as NoUrut, M19.nomformat, M19.keterangan
    from m19hslg M19
    where M19.tipelap='2' and M19.flgdppt='P'
    ) Q0
    WHERE Q0.NoUrut <= 5
    ) Q2 ;
    --
    RETURN QUERY
    SELECT  Q1.Tahun, Q1.Bulan, Q1.NIP, Q1.Nama, Q1.KdUUsa, Q1.KetUsaha,  
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=1 THEN Q1.Nilai ELSE 0 END) Dpt01,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=2 THEN Q1.Nilai ELSE 0 END) Dpt02,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=3 THEN Q1.Nilai ELSE 0 END) Dpt03,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=4 THEN Q1.Nilai ELSE 0 END) Dpt04,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=5 THEN Q1.Nilai ELSE 0 END) Dpt05,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=6 THEN Q1.Nilai ELSE 0 END) Dpt06,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut>6 THEN Q1.Nilai ELSE 0 END) Dpt07,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=1 THEN Q1.Nilai ELSE 0 END) Pot01,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=2 THEN Q1.Nilai ELSE 0 END) Pot02,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=3 THEN Q1.Nilai ELSE 0 END) Pot03,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=4 THEN Q1.Nilai ELSE 0 END) Pot04,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut>4 THEN Q1.Nilai ELSE 0 END) Pot05 ,        
            Q1.Pendapatan1, Q1.Pendapatan2, Q1.Pendapatan3, Q1.Pendapatan4, Q1.Pendapatan5, Q1.Pendapatan6, 
            Q1.Pendapatan7, Q1.Potongan1, Q1.Potongan2, Q1.Potongan3, Q1.Potongan4, Q1.Potongan5
    FROM
    (--Q1                   
    SELECT 	TO_CHAR(S01.TglPayr,'YYYY') :: VARCHAR(4) AS Tahun, 
            TO_CHAR(S01.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            S01.NIP, S01.Nama, S01.KdUUsa, M02.Keterangan AS KetUsaha, M19.FlgDpPt, M19.NomFormat,
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai,
            COALESCE(Fn_GetUrut(M19.FlgDpPt, TO_CHAR(M19.NomFormat,'999')) :: DECIMAL(3,0),0) AS NoUrut,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=1) as Pendapatan1,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=2) as Pendapatan2,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=3) as Pendapatan3,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=4) as Pendapatan4,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=5) as Pendapatan5,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=6) as Pendapatan6,
            'Lain-Lain' ::  VARCHAR(20) as Pendapatan7,
            --COALESCE((select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=7),'Lain-Lain') as Pendapatan7,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=1) as Potongan1,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=2) as Potongan2,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=3) as Potongan3,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=4) as Potongan4,
            'Lain-lain' ::  VARCHAR(20) as Potongan5
            --(select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=5) as Potongan5
    FROM M19HSLG M19 
	INNER JOIN M20DSLG M20 ON M20.TipeLap=M19.TipeLap AND M20.FlgDpPt=M19.FlgDpPt AND M20.NomFormat=M19.NomFormat
    INNER JOIN S02DGAJ S02 ON S02.FlgDpPt=M19.FlgDpPt AND S02.KdDpPt=M20.KdDpPt AND S02.FlgAngs=M20.FlgAngs   
    INNER JOIN S01HGAJ S01 ON S01.TglPayr=S02.TglPayr AND S01.NIP=S02.NIP 
    INNER JOIN M02UUSA M02 ON M02.Kode=S01.KdUUsa
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE TO_CHAR(S01.TglPayr,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM') AND M19.TipeLap='2' AND
          (S01.NIP    BETWEEN l_NIPFr AND l_NIPTo) AND 
          (S01.KdUUsa BETWEEN l_UUsaFr AND l_UUsaTo)
    ) Q1
    GROUP BY Q1.Tahun, Q1.Bulan, Q1.NIP, Q1.Nama, Q1.KdUUsa, Q1.KetUsaha,
             Q1.Pendapatan1, Q1.Pendapatan2, Q1.Pendapatan3, Q1.Pendapatan4, Q1.Pendapatan5, Q1.Pendapatan6, 
             Q1.Pendapatan7, Q1.Potongan1, Q1.Potongan2, Q1.Potongan3, Q1.Potongan4, Q1.Potongan5 ;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_ARS4P1(l_Periode	DATE,
                       l_NIPFr      VARCHAR(10),
                       l_NIPTo      VARCHAR(10),
                       l_UUsaFr     VARCHAR(4),
                       l_UUsaTo     VARCHAR(4),
                       l_Mypass     VARCHAR(128),
                       l_UserId     INT)

select * from R_ARS4P1('2013-12-01',' ','ZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)
*/

