CREATE VIEW V_HGOC AS SELECT M12.Kode,M12.Keterangan,M12.Singkatan,M12.MinGol,M12.MidGol,M12.MaxGol,
M12.recs,M12.tglsuspend,M12.version,M12.created_by,M12.created_on,M12.updated_by,M12.updated_on
FROM M12HGOL M12;
