/********************************************
Name sprocs	: V_HFRM
Description	: Listing Format Preprinted 
Call From	: Main Proc
*****************************************/
CREATE VIEW V_HFRM
AS

SELECT
    M52.KdJfrm, M51.KETERANGAN AS KTJFRM, 
	RIGHT('0000'||CAST (M52.NomFormat AS VARCHAR),4) AS  NomFormat1,
	M52.Keterangan,M52.FlgDpPt,
	M52.CTKBOLD, M52.CTKITALIC, M52.CTKULINE, M52.RECS,
    M52.Created_By, M52.Created_On, M52.Updated_By, M52.Updated_On, M52.Version, 
    M53.KdDpPt, M53.FlgAngs,
    M03.NmDpPt,M52.NomFormat
FROM
M52HFRM M52
INNER JOIN M51JFRM M51 ON M52.KDJFRM = M51.KDJFRM
LEFT JOIN M53DFRM M53 ON M53.KdJfrm=M52.KdJfrm AND M53.NomFormat=M52.NomFormat
LEFT JOIN  M03DPPT M03 ON M03.FlgDpPt=M53.FlgDpPt AND M03.KdDpPt=M53.KdDpPt;
