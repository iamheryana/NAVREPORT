query 50127 "BC Contact Job Responsibility"
{
    QueryType = Normal;
    Caption = 'BC Contact Job Responsibility';

    elements
    {
        dataitem(CONTACT_JOB_RESPONSIBILITY; "Contact Job Responsibility")
        {
            column(CONTACT_NO; "Contact No.") { }
            column(JOB_RESPONSIBILITY_CODE; "Job Responsibility Code") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}