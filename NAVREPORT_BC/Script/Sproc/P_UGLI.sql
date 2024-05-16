/****************************************
Name sprocs	: P_UGLI
Create by	: Wati
Date		: 19/02/2003
Description	: Proses Unmerge 
*****************************************/
DROP FUNCTION P_UGLI (l_TglFr 	DATE,
                    l_TglTo 	DATE,
                    l_Usr_Id	INT);
--
CREATE FUNCTION P_UGLI (l_TglFr 	DATE,
                    l_TglTo 	DATE,
                    l_Usr_Id	INT)
RETURNS VOID 
AS $$
--
BEGIN 
    CREATE TEMP TABLE l_TEMP (Tgl DATE, Flag VARCHAR(1)) ON COMMIT DROP ; 
    --
    -- Unpost Header Penggajian
    UPDATE S01HGAJ S01 
    SET FlagM = ' '
    FROM Z01PTGL Z01
    INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                FROM fn_SECLOGIN(l_Usr_Id)) VSL ON Z01.KdCabang=VSL.Cab_Kode
    WHERE S01.TglPayr=Z01.ApplyDate AND Z01.CaptureFlg=0 AND 
          S01.KdGlng=VSL.Gol_Kode  AND S01.KdGlng=VSL.Gol_Kode AND 
         (Z01.ApplyDate BETWEEN l_TglFr AND l_TglTo) ;
    --
    -- Hapus Data Z01PTGL
    DELETE FROM Z01PTGL 
    WHERE (ApplyDate BETWEEN l_TglFr AND l_TglTo) AND CaptureFlg=0 AND
           KdCabang IN (SELECT CAB_KODE FROM fn_SECLOGIN(l_Usr_Id));
    --
    -- Hapus Data Z03PTGL
    DELETE FROM Z03PTGL 
    WHERE (ApplyDate BETWEEN l_TglFr AND l_TglTo) AND CaptureFlg=0 AND
           KdCabang IN (SELECT CAB_KODE FROM fn_SECLOGIN(l_Usr_Id));
    --
    IF (SELECT DBASEGL FROM FZ2FLDA LIMIT 1 ) <> ' ' OR (SELECT COUNT(PROJ) FROM V14PROJ) <> 0 THEN 
        BEGIN 
            -- Unpost TIME SHEET 
            UPDATE M60TSHT M60
            SET FlagM=' '
            FROM M56NGRP M56
            INNER JOIN S01HGAJ S01 ON M56.NIP=S01.NIP 
            INNER JOIN (SELECT Usr_Id, Gol_Kode, Cab_Kode 
                        FROM fn_SECLOGIN(l_Usr_Id)) VSL ON S01.KdGlng=VSL.Gol_Kode AND S01.KdCaba=VSL.Cab_Kode
            WHERE M60.TglPayroll BETWEEN l_TglFr AND l_TglTo AND M60.TglPayroll=S01.TglPayr AND
                  M60.RID = M56.RID AND M60.KDPGRP = M56.KDPGRP;
            --      
            -- Hapus Data Z01PTGL
            DELETE FROM Z03PTGL 
            WHERE (ApplyDate BETWEEN l_TglFr AND l_TglTo) AND CaptureFlg=0 AND
                   KdCabang IN (SELECT CAB_KODE FROM fn_SECLOGIN(l_Usr_Id));
            --
        END; 
    END IF; 
    --
    -- Proses P4
    PERFORM P_Hapus_Fee_UnMerge (l_TglFr, l_TglTo); 
    --
END;
$$ LANGUAGE plpgsql ;

/* 
SELECT * FROM P_UGLI('2013-01-01'::dATE,'2013-09-01'::DATE,1); 
*/
