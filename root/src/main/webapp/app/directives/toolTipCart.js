/**
 * Created by correnti on 07/12/2015.
 */

define(['./module'], function (directives) {
    'use strict';
    directives.directive('toolTipCart', ['$templateCache', 'productsCartService', '$location',
        function ($templateCache, cartService, location) {
        return {
            template: $templateCache.get('app/partials/toolTipCart.html'),
            link: function(s) {
                s.checkout = function(){
                    location.path('/orderPayment');
                }
            }
        };
    }]);
});

