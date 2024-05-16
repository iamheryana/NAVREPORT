page 50113 "BC Gen Journal Line"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Gen. Journal Line";
    Caption = 'BC Gen Journal Line';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(JOURNAL_TEMPLATE_NAME; "Journal Template Name") { }
                field(JOURNAL_BATCH_NAME; "Journal Batch Name") { }
                field(LINE_NO; "Line No.") { }
                field(ACCOUNT_TYPE; "Account Type") { }
                field(ACCOUNT_NO; "Account No.") { }
                field(POSTING_DATE; "Posting Date") { }
                field(DOCUMENT_NO; "Document No.") { }
                field(CURRENCY_CODE; "Currency Code") { }
                field(AMOUNT; "Amount") { }
                field(DESCRIPTION_; "Description") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}