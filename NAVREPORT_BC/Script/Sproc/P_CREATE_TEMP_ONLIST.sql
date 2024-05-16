
CREATE OR REPLACE FUNCTION public.p_create_temp_onlist (in l_tmpcode varchar, out outname varchar, out outcreated int4) RETURNS record AS
$BODY$ 
--======================================================================================================
--INPUT   l_TMPCODE   = KODE TEMPORARY YANG AKAN DICREATE, DILOOKUP PADA TABELTEMP
--OUTPUT  l_TABLENAME = NAMA TEMPORARY YANG AKAN DICREATE
--OUTPUT  l_CREATED   = FLAG APAKAH TABEL TEMPORARY TERCREATE(1) ATAU TIDAK(0)

--LOOK UP KE TABLETEMP, CARI DEFINISI KOLOM YANG AKAN DI-CREATE

DECLARE l_SQLSTATE  VARCHAR(8000);
	l_TBLSTRUC  VARCHAR(5000);
	l_TABLENAME VARCHAR(8); 
	l_CREATED   INT ; 
	l_error     int; 
DECLARE l_NAMEALLOWED  INT; 

BEGIN 
IF EXISTS (SELECT TMPCODE FROM TABELTEMP WHERE TMPCODE = l_TMPCODE) THEN 
   BEGIN
     l_TBLSTRUC := COLDEF FROM TABELTEMP WHERE TMPCODE = l_TMPCODE ; 
   END ; 
ELSE
   BEGIN
     l_CREATED := 0;      --TABLE TIDAK TERCREATE....DEFINISI KOLOM TIDAK ADA...
     RETURN;
   END; 
END IF; 

--GENERATE RANDOM NUMBER PLUS CHECK ITS UNIQUENESS 

l_NAMEALLOWED := 0;
-- l_NAMEALLOWED,   0 = NOT ALLOWED / DUPLICATED
--                 1 = ALLOWED / UNIQUE
WHILE l_NAMEALLOWED = 0 LOOP 
   BEGIN
     SELECT P_GENERATE_RANDOM() INTO l_TABLENAME ;
     
     IF EXISTS (select * from pg_tables where schemaname='public' and tablename = l_TABLENAME) THEN 
        l_NAMEALLOWED := 0 ;     --TEMPORARY TABLE WITH THIS NAME ALREADY EXISTS
     ELSE
        BEGIN
          l_SQLSTATE := '' ;
               l_CREATED     := 0 ;
               l_NAMEALLOWED := 0 ;   --ERROR OCCURRED		  
          EXECUTE 'CREATE TABLE ' || l_TABLENAME || l_TBLSTRUC ;
          --EXECUTE (l_SQLSTATE) ; 
		  exception when SQLSTATE '00000' then 
             BEGIN 
               l_CREATED     := 1 ;      --TABLE''S CREATED
               l_NAMEALLOWED := 1 ;   --OK, TEMPORARY NAME IS UNIQUE
             END ;
        END;
      END IF; 
   END ;
   
END LOOP ; 
OUTNAME := l_TABLENAME;
OUTCREATED := l_CREATED; 
END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
