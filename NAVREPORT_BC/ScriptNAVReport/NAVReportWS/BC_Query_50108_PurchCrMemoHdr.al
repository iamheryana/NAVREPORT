query 50108 "BC Purch Cr Memo Hdr"
{
    QueryType = Normal;
    Caption = 'BC Purch Cr Memo Hdr';

    elements
    {
        dataitem(PURCH_CR_MEMO_HDR; "Purch. Cr. Memo Hdr.")
        {
            column(NO_; "No.") { }
            column(PAY_TO_VENDOR_NO; "Pay-to Vendor No.") { }
            column(PAY_TO_NAME; "Pay-to Name") { }
            column(POSTING_DATE; "Posting Date") { }
            column(CURRENCY_CODE; "Currency Code") { }
            column(CURRENCY_FACTOR; "Currency Factor") { }
            column(APPLIES_TO_DOC_TYPE; "Applies-to Doc. Type") { }
            column(APPLIES_TO_DOC_NO; "Applies-to Doc. No.") { }
            column(VENDOR_CR_MEMO_NO; "Vendor Cr. Memo No.") { }
            column(VAT_BUS_POSTING_GROUP; "VAT Bus. Posting Group") { }
            column(PREPAYMENT_ORDER_NO; "Prepayment Order No.") { }
            //column(VENDOR_TAX_INVOICE_NO; "Vendor Tax Invoice No.") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}