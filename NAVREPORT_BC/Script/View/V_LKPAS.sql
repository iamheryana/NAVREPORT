CREATE VIEW V_LKPAS AS

select T22.* from t22kpas t22
inner join m15pega m15 on t22.nip = m15.nip 
left join m58pred m58 on m15.kdkjab = m58.kdkjab 
where m58.kdjasu is null 