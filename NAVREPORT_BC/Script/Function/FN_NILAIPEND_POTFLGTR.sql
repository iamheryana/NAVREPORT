
CREATE OR REPLACE FUNCTION public.fn_nilaipend_potflgtr (in l_tglproses date, in l_nip varchar, in l_flgdppt varchar, in l_kddppt varchar, in l_persen numeric, in l_nilai numeric, in l_mnhari numeric, in l_gajirp numeric, in l_reverse varchar, in l_mypass varchar, in l_kdcurr varchar) RETURNS numeric AS
$BODY$
DECLARE 
		l_Flag VARCHAR(1);
        l_M03Singkatan VARCHAR(10);
        l_M03UsComp    VARCHAR(1);
		l_M03Kolom     VARCHAR(2);
		l_M03NoAcc     VARCHAR(10);
		l_M03Status    VARCHAR(1);
		l_M03Persen    DECIMAL(5,2);
		l_M03NiLai     DECIMAL(15,2);
		l_M03NoLyr     Int;
		l_M03KdCurr    VARCHAR(4);
        l_S02Nilai     DECIMAL(15,2);
        l_mnKurs       DECIMAL(10,2);
        l_NilPend_Pot  DECIMAL(15,2);
        l_NilPend_PotVal DECIMAL(15,2);
        l_GajiVal      DECIMAL(15,2);          
        l_FZ1FlgGlng   VARCHAR(1);         
		l_FZ1FlgJaba   VARCHAR(1);
     	l_FZ1FlgKJab 	VARCHAR(1);        	
		l_FZ1Caba    	VARCHAR(1);      
		l_M15kdCaba    VARCHAR(4);
		l_M15KdGlng    VARCHAR(4);
		l_M15kdJaba    VARCHAR(4);
		l_M15kdKJab  	VARCHAR(4);
		l_M09Persen    DECIMAL(5,2);
		l_M09NiLai     DECIMAL(15,2);
		l_KdCab			VARCHAR(4);
		l_TRANS			VARCHAR(1);
		L_Kode			VARCHAR(4);
BEGIN
---- Initialisasi data
	l_S02Nilai     	:=0;
    l_mnKurs       	:=0;
    l_NilPend_Pot  	:=0;
    l_NilPend_PotVal :=0;
    l_GajiVal      	:=0;

	SELECT	FlgGlng,FlgKJab,FlgJaba,FlgCaba INTO l_FZ1FlgGlng,l_FZ1FlgKJab,l_FZ1FlgJaba,l_FZ1Caba FROM FZ1FLDA;

	SELECT M15.KdCaba,M15.KdGlng,M15.KdJaba,M15.KdKJab INTO l_M15kdCaba,l_M15KdGlng,l_M15kdJaba,l_M15kdKJab FROM M15PEGA M15 WHERE NIP = l_NIP;

	l_NilPend_Pot := 0; 

	SELECT KdCab into l_KdCab FROM M09DCAB WHERE KdCab=l_M15kdCaba;
	SELECT TRANS into l_TRANS FROM M09DCAB WHERE KDCAB = l_M15KDCABA AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT;
	
	IF l_FZ1Caba='Y' AND (l_KdCab IS NOT NULL) AND  (COALESCE(l_TRANS,'T') = 'Y') THEN
      SELECT PERSEN,NILAI INTO l_M09PERSEN,l_M09NILAI FROM M09DCAB WHERE KDCAB = l_M15KDCABA AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT;
	  l_NilPend_Pot:=fn_PersenVsNilai(l_TglProses,l_NIP,l_FlgDpPt,l_KdDpPt,l_M09PERSEN,l_M09NILAI,l_mnhari,l_GajiRp,l_reverse,l_MyPass,l_KdCurr);
    END IF;
	
	-- Pendapatan/Potongan Golongan
	SELECT Kode INTO L_Kode FROM M13DGOL WHERE Kode=l_M15KdGlng;
	SELECT TRANS INTO l_TRANS FROM M13DGOL WHERE Kode=l_M15KdGlng AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT;
	IF l_FZ1FlgGlng='Y' AND (L_Kode IS NOT NULL) AND  (COALESCE(l_TRANS,'T') = 'Y' ) THEN
		SELECT PERSEN, NILAI INTO l_M09PERSEN,l_M09NILAI FROM M13DGOL WHERE Kode=l_M15KdGlng AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT;
		l_NilPend_Pot:=COALESCE(l_NilPend_Pot,0)+fn_PersenVsNilai(l_TglProses,l_NIP,l_FlgDpPt,l_KdDpPt,l_M09PERSEN,l_M09NILAI,l_mnhari,l_GajiRp,l_reverse,l_MyPass,l_KdCurr);
    END IF;

	-- Pendapatan/Potongan Kelompok Jabatan
	SELECT Kode INTO L_Kode FROM M07DKJB WHERE Kode=l_M15kdKJab;
	SELECT TRANS INTO l_TRANS FROM M07DKJB WHERE Kode=l_M15kdKJab AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT;
	IF l_FZ1FlgKJab='Y' AND (L_Kode IS NOT NULL) AND (COALESCE(l_TRANS,'T') = 'Y') THEN
		SELECT PERSEN,NILAI INTO  l_M09PERSEN, l_M09NILAI FROM M07DKJB WHERE Kode=l_M15kdKJab AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT;
		l_NilPend_Pot:=COALESCE(l_NilPend_Pot,0)+fn_PersenVsNilai(l_TglProses,l_NIP,l_FlgDpPt,l_KdDpPt,l_M09PERSEN,l_M09NILAI,l_mnhari,l_GajiRp,l_reverse,l_MyPass,l_KdCurr);
    END if;

	-- Pendapatan/Potongan Jabatan
	SELECT Kode INTO l_Kode  FROM M05DJAB WHERE Kode=l_M15kdJaba;
	SELECT TRANS INTO l_TRANS FROM M05DJAB WHERE Kode=l_M15kdJaba AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT;
	IF l_FZ1FlgJaba='Y' AND L_Kode IS NOT NULL AND  (COALESCE(l_TRANS,'T') = 'Y') THEN
		SELECT PERSEN, NILAI INTO l_M09PERSEN,l_M09NILAI FROM M05DJAB WHERE Kode=l_M15kdJaba AND FLGDPPT+KDDPPT = l_FLGDPPT+l_KDDPPT;
		l_NilPend_Pot=COALESCE(l_NilPend_Pot,0)+fn_PersenVsNilai(l_TglProses,l_NIP,l_FlgDpPt,l_KdDpPt,l_M09PERSEN,l_M09NILAI,l_mnhari,l_GajiRp,l_reverse,l_MyPass,l_KdCurr);
    END IF;

  RETURN l_NilPend_Pot;
end;
$BODY$
LANGUAGE 'plpgsql'
GO
