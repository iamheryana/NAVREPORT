/****************************************
Name sprocs	: fn_HakUMP2
Create by	: Wati
Date		: 28-07-2003
Description	: Proses Untuk HakUMP
Call From	: Main sprocs
*****************************************/

DROP FUNCTION public.fn_hakump2 (in p_harian varchar, in bruto numeric, in maxbruto numeric, in kewarganegaraan varchar, in flgdll varchar)
GO
--
CREATE OR REPLACE FUNCTION fn_HakUMP2(P_Harian VARCHAR(1),Bruto DECIMAL(15,2),MaxBruto DECIMAL(15,2),Kewarganegaraan VARCHAR(20),FlgDLL VARCHAR(1))
RETURNS VARCHAR(1)
AS $$

DECLARE 
	HakUMP   VARCHAR(1);
BEGIN 
 
  IF P_Harian='Y' AND Bruto<=MaxBruto THEN
    HakUMP := 'Y';
  ELSIF P_Harian='T' AND Bruto<=MaxBruto AND Kewarganegaraan='INDONESIA' AND  FlgDLL='T' THEN
	HakUMP := 'Y';
  ELSE 
    HakUMP := 'T';
  END IF;
  RETURN HakUMP;
end;
$$
language plpgsql;



/*
SELECT dbo.fn_HakUMP2('T',2000000,2000000,'INDONESIA','T')
*/
--SELECT fn_HakUMP2('T',2000000,2000000,'INDONESIA','T');
