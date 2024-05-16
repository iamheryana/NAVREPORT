SELECT TglPayr, NIP, Nama, 
	fn_KPusat(NIP,EncTakeHomePay,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncTakeHomePay,  
	fn_KPusat(NIP,EncTotInc,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncTotInc,    
	fn_KPusat(NIP,EncTotDed,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncTotDed,   
	fn_KPusat(NIP,EncGrossIncomeNBYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncGrossIncomeNBYTD,
	fn_KPusat(NIP,EncOccSupport1,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOccSupport1,
	fn_KPusat(NIP,EncCol12YTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncCol12YTD,   
	fn_KPusat(NIP,EncEGIYNB,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncEGIYNB,   
	fn_KPusat(NIP,EncPTKP,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncPTKP,   
	fn_KPusat(NIP,EncEYTI,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncEYTI,   
	fn_KPusat(NIP,EncEYIT,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncEYIT,   
	fn_KPusat(NIP,EncYTDIT,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncYTDIT,   
	fn_KPusat(NIP,EncIncTaxAPaidNB,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncIncTaxAPaidNB,    
	fn_KPusat(NIP,EncGrossIncomeBYTD,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncGrossIncomeBYTD,   
	fn_KPusat(NIP,EncOccSupport2,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncOccSupport2,   
	fn_KPusat(NIP,EncTaxTotal,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncTaxTotal,   
	fn_KPusat(NIP,EncIncTaxAPaidB,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS EncIncTaxAPaidB,    
	fn_KPusat(NIP,EncTaxPesangonRp,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') EncTaxPesangonRp    
FROM S01HGAJ
where nip =  'PHW-001' and EXTRACT(YEAR FROM tglpayr) = '2013'
order by nip,tglpayr;
