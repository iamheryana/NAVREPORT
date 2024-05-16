/****************************************
Name sprocs	: fn_TaxCalculationB
Create by	: Wati
Date		: 11-07-2003
Description	: Proses Estimasi Yearly Income Tax B
Call From	: Main sprocs
update by peggy 2008 10 23 : get tax untuk mantan pake range mantan jangan dari klas peg 
*****************************************/
DROP FUNCTION public.fn_taxcalculationb (in l_jnspajak bpchar, in l_income numeric, in l_nip bpchar, in l_persenpkp numeric, in l_persenpj1 numeric)
GO
--
CREATE OR REPLACE function fn_TaxCalculationB(l_JnsPajak CHAR(1),l_Income DECIMAL(15,2),l_NIP CHAR(10),l_PersenPKP DECIMAL(5,2),l_PersenPJ1   DECIMAL(5,2))
RETURNS DECIMAL(15,2)
AS $$
--
DECLARE 
	l_NilPajak DECIMAL(15,2);
	l_FXTAX	DECIMAL(6,2);
	l_M15NPWP	CHAR(20);
	l_NUMNPWP	DECIMAL(20,0);
BEGIN
	l_NilPajak := 0;
	SELECT NPWP into l_M15NPWP FROM M15PEGA WHERE NIP=l_NIP;
	SELECT GREATEST (FaktorXPajak) into l_FXTAX FROM FZ2FLDA;
	--
	-- PERATURAN PAJAK 2009 : GAK PUNYA NPWP MAKA DITAMBAH 20% PAJAKNYA 
	--CASE 
	l_NUMNPWP := CASE WHEN TRIM(BOTH FROM l_M15NPWP) ~ '^[0-9]+$' = T THEN 
				TRIM(BOTH FROM l_M15NPWP) ::DECIMAL(20,0) 
			ELSE 
				0 
			END ;
  --
--  IF l_JnsPajak IN('A','B','X','Y')
--     BEGIN
       SELECT fn_GetTax(l_NIP,FLOOR(((l_Income*l_PersenPKP/100))/1000)*1000,l_JnsPajak)
       INTO l_NilPajak;           
--     END
--  ELSE IF l_JnsPajak IN('C','D')
--     BEGIN
--	 -- GAK ADA NPWP + 20$ 
--     IF l_NUMNPWP = 0 
--     BEGIN 
--       SELECT l_NilPajak=(((l_Income*l_PersenPKP/100)*l_PersenPJ1)/100) * l_FXTAX / 100 
--     END 
--	 ELSE
--	 -- ADA NPWP  
--	 BEGIN 
--       SELECT l_NilPajak=((l_Income*l_PersenPKP/100)*l_PersenPJ1)/100
--	 END 
--     END 
   
  -- Mengembalikan Nilai Gaji Pokok
  RETURN l_NilPajak; 
END;
$$ LANGUAGE plpgsql ;
/*
select dbo.fn_TaxCalculationB('X',10000000,'010',100,50)
*/
