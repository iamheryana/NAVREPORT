DROP VIEW V_BIOPP4;
CREATE VIEW V_BIOPP4
AS
SELECT
    M15.NIP, M15.Nama, M15.NamaKecil, M15.Status, M15.JnsKlmn, M15.AnakTtgg, 
    M15.TglLahir, M15.TempatLahir, M15.Tingkatan,
    M15.KdArea,M01.Keterangan KetArea, M15.KdCaba,M08.NmCaba KetCaba,
    M15.KdUUsa,M02.Keterangan KetUUsa, M15.KdUKer,M17.Keterangan KetUker,
    M15.KdGlng,M12.Keterangan KetGlng,M15.KdKJab,M06.Keterangan KetKJab,
    M15.KdJaba,M04.Keterangan KetJaba, M15.KdKlas,M10.Keterangan KetKlas,
    (CASE T09.RID WHEN 'P' THEN '1' ELSE '2' END) :: VARCHAR AS Jns,
    T09.TglEff,T09.KdHarg,M30.Nama AS KetHarg,T09.NoSK,T09.TglSK,T09.Durasi,T09.Keterangan,
    M15.TglMasuk, M15.Tglkeluar 
FROM T09HARG T09
LEFT JOIN M15PEGA M15 ON M15.NIP = T09.NIP
JOIN M01AREA M01 ON M15.KdArea = M01.Kode
JOIN M08HCAB M08 ON M15.KdCaba = M08.KdCaba
JOIN M02UUSA M02 ON M15.KdUUsa = M02.Kode
JOIN M17UKER M17 ON M15.KdUKer = M17.KdUker
JOIN M12HGOL M12 ON M15.KdGlng = M12.Kode
JOIN M04HJAB M04 ON M15.KdJaba = M04.Kode
JOIN M06HKJB M06 ON M15.KdKJab = M06.Kode
JOIN M10KLAS M10 ON M15.KdKlas = M10.Kode
JOIN M30HAHU M30 ON M30.KdHarg=T09.KdHarg;
