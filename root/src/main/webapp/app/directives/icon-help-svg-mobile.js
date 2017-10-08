define(['./module'], function (directives) {
    'use strict';
    directives.directive('iconHelpSvgMobile', ['$templateCache', function($templateCache){
        return{
            restrict: 'A',
            replace: true,
            template: $templateCache.get('app/partials/icon-help-svg-mobile.html')
        }
    }]);
});