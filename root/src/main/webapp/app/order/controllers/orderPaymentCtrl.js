/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('orderPaymentCtrl', ['$scope', 'resolveParams',
        function (s, resolveParams) {

            s.checkCart();
            s.paymentEnd = true;
            s.shippingCost = resolveParams.shippingCost;
            s.userLogin = resolveParams.userLogin;
            s.itemsPaid = s.cart.productsInCart.length;

            s.CardNumber = ["6789", "0785", "0785", "0785"];

            s.payNow_masterCredit = function(){
                s.paymentEnd = true;
            }

            $("nav .navLinks").css("display" , "none");
        }]);
});




