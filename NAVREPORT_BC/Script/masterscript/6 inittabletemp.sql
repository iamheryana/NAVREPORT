DELETE FROM TABELTEMP;
--
INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W01','(NIP    	Char(10),
            Nama	CHAR(25),
            Cabang      CHAR(4),
            Nama_Cabang CHAR(20),
            TglPayr     DATE,
            TglKeluar   DATE)');
--
---- Untuk Validasi Proses Payroll
INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W0B','(
        NIP               CHAR(10),
        Nama              CHAR(25), 
        Tanggal           DATE,
        ErrRid            CHAR(1))');
--
-- Untuk Validasi Proses Recov UMP
INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W0C','(
        NIP               CHAR(10),
        Nama              CHAR(25), 
	StsPajak	  CHAR(2),
	Harian		  CHAR(1),	
        TglKeuar          DATE,
        PajakDP		  DECIMAL(15,2))');

-- Untuk Validasi Closing
INSERT INTO TABELTEMP(TMPCODE,COLDEF)
VALUES('W0D','(
        NIP               CHAR(10),
        Nama              CHAR(25),
        KdCaba            CHAR(4),
        NmCaba            CHAR(20),
        Akhir             CHAR(1),
        TglPayr           DATE,
        TglKeluar         DATE,
        UserID            CHAR(10),
	UpdDate		  DATE,
 	UpdTime		  DATE)');

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

