/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('orderPaymentCtrl', ['$scope', 'resolveParams', 'orderService',
        function (s, resolveParams, orderService) {

            s.checkCart();
            s.paymentEnd = false;

            console.log(resolveParams.user)
            console.log(s)

            s.user = resolveParams.user
            s.shippingCost = resolveParams.shippingCost;
            s.itemsPaid = s.cart.productsInCart.length;

            s.CardNumber = ["6789", "0785", "0785", "0785"];

            s.payNow_masterCredit = function(){
                s.paymentEnd = true;
            }


            s.$watch("userCookie.response", function(n, o){
                if(n + "" != "undefined"){
                    orderService.getAccountById().
                    then(function (user) {
                        s.shippingCost = 10;
                        l(user)
                        s.user = user
                    });
                }
            })

            $("nav .navLinks").css("display" , "none");


            // cant login with email
            // dont have shipping request to extract
            // sending cost
        }]);
});




