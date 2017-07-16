define(['./module'], function (directives) {
    'use strict';
    directives.directive('iconSearchSvgMobile', ['$templateCache', function($templateCache){
        return{
            restrict: 'A',
            replace: true,
            template: $templateCache.get('app/partials/icon-search-svg-mobile.html')
        }
    }]);
});