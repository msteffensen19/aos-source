/**
 * Created by correnti on 15/12/2015.
 */

define(['./module'], function (directives) {
    'use strict';
    directives.directive('scrollToTop', ['$templateCache', function ($templateCache) {
        return {
            restrict: 'E',
            replace: 'true',
            template: $templateCache.get('app/partials/scrollToTop.html'),
            link: function (scope, element, attr) {
                element.bind('click', function () {
                    $('body, html').animate({scrollTop: 0}, 1000);
                    $(this).find("p").animate({
                        bottom: -100,
                    }, 1000);
                });
                element.bind('mouseover', function () {
                    $(this).find("p").animate({
                        bottom: 0
                    }, 400);
                });
            }
        };
    }]);
});