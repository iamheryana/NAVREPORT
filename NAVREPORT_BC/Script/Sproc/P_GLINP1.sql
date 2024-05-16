/****************************************
Name sprocs	: P_GLINP1
Create by	: wati
Date		: 13-02-2003
Description	: Proses Merge
*****************************************/
DROP  FUNCTION P_GLINP1 (l_TglFr    DATE,
                        l_TglTo      DATE,
                        l_SubAcc1    VARCHAR(1),
                        l_SubAcc2    VARCHAR(1),
                        l_SubAcc3    VARCHAR(1),
                        l_SubAcc4    VARCHAR(1),
                        l_Panjang1   INT,
                        l_Panjang2   INT,
                        l_Panjang3   INT,
                        l_Panjang4   INT,
                        l_Blank1     VARCHAR(1),
                        l_Blank2     VARCHAR(1),
                        l_Blank3     VARCHAR(1),
                        l_Blank4     VARCHAR(1),
            			l_UserName   VARCHAR(12), 
                        l_MyPass     VARCHAR(128),
                        l_UserId     INT);
--
CREATE FUNCTION P_GLINP1 (l_TglFr    DATE,
                        l_TglTo      DATE,
                        l_SubAcc1    VARCHAR(1),
                        l_SubAcc2    VARCHAR(1),
                        l_SubAcc3    VARCHAR(1),
                        l_SubAcc4    VARCHAR(1),
                        l_Panjang1   INT,
                        l_Panjang2   INT,
                        l_Panjang3   INT,
                        l_Panjang4   INT,
                        l_Blank1     VARCHAR(1),
                        l_Blank2     VARCHAR(1),
                        l_Blank3     VARCHAR(1),
                        l_Blank4     VARCHAR(1),
            			l_UserName   VARCHAR(12), 
                        l_MyPass     VARCHAR(128),
                        l_UserId     INT)			
RETURNS VOID 
AS $$
---
DECLARE l_FZ1Kode       VARCHAR(2);
        l_FZ2NoAccount  VARCHAR(32); 
        l_mlPindah      VARCHAR(1); 
---
BEGIN 
    --- Create Temp Tabel untuk S02Dgaj
    CREATE TEMP TABLE l_TEMPS02 
    (  TglPayr  DATE,
       NIP	    VARCHAR(10),
       FlgDpPt  VARCHAR(1),
       KdDpPt   VARCHAR(4),
       FlgAngs  VARCHAR(1),
       Nilai    DECIMAL(15,2)
    ) ON COMMIT DROP ;
    --
    -- Isi data dari S02DGAJ
    INSERT INTO l_TEMPS02
    SELECT S01.TglPayr,S01.NIP,FlgDpPt,KdDpPt,FlgAngs,fn_KPusat(S02.NIP,S02.EncNilai,l_Mypass) ::DECIMAL(15,2)
    FROM S02DGAJ S02
    INNER JOIN S01HGAJ S01 ON S01.TglPayr=S02.TglPayr AND S01.NIP=S02.NIP 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON S01.KdGlng=VSL.Gol_Kode AND S01.KdCaba=VSL.Cab_Kode
    WHERE S01.TglPayr BETWEEN l_TglFr AND l_TglTo; 
    --
    SELECT Kode INTO l_FZ1Kode
    FROM FZ1FLDA;
    ---
    l_FZ2NoAccount := ' '; 
    
    SELECT NoAccount, SUBSTR(STRINGFLAG,2,1) 
    INTO   l_FZ2NoAccount, l_mlPindah
    FROM FZ2FLDA
    WHERE Kode = l_FZ1Kode; 
    --
    -- insert Blank Z01PTGL Header dengan Account FZ2
    INSERT INTO Z01PTGL (journaltype, kdcabang, ketcabang, description, applydate, document, accountcode, 
            currency, debit, credit, description2, hold, intercompany, reversing, repeating, recurring, 
            captureflg, version, created_by, created_on)
    SELECT   '', Hdr.KdCaba, Hdr.NmCaba, '', Hdr.TglPayr, TO_CHAR(Hdr.TglPayr,'YYYYMMDD') AS Document,
             CASE WHEN l_mlPindah='1' THEN HDR.NoAccount ELSE l_FZ2NoAccount END,  
             'IDR',  0,  0, '', 0, 0, 0, 0, 0, 0, 0, l_UserName, localtimestamp
    FROM 
    ( -- Hdr 
    SELECT DISTINCT S01.KdCaba, S01.TglPayr, substr(M08.NmCaba,1,20) AS NmCaba,
	       COALESCE(M08.NoAccount,' ') AS NOACCOUNT 
    FROM S01HGAJ S01
    INNER JOIN M08HCAB M08 ON M08.KdCaba = S01.KdCaba
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON S01.KdGlng=VSL.Gol_Kode AND S01.KdCaba=VSL.Cab_Kode
    WHERE S01.FlagM<>'P' AND  (S01.TglPayr BETWEEN l_TglFr AND l_TglTo)
    ) Hdr  
    LEFT JOIN Z01PTGL Z01 ON Z01.KdCabang = Hdr.KdCaba AND Z01.Document =TO_CHAR(Hdr.TglPayr, 'YYYYMMDD') AND 
                             Z01.AccountCode = l_FZ2NoAccount
    WHERE Z01.KdCabang IS NULL AND Z01.Document IS NULL AND Z01.AccountCode IS NULL ;
    --
    -- Insert Blank Z01PTGL dari Dari detail -- <> PESP PEGGY 2006 01 04
    INSERT INTO Z01PTGL (journaltype, kdcabang, ketcabang, description, applydate, document, accountcode, 
            currency, debit, credit, description2, hold, intercompany, reversing, repeating, recurring, 
            captureflg, version, created_by, created_on)
    SELECT   '', Dtl.KdCaba, Dtl.NmCaba, '', Dtl.TglPayr, TO_CHAR(Dtl.TglPayr, 'YYYYMMDD') AS Document, Dtl.NoAccount,  
             'IDR', 0, 0, '', 0, 0, 0, 0, 0, 0, 0, l_UserName, localtimestamp
    FROM 
    (-- Dtl
    SELECT DISTINCT S01.KdCaba,S01.TglPayr, substr(M08.NmCaba,1,20) AS NmCaba,
           CASE l_SubAcc1 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang1) 		  -- Dasar Unit Kerja 	
                          WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang1) 	  -- Dasar Unit Usaha
                          WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang1) 	  -- Dasar Pendapatan/Potongan
                          WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang1) 		   -- Dasar None
                          WHEN '5' THEN (CASE WHEN l_Blank1='1' THEN rpad(' ', l_Panjang1, ' ') 
                                         ELSE SUBSTR('00000000',1,l_Panjang1) END ) END
           || 
           CASE l_SubAcc2 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang2) 		  -- Dasar Unit Kerja 	
                          WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang2) 	  -- Dasar Unit Usaha
                          WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang2) 	  -- Dasar Pendapatan/Potongan
                          WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang2) 		  -- Dasar None
                          WHEN '5' THEN (CASE WHEN l_Blank2='1' THEN rpad(' ', l_Panjang2, ' ') 
                                         ELSE SUBSTR('00000000',1,l_Panjang2) END ) END
           || 
           CASE l_SubAcc3 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang3)           -- Dasar Unit Kerja 	
                          WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang3)        -- Dasar Unit Usaha
                          WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang3)        -- Dasar Pendapatan/Potongan
                          WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang3)            -- Dasar None
                          WHEN '5' THEN (CASE WHEN l_Blank3='1' THEN rpad(' ', l_Panjang3, ' ') 
                                         ELSE SUBSTR('00000000',1,l_Panjang3) END ) END
           ||
           CASE l_SubAcc4 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang4) 		  -- Dasar Unit Kerja 	
                          WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang4) 	  -- Dasar Unit Usaha
                          WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang4) 	  -- Dasar Pendapatan/Potongan
                          WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang4) 		  -- Dasar None
                          WHEN '5' THEN (CASE WHEN l_Blank4='1' THEN rpad(' ', l_Panjang4, ' ') 
                                         ELSE SUBSTR('00000000',1,l_Panjang4) END ) END AS NoAccount
    FROM S01HGAJ S01
    INNER JOIN S02DGAJ S02 ON S02.TglPayr =S01.TglPayr AND S02.NIP=S01.NIP
    INNER JOIN M08HCAB M08 ON M08.KdCaba = S01.KdCaba
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.KdUKer
    INNER JOIN M02UUSA M02 ON M02.Kode=S01.KdUUsa
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON S01.KdGlng=VSL.Gol_Kode AND S01.KdCaba=VSL.Cab_Kode
    WHERE S01.FlagM<>'P' AND COALESCE(S02.FlgNonGL,0)=0 AND (S01.TglPayr BETWEEN l_TglFr AND l_TglTo) 
    ) Dtl
    LEFT JOIN Z01PTGL Z01 ON Z01.KdCabang=Dtl.KdCaba AND Z01.Document=TO_CHAR(Dtl.TglPayr, 'YYYYMMDD') AND 
                             Z01.AccountCode = Dtl.NoAccount
    WHERE Z01.KdCabang IS NULL AND Z01.Document IS NULL AND Z01.AccountCode IS NULL; 
    
    -- Insert Blank Z01PTGL dari Dari detail --  PESP PEGGY 2006 01 04
    INSERT INTO Z01PTGL (journaltype, kdcabang, ketcabang, description, applydate, document, accountcode, 
            currency, debit, credit, description2, hold, intercompany, reversing, repeating, recurring, 
            captureflg, version, created_by, created_on)
    SELECT   '', Dtl.KdCaba, Dtl.NmCaba, '', Dtl.TglPayr, TO_CHAR(Dtl.TglPayr, 'YYYYMMDD') AS Document, Dtl.NoAccount,  
             'IDR', 0, 0, '', 0, 0, 0, 0, 0, 0, 0, l_UserName, localtimestamp
    FROM 
    (-- Dtl
    SELECT DISTINCT S01.KdCaba, S01.TglPayr, substr(M08.NmCaba,1,20) AS NmCaba,
				-- Sub Acc-1  -- Dasar Cabang
            CASE l_SubAcc1 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang1)                   -- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang1)               -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang1)               -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang1)                   -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank1='1' THEN rpad(' ', l_Panjang1, ' ') 
                                          ELSE SUBSTR('00000000',1,l_Panjang1) END ) END
            || 
            CASE l_SubAcc2 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang2)               -- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang2) 		  -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang2) 		  -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang2)               -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank2='1' THEN rpad(' ', l_Panjang2, ' ') 
                                          ELSE SUBSTR('00000000',1,l_Panjang2) END ) END
            || 
            CASE l_SubAcc3 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang3)               -- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang3) 		  -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang3) 		  -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang3)               -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank3='1' THEN rpad(' ', l_Panjang3, ' ') 
                                          ELSE SUBSTR('00000000',1,l_Panjang3) END ) END
            || 
            CASE l_SubAcc4 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang4)   -- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang4)                -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang4)                -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang4)          		  -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank4='1' THEN rpad(' ', l_Panjang4, ' ') 
                                          ELSE SUBSTR('00000000',1,l_Panjang4) END ) END AS NoAccount
    FROM S01HGAJ S01
    INNER JOIN S02DGAJ S02 ON S02.TglPayr =S01.TglPayr AND S02.NIP=S01.NIP
    INNER JOIN M08HCAB M08 ON M08.KdCaba = S01.KdCaba
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.KdUKer
    INNER JOIN M02UUSA M02 ON M02.Kode=S01.KdUUsa
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt='D' AND M03.KdDpPt='PESP' 
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON S01.KdGlng=VSL.Gol_Kode AND S01.KdCaba=VSL.Cab_Kode
    WHERE S01.FlagM<>'P' AND COALESCE(S02.FlgNonGL,0)=0 AND (S01.TglPayr BETWEEN l_TglFr AND l_TglTo) AND
		  S02.FLGDPPT = 'D' AND S02.KDDPPT = 'PESA' 
    ) Dtl
     LEFT JOIN Z01PTGL Z01 ON Z01.KdCabang=Dtl.KdCaba AND Z01.Document=TO_CHAR(Dtl.TglPayr, 'YYYYMMDD') AND
                              Z01.AccountCode = Dtl.NoAccount
     WHERE Z01.KdCabang IS NULL AND Z01.Document IS NULL AND Z01.AccountCode IS NULL; 
     --
     --  Update Debet/Credit Z01PTGL Dari Detail
     --
    UPDATE Z01PTGL Z01
    SET Debit = COALESCE(Debit,0) + Dtl1.Debet,
        Credit= COALESCE(Credit,0)+ Dtl1.Kredit
    FROM --Z01PTGL Z01
    (--DTL1
    SELECT DTL.KdCaba, DTL.Document, DTL.NoAccount, SUM(DTL.Debet) AS Debet, SUM(DTL.Kredit) AS Kredit
    FROM
    ( -- DTL
    SELECT  CASE WHEN S02.FlgDpPt='D' THEN -- Jika type Pendapatan
              	 CASE WHEN S02.KDDPPT<>'PESA' THEN 
                      CASE WHEN TS02.Nilai>0 THEN TS02.Nilai 
                      ELSE 0 END  
                 ELSE TS02.NILAI-COALESCE(T19.NILAIPERATURAN,0) END                  
            ELSE CASE WHEN TS02.Nilai<0 THEN  -- Jika type Potongan
                      TS02.Nilai*-1
                 ELSE 0 END                    
            END AS Debet,
        	CASE WHEN S02.FlgDpPt='D' THEN -- Jika type Pendapatan
                 CASE WHEN TS02.Nilai<0 THEN 
                      TS02.Nilai*-1
                 ELSE 0 END                    
            ELSE CASE WHEN TS02.Nilai>0 THEN -- Jika type Potongan
                     TS02.Nilai
                 ELSE 0 END                    
   	        END AS Kredit,
            S01.KdCaba, TO_CHAR(S01.TglPayr, 'YYYYMMDD') AS Document,
            CASE l_SubAcc1 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang1)      -- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang1)  -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang1)  -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang1)      -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank1='1' THEN rpad(' ', l_Panjang1, ' ') 
                                          ELSE SUBSTR('00000000',1,l_Panjang1) END ) END
            || 
    		CASE l_SubAcc2 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang2)     -- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang2) -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang2) -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang2)     -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank2='1' THEN rpad(' ', l_Panjang2, ' ') 
                                          ELSE SUBSTR('00000000',1,l_Panjang2) END ) END
            || 
            CASE l_SubAcc3 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang3) 		-- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang3)     -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang3)     -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang3)         -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank3='1' THEN rpad(' ', l_Panjang3, ' ') 
                                           ELSE SUBSTR('00000000',1,l_Panjang3) END ) END
            || 
            CASE l_SubAcc4 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang4)     	-- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang4)     -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang4)     -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang4)         -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank4='1' THEN rpad(' ', l_Panjang4, ' ') 
                                          ELSE SUBSTR('00000000',1,l_Panjang4) END ) END AS NoAccount
    FROM S01HGAJ S01
    INNER JOIN S02DGAJ S02 ON S02.TglPayr =S01.TglPayr AND S02.NIP=S01.NIP
    INNER JOIN l_TEMPS02 TS02 ON TS02.TglPayr=S02.TglPayr AND TS02.NIP=S02.NIP AND TS02.FlgDpPt=S02.FlgDpPt AND 
                                 TS02.KdDpPt=S02.KdDpPt AND TS02.FlgAngs=S02.FlgAngs
    INNER JOIN M08HCAB M08 ON M08.KdCaba = S01.KdCaba
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.KdUKer
    INNER JOIN M02UUSA M02 ON M02.Kode=S01.KdUUsa
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt=S02.FlgDpPt AND M03.KdDpPt=S02.KdDpPt
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON S01.KdGlng=VSL.Gol_Kode AND S01.KdCaba=VSL.Cab_Kode
    LEFT JOIN T19PESA T19 ON T19.NIP = S02.NIP 
    WHERE S01.FlagM<>'P' AND COALESCE(S02.FlgNonGL,0)=0 AND (S01.TglPayr BETWEEN l_TglFr AND l_TglTo) 
    ---------
    UNION ALL 
    ---------
    -- PESP DARI KODE PESA 
    SELECT 	CASE WHEN (S02.FLGDPPT='D' AND S02.KDDPPT= 'PESA') THEN
                      COALESCE(T19.NILAIPERATURAN,0) 
            ELSE 0 END AS Debet,
            CASE WHEN (S02.FLGDPPT='D' AND S02.KDDPPT='PESA') THEN 0 ELSE 0 END AS Kredit,
            S01.KdCaba, TO_CHAR(S01.TglPayr, 'YYYYMMDD') AS Document,
            CASE l_SubAcc1 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang1) -- Dasar Cabang
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang1) -- Dasar Unit Kerja 
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang1) -- Dasar Unit Usaha
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang1) -- Dasar Pendapatan/Potongan		  
                           WHEN '5' THEN (CASE WHEN l_Blank1='1' THEN rpad(' ', l_Panjang1, ' ') -- Dasar None
                                          ELSE SUBSTR('00000000',1,l_Panjang1) END ) END
            || 
            CASE l_SubAcc2 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang2) -- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang2) -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang2) -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang2) -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank2='1' THEN rpad(' ', l_Panjang2, ' ') 
                                          ELSE SUBSTR('00000000',1,l_Panjang2) END ) END
            || 
            CASE l_SubAcc3 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang3) -- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang3) -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang3) -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang3) -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank3='1' THEN rpad(' ', l_Panjang3, ' ') 
                                          ELSE SUBSTR('00000000',1,l_Panjang3) END )  END
            || 
    		CASE l_SubAcc4 WHEN '1' THEN SUBSTR(M08.NoAcc,1,l_Panjang4) -- Dasar Unit Kerja 	
                           WHEN '2' THEN SUBSTR(M17.NoAccount,1,l_Panjang4) -- Dasar Unit Usaha
                           WHEN '3' THEN SUBSTR(M02.NoAccount,1,l_Panjang4) -- Dasar Pendapatan/Potongan
                           WHEN '4' THEN SUBSTR(M03.NoAcc,1,l_Panjang4) -- Dasar None
                           WHEN '5' THEN (CASE WHEN l_Blank4='1' THEN rpad(' ', l_Panjang4, ' ') 
                                          ELSE SUBSTR('00000000',1,l_Panjang4) END )END AS NoAccount
    FROM S01HGAJ S01
    INNER JOIN S02DGAJ S02 ON S02.TglPayr =S01.TglPayr AND S02.NIP=S01.NIP
    INNER JOIN l_TEMPS02 TS02 ON TS02.TglPayr=S02.TglPayr AND TS02.NIP=S02.NIP AND TS02.FlgDpPt=S02.FlgDpPt AND 
                                 TS02.KdDpPt=S02.KdDpPt AND TS02.FlgAngs=S02.FlgAngs
    INNER JOIN M08HCAB M08 ON M08.KdCaba = S01.KdCaba
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.KdUKer
    INNER JOIN M02UUSA M02 ON M02.Kode=S01.KdUUsa
    INNER JOIN M03DPPT M03 ON M03.FlgDpPt='D' AND M03.KdDpPt='PESP'
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_UserId)) VSL ON S01.KdGlng=VSL.Gol_Kode AND S01.KdCaba=VSL.Cab_Kode
    LEFT JOIN T19PESA T19 ON T19.NIP = S02.NIP 
    WHERE S01.FlagM<>'P' AND COALESCE(S02.FlgNonGL,0)=0 AND (S01.TglPayr BETWEEN l_TglFr AND l_TglTo) AND  	
          S02.FLGDPPT = 'D' AND S02.KDDPPT =  'PESA'
    )DTL 
    GROUP BY DTL.KdCaba,DTL.Document,DTL.NoAccount
    )DTL1
    WHERE  DTL1.KdCaba=Z01.KdCabang AND DTL1.Document=Z01.Document AND DTL1.NoAccount=Z01.AccountCode;
    --
    -- isi data Account Lawan DARI FZ2FLDA
    --
    UPDATE Z01PTGL Z01
    SET Debit =XZ01.Debet,--Z01.Debit+XZ01.Debet,
        Credit=XZ01.Credit--Z01.Credit+XZ01.Credit
    FROM --Z01PTGL Z01
    (--XZ01
     SELECT TZ01.Document, TZ01.KdCabang, NoAccount,
            CASE WHEN TZ01.Selisih<0 THEN TZ01.Selisih*-1 ELSE 0 END AS Debet,
            CASE WHEN TZ01.Selisih>=0 THEN TZ01.Selisih   ELSE 0 END AS Credit
     FROM 
    (--TZ01 
     SELECT NoAccount, Document, KdCabang, SUM(Selisih) AS Selisih  
     FROM 
    (--TZ01
     SELECT CASE WHEN l_mlPindah = '1' THEN M08.NoAccount ELSE l_FZ2NoAccount END AS NoAccount,   --(SELECT TOP 1 NoAccount FROM FZ2FLDA),
            Z01.Document,   Z01.KdCabang, (Debit-Credit) AS Selisih
     FROM Z01PTGL Z01
     INNER JOIN M08HCAB M08 ON Z01.KdCabang = M08.KdCaba
     WHERE Z01.ApplyDate between l_TglFr AND l_TglTo AND
           KDCABANG IN (SELECT CAB_KODE FROM fn_SECLOGIN(l_UserId))
     ) TZ01
     GROUP BY TZ01.Document,TZ01.KdCabang, TZ01.NoAccount
     ) TZ01
     ) XZ01
     WHERE XZ01.Document=Z01.Document AND XZ01.KdCabang=Z01.KdCabang AND XZ01.NoAccount=Z01.AccountCode; 
     --
    -- Update File S01HGAJ
    UPDATE S01HGAJ S01 
    SET FlagM = 'P'
    FROM (SELECT * FROM fn_SECLOGIN(l_UserId)) VSL 
    WHERE TglPayr BETWEEN l_TglFr AND l_TglTo AND 
          S01.Kdglng=VSL.GOL_KODE AND S01.Kdcaba=VSL.CAB_KODE;
    --    
    --Perhitungan khusus untuk tempindo
    PERFORM P_Hitung_Fee (l_TglFr, l_TglTo,l_MyPass) ; 
    --
END;
$$ LANGUAGE plpgsql ;

/*
SELECT * from P_GLINP1( '2013-12-01'::DATE,'2013-12-31'::DATE,'4'::VARCHAR(1),'1'::VARCHAR(1),'2'::VARCHAR(1),'3'::VARCHAR(1),8,8,8,8,'1'::VARCHAR(1),'1'::VARCHAR(1),'1'::VARCHAR(1),'1'::VARCHAR(1),'ok', 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved', 1);
*/
