define(['./module'], function (directives) {
    'use strict';
    directives.directive('iconHelpSvg', ['$templateCache', function($templateCache){
        return{
            restrict: 'A',
            replace: true,
            template: $templateCache.get('app/partials/icon-help-svg.html')
        }
    }]);
});