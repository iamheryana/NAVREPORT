page 50111 "BC Fixed Asset"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Fixed Asset";
    Caption = 'BC Fixed Asset';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NO_; "No.") { }
                field(DESCRIPTION_; "Description") { }
                field(FA_CLASS_CODE; "FA Class Code") { }
                field(GLOBAL_DIMENSION_1_CODE; "Global Dimension 1 Code") { }
                field(FA_LOCATION_CODE; "FA Location Code") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}