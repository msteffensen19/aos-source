/**
 * Created by kubany on 10/12/2015.
 */
require.config({
    "waitSeconds" : 600,
    paths: {
        'angular': 'vendor/angular/angular.min',
        'angular-cookie': 'vendor/angular-cookie/angular-cookie.min',
        'angular-translate': 'vendor/angular-translate/angular-translate.min',
        'angular-nouislider': 'vendor/nouislider-angular/nouislider.min',
        'bootstrap' : 'vendor/bootstrap/dist/js/bootstrap.min',
        'ui-bootstrap' : 'vendor/angular-bootstrap/ui-bootstrap-tpls.min',
        'jquery' : 'vendor/jquery/dist/jquery.min',
        'jquery-bez' : 'vendor/jquery-bez/jquery.bez.min',
        'jquery.animate-colors' : 'vendor/jquery-color-animation/jquery.animate-colors-min',
        "uiRouter": 'vendor/angular-ui-router/release/angular-ui-router.min',
        //'angularRoute': 'vendor/angular-route/angular-route.min',
        'angularAnimate' : 'vendor/angular-animate/angular-animate.min',
        'angularAutucomplete' : 'vendor/ngAutocomplete/src/ngAutocomplete',
        'jPushMenu' : 'utils/jPushMenu',
        'mainScript' : 'utils/main',
        'accordion' : 'utils/accordion',
        'server' : 'utils/server',
        'wrongDirection' : 'utils/wrongDirection',
        'UserCookie' : 'utils/Models/UserCookie',
        'slider' : 'utils/slider', },
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
        'angular-nouislider' : {
            deps:[ 'angular' ]
        },
        'angularAutucomplete' : {
            deps: ['angular']
        },
        //'angularRoute': ['angular'],
        'ui-bootstrap': ['angular'],
        'angularAnimate': ['angular'],
        'jquery-bez' : {
            deps: ['jquery']
        },
        'jquery.animate-colors' : {
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
require(['angular', 'app', 'angular-translate', 'angular-nouislider', 'bootstrap',
         'jquery', 'jquery-bez', 'jquery.animate-colors','jPushMenu','mainScript', 'server',
        'accordion', 'wrongDirection', 'UserCookie',
        'slider', 'uiRouter', 'angular-cookie', 'angularAutucomplete',
        'angularAnimate','ui-bootstrap'], function(angular, app)
    {
        angular.element().ready(function() {
            angular.bootstrap(document, ['aos']);
            angular.resumeBootstrap();
        });
    }
);