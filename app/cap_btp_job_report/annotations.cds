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
        {
            $Type : 'UI.DataFieldForAction',
            Action : 'MainService.EntityContainer/generateReport',
            Label : 'generateReport',
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
    UI.SelectionPresentationVariant #table : {
        $Type : 'UI.SelectionPresentationVariantType',
        PresentationVariant : {
            $Type : 'UI.PresentationVariantType',
            Visualizations : [
                '@UI.LineItem',
            ],
            SortOrder : [
                {
                    $Type : 'Common.SortOrderType',
                    Property : timestamp,
                    Descending : false,
                },
            ],
        },
        SelectionVariant : {
            $Type : 'UI.SelectionVariantType',
            SelectOptions : [
            ],
        },
    },
);

