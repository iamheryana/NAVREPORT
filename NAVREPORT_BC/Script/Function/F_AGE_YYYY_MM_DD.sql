create or replace function F_AGE_YYYY_MM_DD
	(
	l_START_DATE		date,
	l_END_DATE		date
	)
returns  varchar(10) 
as $$
/*
Function: F_AGE_YYYY_MM_DD

This function calculates age in years, months and days
from l_START_DATE through l_END_DATE and
returns the age in format YYYY MM DD.

Years is the number of full years between l_START_DATE and l_END_DATE.

Months is the number of full months since the last full year anniversary.

Days is the number of days since the last full month anniversary.

*/
declare l_AGE varchar(10);
declare l_AGE_IN_YEARS		int;
declare l_AGE_IN_MONTHS		int;

declare l_interval 		interval ; 
declare l_eom_start		date; 
declare l_bom_end  		date; 
declare l_bedahari		int; 

begin
-- Return null if l_START_DATE > l_END_DATE
if l_START_DATE > l_END_DATE then 
	return null ; 
else 
select age( l_END_DATE, l_START_DATE ) into l_interval ;

if extract(day from l_START_DATE) > extract(day from l_END_DATE) then 
	select 
		extract(year from  l_START_DATE) :: varchar(4) || '-' || right('00'  || (extract(month from l_START_DATE) + 1):: varchar(2), 2) || '-' || ('01'), 
		extract(year from  l_END_DATE) :: varchar(4) || '-' || right('00'  || extract(month from l_END_DATE):: varchar(2), 2) || '-' || ('01')
		 into l_eom_start, l_bom_end;

	select l_eom_start - 1 into l_eom_start; 

	select (l_eom_start - l_START_DATE ) + (l_END_DATE - l_bom_end) + 2 into l_bedahari; 

else 
	l_bedahari := extract(day from l_interval) ; 
end if ; 

select  extract(year from  l_interval) :: int, 
	extract(month from l_interval) :: int into l_AGE_IN_YEARS, l_AGE_IN_MONTHS; 

select 
	right('0000'|| l_AGE_IN_YEARS :: varchar(4),  4) || ' ' ||
	right('00'  || l_AGE_IN_MONTHS:: varchar(2), 2) || ' ' ||
	right('00'  || l_bedahari :: varchar(2),   2) into l_AGE;


return (l_AGE);
end if;

end;
$$ language 'plpgsql' ; 

-- -- select [Age] = dbo.F_AGE_YYYY_MM_DD('2004-04-07','2006-02-03')
-- -- 
-- -- select [Age] = dbo.F_AGE_YYYY_MM_DD('2006-02-03','2006-02-03')
-- -- 
-- -- select [Age] = dbo.F_AGE_YYYY_MM_DD('2006-02-05','2006-02-03')
-- -- 
-- -- select age (date '2013-04-10', date '2010-04-10')
-- -- 
-- -- 

-- select F_AGE_YYYY_MM_DD(date '2010-04-10', date '2013-04-10') ;
-- select F_AGE_YYYY_MM_DD(date '2010-01-7', date '2013-04-10') ;

--select extract(year from  age (date '2013-04-10', date '2010-04-10')) :: int