/****************************************
Name sprocs	: R_SLIKP2
Create by	: peggy 
Date		: 04-07-2008
Description	: Proses Untuk Report Slip Gaji 5,5 advance 
Call From	: Main Proc
Sub sprocs	: -
*****************************************/
DROP FUNCTION R_SLIKP2  (l_Periode 	DATE,
			l_UkerFr	VARCHAR(4),
			l_UkerTo	VARCHAR(4),
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
			l_CabFr		VARCHAR(4),
			l_CabTo		VARCHAR(4),
			l_MyPass	VARCHAR(128),
			l_U1		VARCHAR(1),
			l_U2		VARCHAR(1),
			l_U3		VARCHAR(1),
			l_U4		VARCHAR(1),
			l_U5		VARCHAR(1),
			l_U6		VARCHAR(1),
			l_U7		VARCHAR(1),
			l_U8		VARCHAR(1),
			l_U9		VARCHAR(1),
			l_U10		VARCHAR(1),
			l_usr_id 	INTEGER) ; 
---
CREATE FUNCTION R_SLIKP2 (l_Periode 	DATE,
			l_UkerFr	VARCHAR(4),
			l_UkerTo	VARCHAR(4),
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
			l_CabFr		VARCHAR(4),
			l_CabTo		VARCHAR(4),
			l_MyPass	VARCHAR(128),
			l_U1		VARCHAR(1),
			l_U2		VARCHAR(1),
			l_U3		VARCHAR(1),
			l_U4		VARCHAR(1),
			l_U5		VARCHAR(1),
			l_U6		VARCHAR(1),
			l_U7		VARCHAR(1),
			l_U8		VARCHAR(1),
			l_U9		VARCHAR(1),
			l_U10		VARCHAR(1),
			l_usr_id 	INTEGER)
--
RETURNS TABLE (OUTTahun			INT,
		OUTBulan		INT,
		OUTTeks1		VARCHAR(15),
		OUTKode1		VARCHAR(8),
		OUTSkKode1		VARCHAR(20),
		OUTTeks2		VARCHAR(15),
		OUTKode2		VARCHAR(8),
		OUTSkKode2		VARCHAR(20),
		OUTTeks3		VARCHAR(15),
		OUTKode3		VARCHAR(8),
		OUTSkKode3		VARCHAR(20),
		OUTTeks4		VARCHAR(15),
		OUTKode4		VARCHAR(8),
		OUTSkKode4		VARCHAR(20),
		OUTTeks5		VARCHAR(15),
		OUTKode5		VARCHAR(8),
		OUTSkKode5		VARCHAR(20),
		OUTTeks6		VARCHAR(15),
		OUTKode6		VARCHAR(8),
		OUTSkKode6		VARCHAR(20),
		OUTCabang		VARCHAR(4),
		OUTKdUker		VARCHAR(8),
		OUTNip			VARCHAR(10), 
		OUTFlagDppt		VARCHAR(1),
		OUTNomFormat	DECIMAL(3,0),
		OUTNama			VARCHAR(25), 
		OUTJnsKlm		VARCHAR(1),
		OUTStsMed		VARCHAR(2),
		OUTKdBank		VARCHAR(4),
		OUTAccHolder	VARCHAR(25),
		OUTAccNumber	VARCHAR(20),
		OUTKetBank		VARCHAR(40),
		OUTJnsPjk		VARCHAR(1),
		OUTFlagTHR		VARCHAR(1),
		OUTPiutang		Decimal(15,2),
		OUTLemburan		Decimal(5,2),
		OUTNoUrut		INT,
		OUTTpFormat1	VARCHAR(1),
		OUTGrossIncome	VARCHAR(40),
		OUTNilGRoss		DECIMAL(15,2),
		OUTTpFormat2	VARCHAR(1),
		OUTDeduction	VARCHAR(40),
		OUTNilDeduc		DECIMAL(15,2),
		OUTKetTakeHomePAy	VARCHAR(40),
		OUTNilTakeHomePAy	DECIMAL(15,2))  
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
	l_M01Keterangan	VARCHAR(20);
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
	l_GetLmbr		Decimal(5,2);
	l_TempTahun		INT;
	l_TempBulan		INT;
	l_TempArea		VARCHAR(4);
	l_TempNmArea	VARCHAR(20);
	l_TempSkArea	VARCHAR(20);
	l_TempCabang	VARCHAR(4);
	l_TempSkCabang	VARCHAR(20);
	l_TempUnitUsaha	VARCHAR(4);
	l_TempSkUUsa	VARCHAR(20);
	l_TempKetUUsa	VARCHAR(20);
	l_TempKdUker	VARCHAR(8);
	l_TempSkUker	VARCHAR(20); 
	l_TempGlng		VARCHAR(4); 
	l_TempSkGlng	VARCHAR(20); 
	l_TempKJab		VARCHAR(4); 
	l_TempSkKjab	VARCHAR(20); 
	l_TempJaba		VARCHAR(4); 
	l_TempSkJaba	VARCHAR(20); 
	l_TempKlas		VARCHAR(4); 
	l_TempSkKlas	VARCHAR(20); 
	l_TempNip		VARCHAR(10); 
	l_TempFlagDppt	VARCHAR(1);
	l_TempNomFormat	DECIMAL(3,0);
	l_TempNama		VARCHAR(25); 
	l_TempJnsKlm	VARCHAR(1);
	l_TempStsMed	VARCHAR(2);
	l_TempKdBank	VARCHAR(4);
	l_TempAccHolder	VARCHAR(25);
	l_TempAccNumber	VARCHAR(20);
	l_TempKetBank	VARCHAR(40);
	l_TempKeterangan    VARCHAR(20);
	l_TempNilai		DECIMAL(15,2);
	l_TempJnsPjk	VARCHAR(1);
	l_TempFlagTHR	VARCHAR(1);
	l_TempPiutang	Decimal(15,2);
	l_TempLemburan	Decimal(5,2);
	l_NIP			VARCHAR(10);
	l_M15NPWP		VARCHAR(20);
	l_FlgDpPt		VARCHAR(1);
	l_NoUrut		INT;
	l_LOOP_TEMP		REFCURSOR;
	l_LOOP_W11		REFCURSOR;
	l_NoPend		INT;
	l_NoPot			INT;
	l_TotPend		DECIMAL(15,2);
	l_TotPot		DECIMAL(15,2);
	l_GolFr         VARCHAR(4);
	l_GolTo         VARCHAR(4);
	l_S01TakeHomePay    DECIMAL(15,2);
	l_TEMPTakeHomePay 	DECIMAL(15,2);
	l_OLDTEMPTahun	INT;
	l_OLDTEMPBulan 	INT;	
	l_OLDTEMPArea	VARCHAR(4);
	l_OLDTEMPNmArea VARCHAR(20);
	l_OLDTEMPSkArea VARCHAR(20);
	l_OLDTEMPCabang VARCHAR(4);
	l_OLDTEMPSkCabang	VARCHAR(20);
	l_OLDTEMPUnitUsaha	VARCHAR(4);
	l_OLDTEMPSkUUsa	VARCHAR(20);
	l_OLDTEMPKetUUsa	VARCHAR(20);
	l_OLDTEMPKdUker	VARCHAR(8);
	l_OLDTEMPSkUker	VARCHAR(20);
	l_OLDTEMPGlng	VARCHAR(4);
	l_OLDTEMPSkGlng	VARCHAR(20);
	l_OLDTEMPKJab	VARCHAR(4);
	l_OLDTEMPSkKjab	VARCHAR(20);
	l_OLDTEMPJaba	VARCHAR(4);
	l_OLDTEMPSkJaba	VARCHAR(20);	
	l_OLDTEMPKlas	VARCHAR(4);
	l_OLDTEMPSkKlas	VARCHAR(20);	
	l_OLDNip		VARCHAR(10);
	l_OLDTEMPFlagDppt	VARCHAR(1);
	l_OLDTEMPNomFormat	DECIMAL(3,0);
	l_OLDTEMPNama	VARCHAR(25);
	l_OLDTEMPJnsKlm	VARCHAR(1);
	l_OLDTEMPStsMed	VARCHAR(2);
	l_OLDTEMPKdBank	VARCHAR(4);
	l_OLDTEMPAccHolder	VARCHAR(25);
	l_OLDTEMPAccNumber	VARCHAR(20);
	l_OLDTEMPKetBank	VARCHAR(40);
	l_OLDTEMPJnsPjk	VARCHAR(1);
	l_OLDTEMPFlagTHR	VARCHAR(1);
	l_OLDTEMPPiutang	DECIMAL(15,2);
	l_OLDTEMPLemburan	DECIMAL(5,2);
	l_OLDNoPot		INT;
	l_OLDTotPot		DECIMAL(15,2); 
	l_urut 			VARCHAR(20);
	l_Teks1			VARCHAR(15);
	l_Kode1			VARCHAR(8);
	l_SkKode1		VARCHAR(20);
	l_Teks2			VARCHAR(15);
	l_Kode2			VARCHAR(8);
	l_SkKode2		VARCHAR(20);
	l_Teks3			VARCHAR(15);
	l_Kode3			VARCHAR(8);
	l_SkKode3		VARCHAR(20);
	l_Teks4			VARCHAR(15);
	l_Kode4			VARCHAR(8);
	l_SkKode4		VARCHAR(20);
	l_Teks5			VARCHAR(15);
	l_Kode5			VARCHAR(8);
	l_SkKode5		VARCHAR(20);
	l_Teks6			VARCHAR(15);
	l_Kode6			VARCHAR(8);
	l_SkKode6		VARCHAR(20);
	l_Teks7			VARCHAR(15);
	l_Kode7			VARCHAR(8);
	l_SkKode7		VARCHAR(20);
	l_Teks8			VARCHAR(15);
	l_Kode8			VARCHAR(8);
	l_SkKode8		VARCHAR(20);
	l_Teks9			VARCHAR(15);
	l_Kode9			VARCHAR(8);
	l_SkKode9		VARCHAR(20);
	l_TEMPTeks1		VARCHAR(15);
	l_TEMPKode1		VARCHAR(8);
	l_TEMPSkKode1	VARCHAR(20);
	l_TEMPTeks2		VARCHAR(15);
	l_TEMPKode2		VARCHAR(8);
	l_TEMPSkKode2	VARCHAR(20);
	l_TEMPTeks3		VARCHAR(15);
	l_TEMPKode3		VARCHAR(8);
	l_TEMPSkKode3	VARCHAR(20);
	l_TEMPTeks4		VARCHAR(15);
	l_TEMPKode4		VARCHAR(8);
	l_TEMPSkKode4	VARCHAR(20);
	l_TEMPTeks5		VARCHAR(15);
	l_TEMPKode5		VARCHAR(8);
	l_TEMPSkKode5	VARCHAR(20);
	l_TEMPTeks6		VARCHAR(15);
	l_TEMPKode6		VARCHAR(8);
	l_TEMPSkKode6	VARCHAR(20);
	l_OLDTEMPTeks1	VARCHAR(15);
	l_OLDTEMPKode1	VARCHAR(8);
	l_OLDTEMPSkKode1    VARCHAR(20);
	l_OLDTEMPTeks2	VARCHAR(15);
	l_OLDTEMPKode2	VARCHAR(8);
	l_OLDTEMPSkKode2	VARCHAR(20);
	l_OLDTEMPTeks3	VARCHAR(15);
	l_OLDTEMPKode3	VARCHAR(8);
	l_OLDTEMPSkKode3	VARCHAR(20);
	l_OLDTEMPTeks4	VARCHAR(15);
	l_OLDTEMPKode4	VARCHAR(8);
	l_OLDTEMPSkKode4	VARCHAR(20);
	l_OLDTEMPTeks5	VARCHAR(15);
	l_OLDTEMPKode5	VARCHAR(8);
	l_OLDTEMPSkKode5	VARCHAR(20);
	l_OLDTEMPTeks6	VARCHAR(15);
	l_OLDTEMPKode6	VARCHAR(8);
	l_OLDTEMPSkKode6	VARCHAR(20);
--
BEGIN 
CREATE TEMP TABLE l_TEMP(Tahun		INT,
			Bulan		INT,
			Teks1		VARCHAR(15),
			Kode1		VARCHAR(8),
			SkKode1		VARCHAR(20),
			Teks2		VARCHAR(15),
			Kode2		VARCHAR(8),
			SkKode2		VARCHAR(20),
			Teks3		VARCHAR(15),
			Kode3		VARCHAR(8),
			SkKode3		VARCHAR(20),
			Teks4		VARCHAR(15),
			Kode4		VARCHAR(8),
			SkKode4		VARCHAR(20),
			Teks5		VARCHAR(15),
			Kode5		VARCHAR(8),
			SkKode5		VARCHAR(20),
			Teks6		VARCHAR(15),
			Kode6		VARCHAR(8),
			SkKode6		VARCHAR(20),
			Cabang		VARCHAR(4),
			KdUker		VARCHAR(8),
			Nip         VARCHAR(10), 
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
			Lemburan	Decimal(5,2))  ON COMMIT DROP ;      
	
CREATE TEMP TABLE l_TEMP1(Tahun		INT,
			Bulan		INT,
			Teks1		VARCHAR(15),
			Kode1		VARCHAR(8),
			SkKode1		VARCHAR(20),
			Teks2		VARCHAR(15),
			Kode2		VARCHAR(8),
			SkKode2		VARCHAR(20),
			Teks3		VARCHAR(15),
			Kode3		VARCHAR(8),
			SkKode3		VARCHAR(20),
			Teks4		VARCHAR(15),
			Kode4		VARCHAR(8),
			SkKode4		VARCHAR(20),
			Teks5		VARCHAR(15),
			Kode5		VARCHAR(8),
			SkKode5		VARCHAR(20),
			Teks6		VARCHAR(15),
			Kode6		VARCHAR(8),
			SkKode6		VARCHAR(20),
			Cabang		VARCHAR(4),
			KdUker		VARCHAR(8),
			Nip         VARCHAR(10), 
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
l_Urut := l_U1||l_U2||l_U3||l_U4||l_U5||l_U6||l_U7||l_U8||l_U9||l_U10;
--
IF position('1' in l_Urut) <> 0 THEN 
 	l_Teks1 := CASE WHEN position('1' in l_Urut) = 1 THEN 'Cabang/PT' 
			 WHEN position('1' in l_Urut) = 2 THEN 'Unit Usaha' 
			 WHEN position('1' in l_Urut) = 3 THEN 'Unit Kerja' 
			 WHEN position('1' in l_Urut) = 4 THEN 'Area'
			 WHEN position('1' in l_Urut) = 5 THEN 'Jabatan'
			 WHEN position('1' in l_Urut) = 6 THEN 'Golongan'
			 WHEN position('1' in l_Urut) = 7 THEN 'Kel. Jabatan' 
			 WHEN position('1' in l_Urut) = 8 THEN 'Klasifikasi' 
			 WHEN position('1' in l_Urut) = 9 THEN 'Status Pajak' 
			 WHEN position('1' in l_Urut) = 10 THEN 'NPWP' 
			 ELSE ' ' 
			END; 
END IF; 

IF position('2' in l_Urut)  <> 0 THEN 
	l_Teks2 := CASE WHEN position('2' in l_Urut) = 1 THEN 'Cabang/PT' 
			 WHEN position('2' in l_Urut) = 2 THEN 'Unit Usaha'
			 WHEN position('2' in l_Urut) = 3 THEN 'Unit Kerja'
			 WHEN position('2' in l_Urut) = 4 THEN 'Area'
			 WHEN position('2' in l_Urut) = 5 THEN 'Jabatan'
			 WHEN position('2' in l_Urut) = 6 THEN 'Golongan'
			 WHEN position('2' in l_Urut) = 7 THEN 'Kel. Jabatan' 
			 WHEN position('2' in l_Urut) = 8 THEN 'Klasifikasi' 
			 WHEN position('2' in l_Urut) = 9 THEN 'Status Pajak' 
			 WHEN position('2' in l_Urut) = 10 THEN 'NPWP' 
			 ELSE ' ' 
			END;
END IF;  

IF position('3' in l_Urut)  <> 0 THEN 
	l_Teks3 := CASE WHEN position('3' in l_Urut) = 1 THEN 'Cabang/PT'
			 WHEN position('3' in l_Urut) = 2 THEN 'Unit Usaha'
			 WHEN position('3' in l_Urut) = 3 THEN 'Unit Kerja'
			 WHEN position('3' in l_Urut) = 4 THEN 'Area'
			 WHEN position('3' in l_Urut) = 5 THEN 'Jabatan'
			 WHEN position('3' in l_Urut) = 6 THEN 'Golongan'
			 WHEN position('3' in l_Urut) = 7 THEN 'Kel. Jabatan' 
			 WHEN position('3' in l_Urut) = 8 THEN 'Klasifikasi' 
			 WHEN position('3' in l_Urut) = 9 THEN 'Status Pajak' 
			 WHEN position('3' in l_Urut) = 10 THEN 'NPWP' 
			 ELSE ' ' 
			END;
END IF; 

IF position('4' in l_Urut) <> 0 THEN 
	l_Teks4 := CASE WHEN position('4' in l_Urut) = 1 THEN 'Cabang/PT'
			 WHEN position('4' in l_Urut) = 2 THEN 'Unit Usaha' 
			 WHEN position('4' in l_Urut) = 3 THEN 'Unit Kerja' 
			 WHEN position('4' in l_Urut) = 4 THEN 'Area'
			 WHEN position('4' in l_Urut) = 5 THEN 'Jabatan'
			 WHEN position('4' in l_Urut) = 6 THEN 'Golongan'
			 WHEN position('4' in l_Urut) = 7 THEN 'Kel. Jabatan' 
			 WHEN position('4' in l_Urut) = 8 THEN 'Klasifikasi' 
			 WHEN position('4' in l_Urut) = 9 THEN 'Status Pajak' 
			 WHEN position('4' in l_Urut) = 10 THEN 'NPWP' 
			 ELSE ' ' 
			END;
END IF;

IF position('5' in l_Urut) <> 0 THEN
	l_Teks5 := CASE WHEN position('5' in l_Urut) = 1 THEN 'Cabang/PT'
			 WHEN position('5' in l_Urut) = 2 THEN 'Unit Usaha'
			 WHEN position('5' in l_Urut) = 3 THEN 'Unit Kerja'
			 WHEN position('5' in l_Urut) = 4 THEN 'Area'
			 WHEN position('5' in l_Urut) = 5 THEN 'Jabatan'
			 WHEN position('5' in l_Urut) = 6 THEN 'Golongan'
			 WHEN position('5' in l_Urut) = 7 THEN 'Kel. Jabatan' 
			 WHEN position('5' in l_Urut) = 8 THEN 'Klasifikasi' 
			 WHEN position('5' in l_Urut) = 9 THEN 'Status Pajak' 
			 WHEN position('5' in l_Urut) = 10 THEN 'NPWP' 
			 ELSE ' ' 
			END;
END IF;

IF position('6' in l_Urut) <> 0 THEN 
	l_Teks6 := CASE WHEN position('6' in l_Urut) = 1 THEN 'Cabang/PT'
			 WHEN position('6' in l_Urut) = 2 THEN 'Unit Usaha'
			 WHEN position('6' in l_Urut) = 3 THEN 'Unit Kerja'
			 WHEN position('6' in l_Urut) = 4 THEN 'Area'
			 WHEN position('6' in l_Urut) = 5 THEN 'Jabatan'
			 WHEN position('6' in l_Urut) = 6 THEN 'Golongan'
			 WHEN position('6' in l_Urut) = 7 THEN 'Kel. Jabatan' 
			 WHEN position('6' in l_Urut) = 8 THEN 'Klasifikasi' 
			 WHEN position('6' in l_Urut) = 9 THEN 'Status Pajak' 
			 WHEN position('6' in l_Urut) = 10 THEN 'NPWP' 
			 ELSE ' '
			 END;
END IF;

IF position('7' in l_Urut) <> 0 THEN
	l_Teks7 := CASE WHEN position('7' in l_Urut) = 1 THEN 'Cabang/PT'
			 WHEN position('7' in l_Urut) = 2 THEN 'Unit Usaha'
			 WHEN position('7' in l_Urut) = 3 THEN 'Unit Kerja'
			 WHEN position('7' in l_Urut) = 4 THEN 'Area'
			 WHEN position('7' in l_Urut) = 5 THEN 'Jabatan'
			 WHEN position('7' in l_Urut) = 6 THEN 'Golongan'
			 WHEN position('7' in l_Urut) = 7 THEN 'Kel. Jabatan' 
			 WHEN position('7' in l_Urut) = 8 THEN 'Klasifikasi' 
			 WHEN position('7' in l_Urut) = 9 THEN 'Status Pajak' 
			 WHEN position('7' in l_Urut) = 10 THEN 'NPWP' 
			 ELSE ' ' 
			 END;
END IF;

IF position('8' in l_Urut) <> 0 THEN 
	l_Teks8 := CASE WHEN position('8' in l_Urut) = 1 THEN 'Cabang/PT'
			 WHEN position('8' in l_Urut) = 2 THEN 'Unit Usaha'
			 WHEN position('8' in l_Urut) = 3 THEN 'Unit Kerja'
			 WHEN position('8' in l_Urut) = 4 THEN 'Area'
			 WHEN position('8' in l_Urut) = 5 THEN 'Jabatan'
			 WHEN position('8' in l_Urut) = 6 THEN 'Golongan'
			 WHEN position('8' in l_Urut) = 7 THEN 'Kel. Jabatan' 
			 WHEN position('8' in l_Urut) = 8 THEN 'Klasifikasi' 
			 WHEN position('8' in l_Urut) = 9 THEN 'Status Pajak' 
			 WHEN position('8' in l_Urut) = 10 THEN 'NPWP' 
			 ELSE ' ' 
			END;
END IF; 

IF position('9' in l_Urut) <> 0 THEN
	l_Teks9 := CASE WHEN position('9' in l_Urut) = 1 THEN 'Cabang/PT'
			 WHEN position('9' in l_Urut) = 2 THEN 'Unit Usaha'
			 WHEN position('9' in l_Urut) = 3 THEN 'Unit Kerja'
			 WHEN position('9' in l_Urut) = 4 THEN 'Area'
			 WHEN position('9' in l_Urut) = 5 THEN 'Jabatan'
			 WHEN position('9' in l_Urut) = 6 THEN 'Golongan'
			 WHEN position('9' in l_Urut) = 7 THEN 'Kel. Jabatan' 
			 WHEN position('9' in l_Urut) = 8 THEN 'Klasifikasi' 
			 WHEN position('9' in l_Urut) = 9 THEN 'Status Pajak' 
			 WHEN position('9' in l_Urut) = 10 THEN 'NPWP' 
			 ELSE ' ' 
			END;
END IF; 

-- kalo ada teks yang blank, 
IF position('8' in l_Urut) = 0 THEN 
	l_teks8 := l_teks9; 
	l_teks9 := ' ';
END IF; 

IF position('7' in l_Urut)  = 0 THEN 
	l_teks7 := l_teks8; 
	l_teks8 := l_teks9; 
	l_teks9 := ' ';
END IF;

IF position('6' in l_Urut)  = 0 THEN 
	l_teks6 := l_teks7; 
	l_teks7 := l_teks8; 
	l_teks8 := l_teks9;
	l_teks9 := ' ';
END IF; 

IF position('5' in l_Urut)  = 0 THEN 
	l_Teks5 := l_Teks6; 
	l_teks6 := l_teks7; 
	l_teks7 := l_teks8; 
	l_teks8 := l_teks9;
	l_teks9 := ' ';
END IF;

IF position('4' in l_Urut)  = 0 THEN 
	l_Teks4 := l_Teks5; 
	l_Teks5 := l_Teks6; 
	l_teks6 := l_teks7; 
	l_teks7 := l_teks8;
	l_teks8 := l_teks9; 
	l_teks9 := ' ';  
END IF; 

IF position('3' in l_Urut) = 0 THEN 
	l_Teks3 := l_Teks4;
	l_Teks4 := l_Teks5;
	l_Teks5 := l_Teks6;
	l_teks6 := l_teks7;
	l_teks7 := l_teks8; 
	l_teks8 := l_teks9; 
	l_teks9 := ' ';
END IF;

IF position('2' in l_Urut) = 0 THEN 
	l_Teks2 := l_Teks3; 
	l_Teks3 := l_Teks4;
	l_Teks4 := l_Teks5;
	l_Teks5 := l_Teks6;
	l_teks6 := l_teks7;
	l_teks7 := l_teks8;
	l_teks8 := l_teks9; 
	l_teks9 := ' ';
END IF; 

IF position('1' in l_Urut) = 0 THEN 
	l_Teks1 := l_Teks2;
	l_Teks2 := l_Teks3; 
	l_Teks3 := l_Teks4;
	l_Teks4 := l_Teks5;
	l_Teks5 := l_Teks6;
	l_teks6 := l_teks7;
	l_teks7 := l_teks8;
	l_teks8 := l_teks9;
	l_teks9 := ' ';
END IF;  

-- Mulai Cursor data


OPEN l_LOOP_W11 FOR 
SELECT  S02.TglPayr,S01.kdArea,S01.kdCaba,S01.kdUUsa,S01.kdUker,S01.kdGlng,S01.kdKJab,
	S01.kdJaba,S01.kdKlas,S01.NIP,S01.Nama,S01.JnsKlmn,S01.StsPjk,S02.FlgDpPt,
	M19.NomFormat,M01.Keterangan,M01.Singkatan,M08.SkCaba,M02.Singkatan,M17.Singkatan,M12.Singkatan,
	M06.Singkatan,M04.Singkatan,M10.Singkatan,M19.Keterangan,M02.Keterangan,
	fn_kpusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2),S01.BankRef,S01.AccHolder,S01.RkBnk,M14.Keterangan,S01.JnsPajak,S01.FlagTHR,
	COALESCE(getpiut(S01.NIP,l_Periode),0), GetLembur(S01.NIP,l_Periode),
 	RIGHT('00000000000000'||RTRIM(M15.NPWP),15) as NPWP
-- 	NPWP=SUBSTRING(RIGHT('00000000000000'+RTRIM(M15.NPWP),15),1,2)+'.'+
-- 		 SUBSTRING(RIGHT('00000000000000'+RTRIM(M15.NPWP),15),3,3)+'.'+
-- 		 SUBSTRING(RIGHT('00000000000000'+RTRIM(M15.NPWP),15),6,3)+'.'+	    
-- 		 SUBSTRING(RIGHT('00000000000000'+RTRIM(M15.NPWP),15),9,1)+'-'+
-- 		 SUBSTRING(RIGHT('00000000000000'+RTRIM(M15.NPWP),15),10,3)+'.'+	    
-- 		 SUBSTRING(RIGHT('00000000000000'+RTRIM(M15.NPWP),15),13,3)
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
INNER JOIN M15PEGA M15 ON M15.NIP=S01.NIP
--INNER JOIN V_SECLOGIN VSL ON M15.Kdglng=VSL.Kdgolg AND M15.Kdcaba=VSL.Kdcaba
WHERE M19.TipeLap='1' AND S01.TglPayr=l_Periode AND
      (S01.kdCaba BETWEEN l_CabFr AND l_CabTo) AND
      (S01.kdUker BETWEEN l_UkerFr AND l_UkerTo) AND
      (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) ;  
      --AND VSL.USR_ID=l_USR_ID;  
      --      
<<l_LOOP_W11>> 
LOOP 
FETCH l_LOOP_W11 
INTO    l_S02TglPayr,l_S01kdArea,l_S01kdCaba,l_S01kdUUsa,l_S01kdUker,l_S01kdGlng,l_S01kdKJab,
	l_S01kdJaba,l_S01kdKlas,l_S01NIP,l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S02FlgDpPt,
	l_M19NomFormat,l_M01Keterangan,l_M01Singkatan,l_M08SkCaba,l_M02Singkatan,l_M17Singkatan,l_M12Singkatan,
	l_M06Singkatan,l_M04Singkatan,l_M10Singkatan,l_M19Keterangan,l_M02Keterangan,
	l_S02Nilai,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,l_S01JnsPajak,l_S01FlagTHR,
	l_GetPiut,l_GetLmbr,l_M15NPWP; 

     IF NOT FOUND THEN
        EXIT ;
     END IF;
     --
     IF (SELECT COUNT(NIP) FROM l_TEMP 
            WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr) AND Bulan=EXTRACT(MONTH FROM l_S02TglPayr) AND
                Nip=l_S01NIP AND FlagDppt=l_S02FlgDpPt AND NomFormat=l_M19NomFormat) > 0 THEN 
                 --
	UPDATE l_TEMP
	SET Nilai=COALESCE(Nilai,0)+l_S02Nilai
	--	
	WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr) AND Bulan=EXTRACT(MONTH FROM l_S02TglPayr) AND
	  Nip=l_S01NIP AND FlagDppt=l_S02FlgDpPt AND NomFormat=l_M19NomFormat; 
         --
     ELSE
	l_Kode1   := ' '; l_Kode2   := ' '; l_Kode3   := ' '; l_Kode4   := ' '; 
	l_Kode5   := ' '; l_Kode6   := ' '; l_Kode7   := ' '; l_Kode8   := ' '; 
	l_SkKode1 := ' '; l_SkKode2 := ' '; l_SkKode3 := ' '; l_SkKode4 := ' '; 
	l_SkKode5 := ' '; l_SkKode6 := ' '; l_SkKode7 := ' '; l_SkKode8 := ' ';

	IF POSITION('1' IN l_Urut) <> 0 THEN 
		l_Kode1 := CASE WHEN position('1' in l_Urut) = 1 THEN l_S01KdCaba
				 WHEN position('1' in l_Urut) = 2 THEN l_S01KdUUsa 
				 WHEN position('1' in l_Urut) = 3 THEN l_S01KdUKer
				 WHEN position('1' in l_Urut) = 4 THEN l_S01KdArea
				 WHEN position('1' in l_Urut) = 5 THEN l_S01KdJaba
				 WHEN position('1' in l_Urut) = 6 THEN l_S01KdGlng
				 WHEN position('1' in l_Urut) = 7 THEN l_S01KdKJab
				 WHEN position('1' in l_Urut) = 8 THEN l_S01KdKlas
				 WHEN position('1' in l_Urut) = 9 THEN l_S01StsPjk
				 WHEN position('1' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,1,8)
				 ELSE ' '
				END;
		l_SkKode1 := CASE WHEN position('1' in l_Urut) = 1 THEN l_M08SkCaba
				 WHEN position('1' in l_Urut) = 2 THEN l_M02Singkatan 
				 WHEN position('1' in l_Urut) = 3 THEN l_M17Singkatan
				 WHEN position('1' in l_Urut) = 4 THEN l_M01Singkatan
				 WHEN position('1' in l_Urut) = 5 THEN l_M04Singkatan
				 WHEN position('1' in l_Urut) = 6 THEN l_M12Singkatan
				 WHEN position('1' in l_Urut) = 7 THEN l_M06Singkatan
				 WHEN position('1' in l_Urut) = 8 THEN l_M10Singkatan
				 WHEN position('1' in l_Urut) = 9 THEN ' '
				 WHEN position('1' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,9,11)
				 ELSE ' '
				END;
	END IF; 
	IF position('2' in l_Urut) <> 0 THEN 
		l_Kode2 := CASE WHEN position('2' in l_Urut) = 1 THEN l_S01KdCaba
				 WHEN position('2' in l_Urut) = 2 THEN l_S01KdUUsa 
				 WHEN position('2' in l_Urut) = 3 THEN l_S01KdUKer
				 WHEN position('2' in l_Urut) = 4 THEN l_S01KdArea
				 WHEN position('2' in l_Urut) = 5 THEN l_S01KdJaba
				 WHEN position('2' in l_Urut) = 6 THEN l_S01KdGlng
				 WHEN position('2' in l_Urut) = 7 THEN l_S01KdKJab
				 WHEN position('2' in l_Urut) = 8 THEN l_S01KdKlas
				 WHEN position('2' in l_Urut) = 9 THEN l_S01StsPjk
				 WHEN position('2' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,1,8)
				 ELSE ' '
				END;
		l_SkKode2 := CASE WHEN position('2' in l_Urut) = 1 THEN l_M08SkCaba
				 WHEN position('2' in l_Urut) = 2 THEN l_M02Singkatan 
				 WHEN position('2' in l_Urut) = 3 THEN l_M17Singkatan
				 WHEN position('2' in l_Urut) = 4 THEN l_M01Singkatan
				 WHEN position('2' in l_Urut) = 5 THEN l_M04Singkatan
				 WHEN position('2' in l_Urut) = 6 THEN l_M12Singkatan
				 WHEN position('2' in l_Urut) = 7 THEN l_M06Singkatan
				 WHEN position('2' in l_Urut) = 8 THEN l_M10Singkatan
				 WHEN position('2' in l_Urut) = 9 THEN ' '
				 WHEN position('2' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,9,11)
				 ELSE ' '
				END; 
	END IF;
	IF position('3' in l_Urut) <> 0 THEN
		l_Kode3 := CASE WHEN position('3' in l_Urut) = 1 THEN l_S01KdCaba
					 WHEN position('3' in l_Urut) = 2 THEN l_S01KdUUsa 
					 WHEN position('3' in l_Urut) = 3 THEN l_S01KdUKer
					 WHEN position('3' in l_Urut) = 4 THEN l_S01KdArea
					 WHEN position('3' in l_Urut) = 5 THEN l_S01KdJaba
					 WHEN position('3' in l_Urut) = 6 THEN l_S01KdGlng
					 WHEN position('3' in l_Urut) = 7 THEN l_S01KdKJab
					 WHEN position('3' in l_Urut) = 8 THEN l_S01KdKlas
					 WHEN position('3' in l_Urut) = 9 THEN l_S01StsPjk
					 WHEN position('3' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,1,8)
					 ELSE ' '
					END;
		l_SkKode3 := CASE WHEN position('3' in l_Urut) = 1 THEN l_M08SkCaba
					 WHEN position('3' in l_Urut) = 2 THEN l_M02Singkatan 
					 WHEN position('3' in l_Urut) = 3 THEN l_M17Singkatan
					 WHEN position('3' in l_Urut) = 4 THEN l_M01Singkatan
					 WHEN position('3' in l_Urut) = 5 THEN l_M04Singkatan
					 WHEN position('3' in l_Urut) = 6 THEN l_M12Singkatan
					 WHEN position('3' in l_Urut) = 7 THEN l_M06Singkatan
					 WHEN position('3' in l_Urut) = 8 THEN l_M10Singkatan
					 WHEN position('3' in l_Urut) = 9 THEN ' '
					 WHEN position('3' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,9,11)
					 ELSE ' '
					END;
	END IF;
	IF position('4' in l_Urut) <> 0 THEN 
		l_Kode4 := CASE WHEN position('4' in l_Urut) = 1 THEN l_S01KdCaba
					 WHEN position('4' in l_Urut) = 2 THEN l_S01KdUUsa 
					 WHEN position('4' in l_Urut) = 3 THEN l_S01KdUKer
					 WHEN position('4' in l_Urut) = 4 THEN l_S01KdArea
					 WHEN position('4' in l_Urut) = 5 THEN l_S01KdJaba
					 WHEN position('4' in l_Urut) = 6 THEN l_S01KdGlng
					 WHEN position('4' in l_Urut) = 7 THEN l_S01KdKJab
					 WHEN position('4' in l_Urut) = 8 THEN l_S01KdKlas
					 WHEN position('4' in l_Urut) = 9 THEN l_S01StsPjk
					 WHEN position('4' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,1,8)
					 ELSE ' '
					END;
		l_SkKode4 := CASE WHEN position('4' in l_Urut) = 1 THEN l_M08SkCaba
					 WHEN position('4' in l_Urut) = 2 THEN l_M02Singkatan 
					 WHEN position('4' in l_Urut) = 3 THEN l_M17Singkatan
					 WHEN position('4' in l_Urut) = 4 THEN l_M01Singkatan
					 WHEN position('4' in l_Urut) = 5 THEN l_M04Singkatan
					 WHEN position('4' in l_Urut) = 6 THEN l_M12Singkatan
					 WHEN position('4' in l_Urut) = 7 THEN l_M06Singkatan
					 WHEN position('4' in l_Urut) = 8 THEN l_M10Singkatan
					 WHEN position('4' in l_Urut) = 9 THEN ' '
					 WHEN position('4' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,9,11)
					 ELSE ' '
					END;
	END IF;  
	IF position('5' in l_Urut) <> 0 THEN 
		l_Kode5 := CASE WHEN position('5' in l_Urut) = 1 THEN l_S01KdCaba
				 WHEN position('5' in l_Urut) = 2 THEN l_S01KdUUsa 
				 WHEN position('5' in l_Urut) = 3 THEN l_S01KdUKer
				 WHEN position('5' in l_Urut) = 4 THEN l_S01KdArea
				 WHEN position('5' in l_Urut) = 5 THEN l_S01KdJaba
				 WHEN position('5' in l_Urut) = 6 THEN l_S01KdGlng
				 WHEN position('5' in l_Urut) = 7 THEN l_S01KdKJab
				 WHEN position('5' in l_Urut) = 8 THEN l_S01KdKlas
				 WHEN position('5' in l_Urut) = 9 THEN l_S01StsPjk
				 WHEN position('5' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,1,8)
				 ELSE ' '
				END;
		l_SkKode5 := CASE WHEN position('5' in l_Urut) = 1 THEN l_M08SkCaba
				 WHEN position('5' in l_Urut) = 2 THEN l_M02Singkatan 
				 WHEN position('5' in l_Urut) = 3 THEN l_M17Singkatan
				 WHEN position('5' in l_Urut) = 4 THEN l_M01Singkatan
				 WHEN position('5' in l_Urut) = 5 THEN l_M04Singkatan
				 WHEN position('5' in l_Urut) = 6 THEN l_M12Singkatan
				 WHEN position('5' in l_Urut) = 7 THEN l_M06Singkatan
				 WHEN position('5' in l_Urut) = 8 THEN l_M10Singkatan
				 WHEN position('5' in l_Urut) = 9 THEN ' '
				 WHEN position('5' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,9,11)
				 ELSE ' '
				END;
	END IF; 
	IF position('6' in l_Urut) <> 0 THEN 
		l_Kode6 := CASE WHEN position('6' in l_Urut) = 1 THEN l_S01KdCaba
				 WHEN position('6' in l_Urut) = 2 THEN l_S01KdUUsa 
				 WHEN position('6' in l_Urut) = 3 THEN l_S01KdUKer
				 WHEN position('6' in l_Urut) = 4 THEN l_S01KdArea
				 WHEN position('6' in l_Urut) = 5 THEN l_S01KdJaba
				 WHEN position('6' in l_Urut) = 6 THEN l_S01KdGlng
				 WHEN position('6' in l_Urut) = 7 THEN l_S01KdKJab
				 WHEN position('6' in l_Urut) = 8 THEN l_S01KdKlas
				 WHEN position('6' in l_Urut) = 9 THEN l_S01StsPjk
				 WHEN position('6' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,1,8)
				 ELSE ' '
				END;
		l_SkKode6 := CASE WHEN position('6' in l_Urut) = 1 THEN l_M08SkCaba
				 WHEN position('6' in l_Urut) = 2 THEN l_M02Singkatan 
				 WHEN position('6' in l_Urut) = 3 THEN l_M17Singkatan
				 WHEN position('6' in l_Urut) = 4 THEN l_M01Singkatan
				 WHEN position('6' in l_Urut) = 5 THEN l_M04Singkatan
				 WHEN position('6' in l_Urut) = 6 THEN l_M12Singkatan
				 WHEN position('6' in l_Urut) = 7 THEN l_M06Singkatan
				 WHEN position('6' in l_Urut) = 8 THEN l_M10Singkatan
				 WHEN position('6' in l_Urut) = 9 THEN ' '
				 WHEN position('6' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,9,11)
				 ELSE ' '
				END;
	END IF; 
	IF position('7' in l_Urut) <> 0 THEN 
		l_Kode7 := CASE WHEN position('7' in l_Urut) = 1 THEN l_S01KdCaba
					 WHEN position('7' in l_Urut) = 2 THEN l_S01KdUUsa 
					 WHEN position('7' in l_Urut) = 3 THEN l_S01KdUKer
					 WHEN position('7' in l_Urut) = 4 THEN l_S01KdArea
					 WHEN position('7' in l_Urut) = 5 THEN l_S01KdJaba
					 WHEN position('7' in l_Urut) = 6 THEN l_S01KdGlng
					 WHEN position('7' in l_Urut) = 7 THEN l_S01KdKJab
					 WHEN position('7' in l_Urut) = 8 THEN l_S01KdKlas
					 WHEN position('7' in l_Urut) = 9 THEN l_S01StsPjk
					 WHEN position('7' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,1,8)
					 ELSE ' '
					END;
		l_SkKode7 := CASE WHEN position('7' in l_Urut) = 1 THEN l_M08SkCaba
					 WHEN position('7' in l_Urut) = 2 THEN l_M02Singkatan 
					 WHEN position('7' in l_Urut) = 3 THEN l_M17Singkatan
					 WHEN position('7' in l_Urut) = 4 THEN l_M01Singkatan
					 WHEN position('7' in l_Urut) = 5 THEN l_M04Singkatan
					 WHEN position('7' in l_Urut) = 6 THEN l_M12Singkatan
					 WHEN position('7' in l_Urut) = 7 THEN l_M06Singkatan
					 WHEN position('7' in l_Urut) = 8 THEN l_M10Singkatan
					 WHEN position('7' in l_Urut) = 9 THEN ' '
					 WHEN position('7' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,9,11)
					 ELSE ' '
					END;
	END IF; 
	IF position('8' in l_Urut) <> 0 THEN 
		l_Kode8 := CASE WHEN position('8' in l_Urut) = 1 THEN l_S01KdCaba
					 WHEN position('8' in l_Urut) = 2 THEN l_S01KdUUsa 
					 WHEN position('8' in l_Urut) = 3 THEN l_S01KdUKer
					 WHEN position('8' in l_Urut) = 4 THEN l_S01KdArea
					 WHEN position('8' in l_Urut) = 5 THEN l_S01KdJaba
					 WHEN position('8' in l_Urut) = 6 THEN l_S01KdGlng
					 WHEN position('8' in l_Urut) = 7 THEN l_S01KdKJab
					 WHEN position('8' in l_Urut) = 8 THEN l_S01KdKlas
					 WHEN position('8' in l_Urut) = 9 THEN l_S01StsPjk
					 WHEN position('8' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,1,8)
					 ELSE ' '
					END; 
		l_SkKode8 := CASE WHEN position('8' in l_Urut) = 1 THEN l_M08SkCaba
					 WHEN position('8' in l_Urut) = 2 THEN l_M02Singkatan 
					 WHEN position('8' in l_Urut) = 3 THEN l_M17Singkatan
					 WHEN position('8' in l_Urut) = 4 THEN l_M01Singkatan
					 WHEN position('8' in l_Urut) = 5 THEN l_M04Singkatan
					 WHEN position('8' in l_Urut) = 6 THEN l_M12Singkatan
					 WHEN position('8' in l_Urut) = 7 THEN l_M06Singkatan
					 WHEN position('8' in l_Urut) = 8 THEN l_M10Singkatan
					 WHEN position('8' in l_Urut) = 9 THEN ' '
					 WHEN position('8' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,9,11)
					 ELSE ' '
					END; 
	END IF; 
	IF position('9' in l_Urut) <> 0 THEN 
		l_Kode9 := CASE WHEN position('9' in l_Urut) = 1 THEN l_S01KdCaba
					 WHEN position('9' in l_Urut) = 2 THEN l_S01KdUUsa 
					 WHEN position('9' in l_Urut) = 3 THEN l_S01KdUKer
					 WHEN position('9' in l_Urut) = 4 THEN l_S01KdArea
					 WHEN position('9' in l_Urut) = 5 THEN l_S01KdJaba
					 WHEN position('9' in l_Urut) = 6 THEN l_S01KdGlng
					 WHEN position('9' in l_Urut) = 7 THEN l_S01KdKJab
					 WHEN position('9' in l_Urut) = 8 THEN l_S01KdKlas
					 WHEN position('9' in l_Urut) = 9 THEN l_S01StsPjk
					 WHEN position('9' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,1,8)
					 ELSE ' '
					END;
		l_SkKode9 := CASE WHEN position('9' in l_Urut) = 1 THEN l_M08SkCaba
					 WHEN position('9' in l_Urut) = 2 THEN l_M02Singkatan 
					 WHEN position('9' in l_Urut) = 3 THEN l_M17Singkatan
					 WHEN position('9' in l_Urut) = 4 THEN l_M01Singkatan
					 WHEN position('9' in l_Urut) = 5 THEN l_M04Singkatan
					 WHEN position('9' in l_Urut) = 6 THEN l_M12Singkatan
					 WHEN position('9' in l_Urut) = 7 THEN l_M06Singkatan
					 WHEN position('9' in l_Urut) = 8 THEN l_M10Singkatan
					 WHEN position('9' in l_Urut) = 9 THEN ' '
					 WHEN position('9' in l_Urut) = 10 THEN SUBSTRING(l_M15NPWP,9,11)
					 ELSE ' '
					END;
	END IF; 
-- -- print l_kode1 + l_kode2+l_kode3+l_kode4+l_kode5 + l_kode6 +l_kode7 +l_kode8 
-- -- print l_urut  
	-- kalo ada teks yang blank, 
	IF position('8' IN l_Urut) = 0 THEN 
		l_Kode8   := l_Kode9  ; l_Kode9   := ' '; 
		l_SkKode8 := l_SkKode9; l_SkKode9 := ' ';
	END IF; 
	IF position('7' IN l_Urut) = 0 THEN 
		l_Kode7   := l_Kode8  ; l_Kode8   := l_Kode9  ; l_Kode9   := ' '; 
		l_SkKode7 := l_SkKode8; l_SkKode8 := l_SkKode9; l_SkKode9 := ' '; 
	END IF; 
	IF position('6' IN l_Urut) = 0 THEN 
		l_Kode6   := l_Kode7  ; l_Kode7   := l_Kode8  ; l_Kode8   := l_Kode9  ; l_Kode9   := ' '; 
		l_SkKode6 := l_SkKode7; l_SkKode7 := l_SkKode8; l_SkKode8 := l_SkKode9; l_SkKode9 := ' ';
	END IF; 
	IF position('5' IN l_Urut) = 0 THEN 
		l_Kode5   := l_Kode6  ; l_Kode6   := l_Kode7  ; l_Kode7   := l_Kode8  ; l_Kode8   := l_Kode9  ; l_Kode9   := ' ';  
		l_SkKode5 := l_SkKode6; l_SkKode6 := l_SkKode7; l_SkKode7 := l_SkKode8; l_SkKode8 := l_SkKode9; l_SkKode9 := ' ';
	END IF; 
	IF position('4' IN l_Urut) = 0 THEN 
		l_Kode4   := l_Kode5  ; l_Kode5   := l_Kode6  ; l_Kode6   := l_Kode7  ; l_Kode7   := l_Kode8  ; l_Kode8   := l_Kode9  ; l_Kode9   := ' ';  
		l_SkKode4 := l_SkKode5; l_SkKode5 := l_SkKode6; l_SkKode6 := l_SkKode7; l_SkKode7 := l_SkKode8; l_SkKode8 := l_SkKode9; l_SkKode9 := ' ';
	END IF; 
	IF position('3' IN l_Urut) = 0 THEN 
		l_Kode3   := l_Kode4  ; l_Kode4   := l_Kode5  ; l_Kode5   := l_Kode6  ; l_Kode6   := l_Kode7  ; l_Kode7   := l_Kode8  ; l_Kode8   := l_Kode9  ; l_Kode9   := ' ';  
		l_SkKode3 := l_SkKode4; l_SkKode4 := l_SkKode5; l_SkKode5 := l_SkKode6; l_SkKode6 := l_SkKode7; l_SkKode7 := l_SkKode8; l_SkKode8 := l_SkKode9; l_SkKode9 := ' ';
	END IF; 
	IF position('2' IN l_Urut) = 0 THEN 
		l_Kode2   := l_Kode3  ; l_Kode3   := l_Kode4  ; l_Kode4   := l_Kode5  ; l_Kode5   := l_Kode6  ; l_Kode6   := l_Kode7  ; l_Kode7   := l_Kode8  ; l_Kode8   := l_Kode9  ; l_Kode9   := ' '; 
		l_SkKode2 := l_SkKode3; l_SkKode3 := l_SkKode4; l_SkKode4 := l_SkKode5; l_SkKode5 := l_SkKode6; l_SkKode6 := l_SkKode7; l_SkKode7 := l_SkKode8; l_SkKode8 := l_SkKode9; l_SkKode9 := ' '; 
	END IF; 
	IF position('1' IN l_Urut) = 0 THEN 
		l_Kode1   := l_Kode2  ; l_Kode2   := l_Kode3  ; l_Kode3   := l_Kode4  ; l_Kode4   := l_Kode5  ; l_Kode5   := l_Kode6  ; l_Kode6   := l_Kode7  ; l_Kode7   := l_Kode8  ; l_Kode8   := l_Kode9  ; l_Kode9   := ' '; 
		l_SkKode1 := l_SkKode2; l_SkKode2 := l_SkKode3; l_SkKode3 := l_SkKode4; l_SkKode4 := l_SkKode5; l_SkKode5 := l_SkKode6; l_SkKode6 := l_SkKode7; l_SkKode7 := l_SkKode8; l_SkKode8 := l_SkKode9; l_SkKode9 := ' ';
	END IF; 
		
	INSERT INTO l_TEMP(Tahun                          ,Bulan                           ,Teks1  ,Kode1  ,SkKode1  ,Teks2  ,Kode2  ,SkKode2  ,Teks3  ,Kode3  ,SkKode3  ,Teks4  ,Kode4  ,SkKode4  ,Teks5  ,Kode5  ,SkKode5  ,Teks6  ,Kode6  ,SkKode6  ,Cabang     , KdUker    ,Nip     ,FlagDppt    ,NomFormat     ,Nama     ,JnsKlm      ,StsMed     ,KdBank      ,AccHolder     ,AccNumber ,KetBank        ,Keterangan     ,Nilai     ,JnsPjk       , FlagThr    ,Piutang  ,Lemburan)
		    VALUES(EXTRACT(YEAR FROM l_S02TglPayr),EXTRACT(MONTH FROM l_S02TglPayr),l_Teks1,l_Kode1,l_SkKode1,l_Teks2,l_Kode2,l_SkKode2,l_Teks3,l_Kode3,l_SkKode3,l_Teks4,l_Kode4,l_SkKode4,l_Teks5,l_Kode5,l_SkKode5,l_Teks6,l_Kode6,l_SkKode6,l_S01KdCaba,l_S01KdUKer,l_S01NIP,l_S02FlgDpPt,l_M19NomFormat,l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,l_M19Keterangan,l_S02Nilai,l_S01JnsPajak,l_S01FlagTHR,l_GetPiut,l_GetLmbr);
--           INSERT INTO l_TEMP(Tahun             ,Bulan              ,Teks1 ,KodeSK1,                Teks2 ,Kode2, SkKode2 ,Teks3 ,Kode3 ,SkKode3 ,Teks4, Kode4, SkKode4, Teks5, Kode5, SkKode5, Teks6, Kode6, SkKode6,Cabang     , KdUker   ,Nip    ,FlagDppt    ,NomFormat     ,Nama     ,JnsKlm      ,StsMed     ,KdBank      ,AccHolder     ,AccNumber ,KetBank        ,Keterangan     ,Nilai     ,JnsPjk, FlagThr,Piutang,Lemburan)
-- 		               VALUES(EXTRACT(YEAR FROM l_S02TglPayr),EXTRACT(MONTH FROM l_S02TglPayr),l_Teks1,l_Kode1 + ' ' + l_SkKode1,l_Teks2,l_Kode2,l_SkKode2,l_Teks3,l_Kode3,l_SkKode3,l_Teks4,l_Kode4,l_SkKode4,l_Teks5,l_Kode5,l_SkKode5,l_Teks6,l_Kode6,l_SkKode6,l_S01KdCaba,l_S01KdUKer,l_S01NIP,l_S02FlgDpPt,l_M19NomFormat,l_S01Nama,l_S01JnsKlmn,l_S01StsPjk,l_S01BankRef,l_S01AccHolder,l_S01RkBnk,l_M14Keterangan,l_M19Keterangan,l_S02Nilai,l_S01JnsPajak,l_S01FlagTHR,l_GetPiut,l_GetLmbr)
    END IF; 
	--	
END LOOP;
CLOSE l_LOOP_W11;

------------------------------------- Isi data ke TEMP1 -----------------------------------
--
l_NIP:=' ';
l_FlgDpPt:=' ';
l_NoUrut:=1;
l_NoPend:=1;
l_NoPot:=1;
--
OPEN l_LOOP_TEMP FOR 
SELECT Tahun,Bulan,Teks1,Kode1,SkKode1,Teks2,Kode2,SkKode2,
       Teks3,Kode3,SkKode3,Teks4,Kode4,SkKode4,Teks5,Kode5,SkKode5,
       Teks6,Kode6,SkKode6,Cabang,KdUKer,Nip,FlagDppt,NomFormat,
       Nama,JnsKlm,StsMed,KdBank,AccHolder,AccNumber,KetBank,
       Keterangan,Nilai,JnsPjk,FlagThr,Piutang,Lemburan
FROM l_TEMP
ORDER BY NIP,FlagDppt,NomFormat;

<<l_LOOP_TEMP>> 
LOOP 
FETCH l_LOOP_TEMP 
INTO l_TEMPTahun,l_TEMPBulan,l_TEMPTeks1,l_TEMPKode1,l_TEMPSkKode1,l_TEMPTeks2,l_TEMPKode2,l_TEMPSkKode2,
     l_TEMPTeks3,l_TEMPKode3,l_TEMPSkKode3,l_TEMPTeks4,l_TEMPKode4,l_TEMPSkKode4,l_TEMPTeks5,l_TEMPKode5,l_TEMPSkKode5,
     l_TEMPTeks6,l_TEMPKode6,l_TEMPSkKode6,l_TEMPCabang,l_TEMPKdUKer,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,
     l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,
     l_TEMPKeterangan,l_TEMPNilai,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan;

    IF NOT FOUND THEN
	EXIT ;
    END IF;
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
                  INSERT INTO l_TEMP1(Tahun         ,Bulan         ,Teks1         ,Kode1         ,SkKode1         ,Teks2         ,Kode2         ,SkKode2         ,Teks3         ,Kode3         ,SkKode3         ,Teks4         ,Kode4         ,SkKode4         ,Teks5         ,Kode5         ,SkKode5         ,Teks6         ,Kode6         ,SkKode6         ,Cabang         ,KdUKer         ,Nip     ,FlagDppt         ,NomFormat         ,Nama         ,JnsKlm         ,StsMed         ,KdBank         ,AccHolder         ,AccNumber         ,KetBank         ,JnsPjk         ,FlagTHR         ,Piutang         ,Lemburan         ,NoUrut    ,TpFormat1,GrossIncome ,NilGRoss ,TpFormat2,Deduction            ,NilDeduc    ,KetTakeHomePAy ,NilTakeHomePAy)
                               VALUES(l_OLDTEMPTahun,l_OLDTEMPBulan,l_OLDTEMPTeks1,l_OLDTEMPKode1,l_OLDTEMPSkKode1,l_OLDTEMPTeks2,l_OLDTEMPKode2,l_OLDTEMPSkKode2,l_OLDTEMPTeks3,l_OLDTEMPKode3,l_OLDTEMPSkKode3,l_OLDTEMPTeks4,l_OLDTEMPKode4,l_OLDTEMPSkKode4,l_OLDTEMPTeks5,l_OLDTEMPKode5,l_OLDTEMPSkKode5,l_OLDTEMPTeks6,l_OLDTEMPKode6,l_OLDTEMPSkKode6,l_OLDTEMPCabang,l_OLDTEMPKdUKer,l_OLDNip,l_OLDTEMPFlagDppt,l_OLDTEMPNomFormat,l_OLDTEMPNama,l_OLDTEMPJnsKlm,l_OLDTEMPStsMed,l_OLDTEMPKdBank,l_OLDTEMPAccHolder,l_OLDTEMPAccNumber,l_OLDTEMPKetBank,l_OLDTEMPJnsPjk,l_OLDTEMPFlagTHR,l_OLDTEMPPiutang,l_OLDTEMPLemburan,l_OLDNoPot,' '      ,' '         , 0       ,'J'      ,'** Total DEDUCTION ',l_OLDTotPot ,'TAKE HOME PAY',0); 
        --                      VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPArea,l_TEMPSkArea,l_TEMPCabang,l_TEMPSkCabang,l_TEMPUnitUsaha,l_TEMPSkUUsa,l_TEMPKetUUsa,l_TEMPKdUker,l_TEMPSkUker,l_TEMPGlng,l_TEMPSkGlng,l_TEMPKJab,l_TEMPSkKjab,l_TEMPJaba,l_TEMPSkJaba,l_TEMPKlas,l_TEMPSkKlas,l_Nip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoPot,' '      ,' '         , 0       ,'J'      ,'** Total DEDUCTION ',l_TotPot ,'TAKE HOME PAY'        ,0) 
             END IF; 
         END IF; 
         -- 
	 l_NIP:=l_TEMPNip;
	 l_NoUrut:=1;
	 l_NoPend:=1;
	 l_NoPot:=1;
	 l_TotPend:=0;
	 l_TotPot:=0;
         --  
    END IF; 
    --*
    --  
    IF l_FlgDpPt<>l_TempFlagDppt THEN 
         --
         IF l_FlgDpPt<>' ' THEN 
              --
              IF l_FlgDpPt='D' AND l_NoUrut<>1 THEN 
	          IF (SELECT COUNT(tahun) FROM l_TEMP1 WHERE NIP=l_TEMPNIP AND NoUrut=l_NoPend) > 0 THEN 
	               UPDATE l_TEMP1
	               SET TpFormat1  ='J',
			   GrossIncome='** Total INCOME  ',
			   NilGRoss   =l_TotPend
	               WHERE NIP=l_TEMPNIP AND NoUrut=l_NoPend;
	          ELSE  
	               INSERT INTO l_TEMP1(Tahun      ,Bulan      ,Teks1,      Kode1,      SkKode1,      Teks2,      Kode2,      SkKode2,      Teks3,      Kode3,      SkKode3,      Teks4,      Kode4,      SkKode4,      Teks5,      Kode5,      SkKode5,      Teks6,      Kode6,      SkKode6,      Cabang      ,KdUKer      ,Nip      ,FlagDppt      ,NomFormat      ,Nama      ,JnsKlm      ,StsMed      ,KdBank      ,AccHolder      ,AccNumber      ,KetBank      ,JnsPjk      ,FlagTHR      ,Piutang      ,Lemburan      ,NoUrut  ,TpFormat1,GrossIncome       ,NilGRoss ,TpFormat2,Deduction,NilDeduc,KetTakeHomePAy,NilTakeHomePAy)
	                            VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPTeks1,l_TEMPKode1,l_TEMPSkKode1,l_TEMPTeks2,l_TEMPKode2,l_TEMPSkKode2,l_TEMPTeks3,l_TEMPKode3,l_TEMPSkKode3,l_TEMPTeks4,l_TEMPKode4,l_TEMPSkKode4,l_TEMPTeks5,l_TEMPKode5,l_TEMPSkKode5,l_TEMPTeks6,l_TEMPKode6,l_TEMPSkKode6,l_TEMPCabang,l_TEMPKdUKer,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoPend,'J'      ,'** Total INCOME' ,l_TotPend,' '      ,' '      ,0       ,'    '        ,0) ;
	          END IF; 
	          --* 
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
         INSERT INTO l_TEMP1(Tahun     ,Bulan     ,Teks1     ,Kode1     ,SkKode1     ,Teks2     ,Kode2     ,SkKode2     ,Teks3     ,Kode3     ,SkKode3     ,Teks4     ,Kode4     ,SkKode4     ,Teks5     ,Kode5     ,SkKode5     ,Teks6     ,Kode6     ,SkKode6     ,Cabang     ,KdUKer     ,Nip     ,FlagDppt     ,NomFormat     ,Nama     ,JnsKlm     ,StsMed     ,KdBank     ,AccHolder     ,AccNumber     ,KetBank     ,JnsPjk     ,FlagTHR     ,Piutang     ,Lemburan     ,NoUrut ,TpFormat1,GrossIncome     ,NilGRoss,TpFormat2,Deduction    ,NilDeduc,KetTakeHomePAy ,NilTakeHomePAy)
                     VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPTeks1,l_TEMPKode1,l_TEMPSkKode1,l_TEMPTeks2,l_TEMPKode2,l_TEMPSkKode2,l_TEMPTeks3,l_TEMPKode3,l_TEMPSkKode3,l_TEMPTeks4,l_TEMPKode4,l_TEMPSkKode4,l_TEMPTeks5,l_TEMPKode5,l_TEMPSkKode5,l_TEMPTeks6,l_TEMPKode6,l_TEMPSkKode6,l_TEMPCabang,l_TEMPKdUKer,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoUrut,'T'      ,'INCOME :',0       ,'T'      ,'DEDUCTION :',0       ,'TAKE HOME PAY  ',0); 
         --
         INSERT INTO l_TEMP1(Tahun     ,Bulan     ,Teks1     ,Kode1     ,SkKode1     ,Teks2     ,Kode2     ,SkKode2     ,Teks3     ,Kode3     ,SkKode3     ,Teks4     ,Kode4     ,SkKode4     ,Teks5     ,Kode5     ,SkKode5     ,Teks6     ,Kode6     ,SkKode6     ,Cabang     ,KdUKer     ,Nip     ,FlagDppt     ,NomFormat     ,Nama     ,JnsKlm     ,StsMed     ,KdBank     ,AccHolder     ,AccNumber     ,KetBank     ,JnsPjk     ,FlagTHR     ,Piutang     ,Lemburan     ,NoUrut ,TpFormat1,GrossIncome     ,NilGRoss,TpFormat2,Deduction    ,NilDeduc,KetTakeHomePAy ,NilTakeHomePAy)
                     VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPTeks1,l_TEMPKode1,l_TEMPSkKode1,l_TEMPTeks2,l_TEMPKode2,l_TEMPSkKode2,l_TEMPTeks3,l_TEMPKode3,l_TEMPSkKode3,l_TEMPTeks4,l_TEMPKode4,l_TEMPSkKode4,l_TEMPTeks5,l_TEMPKode5,l_TEMPSkKode5,l_TEMPTeks6,l_TEMPKode6,l_TEMPSkKode6,l_TEMPCabang,l_TEMPKdUKer,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoUrut,'T'      ,' ',0       ,'T'      ,' ',0       ,' ',0); 
         --
         l_NoPend:=l_NoPend+1;
	 l_NoPot :=l_NoPot+1;
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
	         INSERT INTO l_TEMP1(Tahun       ,Bulan     ,Teks1     ,Kode1     ,SkKode1     ,Teks2     ,Kode2     ,SkKode2     ,Teks3     ,Kode3     ,SkKode3     ,Teks4     ,Kode4     ,SkKode4     ,Teks5     ,Kode5     ,SkKode5     ,Teks6     ,Kode6     ,SkKode6     ,Cabang     ,KdUKer     ,Nip     ,FlagDppt     ,NomFormat     ,Nama     ,JnsKlm     ,StsMed     ,KdBank     ,AccHolder     ,AccNumber     ,KetBank     ,JnsPjk     ,FlagTHR     ,Piutang     ,Lemburan     ,NoUrut ,TpFormat1,GrossIncome     ,NilGRoss,TpFormat2,Deduction    ,NilDeduc,KetTakeHomePAy ,NilTakeHomePAy)
                              VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPTeks1,l_TEMPKode1,l_TEMPSkKode1,l_TEMPTeks2,l_TEMPKode2,l_TEMPSkKode2,l_TEMPTeks3,l_TEMPKode3,l_TEMPSkKode3,l_TEMPTeks4,l_TEMPKode4,l_TEMPSkKode4,l_TEMPTeks5,l_TEMPKode5,l_TEMPSkKode5,l_TEMPTeks6,l_TEMPKode6,l_TEMPSkKode6,l_TEMPCabang,l_TEMPKdUKer,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoPend,'D'      ,l_TempKeterangan,l_TempNilai,' '      ,' '      ,0       ,'    '        ,0); 
 
          END IF; 
          --* 
          l_NoPend :=l_NoPend+1;
	  l_TotPend:=l_TotPend+l_TempNilai;
          --
   ELSE -- Jika Flag Potongan 
          IF (SELECT COUNT(tahun) FROM l_TEMP1 WHERE NIP=l_TEMPNIP AND NoUrut=l_NoPot) > 0 THEN 
               UPDATE l_TEMP1
               SET TpFormat2  ='D',
		   Deduction  =l_TempKeterangan,
		   NilDeduc   =l_TempNilai
               WHERE NIP=l_TEMPNIP AND NoUrut=l_NoPot;
          ELSE  
	       INSERT INTO l_TEMP1(Tahun     ,Bulan     ,Teks1     ,Kode1     ,SkKode1     ,Teks2     ,Kode2     ,SkKode2     ,Teks3     ,Kode3     ,SkKode3     ,Teks4     ,Kode4     ,SkKode4     ,Teks5     ,Kode5     ,SkKode5     ,Teks6     ,Kode6     ,SkKode6     ,Cabang     ,KdUKer     ,Nip     ,FlagDppt     ,NomFormat     ,Nama     ,JnsKlm     ,StsMed     ,KdBank     ,AccHolder     ,AccNumber     ,KetBank     ,JnsPjk     ,FlagTHR     ,Piutang     ,Lemburan     ,NoUrut ,TpFormat1,GrossIncome     ,NilGRoss,TpFormat2,Deduction    ,NilDeduc,KetTakeHomePAy ,NilTakeHomePAy)
		            VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPTeks1,l_TEMPKode1,l_TEMPSkKode1,l_TEMPTeks2,l_TEMPKode2,l_TEMPSkKode2,l_TEMPTeks3,l_TEMPKode3,l_TEMPSkKode3,l_TEMPTeks4,l_TEMPKode4,l_TEMPSkKode4,l_TEMPTeks5,l_TEMPKode5,l_TEMPSkKode5,l_TEMPTeks6,l_TEMPKode6,l_TEMPSkKode6,l_TEMPCabang,l_TEMPKdUKer,l_TEMPNip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoPot,' '      ,' '        ,0       ,'D'      ,l_TempKeterangan,l_TempNilai,'    '        ,0) ;
          END IF; 
          --* 
          l_NoPot :=l_NoPot+1;
	  l_TotPot:=l_TotPot+l_TempNilai;
    END IF; 
    --*
    --
    l_NoUrut:=l_NoUrut+1;
    --- Tampung Posisi Sebelumnya
    l_OLDTEMPTahun   	:= l_TEMPTahun;
    l_OLDTEMPBulan   	:= l_TEMPBulan;
    l_OLDTEMPArea    	:= l_TEMPArea;
    l_OLDTEMPNmArea  	:= l_TEMPNmArea;
    l_OLDTEMPSkArea  	:= l_TEMPSkArea;
    l_OLDTEMPCabang  	:= l_TEMPCabang;
    l_OLDTEMPSkCabang 	:= l_TEMPSkCabang;
    l_OLDTEMPUnitUsaha 	:= l_TEMPUnitUsaha;
    l_OLDTEMPSkUUsa  	:= l_TEMPSkUUsa;
    l_OLDTEMPKetUUsa 	:= l_TEMPKetUUsa;
    l_OLDTEMPKdUker  	:= l_TEMPKdUker;
    l_OLDTEMPSkUker  	:= l_TEMPSkUker;
    l_OLDTEMPGlng    	:= l_TEMPGlng;
    l_OLDTEMPSkGlng  	:= l_TEMPSkGlng;
    l_OLDTEMPKJab    	:= l_TEMPKJab;
    l_OLDTEMPSkKjab  	:= l_TEMPSkKjab;
    l_OLDTEMPJaba    	:= l_TEMPJaba;
    l_OLDTEMPSkJaba  	:= l_TEMPSkJaba;
    l_OLDTEMPKlas    	:= l_TEMPKlas;
    l_OLDTEMPSkKlas  	:= l_TEMPSkKlas;
    l_OLDNip         	:= l_Nip;
    l_OLDTEMPFlagDppt 	:= l_TEMPFlagDppt;
    l_OLDTEMPNomFormat 	:= l_TEMPNomFormat;
    l_OLDTEMPNama    	:= l_TEMPNama;
    l_OLDTEMPJnsKlm  	:= l_TEMPJnsKlm;
    l_OLDTEMPStsMed  	:= l_TEMPStsMed;
    l_OLDTEMPKdBank  	:= l_TEMPKdBank;
    l_OLDTEMPAccHolder 	:= l_TEMPAccHolder;
    l_OLDTEMPAccNumber 	:= l_TEMPAccNumber;
    l_OLDTEMPKetBank 	:= l_TEMPKetBank;
    l_OLDTEMPJnsPjk  	:= l_TEMPJnsPjk;
    l_OLDTEMPFlagTHR 	:= l_TEMPFlagTHR;
    l_OLDTEMPPiutang 	:= l_TEMPPiutang;
    l_OLDTEMPLemburan 	:= l_TEMPLemburan;
    l_OLDNoPot 	 	:= l_NoPot;
    l_OLDTotPot 	:= l_TotPot; 
    l_OLDTEMPTeks1	:= l_TEMPTeks1;
    l_OLDTEMPKode1	:= l_TEMPKode1;
    l_OLDTEMPSkKode1 	:= l_TEMPSkKode1;
    l_OLDTEMPTeks2 	:= l_TEMPTeks2;
    l_OLDTEMPKode2	:= l_TEMPKode2;
    l_OLDTEMPSkKode2 	:= l_TEMPSkKode2;
    l_OLDTEMPTeks3	:= l_TEMPTeks3;
    l_OLDTEMPKode3	:= l_TEMPKode3;
    l_OLDTEMPSkKode3 	:= l_TEMPSkKode3;
    l_OLDTEMPTeks4	:= l_TEMPTeks4;
    l_OLDTEMPKode4	:= l_TEMPKode4;
    l_OLDTEMPSkKode4	:= l_TEMPSkKode4;
    l_OLDTEMPTeks5	:= l_TEMPTeks5;
    l_OLDTEMPKode5	:= l_TEMPKode5;
    l_OLDTEMPSkKode5 	:= l_TEMPSkKode5;
    l_OLDTEMPTeks6	:= l_TEMPTeks6;  
    l_OLDTEMPKode6	:= l_TEMPKode6;
    l_OLDTEMPSkKode6	:= l_TEMPSkKode6;
    --
END LOOP;
CLOSE l_LOOP_TEMP;
--
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
    INSERT INTO l_TEMP1(Tahun     ,Bulan     ,Teks1     ,Kode1     ,SkKode1     ,Teks2     ,Kode2     ,SkKode2     ,Teks3     ,Kode3     ,SkKode3     ,Teks4     ,Kode4     ,SkKode4     ,Teks5     ,Kode5     ,SkKode5     ,Teks6     ,Kode6     ,SkKode6     ,Cabang     ,KdUKer     ,Nip     ,FlagDppt     ,NomFormat     ,Nama     ,JnsKlm     ,StsMed     ,KdBank     ,AccHolder     ,AccNumber     ,KetBank     ,JnsPjk     ,FlagTHR     ,Piutang     ,Lemburan     ,NoUrut ,TpFormat1,GrossIncome     ,NilGRoss,TpFormat2,Deduction    ,NilDeduc,KetTakeHomePAy ,NilTakeHomePAy)
                VALUES(l_TEMPTahun,l_TEMPBulan,l_TEMPTeks1,l_TEMPKode1,l_TEMPSkKode1,l_TEMPTeks2,l_TEMPKode2,l_TEMPSkKode2,l_TEMPTeks3,l_TEMPKode3,l_TEMPSkKode3,l_TEMPTeks4,l_TEMPKode4,l_TEMPSkKode4,l_TEMPTeks5,l_TEMPKode5,l_TEMPSkKode5,l_TEMPTeks6,l_TEMPKode6,l_TEMPSkKode6,l_TEMPCabang,l_TEMPKdUKer,l_Nip,l_TEMPFlagDppt,l_TEMPNomFormat,l_TEMPNama,l_TEMPJnsKlm,l_TEMPStsMed,l_TEMPKdBank,l_TEMPAccHolder,l_TEMPAccNumber,l_TEMPKetBank,l_TEMPJnsPjk,l_TEMPFlagTHR,l_TEMPPiutang,l_TEMPLemburan,l_NoPot,' '      ,' '         , 0       ,'J'      ,'** Total DEDUCTION ',l_TotPot ,'TAKE HOME PAY'       ,0) ;
END IF; 
--*
----
--------------------- isi TakeHome Pay
UPDATE l_TEMP1
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
WHERE TMP.KetTakeHomePAy='TAKE HOME PAY';
----

------------------ Mengisi data yang tdk punya total gross Income

INSERT INTO l_TEMP1(Tahun     ,Bulan     ,Teks1  ,Kode1  ,SkKode1  ,Teks2  ,Kode2  ,SkKode2  ,Teks3  ,Kode3  ,SkKode3  ,Teks4  ,Kode4  ,SkKode4  ,Teks5  ,Kode5  ,SkKode5  ,Teks6  ,Kode6  ,SkKode6  ,Cabang  ,KdUKer  ,Nip   ,FlagDppt     ,NomFormat     ,Nama     ,JnsKlm     ,StsMed     ,KdBank     ,AccHolder     ,AccNumber     ,KetBank     ,JnsPjk     ,FlagTHR     ,Piutang     ,Lemburan     ,NoUrut ,TpFormat1,GrossIncome     ,NilGRoss,TpFormat2,Deduction    ,NilDeduc,KetTakeHomePAy ,NilTakeHomePAy)
SELECT             T.Tahun   ,T.Bulan   ,T.Teks1,T.Kode1,T.SkKode1,T.Teks2,T.Kode2,T.SkKode2,T.Teks3,T.Kode3,T.SkKode3,T.Teks4,T.Kode4,T.SkKode4,T.Teks5,T.Kode5,T.SkKode5,T.Teks6,T.Kode6,T.SkKode6,T.Cabang,T.KdUKer,T.Nip ,T.FlagDppt   ,T.NomFormat   ,T.Nama   ,T.JnsKlm   ,T.StsMed   ,T.KdBank   ,T.AccHolder   ,T.AccNumber   ,T.KetBank   ,T.JnsPjk   ,FlagTHR     ,0        ,0            ,99      ,'J'      ,'** Total INCOME' ,GRS.NilGRoss,'T'      ,' '                   ,0       ,' ',0
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
WHERE GrossIncome='** Total INCOME'
)ADA
ON ADA.NIP=T.NIP
---
LEFT JOIN
(--N
SELECT NIP,SUM(S.NilGRoss) AS NilGRoss
FROM l_TEMP1 S
WHERE GrossIncome<>'** Total INCOME' AND FlagDppt='D'
GROUP BY NIP
)N
ON N.NIP=T.NIP
WHERE ADA.NIP IS NULL
)GRS
----
INNER JOIN 
(--T
SELECT * FROM l_TEMP1
WHERE GrossIncome='INCOME :'
)T
ON T.NIP=GRS.NIP;

-------------------------------------------------- HASIL AKHIR....
RETURN QUERY SELECT * FROM l_TEMP1
WHERE COALESCE(Tahun,0)<>0 AND COALESCE(Bulan,0)<>0;

END;
$$ LANGUAGE plpgsql ;
	
/*
select * from R_SLIKP2('2010-08-15',' ','ZZZ','200705011','200705011','','zz',
'Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 
'1','2','3','4','5','6','7','8','9','10',1);

*/

--select * from S02DGAJ
