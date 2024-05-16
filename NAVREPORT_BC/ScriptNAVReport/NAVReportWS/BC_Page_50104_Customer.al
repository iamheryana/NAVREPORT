page 50104 "BC Customer"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Customer";
    Caption = 'BC Customer';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NO_; "No.") { }
                field(NAME_; Name) { }
                field(SEARCH_NAME; "Search Name") { }
                field(NAME_2; "Name 2") { }
                field(ADDRESS; "Address") { }
                field(ADDRESS_2; "Address 2") { }
                field(CITY; "City") { }
                field(CONTACT; "Contact") { }
                field(PHONE_NO; "Phone No.") { }
                field(GLOBAL_DIMENSION_1_CODE; "Global Dimension 1 Code") { }
                field(CUSTOMER_POSTING_GROUP; "Customer Posting Group") { }
                field(PAYMENT_TERMS_CODE; "Payment Terms Code") { }
                field(SALESPERSON_CODE; "Salesperson Code") { }
                field(BLOCKED; "Blocked") { }
                field(FAX_NO; "Fax No.") { }
                field(VAT_REGISTRATION_NO; "VAT Registration No.") { }
                field(GEN_BUS_POSTING_GROUP; "Gen. Bus. Posting Group") { }
                field(POST_CODE; "Post Code") { }
                field(COUNTY; "County") { }
                field(E_MAIL; "E-Mail") { }
                field(TAX_AREA_CODE; "Tax Area Code") { }
                field(VAT_BUS_POSTING_GROUP; "VAT Bus. Posting Group") { }
                field(RESPONSIBILITY_CENTER; "Responsibility Center") { }
                //field(IRD No; "IRD No") { }
                //field(ADDRESS_3; "Address 3") { }
                //field(ADDRESS_4; "Address 4") { }

                field(LAST_DATE_MODIFIED; "Last Date Modified") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}