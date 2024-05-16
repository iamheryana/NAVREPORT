query 50109 "BC Purch Cr Memo Line"
{
    QueryType = Normal;
    Caption = 'BC Purch Cr Memo Line';

    elements
    {

        dataitem(PURCH_CR_MEMO_LINE; "Purch. Cr. Memo Line")
        {
            column(DOCUMENT_NO; "Document No.") { }
            column(LINE_NO; "Line No.") { }
            column(TYPE_; "Type") { }
            column(NO_; "No.") { }
            column(AMOUNT; "Amount") { }
            column(AMOUNT_INCLUDING_VAT; "Amount Including VAT") { }
            column(VAT_PROD_POSTING_GROUP; "VAT Prod. Posting Group") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            dataitem(ITEM_CATEGORY; "Item Category")
            {
                DataItemLink = "Code" = PURCH_CR_MEMO_LINE."Item Category Code";
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