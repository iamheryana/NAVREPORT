DROP function  R_SCUTP1(
                     l_NIPFR        VARCHAR(10),
                     l_NIPTO        VARCHAR(10),
                     l_TAHUNFR      DECIMAL(4,0),
                     l_TAHUNTO      DECIMAL(4,0),
                     l_GROUP        VARCHAR(1),
                     l_mcStatus     VARCHAR(1),
                     l_mdPerTgl     date,
                     l_UserId       INT);
--
CREATE function  R_SCUTP1(
                     l_NIPFR        VARCHAR(10),
                     l_NIPTO        VARCHAR(10),
                     l_TAHUNFR      DECIMAL(4,0),
                     l_TAHUNTO      DECIMAL(4,0),
                     l_GROUP        VARCHAR(1),
                     l_mcStatus     VARCHAR(1),
                     l_mdPerTgl     date,
                     l_UserId       INT)
--
RETURNS TABLE (	OutNIP          VARCHAR(10),
				OutNama         VARCHAR(25),
				OutTahun        DECIMAL(4,0),
				OutJatahCuti    DECIMAL(4,1) ,
				OutPemakaian    DECIMAL(4,1), 
				OutSisa         DECIMAL(4,1), 
				OutTglMasuk     date, 
				OutTglpayr      date) 
--                    
AS $$
--
BEGIN 
    RETURN QUERY 
    SELECT  S09.NIP, M15.Nama, S09.Tahun, S09.JatahCuti, S09.Pemakaian, 
            JatahCuti-Pemakaian AS Sisa, M15.TglMasuk, M15.TglPayr
    FROM S09HCUT AS S09
    INNER JOIN M15PEGA AS M15 ON S09.NIP = M15.NIP 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON M15.KdGlng=VSL.Gol_Kode AND M15.KdCaba=VSL.Cab_Kode
    WHERE (S09.NIP BETWEEN l_NIPFR AND l_NIPTO)         AND
          (S09.TAHUN BETWEEN l_TAHUNFR AND l_TAHUNTO)   AND
          ( l_mcStatus = 'S' OR 
           (l_mcstatus = 'K' AND M15.TglMasuk IS NOT NULL AND M15.TglKeluar IS NOT NULL AND M15.TglKeluar <= l_mdPerTgl) OR 
           (l_mcstatus = 'A' AND M15.TglMasuk IS NOT NULL AND M15.TglMasuk <= l_mdPerTgl AND
                               (M15.TglKeluar IS NULL OR M15.TglKeluar >= l_mdPerTgl))) ;
--
END ; 
$$ LANGUAGE plpgsql ;  
--*coalesce(field, 'Empty')

/* 
Select * from R_SCUTP1(' ','ZZZZZZZZZZ','2013','2013','G','A', '2013-06-21', 1) ; 
select * from m15pega 
*/
