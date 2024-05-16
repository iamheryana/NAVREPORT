
/********************
  Isi data FZ1FLDA
*********************/
INSERT INTO FZ1FLDA(Kode,NamaPR           ,AdrsPR                ,KotaPR    ,NPWPPR               ,CURRPD            ,NEXTPD             ,IMPLPD            ,FlgPost ,Payroll,Personalia,Pajak,IntvPajak, SPT,HitPjk ,LimPjk1 ,Pjk1,LimPjk2,Pjk2,LimPjk3,Pjk3,NlOccSupp,PrOccSupp,PTKPPay ,PTKPDep,RndTax, NPP,RetAge,KdDHT,IurPgwi,IurPers,Bunga,JKK,JHT,JKM,JPK,HariAstek ,FlgLemb ,JmlLemb,LimWrk1,RateWrk1,RateWrk2,LimLbr1,LimLbr2, RateLbr1, RateLbr2,RateLbr3,RteLbr1,RteLbr2, Pembilang,Penyebut, PembilangH,PenyebutH,PersentasiLembur,FlgGGol, TunjIst,TunjAnk,FlgGlng,FlgKJab,FlgJaba,FlgCaba,FlgPiut,JumlahMangkir,RndPen,FlgTHR  ,IuranSPSI,Hari01, Hari02, Hari03, Hari04 ,Hari05 ,Hari06 ,Hari07 ,Hari08 ,Hari09 ,Hari10 ,Hari11 ,Hari12 ,HariKerja, SavePayrol, SaveHRD ,VarRound ,MToleransiCuti,NlTunjIst, NlTunjAnak, LimLIst1, RateLIst1, RateList2, KdCutiHaid, KdSakitSrtDr,version, created_by,created_on) 
            VALUES ('01','PT. MILIK PAKET ','GEDUNG PELNI LT 2 ','JAKARTA ','916045064004000'    ,'2014-01-01'::date ,'2014-02-01'::date ,'2013-12-01'::date,' '     ,'Y'     ,'Y'      ,'T'   ,1       ,'T' ,'G'    , 0      ,0   ,0      ,0   ,0      ,0   ,500000    ,5       ,24300000 ,2025000,0     ,' ' ,55    ,' '  ,0      ,0      , 0   ,'Y','Y','Y','T',        0 ,    'Y' ,      0,      1,    1.50,   2.00 ,    7  ,     8 ,     2.00,   3.00  ,   4.00 ,   6   ,    5  ,     1    ,    173 ,     3     ,     20  ,     75.00      ,'T'    ,    0   ,0      ,'T'    ,   'T' ,'T'    ,'T'    ,   'Y' ,          30 ,    0 ,     'Y',      .00,    21,    23 ,    21 ,    21  ,   22  ,   23  ,   23  ,   23  ,   23  ,   21  ,   22  ,   22  ,   99    ,    0      ,    0    ,   2     ,   6          , 0       , 0         , 1       , 2        , 3        , ' '       , ' '         ,0      , 'COMPUTER', LOCALTIMESTAMP);
--
INSERT INTO FZ2FLDA(Kode,StringFlag,NamaBank,NoAccount,Cabang,KotaBank,NoRekening,NamaAccHolder,AlamatAccHolder,Kota,TTD,FlagRapel,FlRapelTHR,TglMulaiPolis,TglAkhirPolis,ServerAttendance,UmurAnakMax,UmurIstriMax,JmlHariSethn,ServerGl,DbaseGL,FaktorXPajak)
             VALUES('01','000PTTTT1TTTT' ,' '     ,' '      ,' '   ,' '     ,' '       ,' '          ,' '            ,' ' ,' ',0        ,0   , null        ,null         , ' '            , 21        ,36          ,365         , ' '    ,' '    ,120);
--
/******************
-- Insert M40CURR
******************/
INSERT INTO M40CURR(KDCURR,KTCURR             ,SKCURR   ,VERSION, CREATED_BY, CREATED_ON)   
            VALUES (' ',   'N O N E'          ,' '      ,0      , 'COMPUTER',localtimestamp),
                   ('IDR', 'INDONESIAN RUPIAH','IDR' ,0      , 'COMPUTER',localtimestamp); 

/***************
 Insert M12HGOL
****************/
INSERT INTO M12HGOL(Kode ,Keterangan     ,Singkatan,MinGol,MidGol,MaxGol,Recs ,TGLSUSPEND        ,VERSION, CREATED_BY, CREATED_ON)    
             VALUES(' '  ,'NON GOLONGAN' ,'NON/GLNG',0    ,0     ,0     ,0    ,'12-31-2099'::DATE, 0      , 'COMPUTER',localtimestamp); 

/*****************
 Insert M14BANK
*****************/
INSERT INTO M14BANK(Kode,Keterangan ,Cabang ,Singkatan,NoAccount,VERSION, CREATED_BY, CREATED_ON)  
             VALUES(' ' ,'C A S H ' ,' '    ,'C A S H',' '      ,0      , 'COMPUTER',localtimestamp);                

/*****************
  Insert M41JPJK
******************/
INSERT INTO M41JPJK(Kode,Keterangan,                      Singkatan,  BiayaJabatan,PTKP,PersenPj1,PersenPj2,PersenPj3,PersenPj4,PersenPj5,PersenPj6,PersenPj7,LimitPj1,LimitPj2 ,LimitPj3, LimitPj4, LimitPj5,LimitPj6,LimitPj7,JnsForm1721,FlagKhusus,PersenPKP,Final,VERSION, CREATED_BY, CREATED_ON)   
            VALUES (' ', 'PEGAWAI TETAP (BULANAN/HARIAN)','PGW.TETAP' ,1,           1,   5,        15,       25,       30,       0,        0,        0,       50000000,250000000,500000000,0,        0,       0,       0,       'A1',       0,         100,      0,    0      , 'COMPUTER',   localtimestamp),
                   ('A', 'HONORARIUM'                    ,'HONORARIUM',0,           0,   5,        15,       25,       30,       0,        0,        0,       50000000,250000000,500000000,0,        0,       0,       0,       'B',        0,         100,      1,    0      , 'COMPUTER',   localtimestamp),
                   ('B', 'KOMISI'                        ,'KOMISI'    ,0,           0,   5,        15,       25,       30,       0,        0,        0,       50000000,250000000,500000000,0,        0,       0,       0,       'B',        0,         100,      1,    0      , 'COMPUTER',   localtimestamp),
                   ('C', 'EXPATRIAT/WPLN'                ,'EXPATRIAT' ,0,           0,   20,       0,        0,        0,        0,        0,        0,       0,       0,        0,        0,        0,       0,       0,       'B',        0,         100,      1,    0      , 'COMPUTER',   localtimestamp),
                   ('D', 'TENAGA AHLI'                   ,'TNG.AHLI'  ,0,           0,   15,       0,        0,        0,        0,        0,        0,       0,       0,        0,        0,        0,       0,       0,       'B',        0,         40,       1,    0      , 'COMPUTER',   localtimestamp),
                   ('X', 'PESANGON/TEBUSAN PENSIUN/THT'  ,'PESANGON'  ,0,           0,   0,        5,        15,       25,       0,        0,        0,       50000000,100000000,500000000,0,        0,       0,       0,       'B',        1,         100,      1,    0      , 'COMPUTER',   localtimestamp),
                   ('Y', 'MANTAN PEGAWAI'                ,'M.PEGAWAI' ,0,           0,   10,       15,       15,       15,       15,       0,        0,       17280000,25000000, 0,        0,        0,       0,       0,       'B',        0,         100,      1,    0      , 'COMPUTER',   localtimestamp);

/******************
  Insert M03DPPT
*******************/
--INSERT INTO M03DPPT(FlgDpPt,KdDpPt,NmDpPt, SkDpPt, JnDpPt,Persen,Nilai,KdCurr,Status,UsComp,NoLayar,Kolom,Pajak,NoAcc,Flag,UserId,  UpdDate,                        UpdTime,  Ws)
--            VALUES ('D',    'BNS', 'BONUS','BONUS','B',   1.00,  0,    'IDR',   '0',   'P',   0,      '8',  'D',  ' ',  '1', 'SYSLOG',CONVERT(CHAR(10),GETDATE(),121),GETDATE(),' ')
--INSERT INTO M03DPPT(FlgDpPt,KdDpPt,NmDpPt,    SkDpPt,    JnDpPt,Persen,Nilai,KdCurr,Status,UsComp,NoLayar,Kolom,Pajak,NoAcc,Flag,UserId,  UpdDate,                        UpdTime,  Ws)
--            VALUES ('D',    'INST','INSENTIF','INSENTIF','H',   0,     1,    'IDR', '0',    'P',  0      , '3',  'D',  ' ',  '1', 'SYSLOG',CONVERT(CHAR(10),GETDATE(),121),GETDATE(),' ')
--INSERT INTO M03DPPT(FlgDpPt,KdDpPt,NmDpPt,   SkDpPt,   JnDpPt,Persen,Nilai,KdCurr,Status,UsComp,NoLayar,Kolom,Pajak,NoAcc,Flag,UserId,  UpdDate,                        UpdTime,  Ws)
--            VALUES ('D',    'MEAL','U/MAKAN','U/MAKAN','H',   0,     10000,'IDR', '0',   'P',   0,      '3',  'D',  ' ',  ' ', 'SYSLOG',CONVERT(CHAR(10),GETDATE(),121),GETDATE(),' ')
--INSERT INTO M03DPPT(FlgDpPt,KdDpPt,NmDpPt,      SkDpPt,JnDpPt,Persen,Nilai,KdCurr,Status,UsComp,NoLayar,Kolom,Pajak,NoAcc,Flag,UserId,  UpdDate,                        UpdTime,  Ws)
--            VALUES ('D',    'UDIN','UANG DINAS','UDIN','B',   0,     1,    'IDR', '0',   'P',   0,      '3',  'D',  ' ',  ' ', 'SYSLOG',CONVERT(CHAR(10),GETDATE(),121),GETDATE(),' ')
INSERT INTO M03DPPT(M03_ID, FlgDpPt,KdDpPt,NmDpPt,                          SkDpPt,      JnDpPt,Persen,Nilai,KdCurr,Status,UsComp,NoLayar,Kolom,Pajak,NoAcc,Flag ,VERSION, CREATED_BY, CREATED_ON)
            VALUES (1     , 'D',    'BSAL','GAJI POKOK'                    ,'GAJI POKOK','B',   0,     0,    'IDR',   '0',   'K',   1,      '1',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (2     , 'D',    'INCB','INCENTIVE BULANAN'             ,'IN/BULANAN','B',   0,     0,    'IDR',   '0',   'K',   0,      '1',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (3     , 'D',    'INCT','INCENTIVE TAHUNAN'             ,'I/TAHUNAN' ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '1',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (4     , 'D',    'JHT', 'IURAN J.H.T'                   ,'IURAN JHT' ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '12', 'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (5     , 'D',    'JKK', 'IURAN J.K.K'                   ,'IURAN JKK' ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '5',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (6     , 'D',    'JKM', 'IURAN J.K.M'                   ,'IURAN JKM' ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '5',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (7     , 'D',    'JPK', 'IURAN J.P.K'                   ,'IURAN JPK' ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '5',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (8     , 'D',    'JUMK','TTL U.MKN LEMBUR'              ,'U/MKN LMBR','B',   0,     0,    'IDR',   '0',   'K',   0,      '3',  'D',  ' ',  '1',0      , 'COMPUTER',   localtimestamp),
		   (9     , 'D',    'LEMB','LEMBUR'                        ,'LEMBUR'    ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '3',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (10    , 'D',    'PESA','PESANGON/TEBUSAN PENSIUN/THT'  ,'PESANGON'  ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '3',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (11    , 'D',    'PESP','PESANGON PERATURAN'            ,'PSG PRTR'  ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '3',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (12    , 'D',    'PJK', 'TUNJANGAN PAJAK'               ,'T/PAJAK'   ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '2',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (13    , 'D',    'RPLG','RAPEL GAJI POKOK'              ,'RAPEL GAJI','B',   0,     0,    'IDR',   '0',   'K',   0,      '1',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (14    , 'D',    'RPLT','RAPEL THR'                     ,'RAPEL THR' ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '8',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (15    , 'D',    'THR', 'THR'                           , 'THR'      ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '8',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (16    , 'D',    'TK01','TUNJANGAN ISTRI'               ,'T/ISTRI'   ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '3',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (17    , 'D',    'TK02','TUNJANGAN ANAK'                ,'T/ANAK'    ,'B',   0,     0,    'IDR',   '0',   'K',   0,      '3',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (18    , 'D',    'JPMP','MANTAN PEGAWAI'                ,'MANPEG'    ,'B',   0,     1,    'IDR',   '0',   'K',   0,      '8',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (19    , 'D',    'INCO','INCENTIVE OVERNIGHT'           ,'INC.OVER'  ,'H',   0,     1,    'IDR',   '0',   'K',   0,      '3',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (20    , 'D',    'TRAL','TRANSPORT HR LIBUR'            ,'TRANS. LBR','H',   0,     1,    'IDR',   '0',   'K',   0,      '3',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (21    , 'P',    'JKK', 'POTONGAN  J.K.K'               ,'POT. JKK'  ,'B',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (22    , 'P',    'JKM', 'POTONGAN J.K.M'                ,'POT JKM'   ,'B',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (23    , 'P',    'JPK', 'POTONGAN  J.P.K'               ,'POT. JPK'  ,'B',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (24    , 'P',    'MGKR','POTONGAN MANGKIR'              ,'P/MANGKIR' ,'H',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (25    ,'P',    'PADV','PAYROLL ADVANCE'               ,'P/ADVANCE' ,'B',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (26    ,'P',    'PJK', 'PAJAK'                         ,'PAJAK'     ,'B',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (27    ,'P',    'PP',  'PINJAMAN PEGAWAI'              ,'P/PEG'     ,'B',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (28    ,'P',    'RPJK','PAJAK DIBAYAR PERUSAHAAN'      ,'PJ.DIBAYAR','B',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (29    ,'P',    'SPSI','IURAN SPSI'                    ,'I/SPSI'    ,'B',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (30    ,'P',    'PTHP','POTONGAN PEMBULATAN THP'       ,'P/R THP'   ,'B',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp),
		   (31    ,'P',    'PDTP','PPH PS21 DITANGGUNG PEMERINTAH','PPH 21 DTP','B',   0,     0,    'IDR',   '0',   'K',   0,      ' ',  'D',  ' ',  ' ',0      , 'COMPUTER',   localtimestamp);

ALTER SEQUENCE m03dppt_m03_id_seqRESTART WITH 32;		   
/******************
  Insert M18JAMS
*******************/

INSERT INTO M18JAMS(Progrm,LevelJKK,Perush,Pgwi,PgwiK,UpahMax,   NoAcc,VERSION, CREATED_BY, CREATED_ON)  
            VALUES ('JHT', 0,       3.700, 2.00,0,    0,         ' ',  0      , 'COMPUTER',   localtimestamp),
		   ('JKK', 1,       0.240, 0,   0,    0,         ' ',  0      , 'COMPUTER',   localtimestamp),
		   ('JKK', 2,       0.540, 0,   0,    0,         ' ',  0      , 'COMPUTER',   localtimestamp),
		   ('JKK', 3,       0.890, 0,   0,    0,         ' ',  0      , 'COMPUTER',   localtimestamp),
		   ('JKK', 4,       1.270, 0,   0,    0,         ' ',  0      , 'COMPUTER',   localtimestamp),
		   ('JKK', 5,       1.740, 0,   0,    0,         ' ',  0      , 'COMPUTER',   localtimestamp),
		   ('JKM', 0,       0.300, 0,   0,    0,         ' ',  0      , 'COMPUTER',   localtimestamp),
		   ('JPK', 0,       0,     3.00,6.000,1000000.00,' ',  0      , 'COMPUTER',   localtimestamp);
            
-- Isi Data Temporary File
DELETE FROM TABELTEMP;
--
INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W01','(NIP    	Char(10),
            Nama	CHAR(25),
            Cabang      CHAR(4),
            Nama_Cabang CHAR(20),
            TglPayr     DATETIME,
            TglKeluar   DATETIME)');
--
---- Untuk Validasi Proses Payroll
INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W0B','(
        NIP               CHAR(10),
        Nama              CHAR(25), 
        Tanggal           DATETIME,
        ErrRid            CHAR(1))');
--
-- Untuk Validasi Proses Recov UMP
INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W0C','(
        NIP               CHAR(10),
        Nama              CHAR(25), 
	StsPajak	  CHAR(2),
	Harian		  CHAR(1),	
        TglKeuar          DATETIME,
        PajakDP		  DECIMAL(15,2))');

-- Untuk Validasi Closing
INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W0D','(
        NIP               CHAR(10),
        Nama              CHAR(25),
        KdCaba            CHAR(4),
        NmCaba            CHAR(20),
        Akhir             CHAR(1),
        TglPayr           DATETIME,
        TglKeluar         DATETIME,
        UserID            CHAR(10),
	UpdDate		  DATETIME,
 	UpdTime		  DATETIME)');

-- Temporary Export Bank Transfer ke TXT
INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W0E','(
        TXT				  CHAR(70))');

-- Temporary Export Bank Transfer ke TXT (LENGTH 200 BANK OF TOKYO) 
INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W0F','(
        TXT				  CHAR(200))');

INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W0G','(teks VARCHAR(8000))');

---  Isi Kode Blank dan AFPR di Master Mutasi
INSERT INTO M24MUTR(KdMutr,Nama            ,Singkatan,VERSION, CREATED_BY, CREATED_ON) 
             VALUES('AFPR','AFPR '         ,'AFPR'   ,0      , 'COMPUTER',   localtimestamp),
                   ('   ' ,'Not Available ','N/A'    ,0      , 'COMPUTER',   localtimestamp);
--
INSERT INTO M16ALKL(Kode,Keterangan     ,Singkatan,FlagMati,VERSION, CREATED_BY, CREATED_ON) 
             VALUES(' ' ,'Not Available','N/A'    ,0       ,0      , 'COMPUTER',   localtimestamp);
--
INSERT INTO M21UMRE(Daerah,Keterangan     ,Singkatan,UMR,VERSION, CREATED_BY, CREATED_ON, TglMulai)
             VALUES(' '   ,'Not Available','N/A'    ,0  ,0      , 'COMPUTER',   localtimestamp, CURRENT_DATE) ;

---SELALU DI EDIT SESUAI DENGAN CUSTOMER
--INSERT INTO VerHist
--VALUES('5.62.34B.002.000',GETDATE())
--- UNTUK OTSUKA
--insert into r07flda values('01',0,0,'D','BSAL','g',getdate(), getdate(),' ')

insert into m54para 
values('1','GROUP PRODUCT GROUP',0      , 'COMPUTER',   localtimestamp,'COMPUTER',   localtimestamp) ;


------------------belum             
/*******************
 Isi data M19HSLG
********************/
INSERT INTO M19HSLG(M19_ID, TipeLap,   FlgDpPt,   NomFormat, Keterangan          ,Recs   ,   VERSION, CREATED_BY, CREATED_ON)
             VALUES(1     , '1'    ,   'D'    ,   1        , 'GAJI POKOK'  ,        1    ,   0      , 'COMPUTER',   localtimestamp),
                   (2     , '1'    ,   'D'    ,   2        , 'J.H.T     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp),
                   (3     , '1'    ,   'D'    ,   3        , 'J.K.K     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp),
                   (4     , '1'    ,   'D'    ,   4        , 'J.K.M     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (5     , '1'    ,   'D'    ,   5        , 'J.P.K     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (6     , '1'    ,   'D'    ,   6        , 'U/MKN LEMBUR' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (7     , '1'    ,   'D'    ,   7        , 'LEMBUR      ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (8     , '1'    ,   'D'    ,   8        , 'T/PAJAK     ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (9     , '1'    ,   'D'    ,   9        , 'T.H.R       ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (10    , '1'    ,   'D'    ,   10       , 'T/ ISTRI    ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (11    , '1'    ,   'D'    ,   11       , 'T/ ANAK     ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (12    , '1'    ,   'D'    ,   12       , 'PESANGON    ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (13    , '1'    ,   'D'    ,   13       , 'BONUS       ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (14    , '1'    ,   'D'    ,   14       , 'INCENTIF    ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (15    , '1'    ,   'D'    ,   15       , 'U/MAKAN & TRANSPORT',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (16    , '1'    ,   'D'    ,   16       , 'MANTAN PEGAWAI     ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (17    , '1'    ,   'P'    ,   1        , 'P/J.K.K            ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (18    , '1'    ,   'P'    ,   2        , 'P/J.K.M            ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (19    , '1'    ,   'P'    ,   4        , 'P/ MANGKIR         ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (20    , '1'    ,   'P'    ,   5        , 'PAYROL ADVANCE     ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (21    , '1'    ,   'P'    ,   6        , 'P/ PAJAK           ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (22    , '1'    ,   'P'    ,   7        , 'S.P.S.I            ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (23    , '1'    ,   'P'    ,   8        , 'PAJAK DIBAYAR PRSHN',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (24    , '1'    ,   'P'    ,   9        , 'PINJAMAN PEGAWAI   ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (25    , '1'    ,   'P'    ,   10       , 'BUNGA PINJAMAN     ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
--
                   (26    , '2'    ,   'D'    ,   1        , 'GAJI POKOK'  ,        1    ,   0      , 'COMPUTER',   localtimestamp),
                   (27    , '2'    ,   'D'    ,   2        , 'J.H.T     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp),
                   (28    , '2'    ,   'D'    ,   3        , 'J.K.K     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp),
                   (29    , '2'    ,   'D'    ,   4        , 'J.K.M     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (30    , '2'    ,   'D'    ,   5        , 'J.P.K     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (31    , '2'    ,   'D'    ,   6        , 'U/MKN LEMBUR' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (32    , '2'    ,   'D'    ,   7        , 'LEMBUR      ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (33    , '2'    ,   'D'    ,   8        , 'T/PAJAK     ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (34    , '2'    ,   'D'    ,   9        , 'T.H.R       ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (35    , '2'    ,   'D'    ,   10       , 'T/ ISTRI    ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (36    , '2'    ,   'D'    ,   11       , 'T/ ANAK     ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (37    , '2'    ,   'D'    ,   12       , 'PESANGON    ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (38    , '2'    ,   'D'    ,   13       , 'BONUS       ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (39    , '2'    ,   'D'    ,   14       , 'INCENTIF    ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (40    , '2'    ,   'D'    ,   15       , 'U/MAKAN & TRANSPORT',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (41    , '2'    ,   'D'    ,   16       , 'MANTAN PEGAWAI     ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (42    , '2'    ,   'P'    ,   1        , 'P/J.K.K            ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (43    , '2'    ,   'P'    ,   2        , 'P/J.K.M            ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (44    , '2'    ,   'P'    ,   4        , 'P/ MANGKIR         ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (45    , '2'    ,   'P'    ,   5        , 'PAYROL ADVANCE     ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (46    , '2'    ,   'P'    ,   6        , 'P/ PAJAK           ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (47    , '2'    ,   'P'    ,   7        , 'S.P.S.I            ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (48    , '2'    ,   'P'    ,   8        , 'PAJAK DIBAYAR PRSHN',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (49    , '2'    ,   'P'    ,   9        , 'PINJAMAN PEGAWAI   ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (50    , '2'    ,   'P'    ,   10       , 'BUNGA PINJAMAN     ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
--
                   (51    , '3'    ,   'D'    ,   1        , 'GAJI POKOK'  ,        1    ,   0      , 'COMPUTER',   localtimestamp),
                   (52    , '3'    ,   'D'    ,   2        , 'J.H.T     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp),
                   (53    , '3'    ,   'D'    ,   3        , 'J.K.K     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp),
                   (54    , '3'    ,   'D'    ,   4        , 'J.K.M     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (55    , '3'    ,   'D'    ,   5        , 'J.P.K     '  ,        1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (56    , '3'    ,   'D'    ,   6        , 'U/MKN LEMBUR' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (57    , '3'    ,   'D'    ,   7        , 'LEMBUR      ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (58    , '3'    ,   'D'    ,   8        , 'T/PAJAK     ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (59    , '3'    ,   'D'    ,   9        , 'T.H.R       ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (60    , '3'    ,   'D'    ,   10       , 'T/ ISTRI    ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (61    , '3'    ,   'D'    ,   11       , 'T/ ANAK     ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (62    , '3'    ,   'D'    ,   12       , 'PESANGON    ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (63    , '3'    ,   'D'    ,   13       , 'BONUS       ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (64    , '3'    ,   'D'    ,   14       , 'INCENTIF    ' ,       1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (65    , '3'    ,   'D'    ,   15       , 'U/MAKAN & TRANSPORT',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (66    , '3'    ,   'D'    ,   16       , 'MANTAN PEGAWAI     ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (67    , '3'    ,   'P'    ,   1        , 'P/J.K.K            ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (68    , '3'    ,   'P'    ,   2        , 'P/J.K.M            ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (69    , '3'    ,   'P'    ,   4        , 'P/ MANGKIR         ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (70    , '3'    ,   'P'    ,   5        , 'PAYROL ADVANCE     ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (71    , '3'    ,   'P'    ,   6        , 'P/ PAJAK           ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (72    , '3'    ,   'P'    ,   7        , 'S.P.S.I            ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (73    , '3'    ,   'P'    ,   8        , 'PAJAK DIBAYAR PRSHN',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (74    , '3'    ,   'P'    ,   9        , 'PINJAMAN PEGAWAI   ',  1    ,   0      , 'COMPUTER',   localtimestamp), 
                   (75    , '3'    ,   'P'    ,   10       , 'BUNGA PINJAMAN     ',  1    ,   0      , 'COMPUTER',   localtimestamp);

 
/***********************
  Insert Table M20DSLG
************************/
INSERT INTO M20DSLG(m20_id,TipeLap,FlgDpPt,NomFormat,KdDpPt,FlgAngs, m19_id, m03_id)
             VALUES(1     ,'1'    ,'D'    ,1        ,'BSAL',' '    , 1     , 1     ),
                   (2     ,'1'    ,'D'    ,2        ,'JHT' ,' '    , 2     , 4     ),             
                   (3     ,'1'    ,'D'    ,3        ,'JKK' ,' '    , 3     , 5     ),             
                   (4     ,'1'    ,'D'    ,4        ,'JKM' ,' '    , 4     , 6     ),             
                   (5     ,'1'    ,'D'    ,5        ,'JPK' ,' '    , 5     , 7     ),             
                   (6     ,'1'    ,'D'    ,6        ,'JUMK',' '    , 6     , 8     ),             
                   (7     ,'1'    ,'D'    ,7        ,'LEMB',' '    , 7     , 9     ),             
                   (8     ,'1'    ,'D'    ,8        ,'PJK' ,' '    , 8     , 12    ),             
                   (9     ,'1'    ,'D'    ,9        ,'THR' ,' '    , 9     , 15    ),             
                   (10    ,'1'    ,'D'    ,10       ,'TK01',' '    , 10    , 16    ),             
                   (11    ,'1'    ,'D'    ,11       ,'TK02',' '    , 11    , 17    ),             
                   (12    ,'1'    ,'D'    ,12       ,'PESA',' '    , 12    , 18    ),             
--                   (13    ,'1'    ,'D'    ,13       ,'BNS' ,' '    , 13    , 18    ),             
                   (14    ,'1'    ,'D'    ,14       ,'INCB',' '    , 14    , 2     ),             
                   (15    ,'1'    ,'D'    ,14       ,'INCT',' '    , 14    , 3     ),             
                   (16    ,'1'    ,'D'    ,14       ,'INCT',' '    , 14    , 19    ),             
                   (17    ,'1'    ,'D'    ,15       ,'TRAL',' '    , 15    , 20    ),             
                   (18    ,'1'    ,'D'    ,16       ,'JPMP',' '    , 16    , 18    ),             
                   (19    ,'1'    ,'P'    ,1        ,'JKK' ,' '    , 17    , 21    ),             
                   (20    ,'1'    ,'P'    ,2        ,'JKM' ,' '    , 18    , 22    ),      
INSERT INTO M20DSLG(TipeLap,FlgDpPt,NomFormat,KdDpPt,FlgAngs)
             VALUES('1'   ,'D'     ,15       ,'BNS ',' '),
		   ('3'   ,'D'     ,13       ,'BNS ',' '),
		   ('1'   ,'D'     ,1        ,'BSAL',' '),
		   ('2'   ,'D',1  ,'BSAL',' '),
		   ('3','D',1  ,'BSAL',' '),
		   ('1','D',20 ,'INST',' '),
		   ('1','D',2  ,'JHT ',' '),
		   ('2','D',2  ,'JHT ',' '),
		   ('3','D',2  ,'JHT ',' '),
		   ('1','D',3  ,'JKK ',' '),
		   ('2','D',3  ,'JKK ',' '),
		   ('3','D',3  ,'JKK ',' '),
		   ('1','D',4  ,'JKM ',' '),
		   ('2','D',4  ,'JKM ',' '),
		   ('3','D',4  ,'JKM ',' '),
		   ('1','D',5  ,'JPK ',' '),
		   ('2','D',5  ,'JPK ',' '),
		   ('3','D',5  ,'JPK ',' '),
		   ('1','D',24 ,'JPMP',' '),
		   ('1','D',6  ,'JUMK',' '),
		   ('2','D',6  ,'JUMK',' '),
		   ('3','D',6  ,'JUMK',' '),
		   ('1','D',7  ,'LEMB',' '),
		   ('2','D',7  ,'LEMB',' '),
		   ('3','D',7  ,'LEMB',' '),
		   ('1','D',22 ,'MEAL',' '),
		   ('1','D',12 ,'PESA',' '),
		   ('2','D',12 ,'PESA',' '),
		   ('3','D',12 ,'PESA',' '),
		   ('1','D',8  ,'PJK ',' '),
		   ('2','D',8  ,'PJK ',' '),
		   ('3','D',8  ,'PJK ',' '),
		   ('1','D',9  ,'THR ',' '),
		   ('2','D',9  ,'THR ',' '),
		   ('3','D',9  ,'THR ',' '),
		   ('1','D',10 ,'TK01',' '),
		   ('2','D',10 ,'TK01',' '),
		   ('3','D',10 ,'TK01',' '),
		   ('1','D',11 ,'TK02',' '),
		   ('2','D',11 ,'TK02',' '),
		   ('3','D',11 ,'TK02',' '),
		   ('1','D',23 ,'UDIN',' '),
		   ('1','P',1  ,'JKK ',' '),
		   ('2','P',1  ,'JKK ',' '),
		   ('3','P',1  ,'JKK ',' '),
		   ('1','P',2  ,'JKM ',' '),
		   ('2','P',2  ,'JKM ',' '),
		   ('3','P',2  ,'JKM ',' '),
		   ('2','P',3  ,'JPK ',' '),
		   ('3','P',3  ,'JPK ',' '),
		   ('1','P',4  ,'MGKR',' '),
		   ('2','P',4  ,'MGKR',' '),
		   ('3','P',4  ,'MGKR',' '),
		   ('1','P',5  ,'PADV',' '),
		   ('2','P',5  ,'PADV',' '),
		   ('3','P',5  ,'PADV',' '),
		   ('1','P',6  ,'PJK ',' '),
		   ('2','P',6  ,'PJK ',' '),
		   ('3','P',6  ,'PJK ',' '),
		   ('1','P',24 ,'PP  ','A'),
		   ('1','P',25 ,'PP  ','B'),
		   ('1','P',8  ,'RPJK',' '),
		   ('2','P',8  ,'RPJK',' '),
		   ('3','P',8  ,'RPJK',' '),
		   ('1','P',7  ,'SPSI',' '),
		   ('2','P',7  ,'SPSI',' '),
		   ('3','P',7  ,'SPSI',' ');

