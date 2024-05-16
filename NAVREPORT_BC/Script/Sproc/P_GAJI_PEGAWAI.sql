/****************************************
Name sprocs	: P_Gaji_Pegawai
Create by	: Deni
Date		: 05-07-2003
Description	: Proses Gaji Pegawai
*****************************************/
CREATE OR REPLACE FUNCTION P_GAJI_PEGAWAI(
                        l_NIP       VARCHAR(10),
						l_MyPass    VARCHAR(128),
						l_UserID    INT)
--
RETURNS VOID
AS $$
--
BEGIN
    --
    DELETE FROM S08MUTA WHERE kdMutr IN ('', 'AFPR') AND NIP=l_NIP ;
    DELETE FROM T10MUTA WHERE kdMutr IN ('', 'AFPR') AND NIP=l_NIP ;
    --
    INSERT INTO S08MUTA(nip, kdmutr, skmutr, nosk, tgleff, Keterangan, kdklas, skklas, 
            kdcaba, skcaba, kduusa, skuusa, kduker, skuker, kdglng, skglng, kdjaba, skjaba, kdarea, skarea, kdkjab, skkjab, kdcurr, 
            gajipokok, penilaian, masakerja, encgajipokok, keterangan2, keterangan3, keterangan4, keterangan5,
            version, created_by, created_on, updated_by, updated_on)
    (SELECT  M15.NIP, '' :: VARCHAR(4) AS KdMutr, (SELECT Singkatan FROM M24MUTR WHERE KdMutr='') :: VARCHAR(10) AS SkMutr,
            '' :: VARCHAR(25) AS NoSK, M15.PrdAwl AS TglEff,
            '' :: VARCHAR(100) AS Ket1, M15.kdklas, M10.Singkatan,
            M15.KdCaba, M08.SkCaba, M15.KdUUsa, M02.Singkatan, M15.KdUker, M17.Singkatan, M15.KdGlng, M12.Singkatan,
            M15.Kdjaba, M04.Singkatan, M15.KdArea, M01.Singkatan, M15.KdKJab, M06.Singkatan, M15.KdCurr,
            0 :: DECIMAL(15,0) AS Gapok, 'TP' :: VARCHAR(2) AS Penilaian, M15.MasaKerja, M15.encgajiperc,
            '' :: VARCHAR(100) AS Ket2, '' :: VARCHAR(100) AS Ket3, '' :: VARCHAR(100) AS Ket4, '' :: VARCHAR(100) AS Ket5,
            0 :: INT AS Vers, M15.updated_by, CURRENT_DATE :: TIMESTAMP AS Created_On, M15.updated_by, CURRENT_DATE :: TIMESTAMP AS Updated_On
    FROM M15PEGA M15
    INNER JOIN M10KLAS M10 ON M10.Kode=M15.KdKlas
    INNER JOIN M08HCAB M08 ON M08.KdCaba=M15.KdCaba
    INNER JOIN M01AREA M01 ON M01.Kode=M15.KdArea
    INNER JOIN M02UUSA M02 ON M02.Kode=M15.KdUUsa
    INNER JOIN M04HJAB M04 ON M04.Kode=M15.KdJaba
    INNER JOIN M06HKJB M06 ON M06.Kode=M15.KdKJab
    INNER JOIN M17UKER M17 ON M17.KdUker=M15.KdUker
    INNER JOIN M12HGOL M12 ON M12.Kode=M15.KdGlng
    WHERE M15.NIP = l_NIP AND M15.prdawl <> M15.prdtetap AND M15.prdawl IS NOT NULL);
--
    INSERT INTO S08MUTA(nip, kdmutr, skmutr, nosk, tgleff, Keterangan, kdklas, skklas,
            kdcaba, skcaba, kduusa, skuusa, kduker, skuker, kdglng, skglng,
            kdjaba, skjaba, kdarea, skarea, kdkjab, skkjab, kdcurr,
            gajipokok, penilaian, masakerja, encgajipokok, keterangan2, keterangan3, keterangan4, keterangan5,
            version, created_by, created_on, updated_by, updated_on)
    (SELECT  M15.NIP, 'AFPR' :: VARCHAR(4) AS KdMutr, (SELECT Singkatan FROM M24MUTR WHERE KdMutr='AFPR') :: VARCHAR(10) AS SkMutr,
            '' :: VARCHAR(25) AS NoSK, M15.prdtetap AS TglEff, '' :: VARCHAR(100) AS Ket1, M15.kdklas, M10.Singkatan,
            M15.KdCaba, M08.SkCaba, M15.KdUUsa, M02.Singkatan, M15.KdUker, M17.Singkatan, M15.KdGlng, M12.Singkatan,
            M15.Kdjaba, M04.Singkatan, M15.KdArea, M01.Singkatan, M15.KdKJab, M06.Singkatan, M15.KdCurr,
            0 :: DECIMAL(15,0) AS Gapok, 'TP' :: VARCHAR(2) AS Penilaian, M15.MasaKerja, M15.encgajitetap,
            '' :: VARCHAR(100) AS Ket2, '' :: VARCHAR(100) AS Ket3, '' :: VARCHAR(100) AS Ket4, '' :: VARCHAR(100) AS Ket5,
            0 :: INT AS Vers, M15.updated_by, CURRENT_DATE :: TIMESTAMP AS Created_On, M15.updated_by, CURRENT_DATE :: TIMESTAMP AS Updated_On
    FROM M15PEGA M15
    INNER JOIN M10KLAS M10 ON M10.Kode=M15.KdKlas
    INNER JOIN M08HCAB M08 ON M08.KdCaba=M15.KdCaba
    INNER JOIN M01AREA M01 ON M01.Kode=M15.KdArea
    INNER JOIN M02UUSA M02 ON M02.Kode=M15.KdUUsa
    INNER JOIN M04HJAB M04 ON M04.Kode=M15.KdJaba
    INNER JOIN M06HKJB M06 ON M06.Kode=M15.KdKJab
    INNER JOIN M17UKER M17 ON M17.KdUker=M15.KdUker
    INNER JOIN M12HGOL M12 ON M12.Kode=M15.KdGlng
    WHERE M15.NIP = l_NIP AND M15.prdtetap IS NOT NULL) ;
    --
    INSERT INTO T10MUTA(nip, tgleff, nosk, kdmutr, keterangan, kdklas, kdcaba, kduusa,
            kduker, kdglng, kdjaba, kdarea, kdkjab, gajipokok, kdcurr, penilaian, masakerja,
            keterangan2, keterangan3, keterangan4, keterangan5, m04_id, m06_id, m08_id, m12_id,
            version, created_by, created_on, updated_by, updated_on)
    (SELECT  M15.NIP, M15.PrdAwl, '' AS NoSK, '' AS KdMutr, '', M15.kdklas, M15.KdCaba, M15.KdUUsa,
            M15.KdUker, M15.KdGlng, M15.Kdjaba, M15.KdArea, M15.KdKJab,
            COALESCE(Fn_Kpusat(M15.NIP, M15.encgajiperc, l_MyPass) :: DECIMAL(15,2),0) AS Nilai,
            M15.KdCurr, 'TP', M15.MasaKerja, '', '', '', '', M04.m04_id, M06.m06_id, M08.m08_id, M12.m12_id,
            0, M15.updated_by, CURRENT_DATE, M15.updated_by, CURRENT_DATE
    FROM M15PEGA M15
    INNER JOIN M08HCAB M08 ON M08.KdCaba=M15.KdCaba
    INNER JOIN M04HJAB M04 ON M04.Kode=M15.KdJaba
    INNER JOIN M06HKJB M06 ON M06.Kode=M15.KdKJab
    INNER JOIN M12HGOL M12 ON M12.KODE=M15.KDGLNG
    WHERE M15.NIP = l_NIP AND M15.prdawl <> M15.prdtetap AND M15.prdawl IS NOT NULL) ;
    --
    INSERT INTO T10MUTA(nip, tgleff, nosk, kdmutr, keterangan, kdklas, kdcaba, kduusa,
            kduker, kdglng, kdjaba, kdarea, kdkjab, gajipokok, kdcurr, penilaian, masakerja,
            keterangan2, keterangan3, keterangan4, keterangan5, m04_id, m06_id, m08_id, m12_id,
            version, created_by, created_on, updated_by, updated_on)
    (SELECT  M15.NIP, M15.prdtetap, '' AS NoSK, 'AFPR' AS KdMutr, '', M15.kdklas, M15.KdCaba, M15.KdUUsa,
            M15.KdUker, M15.KdGlng, M15.Kdjaba, M15.KdArea, M15.KdKJab,
            COALESCE(Fn_Kpusat(M15.NIP, M15.encgajitetap, l_MyPass) :: DECIMAL(15,2),0) AS Nilai,
            M15.KdCurr, 'TP', M15.MasaKerja, '', '', '', '', M04.m04_id, M06.m06_id, M08.m08_id, M12.m12_id,
            0, M15.updated_by, CURRENT_DATE, M15.updated_by, CURRENT_DATE
    FROM M15PEGA M15
    INNER JOIN M08HCAB M08 ON M08.KdCaba=M15.KdCaba
    INNER JOIN M04HJAB M04 ON M04.Kode=M15.KdJaba
    INNER JOIN M06HKJB M06 ON M06.Kode=M15.KdKJab
    INNER JOIN M12HGOL M12 ON M12.Kode=M15.KdGlng 
    WHERE M15.NIP = l_NIP AND M15.prdtetap IS NOT NULL) ;
--
END;
$$ LANGUAGE plpgsql ;


/*
select * from P_GAJI_PEGAWAI ('123','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1)

select * FROM S08MUTA where nip='123'
select * FROM T10MUTA where nip='123'

*/