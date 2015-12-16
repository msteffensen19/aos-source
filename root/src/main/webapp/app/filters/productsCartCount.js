/**
 * Created by correnti on 10/12/2015.
 */

define(['./module'], function (controllers) {
    'use strict';

    controllers.filter('productsCartCount', function(){
        return function(cart) {
            var count = 0;
            angular.forEach(cart.productsInCart, function(product){
                count += product.quantity;
            })
            return count;
        };
    });
});

