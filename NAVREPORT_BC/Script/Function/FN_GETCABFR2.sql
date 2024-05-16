/****************************************
Name sprocs	: fn_GetCabFR2
Create by	: Suhe
Date		: 19-08-2003
Description	: Untuk Dapat Bulan From dari SPT 1721 A1 per Cabang
Call From	: P_PRENP1
Sub sprocs	: 

SELECT fn_GetCabFR('B   ',3,2012,'201203001 ');

*****************************************/
CREATE OR REPLACE FUNCTION fn_GetCabFR2(l_CabFr	VARCHAR(4),
			      l_CabTo    VARCHAR(4),
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
	l_Pindah	VARCHAR(1);
	l_mlStop	VARCHAR(1);

BEGIN
    -- Ambil Posisi Pindah Cabang
    SELECT Cab01,Cab02,Cab03,Cab04,Cab05,Cab06,Cab07,Cab08,Cab09,Cab10,Cab11,Cab12
	INTO l_Cab01,l_Cab02,l_Cab03,l_Cab04,l_Cab05,l_Cab06,l_Cab07,l_Cab08,l_Cab09,l_Cab10,l_Cab11,l_Cab12
	FROM S0ESFPC
    WHERE tahun=l_Tahun AND NIP=l_NIP;
    --
    l_Loop := 1;
	IF l_Cab01=l_CabFr OR l_Cab01=l_CabTo THEN
		l_BulanFR :=1;
	ELSE
		l_BulanFR :=0;
	END IF;
	l_Pindah := 'T';

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
		IF l_Cabang <> l_CabFr OR l_Cabang <> l_CabTo THEN
			l_Pindah := 'Y';
			l_mlStop := 'T';
		END IF;
		IF (l_Cabang=l_CabFr OR l_Cabang=l_CabTo) AND l_Pindah='Y' AND l_mlStop='T' THEN
			l_BulanFR:=l_Loop;
			l_mlStop:='Y';
		END IF;
		l_Loop := l_Loop + 1;
    END LOOP;
	l_NmBulanFr:='TEST';
	IF l_BulanFR = 0 THEN
		l_NmBulanFr='Januari';
	ELSIF l_BulanFR = 1 THEN
		l_NmBulanFr='Januari';
	ELSIF l_BulanFR = 2 THEN
		l_NmBulanFr='Februari';
	ELSIF l_BulanFR = 3 THEN
		l_NmBulanFr='Maret';
	ELSIF l_BulanFR = 4 THEN
		l_NmBulanFr='April';
	ELSIF l_BulanFR = 5 THEN
		l_NmBulanFr='Mei';
	ELSIF l_BulanFR = 6 THEN
		l_NmBulanFr='Juni';
	ELSIF l_BulanFR = 7 THEN
		l_NmBulanFr='Juli';
	ELSIF l_BulanFR = 8 THEN
		l_NmBulanFr='Agustus';
	ELSIF l_BulanFR = 9 THEN
		l_NmBulanFr='September';
	ELSIF l_BulanFR = 10 THEN
		l_NmBulanFr='Oktober';
	ELSIF l_BulanFR = 11 THEN
		l_NmBulanFr='November';
	ELSIF l_BulanFR = 12 THEN
		l_NmBulanFr='Desember';
	END IF;

	RETURN l_NmBulanFr;
end;
$$
language plpgsql;

/*
SELECT fn_GetCabFR2('J','J',11,2009,'00003');
*/




