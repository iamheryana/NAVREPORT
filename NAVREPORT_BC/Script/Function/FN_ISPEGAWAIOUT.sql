/****************************************
Name sprocs	: fn_IsPegawaiOut
Create by	: Deni
Date		: 2-11-2009
Description	: Apakah pegawai baru Out (pindah cabang) bulan ini
*****************************************/
CREATE OR REPLACE FUNCTION fn_IsPegawaiOut(	l_Cabang VARCHAR(4),l_Periode 	DATE,l_NIP VARCHAR(10))
RETURNS INT
AS $$
DECLARE
	l_BulanNext	DECIMAL(2,0);
	l_TahunNext	DECIMAL(4,0);
	l_FlagCab	VARCHAR(1);
	l_CabangNext	VARCHAR(5);
	l_StatusOut	INT;

BEGIN
	CASE EXTRACT(MONTH FROM l_Periode)
	WHEN 12 
		THEN l_BulanNext:=1;
	ELSE
		l_BulanNext:=EXTRACT(MONTH FROM l_Periode)+1;
	END CASE;
	
	CASE EXTRACT(YEAR FROM l_Periode)
	WHEN 12 
		THEN l_TahunNext:=EXTRACT(YEAR FROM l_Periode)+1;
	ELSE
		l_TahunNext:=EXTRACT(YEAR FROM l_Periode);
	END CASE;

	SELECT SUBSTRING(StringFlag FROM 2 FOR 1) INTO l_FlagCab FROM FZ2FLDA;

	SELECT CASE COALESCE(l_BulanNext,0) 
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
	END AS l_BulanNext INTO l_CabangNext FROM S0ESFPC WHERE NIP=l_NIP AND TAHUN=l_TahunNext;

	IF l_FlagCab='1' AND l_Cabang<>l_CabangNext AND l_CabangNext<>'' THEN
		l_StatusOut:=1;
	ELSE 
		l_StatusOut:=0;
	END IF;

	RETURN l_StatusOut;
END;
$$
language plpgsql;


/*
SELECT fn_IsPegawaiOut('KSE','2009-01-01','BWBWN');
*/





