using { model } from '../db/schema';

service MainService {

    @odata.draft.enabled
    entity Account as projection on model.Account;

    @odata.draft.enabled
    entity Transaction as projection on model.Transaction;

    entity Report as projection on model.Report;
}