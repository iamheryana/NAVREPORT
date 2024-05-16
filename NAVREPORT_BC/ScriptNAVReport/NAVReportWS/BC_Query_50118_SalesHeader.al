query 50118 "BC Sales Header"
{
    QueryType = Normal;
    Caption = 'BC Sales Header';

    elements
    {
        dataitem(SALES_HEADER; "Sales Header")
        {
            column(DOCUMENT_TYPE; "Document Type") { }
            column(NO_; "No.") { }
            column(SELL_T0_CUSTOMER_NO; "Sell-to Customer No.") { }
            column(BILL_TO_CUTOMER_NO; "Bill-to Customer No.") { }
            column(BILL_TO_NAME; "Bill-to Name") { }
            column(BILL_TO_NAME_2; "Bill-to Name 2") { }
            column(BILL_TO_ADDRESS; "Bill-to Address") { }
            column(BILL_TO_ADDRESS_2; "Bill-to Address 2") { }
            column(YOUR_REFERENCE; "Your Reference") { }
            column(SHIP_TO_CODE; "Ship-to Code") { }
            column(SHIP_TO_NAME; "Ship-to Name") { }
            column(SHIP_TO_NAME_2; "Ship-to Name 2") { }
            column(SHIP_TO_ADDRESS; "Ship-to Address") { }
            column(SHIP_TO_ADDRESS_2; "Ship-to Address 2") { }
            column(ORDER_DATE; "Order Date") { }
            column(POSTING_DATE; "Posting Date") { }
            column(SHIPMENT_DATE; "Shipment Date") { }
            column(POSTING_DESCRIPTION; "Posting Description") { }
            column(PAYMENT_TERMS_CODE; "Payment Terms Code") { }
            column(PAYMENT_DISCOUNT_PCN; "Payment Discount %") { }
            column(LOCATION_CODE; "Location Code") { }
            column(SHORTCUT_DIMENSION_1_CODE; "Shortcut Dimension 1 Code") { }
            column(SHORTCUT_DIMENSION_2_CODE; "Shortcut Dimension 2 Code") { }
            column(CUSTOMER_POSTING_GROUP; "Customer Posting Group") { }
            column(CURRENCY_CODE; "Currency Code") { }
            column(CURRENCY_FACTOR; "Currency Factor") { }
            column(SALESPERSON_CODE; "Salesperson Code") { }
            column(SELL_TO_CUSTOMER_NAME; "Sell-to Customer Name") { }
            column(SELL_TO_CUSTOMER_NAME_2; "Sell-to Customer Name 2") { }
            column(SELL_TO_ADDRESS; "Sell-to Address") { }
            column(SELL_TO_ADDRESS_2; "Sell-to Address 2") { }
            column(SELL_TO_COUNTY; "Sell-to County") { }
            column(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
            column(PACKAGE_TRACKING_NO; "Package Tracking No.") { }
            column(NO_SERIES; "No. Series") { }
            column(STATUS_DOC; "Status") { }
            column(PREPAYMENT_PCN; "Prepayment %") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            column(RESPONSIBILITY_CENTER; "Responsibility Center") { }
            column(REQUESTED_DELIVERY_DATE; "Requested Delivery Date") { }
            column(PROMISED_DELIVERY_DATE; "Requested Delivery Date") { }


            //column(RELEASED_DATE; "Released Date") { }
            //column(RELEASED_BY; "Released By") { }

            //column(BILL_TO_ADDRESS_3; "Bill-to Address 3") { }
            //column(BILL_TO_ADDRESS_4; "Bill-to Address 4") { }
            //column(SHIP_TO_ADDRESS_3; "Ship-to Address 3") { }
            //column(SHIP_TO_ADDRESS_4; "Ship-to Address 4") { }
            //column(SELL_TO_ADDRESS_3; "Sell-to Address 3") { }
            //column(SELL_TO_ADDRESS_4; "Sell-to Address 4") { }

            column(SELL_TO_CONTACT; "Sell-to Contact") { }
            column(SYSTEM_CREATED_AT; SystemCreatedAt) { }
            column(SYSTEM_CREATED_BY; SystemCreatedBy) { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

        }
    }
}