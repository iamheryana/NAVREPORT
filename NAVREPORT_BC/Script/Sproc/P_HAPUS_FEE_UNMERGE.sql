/****************************************
Name sprocs	: P_Hapus_Fee_Unmerge
Create by	: Hermansyah
Date		: 05/09/2002
Description	: Proses Unmerge 
Call From	: Main sprocs
Sub sprocs	: P_Hapus_Fee_Unmerge
*****************************************/
DROP FUNCTION P_Hapus_Fee_Unmerge(l_TglFr	DATE,
				    l_TglTo	DATE);
				    
CREATE FUNCTION P_Hapus_Fee_Unmerge(l_TglFr	DATE,
				    l_TglTo	DATE)
RETURNS VOID 
AS $$
-- Ini Khusus untuk Core enggal Perlu Ngapa-ngapain ....by Suhe
BEGIN
END;
$$ LANGUAGE plpgsql ;

