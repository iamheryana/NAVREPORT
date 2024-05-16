/****************************************
Name sprocs	: P_CLSTEMP
Create by	: Herz
Date		: 04-02-2002
Description	: Isi Data ke File Valdasi
Call From	: P_CLSVLD
*****************************************/
DROP FUNCTION P_CLSTEMP(l_TXXNIP		VARCHAR(10),
				      l_TXXNama		VARCHAR(25),
				      l_TXXTanggal	DATE,
				      l_TXXErrRid	VARCHAR(2),
				      l_TableName1       VARCHAR(8));
--
CREATE FUNCTION P_CLSTEMP(l_TXXNIP		VARCHAR(10),
				      l_TXXNama		VARCHAR(25),
				      l_TXXTanggal	DATE,
				      l_TXXErrRid	VARCHAR(2),
				      l_TableName1       VARCHAR(8))
				      RETURNS VOID 
--
AS $$
--
BEGIN 
IF l_TXXErrRid='10' THEN 
   l_TXXErrRid := 'A';
ELSIF l_TXXErrRid='11' THEN  
   l_TXXErrRid := 'B';
ELSIF l_TXXErrRid='12' THEN 
   l_TXXErrRid := 'C';
ELSIF l_TXXErrRid='13' THEN 
   l_TXXErrRid := 'D';
END IF ;    
-- 
EXECUTE 'INSERT INTO '||l_TableName1||' (NIP, Nama, Tanggal, ErrRid) SELECT $1,$2,$3,$4 WHERE NOT EXISTS(SELECT NIP FROM '|| l_TableName1 || ' WHERE NIP=$1); '
   USING l_TXXNIP, l_TXXNama, l_TXXTanggal, l_TXXErrRid;
--PRINT l_LineTxt
END ; 
$$ LANGUAGE plpgsql ; 
