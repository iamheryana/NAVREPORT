CREATE VIEW V_PMED AS select T15.*, M15.NAMA, M32.KETERANGAN AS KETJMED, M15.KDGLNG, M15.KDCABA FROM T15PMED T15  join m15pega m15 on T15.nip = m15.nip  join M32JMED M32 on T15.JENISMEDICAL = M32.KODE;