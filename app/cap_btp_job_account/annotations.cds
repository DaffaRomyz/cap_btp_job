using MainService as service from '../../srv/MainService';
annotate service.Account with @(
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Value : name,
            Label : 'name',
        },
        {
            $Type : 'UI.DataField',
            Value : balance,
            Label : 'balance',
        },
        {
            $Type : 'UI.DataFieldForAction',
            Action : 'MainService.EntityContainer/updateAccountBalance',
            Label : 'updateAccountBalance',
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
                Value : name,
                Label : 'name',
            },
            {
                $Type : 'UI.DataField',
                Value : balance,
                Label : 'balance',
            },
        ],
    },
);

