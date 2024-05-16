CREATE VIEW V_SECLOGIN 
AS 
--
SELECT  USR.usr_id, USR.usr_loginname, USR.expired_date, USR.flag_activ, 
        M08.m08_id, M08.kdcaba, M08.nmcaba, M12.m12_id, M12.kode AS kdgolg, M12.keterangan AS nmgolg
FROM SEC_USER USR
INNER JOIN sec_usercabang UCAB ON USR.usr_id = UCAB.usr_id
INNER JOIN m08hcab M08 ON UCAB.m08_id=M08.m08_id
INNER JOIN sec_usergolg UGOL ON USR.usr_id = UGOL.usr_id
INNER JOIN m12hgol M12 ON UGOL.m12_id=M12.m12_id;
go

