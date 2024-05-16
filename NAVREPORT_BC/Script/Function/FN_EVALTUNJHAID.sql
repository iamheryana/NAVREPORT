
CREATE OR REPLACE FUNCTION public.fn_evaltunjhaid (in l_fz1kdcutihaid varchar, in l_fz1kdsakitsrtdr varchar, in l_m15jnsklmn varchar, in l_s05tglakhir date, in l_tglproses date, in l_m15tgllahir date, in l_m15nip varchar) RETURNS varchar AS
$BODY$ 
DECLARE l_OK VARCHAR(1)  ; 
-----
BEGIN

l_OK := 'T' ;

IF l_M15JnsKlmn='P' AND --l_FZ1KdSakitSrtDr IS NULL AND
	(NOT EXISTS(SELECT S06.NIP FROM S06ABSH S06 
		    INNER JOIN M29JNSA M29 ON S06.KDABSN = M29.KODE 
		    WHERE S06.NIP=l_M15NIP AND 
			  S06.Tanggal BETWEEN l_S05TglAkhir + 1 AND l_TglProses AND 
			  S06.KdAbsn = l_FZ1KdCutiHaid AND 
			  M29.JnsCuti = 1)) AND 
	(NOT EXISTS(SELECT S06.NIP FROM S06ABSH S06 
		    INNER JOIN M29JNSA M29 ON S06.KDABSN = M29.KODE 
		    WHERE S06.NIP=l_M15NIP AND 
			  S06.Tanggal BETWEEN l_S05TglAkhir + 1 AND l_TglProses AND 
			  KdAbsn <> l_FZ1KdSakitSrtDr AND 
			  M29.JnsCuti = 0)) THEN 

	BEGIN 
	  l_OK := 'Y' ;
	END ;
END IF; 

RETURN l_OK ; 

END ; 
$BODY$
LANGUAGE 'plpgsql'
GO
