/****************************************
Name sprocs	: P_SLIKP1
Create by	: Herz
Date		: 02-10-2001
Description	: Proses Untuk Report Kartu Slip 
Call From	: Main Proc
Sub sprocs	: -
Update by	: Yudi's  -->  26/09/02
*****************************************/
CREATE OR REPLACE FUNCTION P_SLIKP1  (	l_Periode 	DATE,
					l_UkerFr	VARCHAR(4),
					l_UkerTo	VARCHAR(4),
					l_NIPFr		VARCHAR(10),
					l_NIPTo		VARCHAR(10),
					l_FGol  	VARCHAR(4),
					l_TGol   	VARCHAR(4),
					l_FCab   	VARCHAR(4),
					l_TCab   	VARCHAR(4),
					l_MyPass	VARCHAR(128))
--
RETURNS TABLE(OUTTahun		INT,
		OUTBulan		INT,
		OUTArea			VARCHAR(4),
		OUTNmArea		VARCHAR(20),
		OUTSkArea		VARCHAR(10),
		OUTCabang		VARCHAR(4), 
		OUTSkCabang		VARCHAR(10),
		OUTUnitUsaha		VARCHAR(4),
		OUTSkUUsa		VARCHAR(10),
		OUTKetUUsa		VARCHAR(20),
		OUTKdUker		VARCHAR(8),
		OUTSkUker		VARCHAR(10), 
		OUTGlng			VARCHAR(4), 
		OUTSkGlng		VARCHAR(10), 
		OUTKJab			VARCHAR(4), 
		OUTSkKjab		VARCHAR(10), 
		OUTJaba			VARCHAR(4), 
		OUTSkJaba		VARCHAR(10), 
		OUTKlas			VARCHAR(4), 
		OUTSkKlas		VARCHAR(10), 
		OUTNip			VARCHAR(10), 
		OUTFlagDppt		VARCHAR(1),
		OUTNomFormat		DECIMAL(3,0),
		OUTNama			VARCHAR(25), 
		OUTJnsKlm		VARCHAR(1),
		OUTStsMed		VARCHAR(2),
		OUTKdBank		VARCHAR(4),
		OUTAccHolder	 	VARCHAR(25),
		OUTAccNumber		VARCHAR(20),
		OUTKetBank		VARCHAR(40),
		OUTJnsPjk		VARCHAR(1),
		OUTFlagTHR		VARCHAR(1),
		OUTPiutang		Decimal(15,2),
		OUTLemburan		Decimal(5,2),
		OUTNoUrut		INT,
		OUTTpFormat1		VARCHAR(1),
		OUTGrossIncome		VARCHAR(40),
		OUTNilGRoss		DECIMAL(15,2),
		OUTTpFormat2		VARCHAR(1),
		OUTDeduction		VARCHAR(40),
		OUTNilDeduc		DECIMAL(15,2),
		OUTKetTakeHomePAy  	VARCHAR(40),
		OUTNilTakeHomePAy	DECIMAL(15,2))  
 
AS $$
--
DECLARE l_Para			VARCHAR(4000);
	l_LineTXT		VARCHAR(4000);
	l_S02TglPayr		DATE;
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
	l_S01JnsKlmn		VARCHAR(1);
	l_S01StsPjk		VARCHAR(2);
	l_S02FlgDpPt		VARCHAR(1);
	l_M19NomFormat		DECIMAL(3,0);
	l_M01Keterangan		VARCHAR(20);
	l_M01Singkatan		VARCHAR(10);
	l_M08SkCaba		VARCHAR(10);
	l_M02Singkatan		VARCHAR(10);
	l_M17Singkatan		VARCHAR(10);
	l_M12Singkatan		VARCHAR(10);
	l_M06Singkatan		VARCHAR(10);
	l_M04Singkatan		VARCHAR(10);
	l_M10Singkatan		VARCHAR(10);
	l_M19Keterangan		VARCHAR(20);
	l_M02Keterangan		VARCHAR(20);
	l_S02Nilai		DECIMAL(15,2);
	l_S01BankRef		VARCHAR(4);
	l_S01AccHolder		VARCHAR(25);
	l_S01RkBnk		VARCHAR(20);
	l_M14Keterangan		VARCHAR(40);
	l_S01JnsPajak		VARCHAR(1);
	l_S01FlagTHR		VARCHAR(1);
	l_GetPiut		Decimal(15,2);
	l_GetLmbr		Decimal(5,2);
	l_TempTahun		INT;
	l_TempBulan		INT;
	l_TempArea		VARCHAR(4);
	l_TempNmArea		VARCHAR(20);
	l_TempSkArea		VARCHAR(10);
	l_TempCabang		VARCHAR(4);
	l_TempSkCabang		VARCHAR(10);
	l_TempUnitUsaha		VARCHAR(4);
	l_TempSkUUsa		VARCHAR(10);
	l_TempKetUUsa		VARCHAR(20);
	l_TempKdUker		VARCHAR(8);
	l_TempSkUker		VARCHAR(10); 
	l_TempGlng		VARCHAR(4); 
	l_TempSkGlng		VARCHAR(10); 
	l_TempKJab		VARCHAR(4); 
	l_TempSkKjab		VARCHAR(10); 
	l_TempJaba		VARCHAR(4); 
	l_TempSkJaba		VARCHAR(10); 
	l_TempKlas		VARCHAR(4); 
	l_TempSkKlas		VARCHAR(10); 
	l_TempNip		VARCHAR(10); 
	l_TempFlagDppt		VARCHAR(1);
	l_TempNomFormat		DECIMAL(3,0);
	l_TempNama		VARCHAR(25); 
	l_TempJnsKlm		VARCHAR(1);
	l_TempStsMed		VARCHAR(2);
	l_TempKdBank		VARCHAR(4);
	l_TempAccHolder		VARCHAR(25);
	l_TempAccNumber		VARCHAR(20);
	l_TempKetBank		VARCHAR(40);
	l_TempKeterangan 	VARCHAR(20);
	l_TempNilai		DECIMAL(15,2);
	l_TempJnsPjk		VARCHAR(1);
	l_TempFlagTHR		VARCHAR(1);
	l_TempPiutang		Decimal(15,2);
	l_TempLemburan		Decimal(5,2);
	l_NIP			VARCHAR(10);
	l_FlgDpPt		VARCHAR(1);
	l_NoUrut		INT;
	l_LOOP_TEMP		REFCURSOR;
	l_LOOP_W11		REFCURSOR;
	l_NoPend		INT;
	l_NoPot			INT;
	l_TotPend		DECIMAL(15,2);
	l_TotPot		DECIMAL(15,2);
	l_GolFr          	VARCHAR(4);
	l_GolTo          	VARCHAR(4);
	l_S01TakeHomePay 	DECIMAL(15,2);
	l_TEMPTakeHomePay 	DECIMAL(15,2);
	l_OLDTEMPTahun		INT;
	l_OLDTEMPBulan 		INT;	
	l_OLDTEMPArea		VARCHAR(4);
	l_OLDTEMPNmArea 	VARCHAR(20);
	l_OLDTEMPSkArea 	VARCHAR(10);
	l_OLDTEMPCabang 	VARCHAR(4);
	l_OLDTEMPSkCabang 	VARCHAR(10);
	l_OLDTEMPUnitUsaha 	VARCHAR(4);
	l_OLDTEMPSkUUsa		VARCHAR(10);
	l_OLDTEMPKetUUsa 	VARCHAR(20);
	l_OLDTEMPKdUker		VARCHAR(8);
	l_OLDTEMPSkUker		VARCHAR(10);
	l_OLDTEMPGlng		VARCHAR(4);
	l_OLDTEMPSkGlng		VARCHAR(10);
	l_OLDTEMPKJab		VARCHAR(4);
	l_OLDTEMPSkKjab		VARCHAR(10);
	l_OLDTEMPJaba		VARCHAR(4);
	l_OLDTEMPSkJaba		VARCHAR(10);	
	l_OLDTEMPKlas		VARCHAR(4);
	l_OLDTEMPSkKlas		VARCHAR(10);	
	l_OLDNip		VARCHAR(10);
	l_OLDTEMPFlagDppt 	VARCHAR(1);
	l_OLDTEMPNomFormat 	DECIMAL(3,0);
	l_OLDTEMPNama		VARCHAR(25);
	l_OLDTEMPJnsKlm		VARCHAR(1);
	l_OLDTEMPStsMed		VARCHAR(2);
	l_OLDTEMPKdBank		VARCHAR(4);
	l_OLDTEMPAccHolder	VARCHAR(25);
	l_OLDTEMPAccNumber	VARCHAR(20);
	l_OLDTEMPKetBank	VARCHAR(40);
	l_OLDTEMPJnsPjk		VARCHAR(1);
	l_OLDTEMPFlagTHR	VARCHAR(1);
	l_OLDTEMPPiutang	DECIMAL(15,2);
	l_OLDTEMPLemburan	DECIMAL(5,2);
	l_OLDNoPot		INT;
	l_OLDTotPot		DECIMAL(15,2);

--
BEGIN 
CREATE TEMP TABLE l_TEMP (Tahun		INT,
			Bulan		INT,
			Area		VARCHAR(4),
			NmArea		VARCHAR(20),
			SkArea		VARCHAR(10),
			Cabang		VARCHAR(4),
			SkCabang	VARCHAR(10),
			UnitUsaha	VARCHAR(4),
			SkUUsa		VARCHAR(10),
			KetUUsa		VARCHAR(20),
			KdUker		VARCHAR(8),
			SkUker		VARCHAR(10), 
			Glng		VARCHAR(4), 
			SkGlng		VARCHAR(10), 
			KJab		VARCHAR(4), 
			SkKjab		VARCHAR(10), 
			Jaba		VARCHAR(4), 
			SkJaba		VARCHAR(10), 
			Klas		VARCHAR(4), 
			SkKlas		VARCHAR(10), 
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
			FlagTHR		VARCHAR(1),
			Piutang		Decimal(15,2),
			Lemburan	Decimal(5,2)) ON COMMIT DROP ;    

CREATE TEMP TABLE l_TEMP1 (Tahun		INT,
				Bulan		INT,
				Area		VARCHAR(4),
				NmArea		VARCHAR(20),
				SkArea		VARCHAR(10),
				Cabang		VARCHAR(4), 
				SkCabang	VARCHAR(10),
				UnitUsaha	VARCHAR(4),
				SkUUsa		VARCHAR(10),
				KetUUsa		VARCHAR(20),
				KdUker		VARCHAR(8),
				SkUker		VARCHAR(10), 
				Glng		VARCHAR(4), 
				SkGlng		VARCHAR(10), 
				KJab		VARCHAR(4), 
				SkKjab		VARCHAR(10), 
				Jaba		VARCHAR(4), 
				SkJaba		VARCHAR(10), 
				Klas		VARCHAR(4), 
				SkKlas		VARCHAR(10), 
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
				JnsPjk		VARCHAR(1),
				FlagTHR		VARCHAR(1),
				Piutang		Decimal(15,2),
				Lemburan	Decimal(5,2),
				NoUrut		INT,
				TpFormat1	VARCHAR(1),
				GrossIncome	VARCHAR(40),
				NilGRoss	DECIMAL(15,2),
				TpFormat2	VARCHAR(1),
				Deduction	VARCHAR(40),
				NilDeduc	DECIMAL(15,2),
				KetTakeHomePAy  VARCHAR(40),
				NilTakeHomePAy	DECIMAL(15,2))  ON COMMIT DROP ; 
--
-- Mulai Cursor data
OPEN l_LOOP_W11 FOR 
SELECT  S02.TglPayr,S01.kdArea,S01.kdCaba,S01.kdUUsa,S01.kdUker,S01.kdGlng,S01.kdKJab,
	S01.kdJaba,S01.kdKlas,S01.NIP,S01.Nama,S01.JnsKlmn,S01.StsPjk,S02.FlgDpPt,
	M19.NomFormat,M01.Keterangan,M01.Singkatan,M08.SkCaba,M02.Singkatan,M17.Singkatan,M12.Singkatan,
	M06.Singkatan,M04.Singkatan,M10.Singkatan,M19.Keterangan,M02.Keterangan,
	fn_kpusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2),S01.BankRef,S01.AccHolder,S01.RkBnk,M14.Keterangan,S01.JnsPajak,S01.FlagTHR,
	COALESCE(getpiut(S01.NIP,l_Periode),0), GetLembur(S01.NIP,l_Periode)
--
FROM M19HSLG M19
--
INNER JOIN M20DSLG M20 
ON M19.TipeLap=M20.TipeLap AND
   M19.FlgDpPt=M20.FlgDpPt AND
   M19.NomFormat=M20.NomFormat
--
INNER JOIN S02DGAJ S02 
ON S02.FlgDpPt=M19.FlgDpPt AND 
   S02.KdDpPt =M20.KdDpPt AND
   S02.FlgAngs=M20.FlgAngs
--
INNER JOIN S01HGAJ S01
ON S02.TglPayr=S01.TglPayr AND
   S02.NIP    =S01.NIP
--
INNER JOIN M01AREA M01
ON M01.Kode=S01.kdArea
--
INNER JOIN M08HCAB M08
ON M08.KdCaba=S01.kdCaba
--
INNER JOIN M02UUSA M02
ON M02.Kode=S01.kdUUsa
--
INNER JOIN M17UKER M17
ON M17.KdUker=S01.kdUker
--
INNER JOIN M12HGOL M12
ON M12.Kode =S01.kdGlng
--
INNER JOIN M06HKJB M06
ON M06.Kode=S01.kdKJab
--
INNER JOIN M04HJAB M04
ON M04.Kode =S01.kdJaba
--
INNER JOIN M10KLAS M10
ON M10.Kode=S01.kdKlas
--
INNER JOIN M14BANK M14
ON M14.Kode=S01.BankRef 
--
--
WHERE M19.TipeLap='1' AND
      S01.kdUker BETWEEN l_UkerFr AND l_UkerTo AND
      S01.kdCaba BETWEEN l_FCab AND l_TCab AND
      S01.KdGlng BETWEEN l_FGol AND l_TGol AND 
      S01.NIP BETWEEN l_NIPFr AND l_NIPTo AND
      S01.TglPayr=l_Periode;
--      
<<l_LOOP_W11>> 
LOOP 
FETCH l_LOOP_W11 
INTO    l_S02TglPayr,l_S01kdArea,l_S01kdCaba,l_S01kdUUsa,l_S01kdUker,l_S01kdGlng,l_S01kdKJab,
	l_S01kdJaba,l_S01kdKlas,l_S01NIP,l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S02FlgDpPt,
	l_M19NomFormat,l_M01Keterangan,l_M01Singkatan,l_M08SkCaba,l_M02Singkatan,l_M17Singkatan,l_M12Singkatan,
	l_M06Singkatan,l_M04Singkatan,l_M10Singkatan,l_M19Keterangan,l_M02Keterangan,
	l_S02Nilai,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,l_S01JnsPajak,l_S01FlagTHR,
	l_GetPiut,l_GetLmbr ;

	IF NOT FOUND THEN
		EXIT ;
	END IF;

     --	
      IF (SELECT COUNT(Area) FROM l_TEMP WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr) AND Bulan=EXTRACT(MONTH FROM l_S02TglPayr) AND
     			    Area =l_S01kdArea AND Cabang=l_S01kdCaba AND UnitUsaha=l_S01kdUUsa AND
     			    KdUker=l_S01kdUker AND Glng =l_S01kdGlng AND KJab =l_S01kdKJab AND
	                    Jaba =l_S01kdJaba AND  Klas =l_S01kdKlas AND Nip  =l_S01NIP    AND  
     			    FlagDppt=l_S02FlgDpPt AND  NomFormat=l_M19NomFormat ) > 0 THEN 

         --
            UPDATE l_TEMP
	    SET Nilai=COALESCE(Nilai,0)+l_S02Nilai
	    --	
	    WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr) AND Bulan=EXTRACT(MONTH FROM l_S02TglPayr) AND
        	  Area =l_S01kdArea AND Cabang=l_S01kdCaba AND UnitUsaha=l_S01kdUUsa AND
     		  KdUker=l_S01kdUker AND Glng =l_S01kdGlng AND KJab =l_S01kdKJab AND
	          Jaba =l_S01kdJaba AND  Klas =l_S01kdKlas AND Nip  =l_S01NIP    AND  
     		  FlagDppt=l_S02FlgDpPt AND  NomFormat=l_M19NomFormat ; 
         --
     ELSE
	--
          INSERT INTO l_TEMP(Tahun                          ,Bulan                           ,Area       ,NmArea         ,SkArea        ,Cabang     ,SkCabang   ,UnitUsaha  ,SkUUsa        ,KdUker     ,SkUker        ,Glng       ,SkGlng        ,KJab       ,SkKjab        ,Jaba       ,SkJaba        ,Klas       ,SkKlas        ,Nip     ,FlagDppt    ,NomFormat     ,Nama     ,JnsKlm      ,StsMed     ,KdBank      ,AccHolder     ,AccNumber ,KetBank        ,Keterangan     ,Nilai     ,JnsPjk,       FlagThr,     Piutang,  Lemburan)
	              VALUES(EXTRACT(YEAR FROM l_S02TglPayr),EXTRACT(MONTH FROM l_S02TglPayr),l_S01kdArea,l_M01Keterangan,l_M01Singkatan,l_S01kdCaba,l_M08SkCaba,l_S01kdUUsa,l_M02Singkatan,l_S01kdUker,l_M17Singkatan,l_S01kdGlng,l_M12Singkatan,l_S01kdKJab,l_M06Singkatan,l_S01kdJaba,l_M04Singkatan,l_S01kdKlas,l_M10Singkatan,l_S01NIP,l_S02FlgDpPt,l_M19NomFormat,l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,l_M19Keterangan,l_S02Nilai,l_S01JnsPajak,l_S01FlagTHR,l_GetPiut,l_GetLmbr);
     END IF ; 
     --*'
    --
END LOOP;
CLOSE l_LOOP_W11;

------------------------------------- Isi data ke TEMP1 -----------------------------------
--
l_NIP     := ' ';
l_FlgDpPt := ' ';
l_NoUrut  := 1;
l_NoPend  := 1;
l_NoPot   := 1;
--
OPEN l_LOOP_TEMP FOR 
SELECT Tahun,Bulan,Area,NmArea,SkArea,Cabang,SkCabang,
	UnitUsaha,SkUUsa,KetUUsa,KdUker,SkUker,Glng,SkGlng,
	KJab,SkKjab,Jaba,SkJaba,Klas,SkKlas,Nip,FlagDppt,
	NomFormat,Nama,JnsKlm,StsMed,KdBank,AccHolder,AccNumber,
	KetBank,Keterangan,Nilai,JnsPjk,FlagTHR,Piutang,Lemburan
FROM l_TEMP
order by Nip,FlagDppt,NomFormat;

<<l_LOOP_TEMP>> 
LOOP 
FETCH l_LOOP_TEMP 
INTO l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPNmArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,
     l_TEMPUnitUsaha,l_TEMPSkUUsa,l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,
     l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,l_TEMPKlas,l_TEMPSkKlas,l_TEMPNip,l_TEMPFlagDppt,
     l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,
     l_TEMPKetBank,l_TEMPKeterangan,l_TEMPNilai,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan;

	IF NOT FOUND THEN
		EXIT ;
	END IF;
--
--
    IF l_NIP<>l_TEMPNip THEN 
         IF l_NIP<>' ' THEN 
             IF (SELECT COUNT(tahun) FROM l_TEMP1 WHERE NIP=l_NIP AND NoUrut=l_NoPot) > 0 THEN 
                  UPDATE l_TEMP1
                  SET TpFormat2='J',
	              Deduction='** Total DEDUCTION',
	              NilDeduc=l_TotPot,
		      KetTakeHomePAy='TAKE HOME PAY'
                      --NilTakeHomePAy=l_TotPend-l_TotPot	
                  WHERE NIP=l_NIP AND NoUrut=l_NoPot;
             ELSE     
                  INSERT INTO l_TEMP1(Tahun          ,Bulan         ,Area         ,NmArea         ,SkArea         ,Cabang         ,SkCabang         ,UnitUsaha         ,SkUUsa         ,KetUUsa         ,KdUker         ,SkUker         ,Glng         ,SkGlng         ,KJab         ,SkKjab         ,Jaba         ,SkJaba         ,Klas         ,SkKlas         ,Nip     ,FlagDppt         ,NomFormat         ,Nama         ,JnsKlm         ,StsMed         ,KdBank         ,AccHolder         ,AccNumber         ,KetBank         ,JnsPjk         ,FlagTHR         ,Piutang         ,Lemburan         ,NoUrut    ,TpFormat1,GrossIncome ,NilGRoss,TpFormat2,Deduction            ,NilDeduc    ,KetTakeHomePAy,NilTakeHomePAy)
                                VALUES(l_OLDTEMPTahun,l_OLDTEMPBulan,l_OLDTEMPArea,l_OLDTEMPNmArea,l_OLDTEMPSkArea,l_OLDTEMPCabang,l_OLDTEMPSkCabang,l_OLDTEMPUnitUsaha,l_OLDTEMPSkUUsa,l_OLDTEMPKetUUsa,l_OLDTEMPKdUker,l_OLDTEMPSkUker,l_OLDTEMPGlng,l_OLDTEMPSkGlng,l_OLDTEMPKJab,l_OLDTEMPSkKjab,l_OLDTEMPJaba,l_OLDTEMPSkJaba,l_OLDTEMPKlas,l_OLDTEMPSkKlas,l_OLDNip,l_OLDTEMPFlagDppt,l_OLDTEMPNomFormat,l_OLDTEMPNama,l_OLDTEMPJnsKlm,l_OLDTEMPStsMed,l_OLDTEMPKdBank,l_OLDTEMPAccHolder,l_OLDTEMPAccNumber,l_OLDTEMPKetBank,l_OLDTEMPJnsPjk,l_OLDTEMPFlagTHR,l_OLDTEMPPiutang,l_OLDTEMPLemburan,l_OLDNoPot,' '      ,' '         , 0      ,'J'      ,'** Total DEDUCTION ',l_OLDTotPot ,'TAKE HOME PAY'        ,0);
        --                      VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,l_TEMPUnitUsaha,l_TEMPSkUUsa,l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,l_TEMPKlas,l_TEMPSkKlas,l_Nip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoPot,' '      ,' '         , 0       ,'J'      ,'** Total DEDUCTION ',l_TotPot ,'TAKE HOME PAY'        ,0) 

             END IF; 
             -- 
           END IF; 
         -- 
	   l_NIP     :=l_TEMPNip;
	   l_NoUrut  :=1;
	   l_NoPend  :=1;
	   l_NoPot   :=1;
	   l_TotPend :=0;
	   l_TotPot  :=0;
         --  
    END IF; 
    --*
    --  
    IF l_FlgDpPt<>l_TempFlagDppt THEN 
         --
         IF l_FlgDpPt<>' ' THEN 
              --
              IF l_FlgDpPt='D' AND l_NoUrut<>1 THEN 
	          IF (SELECT COUNT(tahun) FROM l_TEMP1 WHERE NIP=l_TEMPNIP AND NoUrut=l_NoPend) >0 THEN
	               UPDATE l_TEMP1
	               SET TpFormat1  ='J',
		           GrossIncome='** Total GROSS INCOME  ',
		           NilGRoss   =l_TotPend
	               WHERE NIP=l_TEMPNIP AND NoUrut=l_NoPend;
	          ELSE  
	               INSERT INTO l_TEMP1(Tahun      ,Bulan      ,Area      ,NmArea      ,SkArea      ,Cabang      ,SkCabang      ,UnitUsaha      ,SkUUsa      ,KetUUsa      ,KdUker      ,SkUker      ,Glng      ,SkGlng      ,KJab      ,SkKjab      ,Jaba      ,SkJaba      ,Klas      ,SkKlas      ,Nip      ,FlagDppt      ,NomFormat      ,Nama      ,JnsKlm      ,StsMed      ,KdBank      ,AccHolder      ,AccNumber      ,KetBank      ,JnsPjk      ,FlagTHR      ,Piutang      ,Lemburan      ,NoUrut  ,TpFormat1,GrossIncome             ,NilGRoss ,TpFormat2,Deduction,NilDeduc,KetTakeHomePAy,NilTakeHomePAy)
	                            VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPNmArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,l_TEMPUnitUsaha,l_TEMPSkUUsa,l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,l_TEMPKlas,l_TEMPSkKlas,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoPend,'J'      ,'** Total GROSS INCOME' ,l_TotPend,' '      ,' '      ,0       ,'    '        ,0); 
	          END IF; 
                  -- 
                  --SELECT l_NoPend=l_NoPend+1
                  --
              END IF;   
              --
         END IF; 
         --*
         l_FlgDpPt := l_TempFlagDppt; 
    END IF; 
    --*
    --
    IF l_NoUrut=1 THEN 
         INSERT INTO l_TEMP1(Tahun     ,Bulan      ,Area      ,NmArea	   ,SkArea      ,Cabang      ,SkCabang      ,UnitUsaha      ,SkUUsa      ,KetUUsa      ,KdUker      ,SkUker      ,Glng      ,SkGlng      ,KJab      ,SkKjab      ,Jaba      ,SkJaba      ,Klas      ,SkKlas      ,Nip      ,FlagDppt      ,NomFormat      ,Nama      ,JnsKlm      ,StsMed      ,KdBank      ,AccHolder      ,AccNumber      ,KetBank      ,JnsPjk      ,FlagTHR      ,Piutang      ,Lemburan      ,NoUrut  ,TpFormat1,GrossIncome     ,NilGRoss,TpFormat2,Deduction    ,NilDeduc,KetTakeHomePAy   ,NilTakeHomePAy)
                     VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPNmArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,l_TEMPUnitUsaha,l_TEMPSkUUsa,l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,l_TEMPKlas,l_TEMPSkKlas,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoUrut,'T'      ,'GROSS INCOME :',0       ,'T'      ,'DEDUCTION :',0       ,'TAKE HOME PAY  ',0); 
         --
         INSERT INTO l_TEMP1(Tahun     ,Bulan      ,Area      ,NmArea	   ,SkArea      ,Cabang      ,SkCabang      ,UnitUsaha      ,SkUUsa      ,KetUUsa      ,KdUker      ,SkUker      ,Glng      ,SkGlng      ,KJab      ,SkKjab      ,Jaba      ,SkJaba      ,Klas      ,SkKlas      ,Nip      ,FlagDppt      ,NomFormat      ,Nama      ,JnsKlm      ,StsMed      ,KdBank      ,AccHolder      ,AccNumber      ,KetBank      ,JnsPjk      ,FlagTHR      ,Piutang      ,Lemburan      ,NoUrut  ,TpFormat1,GrossIncome     ,NilGRoss ,TpFormat2,Deduction    ,NilDeduc,KetTakeHomePAy ,NilTakeHomePAy)
                     VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPNmArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,l_TEMPUnitUsaha,l_TEMPSkUUsa,l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,l_TEMPKlas,l_TEMPSkKlas,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoUrut,'T'      ,'     '         ,0        ,'T'      ,' '          ,0       ,' '            ,0) ;
         --
         l_NoPend := l_NoPend+1;
	 l_NoPot  := l_NoPot+1;
         --
    END IF; 
    --*
    --
    IF l_TempFlagDppt='D' THEN 
          IF (SELECT COUNT(tahun) FROM l_TEMP1 WHERE NIP=l_TEMPNIP AND NoUrut=l_NoPend) > 0 THEN 
               UPDATE l_TEMP1
               SET TpFormat1  ='D',
	           GrossIncome=l_TempKeterangan,
	           NilGRoss   =l_TempNilai
               WHERE NIP=l_TEMPNIP AND NoUrut=l_NoPend;
          ELSE  
               INSERT INTO l_TEMP1(Tahun     ,Bulan      ,Area      ,NmArea	 ,SkArea      ,Cabang      ,SkCabang      ,UnitUsaha      ,SkUUsa      ,KetUUsa      ,KdUker      ,SkUker      ,Glng      ,SkGlng      ,KJab      ,SkKjab      ,Jaba      ,SkJaba      ,Klas      ,SkKlas      ,Nip      ,FlagDppt      ,NomFormat      ,Nama      ,JnsKlm      ,StsMed      ,KdBank      ,AccHolder      ,AccNumber      ,KetBank      ,JnsPjk      ,FlagTHR      ,Piutang      ,Lemburan      ,NoUrut  ,TpFormat1,GrossIncome     ,NilGRoss   ,TpFormat2,Deduction,NilDeduc,KetTakeHomePAy,NilTakeHomePAy)
                           VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPNmArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,l_TEMPUnitUsaha,l_TEMPSkUUsa,l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,l_TEMPKlas,l_TEMPSkKlas,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoPend,'D'      ,l_TempKeterangan,l_TempNilai,' '      ,' '      ,0       ,'    '        ,0);

          END IF; 
          --* 
          l_NoPend  := l_NoPend+1;
	  l_TotPend := l_TotPend+l_TempNilai;
          --
   ELSE -- Jika Flag Potongan 
          IF (SELECT COUNT(tahun) FROM l_TEMP1 WHERE NIP=l_TEMPNIP AND NoUrut=l_NoPot) > 0 THEN 
               UPDATE l_TEMP1
               SET TpFormat2  ='D',
	           Deduction  =l_TempKeterangan,
   	           NilDeduc   =l_TempNilai
               WHERE NIP=l_TEMPNIP AND NoUrut=l_NoPot;
          ELSE  
               INSERT INTO l_TEMP1(Tahun     ,Bulan      ,Area      ,NmArea	 ,SkArea      ,Cabang      ,SkCabang      ,UnitUsaha      ,SkUUsa      ,KetUUsa      ,KdUker      ,SkUker      ,Glng      ,SkGlng      ,KJab      ,SkKjab      ,Jaba      ,SkJaba      ,Klas      ,SkKlas      ,Nip      ,FlagDppt      ,NomFormat      ,Nama      ,JnsKlm      ,StsMed      ,KdBank      ,AccHolder      ,AccNumber      ,KetBank      ,JnsPjk      ,FlagTHR      ,Piutang      ,Lemburan      ,NoUrut ,TpFormat1,GrossIncome,NilGRoss,TpFormat2,Deduction       ,NilDeduc   ,KetTakeHomePAy,NilTakeHomePAy)
                           VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPNmArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,l_TEMPUnitUsaha,l_TEMPSkUUsa,l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,l_TEMPKlas,l_TEMPSkKlas,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoPot,' '      ,' '        ,0       ,'D'      ,l_TempKeterangan,l_TempNilai,'    '        ,0); 
          END IF; 
          --* 
         l_NoPot  := l_NoPot+1;
	 l_TotPot := l_TotPot+l_TempNilai;
    END IF; 
    --*
    --
    l_NoUrut := l_NoUrut+1;
    --- Tampung Posisi Sebelumnya
    l_OLDTEMPTahun:=l_TEMPTahun;
    l_OLDTEMPBulan:=l_TEMPBulan;
    l_OLDTEMPArea:=l_TEMPArea;
    l_OLDTEMPNmArea:=l_TEMPNmArea;
    l_OLDTEMPSkArea:=l_TEMPSkArea;
    l_OLDTEMPCabang:=l_TEMPCabang;
    l_OLDTEMPSkCabang:=l_TEMPSkCabang;
    l_OLDTEMPUnitUsaha:=l_TEMPUnitUsaha;
    l_OLDTEMPSkUUsa:=l_TEMPSkUUsa;
    l_OLDTEMPKetUUsa:=l_TEMPKetUUsa;
    l_OLDTEMPKdUker:=l_TEMPKdUker;
    l_OLDTEMPSkUker:=l_TEMPSkUker;
    l_OLDTEMPGlng:=l_TEMPGlng;
    l_OLDTEMPSkGlng:=l_TEMPSkGlng;
    l_OLDTEMPKJab:=l_TEMPKJab;
    l_OLDTEMPSkKjab:=l_TEMPSkKjab;
    l_OLDTEMPJaba:=l_TEMPJaba;
    l_OLDTEMPSkJaba:=l_TEMPSkJaba;
    l_OLDTEMPKlas:=l_TEMPKlas;
    l_OLDTEMPSkKlas:=l_TEMPSkKlas;
    l_OLDNip:=l_Nip;
    l_OLDTEMPFlagDppt:=l_TEMPFlagDppt;
    l_OLDTEMPNomFormat:=l_TEMPNomFormat;
    l_OLDTEMPNama:=l_TEMPNama;
    l_OLDTEMPJnsKlm:=l_TEMPJnsKlm;
    l_OLDTEMPStsMed:=l_TEMPStsMed;
    l_OLDTEMPKdBank:=l_TEMPKdBank;
    l_OLDTEMPAccHolder:=l_TEMPAccHolder;
    l_OLDTEMPAccNumber:=l_TEMPAccNumber;
    l_OLDTEMPKetBank:=l_TEMPKetBank;
    l_OLDTEMPJnsPjk:=l_TEMPJnsPjk;
    l_OLDTEMPFlagTHR:=l_TEMPFlagTHR;
    l_OLDTEMPPiutang:=l_TEMPPiutang;
    l_OLDTEMPLemburan:=l_TEMPLemburan;
    l_OLDNoPot:=l_NoPot;
    l_OLDTotPot:=l_TotPot;
    --
END LOOP;
CLOSE l_LOOP_TEMP;

--
-- isi Total Deduction tarahir
IF (SELECT COUNT(tahun) FROM l_TEMP1 WHERE NIP=l_NIP AND NoUrut=l_NoPot) > 0 THEN 
    UPDATE l_TEMP1
    SET TpFormat2='J',
        Deduction='** Total DEDUCTION',
        NilDeduc=l_TotPot,
        KetTakeHomePAy='TAKE HOME PAY'
--        NilTakeHomePAy=l_TotPend-l_TotPot	
    WHERE NIP=l_NIP AND NoUrut=l_NoPot;
ELSE  
    INSERT INTO l_TEMP1(Tahun     ,Bulan      ,Area      ,NmArea      ,SkArea      ,Cabang      ,SkCabang      ,UnitUsaha      ,SkUUsa      ,KetUUsa      ,KdUker      ,SkUker      ,Glng      ,SkGlng      ,KJab      ,SkKjab      ,Jaba      ,SkJaba      ,Klas      ,SkKlas      ,Nip  ,FlagDppt      ,NomFormat      ,Nama      ,JnsKlm      ,StsMed      ,KdBank      ,AccHolder      ,AccNumber      ,KetBank      ,JnsPjk      ,FlagTHR      ,Piutang      ,Lemburan      ,NoUrut ,TpFormat1,GrossIncome ,NilGRoss,TpFormat2,Deduction            ,NilDeduc ,KetTakeHomePAy ,NilTakeHomePAy)
                VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPNmArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,l_TEMPUnitUsaha,l_TEMPSkUUsa,l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,l_TEMPKlas,l_TEMPSkKlas,l_Nip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoPot,' '      ,' '         , 0      ,'J'      ,'** Total DEDUCTION ',l_TotPot ,'TAKE HOME PAY',0);
END IF; 
--*
----
--------------------- isi TakeHome Pay
UPDATE l_TEMP1 TMPU
SET NilTakeHomePAy=XXX.Take
FROM l_TEMP1 TMP
INNER JOIN 
(
SELECT NIP,fn_kpusat(S01.NIP,S01.EncTakeHomePay,l_MyPass) ::DECIMAL(15,2) AS Take
FROM S01HGAJ S01
WHERE TglPayr=l_Periode
)XXX
ON XXX.NIP=TMP.NIP
--
WHERE TMP.KetTakeHomePAy='TAKE HOME PAY' AND TMP.KetTakeHomePAy = TMPU.KetTakeHomePAy;
----

------------------ Mengisi data yang tdk punya total gross Income

INSERT INTO l_TEMP1(Tahun     ,Bulan     ,Area     ,NmArea	,SkArea     ,Cabang     ,SkCabang     ,UnitUsaha     ,SkUUsa     ,KetUUsa     ,KdUker     ,SkUker     ,Glng     ,SkGlng     ,KJab     ,SkKjab     ,Jaba     ,SkJaba     ,Klas     ,SkKlas     ,Nip   ,FlagDppt     ,NomFormat     ,Nama     ,JnsKlm     ,StsMed     ,KdBank     ,AccHolder     ,AccNumber     ,KetBank     ,JnsPjk     ,FlagTHR     ,Piutang  ,Lemburan     ,NoUrut  ,TpFormat1,GrossIncome             ,NilGRoss  ,TpFormat2,Deduction             ,NilDeduc,KetTakeHomePAy,NilTakeHomePAy)
SELECT      T.Tahun   ,T.Bulan   ,T.Area   ,T.NmArea	,T.SkArea   ,T.Cabang   ,T.SkCabang   ,T.UnitUsaha   ,T.SkUUsa   ,T.KetUUsa   ,T.KdUker   ,T.SkUker   ,T.Glng   ,T.SkGlng   ,T.KJab   ,T.SkKjab     ,Jaba   ,T.SkJaba   ,T.Klas   ,T.SkKlas   ,T.Nip ,T.FlagDppt   ,T.NomFormat   ,T.Nama   ,T.JnsKlm   ,T.StsMed   ,T.KdBank   ,T.AccHolder   ,T.AccNumber   ,T.KetBank   ,T.JnsPjk   ,FlagTHR     ,0        ,0            ,99      ,'J'      ,'** Total GROSS INCOME' ,GRS.NilGRoss,'T'      ,' '                   ,0       ,' ',0
FROM 
---
(--GRS
SELECT DISTINCT  T.NIP,N.NilGRoss 
FROM l_TEMP1 T
---
LEFT JOIN 
(--ADA
SELECT DISTINCT NIP
FROM l_TEMP1
WHERE GrossIncome='** Total GROSS INCOME'
)ADA
ON ADA.NIP=T.NIP
---
LEFT JOIN
(--N
SELECT NIP,SUM(S.NilGRoss) AS NilGRoss
FROM l_TEMP1 S
WHERE GrossIncome<>'** Total GROSS INCOME' AND FlagDppt='D'
GROUP BY NIP
)N
ON N.NIP=T.NIP
WHERE ADA.NIP IS NULL
)GRS
----
INNER JOIN 
(--T
SELECT * FROM l_TEMP1
WHERE GrossIncome='GROSS INCOME :'
)T
ON T.NIP=GRS.NIP;

-------------------------------------------------- HASIL AKHIR....
RETURN QUERY SELECT * FROM l_TEMP1
WHERE COALESCE(Tahun,0)<>0 AND COALESCE(Bulan,0)<>0; 

END;
$$ LANGUAGE plpgsql ;
	
/*
SELECT * FROM p_slikp1('2013-01-28'::DATE,' ','ZZZ','PHW-001','PHW-001',' ','ZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved');

SELECT * FROM S05PSTD WHERE NIP ='PHW-001' 
*/
