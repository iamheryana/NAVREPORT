
CREATE OR REPLACE FUNCTION public.fn_getptkp (in l_m15nip varchar, in l_m15jnsklmn varchar, in l_fz1ptkppay numeric, in l_fz1ptkpdep numeric, in l_m15stspjk varchar) RETURNS numeric AS
$BODY$
DECLARE
	l_S03PTKP	DECIMAL(15,2);
	l_NIP	VARCHAR(10);
BEGIN
	 l_S03PTKP:=0;
	/*********************--Jika Perempuan********************/
	IF l_M15JnsKlmn = 'P' THEN
		l_S03PTKP := l_FZ1PTKPPay;
        -- Termasuk Karyawati Khusus?
        SELECT NIP INTO l_NIP FROM M45KKHS WHERE NIP = l_M15NIP;
		IF l_NIP IS NOT NULL THEN
            IF SUBSTRING(l_M15StsPjk from 1 for 1) = 'K'  THEN
				CASE SUBSTRING(l_M15StsPjk from 2 for 1)
					WHEN '1' THEN
						l_S03PTKP := l_S03PTKP + l_FZ1PTKPDep;
					WHEN '2' THEN
						l_S03PTKP := l_S03PTKP + (2 * l_FZ1PTKPDep);
					WHEN '3' THEN
						l_S03PTKP := l_S03PTKP + (3 * l_FZ1PTKPDep);
					ELSE
						l_S03PTKP := l_S03PTKP;
				END CASE;
            ELSE
				CASE SUBSTRING(l_M15StsPjk from 2 for 1)
					WHEN '1' THEN
						l_S03PTKP := l_S03PTKP+l_FZ1PTKPDep;
					WHEN '2' THEN
						l_S03PTKP := l_S03PTKP+(2*l_FZ1PTKPDep);
					WHEN '3' THEN
						l_S03PTKP := l_S03PTKP+(3*l_FZ1PTKPDep);
					ELSE
						l_S03PTKP := l_S03PTKP;
				END CASE;
			END IF;
		END IF;
	/*********************--Jika Lelaki********************/
	ELSE 
		IF SUBSTRING(l_M15StsPjk from 1 for 1) = 'K' THEN
			CASE SUBSTRING(l_M15StsPjk from 2 for 1)
				WHEN '0' THEN
					l_S03PTKP := l_FZ1PTKPPay+l_FZ1PTKPDep;
				WHEN '1' THEN
					l_S03PTKP := l_FZ1PTKPPay+(2 * l_FZ1PTKPDep);
				WHEN '2' THEN
					l_S03PTKP := l_FZ1PTKPPay+(3 * l_FZ1PTKPDep);
				WHEN '3' THEN
					l_S03PTKP := l_FZ1PTKPPay+(4 * l_FZ1PTKPDep);
				ELSE
					l_S03PTKP := l_FZ1PTKPPay;
			END CASE;
		ELSE
			CASE SUBSTRING(l_M15StsPjk from 2 for 1)
				WHEN '1' THEN
					l_S03PTKP := l_FZ1PTKPPay + (1 * l_FZ1PTKPDep);
				WHEN '2' THEN
					l_S03PTKP := l_FZ1PTKPPay + (2 * l_FZ1PTKPDep);		
				WHEN '3' THEN
					l_S03PTKP := l_FZ1PTKPPay + (3 * l_FZ1PTKPDep);
				ELSE 
					l_S03PTKP := l_FZ1PTKPPay;
			END CASE;
		END IF;
	END IF;
RETURN l_S03PTKP;
END;
$BODY$
LANGUAGE 'plpgsql'
GO
