/****************************************
Name sprocs	: R_PPHNP1
Create by	: Herz
Date		: 02-10-2001
Description	: Proses Untuk Report Perincian Pph Pasal 21 form B
Call From	: Main Proc
Sub sprocs	: -
*****************************************/
DROP FUNCTION R_PPHNP1 (l_Periode	DATE,
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
			l_Cabang 	VARCHAR(4),
			l_MyPass	VARCHAR(128),
			l_Usr_Id	INT);
--
CREATE FUNCTION R_PPHNP1 (l_Periode	DATE,
			l_NIPFr		VARCHAR(10),
			l_NIPTo		VARCHAR(10),
			l_Cabang 	VARCHAR(4),
			l_MyPass	VARCHAR(128),
			l_Usr_Id	INT)
--
RETURNS TABLE (OUTNIP			VARCHAR(10),
		OUTJns_Form	  	VARCHAR(1),
		OUTNama		  	VARCHAR(25),
		OUTIncYTD_Fix_Income 	DECIMAL(12),
		OUTIncYTD_Col2	  	DECIMAL(12),
		OUTIncYTD_Col3_6  	DECIMAL(12),
		OUTIncYTD_Col8	  	DECIMAL(12),
		OUTYTD_OccSp	  	DECIMAL(12),
		OUTYTD_Astek	  	DECIMAL(12)) 
AS $$

DECLARE l_S0BNIP		VARCHAR(10);
	l_S0BJnsPajak		VARCHAR(1);
	l_M15Nama		VARCHAR(25);
	l_S0BGrossIncPYTD	DECIMAL(15,2);
	l_S0BGrossIncCurrent	DECIMAL(15,2);
        l_S0BVariPYTD		DECIMAL(15,2);
	l_S0BVariCurrent	DECIMAL(15,2);
	l_S0BOccSupport		DECIMAL(15,2);
	l_S0BPTKP		DECIMAL(15,2);
	l_S0BIncomeTaxCurrent	DECIMAL(15,2);
        l_S0BIncomeTaxAPaid	DECIMAL(15,2);
	l_S0BTaxableIncCurrent	DECIMAL(15,2);
	l_S0BTaxableIncPYTD	DECIMAL(15,2);
	l_S0BBonusCurrent	DECIMAL(15,2);
        l_S0BBonusPYTD		DECIMAL(15,2);
	l_S0BPjBnsCurrent	DECIMAL(15,2);
	l_S0BPjBnsPYTD		DECIMAL(15,2);
	l_Abis_THR		INT;
	l_TglPosting		DATE;
	l_FlagTHR		VARCHAR(1);
	l_IncYTD_Col2		DECIMAL(15,2);
	l_IncYTD_Fix_Income	DECIMAL(15,2);
        l_IncYTD_Col3_6		DECIMAL(15,2);
	l_IncYTD_Col8		DECIMAL(15,2);	
	l_YTD_OccSp		DECIMAL(15,2);	
	l_YTD_Astek		DECIMAL(15,2);
	l_S03NIP		VARCHAR(10);
	l_S03FixIncomePYTD	DECIMAL(15,2);
	l_S03FixIncome		DECIMAL(15,2);
	l_S03Col2YTD		DECIMAL(15,2);
	l_S03Col3YTD		DECIMAL(15,2);
	l_S03Col4YTD		DECIMAL(15,2);
        l_S03Col5YTD		DECIMAL(15,2);
	l_S03Col6YTD		DECIMAL(15,2);
	l_S03Col12PYTD		DECIMAL(15,2);
	l_S03Kolom12		DECIMAL(15,2);
	l_S03OccSupport1	DECIMAL(15,2);
	l_S03Col8PYTD		DECIMAL(15,2);
	l_S03Kolom8		DECIMAL(15,2);
	l_S03OccSupport2	DECIMAL(15,2);
        l_LOOP_S0B           	REFCURSOR;
        l_LOOP_S03           	REFCURSOR;
--		
BEGIN
-- Bentuk Table Temp
CREATE TEMP TABLE l_TEMP (NIP		  VARCHAR(10),
			Jns_Form	  VARCHAR(1),
			Nama		  VARCHAR(25),
			IncYTD_Fix_Income DECIMAL(12),
			IncYTD_Col2	  DECIMAL(12),
			IncYTD_Col3_6 	  DECIMAL(12),
			IncYTD_Col8	  DECIMAL(12),
			YTD_OccSp	  DECIMAL(12),
			YTD_Astek	  DECIMAL(12))  ON COMMIT DROP ;
--
-- Mulai Loop S0B
OPEN l_LOOP_S0B FOR 
SELECT  S0B.NIP,S0B.JnsPajak,M15.Nama,
	Fn_Kpusat(S0B.NIP,S0B.EncGrossIncPYTD,l_MyPass) ::DECIMAL(15,2) AS GrossIncPYTD,
	Fn_Kpusat(S0B.NIP,S0B.EncGrossIncCurrent,l_MyPass) ::DECIMAL(15,2) AS GrossIncCurrent,
        Fn_Kpusat(S0B.NIP,S0B.EncVariPYTD,l_MyPass) ::DECIMAL(15,2) AS VariPYTD,
        Fn_Kpusat(S0B.NIP,S0B.EncVariCurrent,l_MyPass) ::DECIMAL(15,2) AS VariCurrent,
	Fn_Kpusat(S0B.NIP,S0B.EncOccSupport,l_MyPass) ::DECIMAL(15,2) AS OccSupport,
	Fn_Kpusat(S0B.NIP,S0B.EncPTKP,l_MyPass) ::DECIMAL(15,2) AS PTKP,
	Fn_Kpusat(S0B.NIP,S0B.EncIncomeTaxCurrent,l_MyPass) ::DECIMAL(15,2) AS IncomeTaxCurrent,
        Fn_Kpusat(S0B.NIP,S0B.EncIncomeTaxAPaid,l_MyPass) ::DECIMAL(15,2) AS IncomeTaxAPaid,
	Fn_Kpusat(S0B.NIP,S0B.EncTaxableIncCurrent,l_MyPass) ::DECIMAL(15,2) AS TaxableIncCurrent,
	Fn_Kpusat(S0B.NIP,S0B.EncTaxableIncPYTD,l_MyPass) ::DECIMAL(15,2) AS TaxableIncPYTD,
	Fn_Kpusat(S0B.NIP,S0B.EncBonusCurrent,l_MyPass) ::DECIMAL(15,2) AS BonusCurrent,
        Fn_Kpusat(S0B.NIP,S0B.EncBonusPYTD,l_MyPass) ::DECIMAL(15,2) AS BonusPYTD,
	Fn_Kpusat(S0B.NIP,S0B.EncPjBnsCurrent,l_MyPass) ::DECIMAL(15,2) AS PjBnsCurrent,
	Fn_Kpusat(S0B.NIP,S0B.EncPjBnsPYTD,l_MyPass) ::DECIMAL(15,2) AS PjBnsPYTD
FROM S0BLSTX S0B
INNER JOIN M15PEGA M15
ON S0B.NIP=M15.NIP
INNER JOIN 
(
SELECT * FROM fn_SECLOGIN(l_Usr_Id)
) VSL 
ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
--
WHERE EXTRACT(MONTH FROM S0B.TglPayr)=EXTRACT(MONTH FROM l_Periode) AND EXTRACT(YEAR FROM S0B.TglPayr)=EXTRACT(YEAR FROM l_Periode) AND
      S0B.NIP BETWEEN l_NIPFr AND l_NIPTo AND
      --M15.KdCaba BETWEEN l_FCab AND l_TCab AND
      M15.KdCaba=l_Cabang ;
<<l_LOOP_S0B>> 
LOOP 
FETCH l_LOOP_S0B
INTO   l_S0BNIP,l_S0BJnsPajak,l_M15Nama,l_S0BGrossIncPYTD,l_S0BGrossIncCurrent,
       l_S0BVariPYTD,l_S0BVariCurrent,l_S0BOccSupport,l_S0BPTKP,l_S0BIncomeTaxCurrent,
       l_S0BIncomeTaxAPaid,l_S0BTaxableIncCurrent,l_S0BTaxableIncPYTD,l_S0BBonusCurrent,
       l_S0BBonusPYTD,l_S0BPjBnsCurrent,l_S0BPjBnsPYTD;

    IF NOT FOUND THEN
	EXIT ;
    END IF;

    l_Abis_THR:=0;
    --
    SELECT TglPosting,
	   FlagTHR
    INTO   l_TglPosting,
	   l_FlagTHR
    FROM S05PSTD
    WHERE NIP=l_S0BNIP 
    ORDER BY NIP,TglPosting;
    --
    IF l_TglPosting <= DATE_TRUNC('month',l_Periode) + interval '1 month - 1 day' AND l_FlagTHR='*' THEN 
       l_Abis_THR:=1;
    END IF;    
    --
    IF l_Abis_THR>0 THEN 
       l_IncYTD_Col2:=l_S0BTaxableIncCurrent;
    ELSE
       l_IncYTD_Col2:=l_S0BTaxableIncCurrent+l_S0BBonusCurrent;
    END IF;    
    --*   
    l_IncYTD_Fix_Income	:= l_S0BGrossIncCurrent+l_S0BBonusCurrent-l_S0BOccSupport-l_S0BPTKP;
    l_IncYTD_Col2	:= l_IncYTD_Col2;
    l_IncYTD_Col3_6	:= l_S0BIncomeTaxCurrent;--+l_S0BPjBnsCurrent;
    l_IncYTD_Col8	:= l_S0BGrossIncPYTD+l_S0BGrossIncCurrent+l_S0BBonusPYTD+l_S0BBonusCurrent-l_S0BOccSupport-l_S0BPTKP;
    l_YTD_OccSp		:= l_IncYTD_Col2+l_S0BTaxableIncPYTD;
    l_YTD_Astek		:= l_S0BIncomeTaxAPaid+l_S0BIncomeTaxCurrent;--+l_S0BPjBnsCurrent
    --
    IF (SELECT COUNT(NIP) FROM l_TEMP WHERE NIP=l_S0BNIP AND Jns_Form=l_S0BJnsPajak) > 0 THEN 
         UPDATE l_TEMP
	 SET IncYTD_Fix_Income=COALESCE(IncYTD_Fix_Income,0)+l_IncYTD_Fix_Income,
             IncYTD_Col2      =COALESCE(IncYTD_Col2,0)+l_IncYTD_Col2,
	     IncYTD_Col3_6    =COALESCE(IncYTD_Col3_6,0)+l_IncYTD_Col3_6,
   	     IncYTD_Col8      =COALESCE(IncYTD_Col8,0)+l_IncYTD_Col8,
	     YTD_OccSp        =COALESCE(YTD_OccSp,0)+l_YTD_OccSp,
             YTD_Astek        =COALESCE(YTD_Astek,0)+l_YTD_Astek
         WHERE NIP=l_S0BNIP AND Jns_Form=l_S0BJnsPajak; 
    ELSE
         INSERT INTO l_TEMP(NIP  ,Jns_Form  ,Nama  ,IncYTD_Fix_Income  ,IncYTD_Col2  ,IncYTD_Col3_6  ,IncYTD_Col8  ,YTD_OccSp  ,YTD_Astek)
    		              VALUES(l_S0BNIP,l_S0BJnsPajak,l_M15Nama,l_IncYTD_Fix_Income,l_IncYTD_Col2,l_IncYTD_Col3_6,l_IncYTD_Col8,l_YTD_OccSp,l_YTD_Astek);
    END IF; 
    --*
--*
END LOOP;
CLOSE l_LOOP_S0B;
--
-- Isi data dari S05
OPEN l_LOOP_S03 FOR 
SELECT  S03.NIP,
	Fn_Kpusat(S03.NIP,S03.EncFixIncomePYTD,l_MyPass) ::DECIMAL(15,2) AS FixIncomePYTD, 
	Fn_Kpusat(S03.NIP,S03.EncFixIncome,l_MyPass) ::DECIMAL(15,2) AS FixIncome, 
	Fn_Kpusat(S03.NIP,S03.EncCol2YTD,l_MyPass) ::DECIMAL(15,2) AS Col2YTD, 
	Fn_Kpusat(S03.NIP,S03.EncCol3YTD,l_MyPass) ::DECIMAL(15,2) AS Col3YTD, 
	Fn_Kpusat(S03.NIP,S03.EncCol4YTD,l_MyPass) ::DECIMAL(15,2) AS Col4YTD, 
        Fn_Kpusat(S03.NIP,S03.EncCol5YTD,l_MyPass) ::DECIMAL(15,2) AS Col5YTD, 
	Fn_Kpusat(S03.NIP,S03.EncCol6YTD,l_MyPass) ::DECIMAL(15,2) AS Col6YTD, 
	Fn_Kpusat(S03.NIP,S03.EncCol12PYTD,l_MyPass) ::DECIMAL(15,2) AS Col12PYTD, 
	Fn_Kpusat(S03.NIP,S03.EncKolom12,l_MyPass) ::DECIMAL(15,2) AS Kolom12, 
	Fn_Kpusat(S03.NIP,S03.EncOccSupport1,l_MyPass) ::DECIMAL(15,2) AS OccSupport1, 
	Fn_Kpusat(S03.NIP,S03.EncCol8PYTD,l_MyPass) ::DECIMAL(15,2) AS Col8PYTD, 
        Fn_Kpusat(S03.NIP,S03.EncKolom8,l_MyPass) ::DECIMAL(15,2) AS Kolom8, 
	Fn_Kpusat(S03.NIP,S03.EncOccSupport2,l_MyPass) ::DECIMAL(15,2) AS OccSupport2, 
	M15.Nama
FROM S03LTAX S03
INNER JOIN M15PEGA M15
ON S03.NIP=M15.NIP
--INNER JOIN 
--(
--SELECT * FROM fn_SECLOGIN(l_Usr_Id)
--) VSL 
--ON M15.Kdglng=VSL.GOL_KODE AND M15.Kdcaba=VSL.CAB_KODE
WHERE EXTRACT(MONTH FROM S03.TglPayr)=EXTRACT(MONTH FROM l_Periode) AND 
      EXTRACT(YEAR FROM S03.TglPayr)=EXTRACT(YEAR FROM l_Periode) AND
      S03.NIP BETWEEN l_NIPFr AND l_NIPTo AND S03.KdCaba=l_Cabang;
<<l_LOOP_S03>> 
LOOP 
FETCH l_LOOP_S03
INTO l_S03NIP,l_S03FixIncomePYTD,l_S03FixIncome,l_S03Col2YTD,l_S03Col3YTD,l_S03Col4YTD,
     l_S03Col5YTD,l_S03Col6YTD,l_S03Col12PYTD,l_S03Kolom12,l_S03OccSupport1,l_S03Col8PYTD,
     l_S03Kolom8,l_S03OccSupport2,l_M15Nama;
     
     IF NOT FOUND THEN
	EXIT ;
     END IF;
     --
     l_IncYTD_Fix_Income:=l_S03FixIncomePYTD + l_S03FixIncome;
     l_IncYTD_Col2      :=l_S03Col2YTD;
     l_IncYTD_Col3_6    :=l_S03Col3YTD + l_S03Col4YTD + l_S03Col5YTD + l_S03Col6YTD;
     l_IncYTD_Col8      :=l_S03Col8PYTD + l_S03Kolom8;
     l_YTD_OccSp        :=l_S03OccSupport1 + l_S03OccSupport2;
     l_YTD_Astek        :=l_S03Col12PYTD + l_S03Kolom12;
     --
     IF (SELECT COUNT(NIP) FROM  l_TEMP WHERE NIP=l_S03NIP) = 0 THEN 
           INSERT INTO l_TEMP(NIP  ,Jns_Form  ,Nama  ,IncYTD_Fix_Income  ,IncYTD_Col2  ,IncYTD_Col3_6  ,IncYTD_Col8  ,YTD_OccSp  ,YTD_Astek)        
   	               VALUES(l_S03NIP,' ',l_M15Nama,l_IncYTD_Fix_Income,l_IncYTD_Col2,l_IncYTD_Col3_6,l_IncYTD_Col8,l_YTD_OccSp,l_YTD_Astek);
     END IF; 
     --*'
     --
END LOOP;
CLOSE l_LOOP_S03;
--
RETURN QUERY 
SELECT * FROM l_TEMP; 
--
END;
$$ LANGUAGE plpgsql ;
/******* END OF PROC *****/
/*
SELECT * FROM R_PPHNP1 ('2013-12-31'::DATE,' ','Z','ABCD','Copyright, 1988 (c) Microsoft Corporation, All rights reserved',1);
SELECT * FROM S0BLSTX
SELECT KDCABA FROM M15PEGA WHERE NIP ='U'
*/
