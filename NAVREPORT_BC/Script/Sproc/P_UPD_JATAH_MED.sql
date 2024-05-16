CREATE FUNCTION P_Upd_Jatah_Med(l_NIP     VARCHAR(10),
     l_Periode DATE,
     l_userID  VARCHAR(12))
RETURNS void AS $$  

DECLARE 
	l_JatahBulanan 	DECIMAL(15,2);
    l_SifatJatah   	VARCHAR(1);
	l_JatahMedical 	DECIMAL(15,2);
	l_NIP1			VARCHAR(10);
		
BEGIN
	l_JatahBulanan:=0;
	l_SifatJatah:=' ';
	l_JatahMedical:=0;
	
	SELECT NIP INTO l_NIP1 FROM T14JTHM WHERE NIP=l_NIP AND Tahun<=EXTRACT (YEAR FROM l_Periode);
	IF l_NIP1 is not null THEN
		SELECT JatahBulanan,SifatJatah,JatahMedical INTO l_JatahBulanan,l_SifatJatah,l_JatahMedical FROM T14JTHM WHERE NIP=l_NIP AND Tahun<=EXTRACT (YEAR FROM l_Periode);
		IF EXTRACT (MONTH FROM Periode)=12 THEN
			IF l_SifatJatah='B' THEN
				INSERT INTO T14JTHM(NIP, Tahun,JatahMedical,Pemakaian,SifatJatah,JatahBulanan,Keterangan,UserId, UpdDate,UpdTime,Ws) VALUES (l_NIP,EXTRACT (YEAR FROM l_Periode)+1,l_JatahBulanan,0,l_SifatJatah,l_JatahBulanan,' ', l_UserID,TO_CHAR(NOW(),'yyyy-mm-dd') ,TO_CHAR(NOW(),'yyyy-mm-dd'),' ') ;
			ELSE
				INSERT INTO T14JTHM(NIP, Tahun,JatahMedical,Pemakaian,SifatJatah,JatahBulanan,Keterangan,UserId, UpdDate,UpdTime,Ws) VALUES (l_NIP,EXTRACT (YEAR FROM l_Periode)+1,l_JatahMedical,0,l_SifatJatah,0,' ', l_UserID,TO_CHAR(NOW(),'yyyy-mm-dd') ,TO_CHAR(NOW(),'yyyy-mm-dd'),' ') ;
			END IF;
		ELSE
			IF l_SifatJatah='B' THEN
				UPDATE T14JTHM SET JatahMedical=COALESCE(JatahMedical,0)+l_JatahBulanan WHERE NIP=l_NIP AND Tahun=EXTRACT (YEAR FROM l_Periode);
			END IF;
		END IF;
	END IF;
END ;
$$
LANGUAGE 'plpgsql'; 
