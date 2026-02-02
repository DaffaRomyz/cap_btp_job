sap.ui.require(
    [
        'sap/fe/test/JourneyRunner',
        'capbtpjobtransaction/test/integration/FirstJourney',
		'capbtpjobtransaction/test/integration/pages/TransactionList',
		'capbtpjobtransaction/test/integration/pages/TransactionObjectPage'
    ],
    function(JourneyRunner, opaJourney, TransactionList, TransactionObjectPage) {
        'use strict';
        var JourneyRunner = new JourneyRunner({
            // start index.html in web folder
            launchUrl: sap.ui.require.toUrl('capbtpjobtransaction') + '/index.html'
        });

       
        JourneyRunner.run(
            {
                pages: { 
					onTheTransactionList: TransactionList,
					onTheTransactionObjectPage: TransactionObjectPage
                }
            },
            opaJourney.run
        );
    }
);