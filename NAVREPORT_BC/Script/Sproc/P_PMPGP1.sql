/****************************************
Name sprocs : P_PMPGP1
Create by   : wati
Date        : 16-06-2003
Description : Proses Mantan Pegawai
Call From   : Main sprocs
*****************************************/
--
DROP FUNCTION P_PMPGP1(l_TglProses  DATE,
			l_LokasiFr   VARCHAR(4),
			l_LokasiTo   VARCHAR(4), 
			l_NIPFr  	 VARCHAR(10),
			l_NIPTo  	 VARCHAR(10),
			l_KodeLDA    VARCHAR(2),
			l_UserID     VARCHAR(12),
			OUT l_Gagal      Int,
			OUT l_TableName1 VARCHAR(8),
			OUT l_mlValid    INT,
			l_MyPass     VARCHAR(128),
			l_Usr_ID     INT);
--
CREATE FUNCTION P_PMPGP1(l_TglProses  DATE,
			l_LokasiFr   VARCHAR(4),
			l_LokasiTo   VARCHAR(4), 
			l_NIPFr  	 VARCHAR(10),
			l_NIPTo  	 VARCHAR(10),
			l_KodeLDA    VARCHAR(2),
			l_UserID     VARCHAR(12),
			OUT l_Gagal      Int,
			OUT l_TableName1 VARCHAR(8),
			OUT l_mlValid    INT,
			l_MyPass     VARCHAR(128),
			l_Usr_ID     INT)

AS $$
--
DECLARE l_S05FlagTHR 	VARCHAR(1);     l_S05TglAkhir   DATE;
	l_Flag_R     	VARCHAR(1);     l_Cabang        VARCHAR(4);	l_FZ1FlgPiut   VARCHAR(1);
	l_VarRound   	VARCHAR(1);     l_V_Round    	INT;          	l_Prs_OK       VARCHAR(1);
	l_M15NIP     	VARCHAR(10);    l_M15Nama    	VARCHAR(25);    l_M15TglLahir  DATE;
	l_M15PrdAwl  	DATE;       	l_M15TglKeluar  DATE;     	l_M15kdCaba    VARCHAR(4);
	l_M15kdKlas  	VARCHAR(4);     l_M15TglMasuk   DATE;     	l_M15KdGlng    VARCHAR(4);
	l_M15MasaKerja  INT;            l_M15kdCurr  	VARCHAR(4);     l_M15PrdTetap  DATE;
        l_M15GajiTetap  DECIMAL(15,2);  l_M15GajiPerc   DECIMAL(15,2);	l_M15PJK       VARCHAR(1);
	l_M15TunjIst 	VARCHAR(1);     l_M15TunjAnak   VARCHAR(1);
	l_M15Status  	VARCHAR(1);     l_M15JnsKlmn 	VARCHAR(1);     l_M15kdJaba    VARCHAR(4);
	l_M15AnakTtgg   INT;            l_M15kdKJab  	VARCHAR(4);     l_M15THR       VARCHAR(1);
	l_M15Agama   	VARCHAR(1);     l_M15BlnTHR  	DECIMAL(5,2); 	l_FZ1TunjIst   DECIMAL(5,2);
	l_M15KdArea  	VARCHAR(4);     l_M15KdUUsa  	VARCHAR(4);     l_M15KdUKer    VARCHAR(8);
	l_M15StsPjk  	VARCHAR(2);     l_M15KPA     	VARCHAR(11);    l_M15Rkbnk     VARCHAR(20);
	l_M15AccHolder  VARCHAR(25);    l_M15BankRef 	VARCHAR(4);      
	l_FZ1FlgTHR  	VARCHAR(1);     l_M15NoSPSI  	VARCHAR(10);
	l_M15Pajak   	VARCHAR(1);     l_FZ1IntvPajak  DECIMAL(9,0); 
	l_M15kdCabaNEW  VARCHAR(4);     l_FZ1NlOccSupp  DECIMAL(15,2);  
        l_mnKurs     	DECIMAL(10,2);  l_FZ1PTKPPay 	DECIMAL(15,2);
	l_M15KdTerr 	VARCHAR(4);     l_FZ1PTKPDep 	DECIMAL(15,2);
	l_M15PrdAwal    DATE;           l_Sumber 	VARCHAR(6);
	l_LOOP_M15   	REFCURSOR;      l_JnsPajak      VARCHAR(1);       
        l_Curr_Income   DECIMAL(15,2);  l_Curr_kolom8   DECIMAL(15,2);  l_JnsPeg       VARCHAR(1);
        l_T19Pesangon   DECIMAL(15,2);  l_FZ1FlgGGol    VARCHAR(1);
        l_FZ1Personalia VARCHAR(1);     l_Test          VARCHAR(10);
        l_FilterDta     INT;            l_Gaji          DECIMAL(15,2);
        l_GajiPokok     DECIMAL(15,2);  l_T18HariKerja  VARCHAR(1);
        l_T18Jmlhari    DECIMAL(4,0);   l_M10JnsPajak   VARCHAR(1);         
        l_FZ1FlgGlng    VARCHAR(1);     l_FZ1FlgJaba    VARCHAR(1);
        l_FZ1JumlahMangkir INT;         l_FlgCabang     VARCHAR(1);
        l_DataValid     INT;            l_FilterData    INT;
        l_Created      	INT;            l_mnHari        DECIMAL(4,1);         
        l_GajiRp       	DECIMAL(15,2);  l_GajiVal       DECIMAL(15,2);
        l_M03KdDppt    	VARCHAR(4);     l_M03Singkatan  VARCHAR(10);
        l_M03UsComp    	VARCHAR(1);     l_M03Kolom   	VARCHAR(2);
        l_M03NoAcc     	VARCHAR(10);    l_M03Status     VARCHAR(1);
        l_M03Persen    	DECIMAL(5,2);   l_M03Nilai      DECIMAL(15,2);
        l_M03NoLyr     	INT;            l_M03KdCurr     VARCHAR(4);
        l_M03Pajak     	VARCHAR(1);     l_M03Flag_N  	VARCHAR(1);
        l_M03Flag_R    	VARCHAR(1);     l_TunjIstri     DECIMAL(15,2);
        l_TunjIstriVal 	DECIMAL(15,2);  l_Err           INT;
        l_FZ2FlagRapel 	INT;            l_FZ2FlRapelTHR INT;
        l_FZ2PrdRapel  	DATE;           l_FZ2THRharian  VARCHAR(1);
        l_M15BerhakLemb INT;            l_M15TglKPA     DATE;
        l_FZ1JKK       	VARCHAR(1);     l_FZ1JHT        VARCHAR(1);
        l_FZ1JKM       	VARCHAR(1);     l_FZ1JPK        VARCHAR(1);
        l_M15JKK       	VARCHAR(1);     l_M15Level      VARCHAR(1);
        l_M15JHT       	VARCHAR(1);     l_M15JKM        VARCHAR(1);
        l_M15JPK       	VARCHAR(1);     l_Level         VARCHAR(1); 
        l_FZ1IuranSPSI 	DECIMAL(5,2);   l_M15TglSPSI    DATE;
        l_NilaiSPSI    	DECIMAL(15,2);  l_NilaiSPSIVal  DECIMAL(15,2);
        l_NilaiPesangon DECIMAL(15,2);  l_NilaiPesangonVal DECIMAL(15,2);
        l_FlagTHR       VARCHAR(1);     l_M10Harian  	INT;
	l_FixIncome  	DECIMAL(15,2);  l_TunjAnak   	DECIMAL(15,2);
	l_TunjAnakVAL   DECIMAL(15,2);  l_EncTotInc  	DECIMAL(15,2);
	l_EncTotDed  	DECIMAL(15,2);  l_EncTakeHomePay DECIMAL(15,2);
	l_S01_ID	INT; 		l_S05_ID	INT; 
--
BEGIN 
--
l_mlValid:=1;
l_Gagal:=0;
--
-- Mengambil Jumlah Hari di FZ1FlDA
l_mnHari:=1;
--
SELECT CASE EXTRACT(MONTH FROM l_TglProses) 
		WHEN 1 THEN  Hari01
		WHEN 2 THEN  Hari02
		WHEN 3 THEN  Hari03
		WHEN 4 THEN  Hari04
		WHEN 5 THEN  Hari05
		WHEN 6 THEN  Hari06
		WHEN 7 THEN  Hari07
		WHEN 8 THEN  Hari08
		WHEN 9 THEN  Hari09
		WHEN 10 THEN Hari10
		WHEN 11 THEN Hari11
		WHEN 12 THEN Hari12  END
INTO l_mnHari             
FROM FZ1FLDA;
--
-- Panggil SProc untuk Bentuk File Validasi 
SELECT * FROM P_CREATE_TEMP_ONLIST ('W0B') INTO l_TableName1, l_Created;--,	               
--
IF l_Created=0 THEN 
   RETURN ;
END IF ;  

-- Ambil Nilai Rounding Data !!
l_V_Round :=CASE l_VarRound
                  WHEN '1' THEN 0
                  WHEN '2' THEN -1
                  WHEN '3' THEN -2
                  ELSE -3
             END;
-- Jika Proses Khusus maka FlagTHR='l_'
l_FlagTHR := '#';
--
-- Mengelola Range Cabang+Golongan dengan Security-nya
/*
IF l_FCAB>=l_LokasiFr AND l_TCAB<=l_LokasiTo
   BEGIN
      SELECT l_LokasiFr=l_FCAB,
         l_LokasiTo=l_TCAB    
   END  
ELSE IF l_FCAB<l_LokasiFr AND l_TCAB<=l_LokasiTo
   BEGIN
      SELECT l_LokasiTo=l_TCAB    
   END  
ELSE IF l_FCAB>=l_LokasiFr AND l_TCAB>l_LokasiTo
   BEGIN
      SELECT l_LokasiFr=l_FCAB
   END  
ELSE IF l_FCAB<l_LokasiFr AND l_TCAB>l_LokasiTo
   BEGIN
      SELECT l_LokasiFr=l_FCAB
   END  
--*
*/
--
OPEN l_LOOP_M15 FOR 
SELECT M15.NIP,M15.Nama,M15.TglLahir,M15.PrdAwl,M15.TglKeluar,M15.KdCaba,M15.KdKlas,
       M15.TglMasuk,M15.TglKeluar,M15.KdGlng,M15.MasaKerja,M15.KdCurr,M15.PrdTetap,
       fn_Kpusat(M15.NIP,M15.EncGajiTetap,l_MyPass) ::DECIMAL(15,2),
       fn_Kpusat(M15.NIP,M15.EncGajiPerc,l_MyPass)  ::DECIMAL(15,2),
       M15.Pajak,M15.TunjIst,M15.TunjAnak,M15.Status,
       M15.JnsKlmn,M15.KdJaba,M15.AnakTtgg,M15.KdKJab,M15.THR,M15.Agama,M15.BlnTHR,
       M15.KdArea,M15.KdUUsa,M15.KdUKer,M15.StsPjk,M15.KPA,M15.Rkbnk,M15.AccHolder,M15.BankRef,
       M15.NoSPSI,M15.KdTerr,M15.BerhakLemb,M15.TglKPA,M15.JKK,M15.Level,M15.JHT,M15.JKM,M15.JPK,
       M15.TglSPSI
FROM M15PEGA M15
INNER JOIN (SELECT * FROM fn_SECLOGIN(l_usr_id)) VSL 
ON M15.kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
      (KdCaba BETWEEN l_LokasiFr AND l_LokasiTo); --AND
--      (KdGlng BETWEEN l_FGOL AND l_TGOL);
      
<<LOOP_M15>> 
LOOP 
FETCH l_LOOP_M15 
INTO l_M15NIP,l_M15Nama,l_M15TglLahir,l_M15PrdAwl,l_M15TglKeluar,l_M15kdCaba,l_M15kdKlas,
     l_M15TglMasuk,l_M15TglKeluar,l_M15KdGlng,l_M15MasaKerja,l_M15kdCurr,l_M15PrdTetap,
     l_M15GajiTetap,l_M15GajiPerc,
     l_M15Pajak,l_M15TunjIst,l_M15TunjAnak,l_M15Status,
     l_M15JnsKlmn,l_M15kdJaba,l_M15AnakTtgg,l_M15kdKJab,l_M15THR,l_M15Agama,l_M15BlnTHR,
     l_M15KdArea,l_M15KdUUsa,l_M15KdUKer,l_M15StsPjk,l_M15KPA,l_M15Rkbnk,l_M15AccHolder,l_M15BankRef,
     l_M15NoSPSI,l_M15KdTerr,l_M15BerhakLemb,l_M15TglKPA,l_M15JKK,l_M15Level,l_M15JHT,l_M15JKM,l_M15JPK,
     l_M15TglSPSI;

	IF NOT FOUND THEN
		EXIT ;
	END IF;

----------- MULAI PROSES DATA -----------------
--
--    IF l_M15kdCaba BETWEEN l_FCAB AND l_TCAB THEN 
--       BEGIN
          --
          --l_S05TglAkhir := ' ';
          l_S05FlagTHR  := ' ';
          --
          -- Ambil Tgl.Akhir Proses Payroll
          SELECT TglPosting,    FlagTHR
          INTO   l_S05TglAkhir, l_S05FlagTHR
          FROM S05PSTD
	  WHERE NIP=l_M15NIP AND FlagTHR='#'
--          WHERE NIP=l_M15NIP 
          ORDER BY NIP,TglPosting;

          -- Validasi Data     
          SELECT fn_Validasi_MTG (l_S05FlagTHR,
                                  l_M15NIP,
                                  l_TglProses,
                                  l_M15TglMasuk,
                                  l_M15TglKeluar,
                                  l_S05TglAkhir)
	   INTO l_DataValid;

          IF l_DataValid<>0 then 
                l_mlValid := 0; 
                PERFORM P_CLSTEMP (l_M15NIP, l_M15Nama, l_S05TglAkhir,l_DataValid,l_TableName1) ;                      
          ELSE
             BEGIN
               -- Unpost Payroll
		PERFORM P_UPBLKH(l_TglProses,
				 l_LokasiFr,
				 l_LokasiTo,
				 l_M15NIP,
				 l_M15NIP,
				 'T'); 

               -- Akibat Unpost S05 Harus di ambil lagi
               --l_S05TglAkhir := ' ';
               l_S05FlagTHR  := ' ';

               -- Ambil Tgl.Akhir Proses Payroll
               SELECT TglPosting,    FlagTHR
               INTO   l_S05TglAkhir, l_S05FlagTHR
               FROM S05PSTD
	       WHERE NIP=l_M15NIP AND FlagTHR='#'
--               WHERE NIP=l_M15NIP 
               ORDER BY NIP,TglPosting;   

               -- Call Procedure Filter data
               SELECT Harian
               INTO   l_M10Harian
               FROM M10KLAS
               WHERE Kode=l_M15kdKlas;
               -- Jika Pegawai Harian Tdk Diproses
               l_FilterData := 0;
               --
               IF l_M10Harian=1 THEN 
                   l_FilterData := 7;           
               END IF;    
               -- Jika Ada     
               IF l_FilterData<>0 THEN 
                   l_mlValid := 0; 
                   PERFORM P_CLSTEMP (l_M15NIP, l_M15Nama, l_S05TglAkhir,l_FilterData,l_TableName1) ;                      
               ELSE 
                  BEGIN
                    -- Gaji Pokok
		    SELECT * from fn_GajiPokok(l_FZ1FlgGGol,
						l_TglProses,
						l_M15PrdTetap,
						l_M15GajiPerc,
						l_M15GajiTetap,
						l_M15NIP,
						l_M15TglMasuk,
						l_mnHari,
						l_M15KdGlng)
		    INTO l_GajiPokok ; 
                    --
                    -- Nilai Kurs
                    l_mnKurs := 1;
		    SELECT * from fn_GetKurs(l_M15kdCurr,l_TglProses)  
		    INTO l_mnKurs; 

                    -- Jenis Pajak
                    SELECT JnsPajak
                    INTO   l_M10JnsPajak
                    FROM  M10KLAS WHERE Kode=l_M15kdKlas; 

                    -- Ambil Nilai yg diperlukan u/Insert S02DGAJ
                    l_GajiRp  := l_GajiPokok*l_mnKurs;
                    l_GajiVal := l_GajiPokok;
                    -- Insert header S01HGAJ
                    --
                    IF (SELECT COUNT(TglPayr) FROM S01HGAJ WHERE TglPayr=l_TglProses AND NIP=l_M15NIP ) = 0 THEN 
                          INSERT INTO S01HGAJ(TglPayr,     NIP    ,  Nama    ,  KdKlas    ,  KdArea    ,  KdCaba    ,  KdUUsa    ,  KdUKer    ,  KdGlng    ,  KdKJab    ,  KdJaba    ,  StsPjk    ,  CommDate    ,  TermDate     ,  JnsKlmn    ,  StsSip    ,  TotAnak     ,  KPA    ,  AccHolder    ,  RkBnk    ,  BankRef    ,  Hari   ,  JnsPajak, NoAcc, NoSpt, TakeHomePay,  EncTakeHomePay		    , TotInc, TotDed, GrossIncomeNBYTD, OccSupport1, Col12YTD, EGIYNB, PTKP, EYTI, EYIT, YTDIT, IncTaxAPaidNB, GrossIncomeBYTD, OccSupport2, TaxTotal, IncTaxAPaidB, FlagTHR , AkmHari, FlagM, FlagH , TaxPesangonRp, Recs, EncTotInc			      , EncTotDed			   , EncGrossIncomeNBYTD	       , EncOccSupport1, 		      EncCol12YTD, 			   EncEGIYNB, 			       EncPTKP, 		           EncEYTI, 			       EncEYIT, 			   EncYTDIT, 			        EncIncTaxAPaidNB, 		    EncGrossIncomeBYTD, 		EncOccSupport2, 		    EncTaxTotal, 			EncIncTaxAPaidB, 		    EncTaxPesangonRp,                  VERSION, CREATED_BY, CREATED_ON,   UPDATED_BY, UPDATED_ON)
                                       VALUES(l_TglProses, l_M15NIP, l_M15Nama, l_M15kdKlas, l_M15KdArea, l_M15kdCaba, l_M15KdUUsa, l_M15KdUKer, l_M15KdGlng, l_M15kdKJab, l_M15kdJaba, l_M15StsPjk, l_M15TglMasuk, l_M15TglKeluar, l_M15JnsKlmn, l_M15Status, l_M15AnakTtgg, l_M15KPA, l_M15AccHolder, l_M15Rkbnk, l_M15BankRef, l_mnHari, 'Y'     ,  ' '  , ' '  , 0          , fn_kcabang(l_M15NIP, '0',l_MyPass), 0     , 0     , 0	              , 0          , 0       , 0     , 0   , 0   , 0   , 0    , 0            , 0              , 0          , 0       , 0	   , l_FlagTHR, 0     , ' '  , ' '   , 0	    , 0   , fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass) , fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass) , fn_kcabang(l_M15NIP, '0',l_MyPass) , fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass) , fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass), fn_kcabang(l_M15NIP, '0',l_MyPass),0      , l_userid,   localtimestamp, null,       null );
-- TotDed dihapus karena di belakang sudah ada EncTotDed. dan yang dipakai itu yang ada Enc-nya. maka valuenyapun di hapus

			   SELECT currval(pg_get_serial_sequence('s01hgaj', 's01_id'))
			   INTO l_S01_ID;

                           -- Jika Gagal Update
		           IF l_S01_ID IS NULL THEN 	           
			      l_Gagal := 1;
                              EXIT LOOP_M15 ; 
                           END IF;                            
                           -- Ambil Nilai dari M03DPPT
                    END IF;    
                    --*
                    -- Mantan Pegawai
                    IF (SELECT COUNT(NIP) FROM T23MPEG WHERE NIP=l_M15NIP AND (l_S05TglAkhir IS NULL OR Tanggal>l_S05TglAkhir) AND Tanggal<=l_TglProses) > 0 THEN 
                         PERFORM P_Mantan_Pegawai(l_M15NIP,
                                                  l_S05TglAkhir,
                                                  l_TglProses,
                                                  l_GajiRp,
                                                  l_mnHari,   
                                                  l_MyPass,
                                                  l_S01_ID); 
                    END IF;    
                    -- Hitung Pajak Pendapatan
                    -- Gross Current Income
                    l_Curr_Income := 0;
                    l_Curr_Kolom8 := 0;
                    SELECT SUM(fn_KPusat(l_M15NIP,EncNilai,l_Mypass) ::DECIMAL(15,2))
                    INTO   l_Curr_Income
                    FROM S02DGAJ
                    WHERE TglPayr=l_TglProses AND NIP=l_M15NIP AND  Kolom='1' AND FlgDPPt='D'
                    GROUP BY NIP;
                    -- Variable
                    SELECT COALESCE(l_Curr_Income,0)+SUM(fn_KPusat(l_M15NIP,EncNilai,l_Mypass) ::DECIMAL(15,2))
                    INTO   l_Curr_Income
                    FROM S02DGAJ
                    WHERE TglPayr=l_TglProses AND NIP=l_M15NIP AND  (Kolom BETWEEN '3' AND '6') AND FlgDPPt='D'
                    GROUP BY NIP;
                    -- Kolom8/Bonus
                    SELECT SUM(fn_KPusat(l_M15NIP,EncNilai,l_Mypass) ::DECIMAL(15,2))
                    INTO   l_Curr_Kolom8
                    FROM S02DGAJ
                    WHERE TglPayr=l_TglProses AND NIP=l_M15NIP AND Kolom='8' AND FlgDPPt='D'
                    GROUP BY NIP;
                    --
                    -- Jika Tdk Ada Pendapatan tidak perlu Hitung Pajak
                    IF COALESCE(l_Curr_Income,0)+COALESCE(l_Curr_Kolom8,0)<>0 THEN 
                          -- Get Jenis Pajak
                          l_JnsPajak := P_Jenis_Pjk (l_TglProses,
						      l_M15NIP) ;

                          -- Pegawai Type B
                              PERFORM P_Hitung_Pjk_TypeB(l_JnsPajak,
						l_TglProses,
						l_S05TglAkhir,  
						l_M15NIP, 
						l_MyPass,
						l_UserID,
						l_FlagTHR,
						'M',
						l_S01_ID) ; 
                    END IF; 

                    -- Jika S01 Tdk punya detail maka s01 harus dihapus
                    IF (SELECT COUNT(NIP) FROM S02DGAJ WHERE NIP=l_M15NIP AND TglPayr=l_TglProses) = 0 THEN 
                          DELETE FROM S01HGAJ
                          WHERE NIP=l_M15NIP AND TglPayr=l_TglProses ;
                    ELSE
                       BEGIN

/* Hanya proses payroll yang update tgl. proses..

                         -- Update Data (Update TglProses Payroll di M.Pegawai
                         UPDATE M15PEGA
                         SET TglPayr=l_TglProses
                         WHERE NIP=l_M15NIP
*/

                         -- Update Posting Terakhir di Summaty Posting
                         IF (SELECT COUNT(NIP) FROM S05PSTD WHERE NIP=l_M15NIP AND TglPosting=l_TglProses) > 0 THEN 
                              UPDATE S05PSTD
                              SET FlagTHR=l_FlagTHR
                              WHERE NIP=l_M15NIP AND TglPosting=l_TglProses; 

			      SELECT currval(pg_get_serial_sequence('s05pstd', 's05_id'))
			      INTO l_S05_ID;				     
				  			  
                              -- Jika Gagal Update
                              IF l_S05_ID IS NULL THEN 
				 l_Gagal := 1;
				 EXIT LOOP_M15 ;
                              END IF; 
                         ELSE
                              INSERT INTO S05PSTD(NIP,    TglPosting,FlagTHR)
                                          VALUES (l_M15NIP,l_TglProses,l_FlagTHR);

			      SELECT currval(pg_get_serial_sequence('s05pstd', 's05_id'))
			      INTO l_S05_ID;				     
				  
                              -- Jika Gagal Update
                              IF l_S05_ID IS NULL THEN 
				  l_Gagal := 1;
				  EXIT LOOP_M15 ;
                              END IF;                                               
			 END IF; 
			 
                         -- Update TakeHomePay di Header Penggajian
                         l_EncTotInc := 0;
                         l_EncTotDed := 0;
                         l_EncTakeHomePay := 0;
                         --  
                         SELECT SUM((CASE WHEN S02.FlgDpPt='D' THEN fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) ELSE 0 END) ::DECIMAL(15,2)),
				SUM((CASE WHEN S02.FlgDpPt='P' THEN fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) ELSE 0 END) ::DECIMAL(15,2))  
			 INTO   l_EncTotInc, 
				l_EncTotDed
                         -- 
                         FROM S02DGAJ S02
                         WHERE  TglPayr=l_TglProses AND NIP=l_M15NIP                 
                         GROUP BY TglPayr,NIP;
                         -- 
                         l_EncTakeHomePay := l_EncTotInc-l_EncTotDed;
                         --
                         UPDATE S01HGAJ
                         SET EncTotInc     =fn_kcabang(l_M15NIP,l_EncTotInc ::VARCHAR(17),l_MyPass),
                             EncTotDed     =fn_kcabang(l_M15NIP,l_EncTotDed ::VARCHAR(17),l_MyPass),
                             EncTakeHomePay=fn_kcabang(l_M15NIP,l_EncTakeHomePay ::VARCHAR(17),l_MyPass)
                         WHERE  TglPayr=l_TglProses  AND  NIP=l_M15NIP ;
                           --
                       END ;
                    END IF;   
                    --*             
                  -- End Of Filter
                  END;
               END IF;
             -- End Of Validasi          
             END;
          END IF;    
         --
--       END; -- END Validasi Cabang
--    END IF;   
    --*
    --------------******* END PROSES DATA   ********----------
     --Akhir Loop 
--*
--
END LOOP;
CLOSE l_LOOP_M15;

END;
$$ LANGUAGE plpgsql ;

/*
SELECT l_mlValid, l_TableName1
FROM  P_UTHR('2013-12-04' :: DATE, ' ', 'ZZZZ', 'U', 'U', ' ', 'ZZZ', ' ', 'ZZZ', 'ok');

SELECT * FROM S01HGAJ
WHERE NIP='U';
SELECT * FROM S02DGAJ
WHERE NIP='U';

SELECT l_Gagal,l_TableName1,l_mlValid
FROM P_PMPGP1('2013-12-04', '', 'ZZZZ', 'U', 'U', '01', 'suhe','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1) ; 
*/


