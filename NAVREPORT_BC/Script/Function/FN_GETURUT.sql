/****************************************
Name sprocs	: fn_GetUrut
Create by	: Suhe
Date		: 27-02-2003
Description	: Untuk Dapat No,Urut Formatur Gaji
Call From	: Main sprocs
Sub sprocs	: 
*****************************************/
DROP FUNCTION fn_GetUrut(l_FlgDpPt	VARCHAR(1),
			    l_NomFormat	VARCHAR(4));

CREATE  FUNCTION fn_GetUrut(l_FlgDpPt	VARCHAR(1),
			    l_NomFormat	VARCHAR(4))
RETURNS Int
AS $$

DECLARE l_NoUrut  	INT;
	l_LOOP_M19	REFCURSOR;
	l_M19NomFormat	VARCHAR(4);	

BEGIN
   --
	l_NoUrut := 0;

	OPEN l_LOOP_M19 FOR 
	SELECT NomFormat
	FROM M19HSLG
	WHERE TipeLap='2' AND FlgDpPt=l_FlgDpPt
	ORDER BY TipeLap,FlgDpPt,NomFormat;
	--
	<<l_LOOP_M19>> 
	LOOP 
	FETCH l_LOOP_M19 
	INTO l_M19NomFormat;

		IF NOT FOUND THEN
			EXIT ;
		END IF;
  
		-- 
		l_NoUrut := l_NoUrut+1;
		--
		IF l_M19NomFormat=l_NomFormat THEN 
			EXIT;
		END IF; 
		--*
	END LOOP;
	CLOSE l_LOOP_M19;
	--     
	RETURN l_NoUrut;
END;
$$ LANGUAGE plpgsql ;

/*
select * from M19hslg 
WHERE TipeLap='2' 
	
SELECT * FROM fn_GetUrut('D','1')
*/
