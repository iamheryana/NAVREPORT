CREATE OR REPLACE function fn_RekAsrn(l_nip varchar(10),l_caba varchar(4), l_uker varchar(8))
returns varchar(20) 
AS $$
DECLARE 
	declare l_rekbank varchar(20);
begin
	select rkbnk into l_rekbank FROM m15pega M15 where m15.nip = l_nip;
	return l_rekbank;
end;
$$
language plpgsql;



--select fn_RekAsrn('2020');
--select fn_RekAsrn('001');
