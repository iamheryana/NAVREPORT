query 50124 "BC Value Entry"
{
    QueryType = Normal;
    Caption = 'BC Value Entry';

    elements
    {
        dataitem(VALUE_ENTRY; "Value Entry")
        {


            column(ENTRY_NO; "Entry No.") { }
            column(ITEM_LEDGER_ENTRY_NO; "Item Ledger Entry No.") { }
            column(COST_AMOUNT_ACTUAL; "Cost Amount (Actual)") { }
            column(COST_AMOUNT_EXPECTED; "Cost Amount (Expected)") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}