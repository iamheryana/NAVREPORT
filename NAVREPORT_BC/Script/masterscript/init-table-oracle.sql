DROP TABLE HIBERNATE_STATISTICS;
DROP SEQUENCE HIBERNATE_STATISTICS_SEQ;

CREATE TABLE HIBERNATE_STATISTICS
(
 ID INTEGER NOT NULL,
 FLUSHCOUNT INTEGER NOT NULL,
 PREPARESTATEMENTCOUNT INTEGER NOT NULL,
 ENTITYLOADCOUNT INTEGER NOT NULL,
 ENTITYUPDATECOUNT INTEGER NOT NULL,
 ENTITYINSERTCOUNT INTEGER NOT NULL,
 ENTITYDELETECOUNT INTEGER NOT NULL,
 ENTITYFETCHCOUNT INTEGER NOT NULL,
 COLLECTIONLOADCOUNT INTEGER NOT NULL,
 COLLECTIONUPDATECOUNT INTEGER NOT NULL,
 COLLECTIONREMOVECOUNT INTEGER NOT NULL,
 COLLECTIONRECREATECOUNT INTEGER NOT NULL,
 COLLECTIONFETCHCOUNT INTEGER NOT NULL,
 QUERYEXECUTIONCOUNT INTEGER NOT NULL,
 QUERYEXECUTIONMAXTIME INTEGER NOT NULL,
 OPTIMISTICFAILURECOUNT INTEGER NOT NULL,
 QUERYEXECUTIONMAXTIMEQUERYSTR VARCHAR(1000),
 CALLMETHOD VARCHAR(1000) NOT NULL,
 JAVAFINISHMS NUMBER NOT NULL,
 FINISHTIME TIMESTAMP NOT NULL,
 CONSTRAINT HIBERNATESTATISTICS_PKEY PRIMARY KEY (ID)
);
CREATE SEQUENCE HIBERNATE_STATISTICS_SEQ;


DROP TABLE HIBERNATE_ENTITY_STATISTICS;
DROP SEQUENCE HIBERNATE_ENTITY_STAT_SEQ;

CREATE TABLE HIBERNATE_ENTITY_STATISTICS
(
 ID INTEGER NOT NULL,
 HIBERNATEENTITYSTATISTICSID NUMBER NOT NULL,
 ENTITYNAME VARCHAR(1000) NOT NULL,
 LOADCOUNT INTEGER NOT NULL,
 UPDATECOUNT INTEGER NOT NULL,
 INSERTCOUNT INTEGER NOT NULL,
 DELETECOUNT INTEGER NOT NULL,
 FETCHCOUNT INTEGER NOT NULL,
 OPTIMISTICFAILURECOUNT INTEGER NOT NULL,
 CONSTRAINT HIBERNATEENTITYSTATISTICS_PKEY PRIMARY KEY (ID)
);
CREATE INDEX FKI_
 ON HIBERNATE_ENTITY_STATISTICS (
 HIBERNATEENTITYSTATISTICSID
 );
CREATE SEQUENCE HIBERNATE_ENTITY_STAT_SEQ;


DROP TABLE APP_NEWS;
DROP SEQUENCE APP_NEWS_SEQ;

CREATE TABLE APP_NEWS (
 ANW_ID INTEGER NOT NULL,
 ANW_TEXT VARCHAR(1000) NULL,
 ANW_DATE DATE NOT NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_APP_NEWS PRIMARY KEY (ANW_ID)
);
CREATE SEQUENCE APP_NEWS_SEQ;


DROP TABLE YOUTUBE_LINK;
DROP SEQUENCE YOUTUBE_LINK_SEQ;

CREATE TABLE YOUTUBE_LINK (
 YTB_ID INTEGER NOT NULL,
 YTB_INTERPRET VARCHAR(50) NULL,
 YTB_TITLE VARCHAR(50) NULL,
 YTB_URL VARCHAR(250) NOT NULL,
 VERSION INTEGER  DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_YOUTUBE_LINK PRIMARY KEY (YTB_ID)
);
CREATE SEQUENCE YOUTUBE_LINK_SEQ;


DROP TABLE BRANCHE;
DROP SEQUENCE BRANCHE_SEQ;

CREATE TABLE BRANCHE (
 BRA_ID INTEGER NOT NULL,
 BRA_NR VARCHAR(20) NULL,
 BRA_BEZEICHNUNG VARCHAR(30) NOT NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_BRANCHE PRIMARY KEY (BRA_ID)
);
CREATE UNIQUE INDEX IDX_BRA_BEZEICHNUNG ON BRANCHE (
BRA_BEZEICHNUNG
);
CREATE SEQUENCE BRANCHE_SEQ;


DROP TABLE ARTIKEL;
DROP SEQUENCE ARTIKEL_SEQ;

CREATE TABLE ARTIKEL (
 ART_ID INTEGER NOT NULL,
 ART_KURZBEZEICHNUNG VARCHAR(50) NOT NULL,
 ART_LANGBEZEICHNUNG VARCHAR(1000) NULL,
 ART_NR VARCHAR(20) NOT NULL,
 ART_PREIS NUMERIC(12,2) DEFAULT 0.00 NOT NULL ENABLE,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_ARTIKEL PRIMARY KEY (ART_ID)
);
CREATE UNIQUE INDEX IDX_ART_NR ON ARTIKEL (
ART_NR
);
CREATE INDEX IDX_ART_BEZEICHNUNG ON ARTIKEL (
ART_KURZBEZEICHNUNG
);
CREATE SEQUENCE ARTIKEL_SEQ;


DROP TABLE AUFTRAG;
DROP SEQUENCE AUFTRAG_SEQ;

CREATE TABLE AUFTRAG (
 AUF_ID INTEGER NOT NULL,
 AUF_KUN_ID INTEGER NOT NULL,
 AUF_NR VARCHAR(20) NOT NULL,
 AUF_BEZEICHNUNG VARCHAR(50) NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_AUFTRAG PRIMARY KEY (AUF_ID)
);
CREATE INDEX IX_AUF_KUN_ID ON AUFTRAG (
AUF_KUN_ID
);
CREATE UNIQUE INDEX IX_AUF_NR ON AUFTRAG (
AUF_NR
);
CREATE SEQUENCE AUFTRAG_SEQ;


DROP TABLE AUFTRAGPOSITION;
DROP SEQUENCE AUFTRAGPOSITION_SEQ;

CREATE TABLE AUFTRAGPOSITION (
 AUP_ID INTEGER NOT NULL,
 AUP_AUF_ID INTEGER NOT NULL,
 ART_ID INTEGER NULL,
 AUP_POSITION INTEGER NULL,
 AUP_MENGE NUMERIC(12,2) NULL,
 AUP_EINZELWERT NUMERIC(12,2) NULL,
 AUP_GESAMTWERT NUMERIC(12,2) NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_AUFTRAGPOSITION PRIMARY KEY (AUP_ID)
);
CREATE INDEX IX_AUP_AUF_ID ON AUFTRAGPOSITION (
AUP_AUF_ID
);
CREATE SEQUENCE AUFTRAGPOSITION_SEQ;


DROP TABLE CALENDAR_EVENT;
DROP SEQUENCE CALENDAR_EVENT_SEQ;

CREATE TABLE CALENDAR_EVENT (
 CLE_ID INTEGER NOT NULL,
 CLE_TITLE VARCHAR(20) NULL,
 CLE_CONTENT VARCHAR(300) NOT NULL,
 CLE_BEGIN_DATE TIMESTAMP NOT NULL,
 CLE_END_DATE TIMESTAMP NOT NULL,
 CLE_HEADER_COLOR VARCHAR(10) NULL,
 CLE_CONTENT_COLOR VARCHAR(10) NULL,
 CLE_USR_ID INTEGER NOT NULL,
 CLE_LOCKED INTEGER DEFAULT 0,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_CALENDAR_EVENT PRIMARY KEY (CLE_ID)
);
CREATE SEQUENCE CALENDAR_EVENT_SEQ;


DROP TABLE FILIALE;
DROP SEQUENCE FILIALE_SEQ;

CREATE TABLE FILIALE (
 FIL_ID INTEGER NOT NULL,
 FIL_NR VARCHAR(20) NOT NULL,
 FIL_BEZEICHNUNG VARCHAR(50) NULL,
 FIL_NAME1 VARCHAR(50) NULL,
 FIL_NAME2 VARCHAR(50) NULL,
 FIL_ORT VARCHAR(50) NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_FILIALE PRIMARY KEY (FIL_ID)
);
CREATE INDEX IX_FIL_BEZEICHNUNG ON FILIALE (
FIL_BEZEICHNUNG
);
CREATE UNIQUE INDEX IX_FIL_NR ON FILIALE (
FIL_NR
);
CREATE SEQUENCE FILIALE_SEQ;


DROP TABLE GUESTBOOK;
DROP SEQUENCE GUESTBOOK_SEQ;

CREATE TABLE GUESTBOOK (
 GUB_ID INTEGER NOT NULL,
 GUB_SUBJECT VARCHAR(40) NOT NULL,
 GUB_DATE TIMESTAMP NOT NULL,
 GUB_USR_NAME VARCHAR(40) NOT NULL,
 GUB_TEXT VARCHAR(1000) NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_GUESTBOOK PRIMARY KEY (GUB_ID)
);
CREATE INDEX IDX_GUB_SUBJECT ON GUESTBOOK (
GUB_SUBJECT
);
CREATE INDEX IDX_GUB_DATE ON GUESTBOOK (
GUB_DATE
);
CREATE INDEX IDX_GUB_USR_NAME ON GUESTBOOK (
GUB_USR_NAME
);
CREATE SEQUENCE GUESTBOOK_SEQ;


DROP TABLE IPC_IP2COUNTRY;
DROP SEQUENCE IPC_IP2COUNTRY_SEQ;

CREATE TABLE IPC_IP2COUNTRY (
 IPC_ID INTEGER NOT NULL,
 IPC_IP_FROM INTEGER NULL,
 IPC_IP_TO INTEGER NULL,
 IPC_COUNTRY_CODE2 VARCHAR(2) NULL,
 IPC_COUNTRY_CODE3 VARCHAR(3) NULL,
 IPC_COUNTRY_NAME VARCHAR(50) NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_IPC_IP2COUNTRY PRIMARY KEY (IPC_ID)
);
CREATE INDEX IDX_IPC_IP_FROM ON IPC_IP2COUNTRY (
IPC_IP_FROM
);
CREATE INDEX IDX_IPC_IP_TO ON IPC_IP2COUNTRY (
IPC_IP_TO
);
CREATE INDEX IDX_IPC_COUNTRY_CODE2 ON IPC_IP2COUNTRY (
IPC_COUNTRY_CODE2
);
CREATE INDEX IDX_IPC_COUNTRY_CODE3 ON IPC_IP2COUNTRY (
IPC_COUNTRY_CODE3
);
CREATE INDEX IDX_IPC_COUNTRY_NAME ON IPC_IP2COUNTRY (
IPC_COUNTRY_NAME
);
CREATE SEQUENCE IPC_IP2COUNTRY_SEQ;


DROP TABLE KUNDE;
DROP SEQUENCE KUNDE_SEQ;

CREATE TABLE KUNDE (
 KUN_ID INTEGER NOT NULL,
 KUN_FIL_ID INTEGER NOT NULL,
 KUN_BRA_ID INTEGER NULL,
 KUN_NR VARCHAR(20) NOT NULL,
 KUN_MATCHCODE VARCHAR(20) NULL,
 KUN_NAME1 VARCHAR(50) NULL,
 KUN_NAME2 VARCHAR(50) NULL,
 KUN_ORT VARCHAR(50) NULL,
 KUN_MAHNSPERRE SMALLINT NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ,
 CONSTRAINT PK_KUNDE PRIMARY KEY (KUN_ID)
);
CREATE INDEX IX_KUN_FIL_ID ON KUNDE (
KUN_FIL_ID
);
CREATE UNIQUE INDEX IX_KUN_NR ON KUNDE (
KUN_NR
);
CREATE SEQUENCE KUNDE_SEQ;


DROP TABLE LOG_IP2COUNTRY;
DROP SEQUENCE LOG_IP2COUNTRY_SEQ;

CREATE TABLE LOG_IP2COUNTRY (
 I2C_ID INTEGER NOT NULL,
 CCD_ID INTEGER NULL,
 I2C_CITY VARCHAR(50) NULL,
 I2C_LATITUDE DECIMAL NULL,
 I2C_LONGITUDE DECIMAL NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_LOG_IP2COUNTRY PRIMARY KEY (I2C_ID)
);
CREATE INDEX IDX_I2C_CCD_ID ON LOG_IP2COUNTRY (
CCD_ID
);
CREATE SEQUENCE LOG_IP2COUNTRY_SEQ;


DROP TABLE SEC_GROUP;
DROP SEQUENCE SEC_GROUP_SEQ;

CREATE TABLE SEC_GROUP (
 GRP_ID INTEGER NOT NULL,
 GRP_SHORTDESCRIPTION VARCHAR(40) NOT NULL,
 GRP_LONGDESCRIPTION VARCHAR(1000) NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_SEC_GROUP PRIMARY KEY (GRP_ID)
);
CREATE UNIQUE INDEX IDX_GRP_SHORTDESCRIPTION ON SEC_GROUP (
GRP_SHORTDESCRIPTION
);
CREATE SEQUENCE SEC_GROUP_SEQ;


DROP TABLE SEC_GROUPRIGHT;
DROP SEQUENCE SEC_GROUPRIGHT_SEQ;

CREATE TABLE SEC_GROUPRIGHT (
 GRI_ID INTEGER NOT NULL,
 GRP_ID INTEGER NOT NULL,
 RIG_ID INTEGER NOT NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_SEC_GROUPRIGHT PRIMARY KEY (GRI_ID)
);
CREATE UNIQUE INDEX IDX_GRI_GRPRIG ON SEC_GROUPRIGHT (
GRP_ID,
RIG_ID
);
CREATE SEQUENCE SEC_GROUPRIGHT_SEQ;


DROP TABLE SEC_LOGINLOG;
DROP SEQUENCE SEC_LOGINLOG_SEQ;

CREATE TABLE SEC_LOGINLOG (
 LGL_ID INTEGER NOT NULL,
 I2C_ID INTEGER NULL,
 LGL_LOGINNAME VARCHAR(50) NOT NULL,
 LGL_LOGTIME TIMESTAMP NOT NULL,
 LGL_IP VARCHAR(19) NULL,
 LGL_BROWSERTYPE VARCHAR(40) NULL,
 LGL_STATUS_ID INTEGER NOT NULL,
 LGL_SESSIONID VARCHAR(50) NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_SEC_LOGINLOG PRIMARY KEY (LGL_ID)
);
CREATE INDEX IDX_LGL_LOGTIME ON SEC_LOGINLOG (
LGL_LOGTIME
);
CREATE INDEX IDX_LGL_I2C_ID ON SEC_LOGINLOG (
I2C_ID
);
CREATE SEQUENCE SEC_LOGINLOG_SEQ;


DROP TABLE SEC_RIGHT;
DROP SEQUENCE SEC_RIGHT_SEQ;

CREATE TABLE SEC_RIGHT (
 RIG_ID INTEGER NOT NULL,
 RIG_TYPE INTEGER DEFAULT 1 NOT NULL ENABLE,
 RIG_NAME VARCHAR(50) NOT NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_SEC_RIGHT PRIMARY KEY (RIG_ID)
);
CREATE INDEX IDX_RIG_TYPE ON SEC_RIGHT (
RIG_TYPE
);
CREATE UNIQUE INDEX IDX_RIG_NAME ON SEC_RIGHT (
RIG_NAME
);
CREATE SEQUENCE SEC_RIGHT_SEQ;


DROP TABLE SEC_ROLE;
DROP SEQUENCE SEC_ROLE_SEQ;

CREATE TABLE SEC_ROLE (
 ROL_ID INTEGER NOT NULL,
 ROL_SHORTDESCRIPTION VARCHAR(30) NOT NULL,
 ROL_LONGDESCRIPTION VARCHAR(1000) NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_SEC_ROLE PRIMARY KEY (ROL_ID)
);
CREATE UNIQUE INDEX IDX_ROLE_SHORTDESCRIPTION ON SEC_ROLE (
ROL_SHORTDESCRIPTION
);
CREATE SEQUENCE SEC_ROLE_SEQ;


DROP TABLE SEC_ROLEGROUP;
DROP SEQUENCE SEC_ROLEGROUP_SEQ;

CREATE TABLE SEC_ROLEGROUP (
 RLG_ID INTEGER NOT NULL,
 GRP_ID INTEGER NOT NULL,
 ROL_ID INTEGER NOT NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_SEC_ROLEGROUP PRIMARY KEY (RLG_ID)
);
CREATE UNIQUE INDEX IDX_RLG_GRPROL ON SEC_ROLEGROUP (
GRP_ID,
ROL_ID
);
CREATE SEQUENCE SEC_ROLEGROUP_SEQ;


DROP TABLE SEC_USER;
DROP SEQUENCE SEC_USER_SEQ;

CREATE TABLE SEC_USER (
 USR_ID INTEGER NOT NULL,
 USR_LOGINNAME VARCHAR(50) NOT NULL,
 USR_PASSWORD VARCHAR(50) NOT NULL,
 USR_LASTNAME VARCHAR(50) NULL,
 USR_FIRSTNAME VARCHAR(50) NULL,
 USR_EMAIL VARCHAR(200) NULL,
 USR_LOCALE VARCHAR(5) NULL,
 USR_ENABLED SMALLINT DEFAULT 0 NOT NULL ENABLE,
 USR_ACCOUNTNONEXPIRED SMALLINT DEFAULT 1 NOT NULL ENABLE,
 USR_CREDENTIALSNONEXPIRED SMALLINT DEFAULT 1 NOT NULL ENABLE,
 USR_ACCOUNTNONLOCKED SMALLINT DEFAULT 1 NOT NULL ENABLE,
 USR_TOKEN VARCHAR(20) NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_SEC_USER PRIMARY KEY (USR_ID)
);
CREATE UNIQUE INDEX IDX_USR_LOGINNAME ON SEC_USER (
USR_LOGINNAME
);
CREATE SEQUENCE SEC_USER_SEQ;


DROP TABLE SEC_USERROLE;
DROP SEQUENCE SEC_USERROLE_SEQ;

CREATE TABLE SEC_USERROLE (
 URR_ID INTEGER NOT NULL,
 USR_ID INTEGER NOT NULL,
 ROL_ID INTEGER NOT NULL,
 VERSION INTEGER  DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_SEC_USERROLE PRIMARY KEY (URR_ID)
);
CREATE UNIQUE INDEX IDX_URR_USRROL ON SEC_USERROLE (
USR_ID,
ROL_ID
);
CREATE SEQUENCE SEC_USERROLE_SEQ;


DROP TABLE SYS_COUNTRYCODE;
DROP SEQUENCE SYS_COUNTRYCODE_SEQ;

CREATE TABLE SYS_COUNTRYCODE (
 CCD_ID INTEGER NOT NULL,
 CCD_NAME VARCHAR(48) NULL,
 CCD_CODE2 VARCHAR(2) NOT NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_SYS_COUNTRYCODE PRIMARY KEY (CCD_ID)
);
CREATE UNIQUE INDEX IDX_CCD_CODE2 ON SYS_COUNTRYCODE (
CCD_CODE2
);
CREATE SEQUENCE SYS_COUNTRYCODE_SEQ;


DROP TABLE SYS_IP4COUNTRY;
DROP SEQUENCE SYS_IP4COUNTRY_SEQ;

CREATE TABLE SYS_IP4COUNTRY (
 I4CO_ID INTEGER NOT NULL,
 I4CO_IP INTEGER NULL,
 I4CO_CCD_ID INTEGER NULL,
 VERSION INTEGER DEFAULT 0 NOT NULL ENABLE,
 CONSTRAINT PK_SYS_IP4COUNTRY PRIMARY KEY (I4CO_ID)
);
CREATE INDEX IDX_I4CO_IP ON SYS_IP4COUNTRY (
I4CO_IP
);
CREATE INDEX IDX_I4CO_CCD_ID ON SYS_IP4COUNTRY (
I4CO_CCD_ID
);
CREATE SEQUENCE SYS_IP4COUNTRY_SEQ;