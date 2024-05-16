query 50107 "BC Item Ledger Entry"
{
    QueryType = Normal;
    Caption = 'BC Item Ledger Entry';

    elements
    {
        dataitem(ITEM_LEDGER_ENTRY; "Item Ledger Entry")
        {
            column(ENTRY_NO; "Entry No.") { }
            column(ITEM_NO; "Item No.") { }
            column(POSTING_DATE; "Posting Date") { }
            column(ENTRY_TYPE; "Entry Type") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(DESCRIPTION_; "Description") { }
            column(LOCATION_CODE; "Location Code") { }
            column(QUANTITY; "Quantity") { }
            column(REMAINING_QUANTITY; "Remaining Quantity") { }
            column(OPEN_; "Open") { }
            column(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
            column(DOCUMENT_LINE_NO; "Document Line No.") { }
            column(ORDER_NO; "Order No.") { }
            column(UNIT_OF_MEASURE_CODE; "Unit of Measure Code") { }
            column(SERIAL_NO; "Serial No.") { }
            //column(WARRANTY_CODE; "Warranty Code") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}