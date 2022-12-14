/**
 * Created by kubany on 10/12/2015.
 */
require.config({
    "waitSeconds": 600,
    paths: {

        'jquery': 'vendor/jquery/dist/jquery.min',
        'jquery-bez': 'vendor/jquery-bez/jquery.bez.min',
        'jquery.animate-colors': 'vendor/jquery-color-animation/jquery.animate-colors-min',


        'angular': 'vendor/angular/angular.min',
        'angular-cookie': 'vendor/angular-cookie/angular-cookie.min',
        'toaster': 'vendor/AngularJS-Toaster/toaster.min',
        'angular-translate': 'vendor/angular-translate/angular-translate.min',
        "uiRouter": 'vendor/angular-ui-router/release/angular-ui-router.min',
        'angularAnimate': 'vendor/angular-animate/angular-animate.min',
        'angularAutocomplete': 'vendor/ngAutocomplete/src/ngAutocomplete',
        'underscore' : 'vendor/underscore/underscore-min',

        'bootstrap': 'vendor/bootstrap/dist/js/bootstrap.min',
        'ui-bootstrap': 'vendor/angular-bootstrap/ui-bootstrap-tpls.min',
        'angular-google-analytics': 'vendor/angular-google-analytics/dist/angular-google-analytics',


        'jPushMenu': 'utils/jPushMenu',
        'mainScript': 'utils/main',
        'accordion': 'utils/accordion',
        'server': 'utils/server',
        'nouislider': 'utils/noUiSlider.8.2.1/nouislider',
        'wrongDirection': 'utils/wrongDirection',
        'UserCookie': 'utils/Models/UserCookie',
        'slider': 'utils/slider',
        'jquery-soap': 'utils/jquery.soap',

        'englishLanguage': 'languages/english',



    },
    shim: {


        'angular': {
            'exports': 'angular'
        },
        'app': {
            deps: ['angular']
        },
        'angular-cookie': {
            deps: ['angular']
        },
        'toaster': {
            deps: ['angular', 'angularAnimate']
        },
        'angular-translate': {
            deps: ['angular']
        },
        'nouislider': {},
        'englishLanguage': {},
        'angularAutocomplete': {
            deps: ['angular']
        },
        'ui-bootstrap': ['angular'],
        'angularAnimate': ['angular'],
        'jquery-bez': {
            deps: ['jquery']
        },
        'jquery-soap': {
            'deps': ['jquery']
        },
        'jquery.animate-colors': {
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
        'uiRouter': {
            deps: ['angular']
        }
    }
});

window.name = "NG_DEFER_BOOTSTRAP!";

require(['angular', 'app', 'angular-translate', 'bootstrap', 'englishLanguage',
        'jquery', 'jquery-bez', 'jquery.animate-colors', 'jPushMenu', 'mainScript', 'server',
        'nouislider', 'accordion', 'wrongDirection', 'UserCookie',
        'slider', 'uiRouter', 'angular-cookie', 'toaster', 'angularAutocomplete',
        'angularAnimate', 'ui-bootstrap', 'jquery-soap', 'underscore', 'angular-google-analytics',
    ], function (angular, app) {
            angular.bootstrap(document, ['aos']);
            angular.resumeBootstrap();
    }
);



