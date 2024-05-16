query 50125 "BC VAT Entry"
{
    QueryType = Normal;
    Caption = 'BC VAT Entry';

    elements
    {
        dataitem(VAT_ENTRY; "VAT Entry")
        {

            column(ENTRY_NO; "Entry No.") { }
            column(POSTING_DATE; "Posting Date") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(DOCUMENT_TYPE; "Document Type") { }
            column(TYPE_; "Type") { }
            column(BASE; "Base") { }
            column(AMOUNT; "Amount") { }
            column(BILL_TO_PAY_TO_NO; "Bill-to/Pay-to No.") { }
            column(VAT_BUS_POSTING_GROUP; "VAT Bus. Posting Group") { }
            column(VAT_PROD_POSTING_GROUP; "VAT Prod. Posting Group") { }
            //column(TAX_INVOICE_NO; "Tax Invoice No.") { }
            //column(TAX_INVOICE_DATE; "Tax Invoice Date") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}