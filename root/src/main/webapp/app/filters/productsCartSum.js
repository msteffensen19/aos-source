/**
 * Created by correnti on 10/12/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.filter('productsCartSum', function(){
        return function(cart, plus) {
            var count = 0;
            var increment = plus || 0;
            if(cart) {
                angular.forEach(cart.productsInCart, function (product) {
                    count += (product.price * product.quantity);
                })
            }
            return parseFloat(increment) + count;
        };
    }).
    filter('secCatWord', function(){
        return function(text, maxLength) {
            if(text.length > maxLength){
                text = text.substring(0, maxLength - 3) + "...";
            }
            return text;
        };
    })
    ;
});

