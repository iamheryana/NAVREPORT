page 50121 "BC Sales Shipment Header"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Sales Shipment Header";
    Caption = 'BC Sales Shipment Header';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NO_; "No.") { }
                field(SELL_TO_CUSTOMER_NO; "Sell-to Customer No.") { }
                field(BILL_TO_CUSTOMER_NO; "Bill-to Customer No.") { }
                field(BILL_TO_NAME; "Bill-to Name") { }
                field(BILL_TO_NAME_2; "Bill-to Name 2") { }
                field(POSTING_DATE; "Posting Date") { }
                field(POSTING_DESCRIPTION; "Posting Description") { }
                field(CURRENCY_CODE; "Currency Code") { }
                field(CURRENCY_FACTOR; "Currency Factor") { }
                field(SALESPERSON_CODE; "Salesperson Code") { }
                field(ORDER_NO; "Order No.") { }
                field(SELL_TO_CUSTOMER_NAME; "Sell-to Customer Name") { }
                field(SELL_TO_CUSTOMER_NAME_2; "Sell-to Customer Name 2") { }
                field(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
                field(DIMENSION_SET_ID; "Dimension Set ID") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}