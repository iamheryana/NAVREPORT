page 50138 "Service Center Cue"
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

            cuegroup(SCActionontainer)
            {
                Caption = 'Create / New - Shortcut';

                actions
                {

                    action("Purchase Order")
                    {
                        ApplicationArea = All;
                        RunObject = page "Purchase Order";
                        RunPageMode = Create;
                        Image = TileReport;
                    }

                    action("Sales Order")
                    {
                        ApplicationArea = All;
                        RunObject = page "Sales Order";
                        RunPageMode = Create;
                        Image = TileReport;
                    }
                    action("Transfer Order")
                    {
                        ApplicationArea = All;
                        RunObject = page "Transfer Order";
                        RunPageMode = Create;
                        Image = TileReport;
                    }

                    action("Warehouse Shipment")
                    {
                        ApplicationArea = All;
                        RunObject = page "Warehouse Shipment";
                        RunPageMode = Create;
                        Image = TileReport;
                    }

                    action("Warehouse Receipt")
                    {
                        ApplicationArea = All;
                        RunObject = page "Warehouse Receipt";
                        RunPageMode = Create;
                        Image = TileReport;
                    }
                }
            }
            cuegroup(CuePO)
            {
                Caption = 'Purchase Order';

                field(MyPO; vMyPO)
                {
                    Caption = 'My PO';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Purchase Order (PO) Service Center yang dibuat sesuai dengan masing-masing User Id ';

                    trigger OnDrillDown()
                    var
                        vPOH: Record "Purchase Header";
                        pPOList: Page "Purchase Order List";
                    begin
                        vPOH.Reset();
                        vPOH.SetRange("Document Type", vPOH."Document Type"::Order);
                        vPOH.SetFilter("Assigned User ID", UserId);
                        vPOH.SetFilter("No. Series", 'POS');

                        pPOList.SetTableView(vPOH);
                        pPOList.Run();
                    end;
                }

                field(MyPOOut; vMyPOOut)
                {
                    Caption = 'PO Outstanding';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Purchase Order (PO) "Service Center" yang detail Barang-nya masih ada yang Outstanding (belum diterima atau belum di-Invoice)';

                    trigger OnDrillDown()
                    var
                        vPOH: Record "Purchase Header";
                        pPOList: Page "Purchase Order List";
                    begin
                        vPOH.Reset();
                        vPOH.SetRange("Document Type", vPOH."Document Type"::Order);
                        vPOH.SetFilter("No.", vMyPOOutNumber);

                        pPOList.SetTableView(vPOH);
                        pPOList.Run();
                    end;
                }
            }

            cuegroup(cueSO)
            {
                Caption = 'Sales Order';
                field(MySOReleased; vMySOReleased)
                {
                    Caption = 'Released';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) "Service Center" yang statusnya "Released" dan belum ada Pengiriman Barang Sama Sekali.';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetFilter("No.", vMySOReleasedNumber);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySOShippedNotInvoiced; vMySOShippedNotInvoiced)
                {
                    Caption = 'Shipped Not Invoiced';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) "Service Center" yang statusnya "Released" dan sudah ada Pengiriman Barang tetapi belum dibuatkan Invoiced.';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetFilter("No.", vMySOShippedNotInvoicedNumber);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySONoReservation; vMySONoReservation)
                {
                    Caption = 'No Reservation';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) "Service Center"  yang statusnya  "Released" dan belum ada Reservasi.';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetFilter("No.", vMySONoReservationNumber);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

            }

            cuegroup(CueInternalTO)
            {
                Caption = 'Internal / Transfer Order';

                field(MyTOReleased; vMyTOReleased)
                {
                    Caption = 'To be Shipped';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Transfer Order (TO) yang siap di Post "Ship" (di kirim) maupun yang sudah di Post "Ship" (di kirim) tetapi baru sebagian Item nya';

                    trigger OnDrillDown()
                    var
                        vTOH: Record "Transfer Header";
                        pTOList: Page "Transfer Orders";
                    begin
                        vTOH.Reset();
                        vTOH.SetFilter("No.", vMyTOReleasedNumber);

                        pTOList.SetTableView(vTOH);
                        pTOList.Run();
                    end;
                }

                field(MyTOShipped; vMyTOShipped)
                {
                    Caption = 'To be Received';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Transfer Order (TO) yang sudah di Post "Ship" / di kirim dari Gudang Pengirim tapi belum di Post "Receive" / di terima di Gudang Penerima';

                    trigger OnDrillDown()
                    var
                        vTOH: Record "Transfer Header";
                        pTOList: Page "Transfer Orders";
                    begin
                        vTOH.Reset();
                        vTOH.SetFilter("No.", vMyTOShippedNumber);

                        pTOList.SetTableView(vTOH);
                        pTOList.Run();
                    end;
                }
            }

            cuegroup(cuePostedDocuments)
            {
                Caption = 'Posted Documents - Created by TODAY';
                field(MySalesShipmentTODAY; vMySalesShipmentTODAY)
                {
                    Caption = 'Sales Shipments';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Sales Shipment / "Surat Jalan (DO)" khusus "Service Center" yang dibuat hari ini';

                    trigger OnDrillDown()
                    var
                        vSSH: Record "Sales Shipment Header";
                        pSSHList: Page "Posted Sales Shipments";
                    begin
                        vSSH.Reset();
                        vSSH.SetFilter("No.", vMySalesShipNumber);

                        pSSHList.SetTableView(vSSH);
                        pSSHList.Run();
                    end;
                }

                field(MySalesInvTODAY; vMySalesInvTODAY)
                {
                    Caption = 'Sales Invoices';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Sales Invoice / "Invoice" khusus "Service Center" yang dibuat hari ini';

                    trigger OnDrillDown()
                    var
                        vSIH: Record "Sales Invoice Header";
                        pSIHList: Page "Posted Sales Invoices";
                    begin
                        vSIH.Reset();
                        vSIH.SetFilter("No.", vMySalesInvNumber);

                        pSIHList.SetTableView(vSIH);
                        pSIHList.Run();
                    end;
                }


                field(MyPurchaseReceiptTODAY; vMyPurchaseReceiptTODAY)
                {
                    Caption = 'Purchase Receipts';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Purchase Receipt / "GR" khusus "Service Center" yang dibuat hari ini';

                    trigger OnDrillDown()
                    var
                        vPRH: Record "Purch. Rcpt. Header";
                        pPRHList: Page "Posted Purchase Receipts";
                    begin
                        vPRH.Reset();
                        vPRH.SetFilter("No.", vMyPurchRcptNumber);

                        pPRHList.SetTableView(vPRH);
                        pPRHList.Run();
                    end;
                }

                field(MyPurchaseInvTODAY; vMyPurchaseInvTODAY)
                {
                    Caption = 'Purchase Invoices';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Purchase Invoice / "Invoice AP" khusus "Service Center" yang dibuat hari ini';

                    trigger OnDrillDown()
                    var
                        vPIH: Record "Purch. Inv. Header";
                        pPIHList: Page "Posted Purchase Invoices";
                    begin
                        vPIH.Reset();
                        vPIH.SetFilter("No.", vMyPurchInvNumber);


                        pPIHList.SetTableView(vPIH);
                        pPIHList.Run();
                    end;
                }
            }
        }
    }


    trigger OnAfterGetCurrRecord()
    var
        MyPurchaseHeader: Record "Purchase Header";
        MyPurchaseLine: Record "Purchase Line";
        MySalesHeader: Record "Sales Header";
        MySalesLine: Record "Sales Line";

        MyTransferHeader: Record "Transfer Header";
        MyTransferLine: Record "Transfer Line";

        MyWarehouseEmployee: Record "Warehouse Employee";

        MyGLRegister: Record "G/L Register";
        MyGLEntry: Record "G/L Entry";
        MySalesShipmentHeader: Record "Sales Shipment Header";
        MyPurchRcptHeader: Record "Purch. Rcpt. Header";
        MySalesInvoiceHeader: Record "Sales Invoice Header";
        MyPurchInvHeader: Record "Purch. Inv. Header";

    begin


        // Purchase Order - [My PO | PO Outstanding] ===================================================================== START
        // Purchase Order - My PO
        MyPurchaseHeader.Reset();
        MyPurchaseHeader.SetRange("Document Type", MyPurchaseHeader."Document Type"::Order);
        MyPurchaseHeader.SetFilter("Assigned User ID", UserId);
        MyPurchaseHeader.SetFilter("No. Series", 'POS');
        vMyPO := MyPurchaseHeader.COUNT;

        // Purchase Order - PO Outstanding
        MyPurchaseHeader.Reset();
        MyPurchaseHeader.SetRange("Document Type", MyPurchaseHeader."Document Type"::Order);
        MyPurchaseHeader.SetFilter("Assigned User ID", UserId);
        MyPurchaseHeader.SetFilter("No. Series", 'POS');

        vMyPOOut := 0;
        vMyPOOutNumber := '.';

        if MyPurchaseHeader.FindSet() THEN
            repeat
            begin
                MyPurchaseLine.Reset();
                MyPurchaseLine.SetRange("Document Type", MyPurchaseHeader."Document Type");
                MyPurchaseLine.SetFilter("Document No.", MyPurchaseHeader."No.");
                MyPurchaseLine.SetFilter("Outstanding Quantity", '<>0');

                if MyPurchaseLine.count <> 0 then begin
                    vMyPOOut := vMyPOOut + 1;
                    vMyPOOutNumber := vMyPOOutNumber + '|' + MyPurchaseHeader."No.";
                end;
            end;
            until MyPurchaseHeader.NEXT = 0;

        // Purchase Order - [My PO | PO Outstanding] ===================================================================== END

        // Sales Order - [Released | Shipped Not Invoiced | No Reservation] ============================================== START
        // Sales Order - Released
        MySalesHeader.Reset();
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange(Status, MySalesHeader.Status::Released);
        MySalesHeader.SetFilter("No. Series", 'SOS');
        vMySOReleased := 0;
        vMySOReleasedNumber := '.';

        if MySalesHeader.FindSet() then
            repeat
            begin
                MySalesLine.Reset();
                MySalesLine.SetRange("Document Type", MySalesHeader."Document Type");
                MySalesLine.SetFilter("Document No.", MySalesHeader."No.");
                MySalesLine.SetFilter("Quantity Shipped", '<>0');

                if MySalesLine.Count = 0 then begin
                    vMySOReleased := vMySOReleased + 1;
                    vMySOReleasedNumber := vMySOReleasedNumber + '|' + MySalesHeader."No.";
                end;
            end;
            until MySalesHeader.Next() = 0;
        //

        // Sales Order - Shipped Not Invoiced
        MySalesHeader.Reset();
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange(Status, MySalesHeader.Status::Released);
        MySalesHeader.SetFilter("No. Series", 'SOS');

        vMySOShippedNotInvoiced := 0;
        vMySOShippedNotInvoicedNumber := '.';

        if MySalesHeader.FindSet() then
            repeat
            begin
                MySalesLine.Reset();
                MySalesLine.SetRange("Document Type", MySalesHeader."Document Type");
                MySalesLine.SetFilter("Document No.", MySalesHeader."No.");
                MySalesLine.SetFilter("Quantity Shipped", '<>0');
                MySalesLine.SetFilter("Quantity Invoiced", '=0');

                if MySalesLine.Count <> 0 then BEGIN
                    vMySOShippedNotInvoiced := vMySOShippedNotInvoiced + 1;
                    vMySOShippedNotInvoicedNumber := vMySOShippedNotInvoicedNumber + '|' + MySalesHeader."No.";
                end;
            end;
            until MySalesHeader.Next() = 0;


        // Sales Order - No Reservation
        MySalesHeader.Reset();
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange(Status, MySalesHeader.Status::Released);
        MySalesHeader.SetFilter("No. Series", 'SOS');

        vMySONoReservation := 0;
        vMySONoReservationNumber := '.';

        if MySalesHeader.FindSet() then
            repeat
            begin
                MySalesLine.Reset();
                MySalesLine.SetRange("Document Type", MySalesHeader."Document Type");
                MySalesLine.SetFilter("Document No.", MySalesHeader."No.");
                MySalesLine.SetFilter("Reserved Quantity", '=0');
                MySalesLine.SetFilter("Outstanding Quantity", '<>0');

                if (MySalesLine.Count <> 0) THEN begin
                    vMySONoReservation := vMySONoReservation + 1;
                    vMySONoReservationNumber := vMySONoReservationNumber + '|' + MySalesHeader."No.";
                end;
            end;
            until MySalesHeader.Next() = 0;

        // Sales Order - [Released | Shipped Not Invoiced | No Reservation] ============================================== END

        // Transfer Order - [To be Shipped | To be Received] ============================================================= START

        // Transfer Order - To be Shipped
        vMyTOReleased := 0;
        vMyTOReleasedNumber := '.';

        // Transfer Order - To be Received
        vMyTOShipped := 0;
        vMyTOShippedNumber := '.';

        MyTransferHeader.Reset();

        if MyTransferHeader.FindSet() then
            repeat
            begin
                MyTransferLine.Reset();
                MyTransferLine.SetFilter("Document No.", MyTransferHeader."No.");
                MyTransferLine.SetFilter("In-Transit Code", 'INTRANSIT');

                vMyQtyTO := 0;
                vMyQtyShippedTO := 0;

                if MyTransferLine.FindSet() then
                    repeat
                    begin
                        vMyQtyTO := vMyQtyTO + MyTransferLine.Quantity;
                        vMyQtyShippedTO := vMyQtyShippedTO + MyTransferLine."Quantity Shipped";
                    end;
                    until MyTransferLine.NEXT = 0;

                //Jika Gudang Asal termasuk kedalam WH Employee si User
                MyWarehouseEmployee.Reset();
                MyWarehouseEmployee.SetFilter("User ID", UserId);
                MyWarehouseEmployee.SetFilter("Location Code", MyTransferHeader."Transfer-from Code");

                if (MyTransferHeader."Transfer-from Code" <> '') = true and (MyWarehouseEmployee.Count > 0) then begin
                    if vMyQtyTO <> vMyQtyShippedTO then begin
                        vMyTOReleased := vMyTOReleased + 1;
                        vMyTOReleasedNumber := vMyTOReleasedNumber + '|' + MyTransferHeader."No.";
                    end;
                end;

                //Jika Gudang Penerima termasuk kedalam WH Employee si User
                MyWarehouseEmployee.Reset();
                MyWarehouseEmployee.SetFilter("User ID", UserId);
                MyWarehouseEmployee.SetFilter("Location Code", MyTransferHeader."Transfer-to Code");

                if (MyTransferHeader."Transfer-to Code" <> '') = TRUE and (MyWarehouseEmployee.Count > 0) then begin
                    if vMyQtyTO = vMyQtyShippedTO then begin
                        vMyTOShipped := vMyTOShipped + 1;
                        vMyTOShippedNumber := vMyTOShippedNumber + '|' + MyTransferHeader."No.";

                    end;
                end;
            end;
            until MyTransferHeader.Next() = 0;

        // Transfer Order - [To be Shipped | To be Received] ============================================================= END


        // Posted Document - Created TODAY - [Sales Shipments | Sales Invoices | Purchase Receipts | Purchase Invoices] == START

        vMySalesShipNumber := 'X';
        vMyPrevSalesShipNumber := 'U';

        vMySalesInvNumber := 'X';
        vMyPrevSalesInvNumber := 'U';

        vMyPurchRcptNumber := 'X';
        vMyPrevPurchRcptNumber := 'U';

        vMyPurchInvNumber := 'X';
        vMyPrevPurchInvNumber := 'U';

        MyGLRegister.Reset();
        MyGLRegister.SetFilter("Creation Date", FORMAT(TODAY));
        MyGLRegister.SetFilter("Source Code", 'INVTPCOST');

        if MyGLRegister.FindSet() then
            repeat
            begin
                MyGLEntry.Reset();
                MyGLEntry.SetFilter("Entry No.", FORMAT(MyGLRegister."From Entry No."));

                if MyGLEntry.FindSet() then
                    repeat
                        if CopyStr(MyGLEntry."Document No.", 1, 4) IN ['DOAS', 'DOSS'] then
                            if MyGLEntry."Document No." <> vMyPrevSalesShipNumber then begin
                                vMyPrevSalesShipNumber := MyGLEntry."Document No.";
                                vMySalesShipNumber := vMySalesShipNumber + '|' + MyGLEntry."Document No.";
                            end;

                        if CopyStr(MyGLEntry."Document No.", 1, 4) IN ['SIAS', 'SISS', 'UMAS', 'UMSS'] then
                            if MyGLEntry."Document No." <> vMyPrevSalesInvNumber then begin
                                vMyPrevSalesInvNumber := MyGLEntry."Document No.";
                                vMySalesInvNumber := vMySalesInvNumber + '|' + MyGLEntry."Document No.";
                            end;

                        if CopyStr(MyGLEntry."Document No.", 1, 4) IN ['GRAS', 'GRSS'] then
                            if MyGLEntry."Document No." <> vMyPrevPurchRcptNumber then begin
                                vMyPrevPurchRcptNumber := MyGLEntry."Document No.";
                                vMyPurchRcptNumber := vMyPurchRcptNumber + '|' + MyGLEntry."Document No.";
                            end;

                        if CopyStr(MyGLEntry."Document No.", 1, 4) IN ['PIAS', 'PISS'] then
                            if MyGLEntry."Document No." <> vMyPrevPurchInvNumber then begin
                                vMyPrevPurchInvNumber := MyGLEntry."Document No.";
                                vMyPurchInvNumber := vMyPurchInvNumber + '|' + MyGLEntry."Document No.";
                            end;

                        if COPYSTR(MyGLEntry."Document No.", 1, 5) IN ['PPIAS', 'PPISS'] then
                            if MyGLEntry."Document No." <> vMyPrevPurchInvNumber then begin
                                vMyPrevPurchInvNumber := MyGLEntry."Document No.";
                                vMyPurchInvNumber := vMyPurchInvNumber + '|' + MyGLEntry."Document No.";
                            end;
                    until MyGLEntry.NEXT = 0;
            end;
            until MyGLRegister.NEXT = 0;

        // Posted Document - Created TODAY - Sales Shipments
        MySalesShipmentHeader.Reset();
        MySalesShipmentHeader.SetFilter("No.", vMySalesShipNumber);
        vMySalesShipmentTODAY := MySalesShipmentHeader.Count;

        // Posted Document - Created TODAY - Sales Invoices
        MySalesInvoiceHeader.Reset();
        MySalesInvoiceHeader.SetFilter("No.", vMySalesInvNumber);
        vMySalesInvTODAY := MySalesInvoiceHeader.Count;

        // Posted Document - Created TODAY - Purchase Receipts
        MyPurchRcptHeader.Reset();
        MyPurchRcptHeader.SetFilter("No.", vMyPurchRcptNumber);
        vMyPurchaseReceiptTODAY := MyPurchRcptHeader.Count;

        // Posted Document - Created TODAY - Purchase Invoices
        MyPurchInvHeader.Reset();
        MyPurchInvHeader.SetFilter("No.", vMyPurchInvNumber);
        vMyPurchaseInvTODAY := MyPurchInvHeader.Count;
        // Posted Document - Created TODAY - [Sales Shipments | Sales Invoices | Purchase Receipts | Purchase Invoices] == END


    end;

    var
        vMyPO: Integer;
        vMyPOOut: Integer;

        vMySOReleased: Integer;
        vMySOShippedNotInvoiced: Integer;
        vMySONoReservation: Integer;
        vMyTOReleased: Integer;
        VMyTOShipped: Integer;

        vMySalesShipmentTODAY: Integer;
        vMySalesInvTODAY: Integer;
        vMyPurchaseReceiptTODAY: Integer;
        vMyPurchaseInvTODAY: Integer;


        vMyPOOutNumber: Text;
        vMySOReleasedNumber: Text;
        vMySOShippedNotInvoicedNumber: Text;
        vMySONoReservationNumber: Text;
        vMyTOReleasedNumber: Text;
        vMyTOShippedNumber: Text;

        vMyQtyTO: Integer;
        vMyQtyShippedTO: Integer;

        vMySalesShipNumber: Text;
        vMyPrevSalesShipNumber: Text;

        vMySalesInvNumber: Text;
        vMyPrevSalesInvNumber: Text;

        vMyPurchRcptNumber: Text;
        vMyPrevPurchRcptNumber: Text;

        vMyPurchInvNumber: Text;
        vMyPrevPurchInvNumber: Text;

        Style: Text;
        vUserId: Text;

}