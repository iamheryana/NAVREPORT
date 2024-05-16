INSERT INTO SEC_USER (USR_ID, USR_LOGINNAME, USR_PASSWORD, USR_LASTNAME, USR_FIRSTNAME, USR_EMAIL, USR_LOCALE, USR_ENABLED, USR_ACCOUNTNONEXPIRED, USR_CREDENTIALSNONEXPIRED, USR_ACCOUNTNONLOCKED, USR_TOKEN,  VERSION) VALUES 
(SEC_USER_SEQ.nextval, 'guest', 'guest', 'guestFirstname', 'guestlastname', 'guest@web.de', NULL, 1, 1, 1, 1, null, 0);
INSERT INTO SEC_USER (USR_ID, USR_LOGINNAME, USR_PASSWORD, USR_LASTNAME, USR_FIRSTNAME, USR_EMAIL, USR_LOCALE, USR_ENABLED, USR_ACCOUNTNONEXPIRED, USR_CREDENTIALSNONEXPIRED, USR_ACCOUNTNONLOCKED, USR_TOKEN,  VERSION) VALUES 
(SEC_USER_SEQ.nextval, 'admin', 'admin', 'Visor', 'Super', 'admin@web.de', NULL, 1, 1, 1, 1, null, 0);
INSERT INTO SEC_USER (USR_ID, USR_LOGINNAME, USR_PASSWORD, USR_LASTNAME, USR_FIRSTNAME, USR_EMAIL, USR_LOCALE, USR_ENABLED, USR_ACCOUNTNONEXPIRED, USR_CREDENTIALSNONEXPIRED, USR_ACCOUNTNONLOCKED, USR_TOKEN,  VERSION) VALUES 
(SEC_USER_SEQ.nextval, 'user1', 'user1', 'Kingsley', 'Ben', 'B.Kingsley@web.de', NULL, 1, 1, 1, 1, null, 0);
INSERT INTO SEC_USER (USR_ID, USR_LOGINNAME, USR_PASSWORD, USR_LASTNAME, USR_FIRSTNAME, USR_EMAIL, USR_LOCALE, USR_ENABLED, USR_ACCOUNTNONEXPIRED, USR_CREDENTIALSNONEXPIRED, USR_ACCOUNTNONLOCKED, USR_TOKEN,  VERSION) VALUES 
(SEC_USER_SEQ.nextval, 'headoffice', 'headoffice', 'Willis', 'Bruce', 'B.Willis@web.de', NULL, 1, 1, 1, 1, null, 0);
INSERT INTO SEC_USER (USR_ID, USR_LOGINNAME, USR_PASSWORD, USR_LASTNAME, USR_FIRSTNAME, USR_EMAIL, USR_LOCALE, USR_ENABLED, USR_ACCOUNTNONEXPIRED, USR_CREDENTIALSNONEXPIRED, USR_ACCOUNTNONLOCKED, USR_TOKEN,  VERSION) VALUES 
(SEC_USER_SEQ.nextval, 'user2', 'user2', 'Kingdom', 'Marta', 'M.Kingdom@web.de', NULL, 1, 1, 1, 1, null, 0);


INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 1, 'menuCat_OfficeData', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_OfficeData_Customers', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_OfficeData_Orders', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 1, 'menuCat_MainData', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_MainData_ArticleItems', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_MainData_Branch', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_MainData_Office', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 1, 'menuCat_Administration', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_Administration_Users', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_Administration_UserRoles', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_Administration_Roles', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_Administration_RoleGroups', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_Administration_Groups', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_Administration_GroupRights', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_Administration_Rights', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 1, 'menuCat_UserRights', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_Administration_LoginsLog', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menuItem_Administration_HibernateStats', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 2, 'menu_Item_Calendar', 0);

/* Pages = TypeINSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0) */
/* --> Page Customer */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'window_customerList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'window_customerDialog', 0);
/* --> Page Orders */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'orderListWindow', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'orderDialogWindow', 0);
/* --> Page Articles */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'windowArticlesList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'window_ArticlesDialog', 0);
/* --> Page Branches */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'window_BranchesList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'window_BranchesDialog', 0);
/* --> Page Offices */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'window_OfficeList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'window_OfficeDialog', 0);
/* --> Page Admin - Users */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Admin_UserList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Admin_UserDialog', 0);
/* --> Page Admin - UserRoles */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Security_UserRolesList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Security_RolesList', 0);
/* --> Page Admin - Roles */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Security_RolesDialog', 0);
/* --> Page Admin - RoleGroups */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Security_RoleGroupsList', 0);
/* --> Page Admin - Groups */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Security_GroupsList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Security_GroupsDialog', 0);
/* --> Page Admin - GroupRights */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Security_GroupRightsList', 0);
/* --> Page Admin - Rights */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Security_RightsList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Security_RightsDialog', 0);
/* --> Page Login Log */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'page_Admin_LoginLogList', 0);
/* --> Nachtrag Page Orders */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'orderPositionDialogWindow', 0);

/* Tabs = TypeINSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 5) */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 5, 'tab_CustomerDialog_Address', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 5, 'tab_CustomerDialog_Chart', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 5, 'tab_CustomerDialog_Orders', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 5, 'tab_CustomerDialog_Memos', 0);

/* Components = TypeINSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6) */
/* --> CustomerList BUTTON */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_CustomerList_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_CustomerList_NewCustomer', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_CustomerList_CustomerFindDialog', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_CustomerList_PrintList', 0);
/* --> CustomerDialog BUTTON */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_CustomerDialog_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_CustomerDialog_btnNew', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_CustomerDialog_btnEdit', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_CustomerDialog_btnDelete', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_CustomerDialog_btnSave', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_CustomerDialog_btnClose', 0);
/* --> OrderList BUTTON */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderList_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderList_NewOrder', 0);
/* --> OrderDialog BUTTON */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderDialog_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderDialog_btnNew', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderDialog_btnEdit', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderDialog_btnDelete', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderDialog_btnSave', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderDialog_btnClose', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderDialog_PrintOrder', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderDialog_NewOrderPosition', 0);
/* --> OrderPositionDialog BUTTON */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderPositionDialog_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderPositionDialog_PrintOrderPositions', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderPositionDialog_btnNew', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderPositionDialog_btnEdit', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderPositionDialog_btnDelete', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderPositionDialog_btnSave', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OrderPositionDialog_btnClose', 0);
/* USERS */
/* --> userListWindow */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'userListWindow', 0);
/* --> userListWindow BUTTONS*/
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserList_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserList_NewUser', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserList_PrintUserList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserList_SearchLoginname', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserList_SearchLastname', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserList_SearchEmail', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'checkbox_UserList_ShowAll', 0);
/* --> Mehode onDoubleClick Listbox */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 3, 'UserList_listBoxUser.onDoubleClick', 0);
/* --> userDialogWindow */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'userDialogWindow', 0);
/* --> userDialogWindow BUTTONS*/
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserDialog_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserDialog_btnNew', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserDialog_btnEdit', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserDialog_btnDelete', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserDialog_btnSave', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_UserDialog_btnClose', 0);
/* --> userDialogWindow Special Admin Panels */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'panel_UserDialog_Status', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'panel_UserDialog_SecurityToken', 0);
/* --> userListWindow Search panel */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'hbox_UserList_SearchUsers', 0);
/* Tab Details */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'tab_UserDialog_Details', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 3, 'data_SeeAllUserData', 0);
/* BRANCHES */
/* branchListWindow Buttons*/
/* --> button_BranchList_btnHelp */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'button_BranchMain_PrintBranches', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'button_BranchMain_Search_BranchName', 0);
/* branchDialogWindow BUTTONS */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_BranchMain_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_BranchMain_btnNew', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_BranchMain_btnEdit', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_BranchMain_btnDelete', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_BranchMain_btnSave', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_BranchMain_btnClose', 0);
/* ARTICLES */
/* window_ArticlesList Buttons*/
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticlesList_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticleList_NewArticle', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticleList_PrintList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticleList_SearchArticleID', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticleList_SearchName', 0);
/* window_ArticlesDialog Buttons*/
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticlesDialog_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticlesDialog_btnNew', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticlesDialog_btnEdit', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticlesDialog_btnDelete', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticlesDialog_btnSave', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_ArticlesDialog_btnClose', 0);
/* OFFICES */
/* window_OfficeList Buttons*/
/* --> button_BranchList_btnHelp */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OfficeList_PrintList', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OfficeList_SearchNo', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OfficeList_SearchName', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OfficeList_SearchCity', 0);
/* window_OfficeDialog BUTTONS */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OfficeMain_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OfficeMain_btnNew', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OfficeMain_btnEdit', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OfficeMain_btnDelete', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OfficeMain_btnSave', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_OfficeMain_btnClose', 0);

/* Method/Event = TypeINSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 3) */
/* --> CustomerList BUTTON */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 3, 'CustomerList_listBoxCustomer.onDoubleClick', 0);
/* --> secRoleDialogWindow */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'secRoleDialogWindow', 0);
/* --> secRoleDialogWindow BUTTONS*/
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRoleDialog_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRoleDialog_btnNew', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRoleDialog_btnEdit', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRoleDialog_btnDelete', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRoleDialog_btnSave', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRoleDialog_btnClose', 0);
/* --> secGroupDialogWindow */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'secGroupDialogWindow', 0);
/* --> secGroupDialogWindow BUTTONS*/
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecGroupDialog_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecGroupDialog_btnNew', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecGroupDialog_btnEdit', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecGroupDialog_btnDelete', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecGroupDialog_btnSave', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecGroupDialog_btnClose', 0);
/* --> secRightDialogWindow */
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 0, 'secRightDialogWindow', 0);
/* --> secRightDialogWindow BUTTONS*/
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRightDialog_btnHelp', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRightDialog_btnNew', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRightDialog_btnEdit', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRightDialog_btnDelete', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRightDialog_btnSave', 0);
INSERT INTO SEC_RIGHT (RIG_ID, RIG_TYPE, RIG_NAME, VERSION) VALUES (SEC_RIGHT_SEQ.NEXTVAL, 6, 'button_SecRightDialog_btnClose', 0);



INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Headoffice Supervisor Group', 'kjhf ff hgfd', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Admin Group - user accounts', 'create/modify user accounts', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Guest Group', 'Minimal Rights for the guests', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Admin Group - user rights', 'edit/modify user rights', 0);
/* Customers */
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Customers_View', 'Allow to  view customers data', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Customers_New', 'Allow create new customers', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Customers_Edit', 'Allow editing of customers', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Customers_Delete', 'Allow deleting of customers', 0);
/* Orders */
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Orders_View', 'Allow to view orders data', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Orders_New', 'Allow create new orders', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Orders_Edit', 'Allow editing of orders', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Orders_Delete', 'Allow deleting of orders', 0);
/* Branches */
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Branch_View', 'Allow to view branches data', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Branch_New', 'Allow create new branches', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Branch_Edit', 'Allow editing of branches', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Branch_Delete', 'Allow deleting of branches', 0);
/* Articles */
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Articles_View', 'Allow to view articles data', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Articles_New', 'Allow create new articles', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Articles_Edit', 'Allow editing of articles', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Articles_Delete', 'Allow deleting of articles', 0);
/* Offices */
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Offices_View', 'Allow to view offices data', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Offices_New', 'Allow create new offices', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Offices_Edit', 'Allow editing of offices', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Offices_Delete', 'Allow deleting of offices', 0);
/* Users */
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'User_View_UsersOnly', 'Allow to view own user data.', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'User_Edit_UsersOnly', 'Allow to edit own user data.', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Users_View', 'Allow to view all users data.', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Users_New', 'Allow create new users', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Users_Edit', 'Allow editing of users', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Users_Delete', 'Allow deleting of users', 0);
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Users_Search', 'Allow searching of users', 0);
/* secGroup */
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Security_Groups', 'Allow to view the securityGroups Dialog', 0);
/* secRole */
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Security_Roles', 'Allow to view the securityRoles Dialog', 0);
/* secRight */
INSERT INTO SEC_GROUP (GRP_ID, GRP_SHORTDESCRIPTION, GRP_LONGDESCRIPTION, VERSION) VALUES (SEC_GROUP_SEQ.nextval, 'Security_Rights', 'Allow to view the securityRights Dialog', 0);


insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Headoffice Supervisor Group'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuCat_MainData'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Headoffice Supervisor Group'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_MainData_ArticleItems'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Headoffice Supervisor Group'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_MainData_Branch'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Headoffice Supervisor Group'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_MainData_Office'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuCat_Administration'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_Users'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_UserRoles'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_Roles'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_RoleGroups'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_Groups'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_GroupRights'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_Rights'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuCat_UserRights'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_LoginsLog'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_HibernateStats'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuCat_OfficeData'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_OfficeData_Customers'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_customerList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'CustomerList_listBoxCustomer.onDoubleClick'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_customerDialog'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerList_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerList_CustomerFindDialog'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerList_PrintList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'tab_CustomerDialog_Address'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'tab_CustomerDialog_Chart'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'tab_CustomerDialog_Orders'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'tab_CustomerDialog_Memos'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_customerList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerList_NewCustomer'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_customerDialog'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnNew'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_customerList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_customerDialog'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_customerList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_customerDialog'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Customers_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_CustomerDialog_btnDelete'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuCat_OfficeData'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_OfficeData_Orders'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'orderListWindow'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderList_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'CustomerList_listBoxCustomer.onDoubleClick'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'orderDialogWindow'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_PrintOrder'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'orderPositionDialogWindow'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_PrintOrderPositions'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderList_NewOrder'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnNew'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_NewOrderPosition'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnNew'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_NewOrderPosition'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_btnDelete'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderDialog_NewOrderPosition'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Orders_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OrderPositionDialog_btnDelete'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuCat_Administration'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_Users'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'userListWindow'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserList_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'UserList_listBoxUser.onDoubleClick'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'userDialogWindow'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'tab_UserDialog_Details'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_Edit_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'User_Edit_UsersOnly'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuCat_Administration'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_Administration_Users'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'userListWindow'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserList_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserList_PrintUserList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'UserList_listBoxUser.onDoubleClick'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'userDialogWindow'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'tab_UserDialog_Details'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'panel_UserDialog_Status'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'panel_UserDialog_SecurityToken'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'data_SeeAllUserData'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserList_NewUser'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnNew'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_UserDialog_btnDelete'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Users_Search'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'hbox_UserList_SearchUsers'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuCat_MainData'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_MainData_Branch'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_BranchesList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_BranchMain_PrintBranches'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_BranchMain_Search_BranchName'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_BranchesDialog'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_BranchMain_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_BranchMain_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_BranchMain_btnNew'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_BranchMain_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_BranchMain_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_BranchMain_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Branch_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_BranchMain_btnDelete'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuCat_MainData'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_MainData_ArticleItems'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'windowArticlesList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticlesList_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticleList_PrintList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_ArticlesDialog'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticlesDialog_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticlesDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticleList_SearchArticleID'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticleList_SearchName'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticleList_NewArticle'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticlesDialog_btnNew'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticlesDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticlesDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticlesDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Articles_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_ArticlesDialog_btnDelete'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuCat_MainData'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'menuItem_MainData_Office'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_OfficeList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeList_PrintList'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeList_SearchNo'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeList_SearchName'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeList_SearchCity'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'window_OfficeDialog'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeMain_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_View'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeMain_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeMain_btnNew'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_New'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeMain_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeMain_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_Edit'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeMain_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Offices_Delete'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_OfficeMain_btnDelete'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Groups'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'secGroupDialogWindow'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Groups'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecGroupDialog_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Groups'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecGroupDialog_btnNew'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Groups'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecGroupDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Groups'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecGroupDialog_btnDelete'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Groups'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecGroupDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Groups'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecGroupDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Roles'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'secRoleDialogWindow'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Roles'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRoleDialog_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Roles'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRoleDialog_btnNew'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Roles'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRoleDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Roles'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRoleDialog_btnDelete'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Roles'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRoleDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Roles'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRoleDialog_btnClose'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Rights'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'secRightDialogWindow'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Rights'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRightDialog_btnHelp'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Rights'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRightDialog_btnNew'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Rights'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRightDialog_btnEdit'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Rights'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRightDialog_btnDelete'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Rights'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRightDialog_btnSave'), 0);
insert into SEC_GROUPRIGHT(GRI_ID, GRP_ID, RIG_ID, VERSION) values (SEC_GROUPRIGHT_SEQ.nextval, (select grp_id from sec_group where GRP_SHORTDESCRIPTION = 'Security_Rights'), (select RIG_ID from SEC_RIGHT where RIG_NAME = 'button_SecRightDialog_btnClose'), 0);


INSERT INTO SEC_ROLE (ROL_ID, ROL_SHORTDESCRIPTION, ROL_LONGDESCRIPTION, VERSION) VALUES (SEC_ROLE_SEQ.nextval, 'ROLE_ADMIN','Administrator Role', 0);
INSERT INTO SEC_ROLE (ROL_ID, ROL_SHORTDESCRIPTION, ROL_LONGDESCRIPTION, VERSION) VALUES (SEC_ROLE_SEQ.nextval, 'ROLE_OFFICE_ALL_RIGHTS','Office User role with all rights granted.', 0);
INSERT INTO SEC_ROLE (ROL_ID, ROL_SHORTDESCRIPTION, ROL_LONGDESCRIPTION, VERSION) VALUES (SEC_ROLE_SEQ.nextval, 'ROLE_GUEST','Guest Role', 0);
INSERT INTO SEC_ROLE (ROL_ID, ROL_SHORTDESCRIPTION, ROL_LONGDESCRIPTION, VERSION) VALUES (SEC_ROLE_SEQ.nextval, 'ROLE_OFFICE_ONLY_VIEW','Office user with rights that granted only view of data.', 0);
INSERT INTO SEC_ROLE (ROL_ID, ROL_SHORTDESCRIPTION, ROL_LONGDESCRIPTION, VERSION) VALUES (SEC_ROLE_SEQ.nextval, 'ROLE_HEADOFFICE_USER','Headoffice User Role', 0);

insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Customers_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Customers_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Customers_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Customers_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Orders_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Orders_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Orders_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Orders_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'User_Edit_UsersOnly'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Customers_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ONLY_VIEW'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Orders_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ONLY_VIEW'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ONLY_VIEW'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Guest Group'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_GUEST'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Admin Group - user accounts'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Customers_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Headoffice Supervisor Group'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Guest Group'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Admin Group - user rights'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Customers_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Customers_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Customers_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Orders_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Orders_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Orders_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Orders_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Branch_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Branch_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Branch_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Branch_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Articles_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Articles_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Articles_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Articles_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Offices_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Offices_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Offices_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Offices_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Users_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Users_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Users_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Users_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Users_Search'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Branch_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Branch_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Branch_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Branch_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Articles_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Articles_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Articles_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Articles_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Offices_View'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Offices_New'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Offices_Edit'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Offices_Delete'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'User_View_UsersOnly'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'User_Edit_UsersOnly'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Security_Groups'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Security_Roles'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_ROLEGROUP(RLG_ID, GRP_ID, ROL_ID, VERSION) VALUES (SEC_ROLEGROUP_SEQ.nextval, (select GRP_ID from SEC_GROUP where GRP_SHORTDESCRIPTION = 'Security_Rights'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);



insert into SEC_USERROLE(URR_ID, USR_ID, ROL_ID, VERSION) values (SEC_USERROLE_SEQ.nextval, (select usr_id from SEC_USER where USR_LOGINNAME = 'guest'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_GUEST'), 0);
insert into SEC_USERROLE(URR_ID, USR_ID, ROL_ID, VERSION) values (SEC_USERROLE_SEQ.nextval, (select usr_id from SEC_USER where USR_LOGINNAME = 'user1'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_USERROLE(URR_ID, USR_ID, ROL_ID, VERSION) values (SEC_USERROLE_SEQ.nextval, (select usr_id from SEC_USER where USR_LOGINNAME = 'headoffice'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_USERROLE(URR_ID, USR_ID, ROL_ID, VERSION) values (SEC_USERROLE_SEQ.nextval, (select usr_id from SEC_USER where USR_LOGINNAME = 'admin'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_ADMIN'), 0);
insert into SEC_USERROLE(URR_ID, USR_ID, ROL_ID, VERSION) values (SEC_USERROLE_SEQ.nextval, (select usr_id from SEC_USER where USR_LOGINNAME = 'admin'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ALL_RIGHTS'), 0);
insert into SEC_USERROLE(URR_ID, USR_ID, ROL_ID, VERSION) values (SEC_USERROLE_SEQ.nextval, (select usr_id from SEC_USER where USR_LOGINNAME = 'admin'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_GUEST'), 0);
insert into SEC_USERROLE(URR_ID, USR_ID, ROL_ID, VERSION) values (SEC_USERROLE_SEQ.nextval, (select usr_id from SEC_USER where USR_LOGINNAME = 'admin'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ONLY_VIEW'), 0);
insert into SEC_USERROLE(URR_ID, USR_ID, ROL_ID, VERSION) values (SEC_USERROLE_SEQ.nextval, (select usr_id from SEC_USER where USR_LOGINNAME = 'admin'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_HEADOFFICE_USER'), 0);
insert into SEC_USERROLE(URR_ID, USR_ID, ROL_ID, VERSION) values (SEC_USERROLE_SEQ.nextval, (select usr_id from SEC_USER where USR_LOGINNAME = 'user2'), (select ROL_ID from SEC_ROLE where ROL_SHORTDESCRIPTION = 'ROLE_OFFICE_ONLY_VIEW'), 0);

