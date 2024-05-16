/****************************************
Name sprocs	: fn_PlfMedical
Create by	: Chak Suhe
Date		: 08-06-2005
Description	: Mencari data Plafon Medical judul laporan untuk pre-print Mutasi Karir
Call From	: Main sprocs
Sub sprocs	: 
*****************************************/
CREATE OR REPLACE function fn_PlfMedical(l_Nip 		 varchar(10),
							   l_TglEff		 DATE,
							   l_TpMedi		 INT,
							   l_JenisMedical varchar(4),
							   l_KelJab		 varchar(4),
							   l_Keluarga	 INT)
RETURNS DECIMAL(15,2)
AS $$
DECLARE 
	l_Nilai		DECIMAL(15,2);
BEGIN
	IF l_TpMedi=1 THEN
       BEGIN
         l_Nilai :=0 ;
	   END;
    ELSE
		BEGIN
			IF l_Keluarga=1 THEN
				BEGIN
					SELECT PlafonKel INTO l_Nilai FROM T20JTMJ WHERE EXTRACT (YEAR FROM l_TglEff) AND JenisMedical=l_JenisMedical AND KelJab=l_KelJab;
				END; 
			ELSE 	
				BEGIN
					SELECT Plafon INTO l_Nilai FROM T20JTMJ WHERE Tahun=EXTRACT (YEAR FROM l_TglEff) AND JenisMedical=l_JenisMedical AND KelJab=l_KelJab;
				END;
			END IF;
		END;
	 END IF;
   return coalesce(l_Nilai, 0);
END;
$$
language plpgsql;


/*
SELECT dbo.fn_PlfMedical('001','2005-06-01',2,'RINP','SOP',0)
Med1KarCur=dbo.fn_PlfMedical(T10.NIP,l_TglEff,l_TpMedical,l_M51KdMedi1,PCUR.CURR_KELJAB,0),
*/

SELECT fn_PlfMedical('001','2005-06-01',2,'RINP','SOP',0)