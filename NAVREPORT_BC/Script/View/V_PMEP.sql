CREATE VIEW V_PMEP AS select T21.*, M15.NAMA, M32.KETERANGAN AS KETJMED, M15.KDGLNG, M15.KDCABA FROM T21PMEP T21  join m15pega m15 on T21.nip = m15.nip  join M32JMED M32 on T21.JENISMEDICAL = M32.KODE