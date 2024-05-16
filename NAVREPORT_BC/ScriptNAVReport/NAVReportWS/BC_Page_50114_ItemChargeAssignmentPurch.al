page 50114 "BC Item Charge Assign - Purch"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Item Charge Assignment (Purch)";
    Caption = 'BC Item Charge Assign - Purch';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(DOCUMENT_TYPE; "Document Type") { }
                field(DOCUMENT_NO; "Document No.") { }
                field(DOCUMENT_LINE_NO; "Document Line No.") { }
                field(LINE_NO; "Line No.") { }
                field(QTY_ASSIGNED; "Qty. Assigned") { }
                field(UNIT_COST; "Unit Cost") { }
                field(APPLIES_TO_DOC_TYPE; "Applies-to Doc. Type") { }
                field(APPLIES_TO_DOC_NO; "Applies-to Doc. No.") { }
                field(APPLIES_TO_DOC_LINE_NO; "Applies-to Doc. Line No.") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}