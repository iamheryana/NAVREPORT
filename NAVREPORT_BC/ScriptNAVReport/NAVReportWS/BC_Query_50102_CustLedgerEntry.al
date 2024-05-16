query 50102 "BC Cust Ledger Entry"
{
    QueryType = Normal;
    Caption = 'BC Cust Ledger Entry';

    elements
    {
        dataitem(CUST_LEDGER_ENTRY; "Cust. Ledger Entry")
        {
            column(ENTRY_NO; "Entry No.") { }
            column(CUSTOMER_NO; "Customer No.") { }
            column(POSTING_DATE; "Posting Date") { }
            column(DOCUMENT_TYPE; "Document Type") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(CURRENCY_CODE; "Currency Code") { }
            column(GLOBAL_DIMENSION_1_CODE; "Global Dimension 1 Code") { }
            column(DUE_DATE; "Due Date") { }
            column(CLOSED_BY_ENTRY_NO; "Closed by Entry No.") { }
            column(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
            column(DESCRIPTION_; "Description") { }
            column(REMAINING_AMOUNT; "Remaining Amount") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}