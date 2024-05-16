USE [NavBI]
GO

/****** Object:  StoredProcedure [dbo].[P_WEBINAR_FEEDBACK]    Script Date: 12-10-2020 2:54:44 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO







CREATE PROCEDURE [dbo].[P_WEBINAR_FEEDBACK] 
	@ProsesId  nvarchar(100) ,   
	@WebinarId  nvarchar(50) , 
	@UserId  nvarchar(15) 
AS   
BEGIN
	DECLARE @T05Id bigint;
	DECLARE @T08Id bigint;
	DECLARE @FeedbackWebinarSudahAda int;
	-- 
	SET @T05Id =  ISNULL( (	SELECT MAX([T05_ID]) 
							FROM [dbo].[T05_WEBINAR_EVENT] 
							WHERE [WEBINAR_ID] = @WebinarId) ,0);
	IF @T05Id = 0 
		BEGIN
			DELETE FROM [dbo].[TEMP03_WEBINAR_FEEDBACK] WHERE [PROSES_ID] = @ProsesId;
			--
			INSERT INTO  [dbo].[TEMP00_UPLOAD_RESULT] ([PROSES_ID], [RESULT_STRING], [PROCESS_ON]) VALUES (@ProsesId, 'WEBINAR ID BELUM DI UPLOAD', GETDATE());
			RETURN  ;
		END
	ELSE
		BEGIN
			SET @FeedbackWebinarSudahAda = ISNULL((
														SELECT CASE WHEN COUNT(T07.T07_ID) >= 1  THEN 1 ELSE 0 END
														FROM [dbo].[T07_FEEDBACK_QST] T07
														WHERE T07.T05_ID = @T05Id
													), 0);
			IF @FeedbackWebinarSudahAda = 1
				BEGIN
					DELETE FROM [dbo].[TEMP03_WEBINAR_FEEDBACK] WHERE [PROSES_ID] = @ProsesId;
					--
					INSERT INTO  [dbo].[TEMP00_UPLOAD_RESULT] ([PROSES_ID], [RESULT_STRING], [PROCESS_ON]) VALUES (@ProsesId, 'FEEDBACK WEBINAR ID SUDAH PERNAH DI UPLOAD SEBELUMNYA', GETDATE());
					RETURN  ;
				END;
			ELSE
				BEGIN			
					DECLARE @jmlPertanyaan int =  ISNULL((
													SELECT JML_KOLOM
													FROM [dbo].TEMP03_WEBINAR_FEEDBACK
													WHERE PROSES_ID = @ProsesId AND REGISTER = '1'
												),0);
					DECLARE @iterPertanyaan int = 1;
					--Input Pertanyaan ke tabel T07_FEEDBACK_QST ----------------------------------------
					WHILE @iterPertanyaan <= @jmlPertanyaan
					BEGIN
						DECLARE    @sqlInsertQst NVARCHAR(MAX);
						SET @sqlInsertQst =
								'INSERT INTO [dbo].[T07_FEEDBACK_QST] (T05_ID, NO_QST, QUESTION, VERSION, CREATED_BY, CREATED_ON) '+
								'SELECT '+CAST(@T05Id AS NVARCHAR(200)) +' , '+CAST(@iterPertanyaan AS NVARCHAR(10))+' , KOLOM_'+RIGHT('0'+CAST(@iterPertanyaan AS NVARCHAR(2)), 2)+' , 1, '''+@UserId+''' , GETDATE() '+
								'FROM [dbo].[TEMP03_WEBINAR_FEEDBACK] '+
								'WHERE PROSES_ID = '+@ProsesId+' AND REGISTER = ''1'';';
						EXEC sp_executesql @sqlInsertQst;
						SET @iterPertanyaan = @iterPertanyaan + 1;
					END;
					-------------------------------------------------------------------------------------
					--
					--
					--Input Jawaban Feedback ke tabel T08_FEEDBACK_PERSON -------------------------------
					-- dan ke tabel T09_FEEDBACK_ANS ----------------------------------------------------
					DECLARE @JmlDataFeedbackMasuk int = 0;
					DECLARE @Temp03Id bigint,
							@WaktuIsi DATETIME,
							@FirstName NVARCHAR(300),
							@LastName NVARCHAR(300),
							@NamaLengkap NVARCHAR(300),
							@NamaPerusahaan NVARCHAR(300),
							@Jabatan NVARCHAR(300),
							@Email NVARCHAR(300),
							@NoHp NVARCHAR(300),
							@GopayNo NVARCHAR(300),
							@InvitedBy NVARCHAR(300),
							@FokusIndustri NVARCHAR(300);
					DECLARE cursorFEEDBACK CURSOR
							FOR SELECT
										TEMP03_ID, WAKTU_ISI, FIRST_NAME, LAST_NAME, NAMA_LENGKAP, NAMA_PERUSAHAAN
										,JABATAN, EMAIL, NO_HP, GOPAY_NO, INVITED_BY, FOKUS_INDUSTRI
								FROM [dbo].TEMP03_WEBINAR_FEEDBACK
								WHERE PROSES_ID = @ProsesId AND REGISTER = '2'
								ORDER BY WAKTU_ISI;
					--
					OPEN cursorFEEDBACK;
					--
					FETCH NEXT FROM cursorFEEDBACK INTO 
								@Temp03Id, @WaktuIsi , @FirstName, @LastName, @NamaLengkap , @NamaPerusahaan
								, @Jabatan , @Email , @NoHp, @GopayNo, @InvitedBy, @FokusIndustri ;
					--
					WHILE @@FETCH_STATUS = 0
						BEGIN
							DECLARE @AttendedTheEvent int = ISNULL ((
																		SELECT COUNT(T06_ID)
																		FROM dbo.T06_WEBINAR_ATTENDEE
																		WHERE T05_ID = @T05Id AND
																				--EMAIL = @Email AND
																				EMAIL_CORPORATE = @Email AND
																				ATTENDED = 'Yes'
																	),0);
							DECLARE @EmailRegistered int = ISNULL ((
																		SELECT COUNT(T06_ID)
																		FROM dbo.T06_WEBINAR_ATTENDEE
																		WHERE T05_ID = @T05Id AND
																				--EMAIL = @Email
																				EMAIL_CORPORATE = @Email
																	),0);
							-- Jika Ada data email di Attendee report dan Hadir
							IF (@AttendedTheEvent >= 1) OR (@AttendedTheEvent = 0 AND @EmailRegistered = 0)
								BEGIN
									DECLARE @sudahIsiFeedback int = ISNULL ((
																				SELECT COUNT(T08_ID)
																				FROM [dbo].[T08_FEEDBACK_PERSON] 
																				WHERE T05_ID = @T05Id AND
																						EMAIL = @Email
																			),0);
									-- Hanya di Input Jika belum pernah Isi
									IF @sudahIsiFeedback = 0
										BEGIN
											SET @T08Id = NEXT VALUE FOR [dbo].[T08_id_SEQ];
											INSERT INTO  [dbo].[T08_FEEDBACK_PERSON] 
												( [T08_ID], [T05_ID], [WAKTU_ISI], [FIRST_NAME], [LAST_NAME], [NAMA_LENGKAP], [NAMA_PERUSAHAAN], [JABATAN], [EMAIL]
													,[NO_HP], [GOPAY_NO], [INVITED_BY], [FOKUS_INDUSTRI], [REGISTERED_ON_ATTANDEE], [VERSION], [CREATED_BY], [CREATED_ON]) VALUES
												( @T08Id, @T05Id, @WaktuIsi, @FirstName, @LastName, @NamaLengkap , @NamaPerusahaan	, @Jabatan , @Email 
													, @NoHp , @GopayNo, @InvitedBy, @FokusIndustri , @AttendedTheEvent, 1 , @UserId , GETDATE());
											--
											SET @JmlDataFeedbackMasuk = @JmlDataFeedbackMasuk + 1;
											SET @iterPertanyaan = 1;
											--
											WHILE @iterPertanyaan <= @jmlPertanyaan
											BEGIN
												DECLARE @sqlInsertAsw NVARCHAR(MAX);
												SET @sqlInsertAsw = 
														N'INSERT INTO [dbo].[T09_FEEDBACK_ANS] ([T05_ID] , [T07_ID] , [T08_ID] , [ANSWER] , [VERSION] , [CREATED_BY] , [CREATED_ON] ) ' +
														'SELECT '+CAST(@T05Id AS NVARCHAR(200))+' , (SELECT MAX(T07_ID) FROM [dbo].[T07_FEEDBACK_QST] WHERE NO_QST = '+CAST(@iterPertanyaan AS NVARCHAR(2))+' AND T05_ID = '+CAST(@T05Id AS NVARCHAR(200))+' ) '+
														'		, '+CAST(@T08Id AS NVARCHAR(200))+' , KOLOM_'+RIGHT('0'+CAST(@iterPertanyaan AS NVARCHAR(2)), 2)+', 1 , '''+@UserId+''' , GETDATE() '+
														'FROM [dbo].[TEMP03_WEBINAR_FEEDBACK] '+
														'WHERE TEMP03_ID = '+CAST(@Temp03Id AS NVARCHAR(200))+' ;';
												EXEC sp_executesql @sqlInsertAsw;
												SET @iterPertanyaan = @iterPertanyaan + 1;
											END;
										END;
								END;								 
							FETCH NEXT FROM cursorFEEDBACK INTO 
									@Temp03Id, @WaktuIsi , @FirstName, @LastName, @NamaLengkap , @NamaPerusahaan
									, @Jabatan , @Email , @NoHp, @GopayNo, @InvitedBy, @FokusIndustri ;
						END;
					--
					CLOSE cursorFEEDBACK;
					--
					DEALLOCATE cursorFEEDBACK;
					-------------------------------------------------------------------------------------
					DELETE FROM [dbo].[TEMP03_WEBINAR_FEEDBACK] WHERE [PROSES_ID] = @ProsesId;
					--
					IF @JmlDataFeedbackMasuk <= 0 
						BEGIN
							DELETE FROM [dbo].[T07_FEEDBACK_QST] WHERE T05_ID = @T05Id;
							--
							INSERT INTO  [dbo].[TEMP00_UPLOAD_RESULT] ([PROSES_ID], [RESULT_STRING], [PROCESS_ON]) VALUES (@ProsesId, 'TIDAK ADA DATA FEEDBACK YANG BERHASIL DI UPLOAD', GETDATE());
							RETURN  ;
						END
					ELSE
						BEGIN
							INSERT INTO  [dbo].[TEMP00_UPLOAD_RESULT] ([PROSES_ID], [RESULT_STRING], [PROCESS_ON]) VALUES (@ProsesId, 'DATA FEEDBACK YANG BERHASIL DI UPLOAD SEBANYAK ' + CAST(@JmlDataFeedbackMasuk AS NVARCHAR(5))+ ' ORANG', GETDATE());
							RETURN  ;
						END;
				END;
			--
		END;
END;
GO


