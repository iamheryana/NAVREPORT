page 50108 "BC E-Faktur Register Header-n"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Dimension Value";
    Caption = 'BC E-Faktur Register Header-n';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                //field(DOCUMENT_TYPE; "Document Type") { }
                //field(NO_; "No.") { }
                //field(EFAKTUR_REGISTER_MAPPING_ID; "E-Faktur Register Mapping ID") { }
                //field(FAKTUR_PAJAK_NO; "Faktur Pajak No.") { }
                //field(FAKTUR_PAJAK_DATE; "Faktur Pajak Date") { }
                //field(KODE_DOKUMEN_PENDUKUNG; "Kode Dokumen Pendukung") { }
                field(SYSTEM_CREATED_AT; "SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; "SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; "SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; "SystemModifiedBy") { }

            }
        }
    }
}