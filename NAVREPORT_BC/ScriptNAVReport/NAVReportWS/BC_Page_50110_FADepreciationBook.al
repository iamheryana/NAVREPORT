page 50110 "BC FA Depreciation Book"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "FA Depreciation Book";
    Caption = 'BC FA Depreciation Book';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(FA_NO; "FA No.") { }
                field(DEPRECIATION_BOOK_CODE; "Depreciation Book Code") { }
                field(DEPRECIATION_STARTING_DATE; "Depreciation Starting Date") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}