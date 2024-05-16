/************************************************
Name sprocs	: Fn_HitPremi
Create by	: Diah
Date		: 24-05-2006
Description	: Function Hitung Premi Asuransi
Call From	: Main sprocs
*************************************************/
CREATE OR REPLACE FUNCTION  fn_HitPremi (l_NIP VARCHAR(10),l_Periode DATE,l_KdJAsu VARCHAR(4),l_LvlBenefit VARCHAR(20))
RETURNS DECIMAL(15,0)
AS $$
DECLARE 
		l_LOOP_S0F      REFCURSOR; 
        l_TglMulaiPolis DATE;
        l_TglAkhirPolis DATE;
        l_HariSetahun   DECIMAL(8); 
        l_TglMasuk      DATE; 
        l_JmlHari       DECIMAL(10,0); 
        l_JmlHariSethn  DECIMAL(10,0); 
        l_TglAwal       DATE; 
        l_S0FNIP        VARCHAR(10); 
        l_S0FPeriode    DATE; 
        l_S0FFlag       VARCHAR(1);
        l_S0FKdJAsu     VARCHAR(4); 
        l_S0FLvlBenefit VARCHAR(20);
        l_S0FStatus     VARCHAR(2);
        l_S0FJnsKlmn    VARCHAR(1);
        l_JmlPremi      DECIMAL(15,0);
        l_Premi         DECIMAL(15,0);
        l_TotPremi      DECIMAL(15,0); 
		l_ctr	       DECIMAL(5,0); 
		l_ada 	       DECIMAL(5,0);  
		l_TglBfr 		DATE; 
		l_TglAft 		DATE; 
		l_TanggalMasuk  	DATE;
		
BEGIN
	SELECT TglMulaiPolis,TglAkhirPolis,JmlHariSethn INTO l_TglMulaiPolis,l_TglAkhirPolis,l_HariSetahun FROM FZ2FLDA;
	SELECT TanggalMasuk INTO l_TglMasuk FROM T22KPAS WHERE NIP=l_NIP AND Kode=l_KdJASu;

	l_TglAwal := l_TglMulaiPolis;
	IF l_TglMasuk > l_TglMulaiPolis THEN
		l_TglMulaiPolis := l_TglMasuk;
	END IF; 

	l_TotPremi := 0;
	l_Ctr := 0;
	l_jmlharisethn := 0;
	l_jmlharisethn := 0; 
	l_Ada := 0; 

	-- apakah ada record di view asuransi, kecuali orang baru di tanggal awal polis
	--AND LvlBenefit=l_LvlBenefit
	SELECT Count(S0F.NIP) INTO l_Ada FROM V_ASRS S0F WHERE NIP=l_NIP AND KdJAsu=l_KdJASu  
	AND Hubungan='' AND S0F.Periode between l_tglmulaipolis and l_TglAkhirPolis	AND S0F.Periode <> l_TglAwal AND S0F.Periode <= l_Periode; 
	-- baru ditambahin karena kalau cetak jan maka muts apr gak boleh diprop. 

	IF COALESCE(l_Ada,0) > 0 THEN
		l_Tglbfr := l_TglMulaiPolis; 

		OPEN l_LOOP_S0F FOR SELECT S0F.NIP, S0F.Periode, S0F.Flag, S0F.KdJAsu, S0F.LvlBenefit, S0F.Status, S0F.JnsKlmn FROM V_ASRS S0F WHERE NIP=l_NIP AND KdJAsu=l_KdJASu AND Hubungan IS NULL AND S0F.Periode BETWEEN l_tglmulaipolis AND l_tglakhirpolis ORDER BY S0F.NIP, S0F.Periode, S0F.Flag;
		LOOP
			FETCH l_LOOP_S0F INTO l_S0FNIP,l_S0FPeriode,l_S0FFlag,l_S0FKdJAsu,l_S0FLvlBenefit,l_S0FStatus,l_S0FJnsKlmn;
			IF NOT FOUND THEN
				--EXIT;
				IF (l_jmlharisethn = l_harisetahun and l_jmlharisethn <> 0) or l_s0fperiode > l_periode OR l_TGLAFT = l_TGLAKHIRPOLIS THEN
				END IF;
				CASE l_S0FJnsKlmn
					WHEN 'L' THEN
						IF l_S0FStatus='L0' THEN
							SELECT	PremiMS INTO l_JmlPremi FROM M59PRE2 M59  WHERE Tahun=YEAR(l_Periode) AND KdJAsu=l_KdJAsu AND LvlBenefit=l_S0FLvlBenefit;
						ELSIF l_S0FStatus='K0' THEN
							SELECT	PremiMK0 INTO l_JmlPremi FROM M59PRE2 M59  WHERE Tahun=YEAR(l_Periode) AND KdJAsu=l_KdJAsu AND LvlBenefit=l_S0FLvlBenefit;
						ELSIF l_S0FStatus='K1' THEN
							SELECT	PremiMK1 INTO l_JmlPremi FROM M59PRE2 M59  WHERE Tahun=YEAR(l_Periode) AND KdJAsu=l_KdJAsu AND LvlBenefit=l_S0FLvlBenefit;
						ELSIF l_S0FStatus='K2' THEN
							SELECT	PremiMK2 INTO l_JmlPremi FROM M59PRE2 M59  WHERE Tahun=YEAR(l_Periode) AND KdJAsu=l_KdJAsu AND LvlBenefit=l_S0FLvlBenefit;
						ELSIF l_S0FStatus='K3' THEN
							SELECT	PremiMK3 INTO l_JmlPremi FROM M59PRE2 M59  WHERE Tahun=YEAR(l_Periode) AND KdJAsu=l_KdJAsu AND LvlBenefit=l_S0FLvlBenefit;
						END IF;
					WHEN 'P' THEN
						IF l_S0FStatus='L0' THEN
							SELECT	PremiFS INTO l_JmlPremi FROM M59PRE2 M59  WHERE Tahun=YEAR(l_Periode) AND KdJAsu=l_KdJAsu AND LvlBenefit=l_S0FLvlBenefit;
						ELSIF l_S0FStatus='K0' THEN
							SELECT	PremiFK0 INTO l_JmlPremi FROM M59PRE2 M59  WHERE Tahun=YEAR(l_Periode) AND KdJAsu=l_KdJAsu AND LvlBenefit=l_S0FLvlBenefit;
						ELSIF l_S0FStatus='K1' THEN
							SELECT	PremiFK1 INTO l_JmlPremi FROM M59PRE2 M59  WHERE Tahun=YEAR(l_Periode) AND KdJAsu=l_KdJAsu AND LvlBenefit=l_S0FLvlBenefit;
						ELSIF l_S0FStatus='K2' THEN
							SELECT	PremiFK2 INTO l_JmlPremi FROM M59PRE2 M59  WHERE Tahun=YEAR(l_Periode) AND KdJAsu=l_KdJAsu AND LvlBenefit=l_S0FLvlBenefit;
						ELSIF l_S0FStatus='K3' THEN
							SELECT	PremiFK3 INTO l_JmlPremi FROM M59PRE2 M59  WHERE Tahun=YEAR(l_Periode) AND KdJAsu=l_KdJAsu AND LvlBenefit=l_S0FLvlBenefit;
						END IF;
					ELSE l_JmlPremi:=0;
				END CASE;	
				
				SELECT Count(S0F.NIP) INTO l_Ada FROM V_ASRS S0F WHERE NIP=l_NIP AND KdJAsu=l_KdJASu AND Hubungan='' AND S0F.Periode BETWEEN l_TglBfr AND l_Periode;
				IF COALESCE(l_Ada,0) > 0 AND (l_S0FPeriode <= l_Periode OR l_CTR >=1) THEN
					-- cari mutasi berikutnya 
					SELECT LEAST(S0F.Periode) INTO l_TglAft FROM V_ASRS S0F WHERE NIP=l_NIP AND S0F.Periode > l_TglBfr AND KdJAsu=l_KdJASu AND Hubungan='' AND flag <> 'X';
					IF l_TglAft IS NULL OR (l_TglAft IS NOT NULL AND (l_TglAft <= l_S0FPeriode or  l_tglAft > l_periode)) THEN
						IF l_ADA > 2 THEN 
							l_TglAft := DATEADD(DD,1,l_TglAkhirPolis);
						ELSE 
							l_TglAft := l_tglAkhirpolis;
						END IF; 
						l_JmlHari := DATEDIFF(Day, l_Tglbfr, l_tglaft);
						l_Premi := (l_JmlHari * l_JmlPremi) / l_HariSetahun;		
						l_TotPremi := COALESCE(l_TotPremi,0) + l_Premi;
						l_ctr := l_ctr + 1 ;
						l_jmlharisethn := l_jmlharisethn + l_jmlhari; 
						l_TglBfr := l_tglaft;
						l_ctr := l_ctr + 1; 
					END IF;
				ELSE 
					l_TotPremi := l_JmlPremi;
				END IF;
			END IF;
		END LOOP;
		CLOSE l_LOOP_S0F;
	ELSE 
		SELECT GREATEST(S0F.Periode) INTO l_S0FPeriode FROM V_ASRS S0F WHERE NIP=l_NIP AND S0F.Periode<= l_TglMulaiPolis AND KdJAsu=l_KdJASu AND LvlBenefit =l_LvlBenefit AND Hubungan='' AND flag <> 'X';
		
		CASE l_S0FJnsKlmn
			WHEN 'L' THEN
				IF l_S0FStatus='L0' THEN
					SELECT	PremiMS INTO l_TotPremi FROM M59PRE2 M59 INNER JOIN V_ASRS S0F ON M59.KdJasu = S0F.KdJasu AND M59.LvlBenefit = S0F.LvlBenefit AND M59.Tahun = YEAR(l_Periode)  WHERE S0F.NIP = l_NIP AND S0F.PERIODE = l_S0FPeriode AND S0F.KdJAsu=l_KdJAsu AND S0F.LvlBenefit=l_LvlBenefit AND S0F.Hubungan = ' ';
				ELSIF l_S0FStatus='K0' THEN
					SELECT	PremiMK0 INTO l_TotPremi FROM M59PRE2 M59 INNER JOIN V_ASRS S0F ON M59.KdJasu = S0F.KdJasu AND M59.LvlBenefit = S0F.LvlBenefit AND M59.Tahun = YEAR(l_Periode)  WHERE S0F.NIP = l_NIP AND S0F.PERIODE = l_S0FPeriode AND S0F.KdJAsu=l_KdJAsu AND S0F.LvlBenefit=l_LvlBenefit AND S0F.Hubungan = ' ';
				ELSIF l_S0FStatus='K1' THEN
					SELECT	PremiMK1 INTO l_TotPremi FROM M59PRE2 M59 INNER JOIN V_ASRS S0F ON M59.KdJasu = S0F.KdJasu AND M59.LvlBenefit = S0F.LvlBenefit AND M59.Tahun = YEAR(l_Periode)  WHERE S0F.NIP = l_NIP AND S0F.PERIODE = l_S0FPeriode AND S0F.KdJAsu=l_KdJAsu AND S0F.LvlBenefit=l_LvlBenefit AND S0F.Hubungan = ' ';
				ELSIF l_S0FStatus='K2' THEN
					SELECT	PremiMK2 INTO l_TotPremi FROM M59PRE2 M59 INNER JOIN V_ASRS S0F ON M59.KdJasu = S0F.KdJasu AND M59.LvlBenefit = S0F.LvlBenefit AND M59.Tahun = YEAR(l_Periode)  WHERE S0F.NIP = l_NIP AND S0F.PERIODE = l_S0FPeriode AND S0F.KdJAsu=l_KdJAsu AND S0F.LvlBenefit=l_LvlBenefit AND S0F.Hubungan = ' ';
				ELSIF l_S0FStatus='K3' THEN
					SELECT	PremiMK3 INTO l_TotPremi FROM M59PRE2 M59 INNER JOIN V_ASRS S0F ON M59.KdJasu = S0F.KdJasu AND M59.LvlBenefit = S0F.LvlBenefit AND M59.Tahun = YEAR(l_Periode)  WHERE S0F.NIP = l_NIP AND S0F.PERIODE = l_S0FPeriode AND S0F.KdJAsu=l_KdJAsu AND S0F.LvlBenefit=l_LvlBenefit AND S0F.Hubungan = ' ';
				END IF;
			WHEN 'P' THEN
				IF l_S0FStatus='L0' THEN
					SELECT	PremiFS INTO l_TotPremi FROM M59PRE2 M59 INNER JOIN V_ASRS S0F ON M59.KdJasu = S0F.KdJasu AND M59.LvlBenefit = S0F.LvlBenefit AND M59.Tahun = YEAR(l_Periode)  WHERE S0F.NIP = l_NIP AND S0F.PERIODE = l_S0FPeriode AND S0F.KdJAsu=l_KdJAsu AND S0F.LvlBenefit=l_LvlBenefit AND S0F.Hubungan = ' ';
				ELSIF l_S0FStatus='K0' THEN
					SELECT	PremiFK0 INTO l_TotPremi FROM M59PRE2 M59 INNER JOIN V_ASRS S0F ON M59.KdJasu = S0F.KdJasu AND M59.LvlBenefit = S0F.LvlBenefit AND M59.Tahun = YEAR(l_Periode)  WHERE S0F.NIP = l_NIP AND S0F.PERIODE = l_S0FPeriode AND S0F.KdJAsu=l_KdJAsu AND S0F.LvlBenefit=l_LvlBenefit AND S0F.Hubungan = ' ';
				ELSIF l_S0FStatus='K1' THEN
					SELECT	PremiFK1 INTO l_TotPremi FROM M59PRE2 M59 INNER JOIN V_ASRS S0F ON M59.KdJasu = S0F.KdJasu AND M59.LvlBenefit = S0F.LvlBenefit AND M59.Tahun = YEAR(l_Periode)  WHERE S0F.NIP = l_NIP AND S0F.PERIODE = l_S0FPeriode AND S0F.KdJAsu=l_KdJAsu AND S0F.LvlBenefit=l_LvlBenefit AND S0F.Hubungan = ' ';
				ELSIF l_S0FStatus='K2' THEN
					SELECT	PremiFK2 INTO l_TotPremi FROM M59PRE2 M59 INNER JOIN V_ASRS S0F ON M59.KdJasu = S0F.KdJasu AND M59.LvlBenefit = S0F.LvlBenefit AND M59.Tahun = YEAR(l_Periode)  WHERE S0F.NIP = l_NIP AND S0F.PERIODE = l_S0FPeriode AND S0F.KdJAsu=l_KdJAsu AND S0F.LvlBenefit=l_LvlBenefit AND S0F.Hubungan = ' ';
				ELSIF l_S0FStatus='K3' THEN
					SELECT	PremiFK3 INTO l_TotPremi FROM M59PRE2 M59 INNER JOIN V_ASRS S0F ON M59.KdJasu = S0F.KdJasu AND M59.LvlBenefit = S0F.LvlBenefit AND M59.Tahun = YEAR(l_Periode)  WHERE S0F.NIP = l_NIP AND S0F.PERIODE = l_S0FPeriode AND S0F.KdJAsu=l_KdJAsu AND S0F.LvlBenefit=l_LvlBenefit AND S0F.Hubungan = ' ';
				END IF;
			ELSE 
				l_JmlPremi:=0;
		END CASE;	
	END IF;
	RETURN COALESCE(l_TotPremi,0);
end;
$$
language plpgsql;
---
/*
declare l_NilPremi DECIMAL(15,0)
SELECT l_NilPremi=dbo.Fn_HitPremi('z001', '2007-05-01', 'ip', '05/05F/06/06F/07/07F')
PRINT l_NilPremi
SELECT dbo.Fn_HitPremi('z001', '2007-01-01', 'ip', '05/05F/06/06F/07/07F')
SELECT dbo.Fn_HitPremi('z001', '2007-06-01', 'ip', '05/05F/06/06F/07/07F')
SELECT dbo.Fn_HitPremi('00088', '2008-01-01', 'ip', '08/08F/09/09F')

select * from v_Asrs 
where nip = 'z005' 
DECLARE l_NIP        VARCHAR(10),
                             l_Periode    DATE,
                             l_KdJAsu     VARCHAR(4),
                             l_LvlBenefit VARCHAR(20)
SELECT l_NIP = 'Z001', l_PERIODE = '2007-01-01', l_KDJASU = 'ip', l_LVLBENEFIT = '05/05F/06/06F/07/07F'

*/
