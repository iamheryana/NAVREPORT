/****************************************
Name sprocs	: fn_TotJamLembur
Create by	: Wati
Date		: 20-06-2003
Description	: Proses Total Jam Lembur
Call From	: Main sprocs
*****************************************/
CREATE OR REPLACE function fn_TotJamLembur(l_JamLemb DECIMAL(4,2),l_FlgLemb varCHAR(1),l_Flag INT)
RETURNS DECIMAL(7,2)
AS $$
DECLARE 
	l_TotJamLembur DECIMAL(7,2);
	l_FZ1LimLbr1	INT;	
	l_FZ1LimLbr2	INT;
	l_FZ1RateLbr1	DECIMAL(5,2);	
	l_FZ1RateLbr2	DECIMAL(5,2);
	l_FZ1RateLbr3	DECIMAL(5,2);	
	l_FZ1LimWrk1	INT;
	l_FZ1Pembilang	DECIMAL(3,0);   
	l_FZ1Penyebut	DECIMAL(3,0);
	l_FZ1RateWrk1	DECIMAL(5,2);	
	l_FZ1RateWrk2	DECIMAL(5,2);
    l_FZ1RteLbr1	INT;      
	l_FZ1RteLbr2    INT;
    l_LimLbr1		INT;        
	l_LimLbr2       INT;
BEGIN
	SELECT LimLbr1,LimLbr2,RateLbr1,RateLbr2,RateLbr3,LimWrk1,RateWrk1,RateWrk2,Pembilang,Penyebut,RteLbr1,RteLbr2
	INTO l_FZ1LimLbr1,l_FZ1LimLbr2,l_FZ1RateLbr1,l_FZ1RateLbr2,l_FZ1RateLbr3,l_FZ1LimWrk1,l_FZ1RateWrk1,l_FZ1RateWrk2,l_FZ1Pembilang,l_FZ1Penyebut,l_FZ1RteLbr1,l_FZ1RteLbr2 FROM FZ1FLDA;
  -- Hari Kerja
	IF l_FlgLemb='K' THEN
		IF l_JamLemb>l_FZ1LimWrk1 THEN    
			l_TotJamLembur:=(l_FZ1LimWrk1*l_FZ1RateWrk1)+((l_JamLemb-l_FZ1LimWrk1)*l_FZ1RateWrk2);
		ELSE
            l_TotJamLembur:=(l_JamLemb*l_FZ1RateWrk1); 
		END IF;
	ELSE
		IF l_Flag=1 THEN
            l_FZ1RteLbr1:=l_LimLbr1;
            l_FZ1RteLbr2:=l_LimLbr2;
        ELSE
			l_FZ1LimLbr1 := l_LimLbr1;
			l_FZ1LimLbr2 := l_LimLbr2;
        END IF;
         
       -- Jam Lembur
		IF l_JamLemb<=l_LimLbr1 THEN
			l_TotJamLembur:=(l_JamLemb*l_FZ1RateLbr1);
		ELSIF l_JamLemb>l_LimLbr1 AND l_JamLemb<=(l_LimLbr1+l_LimLbr2) THEN
			l_TotJamLembur:=(l_LimLbr1*l_FZ1RateLbr1)+((l_JamLemb-l_LimLbr1)*l_FZ1RateLbr2);
		ELSIF l_JamLemb>(l_LimLbr1+l_LimLbr2) THEN
			l_TotJamLembur:=(l_LimLbr1*l_FZ1RateLbr1)+(l_LimLbr2*l_FZ1RateLbr2)+((l_JamLemb-l_LimLbr1-l_LimLbr2)*l_FZ1RateLbr3);
		END IF;
	END IF;

  -- Mengembalikan Nilai Total Jam Lembur
  RETURN l_TotJamLembur;
END;
$$
language plpgsql;



/*
SELECT fn_TotJamLembur(12,'K',1);
*/

