/****************************************
Name sprocs	: fn_kcabang
Create by	: Hermansyah
Date		: 22-11-2001
Description	: Report SLIP
Call From	: Main sprocs
Sub sprocs	: 
*****************************************/
create or replace function fn_kcabang
(
	kdcab char(10), 
	keterangan1 varchar(100), 
	keterangan2 varchar(100)
)
returns varchar(100)

/*
	Author: RS
	Date Written: Aug 30, 2002
	Purpose: To encrypt a string
*/
as
$$
	declare key smallint; unencrypted varchar(100); pass varchar(100); 
	declare keystr char (62); lkey integer; i smallint; 
		Result varchar(100); c smallint; HiNibble smallint; 
		LowNibble smallint; Off1 smallint; Off2 smallint; 
		realpass varchar(100); var1 varchar(200); var2 varchar(200);

begin
	 key := public.fn_depot(kdcab); 
		unencrypted := keterangan1; pass := keterangan2;
	

	realpass := 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved';

	i := 1; var1 := ''; var2 := '';

	while i <= char_length(realpass) loop 
	begin
		 var1 := var1 || right('000'||ascii(substr(realpass,i,1)),3);
		if i <= char_length(pass) then 
			 var2 := var2 || right('000'||ascii(substr(pass,i,1)),3);
		else
			 i :=char_length(realpass) + 1;
		end if ; 
		 i := i + 1;
	end; 	
	end loop; 

	if current_user != 'hapis' or var1 != var2 then 
		return(cast(kdcab as varchar(5)) || ' ' || coalesce(keterangan1,' NULL')); 
	end if ; 

	 lKey := Key; i := 1;
		keystr := 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'||
			'abcdefghijklmnopqrstuvwxyz' ;
	
	while i <= octet_length(unencrypted) loop 
	begin
		 c := ascii(substr(unencrypted, i, 1));
		 c := (c # lkey) & 255;

		 Off1 := (1 + (lkey & 29)) * i;
		 Off2 := (1 + (lkey & 81)) * i;

		 HiNibble := floor((c / 16)) + Off1;
		 LowNibble := (c % 16) + Off2;

		 HiNibble := HiNibble % octet_length(keystr);
		 LowNibble := LowNibble % octet_length(keystr);

		Result := coalesce(Result, '') 
			|| coalesce(Substr(keystr, HiNibble + 1, 1), '') 
			|| coalesce(Substr(keystr, LowNibble + 1, 1), ''); 

		 lkey := lkey / 256;
		if lkey = 0 then 
			lkey := Key ;
		end if ; 
		 i := i + 1;
	end;
	end loop; 
	return(Result);
end; 
$$ language 'plpgsql' ; 