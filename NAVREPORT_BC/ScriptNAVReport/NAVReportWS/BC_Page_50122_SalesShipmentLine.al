page 50122 "BC Sales Shipment Line"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Sales Shipment Line";
    Caption = 'BC Sales Shipment Line';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(DOCUMENT_NO; "Document No.") { }
                field(LINE_NO; "Line No.") { }
                field(TYPE_; "Type") { }
                field(NO_; "No.") { }
                field(DESCRIPTION_; "Description") { }
                field(DESCRIPTION_2; "Description 2") { }
                field(QUANTITY; "Quantity") { }
                field(SHORTCUT_DIMENSION_1_CODE; "Shortcut Dimension 1 Code") { }
                field(SHORTCUT_DIMENSION_2_CODE; "Shortcut Dimension 2 Code") { }
                field(QUANTITY_INVOICED; "Quantity Invoiced") { }
                field(ORDER_NO; "Order No.") { }
                field(ORDER_LINE_NO; "Order Line No.") { }
                field(POSTING_DATE; "Posting Date") { }
                field(DIMENSION_SET_ID; "Dimension Set ID") { }
                field(UNIT_PRICE; "Unit Price") { }
                field(UNIT_OF_MEASURE; "Unit of Measure") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}