/**
 * Created by kubany on 10/12/2015.
 */
require.config({
    paths: {
        angular: '../webjars/angularjs/1.4.7/angular.min',
    },
    shim: {
        'angular' : {'exports' : 'angular'},
        'app': {
            deps: ['angular']
        }
    }
});
window.name = "NG_DEFER_BOOTSTRAP!";
require(['angular', 'app'], function(angular, app)
    {
        angular.element().ready(function() {
            // bootstrap the app manually
            angular.bootstrap(document, ['aos']);
            angular.resumeBootstrap();
        });
    }
);