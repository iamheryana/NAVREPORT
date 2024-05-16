page 50130 "SAO Cue"
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

            cuegroup(SAOActionontainer)
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

                    action("Selespeople")
                    {
                        ApplicationArea = All;
                        RunObject = page "Salesperson/Purchaser Card";
                        RunPageMode = Create;
                        Image = TilePeople;
                    }

                    action("Contact")
                    {
                        ApplicationArea = All;
                        RunObject = page "Contact Card";
                        RunPageMode = Create;
                        Image = TileNew;
                    }

                    action("Customer")
                    {
                        ApplicationArea = All;
                        RunObject = page "Customer Card";
                        RunPageMode = Create;
                        Image = TilePeople;
                    }

                    action("Sales Order")
                    {
                        ApplicationArea = All;
                        RunObject = page "Sales Order";
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


                    // action("Efaktur Master Mapping")
                    // {
                    //     ApplicationArea = All;
                    // #003
                    //     RunObject = page "Warehouse Shipment";
                    //     RunPageMode = Create;
                    //     Image = TileNew;
                    // }


                }
            }
            cuegroup(CueBSO)
            {
                Caption = 'Blanket Sales Order';

                field(MyBSOForMakeOrder; vMyBSOForMakeOrder)
                {
                    Caption = 'For Make Order';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Blanket Sales Order (BSO) yang belum atau siap untuk di "Make Order" menjadi Sales Order (SO)';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pBSOList: Page "Blanket Sales Orders";
                    begin
                        vSH.Reset();
                        vSH.SetRange("Document Type", vSH."Document Type"::"Blanket Order");
                        vSH.SetFilter("No.", vMyBSOForMakeOrderNo);

                        pBSOList.SetTableView(vSH);
                        pBSOList.Run();
                    end;
                }

                field(MyCustomerBlocked; vMyCustomerBlocked)
                {
                    Caption = 'Customer Blocked';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Data Master Customer yang di Bloked';

                    trigger OnDrillDown()
                    var
                        vCust: Record "Customer";
                        pCustList: Page "Customer List";
                    begin
                        vCust.Reset();
                        vCust.SetFilter(Blocked, '<>0');

                        pCustList.SetTableView(vCust);
                        pCustList.Run();
                    end;
                }
            }

            cuegroup(cueSO)
            {
                Caption = 'Sales Order';
                field(MySOForReleased; vMySOForReleased)
                {
                    Caption = 'For Release';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) "Trade" atau "Rental / Managed Service" yang statusnya "Open" dan siap untuk di "Release / Proceed to Logistic"';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetFilter("No. Series", 'SOT|SOR');
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetFilter("External Document No.", '<>*OTP*&<>*BOOKING*');
                        vSH.SetFilter("No.", '<>SOAT160274'); //ada kasus di 2016, SO tersebut sudah diselesaikan oleh Accounting
                        vSH.SetRange("Status", vSH."Status"::Open);
                        vSH.SetFilter("Responsibility Center", vMyUserLocationBranch);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySOTReleasedToday; vMySOTReleasedToday)
                {
                    Caption = 'Released by TODAY';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) "Trade"  atau "Rental / Managed Service" yang baru di "Released" hari ini';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetFilter("No. Series", 'SOT|SOR');
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH."Status"::Released);
                        // #002 belum bisa dilakuka karena cutomisasi field
                        //vSH.SetFilter("Released Date" , FORMAT(TODAY));
                        vSH.SetFilter("Responsibility Center", vMyUserLocationBranch);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySOTOTPBooking; vMySOTOTPBooking)
                {
                    Caption = 'OTP - Booking';
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
                        vSH.SetFilter("Responsibility Center", vMyUserLocationBranch);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

            }

            cuegroup(cueSOKhususReleased)
            {
                Caption = 'Sales Order (Khusus) - Released';
                field(MySOSAT; vMySOSAT)
                {
                    Caption = 'SAT';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) khusus transaksi SAT / AFLA dengan kode dokumen khusus (8SOAT / 8SOST) yang statusnya "Released"';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetFilter("No. Series", 'SOO');
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH."Status"::Released);
                        vSH.SetFilter("Responsibility Center", vMyUserLocationBranch);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySOIdmr; vMySOIdmr)
                {
                    Caption = 'INDOMARCO';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) khusus transaksi INDOMARCO dengan kode dokumen khusus (9SOAT / 9SOST) yang statusnya "Released"';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetFilter("No. Series", 'SOI');
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH."Status"::Released);
                        vSH.SetFilter("Responsibility Center", vMyUserLocationBranch);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySORental; vMySORental)
                {
                    Caption = 'Rental / Managed Service';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) khusus transaksi "Rental" / "Managed Service" dengan kode dokumen khusus (SOAR / SOSR) yang statusnya "Released"';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetFilter("No. Series", 'SOR');
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH."Status"::Released);
                        vSH.SetFilter("Responsibility Center", vMyUserLocationBranch);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

                field(MySOSC; vMySOSC)
                {
                    Caption = 'Service Center';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Sales Order (SO) khusus transaksi "Service Center" dengan kode dokumen khusus (SOAS / SOSS) yang statusnya "Released"';

                    trigger OnDrillDown()
                    var
                        vSH: Record "Sales Header";
                        pSOList: Page "Sales Order List";
                    begin
                        vSH.Reset();
                        vSH.SetFilter("No. Series", 'SOS');
                        vSH.SetRange("Document Type", vSH."Document Type"::Order);
                        vSH.SetRange("Status", vSH."Status"::Released);
                        vSH.SetFilter("Responsibility Center", vMyUserLocationBranch);

                        pSOList.SetTableView(vSH);
                        pSOList.Run();
                    end;
                }

            }

            cuegroup(cuePostedDocuments)
            {
                Caption = 'Posted Documents - Created by TODAY';
                field(MyDOToday; vMyDOToday)
                {
                    Caption = 'Sales Shipments (DO)';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Sales Shipment (DO) / "Surat Jalan" yang dibuat pada hari ini';

                    trigger OnDrillDown()
                    var
                        vSSH: Record "Sales Shipment Header";
                        pSSHList: Page "Posted Sales Shipments";
                    begin
                        vSSH.Reset();
                        vSSH.SetFilter("No.", vMySalesShipmentNo);

                        pSSHList.SetTableView(vSSH);
                        pSSHList.Run();
                    end;
                }

                field(MySalesInvoiceToday; vMySalesInvoiceToday)
                {
                    Caption = 'Sales Invoices';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Sales Invoice / "Invoice" yang dibuat pada hari ini';

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

                field(MySalesCMToday; vMySalesCMToday)
                {
                    Caption = 'Sales Credit Memo';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan Jumlah Dokumen Posted Sales Credit Memo / "Cancel Invoice" yang dibuat pada hari ini';

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
        }
    }


    trigger OnAfterGetCurrRecord()
    var

        MyUserLocation: Record "User Location";
        MySalesHeader: Record "Sales Header";
        MySalesLine: Record "Sales Line";
        MySalesInvoiceLine: Record "Sales Invoice Line";
        MyCustomer: Record Customer;
        MyGLRegister: Record "G/L Register";
        MyGLEntry: Record "G/L Entry";
        MySalesShipmentHeader: Record "Sales Shipment Header";
        MySalesInvoiceHeader: Record "Sales Invoice Header";
        MySalesCreditMemoHeader: Record "Sales Cr.Memo Header";

    begin
        MyUserLocation.Reset();
        MyUserLocation.SetFilter("UserID", UserId);
        MyUserLocation.FindFirst();
        vMyUserLocationBranch := MyUserLocation.Branches;

        // For Make Order
        MySalesHeader.Reset();
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::"Blanket Order");
        MySalesHeader.SetFilter("No. Series", '<>SBOM&<>SBOMS');
        MySalesHeader.SetFilter("Order Date", '>=01012020');
        MySalesHeader.SetFilter("Responsibility Center", vMyUserLocationBranch);

        vMyBSOForMakeOrder := 0;
        vMyBSOForMakeOrderNo := '.';

        IF MySalesHeader.FindSet() then
            repeat
            begin
                MySalesInvoiceLine.Reset();
                MySalesInvoiceLine.SetFilter("Blanket Order No.", MySalesHeader."No.");

                MySalesLine.Reset();
                MySalesLine.SetFilter("Blanket Order No.", MySalesHeader."No.");
                MySalesLine.SetRange("Document Type", MySalesLine."Document Type"::Order);

                IF (MySalesLine.Count = 0) AND (MySalesInvoiceLine.Count = 0) then begin
                    vMyBSOForMakeOrder := vMyBSOForMakeOrder + 1;
                    vMyBSOForMakeOrderNo := vMyBSOForMakeOrderNo + '|' + MySalesHeader."No.";
                end;
            end;
            until MySalesHeader.Next() = 0;


        //Customer Blocked
        MyCustomer.Reset();
        MyCustomer.SetFilter(Blocked, '<>0');
        vMyCustomerBlocked := MyCustomer.Count;


        //Sales Order - For Release
        MySalesHeader.Reset();
        MySalesHeader.SetFilter("No. Series", 'SOT|SOR');
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetFilter("External Document No.", '<>*OTP*&<>*BOOKING*');
        MySalesHeader.SetFilter("No.", '<>SOAT160274'); //ada kasus di 2016, SO tersebut sudah diselesaikan oleh Accounting
        MySalesHeader.SetRange("Status", MySalesHeader."Status"::Open);
        MySalesHeader.SetFilter("Responsibility Center", vMyUserLocationBranch);
        vMySOForReleased := MySalesHeader.Count;


        // Sales Order - Trade - Released by Today
        MySalesHeader.Reset();
        MySalesHeader.SetFilter("No. Series", 'SOT|SOR');
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange("Status", MySalesHeader."Status"::Released);
        // #001 belum bisa dilakuka karena cutomisasi field
        //MySalesHeader.SetFilter("Released Date" , FORMAT(TODAY));
        MySalesHeader.SetFilter("Responsibility Center", vMyUserLocationBranch);
        vMySOTReleasedToday := MySalesHeader.COUNT;

        // Sales Order - Trade - OTP / Bookingan
        MySalesHeader.Reset();
        MySalesHeader.SetFilter("No. Series", 'SOT');
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetFilter("External Document No.", '*OTP*|*BOOKING*');
        MySalesHeader.SetFilter("Responsibility Center", vMyUserLocationBranch);
        vMySOTOTPBooking := MySalesHeader.Count;

        // Sales Order (Khusus) - SAT
        MySalesHeader.Reset();
        MySalesHeader.SetFilter("No. Series", 'SOO');
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange("Status", MySalesHeader."Status"::Released);
        MySalesHeader.SetFilter("Responsibility Center", vMyUserLocationBranch);
        vMySOSAT := MySalesHeader.COUNT;

        // Sales Order (Khusus) -INDOMARCO
        MySalesHeader.Reset();
        MySalesHeader.SetFilter("No. Series", 'SOI');
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange("Status", MySalesHeader."Status"::Released);
        MySalesHeader.SetFilter("Responsibility Center", vMyUserLocationBranch);
        vMySOIdmr := MySalesHeader.Count;

        // Sales Order (Khusus) -RENTAL / Maneged Service
        MySalesHeader.Reset();
        MySalesHeader.SetFilter("No. Series", 'SOR');
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange("Status", MySalesHeader."Status"::Released);
        MySalesHeader.SetFilter("Responsibility Center", vMyUserLocationBranch);
        vMySORental := MySalesHeader.Count;

        // Sales Order (Khusus) - Service Center
        MySalesHeader.Reset();
        MySalesHeader.SetFilter("No. Series", 'SOS');
        MySalesHeader.SetRange("Document Type", MySalesHeader."Document Type"::Order);
        MySalesHeader.SetRange("Status", MySalesHeader."Status"::Released);
        MySalesHeader.SetFilter("Responsibility Center", vMyUserLocationBranch);
        vMySOSC := MySalesHeader.Count;


        vMyNoSalesInvoice := 'X';
        vMySalesShipmentNo := 'X';
        vMyPrevSalesShipmentNo := 'U';

        MyGLRegister.Reset();
        MyGLRegister.SetFilter("Creation Date", FORMAT(TODAY));
        MyGLRegister.SetFilter("Source Code", 'INVTPCOST|SALES');

        if MyGLRegister.FindSet() then
            repeat
            begin
                MyGLEntry.Reset();
                MyGLEntry.SetFilter("Entry No.", FORMAT(MyGLRegister."From Entry No."));

                if MyGLEntry.FindSet() then
                    repeat
                        if (MyGLRegister."Source Code" = 'SALES') THEN
                            vMyNoSalesInvoice := vMyNoSalesInvoice + '|' + MyGLEntry."Document No.";

                        if COPYSTR(MyGLEntry."Document No.", 1, 2) IN ['DO', '8D', '9D'] then
                            if MyGLEntry."Document No." <> vMyPrevSalesShipmentNo then begin
                                vMyPrevSalesShipmentNo := MyGLEntry."Document No.";
                                vMySalesShipmentNo := vMySalesShipmentNo + '|' + MyGLEntry."Document No.";
                            end;
                    until MyGLEntry.Next() = 0;
            end;
            until MyGLRegister.Next() = 0;

        //Posted Documents Created by Today - Sales Shipment
        vMyDOToday := 0;
        MySalesShipmentHeader.Reset();
        MySalesShipmentHeader.SetFilter("No.", vMySalesShipmentNo);
        vMyDOToday := MySalesShipmentHeader.Count;

        //Posted Documents Created by Today - Sales Invoice 
        vMySalesInvoiceToday := 0;
        MySalesInvoiceHeader.Reset();
        MySalesInvoiceHeader.SetFilter("No.", vMyNoSalesInvoice);
        vMySalesInvoiceToday := MySalesInvoiceHeader.Count;

        //Posted Documents Created by Today - Sales Credit Memo
        vMySalesCMToday := 0;
        MySalesCreditMemoHeader.Reset();
        MySalesCreditMemoHeader.SetFilter("No.", vMyNoSalesInvoice);
        vMySalesCMToday := MySalesCreditMemoHeader.Count;
    end;


    var
        vMyBSOForMakeOrder: Integer;
        vMyCustomerBlocked: Integer;

        vMySOForReleased: Integer;
        vMySOTReleasedToday: Integer;
        vMySOTOTPBooking: Integer;

        vMySOSAT: Integer;
        vMySOIdmr: Integer;
        vMySORental: Integer;
        vMySOSC: Integer;

        vMyDOToday: Integer;
        vMySalesInvoiceToday: Integer;
        vMySalesCMToday: Integer;


        vMyBSOForMakeOrderNo: Text;
        vMyUserLocationBranch: Text;
        vMyNoSalesInvoice: Text;
        vMySalesShipmentNo: Text;
        vMyPrevSalesShipmentNo: Text;

        Style: Text;
        vUserId: Text;

}