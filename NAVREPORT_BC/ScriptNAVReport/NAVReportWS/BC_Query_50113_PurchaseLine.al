query 50113 "BC Purchase Line"
{
    QueryType = Normal;
    Caption = 'BC Purchase Line';

    elements
    {
        dataitem(PURCHASE_LINE; "Purchase Line")
        {
            column(DOCUMENT_TYPE; "Document Type") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(LINE_NO; "Line No.") { }
            column(TYPE_; "Type") { }
            column(NO_; "No.") { }
            column(LOCATION_CODE; "Location Code") { }
            column(POSTING_GROUP; "Posting Group") { }
            column(EXPECTED_RECEIPT_DATE; "Expected Receipt Date") { }
            column(DESCRIPTION_; "Description") { }
            column(DESCRIPTION_2; "Description 2") { }
            column(UNIT_OF_MEASURE; "Unit of Measure") { }
            column(QUANTITY; "Quantity") { }
            column(OUTSTANDING_QUANTITY; "Outstanding Quantity") { }
            column(DIRECT_UNIT_COST; "Direct Unit Cost") { }
            column(UNIT_COST_LCY; "Unit Cost (LCY)") { }
            column(LINE_DISCOUNT_PCN; "Line Discount %") { }
            column(LINE_DISCOUNT_AMOUNT; "Line Discount Amount") { }
            column(AMOUNT; "Amount") { }
            column(AMOUNT_INCLUDING_VAT; "Amount Including VAT") { }
            column(SHORTCUT_DIMENSION_1_CODE; "Shortcut Dimension 1 Code") { }
            column(SHORTCUT_DIMENSION_2_CODE; "Shortcut Dimension 2 Code") { }
            column(QUANTITY_RECIEVED; "Quantity Received") { }
            column(QUANTITY_INVOICED; "Quantity Invoiced") { }
            column(RECEIPT_NO; "Receipt No.") { }
            column(RECEIPT_LINE_NO; "Receipt Line No.") { }
            column(GEN_BUS_POSTING_GROUP; "Gen. Bus. Posting Group") { }
            column(GEN_PROD_POSTING_GROUP; "Gen. Prod. Posting Group") { }
            column(VAT_BUS_POSTING_GROUP; "VAT Bus. Posting Group") { }
            column(VAT_PROD_POSTING_GROUP; "VAT Prod. Posting Group") { }
            column(BLANKET_ORDER_NO; "Blanket Order No.") { }
            column(BLANKET_ORDER_LINE_NO; "Blanket Order Line No.") { }
            column(UNIT_COST; "Unit Cost") { }
            column(LINE_AMOUNT; "Line Amount") { }
            column(IC_PARTNER_REFERENCE; "IC Partner Reference") { }
            column(PREPMT_LINE_AMOUNT; "Prepmt. Line Amount") { }
            column(PREPMT_AMT_INCL_VAT; "Prepmt. Amt. Incl. VAT") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            column(UNIT_OF_MEASURE_CODE; "Unit of Measure Code") { }
            column(QUANTITY_BASE; "Quantity (Base)") { }
            column(OUTSTANDING_QTY_BASE; "Outstanding Qty. (Base)") { }
            column(QTY_RECEIVED_BASE; "Qty. Received (Base)") { }
            column(QTY_INVOICED_BASE; "Qty. Invoiced (Base)") { }
            column(PRODUCT_GROUP_CODE; "Item Category Code") { }
            dataitem(ITEM_CATEGORY; "Item Category")
            {
                DataItemLink = "Code" = PURCHASE_LINE."Item Category Code";
                SqlJoinType = LeftOuterJoin;
                column(ITEM_CATEGORY_CODE; "Parent Category") { }
            }

            column(PREPAYMENT_PCN; "Prepayment %") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }

    }
}