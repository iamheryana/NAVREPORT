/****************************************
Name sprocs	: fn_FiltClos
Create by	: wati
Date		: 16-06-2003
Description	: Function Filter untuk Closing
JIKA l_FilterData	'H'=Harian Belum Akhir Bulan
        			'B'=Bulanan Belum Proses Bulanan
                	''=Sukses
*****************************************/
CREATE OR REPLACE FUNCTION fn_FiltClos(
                            l_NIP           VARCHAR(10),
                            l_Periode       DATE,
                            l_M10Harian     INT,
                            l_M15TglPayr	DATE)
--
RETURNS VARCHAR(1)
AS $$ 
--
DECLARE l_FilterData VARCHAR(1) ;
--
BEGIN
  --
  l_FilterData := '';
  --
  -- Jika Pegawai Sudah Proses untuk Bulan Periode
  IF EXISTS(SELECT S01.NIP FROM S01HGAJ S01 WHERE S01.NIP=l_NIP AND TO_CHAR(S01.TglPayr,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM')) THEN
     BEGIN
         -- Jika pegawai Harian Tapi Belum Proses Akhir Bulan Untuk Proses Harian
         IF l_M10Harian=1 THEN
             IF EXISTS(SELECT S01.NIP FROM S01HGAJ S01 WHERE S01.NIP=l_NIP AND COALESCE(S01.FlagH,'')='' AND TO_CHAR(S01.TglPayr,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM')) THEN           
                l_FilterData := '';
             ELSE 
                l_FilterData := 'H' ;
             END IF;
         ELSE
             -- Jika pegawai Bulanan Tapi Belum Pernah Proses Bulanan
             -- Untuk Pegawai Tetap
             IF EXISTS(SELECT S03.NIP FROM S03LTAX S03 WHERE S03.NIP=l_NIP AND COALESCE(S03.FlagTHR,'')='' AND TO_CHAR(S03.TglPayr,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM')) THEN
                l_FilterData := '';
             ELSE
                -- Jika Pegawai jenis Pajaknya <> ' '
                IF EXISTS(SELECT S08.NIP FROM S0BLSTX S08 WHERE S08.NIP=l_NIP AND TO_CHAR(S08.TglPayr,'YYYYMM')=TO_CHAR(l_Periode,'YYYYMM')) THEN
                   l_FilterData := '' ; 
                ELSE
                   l_FilterData :=  'B';
                END IF ;
             END IF ;    
         END IF ;
     END ;
  ELSE
     l_FilterData := 'B' ;
  END IF ;
  --*
  RETURN l_FilterData ; 
END ; 

$$ language plpgsql ;  
/*
SELECT dbo.fn_FiltClos('CLOSX','2003-02-01',0,'2003-02-01')
*/
