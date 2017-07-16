define(['./module'], function (directives) {
    'use strict';
    directives.directive('iconCartSvg', ['$templateCache', function($templateCache){
        return{
            restrict: 'A',
            replace: true,
            template: $templateCache.get('app/partials/icon-cart-svg.html')
        }
    }]);
});