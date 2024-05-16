/****************************************
Name sprocs	: fn_kpusat
Create by	: Hermansyah
Date		: 22-11-2001
Description	: Report SLIP
Call From	: Main sprocs
Sub sprocs	: 
*****************************************/
create or replace function fn_kpusat
(
	kode char(10), 
	keterangan1 varchar(100), 
	keterangan2 varchar(100)
)
returns varchar(100)

/*
	Author: RS
	Date Written: Aug 30, 2002
	Purpose: To decrypt a string
*/
as
$$

	declare key smallint; what varchar(100); keystr char (62); 
		lkey integer; i smallint; result varchar (100); c smallint;
		hiNibble smallint; lowNibble smallint; off1 smallint;
		off2 smallint; 
		pass varchar(100); realpass varchar(100); var1 varchar(200); var2 varchar(200);

begin
	realpass := 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved';
		i := 1;
	var1 := '';
	var2 := '';
	pass := keterangan2;

	while i <= char_length(realpass) loop
	begin
		 var1 := var1 || right('000'||ascii(substr(realpass,i,1)),3);
		if i <= char_length(pass) then 
			 var2 := var2 || right('000'||ascii(substr(pass,i,1)),3);
		else
			 i := char_length(realpass) + 1;
		end if ; 
		 i := i + 1;
	end; 
	end loop; 

	if current_user != 'hapis' or var1 != var2 then 
		return(cast(kode as varchar(5)) || ' ' || coalesce(keterangan1,' NULL'));
	end if;

	key := public.fn_depot(kode); 
	what := keterangan1 ; 
	
	lKey := Key;
	i := 1;
		keystr := 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789' || 
			'abcdefghijklmnopqrstuvwxyz';
	
	while i <= octet_length(what) LOOP
	begin
		 hinibble := public.fn_at(cast(substr(what, i, 1) as varchar(100)), cast(keystr as varchar(100)), cast(1 as smallint)) - 1;
		 lownibble := public.fn_at(cast(substr(what, (i + 1), 1) as varchar(100)), cast(keystr as varchar(100)), cast(1 as smallint)) - 1;
		off1 := ((1 + (lkey & 29)) * ((i + 1) / 2)) % octet_length(keystr);
		off2 := ((1 + (lkey & 81)) * ((i + 1) / 2)) % octet_length(keystr);
		hinibble := hinibble - off1; 
		lownibble := lownibble - off2;
		if hinibble < 0 then 
			hinibble := hinibble + octet_length(keystr);
		end if; 
		if lownibble < 0 then 
			lownibble := lownibble + octet_length(keystr);
		end if ;
		c := hinibble * 16 + lownibble;
		c := (c # lkey) & 255;
		lkey := lkey / 256;
		if lkey = 0 then 
			lkey := key ;
		end if; 
		result := coalesce(result, '') || coalesce(chr(c), '') ;

		i := i + 2;
	end; 
	END LOOP;
	return(result);
end;
$$ language 'plpgsql' ; 