ALTER SEQUENCE sec_user_usr_id_seq RESTART MAXVALUE maxvalue;

ALTER SEQUENCE sec_user_usr_id_seq RESTART WITH (SELECT max(usr_id) FROM sec_user);

--Login to psql and run the following
--What is the result?
SELECT MAX(usr_id) FROM sec_user;

--Then run...
--This should be higher than the last result.
SELECT nextval('sec_user_usr_id_seq');

--If it's not higher... run this set the sequence last to your highest pid it. 
--wise to run a quick pg_dump first...)
SELECT setval('sec_user_usr_id_seq', (SELECT MAX(usr_id) FROM sec_user));


select * from sec_user