/******************************************
KUMPULAN DARI SPROCS UNTUK 
PEMBUATAN TEMP FILE DENGAN CIRI-2 'TM'....
*******************************************/
DROP FUNCTION P_GENERATE_RANDOMX () ; 
--
CREATE FUNCTION P_GENERATE_RANDOMX () RETURNS VARCHAR(8) 
AS $$ 
--=========================================================== 	
DECLARE l_NAMEOUT VARCHAR(8); 
        l_SEED 	  NUMERIC(20,8) ; 
BEGIN 
 
l_SEED := extract(month from  current_DATE) * 100000 + extract(seconds from  current_time) * 1000 + extract(milliseconds from  current_time) / 100000 ;
l_NAMEOUT := SUBSTR('TX' || l_SEED::varchar(15),1,8) ; --SUBSTR('##' || l_SEED::varchar(15),1,8) ; POSTGRES TIDAK BISA CREATE TABLE PAKAI '#' 

l_NAMEOUT := replace(l_NAMEOUT, ',', '0');

RETURN l_NAMEOUT ;
END ; 

$$ LANGUAGE plpgsql ; 

-- SELECT P_GENERATE_RANDOMX()

DROP FUNCTION P_GENERATE_RANDOM();
--
CREATE FUNCTION P_GENERATE_RANDOM() RETURNS VARCHAR(8) 
AS $$
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
--l_NAMEOUT := replace(l_NAMEOUT, ',', '0');
--l_NAMEOUT := replace(l_NAMEOUT, '(', '1');
--l_NAMEOUT := replace(l_NAMEOUT, ')', '7');
--l_NAMEOUT := replace(l_NAMEOUT, ' ', '9');
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

$$ LANGUAGE plpgsql ; 

-- SELECT P_GENERATE_RANDOM()

/*******END OF PROC ****/

DROP FUNCTION P_CREATE_TEMP_ONLIST ( l_TMPCODE VARCHAR(3), OUT OUTNAME VARCHAR(8) , OUT OUTCREATED INT) ;
CREATE FUNCTION P_CREATE_TEMP_ONLIST ( l_TMPCODE VARCHAR(3), OUT OUTNAME VARCHAR(8) , OUT OUTCREATED INT) 
AS $$ 
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
               l_CREATED     := 1 ;      --TABLE'S CREATED
               l_NAMEALLOWED := 1 ;   --OK, TEMPORARY NAME IS UNIQUE
             END ;
        END;
      END IF; 
   END ;
   
END LOOP ; 
OUTNAME := l_TABLENAME;
OUTCREATED := l_CREATED; 
END ; 
$$ LANGUAGE plpgsql ; 
--SELECT P_CREATE_TEMP_ONLIST ('W01');
--select * from tabeltemp ;
--select P_CREATE_TEMP_ONLIST ( 'W01') 


/******** END OF PROC ********/

DROP FUNCTION P_CREATE_TEMP_ONLISTX ( l_TMPCODE VARCHAR(3), OUT OUTNAME VARCHAR(8) , OUT OUTCREATED INT) ;
--
CREATE FUNCTION P_CREATE_TEMP_ONLISTX ( l_TMPCODE VARCHAR(3), OUT OUTNAME VARCHAR(8) , OUT OUTCREATED INT) 
AS $$ 
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
     SELECT P_GENERATE_RANDOMX() INTO l_TABLENAME ;
     
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
               l_CREATED     := 1 ;      --TABLE'S CREATED
               l_NAMEALLOWED := 1 ;   --OK, TEMPORARY NAME IS UNIQUE
             END ;
        END;
      END IF; 
   END ;
   
END LOOP ; 
OUTNAME := l_TABLENAME;
OUTCREATED := l_CREATED; 
END ; 
$$ LANGUAGE plpgsql ; 
--SELECT P_CREATE_TEMP_ONLISTX ('W01');

/******** END OF PROC ********/
DROP FUNCTION P_CREATE_TEMP1 ( l_TMPCODE VARCHAR(3), OUT l_CREATED INT );
--
CREATE FUNCTION P_CREATE_TEMP1 ( l_TMPCODE VARCHAR(3), OUT l_CREATED INT ) AS $$
--LOOK UP KE TABLETEMP, CARI DEFINISI KOLOM YANG AKAN DI-CREATE
DECLARE l_SQLSTATE  VARCHAR(8000);
DECLARE l_TBLSTRUC  VARCHAR(5000);
BEGIN 
IF EXISTS (SELECT TMPCODE FROM TABELTEMP WHERE TMPCODE = l_TMPCODE) THEN 
   BEGIN
     l_TBLSTRUC := COLDEF FROM TABELTEMP WHERE TMPCODE = l_TMPCODE ;
   END;
ELSE
   BEGIN
     l_CREATED  := 0  ;    --TABLE TIDAK TERCREATE....DEFINISI KOLOM TIDAK ADA...
     RETURN;
   END;
END IF ;
l_SQLSTATE := '';
l_CREATED := 0 ;

EXECUTE 'CREATE TABLE ' || l_TMPCODE || l_TBLSTRUC ;
exception when SQLSTATE '00000' then 
begin 
   l_CREATED := 1 ;      --TABLE'S CREATED     
end ;

END ;
-- 
$$ LANGUAGE plpgsql ; 
--SELECT P_CREATE_TEMP1 ('W01');


DROP FUNCTION P_GETTMP (l_TMPFILE VARCHAR(8)) ;
--
CREATE FUNCTION P_GETTMP (l_TMPFILE VARCHAR(8)) RETURNS VOID 
AS $$
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
$$ LANGUAGE plpgsql ; 
--SELECT P_CREATE_TEMP1 ('W01');
