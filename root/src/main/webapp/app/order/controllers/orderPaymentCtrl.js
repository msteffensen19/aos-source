/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('orderPaymentCtrl', ['$scope', 'resolveParams',
        function (s, resolveParams) {

            s.checkCart();

            console.log("order payment page not done yet!");

            window.history.back();


            s.loginModal = {
                email : '',
                password : ''
            }

            s.shippingCost = resolveParams.shippingCost;
            s.userLogin = resolveParams.userLogin;

            $("nav .navLinks").css("display" , "none");
        }]);
});


