query 50101 "BC Change Log Entry"
{
    QueryType = Normal;
    Caption = 'BC Change Log Entry';

    elements
    {
        dataitem(CHANEG_LOG_ENTRY; "Change Log Entry")
        {
            column(ENTRY_NO; "Entry No.") { }
            column(DATE_AND_TIME; "Date and Time") { }
            column(USER_ID_; "User ID") { }
            column(TABLE_NO; "Table No.") { }
            column(FIELD_NO; "Field No.") { }
            column(TYPE_OF_CHANGE; "Type of Change") { }
            column(OLD_VALUE; "Old Value") { }
            column(NEW_VALUE; "New Value") { }
            column(PRIMARY_KEY_FILED_1_VALUE; "Primary Key Field 1 Value") { }
            column(PRIMARY_KEY_FILED_2_VALUE; "Primary Key Field 2 Value") { }
            column(PRIMARY_KEY_FILED_3_VALUE; "Primary Key Field 3 Value") { }
            column(TIME_; "Time") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}