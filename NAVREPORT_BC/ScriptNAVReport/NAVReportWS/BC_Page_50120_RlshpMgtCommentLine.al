page 50120 "BC Rlshp Mgt Comment Line"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Rlshp. Mgt. Comment Line";
    Caption = 'BC Rlshp Mgt Comment Line';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {


                field(TABLE_NO; "Table Name") { }
                field(NO_; "No.") { }
                field(SUB_NO; "Sub No.") { }
                field(LINE_NO; "Line No.") { }
                field(DATE_; "Date") { }
                field(CODE_; "Code") { }
                field(COMMENT; "Comment") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}