/**
 * Created by correnti on 07/12/2015.
 */

define(['./module'], function (directives) {
    'use strict';
    directives.directive('toolTipCart', ['$templateCache', 'productsCartService',
        function ($templateCache, cartService) {
        return {
            restrict: 'E',
            replace: true,
            template: $templateCache.get('app/partials/toolTipCart.html'),
            controller: 'mainCtrl',
            link: function(scope, element, attrs, ctrls) {

                    scope.checkout = function () {
                        console.log('scope.cart');
                        console.log(scope.cart);
                    }
                }
        };
    }]);
});

