page 50123 "BC Transfer Header"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Transfer Header";
    Caption = 'BC Transfer Header';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NO_; "No.") { }
                field(TRANSFER_FROM_CODE; "Transfer-from Code") { }
                field(TRANSFER_TO_CODE; "Transfer-to Code") { }
                field(POSTING_DATE; "Posting Date") { }
                field(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}