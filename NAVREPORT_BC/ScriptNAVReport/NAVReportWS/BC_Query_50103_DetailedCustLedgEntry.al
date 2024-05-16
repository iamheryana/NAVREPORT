query 50103 "BC Detailed Cust Ledg Entry"
{
    QueryType = Normal;
    Caption = 'BC Detailed Cust Ledg Entry';

    elements
    {
        dataitem(DETAILED_CUST_LEDG_ENTRY; "Detailed Cust. Ledg. Entry")
        {
            column(ENTRY_NO; "Entry No.") { }
            column(CUST_LEDGER_ENTRY_NO; "Cust. Ledger Entry No.") { }
            column(ENTRY_TYPE; "Entry Type") { }
            column(POSTING_DATE; "Posting Date") { }
            column(DOCUMENT_TYPE; "Document Type") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(AMOUNT; "Amount") { }
            column(AMOUNT_LCY; "Amount (LCY)") { }
            column(APPLIED_CUST_LEDGER_ENTRY_NO; "Applied Cust. Ledger Entry No.") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}