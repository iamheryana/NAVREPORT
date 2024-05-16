query 50110 "BC Purch Inv Header"
{
    QueryType = Normal;
    Caption = 'BC Purch Inv Header';

    elements
    {
        dataitem(PURCH_INV_HEADER; "Purch. Inv. Header")
        {
            column(NO_; "No.") { }
            column(PAY_TO_VENDOR_NO; "Pay-to Vendor No.") { }
            column(PAY_TO_NAME; "Pay-to Name") { }
            column(POSTING_DATE; "Posting Date") { }
            column(CURRENCY_CODE; "Currency Code") { }
            column(CURRENCY_FACTOR; "Currency Factor") { }
            column(VENDOR_INVOICE_NO; "Vendor Invoice No.") { }
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