page 50126 "BC Acc Schedule Name"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Acc. Schedule Name";
    Caption = 'BC Acc Schedule Name';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NAME_; "Name") { }
                field(DESCRIPTION_; "Description") { }
                //field(DEFAULT_COLUMN_LAYOUT; "Default Column Layout") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}