/****************************************
Name sprocs	: fn_HakUMP
Create by	: Wati
Date		: 28-07-2003
Description	: Proses Untuk HakUMP
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE FUNCTION fn_HakUMP(l_P_Harian        VARCHAR(1),
                          l_Kewarganegaraan VARCHAR(20),
                          l_StrukTural      VARCHAR(1),
                          l_FlgDLL          VARCHAR(1))
RETURNS VARCHAR(1)
AS $$
DECLARE 
	l_HakUMP   VARCHAR(1);
BEGIN 
	l_HakUMP:='T';
	IF l_P_Harian='Y' THEN
		l_HakUMP:='Y';
	END IF;
	--ada perubahan disini karena else if yg asli tidak nyambung
	IF l_Kewarganegaraan='INDONESIA' AND l_Struktural='T' AND l_FlgDLL='T' THEN
		l_HakUMP:='Y';
	ELSE 
		l_HakUMP:='T';
	END IF;
  RETURN l_HakUMP;
END;
$$
language plpgsql;


/*
SELECT fn_HakUMP('Y','INDONESIA','N','T');

*/