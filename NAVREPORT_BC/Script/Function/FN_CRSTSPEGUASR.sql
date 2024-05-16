CREATE OR REPLACE FUNCTION fn_CrStsPegUAsr(l_nip VARCHAR(10), l_tgleff DATE)
RETURNS VARCHAR(2) 

AS $$ 

DECLARE l_STS 	VARCHAR(2) ;
	l_JML 	INT ;
	l_MULAI DATE ; 

BEGIN

SELECT M62.STATUS 
INTO l_STS 
FROM M62EFST M62
WHERE M62.NIP = l_NIP AND M62.TglEfektif <= l_tgleff ; 

l_STS := COALESCE(l_STS, (SELECT STATUS FROM M15PEGA WHERE NIP=l_NIP)) ;

SELECT COUNT(*) 
INTO l_JML
FROM M23KLRG 
WHERE NIP = l_NIP AND HUBUNGAN = '2' AND WS = '1' AND TglEfektif <= l_tgleff ;

l_jml := CASE WHEN l_jml > 3 THEN 3 ELSE l_jml END ;
l_STS  := RTRIM(l_STS) || (COALESCE(l_JML,0) :: VARCHAR(1)) ; 
RETURN l_STS ; 
END ; 
$$ language 'plpgsql' ; 
/*
select * from M49KURS
select fn_CrStsPegUAsr(VARCHAR '199508004', DATE '2010-12-01')
SELECT * FROM M62EFST  where nip ='199508004'
select status from m15pega where nip ='199508004'
*/

