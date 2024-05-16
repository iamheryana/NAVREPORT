query 50112 "BC Purchase Header"
{
    QueryType = Normal;
    Caption = 'BC Purchase Header';

    elements
    {
        dataitem(PURCHASE_HEADER; "Purchase Header")
        {

            column(DOCUMENT_TYPE; "Document Type") { }
            column(NO_; "No.") { }
            column(BUY_FROM_VENDOR_NO; "Buy-from Vendor No.") { }
            column(PAY_TO_VENDOR_NO; "Pay-to Vendor No.") { }
            column(PAY_TO_NAME; "Pay-to Name") { }
            column(PAY_TO_NAME_2; "Pay-to Name 2") { }
            column(PAY_TO_ADDRESS; "Pay-to Address") { }
            column(PAY_TO_ADDRESS_2; "Pay-to Address 2") { }
            column(PAY_TO_CITY; "Pay-to City") { }
            column(PAY_TO_CONTACT; "Pay-to Contact") { }
            column(YOUR_REFERENCE; "Your Reference") { }
            column(SHIP_TO_ADDRESS; "Ship-to Address") { }
            column(SHIP_TO_ADDRESS_2; "Ship-to Address 2") { }
            column(SHIP_TO_CITY; "Ship-to City") { }
            column(SHIP_TO_CONTACT; "Ship-to Contact") { }
            column(ORDER_DATE; "Order Date") { }
            column(POSTING_DATE; "Posting Date") { }
            column(EXPECTED_RECEIPT_DATE; "Expected Receipt Date") { }
            column(POSTING_DESCRIPTION; "Posting Description") { }
            column(PAYMENT_TERMS_CODE; "Payment Terms Code") { }
            column(DUE_DATE; "Due Date") { }
            column(PAYMENT_DISCOUNT_PCN; "Payment Discount %") { }
            column(SHIPMENT_METHOD_CODE; "Shipment Method Code") { }
            column(LOCATION_CODE; "Location Code") { }
            column(SHORTCUT_DIMENSION_1_CODE; "Shortcut Dimension 1 Code") { }
            column(SHORTCUT_DIMENSION_2_CODE; "Shortcut Dimension 2 Code") { }
            column(VENDOR_POSTING_DATE; "Vendor Posting Group") { }
            column(CURRENCY_CODE; "Currency Code") { }
            column(CURRENCY_FACTOR; "Currency Factor") { }
            column(PURCHASER_CODE; "Purchaser Code") { }
            column(VENDOR_ORDER_NO; "Vendor Order No.") { }
            column(VENDOR_SHIPMENT_NO; "Vendor Shipment No.") { }
            column(VENDOR_INVOICE_NO; "Vendor Invoice No.") { }
            column(VENDOR_CR_MEMO_NO; "Vendor Cr. Memo No.") { }
            column(GEN_BUS_POSTING_GRPUP; "Gen. Bus. Posting Group") { }
            column(BUY_FROM_VENDOR_NAME; "Buy-from Vendor Name") { }
            column(BUY_FROM_VENDOR_NAME_2; "Buy-from Vendor Name 2") { }
            column(BUY_FROM_ADDRESS; "Buy-from Address") { }
            column(BUY_FROM_ADDRESS_2; "Buy-from Address 2") { }
            column(BUY_FROM_CITY; "Buy-from City") { }
            column(BUY_FROM_CONTACT; "Buy-from Contact") { }
            column(BUY_FROM_POST_CODE; "Buy-from Post Code") { }
            column(SHIP_TO_POST_CODE; "Ship-to Post Code") { }
            column(DOCUMENT_DATE; "Document Date") { }
            column(NO_SERIES; "No. Series") { }
            column(VAT_BUS_POSTING_GROUP; "VAT Bus. Posting Group") { }
            column(STATUS_; "Status") { }
            column(PREPAYMENT_PCN; "Prepayment %") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            column(RESPONSIBILITY_CENTER; "Responsibility Center") { }
            column(VENDOR_AUTHORIZATION_NO; "Vendor Authorization No.") { }
            column(ASSIGNED_USER_ID; "Assigned User ID") { }

            //field(VALIDATION_DATE; "Validation Date") { }
            //column(PAY_TO_ADDRESS_3; "Pay-to Address 3") { }
            //column(PAY_TO_ADDRESS_4; "Pay-to Address 4") { }
            //column(SHIP_TO_ADDRESS_3; "Ship-to Address 3") { }
            //column(SHIP_TO_ADDRESS_4; "Ship-to Address 4") { }
            //column(BUY_FROM_ADDRESS_3; "Buy-from Address 3") { }
            //column(BUY_FROM_ADDRESS_4; "Buy-from Address 4") { }

            column(SHIP_TO_NAME; "Ship-to Name") { }
            column(PAY_TO_POST_CODE; "Pay-to Post Code") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}