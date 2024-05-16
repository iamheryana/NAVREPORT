/****************************************
Name sprocs	: fn_IsPegawaiIn
Create by	: Deni
Date		: 2-11-2009
Description	: Apakah pegawai baru In (pindah cabang) bulan ini
*****************************************/
CREATE OR REPLACE FUNCTION fn_IsPegawaiIn(	l_Cabang VARCHAR(4),l_Periode 	DATE,l_NIP VARCHAR(10))
RETURNS INT
AS $$
DECLARE 
	l_BulanLast		DECIMAL(2,0);
	l_TahunLast		DECIMAL(4,0);
	l_FlagCab		VARCHAR(1);
	l_CabangLast	VARCHAR(5);
	l_StatusIn		INT;

BEGIN
CASE EXTRACT(MONTH FROM l_Periode)
	WHEN 1 THEN 
		l_BulanLast := 1;
		l_TahunLast := 1; 
	ELSE 
		l_BulanLast := EXTRACT(MONTH FROM l_Periode)-1;
		l_TahunLast := EXTRACT(YEAR FROM l_Periode)-1;
END CASE;
	
SELECT SUBSTRING(StringFlag FROM 2 FOR 1) INTO l_FlagCab FROM FZ2FLDA;

SELECT CASE COALESCE(l_BulanLast,0) 
WHEN 1 THEN 'Cab01'
WHEN 2 THEN 'Cab02'
WHEN 3 THEN 'Cab03'
WHEN 4 THEN 'Cab04'
WHEN 5 THEN 'Cab05'
WHEN 6 THEN 'Cab06'
WHEN 7 THEN 'Cab07'
WHEN 8 THEN 'Cab08'
WHEN 9 THEN 'Cab09'
WHEN 10 THEN 'Cab10'
WHEN 11 THEN 'Cab11'
WHEN 12 THEN 'Cab12'
ELSE  ''
END AS l_BulanLast into l_CabangLast FROM S0ESFPC WHERE NIP=l_NIP AND TAHUN=l_TahunLast;

IF l_FlagCab='1' AND l_Cabang<>COALESCE(l_CabangLast,'') THEN
	l_StatusIn:=1;
ELSE
	l_StatusIn:=0;
END IF;

RETURN l_StatusIn;

end;
$$
language plpgsql;

/*
SELECT fn_IsPegawaiIn('KSE','2009-09-01','BWBWN');
*/





