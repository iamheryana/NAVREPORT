page 50127 "BC Acc Schedule Line"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Acc. Schedule Line";
    Caption = 'BC Acc Schedule Line';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                field(SCHEDULE_NAME; "Schedule Name") { }
                field(LINE_NO; "Line No.") { }
                field(ROW_NO; "Row No.") { }
                field(DESCRIPTION_; "Description") { }
                field(TOTALING; "Totaling") { }
                field(TOTALING_TYPE; "Totaling Type") { }
                field(DIMENSION_1_TOTALING; "Dimension 1 Totaling") { }
                field(DIMENSION_2_TOTALING; "Dimension 2 Totaling") { }
                field(BOLD; "Bold") { }
                field(ITALIC; "Italic") { }
                field(UNDERLINE; "Underline") { }
                field(SHOW_OPPOSITE_SIGN; "Show Opposite Sign") { }
                field(DOUBLE_UNDERLINE; "Double Underline") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}