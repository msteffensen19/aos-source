define(['./module'], function (directives) {
    'use strict';
    directives.directive('iconHomeSvgMobile', ['$templateCache', function($templateCache){
        return{
            restrict: 'A',
            replace: true,
            template: $templateCache.get('app/partials/icon-home-svg-mobile.html')
        }
    }]);
});