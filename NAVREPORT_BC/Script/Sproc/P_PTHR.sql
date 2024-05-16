/****************************************
Name sprocs : P_PTHR
Create by   : Wati
Date        : 02-07-2003
Description : Proses THR
Call From   : Main sprocs
*****************************************/
--
DROP FUNCTION P_PTHR  (l_TglProses  DATE,
			l_LokasiFr   VARCHAR(4),
			l_LokasiTo   VARCHAR(4), 
			l_NIPFr      VARCHAR(10),
			l_NIPTo      VARCHAR(10),
			l_J_THR      VARCHAR(1),
			l_P_THR      VARCHAR(1),
			l_KodeLDA    VARCHAR(2),
			l_UserID     VARCHAR(12),
			OUT l_Gagal      INT,
			OUT l_TableName1 VARCHAR(8),
			OUT l_mlValid    INT,
			l_MyPass     VARCHAR(128),
			l_Usr_ID     INT); 
--
CREATE FUNCTION P_PTHR  (l_TglProses  DATE,
			l_LokasiFr   VARCHAR(4),
			l_LokasiTo   VARCHAR(4), 
			l_NIPFr      VARCHAR(10),
			l_NIPTo      VARCHAR(10),
			l_J_THR      VARCHAR(1),
			l_P_THR      VARCHAR(1),
			l_KodeLDA    VARCHAR(2),
			l_UserID     VARCHAR(12),
			OUT l_Gagal      INT,
			OUT l_TableName1 VARCHAR(8),
			OUT l_mlValid    INT,
			l_MyPass     VARCHAR(128),
			l_Usr_ID     INT) 

AS $$ 
--
DECLARE l_S05FlagTHR 	VARCHAR(1);        l_S05TglAkhir    	DATE;
	l_Flag_R     	VARCHAR(1);        l_Cabang         	VARCHAR(4);     l_FZ1FlgPiut 	VARCHAR(1);
	l_VarRound   	VARCHAR(1);        l_V_Round    	INT;          	l_Prs_OK     	VARCHAR(1);
	l_FZ1FlgLemb	VARCHAR(1);        l_FZ1TunjAnk 	DECIMAL(15,2);  
	l_FZ1FlgKJab 	VARCHAR(1);        l_FZ1Caba    	VARCHAR(1);      
	l_FZ1TglTHRL 	DATE;       	   l_FZ1TglTHRN 	DATE;
	l_FZ1TglHakL 	DATE;       	   l_FZ1TglHakN 	DATE;
	l_M15NIP     	VARCHAR(10);       l_M15Nama    	VARCHAR(25);    l_M15TglLahir  DATE;
	l_M15PrdAwl  	DATE;       	   l_M15TglKeluar   	DATE;     	l_M15kdCaba    VARCHAR(4);
	l_M15kdKlas  	VARCHAR(4);        l_M15TglMasuk    	DATE;     	l_M15KdGlng    VARCHAR(4);
	l_M15MasaKerja  INT;               l_M15kdCurr  	VARCHAR(4);     l_M15PrdTetap  DATE;
	l_M15GajiTetap  DECIMAL(15,2);     l_M15GajiPerc    	DECIMAL(15,2);	l_M15PJK       VARCHAR(1);
	l_M15TunjIst 	VARCHAR(1);        l_M15TunjAnak    	VARCHAR(1);
	l_M15Status  	VARCHAR(1);        l_M15JnsKlmn 	VARCHAR(1);     l_M15kdJaba    VARCHAR(4);
	l_M15AnakTtgg   INT;               l_M15kdKJab  	VARCHAR(4);     l_M15THR       VARCHAR(1);
	l_M15Agama   	VARCHAR(1);        l_M15BlnTHR  	DECIMAL(5,2); 	l_FZ1TunjIst   DECIMAL(5,2);
	l_M15KdArea  	VARCHAR(4);        l_M15KdUUsa  	VARCHAR(4);     l_M15KdUKer    VARCHAR(8);
	l_M15StsPjk  	VARCHAR(2);        l_M15KPA     	VARCHAR(11);    l_M15Rkbnk 	VARCHAR(20);
	l_M15AccHolder  VARCHAR(25);       l_M15BankRef 	VARCHAR(4);      
	l_FZ1FlgTHR  	VARCHAR(1);        l_M15NoSPSI  	VARCHAR(10);
	l_M15Pajak   	VARCHAR(1);        l_FZ1IntvPajak   	DECIMAL(9,0); 	l_FZ1PrOccSupp DECIMAL(5,2);
	l_M15kdCabaNEW  VARCHAR(4);    	   l_FZ1NlOccSupp   	DECIMAL(15,2);  
	l_mnKurs     	DECIMAL(10,2);     l_FZ1PTKPPay 	DECIMAL(15,2);
	l_M15KdTerr 	VARCHAR(4);        l_FZ1PTKPDep 	DECIMAL(15,2);
	l_M15PrdAwal    DATE;       	   l_Sumber 		VARCHAR(6);
	l_LOOP_M15   	REFCURSOR;         l_JnsPajak       	VARCHAR(1);       
	l_Curr_Income   DECIMAL(15,2);     l_Curr_kolom8    	DECIMAL(15,2); l_JnsPeg      VARCHAR(1);
	l_T19Pesangon   DECIMAL(15,2);     l_FZ1FlgGGol     	VARCHAR(1);
	l_FZ1Personalia VARCHAR(1);        l_Test           	VARCHAR(10);
	l_FilterDta     INT;               l_Gaji           	DECIMAL(15,2);
	l_GajiPokok     DECIMAL(15,2);     l_T18HariKerja   	VARCHAR(1);
	l_T18Jmlhari    DECIMAL(4,0);      l_M10JnsPajak   	VARCHAR(1);         
	l_FZ1FlgGlng    VARCHAR(1);        l_FZ1FlgJaba     	VARCHAR(1);
	l_FZ1JumlahMangkir INT;            l_FlgCabang      	VARCHAR(1);
	l_DataValid     INT;        	   l_FilterData     	VARCHAR(1);
	l_Created      	INT;        	   l_mnHari         	DECIMAL(4,1);         
	l_GajiRp       	DECIMAL(15,2);     l_GajiVal        	DECIMAL(15,2);
	l_M03KdDppt    	VARCHAR(4);        l_M03Singkatan   	VARCHAR(10);
	l_M03UsComp    	VARCHAR(1);        l_M03Kolom   	VARCHAR(2);
	l_M03NoAcc     	VARCHAR(10);       l_M03Status      	VARCHAR(1);
	l_M03Persen    	DECIMAL(5,2);      l_M03Nilai       	DECIMAL(15,2);
	l_M03NoLyr     	INT;        	   l_M03KdCurr      	VARCHAR(4);
	l_M03Pajak     	VARCHAR(1);        l_M03Flag_N  	VARCHAR(1);
	l_M03ID         INT;
	l_M03Flag_R    	VARCHAR(1);        l_TunjIstri      	DECIMAL(15,2);
	l_TunjIstriVal 	DECIMAL(15,2); 	   l_Err            	INT;
	l_FZ2FlagRapel 	INT;        	   l_FZ2FlRapelTHR  	INT;
	l_FZ2PrdRapel  	DATE;       	   l_FZ2THRharian   	VARCHAR(1);
	l_M15BerhakLemb INT;        	   l_M15TglKPA      	DATE;
	l_FZ1JKK       	VARCHAR(1);        l_FZ1JHT         	VARCHAR(1);
	l_FZ1JKM       	VARCHAR(1);        l_FZ1JPK         	VARCHAR(1);
	l_M15JKK       	VARCHAR(1);        l_M15Level       	VARCHAR(1);
	l_M15JHT       	VARCHAR(1);        l_M15JKM         	VARCHAR(1);
	l_M15JPK       	VARCHAR(1);        l_Level          	VARCHAR(1); 
	l_FZ1IuranSPSI 	DECIMAL(5,2);      l_M15TglSPSI     	DATE;
	l_NilaiSPSI    	DECIMAL(15,2);     l_NilaiSPSIVal   	DECIMAL(15,2);
	l_NilaiPesangon DECIMAL(15,2);     l_NilaiPesangonVal 	DECIMAL(15,2);
	l_Cab01        	VARCHAR(4);        l_Cab02      	VARCHAR(4);
	l_Cab03        	VARCHAR(4);        l_Cab04      	VARCHAR(4);
	l_Cab05        	VARCHAR(4);        l_Cab06      	VARCHAR(4);
	l_Cab07        	VARCHAR(4);        l_Cab08      	VARCHAR(4);
	l_Cab09        	VARCHAR(4);        l_Cab10      	VARCHAR(4);
	l_Cab11        	VARCHAR(4);        l_Cab12      	VARCHAR(4);
	l_FlagTHR      	VARCHAR(1);        l_Bulan          	VARCHAR(2);
	l_Tahun        	DECIMAL(4,0);      l_Tahun1         	DECIMAL(4,0);  
	l_QPara        	VARCHAR(4000);     l_QDesc         	VARCHAR(4000);
	l_NIP1         	VARCHAR(10);       l_NewKdCaba1     	VARCHAR(4);
	l_FixIncome  	DECIMAL(15,2);     l_TotalFix       	DECIMAL(15,2); 
	l_TunjAnak   	DECIMAL(15,2);     l_OldNIP         	VARCHAR(10);
	l_TunjAnakVAL   DECIMAL(15,2);     l_M10Harian      	INT;
	l_EncTotInc  	DECIMAL(15,2);
	l_EncTotDed  	DECIMAL(15,2);
	l_EncTakeHomePay DECIMAL(15,2);
	l_FZ1NlTunjIst  DECIMAL(15,2);	   l_FZ1NlTunjAnak   	DECIMAL(15,2);	
	l_FZ1KdCutiHaid	VARCHAR(4); 	   l_FZ1KdSakitSrtDr 	VARCHAR(4); 
	l_NlTunjHaid	decimal(15,2); 	   l_NlTunjHaidVal	Decimal(15,2);  
	l_FZ2FlgAdv 	VARCHAR(1) ;	   l_S01_ID		INT; 
	l_S05_ID	INT; 

BEGIN 
-- khusus Pendapatan/potongan dasar kelompok advance 
CREATE TEMP TABLE l_TEMP (Level1 VARCHAR(1), 
			Kode1		VARCHAR(8), 
			Level2		VARCHAR(1), 
			Kode2		VARCHAR(8), 
			M65Persen	DECIMAL(5,2), 
			M65Nilai	DECIMAL(15,2), 
			M65KdCurr	VARCHAR(4),
			M65Trans	VARCHAR(1),
			M03Persen	DECIMAL(5,2), 
			M03Nilai	DECIMAL(15,2), 
			M03KdCurr	VARCHAR(4),
			NoUrut		SERIAL)  ON COMMIT DROP ;          
--
l_mlValid :=1;
l_Gagal := 0 ;
-- Mengambil Jumlah Hari di FZ1FlDA
l_mnHari := 1; 
--
SELECT CASE EXTRACT(MONTH FROM l_TglProses) WHEN 1 THEN  Hari01
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
						WHEN 12 THEN Hari12  END, 
	FlgPiut,
	FlgLemb,
	FlgGGol,
	VarRound,
	TunjIst,
	TunjAnk,
	FlgGlng,
	FlgKJab,
	FlgJaba,
	FlgCaba,
	JumlahMangkir,
	TglTHRL,
	TglTHRN,
	TglHakL,
	TglHakN,
	FlgTHR,
	IntvPajak,
	PrOccSupp,
	NlOccSupp,
	PTKPPay,
	PTKPDep,
	Personalia,
	JKK,
	JHT,
	JKM,
	JPK,
	IuranSPSI,
	NlTunjIst, 
	NlTunjAnak,
	KdCutiHaid, 
	KdSakitSrtDr   
INTO l_mnHari, 	   
     l_FZ1FlgPiut, 
     l_FZ1FlgLemb, 
     l_FZ1FlgGGol, 
     l_VarRound, 
     l_FZ1TunjIst, 
     l_FZ1TunjAnk,
     l_FZ1FlgGlng,
     l_FZ1FlgKJab,
     l_FZ1FlgJaba,
     l_FZ1Caba,
     l_FZ1JumlahMangkir,
     l_FZ1TglTHRL,
     l_FZ1TglTHRN,
     l_FZ1TglHakL,
     l_FZ1TglHakN,
     l_FZ1FlgTHR,
     l_FZ1IntvPajak,
     l_FZ1PrOccSupp,
     l_FZ1NlOccSupp,
     l_FZ1PTKPPay,
     l_FZ1PTKPDep,
     l_FZ1Personalia,
     l_FZ1JKK,
     l_FZ1JHT,
     l_FZ1JKM,
     l_FZ1JPK,
     l_FZ1IuranSPSI,
     l_FZ1NlTunjIst,
     l_FZ1NlTunjAnak,
     l_FZ1KdCutiHaid,
     l_FZ1KdSakitSrtDr
FROM FZ1FLDA ;
--
SELECT CASE SUBSTR(StringFlag,2,1) WHEN '1' THEN 'Y' ELSE 'T' END,
       CASE SUBSTR(StringFlag,1,1) WHEN '1' THEN 'Y' ELSE 'T' END,
       FlagRapel,
       FlRapelTHR,
       PeriodeRapel,
       SUBSTR(StringFlag,10,1)
INTO l_FlgCabang,
     l_FZ2THRharian, 
     l_FZ2FlagRapel,
     l_FZ2FlRapelTHR,
     l_FZ2PrdRapel, 
     l_FZ2FlgAdv
FROM FZ2FLDA
WHERE Kode=l_KodeLDA ;

-- Panggil SProc untuk Bentuk File Validasi 
SELECT l_TableName1, l_Created FROM P_CREATE_TEMP_ONLIST ('W0B') INTO l_TableName1, l_Created;--,	               
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
             END ; 
-- Jika Proses Khusus maka FlagTHR='*'
l_FlagTHR := '*';
--
-- Mengelola Range Cabang+Golongan dengan Security-nya
/*
IF l_FCAB>=l_LokasiFr AND l_TCAB<=l_LokasiTo
   BEGIN
      SELECT l_LokasiFr=l_FCAB,
         l_LokasiTo=l_TCAB    
   END;  
ELSE IF l_FCAB<l_LokasiFr AND l_TCAB<=l_LokasiTo
   BEGIN
      SELECT l_LokasiTo=l_TCAB    
   END;  
ELSE IF l_FCAB>=l_LokasiFr AND l_TCAB>l_LokasiTo
   BEGIN
      SELECT l_LokasiFr=l_FCAB
   END;  
ELSE IF l_FCAB<l_LokasiFr AND l_TCAB>l_LokasiTo
   BEGIN
      SELECT l_LokasiFr=l_FCAB
   END;  
--*
*/
--
-- BEGIN TRAN

----------- MULAI PROSES DATA -----------------
OPEN l_LOOP_M15 FOR SELECT M15.NIP,M15.Nama,M15.TglLahir,M15.PrdAwl,M15.TglKeluar,M15.KdCaba,M15.KdKlas,
       M15.TglMasuk,M15.TglKeluar,M15.KdGlng,M15.MasaKerja,M15.KdCurr,M15.PrdTetap,
       fn_Kpusat(M15.NIP,M15.EncGajiTetap,l_MyPass) ::DECIMAL(15,2),
       fn_Kpusat(M15.NIP,M15.EncGajiPerc,l_MyPass) ::DECIMAL(15,2),
       M15.Pajak,M15.TunjIst,M15.TunjAnak,M15.Status,
       M15.JnsKlmn,M15.KdJaba,M15.AnakTtgg,M15.KdKJab,M15.THR,M15.Agama,M15.BlnTHR,
       M15.KdArea,M15.KdUUsa,M15.KdUKer,M15.StsPjk,M15.KPA,M15.Rkbnk,M15.AccHolder,M15.BankRef,
       M15.NoSPSI,M15.KdTerr,M15.BerhakLemb,M15.TglKPA,M15.JKK,M15.Level,M15.JHT,M15.JKM,M15.JPK,
       M15.TglSPSI

FROM M15PEGA M15
INNER JOIN (SELECT * FROM fn_SECLOGIN(l_usr_id)) VSL 
ON M15.kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE

WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
      (KdCaba BETWEEN l_LokasiFr AND l_LokasiTo);-- AND
--      (KdGlng BETWEEN l_FGOL AND l_TGOL);
--

<<LOOP_M15>> 
LOOP 
FETCH l_LOOP_M15 INTO l_M15NIP,l_M15Nama,l_M15TglLahir,l_M15PrdAwl,l_M15TglKeluar,l_M15kdCaba,l_M15kdKlas,
     l_M15TglMasuk,l_M15TglKeluar,l_M15KdGlng,l_M15MasaKerja,l_M15kdCurr,l_M15PrdTetap,
     l_M15GajiTetap,l_M15GajiPerc,
     l_M15Pajak,l_M15TunjIst,l_M15TunjAnak,l_M15Status,
     l_M15JnsKlmn,l_M15kdJaba,l_M15AnakTtgg,l_M15kdKJab,l_M15THR,l_M15Agama,l_M15BlnTHR,
     l_M15KdArea,l_M15KdUUsa,l_M15KdUKer,l_M15StsPjk,l_M15KPA,l_M15Rkbnk,l_M15AccHolder,l_M15BankRef,
     l_M15NoSPSI,l_M15KdTerr,l_M15BerhakLemb,l_M15TglKPA,l_M15JKK,l_M15Level,l_M15JHT,l_M15JKM,l_M15JPK,
     l_M15TglSPSI ; 
     
IF NOT FOUND THEN
EXIT ;
END IF;
--
  BEGIN
--    IF l_M15kdCaba BETWEEN l_FCAB AND l_TCAB THEN 
--       BEGIN
          --
          --l_S05TglAkhir :=' ';
          l_S05FlagTHR  :=' ';
          -- Clear TotalFix
          IF l_M15NIP<>l_OLDNIP THEN 
             BEGIN
               l_TotalFix := 0;
             END;
          END IF;    
          --
          -- Ambil Tgl.Akhir Proses Payroll
          SELECT TglPosting,    FlagTHR
          INTO   l_S05TglAkhir, l_S05FlagTHR
          FROM S05PSTD
          WHERE NIP=l_M15NIP 
          ORDER BY NIP,TglPosting DESC ; 

          -- Validasi Data     
          SELECT * FROM fn_Validasi_THR(l_S05FlagTHR, l_M15NIP, l_TglProses, l_M15TglMasuk, l_M15TglKeluar, l_S05TglAkhir) 
          INTO l_DataValid ; 
          
          IF l_DataValid<>0 THEN --and l_datavalid <> 2 
             BEGIN
		l_mlValid := 0 ;
		PERFORM P_CLSTEMP (l_M15NIP, l_M15Nama, l_S05TglAkhir,l_DataValid,l_TableName1) ; 
             END; 
          ELSE
             BEGIN
               -- Unpost Payroll
               PERFORM P_UPBLKH (l_TglProses, l_LokasiFr, l_LokasiTo, l_M15NIP, l_M15NIP , 'T' );

               -- Akibat Unpost S05 Harus di ambil lagi
               --l_S05TglAkhir := ' '; 
               l_S05FlagTHR  := ' ';
               --
               -- Ambil Tgl.Akhir Proses Payroll
               SELECT TglPosting,  FlagTHR
	       INTO l_S05TglAkhir, l_S05FlagTHR
               FROM S05PSTD
               WHERE NIP=l_M15NIP 
               ORDER BY NIP,TglPosting ;   
               --        
               -- Call Procedure Filter data
               SELECT Harian
	       INTO   l_M10Harian
               FROM M10KLAS
               WHERE Kode=l_M15kdKlas ; 

               IF l_M10Harian=1 THEN 
                  BEGIN
                    l_FilterData := '7' ; 		 
                  END;
	       ELSE 
                  BEGIN
                    l_FilterData := '0';		 
		  END;
	       END IF ; 
               -- Jika Ada     
               IF l_FilterData <> '0' THEN 
                  BEGIN
                    l_mlValid := 0 ;
                    PERFORM P_CLSTEMP (l_M15NIP, l_M15Nama, l_S05TglAkhir,l_FilterData,l_TableName1) ; 
                  END;       
               ELSE 
                  BEGIN
                    -- Gaji Pokok
                    SELECT * FROM fn_GajiPokok(l_FZ1FlgGGol,
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
                    l_mnKurs := 1 ;
                    SELECT * FROM fn_GetKurs(l_M15kdCurr,l_TglProses) INTO l_mnKurs;

                    -- Jenis Pajak
                    SELECT JnsPajak
		    INTO   l_M10JnsPajak
                    FROM  M10KLAS WHERE Kode=l_M15kdKlas ;

                    -- Ambil Nilai yg diperlukan u/Insert S02DGAJ
                    l_GajiRp   := l_GajiPokok*l_mnKurs ;
                    l_TotalFix := COALESCE(l_TotalFix,0)+COALESCE(l_GajiRp,0) ;
                    -- Insert header S01HGAJ
                    --
                    IF (SELECT COUNT(TglPayr) FROM S01HGAJ WHERE TglPayr=l_TglProses AND NIP=l_M15NIP ) = 0 THEN 
                       BEGIN
                          INSERT INTO S01HGAJ(TglPayr, NIP,     Nama,     KdKlas,     KdArea     ,KdCaba     ,KdUUsa     ,KdUKer     ,KdGlng     ,KdKJab     ,KdJaba     ,StsPjk     ,CommDate     , TermDate     ,JnsKlmn     ,StsSip     ,TotAnak      ,KPA     ,AccHolder     ,RkBnk     ,BankRef     ,Hari    ,JnsPajak     , NoAcc, NoSpt,TakeHomePay,EncTakeHomePay                    ,TotInc,TotDed,GrossIncomeNBYTD,OccSupport1,Col12YTD,EGIYNB,PTKP,EYTI,EYIT,YTDIT,IncTaxAPaidNB,GrossIncomeBYTD,OccSupport2,TaxTotal,IncTaxAPaidB,FlagTHR  ,AkmHari,FlagM,FlagH ,TaxPesangonRp,Recs,EncTotInc                         ,EncTotDed,			       EncGrossIncomeNBYTD		  , EncOccSupport1,   		       EncCol12YTD, 			   EncEGIYNB, 			       EncPTKP, 			   EncEYTI, 			       EncEYIT, 			   EncYTDIT, 			       EncIncTaxAPaidNB, 	   	   EncGrossIncomeBYTD, 		       EncOccSupport2, 	   		   EncTaxTotal, 			EncIncTaxAPaidB, 		   EncTaxPesangonRp,                   version, created_by, created_on, updated_by, updated_on)
                              	    VALUES(l_TglProses,l_M15NIP,l_M15Nama,l_M15kdKlas,l_M15KdArea,l_M15kdCaba,l_M15KdUUsa,l_M15KdUKer,l_M15KdGlng,l_M15kdKJab,l_M15kdJaba,l_M15StsPjk,l_M15TglMasuk,l_M15TglKeluar,l_M15JnsKlmn,l_M15Status,l_M15AnakTtgg,l_M15KPA,l_M15AccHolder,l_M15Rkbnk,l_M15BankRef,l_mnHari,l_M10JnsPajak, ' '   ,' '  ,0          ,fn_kcabang(l_M15NIP, '0',l_MyPass),0     ,0     ,0               ,0          ,0       ,0     ,0   ,0   ,0   ,0    ,0            ,0              ,0          ,0       ,0           ,l_FlagTHR,0      ,' '  ,' '   ,0            ,0   ,fn_kcabang(l_M15NIP, '0',l_MyPass),fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass), 0      , l_userid,   localtimestamp, null, null );
			  --RETURNING S01_ID ; 
			   SELECT currval(pg_get_serial_sequence('s01hgaj', 's01_id'))
			   INTO l_S01_ID;	  

                           -- Jika Gagal Update
                          IF l_S01_ID IS NULL THEN 
			     l_Gagal := 1;
                             EXIT LOOP_M15 ; 
                          END IF;  
		       END ; 
                    END IF ; 
                    --*

                    -- Tunjangan Istri 
-- BY PEGGY 2006 12 18 
--                    IF COALESCE(l_FZ1TunjIst,0)<>0 AND l_M15TunjIst='Y' AND 
                     IF (COALESCE(l_FZ1TunjIst,0)<>0 OR COALESCE(l_FZ1NlTunjIst,0)<>0) AND 
			l_M15TunjIst='Y' AND 
                        l_M15JnsKlmn='L' AND l_M15Status='K' AND 
--                       (SELECT Kolom FROM M03DPPT WHERE FlgDpPt='D' AND KdDpPt='TK01')=1
                       (SELECT Status FROM M03DPPT WHERE FlgDpPt='D' AND KdDpPt='TK01')='1' THEN 
                       BEGIN                   
-- BY PEGGY 2006 12 18 
--                          SELECT l_TunjIstri=(l_FZ1TunjIst*l_GajiRp)/100
			  IF l_FZ1TunjIst <> 0 THEN 
			      SELECT * FROM fn_Vround((l_FZ1TunjIst*l_GajiRp)/100) 
			      INTO l_TunjIstri ; 
			  ELSE  
			      SELECT * FROM fn_Vround(l_FZ1NlTunjIst) 
			      INTO l_TunjIstri ;
			  END IF ;
                          l_TotalFix := COALESCE(l_TotalFix,0)+COALESCE(l_TunjIstri,0) ; 
                       END; 
		     END IF ; 

-- BY PEGGY 2006 12 18 
                     -- Tunjangan Keluarga
                     IF (COALESCE(l_FZ1TunjIst,0)<>0 OR COALESCE(l_FZ1NlTunjIst,0)<>0) AND 
			l_M15TunjIst='Y' AND 
                        l_M15JnsKlmn='P' AND l_M15Status='K' AND 
                       (SELECT Status FROM M03DPPT WHERE FlgDpPt='D' AND KdDpPt='TK01')='1' THEN 
                        BEGIN
                          l_TunjIstri := 0 ;
                          SELECT * FROM fn_Vround(fn_Tunjangan_Keluarga(l_FZ1TunjIst,l_FZ1NlTunjIst,l_GajiRp))
			  INTO l_TunjIstri ;
                          l_TotalFix := COALESCE(l_TotalFix,0)+COALESCE(l_TunjIstri,0) ;
                        END; 
		     END IF; 

                    -- Tunjangan Anak                
		     IF (SELECT FN_EVALTUNJANAK(l_FZ1TunjAnk,l_FZ1NlTunjAnak,l_M15TunjAnak,l_M15JnsKlmn,l_M15Status,l_M15AnakTtgg)) = 'Y' AND 
                       (SELECT Status FROM M03DPPT WHERE FlgDpPt='D' AND KdDpPt='TK02')='1' THEN 
                       BEGIN
                         SELECT * FROM fn_Vround(fn_Tunjangan_Anak(l_M15AnakTtgg,l_FZ1TunjAnk,l_FZ1NlTunjAnak,l_GajiRp))
			 INTO l_TunjAnak ; 
                         l_TotalFix := COALESCE(l_TotalFix,0)+COALESCE(l_TunjAnak,0) ; 
                       END;
		     END IF; 
-- DI HAPIS WEB GAK ADA 
                    -- Pendapatan/Potongan Cabang
--                     IF l_FZ1Caba='Y' AND EXISTS(SELECT M03.Status FROM M09DCAB M09
--                                                INNER JOIN M03DPPT M03
--                                                ON M09.FlgDpPt= M03.FlgDpPt AND M09.KdDpPt=M03.KdDpPt
--                                                WHERE M09.KdCab=l_M15kdCaba AND M09.FlgDpPt='D' AND M03.Status='1')                  
--                        BEGIN
--                          EXEC P_DasarKelompokTHR l_TglProses,
-- 			                                     l_M15kdCaba, 
--                                                  l_M15NIP, 
--             			                         'M09DCAB',                            
--                                                  l_GajiRp,
--                                                  l_mnHari,
--                                                  l_MyPass,
--                         			             l_TotalFix OUTPUT
--                        END;
-- 
--                     -- Pendapatan/Potongan Golongan
-- -- BY PEGGY 2006 12 18 
-- --                     IF l_FZ1FlgGlng='Y' AND EXISTS(SELECT M03.kolom FROM M13DGOL M13
-- --                                                   INNER JOIN M03DPPT M03
-- --                                                   ON M13.FlgDpPt= M03.FlgDpPt AND M13.KdDpPt=M03.KdDpPt
-- --                                                   WHERE M13.Kode=l_M15KdGlng AND M13.FlgDpPt='D' AND M03.Kolom='1')                  
--                     IF l_FZ1FlgGlng='Y' AND EXISTS(SELECT M03.Status FROM M13DGOL M13
--                                                   INNER JOIN M03DPPT M03
--                                                   ON M13.FlgDpPt= M03.FlgDpPt AND M13.KdDpPt=M03.KdDpPt
--                                                   WHERE M13.Kode=l_M15KdGlng AND M13.FlgDpPt='D' AND M03.Status='1')                  
--                        BEGIN
--                          EXEC P_DasarKelompokTHR l_TglProses,
-- 			                                     l_M15KdGlng,
--                                                  l_M15NIP, 
--             		                             'M13DGOL',
--                                                  l_GajiRp,
--                                                  l_mnHari,
--                                                  l_MyPass,
--                                                  l_TotalFix OUTPUT 
--                        END;
-- 
--                     -- Pendapatan/Potongan Kelompok Jabatan
-- -- BY PEGGY 2006 12 18 
-- --                     IF l_FZ1FlgKJab='Y' AND EXISTS(SELECT M03.kolom FROM M07DKJB M07
-- --                                                   INNER JOIN M03DPPT M03
-- --                                                   ON M07.FlgDpPt=M03.FlgDpPt AND M07.KdDpPt=M03.KdDpPt
-- --                                                   WHERE M07.Kode=l_M15kdKJab AND M07.FlgDpPt='D' AND M03.Kolom='1')
--                     IF l_FZ1FlgKJab='Y' AND EXISTS(SELECT M03.Status FROM M07DKJB M07
--                                                   INNER JOIN M03DPPT M03
--                                                   ON M07.FlgDpPt=M03.FlgDpPt AND M07.KdDpPt=M03.KdDpPt
--                                                   WHERE M07.Kode=l_M15kdKJab AND M07.FlgDpPt='D' AND M03.Status='1')
--                        BEGIN
--                          EXEC P_DasarKelompokTHR l_TglProses,
--                                                  l_M15kdKJab,
--                                                  l_M15NIP, 
-- 	                                             'M07DKJB',
--                                                  l_GajiRp,
--                                                  l_mnHari,
--                                                  l_MyPass,
--                                                  l_TotalFix OUTPUT  
--                        END;
--                     -- Pendapatan/Potongan Jabatan
-- -- BY PEGGY 2006 12 18 
-- --                     IF l_FZ1FlgJaba='Y' AND EXISTS(SELECT M03.kolom FROM M05DJAB M05
-- --                                                   INNER JOIN M03DPPT M03
-- --                                                   ON M05.FlgDpPt= M03.FlgDpPt AND M05.KdDpPt=M03.KdDpPt
-- --                                                   WHERE M05.Kode=l_M15kdJaba AND M05.FlgDpPt='D' AND M03.Kolom='1')
--                     IF l_FZ1FlgJaba='Y' AND EXISTS(SELECT M03.Status FROM M05DJAB M05
--                                                   INNER JOIN M03DPPT M03
--                                                   ON M05.FlgDpPt= M03.FlgDpPt AND M05.KdDpPt=M03.KdDpPt
--                                                   WHERE M05.Kode=l_M15kdJaba AND M05.FlgDpPt='D' AND M03.Status='1')
--                        BEGIN
--                          EXEC P_DasarKelompokTHR l_TglProses,
-- 				                                 l_M15kdJaba,
--                                                  l_M15NIP, 
--                 		                         'M05DJAB',
--                                                  l_GajiRp,
--                                                  l_mnHari,
--                                                  l_MyPass,
--                                                  l_TotalFix OUTPUT  
--                        END;

                     -- Pendapatan/Potongan Kelompok Advance 
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
					 M64.Level2 = '8') ;

			IF (SELECT COUNT(Level1) FROM l_Temp) > 0 THEN 
                        BEGIN
                          l_TotalFix := P_DasarKelompokAdvTHR (l_TglProses,
								l_M15NIP, 
								'T',
								l_GajiRp,
								l_mnHari,
								'T',
								0, 
								l_MyPass,
								l_TotalFix) ;
                        END;
			END IF; 
                     END;
		     END IF; 

                    -- Pendapatan/Potongan Tetap
                    IF (SELECT COUNT(T04.NIP) FROM T04TTAP T04
                              INNER JOIN M03DPPT M03
                              ON T04.FlgDpPt=M03.FlgDpPt AND T04.KdDpPt=M03.KdDpPt
                              WHERE T04.NIP=l_M15NIP AND T04.FlgDpPt='D' AND M03.Status='1') > 0 THEN 
-- BY PEGGY 2006 12 18 
--                               WHERE T04.NIP=l_M15NIP AND T04.FlgDpPt='D' AND M03.Kolom='1')
                       BEGIN
                         l_TotalFix := P_Pend_TetapTHR (l_TglProses,
						        l_M15NIP,
						        l_GajiRp,
						        l_mnHari, 
							l_MyPass,
							l_TotalFix) ;
                       END; 
		    END IF; 
                    -- Pendapatan/Potongan Variable
                    IF (SELECT COUNT(NIP) FROM T03VARI T03
                              INNER JOIN M03DPPT M03
                              ON T03.FlgDpPt=M03.FlgDpPt AND T03.KdDpPt=M03.KdDpPt 
-- BY PEGGY 2006 12 18 
--                              WHERE NIP=l_M15NIP AND PrdMulai <= l_Tglproses AND T03.FlgDpPt='D' AND M03.Kolom='1')
                              WHERE NIP=l_M15NIP AND PrdMulai <= l_Tglproses AND T03.FlgDpPt='D' AND M03.Status='1') > 0 THEN 
                       BEGIN
                         l_TotalFix := P_Pend_VariTHR (l_TglProses,
						       l_S05TglAkhir, 
						       l_M15NIP,
						       l_GajiRp,
						       l_MyPass,
						       l_TotalFix) ;
                       END;
		    END IF; 

                    -- Pendapatan/Potongan Harian  
                    IF (SELECT COUNT(NIP) FROM T02ABSN T02 
                              INNER JOIN M03DPPT M03
                              ON T02.FlgDpPt=M03.FlgDpPt AND T02.KdDpPt=M03.KdDpPt
-- BY PEGGY 2006 12 18 
--                              WHERE NIP=l_M15NIP AND Tanggal>l_S05TglAkhir AND Tanggal<=l_TglProses AND T02.FlgDpPt='D' AND M03.Kolom='1')
                              WHERE NIP=l_M15NIP AND Tanggal>l_S05TglAkhir AND Tanggal<=l_TglProses AND T02.FlgDpPt='D' AND M03.Status='1' )
				> 0 THEN 
                       BEGIN
                         l_TotalFix := P_Pend_HarianTHR (l_TglProses,
						         l_S05TglAkhir,
						         l_M15NIP,
						         l_GajiRp,
							 l_MyPass,
							 l_TotalFix) ;
                       END; 
		    END IF; 

                     -- Tunjangan Haid 
		     -- Syarat : perempuan dan tidak ada absen = cuti haid dan tidak ada absen lain selain absen sakit 
		     --	dengan surat dokter 
-- BY PEGGY 2007 05 21 : PINDAH KE FN_EVALTUNJANAK 
-- -- 		     IF l_M15JnsKlmn='P' AND 
-- -- 			(NOT EXISTS(SELECT NIP FROM S06ABSH WHERE NIP=l_M15NIP AND Tanggal BETWEEN l_S05TglAkhir AND l_TglProses AND KdAbsn = l_FZ1KdCutiHaid)) AND 
-- -- 			(NOT EXISTS(SELECT NIP FROM S06ABSH WHERE NIP=l_M15NIP AND Tanggal BETWEEN l_S05TglAkhir AND l_TglProses AND KdAbsn <> l_FZ1KdSakitSrtDr)) 
		    IF (SELECT FN_EVALTUNJHAID(l_FZ1KdCutiHaid,l_FZ1KdSakitSrtDr,l_M15JnsKlmn,l_S05TglAkhir,l_TglProses,l_M15TglLahir,l_M15NIP)) = 'Y' AND 
			(SELECT Status FROM M03DPPT WHERE FlgDpPt='D' AND KdDpPt='HAID')='1' THEN 
			   BEGIN
				   SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
					  fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
				   from  P_M03AllField ('D', 'HAID' ) fx
				   INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
					   l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ; 					  
	                          --
	                          l_totalfix := l_totalfix+fn_NilaiPend_Pot(l_TglProses,l_M15NIP,'D','HAID',l_M03Persen,l_M03Nilai,1,l_GajiRp,'T',l_MyPass,l_M03KdCurr) ;
			   END;  
		    END IF; 

		 -- Sodetan untuk PT. OTSUKA untuk hitung Incentive Pegawai
		 --- New by peggy 03-10-2006
		   l_TotalFix := P_InstOtsuTHR(l_M15NIP,
						l_TglProses,
						l_S05TglAkhir,
						l_GajiRp,
						l_M15TglMasuk,
						l_M15KdGlng,
						l_MyPass,
						l_TotalFix) ;
		 --/
		 -- Sodetan untuk PT. OTSUKA LAWANG untuk hitung Incentive Pegawai 
		 --- New by PEGGY 2006 12 22 
		    l_TotalFix := P_OILIncNTransTHR (l_M15NIP,
						     l_TglProses,
						     l_S05TglAkhir,
						     l_GajiRp,
						     l_M15TglMasuk,
						     l_M15KdGlng,
						     l_M15KdCaba,
						     l_MyPass,
						     l_TotalFix) ;

                    -- Pendapatan Akumulasi
                    IF (SELECT COUNT(NIP) FROM T17PDAK T17
                              INNER JOIN M03DPPT M03
                              ON T17.FlgDpPt=M03.FlgDpPt AND T17.KdDpPt=M03.KdDpPt
-- BY PEGGY 2006 12 18 
--                              WHERE NIP=l_M15NIP AND T17.FlgDpPt='D' AND M03.Kolom='1') 
                              WHERE NIP=l_M15NIP AND T17.FlgDpPt='D' AND M03.Status='1') > 0 THEN 
                       BEGIN
                         l_TotalFix := P_PendAkumulasiTHR (l_TglProses,
							   l_M15NIP,
							   l_MyPass,
							   l_TotalFix);
                       END;
		    END IF; 

                    -- T H R
                    IF l_FZ1FlgTHR='Y' AND l_M15THR='Y' THEN 
                       BEGIN
                         --
                         PERFORM P_Pend_THR(l_TglProses,                       
						  l_M15TglMasuk,
						  l_FZ1TglTHRL,
						  l_FZ1TglTHRN,
						  l_FZ1TglHakL,
						  l_FZ1TglHakN,
						  l_M15NIP,
						  l_J_THR,
						  l_P_THR,
						  l_M15Agama,
						  l_FZ2THRHarian,
						  l_M15BlnTHR,
						  l_TotalFix,
						  l_MyPass, 
						  l_S01_ID);
                       END;
		    END IF; 
                        
                    -- Hitung Pajak Pendapatan
                    -- Gross Current Income
                    l_Curr_Income := 0;
                    l_Curr_Kolom8 := 0;
                    --
                    SELECT COALESCE(SUM(fn_KPusat(l_M15NIP,EncNilai,l_Mypass)::DECIMAL(15,2)),0) 
		    INTO l_Curr_Income
                    FROM S02DGAJ
                    WHERE TglPayr=l_TglProses AND NIP=l_M15NIP AND  Kolom='1' AND FlgDPPt='D'
                    GROUP BY NIP ;
                    -- Variable
                    SELECT l_Curr_Income+COALESCE(SUM(fn_KPusat(l_M15NIP,EncNilai,l_Mypass)::DECIMAL(15,2)),0) 
		    INTO l_Curr_Income
                    FROM S02DGAJ
                    WHERE TglPayr=l_TglProses AND NIP=l_M15NIP AND  (Kolom BETWEEN '3' AND '6') AND FlgDPPt='D'
                    GROUP BY NIP ;
                    -- Kolom8/Bonus
                    SELECT COALESCE(SUM(fn_KPusat(l_M15NIP,EncNilai,l_Mypass)::DECIMAL(15,2)),0)
		    INTO l_Curr_Kolom8
                    FROM S02DGAJ
                    WHERE TglPayr=l_TglProses AND NIP=l_M15NIP AND Kolom='8' AND FlgDPPt='D'
                    GROUP BY NIP ; 

                    IF COALESCE(l_Curr_Income,0)+COALESCE(l_Curr_Kolom8,0)<>0 THEN 
                       BEGIN 
                         -- Get Jenis Pajak
                          l_JnsPajak := P_Jenis_Pjk (l_TglProses,
						     l_M15NIP) ;
       
---------------------------------------SAMPE SINI -- HITUNG PAJAK BELUM  


                         -- Jenis Pajak berdasarkan Klasifikasi Pegawai                 
                         IF COALESCE(l_M10JnsPajak ,' ')=' ' THEN 
                            BEGIN
                              PERFORM P_Hitung_Pjk_TypeA(l_JnsPajak,
						l_TglProses,
						l_M15NIP,
						l_MyPass,
						l_UserID,
						l_FlagTHR,
						l_Curr_Income,
						l_Curr_Kolom8,
						'T',
						l_mnHari,
						1, 
						l_S01_ID) ; 
                            END;
                         ELSE
                            BEGIN 
                              -- Pegawai Type B
                              PERFORM P_Hitung_Pjk_TypeB(l_JnsPajak,
						l_TglProses,
						l_S05TglAkhir,  
						l_M15NIP, 
						l_MyPass,
						l_UserID,
						l_FlagTHR,
						'T',
						l_S01_ID) ; 

                           END;
			 END IF; 
                       END;
		    END IF; 
                    --*
                    -- Update Data (Update TglProses Payroll di M.Pegawai
/*
Hanya proses payroll yang update tgl. proses...

                    UPDATE M15PEGA
                    SET TglPayr=l_TglProses
                    WHERE NIP=l_M15NIP
*/
                    -- Jika Gagal Update
--?                    IF l_l_ERROR <> 0
--?                   GOTO GAGAL_PYR

-- -- BY PEGGY 2006 12 18 
                     -- Pembulatan THP 
                     IF l_M15TglKeluar is null or (l_M15TglKeluar is not null and l_TglProses <> l_M15TglKeluar) THEN 
                        BEGIN
			  PERFORM P_OIL_RND_THP(l_M15NIP, 
					l_TglProses, 
					l_UserId,
					l_MyPass );
                        END; 
		     END IF; 
                     --
                     -- Update Posting Terakhir di Summaty Posting
                     IF (SELECT COUNT(NIP) FROM S05PSTD WHERE NIP=l_M15NIP AND TglPosting=l_TglProses) > 0 THEN 
                        BEGIN
                          UPDATE S05PSTD
                          SET FlagTHR=l_FlagTHR
                          WHERE NIP=l_M15NIP AND TglPosting=l_TglProses; 
			  --RETURNING NIP ;
			   SELECT currval(pg_get_serial_sequence('s05pstd', 's05_id'))
			   INTO l_S05_ID;			  
                           -- Jika Gagal Update
                          IF l_S05_ID IS NULL THEN 
			     l_Gagal := 1;
                             EXIT LOOP_M15 ; 
                          END IF;  
                        END;
                     ELSE
                        BEGIN
                          INSERT INTO S05PSTD(NIP,    TglPosting,FlagTHR)
                                      VALUES (l_M15NIP,l_TglProses,l_FlagTHR);
			  --RETURNING NIP ;
			   SELECT currval(pg_get_serial_sequence('s05pstd', 's05_id'))
			   INTO l_S05_ID;			  
                           -- Jika Gagal Update
                          IF l_S05_ID IS NULL THEN 
			     l_Gagal := 1;
                             EXIT LOOP_M15 ; 
                          END IF;  
                        END; 
		     END IF ;
                    --
                    -- Update TakeHomePay di Header Penggajian
		    l_EncTotInc      := 0;
		    l_EncTotDed      := 0;
		    l_EncTakeHomePay := 0;
                    --  
	            SELECT SUM(CASE WHEN S02.FlgDpPt='D' 
					THEN fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass)  :: DECIMAL(15,2)
					ELSE 0 END),
		           SUM(CASE WHEN S02.FlgDpPt='P' 
					THEN fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass)  :: DECIMAL(15,2)
					ELSE 0 END)   
                    -- 
		    INTO l_EncTotInc, l_EncTotDed
                    FROM S02DGAJ S02
    	            WHERE  TglPayr=l_TglProses AND NIP=l_M15NIP                 
                    GROUP BY TglPayr,NIP;
                    -- 
                    l_EncTakeHomePay := l_EncTotInc-l_EncTotDed;
                    --
                    UPDATE S01HGAJ
                    SET EncTotInc     =fn_kcabang(l_M15NIP,l_EncTotInc::VARCHAR(17),l_MyPass),
                        EncTotDed     =fn_kcabang(l_M15NIP,l_EncTotDed::VARCHAR(17),l_MyPass),
                        EncTakeHomePay=fn_kcabang(l_M15NIP,l_EncTakeHomePay::VARCHAR(17),l_MyPass)
                    WHERE  TglPayr=l_TglProses  AND  NIP=l_M15NIP ; 
                    --    
                    -- Jika S01 Tdk punya detail maka s01 harus dihapus
                    IF (SELECT COUNT(NIP) FROM S02DGAJ WHERE NIP=l_M15NIP AND TglPayr=l_TglProses) = 0 THEN 
                       BEGIN
                          DELETE FROM S01HGAJ
                          WHERE NIP=l_M15NIP AND TglPayr=l_TglProses ;
-- BY PEGGY 2007 01 16 : KALAU GAK ADA DATA SEHARUSNYA GAK MASUK KE SUMMARY TGL PAYROLL 
                          DELETE FROM S05PSTD		
                          WHERE NIP=l_M15NIP AND TglPosting=l_TglProses AND FlagTHR=l_FlagTHR ;
                       END;  
		    END IF ;
                    --*
                   
                  -- END; Of Filter
                  END;
		END IF;
             -- END; Of Validasi          
             END;
	 END IF;
           --
--         END; -- END; Validasi Sec. Cabang
--       END IF;
       --*
       -- Simpan data Lama
       l_OldNIP := l_M15NIP ;
    --------------******* END; PROSES DATA   ********----------
--*
--
END; 
END LOOP;
CLOSE l_LOOP_M15;

-- IF l_Gagal = 1 THEN 
--    ROLLBACK ;
-- ELSE 
--    COMMIT ;
-- END IF ;

END ; 
$$ LANGUAGE plpgsql ;

/*
SELECT l_mlValid, l_TableName1
FROM  P_UTHR('2013-12-22' :: DATE, ' ', 'ZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 'ok');

SELECT l_Gagal, l_TableName1, l_mlValid
FROM P_PTHR ('2013-12-22' :: DATE, '', 'ZZZZ', 'PHW-001', 'PHW-001',
'L', 'I', '01', 'suhe', 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1); 	

SELECT * FROM S05PSTD WHERE NIP = 'PHW-001';
*/


