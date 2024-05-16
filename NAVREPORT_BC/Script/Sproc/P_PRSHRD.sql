/***********************************************
Name sprocs	: P_prsHRD
Create by	: herz
Date		: 12-09-2003
Description	: Proses Posting HRD - Bulanan
Call From	: PAYRP1
Sub sprocs	: -
************************************************/
DROP FUNCTION P_prsHRD (l_NIP		VARCHAR(10),
			l_Tgl_Akhir_x	DATE,
			l_TglProses	DATE,
			l_UserID	VARCHAR(12),
			l_MyPass 	VARCHAR(128));
--
CREATE FUNCTION P_prsHRD (l_NIP		VARCHAR(10),
			l_Tgl_Akhir_x	DATE,
			l_TglProses	DATE,
			l_UserID	VARCHAR(12),
			l_MyPass 	VARCHAR(128))
RETURNS VOID 
AS $$ 
--
DECLARE l_T08TgDocu		DATE;
	l_T08NIP		VARCHAR(10);
	l_T08KdAbsn		VARCHAR(4);
	l_T08JamAbsn		DECIMAL(3,2);
	l_T08HariAbsn		DECIMAL(3,1);
	l_T08CutiTahun		DECIMAL(4,0);
	l_M29Singkatan		VARCHAR(10);
	l_M29JnsCuti		INT;
	l_T12TgDocu		DATE;
	l_T12KdJnsD		VARCHAR(4);
	l_T12KdJrsn		VARCHAR(4);
	l_T12NIP		VARCHAR(10);
	l_T12Hasil		VARCHAR(10);
	l_T12Nilai		VARCHAR(2);
	l_T12BiayaSendiri	INT;
	l_T12NoIjaz		VARCHAR(25);
	l_T11KdLmbD		VARCHAR(4);
	l_T11KdLksD		VARCHAR(4);
	l_T11BulanTrng		DECIMAL(3,0);
	l_T11HariTrng		DECIMAL(4,2);
	l_T11Biaya		DECIMAL(15,2);
	l_M25Singkatan		VARCHAR(10);
	l_M26Singkatan		VARCHAR(10);
	l_M27Singkatan		VARCHAR(10);
	l_M28Singkatan		VARCHAR(10);
	l_T10NIP		VARCHAR(10);
	l_T10TglEff		DATE;
	l_T10KdMutr		VARCHAR(4);
	l_T10NoSK		VARCHAR(25);
	l_T10Keterangan		VARCHAR(100);
	l_T10KdKlas		VARCHAR(4);
	l_T10KdCaba		VARCHAR(4);
	l_T10KdUUsa		VARCHAR(4);
	l_T10KdUKer		VARCHAR(8);
	l_T10KdGlng		VARCHAR(4);
	l_T10KdJaba		VARCHAR(4);
	l_T10KdArea		VARCHAR(4);
	l_T10KdKJab		VARCHAR(4);
	l_T10GajiPokok		DECIMAL(15,2);
	l_T10Penilaian		VARCHAR(2);
	l_T10KdCurr		VARCHAR(4);
	l_M24Singkatan		VARCHAR(10);
	l_M10Singkatan		VARCHAR(10);
	l_M08Singkatan		VARCHAR(10);
	l_M02Singkatan		VARCHAR(10);
	l_M17Singkatan		VARCHAR(10);
	l_M12Singkatan		VARCHAR(10);
	l_M04Singkatan		VARCHAR(10);
	l_M01Singkatan		VARCHAR(10);
	l_M06Singkatan		VARCHAR(10);
	l_T21NIP		VARCHAR(10);
	l_T21Tahun		DECIMAL(4,0);
	l_T21JenisMedical	VARCHAR(4);
	l_T20Plafon		DECIMAL(15,2);
	l_T20PlafonKel		DECIMAL(15,2);
	l_LOOP_T08		REFCURSOR;
	l_LOOP_T12		REFCURSOR;
	l_LOOP_T10		REFCURSOR;
	l_LOOP_T21		REFCURSOR;
	l_FZ1Personalia		VARCHAR(1);
	l_M15KdKJab		VARCHAR(4);
	l_Jatah			DECIMAL(15,2);
	l_Pakai			DECIMAL(15,2);
	l_JatahKel		DECIMAL(15,2);
	l_PakaiKel		DECIMAL(15,2);
	l_mlProp		INT;
	l_T10KETERANGAN2	VARCHAR(100);
	l_T10KETERANGAN3     	VARCHAR(100);
	l_T10KETERANGAN4     	VARCHAR(100);
	l_T10KETERANGAN5     	VARCHAR(100);
	l_BeliModulAsr       	VARCHAR(1);
	l_M15KdUker          	VARCHAR(8);
	l_M15KdCaba          	VARCHAR(4);

BEGIN 
--Setting Nilai Awal
l_T08NIP := ' ';
l_T08KdAbsn := ' ';
l_T08JamAbsn := 0;
l_T08HariAbsn := 0;
l_T08CutiTahun := 0;
l_M29Singkatan := ' ';
l_M29JnsCuti := 0;
--
SELECT SUBSTR(StringFlag,6,1)
INTO l_BeliModulAsr 
FROM FZ2FLDA;

SELECT Personalia
INTO l_FZ1Personalia
FROM FZ1FLDA;

/*
--
-- *************************************** HISTORY ABSENSI ********************************  
-- Mulai Proses data T08+M29 Ke S06 dan S0A
SET l_LOOP_T08=CURSOR FOR
SELECT T08.TgDocu,T08.NIP,T08.KdAbsn,T08.JamAbsn,T08.HariAbsn,T08.CutiTahun,
       M29.Singkatan,M29.JnsCuti
FROM T08ABSN T08
--
INNER JOIN M29JNSA M29
ON M29.Kode=T08.KdAbsn
--
WHERE NIP = l_NIP AND 
      TgDocu BETWEEN DATEADD(DAY,1,l_Tgl_Akhir_x) AND l_TglProses
--
OPEN l_LOOP_T08
FETCH NEXT FROM l_LOOP_T08
INTO  l_T08TgDocu,l_T08NIP,l_T08KdAbsn,l_T08JamAbsn,l_T08HariAbsn,l_T08CutiTahun,
      l_M29Singkatan,l_M29JnsCuti
--
WHILE l_l_FETCH_STATUS=0
  BEGIN
    -- 
    -- Isi Data ke S06 
    IF EXISTS (SELECT NIP FROM S06ABSH WHERE Tanggal=l_T08TgDocu AND NIP=l_T08NIP AND KdAbsn=l_T08KdAbsn)
       BEGIN
         UPDATE S06ABSH 
         SET JamAbsn  = ISNULL(JamAbsn,0) + l_T08JamAbsn,
	     	 HariAbsn = ISNULL(HariAbsn,0)+ l_T08HariAbsn
         WHERE Tanggal=l_T08TgDocu AND NIP=l_T08NIP AND KdAbsn=l_T08KdAbsn
       END
    ELSE --Jika Data belum Ada
       BEGIN
         INSERT INTO S06ABSH(Tanggal   ,NIP    ,KdAbsn    ,SkAbsn       ,JamAbsn    ,HariAbsn    ,PostId ,PostDate			     ,UpdTime)
		      		  VALUES(l_T08TgDocu,l_T08NIP,l_T08KdAbsn,l_M29Singkatan,l_T08JamAbsn,l_T08HariAbsn,l_UserID,CONVERT(VARCHAR(10),GETDATE(),121),CONVERT(VARCHAR(19),GETDATE(),120))
       END
    --*
    --
    -- Jika jenis absensinya cuti maka isi summary cuti
	IF l_M29JnsCuti=1 
	   BEGIN
	     -- Isi Data ke S0A 
	     IF EXISTS (SELECT NIP FROM S0ADCUT WHERE NIP=l_T08NIP AND Tahun=l_T08CutiTahun AND BlnTgl = SUBSTR(CONVERT(VARCHAR(6),l_T08TgDocu,112),5,2)+SUBSTR(CONVERT(VARCHAR(6),l_T08TgDocu,112),3,2) AND KdCuti=l_T08KdAbsn)
	        BEGIN
	          UPDATE S0ADCUT 
	          SET Pemakaian = ISNULL(Pemakaian,0) + l_T08HariAbsn
	          WHERE NIP=l_T08NIP AND Tahun=l_T08CutiTahun AND BlnTgl = SUBSTR(CONVERT(VARCHAR(6),l_T08TgDocu,112),5,2)+SUBSTR(CONVERT(VARCHAR(6),l_T08TgDocu,112),3,2) AND KdCuti=l_T08KdAbsn
	        END
	     ELSE --Jika Data belum Ada
	        BEGIN
	          INSERT INTO S0ADCUT(NIP    ,Tahun        ,BlnTgl    ,KdCuti    ,Pemakaian)
			               VALUES(l_T08NIP,l_T08CutiTahun,SUBSTR(CONVERT(VARCHAR(6),l_T08TgDocu,112),5,2)+SUBSTR(CONVERT(VARCHAR(6),l_T08TgDocu,112),3,2),l_T08KdAbsn,l_T08HariAbsn)
	        END
	     --*
	     --
	   END
	--
    FETCH NEXT FROM l_LOOP_T08
    INTO l_T08TgDocu,l_T08NIP,l_T08KdAbsn,l_T08JamAbsn,l_T08HariAbsn,l_T08CutiTahun,
         l_M29Singkatan,l_M29JnsCuti
  END
--*
CLOSE l_LOOP_T08
DEALLOCATE l_LOOP_T08
--*
-- *************************************** HISTORY TRAINING  ********************************  
-- Mulai Proses data T12+T11 Ke S07
SET l_LOOP_T12 = CURSOR FOR
SELECT T12.TgDocu,T12.KdJnsD,T12.KdJrsn,T12.NIP,T12.Hasil,T12.Nilai,T12.BiayaSendiri,T12.NoIjaz,
	   T11.KdLmbD,T11.KdLksD,T11.BulanTrng,T11.HariTrng,T11.Biaya,
       M25.Singkatan,M26.Singkatan,M27.Singkatan,M28.Singkatan
FROM T12DTRN T12
--
INNER JOIN T11HTRN T11
ON T11.TgDocu = T12.TgDocu AND T11.KdJnsD = T12.KdJnsD AND T11.KdJrsn = T12.KdJrsn
--
INNER JOIN M25JNSD M25
ON M25.Kode = T12.KdJnsD
--
INNER JOIN M26JRSN M26
ON M26.Kode = T12.KdJrsn
--
INNER JOIN M27LMBD M27
ON M27.Kode = T11.KdLmbD
--
INNER JOIN M28LKSD M28
ON M28.Kode = T11.KdLksD
--
WHERE T12.NIP = l_NIP AND 
      T12.TgDocu BETWEEN DATEADD(DAY,1,l_Tgl_Akhir_x) AND l_TglProses
--
OPEN l_LOOP_T12
FETCH NEXT FROM l_LOOP_T12
INTO l_T12TgDocu,l_T12KdJnsD,l_T12KdJrsn,l_T12NIP,l_T12Hasil,l_T12Nilai,l_T12BiayaSendiri,l_T12NoIjaz,
	 l_T11KdLmbD,l_T11KdLksD,l_T11BulanTrng,l_T11HariTrng,l_T11Biaya,
     l_M25Singkatan,l_M26Singkatan,l_M27Singkatan,l_M28Singkatan
--
WHILE l_l_FETCH_STATUS=0
  BEGIN
    --
    -- Isi Data ke S07
    IF NOT EXISTS (SELECT NIP FROM S07TRNG WHERE Tanggal=l_T12TgDocu AND NIP=l_T12NIP AND KdJnsd=l_T12KdJnsD AND KdJrsn=l_T12KdJrsn)
       BEGIN
         INSERT INTO S07TRNG(Tanggal   ,NIP    ,KdJnsd    ,SkJnsD       ,KdJrsn    ,SkJrsn       ,KdLmbD    ,SkLmbD       ,KdLksD    ,SkLksD       ,BulanTrng    ,HariTrng    ,Hasil    ,Nilai    ,BiayaSendiri    ,Biaya    ,NoIjaz    ,PostId ,PostDate			            ,UpdTime)
		      		  VALUES(l_T12TgDocu,l_T12NIP,l_T12KdJnsD,l_M25Singkatan,l_T12KdJrsn,l_M26Singkatan,l_T11KdLmbD,l_M27Singkatan,l_T11KdLksD,l_M28Singkatan,l_T11BulanTrng,l_T11HariTrng,l_T12Hasil,l_T12Nilai,l_T12BiayaSendiri,l_T11Biaya,l_T12NoIjaz,l_UserID,CONVERT(VARCHAR(10),GETDATE(),121),CONVERT(VARCHAR(19),GETDATE(),120))
       END
    --*
	--
    FETCH NEXT FROM l_LOOP_T12
	INTO l_T12TgDocu,l_T12KdJnsD,l_T12KdJrsn,l_T12NIP,l_T12Hasil,l_T12Nilai,l_T12BiayaSendiri,l_T12NoIjaz,
		 l_T11KdLmbD,l_T11KdLksD,l_T11BulanTrng,l_T11HariTrng,l_T11Biaya,
	     l_M25Singkatan,l_M26Singkatan,l_M27Singkatan,l_M28Singkatan
  END
--*
CLOSE l_LOOP_T12
DEALLOCATE l_LOOP_T12
--
*/

-- *************************************** HISTORY MUTASI  ********************************  
---------------- Proses ke S0FPDAP --> Utk Pegawai Baru ---------------------------------
-- -- berubah mekanisme, pindah ke view 
-- -- IF l_BeliModulAsr='Y'
-- -- BEGIN 
-- -- 
-- -- INSERT INTO S0FPDAP
-- -- SELECT distinct T1.Periode, T1.Flag, T1.NIP, T1.NoKartu, T1.Status, 
-- --        T1.NamaPeserta, T1.Hubungan, T1.JnsKlmn, T1.TglLahir, 
-- --        T1.NoRekBank, T1.KdUker, T1.KdCaba, T1.KdKJab, T1.KdJAsu, T1.LvlBenefit
-- -- FROM 
-- -- (-- T1
-- -- SELECT DISTINCT Periode=T22.TanggalMasuk, Flag='N', 
-- --        NIP=M15.NIP, NoKartu=T22.NoPeserta, Status=fn_CrStsPegUAsr(l_NIP),--M15.Status,
-- --        NamaPeserta=M15.Nama, Hubungan='', JnsKlmn=M15.JnsKlmn, TglLahir=M15.TglLahir, 
-- --        NoRekBank=fn_RekAsrn(l_NIP,ISNULL(M15.KdCaba,' '), ISNULL(M15.KdUKer,' ')), 
-- -- 	KdUker=M15.KdUker, KdCaba=M15.KdCaba, 
-- --        KdKJab=M15.KdKJab, KdJAsu=M58.KdJAsu, LvlBenefit=M58.LvlBenefit 
-- -- FROM T22KPAS T22
-- -- INNER JOIN M15PEGA M15 ON M15.NIP=T22.NIP
-- -- INNER JOIN M58PRED M58 ON M58.KDJAsu=T22.Kode and m58.kdkjab = m15.kdkjab 
-- -- WHERE T22.NIP = l_NIP AND T22.TanggalMasuk BETWEEN DATEADD(DAY,1,l_Tgl_Akhir_x)
-- --       AND l_TglProses AND l_BeliModulAsr='Y'
-- -- ) T1
-- -- LEFT JOIN S0FPDAP S0F 
-- -- ON S0F.PERIODE=T1.PERIODE AND S0F.FLAG = T1.FLAG AND S0F.NIP = T1.NIP AND 
-- --    S0F.NOKARTU = T1.NOKARTU AND S0F.NAMAPESERTA = T1.NAMAPESERTA AND 
-- --    S0F.KDJASU = T1.KDJASU 
-- -- WHERE S0F.PERIODE IS NULL 
-- -- 
-- -- 
-- -- ---------------- Proses ke S0FPDAP --> Utk Pegawai Keluar ---------------------------------
-- -- INSERT INTO S0FPDAP
-- -- SELECT distinct T1.Periode, T1.Flag, T1.NIP, T1.NoKartu, T1.Status, 
-- --        T1.NamaPeserta, T1.Hubungan, T1.JnsKlmn, T1.TglLahir, 
-- --        T1.NoRekBank, T1.KdUker, T1.KdCaba, T1.KdKJab, T1.KdJAsu, T1.LvlBenefit
-- -- FROM 
-- -- (-- T1
-- -- SELECT DISTINCT Periode=M15.TglKeluar, Flag='R', 
-- --        NIP=M15.NIP, NoKartu=T22.NoPeserta, Status=fn_CrStsPegUAsr(l_NIP),--M15.Status,
-- --        NamaPeserta=M15.Nama, Hubungan='', JnsKlmn=M15.JnsKlmn, TglLahir=M15.TglLahir, 
-- --        NoRekBank=fn_RekAsrn(l_NIP,ISNULL(M15.KdCaba,' '), ISNULL(M15.KdUKer,' ')), 
-- -- 	KdUker=M15.KdUker, KdCaba=M15.KdCaba, 
-- --        KdKJab=M15.KdKJab, KdJAsu=M58.KdJAsu, LvlBenefit=M58.LvlBenefit 
-- -- FROM M15PEGA M15
-- -- INNER JOIN T22KPAS T22 ON M15.NIP=T22.NIP
-- -- INNER JOIN M58PRED M58 ON M58.KDJAsu=T22.Kode and m58.kdkjab = m15.kdkjab 
-- -- WHERE M15.NIP = l_NIP AND M15.TglKeluar BETWEEN DATEADD(DAY,1,l_Tgl_Akhir_x)
-- --       AND l_TglProses AND l_BeliModulAsr='Y'
-- -- ) T1
-- -- LEFT JOIN S0FPDAP S0F 
-- -- ON S0F.PERIODE=T1.PERIODE AND S0F.FLAG = T1.FLAG AND S0F.NIP = T1.NIP AND 
-- --    S0F.NOKARTU = T1.NOKARTU AND S0F.NAMAPESERTA = T1.NAMAPESERTA AND 
-- --    S0F.KDJASU = T1.KDJASU 
-- -- WHERE S0F.PERIODE IS NULL 
-- -- 
-- -- END 

---- PINDAH KE P_EFSTP1 
-- -- -- -- ---------------------New Emp : create tabel efektif status ---------------------------
-- -- -- -- INSERT INTO M62EFST
-- -- -- -- SELECT DISTINCT T1.NIP, T1.TglEfektif, T1.Status, T1.StsPjk, T1.AnakTtgg, 
-- -- -- -- 				T1.UserId, T1.UpdDate, T1.UpdTime, T1.Ws 
-- -- -- -- FROM 
-- -- -- -- (-- T1
-- -- -- -- SELECT  NIP        = M15.NIP, 
-- -- -- -- 		TglEfektif = M15.TglMasuk, 
-- -- -- -- 		Status     = M15.Status, 
-- -- -- -- 		StsPjk     = M15.StsPjk, 
-- -- -- -- 		AnakTtgg   = M15.AnakTtgg, 
-- -- -- -- 		UserID     = l_UserId, 
-- -- -- -- 		UpdDate    = CONVERT(VARCHAR(10),GETDATE(),121), 
-- -- -- -- 		UpdTime    = CONVERT(VARCHAR(19),GETDATE(),120), 
-- -- -- -- 		Ws         = '' 
-- -- -- -- FROM M15PEGA M15 
-- -- -- -- WHERE M15.NIP = l_NIP AND 
-- -- -- -- 	  M15.TglMasuk BETWEEN DATEADD(DAY,1,l_Tgl_Akhir_x) AND l_TglProses 
-- -- -- -- ) T1
-- -- -- -- LEFT JOIN M62EFST M62
-- -- -- -- ON M62.NIP = T1.NIP AND M62.TglEfektif = T1.TglEfektif 
-- -- -- -- WHERE M62.NIP IS NULL 
-- -- -- -- 
-- -- -- -- ---------------------Existing Emp : Update M. Pegawai -------------------------------
-- -- -- -- UPDATE M15PEGA 
-- -- -- -- SET Status 	 = T1.Status, 
-- -- -- -- 	AnakTtgg = T1.AnakTtgg 
-- -- -- -- FROM 
-- -- -- -- (-- T1
-- -- -- -- SELECT  NIP        = M62.NIP, 
-- -- -- -- 		Status     = M62.Status, 
-- -- -- -- 		AnakTtgg   = M62.AnakTtgg 
-- -- -- -- FROM M62EFST M62
-- -- -- -- WHERE M62.NIP = l_NIP AND 
-- -- -- -- 	  M62.TglEfektif BETWEEN DATEADD(DAY,1,l_Tgl_Akhir_x) AND l_TglProses 
-- -- -- -- ) T1
-- -- -- -- INNER JOIN  M15PEGA M15 
-- -- -- -- ON M15.NIP = T1.NIP 
-- -- -- -- 
-- -- -- -- --------------------Existing Emp : Update M. Pegawai : Sts Pjk Januari doankz -------
-- -- -- -- 
-- -- -- -- IF MONTH(l_TglProses) = 1 
-- -- -- -- BEGIN 
-- -- -- -- 	UPDATE M15PEGA 
-- -- -- -- 	SET StsPjk 	 = T1.StsPjk
-- -- -- -- 	FROM 
-- -- -- -- 	(-- T1
-- -- -- -- 	SELECT  NIP        = M62.NIP, 
-- -- -- -- 			StsPjk     = M62.StsPjk
-- -- -- -- 	FROM M62EFST M62
-- -- -- -- 	INNER JOIN 
-- -- -- -- 	(--q2
-- -- -- -- 	SELECT NIP,TglEfektif=MAX(TglEfektif)
-- -- -- -- 	FROM M62EFST
-- -- -- -- 	WHERE NIP = l_NIP AND TglEfektif <= l_TglProses 
-- -- -- -- 	GROUP BY NIP
-- -- -- -- 	) Q2
-- -- -- -- 	ON Q2.NIP=M62.NIP AND Q2.TglEfektif = M62.TglEfektif 
-- -- -- -- 	) T1
-- -- -- -- 	INNER JOIN  M15PEGA M15 
-- -- -- -- 	ON M15.NIP = T1.NIP 
-- -- -- -- END 

-------------------------- Mulai Proses data T10 Ke S08 --------------------------
OPEN l_LOOP_T10 FOR SELECT T10.NIP,T10.TglEff,T10.KdMutr,T10.NoSK,T10.Keterangan,
				   T10.KdKlas,T10.KdCaba,T10.KdUUsa,T10.KdUKer,T10.KdGlng,
				   T10.KdJaba,T10.KdArea,T10.KdKJab,T10.GajiPokok,T10.Penilaian,
				   T10.KdCurr,
				   M24.Singkatan,M10.Singkatan,M08.SkCaba,M02.Singkatan,
				   M17.Singkatan,M12.Singkatan,M04.Singkatan,M01.Singkatan,
				   M06.Singkatan,M15.KdKJab	,
				   T10.KETERANGAN2,T10.KETERANGAN3,T10.KETERANGAN4,T10.KETERANGAN5,
				   M15.KdCaba, M15.KdUker
			FROM T10MUTA T10
			--
			INNER JOIN M24MUTR M24
			ON M24.KdMutr = T10.KdMutr
			--
			INNER JOIN M10KLAS M10
			ON M10.Kode = T10.KdKlas
			--
			INNER JOIN M08HCAB M08
			ON M08.KdCaba = T10.KdCaba
			--
			INNER JOIN M02UUSA M02
			ON M02.Kode = T10.KdUUsa
			--
			INNER JOIN M17UKER M17
			ON M17.KdUker = T10.KdUKer
			--
			INNER JOIN M12HGOL M12
			ON M12.Kode = T10.KdGlng
			--
			INNER JOIN M04HJAB M04
			ON M04.Kode = T10.KdJaba
			--
			INNER JOIN M01AREA M01
			ON M01.Kode = T10.KdArea
			--
			INNER JOIN M06HKJB M06
			ON M06.Kode = T10.KdKJab
			--
			INNER JOIN M15PEGA M15
			ON M15.NIP=T10.NIP
			--
			WHERE T10.NIP = l_NIP AND 
			      T10.TglEff BETWEEN l_Tgl_Akhir_x + INTERVAL '1 day' AND l_TglProses AND l_FZ1Personalia='Y'
				  AND T10.KdMutr NOT IN (' ','AFPR');
LOOP 
   FETCH l_LOOP_T10 INTO l_T10NIP,l_T10TglEff,l_T10KdMutr,l_T10NoSK,l_T10Keterangan,l_T10KdKlas,l_T10KdCaba,l_T10KdUUsa,l_T10KdUKer,l_T10KdGlng,l_T10KdJaba,l_T10KdArea,l_T10KdKJab,l_T10GajiPokok,l_T10Penilaian,l_T10KdCurr,
  		         l_M24Singkatan,l_M10Singkatan,l_M08Singkatan,l_M02Singkatan,l_M17Singkatan,l_M12Singkatan,l_M04Singkatan,l_M01Singkatan,l_M06Singkatan,
		         l_M15KdKJab	,l_T10KETERANGAN2,l_T10KETERANGAN3,l_T10KETERANGAN4,l_T10KETERANGAN5, l_M15KdCaba, l_M15KdUker;
   IF NOT FOUND THEN
      EXIT ;
   END IF;
    --
---------------- Proses ke S0FPDAP --> Utk Pegawai Mutasi ---------------------------------
-- -- berubah mekanisme, pindah ke view 
-- -- IF l_BeliModulAsr='Y'
-- -- BEGIN 
-- -- 
-- -- INSERT INTO S0FPDAP
-- -- SELECT distinct T1.Periode, T1.Flag, T1.NIP, T1.NoKartu, T1.Status, 
-- --        T1.NamaPeserta, T1.Hubungan, T1.JnsKlmn, T1.TglLahir, 
-- --        T1.NoRekBank, T1.KdUker, T1.KdCaba, T1.KdKJab, T1.KdJAsu, T1.LvlBenefit
-- -- FROM 
-- -- (-- T1
-- -- SELECT DISTINCT Periode=l_T10TglEff, Flag=case when M15.KdKJab<>l_T10KdKJab then 'U' else 'X' end, 
-- --        NIP=M15.NIP, NoKartu=T22.NoPeserta, Status=fn_CrStsPegUAsr(l_NIP),--M15.Status,
-- --        NamaPeserta=M15.Nama, Hubungan='', JnsKlmn=M15.JnsKlmn, TglLahir=M15.TglLahir, 
-- --        NoRekBank=fn_RekAsrn(l_NIP,ISNULL(l_T10KdCaba,' '), ISNULL(l_T10KdUker,' ')), 
-- -- 	KdUker=l_T10KdUker, KdCaba=l_T10KdCaba, 
-- --        KdKJab=l_T10KdKJab, KdJAsu=M58.KdJAsu, LvlBenefit=M58.LvlBenefit 
-- -- FROM M15PEGA M15
-- -- INNER JOIN T22KPAS T22 ON M15.NIP=T22.NIP
-- -- INNER JOIN M58PRED M58 ON M58.KDJAsu=T22.Kode AND M58.KDKJAB = l_T10KdKJab
-- -- WHERE l_T10NIP=M15.NIP AND l_T10TglEff BETWEEN DATEADD(DAY,1,l_Tgl_Akhir_x) AND l_TglProses AND 
-- --       l_BeliModulAsr='Y' AND (M15.KdCaba<>l_T10KdCaba OR M15.KdUker<>l_T10KdUker OR M15.KdKJab<>l_T10KdKJab)
-- -- ) T1
-- -- LEFT JOIN S0FPDAP S0F 
-- -- ON S0F.PERIODE=T1.PERIODE AND S0F.FLAG = T1.FLAG AND S0F.NIP = T1.NIP AND 
-- --    S0F.NOKARTU = T1.NOKARTU AND S0F.NAMAPESERTA = T1.NAMAPESERTA AND 
-- --    S0F.KDJASU = T1.KDJASU 
-- -- WHERE S0F.PERIODE IS NULL 
-- -- 
-- -- END 
--------------------------------------000--------------------------------------------------

    -- Isi Data ke S08
    IF (SELECT NIP FROM S08MUTA WHERE NIP=l_T10NIP AND TglEff=l_T10TglEff AND KdMutr=l_T10KdMutr) IS NULL THEN 
       BEGIN
         INSERT INTO S08MUTA(NIP     ,KdMutr     ,SkMutr        ,NoSK     ,TglEff     ,Keterangan     ,KdKlas     ,SkKlas        ,KdCaba     ,SkCaba        ,KdUUsa     ,SkUUsa        ,KdUKer     ,SkUKer        ,KdGlng     ,SkGlng        ,KdJaba     ,SkJaba        ,KdArea     ,SkArea        ,KdKJab     ,SkKJab        ,KdCurr     ,GajiPokok ,Penilaian     ,PostId  ,PostDate    ,UpdTime     ,EncGajiPokok                                              ,KETERANGAN2     ,KETERANGAN3     ,KETERANGAN4     ,KETERANGAN5     ,VERSION, CREATED_BY, CREATED_ON, UPDATED_BY, UPDATED_ON)
      		      VALUES(l_T10NIP,l_T10KdMutr,l_M24Singkatan,l_T10NoSK,l_T10TglEff,l_T10Keterangan,l_T10KdKlas,l_M10Singkatan,l_T10KdCaba,l_M08Singkatan,l_T10KdUUsa,l_M02Singkatan,l_T10KdUKer,l_M17Singkatan,l_T10KdGlng,l_M12Singkatan,l_T10KdJaba,l_M04Singkatan,l_T10KdArea,l_M01Singkatan,l_T10KdKJab,l_M06Singkatan,l_T10KdCurr,0         ,l_T10Penilaian,l_UserID,CURRENT_DATE,CURRENT_TIME,fn_kcabang(l_T10NIP,l_T10GajiPokok ::VARCHAR(20),l_MyPass),l_T10KETERANGAN2,l_T10KETERANGAN3,l_T10KETERANGAN4,l_T10KETERANGAN5,0      , l_userid,   current_time, null, null );
       END;
    END IF; 
    --*
	--
    OPEN l_LOOP_T21 FOR SELECT T21.NIP,T21.Tahun,T21.JenisMedical,T20.Plafon,T20.PlafonKel
				FROM T21PMEP T21
				INNER JOIN T20JTMJ T20
				ON T20.Tahun = T21.Tahun AND T20.JenisMedical = T21.JenisMedical AND T20.KelJab = l_T10KdKJab
				WHERE T21.NIP = l_T10NIP AND T21.Tahun = EXTRACT(YEAR FROM l_T10TglEff) AND l_M15KdKJab<>l_T10KdKJab;
    LOOP 
       FETCH l_LOOP_T21 INTO l_T21NIP,l_T21Tahun,l_T21JenisMedical,l_T20Plafon,l_T20PlafonKel;
       IF NOT FOUND THEN
          EXIT ;
       END IF;
	    --
	-- Check Dulu jika pemakaian=jatahnya maka tdk boleh update jatah medical
	--- by suhe
	SELECT  SUM(CASE WHEN T2X.FlagKel=0 THEN Pemakaian  ELSE 0 END),
		l_PakaiKel=SUM(CASE WHEN T2X.FlagKel=1 THEN Pemakaian  ELSE 0 END)
	INTO    l_Pakai,
		l_PakaiKel	
	FROM T21PMEP T2X
	WHERE T2X.NIP = l_T10NIP AND T2X.Tahun = l_T21Tahun AND T2X.JenisMedical=l_T21JenisMedical;
        ---
	SELECT JatahMedical,
	       JatahMedicalKel 
	INTO   l_Jatah,
	       l_JatahKel	
	FROM S0CMEDH 
        WHERE NIP=l_T21NIP AND Tahun=l_T21Tahun AND JenisMedical=l_T21JenisMedical;
	--- Jika pegawai proporsional tdk di update
	SELECT CASE WHEN l_TglProses - M15X.TglMasuk <=360 THEN 1 ELSE 0 END
	INTO   l_mlProp
	FROM M15PEGA M15X		
	WHERE M15X.NIP=l_T10NIP;
	---
        IF (l_Jatah-l_Pakai)+(l_JatahKel-l_PakaiKel)<>0 AND l_mlProp=0 THEN 
	   BEGIN
	    -- Update Data ke S0C
	    IF (SELECT NIP FROM S0CMEDH WHERE NIP=l_T21NIP AND Tahun=l_T21Tahun AND JenisMedical=l_T21JenisMedical) IS NOT NULL AND 
		l_T20Plafon+l_T20PlafonKel<>0 THEN 
	       BEGIN
		 UPDATE S0CMEDH 
		 SET JatahMedical    = l_T20Plafon,
		     JatahMedicalKel = l_T20PlafonKel
		 WHERE NIP=l_T21NIP AND Tahun=l_T21Tahun AND JenisMedical=l_T21JenisMedical;
	       END;
	    END IF; 
	    --*
            END;
        END IF;     
        --*
    END LOOP;
    CLOSE l_LOOP_T21;
	--*
	-- Update Data M15PEGA
    IF (SELECT NIP FROM M15PEGA WHERE NIP=l_T10NIP) IS NOT NULL AND
       (l_T10KdKlas IS NOT NULL) AND 
       (l_T10KdCaba IS NOT NULL) AND 
       (l_T10KdUUsa IS NOT NULL) AND 
       (l_T10KdUKer IS NOT NULL) AND 
       (l_T10KdGlng IS NOT NULL) AND 
       (l_T10KdJaba IS NOT NULL) AND 
       (l_T10KdArea IS NOT NULL) AND 
       (l_T10KdCurr IS NOT NULL) THEN 
       BEGIN
         UPDATE M15PEGA 
         SET KdKlas		= l_T10KdKlas,
	     KdCaba		= l_T10KdCaba,
	     KdUUsa		= l_T10KdUUsa,
	     KdUKer		= l_T10KdUKer,
	     KdGlng		= l_T10KdGlng,
	     KdJaba		= l_T10KdJaba,
	     KdArea		= l_T10KdArea,
	     KdKJab		= l_T10KdKJab,                    
	     EncGajiTetap	= fn_kcabang(l_T10NIP,l_T10GajiPokok::VARCHAR(20),l_MyPass),
	     KdCurr		= l_T10KdCurr
         WHERE NIP=l_T10NIP;
       END;
    END IF; 
    --*
END LOOP;
CLOSE l_LOOP_T10;
END ;
$$ LANGUAGE plpgsql ;

/* TESTING ...
--
EXEC P_prsHRD l_NIP = '010',
	      l_Tgl_Akhir_x = '2002-12-20',
              l_TglProses = '2003-07-30',
              l_UserID = 'SUHE',
	      l_MyPass = 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
EXEC P_prsHRD l_NIP = '18',
	      l_Tgl_Akhir_x = '2006-04-20',
              l_TglProses = '2006-05-23',
              l_UserID = 'DIAH',
	      l_MyPass = 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
--
*/
