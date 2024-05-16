SELECT TglPayr, NIP, KdCaba, 
fn_KPusat(NIP,EncOccSupport1,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOccSupport1,
fn_KPusat(NIP,EncOLDOccSupport1,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDOccSupport1, 
--	[FixIncomePYTD], [OLDFixIncomePYTD], [FixIncome], [VarIncomePYTD], [OLDVarIncomePYTD], [VarIncome], [Col2YTD], [OLDCol2YTD], [Col3YTD], [OLDCol3YTD], [Col4YTD], [OLDCol4YTD], [Col5YTD], [OLDCol5YTD], [Col6YTD], [OLDCol6YTD], [Col12PYTD], [OLDCol12PYTD], [Kolom12], [OccSupport1], [OLDOccSupport1], [EGIYNB], [PTKP], [EYTI], [EYIT], [YTDIT], [IncTaxAPaidNB], [OLDIncTaxAPaidNB], [Col8PYTD], [OLDCol8PYTD], [Kolom8], [OccSupport2], [OLDOccSupport2], [EYITT], [IncTaxAPaidB], [OLDIncTaxAPaidB], [TotPot], [FlagTHR], [Userid], [UpdDate], [UpdTime], [WS], [Harian], [FlgStruk], [FlgPdLL], [kdUMP], [NilUMP], [TaxUMP], [TaxAPaidUMP], [TaxUMPYTD], [Hari], [MaxBruto], [PendDP], 
fn_KPusat(NIP,EncFixIncomePYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncFixIncomePYTD, 
fn_KPusat(NIP,EncOLDFixIncomePYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDFixIncomePYTD, 
fn_KPusat(NIP,EncFixIncome,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncFixIncome, 
fn_KPusat(NIP,EncVarIncomePYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncVarIncomePYTD, 
fn_KPusat(NIP,EncOLDVarIncomePYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDVarIncomePYTD, 
fn_KPusat(NIP,EncVarIncome,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncVarIncome, 
fn_KPusat(NIP,EncCol2YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncCol2YTD, 
fn_KPusat(NIP,EncOLDCol2YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDCol2YTD, 
fn_KPusat(NIP,EncCol3YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncCol3YTD, 
fn_KPusat(NIP,EncOLDCol3YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDCol3YTD, 
fn_KPusat(NIP,EncCol4YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncCol4YTD, 
fn_KPusat(NIP,EncOLDCol4YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDCol4YTD, 
fn_KPusat(NIP,EncCol5YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncCol5YTD,  
fn_KPusat(NIP,EncOLDCol5YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDCol5YTD, 
fn_KPusat(NIP,EncCol6YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncCol6YTD, 
fn_KPusat(NIP,EncOLDCol6YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDCol6YTD, 
fn_KPusat(NIP,EncOLDOccSupport2,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDOccSupport2, 
fn_KPusat(NIP,EncEYITT,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncEYITT, 
fn_KPusat(NIP,EncIncTaxAPaidB,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncIncTaxAPaidB, 
fn_KPusat(NIP,EncOLDIncTaxAPaidB,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDIncTaxAPaidB, 
fn_KPusat(NIP,EncCol12PYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncCol12PYTD, 
fn_KPusat(NIP,EncOLDCol12PYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDCol12PYTD, 
fn_KPusat(NIP,EncKolom12,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncKolom12, 
fn_KPusat(NIP,EncOccSupport1,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOccSupport1, 
fn_KPusat(NIP,EncOLDOccSupport1,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDOccSupport1, 
fn_KPusat(NIP,EncEGIYNB,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncEGIYNB, 
fn_KPusat(NIP,EncPTKP,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncPTKP, 
fn_KPusat(NIP,EncEYTI,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncEYTI, 
fn_KPusat(NIP,EncEYIT,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncEYIT, 
fn_KPusat(NIP,EncYTDIT,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncYTDIT, 
fn_KPusat(NIP,EncIncTaxAPaidNB,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncIncTaxAPaidNB, 
fn_KPusat(NIP,EncOLDIncTaxAPaidNB,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDIncTaxAPaidNB, 
fn_KPusat(NIP,EncCol8PYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncCol8PYTD, 
fn_KPusat(NIP,EncOLDCol8PYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDCol8PYTD, 
fn_KPusat(NIP,EncKolom8,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncKolom8, 
fn_KPusat(NIP,EncOccSupport2,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOccSupport2, 
fn_KPusat(NIP,EncoldOccSupport2,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncoldOccSupport2, 
fn_KPusat(NIP,EncEYITT,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncEYITT, 
fn_KPusat(NIP,EncIncTaxAPaidB,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncIncTaxAPaidB, 
fn_KPusat(NIP,EncOLDIncTaxAPaidB,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOLDIncTaxAPaidB,  
fn_KPusat(NIP,EncTotPot,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncTotPot, 
fn_KPusat(NIP,EncNilUMP,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncNilUMP, 
fn_KPusat(NIP,EncTaxUMP,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncTaxUMP, 
fn_KPusat(NIP,EncTaxAPaidUMP,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncTaxAPaidUMP, 
fn_KPusat(NIP,EncTaxUMPYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncTaxUMPYTD, 
fn_KPusat(NIP,EncMaxBruto,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncMaxBruto, 
fn_KPusat(NIP,EncPendDP,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncPendDP

-- (CONVERT(DECIMAL(15,2),Fn_Kpusat(S03.NIP,S03.EncYTDIT,@MyPass))+
-- CONVERT(DECIMAL(15,2),Fn_Kpusat(S03.NIP,S03.EncEYITT,@MyPass)))-
-- CONVERT(DECIMAL(15,2),Fn_Kpusat(S03.NIP,S03.EncEYIT,@MyPass)) 
--FROM v_ltax--S03LTAX
from s03ltax
--where nip = 'TAP050011' and YEAR(tglpayr)='2011'
where nip = 'PHW-001' and EXTRACT(YEAR FROM tglpayr)='2013'

-- USE TEMP2
-- 2013-03-28 00:00:00.000	PS.005    	JKT 	4496161.00       	0.00             	44100000.00      	0.00             	22050000.00      	2499887.00       	0.00             	2996790.00       	18276550.00      	0.00             	5139467.00       	0.00             	0.00             	0.00             	357210.00        	0.00             	0.00             	0.00             	0.00             	63977000.00      	0.00             	0.00             	0.00             	0.00             	0.00             	4496161.00       	0.00             	371708264.00     	24300000.00      	347408000.00     	56852000.00      	14213000.00      	7183250.00       	0.00             	0.00             	0.00             	30000000.00      	1500000.00       	0.00             	63977000.00      	0.00             	0.00             	0	NULL	NULL	NULL	0.00             	0.00             	0.00             
