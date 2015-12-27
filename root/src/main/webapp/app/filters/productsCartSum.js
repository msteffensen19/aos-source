/**
 * Created by correnti on 10/12/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.filter('productsCartSum', function(){
        return function(cart) {
            var count = 0;
            if(cart) {
                angular.forEach(cart.productsInCart, function (product) {
                    count += (product.price * product.quantity);
                })
            }
            return count;
        };
    });
});

