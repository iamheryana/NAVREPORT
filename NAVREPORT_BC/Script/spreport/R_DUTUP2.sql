/****************************************
Name sprocs	: P_DUTUP2
Create by	: Frans
Date		: 19-11-2007
Description	: DAFTAR UPAH dan TENAGA KERJA per UNIT USAHA
Call From	: Main Proc
Sub sprocs	: -
Updated by peggy 11 09 2008 : udah digabungin detail dan total di p1
*****************************************/
IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'P_DUTUP2' AND type = 'P')
DROP PROCEDURE P_DUTUP2
GO
-- -- CREATE PROC P_DUTUP2  (	@mdPeriode	DATETIME,
-- -- 						@mcUUsaFr	CHAR(4),
-- -- 						@mcUUsaTo	CHAR(4),
-- --                         @FCab       CHAR(4),
-- --                         @TCab      	CHAR(4),
-- --                         @FGol       CHAR(4),
-- --                         @TGol       CHAR(4),
-- -- 						@Mypass		VARCHAR(128))
-- -- WITH ENCRYPTION
-- -- AS
-- -- --
-- -- SET NOCOUNT ON
-- -- --
-- -- DECLARE @KdUUsa		CHAR(4),
-- -- 		@NmUUsa		CHAR(20),
-- -- 		@NIP		CHAR(10),
-- -- 		@KPA		CHAR(11),
-- -- 		@Nama		CHAR(25),
-- -- 		@KetJabatan	CHAR(30),
-- -- 		@JKK_Nilai	DECIMAL(15,2),
-- -- 		@JKM_Nilai	DECIMAL(15,2),
-- -- 		@GajiPokok	DECIMAL(15,2),
-- -- 		@JPK_Nilai	DECIMAL(15,2),
-- -- 		@JHT_Nilai2	DECIMAL(15,2),
-- -- 		@JHT_Nilai37	DECIMAL(15,2),
-- -- 		@Var_Round	CHAR(1),
-- -- 		@V_Round	INT,
-- -- 		@TglMasuk	DATETIME,
-- -- 		@Nilai		DECIMAL(15,2),
-- -- 		@KdDpPt		CHAR(4),
-- -- 		@JKK		CHAR(1),
-- -- 		@JKM		CHAR(1),
-- -- 		@Kolom		CHAR(2),
-- -- 		@JHT		CHAR(1),
-- -- 		@Pgwi		DECIMAL(5,2),
-- -- 		@Perush		DECIMAL(5,2),
-- -- 		@KdJaba		CHAR(4),
-- -- 		@FlgDpPt	CHAR(1),
-- -- 		@level		INT,
-- -- 		@M15Level	CHAR(1),
-- -- 		@JKK_Persen	DECIMAL(5,2),
-- -- 		@Status		CHAR(1),
-- -- 		@KetJaba	CHAR(20),
-- -- 		@M15kdUUsa	CHAR(4),
-- -- 		@LOOP_S02	CURSOR,
-- -- 		@M03Status 	char(1) 
-- -- 		
-- -- -- Table Temp
-- -- DECLARE @TEMP TABLE(
-- -- 				 	 KdUUsa		CHAR(4) COLLATE DATABASE_DEFAULT,
-- -- 				 	 NmUUsa		CHAR(20) COLLATE DATABASE_DEFAULT,
-- -- 					 NIP		CHAR(10) COLLATE DATABASE_DEFAULT,
-- -- 					 Nama		CHAR(25) COLLATE DATABASE_DEFAULT,
-- -- 					 JKK_Nilai	DECIMAL(15,2),
-- -- 					 JKM_Nilai	DECIMAL(15,2),
-- -- 					 GajiPokok	DECIMAL(15,2),
-- -- 					 JPK_Nilai	DECIMAL(15,2),
-- -- 					 JHT_Nilai2	DECIMAL(15,2),
-- -- 					 JHT_Nilai37	DECIMAL(15,2),
-- -- 					 JKK_Persen	DECIMAL(5,2))
-- -- --*
-- -- 	SET @LOOP_S02= CURSOR FOR
-- -- 	SELECT S01.KdUUsa, UnitUsaha=M02.Keterangan, S02.NIP,M15.KPA,M15.Nama,M15.TglMasuk,dbo.fn_KPusat(S02.NIP,S02.EncNilai,@MyPass),
-- -- 		S02.KdDpPt,M15.JKK,M15.JKM,S02.Kolom,M15.JHT,M15.KdJaba,M04.Keterangan,M15.Status,
-- -- 		S02.FlgDpPt,M15.[Level],M15.KdUUsa, M03.status 
-- -- 	FROM S02DGAJ S02
-- -- 	INNER JOIN S01HGAJ S01
-- -- 	ON S02.TglPayr = S01.TglPayr AND
-- -- 	   S02.NIP = S01.NIP
-- -- 	--
-- -- 	INNER JOIN M02UUSA M02
-- -- 	ON S01.KdUUsa = M02.Kode
-- -- 	--
-- -- 	INNER JOIN M15PEGA M15
-- -- 	ON S02.NIP=M15.NIP
-- -- 	--
-- -- 	INNER JOIN M04HJAB M04
-- -- 	ON M04.Kode=M15.kdjaba
-- -- 	--
-- -- 	INNER JOIN M03DPPT M03 
-- -- 	ON S02.FLGDPPT = M03.FLGDPPT AND S02.KDDPPT = M03.KDDPPT 
-- -- 	--
-- -- 	WHERE CONVERT(CHAR(6),S02.TglPayr,112)=CONVERT(CHAR(6),@mdPeriode,112) AND 
-- -- 	      M15.KdCaba BETWEEN @FCab AND @TCab AND 
-- -- 	      M15.KdGlng BETWEEN @FGol AND @TGol AND M15.KPA<>' ' AND
-- -- 		  S01.KdUUSa BETWEEN @mcUUsaFr AND @mcUUsaTo
-- -- -- PEGGY 2008 05 15 : sas pl 2 : RUMUSAN DIGANTI JADI NILAI JHT DARI S02 (2%) / (2% + 3,7%) 
-- -- 		  AND M15.TGLKPA IS NOT NULL AND 
-- -- 		  CONVERT(CHAR(6),M15.TglKPA,112)<=CONVERT(CHAR(6),@mdPeriode,112)
-- -- --
-- -- OPEN @LOOP_S02
-- -- FETCH NEXT FROM @LOOP_S02
-- -- INTO @KdUUsa, @NmUUsa, @NIP,@KPA,@Nama,@TglMasuk,@Nilai,@KdDpPt,@JKK,@JKM,@Kolom,@JHT,@KdJaba,@KetJaba,@Status,@FlgDpPt,@M15Level,@M15KdUUsa, @M03Status
-- -- --
-- -- WHILE @@FETCH_STATUS=0
-- --    BEGIN
-- --      --
-- --      -- Reset Data
-- -- 	 SELECT @JKK_Persen=0,
-- -- 			@JKK_Nilai=0,
-- -- 			@JKM_Nilai=0,
-- -- 			@JPK_Nilai=0,
-- -- 			@GajiPokok=0,
-- -- 			@JHT_Nilai2=0,			
-- -- 			@JHT_Nilai37=0
-- -- 
-- --      IF @FlgDpPt='D' AND @kdDpPt='JKK' AND @Nilai>0
-- --         BEGIN
-- --            -- Cari Nilai Level pegawai
-- -- 	       SET @level=0
-- --            --
-- --            IF @M15Level>0
-- --               BEGIN
-- --                  SET @Level=CONVERT(INT,@M15Level)
-- -- 	      	  END	
-- --            ELSE
-- --               BEGIN 
-- --                 SELECT @Level=CONVERT(INT,Kelompok)
-- -- 	            FROM M02UUSA
-- --  	            WHERE Kode=@M15kdUUsa	        
-- --                 --
-- -- 	            IF @Level IS NULL
-- -- 		          SET @Level=0
-- --                 --*            
-- --               END            
-- --               -- Cari Nilai JKK-Persen
-- --               SET @JKK_Persen=0
-- -- 	          SELECT @JKK_Persen=Perush
-- -- 	          FROM M18JAMS
-- --               WHERE Progrm='JKK' AND LevelJKK=@Level
-- -- 	          --
-- -- 	          IF @JKK_Persen IS NULL
-- -- 	            SET @JKK_Persen=0		
-- -- 	          --	
-- --           --*
-- --         END
-- --      --*
-- --      --
-- --      IF @kdDpPt='JKK' AND @FlgDpPt='D'
-- --         BEGIN
-- --            SET @JKK_Nilai=@Nilai
-- --         END
-- --      ELSE IF @kdDpPt='JKM' AND @FlgDpPt='D'
-- --         BEGIN
-- --           SET @JKM_Nilai=@Nilai   
-- --         END
-- --      ELSE IF @kdDpPt='JPK' AND @FlgDpPt='D'
-- --         BEGIN
-- --           SET @JPK_Nilai=@Nilai   
-- --         END
-- -- -- PEGGY 2008 05 15 : sas pl 2 : RUMUSAN DIGANTI JADI NILAI JHT DARI S02 (2%) / (2% + 3,7%) 
-- --      ELSE IF @kdDpPt='JHT' AND @FlgDpPt='D'
-- --         BEGIN
-- -- 	        IF EXISTS (SELECT Progrm FROM M18JAMS WHERE Progrm='JHT') 
-- -- 	           BEGIN
-- -- 	             IF @JHT='Y'
-- -- 	                BEGIN
-- -- 		              SELECT @Pgwi=Pgwi,
-- -- 			                 @Perush=Perush
-- -- 		              FROM M18JAMS 
-- -- 	                  WHERE Progrm='JHT' 	
-- -- 		              --
-- -- 					  SELECT @JHT_Nilai2=ABS(@Nilai), 
-- -- 							 @JHT_Nilai37=dbo.fn_VRound((ABS(@Nilai)*(@Pgwi+@Perush))/@Pgwi) - ABS(@Nilai) -- NILAI 5.7% - NILAI 2%
-- -- 		           END  
-- -- 	             --*
-- --               END
-- --            --*
-- --         END
-- --      ELSE IF @kdDpPt NOT IN ('JKK','JKM','JHT','JPK') AND @FlgDpPt='D' AND @M03Status='1'
-- --       BEGIN
-- --         SET @GajiPokok=@Nilai
-- -- 	  END 
-- -- -- --      ELSE IF @kdDpPt NOT IN ('JKK','JKM') AND @FlgDpPt='D'
-- -- -- --         BEGIN
-- -- -- -- -- BY PEGGY 2006 12 18 : PENDAPATAN TETAP DASAR M03.STATUS DAN BUKAN KOLOM 1 LAGI 
-- -- -- --            IF @M03Status='1'
-- -- -- -- --           IF @Kolom='1'
-- -- -- --               BEGIN
-- -- -- -- 		        SET @GajiPokok=@Nilai
-- -- -- --                 --
-- -- -- --                 IF EXISTS (SELECT Progrm FROM M18JAMS WHERE Progrm='JHT')
-- -- -- --                    BEGIN
-- -- -- --                      IF @JHT='Y'
-- -- -- --                         BEGIN
-- -- -- -- 			              SELECT @Pgwi=Pgwi,
-- -- -- -- 				                 @Perush=Perush
-- -- -- -- 			              FROM M18JAMS 
-- -- -- --                           WHERE Progrm='JHT' 	
-- -- -- -- 			              --
-- -- -- --                           --SELECT @JHT_Nilai=dbo.fn_VRound((@GajiPokok*(@Pgwi+@Perush))/100)
-- -- -- -- 							SELECT @JHT_Nilai2=dbo.fn_VRound((@GajiPokok*@Pgwi)/100)
-- -- -- -- 							SELECT @JHT_Nilai37=dbo.fn_VRound(@JHT_Nilai2/2*(3.7))
-- -- -- -- 			              --
-- -- -- -- 			           END  
-- -- -- --                      --*
-- -- -- --                    END
-- -- -- --                 --*
-- -- -- --                 --
-- -- -- --               END
-- -- -- --            --*
-- -- -- --         END
-- -- -- --      --*
-- --      -- Isi data ke W05
-- --      IF EXISTS(SELECT NIP FROM @TEMP WHERE NIP=@NIP)
-- --         BEGIN
-- --            UPDATE @TEMP
-- --            SET 	JKK_Nilai=ISNULL(JKK_Nilai,0)+ISNULL(@JKK_Nilai,0),
-- -- 	       		JKM_Nilai=ISNULL(JKM_Nilai,0)+ISNULL(@JKM_Nilai,0),
-- -- 	       		GajiPokok=ISNULL(GajiPokok,0)+ISNULL(@GajiPokok,0),
-- -- 	       		JPK_Nilai=ISNULL(JPK_Nilai,0)+ISNULL(@JPK_nilai,0),
-- -- 	       		JHT_Nilai2=ISNULL(JHT_Nilai2,0)+ISNULL(@JHT_Nilai2,0),
-- -- 	       		JHT_Nilai37=ISNULL(JHT_Nilai37,0)+ISNULL(@JHT_Nilai37,0),
-- -- 	       		JKK_Persen=CASE WHEN @FlgDpPt='D' AND @kdDpPt='JKK' AND @Nilai>0 THEN 
-- -- 							  ISNULL(@JKK_Persen,0)
-- -- 						   ELSE 
-- -- 							  JKK_Persen 
-- -- 						   END
-- --           WHERE NIP=@NIP
-- --         END
-- --      ELSE
-- --         BEGIN
-- --            INSERT INTO @TEMP(KdUUsa, NmUUsa, NIP ,Nama ,JKK_Nilai ,JKM_Nilai ,GajiPokok ,JPK_Nilai ,JHT_Nilai2, JHT_Nilai37, JKK_Persen)
-- -- 	              VALUES(@KdUUsa, @NmUUsa, @NIP,@Nama,@JKK_Nilai,@JKM_Nilai,@GajiPokok,@JPK_nilai,@JHT_Nilai2,@JHT_Nilai37,@JKK_Persen)
-- --         END
-- --      --*
-- --      -- 
-- --      FETCH NEXT FROM @LOOP_S02
-- --      INTO  @KdUUsa, @NmUUsa, @NIP,@KPA,@Nama,@TglMasuk,@Nilai,@KdDpPt,@JKK,@JKM,@Kolom,@JHT,@KdJaba,@KetJaba,@Status,@FlgDpPt,@M15Level,@M15KdUUsa, @M03Status
-- --      --   
-- --    END
-- -- --*
-- -- --
-- -- CLOSE @LOOP_S02
-- -- DEALLOCATE @LOOP_S02
-- -- --
-- -- SELECT KdUUsa, NmUUsa, 
-- -- 	   JumlahKaryawan = COUNT(NIP),
-- -- 	   Pendapatan = SUM(GajiPokok),
-- -- 	   JKK_Nilai = SUM(JKK_Nilai),
-- -- 	   JKM_Nilai = SUM(JKM_Nilai),
-- -- 	   JPK_Nilai = SUM(JPK_Nilai),		   
-- -- 	   JHT_Nilai37 = SUM(JHT_Nilai37),
-- -- 	   JHT_Nilai2 = SUM(JHT_Nilai2)
-- -- FROM @TEMP
-- -- GROUP BY KdUUsa, NmUUsa

/******* END OF PROC *****/

GO
/*
EXEC P_DUTUP2  '2006-01-24', 
               ' ',
               'ZZZZ',
               ' ',
               'ZZZZ',
               ' ',
               'ZZZZ',
	           'Copyright, 1988 (c) Microsoft Corporation, All rights reserved'
*/




