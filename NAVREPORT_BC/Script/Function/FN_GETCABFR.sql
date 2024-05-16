/****************************************
Name sprocs	: fn_GetCabFR
Create by	: Suhe
Date		: 19-08-2003
Description	: Untuk Dapat Bulan From dari SPT 1721 A1 per Cabang
Call From	: P_PRENP1
Sub sprocs	: 

SELECT fn_GetCabFR('J',3,2009,'00003');
SELECT fn_GetCabFR('B   ',3,2012,'201203001 ');
SELECT fn_GetCabFR('J   ',3,2012,'201201003');
SELECT fn_GetCabFR('J   ',7,2012,'201201003');
*****************************************/
CREATE OR REPLACE FUNCTION fn_GetCabFR(l_CabFr	VARCHAR(4),
			     l_MonthTo	INT,
			     l_Tahun	INT,
			     l_NIP	VARCHAR(10))	 
RETURNS VARCHAR(9)
AS $$
DECLARE 
	   l_Loop	INT;
       l_Cab01 	VARCHAR(4);
	   l_Cab02 	VARCHAR(4);
	   l_Cab03 	VARCHAR(4);
	   l_Cab04 	VARCHAR(4);
	   l_Cab05 	VARCHAR(4);
	   l_Cab06 	VARCHAR(4);
	   l_Cab07 	VARCHAR(4);
	   l_Cab08 	VARCHAR(4);
	   l_Cab09 	VARCHAR(4);
	   l_Cab10 	VARCHAR(4);
	   l_Cab11 	VARCHAR(4);
	   l_Cab12 	VARCHAR(4);
	   l_Cabang	VARCHAR(4);	   
	   l_BulanFR 	INT;
	   l_NmBulanFr	VARCHAR(9);
BEGIN
    -- Ambil Posisi Pindah Cabang
    SELECT 	Cab01,Cab02,Cab03,Cab04,Cab05,Cab06,Cab07,Cab08,Cab09,Cab10,Cab11,Cab12
    INTO l_Cab01,l_Cab02,l_Cab03,l_Cab04,l_Cab05,l_Cab06,l_Cab07,l_Cab08,l_Cab09,l_Cab10,l_Cab11,l_Cab12
	FROM S0ESFPC
    WHERE tahun=l_Tahun AND NIP=l_NIP;
	
	l_Loop  :=1;
    l_BulanFR:=0;
    WHILE l_Loop <= l_MonthTo LOOP
		IF l_Loop = 1 THEN
			l_Cabang := l_Cab01;
		ELSIF l_Loop = 2 THEN
			l_Cabang := l_Cab02;
		ELSIF l_Loop = 3 THEN
			l_Cabang := l_Cab03;
		ELSIF l_Loop = 4 THEN
			l_Cabang := l_Cab04;
		ELSIF l_Loop = 5 THEN
			l_Cabang := l_Cab05;
		ELSIF l_Loop = 6 THEN
			l_Cabang := l_Cab06;
		ELSIF l_Loop = 7 THEN
			l_Cabang := l_Cab07;
		ELSIF l_Loop = 8 THEN
			l_Cabang := l_Cab08;
		ELSIF l_Loop = 9 THEN
			l_Cabang := l_Cab09;
		ELSIF l_Loop = 10 THEN
			l_Cabang := l_Cab10;
		ELSIF l_Loop = 11 THEN
			l_Cabang := l_Cab11;
		ELSIF l_Loop = 12 THEN
			l_Cabang := l_Cab12;
		END IF;
        
		IF l_Cabang<>l_CabFr THEN
			l_BulanFR:=0;
		END IF;
		IF l_Cabang=l_CabFr AND l_BulanFR=0 THEN
			l_BulanFR:=l_Loop;
        END IF;
		l_Loop := l_Loop + 1;
	END LOOP;
	
	l_NmBulanFr:='TEST';
	
	CASE l_BulanFR
		WHEN 0 THEN l_NmBulanFr:='Januari';
        WHEN 1 THEN l_NmBulanFr:='Januari';
        WHEN 2 THEN l_NmBulanFr:='Februari';
        WHEN 3 THEN l_NmBulanFr:='Maret';
        WHEN 4 THEN l_NmBulanFr:='April';
        WHEN 5 THEN l_NmBulanFr:='Mei';
        WHEN 6 THEN l_NmBulanFr:='Juni';
        WHEN 7 THEN l_NmBulanFr:='Juli';
        WHEN 8 THEN l_NmBulanFr:='Agustus';
        WHEN 9 THEN l_NmBulanFr:='September';
        WHEN 10 THEN l_NmBulanFr:='Oktober';
        WHEN 11 THEN l_NmBulanFr:='November';
        WHEN 12 THEN l_NmBulanFr:='Desember';
	END CASE;
	
	RETURN l_NmBulanFr;
end;
$$
language plpgsql;


/*
SELECT fn_GetCabFR('AB47',7,2003,'suhe');
*/




