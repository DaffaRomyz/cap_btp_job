using MainService as service from '../../srv/MainService';
annotate service.Transaction with @(
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Value : amount,
            Label : 'amount',
        },
        {
            $Type : 'UI.DataField',
            Value : sender_ID,
            Label : 'sender_ID',
        },
        {
            $Type : 'UI.DataField',
            Value : receiver_ID,
            Label : 'receiver_ID',
        },
        {
            $Type : 'UI.DataField',
            Value : timestamp,
            Label : 'timestamp',
        },
    ],
    UI.Facets : [
        {
            $Type : 'UI.ReferenceFacet',
            Label : 'General',
            ID : 'General',
            Target : '@UI.FieldGroup#General',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : 'Account',
            ID : 'Account',
            Target : '@UI.FieldGroup#Account',
        },
    ],
    UI.FieldGroup #General : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : amount,
                Label : 'amount',
            },
            {
                $Type : 'UI.DataField',
                Value : timestamp,
                Label : 'timestamp',
            },
        ],
    },
    UI.FieldGroup #Account : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : sender_ID,
                Label : 'sender_ID',
            },
            {
                $Type : 'UI.DataField',
                Value : receiver_ID,
                Label : 'receiver_ID',
            },
        ],
    },
);

annotate service.Transaction with {
    sender @(
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'Account',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : sender_ID,
                    ValueListProperty : 'ID',
                },
            ],
            Label : 'Sender Account',
        },
        Common.ValueListWithFixedValues : false,
        Common.ExternalID : sender.name,
    )
};

annotate service.Transaction with {
    receiver @(
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'Account',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : receiver_ID,
                    ValueListProperty : 'ID',
                },
            ],
            Label : 'Receiver Account',
        },
        Common.ValueListWithFixedValues : false,
        Common.ExternalID : receiver.name,
    )
};

