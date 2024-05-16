/****************************************
Name sprocs	: FN_Validasi_Data
Create by	: wati
Date		: 16-06-2003
Description	: Proses Bulanan Payroll
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE FUNCTION fn_Validasi_Data(l_FlagKhusus VARCHAR(1),
					    l_FlagTHR    VARCHAR(1),  
					    l_NIP        VARCHAR(10),
					    l_TglPayroll DATE, 
					    l_TglMasuk   DATE,
					    l_TglKeluar  DATE,
					    l_TglAkhir   DATE)

RETURNS INT
AS $$ 


  DECLARE l_DataValid INT;
          l_FlagM     VARCHAR(1); 
	  l_S01KdCaba VARCHAR(4); 
	  l_NewKdCaba VARCHAR(4); 
	  l_FlgCabang VARCHAR(1);     
	  l_KodeLDA   VARCHAR(2);
  
BEGIN
--
  l_DataValid:=0   ;
  --
  SELECT FlagM,   KdCaba 
  INTO   l_FlagM, l_S01KdCaba
  FROM S01HGAJ
  WHERE TglPayr=l_TglAkhir AND NIP=l_NIP;
  --
  SELECT KdCaba 
  INTO l_NewKdCaba
  FROM T10MUTA 
  WHERE NIP = l_NIP AND 
	EXTRACT(MONTH FROM TglEff) = EXTRACT(MONTH FROM l_TglPayroll) AND 
	EXTRACT(YEAR FROM TglEff) = EXTRACT(YEAR FROM l_TglPayroll) ;
--		TglEff <= l_TglPayroll 
  --
  SELECT Kode 
  INTO   l_KodeLDA
  FROM FZ1FLDA LIMIT 1;
  --  
  SELECT CASE SUBSTR(StringFlag,2,1) WHEN '1' THEN 'Y' ELSE 'T' END
  INTO l_FlgCabang
  FROM FZ2FLDA
  WHERE Kode=l_KodeLDA ;
  --
  IF l_TglMasuk IS NULL THEN 
     l_DataValid := 1;   
  END IF;
  IF l_TglMasuk > l_TglPayroll THEN 
     l_DataValid := 1;
-- by peggy 2006 03 21 : kalau sudah keluar gak usah muncul di validasi list 
  ELSIF l_TglKeluar IS NOT NULL AND l_TglKeluar < l_TglPayroll  THEN 
     l_DataValid := 2;
  ELSIF l_TglAkhir >= l_TglPayroll THEN 
     l_DataValid := 3; 
  ELSIF COALESCE(l_FlagM,' ')='P' AND l_TglAkhir=l_TglPayroll THEN 
     l_DataValid := 6;
  ELSIF l_FlagKhusus='Y' AND COALESCE(l_FlagTHR,' ')=' ' AND l_TglAkhir=l_TglPayroll THEN 
     l_DataValid := 4;
  ELSIF l_FlagKhusus<>'Y' AND COALESCE(l_FlagTHR,' ')='@' AND l_TglAkhir=l_TglPayroll THEN 
     l_DataValid := 5;
-- proses payroll bulanan, ada prs payroll dlm prd sama dan cabang beda 
  ELSIF l_FlagKhusus<>'Y' AND 
	EXTRACT(MONTH FROM l_TglAkhir)=EXTRACT(MONTH FROM l_TglPayroll) AND 
	EXTRACT(YEAR FROM l_TglAkhir) = EXTRACT(YEAR FROM l_TglPayroll) AND 
	l_NewKdCaba IS NOT NULL AND l_NewKdCaba <> l_S01KdCaba AND l_FlgCabang = 'Y'  THEN 
     l_DataValid := 13;
  ELSE
     l_DataValid := 0;
  END IF ; 
  -- 
  RETURN l_DataValid;
END;
$$ LANGUAGE plpgsql ;

/*

DECLARE l_S05TglAkhir	DATE,
	l_S05FlagTHR	VARCHAR(1)

    SELECT l_S05TglAkhir=TglPosting,
           l_S05FlagTHR=FlagTHR
    FROM S05PSTD
    WHERE NIP='SUHE'
    ORDER BY NIP,TglPosting

    -- Validasi Data     
    SELECT fn_Validasi_Data('T',
					   l_S05FlagTHR,
			                   'SUHE',
				           '2003-01-24',
				           '2003-01-01',
					   NULL,
					   l_S05TglAkhir)

*/



