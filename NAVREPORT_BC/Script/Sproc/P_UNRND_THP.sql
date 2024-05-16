/****************************************
Name sprocs	: P_UNRND_THP
Create by	: PEGGY 
Date		: 02-04-2008	
Description	: Proses Perhitungan Pembulatan THP CORE 
Call From	: P_UPYRP1 
*****************************************/
CREATE OR REPLACE FUNCTION P_UNRND_THP(l_m15NIP     VARCHAR(10),
					    l_TglProses  DATE) 
						RETURNS VOID 
AS $$ 
--
DECLARE	l_tglnext 	DATE ;

BEGIN 
l_TGLNEXT := l_TGLPROSES + interval '1 day' ;  

-- Delete Pembulatan THP di Transaksi Variabel 
DELETE from T03VARI 
WHERE PrdMulai=l_TGLNEXT AND PrdSd = l_TGLNEXT AND FlgDpPt='P' AND NIP=l_M15NIP AND KdDpPt='PTHP' ; 
END ; 
$$ LANGUAGE plpgsql ;  
