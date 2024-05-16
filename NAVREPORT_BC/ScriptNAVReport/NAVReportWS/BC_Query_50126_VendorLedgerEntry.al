query 50126 "BC Vendor Ledger Entry"
{
    QueryType = Normal;
    Caption = 'BC Vendor Ledger Entry';

    elements
    {
        dataitem(VENDOR_LEDGER_ENTRY; "Vendor Ledger Entry")
        {

            column(ENTRY_NO; "Entry No.") { }
            column(VENDOR_NO; "Vendor No.") { }
            column(POSTING_DATE; "Posting Date") { }
            column(DOCUMENT_TYPE; "Document Type") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(CURRENCY_CODE; "Currency Code") { }
            column(DUE_DATE; "Due Date") { }
            column(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}