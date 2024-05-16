CREATE VIEW v_ingj AS

select nip, nama, kdcaba, kdglng, 
case CAST (fn_KPusat(m15.NIP,m15.Encgajiperc,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved') AS NUMERIC)
when '1' then ' '
else 'X'
end AS gajipercobaan,

case CAST (fn_KPusat(m15.NIP,m15.Encgajitetap,'Copyright, 1988 (c) Microsoft Corporation, All rights reserved')  AS NUMERIC)
when '1' then ' '
else 'X' 
end AS gajitetap


from m15pega m15


