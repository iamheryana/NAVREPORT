page 50115 "BC Item"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Item";
    Caption = 'BC Item';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NO_; "No.") { }
                field(DESCRIPTION_; "Description") { }
                field(DESCRIPTION_2; "Description 2") { }
                field(BASE_OUM; "Base Unit of Measure") { }
                field(SHELF_NO; "Shelf No.") { }
                field(UNIT_COST; "Unit Cost") { }
                field(BLOCKED; "Blocked") { }
                field(INVENTORY_VALUE_ZERO; "Inventory Value Zero") { }
                field(SALES_OUM; "Sales Unit of Measure") { }
                field(ITEM_CATEGORY_CODE; "Item Category Code") { }
                field(LAST_DATE_MODIFIED; "Last Date Modified") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}