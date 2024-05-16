SELECT l_Gagal, l_TableName1, l_mlValid
FROM  P_PAYRP1('2013-05-08' ::DATE, ' ', 'ZZZZ', ' ', 'ZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 
		'L','I','T','01','OK','Copyright, 1988 (c) Microsoft Corporation, All rights reserved') FX; 

SELECT l_mlValid, l_TableName1
FROM  P_UPYRP1 ('2013-05-08' :: DATE, '', 'ZZZZ', '', 'ZZZZZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 'ok');

SELECT S02_ID, S01_ID, TglPayr, NIP, FlgDpPt, KdDpPt, FlgAngs, Singkatan, 
		Nilai, KdCurr, NilaiVal, 
		Kolom, NoAcc, UsComp, NoLyr, FlgNonGL, 
		fn_KPusat(NIP,EncNilai,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS ENCNILAI,  
		fn_KPusat(NIP,EncNilaiVal,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS ENCNILAIVAL  
FROM S02DGAJ WHERE NIP ='PHW-001'; 


SELECT * FROM M15PEGA WHERE NIP ='PHW-001' 
SELECT * from fn_GetKurs('IDR','2013-01-28' ::DATE)  
SELECT NIP FROM T04TTAP WHERE NIP ='PHW-001' ;
select * from m10klas ; 
SELECT COUNT(*) FROM S01HGAJ ; 
SELECT COUNT(*) FROM S02DGAJ ; 

SELECT COUNT(*) FROM S03LTAX ; --6088
SELECT COUNT(*) FROM S04LEMB ; 
SELECT COUNT(*) FROM S05PSTD ; --7288
SELECT COUNT(*) FROM S06ABSH ; 
SELECT COUNT(*) FROM S07TRNG ; 
SELECT COUNT(*) FROM S08MUTA ; 
SELECT COUNT(*) FROM S09HCUT ; 
SELECT COUNT(*) FROM S0ADCUT ; 
SELECT COUNT(*) FROM S0BLSTX ; 
SELECT COUNT(*) FROM S0CMEDH ; 
SELECT COUNT(*) FROM S0DMEDD ; 
SELECT COUNT(*) FROM S0ESFPC ; --623
SELECT COUNT(*) FROM S0FPDAP ; 
SELECT COUNT(*) FROM S0GPREM ; 		

SELECT SETval('s03ltax_s03_id_seq'::regclass, 6088)			    


SELECT * FROM S01HGAJ WHERE NIP ='PHW-001'; 
SELECT * FROM S02DGAJ WHERE NIP ='PHW-001'; 

SELECT * FROM S03LTAX WHERE NIP ='PHW-001'; 
SELECT * FROM S04LEMB WHERE NIP ='PHW-001'; 
SELECT * FROM S05PSTD WHERE NIP ='PHW-001'; 
SELECT * FROM S06ABSH WHERE NIP ='PHW-001'; 
SELECT * FROM S07TRNG WHERE NIP ='PHW-001'; 
SELECT * FROM S08MUTA WHERE NIP ='PHW-001'; 
SELECT * FROM S09HCUT WHERE NIP ='PHW-001'; 
SELECT * FROM S0ADCUT WHERE NIP ='PHW-001'; 
SELECT * FROM S0BLSTX WHERE NIP ='PHW-001'; 
SELECT * FROM S0CMEDH WHERE NIP ='PHW-001'; 
SELECT * FROM S0DMEDD WHERE NIP ='PHW-001'; 
SELECT * FROM S0ESFPC WHERE NIP ='PHW-001'; 
SELECT * FROM S0FPDAP WHERE NIP ='PHW-001'; 
SELECT * FROM S0GPREM WHERE NIP ='PHW-001'; 



--------------buat kill 
SELECT pg_cancel_backend(30795);

select procpid, datname, usename, client_addr,  current_query from pg_stat_activity where current_query!='<IDLE>'; 

SELECT pg_cancel_backend(18568);


---------------------
SELECT l_mlValid, l_TableName1
FROM  P_UPYRP1 ('2013-02-28' :: DATE, '', 'ZZZZ', '', 'ZZZZZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 'ok');

SELECT l_mlValid, l_TableName1
FROM  P_UPYRP1 ('2013-01-28' :: DATE, '', 'ZZZZ', '', 'ZZZZZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 'ok');

SELECT l_mlValid, l_TableName1
FROM  P_UPYRP1 ('2013-01-15' :: DATE, '', 'ZZZZ', '', 'ZZZZZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 'ok');

SELECT S02_ID, S01_ID, TglPayr, NIP, FlgDpPt, KdDpPt, FlgAngs, Singkatan, 
		Nilai, KdCurr, NilaiVal, 
		Kolom, NoAcc, UsComp, NoLyr, FlgNonGL, 
		fn_KPusat(NIP,EncNilai,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS ENCNILAI,  
		fn_KPusat(NIP,EncNilaiVal,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS ENCNILAIVAL  
FROM S02DGAJ WHERE NIP ='PHW-001';	

select l_Gagal, l_TableName1, l_mlValid
from P_PAYHP1('2013-01-15' ::date,
'' ::VARCHAR(4),
'ZZZZ' ::VARCHAR(4),
''  ::VARCHAR(8),
'ZZZZ' ::VARCHAR(8),
'PHW-001' ::VARCHAR(10),
'PHW-001' ::VARCHAR(10),
10 :: decimal(4,1), 
0 :: int, 
' ' ::VARCHAR(4),
'ZZZ'::VARCHAR(4),
' '::VARCHAR(4),
'ZZZ'::VARCHAR(4),    

'01'::VARCHAR(2),
'suhe'::VARCHAR(12),
'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'::VARCHAR(128)) ; 


-- akhir bulan 

select l_Gagal, l_TableName1, l_mlValid
from P_PAYHP1('2013-02-28' ::date,
'' ::VARCHAR(4),
'ZZZZ' ::VARCHAR(4),
''  ::VARCHAR(8),
'ZZZZ' ::VARCHAR(8),
'PHW-001' ::VARCHAR(10),
'PHW-001' ::VARCHAR(10),
10 :: decimal(4,1), 
1 :: int, 
' ' ::VARCHAR(4),
'ZZZ'::VARCHAR(4),
' '::VARCHAR(4),
'ZZZ'::VARCHAR(4),    

'01'::VARCHAR(2),
'suhe'::VARCHAR(12),
'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'::VARCHAR(128)) ; 

	  SELECT *
	  FROM T02ABSN 
	  WHERE NIP = 'PHW-001' ::VARCHAR(10) AND FLGDPPT = 'D' AND KDDPPT ='BSAL' AND 
	       TANGGAL BETWEEN l_S05TGLAKHIR + INTERVAL '1 day' AND l_TGLPROSES AND FLAG = 0 ;

                    -- Gaji Pokok
                    SELECT fn_GajiHarian('2013-01-28' ::date,  
					NULL ::DATE ,
					'PHW-001' ::VARCHAR(10), 
					 '2013-04-01' ::date,       
					 80000,
					 100000,
					 20,
					 'T', -- + BY PEGGY 2007 01 19 
					 '2013-01-01' ::date,  -- + BY PEGGY 2007 01 19 
					 'T') -- + BY PEGGY 2007 01 19 		
		    INTO l_GajiPokok ; 	  
 SELECT * from fn_GetKurs('IDR','2013-01-28' ::date)  
		    SELECT * FROM M15PEGA WHERE NIP ='PHW-001'   
select * from m03dppt where kddppt ='BSAL'
		    SELECT * FROM fn_Vround(100000000.00*1)  


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


SELECT l_Gagal, l_TableName1, l_mlValid
FROM  P_PAYRP1('2013-02-28' ::DATE, ' ', 'ZZZZ', ' ', 'ZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 
		'L','I','T','01','OK','Copyright, 1988 (c) Microsoft Corporation, All rights reserved') FX; 	    
SELECT l_mlValid, l_TableName1
FROM  P_UPYRP1 ('2013-01-28' :: DATE, '', 'ZZZZ', '', 'ZZZZZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 'ok');


SELECT l_Gagal, l_TableName1, l_mlValid
FROM P_PTHR ('2013-03-10' :: DATE, '', 'ZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 
'L', 'I', '01', 'suhe', 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'); 		



SELECT l_Gagal, l_TableName1, l_mlValid
FROM P_PTHR ('2013-03-10' :: DATE, ' ', 'ZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 
'L', 'I', '01', 'suhe', 'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'); 	

SELECT S02_ID, S01_ID, TglPayr, NIP, FlgDpPt, KdDpPt, FlgAngs, Singkatan, 
		Nilai, KdCurr, NilaiVal, 
		Kolom, NoAcc, UsComp, NoLyr, FlgNonGL, 
		fn_KPusat(NIP,EncNilai,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS ENCNILAI,  
		fn_KPusat(NIP,EncNilaiVal,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS ENCNILAIVAL  
FROM S02DGAJ WHERE NIP ='PHW-001';	

selecT * from s01hgaj where nip ='PHW-001' 
selecT * from m15pega where nip ='PHW-001' 
select * from fz1flda 

SELECT l_mlValid, l_TableName1
FROM  P_UPYRP1 ('2013-03-10' :: DATE, '', 'ZZZZ', '', 'ZZZZZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 'ok');


SELECT l_mlValid, l_TableName1
FROM  P_UTHR('2013-03-10' :: DATE, ' ', 'ZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 'ok');

SELECT l_Gagal, l_TableName1, l_mlValid
FROM  P_PAYRP1('2013-02-28' ::DATE, ' ', 'ZZZZ', ' ', 'ZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 
		'L','I','T','01','OK','Copyright, 1988 (c) Microsoft Corporation, All rights reserved') FX; 	
		    
SELECT l_mlValid, l_TableName1
FROM  P_UPYRP1 ('2013-01-28' :: DATE, '', 'ZZZZ', '', 'ZZZZZZZZ', 'PHW-001', 'PHW-001', ' ', 'ZZZ', ' ', 'ZZZ', 'ok');
