/********************************************
Name sprocs	: V_LKLRG
Create by	: PEGGY
Date		: 17-06-2006
Description	: Script Listing KELUARGA YANG TIDAK MEMENUHI SYARAT 
Call From	: Main Proc
Sub sprocs	: -
*****************************************/

CREATE VIEW V_LKLRG AS
select M23.*, 
CASE 
when m23.tgllahir is null then 'Tanggal Lahir masih kosong' 
when m23.tgllahir is not null and m44.ws ='Y' and m23.jnsklmn = 'P' and m23.hubungan = '1' and (date_part('year',m23.tgllahir)-date_part('year',(select greatest(tglmulaipolis) from fz2flda))) > (select greatest(umuristrimax) from fz2flda) then 'istri melebihi umur max untuk asuransi maternity' 
when m23.tgllahir is not null and m44.ws ='T' and m23.hubungan = '2' and (date_part('year',m23.tgllahir)-date_part('year',(select greatest(tglmulaipolis) from fz2flda))) > (select greatest(umuranakmax) from fz2flda) then 'Umur anak melebihi umur max' 
end AS alasan

FROM M23KLRG M23  join m15pega m15 on M23.nip = m15.nip 
 join t22kpas t22 on m15.nip = t22.nip 
 join m44jasu m44 on t22.kode = m44.kode  

where m23.tgllahir is not null and m23.ws = '1' and 
(
(m44.ws ='Y' and m23.jnsklmn = 'P' and m23.hubungan = '1' and (date_part('year',m23.tgllahir)-date_part('year',(select greatest(tglmulaipolis) from fz2flda))) > (select greatest(umuristrimax) from fz2flda)) 
or 
(m44.ws = 'T' and m23.hubungan = '2' and (date_part('year',m23.tgllahir)-date_part('year',(select greatest(tglmulaipolis) from fz2flda)) > (select greatest(umuranakmax) from fz2flda))) 
or m23.tgllahir is null
)
