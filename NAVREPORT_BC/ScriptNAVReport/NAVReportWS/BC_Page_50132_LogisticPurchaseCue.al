page 50132 "Logistic (Purchase) Cue"
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

            cuegroup(LogisticPurchaseActionontainer)
            {
                Caption = 'Create / New - Shortcut';

                actions
                {

                    action("Blanket Purchase Order")
                    {
                        ApplicationArea = All;
                        RunObject = page "Blanket Purchase Order";
                        RunPageMode = Create;
                        Image = TileReport;
                    }

                    action("Purchase Order")
                    {
                        ApplicationArea = All;
                        RunObject = page "Purchase Order";
                        RunPageMode = Create;
                        Image = TileReport;
                    }


                    action("Sales Reservation Avail.")
                    {
                        ApplicationArea = All;
                        RunObject = report "Sales Reservation Avail.";
                        RunPageMode = Create;
                        Image = TileReport;
                    }

                }
            }

            cuegroup(CueBPO)
            {
                Caption = 'Blanket Purchase Order (BPO) - Outstanding';

                field(MyOutBPOOTP; vMyOutBPOOTP)
                {
                    Caption = 'OTP';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Blanket Purchase Order (BPO) "OTP" yang detail Barang-nya masih ada yang Outstanding (belum diterima dan atau belum di-Invoice)';

                    trigger OnDrillDown()
                    var
                        vPH: Record "Purchase Header";
                        pPHList: Page "Blanket Purchase Orders";
                    begin
                        vPH.Reset();
                        vPH.SetRange("Document Type", vPH."Document Type"::"Blanket Order");
                        vPH.SetFilter("No.", vMyOutBPOOTPNumber);

                        pPHList.SetTableView(vPH);
                        pPHList.Run();
                    end;
                }

                field(MyOutBPOTrade; vMyOutBPOTrade)
                {
                    Caption = 'Trade';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Blanket Purchase Order (BPO) "Trade" yang detail Barang-nya masih ada yang Outstanding (belum diterima dan atau belum di-Invoice)';

                    trigger OnDrillDown()
                    var
                        vPH: Record "Purchase Header";
                        pPHList: Page "Blanket Purchase Orders";
                    begin
                        vPH.Reset();
                        vPH.SetRange("Document Type", vPH."Document Type"::"Blanket Order");
                        vPH.SetFilter("No.", vMyOutBPOTradeNumber);

                        pPHList.SetTableView(vPH);
                        pPHList.Run();
                    end;
                }

                field(MyOutBPONonTrade; vMyOutBPONonTrade)
                {
                    Caption = 'Non Trade';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Blanket Purchase Order (BPO) "Non Trade" yang detail Barang-nya masih ada yang Outstanding (belum diterima dan atau belum di-Invoice)';

                    trigger OnDrillDown()
                    var
                        vPH: Record "Purchase Header";
                        pPHList: Page "Blanket Purchase Orders";
                    begin
                        vPH.Reset();
                        vPH.SetRange("Document Type", vPH."Document Type"::"Blanket Order");
                        vPH.SetFilter("No.", vMyOutBPONonTradeNumber);

                        pPHList.SetTableView(vPH);
                        pPHList.Run();
                    end;
                }
            }

            cuegroup(CuePO)
            {
                Caption = 'Purchase Order (PO) - Outstanding';

                field(MyOutPOOTP; vMyOutPOOTP)
                {
                    Caption = 'OTP';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Purchase Order (PO) "OTP" yang detail Barang-nya masih ada yang Outstanding (belum diterima dan atau belum di-Invoice)';

                    trigger OnDrillDown()
                    var
                        vPH: Record "Purchase Header";
                        pPHList: Page "Purchase Order List";
                    begin
                        vPH.Reset();
                        vPH.SetRange("Document Type", vPH."Document Type"::Order);
                        vPH.SetFilter("No.", vMyOutPOOTPNumber);

                        pPHList.SetTableView(vPH);
                        pPHList.Run();
                    end;
                }

                field(MyOutPOTrade; vMyOutPOTrade)
                {
                    Caption = 'Trade';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Purchase Order (PO) "Trade" yang detail Barang-nya masih ada yang Outstanding (belum diterima dan atau belum di-Invoice)';

                    trigger OnDrillDown()
                    var
                        vPH: Record "Purchase Header";
                        pPHList: Page "Purchase Order List";
                    begin
                        vPH.Reset();
                        vPH.SetRange("Document Type", vPH."Document Type"::Order);
                        vPH.SetFilter("No.", vMyOutPOTradeNumber);

                        pPHList.SetTableView(vPH);
                        pPHList.Run();
                    end;
                }

                field(MyOutPONonTrade; vMyOutPONonTrade)
                {
                    Caption = 'Non Trade';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Purchase Order (PO) "Non Trade" yang detail Barang-nya masih ada yang Outstanding (belum diterima dan atau belum di-Invoice)';

                    trigger OnDrillDown()
                    var
                        vPH: Record "Purchase Header";
                        pPHList: Page "Purchase Order List";
                    begin
                        vPH.Reset();
                        vPH.SetRange("Document Type", vPH."Document Type"::Order);
                        vPH.SetFilter("No.", vMyOutPONonTradeNumber);

                        pPHList.SetTableView(vPH);
                        pPHList.Run();
                    end;
                }
            }

            cuegroup(CuePOOther)
            {
                Caption = 'Purchase Order (PO) - Other Status';

                field(MyPOOverEstTrade; vMyPOOverEstTrade)
                {
                    Caption = 'Over "Exp. Rcpt. Date" (Trade)';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Purchase Order (PO) "Trade" yang salah satu detail Barang-nya ada yang melebihi waktu yang sudah di estimasi kan akan tiba (Expected Receipt Date > TODAY)';

                    trigger OnDrillDown()
                    var
                        vPH: Record "Purchase Header";
                        pPHList: Page "Purchase Order List";
                    begin
                        vPH.Reset();
                        vPH.SetRange("Document Type", vPH."Document Type"::Order);
                        vPH.SetFilter("No.", vMyPOOverEstTradeNumber);

                        pPHList.SetTableView(vPH);
                        pPHList.Run();
                    end;
                }
            }

            cuegroup(CueSO)
            {
                Caption = 'Sales Order (SO)';

                field(MySOTOTPBooking; vMySOTOTPBooking)
                {
                    Caption = 'OTP / Booking';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) "Trade" khusus OTP ataupun "Bookingan"';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetFilter("No. Series", 'SOT');
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetFilter("External Document No.", '*OTP*|*BOOKING*');

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySONoReservation; vMySONoReservation)
                {
                    Caption = 'Need Reservation';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO)  yang statusnya  "Released" dan belum ada Reservasi.';

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

            cuegroup(cuePostedDocuments)
            {
                Caption = 'Posted Documents - Created by TODAY';
                field(MyPurchaseReceiveTODAY; vMyPurchaseReceiveTODAY)
                {
                    Caption = 'Purchase Receipts (GR)';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Purchase Receipts / "GR" yang dibuat pada hari ini';

                    trigger OnDrillDown()
                    var
                        vPRH: Record "Purch. Rcpt. Header";
                        pPRHList: Page "Posted Purchase Receipts";
                    begin
                        vPRH.Reset();
                        vPRH.SetFilter("No.", vMyPurchaseReceiptsNumber);

                        pPRHList.SetTableView(vPRH);
                        pPRHList.Run();
                    end;
                }

                field(MyPurchaseInvoiceToday; vMyPurchaseInvoiceToday)
                {
                    Caption = 'Purchase Invoices';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Purchase Invoice / "Invoice AP" yang dibuat pada hari ini';

                    trigger OnDrillDown()
                    var
                        vPIH: Record "Purch. Inv. Header";
                        pPIHList: Page "Posted Purchase Invoices";
                    begin
                        vPIH.Reset();
                        vPIH.SetFilter("No.", vMyPurchaseInvoiceNumber);

                        pPIHList.SetTableView(vPIH);
                        pPIHList.Run();
                    end;
                }

                field(MyPurchaseCreditMemoTODAY; vMyPurchaseCreditMemoTODAY)
                {
                    Caption = 'Purchase Credit Memo';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Purchase Credit Memo / "Cancel Invoice AP" yang dibuat pada hari ini';

                    trigger OnDrillDown()
                    var
                        vPCMH: Record "Purch. Cr. Memo Hdr.";
                        pPCMList: Page "Posted Purchase Credit Memos";
                    begin
                        vPCMH.Reset();
                        vPCMH.SetFilter("No.", vMyPurchaseInvoiceNumber);

                        pPCMList.SetTableView(vPCMH);
                        pPCMList.Run();
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

        MyItem: Record "Item";

        MyGLRegister: Record "G/L Register";
        MyGLEntry: Record "G/L Entry";
        MyPurchRcptHeader: Record "Purch. Rcpt. Header";
        MyPurchInvHeader: Record "Purch. Inv. Header";
        MyPurchCrMemoHeader: Record "Purch. Cr. Memo Hdr.";

    begin
        // Blanket Purchase Order (BPO) - Outstanding [OTP|Trade|Non Trade] ============================================ START
        MyPurchaseHeader.Reset();
        MyPurchaseHeader.SetRange("Document Type", MyPurchaseHeader."Document Type"::"Blanket Order");

        // Blanket Purchase Order (BPO) - Outstanding - OTP
        vMyOutBPOOTP := 0;
        vMyOutBPOOTPNumber := '.';

        // Blanket Purchase Order (BPO) - Outstanding - Trade
        vMyOutBPOTrade := 0;
        vMyOutBPOTradeNumber := '.';

        // Blanket Purchase Order (BPO) - Outstanding - Non Trade
        vMyOutBPONonTrade := 0;
        vMyOutBPONonTradeNumber := '.';

        if MyPurchaseHeader.FindSet() then
            repeat
            begin
                MyPurchaseLine.Reset();
                MyPurchaseLine.SetFilter("Document No.", MyPurchaseHeader."No.");
                MyPurchaseLine.SetRange("Document Type", MyPurchaseHeader."Document Type");
                MyPurchaseLine.SetFilter("Outstanding Quantity", '<>0');

                IF MyPurchaseLine.Count <> 0 then begin
                    IF MyPurchaseHeader."No. Series" = 'PBOTP' then begin
                        vMyOutBPOOTP := vMyOutBPOOTP + 1;
                        vMyOutBPOOTPNumber := vMyOutBPOOTPNumber + '|' + MyPurchaseHeader."No.";
                    end;

                    IF MyPurchaseHeader."No. Series" = 'PBOT' then begin
                        vMyOutBPOTrade := vMyOutBPOTrade + 1;
                        vMyOutBPOTradeNumber := vMyOutBPOTradeNumber + '|' + MyPurchaseHeader."No.";
                    end;

                    IF MyPurchaseHeader."No. Series" = 'PBON' then begin
                        vMyOutBPONonTrade := vMyOutBPONonTrade + 1;
                        vMyOutBPONonTradeNumber := vMyOutBPONonTradeNumber + '|' + MyPurchaseHeader."No.";
                    end;
                end;
            end;
            until MyPurchaseHeader.Next() = 0;

        // Blanket Purchase Order (BPO) - Outstanding [OTP|Trade|Non Trade] ============================================ END



        // Purchase Order (PO) - Outstanding [OTP|Trade|Non Trade]  / Other Status - Over "Exp. Rcpt. Date" ============ START
        MyPurchaseHeader.Reset();
        MyPurchaseHeader.SetRange("Document Type", MyPurchaseHeader."Document Type"::Order);
        MyPurchaseHeader.SetFilter("Order Date", '>=0101' + FORMAT(DATE2DMY(TODAY, 3) - 2));
        //MESSAGE('>=0101'+FORMAT(DATE2DMY(TODAY,3) - 2));

        // Purchase Order (PO) - Outstanding - OTP
        vMyOutPOOTP := 0;
        vMyOutPOOTPNumber := '.';

        // Purchase Order (PO) - Outstanding - Trade
        vMyOutPOTrade := 0;
        vMyOutPOTradeNumber := '.';

        // Purchase Order (PO) - Outstanding - Non Trade
        vMyOutPONonTrade := 0;
        vMyOutPONonTradeNumber := '.';


        // Purchase Order (PO) - Other Status - Over "Exp. Rcpt. Date" (Trade)
        vMyPOOverEstTrade := 0;
        vMyPOOverEstTradeNumber := '.';

        if MyPurchaseHeader.FindSet() then
            repeat
            begin
                MyPurchaseLine.Reset();
                MyPurchaseLine.SetFilter("Document No.", MyPurchaseHeader."No.");
                MyPurchaseLine.SetRange("Document Type", MyPurchaseHeader."Document Type");
                MyPurchaseLine.SetFilter("Outstanding Quantity", '<>0');

                if MyPurchaseLine.Count <> 0 then begin
                    if MyPurchaseHeader."No. Series" = 'POTP' then begin
                        vMyOutPOOTP := vMyOutPOOTP + 1;
                        vMyOutPOTradeNumber := vMyOutPOTradeNumber + '|' + MyPurchaseHeader."No.";
                    end;

                    if MyPurchaseHeader."No. Series" = 'POT' then begin
                        vMyOutPOTrade := vMyOutPOTrade + 1;
                        vMyOutPOTradeNumber := vMyOutPOTradeNumber + '|' + MyPurchaseHeader."No.";
                    end;

                    if MyPurchaseHeader."No. Series" = 'PON' then begin
                        vMyOutPONonTrade := vMyOutPONonTrade + 1;
                        vMyOutPONonTradeNumber := vMyOutPONonTradeNumber + '|' + MyPurchaseHeader."No.";
                    end;

                end;

                MyPurchaseLine.SetFilter("Expected Receipt Date", '<' + FORMAT(TODAY));
                if MyPurchaseLine.Count > 0 then begin
                    if MyPurchaseHeader."No. Series" = 'POT' then begin
                        vMyPOOverEstTrade := vMyPOOverEstTrade + 1;
                        vMyPOOverEstTradeNumber := vMyPOOverEstTradeNumber + '|' + MyPurchaseHeader."No.";
                    end;
                end;

            end;
            until MyPurchaseHeader.Next() = 0;

        // Purchase Order (PO) - Outstanding [OTP|Trade|Non Trade]  / Other Status - Over "Exp. Rcpt. Date" ============ END



        // Sales Order (SO) -  [OTP / Booking |Need Reservation] ======================================================= START

        // Sales Order (SO) - OTP / Booking
        MySalesHeader.Reset();
        MySalesHeader.SetFilter("No. Series", 'SOT');
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetFilter("External Document No.", '*OTP*|*BOOKING*');
        vMySOTOTPBooking := MySalesHeader.COUNT;


        // Sales Order (SO) - Need Reservation
        MySalesHeader.Reset();
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange(Status, MySalesHeader.Status::Released);
        MySalesHeader.SetFilter("No. Series", 'SOT|SOS');

        vMySONoReservation := 0;
        vMySONoReservationNumber := '.';

        if MySalesHeader.FindSet() then
            repeat
            begin
                MySalesLine.Reset();
                MySalesLine.SetRange("Document Type", MySalesLine."Document Type");
                MySalesLine.SetFilter("Document No.", MySalesHeader."No.");
                MySalesLine.SetRange(Type, MySalesLine.Type::Item);
                //MySalesLine.SetFilter("Item Category Code", 'HW01|PS01|PS04');
                //MySalesLine.SetFilter("Product Group Code", '<>S403&<>S404');
                MySalesLine.SetFilter("Reserved Quantity", '=0');
                MySalesLine.SetFilter("Outstanding Quantity", '<>0');

                vMyCounterNoReserve := 0;
                if MySalesLine.FindSet() then
                    repeat
                    begin
                        MyItem.Reset();
                        MyItem.SetFilter("No.", MySalesLine."No.");
                        MyItem.FindFirst();

                        if MyItem."Inventory Value Zero" = false then
                            vMyCounterNoReserve := vMyCounterNoReserve + 1;
                    end;
                    until MySalesLine.Next() = 0;

                if (vMyCounterNoReserve <> 0) then begin
                    vMySONoReservation := vMySONoReservation + 1;
                    vMySONoReservationNumber := vMySONoReservationNumber + '|' + MySalesHeader."No.";
                end;
            end;
            until MySalesHeader.Next() = 0;



        // Sales Order (SO) -  [OTP / Booking |Need Reservation] ======================================================= END

        // Posted Documents - Created by TODAY -  [Purchase Receipts (GR) | Purchase Invoice | Purchase Invoice Credit Memo ] === START

        vMyPurchaseInvoiceNumber := 'X';
        vMyPurchaseReceiptsNumber := 'X';
        vMyPrevPurchaseReceiptsNumber := 'U';

        MyGLRegister.Reset();
        MyGLRegister.SetFilter("Creation Date", FORMAT(TODAY));
        MyGLRegister.SetFilter("Source Code", 'INVTPCOST|PURCHASES');

        if MyGLRegister.FindSet() then
            repeat
            begin
                MyGLEntry.Reset();
                MyGLEntry.SetFilter("Entry No.", FORMAT(MyGLRegister."From Entry No."));

                if MyGLEntry.FindSet() then
                    repeat
                        if (MyGLRegister."Source Code" = 'PURCHASES') then
                            vMyPurchaseInvoiceNumber := vMyPurchaseInvoiceNumber + '|' + MyGLEntry."Document No.";

                        if COPYSTR(MyGLEntry."Document No.", 1, 2) IN ['GR'] then
                            if MyGLEntry."Document No." <> vMyPrevPurchaseReceiptsNumber then begin
                                vMyPrevPurchaseReceiptsNumber := MyGLEntry."Document No.";
                                vMyPurchaseReceiptsNumber := vMyPurchaseReceiptsNumber + '|' + MyGLEntry."Document No.";
                            end;
                    until MyGLEntry.NEXT = 0;
            end;
            until MyGLRegister.NEXT = 0;

        // Posted Documents - Created by TODAY - Purchase Receipts (GR)
        vMyPurchaseReceiveTODAY := 0;
        MyPurchRcptHeader.Reset();
        MyPurchRcptHeader.SetFilter("No.", vMyPurchaseReceiptsNumber);
        vMyPurchaseReceiveTODAY := MyPurchRcptHeader.Count;

        // Posted Documents - Created by TODAY - Purchase Invoice
        vMyPurchaseInvoiceTODAY := 0;
        MyPurchInvHeader.Reset();
        MyPurchInvHeader.SetFilter("No.", vMyPurchaseInvoiceNumber);
        vMyPurchaseInvoiceTODAY := MyPurchInvHeader.Count;

        // Posted Documents - Created by TODAY - Purchase Invoice Credit Memo
        vMyPurchaseCreditMemoTODAY := 0;
        MyPurchCrMemoHeader.Reset();
        MyPurchCrMemoHeader.SetFilter("No.", vMyPurchaseInvoiceNumber);
        vMyPurchaseCreditMemoTODAY := MyPurchCrMemoHeader.Count;


        // Posted Documents - Created by TODAY -  [Purchase Receipts (GR) | Purchase Invoice | Purchase Invoice Credit Memo ] === END
    end;

    var
        vMyOutBPOOTP: Integer;
        vMyOutBPOTrade: Integer;
        vMyOutBPONonTrade: Integer;

        vMyOutPOOTP: Integer;
        vMyOutPOTrade: Integer;
        vMyOutPONonTrade: Integer;

        vMyPOOverEstTrade: Integer;

        vMySOTOTPBooking: Integer;
        vMySONoReservation: Integer;

        vMyPurchaseReceiveTODAY: Integer;
        vMyPurchaseInvoiceTODAY: Integer;
        vMyPurchaseCreditMemoTODAY: Integer;


        vMyOutBPOOTPNumber: Text;
        vMyOutBPOTradeNumber: Text;
        vMyOutBPONonTradeNumber: Text;

        vMyOutPOOTPNumber: Text;
        vMyOutPOTradeNumber: Text;
        vMyOutPONonTradeNumber: Text;
        vMyPOOverEstTradeNumber: Text;
        vMySONoReservationNumber: Text;

        vMyPurchaseInvoiceNumber: Text;
        vMyPurchaseReceiptsNumber: Text;
        vMyPrevPurchaseReceiptsNumber: Text;

        vMyCounterNoReserve: Integer;

        Style: Text;
        vUserId: Text;

}