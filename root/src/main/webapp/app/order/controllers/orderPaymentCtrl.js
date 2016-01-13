/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('orderPaymentCtrl', ['$scope', 'resolveParams', 'orderService',
        function (s, resolveParams, orderService) {

            s.checkCart();

            s.paymentEnd = false;

            console.log(resolveParams)

            s.user = resolveParams.user
            s.shippingCost = resolveParams.shippingCost;
            s.itemsPaid = s.cart ? s.cart.productsInCart.length : 0;

            s.CardNumber = ["6789", "0785", "0785", "0785"];

            var d = new Date();
            s.Date_Ordered = [ d.getDate(),(d.getMonth()+1), d.getFullYear()].join('/');

            s.payNow_masterCredit = function(){
                s.paymentEnd = true;
            }

            s.$watch("userCookie.response", function(n, o){
                if(n + "" != "undefined"){
                    orderService.getAccountById().
                    then(function (user) {
                        if(user)
                        {
                            orderService.getShippingCost(user).
                            then(function (shippingCost) {
                                s.shippingCost = shippingCost;
                                s.loginUser = user
                            });
                        }
                        else {
                            s.shippingCost = null;
                            s.loginUser = null;
                        }
                    });
                }
            })

            $("nav .navLinks").css("display" , "none");

            Helper.forAllPage();

        }]);
});




