/**
 * Created by correnti on 06/12/2015.
 */

define(['./module'], function(controllers){
    'use strict';
    controllers.controller('shoppingCartCtrl', ['$scope', 'productsCartService',
        function(s, cartService){

            s.categoryName = 'MICE';
            s.cart;

            cartService.getCart().then(function(cart){
                s.cart = cart;
            });

            s.removeProduct = function(index){
                cartService.removeProduct(index).then(function(cart){
                    s.cart = cart;
                });
            }

            Helper.forAllPage();
            $("nav .navLinks").css("display" , "none");
    }]);
});