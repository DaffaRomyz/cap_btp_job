using { model } from '../db/schema';

service MainService {

    @odata.draft.enabled
    entity Account as projection on model.Account;

    @odata.draft.enabled
    entity Transaction as projection on model.Transaction;

    entity Report as projection on model.Report;

    @Common.SideEffects : {
        $Type : 'Common.SideEffectsType',
        TargetEntities : ['/MainService.EntityContainer/Account'],
    }
    action updateAccountBalance();

    @Common.SideEffects : {
        $Type : 'Common.SideEffectsType',
        TargetEntities : ['/MainService.EntityContainer/Report'],
    }
    action generateReport();
}