query 50114 "BC Sales Comment Line"
{
    QueryType = Normal;
    Caption = 'BC Sales Comment Line';

    elements
    {
        dataitem(SALES_COMMENT_LINE; "Sales Comment Line")
        {
            column(DOCUMENT_TYPE; "Document Type") { }
            column(NO_; "No.") { }
            column(DOCUMENT_LINE_NO; "Document Line No.") { }
            column(LINE_NO; "Line No.") { }
            column(DATE_; "Date") { }
            column(CODE_; "Code") { }
            column(COMMENT; "Comment") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}