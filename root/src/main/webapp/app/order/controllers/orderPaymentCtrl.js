/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('orderPaymentCtrl', ['$scope', 'resolveParams',
        function (s, resolveParams) {

            s.loginModal = {
                email : '',
                password : ''
            }

            s.shippingCost = resolveParams.shippingCost;

            s.login = function(){
                //alert()
            }

            $("nav .navLinks").css("display" , "none");
        }]);
});


