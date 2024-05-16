page 50116 "BC Purch Comment Line"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Purch. Comment Line";
    Caption = 'BC Purch Comment Line';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(DOCUMENT_TYPE; "Document Type") { }
                field(NO_; "No.") { }
                field(DOCUMENT_LINE_NO; "Document Line No.") { }
                field(LINE_NO; "Line No.") { }
                field(COMMENT; "Comment") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}