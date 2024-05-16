/******************************************************
Name sprocs	: R_SLIP_B
Description	: cetak Slip Gaji Standard A 
Call From	: SLIPS1
Sub sprocs	: 
********************************************************/
DROP FUNCTION R_SLIP_B (
        l_Periode       DATE, 
        l_UkerFr        VARCHAR(8), 
        l_UkerTo        VARCHAR(8), 
        l_FCab          VARCHAR(4), 
        l_TCab          VARCHAR(4), 
        l_NIPFr         VARCHAR(10), 
        l_NIPTo         VARCHAR(10), 
        l_MyPass        VARCHAR(128),
        l_UserId        INT);
--
CREATE FUNCTION R_SLIP_B (
        l_Periode       DATE, 
        l_UkerFr        VARCHAR(8), 
        l_UkerTo        VARCHAR(8), 
        l_FCab          VARCHAR(4), 
        l_TCab          VARCHAR(4), 
        l_NIPFr         VARCHAR(10), 
        l_NIPTo         VARCHAR(10), 
        l_MyPass        VARCHAR(128),
        l_UserId        INT)
--
RETURNS TABLE ( 
         OUTnourut      INT4, 
         OUTtahun       INT4, 
         OUTbulan       INT4, 
         OUTarea        VARCHAR(4), 
         OUTskarea      VARCHAR(40), 
         OUTcabang      VARCHAR(4), 
         OUTskcabang    VARCHAR(40), 
         OUTunitusaha   VARCHAR(4), 
         OUTskuusa      VARCHAR(40), 
         OUTketuusa     VARCHAR(40), 
         OUTkduker      VARCHAR(4), 
         OUTskuker      VARCHAR(40), 
         OUTglng        VARCHAR(4), 
         OUTskglng      VARCHAR(40), 
         OUTkjab        VARCHAR(40), 
         OUTskkjab      VARCHAR(40), 
         OUTjaba        VARCHAR(4), 
         OUTskjaba      VARCHAR(40), 
         OUTklas        VARCHAR(4), 
         OUTskklas      VARCHAR(40), 
         OUTnip         VARCHAR(10), 
         OUThari        INT4, 
         OUTflagdppt    VARCHAR(1), 
         OUTnomformat   VARCHAR(20), 
         OUTnama        VARCHAR(40), 
         OUTjnsklm      VARCHAR(1), 
         OUTstsmed      VARCHAR(2), 
         OUTkdbank      VARCHAR(4), 
         OUTaccholder   VARCHAR(25), 
         OUTaccnumber   VARCHAR(25), 
         OUTketbank     VARCHAR(40), 
         OUTtpformat1   VARCHAR(20), 
         OUTketerangan  VARCHAR(40), 
         OUTnilai       NUMERIC, 
         OUTtpformat2   VARCHAR(20), 
         OUTkettaxcal   VARCHAR(40), 
         OUTnilaitaxcal NUMERIC, 
         OUTjnspjk      VARCHAR(4), 
         OUTjmlpiutang  NUMERIC, 
         OUTflagthr     INT4, 
         OUTharian      INT4, 
         OUTtglpayr     DATE) 
AS $$
--
DECLARE l_Para              VARCHAR(4000);		
        l_LineTXT           VARCHAR(4000);
        l_S02TglPayr		DATE;			
        l_S01kdArea         VARCHAR(4);
        l_S01kdCaba         VARCHAR(4);		
        l_S01kdUUsa         VARCHAR(4);
        l_S01kdUker         VARCHAR(8);		
        l_S01kdGlng         VARCHAR(4);
        l_S01kdKJab         VARCHAR(4);		
        l_S01kdJaba         VARCHAR(4);
        l_S01kdKlas         VARCHAR(4);		
        l_S01NIP            VARCHAR(10);
        l_S01Nama           VARCHAR(25);		
        l_S01JnsKlmn		VARCHAR(1);
        l_S01StsPjk         VARCHAR(2);		
        l_S02FlgDpPt		VARCHAR(1);
        l_M19NomFormat		DECIMAL(3,0);		
        l_M01Singkatan		VARCHAR(20);
        l_M08SkCaba         VARCHAR(40);		
        l_M02Singkatan		VARCHAR(20);
        l_M17Singkatan		VARCHAR(20);		
        l_M12Singkatan		VARCHAR(20);
        l_M06Singkatan		VARCHAR(20);		
        l_M04Singkatan		VARCHAR(20);
        l_M10Singkatan		VARCHAR(20);		
        l_M19Keterangan		VARCHAR(20);
        l_M02Keterangan		VARCHAR(20);		
        l_S02Nilai          DECIMAL(15,2);
        l_S01BankRef		VARCHAR(4);		
        l_S01AccHolder		VARCHAR(25);
        l_S01RkBnk          VARCHAR(20);		
        l_M14Keterangan		VARCHAR(20);
        l_S01JnsPajak		VARCHAR(1);		
        l_S01FlagTHR		VARCHAR(1);
        l_GetPiut           Decimal(15,2);		
        l_TEMPTahun         INT;
        l_TEMPBulan         INT;	        	
        l_TEMPArea          VARCHAR(4);
        l_TEMPSkArea		VARCHAR(20);		
        l_TEMPCabang		VARCHAR(4);
        l_TEMPSkCabang		VARCHAR(40);		
        l_TEMPUnitUsaha		VARCHAR(4);
        l_TEMPSkUUsa		VARCHAR(20);		
        l_TEMPKetUUsa		VARCHAR(20);
        l_TEMPKdUker		VARCHAR(8);		
        l_TEMPSkUker		VARCHAR(20); 
        l_TEMPGlng          VARCHAR(4);		
        l_TEMPSkGlng		VARCHAR(20); 
        l_TEMPKJab          VARCHAR(4);		
        l_TEMPSkKjab		VARCHAR(20); 
        l_TEMPJaba          VARCHAR(4);        	
        l_TEMPSkJaba		VARCHAR(20); 
        l_TEMPKlas          VARCHAR(4); 		
        l_TEMPSkKlas		VARCHAR(20); 
        l_TEMPNip           VARCHAR(10);       	
        l_TEMPFlagDppt		VARCHAR(1);
        l_TEMPNomFormat		VARCHAR(2);		
        l_TEMPNama          VARCHAR(25); 
        l_TEMPJnsKlm		VARCHAR(1);        	
        l_TEMPStsMed		VARCHAR(2);
        l_TEMPKdBank		VARCHAR(4);		
        l_TEMPAccHolder		VARCHAR(25);
        l_TEMPAccNumber		VARCHAR(20);		
        l_TEMPKetBank		VARCHAR(40);
        l_TEMPKeterangan	VARCHAR(40);		
        l_TEMPNilai         DECIMAL(15,2);
        l_TEMPJnsPjk		VARCHAR(1);		
        l_TEMPJmlPiutang	DECIMAL(15,2);
        l_TEMPFlagThr		INT;  	        	
        l_LOOP_TEMP         REFCURSOR;
        l_Nomor             INT;	        	
        l_TpFormat2         VARCHAR(1);
        l_KetTaxCal         VARCHAR(40);		
        l_NilTaxCal         DECIMAL(15,2);
        l_S01GrossIncomeNBYTD 	DECIMAL(15,2);		
        l_S01OccSupport1	DECIMAL(15,2);
        l_S01Col12YTD		DECIMAL(15,2);		
        l_S01EGIYNB         DECIMAL(15,2);
        l_S01PTKP           DECIMAL(15,2);		
        l_S01EYTI           DECIMAL(15,2);
        l_S01EYIT           DECIMAL(15,2);		
        l_S01YTDIT          DECIMAL(15,2);
        l_S01IncTaxAPaidNB 	DECIMAL(15,2);		
        l_S01IncTaxAPaidB	DECIMAL(15,2);
        l_S01GrossIncomeBYTD	DECIMAL(15,2);		
        l_S01OccSupport2	DECIMAL(15,2);
        l_S01TaxTotal		DECIMAL(15,2);		
        l_NIP               VARCHAR(10);
        l_TotPend           DECIMAL(15,2);		
        l_TotPot            DECIMAL(15,2);
        l_FlgDpPt           VARCHAR(1);		
        l_S01Hari           DECIMAL(4,1);
        l_M10Harian         INT;            	
        l_S02KdDppt      	VARCHAR(4);
        l_LOOP_W11       	REFCURSOR;
--
BEGIN 
CREATE TEMP TABLE l_TEMP (Tahun		INT,
			Bulan		INT,
			Area		VARCHAR(4),
			SkArea		VARCHAR(20),
			Cabang		VARCHAR(4),
			SkCabang	VARCHAR(40),
			UnitUsaha	VARCHAR(4),
			SkUUsa		VARCHAR(20),
			KetUUsa		VARCHAR(20),
			KdUker		VARCHAR(8),
			SkUker		VARCHAR(20), 
			Glng		VARCHAR(4), 
			SkGlng		VARCHAR(20), 
			KJab		VARCHAR(4), 
			SkKjab		VARCHAR(20),  
			Jaba		VARCHAR(4), 
			SkJaba		VARCHAR(20), 
			Klas		VARCHAR(4), 
			SkKlas		VARCHAR(20), 
			Nip		VARCHAR(10), 
			FlagDppt	VARCHAR(1),
			NomFormat	VARCHAR(2),
			Nama		VARCHAR(25), 
			JnsKlm		VARCHAR(1),
			StsMed		VARCHAR(2),
			KdBank		VARCHAR(4),
			AccHolder	VARCHAR(25),
			AccNumber	VARCHAR(20),
			KetBank		VARCHAR(40),
			Keterangan	VARCHAR(40),
			Nilai		DECIMAL(15,2),
			JnsPjk		VARCHAR(1),
			JmlPiutang	DECIMAL(15,2),
			FlagThr		INT)  ON COMMIT DROP ;  
--
CREATE TEMP TABLE l_TEMP1(NoUrut	INT,
			Tahun		INT,
			Bulan		INT,
			Area		VARCHAR(4),
			SkArea		VARCHAR(20),
			Cabang		VARCHAR(4),
			SkCabang	VARCHAR(40),
			UnitUsaha	VARCHAR(4),
			SkUUsa		VARCHAR(20),
			KetUUsa		VARCHAR(20),
			KdUker		VARCHAR(8),
			SkUker		VARCHAR(20), 
			Glng		VARCHAR(4), 
			SkGlng		VARCHAR(20), 
			KJab		VARCHAR(4), 
			SkKjab		VARCHAR(20), 
			Jaba		VARCHAR(4),  
			SkJaba		VARCHAR(20), 
			Klas		VARCHAR(4), 
			SkKlas		VARCHAR(20), 
			Nip         VARCHAR(10), 
			Hari		INT,
			FlagDppt	VARCHAR(1),
			NomFormat	VARCHAR(2),
			Nama		VARCHAR(25), 
			JnsKlm		VARCHAR(1),
			StsMed		VARCHAR(2),
			KdBank		VARCHAR(4),
			AccHolder	VARCHAR(25),
			AccNumber	VARCHAR(20),
			KetBank		VARCHAR(40),
			TpFormat1	VARCHAR(1),	
			Keterangan	VARCHAR(40),
			Nilai		DECIMAL(15,2),
			TpFormat2	VARCHAR(1),	
			KetTaxCal	VARCHAR(40),
			NilaiTaxCal	DECIMAL(15,2),
			JnsPjk		VARCHAR(1),
			JmlPiutang	DECIMAL(15,2),
			FlagThr		INT,
			Harian		INT,
			TglPayr		DATE)  ON COMMIT DROP ;  
--*
-- Mulai Cursor data
OPEN l_LOOP_W11 FOR 
SELECT  S02.TglPayr,S01.kdArea,S01.kdCaba,S01.kdUUsa,S01.kdUker,S01.kdGlng,S01.kdKJab,
		S01.kdJaba,S01.kdKlas,S01.NIP,S01.Nama,S01.JnsKlmn,S01.StsPjk,S02.FlgDpPt,
		M19.NomFormat,M01.Keterangan,M08.NmCaba,M02.Keterangan,M17.Keterangan,M12.Keterangan,
		M06.Keterangan,M04.Keterangan,M10.Keterangan,M19.Keterangan,M02.Keterangan,
		fn_kpusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) AS EncNilai,
		S01.BankRef,S01.AccHolder,
		S01.RkBnk,M14.Keterangan,S01.JnsPajak,S01.FlagTHR,
		COALESCE(getpiut(S01.NIP,l_Periode),0),
		fn_kpusat(S01.NIP,S01.EncIncTaxAPaidNB,l_MyPass) ::DECIMAL(15,2) AS EncIncTaxAPaidNB,
		fn_kpusat(S01.NIP,S01.EncIncTaxAPaidB,l_MyPass) ::DECIMAL(15,2) AS EncIncTaxAPaidB,
		fn_kpusat(S01.NIP,S01.EncGrossIncomeBYTD,l_MyPass) ::DECIMAL(15,2) AS EncGrossIncomeBYTD,
		fn_kpusat(S01.NIP,S01.EncOccSupport2,l_MyPass) ::DECIMAL(15,2) AS EncOccSupport2,
		fn_kpusat(S01.NIP,S01.EncPTKP,l_MyPass) ::DECIMAL(15,2) AS EncPTKP,
		fn_kpusat(S01.NIP,S01.EncTaxTotal,l_MyPass) ::DECIMAL(15,2) AS EncTaxTotal,
		fn_kpusat(S01.NIP,S01.EncGrossIncomeNBYTD,l_MyPass) ::DECIMAL(15,2) AS EncGrossIncomeNBYTD,
		fn_kpusat(S01.NIP,S01.EncOccSupport1,l_MyPass) ::DECIMAL(15,2) AS EncOccSupport1,
		fn_kpusat(S01.NIP,S01.EncCol12YTD,l_MyPass) ::DECIMAL(15,2) AS EncCol12YTD,
		fn_kpusat(S01.NIP,S01.EncEGIYNB,l_MyPass) ::DECIMAL(15,2) AS EncEGIYNB,
		fn_kpusat(S01.NIP,S01.EncPTKP,l_MyPass) ::DECIMAL(15,2) AS EncPTKP,
		fn_kpusat(S01.NIP,S01.EncEYTI,l_MyPass) ::DECIMAL(15,2) AS EncEYTI,
		fn_kpusat(S01.NIP,S01.EncEYIT,l_MyPass) ::DECIMAL(15,2) AS EncEYIT,
		fn_kpusat(S01.NIP,S01.EncYTDIT,l_MyPass) ::DECIMAL(15,2) AS EncYTDIT,
		S01.Hari,M10.Harian
FROM M19HSLG M19 
INNER JOIN M20DSLG M20 ON M19.TipeLap=M20.TipeLap AND M19.FlgDpPt=M20.FlgDpPt AND M19.NomFormat=M20.NomFormat
INNER JOIN S02DGAJ S02 ON S02.FlgDpPt=M19.FlgDpPt AND S02.KdDpPt =M20.KdDpPt AND S02.FlgAngs=M20.FlgAngs
INNER JOIN S01HGAJ S01 ON S02.TglPayr=S01.TglPayr AND S02.NIP    =S01.NIP
INNER JOIN M01AREA M01 ON M01.Kode=S01.kdArea
INNER JOIN M08HCAB M08 ON M08.KdCaba=S01.kdCaba
INNER JOIN M02UUSA M02 ON M02.Kode=S01.kdUUsa
INNER JOIN M17UKER M17 ON M17.KdUker=S01.kdUker
INNER JOIN M12HGOL M12 ON M12.Kode =S01.kdGlng
INNER JOIN M06HKJB M06 ON M06.Kode=S01.kdKJab
INNER JOIN M04HJAB M04 ON M04.Kode =S01.kdJaba
INNER JOIN M10KLAS M10 ON M10.Kode=S01.kdKlas
INNER JOIN M14BANK M14 ON M14.Kode=S01.BankRef 
INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
            FROM fn_SECLOGIN(l_UserId)) VSL ON S01.KdGlng=VSL.Gol_Kode AND S01.KdCaba=VSL.Cab_Kode
LEFT JOIN S0BLSTX S0B ON S0B.TglPayr=S01.TglPayr AND S0B.NIP=S01.NIP
WHERE M19.TipeLap='1' AND S01.TglPayr=l_Periode AND
      S01.NIP BETWEEN l_NIPFr AND l_NIPTo AND
      S01.kdUker BETWEEN l_UkerFr AND l_UkerTo AND
      S01.kdCaba BETWEEN l_FCab AND l_TCab ;
--ORDER BY S02.TglPayr, S01.NIP, S02.FlgDpPt, M19.NomFormat; 
--
<<l_LOOP_W11>> 
LOOP 
      FETCH l_LOOP_W11 
      INTO  l_S02TglPayr,l_S01kdArea,l_S01kdCaba,l_S01kdUUsa,l_S01kdUker,l_S01kdGlng,l_S01kdKJab,
            l_S01kdJaba,l_S01kdKlas,l_S01NIP,l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S02FlgDpPt,
            l_M19NomFormat,l_M01Singkatan,l_M08SkCaba,l_M02Singkatan,l_M17Singkatan,l_M12Singkatan,
            l_M06Singkatan,l_M04Singkatan,l_M10Singkatan,l_M19Keterangan,l_M02Keterangan,
            l_S02Nilai,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,l_S01JnsPajak,l_S01FlagTHR,
            l_GetPiut,
            l_S01IncTaxAPaidNB,l_S01IncTaxAPaidB,l_S01GrossIncomeBYTD,l_S01OccSupport2,l_S01PTKP,l_S01TaxTotal,l_S01GrossIncomeNBYTD,
            l_S01OccSupport1,l_S01Col12YTD,l_S01EGIYNB,l_S01PTKP,l_S01EYTI,l_S01EYIT,l_S01YTDIT,
            l_S01Hari,l_M10Harian; 
      ---      
      IF NOT FOUND THEN
         EXIT ;
      END IF;
     --      
      IF (SELECT COUNT(Area)  
          FROM l_Temp 
          WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr) AND Bulan=EXTRACT(MONTH FROM l_S02TglPayr) AND
                Area =l_S01kdArea AND Cabang=l_S01kdCaba AND UnitUsaha=l_S01kdUUsa AND
                KdUker=l_S01kdUker AND Glng =l_S01kdGlng AND KJab =l_S01kdKJab AND
                Jaba =l_S01kdJaba AND Klas =l_S01kdKlas AND Nip  =l_S01NIP    AND  
                FlagDppt=l_S02FlgDpPt AND NomFormat=l_M19NomFormat::VARCHAR(2) ) > 0 THEN 
          --
          UPDATE l_Temp
          SET Nilai=COALESCE(Nilai,0)+l_S02Nilai
  	      WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr) AND Bulan=EXTRACT(MONTH FROM l_S02TglPayr) AND
                Area =l_S01kdArea AND Cabang=l_S01kdCaba AND UnitUsaha=l_S01kdUUsa AND
                KdUker=l_S01kdUker AND Glng =l_S01kdGlng AND KJab =l_S01kdKJab AND
                Jaba =l_S01kdJaba AND  Klas =l_S01kdKlas AND Nip  =l_S01NIP    AND  
                FlagDppt=l_S02FlgDpPt AND  NomFormat=l_M19NomFormat ::VARCHAR(2); 
      ELSE
          INSERT INTO l_TEMP (Tahun, Bulan, Area, SkArea, Cabang,
                  SkCabang, UnitUsaha, SkUUsa, KdUker, SkUker, Glng, SkGlng, KJab,
                  SkKjab, Jaba, SkJaba, Klas, SkKlas, Nip, FlagDppt, NomFormat,
                  Nama, JnsKlm, StsMed, KdBank, AccHolder, AccNumber, KetBank, Keterangan,
                  Nilai, JnsPjk, JmlPiutang, FlagTHR, ketUUsa)
          VALUES (EXTRACT(YEAR FROM l_S02TglPayr),EXTRACT(MONTH FROM l_S02TglPayr),l_S01kdArea,l_M01Singkatan,l_S01kdCaba,
                  l_M08SkCaba,l_S01kdUUsa,l_M02Singkatan,l_S01kdUker,l_M17Singkatan,l_S01kdGlng,l_M12Singkatan,l_S01kdKJab,
                  l_M06Singkatan,l_S01kdJaba,l_M04Singkatan,l_S01kdKlas,l_M10Singkatan,l_S01NIP,l_S02FlgDpPt,l_M19NomFormat,
                  l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,l_M19Keterangan,
                  l_S02Nilai,l_S01JnsPajak, l_GetPiut, CASE l_S01FlagThr WHEN '*' THEN 1 ELSE 0 END,l_M02Keterangan);
      END IF; 
     --*
     -- Isi Data TEMP1 
     IF (SELECT COUNT(Area) 
         FROM l_Temp1 
         WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr) AND 
              Bulan=EXTRACT(MONTH FROM l_S02TglPayr) AND
              Area =l_S01kdArea AND Cabang=l_S01kdCaba AND 
              UnitUsaha=l_S01kdUUsa AND KdUker=l_S01kdUker AND 
              Glng =l_S01kdGlng AND KJab =l_S01kdKJab AND
              Jaba =l_S01kdJaba AND Klas =l_S01kdKlas AND Nip  =l_S01NIP ) = 0 THEN 
          --
          l_Nomor := 1; 
          --
          WHILE l_Nomor<=18
          LOOP 
                l_NiltaxCal := 0;
                -- 
                IF l_Nomor=1 THEN 
                     l_TpFormat2 := 'D';
                     l_KetTaxCal := 'Penghasilan Bruto';
                     l_NiltaxCal := l_S01GrossIncomeNBYTD;
                ELSIF l_Nomor=2 THEN 
    	             l_TpFormat2 := 'D';
                     l_KetTaxCal := 'Penghasilan Kena Pajak';
                     l_NiltaxCal := l_S01EYTI;
                ELSIF l_Nomor=3 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=4 THEN 
    	             l_TpFormat2 := 'D';
                     l_KetTaxCal := 'Pajak Penghasilan';
                     l_NiltaxCal := l_S01TaxTotal;
                ELSIF l_Nomor=5 THEN 
    	             l_TpFormat2 := 'D';
                     l_KetTaxCal := 'Pajak Yang Telah di Potong';
                     l_NiltaxCal := l_S01GrossIncomeBYTD;
                ELSIF l_Nomor=6 THEN 
    	             l_TpFormat2 := 'G';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=7 THEN 
    	             l_TpFormat2 := 'D';
                     l_KetTaxCal := 'Pajak Yang Harus di Potong';
        --                             l_NiltaxCal =l_S01EYIT   /* sebelum edit*/
                     l_NiltaxCal := l_S01EYIT;
                ELSIF l_Nomor=8 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=9 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=10 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=11 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=12 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=13 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=14 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=15 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=16 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=17 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=18 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                END IF; 
                --* 
                INSERT INTO l_TEMP1(NoUrut,  Tahun,            Bulan,                            Area,   SkArea,
                        Cabang     ,SkCabang   ,UnitUsaha  ,SkUUsa        ,KdUker     ,SkUker        ,Glng       ,SkGlng,
                        KJab       ,SkKjab        ,Jaba       ,SkJaba        ,Klas       ,SkKlas        ,Nip     ,FlagDppt,
                        NomFormat     ,Nama     ,JnsKlm      ,StsMed     ,KdBank      ,AccHolder     ,AccNumber, KetBank,
                        Keterangan  ,Nilai,JnsPjk       ,JmlPiutang, FlagTHR                                     ,ketUUsa,
                        TpFormat2  ,KetTaxCal   ,NilaiTaxCal ,Tpformat1,Hari     ,Harian     ,TglPayr)
	       	    VALUES( l_Nomor,EXTRACT(YEAR FROM l_S02TglPayr),EXTRACT(MONTH FROM l_S02TglPayr),l_S01kdArea,l_M01Singkatan,
                        l_S01kdCaba,l_M08SkCaba,l_S01kdUUsa,l_M02Singkatan,l_S01kdUker,l_M17Singkatan,l_S01kdGlng,l_M12Singkatan,
                        l_S01kdKJab,l_M06Singkatan,l_S01kdJaba,l_M04Singkatan,l_S01kdKlas,l_M10Singkatan,l_S01NIP,l_S02FlgDpPt,
                        l_M19NomFormat,l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,
                        '  ' ,0 , l_S01JnsPajak, l_GetPiut ,CASE l_S01FlagThr WHEN '*' THEN 1 ELSE 0 END, l_M02Keterangan,
                        l_TpFormat2,l_KetTaxCal ,l_NiltaxCal ,'T'      ,l_S01Hari,l_M10Harian,l_S02TglPayr);
                --
                l_Nomor := l_Nomor+1;
                --                
          END LOOP; 
          --* 
     END IF ; 
     --*    
--*
END LOOP;
CLOSE l_LOOP_W11;

--
-- Isi Data Tax Calculation ke TEMP1
-- Proses Isi Ulang ke Temp1 dari Temp
l_Nomor   := 0;
l_NIP     := ' ';
l_FlgDpPt := ' ';

OPEN l_LOOP_TEMP FOR
SELECT  Tahun, Bulan, Area, SkArea, Cabang, SkCabang, UnitUsaha, SkUUsa, KetUUsa, KdUker, SkUker, Glng, SkGlng, 
        KJab, SkKjab, Jaba, SkJaba, Klas, SkKlas, Nip, FlagDppt, NomFormat, Nama, JnsKlm, StsMed, KdBank, 
        AccHolder, AccNumber, KetBank, Keterangan, Nilai,JnsPjk,JmlPiutang,FlagThr
FROM l_TEMP
ORDER BY Tahun, Bulan, Area, SkArea, Cabang, SkCabang, UnitUsaha, SkUUsa, KetUUsa, KdUker, SkUker, Glng, SkGlng, 
        KJab, SkKjab, Jaba, SkJaba, Klas, SkKlas, Nip, FlagDppt, NomFormat;
--
<<l_LOOP_TEMP>> 
LOOP 
FETCH l_LOOP_TEMP 
INTO l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,l_TEMPUnitUsaha,l_TEMPSkUUsa,l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,
     l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,l_TEMPKlas,l_TEMPSkKlas,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,
     l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPKeterangan,l_TEMPNilai,l_TEMPJnsPjk,l_TEMPJmlPiutang,l_TEMPFlagThr;
     --
     IF NOT FOUND THEN
	   EXIT ;
     END IF;
     --
     -- Jika NIP beda maka perhitungan baris dari 1 lagi
     IF l_NIP<>l_TEMPNip THEN 
          --
          IF l_NIP<>' ' AND l_TEMPFlagDppt='D' AND l_TEMPFlagDppt<>l_FlgDpPt THEN 
               UPDATE l_TEMP1
               SET TpFormat1 ='G',
                   Keterangan='_________________',
                   Nilai     =0	
                WHERE NIP=l_NIP  AND NoUrut=l_Nomor;     
               --
               l_Nomor := l_Nomor+1; 
               --
               UPDATE l_TEMP1
               SET TpFormat1 ='J',
                   Keterangan=' ',
                   Nilai     =l_TotPot
               WHERE NIP=l_NIP  AND NoUrut=l_Nomor;
               --
               l_Nomor := l_Nomor+1;         
               --
               UPDATE l_TEMP1
               SET TpFormat1 ='G',
                   Keterangan='_________________',
                   Nilai     =0	
               WHERE NIP=l_NIP  AND NoUrut=l_Nomor;
               --
    	       l_Nomor := l_Nomor+1;
               --
               UPDATE l_TEMP1
               SET TpFormat1 ='J',
                   Keterangan='Total Slip Gaji ',
                   Nilai     =l_TotPEnd+l_TotPot
               WHERE NIP=l_Nip  AND NoUrut=l_Nomor;     
          END IF; 
          --* 
          l_Nomor   := 1;
          l_TotPend := 0;
          l_TotPot  := 0;
          l_NIP     := l_TEMPNip;	      
     END IF; 
     --*
     --       
     IF l_TEMPFlagDppt='D' THEN 
          l_TotPend := COALESCE(l_TotPend,0)+l_TEMPNilai;
     ELSE
          l_TotPot  := COALESCE(l_TotPot,0)+l_TEMPNilai;
     END IF; 
     --* 
     --
     IF l_TEMPFlagDppt='D' AND l_TEMPFlagDppt<>l_FlgDpPt THEN 
          UPDATE l_TEMP1
          SET TpFormat1 ='T',
              Keterangan='PENDAPATAN',
              Nilai     =0	
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;
          --
          l_Nomor := l_Nomor+1;
          
          UPDATE l_TEMP1
          SET TpFormat1 ='D',
              Keterangan=l_TEMPKeterangan,
              Nilai     =l_TEMPNilai		  
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;
          --
     ELSIF l_TEMPFlagDppt='P' AND l_TEMPFlagDppt<>l_FlgDpPt THEN 
          UPDATE l_TEMP1
          SET TpFormat1 ='G',
              Keterangan='_________________',
              Nilai     =0	
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
          --
          l_Nomor := l_Nomor+1;
          --
          UPDATE l_TEMP1
          SET TpFormat1 ='J',
              Keterangan=' ',
              Nilai     =l_TotPend
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
          --
          l_Nomor := l_Nomor+1;         
          --      
          UPDATE l_TEMP1
          SET TpFormat1='T',
              Keterangan='POTONGAN',
              Nilai     =0	
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
          --
          l_Nomor := l_Nomor+1;
          --
          UPDATE l_TEMP1
          SET TpFormat1 ='D',
              Keterangan=l_TEMPKeterangan,
    --		      Nilai     =l_TEMPNilai  /* sebelum edit*/
              Nilai     =l_TEMPNilai
              WHERE NIP=l_NIP  AND NoUrut=l_Nomor;            
     ELSE
          UPDATE l_TEMP1
          SET TpFormat1 ='D',
              Keterangan=l_TEMPKeterangan,
              Nilai     =l_TEMPNilai
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
         --* 
     END IF; 
     --
     l_Nomor := l_Nomor+1;
     -- 
     IF l_FlgDpPt<>l_TEMPFlagDppt THEN 
           l_FlgDpPt := l_TEMPFlagDppt;
     END IF; 
     --*
END LOOP;
CLOSE l_LOOP_TEMP;
--
--
  UPDATE l_TEMP1
  SET TpFormat1 ='G',
      Keterangan='_________________',
      Nilai     =0	
  WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
  --
  l_Nomor := l_Nomor+1;
  --
  UPDATE l_TEMP1
  SET TpFormat1 ='J',
      Keterangan=' ',
--      Nilai     =l_TotPot  /* sebelum edit*/
      Nilai     =l_TotPot
  WHERE NIP=l_NIP  AND NoUrut=l_Nomor;
  --
  l_Nomor := l_Nomor+1;
  
  UPDATE l_TEMP1
  SET TpFormat1 ='G',
      Keterangan='_________________',
      Nilai     =0
  WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
  --
  l_Nomor := l_Nomor+1;         
  --
  UPDATE l_TEMP1
  SET TpFormat1 ='J',
      Keterangan='Total Slip Gaji ',
--      Nilai     =l_TotPEnd-l_TotPot /* sebelum edit*/
      Nilai     =l_TotPEnd-l_TotPot
  WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
  --
  RETURN QUERY 
  SELECT * FROM l_TEMP1
  WHERE JnsPjk<>' ';
  --
END;
$$ LANGUAGE plpgsql ;
GO


/*
select * from R_SLIP_B ('2013-12-04', ' ','ZZZ', ' ', 'ZZZ', 'U','U',
                        'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',
                        1) 
order by outnourut;
*/