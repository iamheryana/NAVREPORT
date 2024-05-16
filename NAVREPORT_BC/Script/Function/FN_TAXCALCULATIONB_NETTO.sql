/****************************************
Name sprocs	: fn_TaxCalculationB_Netto
Create by	: Wati
DATETIME		: 15-07-2003
Description	: Proses Perhitungan Pajak Netto B
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE FUNCTION fn_TaxCalculationB_Netto(l_JnsPajak    VARCHAR(1),
                                         l_Income   	DECIMAL(15,2),  
                                         l_NIP         VARCHAR(10),
                                         l_PersenPKP   DECIMAL(5,2),
                                         l_PersenPJ1   DECIMAL(5,2))
                              
			
RETURNS DECIMAL(15,2)
AS $$
  DECLARE l_FZ1IntvPajak DECIMAL(9,0);
          l_KaliLoop     INT;
          l_PajakCurrent DECIMAL(15,2);
          l_Pajak_Old    DECIMAL(15,2);
          l_Selisih      DECIMAL(15,2);
          l_TotalIncome  DECIMAL(15,2);

BEGIN
  SELECT IntvPajak
  INTO   l_FZ1IntvPajak
         FROM FZ1FLDA;

  l_KaliLoop:=0;
  l_PajakCurrent:=0;
  l_Pajak_Old:=0;
  l_Selisih:=l_FZ1IntvPajak+1;

  WHILE (l_Selisih > l_FZ1IntvPajak) AND (l_KaliLoop <= 20)
  LOOP 
    l_Pajak_Old    := l_PajakCurrent;
    l_TotalIncome  := l_Income+l_Pajak_Old;
    l_PajakCurrent := fn_TaxCalculationB(l_JnsPajak,l_TotalIncome,l_NIP,l_PersenPKP,l_PersenPJ1);
    l_Selisih      := ABS(l_PajakCurrent-l_Pajak_Old);
    l_KaliLoop     := l_KaliLoop+1;

  END LOOP; 
  RETURN l_PajakCurrent;
END;
$$ LANGUAGE plpgsql ;
/*
select dbo.fn_TaxCalculationB_Netto('A',20000000,'01',25,20)
*/
