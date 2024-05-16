/****************************************
Name sprocs	: P_Pend_THR
Create by	: wati
Date		: 17-06-2003
Description	: Proses Pendapatan THR
Call From	: Main sprocs
*****************************************/
DROP FUNCTION P_Pend_THR(l_TglProses    DATE,                       
		       l_TglMasuk     DATE,
		       l_TglTHRL      DATE,
		       l_TglTHRN      DATE,
		       l_TglHakL      DATE,
		       l_TglHakN      DATE,
		       l_NIP          VARCHAR(10),
		       l_J_THR        VARCHAR(1),
		       l_P_THR        VARCHAR(1),				       
		       l_Agama        VARCHAR(1),
		       l_FZ2THRHarian VARCHAR(1),
		       l_BlnTHR       DECIMAL(5,2),
		       l_FixIncome    DECIMAL(15,2),	
		       l_MyPass       VARCHAR(128),
		       l_S01_ID	      INT);
--
CREATE FUNCTION P_Pend_THR(l_TglProses    DATE,                       
		       l_TglMasuk     DATE,
		       l_TglTHRL      DATE,
		       l_TglTHRN      DATE,
		       l_TglHakL      DATE,
		       l_TglHakN      DATE,
		       l_NIP          VARCHAR(10),
		       l_J_THR        VARCHAR(1),
		       l_P_THR        VARCHAR(1),				       
		       l_Agama        VARCHAR(1),
		       l_FZ2THRHarian VARCHAR(1),
		       l_BlnTHR       DECIMAL(5,2),
		       l_FixIncome    DECIMAL(15,2),	
		       l_MyPass       VARCHAR(128),
		       l_S01_ID	      INT)
RETURNS VOID 
AS $$
BEGIN 
-- Check 
IF (l_P_THR='I' AND l_Agama='1' ) OR (l_P_THR='N' AND l_Agama<>'1') OR l_P_THR='B' THEN 
   BEGIN
     IF l_J_THR='L' THEN 
        BEGIN
          IF l_TglMasuk<=l_TglHakL THEN 
             BEGIN
               PERFORM P_Nilai_THR (l_TglProses,
				l_TglTHRL,
				l_TglMasuk,
				l_NIP,
				l_FZ2THRHarian,
				l_FixIncome,  
				l_BlnTHR,
				'THR',
				l_MyPass,
				l_S01_ID);
             END;
	  END IF;
        END;
     ELSE
        BEGIN
          IF l_TglMasuk<=l_TglHakN THEN 
             BEGIN
               PERFORM P_Nilai_THR (l_TglProses,
                                l_TglTHRN,
                                l_TglMasuk,
                                l_NIP,
                                l_FZ2THRHarian,
                                l_FixIncome,  
                                l_BlnTHR,
                                'THR',
				l_MyPass,
				l_S01_ID);
             END;
	  END IF; 
        END;
   END IF;
   END;
END IF ;
END ;
$$ LANGUAGE plpgsql ;
/*


EXEC P_Pend_THR l_TglProses='2003-03-27',                       
                l_TglMasuk='2003-01-01',
                l_TglTHRL='2003-03-30',
	        l_TglTHRN=NULL,
	        l_TglHakL='2002-01-01',
	        l_TglHakN=NULL,
                l_NIP='SUHE',
                l_J_THR='L',
                l_P_THR='I',
                l_Agama='1',
                l_FZ2THRHarian='T',
                l_BlnTHR=1,
		l_FixIncome=1500000,	
                l_MyPass='Copyright, 1988 (c) Microsoft Corporation, All rights reserved'

Jns_THR=L/N
P_THR=I/N/B
Agama=1
*/
