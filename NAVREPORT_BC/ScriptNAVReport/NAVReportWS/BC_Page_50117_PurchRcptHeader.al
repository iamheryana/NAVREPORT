page 50117 "BC Purch Rcpt Header"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Purch. Rcpt. Header";
    Caption = 'BC Purch Rcpt Header';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(NO_; "No.") { }
                field(PAY_TO_VENDOR_NO; "Pay-to Vendor No.") { }
                field(PAY_TO_NAME; "Pay-to Name") { }
                field(ORDER_DATE; "Order Date") { }
                field(POSTING_DATE; "Posting Date") { }
                field(ORDER_NO; "Order No.") { }
                field(VENDOR_SHIPMENT_NO; "Vendor Shipment No.") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}