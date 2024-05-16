-- Function: l_m41jpjk(character varying, character varying)

-- DROP FUNCTION l_m41jpjk(character varying, character varying);


CREATE OR REPLACE FUNCTION l_m41jpjk(IN l_kodeFr character varying, IN l_kodeTo character varying)
  RETURNS TABLE(OutKode character, 
                OutKeterangan character varying, 
                OutSingkatan character varying,
                Outbiayajabatan text, 
                OutPtkp text, 
                OutPersenpj1 numeric,
                OutPersenpj2 numeric, 
                OutPersenpj3 numeric, 
                OutPersenpj4 numeric,
                OutPersenpj5 numeric, 
                OutPersenpj6 numeric, 
                OutPersenpj7 numeric,
                OutLimitpj1 numeric, 
                OutLimitpj2 numeric, 
                OutLimitpj3 numeric,
                OutLimitpj4 numeric, 
                OutLimitpj5 numeric, 
                OutLimitpj6 numeric,
                OutLimitpj7 numeric, 
                Outjnsform1721 character varying, 
                OutFlagKhusus text,
                OutPersenpkp numeric, 
                OutFinal text, 
		        OutVersion integer,
		        OutCreated_by character varying,
		        OutCreated_on timestamp without time zone,
		        OutUpdated_by character varying,
		        OutUpdated_on timestamp without time zone) AS
$BODY$ 
BEGIN
	RETURN QUERY 
	SELECT	m41.kode as OutKode, m41.keterangan as OutKeterangan, m41.singkatan as OutSingkatan,
            (case when m41.biayajabatan=1 then 'Ya' else 'Tidak' end) as Outbiayajabatan, 
	    (case when m41.ptkp=1 then 'Ya' else 'Tidak' end) as OutPtkp, 
		m41.persenpj1 as OutPersenpj1,
            m41.persenpj2 as OutPersenpj2, m41.persenpj3 as OutPersenpj3, m41.persenpj4 as OutPersenpj4,
            m41.persenpj5 as OutPersenpj5, m41.persenpj6 as OutPersenpj6, m41.persenpj7 as OutPersenpj7,
            m41.limitpj1 as OutLimitpj1, m41.limitpj2 as OutLimitpj2, m41.limitpj3 as OutLimitpj3,
            m41.limitpj4 as OutLimitpj4, m41.limitpj5 as OutLimitpj5, m41.limitpj6 as OutLimitpj6,
            m41.limitpj7 as OutLimitpj7, m41.jnsform1721 as Outjnsform1721, 
	    (case when m41.flagkhusus=1 then 'Ya' else 'Tidak' end) as OutFlagKhusus,
            m41.persenpkp as OutPersenpkp, 
	    (case when m41.final=1 then 'Ya' else 'Tidak' end) as OutFinal, 
            m41.version as OutVersion, m41.created_by as OutCreated_by,
		    m41.created_on as OutCreated_on, m41.updated_by as OutUpdated_by, m41.updated_on as OutUpdated_on
	FROM m41jpjk m41
	WHERE (m41.kode BETWEEN l_kodeFr AND l_kodeTo); 
END ; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

/*
select * from m41jpjk

select * from l_m41jpjk('','zzz')
*/
