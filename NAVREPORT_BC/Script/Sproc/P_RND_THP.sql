/****************************************
Name sprocs	: P_RND_THP
Create by	: PEGGY 
Date		: 02-04-2008
Description	: Proses Perhitungan Pembulatan THP CORE  
Call From	: P_PAYRP1
*****************************************/
DROP FUNCTION P_RND_THP(l_m15NIP        VARCHAR(10),
		     l_TglProses  	DATE,
		     l_FZ2THPROUND	VARCHAR(1),
		     l_UserId		VARCHAR(12),
		     l_MyPass     	VARCHAR(128), 
		     l_S01_ID	        INT);
--
CREATE FUNCTION P_RND_THP(l_m15NIP      VARCHAR(10),
		     l_TglProses  	DATE,
		     l_FZ2THPROUND	VARCHAR(1),
		     l_UserId		VARCHAR(12),
		     l_MyPass     	VARCHAR(128), 
		     l_S01_ID	        INT)
RETURNS VOID 
AS $$
--
DECLARE l_EncTotInc  	DECIMAL(15,2);
    	l_EncTotDed  	DECIMAL(15,2);
    	l_EncTakeHomePay DECIMAL(15,2);
	l_NilRND	DECIMAL(15,0);
	l_NilPRTHP	DECIMAL(15,0);
	l_NilPRTHPVAL	DECIMAL(15,0);
	l_V_Round    	INT; 
	l_M03KdDppt     VARCHAR(4);          l_M03Singkatan   	VARCHAR(10);
        l_M03UsComp     VARCHAR(1);          l_M03Kolom   	VARCHAR(2);
        l_M03NoAcc      VARCHAR(10);         l_M03Status      	VARCHAR(1);
        l_M03Persen     DECIMAL(5,2);        l_M03Nilai       	DECIMAL(15,2);
        l_M03NoLyr      INT;                 l_M03KdCurr      	VARCHAR(4);
        l_M03Pajak      VARCHAR(1);          l_M03Flag_N  	VARCHAR(1);
        l_M03Flag_R     VARCHAR(1); 	     l_M03ID            INT;
	l_tglnext 	DATE;

BEGIN 
l_EncTotInc      := 0;
l_EncTotDed      := 0;
l_EncTakeHomePay := 0;
--  
SELECT 	SUM(EncTotInc), SUM(EncTotDed)
INTO    l_EncTotInc,    l_EncTotDed 
FROM 
( 
SELECT 	CASE WHEN S02.FlgDpPt='D' THEN fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) ELSE 0 END AS EncTotInc,
	CASE WHEN S02.FlgDpPt='P' THEN fn_KPusat(S02.NIP,S02.EncNilai,l_MyPass) ::DECIMAL(15,2) ELSE 0 END AS EncTotDed   
FROM S02DGAJ S02
WHERE  TglPayr=l_TglProses AND NIP=l_M15NIP                 
--GROUP BY TglPayr,NIP
) Q;
-- 
l_EncTakeHomePay := l_EncTotInc-l_EncTotDed   ;
--
l_V_Round := CASE l_FZ2THPROUND
                  WHEN '1' THEN -1
                  WHEN '2' THEN -2
                  ELSE -3
             END;

l_NilRND := ROUND(l_ENCTAKEHOMEPAY,l_V_ROUND::INT)  ;
--SET l_NilRND=ROUND(l_ENCTAKEHOMEPAY,-1)  

l_NILPRTHP    := l_EncTakeHomePay-l_NilRND;
l_NILPRTHPVAL := l_EncTakeHomePay-l_NilRND;

IF l_NILPRTHP > 0 THEN 
BEGIN 
	SELECT fx.l_Singkatan, fx.l_UsComp, fx.l_Kolom, fx.l_NoAcc, fx.l_STS,
	  fx.l_PRS, fx.l_NL, fx.l_FLBR, fx.l_kdCurrT
	from  P_M03AllField ('P', 'PTHP' ) fx
	INTO    l_M03Singkatan, l_M03UsComp, l_M03Kolom, l_M03NoAcc, l_M03Status, 
	   l_M03Persen, l_M03NiLai, l_M03NoLyr, l_M03KdCurr ;  	      
      --
      -- Insert/Update Pembulatan THP 
      IF (SELECT NIP FROM S02DGAJ WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='PTHP') IS NOT NULL THEN 
         BEGIN
           UPDATE S02DGAJ
           SET KdCurr=l_M03KdCurr,
               EncNilaiVal =fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP,EncNilaiVal,l_MyPass) ::DECIMAL(15,2)+l_NILPRTHPVAL) ::VARCHAR(17),l_MyPass),
               EncNilai=fn_kcabang(l_M15NIP,(fn_kpusat(l_M15NIP, EncNilai, l_MyPass) :: Decimal(15,2)+l_NILPRTHP) :: VARCHAR(17), l_MyPass)
           WHERE TglPayr=l_TglProses AND FlgDpPt='P' AND COALESCE(FlgAngs,' ')=' ' AND NIP=l_M15NIP AND KdDpPt='PTHP' ; 
         END; 
      ELSE
         BEGIN
           INSERT INTO S02DGAJ(TglPayr    ,NIP     ,FlgDpPt, KdDpPt,FlgAngs,Singkatan ,    EncNilai                                                  ,Nilai,KdCurr     ,EncNilaiVal                                                 ,NilaiVal,Kolom     ,NoAcc ,    UsComp ,    NoLyr,     FlgNonGL, S01_ID)
                        VALUES(l_TglProses,l_M15NIP,'P'     ,'PTHP',' '    ,l_M03Singkatan,fn_kcabang(l_M15NIP, (l_NILPRTHP :: VARCHAR(17)),l_MyPass),0    ,l_M03KdCurr,fn_kCabang(l_M15NIP,(l_NILPRTHPVal :: VARCHAR(17)),l_MyPass),0       ,l_M03Kolom,l_M03NoAcc,l_M03UsComp,l_M03NoLyr,0       , l_S01_ID);   
         END;
      END IF; 

      l_TGLNEXT := l_TGLPROSES + INTERVAL '1 day'; 

      -- Insert/Update Pembulatan THP di Transaksi Variabel 
      IF (SELECT NIP FROM T03VARI WHERE PrdMulai=l_TGLNEXT AND PrdSd = l_TGLNEXT AND FlgDpPt='P' AND NIP=l_M15NIP AND KdDpPt='PTHP') IS NOT NULL THEN 
         BEGIN
           UPDATE T03VARI 
           SET Nilai=Nilai + l_NILPRTHP
           WHERE PrdMulai=l_TGLNEXT AND PrdSd = l_TGLNEXT AND FlgDpPt='P' AND NIP=l_M15NIP AND KdDpPt='PTHP';
         END;
      ELSE
         BEGIN
           INSERT INTO T03VARI(NIP,    FlgDpPt, KdDpPt, PrdMulai,  PrdSd  ,  Nilai   ,   KdCurr    ,  Persen, BayarDimuka, Flag, M03_ID,  VERSION, CREATED_BY, CREATED_ON,   UPDATED_BY, UPDATED_ON)
                        VALUES(l_M15NIP,'P'    ,'PTHP', l_TGLNEXT ,l_TGLNEXT,l_NILPRTHP, l_M03KdCurr, 0     , 0          , 1   , l_M03ID, 0      , l_userid,   localtimestamp,     null,       null );
         END;
      END IF;                         

END; 
END IF; 

END;
$$ LANGUAGE plpgsql ;   
