page 50106 "BC Dimension Set Entry"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Dimension Set Entry";
    Caption = 'BC Dimension Set Entry';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(DIMENSION_SET_ID; "Dimension Set ID") { }
                field(DIMENSION_CODE; "Dimension Code") { }
                field(DIMENSION_VALUE_CODE; "Dimension Value Code") { }
                field(DIMENSION_VALUE_ID; "Dimension Value ID") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}