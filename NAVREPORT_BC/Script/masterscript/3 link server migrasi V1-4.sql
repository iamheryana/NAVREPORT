-- jalanin di sql server, add odbc dengan nama postgresql 
-- kalau nama database bukan hapisdev2 find and replace aja 

-- EXEC master.dbo.sp_addlinkedserver @server = N'POSTGRESQL', @srvproduct=N'Microsoft OLE DB Provider for ODBC Driver', @provider=N'MSDASQL', @datasrc='PostgreSQL', @location='localhost', @catalog='public'
-- 
-- 
-- EXEC master.dbo.sp_addlinkedsrvlogin @rmtsrvname=N'POSTGRESQL', @useself=N'False', @locallogin=NULL, @rmtuser='', @rmtpassword=''
-- 
-- SELECT * FROM OpenQuery(POSTGRESQL, 'select * from m03dppt')


INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM FZ1FLDA')  
	SELECT RTRIM(kode),RTRIM(namapr),RTRIM(adrspr),RTRIM(kotapr),RTRIM(npwppr), currpd, nextpd, implpd, 
       tgfound, servawd, flgpost, postdate, payroll, personalia, pajak, 
       intvpajak, spt, hitpjk, limpjk1, pjk1, limpjk2, pjk2, limpjk3, 
       pjk3, nloccsupp, proccsupp, ptkppay, ptkpdep, rndtax, RTRIM(npp), retage, 
       RTRIM(kddht), iurpgwi, iurpers, bunga, jkk, jht, jkm, jpk, hariastek, 
       flglemb, jmllemb, limwrk1, ratewrk1, ratewrk2, limlbr1, limlbr2, 
       ratelbr1, ratelbr2, ratelbr3, rtelbr1, rtelbr2, pembilang, penyebut, 
       pembilangh, penyebuth, persentasilembur, flgggol, tunjist, tunjank, 
       flgglng, flgkjab, flgjaba, flgcaba, flgpiut, tglthrl, tglhakl, 
       tglthrn, tglhakn, jumlahmangkir, rndpen, flgthr, iuranspsi, hari01, 
       hari02, hari03, hari04, hari05, hari06, hari07, hari08, hari09, 
       hari10, hari11, hari12, harikerja, savepayrol, savehrd, varround, 
       mtoleransicuti, userid, upddate, updtime, ws, maxbruto, penddp, 
       nltunjist, nltunjanak, limlist1, ratelist1, ratelist2, RTRIM(kdcutihaid),RTRIM(kdsakitsrtdr), 
		0, RTRIM(userid), updtime, RTRIM(userid), updtime FROM FZ1FLDA ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM FZ2FLDA')  
	SELECT RTRIM(kode),RTRIM(stringflag),RTRIM(namabank),RTRIM(noaccount),RTRIM(cabang),RTRIM(kotabank),RTRIM(norekening), 
       RTRIM(namaaccholder),RTRIM(alamataccholder),RTRIM(kota),RTRIM(ttd), flagrapel, flrapelthr, 
       perioderapel, tglmulaipolis, tglakhirpolis, RTRIM(serverattendance), 
       umuranakmax, umuristrimax, jmlharisethn, RTRIM(servergl),RTRIM(dbasegl), faktorxpajak, 0, userid, updtime, userid, updtime FROM FZ2FLDA ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM LOCKINFO')  
	SELECT ID,RTRIM(KETERANGAN), 0, ' ', getdate(), ' ', getdate() FROM LOCKINFO ;
-- 
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M01AREA')  
	SELECT RTRIM(kode),RTRIM(keterangan),RTRIM(singkatan), umr, 0, userid, updtime, userid, updtime FROM M01AREA ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M02UUSA')  
	SELECT RTRIM(kode),RTRIM(keterangan),RTRIM(singkatan), RTRIM(npwp), RTRIM(kelompok), RTRIM(noaccount), 0, userid, updtime, userid, updtime FROM M02UUSA ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M40CURR')  
	SELECT RTRIM(kdcurr), RTRIM(ktcurr), RTRIM(skcurr), 0, userid, updtime, userid, updtime FROM M40CURR ;
--drop table #M40CURR ;
--
select IDENTITY(INT,1,1) as row_num, * into #M03DPPT from M03DPPT ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	'SELECT  *  FROM M03DPPT')  
	SELECT row_num, flgdppt, RTRIM(kddppt), RTRIM(nmdppt), RTRIM(skdppt), RTRIM(jndppt), persen, nilai, 
       RTRIM(kdcurr), status, uscomp, nolayar, RTRIM(kolom), pajak, RTRIM(noacc), flag, 0, m03.userid, m03.updtime, m03.userid, m03.updtime
	FROM #M03DPPT m03 ;
 --drop table #M03DPPT ;

----------------------------------------
select IDENTITY(INT,1,1) as row_num, * into #M04HJAB from M04HJAB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M04HJAB')   
	SELECT row_num, RTRIM(kode),RTRIM(keterangan),RTRIM(singkatan), RTRIM(kdkjab), recs, flgstruk, 
        null, cast('2999-12-31' as datetime) , 0, userid, updtime, userid, updtime FROM #M04HJAB ;
-- drop table #M04HJAB ;

select IDENTITY(INT,1,1) as row_num, * into #M05DJAB from M05DJAB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	'SELECT  * FROM M05DJAB')   
	SELECT m05.row_num, RTRIM(m05.kode),RTRIM(m05.flgdppt),RTRIM(m05.kddppt), m05.persen, m05.nilai, RTRIM(m05.kdcurr), m05.trans, 
			q1.row_num, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime
	FROM #M05DJAB m05 
	inner join #M04HJAB q1 on q1.kode = M05.kode 
	inner join #m03dppt m03 on m03.flgdppt = m05.flgdppt and m03.kddppt = m05.kddppt;
--drop table #M05DJAB ;
------------------------------------------------------------------------------------------------
select IDENTITY(INT,1,1) as row_num, * into #M06HKJB from M06HKJB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M06HKJB')   
	SELECT row_num, RTRIM(kode),RTRIM(keterangan),RTRIM(singkatan),  recs, cast('2999-12-31' as datetime) , 0, userid, updtime, userid, updtime FROM #M06HKJB ;
--drop table #M06HKJB ;
--
select IDENTITY(INT,1,1) as row_num, * into #M07DKJB from M07DKJB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M07DKJB')   
	SELECT m07.row_num, RTRIM(m07.kode),RTRIM(m07.flgdppt),RTRIM(m07.kddppt), m07.persen, m07.nilai, RTRIM(m07.kdcurr),RTRIM(m07.trans), 
			q1.row_num, m03.row_num , 0, q1.userid, q1.updtime, q1.userid, q1.updtime
	FROM #M07DKJB m07 
	inner join #M06HKJB q1 on q1.kode = M07.kode 
	inner join #m03dppt m03 on m03.flgdppt = m07.flgdppt and m03.kddppt = m07.kddppt;
--drop table #M07DKJB ;
--
select IDENTITY(INT,1,1) as row_num, * into #M08HCAB from M08HCAB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M08HCAB')   
	SELECT row_num, RTRIM(kdcaba),RTRIM(nmcaba),RTRIM(skcaba),RTRIM(kacaba),RTRIM(alcaba),RTRIM(kota),RTRIM(telpon), 
       RTRIM(fax),RTRIM(npwp),RTRIM(kdarea),RTRIM(noacc), recs, RTRIM(jabatan),RTRIM(npwp2),RTRIM(namajamsostek), 
       RTRIM(jabatanjamsostek),RTRIM(npp),RTRIM(norekening),RTRIM(namabank),RTRIM(cabangbank),RTRIM(kotabank), 
       RTRIM(namaaccholder),RTRIM(alamataccholder),RTRIM(kotaaccholder),RTRIM(ttd),RTRIM(noaccount), 
       flgdtpnpwp, flgdtptdknpwp, ' ', cast('2999-12-31' as datetime) , 0, userid, updtime, userid, updtime FROM #M08HCAB ;
--drop table #M08HCAB ;
--
select IDENTITY(INT,1,1) as row_num, * into #M09DCAB from M09DCAB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M09DCAB')   
	SELECT M09.row_num, RTRIM(M09.kdcab),RTRIM(M09.flgdppt),RTRIM(M09.kddppt), M09.persen, M09.nilai, RTRIM(M09.kdcurr), RTRIM(M09.trans),  
			q1.row_num, m03.row_num , 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M09DCAB m09
	inner join #M08HCAB q1 on q1.KDCABA = M09.KDCAB 
	inner join #m03dppt m03 on m03.flgdppt = m09.flgdppt and m03.kddppt = m09.kddppt;
drop table #M09DCAB ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M41JPJK')   
	SELECT kode,RTRIM(keterangan),RTRIM(singkatan),biayajabatan, ptkp, persenpj1, persenpj2, 
       persenpj3, persenpj4, persenpj5, persenpj6, persenpj7, limitpj1, 
       limitpj2, limitpj3, limitpj4, limitpj5, limitpj6, limitpj7, RTRIM(jnsform1721), 
       flagkhusus, persenpkp, final,  0, userid, updtime, userid, updtime FROM M41JPJK ;
--drop table #M41JPJK ;

INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M10KLAS')   
	SELECT RTRIM(kode),RTRIM(keterangan),RTRIM(singkatan), jmlpgwblnll, harian, kontrak, jnspajak, 
		0, userid, updtime, userid, updtime FROM M10KLAS where kode <> ' ' 
	union all 
	SELECT kode, keterangan,singkatan, jmlpgwblnll, harian, kontrak, jnspajak, 
		0, userid, updtime, userid, updtime FROM M10KLAS where kode = ' ' ; 
--drop table #M10KLAS ;
------------------------------------------------------------------------------------------------

select IDENTITY(INT,1,1) as row_num, * into #M12HGOL from M12HGOL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M12HGOL')   
	SELECT row_num, RTRIM(kode),RTRIM(keterangan),RTRIM(singkatan), mingol, midgol, maxgol, 
       recs, cast('2999-12-31' as datetime) , 0, userid, updtime, userid, updtime FROM #M12HGOL ;
--drop table #M12HGOL ;

select IDENTITY(INT,1,1) as row_num, * into #M11GGOL from M11GGOL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M11GGOL')  
	 SELECT M11.row_num, RTRIM(M11.kode), M11.frtempo, M11.gajipokok, RTRIM(M11.kdcurr), q1.row_num, 0, M11.userid, M11.updtime, M11.userid, M11.updtime  
	FROM #M11GGOL m11 
	inner join #M12HGOL q1 on q1.kode = M11.kode  ;

drop table #M11GGOL ;
--
select IDENTITY(INT,1,1) as row_num, * into #M13DGOL from M13DGOL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M13DGOL')   
--SELECT *, 0, userid, updtime, userid, updtime FROM #M13DGOL ;
	SELECT M13.row_num, RTRIM(M13.kode),RTRIM(M13.flgdppt),RTRIM(M13.kddppt), M13.persen, M13.nilai, RTRIM(M13.kdcurr), M13.trans, 
		q1.row_num, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime
	FROM #M13DGOL m13 
	inner join #M12HGOL q1 on q1.kode = M13.kode  
	inner join #m03dppt m03 on m03.flgdppt = m13.flgdppt and m03.kddppt = m13.kddppt;
drop table #M13DGOL ;

INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M14BANK')   
	SELECT RTRIM(kode),RTRIM(keterangan), RTRIM(cabang), RTRIM(singkatan), RTRIM(noaccount), biayabank, 0, userid, updtime, userid, updtime 
	FROM M14BANK where kode <> ' '
	union all 
	SELECT kode,RTRIM(keterangan), RTRIM(cabang), RTRIM(singkatan), RTRIM(noaccount), biayabank, 0, userid, updtime, userid, updtime 
	FROM M14BANK where kode = ' ';
--drop table #M14BANK ;

--------------------------------------------------------------------------------------------------------------------
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M16ALKL')   
	SELECT RTRIM(kode),RTRIM(keterangan),RTRIM(singkatan), flagmati, 0, userid, updtime, userid, updtime FROM M16ALKL ;
--drop table #M16ALKL ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M17UKER')   
	SELECT RTRIM(kduker), RTRIM(keterangan),RTRIM(singkatan), RTRIM(noaccount), 0, userid, updtime, userid, updtime FROM M17UKER ;
--drop table #M17UKER ;
--
select IDENTITY(INT,1,1) as row_num, * into #M18JAMS from M18JAMS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
 	'SELECT  * FROM M18JAMS')   
	SELECT row_num, RTRIM(progrm), leveljkk, perush, pgwi, pgwik, upahmax, RTRIM(noacc), 0, userid, updtime, userid, updtime FROM #M18JAMS ;
drop table #M18JAMS ;
--
select IDENTITY(INT,1,1) as row_num, * into #M19HSLG from M19HSLG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M19HSLG')   
	SELECT row_num, RTRIM(tipelap),RTRIM(flgdppt), nomformat,RTRIM(keterangan), recs, 0, userid, updtime, userid, updtime FROM #M19HSLG ;
--drop table #M19HSLG ;
--
select IDENTITY(INT,1,1) as row_num, * into #M20DSLG from M20DSLG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M20DSLG')   
	SELECT m20.row_num, RTRIM(m20.tipelap),RTRIM(m20.flgdppt), m20.nomformat, RTRIM(m20.kddppt), m20.flgangs, 
			q1.row_num, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M20DSLG m20
	inner join #m19hslg q1 on q1.tipelap = m20.tipelap and q1.flgdppt = m20.flgdppt and q1.nomformat = m20.nomformat 
	inner join #m03dppt m03 on m03.flgdppt = m20.flgdppt and m03.kddppt = m20.kddppt;
drop table #M20DSLG ;
--

INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M21UMRE')   
	SELECT RTRIM(daerah), RTRIM(keterangan),RTRIM(singkatan), umr, tglmulai,  0, userid, updtime, userid, updtime FROM M21UMRE ;
--op table #M21UMRE ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M22JNSP')   
	SELECT RTRIM(kdjnsp),RTRIM(nmpiut),RTRIM(skpiut), 0, userid, updtime, userid, updtime FROM M22JNSP ;
--drop table #M22JNSP ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M24MUTR')   
	SELECT RTRIM(kdmutr),RTRIM(nama),RTRIM(singkatan), 0, userid, updtime, userid, updtime FROM M24MUTR ;
--drop table #M24MUTR ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M25JNSD')   
	SELECT RTRIM(kode),RTRIM(nama),RTRIM(singkatan), 0, userid, updtime, userid, updtime FROM M25JNSD ;
--drop table #M25JNSD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M26JRSN')   
	SELECT RTRIM(kode),RTRIM(nama),RTRIM(singkatan),  0, userid, updtime, userid, updtime FROM M26JRSN ;
--drop table #M26JRSN ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M27LMBD')   
	SELECT RTRIM(kode),RTRIM(nama),RTRIM(singkatan),RTRIM(kalembaga),RTRIM(alamat),RTRIM(kota),RTRIM(telfon),RTRIM(fax), 
		0, userid, updtime, userid, updtime FROM M27LMBD ;
--drop table #M27LMBD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M28LKSD')   
	SELECT RTRIM(kode),RTRIM(nama),RTRIM(singkatan),  0, userid, updtime, userid, updtime FROM M28LKSD ;
--drop table #M28LKSD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M29JNSA')   
	SELECT RTRIM(kode),RTRIM(nama),RTRIM(singkatan), jnscuti, 0, userid, updtime, userid, updtime FROM M29JNSA ;
--drop table #M29JNSA ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M30HAHU')   
	SELECT RTRIM(kdharg), RTRIM(nama),RTRIM(singkatan), 0, userid, updtime, userid, updtime FROM M30HAHU ;
--drop table #M30HAHU ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M31FASL')   
	SELECT RTRIM(kode),RTRIM(keterangan),RTRIM(singkatan), 0, userid, updtime, userid, updtime FROM M31FASL ;
--drop table #M31FASL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M32JMED')   
	SELECT RTRIM(kode),RTRIM(keterangan),RTRIM(singkatan),  0, userid, updtime, userid, updtime FROM M32JMED ;
--drop table #M32JMED ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M33JABT')   
	SELECT RTRIM(kode),RTRIM(keterangan), RTRIM(nama), RTRIM(jabatan),  0, userid, updtime, userid, updtime FROM M33JABT ;
--drop table #M33JABT ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M34HOBI')   
	SELECT RTRIM(kode),RTRIM(keterangan), 0, userid, updtime, userid, updtime FROM M34HOBI ;
--drop table #M34HOBI ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M36JBHS')   
	SELECT RTRIM(kode),RTRIM(keterangan), 0, userid, updtime, userid, updtime FROM M36JBHS ;
--drop table #M36JBHS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M15PEGA')   
	SELECT RTRIM(q1.nip),RTRIM(q1.nama),RTRIM(q1.namakecil),RTRIM(q1.alamat),RTRIM(q1.kelurahan),RTRIM(q1.kecamatan),RTRIM(q1.kota),RTRIM(q1.kodepos),
		RTRIM(q1.telpon),RTRIM(q1.agama),RTRIM(q1.kewarganegaraan),RTRIM(q1.sukubangsa),RTRIM(q1.noktp),RTRIM(q1.npwp),RTRIM(q1.keterangan),
       RTRIM(q1.kdklas), q1.ttlkontrak, q1.tglkontrakakhir, RTRIM(q1.tingkatan),RTRIM(q1.status), q1.anakttgg, 
       RTRIM(q1.jnsklmn),RTRIM(q1.stsmed),RTRIM(q1.stspjk), q1.tgllahir, RTRIM(q1.tempatlahir),RTRIM(q1.namakontak),RTRIM(q1.alamatkontak), 
       RTRIM(q1.kotakontak),RTRIM(q1.telponkontak), q1.tglmasuk, RTRIM(q1.flggnt), RTRIM(q1.payroll),RTRIM(q1.accholder), 
       RTRIM(q1.bankref),RTRIM(q1.rkbnk), RTRIM(q1.kpa), q1.tglkpa, RTRIM(q1.nodanapen), q1.tgldanapen, RTRIM(q1.nokop), q1.tglkop, 
       RTRIM(q1.nospsi), q1.tglspsi, RTRIM(q1.noydtp), q1.tglydtp, q1.tglmutasi, RTRIM(q1.kdarea),RTRIM(q1.kdcaba), 
       RTRIM(q1.kduusa),RTRIM(q1.kduker),RTRIM(q1.kdglng),RTRIM(q1.kdkjab),RTRIM(q1.kdjaba), q1.masakerja, RTRIM(q1.kdcurr), q1.gajiperc, 
       q1.prdawl, q1.gajitetap, q1.prdtetap, RTRIM(q1.dht),RTRIM(q1.tunjist),RTRIM(q1.tunjanak),RTRIM(q1.thr), q1.blnthr, 
       RTRIM(q1.jkk), q1.level, RTRIM(q1.jht),RTRIM(q1.jkm),RTRIM(q1.jpk),RTRIM(q1.pajak), q1.tglkeluar, RTRIM(q1.kdterr), q1.tglpayr, 
       q1.tglpers, q1.tglpajak, q1.berhaklemb, q1.encgajiperc, q1.encgajitetap, q1.foto, 
       q1.tgldaftarnpwp, q1.alamatnpwp, q1.tglmasukhrd, 
	   m04.row_num, m06.row_num,m08.row_num, m12.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime
	FROM M15PEGA q1

	inner join #M04HJAB m04 on m04.kode = q1.kdJABa
	inner join #M06HKJB m06 on m06.kode = q1.kdkJAB 
	inner join #M08HCAB m08 on m08.kdcaba = q1.kdcaba 
	inner join #M12HGOL m12 on m12.kode = q1.kdglng  ;
--drop table #M15PEGA ;
select IDENTITY(INT,1,1) as row_num, * into #M23KLRG from M23KLRG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M23KLRG')   
	SELECT row_num, RTRIM(nip),RTRIM(nama),RTRIM(jnsklmn),RTRIM(hubungan), anakke, tgllahir,RTRIM(tmplahir), 
       RTRIM(pendidikan),RTRIM(keterangan), tglefektif,0, q1.userid, q1.updtime, q1.userid, q1.updtime,ws
	FROM #M23KLRG q1 ;
--drop table #M23KLRG ;

------------------------------------------------------------------------------------------------------------
select IDENTITY(INT,1,1) as row_num, * into #M35HOBI from M35HOBI ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M35HOBI')   
	SELECT row_num, RTRIM(nip),RTRIM(kode),RTRIM(keterangan), 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M35HOBI q1 ;
--drop table #M35HOBI ;

select IDENTITY(INT,1,1) as row_num, * into #M37NBHS from M37NBHS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M37NBHS')   
	SELECT row_num, RTRIM(nip),RTRIM(kode), RTRIM(kemampuan),  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M37NBHS q1  ;
--drop table #M37NBHS ;

select IDENTITY(INT,1,1) as row_num, * into #M38PKRJ from M38PKRJ ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M38PKRJ')  
	SELECT row_num, RTRIM(nip),RTRIM(perusahaan), tglmasuk, tglkeluar, RTRIM(jenisusaha),RTRIM(jabatan), 
       gaji, RTRIM(alasan), 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M38PKRJ q1 ;
--drop table #M38PKRJ ;

select IDENTITY(INT,1,1) as row_num, * into #M39TPDK from M39TPDK ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M39TPDK')   
	SELECT row_num, RTRIM(nip), tanggal, RTRIM(tingkatan),RTRIM(bidang),RTRIM(keterangan), lamathn, 
       lamabln, ijasah, formal,  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M39TPDK q1 ;
--drop table #M39TPDK ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M42TSPL')   
	SELECT RTRIM(kode),RTRIM(keterangan),RTRIM(singkatan), 0, userid, updtime, userid, updtime 
	FROM M42TSPL ;
--drop table #M42TSPL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M43SUPL')   
	SELECT RTRIM(kode),RTRIM(keterangan),RTRIM(alamat),RTRIM(kota),RTRIM(kodepos),RTRIM(notelpon),RTRIM(tipesupplier),  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM M43SUPL q1 
--drop table #M43SUPL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M44JASU')   
	SELECT RTRIM(kode),RTRIM(keterangan),RTRIM(perusahaan),RTRIM(jenisasuransi), premipegawai, premiperusahaan, 
		RTRIM(flgdppt1),RTRIM(kddppt1),RTRIM(flgdppt2),RTRIM(kddppt2), m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime,q1.ws, m032.row_num 
	FROM M44JASU q1 
	inner  join #m03dppt m03 on m03.flgdppt = q1.flgdppt1 and m03.kddppt = q1.kddppt1
	inner  join #m03dppt m032 on m03.flgdppt = q1.flgdppt2 and m03.kddppt = q1.kddppt2;
--drop table #M44JASU ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	'SELECT  * FROM M45KKHS')   
	SELECT RTRIM(nip),RTRIM(keterangan), 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM M45KKHS q1 ;
--drop table #M45KKHS ;
select IDENTITY(INT,1,1) as row_num, * into #M46PPKH from M46PPKH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	 'SELECT  * FROM M46PPKH')   
	SELECT q1.row_num, RTRIM(q1.flgdppt),RTRIM(q1.kddppt),RTRIM(q1.keterangan), m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M46PPKH q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;
--drop table #M46PPKH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M47PRLR')   
	SELECT tglproses, tglawal, tglakhir, 0, userid, updtime, userid, updtime FROM M47PRLR ;
--drop table #M47PRLR ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	 'SELECT  * FROM M48PCUT')   
	SELECT RTRIM(kode), jatah, maxsisa, RTRIM(jenis), ' ',  0, userid, updtime, userid, updtime FROM M48PCUT ;
--drop table #M48PCUT ;
select IDENTITY(INT,1,1) as row_num, * into #M49KURS from M49KURS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	 'SELECT  * FROM M49KURS')  
	SELECT row_num, RTRIM(kdcurr), periode, kurs,  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M49KURS q1 ;
--drop table #M49KURS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M51JFRM')   
	SELECT RTRIM(kdjfrm),RTRIM(keterangan),RTRIM(teks),RTRIM(prnfasi),RTRIM(kdfasi1),RTRIM(kdfasi2),RTRIM(kdfasi3),
       RTRIM(kdfasi4),RTRIM(txfasi1),RTRIM(txfasi2),RTRIM(txfasi3),RTRIM(txfasi4),RTRIM(prnplaf),RTRIM(kdmedi1),
       RTRIM(kdmedi2),RTRIM(kdmedi3),RTRIM(kdmedi4),RTRIM(txmedi1),RTRIM(txmedi2),RTRIM(txmedi3),RTRIM(txmedi4),
       RTRIM(apprby1),RTRIM(apprby2),RTRIM(apprby3),RTRIM(apprby4),0, userid, updtime, userid, updtime FROM M51JFRM ;
--drop table #M51JFRM ;
select IDENTITY(INT,1,1) as row_num, * into #M52HFRM from M52HFRM ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M52HFRM')   
	SELECT row_num, RTRIM(kdjfrm), nomformat, RTRIM(keterangan),RTRIM(flgdppt),RTRIM(ctkbold),RTRIM(ctkitalic), 
       RTRIM(ctkuline), recs, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #M52HFRM q1 ;
--drop table #M52HFRM ;
select IDENTITY(INT,1,1) as row_num, * into #M53DFRM from M53DFRM ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	 'SELECT  * FROM M53DFRM')  
	SELECT q1.row_num, RTRIM(q1.kdjfrm), q1.nomformat, RTRIM(q1.flgdppt),RTRIM(q1.kddppt),RTRIM(q1.flgangs), 
			m52.row_num, m03.row_num, 0, m52.userid, m52.updtime, m52.userid, m52.updtime 
	FROM #M53DFRM q1 
	inner join #M52HFRM m52 on q1.kdjfrm = m52.kdjfrm and q1.nomformat = m52.nomformat and q1.flgdppt = m52.flgdppt
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;
--drop table #M53DFRM ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M54PARA')   
	SELECT RID, RTRIM(Nama), 
		0, userid, updtime, userid, updtime
	FROM M54PARA;
--drop table #M54PARA ;
select IDENTITY(INT,1,1) as row_num, * into #M55PGRP from M55PGRP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M55PGRP')   
	SELECT row_num, rid, RTRIM(kode),RTRIM(nama),RTRIM(singkatan),  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M55PGRP q1 ;
--drop table #M55PGRP ;
select IDENTITY(INT,1,1) as row_num, * into #M56NGRP from M56NGRP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	 'SELECT  * FROM M56NGRP')   
	SELECT q1.row_num, RTRIM(q1.rid),RTRIM(q1.nip),RTRIM(q1.kdpgrp), q1.tglberlaku,  m55.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M56NGRP q1 
  	inner join #M55PGRP m55 on m55.rid = q1.rid and m55.kode = q1.KdPGrp ;
--drop table #M56NGRP ;
select IDENTITY(INT,1,1) as row_num, * into #M57PREH from M57PREH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M57PREH')   
	SELECT row_num, RTRIM(kdjasu),RTRIM(lvlbenefit), recs, recs2,  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #M57PREH q1 ;
--drop table #M57PREH ;
select IDENTITY(INT,1,1) as row_num, * into #M58PRED from M58PRED ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M58PRED')   
	SELECT q1.row_num, RTRIM(q1.kdjasu),RTRIM(q1.lvlbenefit),RTRIM(q1.kdkjab),  m57.row_num, m06.row_num, 0, m57.userid, m57.updtime, m57.userid, m57.updtime 
	FROM #M58PRED q1 
	inner join #M57PREH m57 on m57.kdjasu = q1.kdjasu and m57.lvlbenefit = q1.lvlbenefit
	inner join #M06HKJB m06 on q1.kdkjab = M06.kode ;
--drop table #M58PRED ;
select IDENTITY(INT,1,1) as row_num, * into #M59PRE2 from M59PRE2 ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M59PRE2')  
	SELECT q1.row_num, RTRIM(q1.kdjasu),RTRIM(q1.lvlbenefit),RTRIM(q1.tahun), q1.premims, q1.premimk0, q1.premimk1, 
       q1.premimk2, q1.premimk3, q1.premifs, q1.premifk0, q1.premifk1, q1.premifk2, q1.premifk3, 
       q1.benefitms, q1.benefitmk0, q1.benefitmk1, q1.benefitmk2, q1.benefitmk3, q1.benefitfs, 
       q1.benefitfk0, q1.benefitfk1, q1.benefitfk2, q1.benefitfk3, m57.row_num, 0, m57.userid, m57.updtime, m57.userid, m57.updtime  
	FROM #M59PRE2 q1
 	inner join #M57PREH m57 on m57.kdjasu = q1.kdjasu and m57.lvlbenefit = q1.lvlbenefit ;
--drop table #M59PRE2 ;
select IDENTITY(INT,1,1) as row_num, * into #M60TSHT from M60TSHT ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M60TSHT')   
	SELECT q1.row_num, RTRIM(q1.rid),RTRIM(kdpgrp), tglpayroll, RTRIM(proyek), prsdist, RTRIM(flagm),  m55.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #M60TSHT q1 
   	inner join #M55PGRP m55 on m55.rid = q1.rid and m55.kode = q1.KdPGrp;
--drop table #M60TSHT ;
select IDENTITY(INT,1,1) as row_num, * into #M61DBCB from M61DBCB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M61DBCB')   
	SELECT q1.row_num, RTRIM(q1.kdcaba),RTRIM(q1.server),RTRIM(q1.dbase),RTRIM(q1.fpms), m08.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #M61DBCB q1
	inner join #M08HCAB m08 on q1.KDCABA = M08.KDCABa ;
--drop table #M61DBCB ;
select IDENTITY(INT,1,1) as row_num, * into #M62EFST from M62EFST ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M62EFST')   
	SELECT row_num, RTRIM(nip), tglefektif, RTRIM(status), RTRIM(stspjk), anakttgg,  0, q1.userid, q1.updtime, q1.userid, q1.updtime   
	FROM #M62EFST q1  ;
--drop table #M62EFST ;
select IDENTITY(INT,1,1) as row_num, * into #M63HPGL from M63HPGL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M63HPGL')   
	SELECT row_num,RTRIM(AccFr1),RTRIM(AccFr2),RTRIM(AccFr3),RTRIM(AccFr4),RTRIM(AccTo1),RTRIM(AccTo2),RTRIM(AccTo3),RTRIM(AccTo4),RTRIM(AccTgt),
		RTRIM(TpCCenterTgt),RTRIM(CdCCenterTgt), UserId, CONVERT(VARCHAR(10),UpdateTime,111) 
		, 0, userid, UpdateTime, userid, UpdateTime 
	FROM #M63HPGL ;
--drop table #M63HPGL ;
select IDENTITY(INT,1,1) as row_num, * into #M64ADVH from M64ADVH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M64ADVH')   
	SELECT row_num,RTRIM(Level1),RTRIM(Kode1),RTRIM(Level2),RTRIM(Kode2),
		0, userid, UpdateTime, userid, UpdateTime  
	FROM #M64ADVH ;
-- drop table #M64ADVH ;
select IDENTITY(INT,1,1) as row_num, * into #M65ADVD from M65ADVD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM M65ADVD')   
	SELECT q1.row_num, RTRIM(q1.level1),RTRIM(q1.kode1),RTRIM(q1.level2),RTRIM(q1.kode2),RTRIM(q1.flgdppt),RTRIM(q1.kddppt), q1.persen, 
       q1.nilai, RTRIM(q1.kdcurr),RTRIM(q1.trans), m64.row_num, m03.row_num, 0, m64.userid, m64.UpdateTime, m64.userid, m64.UpdateTime 
	FROM #M65ADVD q1
	inner join #M64ADVH m64 on m64.level1 = q1.level1 and m64.kode1 = q1.kode1 and m64.level2 = q1.level2 and m64.kode2 = q1.kode2
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;
drop table #M65ADVD ;

------------------------------------------------------------------------------------------------------------------------------------
select IDENTITY(INT,1,1) as row_num, * into #S01HGAJ from S01HGAJ ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S01HGAJ')   
	SELECT row_num, tglpayr, RTRIM(nip),RTRIM(nama),RTRIM(kdklas),RTRIM(kdarea),RTRIM(kdcaba),RTRIM(kduusa),RTRIM(kduker), 
       RTRIM(kdglng),RTRIM(kdkjab),RTRIM(kdjaba),RTRIM(stspjk), commdate, termdate, RTRIM(jnsklmn), 
       RTRIM(stssip), totanak, RTRIM(kpa),RTRIM(accholder), RTRIM(rkbnk), case when bankref <> ' ' then RTRIM(bankref) else bankref end, 
	RTRIM(noacc),RTRIM(nospt), 
       takehomepay, totinc, totded, grossincomenbytd, occsupport1, col12ytd, 
       egiynb, ptkp, eyti, eyit, ytdit, inctaxapaidnb, grossincomebytd, 
       occsupport2, taxtotal, inctaxapaidb, RTRIM(flagthr), hari, akmhari, 
       RTRIM(flagm), RTRIM(flagh), jnspajak, taxpesangonrp, recs, enctakehomepay, 
       enctotinc, enctotded, encgrossincomenbytd, encoccsupport1, enccol12ytd, 
       encegiynb, encptkp, enceyti, enceyit, encytdit, encinctaxapaidnb, 
       encgrossincomebytd, encoccsupport2, enctaxtotal, encinctaxapaidb, 
       enctaxpesangonrp,  0, userid, updtime, userid, updtime FROM #S01HGAJ --where year(tglpayr)>2012;
--drop table #S01HGAJ ;
--select IDENTITY(INT,1,1) as row_num, * into #S01HGAJ from S01HGAJ ;

select IDENTITY(INT,1,1) as row_num, * into #S02DGAJ from S02DGAJ ;
SELECT row_num, tglpayr, RTRIM(nip) as nip, RTRIM(flgdppt) as flgdppt, RTRIM(kddppt) as kddppt , RTRIM(flgangs) as flgangs, 
RTRIM(singkatan) as singkatan, nilai, 
       RTRIM(kdcurr) as kdcurr, nilaival, RTRIM(kolom) as kolom, RTRIM(noacc) as noacc, RTRIM(uscomp) as uscomp, nolyr, flgnongl, encnilai, 
       encnilaival,  (select s01.row_num from #S01HGAJ s01 where q1.TglPayr = s01.TglPayr and q1.NIP = s01.NIP) as s01_id  
into #s02dgaj2009
FROM #S02DGAJ q1
--where year(q1.tglpayr)>2012;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S02DGAJ')   
	SELECT *, 0 as version, ' ' , getdate(), ' ' , getdate()  
	FROM #S02DGAJ2009;
drop table #S02DGAJ2009;
--
select IDENTITY(INT,1,1) as row_num, * into #S03LTAX from S03LTAX ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S03LTAX')   
	SELECT row_num, tglpayr, RTRIM(nip),RTRIM(kdcaba), fixincomepytd, oldfixincomepytd, 
       fixincome, varincomepytd, oldvarincomepytd, varincome, col2ytd, 
       oldcol2ytd, col3ytd, oldcol3ytd, col4ytd, oldcol4ytd, col5ytd, 
       oldcol5ytd, col6ytd, oldcol6ytd, col12pytd, oldcol12pytd, kolom12, 
       occsupport1, oldoccsupport1, egiynb, ptkp, eyti, eyit, ytdit, 
       inctaxapaidnb, oldinctaxapaidnb, col8pytd, oldcol8pytd, kolom8, 
       occsupport2, oldoccsupport2, eyitt, inctaxapaidb, oldinctaxapaidb, 
       totpot, flagthr, userid, upddate, updtime, ws, harian, flgstruk, 
       flgpdll, kdump, nilump, taxump, taxapaidump, taxumpytd, hari, 
       maxbruto, penddp, encfixincomepytd, encoldfixincomepytd, encfixincome, 
       encvarincomepytd, encoldvarincomepytd, encvarincome, enccol2ytd, 
       encoldcol2ytd, enccol3ytd, encoldcol3ytd, enccol4ytd, encoldcol4ytd, 
       enccol5ytd, encoldcol5ytd, enccol6ytd, encoldcol6ytd, enccol12pytd, 
       encoldcol12pytd, enckolom12, encoccsupport1, encoldoccsupport1, 
       encegiynb, encptkp, enceyti, enceyit, encytdit, encinctaxapaidnb, 
       encoldinctaxapaidnb, enccol8pytd, encoldcol8pytd, enckolom8, 
       encoccsupport2, encoldoccsupport2, enceyitt, encinctaxapaidb, 
       encoldinctaxapaidb, enctotpot, encnilump, enctaxump, enctaxapaidump, 
       enctaxumpytd, encmaxbruto, encpenddp, 0, userid, updtime, userid, updtime FROM #S03LTAX ;
drop table #S03LTAX ;
--
select IDENTITY(INT,1,1) as row_num, * into #S04LEMB from S04LEMB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	 'SELECT  * FROM S04LEMB')   
	SELECT row_num, tglpayr, RTRIM(nip),RTRIM(nama),RTRIM(kdarea),RTRIM(kdcaba),RTRIM(kduusa),RTRIM(kduker),RTRIM(area), 
       RTRIM(caba),RTRIM(uusa),RTRIM(uker),RTRIM(kdjaba),RTRIM(skjaba),lembk1, lembk2, lembl1, lembl2, 
       lembl3, ttllemb, nilai, perhitungan1, perhitungan2, perhitungan3, 
       RTRIM(dasarlembur),RTRIM(pendek), postid, postdate, updtime, lembi1, lembi2,  0, postid, updtime, postid, updtime FROM #S04LEMB ;
drop table #S04LEMB ;
--
select IDENTITY(INT,1,1) as row_num, * into #S05PSTD from S05PSTD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S05PSTD')   
	SELECT row_num, RTRIM(nip), tglposting, RTRIM(flagthr), 0, null, null, null, null  
	FROM #S05PSTD q1  ;
drop table #S05PSTD ;
--
select IDENTITY(INT,1,1) as row_num, * into #S06ABSH from S06ABSH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S06ABSH')   
	SELECT row_num, tanggal, RTRIM(nip),RTRIM(kdabsn),RTRIM(skabsn), jamabsn, hariabsn, postid, updtime, updtime,0, postid, updtime,postid, updtime 
	FROM #S06ABSH ;
drop table #S06ABSH ;
--------------------------------------------------------------------------------------------------------------------------------- 
select IDENTITY(INT,1,1) as row_num, * into #S07TRNG from S07TRNG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S07TRNG')   
	SELECT row_num, tanggal, RTRIM(nip),RTRIM(kdjnsd),RTRIM(skjnsd),RTRIM(kdjrsn),RTRIM(skjrsn),RTRIM(kdlmbd),
			RTRIM(sklmbd),RTRIM(kdlksd),RTRIM(sklksd), bulantrng, haritrng, RTRIM(hasil),RTRIM(nilai), biayasendiri, 
       biaya, RTRIM(noijaz), postid, postdate, updtime, 0, postid, updtime, postid, updtime FROM #S07TRNG ;
drop table #S07TRNG ;
--
select IDENTITY(INT,1,1) as row_num, * into #S08MUTA from S08MUTA ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S08MUTA')   
	SELECT row_num, RTRIM(nip),RTRIM(kdmutr),RTRIM(skmutr),RTRIM(nosk), tgleff, RTRIM(keterangan),RTRIM(kdklas), 
       RTRIM(skklas),RTRIM(kdcaba),RTRIM(skcaba),RTRIM(kduusa),RTRIM(skuusa),RTRIM(kduker),RTRIM(skuker),RTRIM(kdglng), 
       RTRIM(skglng),RTRIM(kdjaba),RTRIM(skjaba),RTRIM(kdarea),RTRIM(skarea),RTRIM(kdkjab),RTRIM(skkjab), prdpelks, 
       RTRIM(kdcurr), gajipokok, RTRIM(penilaian), masakerja, postid, postdate, updtime, 
       encgajipokok, RTRIM(keterangan2),RTRIM(keterangan3),RTRIM(keterangan4),RTRIM(keterangan5),  0, postid, updtime, postid, updtime FROM #S08MUTA ;
drop table #S08MUTA ;
--
select IDENTITY(INT,1,1) as row_num, * into #S09HCUT from S09HCUT ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S09HCUT')   
	SELECT row_num, RTRIM(nip), tahun, jatahcuti, pemakaian, RTRIM(keterangan), 0, userid, updtime, userid, updtime FROM #S09HCUT ;
--drop table #S09HCUT ;
---
select IDENTITY(INT,1,1) as row_num, * into #S0ADCUT from S0ADCUT ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S0ADCUT')   
	SELECT q1.row_num, RTRIM(q1.nip), q1.tahun, RTRIM(q1.blntgl), RTRIM(q1.kdcuti), q1.pemakaian, s09.row_num, 0, ' ', getdate(), ' ', getdate() 
	FROM #S0ADCUT q1 
	inner join #S09HCUT s09 on q1.nip = s09.nip and q1.tahun = s09.tahun;
drop table #S0ADCUT ;
--
select IDENTITY(INT,1,1) as row_num, * into #S0BLSTX from S0BLSTX ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S0BLSTX')   
	SELECT row_num, tglpayr, RTRIM(nip), RTRIM(jnspajak), grossincpytd, grossinccurrent, 
       varipytd, varicurrent, occsupport, ptkp, incometaxcurrent, incometaxapaid, 
       taxableinccurrent, taxableincpytd, bonuscurrent, bonuspytd, pjbnscurrent, 
       pjbnspytd, incometaxttl, RTRIM(flagthr), encgrossincpytd, encgrossinccurrent, 
       encvaripytd, encvaricurrent, encoccsupport, encptkp, encincometaxcurrent, 
       encincometaxapaid, enctaxableinccurrent, enctaxableincpytd, encbonuscurrent, 
       encbonuspytd, encpjbnscurrent, encpjbnspytd, encincometaxttl,  0, userid, updtime, userid, updtime FROM #S0BLSTX ;
drop table #S0BLSTX ;
---
select IDENTITY(INT,1,1) as row_num, * into #S0CMEDH from S0CMEDH ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S0CMEDH')   
	SELECT row_num, RTRIM(nip), tahun, RTRIM(jenismedical), jatahmedical, jatahmedicalkel, 
       pemakaian, pemakaiankel, pemakaiankelovr, 0, userid, updtime, userid, updtime FROM #S0CMEDH ;
--drop table #S0CMEDH ;
select IDENTITY(INT,1,1) as row_num, * into #S0DMEDD from S0DMEDD ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S0DMEDD')   
	SELECT q1.row_num, RTRIM(q1.nip), q1.tahun, RTRIM(q1.jenismedical), q1.tanggal, q1.pemakaian, RTRIM(Q1.keterangan), s0c.row_num, 0, s0c.userid, s0c.updtime, s0c.userid, s0c.updtime 
	FROM #S0DMEDD q1 
	inner join #S0CMEDH s0c on q1.nip = s0c.nip and q1.tahun = s0c.tahun and q1.jenismedical = s0c.jenismedical;
drop table #S0DMEDD ;
--
select IDENTITY(INT,1,1) as row_num, * into #S0ESFPC from S0ESFPC ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S0ESFPC')   
	SELECT row_num, tahun, RTRIM(nip),RTRIM(cab01),RTRIM(cab02),RTRIM(cab03),RTRIM(cab04),RTRIM(cab05),RTRIM(cab06), 
       RTRIM(cab07),RTRIM(cab08),RTRIM(cab09),RTRIM(cab10),RTRIM(cab11),RTRIM(cab12),  0, ' ', getdate(), ' ', getdate() FROM #S0ESFPC ;
drop table #S0ESFPC ;
--
select IDENTITY(INT,1,1) as row_num, * into #S0FPDAP from S0FPDAP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM S0FPDAP')   
	SELECT row_num, periode,RTRIM(flag),RTRIM(nip),RTRIM(nokartu),RTRIM(status),RTRIM(namapeserta),RTRIM(hubungan),
		RTRIM(jnsklmn),tgllahir,RTRIM(norekbank),RTRIM(kduker),RTRIM(kdcaba),RTRIM(kdkjab),RTRIM(kdjasu),RTRIM(lvlbenefit),
		  0, ' ', getdate(), ' ', getdate() FROM #S0FPDAP ;
drop table #S0FPDAP ;
--
select IDENTITY(INT,1,1) as row_num, * into #S0GPREM from S0GPREM ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	'SELECT  * FROM S0GPREM')   
	SELECT q1.row_num, RTRIM(q1.nip),RTRIM(q1.flgdppt),RTRIM(q1.kddppt), q1.prdmulai, q1.prdsd, q1.nilai, RTRIM(q1.kdcurr), 
       q1.persen, q1.bayardimuka, RTRIM(q1.flag), m03.row_num ,0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #S0GPREM q1 
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;

drop table #S0GPREM ;
---------------------------------------------------------------------------------------------------------------------------------
select IDENTITY(INT,1,1) as row_num, * into #T01LEMB from T01LEMB ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T01LEMB')   
	SELECT row_num, tgdocu, RTRIM(nip),RTRIM(flglemb),RTRIM(refdok), jamlemb, jmlumkn, flag, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #T01LEMB q1 ;
drop table #T01LEMB ;
--
select IDENTITY(INT,1,1) as row_num, * into #T02ABSN from T02ABSN ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T02ABSN')   
	SELECT q1.row_num, RTRIM(q1.nip), q1.tanggal, RTRIM(q1.flgdppt),RTRIM(q1.kddppt), q1.jmlabsn, q1.flag, q1.bayardimuka,  m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T02ABSN q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt ;
drop table #T02ABSN ;
--
select IDENTITY(INT,1,1) as row_num, * into #T03VARI from T03VARI ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T03VARI')   
	SELECT q1.row_num, RTRIM(q1.nip),RTRIM(q1.flgdppt),RTRIM(q1.kddppt), q1.prdmulai, q1.prdsd, q1.nilai, RTRIM(q1.kdcurr), 
       q1.persen, q1.bayardimuka, q1.flag, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T03VARI q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt ;
drop table #T03VARI ;
--
select IDENTITY(INT,1,1) as row_num, * into #T04TTAP from T04TTAP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi',
	'SELECT  * FROM T04TTAP')   
	SELECT  q1.row_num, RTRIM(q1.nip),RTRIM(q1.flgdppt),RTRIM(q1.kddppt), q1.nilai, RTRIM(q1.kdcurr), q1.persen, q1.bayardimuka, 
       RTRIM(q1.flgauto), q1.flag, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T04TTAP q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;
drop table #T04TTAP ;
--
select IDENTITY(INT,1,1) as row_num, * into #T05HPIU from T05HPIU ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT * FROM T05HPIU')   
	SELECT q1.row_num, RTRIM(q1.nip),RTRIM(q1.kdjnsp), q1.tgdoku, RTRIM(q1.noref), q1.piutang, q1.bunga, q1.ttlangspok, 
      q1.ttlangsbun, q1.baypokpay, q1.baybngpay, q1.baypoklgs, q1.baybnglgs, q1.prsbunga, 
       RTRIM(q1.jnsbunga),q1.recs, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #T05HPIU q1  ;
--drop table #T05HPIU ;
--
select IDENTITY(INT,1,1) as row_num, * into #T06DPIU from T06DPIU ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T06DPIU')   
	SELECT q1.row_num, RTRIM(q1.nip),RTRIM(q1.kdjnsp), q1.tgdoku, q1.prdangs, q1.jmlangs, q1.jmlbunga,t05.row_num, 0, t05.userid, t05.updtime, t05.userid, t05.updtime 
	FROM #T06DPIU q1 
	inner join #T05HPIU t05 on q1.nip = t05.nip and q1.KdJnsP = t05.kdjnsp and q1.TgDoku = t05.tgdoku 
drop table #T06DPIU ;
--
select IDENTITY(INT,1,1) as row_num, * into #T07BAYP from T07BAYP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T07BAYP')   
	SELECT q1.row_num, RTRIM(q1.nip),RTRIM(q1.kdjnsp), q1.tgpiut, q1.tgdoku, q1.baypokok, q1.baybunga, q1.comp, 
       RTRIM(q1.keterangan), t05.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T07BAYP q1
	inner join #T05HPIU t05 on q1.nip = t05.nip and q1.KdJnsP = t05.kdjnsp and q1.TgDoku = t05.tgdoku 
drop table #T07BAYP ;
--
select IDENTITY(INT,1,1) as row_num, * into #T08ABSN from T08ABSN ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T08ABSN')   
	SELECT q1.row_num, q1.tgdocu, RTRIM(q1.nip),RTRIM(q1.kdabsn), q1.cutitahun, q1.jamabsn, q1.hariabsn, rTRIM(q1.keterangan),  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T08ABSN q1 ;
drop table #T08ABSN ;
--
select IDENTITY(INT,1,1) as row_num, * into #T09HARG from T09HARG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T09HARG')   
	SELECT q1.row_num, q1.rid, q1.tglentry, RTRIM(q1.nip),RTRIM(q1.kdharg), q1.tgleff, RTRIM(q1.nosk), q1.tglsk, q1.durasi, 
       RTRIM(q1.keterangan), 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T09HARG q1;
drop table #T09HARG ;
--
select IDENTITY(INT,1,1) as row_num, * into #T10MUTA from T10MUTA ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T10MUTA')   
	SELECT q1.row_num,RTRIM(q1.nip),q1.tgleff,RTRIM(q1.nosk),RTRIM(q1.kdmutr),RTRIM(q1.keterangan),RTRIM(q1.kdklas),RTRIM(q1.kdcaba),
			RTRIM(q1.kduusa),RTRIM(q1.kduker),RTRIM(q1.kdglng),RTRIM(q1.kdjaba),RTRIM(q1.kdarea),RTRIM(q1.kdkjab),q1.prdpelks,q1.gajipokok,RTRIM(q1.kdcurr),
			RTRIM(q1.penilaian),q1.masakerja,q1.prn,RTRIM(q1.keterangan2),RTRIM(q1.keterangan3),RTRIM(q1.keterangan4),RTRIM(q1.keterangan5),
			m04.row_num, m06.row_num, m08.row_num, m12.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime
	FROM #T10MUTA q1
	inner join #M04HJAB m04 on m04.kode = q1.kdJABa
	inner join #M06HKJB m06 on m06.kode = q1.kdkJAB 
	inner join #M08HCAB m08 on m08.kdcaba = q1.kdcaba 
	inner join #M12HGOL m12 on m12.kode = q1.kdglng ;
drop table #T10MUTA ;
--
select IDENTITY(INT,1,1) as row_num, * into #T11HTRN from T11HTRN ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T11HTRN')   
	SELECT q1.row_num, q1.tgdocu, RTRIM(q1.kdjnsd),RTRIM(q1.kdjrsn),RTRIM(q1.kdlmbd),RTRIM(q1.kdlksd), q1.haritrng, q1.bulantrng, 
       q1.biaya, RTRIM(q1.nomorsk), q1.tglsk, RTRIM(q1.tingkatan), q1.recs, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T11HTRN q1 ;
--drop table #T11HTRN ;
--
select IDENTITY(INT,1,1) as row_num, * into #T12DTRN from T12DTRN ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T12DTRN')   
	SELECT q1.row_num, q1.tgdocu, RTRIM(q1.kdjnsd),RTRIM(q1.kdjrsn),RTRIM(q1.nip),RTRIM(q1.hasil),RTRIM(q1.nilai),RTRIM(q1.noijaz), q1.biayasendiri, 
		t11.row_num,  0, t11.userid, t11.updtime, t11.userid, t11.updtime 
	FROM #T12DTRN q1 
	inner join #T11HTRN t11 on q1.TgDocu = t11.TgDocu and q1.KdJnsD = t11.KdJnsD and q1.KdJrsn = t11.KdJrsn ;
drop table #T12DTRN ;
-------------------------------------------------------------------------------------------------------------------------------dari sini 
select IDENTITY(INT,1,1) as row_num, * into #T13FASI from T13FASI ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T13FASI')   
	SELECT row_num, RTRIM(nip), tgldiberi,RTRIM(kdfasi), tglkembali, RTRIM(keterangan),  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T13FASI q1 ;
drop table #T13FASI ;
--
select IDENTITY(INT,1,1) as row_num, * into #T14JTHM from T14JTHM ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T14JTHM')   
	SELECT row_num, RTRIM(nip), tahun, jatahmedical, pemakaian, RTRIM(sifatjatah), jatahbulanan, 
       RTRIM(keterangan),  0, q1.userid, q1.updtime, q1.userid, q1.updtime FROM #T14JTHM q1
--drop table #T14JTHM ;
--
select IDENTITY(INT,1,1) as row_num, * into #T15PMED from T15PMED ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T15PMED')   
	SELECT q1.row_num, RTRIM(q1.nip), q1.tahun, q1.tanggal, RTRIM(q1.keterangan), RTRIM(q1.jenismedical), q1.pemakaian, t14.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T15PMED q1 
	inner join #T14JTHM t14 on q1.nip = t14.nip and q1.tahun = t14.tahun ;
drop table #T15PMED ;
--
select IDENTITY(INT,1,1) as row_num, * into #T16PKON from T16PKON ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T16PKON')   
	SELECT q1.row_num, RTRIM(q1.nip), q1.tglakhir, RTRIM(q1.keterangan), RTRIM(q1.nokontrak), RTRIM(q1.jabatan), q1.encprogaji, 
       RTRIM(q1.kdcurr), q1.frdate, m04.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T16PKON q1
	inner join #M04HJAB m04 on m04.kode = q1.jabatan ;
drop table #T16PKON ;
--
select IDENTITY(INT,1,1) as row_num, * into #T17PDAK from T17PDAK ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T17PDAK')   
	SELECT q1.row_num, RTRIM(q1.nip), q1.levelakm, RTRIM(q1.flgdppt),RTRIM(q1.kddppt), q1.persen, q1.flag, m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T17PDAK q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt ;
drop table #T17PDAK ;
--
select IDENTITY(INT,1,1) as row_num, * into #T18HKER from T18HKER ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T18HKER')   
	SELECT q1.row_num, RTRIM(q1.nip), RTRIM(q1.inout), q1.hkerja, 0, q1.userid, q1.updtime, q1.userid, q1.updtime FROM #T18HKER q1 ;
drop table #T18HKER ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T19PESA')   
	SELECT RTRIM(nip), tglkeluar, nilaipesangon, RTRIM(kdcurr), RTRIM(flagproses), nilaiperaturan,  0, q1.userid, q1.updtime, q1.userid, q1.updtime  FROM T19PESA q1 ;
--
select IDENTITY(INT,1,1) as row_num, * into #T20JTMJ from T20JTMJ ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T20JTMJ')   
	SELECT q1.row_num, q1.tahun, RTRIM(q1.jenismedical), RTRIM(q1.keljab), q1.plafon, q1.plafonkel, RTRIM(q1.keterangan), 
       q1.flagkasus, m06.row_num,  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T20JTMJ q1 
	inner join #M06HKJB m06 on m06.kode = q1.KelJab;
drop table #T20JTMJ ;
--
select IDENTITY(INT,1,1) as row_num, * into #T21PMEP from T21PMEP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T21PMEP')   
	SELECT q1.row_num, RTRIM(q1.nip), q1.tahun, RTRIM(q1.jenismedical), q1.tanggal, q1.flagkel, RTRIM(q1.namakel), RTRIM(q1.kdsupl), 
			q1.pemakaian, RTRIM(q1.keterangan),  0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T21PMEP q1  ;
drop table #T21PMEP ;
--
select IDENTITY(INT,1,1) as row_num, * into #T22KPAS from T22KPAS ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T22KPAS')   
	SELECT q1.row_num, RTRIM(q1.nip), RTRIM(q1.kode), RTRIM(q1.nopeserta), q1.tanggalmasuk, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #T22KPAS q1;
drop table #T22KPAS ;
--
select IDENTITY(INT,1,1) as row_num, * into #T23MPEG from T23MPEG ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T23MPEG')   
	SELECT q1.row_num, q1.tanggal, RTRIM(q1.nip), RTRIM(q1.flgdppt), RTRIM(q1.kddppt), q1.nilai, RTRIM(q1.kdcurr), 
			m03.row_num, 0, q1.userid, q1.updtime, q1.userid, q1.updtime  
	FROM #T23MPEG q1
	inner join #m03dppt m03 on m03.flgdppt = q1.flgdppt and m03.kddppt = q1.kddppt;
drop table #T23MPEG ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T24PDLL')   
	SELECT RTRIM(nip),RTRIM(keterangan),q1.userid, q1.upddate, q1.updtime, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM T24PDLL q1 ;
--
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T25OPBP')   
	SELECT rtrim(flgimpl),RTRIM(nip), tanggal, prevdate, RTRIM(kdcaba), k1gct, k1nct, k2gct, 
       k2nct, k3gct, k3nct, k4gct, k4nct, k5gct, k5nct, k6gct, k6nct, 
       k10gct, k12gct, pphct, k8gct, k8nct, k11gct, k1gcs, k1ncs, k2gcs, 
       k2ncs, k3gcs, k3ncs, k4gcs, k4ncs, k5gcs, k5ncs, k6gcs, k6ncs, 
       k10gcs, k12gcs, pphcs, k8gcs, k8ncs, k11gcs, masaperolehan, encs01grossincomenbytd, 
       encs01occsupport1, encs01col12ytd, encs01oldinctaxapaidnb, encs01grossincomebytd, 
       encs01taxtotal, encs01oldinctaxapaidb, encfixincomepytd, encoldfixincomepytd, 
       encfixincome, encvarincomepytd, encoldvarincomepytd, encvarincome, 
       enccol2ytd, encoldcol2ytd, enccol3ytd, encoldcol3ytd, enccol4ytd, 
       encoldcol4ytd, enccol5ytd, encoldcol5ytd, enccol6ytd, encoldcol6ytd, 
       enccol12pytd, encoldcol12pytd, enckolom12, encoccsupport1, encoldoccsupport1, 
       encegiynb, encptkp, enceyti, enceyit, encytdit, encinctaxapaidnb, 
       encoldinctaxapaidnb, enccol8pytd, encoldcol8pytd, enckolom8, 
       encoccsupport2, encoldoccsupport2, enceyitt, encinctaxapaidb, 
       encoldinctaxapaidb, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM T25OPBP q1  ;
--
select IDENTITY(INT,1,1) as row_num, * into #T26PPHL from T26PPHL ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T26PPHL')   
	SELECT row_num, periode, RTRIM(nip), encpelunasan, 0, q1.userid, q1.updtime, q1.userid, q1.updtime 
	FROM #T26PPHL q1;
drop table #T26PPHL ;

select IDENTITY(INT,1,1) as row_num, * into #T27PMEP from T27PMEP ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM T27PMEP')   
	SELECT row_num, RTRIM(nip), tahun, RTRIM(jenismedical), tanggal, RTRIM(flagkel),RTRIM(namakel), 
       RTRIM(kdsupl), crtpiutang, tglangs1, nlmed2piut, 0, ' ', getdate(), ' ', getdate()
	FROM #T27PMEP Q1;
drop table #T27PMEP ;

--select IDENTITY(INT,1,1) as row_num, * into #VerHist from VerHist ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM VerHist')   
	SELECT * FROM VerHist ;
--drop table #VerHist ;
--select IDENTITY(INT,1,1) as row_num, * into #W01JRGR from W01JRGR ;
INSERT INTO OPENROWSET('MSDASQL', 'Driver=PostgreSQL;uid=postgres;Server=localhost;database=hapisdev2;pwd=solusi', 
	'SELECT  * FROM W01JRGR')   
	SELECT * FROM W01JRGR ;
--drop table #W01JRGR ;



