CREATE VIEW V_HSGL AS

SELECT
M19.TipeLap, M19.FlgDpPt,RIGHT('000'||M19.NomFormat :: varchar(3),3) AS NomFormat1,
M19.Keterangan, M19.Created_By, M19.Updated_By, M19.Updated_On,
M20.KdDpPt, M20.FlgAngs,M03.NmDpPt,M19.NomFormat
FROM
M19HSLG M19 LEFT JOIN M20DSLG M20 ON M20.TipeLap=M19.TipeLap AND M20.FlgDpPt=M19.FlgDpPt AND M20.NomFormat=M19.NomFormat
LEFT JOIN  M03DPPT M03 ON M03.FlgDpPt=M20.FlgDpPt AND M03.KdDpPt=M20.KdDpPt