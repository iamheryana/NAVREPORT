page 50124 "BC Transfer Line"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Transfer Line";
    Caption = 'BC Transfer Line';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(DOCUMENT_NO; "Document No.") { }
                field(LINE_NO; "Line No.") { }
                field(ITEM_NO; "Item No.") { }
                field(QUANTITY; "Quantity") { }
                field(UNIT_OF_MEASURE; "Unit of Measure") { }
                field(QUANTITY_SHIPPED; "Quantity Shipped") { }
                field(QUANTITY_RECEIVED; "Quantity Received") { }
                field(DESCRIPTION_; "Description") { }
                field(IN_TRANSIT_CODE; "In-Transit Code") { }
                field(TRANSFER_FROM_CODE; "Transfer-from Code") { }
                field(TRANSFER_TO_CODE; "Transfer-to Code") { }
                field(COMPLETELY_SHIPPED; "Completely Shipped") { }
                field(COMPLETELY_RECEIVED; "Completely Received") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}