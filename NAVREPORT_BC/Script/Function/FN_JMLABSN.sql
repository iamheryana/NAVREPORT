
CREATE OR REPLACE FUNCTION public.fn_jmlabsn (in nip varchar, in tglakhir date, in tglproses date) RETURNS numeric AS
$BODY$
DECLARE 
	Nilai DECIMAL(4,1);
BEGIN
	Nilai := 999.9;
	RETURN Nilai;
end;
$BODY$
LANGUAGE 'plpgsql'
GO
