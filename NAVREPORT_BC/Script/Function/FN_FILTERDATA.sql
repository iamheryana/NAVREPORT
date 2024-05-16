/****************************************
Name sprocs	: P_FilterData
Create by	: wati
Date		: 16-06-2003
Description	: Proses Bulanan Payroll
Call From	: Main sprocs
*****************************************/
--
DROP FUNCTION fn_FilterData(l_M15kdKlas VARCHAR(4),
			      l_M15Caba VARCHAR(4),
			      l_M15Gol  VARCHAR(4),
			      l_usr_id 	INTEGER); 
--
CREATE FUNCTION fn_FilterData(l_M15kdKlas VARCHAR(4),
			      l_M15Caba   VARCHAR(4),
			      l_M15Gol    VARCHAR(4),
			      l_usr_id 	  INTEGER)  

RETURNS INT

AS $$ 

  DECLARE l_FilterData INT ;
          l_M10Harian  INT ;

BEGIN
  SELECT Harian
  INTO l_M10Harian
  FROM M10KLAS
  WHERE Kode=l_M15kdKlas ; 

  IF l_M10Harian=1 THEN 
     l_FilterData := 7 ;
  --ELSIF (l_M15Caba < l_LokasiFr OR l_M15Caba > l_LokasiTo) OR (l_M15Gol < l_FGol OR l_M15Gol > l_TGol) THEN 
  ELSIF l_M15Caba NOT IN (SELECT M08.KDCABA 
			FROM fn_SECLOGIN(l_usr_id) UC 
			INNER JOIN M08HCAB M08
			ON UC.CAB_ID = M08.M08_ID 
			WHERE UC.USR_ID = l_usr_id4)
	OR l_M15Gol NOT IN (SELECT M12.KODE 
			FROM fn_SECLOGIN(l_usr_id) UG 
			INNER JOIN M12HGOL M12
			ON UG.GOL_ID = M12.M12_ID) THEN 
     l_FilterData := 8 ;
  ELSE
     l_FilterData := 0 ;
  END IF ;
   
  RETURN l_FilterData ; 
END ; 

$$ language plpgsql ;  

/* 
SELECT fn_FilterDataZ('P', 'ZZ','ZZ',2) 
SELECT fn_FilterData('TA', 'J','J', ' ', 'ZZ', 'J','K') 

l_M15kdKlas VARCHAR(4),
l_LokasiFr  VARCHAR(4),
l_LokasiTo  VARCHAR(4),
l_Fgol      VARCHAR(4),  
l_Tgol      VARCHAR(4),
l_M15Caba   VARCHAR(4),
l_M15Gol    VARCHAR(4)) 
SELECT * FROM M10KLAS 
*/ 