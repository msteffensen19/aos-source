define(['./module'], function (directives) {
    'use strict';
    directives.directive('iconSearchSvg', ['$templateCache', function($templateCache){
        return{
            restrict: 'A',
            replace: true,
            template: $templateCache.get('app/partials/icon-search-svg.html')
        }
    }]);
});