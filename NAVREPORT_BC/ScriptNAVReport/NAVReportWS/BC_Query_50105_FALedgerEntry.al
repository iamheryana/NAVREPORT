query 50105 "BC FA Ledger Entry"
{
    QueryType = Normal;
    Caption = 'BC FA Ledger Entry';

    elements
    {
        dataitem(FA_LEDGER_ENTRY; "FA Ledger Entry")
        {
            column(ENTRY_NO; "Entry No.") { }
            column(FA_NO; "FA No.") { }
            column(FA_POSTING_DATE; "FA Posting Date") { }
            column(FA_POSTING_CATEGORY; "FA Posting Category") { }
            column(FA_POSTING_TYPE; "FA Posting Type") { }
            column(AMOUNT; "Amount") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}