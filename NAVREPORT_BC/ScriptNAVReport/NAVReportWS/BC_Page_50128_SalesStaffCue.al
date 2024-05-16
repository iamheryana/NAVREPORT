page 50128 "Sales Staff Cue"
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
            cuegroup(CueBSO)
            {
                Caption = 'Blanket Sales Order';

                field(MyBSO; vMyBSO)
                {
                    Caption = 'My BSO';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Blanket Sales Order (BSO) sesuai dengan masing-masing Sales Login';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pBSOList: Page "Blanket Sales Orders";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::"Blanket Order");
                        vSH.SetFilter("No. Series", '<>SBOM&<>SBOMS');
                        vSH.SetFilter("Salesperson Code", UserId);


                        pBSOList.SetTableView(vSH);
                        pBSOList.Run();
                    end;
                }



                field(MyOutBSO; vMyOutBSO)
                {
                    Caption = 'My Outstanding BSO';
                    ApplicationArea = all;
                    //StyleExpr = Style;
                    ToolTip = 'Menyatakan Jumlah Dokumen Blanket Sales Order (BSO) yang masih Outstanding ( belum diproses oleh SAO / Admin ) sesuai dengan masing-masing Sales Login';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pBSOList: Page "Blanket Sales Orders";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::"Blanket Order");
                        vSH.SetFilter("No.", vMyOutBSONo);


                        pBSOList.SetTableView(vSH);
                        pBSOList.Run();
                    end;
                }
                field(MyBSOPipeline; vMyBSOPipeline)
                {
                    Caption = 'My BSO - Pipeline ACS';
                    ApplicationArea = all;
                    //StyleExpr = Style;
                    ToolTip = 'Menyatakan Jumlah Dokumen Blanket Sales Order (BSO) Pipeline sesuai dengan masing-masing Sales Login';
                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pBSOList: Page "Blanket Sales Orders";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::"Blanket Order");
                        vSH.SetFilter("No. Series", 'SBOM|SBOMS');
                        vSH.SetFilter("Salesperson Code", UserId);

                        pBSOList.SetTableView(vSH);
                        pBSOList.Run();
                    end;
                }


            }

            cuegroup(cueSO)
            {
                Caption = 'Sales Order';
                field(MySOOpen; vMySOOpen)
                {
                    Caption = 'My SO - Open';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) yang statusnya Open sesuai dengan masing-masing Sales Login';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH."Status"::Open);
                        vSH.SetFilter("No. Series", '<>SBOM&<>SBOMS');
                        vSH.SetFilter("Salesperson Code", UserId);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySOReleased; vMySOReleased)
                {
                    Caption = 'My SO - Released';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) yang Statusnya Released sesuai dengan masing-masing Sales Login';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH."Status"::Released);
                        vSH.SetFilter("Shipped Not Invoiced", 'No');
                        vSH.SetFilter("No. Series", '<>SBOM&<>SBOMS');
                        vSH.SetFilter("Salesperson Code", UserId);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySOShippedNotInvoiced; vMySOShippedNotInvoiced)
                {
                    Caption = 'My SO - Shipped Not Invoiced';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) yang sudah dibuatkan Surat Jalan tetapi belum di Invoiced sesuai dengan masing-masing Sales Login';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH."Status"::Released);
                        vSH.SetFilter("Shipped Not Invoiced", 'Yes');
                        vSH.SetFilter("No. Series", '<>SBOM&<>SBOMS');
                        vSH.SetFilter("Salesperson Code", UserId);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }
            }

            cuegroup(cuePostedDocument)
            {
                Caption = 'Posted Document';
                field(MySalesShipment; vMySalesShipment)
                {
                    Caption = 'My Sales Shipments';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Sales Shipment / Surat Jalan sesuai dengan masing-masing Sales Login';

                    trigger OnDrillDown()
                    var
                        vSSH: Record "Sales Shipment Header";
                        pSHList: Page "Posted Sales Shipments";
                    begin
                        vSSH.Reset();
                        vSSH.SetFilter("Salesperson Code", UserId);

                        pSHList.SetTableView(vSSH);
                        pSHList.Run();
                    end;
                }
                field(MySalesInvoice; vMySalesInvoice)
                {
                    Caption = 'My Sales Invoices';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Sales Invoice / Invoice AR sesuai dengan masing-masing Sales Login';

                    trigger OnDrillDown()
                    var
                        vSIH: Record "Sales Invoice Header";
                        pSIHList: Page "Posted Sales Invoices";
                    begin
                        vSIH.Reset();
                        vSIH.SetFilter("Salesperson Code", UserId);
                        vSIH.SetFilter("No.", vMyNoInvCancel);

                        pSIHList.SetTableView(vSIH);
                        pSIHList.Run();
                    end;


                }
            }

            cuegroup(cueSPB)
            {
                Caption = 'Surat Peminjaman Barang (SPB)';
                field(MyOutstandingSPB; vMyOutstandingSPB)
                {
                    Caption = 'My Outstanding SPB';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Surat Peminjaman Barang (SPB) di Gudang HO yang belum di kembalikan sesuai dengan masing-masing Sales Login';

                    trigger OnDrillDown()
                    var
                        vILE: Record "Item Ledger Entry";
                        pILEList: Page "Item Ledger Entries";
                    begin
                        vILE.Reset();
                        vILE.SetRange("Document Type", vILE."Document Type"::"Transfer Receipt");
                        vILE.SetFilter(Open, '1');
                        vILE.SetFilter("Location Code", 'HO-SPB|CKR-SPB|SMR-SPB|SBY-SPB|DPS-SPB');
                        vILE.SetFilter("External Document No.", UserId + '*');

                        pILEList.SetTableView(vILE);
                        pILEList.Run();
                    end;
                }
            }

            cuegroup(cueAddContacts)
            {
                Caption = 'Additional Contacts';
                field(MyAddContacts; vMyAddContacts)
                {
                    Caption = 'Additional Contacts';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Additional Contacts';

                    trigger OnDrillDown()
                    var
                        vCon: Record "Contact";
                        pConList: Page "Contact List";
                    begin
                        vCon.Reset();
                        vCon.SetFilter("Salesperson Code", UserId);
                        vMyAddContacts := vCon.COUNT;

                        pConList.SetTableView(vCon);
                        pConList.Run();
                    end;
                }
            }

        }
    }

    trigger OnAfterGetCurrRecord()
    var

        MyBlanketSalesOrder: Record "Sales Header";
        MyBlanketSalesOrderLine: Record "Sales Line";
        MyPostedSalesInvoiceLine: Record "Sales Invoice Line";
        MySalesOrder: Record "Sales Header";
        MySalesShipment: Record "Sales Shipment Header";

        MySalesInvoice: Record "Sales Invoice Header";
        MySalesCreditMemo: Record "Sales Cr.Memo Header";
        MyItemLedgerEntry: Record "Item Ledger Entry";
        MyAddtionalContact: Record "Contact";

    begin
        // My BSO
        MyBlanketSalesOrder.Reset();
        MyBlanketSalesOrder.SetRange("Document Type", MyBlanketSalesOrder."Document Type"::"Blanket Order");
        MyBlanketSalesOrder.SetFilter("No. Series", '<>SBOM&<>SBOMS');
        MyBlanketSalesOrder.SetFilter("Salesperson Code", UserId);
        vMyBSO := MyBlanketSalesOrder.Count();

        // My Outstanding BSO
        MyBlanketSalesOrder.Reset();
        MyBlanketSalesOrder.SetRange("Document Type", MyBlanketSalesOrder."Document Type"::"Blanket Order");
        MyBlanketSalesOrder.SetFilter("No. Series", '<>SBOM&<>SBOMS');
        MyBlanketSalesOrder.SetFilter("Order Date", '>01012019');
        MyBlanketSalesOrder.SetFilter("Salesperson Code", UserId);
        vMyOutBSO := 0;
        vMyOutBSONo := '.';
        if MyBlanketSalesOrder.FindSet() then
            repeat
            begin
                MyPostedSalesInvoiceLine.Reset();
                MyPostedSalesInvoiceLine.SetFilter("Blanket Order No.", MyBlanketSalesOrder."No.");

                MyBlanketSalesOrderLine.Reset();
                MyBlanketSalesOrderLine.SetFilter("Blanket Order No.", MyBlanketSalesOrder."No.");
                MyBlanketSalesOrderLine.SetFilter("Document Type", '1');


                IF (MyBlanketSalesOrderLine.Count = 0) AND (MyPostedSalesInvoiceLine.Count = 0) THEN begin
                    vMyOutBSO := vMyOutBSO + 1;
                    vMyOutBSONo := vMyOutBSONo + '|' + MyBlanketSalesOrder."No.";
                end;
            end;
            until MyBlanketSalesOrder.Next() = 0;


        // My BSO Pipeline
        MyBlanketSalesOrder.Reset();
        MyBlanketSalesOrder.SetRange("Document Type", MyBlanketSalesOrder."Document Type"::"Blanket Order");
        MyBlanketSalesOrder.SetFilter("No. Series", 'SBOM|SBOMS');
        MyBlanketSalesOrder.SetFilter("Salesperson Code", UserId);
        vMyBSOPipeline := MyBlanketSalesOrder.Count;


        // My SO - Open
        MySalesOrder.Reset();
        MySalesOrder.SetRange("Document Type", MySalesOrder."Document Type"::Order);
        MySalesOrder.SetRange("Status", MySalesOrder."Status"::Open);
        MySalesOrder.SetFilter("No. Series", '<>SBOM&<>SBOMS');
        MySalesOrder.SetFilter("Salesperson Code", UserId);
        vMySOOpen := MySalesOrder.Count;

        // My SO - Released
        MySalesOrder.Reset();
        MySalesOrder.SetRange("Document Type", MySalesOrder."Document Type"::Order);
        MySalesOrder.SetRange("Status", MySalesOrder."Status"::Released);
        MySalesOrder.SetFilter("Shipped Not Invoiced", 'No');
        MySalesOrder.SetFilter("No. Series", '<>SBOM&<>SBOMS');
        MySalesOrder.SetFilter("Salesperson Code", UserId);
        vMySOReleased := MySalesOrder.Count;

        // My SO - Shipped Not Invoiced
        MySalesOrder.Reset();
        MySalesOrder.SetRange("Document Type", MySalesOrder."Document Type"::Order);
        MySalesOrder.SetRange("Status", MySalesOrder."Status"::Released);
        MySalesOrder.SetFilter("Shipped Not Invoiced", 'Yes');
        MySalesOrder.SetFilter("No. Series", '<>SBOM&<>SBOMS');
        MySalesOrder.SetFilter("Salesperson Code", UserId);
        vMySOShippedNotInvoiced := MySalesOrder.Count;


        //My Sales Shipments
        MySalesShipment.Reset();
        MySalesShipment.SetFilter("Salesperson Code", UserId);
        vMySalesShipment := MySalesShipment.Count;

        //My Sales Invoice
        MySalesCreditMemo.Reset();
        MySalesCreditMemo.SetFilter("Salesperson Code", UserId);

        vMyNoInvCancel := '<>.';
        IF MySalesCreditMemo.FindSet() THEN
            REPEAT
            BEGIN
                IF MySalesCreditMemo."Applies-to Doc. No." <> '' THEN
                    vMyNoInvCancel := vMyNoInvCancel + '&<>' + MySalesCreditMemo."Applies-to Doc. No.";
            END;
            UNTIL MySalesCreditMemo.Next() = 0;

        MySalesInvoice.Reset();
        MySalesInvoice.SetFilter("Salesperson Code", UserId);
        MySalesInvoice.SetFilter("No.", vMyNoInvCancel);
        vMySalesInvoice := MySalesInvoice.Count;


        //My Outstanding BSO
        MyItemLedgerEntry.Reset();
        MyItemLedgerEntry.SetRange("Document Type", MyItemLedgerEntry."Document Type"::"Transfer Receipt");
        MyItemLedgerEntry.SetFilter(Open, '1');
        MyItemLedgerEntry.SetFilter("Location Code", 'HO-SPB|CKR-SPB|SMR-SPB|SBY-SPB|DPS-SPB');
        MyItemLedgerEntry.SetFilter("External Document No.", UserId + '*');
        vMyOutstandingSPB := MyItemLedgerEntry.Count;


        //Additional Contacts
        MyAddtionalContact.Reset();
        MyAddtionalContact.SetFilter("Salesperson Code", UserId);
        vMyAddContacts := MyAddtionalContact.Count;


    end;


    var
        vMyBSO: Integer;
        vMyOutBSO: Integer;
        vMyBSOPipeline: Integer;

        vMySOOpen: Integer;
        vMySOReleased: Integer;
        vMySOShippedNotInvoiced: Integer;

        vMySalesShipment: Integer;
        vMySalesInvoice: Integer;

        vMyOutstandingSPB: Integer;

        vMyAddContacts: Integer;

        vMyOutBSONo: Text;
        vMyNoInvCancel: Text;

    // Style: Text;
    // vUserId: Text;

}