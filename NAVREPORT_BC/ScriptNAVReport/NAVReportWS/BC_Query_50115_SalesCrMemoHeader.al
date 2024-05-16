query 50115 "BC Sales Cr Memo Header"
{
    QueryType = Normal;
    Caption = 'BC Sales Cr Memo Header';

    elements
    {
        dataitem(SALES_CR_MEMO_HEADER; "Sales Cr.Memo Header")
        {
            column(NO_; "No.") { }
            column(SELL_T0_CUSTOMER_NO; "Sell-to Customer No.") { }
            column(BILL_TO_CUSTOMER_NO; "Bill-to Customer No.") { }
            column(BILL_TO_NAME; "Bill-to Name") { }
            column(BILL_TO_NAME_2; "Bill-to Name 2") { }
            column(POSTING_DATE; "Posting Date") { }
            column(POSTING_DESCRIPTION; "Posting Description") { }
            column(PAYMENT_TERMS_CODE; "Payment Terms Code") { }
            column(SHORTCUT_DIMENSION_1_CODE; "Shortcut Dimension 1 Code") { }
            column(SHORTCUT_DIMENSION_2_CODE; "Shortcut Dimension 2 Code") { }
            column(CUSTOMER_POSTING_GROUP; "Customer Posting Group") { }
            column(CURRENCY_CODE; "Currency Code") { }
            column(CURRENCY_FACTOR; "Currency Factor") { }
            column(SALESPERSON_CODE; "Salesperson Code") { }
            column(APPLIES_TO_DOC_TYPE; "Applies-to Doc. Type") { }
            column(APPLIES_TO_DOC_NO; "Applies-to Doc. No.") { }
            column(SELL_TO_CUSTOMER_NAME; "Sell-to Customer Name") { }
            column(SELL_TO_CUSTOMER_NAME_2; "Sell-to Customer Name 2") { }
            column(EXTERNAL_DOCUMENT_NO; "External Document No.") { }
            column(SOURCE_CODE; "Source Code") { }
            column(TAX_AREA_CODE; "Tax Area Code") { }
            column(PREPAYMENT_ORDER_NO; "Prepayment Order No.") { }
            column(DIMENSION_SET_ID; "Dimension Set ID") { }
            column(RESPONSIBILITY_CENTER; "Responsibility Center") { }
            //column(FAKTUR_PAJAK_NO; "Faktur Pajak No.") { } 
            column(GEN_BUS_POSTING_GROUP; "Gen. Bus. Posting Group") { }
            column(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
            column(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
            column(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
            column(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

        }
    }
}