/**************************************************************
Name sprocs	: L_PINDAHCABANG
Create by	: Deni
Date		: 12-02-2014
Description	: Listing Pindah Cabang
    l_Cetak : T= Pajak (Tax)
              A= Asuransi (Jamsostek)
              P= Payment
***************************************************************/
DROP FUNCTION L_PINDAHCABANG(
                       l_CabFr      VARCHAR(4),
                       l_CabTo      VARCHAR(4));
--
CREATE FUNCTION L_PINDAHCABANG(
                       l_CabFr      VARCHAR(4),
                       l_CabTo      VARCHAR(4))
--
RETURNS TABLE (
	kdcaba          	varchar(4) ,
	nmcaba          	varchar(40) ,
	tax_nama          	varchar(30) ,
	tax_jabatan        	varchar(25) ,
	tax_npwp           	varchar(20) ,
	tax_dtpnpwp      	varchar(1) ,
	tax_dtptdknpwp   	varchar(1) ,
	asu_nama           	varchar(25) ,
	asu_jabatan         varchar(25) ,
	asu_npp            	varchar(8) ,
	pay_norek         	varchar(20) ,
	pay_pemiliacc   	varchar(30) ,
	pay_alamatacc    	varchar(40) ,
	pay_kotaacc        	varchar(25) ,
	pay_noaccount      	varchar(32) ,
	pay_ttd            	varchar(30) ,
	pay_bank        	varchar(40) ,
	pay_cabbank      	varchar(20) ,
	pay_kotabank       	varchar(25) )
--
--
AS $$
--
BEGIN
    --
    RETURN QUERY
	SELECT  m08.kdcaba, m08.nmcaba, m08.kacaba, m08.jabatan, m08.npwp2, 
            coalesce(m08.flgdtpnpwp,'T') :: varchar(1) as flgnpwp , 
            coalesce(m08.flgdtptdknpwp,'T') :: varchar(1) as flgtdknpwp,
            m08.namajamsostek, m08.jabatanjamsostek, m08.npp, m08.norekening, 
            m08.namaaccholder, m08.alamataccholder, m08.kotaaccholder, m08.noaccount, 
            m08.ttd, m08.namabank, m08.cabangbank, m08.kotabank
	FROM m08hcab m08
	WHERE (m08.kdcaba BETWEEN l_CabFr AND l_CabTo); 
    --
END;
--
$$ LANGUAGE plpgsql ;
--
/*
select * from L_PINDAHCABANG(' ','ZZZ')
*/