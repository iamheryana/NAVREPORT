
CREATE OR REPLACE FUNCTION public.fn_validasi_unpost (in l_flagthr varchar, in l_nip varchar, in l_tglpayroll date, in l_tglmasuk date, in l_tglkeluar date, in l_tglakhir date, in l_p_unbln varchar) RETURNS varchar AS
$BODY$


  DECLARE l_DataValid VARCHAR(1);
          l_FlagM     VARCHAR(1);
BEGIN
  --
  SELECT FlagM
  INTO   l_FlagM
  FROM S01HGAJ
  WHERE TglPayr=l_TglAkhir AND NIP=l_NIP ;
         --
  IF l_TglAkhir > l_TglPayroll THEN 
      l_DataValid := '1'; 
  ELSIF l_TglAkhir < l_TglPayroll THEN
      l_DataValid := '2';
  ELSIF l_TglAkhir=l_TglPayroll AND COALESCE(l_FlagM,' ')='P' THEN
      l_DataValid := '4';
  -- Dari Unpost Bulanan/Harian
  ELSIF l_P_UnBln='Y' AND l_TglAkhir=l_TglPayroll AND COALESCE(l_FlagTHR,' ')<>' ' THEN
      l_DataValid := '3';
  -- Dari Unpost THR/Khusus/mantan Pengawai
  ELSIF l_P_UnBln='T' AND l_TglAkhir=l_TglPayroll AND COALESCE(l_FlagTHR,' ')=' '  THEN
      l_DataValid := '5'; 
  ELSE
      l_DataValid := '0'; 
  END IF;     
  -- 
  RETURN l_DataValid; 
  
END;
$BODY$
LANGUAGE 'plpgsql'
GO
