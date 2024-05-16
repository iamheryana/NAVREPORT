
  CREATE OR REPLACE VIEW v_ltaxdt AS 
 SELECT tmp.tahun, tmp.nip, MAX(tmp.tglpayr) AS tglpayr
   FROM ( SELECT s03.tglpayr, date_part('year'::text, s03.tglpayr) AS tahun, m15.nip
           FROM s03ltax s03
      JOIN m15pega m15 ON m15.nip::text = s03.nip::text
   JOIN m10klas m10 ON m10.kode::text = m15.kdklas::text
   JOIN m41jpjk m41 ON m10.jnspajak = m41.kode
   JOIN m04hjab m04 ON m15.kdjaba::text = m04.kode::text
  WHERE m41.jnsform1721::text = 'A1'::text ) tmp
  GROUP BY tmp.tahun, tmp.nip--, tmp.tglpayr;

