CREATE SEQUENCE sec_usercabang_ucab_id_seq;
GO
--
CREATE SEQUENCE sec_sec_usergolg_ugol_id_seq;
GO
--
CREATE TABLE sec_usercabang  ( 
	ucab_id	int4 NOT NULL DEFAULT nextval('sec_usercabang_ucab_id_seq'::regclass),
	usr_id 	int4 NOT NULL,
	m08_id 	int4 NOT NULL,
	version	int4 NOT NULL DEFAULT 0,
	PRIMARY KEY(ucab_id)
) ;
GO
--
CREATE TABLE public.sec_usergolg  ( 
	ugol_id	int4 NOT NULL DEFAULT nextval('sec_sec_usergolg_ugol_id_seq'::regclass),
	usr_id 	int4 NOT NULL,
	m12_id 	int4 NOT NULL,
	version	int4 NOT NULL DEFAULT 0,
	PRIMARY KEY(ugol_id)
) ;
GO
--
