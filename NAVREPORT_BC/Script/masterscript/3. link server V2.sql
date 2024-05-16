-- EXEC master.dbo.sp_addlinkedserver @server = N'POSTGRESQL', @srvproduct=N'Microsoft OLE DB Provider for ODBC Driver', @provider=N'MSDASQL', @datasrc='PostgreSQL', @location='localhost', @catalog='public'
-- 
-- 
-- EXEC master.dbo.sp_addlinkedsrvlogin @rmtsrvname=N'POSTGRESQL', @useself=N'False', @locallogin=NULL, @rmtuser='', @rmtpassword=''
-- 
-- SELECT * FROM OpenQuery(POSTGRESQL, 'select * from m03dppt')

INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM FZ1FLDA')  
	SELECT *, 0, userid, updtime, userid, updtime FROM FZ1FLDA ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM FZ2FLDA')  
	SELECT *, 0, userid, updtime, userid, updtime FROM FZ2FLDA ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM LOCKINFO')  
	SELECT *, 0, ' ', getdate(), ' ', getdate() FROM LOCKINFO ;
-- 
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M01AREA')  
	SELECT *, 0, userid, updtime, userid, updtime FROM M01AREA ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M02UUSA')  
	SELECT *, 0, userid, updtime, userid, updtime FROM M02UUSA ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M40CURR')  
	SELECT *, 0, userid, updtime, userid, updtime FROM M40CURR ;
--drop table #M40CURR ;
--
select IDENTITY(INT,1,1) as row_num, * into #M03DPPT from M03DPPT ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	'SELECT  *  FROM M03DPPT')  
	SELECT m03.* , 0, m03.userid, m03.updtime, m03.userid, m03.updtime
	FROM #M03DPPT m03 ;
 --drop table #M03DPPT ;

----------------------------------------
select IDENTITY(INT,1,1) as row_num, * into #M04HJAB from M04HJAB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M04HJAB')   
	SELECT * , null, cast('2999-12-31' as datetime) , 0, userid, updtime, userid, updtime FROM #M04HJAB ;
-- drop table #M04HJAB ;

select IDENTITY(INT,1,1) as row_num, * into #M05DJAB from M05DJAB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	'SELECT  * FROM M05DJAB')   
	SELECT m05.*, q1.row_num, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime
	FROM #M05DJAB m05 
	inner join #M04HJAB q1 on q1.kode = M05.kode 
	inner join #m03dppt m03 on m03.flgdppt = m05.flgdppt and m03.kddppt = m05.kddppt;
--drop table #M05DJAB ;
--
select IDENTITY(INT,1,1) as row_num, * into #M06HKJB from M06HKJB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M06HKJB')   
	SELECT *,cast('2999-12-31' as datetime) , 0, userid, updtime, userid, updtime FROM #M06HKJB ;
--drop table #M06HKJB ;
--
select IDENTITY(INT,1,1) as row_num, * into #M07DKJB from M07DKJB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M07DKJB')   
	SELECT m07.*, q1.row_num, m03.row_num , 0, q1.userid, q1.updtime, q1.userid, q1.updtime
	FROM #M07DKJB m07 
	inner join #M06HKJB q1 on q1.kode = M07.kode 
	inner join #m03dppt m03 on m03.flgdppt = m07.flgdppt and m03.kddppt = m07.kddppt;
--drop table #M07DKJB ;
--
select IDENTITY(INT,1,1) as row_num, * into #M08HCAB from M08HCAB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M08HCAB')   
	SELECT *, ' ', cast('2999-12-31' as datetime) , 0, userid, updtime, userid, updtime FROM #M08HCAB ;
--drop table #M08HCAB ;
--
select IDENTITY(INT,1,1) as row_num, * into #M09DCAB from M09DCAB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M09DCAB')   
	SELECT m09.*, q1.row_num, m03.row_num , 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M09DCAB m09
	inner join #M08HCAB q1 on q1.KDCABA = M09.KDCAB 
	inner join #m03dppt m03 on m03.flgdppt = m09.flgdppt and m03.kddppt = m09.kddppt;
drop table #M09DCAB ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M41JPJK')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M41JPJK ;
--drop table #M41JPJK ;

INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M10KLAS')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M10KLAS ; 
--drop table #M10KLAS ;
-------------------------------------------------------------------------

select IDENTITY(INT,1,1) as row_num, * into #M12HGOL from M12HGOL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M12HGOL')   
	SELECT *,cast('2999-12-31' as datetime) , 0, userid, updtime, userid, updtime FROM #M12HGOL ;
--drop table #M12HGOL ;

select IDENTITY(INT,1,1) as row_num, * into #M11GGOL from M11GGOL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M11GGOL')  
	 SELECT m11.*, q1.row_num, 0, M11.userid, M11.updtime, M11.userid, M11.updtime  
	FROM #M11GGOL m11 
	inner join #M12HGOL q1 on q1.kode = M11.kode  ;

drop table #M11GGOL ;
--
select IDENTITY(INT,1,1) as row_num, * into #M13DGOL from M13DGOL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M13DGOL')   
--SELECT *, 0, userid, updtime, userid, updtime FROM #M13DGOL ;
	 SELECT m13.*, q1.row_num, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime
	FROM #M13DGOL m13 
	inner join #M12HGOL q1 on q1.kode = M13.kode  
	inner join #m03dppt m03 on m03.flgdppt = m13.flgdppt and m03.kddppt = m13.kddppt;
drop table #M13DGOL ;

INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M14BANK')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M14BANK ;
--drop table #M14BANK ;

-----------------------------
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M16ALKL')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M16ALKL ;
--drop table #M16ALKL ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M17UKER')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M17UKER ;
--drop table #M17UKER ;
--
select IDENTITY(INT,1,1) as row_num, * into #M18JAMS from M18JAMS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
 	'SELECT  * FROM M18JAMS')   
	SELECT *, 0, userid, updtime, userid, updtime FROM #M18JAMS ;
drop table #M18JAMS ;
--
select IDENTITY(INT,1,1) as row_num, * into #M19HSLG from M19HSLG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M19HSLG')   
	SELECT *, 0, userid, updtime, userid, updtime FROM #M19HSLG ;
--drop table #M19HSLG ;
--
select IDENTITY(INT,1,1) as row_num, * into #M20DSLG from M20DSLG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M20DSLG')   
	SELECT m20.*, q1.row_num, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M20DSLG m20
	inner join #m19hslg q1 on q1.tipelap = m20.tipelap and q1.flgdppt = m20.flgdppt and q1.nomformat = m20.nomformat 
	inner join #m03dppt m03 on m03.flgdppt = m20.flgdppt and m03.kddppt = m20.kddppt;
drop table #M20DSLG ;
--

INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M21UMRE')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M21UMRE ;
--op table #M21UMRE ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M22JNSP')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M22JNSP ;
--drop table #M22JNSP ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M24MUTR')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M24MUTR ;
--drop table #M24MUTR ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M25JNSD')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M25JNSD ;
--drop table #M25JNSD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M26JRSN')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M26JRSN ;
--drop table #M26JRSN ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M27LMBD')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M27LMBD ;
--drop table #M27LMBD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M28LKSD')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M28LKSD ;
--drop table #M28LKSD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M29JNSA')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M29JNSA ;
--drop table #M29JNSA ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M30HAHU')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M30HAHU ;
--drop table #M30HAHU ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M31FASL')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M31FASL ;
--drop table #M31FASL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M32JMED')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M32JMED ;
--drop table #M32JMED ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M33JABT')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M33JABT ;
--drop table #M33JABT ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M34HOBI')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M34HOBI ;
--drop table #M34HOBI ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M36JBHS')   
	sELECT *, 0, userid, updtime, userid, updtime FROM M36JBHS ;
--drop table #M36JBHS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M15PEGA')   
	SELECT q1.*, m04.row_num, m06.row_num,m08.row_num, m12.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime
	FROM M15PEGA q1

	inner join #M04HJAB m04 on m04.kode = q1.kdJABa
	inner join #M06HKJB m06 on m06.kode = q1.kdkJAB 
	inner join #M08HCAB m08 on m08.kdcaba = q1.kdcaba 
	inner join #M12HGOL m12 on m12.kode = q1.kdglng  ;
--drop table #M15PEGA ;
select IDENTITY(INT,1,1) as row_num, * into #M23KLRG from M23KLRG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M23KLRG')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M23KLRG q1 ;
--drop table #M23KLRG ;

--------------------------
select IDENTITY(INT,1,1) as row_num, * into #M35HOBI from M35HOBI ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M35HOBI')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M35HOBI q1 ;
--drop table #M35HOBI ;

select IDENTITY(INT,1,1) as row_num, * into #M37NBHS from M37NBHS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M37NBHS')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M37NBHS q1  ;
--drop table #M37NBHS ;

select IDENTITY(INT,1,1) as row_num, * into #M38PKRJ from M38PKRJ ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M38PKRJ')  
	SELECT q1.*,0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M38PKRJ q1 ;
--drop table #M38PKRJ ;

select IDENTITY(INT,1,1) as row_num, * into #M39TPDK from M39TPDK ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M39TPDK')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M39TPDK q1 ;
--drop table #M39TPDK ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M42TSPL')   
	SELECT *, 0, userid, updtime, userid, updtime 
	FROM M42TSPL ;
--drop table #M42TSPL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M43SUPL')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM M43SUPL q1 
--drop table #M43SUPL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M44JASU')   
	SELECT q1.*, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM M44JASU q1 
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt1 and m03.kddppt = q1.kddppt1;
--drop table #M44JASU ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	'SELECT  * FROM M45KKHS')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM M45KKHS q1 ;
--drop table #M45KKHS ;
select IDENTITY(INT,1,1) as row_num, * into #M46PPKH from M46PPKH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	 'SELECT  * FROM M46PPKH')   
	SELECT q1.*, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M46PPKH q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;
--drop table #M46PPKH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M47PRLR')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M47PRLR ;
--drop table #M47PRLR ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	 'SELECT  * FROM M48PCUT')   
	SELECT * , ' ', 0, userid, updtime, userid, updtime FROM M48PCUT ;
--drop table #M48PCUT ;
select IDENTITY(INT,1,1) as row_num, * into #M49KURS from M49KURS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	 'SELECT  * FROM M49KURS')  
	 SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M49KURS q1 ;
--drop table #M49KURS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M51JFRM')   
	SELECT *, 0, userid, updtime, userid, updtime FROM M51JFRM ;
--drop table #M51JFRM ;
select IDENTITY(INT,1,1) as row_num, * into #M52HFRM from M52HFRM ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M52HFRM')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #M52HFRM q1 ;
--drop table #M52HFRM ;
select IDENTITY(INT,1,1) as row_num, * into #M53DFRM from M53DFRM ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	 'SELECT  * FROM M53DFRM')  
	 SELECT q1.*, m52.row_num, m03.row_num, 0, m52.userid, m52.updtime, m52.userid, m52.updtime 
	FROM #M53DFRM q1 
	inner join #M52HFRM m52 on q1.kdjfrm = m52.kdjfrm and q1.nomformat = m52.nomformat and q1.flgdppt = m52.flgdppt
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;
--drop table #M53DFRM ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M54PARA')   
	SELECT RID, Nama, UserId, CONVERT(VARCHAR(10),upddate,111), UpdTime, Ws, 
		0, userid, updtime, userid, updtime
	FROM M54PARA;
--drop table #M54PARA ;
select IDENTITY(INT,1,1) as row_num, * into #M55PGRP from M55PGRP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M55PGRP')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M55PGRP q1 ;
--drop table #M55PGRP ;
select IDENTITY(INT,1,1) as row_num, * into #M56NGRP from M56NGRP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	 'SELECT  * FROM M56NGRP')   
	SELECT q1.*, m55.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M56NGRP q1 
  	inner join #M55PGRP m55 on m55.rid = q1.rid and m55.kode = q1.KdPGrp ;
--drop table #M56NGRP ;
select IDENTITY(INT,1,1) as row_num, * into #M57PREH from M57PREH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M57PREH')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M57PREH q1 ;
--drop table #M57PREH ;
select IDENTITY(INT,1,1) as row_num, * into #M58PRED from M58PRED ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M58PRED')   
	SELECT q1.*, m57.row_num, m06.row_num, 0, m57.userid, m57.updtime, m57.userid, m57.updtime 
	FROM #M58PRED q1 
	inner join #M57PREH m57 on m57.kdjasu = q1.kdjasu and m57.lvlbenefit = q1.lvlbenefit
	inner join #M06HKJB m06 on q1.kdkjab = M06.kode ;
--drop table #M58PRED ;
select IDENTITY(INT,1,1) as row_num, * into #M59PRE2 from M59PRE2 ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M59PRE2')  
	 SELECT q1.*, m57.row_num, 0, m57.userid, m57.updtime, m57.userid, m57.updtime  
	FROM #M59PRE2 q1
 	inner join #M57PREH m57 on m57.kdjasu = q1.kdjasu and m57.lvlbenefit = q1.lvlbenefit ;
--drop table #M59PRE2 ;
select IDENTITY(INT,1,1) as row_num, * into #M60TSHT from M60TSHT ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M60TSHT')   
	SELECT q1.*, m55.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #M60TSHT q1 
   	inner join #M55PGRP m55 on m55.rid = q1.rid and m55.kode = q1.KdPGrp;
--drop table #M60TSHT ;
select IDENTITY(INT,1,1) as row_num, * into #M61DBCB from M61DBCB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M61DBCB')   
	SELECT q1.*, m08.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #M61DBCB q1
	inner join #M08HCAB m08 on q1.KDCABA = M08.KDCABa ;
--drop table #M61DBCB ;
select IDENTITY(INT,1,1) as row_num, * into #M62EFST from M62EFST ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M62EFST')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime   
	FROM #M62EFST q1  ;
--drop table #M62EFST ;
select IDENTITY(INT,1,1) as row_num, * into #M63HPGL from M63HPGL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M63HPGL')   
	SELECT row_num, AccFr1, AccFr2, AccFr3, AccFr4, AccTo1, AccTo2, AccTo3, AccTo4, AccTgt, 
		TpCCenterTgt, CdCCenterTgt, UserId, CONVERT(VARCHAR(10),UpdateTime,111) 
		, 0, userid, UpdateTime, userid, UpdateTime 
	FROM #M63HPGL ;
--drop table #M63HPGL ;
select IDENTITY(INT,1,1) as row_num, * into #M64ADVH from M64ADVH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M64ADVH')   
	SELECT row_num, Level1, Kode1, Level2, Kode2, UserId, CONVERT(VARCHAR(10),UpdateTime,111),
		0, userid, UpdateTime, userid, UpdateTime  
	FROM #M64ADVH ;
-- drop table #M64ADVH ;
select IDENTITY(INT,1,1) as row_num, * into #M65ADVD from M65ADVD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM M65ADVD')   
	SELECT q1.*, m64.row_num, m03.row_num, 0, m64.userid, m64.UpdateTime, m64.userid, m64.UpdateTime 
	FROM #M65ADVD q1
	inner join #M64ADVH m64 on m64.level1 = q1.level1 and m64.kode1 = q1.kode1 and m64.level2 = q1.level2 and m64.kode2 = q1.kode2
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;
drop table #M65ADVD ;
--
select IDENTITY(INT,1,1) as row_num, * into #S01HGAJ from S01HGAJ ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S01HGAJ')   
	SELECT *, 0, userid, updtime, userid, updtime FROM #S01HGAJ where year(tglpayr)>2012;
--drop table #S01HGAJ ;
--select IDENTITY(INT,1,1) as row_num, * into #S01HGAJ from S01HGAJ ;
select IDENTITY(INT,1,1) as row_num, * into #S02DGAJ from S02DGAJ ;
SELECT q1.*, (select s01.row_num from #S01HGAJ s01 where q1.TglPayr = s01.TglPayr and q1.NIP = s01.NIP) as s01_id  
into #s02dgaj2009
FROM #S02DGAJ q1
where year(q1.tglpayr)>2012;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S02DGAJ')   
	SELECT *, 0 as version, ' ' , getdate(), ' ' , getdate()  
	FROM #S02DGAJ2009;
drop table #S02DGAJ2009;
--
select IDENTITY(INT,1,1) as row_num, * into #S03LTAX from S03LTAX ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S03LTAX')   
	SELECT *, 0, userid, updtime, userid, updtime FROM #S03LTAX ;
drop table #S03LTAX ;
--
select IDENTITY(INT,1,1) as row_num, * into #S04LEMB from S04LEMB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	 'SELECT  * FROM S04LEMB')   
	SELECT *, 0, postid, updtime, postid, updtime FROM #S04LEMB ;
drop table #S04LEMB ;
--
select IDENTITY(INT,1,1) as row_num, * into #S05PSTD from S05PSTD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S05PSTD')   
	SELECT q1.*, 0, null, null, null, null  
	FROM #S05PSTD q1  ;
drop table #S05PSTD ;
--
select IDENTITY(INT,1,1) as row_num, * into #S06ABSH from S06ABSH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S06ABSH')   
	SELECT *, 0, postid, updtime, postid, updtime FROM #S06ABSH ;
drop table #S06ABSH ;
--
select IDENTITY(INT,1,1) as row_num, * into #S07TRNG from S07TRNG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S07TRNG')   
	SELECT *, 0, postid, updtime, postid, updtime FROM #S07TRNG ;
drop table #S07TRNG ;
--
select IDENTITY(INT,1,1) as row_num, * into #S08MUTA from S08MUTA ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S08MUTA')   
	SELECT *, 0, postid, updtime, postid, updtime FROM #S08MUTA ;
drop table #S08MUTA ;
--
select IDENTITY(INT,1,1) as row_num, * into #S09HCUT from S09HCUT ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S09HCUT')   
	SELECT *, 0, userid, updtime, userid, updtime FROM #S09HCUT ;
--drop table #S09HCUT ;
---
select IDENTITY(INT,1,1) as row_num, * into #S0ADCUT from S0ADCUT ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S0ADCUT')   
	SELECT q1.*, s09.row_num, 0, ' ', getdate(), ' ', getdate() 
	FROM #S0ADCUT q1 
	inner join #S09HCUT s09 on q1.nip = s09.nip and q1.tahun = s09.tahun;
drop table #S0ADCUT ;
--
select IDENTITY(INT,1,1) as row_num, * into #S0BLSTX from S0BLSTX ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S0BLSTX')   
	SELECT *, 0, userid, updtime, userid, updtime FROM #S0BLSTX ;
drop table #S0BLSTX ;
---
select IDENTITY(INT,1,1) as row_num, * into #S0CMEDH from S0CMEDH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S0CMEDH')   
	SELECT *, 0, userid, updtime, userid, updtime FROM #S0CMEDH ;
--drop table #S0CMEDH ;
select IDENTITY(INT,1,1) as row_num, * into #S0DMEDD from S0DMEDD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S0DMEDD')   
	SELECT q1.*, s0c.row_num, 0, s0c.userid, s0c.updtime, s0c.userid, s0c.updtime 
	FROM #S0DMEDD q1 
	inner join #S0CMEDH s0c on q1.nip = s0c.nip and q1.tahun = s0c.tahun and q1.jenismedical = s0c.jenismedical;
drop table #S0DMEDD ;
--
select IDENTITY(INT,1,1) as row_num, * into #S0ESFPC from S0ESFPC ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S0ESFPC')   
	SELECT *, 0, ' ', getdate(), ' ', getdate() FROM #S0ESFPC ;
drop table #S0ESFPC ;
--
select IDENTITY(INT,1,1) as row_num, * into #S0FPDAP from S0FPDAP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM S0FPDAP')   
	SELECT *, 0, ' ', getdate(), ' ', getdate() FROM #S0FPDAP ;
drop table #S0FPDAP ;
--
select IDENTITY(INT,1,1) as row_num, * into #S0GPREM from S0GPREM ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	'SELECT  * FROM S0GPREM')   
	SELECT q1.*, m03.row_num ,0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #S0GPREM q1 
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;

drop table #S0GPREM ;
--
select IDENTITY(INT,1,1) as row_num, * into #T01LEMB from T01LEMB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T01LEMB')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #T01LEMB q1 ;
drop table #T01LEMB ;
--
select IDENTITY(INT,1,1) as row_num, * into #T02ABSN from T02ABSN ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T02ABSN')   
	SELECT q1.*, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T02ABSN q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt ;
drop table #T02ABSN ;
--
select IDENTITY(INT,1,1) as row_num, * into #T03VARI from T03VARI ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T03VARI')   
	SELECT q1.*, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T03VARI q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt ;
drop table #T03VARI ;
--
select IDENTITY(INT,1,1) as row_num, * into #T04TTAP from T04TTAP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi',
	'SELECT  * FROM T04TTAP')   
	SELECT q1.*, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T04TTAP q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;
drop table #T04TTAP ;
--
select IDENTITY(INT,1,1) as row_num, * into #T05HPIU from T05HPIU ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT * FROM T05HPIU')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #T05HPIU q1  ;
--drop table #T05HPIU ;
--
select IDENTITY(INT,1,1) as row_num, * into #T06DPIU from T06DPIU ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T06DPIU')   
	SELECT q1.*, t05.row_num, 0, t05.userid, t05.updtime, t05.userid, t05.updtime 
	FROM #T06DPIU q1 
	inner join #T05HPIU t05 on q1.nip = t05.nip and q1.KdJnsP = t05.kdjnsp and q1.TgDoku = t05.tgdoku 
drop table #T06DPIU ;
--
select IDENTITY(INT,1,1) as row_num, * into #T07BAYP from T07BAYP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T07BAYP')   
	SELECT q1.*, t05.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T07BAYP q1
	inner join #T05HPIU t05 on q1.nip = t05.nip and q1.KdJnsP = t05.kdjnsp and q1.TgDoku = t05.tgdoku 
drop table #T07BAYP ;
--
select IDENTITY(INT,1,1) as row_num, * into #T08ABSN from T08ABSN ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T08ABSN')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T08ABSN q1 ;
drop table #T08ABSN ;
--
select IDENTITY(INT,1,1) as row_num, * into #T09HARG from T09HARG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T09HARG')   
	SELECT q1.*,  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T09HARG q1;
drop table #T09HARG ;
--
select IDENTITY(INT,1,1) as row_num, * into #T10MUTA from T10MUTA ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T10MUTA')   
	SELECT q1.*, m04.row_num, m06.row_num, m08.row_num, m12.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime
	FROM #T10MUTA q1
	inner join #M04HJAB m04 on m04.kode = q1.kdJABa
	inner join #M06HKJB m06 on m06.kode = q1.kdkJAB 
	inner join #M08HCAB m08 on m08.kdcaba = q1.kdcaba 
	inner join #M12HGOL m12 on m12.kode = q1.kdglng ;
drop table #T10MUTA ;
--
select IDENTITY(INT,1,1) as row_num, * into #T11HTRN from T11HTRN ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T11HTRN')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T11HTRN q1 ;
--drop table #T11HTRN ;
--
select IDENTITY(INT,1,1) as row_num, * into #T12DTRN from T12DTRN ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T12DTRN')   
	SELECT q1.*, t11.row_num,  0, t11.userid, t11.updtime, t11.userid, t11.updtime 
	FROM #T12DTRN q1 
	inner join #T11HTRN t11 on q1.TgDocu = t11.TgDocu and q1.KdJnsD = t11.KdJnsD and q1.KdJrsn = t11.KdJrsn ;
drop table #T12DTRN ;
--
select IDENTITY(INT,1,1) as row_num, * into #T13FASI from T13FASI ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T13FASI')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T13FASI q1 ;
drop table #T13FASI ;
--
select IDENTITY(INT,1,1) as row_num, * into #T14JTHM from T14JTHM ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T14JTHM')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime FROM #T14JTHM q1
--drop table #T14JTHM ;
--
select IDENTITY(INT,1,1) as row_num, * into #T15PMED from T15PMED ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T15PMED')   
	SELECT q1.*, t14.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T15PMED q1 
	inner join #T14JTHM t14 on q1.nip = t14.nip and q1.tahun = t14.tahun ;
drop table #T15PMED ;
--
select IDENTITY(INT,1,1) as row_num, * into #T16PKON from T16PKON ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T16PKON')   
	SELECT q1.*, m04.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T16PKON q1
	inner join #M04HJAB m04 on m04.kode = q1.jabatan ;
drop table #T16PKON ;
--
select IDENTITY(INT,1,1) as row_num, * into #T17PDAK from T17PDAK ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T17PDAK')   
	SELECT q1.*, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T17PDAK q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt ;
drop table #T17PDAK ;
--
select IDENTITY(INT,1,1) as row_num, * into #T18HKER from T18HKER ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T18HKER')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime FROM #T18HKER q1 ;
drop table #T18HKER ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T19PESA')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  FROM T19PESA q1 ;
--
select IDENTITY(INT,1,1) as row_num, * into #T20JTMJ from T20JTMJ ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T20JTMJ')   
	SELECT q1.*, m06.row_num,  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T20JTMJ q1 
	inner join #M06HKJB m06 on m06.kode = q1.KelJab;
drop table #T20JTMJ ;
--
select IDENTITY(INT,1,1) as row_num, * into #T21PMEP from T21PMEP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T21PMEP')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T21PMEP q1  ;
drop table #T21PMEP ;
--
select IDENTITY(INT,1,1) as row_num, * into #T22KPAS from T22KPAS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T22KPAS')   
	SELECT q1.*,  0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #T22KPAS q1;
drop table #T22KPAS ;
--
select IDENTITY(INT,1,1) as row_num, * into #T23MPEG from T23MPEG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T23MPEG')   
	SELECT q1.*, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #T23MPEG q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;
drop table #T23MPEG ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T24PDLL')   
	SELECT q1.*,0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM T24PDLL q1 ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T25OPBP')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM T25OPBP q1  ;
--
select IDENTITY(INT,1,1) as row_num, * into #T26PPHL from T26PPHL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T26PPHL')   
	SELECT q1.*, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T26PPHL q1;
drop table #T26PPHL ;

select IDENTITY(INT,1,1) as row_num, * into #T27PMEP from T27PMEP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM T27PMEP')   
	SELECT q1.*, 0, ' ', getdate(), ' ', getdate()
	FROM #T27PMEP Q1;
drop table #T27PMEP ;

--select IDENTITY(INT,1,1) as row_num, * into #VerHist from VerHist ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM VerHist')   
	SELECT * FROM VerHist ;
--drop table #VerHist ;
--select IDENTITY(INT,1,1) as row_num, * into #W01JRGR from W01JRGR ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=pghapis;pwd=solusi', 
	'SELECT  * FROM W01JRGR')   
	SELECT * FROM W01JRGR ;
--drop table #W01JRGR ;
