page 50125 "BC Vendor"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Vendor";
    Caption = 'BC Vendor';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NO_; "No.") { }
                field(NAME_; "Name") { }
                field(ADDRESS_; "Address") { }
                field(ADDRESS_2; "Address 2") { }
                field(PHONE_NO; "Phone No.") { }
                field(PAYMENT_TERMS_CODE; "Payment Terms Code") { }
                field(COUNTRY_REGION_CODE; "Country/Region Code") { }
                field(VAT_REGISTRATION_NO; "VAT Registration No.") { }
                //field(TAX_NAME; "Tax Name") { }
                //field(TAX_ADDRESS_1; "Tax Address 1") { }
                //field(TAX_ADDRESS_2; "Tax Address 2") { }

                field(FAX_NO; "Fax No.") { }
                field(EMAIL; "E-Mail") { }
                field(CONTACT; "Contact") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}