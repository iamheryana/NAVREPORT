
CREATE OR REPLACE FUNCTION public.fn_validasi_thr (in l_flagthr varchar, in l_nip varchar, in l_tglpayroll date, in l_tglmasuk date, in l_tglkeluar date, in l_tglakhir date) RETURNS int4 AS
$BODY$
DECLARE 
	l_DataValid INT;
    l_FlagM     VARCHAR(1);
BEGIN
	SELECT FlagM INTO l_FlagM FROM S01HGAJ WHERE TglPayr=l_TglAkhir AND NIP=l_NIP;

	IF l_TglMasuk > l_TglPayroll THEN
		SET l_DataValid=1;
		-- by peggy 2006 03 21 : kalau sudah keluar gak usah muncul di validasi list 
	ELSIF l_TglKeluar is not null  AND l_TglKeluar <l_TglPayroll THEN
		l_DataValid := 2;
	ELSIF l_TglAkhir > l_TglPayroll THEN
		l_DataValid := 3;
	ELSIF l_TglAkhir=l_TglPayroll AND l_FlagTHR<>'*' THEN
		l_DataValid := 4;
	ELSIF coalesce(l_FlagM,' ')='P' AND l_TglAkhir=l_TglPayroll THEN
		l_DataValid := 6;
	ELSE
		l_DataValid := 0;
	END IF;
	
  RETURN l_DataValid;
END;
$BODY$
LANGUAGE 'plpgsql'
GO
