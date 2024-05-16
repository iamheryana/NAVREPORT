CREATE OR REPLACE VIEW V_HJAC AS SELECT M04.Kode,
M04.Keterangan,M04.Singkatan,M04.Recs,
M04.tglsuspend,M04.version, M04.created_by,
M04.created_on,M04.updated_by,M04.updated_on
FROM M04HJAB M04;