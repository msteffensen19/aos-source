/**
 * Created by correnti on 06/12/2015.
 */

define(['./module'], function(controllers){
    'use strict';
    controllers.controller('shoppingCartCtrl', ['$scope', 'productsCartService', 'userService',
        function(s, cartService, userService){

            Helper.forAllPage();
            s.enableWarranty = false;
            userService.haveConfiguration().then(function(res){
                    s.enableWarranty = userService.getWarrantyProperties().enableWarranty;
                },
                function(err){

                });
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