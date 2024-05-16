query 50104 "BC Detailed Vendor Ledg Entry"
{
    QueryType = Normal;
    Caption = 'BC Detailed Vendor Ledg Entry';

    elements
    {
        dataitem(DETAILED_VENDOR_LEDG_ENTRY; "Detailed Vendor Ledg. Entry")
        {
            column(ENTRY_NO; "Entry No.") { }
            column(VENDOR_LEDGER_ENTRY_NO; "Vendor Ledger Entry No.") { }
            column(ENTRY_TYPE; "Entry Type") { }
            column(POSTING_DATE; "Posting Date") { }
            column(DOCUMENT_TYPE; "Document Type") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(AMOUNT; "Amount") { }
            column(AMOUNT_LCY; "Amount (LCY)") { }
            column(APPLIED_VEND_LEDGER_ENTRY_NO; "Applied Vend. Ledger Entry No.") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}