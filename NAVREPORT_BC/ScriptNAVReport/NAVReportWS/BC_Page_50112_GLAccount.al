page 50112 "BC GL Account"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "G/L Account";
    Caption = 'BC GL Account';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NO_; "No.") { }
                field(NAME_; "Name") { }
                field(INCOME_BALANCE; "Income/Balance") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}