namespace model;

using { cuid, Currency } from '@sap/cds/common';

entity Account : cuid {
    name : String(12);

    @Measures.ISOCurrency : currency_code
    balance : Decimal(9, 2);
    
    currency : Currency;
}

entity Transaction : cuid {
    timestamp : Timestamp;

    @Measures.ISOCurrency : currency_code
    amount : Decimal(9, 2);
    
    currency : Currency;

    sender : Association to one Account;

    receiver : Association to one Account;
}

entity Report : cuid{
    timestamp : Timestamp;

    @Measures.ISOCurrency : currency_code
    totalmoved: Decimal(9, 2);
    
    currency : Currency;
}