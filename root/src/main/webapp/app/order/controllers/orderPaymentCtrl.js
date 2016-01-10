/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('orderPaymentCtrl', ['$scope', 'resolveParams',
        function (s, resolveParams) {

            s.checkCart();
            s.paymentEnd = false;
            s.shippingCost = resolveParams.shippingCost;
            s.userLogin = resolveParams.userLogin;

            s.payNow_masterCredit = function(){
                console.log(s)
                s.paymentEnd = true;
            }

            $("nav .navLinks").css("display" , "none");
        }]);
});


