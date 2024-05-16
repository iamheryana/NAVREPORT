CREATE OR REPLACE function fn_getkurs(l_kdcurr varchar(4), l_tglproses date)
returns decimal(10,2)
AS $$
DECLARE 
l_nilcurr decimal(10,2); 
begin
	l_nilcurr := 1;
	select M49.KURS into l_nilcurr FROM M49KURS M49 where M49.kdcurr=l_kdcurr AND M49.Periode<=l_tglproses;
	return coalesce(l_nilcurr, 1);
	
end;
$$
language plpgsql;


/*
select * from M49KURS
select dbo.fn_GetKurs('USD','2002-09-13')
*/
select fn_GetKurs('USD','2002-09-13');
