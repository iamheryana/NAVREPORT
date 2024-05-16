/***********************************************
Name sprocs	: P_EFSTP1
Create by	: PEGGY 
Date		: 19-08-2008
Description	: Proses Posting Efektif status 
Call From	: PAYRP1
Sub sprocs	: -
************************************************/
DROP FUNCTION P_EFSTP1 (l_NIP		VARCHAR(10),
		   		      l_Tgl_Akhir_x	DATE,
				      l_TglProses	DATE,
				      l_UserID		VARCHAR(12),
				      l_MyPass 		VARCHAR(128));
--
CREATE FUNCTION P_EFSTP1 (l_NIP		VARCHAR(10),
		   		      l_Tgl_Akhir_x	DATE,
				      l_TglProses	DATE,
				      l_UserID		VARCHAR(12),
				      l_MyPass 		VARCHAR(128))
RETURNS VOID 
AS $$
--
BEGIN 
---------------------New Emp : create tabel efektif status ---------------------------
INSERT INTO M62EFST (nip,    tglefektif,    status,    stspjk,    anakttgg,    version, created_by, created_on, updated_by, updated_on)
SELECT DISTINCT      T1.NIP, T1.TglEfektif, T1.Status, T1.StsPjk, T1.AnakTtgg, 0      , l_userid,   localtimestamp, null, localtimestamp 
FROM 
(-- T1
SELECT M15.NIP AS NIP, 
	M15.TglMasuk AS TglEfektif, 
	M15.Status AS Status, 
	M15.StsPjk AS StsPjk, 
	M15.AnakTtgg AS AnakTtgg
FROM M15PEGA M15 
WHERE M15.NIP = l_NIP AND 
	  ((l_Tgl_Akhir_x IS NOT NULL AND M15.TglMasuk BETWEEN l_Tgl_Akhir_x + INTERVAL '1 day' AND l_TglProses)  OR 
	  l_Tgl_Akhir_x IS NULL) 
) T1
LEFT JOIN M62EFST M62
ON M62.NIP = T1.NIP AND M62.TglEfektif = T1.TglEfektif 
WHERE M62.NIP IS NULL ;

---------------------Existing Emp : Update M. Pegawai -------------------------------
UPDATE M15PEGA 
SET Status 	 = T1.Status, 
    AnakTtgg     = T1.AnakTtgg, 
    StsPjk 	 = T1.StsPjk  	-- autojaya pl#9 
FROM 
(-- T1
SELECT M62.NIP AS NIP, 
	M62.Status AS Status, 
	M62.StsPjk AS StsPjk,
	M62.AnakTtgg AS AnakTtgg 
FROM M62EFST M62
WHERE M62.NIP = l_NIP AND 
      M62.TglEfektif BETWEEN l_Tgl_Akhir_x + INTERVAL '1 day' AND l_TglProses 
) T1
INNER JOIN  M15PEGA M15 
ON M15.NIP = T1.NIP ;

--------------------Existing Emp : Update M. Pegawai : Sts Pjk Januari doankz -------
-- autojaya pl#9 
-- -- IF MONTH(l_TglProses) = 1 
-- -- BEGIN 
-- -- 	UPDATE M15PEGA 
-- -- 	SET StsPjk 	 = T1.StsPjk
-- -- 	FROM 
-- -- 	(-- T1
-- -- 	SELECT  NIP        = M62.NIP, 
-- -- 			StsPjk     = M62.StsPjk
-- -- 	FROM M62EFST M62
-- -- 	INNER JOIN 
-- -- 	(--q2
-- -- 	SELECT NIP,TglEfektif=MAX(TglEfektif)
-- -- 	FROM M62EFST
-- -- 	WHERE NIP = l_NIP AND TglEfektif <= l_TglProses 
-- -- 	GROUP BY NIP
-- -- 	) Q2
-- -- 	ON Q2.NIP=M62.NIP AND Q2.TglEfektif = M62.TglEfektif 
-- -- 	) T1
-- -- 	INNER JOIN  M15PEGA M15 
-- -- 	ON M15.NIP = T1.NIP 
-- -- END 

END ;
$$ LANGUAGE plpgsql ;

/* TESTING ...
--
EXEC P_EFSTP1 l_NIP = '010',
	      l_Tgl_Akhir_x = '2002-12-20',
              l_TglProses = '2003-07-30',
              l_UserID = 'SUHE',
	      l_MyPass = 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
EXEC P_EFSTP1 l_NIP = '18',
	      l_Tgl_Akhir_x = '2006-04-20',
              l_TglProses = '2006-05-23',
              l_UserID = 'DIAH',
	      l_MyPass = 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
--
*/
