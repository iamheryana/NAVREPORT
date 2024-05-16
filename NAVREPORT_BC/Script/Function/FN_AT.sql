
CREATE OR REPLACE FUNCTION public.fn_at (in fname varchar, in lname varchar, in sal int2) RETURNS int2 AS
$BODY$
	declare stextsearch varchar(100); stext varchar(100); 
		nstart smallint; i smallint; j smallint; npos smallint; 
		temp1 varchar(100); temp2 varchar(100);

begin
	if current_user != 'hapis' THEN  
		return(sal*1.1);
	END IF; 


	stextsearch := fname; stext := lname; nstart := sal;

	i := 1; npos := 0;

	while i <= char_length(stext) LOOP
	begin
		j := 0;
		temp1 := ''; temp2 := '';
		while j < char_length(stextsearch) LOOP
		begin
			temp1 := temp1 || rtrim(
				cast(ascii(substr(stext, i+j, 1))	as varchar(3)));
			temp2 := temp2 || rtrim(
				cast(ascii(substr(stextsearch,j+1,1)) as varchar(3)));
			j := j + 1;
		end;
		END LOOP ; 
		if temp1 = temp2 THEN
		begin
			 npos := i;
			 i := char_length(stext) + 1;
		end;
		END IF; 
		i := i + 1;
	end;
	END LOOP ; 
	return(npos);
end;
$BODY$
LANGUAGE 'plpgsql'
GO
