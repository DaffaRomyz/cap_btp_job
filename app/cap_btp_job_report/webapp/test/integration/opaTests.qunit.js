sap.ui.require(
    [
        'sap/fe/test/JourneyRunner',
        'capbtpjobreport/test/integration/FirstJourney',
		'capbtpjobreport/test/integration/pages/ReportList',
		'capbtpjobreport/test/integration/pages/ReportObjectPage'
    ],
    function(JourneyRunner, opaJourney, ReportList, ReportObjectPage) {
        'use strict';
        var JourneyRunner = new JourneyRunner({
            // start index.html in web folder
            launchUrl: sap.ui.require.toUrl('capbtpjobreport') + '/index.html'
        });

       
        JourneyRunner.run(
            {
                pages: { 
					onTheReportList: ReportList,
					onTheReportObjectPage: ReportObjectPage
                }
            },
            opaJourney.run
        );
    }
);