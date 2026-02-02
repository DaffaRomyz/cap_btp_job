using MainService as service from '../../srv/MainService';
annotate service.Report with @(
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Value : totalmoved,
            Label : 'totalmoved',
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
    ],
    UI.FieldGroup #General : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : totalmoved,
                Label : 'totalmoved',
            },
            {
                $Type : 'UI.DataField',
                Value : timestamp,
                Label : 'timestamp',
            },
        ],
    },
);

