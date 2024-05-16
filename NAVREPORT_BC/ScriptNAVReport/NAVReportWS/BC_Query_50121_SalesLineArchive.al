query 50121 "BC Sales Line Archive"
{
    QueryType = Normal;
    Caption = 'BC Sales Line Archive';

    elements
    {
        dataitem(SALES_LINE_ARCHIVE; "Sales Line Archive")
        {
            column(DOCUMENT_TYPE; "Document Type") { }
            column(DOCUMENT_NO; "Document No.") { }
            column(DOC_NO_OCCORRENCE; "Doc. No. Occurrence") { }
            column(VERSION_NO; "Version No.") { }
            column(LINE_NO; "Line No.") { }
            column(TYPE_; "Type") { }
            column(NO_; "No.") { }
            column(DESCRIPTION_; "Description") { }
            column(UNIT_OF_MEASURE_CODE; "Unit of Measure Code") { }
            column(QUANTITY; "Quantity") { }
            column(UNIT_PRICE; "Unit Price") { }
            column(LINE_DISCOUNT_AMOUNT; "Line Discount Amount") { }
            column(AMOUNT; "Amount") { }
            column(AMOUNT_INCLUDING_VAT; "Amount Including VAT") { }
            column(BLANKET_ORDER_NO; "Blanket Order No.") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}