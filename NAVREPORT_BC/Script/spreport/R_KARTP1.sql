/****************************************
Name sprocs	: R_KARTP1
Create by	: Herz
Date		: 02-10-2001
Description	: Proses Untuk Report Kartu Slip Standard
*****************************************/
DROP FUNCTION R_KARTP1(
            l_Tahun     INT,
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
            l_FCab      VARCHAR(4),
            l_TCab      VARCHAR(4),
			l_MyPass 	VARCHAR(128),
			l_Usr_Id	INT);    
--
CREATE FUNCTION R_KARTP1(
            l_Tahun     INT,
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
            l_FCab      VARCHAR(4),
            l_TCab      VARCHAR(4),
			l_MyPass 	VARCHAR(128),
			l_Usr_Id	INT)     
--
RETURNS TABLE (OUTTahun		VARCHAR(4),
            OUTBulan        VARCHAR(2),
            OUTArea         VARCHAR(4),
            OUTSkArea       VARCHAR(10),
            OUTCabang       VARCHAR(4),
            OUTSkCabang     VARCHAR(10),
            OUTUnitUsaha	VARCHAR(4),
            OUTSkUUsa       VARCHAR(10),
            OUTKetUUsa      VARCHAR(20),
            OUTKdUker       VARCHAR(8),
            OUTSkUker       VARCHAR(10), 
            OUTGlng         VARCHAR(4), 
            OUTSkGlng       VARCHAR(10), 
            OUTKJab         VARCHAR(4), 
            OUTSkKjab       VARCHAR(10), 
            OUTJaba         VARCHAR(4), 
            OUTSkJaba       VARCHAR(10), 
            OUTKlas         VARCHAR(4), 
            OUTSkKlas       VARCHAR(10), 
            OUTNip          VARCHAR(10), 
            OUTNama         VARCHAR(25), 
            OUTJnsKlm       VARCHAR(1),
            OUTStsMed       VARCHAR(2),
            OUTFlagDppt     VARCHAR(1),
            OUTNomFormat	DECIMAL(3,0),
            OUTKeterangan	VARCHAR(20),
            OUTNilai       	DECIMAL(12),
            OUTPTKP         DECIMAL(15),
            OUTBln01        DECIMAL(12), 
            OUTBln02        DECIMAL(12),
            OUTBln03        DECIMAL(12),
            OUTBln04        DECIMAL(12),
            OUTBln05        DECIMAL(12),
            OUTBln06        DECIMAL(12),
            OUTBln07        DECIMAL(12),
            OUTBln08        DECIMAL(12),
            OUTBln09        DECIMAL(12),
            OUTBln10        DECIMAL(12),
            OUTBln11        DECIMAL(12),
            OUTBln12        DECIMAL(12)) 
--
AS $$
--
DECLARE l_S02TglPayr	DATE;
        l_S01kdArea     VARCHAR(4);
        l_S01kdCaba     VARCHAR(4);
        l_S01kdUUsa     VARCHAR(4);
        l_S01kdUker     VARCHAR(8);
        l_S01kdGlng     VARCHAR(4);
        l_S01kdKJab     VARCHAR(4);
        l_S01kdJaba     VARCHAR(4);
        l_S01kdKlas     VARCHAR(4);
        l_S01NIP        VARCHAR(10);
        l_S01Nama       VARCHAR(25);
        l_S01JnsKlmn	VARCHAR(1);
        l_S01StsPjk     VARCHAR(2);
        l_S02FlgDpPt	VARCHAR(1);
        l_M19NomFormat	DECIMAL(3,0);
        l_M01Singkatan	VARCHAR(10);
        l_M08SkCaba     VARCHAR(10);
        l_M02Singkatan	VARCHAR(10);
        l_M17Singkatan	VARCHAR(10);
        l_M12Singkatan	VARCHAR(10);
        l_M06Singkatan	VARCHAR(10);
        l_M04Singkatan	VARCHAR(10);
        l_M10Singkatan	VARCHAR(10);
        l_M19Keterangan	VARCHAR(20);
        l_M02Keterangan	VARCHAR(20);
        l_S02Nilai      DECIMAL(15,2);
        l_Bulan         Int;
        l_PTKP          DECIMAL(15,2);
        l_PTKPPay       DECIMAL(15,2);
        l_PTKPDep       DECIMAL(15,2);
        l_TunjAnk       DECIMAL(15,2);
        l_LOOP_W07      REFCURSOR; 
--
BEGIN
    -- Panggil sProc untuk bentuk Table Temp
    CREATE TEMP TABLE l_TEMP (
                Tahun		VARCHAR(4),
                Bulan		VARCHAR(2),
                Area		VARCHAR(4),
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
                Nip         VARCHAR(10), 
                Nama		VARCHAR(25), 
                JnsKlm		VARCHAR(1),
                StsMed		VARCHAR(2),
                FlagDppt	VARCHAR(1),
                NomFormat	DECIMAL(3,0),
                Keterangan	VARCHAR(20),
                Nilai       DECIMAL(12),
                PTKP		DECIMAL(15),
                Bln01		DECIMAL(12), 
                Bln02		DECIMAL(12),
                Bln03		DECIMAL(12),
                Bln04		DECIMAL(12),
                Bln05		DECIMAL(12),
                Bln06		DECIMAL(12),
                Bln07		DECIMAL(12),
                Bln08		DECIMAL(12),
                Bln09		DECIMAL(12),
                Bln10		DECIMAL(12),
                Bln11		DECIMAL(12),
                Bln12		DECIMAL(12)
                ) ON COMMIT DROP ;          
    --
    -- Ambil data di FZ1FLDA
    SELECT  PTKPPay, PTKPDep, TunjAnk INTO l_PTKPPay, l_PTKPDep, l_TunjAnk
    FROM FZ1FLDA; 
    --
    -- Mulai Cursor data
    OPEN l_LOOP_W07 FOR
    SELECT  S02.TglPayr,S01.kdArea,S01.kdCaba,S01.kdUUsa,S01.kdUker,S01.kdGlng,S01.kdKJab,
            S01.kdJaba,S01.kdKlas,S01.NIP,S01.Nama, VG.StsSIP/*S01.JnsKlmn*/, VG.StsPjk,S02.FlgDpPt,
            M19.NomFormat,M01.Singkatan,M08.SkCaba,M02.Singkatan,M17.Singkatan,M12.Singkatan,
            M06.Singkatan,M04.Singkatan,M10.Singkatan,M19.Keterangan,M02.Keterangan,
            fn_kpusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2)
    FROM M19HSLG M19
    INNER JOIN M20DSLG M20 ON M19.TipeLap=M20.TipeLap AND M19.FlgDpPt=M20.FlgDpPt AND M19.NomFormat=M20.NomFormat
    INNER JOIN S02DGAJ S02 ON S02.FlgDpPt=M19.FlgDpPt AND S02.KdDpPt =M20.KdDpPt AND S02.FlgAngs=M20.FlgAngs
    INNER JOIN S01HGAJ S01 ON S02.TglPayr=S01.TglPayr AND S02.NIP    =S01.NIP
    INNER JOIN V_HGAJ VG   ON FN_CARITGLPAYR(S02.NIP, S02.TglPayr) = VG.TglPayr AND S02.NIP = VG.NIP
    INNER JOIN M01AREA M01 ON M01.Kode=S01.kdArea
    INNER JOIN M08HCAB M08 ON M08.KdCaba=S01.kdCaba
    INNER JOIN M02UUSA M02 ON M02.Kode=S01.kdUUsa
    INNER JOIN M17UKER M17 ON M17.KdUker=S01.kdUker
    INNER JOIN M12HGOL M12 ON M12.Kode =S01.kdGlng
    INNER JOIN M06HKJB M06 ON M06.Kode=S01.kdKJab
    INNER JOIN M04HJAB M04 ON M04.Kode =S01.kdJaba
    INNER JOIN M10KLAS M10 ON M10.Kode=S01.kdKlas
    INNER JOIN (SELECT * FROM fn_SECLOGIN(l_Usr_Id)) VSL ON S01.Kdglng=VSL.GOL_KODE AND S01.Kdcaba=VSL.CAB_KODE
    WHERE M19.TipeLap='3' AND EXTRACT(YEAR FROM S01.TglPayr)=l_Tahun AND
          (S01.NIP BETWEEN l_NIPFr AND l_NIPTo) AND (S01.KdCaba BETWEEN l_FCab AND l_TCab) ;
        --      
        <<l_LOOP_W07>> 
        LOOP 
        FETCH l_LOOP_W07 
        INTO l_S02TglPayr, l_S01kdArea, l_S01kdCaba, l_S01kdUUsa, l_S01kdUker, l_S01kdGlng, l_S01kdKJab,
             l_S01kdJaba, l_S01kdKlas, l_S01NIP, l_S01Nama, l_S01JnsKlmn, l_S01StsPjk, l_S02FlgDpPt,
             l_M19NomFormat, l_M01Singkatan, l_M08SkCaba, l_M02Singkatan, l_M17Singkatan, l_M12Singkatan,
             l_M06Singkatan, l_M04Singkatan, l_M10Singkatan, l_M19Keterangan, l_M02Keterangan, l_S02Nilai;
        --        
        IF NOT FOUND THEN
            EXIT ;
        END IF;
        --      
        -- Ambil Nilai PTKP
        l_PTKP := fn_GetPTKP(l_S01NIP,l_S01JnsKlmn,l_PTKPPay,l_PTKPDep,l_S01StsPjk);
        l_Bulan:=EXTRACT(MONTH FROM l_S02TglPayr);
        --
        IF (SELECT COUNT(*) 
            FROM l_TEMP 
            WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr)::VARCHAR(4) AND Area=l_S01kdArea 
                  AND Cabang=l_S01kdCaba AND UnitUsaha=l_S01kdUUsa AND KdUker=l_S01kdUker   
                  AND Glng=l_S01kdGlng AND KJab=l_S01kdKJab AND Jaba=l_S01kdJaba
                  AND Klas=l_S01kdKlas AND Nip =l_S01NIP AND FlagDppt=l_S02FlgDpPt
                  AND NomFormat=l_M19NomFormat) > 0 THEN 
          --
          UPDATE l_TEMP
          SET PTKP  = l_PTKP,
              Bln01 = COALESCE(Bln01,0)+ CASE l_Bulan WHEN 1 THEN  l_S02Nilai ELSE 0 END,
              Bln02 = COALESCE(Bln02,0)+ CASE l_Bulan WHEN 2 THEN  l_S02Nilai ELSE 0 END,
              Bln03 = COALESCE(Bln03,0)+ CASE l_Bulan WHEN 3 THEN  l_S02Nilai ELSE 0 END,
              Bln04 = COALESCE(Bln04,0)+ CASE l_Bulan WHEN 4 THEN  l_S02Nilai ELSE 0 END,
              Bln05 = COALESCE(Bln05,0)+ CASE l_Bulan WHEN 5 THEN  l_S02Nilai ELSE 0 END,
              Bln06 = COALESCE(Bln06,0)+ CASE l_Bulan WHEN 6 THEN  l_S02Nilai ELSE 0 END,
              Bln07 = COALESCE(Bln07,0)+ CASE l_Bulan WHEN 7 THEN  l_S02Nilai ELSE 0 END,
              Bln08 = COALESCE(Bln08,0)+ CASE l_Bulan WHEN 8 THEN  l_S02Nilai ELSE 0 END,
              Bln09 = COALESCE(Bln09,0)+ CASE l_Bulan WHEN 9 THEN  l_S02Nilai ELSE 0 END,
              Bln10 = COALESCE(Bln10,0)+ CASE l_Bulan WHEN 10 THEN  l_S02Nilai ELSE 0 END,
              Bln11 = COALESCE(Bln11,0)+ CASE l_Bulan WHEN 11 THEN  l_S02Nilai ELSE 0 END,
              Bln12 = COALESCE(Bln12,0)+ CASE l_Bulan WHEN 12 THEN  l_S02Nilai ELSE 0 END
          WHERE Tahun=EXTRACT(YEAR FROM l_S02TglPayr)::VARCHAR(4) AND Area=l_S01kdArea 
              AND Cabang=l_S01kdCaba AND UnitUsaha=l_S01kdUUsa AND KdUker=l_S01kdUker   
              AND Glng=l_S01kdGlng AND KJab=l_S01kdKJab AND Jaba=l_S01kdJaba
              AND Klas=l_S01kdKlas AND Nip =l_S01NIP AND FlagDppt=l_S02FlgDpPt
              AND NomFormat=l_M19NomFormat;
        ELSE
           INSERT INTO l_TEMP(Tahun, Bulan, Area, SkArea, Cabang, SkCabang,
                    UnitUsaha, SkUUsa, KetUUsa, KdUker, SkUker,
                    Glng, SkGlng, KJab, SkKjab, Jaba, SkJaba,
                    Klas, SkKlas, Nip, Nama, JnsKlm, StsMed,
                    FlagDppt, NomFormat, Keterangan, Nilai, PTKP,
                    Bln01,Bln02,Bln03,Bln04,Bln05,Bln06,Bln07,Bln08,Bln09,Bln10,Bln11,Bln12)
           VALUES(  EXTRACT(YEAR FROM l_S02TglPayr), l_Bulan, l_S01kdArea, l_M01Singkatan, l_S01kdCaba, l_M08SkCaba,
                    l_S01kdUUsa, l_M02Singkatan, l_M02Keterangan, l_S01kdUker, l_M17Singkatan,
                    l_S01kdGlng, l_M12Singkatan, l_S01kdKJab, l_M06Singkatan, l_S01kdJaba, l_M04Singkatan,
                    l_S01kdKlas, l_M10Singkatan, l_S01NIP, l_S01Nama, l_S01JnsKlmn, l_S01StsPjk,
                    l_S02FlgDpPt,l_M19NomFormat, l_M19Keterangan, 0, l_PTKP,
                    CASE l_Bulan WHEN 1 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 2 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 3 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 4 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 5 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 6 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 7 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 8 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 9 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 10 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 11 THEN  l_S02Nilai ELSE 0 END,
                    CASE l_Bulan WHEN 12 THEN  l_S02Nilai ELSE 0 END);
        END IF; 
        --*
    END LOOP;
    CLOSE l_LOOP_W07;
    --
    RETURN QUERY 
    SELECT * FROM l_TEMP;
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/*
SELECT * FROM R_KARTP1(2013,' ','ZZZZ',' ','ZZZ','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1) ;  
*/
