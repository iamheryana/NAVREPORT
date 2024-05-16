create or replace function fn_depot
(
	kode char(10)
)
returns smallint
as
$$
	declare i smallint; j int;

begin
	i := 0; j := 1;

	while j <= char_length(kode) loop 
	begin
		i := i + (ascii(substr(kode,j,1)) * j);
		j := j + 1;
	end;
	end loop ; 

	if i > 256 then 
	  i := i % 256;
	end if ; 

	return(i);
end;
$$ language 'plpgsql'; 