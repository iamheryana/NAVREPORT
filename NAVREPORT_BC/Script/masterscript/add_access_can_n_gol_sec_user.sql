ALTER TABLE sec_user ADD access_cabang character varying(1); 
ALTER TABLE sec_user ADD access_golongan character varying(1);
update sec_user set  access_cabang ='A', access_golongan = 'A';
