page 50119 "BC Reservation Entry"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Reservation Entry";
    Caption = 'BC Reservation Entry';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(ENTRY_NO; "Entry No.") { }
                field(POSITIVE; "Positive") { }
                field(ITEM_NO; "Item No.") { }
                field(LOCATION_CODE; "Location Code") { }
                field(QUANTITY_BASE; "Quantity (Base)") { }
                field(RESERVATION_STATUS; "Reservation Status") { }
                field(SOURCE_ID; "Source ID") { }
                field(SOURCE_REF_NO; "Source Ref. No.") { }
                field(ITEM_LEDGER_ENTRY_NO; "Item Ledger Entry No.") { }
                field(EXPECTED_RECEIPT_DATE; "Expected Receipt Date") { }
                field(SERIAL_NO; "Serial No.") { }
                field(QTY_PER_UOM; "Qty. per Unit of Measure") { }
                field(QUANTITY; Quantity) { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}