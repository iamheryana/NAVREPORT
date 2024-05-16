page 50109 "BC E-Faktur Register Mapping-n"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "Dimension Value";
    Caption = 'BC E-Faktur Register Mapping-n';
    Editable = false;

    layout
    {
        area(Content)
        {
            repeater(GroupName)
            {
                //field(EFAKTUR_ID; "E-Faktur ID") { }
                //field(HEADER_CODE; "Header Code") { }
                //field(CSV_JENIS_DOKUMEN; "CSV - Jenis Dokumen") { }
                //field(CSV_KODE_JENIS_TRANSAKSI; "CSV - Kode Jenis Transaksi") { }
                //field(CSV_FG_PENGGANTI; "CSV - FG Pengganti") { }
                //field(CSV_ID_KETERANGAN; "CSV - ID Keterangan") { }
                field(SYSTEM_CREATED_AT; Rec."SystemCreatedAt") { }
                field(SYSTEM_CREATED_BY; Rec."SystemCreatedBy") { }
                field(SYSTEM_MODIFIED_AT; Rec."SystemModifiedAt") { }
                field(SYSTEM_MODIFIED_BY; Rec."SystemModifiedBy") { }

            }
        }
    }
}