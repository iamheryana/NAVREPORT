query 50119 "BC Sales Invoice Header"
{
    QueryType = Normal;
    Caption = 'BC Sales Invoice Header';

    elements
    {
        dataitem(SALES_INVOICE_HEADER; "Sales Invoice Header")
        {
            column(NO_; "No.") { }
            column(SELL_T0_CUSTOMER_NO; "Sell-to Customer No.") { }
            column(BILL_TO_CUSTOMER_NO; "Bill-to Customer No.") { }
            column(BILL_TO_NAME; "Bill-to Name") { }
            column(BILL_TO_NAME_2; "Bill-to Name 2") { }
            column(BILL_TO_ADDRESS; "Bill-to Address") { }
            column(BILL_TO_ADDRESS_2; "Bill-to Address 2") { }
            column(YOUR_REFERENCE; "Your Reference") { }
            column(ORDER_DATE; "Order Date") { }
            column(POSTING_DATE; "Posting Date") { }
            column(POSTING_DESCRIPTION; "Posting Description") { }
            column(PAYMENT_TERMS_CODE; "Payment Terms Code") { }
            column(SHORTCUT_DIMENSION_1_CODE; "Shortcut Dimension 1 Code") { }
            column(SHORTCUT_DIMENSION_2_CODE; "Shortcut Dimension 2 Code") { }
            column(CUSTOMER_POSTING_GROUP; "Customer Posting Group") { }
            column(CURRENCY_CODE; "Currency Code") { }
            column(CURRENCY_FACTOR; "Currency Factor") { }
            column(SALESPERSON_CODE; "Salesperson Code") { }
            column(ORDER_NO; "Order No.") { }
            column(SELL_TO_CUSTOMER_NAME; "Sell-to Customer Name") { }
            column(SELL_TO_CUSTOMER_NAME_2; "Sell-to Customer Name 2") { }
            column(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
            column(TAX_AREA_CODE; "Tax Area Code") { }
            column(PREPAYMENT_ORDER_NO; "Prepayment Order No.") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }

            //column(FAKTUR_PAJAK_NO; "Faktur Pajak No.") { } 
            //column(BILL_TO_ADDRESS_3; "Bill-to Address 3") { }
            //column(BILL_TO_ADDRESS_4; "Bill-to Address 4") { }
            column(GEN_BUS_POSTING_GROUP; "Gen. Bus. Posting Group") { }
            column(DUE_DATE; "Due Date") { }
            column(SYSTEM_CREATED_AT; SystemCreatedAt) { }
            column(SYSTEM_CREATED_BY; SystemCreatedBy) { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}