
CREATE OR REPLACE FUNCTION public.p_gettmp (in l_tmpfile varchar) RETURNS void AS
$BODY$
--
DECLARE l_SQLSTRING VARCHAR(1000) ;
DECLARE l_CREATED INT ;
--
BEGIN 
IF LEFT(l_TMPFILE,2)='TM' THEN 
   l_SQLSTRING := 'SELECT * FROM '+l_TMPFILE ;
ELSE
  IF NOT EXISTS (select * from pg_tables where schemaname='public' and tablename = l_TMPFILE) THEN 
     BEGIN       
       l_CREATED := (SELECT P_CREATE_TEMP1 l_TMPFILE);                	
       l_SQLSTRING :='SELECT * FROM '+l_TMPFILE ;	
     END;
  END IF; 
--*
END IF ;
EXECUTE (l_SQLSTRING) ;
END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
