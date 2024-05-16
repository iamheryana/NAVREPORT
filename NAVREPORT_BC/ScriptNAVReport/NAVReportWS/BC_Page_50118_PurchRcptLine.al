page 50118 "BC Purch Rcpt Line"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Purch. Rcpt. Line";
    Caption = 'BC Purch Rcpt Line';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(DOCUMENT_NO; "Document No.") { }
                field(LINE_NO; "Line No.") { }
                field(TYPE_; "Type") { }
                field(NO_; "No.") { }
                field(DESCRIPTION_; "Description") { }
                field(QUANTITY; "Quantity") { }
                field(UNIT_COST_LCY; "Unit Cost (LCY)") { }
                field(LINE_DISCOUNT_PCN; "Line Discount %") { }
                field(QUANTITY_INVOICED; "Quantity Invoiced") { }
                field(ORDER_NO; "Order No.") { }
                field(ORDER_LINE_NO; "Order Line No.") { }
                field(POSTING_DATE; "Posting Date") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}