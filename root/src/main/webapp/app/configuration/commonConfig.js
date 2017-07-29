/**
 * Created by kubany on 7/25/2017.
 */
define([], function () {

    function config(AnalyticsProvider) {
        AnalyticsProvider.setAccount('UA-81334227-1');
        AnalyticsProvider.trackUrlParams(true);
        AnalyticsProvider.trackPages(true);
        AnalyticsProvider.setPageEvent('$stateChangeSuccess');
    }

    return ['AnalyticsProvider', config];

});