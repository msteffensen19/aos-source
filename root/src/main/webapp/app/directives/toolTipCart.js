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

                scope.checkout = function () {

                    cartService.checkout().then(function (userLogin) {
                        if (!userLogin) {
                            scope.login();
                        }
                        else {
                            console.log("move user to checkout page...");
                        }
                    });
                }
            }
        };
    }]);
});

