query 50111 "BC Purch Inv Line"
{
    QueryType = Normal;
    Caption = 'BC Purch Inv Line';

    elements
    {
        dataitem(PURCH_INV_LINE; "Purch. Inv. Line")
        {
            column(DOCUMENT_NO; "Document No.") { }
            column(LINE_NO; "Line No.") { }
            column(TYPE_; "Type") { }
            column(NO_; "No.") { }
            column(DESCRIPTION_; "Description") { }
            column(UNIT_OF_MEASURE; "Unit of Measure") { }
            column(QUANTITY; "Quantity") { }
            column(DIRECT_UNIT_COST; "Direct Unit Cost") { }
            column(AMOUNT; "Amount") { }
            column(AMOUNT_INCLUDING_VAT; "Amount Including VAT") { }
            column(RECEIPT_NO; "Receipt No.") { }
            column(RECEIPT_LINE_NO; "Receipt Line No.") { }
            column(VAT_PROD_POSTING_GROUP; "VAT Prod. Posting Group") { }
            column(LINE_AMOUNT; "Line Amount") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            dataitem(ITEM_CATEGORY; "Item Category")
            {
                DataItemLink = "Code" = PURCH_INV_LINE."Item Category Code";
                SqlJoinType = LeftOuterJoin;
                column(ITEM_CATEGORY_CODE; "Parent Category") { }
            }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}