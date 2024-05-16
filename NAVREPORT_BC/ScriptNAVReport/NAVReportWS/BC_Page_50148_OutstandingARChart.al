page 50248 "Outstanding AR Chart"
{
    PageType = CardPart;
    Caption = 'Outstanding AR';
    UsageCategory = Administration;
    ApplicationArea = all;

    layout
    {

        area(Content)
        {
            label(xxx)
            {
                Caption = 'XXXXXXXX';
            }


            usercontrol(chart; "Microsoft.Dynamics.Nav.Client.BusinessChart")
            {
                ApplicationArea = all;

                trigger DataPointClicked(point: JsonObject)
                var
                    JsonTxt: Text;
                    JsonTokenXValueString: JsonToken;
                    JsonTokenXCValueString: JsonArray;
                    XValueString: Text;
                    MyParamIndex: Integer;

                    MyVarDateFrom: Date;
                    MyVarDateUpto: Date;

                    CustLedgerEntry: Record "Cust. Ledger Entry";
                    CustLedgerP: Page "Customer Ledger Entries";

                begin
                    MyParamIndex := -1;

                    if (point.Get('XValueString', JsonTokenXValueString)) then begin
                        XValueString := Format(JsonTokenXValueString);
                        Evaluate(MyParamIndex, (CopyStr(XValueString, 3, 1)));
                        // Message(XValueString);
                        // Message(format(MyParamIndex));
                        // Message(format(MyVarDateStart));
                    end;

                    MyParamIndex := MyParamIndex - 1;

                    CustLedgerEntry.Reset();

                    CustLedgerEntry.SetFilter("Remaining Amount", '<>0');

                    if myChartBy = 1 then begin
                        if MyParamIndex = 0 then begin
                            MyVarDateFrom := MyVarDateStart;
                            MyVarDateUpto := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) - 1) + 'D'), MyVarDateStart);

                            CustLedgerEntry.SetFilter("Due Date", '..' + FORMAT(MyVarDateUpto));
                        end;

                        if MyParamIndex = 1 then begin
                            MyVarDateFrom := MyVarDateStart;
                            MyVarDateFrom := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) - 0) + 'D'), MyVarDateStart);
                            MyVarDateUpto := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + (7 - 1)) + 'D'), MyVarDateStart);

                            CustLedgerEntry.SetRange("Due Date", MyVarDateFrom, MyVarDateUpto);
                        end;

                        if MyParamIndex = 2 then begin
                            MyVarDateFrom := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + 7) + 'D'), MyVarDateStart);
                            MyVarDateUpto := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + (14 - 1)) + 'D'), MyVarDateStart);

                            CustLedgerEntry.SetRange("Due Date", MyVarDateFrom, MyVarDateUpto);
                        end;

                        if MyParamIndex = 3 then begin
                            MyVarDateFrom := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + 14) + 'D'), MyVarDateStart);
                            MyVarDateUpto := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + (21 - 1)) + 'D'), MyVarDateStart);

                            CustLedgerEntry.SetRange("Due Date", MyVarDateFrom, MyVarDateUpto);
                        end;

                        if MyParamIndex = 4 then begin
                            MyVarDateFrom := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + 21) + 'D'), MyVarDateStart);
                            MyVarDateUpto := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + (35 - 1)) + 'D'), MyVarDateStart);

                            CustLedgerEntry.SetRange("Due Date", MyVarDateFrom, MyVarDateUpto);
                        end;

                        if MyParamIndex = 5 then begin
                            MyVarDateFrom := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + 35) + 'D'), MyVarDateStart);
                            MyVarDateUpto := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + (49 - 1)) + 'D'), MyVarDateStart);

                            CustLedgerEntry.SetRange("Due Date", MyVarDateFrom, MyVarDateUpto);
                        end;

                        if MyParamIndex = 6 then begin
                            MyVarDateFrom := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + 49) + 'D'), MyVarDateStart);
                            MyVarDateUpto := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + (63 - 1)) + 'D'), MyVarDateStart);

                            CustLedgerEntry.SetRange("Due Date", MyVarDateFrom, MyVarDateUpto);
                        end;

                        if MyParamIndex = 7 then begin
                            MyVarDateFrom := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + 63) + 'D'), MyVarDateStart);
                            MyVarDateUpto := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + (77 - 1)) + 'D'), MyVarDateStart);

                            CustLedgerEntry.SetRange("Due Date", MyVarDateFrom, MyVarDateUpto);
                        end;

                        if MyParamIndex = 8 then begin
                            MyVarDateFrom := CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + 77) + 'D'), MyVarDateStart);
                            MyVarDateUpto := CALCDATE('1M', CALCDATE((FORMAT((14 - (DATE2DWY(MyVarDateStart, 1) + 6)) + 77) + 'D'), MyVarDateStart));

                            CustLedgerEntry.SetRange("Due Date", MyVarDateFrom, MyVarDateUpto);
                        end;
                    end;

                    if myChartBy = 2 then begin
                        MyVarDateFrom := CALCDATE('<CM>', MyVarDateStart);
                        MyVarDateFrom := CALCDATE('1D', MyVarDateFrom);
                        MyVarDateFrom := CALCDATE('-1M', MyVarDateFrom);

                        MyVarDateUpto := CALCDATE('<CM>', MyVarDateFrom);

                        IF MyParamIndex = 0 THEN
                            CustLedgerEntry.SetFilter("Due Date", '..' + FORMAT(MyVarDateUpto))
                        ELSE
                            CustLedgerEntry.SetRange("Due Date", CALCDATE(FORMAT(MyParamIndex) + 'M', MyVarDateFrom), CALCDATE('<CM>', CALCDATE(FORMAT(MyParamIndex) + 'M', MyVarDateFrom)));

                    end;

                    if myChartBy = 3 then begin
                        MyVarDateFrom := MyVarDateStart;
                        MyVarDateUpto := CALCDATE('6D', MyVarDateFrom);

                        if MyParamIndex = 0 then
                            CustLedgerEntry.SetFilter("Due Date", '..' + FORMAT(MyVarDateUpto))
                        else
                            CustLedgerEntry.SetRange("Due Date", CALCDATE(FORMAT(MyParamIndex * 7) + 'D', MyVarDateFrom), CALCDATE(FORMAT(MyParamIndex * 7) + 'D', MyVarDateUpto));

                    end;

                    if myChartBy = 4 then begin
                        if MyParamIndex = 0 then
                            CustLedgerEntry.SetFilter("Due Date", '..' + FORMAT(MyVarDateStart))
                        else
                            CustLedgerEntry.SetFilter("Due Date", FORMAT(CALCDATE(FORMAT(MyParamIndex) + 'D', MyVarDateStart)));
                    end;

                    CustLedgerP.SetTableView(CustLedgerEntry);
                    CustLedgerP.Run();
                end;


                trigger AddInReady()
                var

                begin
                    CurrPage.Caption := 'Outstanding AR - Weekly And Biweekly (Cash Flow Format)';
                    CurrPage.Update();

                    myChartBy := 1;

                    MyVarOutAmout1 := 0;
                    MyVarOutAmout2 := 0;
                    MyVarOutAmout3 := 0;
                    MyVarOutAmout4 := 0;
                    MyVarOutAmout5 := 0;
                    MyVarOutAmout6 := 0;
                    MyVarOutAmout7 := 0;
                    MyVarOutAmout8 := 0;
                    MyVarOutAmout9 := 0;

                    if myChartBy = 1 then begin
                        MyVarDateStart := Today;

                        Buffer.Initialize();

                        MyVarDay := 14 - (DATE2DWY(MyVarDateStart, 1) + 6);


                        MyVarMonth1 := MyVarDateStart;
                        MyVarMonth2 := CALCDATE((FORMAT(MyVarDay) + 'D'), MyVarDateStart);
                        MyVarMonth3 := CALCDATE((FORMAT(MyVarDay + 7) + 'D'), MyVarDateStart);
                        MyVarMonth4 := CALCDATE((FORMAT(MyVarDay + 14) + 'D'), MyVarDateStart);
                        MyVarMonth5 := CALCDATE((FORMAT(MyVarDay + 21) + 'D'), MyVarDateStart);

                        MyVarMonth6 := CALCDATE((FORMAT(MyVarDay + 35) + 'D'), MyVarDateStart);
                        MyVarMonth7 := CALCDATE((FORMAT(MyVarDay + 49) + 'D'), MyVarDateStart);
                        MyVarMonth8 := CALCDATE((FORMAT(MyVarDay + 63) + 'D'), MyVarDateStart);
                        MyVarMonth9 := CALCDATE((FORMAT(MyVarDay + 77) + 'D'), MyVarDateStart);


                        MyVarLabel1 := '(1) ' + FORMAT(MyVarMonth1) + ' upto ' + FORMAT(CALCDATE((FORMAT(MyVarDay - 1) + 'D'), MyVarMonth1));
                        MyVarLabel2 := '(2) ' + FORMAT(MyVarMonth2) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth2));
                        MyVarLabel3 := '(3) ' + FORMAT(MyVarMonth3) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth3));
                        MyVarLabel4 := '(4) ' + FORMAT(MyVarMonth4) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth4));

                        MyVarLabel5 := '(5) ' + FORMAT(MyVarMonth5) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth5));
                        MyVarLabel6 := '(6) ' + FORMAT(MyVarMonth6) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth6));
                        MyVarLabel7 := '(7) ' + FORMAT(MyVarMonth7) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth7));
                        MyVarLabel8 := '(8) ' + FORMAT(MyVarMonth8) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth8));
                        MyVarLabel9 := '(9) ' + FORMAT(MyVarMonth9) + ' upto ' + FORMAT(CALCDATE('1M', MyVarMonth9));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '<=' + FORMAT(CALCDATE('1M', MyVarMonth9)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin

                            IF CustLedgerEntry.Due_Date <= CALCDATE((FORMAT(MyVarDay - 1) + 'D'), MyVarMonth1) THEN
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth2)) THEN
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth3)) THEN
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth4)) THEN
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth5) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth5)) THEN
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth6) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth6)) THEN
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth7) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth7)) THEN
                                MyVarOutAmout7 := MyVarOutAmout7 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth8) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth8)) THEN
                                MyVarOutAmout8 := MyVarOutAmout8 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth9) AND (CustLedgerEntry.Due_Date <= CALCDATE('1M', MyVarMonth9)) THEN
                                MyVarOutAmout9 := MyVarOutAmout9 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        //   Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.AddColumn(MyVarLabel7);
                        Buffer.SetValueByIndex(0, 6, MyVarOutAmout7);

                        Buffer.AddColumn(MyVarLabel8);
                        Buffer.SetValueByIndex(0, 7, MyVarOutAmout8);

                        Buffer.AddColumn(MyVarLabel9);
                        Buffer.SetValueByIndex(0, 8, MyVarOutAmout9);

                        Buffer.Update(CurrPage.Chart);

                    end;

                    if myChartBy = 2 then begin
                        MyVarDateStart := Today;

                        Buffer.Initialize();
                        MyVarDateStart := CALCDATE('<CM>', MyVarDateStart);

                        MyVarMonth := DATE2DMY(MyVarDateStart, 2);
                        MyVarYear := DATE2DMY(MyVarDateStart, 3);

                        MyVarMonth1 := CALCDATE('1D', MyVarDateStart);
                        MyVarMonth1 := CALCDATE('-1M', MyVarMonth1);

                        MyVarMonth2 := CALCDATE('1M', MyVarMonth1);
                        MyVarMonth3 := CALCDATE('2M', MyVarMonth1);
                        MyVarMonth4 := CALCDATE('3M', MyVarMonth1);
                        MyVarMonth5 := CALCDATE('4M', MyVarMonth1);
                        MyVarMonth6 := CALCDATE('5M', MyVarMonth1);


                        IF MyVarMonth = 1 THEN BEGIN
                            MyVarLabel1 := '(1) Jan-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Feb-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) May-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jun-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 2 THEN BEGIN
                            MyVarLabel1 := '(1) Feb-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) May-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jul-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 3 THEN BEGIN
                            MyVarLabel1 := '(1) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) May-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Aug-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 4 THEN BEGIN
                            MyVarLabel1 := '(1) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) May-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Sep-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 5 THEN BEGIN
                            MyVarLabel1 := '(1) May-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Oct-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 6 THEN BEGIN
                            MyVarLabel1 := '(1) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Nov-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 7 THEN BEGIN
                            MyVarLabel1 := '(1) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Dec-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 8 THEN BEGIN
                            MyVarLabel1 := '(1) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jan-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 9 THEN BEGIN
                            MyVarLabel1 := '(1) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Feb-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 10 THEN BEGIN
                            MyVarLabel1 := '(1) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Mar-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 11 THEN BEGIN
                            MyVarLabel1 := '(1) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel4 := '(4) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Mar-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Apr-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 12 THEN BEGIN
                            MyVarLabel1 := '(1) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel3 := '(3) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel4 := '(4) Mar-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Apr-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) May-' + FORMAT(MyVarYear + 1);
                        END;

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '<=' + FORMAT(CALCDATE('<CM>', MyVarMonth6)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin

                            if CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth1) then
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth2)) then
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth3)) then
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth4)) then
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth5) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth5)) then
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth6) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth6)) then
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        //      Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.Update(CurrPage.Chart);
                    end;

                    if myChartBy = 3 then begin
                        MyVarDateStart := Today;

                        Buffer.Initialize();

                        MyVarMonth1 := MyVarDateStart;
                        MyVarMonth2 := CALCDATE('7D', MyVarDateStart);
                        MyVarMonth3 := CALCDATE('14D', MyVarDateStart);
                        MyVarMonth4 := CALCDATE('21D', MyVarDateStart);
                        //MyVarMonth5 := CALCDATE('28D',MyVarDateStart);
                        //MyVarMonth6 := CALCDATE('35D',MyVarDateStart);

                        MyVarLabel1 := '(1) ' + FORMAT(MyVarMonth1) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth1));
                        MyVarLabel2 := '(2) ' + FORMAT(MyVarMonth2) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth2));
                        MyVarLabel3 := '(3) ' + FORMAT(MyVarMonth3) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth3));
                        MyVarLabel4 := '(4) ' + FORMAT(MyVarMonth4) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth4));
                        //MyVarLabel5 := '(5) ' + FORMAT(MyVarMonth5) + ' upto '+ FORMAT(CALCDATE ('6D', MyVarMonth5));
                        //MyVarLabel6 := '(6) ' + FORMAT(MyVarMonth6) + ' upto '+ FORMAT(CALCDATE ('6D', MyVarMonth6));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '..' + FORMAT(CALCDATE('6D', MyVarMonth4)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin
                            if CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth1) then
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth2)) THEN
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth3)) THEN
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth4)) THEN
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        //  Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.Update(CurrPage.Chart);

                        //   Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);


                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        //Buffer.AddColumn(MyVarLabel5);
                        //Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        //Buffer.AddColumn(MyVarLabel6);
                        //Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.Update(CurrPage.Chart);
                    end;

                    if myChartBy = 4 then begin
                        MyVarDateStart := Today;

                        Buffer.Initialize();

                        MyVarLabel1 := '(1) ' + FORMAT(MyVarDateStart);
                        MyVarLabel2 := '(2) ' + FORMAT(CALCDATE('1D', MyVarDateStart));
                        MyVarLabel3 := '(3) ' + FORMAT(CALCDATE('2D', MyVarDateStart));
                        MyVarLabel4 := '(4) ' + FORMAT(CALCDATE('3D', MyVarDateStart));
                        MyVarLabel5 := '(5) ' + FORMAT(CALCDATE('4D', MyVarDateStart));
                        MyVarLabel6 := '(6) ' + FORMAT(CALCDATE('5D', MyVarDateStart));
                        MyVarLabel7 := '(7) ' + FORMAT(CALCDATE('6D', MyVarDateStart));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '..' + FORMAT(CALCDATE('6D', MyVarDateStart)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin
                            if CustLedgerEntry.Due_Date <= MyVarDateStart then
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('1D', MyVarDateStart)) then
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('2D', MyVarDateStart)) then
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('3D', MyVarDateStart)) then
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('4D', MyVarDateStart)) then
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('5D', MyVarDateStart)) then
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('6D', MyVarDateStart)) then
                                MyVarOutAmout7 := MyVarOutAmout7 + CustLedgerEntry.Remaining_Amount;
                        end;
                        CustLedgerEntry.Close();

                        //   Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.AddColumn(MyVarLabel7);
                        Buffer.SetValueByIndex(0, 6, MyVarOutAmout7);

                        Buffer.Update(CurrPage.Chart);
                    end;


                end;
            }
        }
    }


    actions
    {
        area(Processing)
        {


            group("Show Period by")
            {
                action("Weekly And Biweekly (Cash Flow Format)")
                {
                    trigger OnAction()
                    begin
                        CurrPage.Caption := 'Outstanding AR - Weekly And Biweekly';
                        myChartBy := 1;
                        MyVarDateStart := Today;

                        MyVarOutAmout1 := 0;
                        MyVarOutAmout2 := 0;
                        MyVarOutAmout3 := 0;
                        MyVarOutAmout4 := 0;
                        MyVarOutAmout5 := 0;
                        MyVarOutAmout6 := 0;
                        MyVarOutAmout7 := 0;
                        MyVarOutAmout8 := 0;
                        MyVarOutAmout9 := 0;

                        Buffer.Initialize();

                        MyVarDay := 14 - (DATE2DWY(MyVarDateStart, 1) + 6);


                        MyVarMonth1 := MyVarDateStart;
                        MyVarMonth2 := CALCDATE((FORMAT(MyVarDay) + 'D'), MyVarDateStart);
                        MyVarMonth3 := CALCDATE((FORMAT(MyVarDay + 7) + 'D'), MyVarDateStart);
                        MyVarMonth4 := CALCDATE((FORMAT(MyVarDay + 14) + 'D'), MyVarDateStart);
                        MyVarMonth5 := CALCDATE((FORMAT(MyVarDay + 21) + 'D'), MyVarDateStart);

                        MyVarMonth6 := CALCDATE((FORMAT(MyVarDay + 35) + 'D'), MyVarDateStart);
                        MyVarMonth7 := CALCDATE((FORMAT(MyVarDay + 49) + 'D'), MyVarDateStart);
                        MyVarMonth8 := CALCDATE((FORMAT(MyVarDay + 63) + 'D'), MyVarDateStart);
                        MyVarMonth9 := CALCDATE((FORMAT(MyVarDay + 77) + 'D'), MyVarDateStart);


                        MyVarLabel1 := '(1) ' + FORMAT(MyVarMonth1) + ' upto ' + FORMAT(CALCDATE((FORMAT(MyVarDay - 1) + 'D'), MyVarMonth1));
                        MyVarLabel2 := '(2) ' + FORMAT(MyVarMonth2) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth2));
                        MyVarLabel3 := '(3) ' + FORMAT(MyVarMonth3) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth3));
                        MyVarLabel4 := '(4) ' + FORMAT(MyVarMonth4) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth4));

                        MyVarLabel5 := '(5) ' + FORMAT(MyVarMonth5) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth5));
                        MyVarLabel6 := '(6) ' + FORMAT(MyVarMonth6) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth6));
                        MyVarLabel7 := '(7) ' + FORMAT(MyVarMonth7) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth7));
                        MyVarLabel8 := '(8) ' + FORMAT(MyVarMonth8) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth8));
                        MyVarLabel9 := '(9) ' + FORMAT(MyVarMonth9) + ' upto ' + FORMAT(CALCDATE('1M', MyVarMonth9));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '<=' + FORMAT(CALCDATE('1M', MyVarMonth9)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin

                            IF CustLedgerEntry.Due_Date <= CALCDATE((FORMAT(MyVarDay - 1) + 'D'), MyVarMonth1) THEN
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth2)) THEN
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth3)) THEN
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth4)) THEN
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth5) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth5)) THEN
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth6) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth6)) THEN
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth7) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth7)) THEN
                                MyVarOutAmout7 := MyVarOutAmout7 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth8) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth8)) THEN
                                MyVarOutAmout8 := MyVarOutAmout8 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth9) AND (CustLedgerEntry.Due_Date <= CALCDATE('1M', MyVarMonth9)) THEN
                                MyVarOutAmout9 := MyVarOutAmout9 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        //    Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);


                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.AddColumn(MyVarLabel7);
                        Buffer.SetValueByIndex(0, 6, MyVarOutAmout7);

                        Buffer.AddColumn(MyVarLabel8);
                        Buffer.SetValueByIndex(0, 7, MyVarOutAmout8);

                        Buffer.AddColumn(MyVarLabel9);
                        Buffer.SetValueByIndex(0, 8, MyVarOutAmout9);

                        Buffer.Update(CurrPage.Chart);
                    end;
                }
                action("Monthly")
                {
                    trigger OnAction()
                    begin
                        CurrPage.Caption := 'Outstanding AR - Monthly';
                        myChartBy := 2;
                        MyVarDateStart := Today;

                        MyVarOutAmout1 := 0;
                        MyVarOutAmout2 := 0;
                        MyVarOutAmout3 := 0;
                        MyVarOutAmout4 := 0;
                        MyVarOutAmout5 := 0;
                        MyVarOutAmout6 := 0;

                        Buffer.Initialize();

                        MyVarDateStart := CALCDATE('<CM>', MyVarDateStart);

                        MyVarMonth := DATE2DMY(MyVarDateStart, 2);
                        MyVarYear := DATE2DMY(MyVarDateStart, 3);

                        MyVarMonth1 := CALCDATE('1D', MyVarDateStart);
                        MyVarMonth1 := CALCDATE('-1M', MyVarMonth1);

                        MyVarMonth2 := CALCDATE('1M', MyVarMonth1);
                        MyVarMonth3 := CALCDATE('2M', MyVarMonth1);
                        MyVarMonth4 := CALCDATE('3M', MyVarMonth1);
                        MyVarMonth5 := CALCDATE('4M', MyVarMonth1);
                        MyVarMonth6 := CALCDATE('5M', MyVarMonth1);


                        IF MyVarMonth = 1 THEN BEGIN
                            MyVarLabel1 := '(1) Jan-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Feb-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) May-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jun-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 2 THEN BEGIN
                            MyVarLabel1 := '(1) Feb-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) May-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jul-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 3 THEN BEGIN
                            MyVarLabel1 := '(1) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) May-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Aug-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 4 THEN BEGIN
                            MyVarLabel1 := '(1) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) May-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Sep-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 5 THEN BEGIN
                            MyVarLabel1 := '(1) May-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Oct-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 6 THEN BEGIN
                            MyVarLabel1 := '(1) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Nov-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 7 THEN BEGIN
                            MyVarLabel1 := '(1) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Dec-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 8 THEN BEGIN
                            MyVarLabel1 := '(1) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jan-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 9 THEN BEGIN
                            MyVarLabel1 := '(1) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Feb-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 10 THEN BEGIN
                            MyVarLabel1 := '(1) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Mar-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 11 THEN BEGIN
                            MyVarLabel1 := '(1) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel4 := '(4) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Mar-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Apr-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 12 THEN BEGIN
                            MyVarLabel1 := '(1) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel3 := '(3) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel4 := '(4) Mar-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Apr-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) May-' + FORMAT(MyVarYear + 1);
                        END;

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '<=' + FORMAT(CALCDATE('<CM>', MyVarMonth6)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin

                            if CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth1) then
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth2)) then
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth3)) then
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth4)) then
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth5) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth5)) then
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth6) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth6)) then
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        //    Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);


                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.Update(CurrPage.Chart);
                    end;
                }
                action("Weekly")
                {
                    trigger OnAction()
                    begin
                        CurrPage.Caption := 'Outstanding AR - Weekly';
                        myChartBy := 3;
                        MyVarDateStart := Today;

                        MyVarOutAmout1 := 0;
                        MyVarOutAmout2 := 0;
                        MyVarOutAmout3 := 0;
                        MyVarOutAmout4 := 0;

                        Buffer.Initialize();

                        MyVarMonth1 := MyVarDateStart;
                        MyVarMonth2 := CALCDATE('7D', MyVarDateStart);
                        MyVarMonth3 := CALCDATE('14D', MyVarDateStart);
                        MyVarMonth4 := CALCDATE('21D', MyVarDateStart);
                        //MyVarMonth5 := CALCDATE('28D',MyVarDateStart);
                        //MyVarMonth6 := CALCDATE('35D',MyVarDateStart);

                        MyVarLabel1 := '(1) ' + FORMAT(MyVarMonth1) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth1));
                        MyVarLabel2 := '(2) ' + FORMAT(MyVarMonth2) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth2));
                        MyVarLabel3 := '(3) ' + FORMAT(MyVarMonth3) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth3));
                        MyVarLabel4 := '(4) ' + FORMAT(MyVarMonth4) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth4));
                        //MyVarLabel5 := '(5) ' + FORMAT(MyVarMonth5) + ' upto '+ FORMAT(CALCDATE ('6D', MyVarMonth5));
                        //MyVarLabel6 := '(6) ' + FORMAT(MyVarMonth6) + ' upto '+ FORMAT(CALCDATE ('6D', MyVarMonth6));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '..' + FORMAT(CALCDATE('6D', MyVarMonth4)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin
                            if CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth1) THEN
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth2)) THEN
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth3)) THEN
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth4)) THEN
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        //     Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        //Buffer.AddColumn(MyVarLabel5);
                        //Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        //Buffer.AddColumn(MyVarLabel6);
                        //Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.Update(CurrPage.Chart);
                    end;
                }
                action("Daily")
                {
                    trigger OnAction()
                    begin
                        CurrPage.Caption := 'Outstanding AR - Daily';
                        myChartBy := 4;
                        MyVarDateStart := Today;

                        MyVarOutAmout1 := 0;
                        MyVarOutAmout2 := 0;
                        MyVarOutAmout3 := 0;
                        MyVarOutAmout4 := 0;
                        MyVarOutAmout5 := 0;
                        MyVarOutAmout6 := 0;
                        MyVarOutAmout7 := 0;

                        Buffer.Initialize();

                        MyVarLabel1 := '(1) ' + FORMAT(MyVarDateStart);
                        MyVarLabel2 := '(2) ' + FORMAT(CALCDATE('1D', MyVarDateStart));
                        MyVarLabel3 := '(3) ' + FORMAT(CALCDATE('2D', MyVarDateStart));
                        MyVarLabel4 := '(4) ' + FORMAT(CALCDATE('3D', MyVarDateStart));
                        MyVarLabel5 := '(5) ' + FORMAT(CALCDATE('4D', MyVarDateStart));
                        MyVarLabel6 := '(6) ' + FORMAT(CALCDATE('5D', MyVarDateStart));
                        MyVarLabel7 := '(7) ' + FORMAT(CALCDATE('6D', MyVarDateStart));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '..' + FORMAT(CALCDATE('6D', MyVarDateStart)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin
                            if CustLedgerEntry.Due_Date <= MyVarDateStart then
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('1D', MyVarDateStart)) then
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('2D', MyVarDateStart)) then
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('3D', MyVarDateStart)) then
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('4D', MyVarDateStart)) then
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('5D', MyVarDateStart)) then
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('6D', MyVarDateStart)) then
                                MyVarOutAmout7 := MyVarOutAmout7 + CustLedgerEntry.Remaining_Amount;
                        end;
                        CustLedgerEntry.Close();

                        //      Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.AddColumn(MyVarLabel7);
                        Buffer.SetValueByIndex(0, 6, MyVarOutAmout7);

                        Buffer.Update(CurrPage.Chart);
                    end;
                }

            }

            action("Previous Period")
            {
                ApplicationArea = All;

                trigger OnAction()
                begin

                    MyVarOutAmout1 := 0;
                    MyVarOutAmout2 := 0;
                    MyVarOutAmout3 := 0;
                    MyVarOutAmout4 := 0;
                    MyVarOutAmout5 := 0;
                    MyVarOutAmout6 := 0;
                    MyVarOutAmout7 := 0;
                    MyVarOutAmout8 := 0;
                    MyVarOutAmout9 := 0;

                    if myChartBy = 1 then begin
                        MyVarDateStart := CALCDATE('-7D', MyVarDateStart);

                        Buffer.Initialize();

                        MyVarDay := 14 - (DATE2DWY(MyVarDateStart, 1) + 6);

                        MyVarMonth1 := MyVarDateStart;
                        MyVarMonth2 := CALCDATE((FORMAT(MyVarDay) + 'D'), MyVarDateStart);
                        MyVarMonth3 := CALCDATE((FORMAT(MyVarDay + 7) + 'D'), MyVarDateStart);
                        MyVarMonth4 := CALCDATE((FORMAT(MyVarDay + 14) + 'D'), MyVarDateStart);
                        MyVarMonth5 := CALCDATE((FORMAT(MyVarDay + 21) + 'D'), MyVarDateStart);

                        MyVarMonth6 := CALCDATE((FORMAT(MyVarDay + 35) + 'D'), MyVarDateStart);
                        MyVarMonth7 := CALCDATE((FORMAT(MyVarDay + 49) + 'D'), MyVarDateStart);
                        MyVarMonth8 := CALCDATE((FORMAT(MyVarDay + 63) + 'D'), MyVarDateStart);
                        MyVarMonth9 := CALCDATE((FORMAT(MyVarDay + 77) + 'D'), MyVarDateStart);


                        MyVarLabel1 := '(1) ' + FORMAT(MyVarMonth1) + ' upto ' + FORMAT(CALCDATE((FORMAT(MyVarDay - 1) + 'D'), MyVarMonth1));
                        MyVarLabel2 := '(2) ' + FORMAT(MyVarMonth2) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth2));
                        MyVarLabel3 := '(3) ' + FORMAT(MyVarMonth3) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth3));
                        MyVarLabel4 := '(4) ' + FORMAT(MyVarMonth4) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth4));

                        MyVarLabel5 := '(5) ' + FORMAT(MyVarMonth5) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth5));
                        MyVarLabel6 := '(6) ' + FORMAT(MyVarMonth6) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth6));
                        MyVarLabel7 := '(7) ' + FORMAT(MyVarMonth7) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth7));
                        MyVarLabel8 := '(8) ' + FORMAT(MyVarMonth8) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth8));
                        MyVarLabel9 := '(9) ' + FORMAT(MyVarMonth9) + ' upto ' + FORMAT(CALCDATE('1M', MyVarMonth9));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '<=' + FORMAT(CALCDATE('1M', MyVarMonth9)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin

                            IF CustLedgerEntry.Due_Date <= CALCDATE((FORMAT(MyVarDay - 1) + 'D'), MyVarMonth1) THEN
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth2)) THEN
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth3)) THEN
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth4)) THEN
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth5) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth5)) THEN
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth6) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth6)) THEN
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth7) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth7)) THEN
                                MyVarOutAmout7 := MyVarOutAmout7 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth8) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth8)) THEN
                                MyVarOutAmout8 := MyVarOutAmout8 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth9) AND (CustLedgerEntry.Due_Date <= CALCDATE('1M', MyVarMonth9)) THEN
                                MyVarOutAmout9 := MyVarOutAmout9 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        // Index 0
                        //     Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);


                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.AddColumn(MyVarLabel7);
                        Buffer.SetValueByIndex(0, 6, MyVarOutAmout7);

                        Buffer.AddColumn(MyVarLabel8);
                        Buffer.SetValueByIndex(0, 7, MyVarOutAmout8);

                        Buffer.AddColumn(MyVarLabel9);
                        Buffer.SetValueByIndex(0, 8, MyVarOutAmout9);

                        Buffer.Update(CurrPage.Chart);

                    end;

                    if myChartBy = 2 then begin
                        MyVarDateStart := CALCDATE('-1M', MyVarDateStart);

                        Buffer.Initialize();
                        MyVarDateStart := CALCDATE('<CM>', MyVarDateStart);

                        MyVarMonth := DATE2DMY(MyVarDateStart, 2);
                        MyVarYear := DATE2DMY(MyVarDateStart, 3);

                        MyVarMonth1 := CALCDATE('1D', MyVarDateStart);
                        MyVarMonth1 := CALCDATE('-1M', MyVarMonth1);

                        MyVarMonth2 := CALCDATE('1M', MyVarMonth1);
                        MyVarMonth3 := CALCDATE('2M', MyVarMonth1);
                        MyVarMonth4 := CALCDATE('3M', MyVarMonth1);
                        MyVarMonth5 := CALCDATE('4M', MyVarMonth1);
                        MyVarMonth6 := CALCDATE('5M', MyVarMonth1);


                        IF MyVarMonth = 1 THEN BEGIN
                            MyVarLabel1 := '(1) Jan-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Feb-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) May-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jun-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 2 THEN BEGIN
                            MyVarLabel1 := '(1) Feb-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) May-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jul-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 3 THEN BEGIN
                            MyVarLabel1 := '(1) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) May-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Aug-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 4 THEN BEGIN
                            MyVarLabel1 := '(1) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) May-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Sep-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 5 THEN BEGIN
                            MyVarLabel1 := '(1) May-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Oct-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 6 THEN BEGIN
                            MyVarLabel1 := '(1) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Nov-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 7 THEN BEGIN
                            MyVarLabel1 := '(1) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Dec-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 8 THEN BEGIN
                            MyVarLabel1 := '(1) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jan-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 9 THEN BEGIN
                            MyVarLabel1 := '(1) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Feb-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 10 THEN BEGIN
                            MyVarLabel1 := '(1) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Mar-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 11 THEN BEGIN
                            MyVarLabel1 := '(1) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel4 := '(4) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Mar-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Apr-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 12 THEN BEGIN
                            MyVarLabel1 := '(1) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel3 := '(3) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel4 := '(4) Mar-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Apr-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) May-' + FORMAT(MyVarYear + 1);
                        END;

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '<=' + FORMAT(CALCDATE('<CM>', MyVarMonth6)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin

                            if CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth1) then
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth2)) then
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth3)) then
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth4)) then
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth5) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth5)) then
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth6) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth6)) then
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        //      Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        //Message('1=' + Format(MyVarOutAmout1) + ' - ' + '2=' + Format(MyVarOutAmout2) + ' - ' + '3=' + Format(MyVarOutAmout3) + ' - ' + '4=' + Format(MyVarOutAmout4) + ' - ' + '5=' + Format(MyVarOutAmout5) + ' - ' + '6=' + Format(MyVarOutAmout6));

                        Buffer.Update(CurrPage.Chart);
                    end;

                    if myChartBy = 3 then begin
                        MyVarDateStart := CALCDATE('-7D', MyVarDateStart);

                        Buffer.Initialize();

                        MyVarMonth1 := MyVarDateStart;
                        MyVarMonth2 := CALCDATE('7D', MyVarDateStart);
                        MyVarMonth3 := CALCDATE('14D', MyVarDateStart);
                        MyVarMonth4 := CALCDATE('21D', MyVarDateStart);
                        //MyVarMonth5 := CALCDATE('28D',MyVarDateStart);
                        //MyVarMonth6 := CALCDATE('35D',MyVarDateStart);

                        MyVarLabel1 := '(1) ' + FORMAT(MyVarMonth1) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth1));
                        MyVarLabel2 := '(2) ' + FORMAT(MyVarMonth2) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth2));
                        MyVarLabel3 := '(3) ' + FORMAT(MyVarMonth3) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth3));
                        MyVarLabel4 := '(4) ' + FORMAT(MyVarMonth4) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth4));
                        //MyVarLabel5 := '(5) ' + FORMAT(MyVarMonth5) + ' upto '+ FORMAT(CALCDATE ('6D', MyVarMonth5));
                        //MyVarLabel6 := '(6) ' + FORMAT(MyVarMonth6) + ' upto '+ FORMAT(CALCDATE ('6D', MyVarMonth6));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '..' + FORMAT(CALCDATE('6D', MyVarMonth4)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin
                            if CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth1) THEN
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth2)) THEN
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth3)) THEN
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth4)) THEN
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        //       Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        //Buffer.AddColumn(MyVarLabel5);
                        //Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        //Buffer.AddColumn(MyVarLabel6);
                        //Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.Update(CurrPage.Chart);
                    end;

                    if myChartBy = 4 then begin
                        MyVarDateStart := CALCDATE('-1D', MyVarDateStart);

                        Buffer.Initialize();

                        MyVarLabel1 := '(1) ' + FORMAT(MyVarDateStart);
                        MyVarLabel2 := '(2) ' + FORMAT(CALCDATE('1D', MyVarDateStart));
                        MyVarLabel3 := '(3) ' + FORMAT(CALCDATE('2D', MyVarDateStart));
                        MyVarLabel4 := '(4) ' + FORMAT(CALCDATE('3D', MyVarDateStart));
                        MyVarLabel5 := '(5) ' + FORMAT(CALCDATE('4D', MyVarDateStart));
                        MyVarLabel6 := '(6) ' + FORMAT(CALCDATE('5D', MyVarDateStart));
                        MyVarLabel7 := '(7) ' + FORMAT(CALCDATE('6D', MyVarDateStart));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '..' + FORMAT(CALCDATE('6D', MyVarDateStart)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin
                            if CustLedgerEntry.Due_Date <= MyVarDateStart then
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('1D', MyVarDateStart)) then
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('2D', MyVarDateStart)) then
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('3D', MyVarDateStart)) then
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('4D', MyVarDateStart)) then
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('5D', MyVarDateStart)) then
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('6D', MyVarDateStart)) then
                                MyVarOutAmout7 := MyVarOutAmout7 + CustLedgerEntry.Remaining_Amount;
                        end;
                        CustLedgerEntry.Close();

                        //       Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.AddColumn(MyVarLabel7);
                        Buffer.SetValueByIndex(0, 6, MyVarOutAmout7);

                        Buffer.Update(CurrPage.Chart);
                    end;


                end;
            }
            action("Next Period")
            {
                //ApplicationArea = All;

                trigger OnAction()
                begin

                    MyVarOutAmout1 := 0;
                    MyVarOutAmout2 := 0;
                    MyVarOutAmout3 := 0;
                    MyVarOutAmout4 := 0;
                    MyVarOutAmout5 := 0;
                    MyVarOutAmout6 := 0;
                    MyVarOutAmout7 := 0;
                    MyVarOutAmout8 := 0;
                    MyVarOutAmout9 := 0;

                    if myChartBy = 1 then begin
                        MyVarDateStart := CALCDATE('7D', MyVarDateStart);

                        Buffer.Initialize();

                        MyVarDay := 14 - (DATE2DWY(MyVarDateStart, 1) + 6);

                        MyVarMonth1 := MyVarDateStart;
                        MyVarMonth2 := CALCDATE((FORMAT(MyVarDay) + 'D'), MyVarDateStart);
                        MyVarMonth3 := CALCDATE((FORMAT(MyVarDay + 7) + 'D'), MyVarDateStart);
                        MyVarMonth4 := CALCDATE((FORMAT(MyVarDay + 14) + 'D'), MyVarDateStart);
                        MyVarMonth5 := CALCDATE((FORMAT(MyVarDay + 21) + 'D'), MyVarDateStart);

                        MyVarMonth6 := CALCDATE((FORMAT(MyVarDay + 35) + 'D'), MyVarDateStart);
                        MyVarMonth7 := CALCDATE((FORMAT(MyVarDay + 49) + 'D'), MyVarDateStart);
                        MyVarMonth8 := CALCDATE((FORMAT(MyVarDay + 63) + 'D'), MyVarDateStart);
                        MyVarMonth9 := CALCDATE((FORMAT(MyVarDay + 77) + 'D'), MyVarDateStart);

                        MyVarLabel1 := '(1) ' + FORMAT(MyVarMonth1) + ' upto ' + FORMAT(CALCDATE((FORMAT(MyVarDay - 1) + 'D'), MyVarMonth1));
                        MyVarLabel2 := '(2) ' + FORMAT(MyVarMonth2) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth2));
                        MyVarLabel3 := '(3) ' + FORMAT(MyVarMonth3) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth3));
                        MyVarLabel4 := '(4) ' + FORMAT(MyVarMonth4) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth4));

                        MyVarLabel5 := '(5) ' + FORMAT(MyVarMonth5) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth5));
                        MyVarLabel6 := '(6) ' + FORMAT(MyVarMonth6) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth6));
                        MyVarLabel7 := '(7) ' + FORMAT(MyVarMonth7) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth7));
                        MyVarLabel8 := '(8) ' + FORMAT(MyVarMonth8) + ' upto ' + FORMAT(CALCDATE('13D', MyVarMonth8));
                        MyVarLabel9 := '(9) ' + FORMAT(MyVarMonth9) + ' upto ' + FORMAT(CALCDATE('1M', MyVarMonth9));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '<=' + FORMAT(CALCDATE('1M', MyVarMonth9)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin

                            IF CustLedgerEntry.Due_Date <= CALCDATE((FORMAT(MyVarDay - 1) + 'D'), MyVarMonth1) THEN
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth2)) THEN
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth3)) THEN
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth4)) THEN
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth5) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth5)) THEN
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth6) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth6)) THEN
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth7) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth7)) THEN
                                MyVarOutAmout7 := MyVarOutAmout7 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth8) AND (CustLedgerEntry.Due_Date <= CALCDATE('13D', MyVarMonth8)) THEN
                                MyVarOutAmout8 := MyVarOutAmout8 + CustLedgerEntry.Remaining_Amount;

                            IF (CustLedgerEntry.Due_Date >= MyVarMonth9) AND (CustLedgerEntry.Due_Date <= CALCDATE('1M', MyVarMonth9)) THEN
                                MyVarOutAmout9 := MyVarOutAmout9 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        // Index 0
                        //         Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);


                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.AddColumn(MyVarLabel7);
                        Buffer.SetValueByIndex(0, 6, MyVarOutAmout7);

                        Buffer.AddColumn(MyVarLabel8);
                        Buffer.SetValueByIndex(0, 7, MyVarOutAmout8);

                        Buffer.AddColumn(MyVarLabel9);
                        Buffer.SetValueByIndex(0, 8, MyVarOutAmout9);

                        Buffer.Update(CurrPage.Chart);

                    end;

                    if myChartBy = 2 then begin
                        MyVarDateStart := CALCDATE('1M', MyVarDateStart);

                        Buffer.Initialize();

                        MyVarDateStart := CALCDATE('<CM>', MyVarDateStart);

                        MyVarMonth := DATE2DMY(MyVarDateStart, 2);
                        MyVarYear := DATE2DMY(MyVarDateStart, 3);

                        MyVarMonth1 := CALCDATE('1D', MyVarDateStart);
                        MyVarMonth1 := CALCDATE('-1M', MyVarMonth1);

                        MyVarMonth2 := CALCDATE('1M', MyVarMonth1);
                        MyVarMonth3 := CALCDATE('2M', MyVarMonth1);
                        MyVarMonth4 := CALCDATE('3M', MyVarMonth1);
                        MyVarMonth5 := CALCDATE('4M', MyVarMonth1);
                        MyVarMonth6 := CALCDATE('5M', MyVarMonth1);

                        IF MyVarMonth = 1 THEN BEGIN
                            MyVarLabel1 := '(1) Jan-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Feb-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) May-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jun-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 2 THEN BEGIN
                            MyVarLabel1 := '(1) Feb-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) May-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jul-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 3 THEN BEGIN
                            MyVarLabel1 := '(1) Mar-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) May-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Aug-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 4 THEN BEGIN
                            MyVarLabel1 := '(1) Apr-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) May-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Sep-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 5 THEN BEGIN
                            MyVarLabel1 := '(1) May-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Oct-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 6 THEN BEGIN
                            MyVarLabel1 := '(1) Jun-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Nov-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 7 THEN BEGIN
                            MyVarLabel1 := '(1) Jul-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Dec-' + FORMAT(MyVarYear);
                        END;

                        IF MyVarMonth = 8 THEN BEGIN
                            MyVarLabel1 := '(1) Aug-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel6 := '(6) Jan-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 9 THEN BEGIN
                            MyVarLabel1 := '(1) Sep-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel5 := '(5) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Feb-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 10 THEN BEGIN
                            MyVarLabel1 := '(1) Oct-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel4 := '(4) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Mar-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 11 THEN BEGIN
                            MyVarLabel1 := '(1) Nov-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel3 := '(3) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel4 := '(4) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Mar-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) Apr-' + FORMAT(MyVarYear + 1);
                        END;

                        IF MyVarMonth = 12 THEN BEGIN
                            MyVarLabel1 := '(1) Dec-' + FORMAT(MyVarYear);
                            MyVarLabel2 := '(2) Jan-' + FORMAT(MyVarYear + 1);
                            MyVarLabel3 := '(3) Feb-' + FORMAT(MyVarYear + 1);
                            MyVarLabel4 := '(4) Mar-' + FORMAT(MyVarYear + 1);
                            MyVarLabel5 := '(5) Apr-' + FORMAT(MyVarYear + 1);
                            MyVarLabel6 := '(6) May-' + FORMAT(MyVarYear + 1);
                        END;

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '<=' + FORMAT(CALCDATE('<CM>', MyVarMonth6)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin

                            if CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth1) then
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth2)) then
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth3)) then
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth4)) then
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth5) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth5)) then
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth6) AND (CustLedgerEntry.Due_Date <= CALCDATE('<CM>', MyVarMonth6)) then
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        //          Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        //Message('1=' + Format(MyVarOutAmout1) + ' - ' + '2=' + Format(MyVarOutAmout2) + ' - ' + '3=' + Format(MyVarOutAmout3) + ' - ' + '4=' + Format(MyVarOutAmout4) + ' - ' + '5=' + Format(MyVarOutAmout5) + ' - ' + '6=' + Format(MyVarOutAmout6));

                        Buffer.Update(CurrPage.Chart);
                    end;

                    if myChartBy = 3 then begin
                        MyVarDateStart := CALCDATE('7D', MyVarDateStart);

                        Buffer.Initialize();

                        MyVarMonth1 := MyVarDateStart;
                        MyVarMonth2 := CALCDATE('7D', MyVarDateStart);
                        MyVarMonth3 := CALCDATE('14D', MyVarDateStart);
                        MyVarMonth4 := CALCDATE('21D', MyVarDateStart);
                        //MyVarMonth5 := CALCDATE('28D',MyVarDateStart);
                        //MyVarMonth6 := CALCDATE('35D',MyVarDateStart);

                        MyVarLabel1 := '(1) ' + FORMAT(MyVarMonth1) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth1));
                        MyVarLabel2 := '(2) ' + FORMAT(MyVarMonth2) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth2));
                        MyVarLabel3 := '(3) ' + FORMAT(MyVarMonth3) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth3));
                        MyVarLabel4 := '(4) ' + FORMAT(MyVarMonth4) + ' upto ' + FORMAT(CALCDATE('6D', MyVarMonth4));
                        //MyVarLabel5 := '(5) ' + FORMAT(MyVarMonth5) + ' upto '+ FORMAT(CALCDATE ('6D', MyVarMonth5));
                        //MyVarLabel6 := '(6) ' + FORMAT(MyVarMonth6) + ' upto '+ FORMAT(CALCDATE ('6D', MyVarMonth6));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '..' + FORMAT(CALCDATE('6D', MyVarMonth4)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin
                            if CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth1) THEN
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth2) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth2)) THEN
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth3) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth3)) THEN
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date >= MyVarMonth4) AND (CustLedgerEntry.Due_Date <= CALCDATE('6D', MyVarMonth4)) THEN
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                        end;
                        CustLedgerEntry.Close();

                        // Index 0
                        //          Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        //Buffer.AddColumn(MyVarLabel5);
                        //Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        //Buffer.AddColumn(MyVarLabel6);
                        //Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.Update(CurrPage.Chart);
                    end;

                    if myChartBy = 4 then begin
                        MyVarDateStart := CALCDATE('1D', MyVarDateStart);

                        Buffer.Initialize();

                        MyVarLabel1 := '(1) ' + FORMAT(MyVarDateStart);
                        MyVarLabel2 := '(2) ' + FORMAT(CALCDATE('1D', MyVarDateStart));
                        MyVarLabel3 := '(3) ' + FORMAT(CALCDATE('2D', MyVarDateStart));
                        MyVarLabel4 := '(4) ' + FORMAT(CALCDATE('3D', MyVarDateStart));
                        MyVarLabel5 := '(5) ' + FORMAT(CALCDATE('4D', MyVarDateStart));
                        MyVarLabel6 := '(6) ' + FORMAT(CALCDATE('5D', MyVarDateStart));
                        MyVarLabel7 := '(7) ' + FORMAT(CALCDATE('6D', MyVarDateStart));

                        // Set Amount - disini
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Due_Date, '..' + FORMAT(CALCDATE('6D', MyVarDateStart)));
                        CustLedgerEntry.SetFilter(CustLedgerEntry.Remaining_Amount, '<>0');

                        CustLedgerEntry.Open();
                        WHILE CustLedgerEntry.READ DO begin
                            if CustLedgerEntry.Due_Date <= MyVarDateStart then
                                MyVarOutAmout1 := MyVarOutAmout1 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('1D', MyVarDateStart)) then
                                MyVarOutAmout2 := MyVarOutAmout2 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('2D', MyVarDateStart)) then
                                MyVarOutAmout3 := MyVarOutAmout3 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('3D', MyVarDateStart)) then
                                MyVarOutAmout4 := MyVarOutAmout4 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('4D', MyVarDateStart)) then
                                MyVarOutAmout5 := MyVarOutAmout5 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('5D', MyVarDateStart)) then
                                MyVarOutAmout6 := MyVarOutAmout6 + CustLedgerEntry.Remaining_Amount;

                            if (CustLedgerEntry.Due_Date = CALCDATE('6D', MyVarDateStart)) then
                                MyVarOutAmout7 := MyVarOutAmout7 + CustLedgerEntry.Remaining_Amount;
                        end;
                        CustLedgerEntry.Close();

                        //            Buffer.AddMeasure('Out. Amount', 0, Buffer."Data Type"::Decimal, Buffer."Chart Type"::Column);

                        Buffer.SetXAxis('Periode', Buffer."Data Type"::String);

                        Buffer.AddColumn(MyVarLabel1);
                        Buffer.SetValueByIndex(0, 0, MyVarOutAmout1);

                        Buffer.AddColumn(MyVarLabel2);
                        Buffer.SetValueByIndex(0, 1, MyVarOutAmout2);

                        Buffer.AddColumn(MyVarLabel3);
                        Buffer.SetValueByIndex(0, 2, MyVarOutAmout3);

                        Buffer.AddColumn(MyVarLabel4);
                        Buffer.SetValueByIndex(0, 3, MyVarOutAmout4);

                        Buffer.AddColumn(MyVarLabel5);
                        Buffer.SetValueByIndex(0, 4, MyVarOutAmout5);

                        Buffer.AddColumn(MyVarLabel6);
                        Buffer.SetValueByIndex(0, 5, MyVarOutAmout6);

                        Buffer.AddColumn(MyVarLabel7);
                        Buffer.SetValueByIndex(0, 6, MyVarOutAmout7);

                        Buffer.Update(CurrPage.Chart);
                    end;
                end;
            }

        }
    }


    var
        myLabelChart: Text;


        myChartBy: Integer;
        // 1 = Weekly And Biweekly
        // 2 = Monthly
        // 3 = Weekly
        // 4 = Daily

        Buffer: Record "Business Chart Buffer" temporary;
        Customer: Record Customer;
        CustLedgerEntry: Query "Cust. Ledger Entries";
        CustLedgerP: Page "Customer Ledger Entries";
        i: Integer;
        MyVarMonth: Integer;
        MyVarYear: Integer;

        MyVarDateStart: Date;
        MyVarDay: Integer;

        MyVarMonth1: Date;
        MyVarMonth2: Date;
        MyVarMonth3: Date;
        MyVarMonth4: Date;
        MyVarMonth5: Date;
        MyVarMonth6: Date;
        MyVarMonth7: Date;
        MyVarMonth8: Date;
        MyVarMonth9: Date;

        MyVarLabel1: Text;
        MyVarLabel2: Text;
        MyVarLabel3: Text;
        MyVarLabel4: Text;
        MyVarLabel5: Text;
        MyVarLabel6: Text;
        MyVarLabel7: Text;
        MyVarLabel8: Text;
        MyVarLabel9: Text;

        MyVarOutAmout1: Decimal;
        MyVarOutAmout2: Decimal;
        MyVarOutAmout3: Decimal;
        MyVarOutAmout4: Decimal;
        MyVarOutAmout5: Decimal;
        MyVarOutAmout6: Decimal;
        MyVarOutAmout7: Decimal;
        MyVarOutAmout8: Decimal;
        MyVarOutAmout9: Decimal;

}