table 50100 "User Location"
{
    DataClassification = ToBeClassified;

    fields
    {
        field(1; UserID; Code[50])
        {

            DataClassification = ToBeClassified;
        }
        field(2; Branches; Code[10])
        {
            DataClassification = ToBeClassified;
        }

        field(3; GroupName; Code[20])
        {
            DataClassification = ToBeClassified;
        }
    }

    keys
    {
        key(PK; UserID)
        {
            Clustered = true;
        }
    }
}