query 50122 "BC Sales Line"
{
    QueryType = Normal;
    Caption = 'BC Sales Line';

    elements
    {
        dataitem(SALES_LINE; "Sales Line")
        {
            column(DOCUMENT_TYPE; "Document Type") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(LINE_NO; "Line No.") { }
            column(TYPE_; "Type") { }
            column(NO_; "No.") { }
            column(LOCATION_CODE; "Location Code") { }
            column(SHIPMENT_DATE; "Shipment Date") { }
            column(DESCRIPTION_; "Description") { }
            column(DESCRIPTION_2; "Description 2") { }
            column(UNIT_OF_MEASURE; "Unit of Measure") { }
            column(QUANTITY; "Quantity") { }
            column(OUTSTANDING_QUANTITY; "Outstanding Quantity") { }
            column(UNIT_PRICE; "Unit Price") { }
            column(LINE_DISCOUNT_AMOUNT; "Line Discount Amount") { }
            column(AMOUNT; "Amount") { }
            column(AMOUNT_INCLUDING_VAT; "Amount Including VAT") { }
            column(SHORTCUT_DIMENSION_1_CODE; "Shortcut Dimension 1 Code") { }
            column(SHORTCUT_DIMENSION_2_CODE; "Shortcut Dimension 2 Code") { }
            column(QUANTITY_SHIPPED; "Quantity Shipped") { }
            column(QUANTITY_INVOICED; "Quantity Invoiced") { }
            column(BILL_TO_CUSTOMER_NO; "Bill-to Customer No.") { }
            column(BLANKET_ORDER_NO; "Blanket Order No.") { }
            column(BLANKET_ORDER_LINE_NO; "Blanket Order Line No.") { }
            column(LINE_AMOUNT; "Line Amount") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            column(QUANTITY_BASE; "Quantity (Base)") { }
            column(QTY_SHIPPED_BASE; "Qty. Shipped (Base)") { }
            column(QTY_INVOICED_BASE; "Qty. Invoiced (Base)") { }
            dataitem(ITEM_CATEGORY; "Item Category")
            {
                DataItemLink = "Code" = SALES_LINE."Item Category Code";
                SqlJoinType = LeftOuterJoin;
                column(ITEM_CATEGORY_CODE; "Parent Category") { }
            }
            column(REQUESTED_DELIVERY_DATE; "Requested Delivery Date") { }
            //column(WARRANTY_CODE; "Warranty Code") { }
            column(QTY_TO_SHIP; "Qty. to Ship") { }
            column(SYSTEM_CREATED_AT; SystemCreatedAt) { }
            column(SYSTEM_CREATED_BY; SystemCreatedBy) { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}