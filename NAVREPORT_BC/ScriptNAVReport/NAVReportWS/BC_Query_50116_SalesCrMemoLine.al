query 50116 "BC Sales Credit Memo Line"
{
    QueryType = Normal;
    Caption = 'BC Sales Credit Memo Line';

    elements
    {
        dataitem(SALES_CR_MEMO_LINE; "Sales Cr.Memo Line")
        {
            column(DOCUMENT_NO; "Document No.") { }
            column(LINE_NO; "Line No.") { }
            column(TYPE_; "Type") { }
            column(NO_; "No.") { }
            column(DESCRIPTION_; "Description") { }
            column(DESCRIPTION_2; "Description 2") { }
            column(UNIT_OF_MEASURE; "Unit of Measure") { }
            column(QUANTITY; Quantity) { }
            column(UNIT_PRICE; "Unit Price") { }
            column(AMOUNT; "Amount") { }
            column(AMOUNT_INCLUDING_VAT; "Amount Including VAT") { }
            column(SHORTCUT_DIMENSION_1_CODE; "Shortcut Dimension 1 Code") { }
            column(SHORTCUT_DIMENSION_2_CODE; "Shortcut Dimension 2 Code") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            column(QUANTITY_BASE; "Quantity (Base)") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}