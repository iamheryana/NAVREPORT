CREATE VIEW V_HKJC AS SELECT M06.Kode,M06.Keterangan,
M06.Singkatan,M06.Recs,
M06.tglsuspend,M06.version, M06.created_by,
M06.created_on,M06.updated_by,M06.updated_on
FROM M06HKJB  M06;