page 50102 "BC Contact Mailing Group"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Contact Mailing Group";
    Caption = 'BC Contact Mailing Group';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(CONTACT_NO; "Contact No.") { }
                field(MAILING_GROUP_CODE; "Mailing Group Code") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}