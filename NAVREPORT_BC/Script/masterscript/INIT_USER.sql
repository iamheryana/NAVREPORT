DELETE FROM sec_user

INSERT INTO sec_user(
            usr_id, usr_loginname, usr_password, usr_lastname, usr_firstname, 
            usr_email, usr_locale, usr_enabled, usr_accountnonexpired, usr_credentialsnonexpired, 
            usr_accountnonlocked, usr_token, version, nik_id, subunit_id, 
            created_on, created_by, updated_on, updated_by, expired_date, 
            flag_activ, access_cabang, access_golongan)
    VALUES (1;'admin';'8540b65b4d0dbf49a8770e34c6f852cb9dcf7d2b';'Admin';'Admin';'admin@yahoo.co.id';'';1;1;1;1;'';19;;;'';'';current_timestamp;'admin';current_date;'Y';'A';'A');
