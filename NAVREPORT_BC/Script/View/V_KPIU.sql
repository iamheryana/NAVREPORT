CREATE VIEW V_KPIU AS
SELECT
    M15.NIP, M15.Nama,  M15.TglPayr,M15.KdCaba,M08.NmCaba,M15.KdUUsa,M02.Keterangan AS KetUUSA,M15.KdUKer,M17.Keterangan AS KetUker,
    COALESCE(T05.KdJnsP,' ') AS JnsPiut,M22.NmPiut,T05.TgDoku AS TgPiu,COALESCE(T05.Piutang,0) AS Piutang, COALESCE(T05.Bunga,0) AS Bunga,    
    T07.TgDoku AS Tgbay , COALESCE(T07.BayPokok,0) AS BayPokok, COALESCE(T07.BayBunga,0) AS BayBunga, 
    COALESCE(T07.Comp,' ') AS Comp

FROM
M15PEGA M15 LEFT JOIN T05HPIU T05 ON M15.NIP = T05.NIP LEFT JOIN T07BAYP T07 ON T05.NIP = T07.NIP  AND T05.KdJnsP = T07.KdJnsP AND  T05.TgDoku = T07.TgPiut                         
    LEFT JOIN M08HCAB M08 ON M15.kdCaba= M08.kdCaba                        
    LEFT JOIN M02UUSA M02 ON M15.KdUUsa=M02.Kode               
    LEFT JOIN M17UKER M17 ON M15.KdUKer =M17.KdUker        
    LEFT JOIN M22JNSP M22 ON T05.KdJnsP=M22.KdJnsP;