page 50146 "User Location"
{
    PageType = List;
    ApplicationArea = All;
    UsageCategory = Lists;
    SourceTable = "User Location";
    Caption = 'User Location';
    Editable = true;

    layout
    {
        area(Content)
        {
            repeater(Group)
            {
                field("User ID"; UserID)
                {
                    Caption = 'User ID';
                }
                field(Branches; Branches) { }
                field(GroupName; GroupName) { }


            }
        }
    }
}