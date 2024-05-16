/****************************************
Name sprocs	: R_ARUCP1
Create by	: Deni
Date		: 03-09-2002
Description	: Arsip Gaji per Unit Usaha dan Cabang
Call From	: arucS1
*****************************************/
DROP FUNCTION R_ARUCP1(l_Tanggal	DATE,
                       l_NIPFr      VARCHAR(10),
                       l_NIPTo      VARCHAR(10),
                       l_UUsaFr     VARCHAR(4),
                       l_UUsaTo     VARCHAR(4),
                       l_Mypass     VARCHAR(128),
                       l_UserId     INT);
--
CREATE FUNCTION R_ARUCP1(l_Tanggal	DATE,
                       l_NIPFr      VARCHAR(10),
                       l_NIPTo      VARCHAR(10),
                       l_UUsaFr     VARCHAR(4),
                       l_UUsaTo     VARCHAR(4),
                       l_Mypass     VARCHAR(128),
                       l_UserId     INT)
--
RETURNS TABLE (
        Tahun		VARCHAR(4) ,
        Bulan		VARCHAR(2) ,
        NIP         VARCHAR(10) ,
        Nama		VARCHAR(25) ,
        KdUUsa      VARCHAR(4),
        NmUUsa      VARCHAR(20),
        kdCaba      VARCHAR(4),
        SkCaba      VARCHAR(20),
        GLng        VARCHAR(4),
        JnsKlmn     VARCHAR(1),
        TotAnak     INT,
        StsMed      VARCHAR(2),	
        Dpt01       DECIMAL(15,2),
        Dpt02       DECIMAL(15,2),
        Dpt03       DECIMAL(15,2),
        Dpt04       DECIMAL(15,2),
        Dpt05       DECIMAL(15,2),
        Dpt06       DECIMAL(15,2),
        Dpt07       DECIMAL(15,2),
        Dpt08       DECIMAL(15,2),
        Dpt09       DECIMAL(15,2),
        Dpt10       DECIMAL(15,2),
        Dpt11       DECIMAL(15,2),
        Dpt12       DECIMAL(15,2),
        Dpt13       DECIMAL(15,2),
        Dpt14       DECIMAL(15,2),
        Dpt15       DECIMAL(15,2),
        Dpt16       DECIMAL(15,2),
        Pot01       DECIMAL(15,2),
        Pot02       DECIMAL(15,2),
        Pot03       DECIMAL(15,2),
        Pot04       DECIMAL(15,2),
        Pot05       DECIMAL(15,2),
        Pot06       DECIMAL(15,2),
        Pot07       DECIMAL(15,2),
        Pot08       DECIMAL(15,2),
        Pot09       DECIMAL(15,2),
        Pot10       DECIMAL(15,2),
        Pot11       DECIMAL(15,2),
        Pot12       DECIMAL(15,2),
        Pot13       DECIMAL(15,2),
        Pot14       DECIMAL(15,2),
        Pot15       DECIMAL(15,2),
        Pot16       DECIMAL(15,2),
        Pendapatan1 VARCHAR(20) ,
        Pendapatan2 VARCHAR(20) ,
        Pendapatan3 VARCHAR(20) ,
        Pendapatan4 VARCHAR(20) ,
        Pendapatan5 VARCHAR(20) ,
        Pendapatan6 VARCHAR(20) ,
        Pendapatan7 VARCHAR(20) ,
        Pendapatan8 VARCHAR(20) ,
        Pendapatan9 VARCHAR(20) ,
        Pendapatan10 VARCHAR(20) ,
        Pendapatan11 VARCHAR(20) ,
        Pendapatan12 VARCHAR(20) ,
        Pendapatan13 VARCHAR(20) ,
        Pendapatan14 VARCHAR(20) ,
        Pendapatan15 VARCHAR(20) ,
        Pendapatan16 VARCHAR(20) ,
        Potongan1   VARCHAR(20) ,
        Potongan2   VARCHAR(20) ,
        Potongan3   VARCHAR(20) ,
        Potongan4   VARCHAR(20) ,
        Potongan5   VARCHAR(20) ,
        Potongan6   VARCHAR(20) ,
        Potongan7   VARCHAR(20) ,
        Potongan8   VARCHAR(20) ,
        Potongan9   VARCHAR(20) ,
        Potongan10  VARCHAR(20) ,
        Potongan11  VARCHAR(20) ,
        Potongan12  VARCHAR(20) ,
        Potongan13  VARCHAR(20) ,
        Potongan14  VARCHAR(20) ,
        Potongan15  VARCHAR(20) ,
        Potongan16  VARCHAR(20))
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
    WHERE Q1.NoUrut <= 16
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
    WHERE Q0.NoUrut <= 16
    ) Q2 ;
    --
    RETURN QUERY
    SELECT  Q1.Tahun, Q1.Bulan, Q1.NIP, Q1.Nama, Q1.KdUUsa, Q1.KetUsaha, Q1.KdCaba, Q1.SkCaba, 
            Q1.KdGlng, Q1.JnsKlmn, Q1. TotAnak, Q1.StsPjk,
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=1 THEN Q1.Nilai ELSE 0 END) Dpt01,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=2 THEN Q1.Nilai ELSE 0 END) Dpt02,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=3 THEN Q1.Nilai ELSE 0 END) Dpt03,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=4 THEN Q1.Nilai ELSE 0 END) Dpt04,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=5 THEN Q1.Nilai ELSE 0 END) Dpt05,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=6 THEN Q1.Nilai ELSE 0 END) Dpt06,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=7 THEN Q1.Nilai ELSE 0 END) Dpt07,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=8 THEN Q1.Nilai ELSE 0 END) Dpt08,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=9 THEN Q1.Nilai ELSE 0 END) Dpt09,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=10 THEN Q1.Nilai ELSE 0 END) Dpt10,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=11 THEN Q1.Nilai ELSE 0 END) Dpt11,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=12 THEN Q1.Nilai ELSE 0 END) Dpt12,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=13 THEN Q1.Nilai ELSE 0 END) Dpt13,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=14 THEN Q1.Nilai ELSE 0 END) Dpt14,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut=15 THEN Q1.Nilai ELSE 0 END) Dpt15,            
            SUM(CASE WHEN Q1.FlgDpPt='D' AND Q1.NoUrut>15 THEN Q1.Nilai ELSE 0 END) Dpt16,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=1 THEN Q1.Nilai ELSE 0 END) Pot01,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=2 THEN Q1.Nilai ELSE 0 END) Pot02,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=3 THEN Q1.Nilai ELSE 0 END) Pot03,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=4 THEN Q1.Nilai ELSE 0 END) Pot04,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=5 THEN Q1.Nilai ELSE 0 END) Pot05,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=6 THEN Q1.Nilai ELSE 0 END) Pot06,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=7 THEN Q1.Nilai ELSE 0 END) Pot07,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=8 THEN Q1.Nilai ELSE 0 END) Pot08,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=9 THEN Q1.Nilai ELSE 0 END) Pot09,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=10 THEN Q1.Nilai ELSE 0 END) Pot10,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=11 THEN Q1.Nilai ELSE 0 END) Pot11,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=12 THEN Q1.Nilai ELSE 0 END) Pot12,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=13 THEN Q1.Nilai ELSE 0 END) Pot13,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=14 THEN Q1.Nilai ELSE 0 END) Pot14,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut=15 THEN Q1.Nilai ELSE 0 END) Pot15,            
            SUM(CASE WHEN Q1.FlgDpPt='P' AND Q1.NoUrut>16 THEN Q1.Nilai ELSE 0 END) Pot16,         
            Q1.Pendapatan1, Q1.Pendapatan2, Q1.Pendapatan3, Q1.Pendapatan4, Q1.Pendapatan5, Q1.Pendapatan6, 
            Q1.Pendapatan7, Q1.Pendapatan8, Q1.Pendapatan9, Q1.Pendapatan10, Q1.Pendapatan11, Q1.Pendapatan12, 
            Q1.Pendapatan13,Q1.Pendapatan14,Q1.Pendapatan15,Q1.Pendapatan16,
            Q1.Potongan1, Q1.Potongan2, Q1.Potongan3, Q1.Potongan4, Q1.Potongan5, Q1.Potongan6,
            Q1.Potongan7, Q1.Potongan8, Q1.Potongan9, Q1.Potongan10, Q1.Potongan11, Q1.Potongan12, 
            Q1.Potongan13, Q1.Potongan14, Q1.Potongan15, Q1.Potongan16    FROM
    (--Q1                   
    SELECT 	TO_CHAR(S01.TglPayr,'YYYY') :: VARCHAR(4) AS Tahun, 
            TO_CHAR(S01.TglPayr,'MM') :: VARCHAR(2) AS Bulan, 
            S01.NIP, S01.Nama, S01.KdUUsa, M02.Keterangan AS KetUsaha, S01.KdCaba, M08.SkCaba, 
            S01.KdGlng, S01.JnsKlmn, S01. TotAnak, S01.StsPjk, M19.FlgDpPt,
            COALESCE(Fn_Kpusat(S02.NIP, S02.EncNilai, l_Mypass) :: DECIMAL(15,2),0) AS Nilai,
            COALESCE(Fn_GetUrut(M19.FlgDpPt, TO_CHAR(M19.NomFormat,'999')) :: DECIMAL(3,0),0) AS NoUrut,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=1) as Pendapatan1,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=2) as Pendapatan2,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=3) as Pendapatan3,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=4) as Pendapatan4,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=5) as Pendapatan5,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=6) as Pendapatan6,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=7) as Pendapatan7,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=8) as Pendapatan8,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=9) as Pendapatan9,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=10) as Pendapatan10,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=11) as Pendapatan11,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=12) as Pendapatan12,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=13) as Pendapatan13,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=14) as Pendapatan14,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=15) as Pendapatan15,
            COALESCE((select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='D' and TMP.nourut=16),'Tunj. Lain-Lain') as Pendapatan16,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=1) as Potongan1,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=2) as Potongan2,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=3) as Potongan3,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=4) as Potongan4,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=5) as Potongan5,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=6) as Potongan6,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=7) as Potongan7,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=8) as Potongan8,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=9) as Potongan9,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=10) as Potongan10,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=11) as Potongan11,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=12) as Potongan12,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=13) as Potongan13,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=14) as Potongan14,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=15) as Potongan15,
            (select TMP.keterangan from l_TEMP1 TMP where TMP.fldppt='P' and TMP.nourut=16) as Potongan16    
    FROM S01HGAJ S01
    INNER JOIN M08HCAB M08 ON S01.KdCaba=M08.KdCaba
    INNER JOIN M02UUSA M02 ON M02.Kode=S01.KdUUsa
    INNER JOIN S02DGAJ S02 ON S02.TglPayr=S01.TglPayr AND S02.NIP=S01.NIP 
    INNER JOIN M20DSLG M20 ON S02.FlgDpPt=M20.FlgDpPt AND S02.KdDpPt=M20.KdDpPt AND S02.FlgAngs=M20.FlgAngs
    INNER JOIN M19HSLG M19 ON M19.TipeLap=M20.TipeLap AND M19.FlgDpPt=M20.FlgDpPt AND M19.NomFormat=M20.NomFormat
    INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE M19.TipeLap='2' AND 
          S01.NIP	BETWEEN l_NIPFr	AND l_NIPTo AND
          S01.KdUUsa BETWEEN l_UUsaFr AND l_UUsaTo AND
          TO_CHAR(S01.TglPayr,'YYYYMM')=TO_CHAR(l_Tanggal,'YYYYMM')
    ) Q1
    GROUP BY Q1.Tahun, Q1.Bulan, Q1.NIP, Q1.Nama, Q1.KdUUsa, Q1.KetUsaha, Q1.KdCaba, Q1.SkCaba, 
             Q1.KdGlng, Q1.JnsKlmn, Q1. TotAnak, Q1.StsPjk,
             Q1.Pendapatan1, Q1.Pendapatan2, Q1.Pendapatan3, Q1.Pendapatan4, Q1.Pendapatan5, Q1.Pendapatan6, 
             Q1.Pendapatan7, Q1.Pendapatan8, Q1.Pendapatan9, Q1.Pendapatan10, Q1.Pendapatan11, Q1.Pendapatan12, 
             Q1.Pendapatan13,Q1.Pendapatan14,Q1.Pendapatan15,Q1.Pendapatan16,
             Q1.Potongan1, Q1.Potongan2, Q1.Potongan3, Q1.Potongan4, Q1.Potongan5, Q1.Potongan6,
             Q1.Potongan7, Q1.Potongan8, Q1.Potongan9, Q1.Potongan10, Q1.Potongan11, Q1.Potongan12, 
             Q1.Potongan13, Q1.Potongan14, Q1.Potongan15, Q1.Potongan16 ;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/* 
CREATE FUNCTION R_ARUCP1(l_Tanggal	DATE,
                       l_NIPFr      VARCHAR(10),
                       l_NIPTo      VARCHAR(10),
                       l_UUsaFr     VARCHAR(4),
                       l_UUsaTo     VARCHAR(4),
                       l_Mypass     VARCHAR(128),
                       l_UserId     INT)

select * from R_ARUCP1('2013-12-01',' ','ZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1)

*/






