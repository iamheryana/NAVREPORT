/****************************************
Name sprocs	: fn_Validasi_Data
Create by	: Wati
Date		: 05-06-2003
Description	: Proses Validasi Mantan Pegawai
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE function fn_Validasi_MTG (l_FlagTHR VARCHAR(1),l_NIP VARCHAR(10),l_TglPayroll DATE,l_TglMasuk DATE, l_TglKeluar DATE, l_TglAkhir DATE)

RETURNS INT
AS $$
DECLARE 
	l_DataValid INT;
	l_FlagM     VARCHAR(1);
BEGIN
	l_DataValid:=0;
	SELECT FlagM into l_FlagM FROM S01HGAJ WHERE TglPayr=l_TglAkhir AND NIP=l_NIP;

	IF l_TglMasuk > l_TglPayroll THEN
		l_DataValid:=1;
	ELSIF l_TglKeluar IS NULL OR (coalesce(l_TglKeluar,' ')<>' ' AND coalesce(l_TglKeluar,' ')>l_TglPayroll) THEN
		l_DataValid:=12;
	ELSIF l_TglAkhir > l_TglPayroll THEN
		l_DataValid:=3; 
	ELSIF l_TglAkhir=l_TglPayroll AND coalesce(l_FlagTHR,' ')<>'#' THEN
		l_DataValid:=10;
	ELSIF l_TglAkhir=l_TglPayroll AND coalesce(l_FlagM,' ')='P' THEN
		l_DataValid:=6;
	ELSE
		l_DataValid:=0;
	END IF;

  RETURN l_DataValid;
END;
$$
language plpgsql;


/*

    -- Validasi Data     

     04         2003-12-20 00:00:00.000                                2003-01-01 00:00:00.000                                2003-06-01 00:00:00.000                                2003-01-10 00:00:00.000

    SELECT dbo.fn_Validasi_MTG(' ',
			       '04',
			       '2003-12-20',
			       '2003-01-01',
			       '2003-06-01',
    			       '2003-01-10')

*/



