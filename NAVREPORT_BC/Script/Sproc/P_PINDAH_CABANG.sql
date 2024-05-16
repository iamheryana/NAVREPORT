/****************************************
Name sprocs	: P_Pindah_Cabang
Create by	: Wati
Date		: 16-04-2003
Description	: Untuk Masukkn Data S01, S02,S03,S0E dari perhitungan Pajak
Call From	: Sub Routine
Sub sprocs	: -
*****************************************/
DROP FUNCTION P_Pindah_Cabang(
                     l_NewKdCaba    		VARCHAR(4),
                     l_OldKdCaba    		VARCHAR(4), 
                     l_FZ2FlgCabang 		VARCHAR(1),
                     -- FixIncome  
                     l_FixIncome        	DECIMAL(15,2),
                     INOUT l_FixIncomePYTD    	DECIMAL(15,2),
                     INOUT l_OLDFixIncomePYTD 	DECIMAL(15,2),
                     -- VariIncome 
                     l_VarIncome        	DECIMAL(15,2),
                     INOUT l_VarIncomePYTD    	DECIMAL(15,2),
                     INOUT l_OLDVarIncomePYTD 	DECIMAL(15,2),
                     -- Kolom2
                     l_Kolom2           	DECIMAL(15,2),  
                     INOUT l_Col2YTD          	DECIMAL(15,2),
                     INOUT l_OLDCol2YTD       	DECIMAL(15,2),
		     -- Kolom3 
		     l_Kolom3           	DECIMAL(15,2),
		     INOUT l_Col3YTD          	DECIMAL(15,2),
		     INOUT l_OLDCol3YTD       	DECIMAL(15,2),
		     -- Kolom4
		     l_Kolom4           	DECIMAL(15,2),  
		     INOUT l_Col4YTD          	DECIMAL(15,2),
		     INOUT l_OLDCol4YTD       	DECIMAL(15,2),
		     -- Kolom5
		     l_Kolom5           	DECIMAL(15,2),  
		     INOUT l_Col5YTD          	DECIMAL(15,2),
		     INOUT l_OLDCol5YTD       	DECIMAL(15,2),
		     -- Kolom6
		     l_Kolom6           	DECIMAL(15,2),
	             INOUT l_Col6YTD 	       	DECIMAL(15,2),
		     INOUT l_OLDCol6YTD       	DECIMAL(15,2),
		     -- Kolom12
		     INOUT l_Col12PYTD        	DECIMAL(15,2),
		     INOUT l_OLDCol12PYTD     	DECIMAL(15,2),
		     -- Kolom8/Bonus
		     INOUT l_Col8PYTD 	       	DECIMAL(15,2),
		     INOUT l_OLDCol8PYTD      	DECIMAL(15,2),
		     -- OccSupport1
		     l_OccSupport1PYTD  	DECIMAL(15,2),
		     INOUT l_OLDOccSupport1   	DECIMAL(15,2),
		     -- OccSupport2
		     l_OccSupport2PYTD  	DECIMAL(15,2),
		     INOUT l_OLDOccSupport2   	DECIMAL(15,2),
	             -- Tax Allready Paid Non Bonus 
		     INOUT l_IncTaxAPaidNB    	DECIMAL(15,2),
		     INOUT l_OLDIncTaxAPaidNB 	DECIMAL(15,2),
		     -- Tax Allready Paid Bonus 
		     INOUT l_IncTaxAPaidB     	DECIMAL(15,2),
		     INOUT l_OLDIncTaxAPaidB  	DECIMAL(15,2),
                     -- UMP
                     l_TaxUMP           	DECIMAL(15,2),
                     l_TaxAPaidUMP      	DECIMAL(15,2),
                     l_TaxUMP_PYTD      	DECIMAL(15,2),  
                     INOUT l_TaxUMPYTD        	DECIMAL(15,2));
--
CREATE FUNCTION P_Pindah_Cabang(
                     l_NewKdCaba    		VARCHAR(4),
                     l_OldKdCaba    		VARCHAR(4), 
                     l_FZ2FlgCabang 		VARCHAR(1),
                     -- FixIncome  
                     l_FixIncome        	DECIMAL(15,2),
                     INOUT l_FixIncomePYTD    	DECIMAL(15,2),
                     INOUT l_OLDFixIncomePYTD 	DECIMAL(15,2),
                     -- VariIncome 
                     l_VarIncome        	DECIMAL(15,2),
                     INOUT l_VarIncomePYTD    	DECIMAL(15,2),
                     INOUT l_OLDVarIncomePYTD 	DECIMAL(15,2),
                     -- Kolom2
                     l_Kolom2           	DECIMAL(15,2),  
                     INOUT l_Col2YTD          	DECIMAL(15,2),
                     INOUT l_OLDCol2YTD       	DECIMAL(15,2),
		     -- Kolom3 
		     l_Kolom3           	DECIMAL(15,2),
		     INOUT l_Col3YTD          	DECIMAL(15,2),
		     INOUT l_OLDCol3YTD       	DECIMAL(15,2),
		     -- Kolom4
		     l_Kolom4           	DECIMAL(15,2),  
		     INOUT l_Col4YTD          	DECIMAL(15,2),
		     INOUT l_OLDCol4YTD       	DECIMAL(15,2),
		     -- Kolom5
		     l_Kolom5           	DECIMAL(15,2),  
		     INOUT l_Col5YTD          	DECIMAL(15,2),
		     INOUT l_OLDCol5YTD       	DECIMAL(15,2),
		     -- Kolom6
		     l_Kolom6           	DECIMAL(15,2),
	             INOUT l_Col6YTD 	       	DECIMAL(15,2),
		     INOUT l_OLDCol6YTD       	DECIMAL(15,2),
		     -- Kolom12
		     INOUT l_Col12PYTD        	DECIMAL(15,2),
		     INOUT l_OLDCol12PYTD     	DECIMAL(15,2),
		     -- Kolom8/Bonus
		     INOUT l_Col8PYTD 	       	DECIMAL(15,2),
		     INOUT l_OLDCol8PYTD      	DECIMAL(15,2),
		     -- OccSupport1
		     l_OccSupport1PYTD  	DECIMAL(15,2),
		     INOUT l_OLDOccSupport1   	DECIMAL(15,2),
		     -- OccSupport2
		     l_OccSupport2PYTD  	DECIMAL(15,2),
		     INOUT l_OLDOccSupport2   	DECIMAL(15,2),
	             -- Tax Allready Paid Non Bonus 
		     INOUT l_IncTaxAPaidNB    	DECIMAL(15,2),
		     INOUT l_OLDIncTaxAPaidNB 	DECIMAL(15,2),
		     -- Tax Allready Paid Bonus 
		     INOUT l_IncTaxAPaidB     	DECIMAL(15,2),
		     INOUT l_OLDIncTaxAPaidB  	DECIMAL(15,2),
                     -- UMP
                     l_TaxUMP           	DECIMAL(15,2),
                     l_TaxAPaidUMP      	DECIMAL(15,2),
                     l_TaxUMP_PYTD      	DECIMAL(15,2),  
                     INOUT l_TaxUMPYTD        	DECIMAL(15,2))

AS $$
---
DECLARE l_S03FixIncomePYTD    DECIMAL(15,2); 
        l_S03OLDFixIncomePYTD DECIMAL(15,2);
        -- VariIncome 
        l_S03VarIncomePYTD    DECIMAL(15,2);
        l_S03OLDVarIncomePYTD DECIMAL(15,2);
        -- Kolom2
        l_S03Col2YTD          DECIMAL(15,2);
        l_S03OLDCol2YTD       DECIMAL(15,2);
        -- Kolom3 
	l_S03Col3YTD          DECIMAL(15,2);
	l_S03OLDCol3YTD       DECIMAL(15,2);
	-- Kolom4
	l_S03Col4YTD          DECIMAL(15,2);
	l_S03OLDCol4YTD       DECIMAL(15,2);
	-- Kolom5
	l_S03Col5YTD          DECIMAL(15,2);
	l_S03OLDCol5YTD       DECIMAL(15,2);
	-- Kolom6
	l_S03Col6YTD          DECIMAL(15,2);
	l_S03OLDCol6YTD       DECIMAL(15,2);
	-- Kolom12
	l_S03Col12PYTD        DECIMAL(15,2);
	l_S03OLDCol12PYTD     DECIMAL(15,2);
	-- Kolom8/Bonus
	l_S03Col8PYTD 	     DECIMAL(15,2);
	l_S03OLDCol8PYTD      DECIMAL(15,2);
	-- OccSupport1
	l_S03OccSupport1PYTD  DECIMAL(15,2);
	l_S03OLDOccSupport1   DECIMAL(15,2);
	-- OccSupport2
	l_S03OccSupport2PYTD  DECIMAL(15,2);
	l_S03OLDOccSupport2   DECIMAL(15,2);
	-- Tax Allready Paid Non Bonus 
	l_S03IncTaxAPaidNB    DECIMAL(15,2);
	l_S03OLDIncTaxAPaidNB DECIMAL(15,2);
	-- Tax Allready Paid Bonus 
	l_S03IncTaxAPaidB     DECIMAL(15,2);
	l_S03OLDIncTaxAPaidB  DECIMAL(15,2);

BEGIN 
l_S03FixIncomePYTD     := l_FixIncomePYTD; 
l_S03OLDFixIncomePYTD  := l_OLDFixIncomePYTD;
-- VariIncome 
l_S03VarIncomePYTD     := l_VarIncomePYTD;
l_S03OLDVarIncomePYTD  := l_OLDVarIncomePYTD;
-- Kolom2
l_S03Col2YTD           := l_Col2YTD;
l_S03OLDCol2YTD        := l_OLDCol2YTD;
-- Kolom3 
l_S03Col3YTD           := l_Col3YTD;
l_S03OLDCol3YTD        := l_OLDCol3YTD;
-- Kolom4
l_S03Col4YTD           := l_Col4YTD;
l_S03OLDCol4YTD        := l_OLDCol4YTD;
-- Kolom5
l_S03Col5YTD           := l_Col5YTD;
l_S03OLDCol5YTD        := l_OLDCol5YTD;
-- Kolom6
l_S03Col6YTD           := l_Col6YTD;
l_S03OLDCol6YTD        := l_OLDCol6YTD;
-- Kolom12
l_S03Col12PYTD         := l_Col12PYTD;
l_S03OLDCol12PYTD      := l_OLDCol12PYTD;
-- Kolom8/Bonus
l_S03Col8PYTD 	       := l_Col8PYTD;
l_S03OLDCol8PYTD       := l_OLDCol8PYTD;
-- OccSupport1
l_S03OccSupport1PYTD   := l_OccSupport1PYTD;
l_S03OLDOccSupport1    := l_OLDOccSupport1;
-- OccSupport2
l_S03OccSupport2PYTD   := l_OccSupport2PYTD;
l_S03OLDOccSupport2    := l_OLDOccSupport2;
-- Tax Allready Paid Non Bonus 
l_S03IncTaxAPaidNB     := l_IncTaxAPaidNB;
l_S03OLDIncTaxAPaidNB  := l_OLDIncTaxAPaidNB;
-- Tax Allready Paid Bonus 
l_S03IncTaxAPaidB      := l_IncTaxAPaidB;
l_S03OLDIncTaxAPaidB   := l_OLDIncTaxAPaidB;

l_FixIncomePYTD    := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN 0
			ELSE COALESCE(l_S03FixIncomePYTD,0) END;
l_OLDFixIncomePYTD := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03FixIncomePYTD+l_S03OLDFixIncomePYTD,0)
			ELSE COALESCE(l_S03OLDFixIncomePYTD,0) END;
-- VariIncome 
l_VarIncomePYTD    := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN 0
			ELSE COALESCE(l_S03VarIncomePYTD,0) END;
l_OLDVarIncomePYTD := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03VarIncomePYTD+l_S03OLDVarIncomePYTD,0)
			ELSE COALESCE(l_S03OLDVarIncomePYTD,0) END;
-- Kolom2
l_Col2YTD         := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_Kolom2,0)
		       ELSE COALESCE(l_Kolom2+l_S03Col2YTD,0) END;
l_OLDCol2YTD      := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03Col2YTD+l_S03OLDCol2YTD,0)
		       ELSE COALESCE(l_S03OLDCol2YTD,0) END;
-- Kolom3 
l_Col3YTD         := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_Kolom3,0)
		       ELSE COALESCE(l_Kolom3+l_S03Col3YTD,0) END;
l_OLDCol3YTD      := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03Col3YTD+l_S03OLDCol3YTD,0)
		       ELSE COALESCE(l_S03OLDCol3YTD,0) END;
-- Kolom4
l_Col4YTD         := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_Kolom4,0)
		       ELSE COALESCE(l_Kolom4+l_S03Col4YTD,0) END;
l_OLDCol4YTD      := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03Col4YTD+l_S03OLDCol4YTD,0)
		       ELSE COALESCE(l_S03OLDCol4YTD,0) END;
-- Kolom5
l_Col5YTD         := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_Kolom5,0)
		       ELSE COALESCE(l_Kolom5+l_S03Col5YTD,0) END;
l_OLDCol5YTD      := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03Col5YTD+l_S03OLDCol5YTD,0)
		       ELSE COALESCE(l_S03OLDCol5YTD,0) END;
-- Kolom6
l_Col6YTD         := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_Kolom6,0)
		       ELSE COALESCE(l_Kolom6+l_S03Col6YTD,0) END;
l_OLDCol6YTD      := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03Col6YTD+l_S03OLDCol6YTD,0)
		       ELSE COALESCE(l_S03OLDCol6YTD,0) END;
-- Kolom12
l_Col12PYTD       := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN 0
		       ELSE COALESCE(l_S03Col12PYTD,0) END;
l_OLDCol12PYTD    := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03Col12PYTD+l_S03OLDCol12PYTD,0)
		       ELSE COALESCE(l_S03OLDCol12PYTD,0) END;
-- Kolom8/Bonus
l_Col8PYTD        := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN 0
		       ELSE COALESCE(l_S03Col8PYTD,0) END;
l_OLDCol8PYTD     := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03Col8PYTD+l_S03OLDCol8PYTD,0)
		       ELSE COALESCE(l_S03OLDCol8PYTD,0) END;
-- OccSupport1
l_OLDOccSupport1  := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03OccSupport1PYTD,0)
		       ELSE COALESCE(l_S03OldOccSupport1,0) END;
-- OccSupport2
l_OLDOccSupport2  := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03OccSupport2PYTD,0)
		       ELSE COALESCE(l_S03OldOccSupport2,0) END;
-- Tax Allready Paid Non Bonus 
l_IncTaxAPaidNB   := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN 0
		       ELSE COALESCE(l_S03IncTaxAPaidNB-l_S03OLDIncTaxAPaidNB,0) END;
l_OLDIncTaxAPaidNB:= CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03IncTaxAPaidNB,0)
		       ELSE COALESCE(l_S03OLDIncTaxAPaidNB,0) END;
-- Tax Allready Paid Bonus 
l_IncTaxAPaidB    := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN 0
		       ELSE COALESCE(l_S03IncTaxAPaidB-l_S03OLDIncTaxAPaidB,0) END;
l_OLDIncTaxAPaidB := CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN COALESCE(l_S03IncTaxAPaidB,0)
		       ELSE COALESCE(l_S03OLDIncTaxAPaidB,0) END; 
-- UMP
l_TaxUMPYTD       := COALESCE(l_TAXUMPYTD,0); --CASE WHEN (l_NewKdCaba<>l_OldKdCaba AND l_FZ2FlgCabang='Y') THEN (COALESCE(l_TaxUMP,0)-COALESCE(l_TaxAPaidUMP,0))
		  --     ELSE (COALESCE(l_TaxUMP_PYTD,0)+(COALESCE(l_TaxUMP,0)-COALESCE(l_TaxAPaidUMP,0))) END 
END ; 
$$ LANGUAGE plpgsql ;

