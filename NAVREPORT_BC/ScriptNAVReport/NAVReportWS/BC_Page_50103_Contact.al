page 50103 "BC Contact"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Contact";
    Caption = 'BC Contact';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NO_; "No.") { }
                field(NAME_; "Name") { }
                field(SEARCH_NAME; "Search Name") { }
                field(ADDRESS_; "Address") { }
                field(ADDRESS_2; "Address 2") { }
                field(PHONE_NO; "Phone No.") { }
                field(SALESPERSON_CODE; "Salesperson Code") { }
                field(FAX_NO; "Fax No.") { }
                field(EMAIL; "E-mail") { }
                field(TYPE_; "Type") { }
                field(COMPANY_NO; "Company No.") { }
                field(COMPANY_NAME; "Company Name") { }
                field(FIRST_NAME; "First Name") { }
                field(MIDDLE_NAME; "Middle Name") { }
                field(SURNAME; "Surname") { }
                field(JOB_TITLE; "Job Title") { }
                field(MOBILE_PHONE_NO; "Mobile Phone No.") { }
                field(ORGANIZATIONAL_LEVEL_CODE; "Organizational Level Code") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}