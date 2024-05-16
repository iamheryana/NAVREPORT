page 50142 "Finance Cue"
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

            cuegroup(CueAR)
            {
                Caption = 'AR / Receivables';

                field(MyDueDateAR; vMyDueDateAR)
                {
                    Caption = 'Due Date AR';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan jumlah dokumen AR (Account Receivable) yang Due Date (Tanggal Jatuh Tempo) nya sudah melewati atau sama dengan hari ini';

                    trigger OnDrillDown()
                    var
                        vCLE: Record "Cust. Ledger Entry";
                        pCLEList: Page "Customer Ledger Entries";
                    begin
                        vCLE.Reset();
                        vCLE.SetFilter("Due Date", '..' + FORMAT(TODAY));
                        vCLE.SetFilter("Remaining Amount", '<>0');

                        pCLEList.SetTableView(vCLE);
                        pCLEList.Run();
                    end;
                }

                field(MyOverdueAR; vMyOverdueAR)
                {
                    Caption = 'Due Date AR';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan jumlah dokumen AR (Account Receivable) yang Overdue lebih dari 30 hari (terhitung dari Tgl. Invoice) dan Due Date (Tanggal Jatuh Tempo) nya sudah melewati atau sama dengan hari ini';

                    trigger OnDrillDown()
                    var
                        vCLE: Record "Cust. Ledger Entry";
                        pCLEList: Page "Customer Ledger Entries";
                    begin
                        vCLE.Reset();
                        vCLE.SetFilter("Due Date", '..' + FORMAT(TODAY));
                        vCLE.SetFilter("Remaining Amount", '<>0');
                        vCLE.SetFilter("Posting Date", '..' + FORMAT(CALCDATE('-30D', TODAY)));

                        pCLEList.SetTableView(vCLE);
                        pCLEList.Run();
                    end;
                }
            }

            cuegroup(CueAP)
            {
                Caption = 'AP / Payables';

                field(MyDueDateAP; vMyDueDateAP)
                {
                    Caption = 'Due Date AP';
                    ApplicationArea = all;
                    //StyleExpr = 'Unfavorable';
                    ToolTip = 'Menyatakan jumlah dokumen AP (Account Payable) yang Due Date (Tanggal Jatuh Tempo) nya sudah melewati atau sama dengan hari ini';

                    trigger OnDrillDown()
                    var
                        vVLE: Record "Vendor Ledger Entry";
                        pVLEList: Page "Vendor Ledger Entries";
                    begin
                        vVLE.Reset();
                        vVLE.SetFilter("Due Date", '..' + FORMAT(TODAY));
                        vVLE.SetFilter("Remaining Amount", '<>0');

                        pVLEList.SetTableView(vVLE);
                        pVLEList.Run();
                    end;
                }

            }

        }
    }


    trigger OnAfterGetCurrRecord()
    var
        MyCustLedgerEntry: Record "Cust. Ledger Entry";
        MyVendorLedgerEntry: Record "Vendor Ledger Entry";

    begin

        // AR / Receivables - [Due Date AR | Overdue 30 hari] ============================================================= START
        // AR / Receivables - [Due Date AR]
        MyCustLedgerEntry.Reset();
        MyCustLedgerEntry.SetFilter("Due Date", '..' + FORMAT(TODAY));
        MyCustLedgerEntry.SetFilter("Remaining Amount", '<>0');
        vMyDueDateAR := MyCustLedgerEntry.count;


        // AR / Receivables - [Overdue 30 hari]
        MyCustLedgerEntry.Reset();
        MyCustLedgerEntry.SetFilter("Due Date", '..' + FORMAT(TODAY));
        MyCustLedgerEntry.SetFilter("Remaining Amount", '<>0');
        MyCustLedgerEntry.SetFilter("Posting Date", '..' + FORMAT(CALCDATE('-30D', TODAY)));
        vMyOverdueAR := MyCustLedgerEntry.count;
        // AR / Receivables - [Due Date AR | Overdue 30 hari] ============================================================= END

        // AP / Payables - [Due Date AP] ================================================================================== START
        MyVendorLedgerEntry.Reset();
        MyVendorLedgerEntry.SetFilter("Due Date", '..' + FORMAT(TODAY));
        MyVendorLedgerEntry.SetFilter("Remaining Amount", '<>0');
        vMyDueDateAP := MyVendorLedgerEntry.count;
        // AP / Payables - [Due Date AP] ================================================================================== END


    end;

    var
        vMyDueDateAR: Integer;
        vMyOverdueAR: Integer;

        vMyDueDateAP: Integer;


        Style: Text;
        vUserId: Text;

}