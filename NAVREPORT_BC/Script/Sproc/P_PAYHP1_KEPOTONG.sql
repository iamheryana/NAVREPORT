/****************************************
Name sprocs : P_PAYHP1
Create by   : wati
Date        : 07-07-2003
Description : Proses Harian Payroll
Call From   : Main sprocs
updated by peggy 2006 09 23 : nambahin range parameter uker. 

*****************************************/
CREATE OR REPLACE FUNCTION  P_PAYHP1(l_TglProses  DATE,
					l_LokasiFr   VARCHAR(4),
					l_LokasiTo   VARCHAR(4), 
					l_ukerFr     VARCHAR(8),
					l_ukerTo     VARCHAR(8), 
					l_NIPFr      VARCHAR(10),
					l_NIPTo      VARCHAR(10),
					l_HKerja     DECIMAL(4,1),
					l_AkhirBln   INT, 
					l_FGOL       VARCHAR(4),
					l_TGOL       VARCHAR(4),    
					l_FCAB       VARCHAR(4),
					l_TCAB       VARCHAR(4),    
					l_KodeLDA    VARCHAR(2),
					l_UserID     VARCHAR(12),
					l_Gagal      Int OUTPUT,
					l_TableName1 VARCHAR(8) OUTPUT,
					l_mlValid    INT OUTPUT,
					l_MyPass     VARCHAR(128))
RETURNS VOID 
AS $$
--
l_mlValid := 1;
--
DECLARE l_S05FlagTHR 	VARCHAR(1);       	l_S05TglAkhir   DATE;
	l_Flag_R     	VARCHAR(1);       	l_Cabang        VARCHAR(4);  		
	l_FZ1FlgPiut 	VARCHAR(1);		l_Prs_OK     	VARCHAR(1);
	l_VarRound   	VARCHAR(1);       	l_V_Round    	INT;            
	l_FZ1FlgLemb 	VARCHAR(1);       	l_FZ1TunjAnk 	DECIMAL(15,2);  
	l_FZ1FlgKJab 	VARCHAR(1);       	l_FZ1Caba    	VARCHAR(1);      
	l_FZ1TglTHRL 	DATE;      		l_FZ1TglTHRN 	DATE;
	l_FZ1TglHakL 	DATE;      		l_FZ1TglHakN 	DATE;
	l_M15NIP     	VARCHAR(10);      	l_M15Nama    	VARCHAR(25);     	
	l_M15TglLahir  	DATE;
	l_M15TglMasuk   DATE;			l_M15TglKeluar  DATE;  		
	l_M15PrdAwl  	DATE;			l_M15PrdTetap  	DATE;
	l_M15kdCaba  	VARCHAR(4);		l_M15KdUKer    	VARCHAR(8);
	l_M15kdKlas  	VARCHAR(4);		l_M15KdGlng    	VARCHAR(4);
	l_M15KdArea  	VARCHAR(4);       	l_M15KdUUsa  	VARCHAR(4);      	
	l_M15MasaKerja  INT;            	l_M15kdCurr  	VARCHAR(4);      	
	l_M15GajiTetap  DECIMAL(15,2);  	l_M15GajiPerc   DECIMAL(15,2);	
	l_M15PJK      	VARCHAR(1);    		l_M15Agama   	VARCHAR(1); 
	l_M15TunjIst 	VARCHAR(1);       	l_M15Tunj    	VARCHAR(1);
	l_M15Status  	VARCHAR(1);       	l_M15JnsKlmn 	VARCHAR(1);      	
	l_M15kdJaba    	VARCHAR(4);		l_M15kdKJab  	VARCHAR(4);      	  	
	l_M15THR       	VARCHAR(1);		l_M15BlnTHR  	DECIMAL(5,2); 	
	l_FZ1TunjIst   	DECIMAL(5,2);		l_M15AnakTtgg   INT;            
	l_M15StsPjk  	VARCHAR(2);       	l_M15KPA     	VARCHAR(11);     	
	l_M15Rkbnk 	VARCHAR(20);
	l_M15AccHolder  VARCHAR(25);       	l_M15BankRef 	VARCHAR(4);      
	l_FZ1FlgTHR  	VARCHAR(1);       	l_M15NoSPSI  	VARCHAR(10);
	l_M15Pajak   	VARCHAR(1);       	l_FZ1IntvPajak  DECIMAL(9,0); 	
	l_FZ1PrOccSupp 	DECIMAL(5,2);		l_FZ1NlOccSupp  DECIMAL(15,2);  
	l_M15kdCabaNEW  VARCHAR(4);  	 	
	l_mnKurs     	DECIMAL(10,2); 		l_FZ1PTKPPay 	DECIMAL(15,2);
	l_M15KdTerr 	VARCHAR(4);        	l_FZ1PTKPDep 	DECIMAL(15,2);
	l_M15PrdAwal   	DATE;       		l_Sumber 	VARCHAR(6);
	l_LOOP_M15   	REFCURSOR,       	l_JnsPajak      VARCHAR(1);       
	l_Curr_Income  	DECIMAL(15,2);  	_Curr_kolom8    DECIMAL(15,2); 	
	l_JnsPeg       	VARCHAR(1);
	l_T19Pesangon  	DECIMAL(15,2);  	l_FZ1FlgGGol    VARCHAR(1);
	l_FZ1Personalia VARCHAR(1);        	l_Test          VARCHAR(10);
	l_FilterDta     INT;        		l_Gaji          DECIMAL(15,2);
	l_GajiPokok     DECIMAL(15,2);  	l_T18HariKerja  VARCHAR(1);
	l_T18Jmlhari    DECIMAL(4,0);   	l_M10JnsPajak   VARCHAR(1);         
	l_FZ1FlgGlng    VARCHAR(1);        	l_FZ1FlgJaba    VARCHAR(1);
	l_FZ1JumlahMangkir INT;      		l_FlgCabang     VARCHAR(1);
	l_DataValid     INT;        		l_FilterData    INT;
	l_Created      	INT;        		l_mnHari        DECIMAL(4,1);         
	l_GajiRp       	DECIMAL(15,2);  	l_GajiVal       DECIMAL(15,2);
	l_M03KdDppt    	VARCHAR(4);        	l_M03Singkatan  VARCHAR(10);
	l_M03UsComp    	VARCHAR(1);        	l_M03Kolom   	VARCHAR(2);
	l_M03NoAcc     	VARCHAR(10);       	l_M03Status     VARCHAR(1);
	l_M03Persen    	DECIMAL(5,2);   	l_M03Nilai      DECIMAL(15,2);
	l_M03NoLyr     	INT;        		l_M03KdCurr     VARCHAR(4);
	l_M03Pajak     	VARCHAR(1);        	l_M03Flag_N  	VARCHAR(1);
	l_M03Flag_R    	VARCHAR(1);        	l_TunjIstri     DECIMAL(15,2);
	l_TunjIstriVal 	DECIMAL(15,2);  	l_Err           INT;
	l_FZ2FlagRapel 	INT;        		l_FZ2FlRapelTHR INT;
	l_FZ2PrdRapel  	DATE;       		l_FZ2THRharian  VARCHAR(1);
	l_M15BerhakLemb INT;        		l_M15TglKPA     DATE;
	l_FZ1JKK       	VARCHAR(1);        	l_FZ1JHT        VARCHAR(1);
	l_FZ1JKM       	VARCHAR(1);        	l_FZ1JPK        VARCHAR(1);
	l_M15JKK       	VARCHAR(1);        	l_M15Level      VARCHAR(1);
	l_M15JHT       	VARCHAR(1);        	l_M15JKM        VARCHAR(1);
	l_M15JPK       	VARCHAR(1);        	l_Level         VARCHAR(1); 
	l_FZ1IuranSPSI 	DECIMAL(5,2);   	l_M15TglSPSI    DATE;
	l_NilaiSPSI    	DECIMAL(15,2);  	l_NilaiSPSIVal  DECIMAL(15,2);
	l_NilaiPesangon DECIMAL(15,2);  	l_NilaiPesangonVal DECIMAL(15,2);
	l_Cab01        	VARCHAR(4);        	l_Cab02      	VARCHAR(4);
	l_Cab03        	VARCHAR(4);        	l_Cab04      	VARCHAR(4);
	l_Cab05        	VARCHAR(4);        	l_Cab06      	VARCHAR(4);
	l_Cab07        	VARCHAR(4);        	l_Cab08      	VARCHAR(4);
	l_Cab09        	VARCHAR(4);        	l_Cab10      	VARCHAR(4);
	l_Cab11        	VARCHAR(4);        	l_Cab12      	VARCHAR(4);
	l_FlagTHR      	VARCHAR(1);        	l_Bulan         VARCHAR(2);
	l_M15kdCaba1  	VARCHAR(4);
	l_Tahun        	DECIMAL(4,0);   	l_Tahun1        DECIMAL(4,0);  
	l_QPara        	VARCHAR(4000); 		l_QDesc         VARCHAR(4000);
	l_NIP1       	VARCHAR(10);      	l_NewKdCaba1    VARCHAR(4);
	l_FixIncome  	DECIMAL(15,2);
	l_TunjAnak   	DECIMAL(15,2);
	l_TunjAnakVAL   DECIMAL(15,2);
	l_EncTotInc  	DECIMAL(15,2);
	l_EncTotDed  	DECIMAL(15,2);
	l_EncTakeHomePay DECIMAL(15,2);
	l_M10Harian  	INT;       		l_FlagKhusus     VARCHAR(1); 
	l_POTHari       DECIMAL(4,1);		l_FZ1NlTunjAnak  DECIMAL(15,2);
	l_PLUSHari      DECIMAL(4,1); 		l_M15TunjAnak    VARCHAR(1);
	l_hit		INT;
	l_FZ2RNDTHPPOT	VARCHAR(1);        	l_FZ2THPROUND	 VARCHAR(1); 
	l_FZ2FlgAdv 	VARCHAR(1);		l_S01_ID	 INT; 
	l_S02_ID	 INT;

BEGIN
-- khusus pendapatan/potongan dasar kelompok advance 
CREATE TEMP TABLE l_TEMP (Level1	VARCHAR(1), 
			  Kode1		VARCHAR(8), 
			  Level2	VARCHAR(1), 
			  Kode2		VARCHAR(8), 
			  M65Persen	DECIMAL(5,2), 
			  M65Nilai	DECIMAL(15,2), 
			  M65KdCurr	VARCHAR(4),
			  M65Trans	VARCHAR(1),
			  M03Persen	DECIMAL(5,2), 
			  M03Nilai	DECIMAL(15,2), 
			  M03KdCurr	VARCHAR(4),
			  NoUrut	SERIAL) ON COMMIT DROP ; 


--
l_Gagal := 0;
-- Mengambil Jumlah Hari di FZ1FlDA
-- BY FRANS 2006 12 28 : mengambil nilai NlTunjAnak dari tabel FZ1FLDA
SELECT JKK,      JHT,      JKM,      JPK,      NlTunjAnak,      Personalia,      FlgGGol	
INTO   l_FZ1JKK, l_FZ1JHT, l_FZ1JKM, l_FZ1JPK, l_FZ1NlTunjAnak, l_FZ1Personalia, l_FZ1FlgGGol
FROM FZ1FLDA;
-- Mengambil Jumlah hari dari S/P Jumlah Hari Kerja
-- BY PEGGY 2006 08 11 : JML HARI KERJA DIAMBIL DARI TRANS HARIAN ... 
--SET l_mnHari=l_HKerja
l_mnHari := 0;
l_HKerja := 0;
--
SELECT CASE SUBSTR(StringFlag,2,1) WHEN '1' THEN 'Y' ELSE 'T' END,
	CASE SUBSTR(StringFlag,1,1) WHEN '1' THEN 'Y' ELSE 'T' END,
	FlagRapel,
	FlRapelTHR,
	PeriodeRapel,
	SUBSTR(StringFlag,8,1),
	SUBSTR(StringFlag,9,1)
INTO l_FlgCabang,
	l_FZ2THRharian,
	l_FZ2FlagRapel,
	l_FZ2FlRapelTHR,
	l_FZ2PrdRapel,
	l_FZ2RNDTHPPOT, 
	l_FZ2THPROUND
FROM FZ2FLDA
WHERE Kode=l_KodeLDA;

-- Panggil SProc untuk Bentuk File Validasi 
SELECT  P_CREATE_TEMP_ONLIST ('W0B') INTO l_TableName1, l_Created;--,	               
--
IF l_Created=0 THEN 
   RETURN ;
END IF ;    

-- Ambil Nilai Rounding Data !!
l_V_Round := CASE l_VarRound
                  WHEN '1' THEN 0
                  WHEN '2' THEN -1
                  WHEN '3' THEN -2
                  ELSE -3
             END;
-- Jika Proses Khusus maka FlagTHR='l_'
l_FlagTHR    := ' ';
l_FlagKhusus := 'T';
l_hit        :=  1;
--
--
--BEGIN TRAN
----------- MULAI PROSES DATA -----------------
OPEN l_LOOP_M15 FOR EXECUTE('SELECT M15.NIP,M15.Nama,M15.TglLahir,M15.PrdAwl,M15.TglKeluar,
				   COALESCE(T10.Kdcaba,M15.KdCaba) AS KdCaba,
				   COALESCE(T10.KdKlas,M15.KdKlas) AS KdKlas,
			           M15.TglMasuk,M15.TglKeluar,
				   COALESCE(T10.KdGlng,M15.KdGlng) AS KdGlng,
				   M15.MasaKerja,
				   COALESCE(T10.KdCurr,M15.KdCurr) AS KdCurr,
				   M15.PrdTetap,
			           COALESCE(T10.GajiPokok,fn_Kpusat(M15.NIP,M15.EncGajiTetap,l_MyPass) ::DECIMAL(15,2)) AS A,
			           fn_Kpusat(M15.NIP,M15.EncGajiPerc,l_MyPass) ::DECIMAL(15,2) AS B,
			           M15.Pajak,M15.TunjIst,M15.TunjAnak,M15.Status,M15.JnsKlmn,
				   COALESCE(T10.KdJaba,M15.KdJaba) AS KdJaba,
				   M15.AnakTtgg,
				   COALESCE(T10.KdKJab,M15.KdKJab) AS KdKJab,
				   M15.THR,M15.Agama,M15.BlnTHR,
			           COALESCE(T10.KdArea,M15.KdArea) AS KdArea,
				   COALESCE(T10.KdUUsa,M15.KdUUsa) AS KdUUsa,
				   COALESCE(T10.KdUKer,M15.KdUKer) AS KdUKer,
				   M15.StsPjk,M15.KPA,M15.Rkbnk,M15.AccHolder,M15.BankRef,
			           M15.NoSPSI,M15.KdTerr,M15.BerhakLemb,M15.TglKPA,
				   M15.JKK,M15.Level,M15.JHT,M15.JKM,M15.JPK,
			           M15.TglSPSI
			FROM M15PEGA M15
			LEFT JOIN T10MUTA T10 
			ON M15.NIP=T10.NIP AND 
			   T10.TGLEFF = (SELECT MAX(X10.TGLEFF) 
					 FROM T10MUTA X10 
					 WHERE X10.TGLEFF <= l_TGLPROSES AND M15.NIP = X10.NIP
					 GROUP BY X10.NIP) 
			LEFT JOIN M10KLAS M10 
			ON M10.Kode = COALESCE(T10.KdKlas,M15.KdKlas) 
			WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
			      (COALESCE(T10.Kdcaba,M15.KdCaba) BETWEEN l_LokasiFr AND l_LokasiTo) AND
			      (COALESCE(T10.KdGlng,M15.KdGlng) BETWEEN l_FGOL AND l_TGOL)  AND 
			      (COALESCE(T10.KdUKer,M15.KdUKer) BETWEEN l_UKerFr AND l_UKerTo )');
<<LOOP_M15>> 
LOOP 
   FETCH l_LOOP_M15 INTO l_M15NIP,l_M15Nama,l_M15TglLahir,l_M15PrdAwl,l_M15TglKeluar,l_M15kdCaba,l_M15kdKlas,
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

    IF l_M15kdCaba BETWEEN l_FCAB AND l_TCAB THEN 
       BEGIN
	  -- 
          l_S05TglAkhir := ' ';
          l_S05FlagTHR  := ' ';
          --
          -- Ambil Tgl.Akhir Proses Payroll
          SELECT TglPosting,    FlagTHR
	  INTO   l_S05TglAkhir, l_S05FlagTHR
          FROM S05PSTD
          WHERE NIP=l_M15NIP AND FlagTHR=' '
-- nggak tau kenapa sebelumnya, yang betul adalah where flagthr = blank 
-- problem terjadi karena ada lembur tgl 28 dan 29, ada proses khusus tgl 30, 
-- waktu proses payroll tgl 4, lemburan tsb nggak masuk. 
--          WHERE NIP=l_M15NIP 
          ORDER BY NIP,TglPosting;
          --
	  -- nambah dari tr harian  by peggy 2006 06 07 
	  SELECT COALESCE(SUM(JMLABSN),0) 
	  INTO   l_PotHari
	  FROM T02ABSN 
	  WHERE NIP = l_M15NIP AND FLGDPPT = 'D' AND KDDPPT ='BSAL' AND 
	       TANGGAL BETWEEN S05TGLAKHIR + INTERVAL '1 day' AND l_TGLPROSES AND FLAG = 1 ;

	  SELECT COALESCE(SUM(JMLABSN),0) 
	  INTO   l_PlusHari 
	  FROM T02ABSN 
	  WHERE NIP = l_M15NIP AND FLGDPPT = 'D' AND KDDPPT ='BSAL' AND 
	       TANGGAL BETWEEN S05TGLAKHIR + INTERVAL '1 day' AND l_TGLPROSES AND FLAG = 0 ;

	  l_mnHari := l_PlusHari - l_PotHari ;
	  l_HKerja := l_PlusHari - l_PotHari ;
	  -- 
	
          -- Validasi Data     
          SELECT l_DataValid=fn_Validasi_Data(l_FlagKhusus,
                 				 l_S05FlagTHR,
                 				 l_M15NIP,
                 				 l_TglProses,
                 				 l_M15TglMasuk,
                 				 l_M15TglKeluar,
                 				 l_S05TglAkhir);
          
          IF l_DataValid<>0 THEN 
             BEGIN
               -- Jika peg. belum aktif tdk perlu masuk ke list. validasi
               IF l_DataValid<>1 THEN 
                  BEGIN
                    l_mlValid := 0;
                    SELECT P_CLSTEMP (l_M15NIP, l_M15Nama, l_S05TglAkhir,l_DataValid,l_TableName1) ; 
                  END;
	       END IF; 
               --*
             END;       
          ELSE
             BEGIN
               -- Call Procedure Filter data
               SELECT Harian
	       INTO l_M10Harian
               FROM M10KLAS
               WHERE Kode=l_M15kdKlas;
               -- Jika Pegawai Harian Tdk Diproses
               l_FilterData := 0;
               IF l_M10Harian=0 THEN 
                  BEGIN 
                    l_FilterData := 11;
                  END;
	       END IF; 
               --* 
               IF l_FilterData<>0 THEN 
                  BEGIN
                    l_mlValid := 0; 
                    SELECT P_CLSTEMP (l_M15NIP, l_M15Nama, l_S05TglAkhir,l_FilterData,l_TableName1) ; 
                  END;       
               ELSE 
                  BEGIN
	  -- 
               -- Unpost Payroll
		    SELECT P_UPBLKH(l_TglProses,
		  		    l_LokasiFr,
				    l_LokasiTo,
				    l_M15NIP,
				    l_M15NIP,
				    'Y'); 

               -- Akibat Unpost S05 Harus di ambil lagi
                    l_S05TglAkhir := ' ';
                    l_S05FlagTHR  := ' ';
               --
               -- Ambil Tgl.Akhir Proses Payroll
                    SELECT TglPosting,    FlagTHR
                    INTO   l_S05TglAkhir, l_S05FlagTHR
                    FROM S05PSTD
	            WHERE NIP=l_M15NIP AND FlagTHR=' '
-- nggak tau kenapa sebelumnya, yang betul adalah where flagthr = blank 
-- problem terjadi karena ada lembur tgl 28 dan 29, ada proses khusus tgl 30, 
-- waktu proses payroll tgl 4, lemburan tsb nggak masuk. 
--               WHERE NIP=l_M15NIP 
                    ORDER BY NIP,TglPosting  ;

                -- post efektif status 
                 -- 
                 SELECT P_EFSTP1(l_M15NIP,
				l_S05TglAkhir,
				l_TglProses,
				l_UserID,
				l_MyPass) ;
                 --

-- BY PEGGY 2007 01 10 : PEGAWAI HARIAN DIPROSES HRD JUGA DONKZ 
                -- Fasilitas Modul HRD        
		-- post hrd hanya jika bukan proses khusus        
                IF l_FZ1Personalia='Y' and l_FlagKhusus='T' THEN 
                   BEGIN
                     -- 
                     SELECT P_prsHRD(l_M15NIP,
					l_S05TglAkhir,
					l_TglProses,
					l_UserID,
					l_MyPass); 
                     --
                     -- Ambil Ulang Posisi Pegawai Terakhir
                     SELECT kdKlas,      kdCaba,      KdUUsa,        KdUKer,      KdGlng,      kdJaba,      KdArea,      kdKJab,
			    fn_Kpusat(NIP,EncGajiTetap,l_MyPass) ::DECIMAL(15,2),
			    kdCurr,      Status,      AnakTtgg,      StsPjk
                     INTO   l_M15kdKlas, l_M15kdCaba, l_M15KdUUsa,   l_M15KdUKer, l_M15KdGlng, l_M15kdJaba, l_M15KdArea, l_M15kdKJab,
			    l_M15GajiTetap,
			    l_M15kdCurr, l_M15Status, l_M15AnakTtgg, l_M15StsPjk
                     FROM M15PEGA
                     WHERE NIP=l_M15NIP ;
                     --
                   END;
		END IF; 
-- END BY PEGGY 2007 01 10 : PEGAWAI HARIAN DIPROSES HRD JUGA DONKZ 

                    -- Gaji Pokok
                    SELECT fn_GajiHarian(l_TglProses,  
					l_S05TglAkhir,
					l_M15NIP, 
					l_M15PrdTetap,     
					l_M15GajiPerc,
					l_M15GajiTetap,
					l_HKerja,
					l_FZ1FlgGGol, -- + BY PEGGY 2007 01 19 
					l_M15TglMasuk,-- + BY PEGGY 2007 01 19 
					l_M15KdGlng)  -- + BY PEGGY 2007 01 19 		
		    INTO l_GajiPokok ; 
                    --
                    -- Nilai Kurs
                    l_mnKurs := 1;
                    l_mnKurs := fn_GetKurs(l_M15kdCurr,l_TglProses); 

                    -- Jenis Pajak
                    SELECT JnsPajak
                    INTO   l_M10JnsPajak
                    FROM  M10KLAS WHERE Kode=l_M15kdKlas;

                    -- Ambil Nilai yg diperlukan u/Insert S02DGAJ
                    l_GajiRp  := fn_Vround(l_GajiPokok*l_mnKurs);
                    l_GajiVal := fn_Vround(l_GajiPokok);

                    -- Insert header S01HGAJ
                    --
-- 			Select TglPayr, GrossIncomeNBYTD=CONVERT(DECIMAL(15,2),Fn_Kpusat(NIP,EncGrossIncomeNBYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'))
-- 			from s01hgaj
-- 			where nip = 5

                    IF (SELECT TglPayr FROM S01HGAJ WHERE TglPayr=l_TglProses AND NIP=l_M15NIP ) IS NULL THEN 
                       BEGIN
                          INSERT INTO S01HGAJ(TglPayr    ,NIP     ,Nama     ,KdKlas     ,KdArea     ,KdCaba     ,KdUUsa     ,KdUKer     ,KdGlng     ,KdKJab     ,KdJaba     ,StsPjk     ,CommDate     ,TermDate      ,JnsKlmn      ,StsSip    ,TotAnak      ,KPA     ,AccHolder     ,RkBnk     ,BankRef     ,Hari    ,JnsPajak     ,NoAcc ,NoSpt,TakeHomePay,EncTakeHomePay                    ,TotInc,TotDed,GrossIncomeNBYTD,OccSupport1,Col12YTD,EGIYNB,PTKP,EYTI,EYIT,YTDIT,IncTaxAPaidNB,GrossIncomeBYTD,OccSupport2,TaxTotal,IncTaxAPaidB,FlagTHR,AkmHari,FlagM,FlagH ,TaxPesangonRp,Recs,EncTotInc                          ,EncTotDed			       ,EncGrossIncomeNBYTD		   , EncOccSupport1			, EncCol12YTD			     , EncEGIYNB, 			   EncPTKP, 			       EncEYTI, 			   EncEYIT, 	 		       EncYTDIT, 			   EncIncTaxAPaidNB, 		       EncGrossIncomeBYTD, 		   EncOccSupport2, 		       EncTaxTotal, 			   EncIncTaxAPaidB, 		       EncTaxPesangonRp,                  version, created_by, created_on,   updated_by, updated_on))
                                       VALUES(l_TglProses,l_M15NIP,l_M15Nama,l_M15kdKlas,l_M15KdArea,l_M15kdCaba,l_M15KdUUsa,l_M15KdUKer,l_M15KdGlng,l_M15kdKJab,l_M15kdJaba,l_M15StsPjk,l_M15TglMasuk,l_M15TglKeluar,l_M15JnsKlmn,l_M15Status,l_M15AnakTtgg,l_M15KPA,l_M15AccHolder,l_M15Rkbnk,l_M15BankRef,l_mnHari,l_M10JnsPajak, ' '   ,' ' ,0          ,fn_kcabang(l_M15NIP, '0',l_MyPass),0     ,0     ,0               ,0          ,0       ,0     ,0   ,0   ,0   ,0    ,0            ,0              ,0          ,0       ,0           ,l_FlagTHR,0      ,' '  ,' '  ,0            ,0  ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) , fn_kcabang(l_M15NIP, '0',l_MyPass) , fn_kcabang(l_M15NIP, '0',l_MyPass) , fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass),0      , l_userid,   current_time, null,       null )
			  RETURNING S01_ID AS l_S01_ID ; 

		          IF l_S01_ID IS NULL THEN 
			     l_Gagal := 1;
                             EXIT LOOP_M15 ; 
                          END IF;                            
                       END  
                    END IF; 
                    --*
                    --- Insert Gaji 
                    IF l_FlagKhusus='T' THEN 
                       BEGIN
			  SELECT  P_M03AllField ('D', 'BSAL' )
			  INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
				  l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;
                              -- Insert Gaji  
                          INSERT INTO S02DGAJ(TglPayr    ,NIP     ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                              ,Nilai,KdCurr     ,EncNilaiVal                                              ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                                       VALUES(l_TglProses,l_M15NIP,'D'    ,'BSAL',' '    ,l_M03Singkatan,fn_kcabang(l_M15NIP,(l_GajiRp :: VARCHAR(17)),l_MyPass),0    ,l_M15kdCurr,fn_kCabang(l_M15NIP,(l_GajiVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);
                        END; 
                    END IF; 

                    -- Tunjangan Istri 
                    IF COALESCE(l_FZ1TunjIst,0)<>0 AND l_M15TunjIst='Y' AND 
                       l_M15JnsKlmn='L' AND l_M15Status='K' AND l_FlagKhusus='T' THEN 
                       BEGIN
			  SELECT  P_M03AllField ('D', 'TK01' )
			  INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
				  l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;                    
                         --
                          l_TunjIstri    := fn_Vround((l_FZ1TunjIst*l_GajiRp)/100);
                          l_TunjIstriVal := fn_Vround(((l_FZ1TunjIst*l_GajiRp)/100)/fn_GetKurs(l_M03KdCurr,l_TglProses)); 

                         -- Insert/Update Tunjangan Istri
                         IF  (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND 
							    NIP=l_M15NIP AND KdDpPt='TK01') IS NOT NULL THEN 
                            BEGIN
                              UPDATE S02DGAJ
                              SET KdCurr      = l_M03KdCurr,
                                  EncNilaiVal = fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_TunjIstriVal) :: VARCHAR(17),l_MyPass),
                                  EncNilai    = fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP,EncNilai,l_MyPass) :: Decimal(15,2)+l_TunjIstri) :: VARCHAR(17), l_MyPass)
                              WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='TK01'
                              RETURNING S02_ID AS l_S02_ID ;

                              -- Jika Gagal Update
			      IF l_S02_ID IS NULL THEN 
			         l_Gagal := 1;
			         EXIT LOOP_M15 ; 
			      END IF;                            
                            END;
                         ELSE
                            BEGIN
                              INSERT INTO S02DGAJ(TglPayr    ,NIP     ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                   ,Nilai,KdCurr     ,EncNilaiVal                                                  ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,    FlgNonGL, VERSION, CREATED_BY, CREATED_ON,   UPDATED_BY, UPDATED_ON)
                                           VALUES(l_TglProses,l_M15NIP,'D'    ,'TK01',' '    ,l_M03Singkatan,fn_kcabang(l_M15NIP, (l_TunjIstri :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_M15NIP,(l_TunjIstriVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0,       0      , l_userid,   current_time, null,       null )   
                              RETURNING S02_ID AS l_S02_ID ;

                              -- Jika Gagal Update
			      IF l_S02_ID IS NULL THEN 
			         l_Gagal := 1;
			         EXIT LOOP_M15 ; 
			      END IF;                            
                            END;  
                          END IF;                       
                       END; 
                    END IF; 

                    -- Tunjangan Anak                
                    IF COALESCE(l_FZ1TunjAnk,0)<>0 AND l_M15TunjAnak='Y' AND 
                       ((l_M15JnsKlmn='L' AND (l_M15Status='K' OR l_M15Status='D')) OR 
                       (l_M15JnsKlmn='P' AND l_M15Status='X')) AND l_M15AnakTtgg > 0 AND l_FlagKhusus='T' THEN 
                       BEGIN
                         -- Hitung Nilai Tunjangan Anak                       l_M15AnakTtgg,l_FZ1TunjAnk,l_FZ1NlTunjAnak,l_GajiRp
                         SELECT fn_Vround(fn_Tunjangan_Anak(l_M15AnakTtgg,l_FZ1TunjAnk,l_FZ1NlTunjAnak,l_GajiRp))
                         INTO l_TunjAnak;
                         -- Update Tunjangan Anak 
			 SELECT  P_M03AllField ('D', 'TK02' )
			 INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
			         l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;                         
                         -- Nilai Kurs
                         SELECT fn_Vround(l_TunjAnak/fn_GetKurs(l_M03KdCurr,l_TglProses))
                         INTO l_TunjAnakVal ; 
                         -- 
                         IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND 
							   NIP=l_M15NIP AND KdDpPt='TK02') IS NOT NULL THEN 
                            BEGIN
				UPDATE S02DGAJ
				SET KdCurr=l_M03KdCurr,
				    NilaiVal=0,
				    EncNilaiVal = fn_kcabang(l_M15NIP,((fn_kpusat(l_M15NIP, EncNilaiVal, l_MyPass) :: Decimal(15,2))+l_TunjAnakVal :: VARCHAR(17)),l_MyPass),
			 	    Nilai=0,
				    EncNilai = fn_kcabang(l_M15NIP,((fn_kpusat(l_M15NIP, EncNilai, l_MyPass) :: Decimal(15,2))+l_TunjAnak :: VARCHAR(17)), l_MyPass)
				WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='TK02';  
                            END; 
                         ELSE
                            BEGIN
                              --
                              INSERT INTO S02DGAJ(TglPayr    ,NIP ,    FlgDpPt,KdDpPt,FlgAngs,Singkatan ,   EncNilai                                                  ,Nilai,KdCurr     ,EncNilaiVal                                                 ,NilaiVal,Kolom     ,NoAcc     ,UsComp     ,NoLyr     ,FlgNonGL,S01_ID)
                                          VALUES (l_TglProses,l_M15NIP,'D'    ,'TK02',' '    ,l_M03Singkatan,fn_kcabang(l_M15NIP,(l_TunjAnak :: VARCHAR(17)),l_MyPass),    0,l_M03KdCurr,fn_kcabang(l_M15NIP,(l_TunjAnakVal :: VARCHAR(17)),l_MyPass),       0,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,       0,l_S01_ID);
                            END;
                         END IF; 
                         --  
                       END; 
		    END IF; 
--                     -- Pendapatan/Potongan Cabang
--                     IF l_FZ1Caba='Y' AND (SELECT KdCab FROM M09DCAB WHERE KdCab=l_M15kdCaba) IS NOT NULLTHEN 
--                        BEGIN
--                          SELECT P_DasarKelompok(l_TglProses,
-- 						l_M15kdCaba,
-- 						l_M15NIP, 
-- 						'M09DCAB',
-- 						l_FlagKhusus,
-- 						l_GajiRp,
-- 						0,
-- 						'Y',
-- 						l_HKErja, 
-- 						l_MyPass);
--                        END;
--                     END IF; 
-- 
--                     -- Pendapatan/Potongan Golongan
--                     IF l_FZ1FlgGlng='Y' AND (SELECT Kode FROM M13DGOL WHERE Kode=l_M15KdGlng) IS NOT NULL THEN 
--                        BEGIN
--                          SELECT P_DasarKelompok(l_TglProses,
-- 						l_M15kdCaba,
-- 						l_M15NIP, 
-- 						'M09DCAB',
-- 						l_FlagKhusus,
-- 						l_GajiRp,
-- 						0,
-- 						'Y',
-- 						l_HKErja, 
-- 						l_MyPass);
--                        END;
--                     END IF; 
--                     -- Pendapatan/Potongan Kelompok Jabatan
--                     IF l_FZ1FlgKJab='Y' AND (SELECT Kode FROM M07DKJB WHERE Kode=l_M15kdKJab) IS NOT NULL THEN 
--                        BEGIN
--                          SELECT P_DasarKelompok(l_TglProses,
-- 						l_M15kdCaba,
-- 						l_M15NIP, 
-- 						'M09DCAB',
-- 						l_FlagKhusus,
-- 						l_GajiRp,
-- 						0,
-- 						'Y',
-- 						l_HKErja, 
-- 						l_MyPass);
--                        END;
--                     END IF; 
--                     -- Pendapatan/Potongan Jabatan
--                     IF l_FZ1FlgJaba='Y' AND (SELECT Kode FROM M05DJAB WHERE Kode=l_M15kdJaba) IS NOT NULL THEN
--                        BEGIN
--                          SELECT P_DasarKelompok(l_TglProses,
-- 						l_M15kdCaba,
-- 						l_M15NIP, 
-- 						'M09DCAB',
-- 						l_FlagKhusus,
-- 						l_GajiRp,
-- 						0,
-- 						'Y',
-- 						l_HKErja, 
-- 						l_MyPass);
--                        END;
--                     END IF;                        

                     -- Pendapatan/Potongan Kelompok Advance 
		     -- fz2flgadv emang gak diisiin (idem pendapatan/potongan kelompok) 
		     ---------------------SAMPE SINI
                     IF l_FZ2FlgAdv='Y' THEN 
			BEGIN 
			   INSERT INTO l_TEMP 
			   SELECT M64.LEVEL1, M64.KODE1,
			  	  M64.LEVEL2, M64.KODE2, 
				  M65.Persen, M65.NILAI, M65.KDCURR, M65.Trans,    
				  M03.Persen, M03.NILAI, M03.KDCURR   
			   FROM M64ADVH M64
			   LEFT JOIN M65ADVD M65 
			   ON M64.Level1 = M65.Level1 AND M64.Kode1 = M65.Kode1 AND 
			      M64.Level2 = M65.Level2 AND M64.Kode2 = M65.Kode2 
			   LEFT JOIN M03DPPT M03
			   ON M65.FlgDppt = M03.FlgDppt AND M65.KdDppt = M03.KdDppt 
			   WHERE (l_M15KDAREA = CASE WHEN M64.Level1 = '1' THEN M64.Kode1 ELSE '########' END OR 
				     l_M15KDCABA = CASE WHEN M64.Level1 = '2' THEN M64.Kode1 ELSE '########' END OR 
				     l_M15KDUUSA = CASE WHEN M64.Level1 = '3' THEN M64.Kode1 ELSE '########' END OR 
				     l_M15KDUKER = CASE WHEN M64.Level1 = '4' THEN M64.Kode1 ELSE '########' END OR 
				     l_M15KDGLNG = CASE WHEN M64.Level1 = '5' THEN M64.Kode1 ELSE '########' END OR 
				     l_M15KDKJAB = CASE WHEN M64.Level1 = '6' THEN M64.Kode1 ELSE '########' END OR 
				     l_M15KDJABA = CASE WHEN M64.Level1 = '7' THEN M64.Kode1 ELSE '########' END)
				AND (l_M15KDAREA = CASE WHEN M64.Level2 = '1' THEN M64.Kode2 ELSE '########' END OR 
				     l_M15KDCABA = CASE WHEN M64.Level2 = '2' THEN M64.Kode2 ELSE '########' END OR 
				     l_M15KDUUSA = CASE WHEN M64.Level2 = '3' THEN M64.Kode2 ELSE '########' END OR 
				     l_M15KDUKER = CASE WHEN M64.Level2 = '4' THEN M64.Kode2 ELSE '########' END OR 
				     l_M15KDGLNG = CASE WHEN M64.Level2 = '5' THEN M64.Kode2 ELSE '########' END OR 
				     l_M15KDKJAB = CASE WHEN M64.Level2 = '6' THEN M64.Kode2 ELSE '########' END OR 
				     l_M15KDJABA = CASE WHEN M64.Level2 = '7' THEN M64.Kode2 ELSE '########' END OR 
				     M64.Level2 = '8') 

			   IF (SELECT Level1 FROM l_Temp) IS NOT NULL THEN 
                              BEGIN
                                 SELECT P_DasarKelompokAdv(l_TglProses,
							   l_M15NIP, 
							   l_FlagKhusus,
							   l_GajiRp,
							   l_mnHari,
							   'T',
							   0, 
							   l_MyPass,
							   l_S01_ID);  
			       END;
			   END IF; 
                        END; 
		    END IF; 

                    -- Pendapatan/Potongan Tetap
                    IF (SELECT NIP FROM T04TTAP WHERE NIP=l_M15NIP) IS NOT NULL THEN 
                       BEGIN
                         SELECT P_Pend_Tetap(l_TglProses,
                                           l_FlagKhusus,
                                           l_M15NIP,
                                           l_GajiRp,
                                           0,
                                           'Y',
                                           l_HKerja,  
					   l_MyPass,
					   l_S01_ID);  
                       END;
                    END IF; 

                    -- Pendapatan/Potongan Variable
                    IF (SELECT NIP FROM T03VARI WHERE NIP=l_M15NIP AND PrdMulai <= l_Tglproses) IS NOT NULL THEN 
                       BEGIN
                         SELECT P_Pend_Vari(l_TglProses,
                                            l_S05TglAkhir,
                                            l_FlagKhusus,
                                            l_M15NIP,
                                      	    l_GajiRp,
		                            'Y',
					    l_MyPass,
					    l_S01_ID); 
                       END;
                    END IF; 

                    -- Pendapatan/Potongan Harian  
                    IF (SELECT NIP FROM T02ABSN WHERE NIP=l_M15NIP AND Tanggal>l_S05TglAkhir AND Tanggal<=l_TglProses) IS NOT NULL THEN 
                       BEGIN
                         SELECT P_Pend_Harian(l_TglProses,
                                              l_FlagKhusus,
                                              l_S05TglAkhir,
                                              l_M15NIP,
                                              l_GajiRp,
                                              l_FZ1JumlahMangkir, 
					      l_MyPass,
					      l_S01_ID); 
                       END;
                    END IF; 
-- SAMPE SINI 
                    -- Pendapatan Akumulasi
                    IF EXISTS(SELECt NIP FROM T17PDAK WHERE NIP=l_M15NIP) 
                       BEGIN
                         EXEC P_PendAkumulasi l_TglProses,
                                              l_M15NIP,
                                              l_FlagKhusus,
                                              l_MyPass
                       END

                        
                    -- Piutang Pegawai
                    IF l_FZ1FlgPiut='Y' AND l_FlagKhusus='T' AND EXISTS(SELECT NIP FROM T06DPIU WHERE NIP=l_M15NIP AND PrdAngs > l_S05TglAkhir AND PrdAngs <= l_TglProses)
                       BEGIN
                         EXEC P_Piutang_Peg l_TglProses,
                      