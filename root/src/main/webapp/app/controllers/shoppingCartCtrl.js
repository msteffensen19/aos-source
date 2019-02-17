/**
 * Created by correnti on 06/12/2015.
 */

define(['./module'], function(controllers){
    'use strict';
    controllers.controller('shoppingCartCtrl', ['$scope', 'productsCartService',
        function(s, cartService){

            Helper.forAllPage();

            angular.element(document).ready(function () {

                var result = document.getElementsByClassName("uft-class");

                var checkoutBtn = angular.element(result);

                checkoutBtn.attr("id", "checkOutButton");
                checkoutBtn.attr("name", "check_out_btn");
            });




            s.saveCart = function(){
                cartService.saveCart(s.cart);
            }
    }]);
});