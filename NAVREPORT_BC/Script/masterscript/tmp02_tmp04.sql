CREATE SEQUENCE public.tmp02harian_tmp02_id_seq
GO

CREATE SEQUENCE public.tmp03lembur_tmp03_id_seq
GO

CREATE SEQUENCE public.tmp04mutasi_tmp04_id_seq
GO



CREATE TABLE public.tmp02harian  ( 
	tmp02_id       	int4 NOT NULL DEFAULT nextval('tmp02harian_tmp02_id_seq'::regclass),
	nip            	varchar(100) NULL,
	flgdppt        	varchar(100) NULL,
	kddppt         	varchar(100) NULL,
	tanggal       	varchar(100) NULL,
	hari          	varchar(100) NULL,
	bayardimuka    	varchar(100) NULL,
	flag           	varchar(100) NULL,
	status_validasi	varchar(100) NULL,
	proses_id      	varchar(100) NULL,
	version        	int4 NULL,
	created_by     	varchar(15) NULL,
	created_on     	timestamp NULL,
	updated_by     	varchar(15) NULL,
	updated_on     	timestamp NULL,
	PRIMARY KEY(tmp02_id)
)
WITHOUT OIDS 
TABLESPACE pg_default
GO

CREATE TABLE public.tmp03lembur  ( 
	tmp03_id       	int4 NOT NULL DEFAULT nextval('tmp03lembur_tmp03_id_seq'::regclass),
	tanggal       	varchar(100) NULL,
	nip            	varchar(100) NULL,
	flglembur      	varchar(100) NULL,
	refdokumen     	varchar(100) NULL,
	jamlembur      	varchar(100) NULL,
	jmluangmuka    	varchar(100) NULL,
	flag           	varchar(100) NULL,
	status_validasi	varchar(100) NULL,
	proses_id      	varchar(100) NULL,
	version        	int4 NULL,
	created_by     	varchar(15) NULL,
	created_on     	timestamp NULL,
	updated_by     	varchar(15) NULL,
	updated_on     	timestamp NULL,
	PRIMARY KEY(tmp03_id)
)
WITHOUT OIDS 
TABLESPACE pg_default
GO

CREATE TABLE public.tmp04mutasi  ( 
	tmp04_id       	int4 NOT NULL DEFAULT nextval('tmp04mutasi_tmp04_id_seq'::regclass),
	nip            	varchar(100) NULL,
	tglefektif     	varchar(100) NULL,
	alasan_mutasi  	varchar(100) NULL,
	no_sk        	varchar(100) NULL,
	keterangan     	varchar(100) NULL,
	gaji_pokok    	varchar(100) NULL,
	valuta         	varchar(100) NULL,
	penilaian      	varchar(100) NULL,
	klas_pegawai   	varchar(100) NULL,
	unit_kerja     	varchar(100) NULL,
	unit_usaha     	varchar(100) NULL,
	kdarea         	varchar(100) NULL,
	cabang         	varchar(100) NULL,
	golongan       	varchar(100) NULL,
	jabatan        	varchar(100) NULL,
	kel_jabatan    	varchar(100) NULL,
	status_validasi	varchar(100) NULL,
	proses_id      	varchar(100) NULL,
	version        	int4 NULL,
	created_by     	varchar(15) NULL,
	created_on     	timestamp NULL,
	updated_by     	varchar(15) NULL,
	updated_on     	timestamp NULL,
	PRIMARY KEY(tmp04_id)
)
WITHOUT OIDS 
TABLESPACE pg_default
GO

