/**
 * Created by kubany on 10/12/2015.
 */
require.config({
    "waitSeconds" : 600,
    paths: {
        'angular': 'vendor/angular/angular.min',
        'angular-cookie': 'vendor/angular-cookie/angular-cookie.min',
        'angular-translate': 'vendor/angular-translate/angular-translate.min',
        'bootstrap' : 'vendor/bootstrap/dist/js/bootstrap.min',
        'ui-bootstrap' : 'vendor/angular-bootstrap/ui-bootstrap-tpls.min',
        'jquery' : 'vendor/jquery/dist/jquery.min',
        'jquery-bez' : 'vendor/jquery-bez/jquery.bez.min',
        'jPushMenu' : 'utils/jPushMenu',
        'mainScript' : 'utils/main',
        'accordion' : 'utils/accordion',
        'wrongDirection' : 'utils/wrongDirection',
        'UserCookie' : 'utils/Models/UserCookie',
        'slider' : 'utils/slider',
        "uiRouter": 'vendor/angular-ui-router/release/angular-ui-router.min',
        //'angularRoute': 'vendor/angular-route/angular-route.min',
        'angularAnimate' : 'vendor/angular-animate/angular-animate.min',
    },
    shim: {
        'angular' : {'exports' : 'angular'},
        'app': {
            deps: ['angular']
        },
        'angular-cookie': {
            deps: ['angular']
        },
        'angular-translate': {
            deps: ['angular']
        },
        //'angularRoute': ['angular'],
        'ui-bootstrap': ['angular'],
        'angularAnimate': ['angular'],
        'jquery-bez' : {
            deps: ['jquery']
        },
        'bootstrap': {
            deps: ['jquery']
        },
        'jPushMenu': {
            deps: ['jquery']
        },
        'mainScript': {
            deps: ['jquery']
        },
        'accordion': {
            deps: ['jquery']
        },
        'slider': {
            deps: ['jquery']
        },
        'wrongDirection': {
            deps: ['jquery']
        },
        'uiRouter':{
            deps: ['angular']
        }
    }
});

window.name = "NG_DEFER_BOOTSTRAP!";
require(['angular', 'app', 'angular-translate', 'bootstrap',
         'jquery', 'jquery-bez', 'jPushMenu','mainScript',
        'accordion', 'wrongDirection', 'UserCookie',
        'slider', 'uiRouter', 'angular-cookie',
        'angularAnimate','ui-bootstrap'], function(angular, app)
    {
        angular.element().ready(function() {
            // bootstrap the app
            angular.bootstrap(document, ['aos']);
            angular.resumeBootstrap();
        });
    }
);