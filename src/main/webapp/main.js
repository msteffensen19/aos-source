/**
 * Created by kubany on 10/12/2015.
 */
require.config({
    "waitSeconds" : 600,
    paths: {
        angular: 'vendor/angular/angular.min',
        'angular-translate': 'vendor/angular-translate/angular-translate.min',
        'bootstrap' : 'vendor/bootstrap/dist/js/bootstrap.min',
        'jquery' : 'vendor/jquery/dist/jquery.min',
        'jPushMenu' : 'utils/jPushMenu',
        'angularRoute': 'vendor/angular-route/angular-route.min',
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