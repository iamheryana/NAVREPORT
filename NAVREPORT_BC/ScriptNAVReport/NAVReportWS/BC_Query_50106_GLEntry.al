query 50106 "BC GL Entry"
{
    QueryType = Normal;
    Caption = 'BC GL Entry';

    elements
    {
        dataitem(GL_ENTRY; "G/L Entry")
        {
            column(ENTRY_NO; "Entry No.") { }
            column(GL_ACCOUNT_NO; "G/L Account No.") { }
            column(POSTING_DATE; "Posting Date") { }
            column(DOCUMENT_TYPE; "Document Type") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(DESCRIPTION_; Description) { }
            column(AMOUNT; "Amount") { }
            column(GLOBAL_DIMENSION_1_CODE; "Global Dimension 1 Code") { }
            column(GLOBAL_DIMENSION_2_CODE; "Global Dimension 2 Code") { }
            column(USER_ID; "User ID") { }
            column(SOURCE_CODE; "Source Code") { }
            column("JOURNAL_BATCH_NAME"; "Journal Batch Name") { }
            column("GEN_POSTING_TYPE"; "Gen. Posting Type") { }
            column("GEN_BUS_POSTING_GROUP"; "Gen. Bus. Posting Group") { }
            column("GEN_PROD_POSTING_GROUP"; "Gen. Prod. Posting Group") { }
            column("TRANSACTION_NO"; "Transaction No.") { }
            column(DEBIT_AMOUNT; "Debit Amount") { }
            column(CREDIT_AMOUNT; "Credit Amount") { }
            column(DOCUMENT_DATE; "Document Date") { }
            column(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
            column(SOURCE_TYPE; "Source Type") { }
            column(SOURCE_NO; "Source No.") { }
            column(NO_SERIES; "No. Series") { }
            column(VAT_BUS_POSTING_GROUP; "VAT Bus. Posting Group") { }
            column(VAT_PROD_POSTING_GROUP; "VAT Prod. Posting Group") { }
            column(CLOSE_INCOME_STATEMENT_DIM_ID; "Close Income Statement Dim. ID") { }
            column(IC_PARTNER_CODE; "IC Partner Code") { }
            column(REVERSED; "Reversed") { }
            column(REVERSED_BY_ENTRY_NO; "Reversed by Entry No.") { }
            column(REVERSED_ENTRY_NO; "Reversed Entry No.") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            column(LAST_MODIFIED_DATETIME; "Last Modified DateTime") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}