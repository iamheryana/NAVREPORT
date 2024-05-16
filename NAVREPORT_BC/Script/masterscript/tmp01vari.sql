CREATE TABLE tmp01vari
(
  tmp01_id serial NOT NULL,
  nip character varying(100),
  flgdppt character varying(100),
  kddppt character varying(100),
  prdmulai character varying(100),
  prdsd character varying(100),
  nilai character varying(100),
  kdcurr character varying(100),
  persen character varying(100),
  bayardimuka character varying(100),
  flag character varying(100),
  status_validasi character varying(100),
  proses_id character varying(100),
  version integer,
  created_by character varying(15),
  created_on timestamp without time zone,
  updated_by character varying(15),
  updated_on timestamp without time zone,
  CONSTRAINT tmp01_key1 PRIMARY KEY (tmp01_id)
) WITH (
  OIDS=FALSE
);
