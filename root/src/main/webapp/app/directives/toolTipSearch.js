/**
 * Created by correnti on 07/12/2015.
 */

define(['./module'], function (directives) {
    'use strict';
    directives.directive('toolTipSearch', ['$templateCache',
        function ($templateCache) {
        return {
            restrict: 'E',
            replace: true,
            template: $templateCache.get('app/partials/toolTipSearch.html'),
        };
    }]);
});

