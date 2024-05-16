
CREATE OR REPLACE FUNCTION FN_CRCBKJUKPEGUASR(l_NIP VARCHAR(10), l_TglEff DATE, l_Flag VARCHAR(2))
RETURNS VARCHAR(8) 

AS $$ 

DECLARE l_Kode 	 VARCHAR(8) ;
	l_KdCaba VARCHAR(4) ;
	l_KdKJab VARCHAR(4) ;
	l_KdUKer VARCHAR(8) ;

BEGIN

 SELECT S08.KdCaba, S08.KdKJab, S08.KdUKer 
 INTO l_KdCaba, l_KdKJab, l_KdUKer
 FROM S08MUTA S08
 INNER JOIN T10MUTA T10
 ON S08.NIP = T10.NIP AND S08.TGLEFF = T10.TGLEFF
 WHERE T10.NIP = l_NIP AND T10.TGLEFF <= l_TGLEFF ;

IF l_Flag = 'CB' THEN 
BEGIN 
	l_Kode := l_KdCaba ;
END ;
END IF; 

IF l_Flag = 'UK' THEN 
BEGIN 
	l_Kode :=  l_KdUKer ;
END ;
END IF; 

IF l_Flag = 'KJ' THEN 
BEGIN 
	l_Kode := l_KdKJab ;
END ; 
END IF; 

RETURN l_Kode ; 

END ; 
$$ language 'plpgsql' ; 

/*
select * from M49KURS
select fn_CrCbKJUKPegUAsr('201108015','2011-05-08','KJ')
selecT * from t10muta  where nip = '201108015' 
*/

