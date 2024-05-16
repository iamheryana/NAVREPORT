page 50144 "Accounting Cue"
{
    PageType = CardPart;
    //SourceTable = SalesInvoiceCueTable;
    //Caption = 'Sales Staff Activities';

    //PageType = CardPart;
    //SourceTable = CustomCueTable;
    RefreshOnActivate = true;

    layout
    {
        area(Content)
        {

            cuegroup(CueSO)
            {
                Caption = 'Out. Sales Order';

                field(MySOShippedNotInvoiced; vMySOShippedNotInvoiced)
                {
                    Caption = 'SO Shipped Not Invoiced';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) "Trade" / "Service Center" yang sudah ada Surat Jalan (Shipped) tapi belum dibuat Invoice';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH.Status::Released);
                        vSH.SetFilter("Shipped Not Invoiced", 'Yes');
                        vSH.SetFilter("No. Series", '<>SOR');

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySOMsrvAll; vMySOMsrvAll)
                {
                    Caption = 'SO Man. Service - Need to Invoice - All';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) - Managed Service / Rental secara keseluruhan yang belum dibuat Invoice';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH.Status::Released);
                        vSH.SetFilter("No. Series", 'SOR');

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySOMsrvToday; vMySOMsrvToday)
                {
                    Caption = 'SO Man. Service - Need to Invoice - TODAY';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) - Managed Service / Rental yang harus dibuat hari ini';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH.Status::Released);
                        vSH.SetFilter("No. Series", 'SOR');
                        vSH.SetFilter("Posting Date", '<=' + FORMAT(TODAY));

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }
            }

            cuegroup(CueAR)
            {
                Caption = 'Posted Sales Invoiced / AR - Created Today';

                field(MySIToday; vMySIToday)
                {
                    Caption = 'Sales Invoices';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Invoice yang dibuat pada hari ini';

                    trigger OnDrillDown()
                    var
                        vSIH: Record "Sales Invoice Header";
                        pSIHList: Page "Posted Sales Invoices";
                    begin
                        vSIH.Reset();
                        vSIH.SetFilter("No.", vMyNoSalesInvoice);

                        pSIHList.SetTableView(vSIH);
                        pSIHList.Run();
                    end;
                }

                field(MySCMToday; vMySCMToday)
                {
                    Caption = 'Sales Credit Memos';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Credit Memo / Cancel Sales Invoice yang dibuat pada hari ini';

                    trigger OnDrillDown()
                    var
                        vSCMH: Record "Sales Cr.Memo Header";
                        pSCMList: Page "Posted Sales Credit Memos";
                    begin
                        vSCMH.Reset();
                        vSCMH.SetFilter("No.", vMyNoSalesInvoice);

                        pSCMList.SetTableView(vSCMH);
                        pSCMList.Run();
                    end;
                }
            }

            cuegroup(CueAP)
            {
                Caption = 'Posted Purchase Invoiced / AP - Created Today';

                field(MyPIToday; vMyPIToday)
                {
                    Caption = 'Purchase Invoices';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Purchase Invoice yang dibuat pada hari ini';

                    trigger OnDrillDown()
                    var
                        vPIH: Record "Purch. Inv. Header";
                        pPIHList: Page "Posted Purchase Invoices";
                    begin
                        vPIH.Reset();
                        vPIH.SetFilter("No.", vMyNoPurchaseInvoice);

                        pPIHList.SetTableView(vPIH);
                        pPIHList.Run();
                    end;
                }

                field(MyPCMToday; vMyPCMToday)
                {
                    Caption = 'Purchase Credit Memos';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Purchase Credit Memo / Cancel Purchase Invoice yang dibuat pada hari ini';

                    trigger OnDrillDown()
                    var
                        vPCMH: Record "Purch. Cr. Memo Hdr.";
                        pPCMList: Page "Posted Purchase Credit Memos";
                    begin
                        vPCMH.Reset();
                        vPCMH.SetFilter("No.", vMyNoPurchaseInvoice);

                        pPCMList.SetTableView(vPCMH);
                        pPCMList.Run();
                    end;
                }
            }

            cuegroup(CueOutCM)
            {
                Caption = 'Out. Sales Credit Memo';

                field(MyOutCM; vMyOutCM)
                {
                    Caption = 'Out. Sales Credit Memos';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Credit Memo / Cancel Sales Invoice yang belum dibuat Sales Invoice penggantinya';

                    trigger OnDrillDown()
                    var
                        vILE: Record "Item Ledger Entry";
                        pILEList: Page "Item Ledger Entries";
                    begin
                        vILE.Reset();
                        vILE.SetFilter("Location Code", 'HO-CM');
                        vILE.SetFilter(Open, '1');

                        pILEList.SetTableView(vILE);
                        pILEList.Run();
                    end;
                }
            }

        }
    }


    trigger OnAfterGetCurrRecord()
    var
        MySalesHeader: Record "Sales Header";
        MyGLReg: Record "G/L Register";
        MyGLEntry: Record "G/L Entry";


        MySalesInvoiceHeader: Record "Sales Invoice Header";
        MyCrMemoHeader: Record "Sales Cr.Memo Header";

        MyPurchInvHeader: Record "Purch. Inv. Header";
        MyPurchCrMemoHdr: Record "Purch. Cr. Memo Hdr.";

        MyItemLedgerEntry: Record "Item Ledger Entry";
    begin

        // Out. Sales Order - [ SO Shipped Not Invoiced | 
        //                      SO Man. Service - Need to Invoice - All | 
        //                      SO Man. Service - Need to Invoice - TODAY ] =============================================== START

        // Out. Sales Order - [ SO Shipped Not Invoiced ] 
        MySalesHeader.Reset();
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange("Status", MySalesHeader.Status::Released);
        MySalesHeader.SetFilter("Shipped Not Invoiced", 'Yes');
        MySalesHeader.SetFilter("No. Series", '<>SOR');
        vMySOShippedNotInvoiced := MySalesHeader.Count;


        // Out. Sales Order - [ SO Man. Service - Need to Invoice - All ]
        MySalesHeader.Reset();
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange("Status", MySalesHeader.Status::Released);
        MySalesHeader.SetFilter("No. Series", 'SOR');
        vMySOMsrvAll := MySalesHeader.Count;


        // Out. Sales Order - [ SO Man. Service - Need to Invoice - TODAY ]
        MySalesHeader.Reset();
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange("Status", MySalesHeader.Status::Released);
        MySalesHeader.SetFilter("No. Series", 'SOR');
        MySalesHeader.SetFilter("Posting Date", '<=' + FORMAT(TODAY));
        vMySOMsrvToday := MySalesHeader.Count;

        // Out. Sales Order - [ SO Shipped Not Invoiced | 
        //                      SO Man. Service - Need to Invoice - All | 
        //                      SO Man. Service - Need to Invoice - TODAY ] =============================================== END


        vMyNoSalesInvoice := 'X';
        vMyNoPurchaseInvoice := 'Y';

        MyGLReg.Reset();
        MyGLReg.SetFilter("Creation Date", FORMAT(TODAY));
        MyGLReg.SetFilter("Source Code", 'SALES|PURCHASES');
        if MyGLReg.FindSet() then
            repeat
            begin
                MyGLEntry.Reset();
                MyGLEntry.SetFilter("Entry No.", FORMAT(MyGLReg."From Entry No."));
                if MyGLEntry.FindSet() then
                    repeat
                        if (MyGLReg."Source Code" = 'SALES') then
                            vMyNoSalesInvoice := vMyNoSalesInvoice + '|' + MyGLEntry."Document No.";
                        if (MyGLReg."Source Code" = 'PURCHASES') then
                            vMyNoPurchaseInvoice := vMyNoPurchaseInvoice + '|' + MyGLEntry."Document No.";
                    until MyGLEntry.Next() = 0;
            end;
            until MyGLReg.Next() = 0;

        // Posted Sales Invoiced / AR - Created Today - [ Sales Invoices | Sales Credit Memos ] =========================== START
        // Posted Sales Invoiced / AR - Created Today - [ Sales Invoices ]
        vMySIToday := 0;
        MySalesInvoiceHeader.Reset();
        MySalesInvoiceHeader.SetFilter("No.", vMyNoSalesInvoice);
        vMySIToday := MySalesInvoiceHeader.Count;


        // Posted Sales Invoiced / AR - Created Today - [ Sales Credit Memos ]     
        vMySCMToday := 0;
        MyCrMemoHeader.Reset();
        MyCrMemoHeader.SetFilter("No.", vMyNoSalesInvoice);
        vMySCMToday := MyCrMemoHeader.Count;

        // Posted Sales Invoiced / AR - Created Today - [ Sales Invoices | Sales Credit Memos ] =========================== END



        // Posted Purchase Invoiced / AP - Created Today - [ Purchase Invoices | Purchase Credit Memos ] ================== START
        // Posted Purchase Invoiced / AP - Created Today - [ Purchase Invoices ]
        vMyPIToday := 0;
        MyPurchInvHeader.Reset();
        MyPurchInvHeader.SetFilter("No.", vMyNoPurchaseInvoice);
        vMyPIToday := MyPurchInvHeader.Count;


        // Posted Purchase Invoiced / AP - Created Today - [ Purchase Credit Memos ]      
        vMyPCMToday := 0;
        MyPurchCrMemoHdr.Reset();
        MyPurchCrMemoHdr.SetFilter("No.", vMyNoPurchaseInvoice);
        vMyPCMToday := MyPurchCrMemoHdr.Count;
        // Posted Purchase Invoiced / AP - Created Today - [ Purchase Invoices | Purchase Credit Memos ] ================== END

        // Out. Sales Credit Memo ========================================================================================= START
        MyItemLedgerEntry.Reset();
        MyItemLedgerEntry.SetFilter("Location Code", 'HO-CM');
        MyItemLedgerEntry.SetFilter(Open, '1');

        vMyCMDoc := '';
        vMyCMDocPrev := '';
        vMyOutCM := 0;

        if MyItemLedgerEntry.FindSet() then
            repeat
            begin
                vMyCMDoc := MyItemLedgerEntry."Document No.";
                if vMyCMDoc <> vMyCMDocPrev then begin
                    vMyOutCM := vMyOutCM + 1;
                    vMyCMDocPrev := vMyCMDoc;
                end;
            end;
            until MyItemLedgerEntry.NEXT = 0;
        // Out. Sales Credit Memo ========================================================================================= END


    end;

    var
        vMySOShippedNotInvoiced: Integer;
        vMySOMsrvAll: Integer;
        vMySOMsrvToday: Integer;

        vMySIToday: Integer;
        vMySCMToday: Integer;

        vMyPIToday: Integer;
        vMyPCMToday: Integer;

        vMyOutCM: Integer;

        vMyNoSalesInvoice: Text;
        vMyNoPurchaseInvoice: Text;

        vMyCMDoc: Text;
        vMyCMDocPrev: Text;

        Style: Text;
        vUserId: Text;

}