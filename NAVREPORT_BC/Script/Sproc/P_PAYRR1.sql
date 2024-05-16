/****************************************
Name sprocs	: P_PAYRR1
Create by	: Herz
Date		: 03-06-2002
Description	: Listing Validasi Proses Bulanan Payroll
Call From	: Main Proc
Sub sprocs	: -
Note		: 
*****************************************/
IF EXISTS (SELECT name FROM   sysobjects WHERE  name = N'P_PAYRR1' AND type = 'P')
DROP PROCEDURE P_PAYRR1
GO

CREATE PROC P_PAYRR1  (@TableName1 CHAR(20),
		       @TpProses   CHAR(1))
--
WITH ENCRYPTION
AS

SET NOCOUNT ON
--
DECLARE @mcTXT CHAR(50)
--
IF @TpProses='L'
   BEGIN
      SET @mcTXT='SELECT * FROM '+@TableName1   
   END
ELSE IF @TpProses='D'
   BEGIN
      SET @mcTXT='DROP TABLE '+@TableName1   
   END
--*   
--
EXEC(@mcTxt)
GO

/*
EXEC P_PAYRR1  'TM450538','D'
*/

