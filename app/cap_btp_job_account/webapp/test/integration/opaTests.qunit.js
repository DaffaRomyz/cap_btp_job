sap.ui.require(
    [
        'sap/fe/test/JourneyRunner',
        'capbtpjobaccount/test/integration/FirstJourney',
		'capbtpjobaccount/test/integration/pages/AccountList',
		'capbtpjobaccount/test/integration/pages/AccountObjectPage'
    ],
    function(JourneyRunner, opaJourney, AccountList, AccountObjectPage) {
        'use strict';
        var JourneyRunner = new JourneyRunner({
            // start index.html in web folder
            launchUrl: sap.ui.require.toUrl('capbtpjobaccount') + '/index.html'
        });

       
        JourneyRunner.run(
            {
                pages: { 
					onTheAccountList: AccountList,
					onTheAccountObjectPage: AccountObjectPage
                }
            },
            opaJourney.run
        );
    }
);