query 50123 "BC Transfer Receipt Header"
{
    QueryType = Normal;
    Caption = 'BC Transfer Receipt Header';

    elements
    {
        dataitem(TRANSFER_RECEIPT_HEADER; "Transfer Receipt Header")
        {
            column(NO_; "No.") { }
            column(TRANSFER_TO_NAME; "Transfer-to Name") { }
            column(TRANSFER_FROM_CONTACT; "Transfer-from Contact") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }
        }
    }
}