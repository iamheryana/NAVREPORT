DROP FUNCTION  fn_SECLOGIN (l_UserId INT);
--
CREATE FUNCTION  fn_SECLOGIN (l_UserId INT)
--
RETURNS TABLE ( Usr_id          INT, 
                Usr_loginname   VARCHAR(50), 
                Usr_expired     DATE, 
                Usr_Flg         VARCHAR(1), 
                Cab_id          INT, 
                Cab_Kode        VARCHAR(4), 
                Cab_Nama        VARCHAR(40), 
                Gol_id          INT, 
                Gol_Kode        VARCHAR(4), 
                Gol_Nama        VARCHAR(20))
AS $$
--
BEGIN 
    --
	RETURN QUERY 
    SELECT  Q1.usr_id, Q1.usr_loginname, Q1.expired_date, Q1.flag_activ, 
            Q1.m08_id, Q1.kdcaba, Q1.nmcaba, Q1.m12_id, Q1.kdgolg, Q1.nmgolg
    FROM
    ( --Q1
    SELECT  USR.usr_id, USR.usr_loginname, USR.expired_date, USR.flag_activ, 
            M08.m08_id, M08.kdcaba, M08.nmcaba, M12.m12_id, M12.kode AS kdgolg, M12.keterangan AS nmgolg
    FROM SEC_USER USR
    INNER JOIN sec_usercabang UCAB ON USR.usr_id = UCAB.usr_id
    INNER JOIN m08hcab M08 ON UCAB.m08_id=M08.m08_id
    INNER JOIN sec_usergolg UGOL ON USR.usr_id = UGOL.usr_id
    INNER JOIN m12hgol M12 ON UGOL.m12_id=M12.m12_id
    WHERE USR.usr_id=l_UserId AND USR.access_cabang='D' AND USR.access_golongan='D' 
    ----------   
    UNION ALL
    ----------   
    SELECT  USR.usr_id, USR.usr_loginname, USR.expired_date, USR.flag_activ, 
            M08.m08_id, M08.kdcaba, M08.nmcaba, M12.m12_id, M12.kode AS kdgolg, M12.keterangan AS nmgolg
    FROM SEC_USER USR
    INNER JOIN m08hcab M08 ON 1=1
    INNER JOIN sec_usergolg UGOL ON USR.usr_id = UGOL.usr_id
    INNER JOIN m12hgol M12 ON UGOL.m12_id=M12.m12_id
    WHERE USR.usr_id=l_UserId AND USR.access_cabang='A' AND USR.access_golongan='D' 
    ----------   
    UNION ALL
    ----------   
    SELECT  USR.usr_id, USR.usr_loginname, USR.expired_date, USR.flag_activ, 
            M08.m08_id, M08.kdcaba, M08.nmcaba, M12.m12_id, M12.kode AS kdgolg, M12.keterangan AS nmgolg
    FROM SEC_USER USR
    INNER JOIN sec_usercabang UCAB ON USR.usr_id = UCAB.usr_id
    INNER JOIN m08hcab M08 ON UCAB.m08_id=M08.m08_id
    INNER JOIN m12hgol M12 ON 1=1
    WHERE USR.usr_id=l_UserId AND USR.access_cabang='D' AND USR.access_golongan='A' 
    ----------   
    UNION ALL
    ----------   
    SELECT  USR.usr_id, USR.usr_loginname, USR.expired_date, USR.flag_activ, 
            M08.m08_id, M08.kdcaba, M08.nmcaba, M12.m12_id, M12.kode AS kdgolg, M12.keterangan AS nmgolg
    FROM SEC_USER USR
    INNER JOIN m08hcab M08 ON 1=1
    INNER JOIN m12hgol M12 ON 1=1
    WHERE USR.usr_id=l_UserId AND USR.access_cabang='A' AND USR.access_golongan='A' 
    ) Q1 ;
    --   

END;
$$ LANGUAGE plpgsql ;

/*
select * from fn_SECLOGIN(1)

select m15.nip, m15.nama, m15.kdcaba, m15.kdglng
from m15pega m15
inner join (select * from fn_SECLOGIN(1)) vsl on m15.kdglng=vsl.GOL_KODE and m15.kdcaba=vsl.CAB_KODE


select m15.nip, m15.nama, m15.kdcaba, m15.kdglng
from m15pega m15
inner join v_seclogin vsl on m15.kdglng=vsl.kdgolg and m15.kdcaba=vsl.kdcaba
where vsl.usr_id=1;  ---------where vsl.usr_loginname='admin';

*/
