page 50100 "BC Bank Account"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Bank Account";
    Caption = 'BC Bank Account';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NO_; "No.") { }
                field(NAME_; "Name") { }
                field(BANK_ACCOUNT_NO; "Bank Account No.") { }
                field(CURRENCY_CODE; "Currency Code") { }
                //field(PUBLISH_TO_CUSTOMER; "Publish to Costomer") { }

                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}