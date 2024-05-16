/****************************************
Name sprocs : P_PAYRP1
Create by   : wati
Date        : 16-06-2003
Description : Proses Bulanan Payroll
Call From   : Main sprocs
updated by peggy 2006 09 23 : nambahin range parameter uker. 
updated by peggy 2006 12 18 : modi oi lwg --> core dan sodetan 
*****************************************/
-- DROP FUNCTION  P_PAYRP1   (l_TglProses  	DATE,
-- 					l_LokasiFr   	VARCHAR(4),
-- 					l_LokasiTo   	VARCHAR(4), 
-- 					l_ukerFr   	VARCHAR(8),
-- 					l_ukerTo   	VARCHAR(8), 
-- 					l_NIPFr  	VARCHAR(10),
-- 					l_NIPTo  	VARCHAR(10),
-- 					l_FGOL       	VARCHAR(4),
-- 					l_TGOL       	VARCHAR(4),    
-- 					l_FCAB       	VARCHAR(4),
-- 					l_TCAB       	VARCHAR(4),    
-- 					l_J_THR  	VARCHAR(1),
-- 					l_P_THR  	VARCHAR(1),
-- 					l_FlagKhusus 	VARCHAR(1),
-- 					l_KodeLDA    	VARCHAR(2),
-- 					l_UserID     	VARCHAR(12),
-- 					OUT l_Gagal 	Int,
-- 					OUT l_TableName1 VARCHAR(8),
-- 					OUT l_mlValid    INT,
-- 					l_MyPass     	VARCHAR(128))
DROP FUNCTION  P_PAYRP1   (l_TglProses  	DATE,
					l_LokasiFr   	VARCHAR(4),
					l_LokasiTo   	VARCHAR(4), 
					l_ukerFr   	VARCHAR(8),
					l_ukerTo   	VARCHAR(8), 
					l_NIPFr  	VARCHAR(10),
					l_NIPTo  	VARCHAR(10),
					l_J_THR  	VARCHAR(1),
					l_P_THR  	VARCHAR(1),
					l_FlagKhusus 	VARCHAR(1),
					l_KodeLDA    	VARCHAR(2),
					l_UserID     	VARCHAR(12),
					OUT l_Gagal 	Int,
					OUT l_TableName1 VARCHAR(8),
					OUT l_mlValid    INT,
					l_MyPass     	VARCHAR(128),
					l_usr_id	INT);
CREATE FUNCTION  P_PAYRP1   (l_TglProses  	DATE,
					l_LokasiFr   	VARCHAR(4),
					l_LokasiTo   	VARCHAR(4), 
					l_ukerFr   	VARCHAR(8),
					l_ukerTo   	VARCHAR(8), 
					l_NIPFr  	VARCHAR(10),
					l_NIPTo  	VARCHAR(10),
					l_J_THR  	VARCHAR(1),
					l_P_THR  	VARCHAR(1),
					l_FlagKhusus 	VARCHAR(1),
					l_KodeLDA    	VARCHAR(2),
					l_UserID     	VARCHAR(12),
					OUT l_Gagal 	Int,
					OUT l_TableName1 VARCHAR(8),
					OUT l_mlValid    INT,
					l_MyPass     	VARCHAR(128),
					l_usr_id	INT)

AS $$
--
DECLARE l_S05FlagTHR 	VARCHAR(1);        l_S05TglAkhir    	DATE;	 	l_S05TglAkhir_k DATE;	
    	l_Flag_R     	VARCHAR(1);        l_Cabang         	VARCHAR(4);    	l_FZ1FlgPiut 	VARCHAR(1);
    	l_VarRound   	VARCHAR(1);        l_V_Round    	INT;         	l_Prs_OK     	VARCHAR(1);
    	l_FZ1FlgLemb 	VARCHAR(1);        l_FZ1TunjAnk 	DECIMAL(15,2);  
    	l_FZ1FlgKJab 	VARCHAR(1);        l_FZ1Caba    	VARCHAR(1);      
    	l_FZ1TglTHRL 	DATE;       	   l_FZ1TglTHRN 	DATE;
    	l_FZ1TglHakL 	DATE;       	   l_FZ1TglHakN 	DATE;
    	l_M15NIP     	VARCHAR(10);       l_M15Nama    	VARCHAR(25);    l_M15TglLahir  	DATE;
    	l_M15PrdAwl  	DATE;       	   l_M15TglKeluar   	DATE;     	l_M15kdCaba    	VARCHAR(4);
    	l_M15kdKlas  	VARCHAR(4);        l_M15TglMasuk    	DATE;     	l_M15KdGlng    	VARCHAR(4);
    	l_M15MasaKerja  INT;               l_M15kdCurr  	VARCHAR(4);     l_M15PrdTetap  	DATE;
        l_M15GajiTetap  DECIMAL(15,2);     l_M15GajiPerc    	DECIMAL(15,2);	l_M15PJK       	VARCHAR(1);
    	l_M15TunjIst 	VARCHAR(1);        l_M15TunjAnak    	VARCHAR(1);
    	l_M15Status  	VARCHAR(1);        l_M15JnsKlmn 	VARCHAR(1);     l_M15kdJaba    	VARCHAR(4);
    	l_M15AnakTtgg   INT;           	   l_M15kdKJab  	VARCHAR(4);     l_M15THR       	VARCHAR(1);
    	l_M15Agama   	VARCHAR(1);        l_M15BlnTHR  	DECIMAL(5,2); 	l_FZ1TunjIst   	DECIMAL(5,2);
    	l_M15KdArea  	VARCHAR(4);        l_M15KdUUsa  	VARCHAR(4);     l_M15KdUKer    	VARCHAR(8);
    	l_M15StsPjk  	VARCHAR(2);        l_M15KPA     	VARCHAR(11);    l_M15Rkbnk 	VARCHAR(20);
    	l_M15AccHolder  VARCHAR(25);       l_M15BankRef 	VARCHAR(4);      
    	l_FZ1FlgTHR  	VARCHAR(1);        l_M15NoSPSI  	VARCHAR(10);
    	l_M15Pajak   	VARCHAR(1);        l_FZ1IntvPajak   	DECIMAL(9,0); 	l_FZ1PrOccSupp 	DECIMAL(5,2);
    	l_M15kdCabaNEW  VARCHAR(4);    	   l_FZ1NlOccSupp   	DECIMAL(15,2);  
        l_mnKurs     	DECIMAL(10,2);     l_FZ1PTKPPay 	DECIMAL(15,2);
    	l_M15KdTerr 	VARCHAR(4);        l_FZ1PTKPDep 	DECIMAL(15,2);
    	l_M15PrdAwal    DATE;       	   l_Sumber 		VARCHAR(6);
    	l_LOOP_M15   	REFCURSOR;         l_JnsPajak       	varCHAR(1);       
        l_Curr_Income   DECIMAL(15,2);     l_Curr_kolom8    	DECIMAL(15,2); 	l_JnsPeg        VARCHAR(1);
        l_T19Pesangon   DECIMAL(15,2);     l_FZ1FlgGGol     	VARCHAR(1);	l_Curr_Income36 DECIMAL(15,2);
        l_FZ1Personalia VARCHAR(1);        l_Test           	VARCHAR(10);
        l_FilterDta     INT;        	   l_Gaji           	DECIMAL(15,2);
        l_GajiPokok     DECIMAL(15,2);     l_T18HariKerja   	VARCHAR(1);
        l_T18Jmlhari    DECIMAL(4,0);      l_M10JnsPajak    	varCHAR(1);         
        l_FZ1FlgGlng    VARCHAR(1);        l_FZ1FlgJaba     	VARCHAR(1);
        l_FZ1JumlahMangkir INT;            l_FlgCabang      	VARCHAR(1);
        l_DataValid     INT;        	   l_FilterData     	INT;
        l_Created      	INT;        	   l_mnHari         	DECIMAL(4,1);         
        l_GajiRp       	DECIMAL(15,2);     l_GajiVal        	DECIMAL(15,2);
        l_M03KdDppt    	VARCHAR(4);        l_M03Singkatan   	VARCHAR(10);
        l_M03UsComp    	VARCHAR(1);        l_M03Kolom   	VARCHAR(2);
        l_M03NoAcc     	VARCHAR(10);       l_M03Status      	VARCHAR(1);
        l_M03Persen    	DECIMAL(5,2);      l_M03Nilai       	DECIMAL(15,2);
        l_M03NoLyr     	INT;               l_M03KdCurr      	VARCHAR(4);
        l_M03Pajak     	VARCHAR(1);        l_M03Flag_N  	VARCHAR(1);
	l_M03ID         INT;	
        l_M03Flag_R    	VARCHAR(1);        l_TunjIstri      	DECIMAL(15,2);
        l_TunjIstriVal 	DECIMAL(15,2);     l_Err            	INT;
        l_FZ2FlagRapel 	INT;               l_FZ2FlRapelTHR  	INT;
        l_FZ2PrdRapel  	DATE;              l_FZ2THRharian   	VARCHAR(1);
        l_M15BerhakLemb INT;               l_M15TglKPA      	DATE;
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
        l_QPara        	VARCHAR(4000);     l_QDesc          	VARCHAR(4000);
        l_NIP1         	VARCHAR(10);       l_NewKdCaba1     	VARCHAR(4);
    	l_FixIncome  	DECIMAL(15,2);     l_T19KdCurr      	VARCHAR(4);
    	l_TunjAnak   	DECIMAL(15,2);
    	l_TunjAnakVAL   DECIMAL(15,2);
    	l_EncTotInc  	DECIMAL(15,2);
    	l_EncTotDed  	DECIMAL(15,2);
    	l_EncTakeHomePay DECIMAL(15,2);
    	l_M10Harian  	INT;
	l_JmlAbsen	DECIMAL(4,1); 
	l_FZ1NlTunjIst  DECIMAL(15,2);	   l_FZ1NlTunjAnak   	DECIMAL(15,2); 
	l_FZ1KdCutiHaid	VARCHAR(4); 	   l_FZ1KdSakitSrtDr 	VARCHAR(4); 
	l_NlTunjHaid	decimal(15,2);     l_NlTunjHaidVal	Decimal(15,2);
	l_mnHariKerja	DECIMAL(4,1); 	
	l_FZ2RNDTHPPOT	VARCHAR(1);        l_FZ2THPROUND	VARCHAR(1); 
	l_FZ2FlgAdv 	VARCHAR(1);	   l_S01_ID	 	INT; 
	l_S02_ID	INT;		   l_S05_ID	 	INT; 
	l_S0E_ID	INT; 		   l_T19_ID	 	INT; 

BEGIN 
l_mlValid := 1; 	
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
--
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
INTO    l_mnHari, 
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
FROM FZ1FLDA;
--
SELECT CASE SUBSTR(StringFlag,2,1) WHEN '1' THEN 'Y' ELSE 'T' END,
       CASE SUBSTR(StringFlag,1,1) WHEN '1' THEN 'Y' ELSE 'T' END,
       FlagRapel,
       FlRapelTHR,
       PeriodeRapel,
       SUBSTR(StringFlag,8,1),
       SUBSTR(StringFlag,9,1),
       SUBSTR(StringFlag,10,1)
INTO   l_FlgCabang,
       l_FZ2THRharian,
       l_FZ2FlagRapel,
       l_FZ2FlRapelTHR,
       l_FZ2PrdRapel,
       l_FZ2RNDTHPPOT,
       l_FZ2THPROUND,
       l_FZ2FlgAdv
FROM FZ2FLDA
WHERE Kode=l_KodeLDA;
--

-- Panggil SProc untuk Bentuk File Validasi 
SELECT * FROM P_CREATE_TEMP_ONLIST ('W0B') INTO l_TableName1, l_Created;--,	               
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
-- Jika Proses Khusus maka FlagTHR='@'
l_FlagTHR := CASE WHEN l_FlagKhusus='Y' THEN '@' ELSE ' ' END;
--
--BEGIN TRAN
----------- MULAI PROSES DATA -----------------

OPEN l_LOOP_M15 
FOR SELECT M15.NIP,M15.Nama,M15.TglLahir,M15.PrdAwl,M15.TglKeluar,
		CASE WHEN (l_FZ1Personalia='T' OR l_FlagKhusus='Y') THEN M15.KdCaba ELSE COALESCE(T10.Kdcaba,M15.KdCaba) END AS KdCaba,
		CASE WHEN (l_FZ1Personalia='T' OR l_FlagKhusus='Y') THEN M15.KdKlas ELSE COALESCE(T10.KdKlas,M15.KdKlas) END AS KdKlas,
		M15.TglMasuk,M15.TglKeluar,
		CASE WHEN (l_FZ1Personalia='T' OR l_FlagKhusus='Y') THEN M15.KdGlng ELSE COALESCE(T10.KdGlng,M15.KdGlng) END AS KdGlng,
		M15.MasaKerja,
		CASE WHEN (l_FZ1Personalia='T' OR l_FlagKhusus='Y') THEN M15.KdCurr ELSE COALESCE(T10.KdCurr,M15.KdCurr) END AS KdCurr,
		M15.PrdTetap,
		CASE WHEN (l_FZ1Personalia='T' OR l_FlagKhusus='Y') THEN 
				fn_Kpusat(M15.NIP,M15.EncGajiTetap,l_MyPass)  ::DECIMAL(15,2) 
			ELSE 
				COALESCE(T10.GajiPokok,fn_Kpusat(M15.NIP,M15.EncGajiTetap,l_MyPass) ::DECIMAL(15,2)) 
			END AS a,
		fn_Kpusat(M15.NIP,M15.EncGajiPerc,l_MyPass) ::DECIMAL(15,2) AS b, 
		M15.Pajak,M15.TunjIst,M15.TunjAnak,M15.Status,M15.JnsKlmn,
		CASE WHEN (l_FZ1Personalia='T' OR l_FlagKhusus='Y') THEN M15.KdJaba ELSE COALESCE(T10.KdJaba,M15.KdJaba) END AS KdJaba,
		M15.AnakTtgg,
		CASE WHEN (l_FZ1Personalia='T' OR l_FlagKhusus='Y') THEN M15.KdKJab ELSE COALESCE(T10.KdKJab,M15.KdKJab) END AS KdKJab,
		M15.THR,M15.Agama,M15.BlnTHR,
		CASE WHEN (l_FZ1Personalia='T' OR l_FlagKhusus='Y') THEN M15.KdArea ELSE COALESCE(T10.KdArea,M15.KdArea) END AS KdArea,
		CASE WHEN (l_FZ1Personalia='T' OR l_FlagKhusus='Y') THEN M15.KdUUsa ELSE COALESCE(T10.KdUUsa,M15.KdUUsa) END AS KdUUsa,
		CASE WHEN (l_FZ1Personalia='T' OR l_FlagKhusus='Y') THEN M15.KdUKer ELSE COALESCE(T10.KdUKer,M15.KdUKer) END AS KdUKer,
		M15.StsPjk,M15.KPA,M15.Rkbnk,M15.AccHolder,M15.BankRef,
		M15.NoSPSI,M15.KdTerr,M15.BerhakLemb,M15.TglKPA,
		M15.JKK,M15.Level,M15.JHT,M15.JKM,M15.JPK,
		M15.TglSPSI, M10.HARIAN
	FROM M15PEGA M15
	LEFT JOIN T10MUTA T10 
	ON M15.NIP=T10.NIP AND 
	   T10.TGLEFF = (SELECT MAX(X10.TGLEFF) 
			 FROM T10MUTA X10 
			 WHERE X10.TGLEFF BETWEEN COALESCE((SELECT MAX(TglPosting) 
							    FROM S05PSTD WHERE NIP=M15.NIP AND FlagTHR=l_FlagTHR),M15.TGLMASUK) + INTERVAL ' 1 day' 
					      AND l_TGLPROSES 
				AND M15.NIP = X10.NIP 
			 GROUP BY X10.NIP) 
	LEFT JOIN M10KLAS M10 
	ON M10.Kode = COALESCE(T10.KdKlas,M15.KdKlas) 
	INNER JOIN (SELECT * FROM fn_SECLOGIN(l_usr_id)) VSL 
	ON M15.kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
	
	WHERE (M15.NIP BETWEEN l_NIPFr AND l_NIPTo) AND
	      (COALESCE(T10.Kdcaba,M15.KdCaba) BETWEEN l_LokasiFr AND l_LokasiTo) AND
--	      (COALESCE(T10.KdGlng,M15.KdGlng) BETWEEN l_FGOL AND l_TGOL)  AND 
	      (COALESCE(T10.KdUKer,M15.KdUKer) BETWEEN l_UKerFr AND l_UKerTo );
<<LOOP_M15>> 
LOOP 
FETCH l_LOOP_M15 INTO l_M15NIP,l_M15Nama,l_M15TglLahir,l_M15PrdAwl,l_M15TglKeluar,l_M15kdCaba,l_M15kdKlas,
     l_M15TglMasuk,l_M15TglKeluar,l_M15KdGlng,l_M15MasaKerja,l_M15kdCurr,l_M15PrdTetap,
     l_M15GajiTetap,l_M15GajiPerc,
     l_M15Pajak,l_M15TunjIst,l_M15TunjAnak,l_M15Status,
     l_M15JnsKlmn,l_M15kdJaba,l_M15AnakTtgg,l_M15kdKJab,l_M15THR,l_M15Agama,l_M15BlnTHR,
     l_M15KdArea,l_M15KdUUsa,l_M15KdUKer,l_M15StsPjk,l_M15KPA,l_M15Rkbnk,l_M15AccHolder,l_M15BankRef,
     l_M15NoSPSI,l_M15KdTerr,l_M15BerhakLemb,l_M15TglKPA,l_M15JKK,l_M15Level,l_M15JHT,l_M15JKM,l_M15JPK,
     l_M15TglSPSI, l_M10Harian;
   IF NOT FOUND THEN
	EXIT ;
   END IF;

--   IF l_M15kdCaba BETWEEN l_FCAB AND l_TCAB THEN --AND 
--       ((l_FlagKhusus='Y') OR (l_FlagKhusus = 'T' AND l_M10Harian <> 1))
--       BEGIN
          --
           --l_S05TglAkhir := ' ';
           l_S05FlagTHR  := ' ';
           --
           -- Ambil Tgl.Akhir Proses Payroll
           SELECT TglPosting,    FlagTHR
           INTO   l_S05TglAkhir, l_S05FlagTHR
           FROM S05PSTD
--           WHERE NIP=l_M15NIP AND FlagTHR=l_FlagTHR
           WHERE NIP=l_M15NIP --AND FlagTHR=l_FlagTHR
           ORDER BY NIP,TglPosting DESC LIMIT 1;
           -- Validasi Data     
           l_DataValid := 0;
           SELECT fn_Validasi_Data(l_FlagKhusus,
					 l_S05FlagTHR,
					 l_M15NIP,
					 l_TglProses,
					 l_M15TglMasuk,
					 l_M15TglKeluar,
					 l_S05TglAkhir)
	   INTO l_DataValid;
             
           IF l_DataValid<>0 and l_datavalid <> 2 THEN 
              BEGIN  
                 -- Jika Pegawai belum Aktif tdk perlu dimasukkan ke list validasi
                 IF l_DataValid<>1 THEN 
                    BEGIN                      
                      l_mlValid := 0;                 
                      PERFORM P_CLSTEMP (l_M15NIP, l_M15Nama, l_S05TglAkhir,l_DataValid::VARCHAR(2),l_TableName1) ;                      
                    END;
                 END IF;    
                 --*
              END; 
           END IF;        
           IF l_DataValid=0 THEN 
              BEGIN  
               IF (l_FlagKhusus = 'T' AND l_M10Harian = 1) THEN 
                  BEGIN 
		    l_FilterData := 7;           
		    l_mlValid    := 0;  
                    PERFORM P_CLSTEMP (l_M15NIP, l_M15Nama, l_S05TglAkhir,l_FilterData::VARCHAR(2),l_TableName1) ;                      
                  END;
               END IF; 
              END;        
	   END IF; 
--           ELSE
              IF l_DataValid=0 THEN 
                 BEGIN
                -- 
                -- Call Procedure Filter data
                   l_M10Harian := 0;
		   l_FilterData:= 0;
                ---
                SELECT Harian
                INTO   l_M10Harian 
                FROM M10KLAS
                WHERE Kode=l_M15kdKlas; 
                -- Jika Pegawai Harian Tdk Diproses
-- remark 
-- tadi nya di remark, tapi pegawai harian jadi  terproses dengan nilai 0 
-- makanya dicabut remarknya by peggy 2006 09 08 
-- update by peggy 2006 10 10 : thr khusus untuk swadaya : peg harian -- 
               IF (l_FlagKhusus = 'T' AND l_M10Harian = 1) THEN 
--               IF l_M10Harian=1 
                  BEGIN 
                    l_FilterData := 7;           
                  END; 
               END IF;    
                --* 
                -- Jika Ada     
               IF l_FilterData<>0 THEN 
                   BEGIN
                      l_mlValid := 0; 
                      PERFORM P_CLSTEMP (l_M15NIP, l_M15Nama, l_S05TglAkhir,l_FilterData::VARCHAR(2),l_TableName1) ;                      
                   END;        
                ELSE 
                   BEGIN
		      IF l_FlagKhusus='Y' THEN 
			 BEGIN	
			 -- Unpost Payroll
			    PERFORM P_UPBLKH(l_TglProses,
					    l_LokasiFr,
					    l_LokasiTo,
					    l_M15NIP,
					    l_M15NIP,
					    'T'); 
			 END;
		       ELSE
			 BEGIN	
			-- Unpost Payroll
			    PERFORM P_UPBLKH(l_TglProses,
					    l_LokasiFr,
					    l_LokasiTo,
					    l_M15NIP,
					    l_M15NIP,
					    'Y'); 
			 END; 
		       END IF; 
                -- Akibat Unpost S05 Harus di ambil lagi
		     --  l_S05TglAkhir := NULL;
                     --  l_S05FlagTHR  := ' '; 
                --
		-- Ambil Tgl.Akhir Proses Payroll
			SELECT TglPosting,    FlagTHR
			INTO   l_S05TglAkhir, l_S05FlagTHR
			FROM S05PSTD
			WHERE NIP=l_M15NIP AND FlagTHR=l_FlagTHR 
			ORDER BY NIP,TglPosting;   
				--        
			-- Hitung Jumlah H kerja dasar Absensi
			-- Khusus Payroll PT. RTI
			l_JmlAbsen := 0; 
                --
			SELECT COALESCE(fn_JmlAbsn(l_M15NIP,l_S05TglAkhir,l_TglProses),0)
			INTO l_JmlAbsen ;
				-- Jika Nilai Absensi hari kerjanya ada maka hari kerja diambil dari Absensi
			IF l_JmlAbsen<>999.9 THEN 
			   BEGIN
			      l_mnHari := l_JmlAbsen ;
			   END;
			END IF;    
			--* 
			-- post efektif status 
			IF l_FlagKhusus='Y' THEN 
			   BEGIN
			-- Ambil Tgl.Akhir u/ Proses Payroll khusus
				SELECT TglPosting
				INTO   l_S05TglAkhir_k
				FROM S05PSTD
				WHERE NIP=l_M15NIP AND FlagTHR=' '
		                ORDER BY NIP,TglPosting;  
			   END;
			ELSE
 			   BEGIN
				l_S05TglAkhir_k := l_S05TglAkhir;
			   END; 
			END IF; 
			-- 
			PERFORM P_EFSTP1(l_M15NIP,
					l_S05TglAkhir_k,
					l_TglProses,
					l_UserID,
					l_MyPass) ;
			 --
			 -- Ambil Ulang Posisi Pegawai Terakhir
			SELECT Status,      AnakTtgg,      StsPjk
			INTO   l_M15Status, l_M15AnakTtgg, l_M15StsPjk
			FROM M15PEGA
			WHERE NIP=l_M15NIP;  
			--

			-- Fasilitas Modul HRD        
			-- post hrd hanya jika bukan proses khusus        
			IF l_FZ1Personalia='Y' and l_FlagKhusus='T' THEN 
			   BEGIN
			     -- 
			     PERFORM P_prsHRD(l_M15NIP,
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
			--*
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
			FROM  M10KLAS 
			WHERE Kode=l_M15kdKlas;

                     -- Ambil Nilai yg diperlukan u/Insert S02DGAJ
			SELECT * FROM fn_Vround(l_GajiPokok*l_mnKurs) INTO l_GajiRp; 
			SELECT * FROM fn_Vround(l_GajiPokok) INTO l_GajiVal; 
			-- jml hari kerja 
			SELECT * FROM fn_Hit_Hari_Kerja(l_TglProses,
						l_M15PrdTetap,
						l_M15NIP,
						l_M15TglMasuk,
						l_mnHari) 
			INTO l_mnHariKerja; 

                     -- Insert header S01HGAJ
                     --
                     IF (SELECT COUNT(TglPayr) FROM S01HGAJ WHERE TglPayr=l_TglProses AND NIP=l_M15NIP ) = 0 THEN 
                        BEGIN
                           INSERT INTO S01HGAJ(S01_ID, TglPayr    ,NIP     ,Nama     ,KdKlas     ,KdArea     ,KdCaba     ,KdUUsa     ,KdUKer     ,KdGlng     ,KdKJab     ,KdJaba     ,StsPjk     ,CommDate     ,TermDate      ,JnsKlmn     ,StsSip     ,TotAnak      ,KPA     ,AccHolder     ,RkBnk     ,BankRef     ,Hari         ,JnsPajak     ,NoAcc,NoSpt,TakeHomePay,EncTakeHomePay                    ,TotInc,TotDed,GrossIncomeNBYTD,OccSupport1,Col12YTD,EGIYNB,PTKP,EYTI,EYIT,YTDIT,IncTaxAPaidNB,GrossIncomeBYTD,OccSupport2,TaxTotal,IncTaxAPaidB,FlagTHR  ,AkmHari,FlagM,FlagH ,TaxPesangonRp,Recs,EncTotInc                          ,EncTotDed 			      , EncGrossIncomeNBYTD		  , EncOccSupport1, 		       EncCol12YTD, 			   EncEGIYNB, 			       EncPTKP, 			   EncEYTI, 			       EncEYIT, 			   EncYTDIT, 			       EncIncTaxAPaidNB, 		   EncGrossIncomeBYTD, 		       EncOccSupport2, 		  	   EncTaxTotal, 		       EncIncTaxAPaidB, 		   EncTaxPesangonRp                  ,VERSION, CREATED_BY, CREATED_ON,   UPDATED_BY, UPDATED_ON)
                                        VALUES(DEFAULT,l_TglProses,l_M15NIP,l_M15Nama,l_M15kdKlas,l_M15KdArea,l_M15kdCaba,l_M15KdUUsa,l_M15KdUKer,l_M15KdGlng,l_M15kdKJab,l_M15kdJaba,l_M15StsPjk,l_M15TglMasuk,l_M15TglKeluar,l_M15JnsKlmn,l_M15Status,l_M15AnakTtgg,l_M15KPA,l_M15AccHolder,l_M15Rkbnk,l_M15BankRef,l_mnHariKerja,l_M10JnsPajak,' '  ,' '  ,0          ,fn_kcabang(l_M15NIP, '0',l_MyPass),0     ,0     ,0               ,0          ,0       ,0     ,0   ,0   ,0   ,0    ,0            ,0              ,0          ,0       ,0           ,l_FlagTHR,0      ,' '  ,' '   ,0            ,0   ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass) ,fn_kcabang(l_M15NIP, '0',l_MyPass),0      , l_userid,   localtimestamp, null,       null );
--			   RETURNING S01_ID;-- AS l_S01_ID ; 
			   SELECT currval(pg_get_serial_sequence('s01hgaj', 's01_id'))
			   INTO l_S01_ID;

		           IF l_S01_ID IS NULL THEN 
			      l_Gagal := 1;
                              EXIT LOOP_M15 ; 
                           END IF;                            
                        END  ;
                     END IF;                                         
                     --- Insert Gaji
                     IF l_FlagKhusus='T' THEN 
                        BEGIN
			   SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
				  fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
			   from  P_M03AllField ('D', 'BSAL' ) fx
			   INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
			 	   l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;                        
                               -- Insert Gaji  
                           INSERT INTO S02DGAJ(TglPayr    ,NIP     ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                ,Nilai,KdCurr     ,EncNilaiVal                                             ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                                        VALUES(l_TglProses,l_M15NIP,'D'    ,'BSAL',' '    ,l_M03Singkatan,fn_kcabang(l_M15NIP, (l_GajiRp :: VARCHAR(17)),l_MyPass),0    ,l_M15kdCurr,fn_kCabang(l_M15NIP,(l_GajiVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);  
                        END; 
                     END IF;    
                     -- Tunjangan Istri 
-- BY PEGGY 2006 12 18 
--                     IF COALESCE(l_FZ1TunjIst,0)<>0 AND l_M15TunjIst='Y' AND 
                     IF (COALESCE(l_FZ1TunjIst,0)<>0 OR COALESCE(l_FZ1NlTunjIst,0)<>0) AND 
			l_M15TunjIst='Y' AND 
                        l_M15JnsKlmn='L' AND l_M15Status='K' AND l_FlagKhusus='T' THEN 
                        BEGIN
			   SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
				  fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
			   from  P_M03AllField ('D', 'TK01' ) fx			   
			   INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
			 	   l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;                        
                          --
-- BY PEGGY 2006 12 18 
--                          SELECT l_TunjIstri=fn_Vround((l_FZ1TunjIst*l_GajiRp)/100),
--                                 l_TunjIstriVal=fn_Vround(((l_FZ1TunjIst*l_GajiRp)/100)/fn_GetKurs(l_M03KdCurr,l_TglProses))
		           l_TunjIstri := CASE WHEN l_FZ1TunjIst <> 0 
						THEN fn_Vround((l_FZ1TunjIst*l_GajiRp)/100)
						ELSE fn_Vround(l_FZ1NlTunjIst)
						END;
			   l_TunjIstriVal := CASE WHEN l_FZ1TunjIst <> 0 
						THEN fn_Vround(((l_FZ1TunjIst*l_GajiRp)/100)/fn_GetKurs(l_M03KdCurr,l_TglProses))
						ELSE fn_Vround(l_FZ1NlTunjIst)
						END; 
                          -- Insert/Update Tunjangan Istri
                          IF (SELECT COUNT(NIP)
				FROM S02DGAJ 
				WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND 
					COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='TK01') > 0 THEN 
                             BEGIN
                               UPDATE S02DGAJ
                               SET KdCurr=l_M03KdCurr,
                                   EncNilaiVal =fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_TunjIstriVal) ::VARCHAR(17) ,l_MyPass),
                                   EncNilai=fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_TunjIstri) :: VARCHAR(17), l_MyPass)
                               WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='TK01';  
                               --RETURNING S02_ID AS l_S02_ID ;
			       SELECT currval(pg_get_serial_sequence('s02dgaj', 's02_id'))
			       INTO l_S02_ID;

                               -- Jika Gagal Update
			       IF l_S02_ID IS NULL THEN 
			          l_Gagal := 1;
			          EXIT LOOP_M15 ; 
			       END IF;                            
                             END; 
                          ELSE
                             BEGIN
                               INSERT INTO S02DGAJ(TglPayr    ,NIP     ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                  ,Nilai,KdCurr     ,EncNilaiVal                                                  ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                                            VALUES(l_TglProses,l_M15NIP,'D'    ,'TK01',' '    ,l_M03Singkatan,fn_kcabang(l_M15NIP,(l_TunjIstri :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_M15NIP,(l_TunjIstriVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID)    ;
			       SELECT currval(pg_get_serial_sequence('s02dgaj', 's02_id'))
			       INTO l_S02_ID;

                               -- Jika Gagal Update
			       IF l_S02_ID IS NULL THEN 
			          l_Gagal := 1;
			          EXIT LOOP_M15 ; 
			       END IF;                            
                             END; 
			  END IF; 
                        END;     
		     END IF; 
-- BY PEGGY 2006 12 18 
                     -- Tunjangan Keluarga
                     IF (COALESCE(l_FZ1TunjIst,0)<>0 OR COALESCE(l_FZ1NlTunjIst,0)<>0) AND 
			l_M15TunjIst='Y' AND 
                        l_M15JnsKlmn='P' AND l_M15Status='K' AND l_FlagKhusus='T'  THEN 
                        BEGIN
			   PERFORM P_OIL_TUNJ_KEL(l_M15NIP, 
						l_TglProses, 
						l_FZ1TunjIst,
						l_FZ1NlTunjIst,
						l_GajiRp,
						l_MyPass,
						l_S01_ID) ; 
						
                        END;
		     END IF; 

                     -- Tunjangan Anak                
-- BY PEGGY 2006 12 18 
--                     IF COALESCE(l_FZ1TunjAnk,0)<>0 AND l_M15TunjAnak='Y' AND 
-- BY PEGGY 2007 05 21 : PINDAH KE FN_EVALTUNJANAK 
-- --                      IF (COALESCE(l_FZ1TunjAnk,0)<>0 OR COALESCE(l_FZ1NlTunjAnak,0)<>0) AND 
-- -- 						l_M15TunjAnak='Y' AND 
-- --                         ((l_M15JnsKlmn='L' AND (l_M15Status='K' OR l_M15Status='D')) OR 
-- --                         (l_M15JnsKlmn='P' AND l_M15Status='X')) AND l_M15AnakTtgg > 0 AND l_FlagKhusus='T'
		     IF (SELECT FN_EVALTUNJANAK(l_FZ1TunjAnk,l_FZ1NlTunjAnak,l_M15TunjAnak,l_M15JnsKlmn,l_M15Status,l_M15AnakTtgg)) = 'Y' AND 
			  l_FlagKhusus='T' THEN 
                        BEGIN
                          -- Hitung Nilai Tunjangan Anak
-- BY PEGGY 2006 12 18 
--                          SELECT l_TunjAnak=fn_Vround(fn_Tunjangan_Anak(l_M15AnakTtgg,l_FZ1TunjAnk,l_GajiRp))
                           l_TunjAnak := fn_Vround(fn_Tunjangan_Anak(l_M15AnakTtgg,l_FZ1TunjAnk,l_FZ1NlTunjAnak,l_GajiRp)); 
                          -- Update Tunjangan Anak 
			   SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
				  fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
			   from  P_M03AllField ('D', 'TK02' ) fx
			   INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
			 	   l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;                        
                     
                          -- Nilai Kurs
                           l_TunjAnakVal := fn_Vround(l_TunjAnak/fn_GetKurs(l_M03KdCurr,l_TglProses));
                          -- 
                           IF (SELECT COUNT(NIP) FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='TK02') > 0 THEN 
                             BEGIN
                               UPDATE S02DGAJ
			       SET KdCurr=l_M03KdCurr,
				   NilaiVal=0,
				   EncNilaiVal = fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP, EncNilaiVal, l_MyPass) :: Decimal(15,2)+l_TunjAnakVal) :: VARCHAR(17),l_MyPass),
				   Nilai=0,
				   EncNilai=fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_TunjAnak) :: VARCHAR(17), l_MyPass)
                               WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='TK02';   
                             END; 
                          ELSE
                             BEGIN
                               --
                               INSERT INTO S02DGAJ(TglPayr    ,NIP     ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                 ,Nilai,KdCurr     ,EncNilaiVal                                                 ,NilaiVal,Kolom     ,NoAcc     ,UsComp     ,NoLyr     ,FlgNonGL, S01_ID)
                                           VALUES (l_TglProses,l_M15NIP,'D'    ,'TK02',' '    ,l_M03Singkatan,fn_kcabang(l_M15NIP,(l_TunjAnak :: VARCHAR(17)),l_MyPass),    0,l_M03KdCurr,fn_kcabang(l_M15NIP,(l_TunjAnakVal :: VARCHAR(17)),l_MyPass),       0,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,       0, l_S01_ID); 
                             END;
                          END IF;    
                        END; 
                     END IF;    

                     -- Pendapatan/Potongan Cabang
--                      IF l_FZ1Caba='Y' AND EXISTS(SELECT KdCab FROM M09DCAB WHERE KdCab=l_M15kdCaba)
--                         BEGIN
--                           EXEC P_DasarKelompok l_TglProses,
--                                                l_M15kdCaba,
--                                                l_M15NIP, 
--                                        	       'M09DCAB',
--                                                l_FlagKhusus,
--                                                l_GajiRp,
--                                                l_mnHari,
--                                                'T',
--                                                 0, 
--                                                l_MyPass 
--                         END
-- 
--                      -- Pendapatan/Potongan Golongan
--                      IF l_FZ1FlgGlng='Y' AND EXISTS(SELECT Kode FROM M13DGOL WHERE Kode=l_M15KdGlng)
--                         BEGIN
--                           EXEC P_DasarKelompok l_TglProses,
--                                    	       l_M15KdGlng,
--                                                l_M15NIP, 
--                                        	       'M13DGOL',
--                                                l_FlagKhusus,
--                                                l_GajiRp,
--                                                l_mnHari,
--                                                'T',
--                                                0, 
--                                                l_MyPass 
--                         END
-- 
--                      -- Pendapatan/Potongan Kelompok Jabatan
--                      IF l_FZ1FlgKJab='Y' AND EXISTS(SELECT Kode FROM M07DKJB WHERE Kode=l_M15kdKJab)
--                         BEGIN
--                           EXEC P_DasarKelompok l_TglProses,
--                                                l_M15kdKJab,
--                                                l_M15NIP, 
--                                                'M07DKJB',
--                                                l_FlagKhusus,
--                                                l_GajiRp,
--                                                l_mnHari,
--                                                'T',
--                                                 0, 
--                                                l_MyPass 
--                         END
-- 
--                      -- Pendapatan/Potongan Jabatan
--                      IF l_FZ1FlgJaba='Y' AND EXISTS(SELECT Kode FROM M05DJAB WHERE Kode=l_M15kdJaba)
--                         BEGIN
--                           EXEC P_DasarKelompok l_TglProses,
--                                l_M15kdJaba,
--                                                l_M15NIP, 
--                                        		   'M05DJAB',
--                                                l_FlagKhusus,
--                                                l_GajiRp,
--                                                l_mnHari,
--                                                'T',
--                                                 0, 
--                                                l_MyPass 
--                         END

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
			        PERFORM P_DasarKelompokAdv(l_TglProses,
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
                     IF (SELECT COUNT(NIP) FROM T04TTAP WHERE NIP=l_M15NIP) > 0 THEN 
                        BEGIN
                         PERFORM P_Pend_Tetap(l_TglProses,
                                            l_FlagKhusus,
                                            l_M15NIP,
                                            l_GajiRp,
                                            l_mnHari,
                                            'T',
                                            0,  
                                            l_MyPass, 
					    l_S01_ID);  
                       END;                        
		     END IF; 
                     -- Pendapatan/Potongan Variable
                     IF (SELECT COUNT(NIP) FROM T03VARI WHERE NIP=l_M15NIP AND PrdMulai <= l_Tglproses) > 0 THEN 
                        BEGIN
                         PERFORM P_Pend_Vari(l_TglProses,
                                            l_S05TglAkhir,
                                            l_FlagKhusus,
                                            l_M15NIP,
                                      	    l_GajiRp,
		                            'T',
					    l_MyPass,
					    l_S01_ID); 
                        END;
                     END IF;                        

                     -- Pendapatan/Potongan Harian  
                     IF (SELECT COUNT(NIP) FROM T02ABSN WHERE NIP=l_M15NIP AND Tanggal>l_S05TglAkhir AND Tanggal<=l_TglProses) > 0 THEN 
                        BEGIN
                         PERFORM P_Pend_Harian(l_TglProses,
                                              l_FlagKhusus,
                                              l_S05TglAkhir,
                                              l_M15NIP,
                                              l_GajiRp,
                                              l_FZ1JumlahMangkir, 
					      l_MyPass,
					      l_S01_ID); 
                        END;
                     END IF;                         
                     -- Tunjangan Haid 
		     -- Syarat : perempuan dan tidak ada absen = cuti haid dan tidak ada absen lain selain absen sakit 
		     --	dengan surat dokter 
-- BY PEGGY 2007 05 21 : PINDAH KE FN_EVALTUNJHAID
-- -- 		     IF l_M15JnsKlmn='P' AND l_FlagKhusus='T' AND l_FZ1KdSakitSrtDr IS NULL AND
-- -- 			(NOT EXISTS(SELECT S06.NIP FROM S06ABSH S06 
-- -- 					INNER JOIN M29JNSA M29 ON S06.KDABSN = M29.KODE 
-- -- 					WHERE S06.NIP=l_M15NIP AND 
-- -- 						S06.Tanggal BETWEEN l_S05TglAkhir AND l_TglProses AND 
-- -- 						S06.KdAbsn = l_FZ1KdCutiHaid AND 
-- -- 						M29.JnsCuti = 1)) AND 
-- -- 			(NOT EXISTS(SELECT S06.NIP FROM S06ABSH S06 
-- -- 					INNER JOIN M29JNSA M29 ON S06.KDABSN = M29.KODE 
-- -- 					WHERE S06.NIP=l_M15NIP AND 
-- -- 						S06.Tanggal BETWEEN l_S05TglAkhir AND l_TglProses AND 
-- -- 						KdAbsn <> l_FZ1KdSakitSrtDr AND 
-- -- 						M29.JnsCuti = 0)) 
		     IF (SELECT FN_EVALTUNJHAID(l_FZ1KdCutiHaid,l_FZ1KdSakitSrtDr,l_M15JnsKlmn,l_S05TglAkhir,l_TglProses,l_M15TglLahir,l_M15NIP)) = 'Y' AND 
			  l_FlagKhusus='T' THEN 
		        BEGIN
			  SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
				  fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
			   from  P_M03AllField ('D', 'HAID' ) fx
			  INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
				  l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr, l_M03ID ;
			  --
			  l_NlTunjHaid    := fn_NilaiPend_Pot(l_TglProses,l_M15NIP,'D','HAID',l_M03Persen,l_M03Nilai,1,l_GajiRp,'T',l_MyPass,l_M03KdCurr);
			  l_NlTunjHaidVal := fn_NilaiPend_Pot(l_TglProses,l_M15NIP,'D','HAID',l_M03Persen,l_M03Nilai,1,l_GajiRp,'T',l_MyPass,l_M03KdCurr); 
			  -- Insert/Update Tunjangan Istri
			  IF (SELECT COUNT(NIP) FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='HAID') > 0 THEN 
			     BEGIN
			       UPDATE S02DGAJ
			       SET KdCurr=l_M03KdCurr,
				   EncNilaiVal =fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_NlTunjHaidVal) ::VARCHAR(17),l_MyPass),
				   EncNilai=fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_NlTunjHaid) :: VARCHAR(17), l_MyPass)
			       WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='HAID';  
                               --RETURNING S02_ID AS l_S02_ID ;
			       SELECT currval(pg_get_serial_sequence('s02dgaj', 's02_id'))
			       INTO l_S02_ID;                               

                               -- Jika Gagal Update
			       IF l_S02_ID IS NULL THEN 
			          l_Gagal := 1;
			          EXIT LOOP_M15 ; 
			       END IF;                            
			     END; 
			  ELSE
			     BEGIN
			       INSERT INTO S02DGAJ(TglPayr    ,NIP     ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                    ,Nilai,KdCurr     ,EncNilaiVal                                                   ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
					    VALUES(l_TglProses,l_M15NIP,'D'    ,'HAID',' '    ,l_M03Singkatan,fn_kcabang(l_M15NIP, (l_NlTunjHaid :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_M15NIP,(l_NlTunjHaidVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID)   ;
                              --RETURNING S02_ID AS l_S02_ID ;
			       SELECT currval(pg_get_serial_sequence('s02dgaj', 's02_id'))
			       INTO l_S02_ID;                              

                              -- Jika Gagal Update
			      IF l_S02_ID IS NULL THEN 
			         l_Gagal := 1;
			         EXIT LOOP_M15 ; 
			      END IF;                            
			     END; 
			  END IF;                             
			END;
		     END IF;  
				
		     -- Sodetan untuk PT. OTSUKA untuk hitung Incentive Pegawai
		     --- New by suhe 03-02-2005
		     IF l_FlagKhusus='T' THEN 
		        BEGIN
			   PERFORM P_InstOtsu(l_M15NIP,
						l_TglProses,
						l_S05TglAkhir,
						l_GajiRp,
						l_M15TglMasuk,
						l_M15KdGlng,
						l_MyPass, 
					        l_S01_ID); 
                        END;
                     END IF; 						

		     -- Sodetan untuk PT. OTSUKA LAWANG untuk hitung Incentive Pegawai 
		     --- New by PEGGY 2006 12 22 
		     IF l_FlagKhusus='T' THEN 
		        BEGIN
		           PERFORM P_OILIncNTrans(l_M15NIP,
						 l_TglProses,
						 l_S05TglAkhir,
						 l_GajiRp,
						 l_M15TglMasuk,
						 l_M15KdGlng,
						 l_M15KdCaba,
						 l_MyPass, 
					         l_S01_ID); 
                        END;
                     END IF; 						
                     -- Pendapatan Akumulasi
                     IF (SELECt COUNT(NIP) FROM T17PDAK WHERE NIP=l_M15NIP) > 0 THEN 
                        BEGIN
                         PERFORM P_PendAkumulasi(l_TglProses,
                                                l_M15NIP,
                                                l_FlagKhusus,
					        l_MyPass,
					        l_S01_ID); 
                        END;
                     END IF;                         

                     -- Rapel Gaji/THR
                     IF l_FlagKhusus='T' AND l_FZ2FlagRapel=1 AND DATE_TRUNC('month',l_FZ2PrdRapel) < DATE_TRUNC('month',l_TglProses) THEN 
                        BEGIN
                          PERFORM P_Pend_RapelGaji(l_TglProses,
                                                l_FZ2PrdRapel,
                                                l_GajiRp,
                                                l_M15NIP,
                                                l_MyPass,
                                                l_S01_ID);
                          -- Rapel THR 
                          IF l_FZ2FlRapelTHR=1 THEN 
                             BEGIN
                               PERFORM P_Pend_RapelTHR(l_TglProses,
                                                    l_FZ2PrdRapel,
                                                    l_FZ2THRHarian,
                                                    l_M15TglMasuk,
                                                    l_GajiRp,
                                                    l_M15NIP,
                                                    l_M15BlnTHR, 
                                                    l_MyPass,
                                                    l_S01_ID);
                             END; 
                          END IF;    
                        END;
		     END IF; 

                     -- T H R
                     IF l_FZ1FlgTHR='T' AND l_M15THR='Y' AND l_FlagKhusus='T' THEN 
                        BEGIN
                          -- Hitung Fix Income by Suhe
                          l_FixIncome := 0;
                          SELECT SUM(fn_KPusat(l_M15NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2))
                          INTO   l_FixIncome
                          FROM S02DGAJ S02
			  INNER JOIN M03DPPT M03 
			  ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
			  WHERE NIP=l_M15NIP AND M03.STATUS = '1' AND S02.TglPayr=l_TglProses 
                          GROUP BY S02.NIP;
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
						  l_FixIncome,
						  l_MyPass, 
						  l_S01_ID);
                        END;
		     END IF; 
                     -- Piutang Pegawai
                     IF l_FZ1FlgPiut='Y' AND l_FlagKhusus='T' AND 
			(SELECT COUNT(NIP) FROM T06DPIU WHERE NIP=l_M15NIP AND PrdAngs > l_S05TglAkhir AND PrdAngs <= l_TglProses) > 0 THEN 
                        BEGIN
                         PERFORM P_Piutang_Peg(l_TglProses,
                                              l_S05TglAkhir,                       
                                              l_M15NIP,                           
                                              l_UserID,
					      l_MyPass,
					      l_S01_ID); 
                        END;

                     END IF;                         
                     -- Lembur
                     IF l_FZ1FlgLemb='Y' AND (SELECT COUNT(NIP) FROM T01LEMB WHERE NIP=l_M15NIP) > 0 THEN 
                        BEGIN
                         PERFORM P_Lembur(l_TglProses,
                                         l_S05TglAkhir,
                                         l_FlagKhusus,
                                         l_M15NIP,
                                         l_M15BerhakLemb,
                                         l_M15KdArea,
                                         l_M15KdCaba,
                                         l_M15KdUUsa,
                                         l_M15KdUker, 
                                         l_M15KdJaba,
                                         l_M15Nama,
                                         l_UserID,  
				         l_MyPass,
				         l_S01_ID); 
                        END;
                     END IF;                        
                     -- Jamsostek
                     IF COALESCE(l_M15KPA,' ')<>' ' AND l_M15TglKPA<=l_TglProses AND l_FlagKhusus='T' THEN 
                        BEGIN
                          -- Dapat JKK
                          IF l_FZ1JKK='Y' AND l_M15JKK='Y' THEN 
                             BEGIN 
                               -- Ambil Level JKK  
                               IF COALESCE(l_M15Level,' ')=' ' THEN 
                                  BEGIN
                                    SELECT Kelompok
                                    INTO   l_Level
                                    FROM M02UUSA WHERE Kode=l_M15KdUUsa; 
                                  END; 
                               ELSE
                                  BEGIN
                                    l_Level := l_M15Level;
                                  END; 
                               END IF;     

                               -- Call Proc. Hitung jamina Kecelakaan Kerja (JKK)
                               PERFORM P_Hit_JKK(l_TglProses,
                                               l_Level,
                                               l_M15NIP,
				               l_MyPass,
				               l_S01_ID); 
                             END;
			  END IF; 
                          -- Jaminan Hari Tua (JHT)
                          IF l_FZ1JHT='Y' AND l_M15JHT='Y' THEN 
                             BEGIN
                              PERFORM P_Hit_JHT(l_TglProses,
                                               l_M15NIP,
				               l_MyPass,
				               l_S01_ID); 
                             END;
			  END IF; 
                          -- Jaminan Kematian (JKM)
                          IF l_FZ1JKM='Y' AND l_M15JKM='Y' THEN 
                             BEGIN
                               PERFORM P_Hit_JKM(l_TglProses,
                                               l_M15NIP,
				               l_MyPass,
				               l_S01_ID); 
                             END;
			  END IF;                              
                          -- Jaminan Hari Tua (JPK)
                          IF l_FZ1JPK='Y' AND l_M15JPK='Y' THEN 
                             BEGIN
                              PERFORM P_Hit_JPK(l_TglProses,
                                               l_M15NIP,
                                               l_M15Status,   
				               l_MyPass,
				               l_S01_ID); 
                             END; 
			  END IF; 
                        END; 
                     END IF; 
                     -- SPSI
                     IF COALESCE(l_M15NoSPSI,' ')<>' ' AND l_M15TglSPSI<=l_TglProses AND l_FlagKhusus='T' THEN 
                        BEGIN
                          -- Hitung Pendapatan Tetap
                          SELECT fn_Vround((TBL.FixIncome*l_FZ1IuranSPSI)/100)
                          INTO   l_NilaiSPSI
                          FROM
                          (
                          SELECT S02.NIP,SUM(fn_KPusat(l_M15NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)) AS FixIncome
                          FROM S02DGAJ S02
			  INNER JOIN M03DPPT M03 
			  ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
			  WHERE NIP=l_M15NIP AND M03.STATUS = '1' AND S02.TglPayr=l_TglProses 
                          GROUP BY S02.NIP
                          ) TBL;
                          -- Ambil Nilai Master Pandapatan dan Potongan
			  SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
				  fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
			   from  P_M03AllField ('D', 'SPSI' ) fx 
			  INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
				  l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;                          
                          -- Get Kurs
                          l_NilaiSPSIVal := fn_Vround(l_NilaiSPSI/fn_GetKurs(l_M03KdCurr,l_TglProses)); 

                          -- Insert dan Update untuk Potongan SPSI
                          IF (SELECT COUNT(NIP) FROM S02DGAJ WHERE NIP=l_M15NIP AND TglPayr=l_TglProses AND 
                                    FlgDpPt='P' AND KdDpPt='SPSI' AND COALESCE(FlgAngs,' ')=' ') > 0 THEN 
                             BEGIN
                               UPDATE S02DGAJ
                               SET EncNilaiVal =fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_NilaiSPSIVal) ::VARCHAR(17),l_MyPass),
                                   EncNilai=fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP, EncNilai,l_MyPass) :: Decimal(15,2)+l_NilaiSPSI) :: VARCHAR(17),l_MyPass)
                               WHERE NIP=l_M15NIP AND TglPayr=l_TglProses AND FlgDpPt='P' AND KdDpPt='SPSI' AND COALESCE(FlgAngs,' ')=' ';
                               --RETURNING S02_ID AS l_S02_ID ;
			       SELECT currval(pg_get_serial_sequence('s02dgaj', 's02_id'))
			       INTO l_S02_ID;                             

                               -- Jika Gagal Update
			       IF l_S02_ID IS NULL THEN 
			          l_Gagal := 1;
			          EXIT LOOP_M15 ; 
			      END IF;                            
                             END;
                          ELSE
                             BEGIN
                               INSERT INTO S02DGAJ(TglPayr    ,NIP     ,FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                  ,Nilai,KdCurr     ,EncNilaiVal                                                  ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                                            VALUES(l_TglProses,l_M15NIP,'P'    ,'SPSI',' '    ,l_M03Singkatan,fn_kcabang(l_M15NIP,(l_NilaiSPSI :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_M15NIP,(l_NilaiSPSIVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);   
                               --RETURNING S02_ID AS l_S02_ID ;
			       SELECT currval(pg_get_serial_sequence('s02dgaj', 's02_id'))
			       INTO l_S02_ID;
                               
                               -- Jika Gagal Update
			       IF l_S02_ID IS NULL THEN 
			          l_Gagal := 1;
			          EXIT LOOP_M15 ; 
			       END IF;                                                                     
                             END; 
                          END IF;    
                        END; 
                     END IF;                           
                     -- Asuransi
                     IF l_FlagKhusus='T' AND 
			(SELECT COUNT(NIP) FROM T22KPAS WHERE NIP=l_M15NIP) > 0 and 
			(SELECT SUBSTR(StringFlag,6,1) FROM FZ2FLDA LIMIT 1) = 'T' THEN 
                        BEGIN
                          PERFORM P_Pot_Asuransi(l_TglProses,
                                                l_M15NIP,
                                                l_MyPass,
                                                l_S01_ID); 
                        END; 
                     END IF;    
                     -- Hitung Pajak Pendapatan
                     -- Gross Current Income
                     l_Curr_Income := 0;
                     l_Curr_Kolom8 := 0;
                     --  
                     SELECT SUM(fn_KPusat(l_M15NIP,EncNilai,l_Mypass) ::DECIMAL(15,2)) 
                     INTO   l_Curr_Income
                     FROM S02DGAJ
                     WHERE TglPayr=l_TglProses AND NIP=l_M15NIP AND  Kolom='1' AND FlgDPPt='D'
                     GROUP BY NIP; 
                     -- Variable
                     SELECT COALESCE(SUM(fn_KPusat(l_M15NIP,EncNilai,l_Mypass) ::DECIMAL(15,2)),0)  
                     INTO   l_Curr_Income36
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
                     l_Curr_Income = COALESCE(l_Curr_Income,0)+COALESCE(l_Curr_Income36,0);
                     -- Jika Tdk Ada Pendapatan tidak perlu Hitung Pajak
                     IF COALESCE(l_Curr_Income,0)+COALESCE(l_Curr_Kolom8,0)<>0 THEN 
                         BEGIN
                     
                           -- Get Jenis Pajak
                           l_JnsPajak := P_Jenis_Pjk (l_TglProses,
						      l_M15NIP) ;
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
                     -- Pesangon (Jika Tgl.Proses Payroll = Tgl.Keluar Pegawai Dan Pesangon > 0
                     IF (SELECT COUNT(NIP) FROM T19PESA WHERE NIP=l_M15NIP AND NilaiPesangon > 0) > 0 AND (l_TglProses=l_M15TglKeluar) THEN 
                        BEGIN
                          SELECT NilaiPesangon,   KdCurr 
                          INTO   l_NilaiPesangon, l_T19KdCurr
                          FROM T19PESA 
                          WHERE NIP=l_M15NIP;
                          --  
                          IF l_FlagKhusus='T' OR 
			     ((SELECT COUNT(FlgDpPt) FROM M46PPKH WHERE FlgDpPt='D' AND KdDpPt='PESA') > 0 AND l_FlagKhusus='Y') THEN 
                             BEGIN
                               -- Ambil Nilai Master Pandapatan dan Potongan
	                       SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
				  fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
			       from  P_M03AllField ('D', 'PESA' ) FX
			       INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
				       l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;                               
                               -- Get Kurs
                               l_M03KdCurr        := CASE WHEN COALESCE(l_T19KdCurr,' ')=' ' THEN l_M03KdCurr ELSE l_T19KdCurr END;
                               l_NilaiPesangon    := fn_Vround(l_NilaiPesangon*fn_GetKurs(l_M03KdCurr,l_TglProses));
                               l_NilaiPesangonVal := fn_Vround(l_NilaiPesangon/fn_GetKurs(l_M03KdCurr,l_TglProses));

                               -- Insert/Update Pesangon
                               IF (SELECT COUNT(NIP) FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='PESA') > 0 THEN 
                                  BEGIN
                                    UPDATE S02DGAJ
                                    SET EncNilaiVal =fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_NilaiPesangonVal) ::VARCHAR(17),l_MyPass),
                                        EncNilai=fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_NilaiPesangon) :: VARCHAR(17), l_MyPass)
                                    WHERE TglPayr=l_TglProses AND FlgDpPt='D' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='PESA' ;
				    --RETURNING S02_ID ; 
			            SELECT currval(pg_get_serial_sequence('s02dgaj', 's02_id'))
			            INTO l_S02_ID;

				    -- Jika Gagal Update
				    IF S02_ID IS NULL THEN 
				       l_Gagal := 1;
				       EXIT LOOP_M15 ; 
				    END IF;  
                                  END;
                               ELSE
                                  BEGIN
                                    INSERT INTO S02DGAJ(TglPayr    ,NIP ,    FlgDpPt,KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                      ,Nilai,KdCurr     ,EncNilaiVal                                                      ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                                                 VALUES(l_TglProses,l_M15NIP,'D'    ,'PESA',' '    ,l_M03Singkatan,fn_kcabang(l_M15NIP,(l_NilaiPesangon :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_M15NIP,(l_NilaiPesangonVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);
				    --RETURNING S02_ID ; 
			            SELECT currval(pg_get_serial_sequence('s02dgaj', 's02_id'))
			            INTO l_S02_ID;

				    -- Jika Gagal Update
				    IF S02_ID IS NULL THEN 
				       l_Gagal := 1;
				       EXIT LOOP_M15 ; 
				    END IF;  
                                  END;
                               END IF;    
                               --*
                               -- Update Table Pesangon
                               UPDATE T19PESA
                               SET FlagProses='P'
                               WHERE NIP=l_M15NIP; 
			       --RETURNING S02_ID ; 
			       SELECT currval(pg_get_serial_sequence('t19pesa', 't19_id'))
			       INTO l_T19_ID;                               

			       -- Jika Gagal Update
			       IF l_T19_ID IS NULL THEN 
			          l_Gagal := 1;
			          EXIT LOOP_M15 ; 
			       END IF;  

                               -- Hitung Pajak Pesangon 
                               IF COALESCE(l_M10JnsPajak ,' ')=' ' THEN 
                                  BEGIN
                                    PERFORM P_Pajak_Pesangon(l_TglProses,
                                                            l_M15NIP, 
                                                            l_NilaiPesangon,
                                                      	    l_MyPass,
	                                                    l_UserID,
	                                              	    l_FlagTHR,
							    l_JnsPajak,
							    l_S01_ID); 
                                  END; 
                               ELSE
                                  BEGIN 
				     PERFORM P_Hitung_Pjk_TypeB(l_JnsPajak,
								l_TglProses,
								l_S05TglAkhir,  
								l_M15NIP, 
								l_MyPass,
								l_UserID,
								l_FlagTHR,
								'Y',
								l_S01_ID) ;                                   
                                  END; 
                                END IF;     
                             END; 
                           END IF; -- FLAG KHUSUS   
                        END; 
                     END IF; -- PESANGON    
-- BY PEGGY 2008 04 02 
                     -- Pembulatan THP CORE : BULANAN / HARIAN, MILIH PEMBULATAN THP Y, TIPE A DAN BUKAN KELUAR 
                     IF l_FlagKhusus = 'T' AND l_FZ2RNDTHPPOT = 'Y' AND COALESCE(l_M10JnsPajak ,' ')=' ' AND
			(l_M15TglKeluar IS NULL OR 
			(l_M15TglKeluar IS NOT NULL AND l_TglProses <> l_M15TglKeluar))  THEN 
                        BEGIN
			   PERFORM P_RND_THP(l_M15NIP, 
			   		    l_TglProses, 
					    l_FZ2THPROUND,
					    l_UserId,
					    l_MyPass, 
					    l_S01_ID);
		        END ; 
		      END IF;                         
-- BY PEGGY 2006 12 18 
                     -- Pembulatan THP 
                     IF l_M15Rkbnk = ' ' and 
			(l_M15TglKeluar is null or (l_M15TglKeluar is not null and l_TglProses <> l_M15TglKeluar))  THEN 
                        BEGIN
			  PERFORM P_OIL_RND_THP(l_M15NIP, 
					       l_TglProses, 
					       l_UserId,
					       l_MyPass );
                        END; 
		     END IF;                        
                     -- Update Kode Cabang Di Table Summary Pindah Cabang
                     l_Bulan := CASE  EXTRACT(MONTH FROM l_TglProses) --(CASE WHEN EXTRACT(MONTH FROMl_TglProses)-1=0 THEN 1 ELSE EXTRACT(MONTH FROMl_TglProses)-1 END) 
                                      WHEN 1 THEN '01'
                                      WHEN 2 THEN '02'
                                      WHEN 3 THEN '03'
                                      WHEN 4 THEN '04'
                                      WHEN 5 THEN '05'
                                      WHEN 6 THEN '06'
                                      WHEN 7 THEN '07'
                                      WHEN 8 THEN '08'
                                      WHEN 9 THEN '09'
                                      WHEN 10 THEN '10'
                                      WHEN 11 THEN '11'
                                      WHEN 12 THEN '12'
                                END; 
                     l_Tahun := EXTRACT(YEAR FROM l_TglProses);
                     -- Frans: untuk menghilangkan yang nilainya 0 0
		     DELETE 
		     FROM S02DGAJ S02
		     WHERE fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)= 0 
			   and NIP=l_M15NIP AND TglPayr=l_TglProses and flgdppt || kddppt = 'DHAID' ;

                     -- Jika S01 Tdk punya detail maka s01 harus dihapus
                     IF (SELECT COUNT(NIP) FROM S02DGAJ WHERE NIP=l_M15NIP AND TglPayr=l_TglProses) = 0  THEN 
                        BEGIN
                           DELETE FROM S01HGAJ
                           WHERE NIP=l_M15NIP AND TglPayr=l_TglProses; 
                        END; 
                     ELSE
                        BEGIN
			   -- Hanya Proses payroll yang hanya update tgl payroll di master pegawai !
			   -- hanya proses payroll yang nulis ke summ pindah cabang -- peggy 2008 02 12 
			   IF l_FlagKhusus<>'Y'	THEN 
			      BEGIN	
				-- Update Data (Update TglProses Payroll di M.Pegawai
				UPDATE M15PEGA
				SET TglPayr=l_TglProses
				WHERE NIP=l_M15NIP;
				---
			   --/
                                -- Update File S0ESFPC
                                IF (SELECT COUNT(NIP) FROM S0ESFPC WHERE Tahun=EXTRACT(YEAR FROM l_TglProses) AND NIP=l_M15NIP) > 0 THEN 
                                   BEGIN
                                      EXECUTE 'UPDATE S0ESFPC SET Cab'||l_Bulan||' = $1
					       WHERE Tahun=$2 AND NIP=$3 ;'  
				      USING l_M15KdCaba, l_Tahun, l_M15NIP ;
                                     END; 
                                ELSE
                                   BEGIN
                                     -- Get Isi Cabang
                                     l_Cab01 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=1  THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab02 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=2  THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab03 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=3  THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab04 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=4  THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab05 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=5  THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab06 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=6  THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab07 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=7  THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab08 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=8  THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab09 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=9  THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab10 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=10 THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab11 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=11 THEN l_M15KdCaba ELSE ' ' END;
                                     l_Cab12 := CASE WHEN EXTRACT(MONTH FROM l_Tglproses)=12 THEN l_M15KdCaba ELSE ' ' END;
        
                                     INSERT INTO S0ESFPC(Tahun,  NIP,     Cab01,  Cab02,  Cab03,  Cab04,  Cab05,  Cab06,  Cab07,  Cab08,  Cab09,  Cab10,  Cab11,  Cab12)
						 VALUES (l_Tahun,l_M15NIP,l_Cab01,l_Cab02,l_Cab03,l_Cab04,l_Cab05,l_Cab06,l_Cab07,l_Cab08,l_Cab09,l_Cab10,l_Cab11,l_Cab12);
				     SELECT currval(pg_get_serial_sequence('s0esfpc', 's0e_id'))
				     INTO l_S0E_ID;				     
                                     -- Jika Gagal Update
                                     IF l_S0E_ID IS NULL THEN 
				        l_Gagal := 1;
		                        EXIT LOOP_M15 ;   
				     END IF; 
        	                   END; 
        	                END IF; --S0E      
			      END; -- FLAG KHUSUS 
			   END IF;    
                           --*
                           -- Update Posting Terakhir di Summaty Posting
                           IF (SELECT COUNT(NIP) FROM S05PSTD WHERE NIP=l_M15NIP AND TglPosting=l_TglProses) > 0 THEN 
                               BEGIN
                                  UPDATE S05PSTD
                                  SET FlagTHR=l_FlagTHR
                                  WHERE NIP=l_M15NIP AND TglPosting=l_TglProses ;
				  --RETURNING S05_ID INTO l_S05_ID; 	
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
				  --RETURNING S05_ID INTO l_S05_ID; 
				  SELECT currval(pg_get_serial_sequence('s05pstd', 's05_id'))
				  INTO l_S05_ID;				     
				  
                                  -- Jika Gagal Update
                                  IF l_S05_ID IS NULL THEN 
				     l_Gagal := 1;
				     EXIT LOOP_M15 ;
                                  END IF;                                               
                               END; 
                           END IF;     
                            --*
                            -- Update TakeHomePay di Header Penggajian
                           l_EncTotInc := 0;
                           l_EncTotDed := 0;
                           l_EncTakeHomePay := 0;
                            --  
                           SELECT SUM(CASE WHEN S02.FlgDpPt='D' THEN fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) ELSE 0 END),
                            	  SUM(CASE WHEN S02.FlgDpPt='P' THEN fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) ELSE 0 END)    
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
                           WHERE  TglPayr=l_TglProses  AND  NIP=l_M15NIP; 
                            --
                        END;
                     END IF ; -- SELECT NIP FROM S02     
                     --*             
                   -- End Of Filter
--                   END IF; --?
                 END ;  
              -- End Of Validasi                 
              END IF ;
--           END ;   
         --
--       END IF ;   -- END Validasi Sec. Cabang
--    END IF;        
 END ;   
 END IF;   
-----------------
END LOOP;
CLOSE l_LOOP_M15;
END;
$$ LANGUAGE plpgsql ;

/*
SELECT l_Gagal, l_TableName1, l_mlValid
FROM  P_PAYRP1('2013-05-01' ::DATE, ' ', 'ZZZZ', ' ', 'ZZZZ', 'PHW-001', 'PHW-001',  
		'L','I','T','01','OK','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1) FX; 

SELECT * FROM S01HGAJ WHERE NIP ='PHW-001'		
*/
