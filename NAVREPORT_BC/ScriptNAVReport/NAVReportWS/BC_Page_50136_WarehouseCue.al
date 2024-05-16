page 50136 "Warehouse Cue"
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

            cuegroup(WarehouseActionontainer)
            {
                Caption = 'Create / New - Shortcut';

                actions
                {
                    action("Item")
                    {
                        ApplicationArea = All;
                        RunObject = page "Item Card";
                        RunPageMode = Create;
                        Image = TileCamera;
                    }

                    action("Transfer Order")
                    {
                        ApplicationArea = All;
                        RunObject = page "Transfer Order";
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

            cuegroup(CueOutbound)
            {
                Caption = 'Outbound / Warehouse Shipment';

                field(MyWhShipReleased; vMyWhShipReleased)
                {
                    Caption = 'To be Shipped';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Warehouse Shipment (WS) yang sudah di "Released" dan siap untuk di Post "Ship" / di kirim';

                    trigger OnDrillDown()
                    var
                        vWSH: Record "Warehouse Shipment Header";
                        pWSHList: Page "Warehouse Shipment List";
                    begin
                        vWSH.Reset();
                        vWSH.SetRange(Status, vWSH.Status::Released);
                        vWSH.SetFilter("Location Code", vMyLocCodeAllowed);

                        pWSHList.SetTableView(vWSH);
                        pWSHList.Run();
                    end;
                }

                field(MyWhShipPartiallyShipped; vMyWhShipPartiallyShipped)
                {
                    Caption = 'Patially Shipped';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Warehouse Shipment (WS) yang sudah di "Shipped" / di kirim sebagian di salah satu Item nya';

                    trigger OnDrillDown()
                    var
                        vWSH: Record "Warehouse Shipment Header";
                        pWSHList: Page "Warehouse Shipment List";
                    begin
                        vWSH.Reset();
                        vWSH.SetRange("Document Status", vWSH."Document Status"::"Partially Shipped");
                        vWSH.SetFilter("Location Code", vMyLocCodeAllowed);

                        pWSHList.SetTableView(vWSH);
                        pWSHList.Run();
                    end;
                }
            }


            cuegroup(CueInbound)
            {
                Caption = 'Inbound / Warehouse Receipt';

                field(MyWhRcvReleased; vMyWhRcvReleased)
                {
                    Caption = 'To be Received';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Warehouse Receipt (WR) yang belum di Post "Receive" / di terima';

                    trigger OnDrillDown()
                    var
                        vWRH: Record "Warehouse Receipt Header";
                        pWRHList: Page "Warehouse Receipts";
                    begin
                        vWRH.Reset();
                        vWRH.SetRange("Document Status", vWRH."Document Status"::" ");
                        vWRH.SetFilter("Location Code", vMyLocCodeAllowed);

                        pWRHList.SetTableView(vWRH);
                        pWRHList.Run();
                    end;
                }

                field(MyWhRcvPartiallyReceived; vMyWhRcvPartiallyReceived)
                {
                    Caption = 'Patially Received';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Warehouse Receipt (WR) yang sudah di "Recieved" / di terima sebagian di salah satu Item nya';

                    trigger OnDrillDown()
                    var
                        vWRH: Record "Warehouse Receipt Header";
                        pWRHList: Page "Warehouse Receipts";
                    begin
                        vWRH.Reset();
                        vWRH.setRange("Document Status", vWRH."Document Status"::"Partially Received");
                        vWRH.SetFilter("Location Code", vMyLocCodeAllowed);

                        pWRHList.SetTableView(vWRH);
                        pWRHList.Run();
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

            cuegroup(CuePostedDocument)
            {
                Caption = 'Posted Documents - Created by TODAY';

                field(MySalesShipmentTODAY; vMySalesShipmentTODAY)
                {
                    Caption = 'Sales Shipment';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Sales Shipments / "Surat Jalan (DO)" yang dibuat pada hari ini';

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

                field(MyPurchaseReceiptTODAY; vMyPurchaseReceiptTODAY)
                {
                    Caption = 'Purchase Receipt';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Purchase Receipts / "GR" yang dibuat pada hari ini';

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
            }


        }
    }


    trigger OnAfterGetCurrRecord()
    var
        MyWarehouseEmployee: Record "Warehouse Employee";

        MyWarehouseShipmentHeader: Record "Warehouse Shipment Header";
        MyWarehouseReceiptHeader: Record "Warehouse Receipt Header";

        MyTransferHeader: Record "Transfer Header";
        MyTransferLine: Record "Transfer Line";

        MySalesShipmentHeader: Record "Sales Shipment Header";
        MyPurchRcptHeader: Record "Purch. Rcpt. Header";

        MyGLRegister: Record "G/L Register";
        MyGLEntry: Record "G/L Entry";

    begin
        vMyLocCodeAllowed := '.';
        MyWarehouseEmployee.Reset();
        MyWarehouseEmployee.SetFilter("User ID", UserId);

        if MyWarehouseEmployee.FindSet() then
            repeat
            begin
                vMyLocCodeAllowed := vMyLocCodeAllowed + '|' + MyWarehouseEmployee."Location Code";
            end;
            until MyWarehouseEmployee.NEXT = 0;

        // Outbound / Warehouse Shipment - [To be Shipped | Patially Shipped] =================================================== START

        // Outbound / Warehouse Shipment - To be Shipped
        MyWarehouseShipmentHeader.Reset();
        MyWarehouseShipmentHeader.SetRange(Status, MyWarehouseShipmentHeader.Status::Released);
        MyWarehouseShipmentHeader.SetFilter("Location Code", vMyLocCodeAllowed);
        vMyWhShipReleased := MyWarehouseShipmentHeader.Count;

        // Outbound / Warehouse Shipment - Patially Shipped
        MyWarehouseShipmentHeader.Reset();
        MyWarehouseShipmentHeader.SetRange("Document Status", MyWarehouseShipmentHeader."Document Status"::"Partially Shipped");
        MyWarehouseShipmentHeader.SetFilter("Location Code", vMyLocCodeAllowed);
        vMyWhShipPartiallyShipped := MyWarehouseShipmentHeader.Count;

        // Outbound / Warehouse Shipment - [To be Shipped | Patially Shipped] =================================================== END

        // Inbound / Warehouse Receipt - [To be Received | Patially Received] ================================================== START

        // Inbound / Warehouse Receipt - To be Received
        MyWarehouseReceiptHeader.Reset();
        MyWarehouseReceiptHeader.SetRange("Document Status", MyWarehouseReceiptHeader."Document Status"::" ");
        MyWarehouseReceiptHeader.SetFilter("Location Code", vMyLocCodeAllowed);
        vMyWhRcvReleased := MyWarehouseReceiptHeader.Count;

        // Inbound / Warehouse Receipt - Patially Received
        MyWarehouseReceiptHeader.Reset();
        MyWarehouseReceiptHeader.setRange("Document Status", MyWarehouseReceiptHeader."Document Status"::"Partially Received");
        MyWarehouseReceiptHeader.SetFilter("Location Code", vMyLocCodeAllowed);
        vMyWhRcvPartiallyReceived := MyWarehouseReceiptHeader.Count;

        // Inbound / Warehouse Receipt	- [To be Received | Patially Received] ================================================== END

        // Internal / Transfer Order - [To be Shipped | To be Received] ========================================================= START

        // Internal / Transfer Order - To be Shipped
        vMyTOReleased := 0;
        vMyTOReleasedNumber := '.';

        // Internal / Transfer Order - To be Received
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
            until MyTransferHeader.NEXT = 0;

        // Internal / Transfer Order - [To be Shipped | To be Received] ========================================================= END

        // Posted Documents - Created by TODAY -  [Sales Shipment | Purchase Receipt] =========================================== START

        vMySalesShipNumber := 'X';
        vMyPrevSalesShipNumber := 'U';

        vMyPurchRcptNumber := 'X';
        vMyPrevPurchRcptNumber := 'U';

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
                        if COPYSTR(MyGLEntry."Document No.", 1, 2) in ['DO', '8D', '9D'] then
                            if MyGLEntry."Document No." <> vMyPrevSalesShipNumber then begin
                                vMyPrevSalesShipNumber := MyGLEntry."Document No.";
                                vMySalesShipNumber := vMySalesShipNumber + '|' + MyGLEntry."Document No.";
                            end;

                        if COPYSTR(MyGLEntry."Document No.", 1, 2) in ['GR'] then
                            if MyGLEntry."Document No." <> vMyPrevPurchRcptNumber then begin
                                vMyPrevPurchRcptNumber := MyGLEntry."Document No.";
                                vMyPurchRcptNumber := vMyPurchRcptNumber + '|' + MyGLEntry."Document No.";
                            end;
                    until MyGLEntry.NEXT = 0;
            end;
            until MyGLRegister.NEXT = 0;

        // Posted Documents - Created by TODAY -  Sales Shipment
        MySalesShipmentHeader.Reset();
        MySalesShipmentHeader.SetFilter("No.", vMySalesShipNumber);
        vMySalesShipmentTODAY := MySalesShipmentHeader.Count;


        // Posted Documents - Created by TODAY -  Purchase Receipt
        MyPurchRcptHeader.Reset();
        MyPurchRcptHeader.SetFilter("No.", vMyPurchRcptNumber);
        vMyPurchaseReceiptTODAY := MyPurchRcptHeader.Count;

        // Posted Documents - Created by TODAY -  [Sales Shipment | Purchase Receipt] =========================================== END
    end;

    var
        vMyWhShipReleased: Integer;
        vMyWhShipPartiallyShipped: Integer;

        vMyWhRcvReleased: Integer;
        vMyWhRcvPartiallyReceived: Integer;

        vMyTOReleased: Integer;
        vMyTOShipped: Integer;

        vMySalesShipmentTODAY: Integer;
        vMyPurchaseReceiptTODAY: Integer;

        vMyLocCodeAllowed: Text;
        vMyTOReleasedNumber: Text;
        vMyTOShippedNumber: Text;

        vMyQtyTO: Integer;
        vMyQtyShippedTO: Integer;

        vMySalesShipNumber: Text;
        vMyPrevSalesShipNumber: Text;

        vMyPurchRcptNumber: Text;
        vMyPrevPurchRcptNumber: Text;

        Style: Text;
        vUserId: Text;

}