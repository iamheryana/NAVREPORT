page 50107 "BC E-Faktur Master Mapping - n"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Dimension Value";
    Caption = 'BC E-Faktur Master Mapping - n';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                //field(TYPE_; "Type") { }
                //field(NO_; "No.") { }
                //field(TAX_NAME; "Tax Name") { }
                //field(TAX_ADDRESS_1; "Tax Address 1") { }
                //field(TAX_ADDRESS_2; "Tax Address 2") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}