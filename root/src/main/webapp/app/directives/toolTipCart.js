/**
 * Created by correnti on 07/12/2015.
 */

define(['./module'], function (directives) {
    'use strict';
    directives.directive('toolTipCart', ['$templateCache', 'productsCartService',
        function ($templateCache, cartService) {
        return {
            template: $templateCache.get('app/partials/toolTipCart.html'),
            link: function(scope) {
            }
        };
    }]);
});

