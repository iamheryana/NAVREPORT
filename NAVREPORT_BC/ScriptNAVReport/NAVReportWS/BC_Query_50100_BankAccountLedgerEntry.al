query 50100 "BC Bank Account Ledger Entry"
{
    QueryType = Normal;
    Caption = 'BC Bank Account Ledger Entry';

    elements
    {
        dataitem(BANK_ACCOUNT_LEDGER_ENTRY; "Bank Account Ledger Entry")
        {
            column(ENTRY_NO; "Entry No.") { }
            column(BANK_ACCOUNT_NO; "Bank Account No.") { }
            column(POSTING_DATE; "Posting Date") { }
            column(DOCUMENT_TYPE; "Document Type") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(DESCRIPTION_; "Description") { }
            column(CURRENCY_CODE; "Currency Code") { }
            column(AMOUNT; "Amount") { }
            column(DEBIT_AMOUNT; "Debit Amount") { }
            column(CREDIT_AMOUNT; "Credit Amount") { }
            column(REVERSED; "Reversed") { }
            column(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}