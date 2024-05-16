/******************************************************
Name sprocs	: R_SLIP_A
Description	: cetak Slip Gaji Standard A 
Call From	: SLIPS1
Sub sprocs	: 
********************************************************/
DROP FUNCTION R_SLIP_A (l_periode date, 
                    l_ukerfr        varchar(8), 
                    l_ukerto        varchar(8), 
                    l_fcab          varchar(4), 
                    l_tcab          varchar(4), 
                    l_nipfr         varchar(10), 
                    l_nipto         varchar(10), 
                    l_mypass        varchar(128),
                    l_UserId        INT);
--
CREATE FUNCTION R_SLIP_A (l_periode date, 
                    l_ukerfr        varchar(8), 
                    l_ukerto        varchar(8), 
                    l_fcab          varchar(4), 
                    l_tcab          varchar(4), 
                    l_nipfr         varchar(10), 
                    l_nipto         varchar(10), 
                    l_mypass        varchar(128),
                    l_UserId        INT)
--
RETURNS TABLE ( 
         outnourut        int4, 
         outtahun         int4, 
         outbulan         int4, 
         outarea          varchar(4), 
         outskarea        varchar(40), 
         outcabang        varchar(4), 
         outskcabang      varchar(40), 
         outunitusaha     varchar(4), 
         outskuusa        varchar(40), 
         outketuusa       varchar(40), 
         outkduker        varchar(4), 
         outskuker        varchar(40), 
         outglng          varchar(4), 
         outskglng        varchar(40), 
         outkjab          varchar(4), 
         outskkjab        varchar(40), 
         outjaba          varchar(4), 
         outskjaba        varchar(40), 
         outklas          varchar(4), 
         outskklas        varchar(40), 
         outnip           varchar(10), 
         outhari          numeric, 
         outflagdppt      varchar(1), 
         outnomformat     numeric, 
         outnama          varchar(40), 
         outjnsklm        varchar(10), 
         outstsmed        varchar(1), 
         outkdbank        varchar(10), 
         outaccholder     varchar(10), 
         outaccnumber     varchar(10), 
         outketbank       varchar(40), 
         outtpformat1     varchar(40), 
         outketerangan    varchar(100), 
         outnilai         numeric, 
         outtpformat2     varchar(40), 
         outkettaxcal     varchar(40), 
         outnilaitaxcal   numeric, 
         outjnspjk        varchar(4), 
         outjmlpiutang    numeric, 
         outflagthr       int4, 
         outharian        int4, 
         outjmllembur     numeric, 
         outflgopbl       varchar(1),
         outketfooter     varchar(200))
AS $$
--
DECLARE l_Para		VARCHAR(4000);	
    l_LineTXT		VARCHAR(4000);
	l_S02TglPayr	DATE;		
    l_S01kdArea		VARCHAR(4);
	l_S01kdCaba		VARCHAR(4);	
    l_S01kdUUsa		VARCHAR(4);
	l_S01kdUker		VARCHAR(8);	
    l_S01kdGlng		VARCHAR(4);
	l_S01kdKJab		VARCHAR(4);	
    l_S01kdJaba		VARCHAR(4);
	l_S01kdKlas		VARCHAR(4);	
    l_S01NIP		VARCHAR(10);
	l_S01Nama		VARCHAR(25);	
    l_S01JnsKlmn	VARCHAR(1);
	l_S01StsPjk		VARCHAR(2);	
    l_S02FlgDpPt	VARCHAR(1);
	l_M19NomFormat	DECIMAL(3,0);	
    l_M01Singkatan	VARCHAR(20);
	l_M08SkCaba		VARCHAR(20);	
    l_M02Singkatan	VARCHAR(20);
	l_M17Singkatan	VARCHAR(20);	
    l_M12Singkatan	VARCHAR(20);
	l_M06Singkatan	VARCHAR(20);	
    l_M04Singkatan	VARCHAR(20);
	l_M10Singkatan	VARCHAR(20);	
    l_M19Keterangan	VARCHAR(20);
	l_M02Keterangan	VARCHAR(20);	
    l_S02Nilai		DECIMAL(15,2);
	l_S01BankRef	VARCHAR(4);	
    l_S01AccHolder	VARCHAR(25);
	l_S01RkBnk		VARCHAR(20);	
    l_M14Keterangan	VARCHAR(40);
	l_S01JnsPajak	VARCHAR(1);	
    l_S01FlagTHR	VARCHAR(1);
	l_GetPiut		Decimal(15,2);	
    l_TEMPTahun		INT;
	l_TEMPBulan		INT;		
    l_TEMPArea		VARCHAR(4);
	l_TEMPSkArea	VARCHAR(20);	
    l_TEMPCabang	VARCHAR(4);
	l_TEMPSkCabang	VARCHAR(20);	
    l_TEMPUnitUsaha	VARCHAR(4);
	l_TEMPSkUUsa	VARCHAR(20);	
    l_TEMPKetUUsa	VARCHAR(20);
	l_TEMPKdUker	VARCHAR(8);	
    l_TEMPSkUker	VARCHAR(20); 
	l_TEMPGlng		VARCHAR(4);	
    l_TEMPSkGlng	VARCHAR(20); 
	l_TEMPKJab		VARCHAR(4);	
    l_TEMPSkKjab	VARCHAR(20); 
	l_TEMPJaba		VARCHAR(4);	
    l_TEMPSkJaba	VARCHAR(20); 
	l_TEMPKlas		VARCHAR(4);	
    l_TEMPSkKlas	VARCHAR(20); 
	l_TEMPNip		VARCHAR(10);    
    l_TEMPFlagDppt	VARCHAR(1);
    l_TEMPNomFormat	DECIMAL(3,0);	
    l_TEMPNama		VARCHAR(25); 
	l_TEMPJnsKlm	VARCHAR(1);     
    l_TEMPStsMed	VARCHAR(2);
    l_TEMPKdBank	VARCHAR(4);	
    l_TEMPAccHolder	VARCHAR(25);
	l_TEMPAccNumber	VARCHAR(20);	
    l_TEMPKetBank	VARCHAR(40);
	l_TEMPKeterangan 	VARCHAR(20);	
    l_TEMPNilai		DECIMAL(15,2);
	l_TEMPJnsPjk	VARCHAR(1);	
    l_TEMPJmlPiutang 	DECIMAL(15,2);
	l_TEMPFlagThr	INT;    	
    l_LOOP_TEMP		REFCURSOR;
	l_Nomor			INT;    	
    l_TpFormat2		VARCHAR(1);
	l_KetTaxCal		VARCHAR(45);	
    l_NilTaxCal		DECIMAL(15,2);
	l_S01GrossIncomeNBYTD	DECIMAL(15,2);	
    l_S01OccSupport1    DECIMAL(15,2);
	l_S01Col12YTD	DECIMAL(15,2);	
    l_S01EGIYNB		DECIMAL(15,2);
	l_S01PTKP		DECIMAL(15,2);	
    l_S01EYTI		DECIMAL(15,2);
	l_S01EYIT		DECIMAL(15,2);	
    l_S01YTDIT		DECIMAL(15,2);
	l_S01IncTaxAPaidNB	DECIMAL(15,2);	
    l_S01IncTaxAPaidB	DECIMAL(15,2);
	l_S01GrossIncomeBYTD	DECIMAL(15,2);	
    l_S01OccSupport2	DECIMAL(15,2);
	l_S01TaxTotal	DECIMAL(15,2);	
    l_NIP			VARCHAR(10);
	l_TotPend		DECIMAL(15,2);	
    l_TotPot		DECIMAL(15,2);
	l_S02KdDpPt		VARCHAR(4);	
    l_S01Hari		DECIMAL(4,1);
	l_M10Harian		INT;            
    l_Nilai         DECIMAL(15,2);
    l_S01TaxPesangonRp      DECIMAL(15,2);  
    l_FlgDpPt       VARCHAR(1);
	l_S03TaxUMP		DECIMAL(15,2);  
    l_S03TaxAPaidUMP  	DECIMAL(15,2);
	l_JmlLembur 	DECIMAL(5,1);	
    l_LOOP_W11		REFCURSOR;
    --
BEGIN 
CREATE TEMP TABLE l_TEMP (Tahun		INT,
			Bulan		INT,
			Area		VARCHAR(4),
			SkArea		VARCHAR(20),
			Cabang		VARCHAR(4),
			SkCabang	VARCHAR(20),
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
			NomFormat	DECIMAL(3,0),
			Nama		VARCHAR(25), 
			JnsKlm		VARCHAR(1),
			StsMed		VARCHAR(2),
			KdBank		VARCHAR(4),
			AccHolder	VARCHAR(25),
			AccNumber	VARCHAR(20),
			KetBank		VARCHAR(40),
			Keterangan	VARCHAR(20),
			Nilai		DECIMAL(15,2),
			JnsPjk		VARCHAR(1),
			JmlPiutang	DECIMAL(15,2),
			FlagThr		INT) ON COMMIT DROP ;
--
CREATE TEMP TABLE l_TEMP1 (NoUrut	INT,
			Tahun		INT,
			Bulan		INT,
			Area		VARCHAR(4),
			SkArea		VARCHAR(20),
			Cabang		VARCHAR(4),
			SkCabang	VARCHAR(20),
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
			Hari		DECIMAL(4,1), 
			FlagDppt	VARCHAR(1),
			NomFormat	DECIMAL(3,0),
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
			KetTaxCal	VARCHAR(45),
			NilaiTaxCal	DECIMAL(15,2),
			JnsPjk		VARCHAR(1),
			JmlPiutang	DECIMAL(15,2),
			FlagThr		INT,
			Harian		INT,
			JmlLembur  	DECIMAL(5,1),
			Ket_periode	VARCHAR(100),
			Ket_Piutang	VARCHAR(100),
			Ket_Lembur	VARCHAR(50)
            ) ON COMMIT DROP ;          
--*
-- Mulai Cursor data
OPEN l_LOOP_W11 FOR 
SELECT  S02.TglPayr,S01.kdArea,S01.kdCaba,S01.kdUUsa,S01.kdUker,S01.kdGlng,S01.kdKJab,
	S01.kdJaba,S01.kdKlas,S01.NIP,S01.Nama,S01.JnsKlmn,S01.StsPjk,S02.FlgDpPt,
	M19.NomFormat,M01.Keterangan,M08.NmCaba,M02.Keterangan,M17.Keterangan,M12.Keterangan,
	M06.Keterangan,M04.Keterangan,M10.Keterangan,M19.Keterangan,M02.Keterangan,
	fn_kpusat(S02.NIP,S02.EncNilai,l_MyPass)::DECIMAL(15,2) AS Nilai,
	S01.BankRef,S01.AccHolder,S01.RkBnk,M14.Keterangan,S01.JnsPajak,S01.FlagTHR,
	COALESCE(getpiut(S01.NIP,l_Periode),0),
	fn_kpusat(S01.NIP,S01.EncIncTaxAPaidNB,l_MyPass) ::DECIMAL(15,2) AS IncTaxAPaidNB,
	fn_kpusat(S01.NIP,S01.EncIncTaxAPaidB,l_MyPass) ::DECIMAL(15,2) AS IncTaxAPaidB,
	fn_kpusat(S01.NIP,S01.EncGrossIncomeBYTD,l_MyPass) ::DECIMAL(15,2) AS GrossIncomeBYTD,
	fn_kpusat(S01.NIP,S01.EncOccSupport2,l_MyPass) ::DECIMAL(15,2) + 
	fn_kpusat(S03.NIP,S03.EncOLDOccSupport2,l_MyPass) ::DECIMAL(15,2) AS OccSupport2,
	fn_kpusat(S01.NIP,S01.EncPTKP,l_MyPass) ::DECIMAL(15,2) AS PTKP,
	fn_kpusat(S01.NIP,S01.EncTaxTotal,l_MyPass) ::DECIMAL(15,2) AS TaxTotal,
	fn_kpusat(S01.NIP,S01.EncGrossIncomeNBYTD,l_MyPass) ::DECIMAL(15,2) AS GrossIncomeNBYTD,
	fn_kpusat(S03.NIP,S03.EncOccSupport1,l_MyPass) ::DECIMAL(15,2) + 
	fn_kpusat(S03.NIP,S03.EncOLDOccSupport1,l_MyPass) ::DECIMAL(15,2) AS OccSupport1,
	fn_kpusat(S01.NIP,S01.EncCol12YTD,l_MyPass) ::DECIMAL(15,2) AS Col12YTD,
	fn_kpusat(S01.NIP,S01.EncEGIYNB,l_MyPass) ::DECIMAL(15,2) AS EGIYNB,
	fn_kpusat(S01.NIP,S01.EncPTKP,l_MyPass) ::DECIMAL(15,2) AS PTKP,
	fn_kpusat(S01.NIP,S01.EncEYTI,l_MyPass) ::DECIMAL(15,2) AS EYTI,
	fn_kpusat(S01.NIP,S01.EncEYIT,l_MyPass) ::DECIMAL(15,2) AS EYIT,
	fn_kpusat(S01.NIP,S01.EncYTDIT,l_MyPass) ::DECIMAL(15,2) AS YTDIT,
	S01.Hari,M10.Harian,S02.KdDpPt,
	fn_kpusat(S01.NIP,S01.EncTaxPesangonRp,l_MyPass) ::DECIMAL(15,2) AS TaxPesangonRp,
	COALESCE(fn_kpusat(S03.NIP,S03.EncTaxUMP,l_MyPass) ::DECIMAL(15,2),0) AS TaxUMP,
	COALESCE(fn_kpusat(S03.NIP,S03.EncTaxAPaidUMP,l_MyPass) ::DECIMAL(15,2),0) AS TaxAPaidUMP,
	GetLembur(S01.NIP,l_Periode) AS Lembur
FROM M19HSLG M19
INNER JOIN M20DSLG M20 ON M19.TipeLap=M20.TipeLap AND M19.FlgDpPt=M20.FlgDpPt AND M19.NomFormat=M20.NomFormat
INNER JOIN S02DGAJ S02 ON S02.FlgDpPt=M19.FlgDpPt AND S02.KdDpPt =M20.KdDpPt AND S02.FlgAngs=M20.FlgAngs
INNER JOIN S01HGAJ S01 ON S02.TglPayr=S01.TglPayr AND S02.NIP=S01.NIP
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
LEFT JOIN S03LTAX S03 ON S03.TglPayr=S01.TglPayr AND S03.NIP=S01.NIP
WHERE M19.TipeLap='1' AND S01.NIP BETWEEN l_NIPFr AND l_NIPTo AND
      S01.kdUker BETWEEN l_UkerFr AND l_UkerTo AND
      S01.kdCaba BETWEEN l_FCab AND l_TCab AND
      S01.TglPayr=l_Periode ;
--ORDER BY S01.TglPayr,S01.NIP,S02.FlgDpPt,M19.NomFormat ;
--      
<<LOOP_W11>> 
LOOP 
    FETCH l_LOOP_W11 
    INTO l_S02TglPayr,l_S01kdArea,l_S01kdCaba,l_S01kdUUsa,l_S01kdUker,l_S01kdGlng,l_S01kdKJab,
        l_S01kdJaba,l_S01kdKlas,l_S01NIP,l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S02FlgDpPt,
        l_M19NomFormat,l_M01Singkatan,l_M08SkCaba,l_M02Singkatan,l_M17Singkatan,l_M12Singkatan,
        l_M06Singkatan,l_M04Singkatan,l_M10Singkatan,l_M19Keterangan,l_M02Keterangan,
        l_S02Nilai,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,l_S01JnsPajak,l_S01FlagTHR,
        l_GetPiut,
        l_S01IncTaxAPaidNB,l_S01IncTaxAPaidB,l_S01GrossIncomeBYTD,l_S01OccSupport2,l_S01PTKP,l_S01TaxTotal,l_S01GrossIncomeNBYTD,
        l_S01OccSupport1,l_S01Col12YTD,l_S01EGIYNB,l_S01PTKP,l_S01EYTI,l_S01EYIT,l_S01YTDIT,
        l_S01Hari,l_M10Harian,l_S02KdDpPt,l_S01TaxPesangonRp,l_S03TaxUMP,l_S03TaxAPaidUMP,
        l_JmlLembur;
    --
    IF NOT FOUND THEN
        EXIT ;
    END IF;
     --
      l_Nilai:=0;
      ---
      l_Nilai:=l_S02Nilai;
      ---
      IF (SELECT COUNT(Area)  
          FROM l_Temp 
          WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr) AND Bulan=EXTRACT(MONTH FROM l_S02TglPayr) AND 
                Area =l_S01kdArea AND Cabang=l_S01kdCaba AND 
                UnitUsaha=l_S01kdUUsa AND KdUker=l_S01kdUker AND Glng =l_S01kdGlng AND KJab =l_S01kdKJab AND
                Jaba =l_S01kdJaba AND  Klas =l_S01kdKlas AND Nip  =l_S01NIP  AND  FlagDppt=l_S02FlgDpPt AND 
                NomFormat=l_M19NomFormat ) > 0 THEN 
          --
            UPDATE l_Temp
            SET Nilai=COALESCE(Nilai,0)+l_Nilai
            WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr) AND Bulan=EXTRACT(MONTH FROM l_S02TglPayr) AND
                  Area =l_S01kdArea AND Cabang=l_S01kdCaba AND UnitUsaha=l_S01kdUUsa AND
                  KdUker=l_S01kdUker AND Glng =l_S01kdGlng AND KJab =l_S01kdKJab AND
                  Jaba =l_S01kdJaba AND  Klas =l_S01kdKlas AND Nip  =l_S01NIP    AND  
                  FlagDppt=l_S02FlgDpPt AND  NomFormat=l_M19NomFormat; 
     ELSE
    	--
          INSERT INTO l_TEMP (Tahun              ,Bulan                           ,Area       ,SkArea        ,Cabang     ,
                    SkCabang, UnitUsaha  ,SkUUsa        ,KdUker     ,SkUker        ,Glng       ,SkGlng        ,KJab       ,
                    SkKjab        ,Jaba       ,SkJaba        ,Klas       ,SkKlas        ,Nip     ,FlagDppt    ,NomFormat     ,
                    Nama     ,JnsKlm      ,StsMed     ,KdBank      ,AccHolder     ,AccNumber ,KetBank        ,Keterangan     ,
                    Nilai  ,JnsPjk       ,JmlPiutang, FlagTHR, ketUUsa)
	      VALUES (EXTRACT(YEAR FROM l_S02TglPayr),EXTRACT(MONTH FROM l_S02TglPayr),l_S01kdArea,l_M01Singkatan,l_S01kdCaba,
                  l_M08SkCaba,l_S01kdUUsa,l_M02Singkatan,l_S01kdUker,l_M17Singkatan,l_S01kdGlng,l_M12Singkatan,l_S01kdKJab,
                  l_M06Singkatan,l_S01kdJaba,l_M04Singkatan,l_S01kdKlas,l_M10Singkatan,l_S01NIP,l_S02FlgDpPt,l_M19NomFormat,
                  l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,l_M19Keterangan,
                  l_Nilai,l_S01JnsPajak, l_GetPiut,CASE l_S01FlagThr WHEN '*' THEN 1 ELSE 0 END,l_M02Keterangan);
     END IF; 	
     --*
     -- Isi Data TEMP1 
     IF (SELECT COUNT(Area) 
         FROM l_Temp1 
         WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr) AND 
               Bulan=EXTRACT(MONTH FROM l_S02TglPayr) AND
               Area =l_S01kdArea AND 
               Cabang=l_S01kdCaba AND 
               UnitUsaha=l_S01kdUUsa AND
               KdUker=l_S01kdUker AND 
               Glng =l_S01kdGlng AND 
               KJab =l_S01kdKJab AND
               Jaba =l_S01kdJaba AND  
               Klas =l_S01kdKlas AND 
               Nip  =l_S01NIP ) = 0 THEN 
          --
          l_Nomor:=1;
          --
          WHILE l_Nomor<=39
          LOOP 
                l_NiltaxCal := 0; 
                -- 
                IF l_Nomor=1 THEN 
                     l_TpFormat2 := 'T';
                     l_KetTaxCal := 'NON BONUS';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=2 THEN 
                     l_TpFormat2 := 'D';
                     l_KetTaxCal := 'Penghasilan Bruto(YTD)';
                     l_NiltaxCal := l_S01GrossIncomeNBYTD;
                ELSIF l_Nomor=3 THEN
    	             l_TpFormat2 := 'D';
            	     l_KetTaxCal := 'Biaya Jabatan';
                     l_NiltaxCal := l_S01OccSupport1*-1;
                ELSIF l_Nomor=4 THEN
                     l_TpFormat2 := 'D';
        		     l_KetTaxCal := 'Iuran J.H.T.(YTD)';
                     l_NiltaxCal := l_S01Col12YTD;
                ELSIF l_Nomor=5 THEN 
                     l_TpFormat2 := 'G';
        		     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=6 THEN 
                     l_TpFormat2 := 'J';
        		     l_KetTaxCal := 'Penghasilan Netto(YTD)';
                     l_NiltaxCal := l_S01GrossIncomeNBYTD-l_S01OccSupport1+l_S01Col12YTD;
                ELSIF l_Nomor=7 THEN 
                     l_TpFormat2 := 'T';
        		     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=8 THEN                   
                     l_TpFormat2 := 'D';
        		     l_KetTaxCal := 'Penghasilan Netto Disetahunkan';
                     l_NiltaxCal := l_S01EGIYNB;
                ELSIF l_Nomor=9 THEN 
                     l_TpFormat2 := 'D';
        		     l_KetTaxCal := 'P.T.K.P';
                     l_NiltaxCal := CASE WHEN l_S01EGIYNB<> 0 THEN  l_S01PTKP ELSE 0 END;
                ELSIF l_Nomor=10 THEN 
                     l_TpFormat2 := 'D';
        		     l_KetTaxCal := 'Penghasilan Kena Pajak Setahun';
                     l_NiltaxCal := CASE WHEN l_S01EGIYNB<> 0 THEN l_S01EYTI ELSE 0 END;
                ELSIF l_Nomor=11 THEN 
                     l_TpFormat2 := 'D';
        		     l_KetTaxCal := 'PPh terutang Setahun';
                     l_NiltaxCal := l_S01EYIT;
                ELSIF l_Nomor=12 THEN 
                     l_TpFormat2 := 'T';
        		     l_KetTaxCal := '';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=13 THEN 
                     l_TpFormat2 := 'D';
        		     l_KetTaxCal := 'PPh terutang(YTD)';
                     l_NiltaxCal := l_S01YTDIT;
                ELSIF l_Nomor=14 THEN 
                     l_TpFormat2 := 'D';
        		     l_KetTaxCal := 'PPh yang telah dipotong';
                     l_NiltaxCal := l_S01IncTaxAPaidNB;
                ELSIF l_Nomor=15 THEN 
                     l_TpFormat2 := 'G';
        		     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=16 THEN 
                     l_TpFormat2 := 'J';
        		     l_KetTaxCal := 'PPh yang harus dipotong';
                     l_NiltaxCal := l_S01YTDIT-l_S01IncTaxAPaidNB;
                ELSIF l_Nomor=17 THEN 
                     l_TpFormat2 := 'P';
        		     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=18 THEN 
                     l_TpFormat2 := 'T';
        		     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=19 THEN 
                     l_TpFormat2 := 'T';
        		     l_KetTaxCal := 'BONUS ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=20 THEN 
                     l_TpFormat2 := 'D';
        		     l_KetTaxCal := 'Penghasilan Netto Disetahunkan(Non Bonus)';
                     l_NiltaxCal := l_S01EGIYNB;
                ELSIF l_Nomor=21 THEN 
                     l_TpFormat2 := 'D';
        		     l_KetTaxCal := 'Bonus Setahun';
                     l_NiltaxCal := l_S01GrossIncomeBYTD;
                ELSIF l_Nomor=22 THEN 
                     l_TpFormat2 := 'D';
        		     l_KetTaxCal := 'Biaya Jabatan';
                     l_NiltaxCal := l_S01OccSupport2 *-1;
                ELSIF l_Nomor=23 THEN 
                     l_TpFormat2 := 'D';
                     l_KetTaxCal := 'P.T.K.P';
                     l_NiltaxCal := l_S01PTKP;
                ELSIF l_Nomor=24 THEN 
    	             l_TpFormat2 := 'G';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=25 THEN 
    	             l_TpFormat2 := 'J';
                     l_KetTaxCal := 'Total Penghasilan Kena Pajak';
                     l_NiltaxCal := CASE WHEN ((l_S01EGIYNB+l_S01GrossIncomeBYTD)-(l_S01OccSupport2+l_S01PTKP)) < 0 THEN 
                    				       (FLOOR((ABS((l_S01EGIYNB+l_S01GrossIncomeBYTD)-(l_S01OccSupport2+l_S01PTKP))/1000))*1000)*-1
                                    ELSE FLOOR((((l_S01EGIYNB+l_S01GrossIncomeBYTD)-(l_S01OccSupport2+l_S01PTKP))/1000))*1000 END;
                ELSIF l_Nomor=26 THEN 
    	             l_TpFormat2 := 'T';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=27 THEN 
    	             l_TpFormat2 := 'D';
                     l_KetTaxCal := 'Total PPh terutang Setahun';
                     l_NiltaxCal := l_S01TaxTotal;
                ELSIF l_Nomor=28 THEN 
    	             l_TpFormat2 := 'D';
                     l_KetTaxCal := 'PPh Setahun - Non Bonus';
                     l_NiltaxCal := l_S01EYIT;
                ELSIF l_Nomor=29 THEN 
    	             l_TpFormat2 := 'G';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=30 THEN 
    	             l_TpFormat2 := 'J';
                     l_KetTaxCal := 'PPh Setahun - Bonus';
                     l_NiltaxCal := l_S01TaxTotal - l_S01EYIT;
                ELSIF l_Nomor=31 THEN 
    	             l_TpFormat2 := 'D';
                     l_KetTaxCal := 'PPh - Bonus yang telah dipotong';
                     l_NiltaxCal := l_S01IncTaxAPaidB;
                ELSIF l_Nomor=32 THEN 
    	             l_TpFormat2 := 'G';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=33 THEN 
    	             l_TpFormat2 := 'J';
                     l_KetTaxCal := 'PPh - Bonus yang harus dipotong';
                     l_NiltaxCal := l_S01TaxTotal - l_S01EYIT - l_S01IncTaxAPaidB;
                ELSIF l_Nomor=34 THEN 
    	             l_TpFormat2 := 'P';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=35 THEN 
    	             l_TpFormat2 := 'T'; 
                     l_KetTaxCal := 'PPh Ditanggung Pemerintah(DP)';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=36 THEN 
    	             l_TpFormat2 := 'D';
                     l_KetTaxCal := 'PPh DP Bulan ini';
                     l_NiltaxCal := l_S03TaxUMP;
                ELSIF l_Nomor=37 THEN 
    	             l_TpFormat2 := 'D';
                     l_KetTaxCal := 'PPh DP yang telah dipotong';
                     l_NiltaxCal := l_S03TaxAPaidUMP;
                ELSIF l_Nomor=38 THEN 
    	             l_TpFormat2 := 'G';
                     l_KetTaxCal := ' ';
                     l_NiltaxCal := 0;
                ELSIF l_Nomor=39 THEN 
    	             l_TpFormat2 := 'J';
                     l_KetTaxCal := 'PPh DP yang harus dipotong';
                     l_NiltaxCal := l_S03TaxUMP-l_S03TaxAPaidUMP;
                END IF; 

                INSERT INTO l_TEMP1(NoUrut, Tahun, Bulan, Area, SkArea,
                                    Cabang, SkCabang, UnitUsaha, SkUUsa, KdUker, SkUker, Glng, SkGlng, KJab, SkKjab,
                                    Jaba, SkJaba, Klas, SkKlas, Nip, FlagDppt,
                                    NomFormat, Nama, JnsKlm, StsMed, KdBank, AccHolder, AccNumber, KetBank,
                                    Keterangan, Nilai, JnsPjk, JmlPiutang, FlagTHR, ketUUsa, 
                                    TpFormat2, KetTaxCal, NilaiTaxCal, Tpformat1, Hari, Harian, JmlLembur)
	       	    VALUES(l_Nomor,EXTRACT(YEAR FROM l_S02TglPayr),EXTRACT(MONTH FROM l_S02TglPayr),l_S01kdArea,l_M01Singkatan,
                       l_S01kdCaba,l_M08SkCaba,l_S01kdUUsa,l_M02Singkatan,l_S01kdUker,l_M17Singkatan,l_S01kdGlng,l_M12Singkatan,
                       l_S01kdKJab,l_M06Singkatan,l_S01kdJaba,l_M04Singkatan,l_S01kdKlas,l_M10Singkatan,l_S01NIP,l_S02FlgDpPt,
                       l_M19NomFormat,l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,
                       ' ', 0, l_S01JnsPajak, l_GetPiut, CASE l_S01FlagThr WHEN '*' THEN 1 ELSE 0 END, l_M02Keterangan,
                       l_TpFormat2,l_KetTaxCal, l_NiltaxCal, 'T', l_S01Hari,l_M10Harian,l_JmlLembur);
                --
                l_Nomor := l_Nomor+1; 
                --                
          END LOOP; 
          --* 
          --
     END IF; 
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
--
OPEN l_LOOP_TEMP FOR 
SELECT  Tahun, Bulan, Area, SkArea, Cabang, SkCabang, UnitUsaha, SkUUsa, KetUUsa, KdUker, SkUker, Glng, SkGlng, KJab, SkKjab,
        Jaba, SkJaba, Klas, SkKlas, Nip, FlagDppt, NomFormat, Nama, JnsKlm, StsMed, KdBank, AccHolder, AccNumber, KetBank,
        Keterangan, Nilai, JnsPjk, JmlPiutang, FlagThr
FROM l_TEMP
ORDER BY Tahun, Bulan, Area, SkArea, Cabang, SkCabang, UnitUsaha, SkUUsa, KetUUsa, KdUker, SkUker, Glng, SkGlng, KJab, SkKjab,
        Jaba, SkJaba, Klas, SkKlas, Nip, FlagDppt, NomFormat;
--
<<l_LOOP_TEMP>> 
LOOP 
    FETCH l_LOOP_TEMP 
    INTO    l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,l_TEMPUnitUsaha,l_TEMPSkUUsa,
            l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,
            l_TEMPKlas,l_TEMPSkKlas,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,
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
                   Keterangan=' ',
                   Nilai     =0	
               WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
               
               l_Nomor := l_Nomor+1;
               --
               UPDATE l_TEMP1
               SET TpFormat1 ='J',
                   Keterangan='  ',
                   Nilai     =l_TotPot
               WHERE NIP=l_NIP  AND NoUrut=l_Nomor;
	                                        
               l_Nomor := l_Nomor+1;
	       --
               UPDATE l_TEMP1
               SET TpFormat1 ='G',
                   Keterangan=' ',
                   Nilai     =0	
               WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
                   --
               l_Nomor := l_Nomor+1;

               UPDATE l_TEMP1
               SET TpFormat1 ='J',
                   Keterangan='Total Slip Gaji',
                   Nilai     =l_TotPEnd-l_TotPot
               WHERE NIP=l_Nip  AND NoUrut=l_Nomor;
          END IF; 
          --* 
          l_Nomor   := 1;
          l_TotPend := 0;
          l_TotPot  := 0;
          l_NIP     := l_TEMPNip;
     END IF; 
     --*
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
              Keterangan=' ',
              Nilai     =0	
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
          --
          l_Nomor := l_Nomor+1;
          --
          UPDATE l_TEMP1
          SET TpFormat1 ='J',
              Keterangan='  ',
              Nilai     =l_TotPend
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
          --
          l_Nomor := l_Nomor+1;
          --      
          UPDATE l_TEMP1
          SET TpFormat1 ='T',
              Keterangan='POTONGAN',
              Nilai     =0	
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
          --
          l_Nomor := l_Nomor+1;
          --
          UPDATE l_TEMP1
          SET TpFormat1 ='D',
              Keterangan=l_TEMPKeterangan,
              Nilai     =l_TEMPNilai		  
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;            
     ELSE
          UPDATE l_TEMP1
          SET TpFormat1 ='D',
              Keterangan=l_TEMPKeterangan,
              Nilai     =l_TEMPNilai	
          WHERE NIP=l_NIP  AND NoUrut=l_Nomor;     
     END IF;
     --* 

     l_Nomor := l_Nomor+1;
     -- 
     IF l_FlgDpPt<>l_TEMPFlagDppt THEN 
           l_FlgDpPt := l_TEMPFlagDppt; 
     END IF; 
     --*
--
END LOOP;
CLOSE l_LOOP_TEMP;
--
    UPDATE l_TEMP1
    SET TpFormat1 ='G',
        Keterangan=' ',
        Nilai     =0	
    WHERE NIP=l_NIP  AND NoUrut=l_Nomor;
    --
    l_Nomor := l_Nomor+1;
    --
    UPDATE l_TEMP1
    SET TpFormat1 ='J',
         Keterangan='  ',
        Nilai     =l_TotPot
    WHERE NIP=l_NIP  AND NoUrut=l_Nomor;
    --
    l_Nomor := l_Nomor+1;

    UPDATE l_TEMP1
    SET TpFormat1 ='G',
        Keterangan=' ',
        Nilai     =0
    WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
    --
    l_Nomor := l_Nomor+1;         
    --
    UPDATE l_TEMP1
    SET TpFormat1 ='J',
        Keterangan='Total Slip Gaji ',
        Nilai     =l_TotPEnd-l_TotPot
    WHERE NIP=l_NIP  AND NoUrut=l_Nomor;    
    --
    UPDATE l_TEMP1
    SET Ket_Periode ='* Sisa Piutang per '||TO_CHAR(l_Periode,'DD-MM-YYYY'),
        Ket_Piutang =' = Rp. '||CAST(JmlPiutang AS VARCHAR(15)),
        Ket_Lembur  ='* Total lembur = '||CAST(JmlLembur AS VARCHAR(5));
    --
    RETURN QUERY 
    SELECT TMP.NoUrut, TMP.Tahun, TMP.Bulan, TMP.Area, TMP.SkArea, TMP.Cabang, TMP.SkCabang, TMP.UnitUsaha,
        TMP.SkUUsa, TMP.KetUUsa, TMP.KdUker, TMP.SkUker, TMP.Glng, TMP.SkGlng, TMP.KJab, TMP.SkKjab, 
        TMP.Jaba, TMP.SkJaba, TMP.Klas, TMP.SkKlas, TMP.Nip, TMP.Hari, TMP.FlagDppt, TMP.NomFormat,
        TMP.Nama, TMP.JnsKlm, TMP.StsMed, TMP.KdBank, TMP.AccHolder, TMP.AccNumber, TMP.KetBank, TMP.TpFormat1,	
        TMP.Keterangan, TMP.Nilai, TMP.TpFormat2, TMP.KetTaxCal, TMP.NilaiTaxCal, TMP.JnsPjk, 
        TMP.JmlPiutang, TMP.FlagThr, TMP.Harian, TMP.JmlLembur, 
        CASE WHEN EXTRACT(YEAR FROM M15.PrdAwl)<>EXTRACT(YEAR FROM l_Periode) OR 
                 (EXTRACT(YEAR FROM M15.PrdAwl)=EXTRACT(YEAR FROM l_Periode) AND 
                  (SELECT COUNT(T25.NIP) FROM T25OPBP T25 WHERE T25.NIP = M15.NIP AND T25.FLGIMPL ='T')= 0) THEN 
             ' '
        ELSE 'Pindah Kerja' END :: VARCHAR(12) AS FLGOPBL,
        CASE WHEN TMP.JmlPiutang<>0 AND TMP.JmlLembur<>0 THEN TMP.Ket_Periode||TMP.Ket_Piutang||TMP.Ket_Lembur
             WHEN TMP.JmlPiutang<>0 AND TMP.JmlLembur=0  THEN TMP.Ket_Periode||TMP.Ket_Piutang
             WHEN TMP.JmlPiutang=0 AND TMP.JmlLembur<>0  THEN TMP.Ket_Lembur
             ELSE ' ' END :: VARCHAR(200) AS KET_FOOTER
    FROM l_TEMP1 TMP
    INNER JOIN M15PEGA M15 ON TMP.NIP = M15.NIP 
    where TMP.JnsPjk=' '; 
  --
END;
--
$$ LANGUAGE plpgsql ;
GO

/*
select * from R_SLIP_A ('2013-11-20', ' ','ZZZ', ' ', 'ZZZ', '.','ZZZ',
                        'Copyright, 1988 (c) Microsoft Corporation, All rights reserved',
                        1) 
order by outnourut;

*/--select 'tanggal : '||cast(current_date as varchar(10)) as tgl, 'persen : '||cast(25/100 :: numeric(15,2) as varchar(4)) as pct

 





