/****************************************
Name sprocs	: P_Pend_RapelTHR
Create by	: wati
Date		: 17-06-2003
Description	: Proses Pendapatan Rapel THR
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Pend_RapelTHR(l_TglProses    DATE,
                            l_FZ2PrdRapel  DATE,
                            l_FZ2THRHarian VARCHAR(1),
                            l_TglMasuk     DATE,
                            l_GajiRp       DECIMAL(15,2),
                            l_NIP          VARCHAR(10),
                            l_BlnTHR       DECIMAL(5,2), 
                            l_MyPass    VARCHAR(128),
                            l_S01_ID    INT);      
--
CREATE FUNCTION P_Pend_RapelTHR(l_TglProses    DATE,
                            l_FZ2PrdRapel  DATE,
                            l_FZ2THRHarian VARCHAR(1),
                            l_TglMasuk     DATE,
                            l_GajiRp       DECIMAL(15,2),
                            l_NIP          VARCHAR(10),
                            l_BlnTHR       DECIMAL(5,2), 
                            l_MyPass    VARCHAR(128),
                            l_S01_ID    INT)      

RETURNS VOID 
AS $$

DECLARE l_RapelTHR      DECIMAL(15,2);
        l_RapelTHRVal   DECIMAL(15,2);
        l_LOOP_S02      REFCURSOR;
        l_S02TglPayr    DATE;
        l_GajiLama      DECIMAL(15,2);

BEGIN 
OPEN l_LOOP_S02 FOR EXECUTE('SELECT TglPayr
			     FROM S02DGAJ 
			     WHERE NIP=l_NIP AND KdDpPt=''THR'' AND
			           (DATE_TRUNC(''month'',TglPayr) >= DATE_TRUNC(''month'',l_FZ2PrdRapel)) AND
			           (DATE_TRUNC(''month'',TglPayr) < DATE_TRUNC(''month'',,l_TglProses))');
LOOP 
    FETCH l_LOOP_S02 INTO l_S02TglPayr;
    IF NOT FOUND THEN
       EXIT ;
    END IF;
    --
    l_GajiLama := 0; 
    ---
    SELECT fn_KPusat(l_NIP,EncNilai,l_MyPass) ::DECIMAL(15,2)
    INTO   l_GajiLama
    FROM S02DGAJ
    WHERE NIP=l_NIP AND KdDpPt='BSAL' AND TglPayr<=l_S02TglPayr;    
    -- RapelTHR
    l_RapelTHR := l_GajiRp-l_GajiLama;
    -- 
    IF l_RapelTHR<>0 THEN 
	BEGIN	
           SELECT P_Nilai_THR (l_TglProses,
	                        l_FZ2PrdRapel,
	                        l_TglMasuk,
	                        l_NIP,
	                        l_FZ2THRHarian,
	                        l_RapelTHR,  
	                        l_BlnTHR,
				'RPLT',
				l_MyPass,
				l_S01_ID);	                     
	END;
    END IF; 
END LOOP;
CLOSE l_LOOP_S02;
END;
$$ LANGUAGE plpgsql ;

/*
EXEC P_Pend_RapelTHR '2003-05-20',
                     '2003-04-20',
                     'Y',
                     '2003-01-02',
                     '6000000',
                     '03',
                     1, 
                     'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
*/ 



