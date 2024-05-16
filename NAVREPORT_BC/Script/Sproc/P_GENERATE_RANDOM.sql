
CREATE OR REPLACE FUNCTION public.p_generate_random () RETURNS varchar AS
$BODY$
--===========================================================
DECLARE l_NAMEOUT VARCHAR(8); 
        l_SEED 	  NUMERIC(20,8) ; 
        l_posisiTM int; 
        l_panjangTM int; 
        l_SEEDChar VARCHAR(30); 
BEGIN 
 
l_SEED := extract(month from  current_DATE) * 100000 + extract(seconds from  current_time) * 1000 + extract(milliseconds from  current_time) / 100000 ;
l_SEEDChar := l_SEED::varchar(15);
l_SEEDChar := replace(l_SEEDChar, ',', '0');
l_SEEDChar := replace(l_SEEDChar, '(', '1');
l_SEEDChar := replace(l_SEEDChar, ')', '7');
l_SEEDChar := replace(l_SEEDChar, ' ', '9');
l_SEEDChar := l_SEEDChar || '1234567890';
l_SEEDChar := 'TM'|| l_SEEDChar; 
l_NAMEOUT := SUBSTR(l_SEEDChar,1,8) ;
--l_NAMEOUT := replace(l_NAMEOUT, '','', ''0'');
--l_NAMEOUT := replace(l_NAMEOUT, ''('', ''1'');
--l_NAMEOUT := replace(l_NAMEOUT, '')'', ''7'');
--l_NAMEOUT := replace(l_NAMEOUT, '' '', ''9'');
l_posisiTM := position('TM' in l_NAMEOUT); 
l_panjangTM := char_length(l_NAMEOUT);
IF l_posisiTM <> 1 THEN 
   l_NAMEOUT := SUBSTR(l_NAMEOUT,2,7) || '0'; 
END IF; 
IF l_panjangTM < 8 THEN 
   l_NAMEOUT := SUBSTR(l_NAMEOUT||'1234567890',1,8) ; 
END IF; 

RETURN l_NAMEOUT ;

END ; 

$BODY$
LANGUAGE 'plpgsql'
GO
