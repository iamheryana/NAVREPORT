query 50117 "BC Sales Header Archive"
{
    QueryType = Normal;
    Caption = 'BC Sales Header Archive';

    elements
    {
        dataitem(SALES_HEADER_ARCHIVE; "Sales Header Archive")
        {
            column(DOCUMENT_TYPE; "Document Type") { }
            column(NO_; "No.") { }
            column(DOC_NO_OCCORRENCE; "Doc. No. Occurrence") { }
            column(VERSION_NO; "Version No.") { }
            column(SELL_TO_CUSTOMER_NO; "Sell-to Customer No.") { }
            column(BILL_TO_CUSTOMER_NO; "Bill-to Customer No.") { }
            column(BILL_TO_NAME; "Bill-to Name") { }
            column(ORDER_DATE; "Order Date") { }
            column(PAYMENT_TERM_CODE; "Payment Terms Code") { }
            column(SHORTCUT_DIMENSION_1_CODE; "Shortcut Dimension 1 Code") { }
            column(CURRENCY_CODE; "Currency Code") { }
            column(CURRENCY_FACTOR; "Currency Factor") { }
            column(SALESPERSON_CODE; "Salesperson Code") { }
            column(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
            column(REQUESTED_DELIVERY_DATE; "Requested Delivery Date") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}