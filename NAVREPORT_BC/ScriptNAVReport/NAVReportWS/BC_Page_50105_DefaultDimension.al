page 50105 "BC Default Dimension"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Default Dimension";
    Caption = 'BC Default Dimension';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(TABLE_ID; "Table ID") { }
                field(NO_; "No.") { }
                field(DIMENSION_CODE; "Dimension Code") { }
                field(DIMENSION_VALUE_CODE; "Dimension Value Code") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}