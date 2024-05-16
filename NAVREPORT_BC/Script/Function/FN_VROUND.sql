
CREATE OR REPLACE FUNCTION public.fn_vround (in l_nilai numeric) RETURNS numeric AS
$BODY$

DECLARE l_NilRND		DECIMAL(15,0);
	l_Round		INT;
	l_FZ1VarRound	CHAR(1);

BEGIN 
  --
  SELECT VarRound
  INTO l_FZ1VarRound
  FROM FZ1FLDA;
  --
  l_Round := CASE l_FZ1VarRound 
                     WHEN '1' THEN 1
                     WHEN '2' THEN -1
                     WHEN '3' THEN -2
                     WHEN '4' THEN -3
                END;  
  --
  l_NilRND := ROUND(l_Nilai,l_Round); 
  --
  RETURN l_NilRND; 
  -- 
END; 
$BODY$
LANGUAGE 'plpgsql'
GO
