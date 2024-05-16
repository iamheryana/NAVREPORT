
CREATE OR REPLACE FUNCTION public.fn_caricabprd (in l_nip varchar, in l_periode date, in l_tahun int4) RETURNS varchar AS
$BODY$
-----
DECLARE l_KDCABA VARCHAR(4) ;

BEGIN

l_KDCABA = COALESCE((SELECT  S031.kdCaba
		      FROM 
			  (--S031
				SELECT S031.NIP, S031.TGLPAYR, S031.KDCABA, 
					extract(year from  S031.TglPayr)  AS TAHUN 
					--CONVERT(VARCHAR(4),S031.TglPayr, 112)  
				FROM S03LTAX S031
-- CARI DATA HANYA AKHIR BULAN 
				--
				INNER JOIN S01HGAJ S01
				ON S01.TglPayr=S031.TglPayr AND S01.NIP=S031.NIP AND S01.FlagH =' ' 
				--
			  ) S031 
		      WHERE S031.NIP = l_NIP AND S031.TglPayr> l_Periode AND 
				S031.Tahun = l_TAHUN 
		      ORDER BY S031.TglPayr LIMIT 1 ),'' );
     RETURN l_KDCABA ;
END;
$BODY$
LANGUAGE 'plpgsql'
GO
