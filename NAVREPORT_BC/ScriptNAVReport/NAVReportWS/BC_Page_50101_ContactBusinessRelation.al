page 50101 "BC Contact Business Relation"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Contact Business Relation";
    Caption = 'BC Contact Business Relation';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(CONTACT_NO; "Contact No.") { }
                field(BUSINESS_RELATION_CODE; "Business Relation Code") { }
                field(NO_; "No.") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}