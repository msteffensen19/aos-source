/**
 * Created by kubany on 10/12/2015.
 */
require.config({
    paths: {
        angular: 'webjars/angularjs/1.4.7/angular.min',
        'angular-translate': 'webjars/angular-translate/2.7.2/angular-translate',
        'bootstrap' : 'webjars/bootstrap/3.3.5/js/bootstrap.min',
        'jquery' : 'webjars/jquery/2.1.4/jquery.min',
        'jPushMenu' : 'utils/jPushMenu',
        'angularRoute': 'webjars/angularjs/1.4.7/angular-route.min',
    },
    shim: {
        'angular' : {'exports' : 'angular'},
        'app': {
            deps: ['angular']
        },
        'angular-translate': {
            deps: ['angular']
        },
        'angularRoute': ['angular'],
        'bootstrap': {
            deps: ['jquery']
        },
        'jPushMenu': {
            deps: ['jquery']
        }
    }
});
window.name = "NG_DEFER_BOOTSTRAP!";
require(['angular', 'app', 'angular-translate', 'bootstrap', 'jquery', 'jPushMenu', 'angularRoute'], function(angular, app)
    {
        angular.element().ready(function() {
            // bootstrap the app manually
            angular.bootstrap(document, ['aos']);
            angular.resumeBootstrap();
        });
    }
);