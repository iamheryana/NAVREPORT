
CREATE OR REPLACE FUNCTION public.fn_gettax (in l_m15nip varchar, in l_estytaxinc numeric, in l_jnspajak varchar) RETURNS numeric AS
$BODY$
DECLARE
	l_S03EYIT		DECIMAL(15,2);
	l_M15kdKlas		VARCHAR(4);
    l_M10Harian		INT;
	l_M10JnsPajak	VARCHAR(1);
	l_l_JnsPajak	VARCHAR(1);
	l_M41Final 		INT;
    l_M41PTKP		INT;       	
    l_M41PersenPKP  DECIMAL(5,2);
    l_M41LimitPj1   DECIMAL(15,2);
    l_M41LimitPj2   DECIMAL(15,2);	
    l_M41LimitPj3   DECIMAL(15,2);
    l_M41LimitPj4   DECIMAL(15,2);
    l_M41LimitPj5   DECIMAL(15,2);
    l_M41PersenPj1  DECIMAL(5,2);
    l_M41PersenPj2  DECIMAL(5,2);
    l_M41PersenPj3  DECIMAL(5,2);
    l_M41PersenPj4  DECIMAL(5,2);
    l_M41PersenPj5  DECIMAL(5,2);
    l_M41BiayaJabatan 	DECIMAL(15,2);
	l_Nilai			DECIMAL(15,2);
	l_Nilai1		DECIMAL(15,2);
	l_Nilai2		DECIMAL(15,2);
	l_Nilai3		DECIMAL(15,2);
	l_FXTAX			DECIMAL(6,2);
	l_M15NPWP		VARCHAR(20);
	l_NUMNPWP		DECIMAL(20,0);
	l_l_M15NPWP		VARCHAR(20);

BEGIN
	l_S03EYIT:=0;
	SELECT kdKlas,NPWP INTO  l_M15kdKlas,l_M15NPWP FROM M15PEGA WHERE NIP=l_M15NIP;
	SELECT Harian, JnsPajak INTO l_M10Harian,l_M10JnsPajak FROM M10KLAS WHERE Kode=l_M15kdKlas;

	IF l_JnsPajak = ' '  THEN 
		SELECT Final,PTKP,PersenPKP,LimitPj1,LimitPj2,LimitPj3,LimitPj4,LimitPj5,PersenPj1,PersenPj2,PersenPj3,PersenPj4,PersenPj5,BiayaJabatan
			INTO l_M41Final,l_M41PTKP,l_M41PersenPKP,l_M41LimitPj1,l_M41LimitPj2,l_M41LimitPj3,l_M41LimitPj4,l_M41LimitPj5,l_M41PersenPj1,l_M41PersenPj2,l_M41PersenPj3,l_M41PersenPj4,l_M41PersenPj5,l_M41BiayaJabatan
		FROM M41JPJK
		-- KALAU JENIS PAJAK DIPASSING, ARTINYA DARI MANTAN PEGAWAI. 
		WHERE Kode = l_M10JnsPajak;

	ELSE
		SELECT Final,PTKP,PersenPKP,LimitPj1,LimitPj2,LimitPj3,LimitPj4,LimitPj5,PersenPj1,PersenPj2,PersenPj3,PersenPj4,PersenPj5,BiayaJabatan
			INTO l_M41Final,l_M41PTKP,l_M41PersenPKP,l_M41LimitPj1,l_M41LimitPj2,l_M41LimitPj3,l_M41LimitPj4,l_M41LimitPj5,l_M41PersenPj1,l_M41PersenPj2,l_M41PersenPj3,l_M41PersenPj4,l_M41PersenPj5,l_M41BiayaJabatan
		FROM M41JPJK
		-- KALAU JENIS PAJAK DIPASSING, ARTINYA DARI MANTAN PEGAWAI. 
		WHERE Kode = l_JnsPajak;
	END IF;
	
	SELECT GREATEST(FaktorXPajak) INTO l_FXTAX FROM FZ2FLDA;

	--	 l_EstYTaxInc= (l_EstYTaxInc * l_M41PersenPKP) / 100
	/* sudah dicover pd saat passing parameter*/
	--         l_EstYTaxInc=(l_EstYTaxInc/1000)*1000

	----- Rumusan Pajak Tahunan
	
	l_Nilai  := 0;
	l_Nilai1 := 0;
	l_Nilai2 := 0;
	l_Nilai3 := 0;

	IF l_EstYTaxInc <= 0 THEN
		l_S03EYIT := 0;
	ELSE
		--l_Nilai :=100000000-50000000
		l_Nilai := l_EstYTaxInc - l_M41LimitPj1;
	    IF l_Nilai <= 0 THEN
			l_S03EYIT :=l_EstYTaxInc*l_M41PersenPj1/100;
	    ELSE
			--l_Nilai=50000000
			--l_M41PersenPj1=5
			--l_M41LimitPj1=50000000
			l_S03EYIT := (l_M41PersenPj1 * l_M41LimitPj1) / 100;
			--l_S03EYIT=2500000
			--l_M41LimitPj2=250000000
			l_Nilai1 := l_Nilai - (l_M41LimitPj2 - l_M41LimitPj1);
			--l_Nilai1=50000000-250000000-50000000
			IF l_Nilai1 <= 0 THEN
				l_S03EYIT := l_S03EYIT + ((l_M41PersenPj2 * l_Nilai) / 100);
				--l_S03EYIT=2500000+(15*50000000)/100=2500000+7500000=10000000
            ELSE
				BEGIN
					l_S03EYIT := l_S03EYIT + ((l_M41PersenPj2 * (l_M41LimitPj2 - l_M41LimitPj1)) / 100); 
					l_Nilai2 := l_Nilai1 - (l_M41LimitPj3 - l_M41LimitPj2);
					IF l_Nilai2 <= 0 THEN
						l_S03EYIT := l_S03EYIT + ((l_M41PersenPj3 * l_Nilai1) / 100) ;
					ELSE
						l_S03EYIT := l_S03EYIT + ((l_M41PersenPj3 * (l_M41LimitPj3 - l_M41LimitPj2)) / 100);
						l_Nilai3 := l_Nilai2 - (l_M41LimitPj4 - l_M41LimitPj3);
								
								-- Edit 2012-10-30, tambah kondisi jika l_M41LimitPj4=0 maka l_M41LimitPj4 di hapus
								-- IF l_M41LimitPj4 = 0 AND l_JnsPajak IN (''X'',''Y'')
								--  l_Nilai3 = l_Nilai2 - (l_M41LimitPj3)
								-- ELSE 
								--  l_Nilai3 = l_Nilai2 - (l_M41LimitPj4 - l_M41LimitPj3)	

						IF l_Nilai3 <= 0 THEN
							l_S03EYIT := l_S03EYIT + ((l_M41PersenPj4 *l_Nilai2) / 100);
						ELSE
							l_S03EYIT := l_S03EYIT + ((l_M41PersenPj4 * (l_M41LimitPj4 - l_M41LimitPj3)) / 100);
							l_S03EYIT := l_S03EYIT + ((l_M41PersenPj5 * l_Nilai3) / 100);
						END IF;
					END IF;
				END;
			END IF;
		END IF;
	END IF;

	-- PERATURAN PAJAK 2009 : GAK PUNYA NPWP MAKA DITAMBAH 20% PAJAKNYA
	-- IF (l_l_M15NPWP ~ ''^[0-9]+$'') = 1 THEN
	-- IF CAST (l_l_M15NPWP AS NUMERIC) = 1 
	l_l_M15NPWP:=TRIM(l_M15NPWP);
	IF (l_l_M15NPWP ~ '^[0-9]+$') THEN
		l_NUMNPWP := CAST (l_l_M15NPWP AS DECIMAL(20,0));
	ELSE
		l_NUMNPWP:=0;
	END IF;
	IF l_NUMNPWP = 0 THEN
		l_S03EYIT := l_S03EYIT * l_FXTAX / 100;
	END IF; 

	--Nilai Balikan
	RETURN l_S03EYIT;
END;
$BODY$
LANGUAGE 'plpgsql'
GO
