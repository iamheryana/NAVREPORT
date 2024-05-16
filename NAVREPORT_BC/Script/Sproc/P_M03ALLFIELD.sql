/****************************************
Name sprocs	: P_DP_PT
Create by	: Rudy
Date		: 19-12-2001
Description	: Proses Payroll
Call From	: Main Sprocs
Sub sprocs	: 
*****************************************/
CREATE OR REPLACE FUNCTION P_M03AllField (l_FLG	        VARCHAR(1),
				           l_KD         VARCHAR(4))

RETURNS TABLE (out_l_Singkatan  VARCHAR(10),
		out_l_UsComp	VARCHAR(1),
		out_l_Kolom	VARCHAR(2),
		out_l_NoAcc	VARCHAR(10),
		out_l_STS	VARCHAR(1),
		out_l_PRS	DECIMAL(5,2),
		out_l_NL	DECIMAL(15,2),
		out_l_FLBR	INT,
		out_l_kdCurrT   VARCHAR(4),
		out_l_M03ID	INT) 

AS $$ 
--
BEGIN 
--Nilai Awal
out_l_Singkatan := ' ';
out_l_UsComp    := ' ';
out_l_Kolom     := ' ';
out_l_NoAcc     := ' ';
out_l_STS       := ' ';
out_l_PRS       := 0;
out_l_NL        := 0;
out_l_FLBR      := 0;
out_l_kdCurrT   := ' ';
out_l_M03ID      := 0;

--DECLARE  l_PJK_PGW	VARCHAR(1)
--
RETURN QUERY SELECT SkDpPt,          UsComp,       Kolom,       NoAcc,       Status,     Persen,   Nilai,    NoLayar,    KdCurr, M03_ID 
FROM M03DPPT  
WHERE FlgDpPt=l_FLG AND KdDpPt=l_KD ;

END ; 
$$ LANGUAGE plpgsql ;

/*
select P_M03AllField ('D','BSAL');
*/