/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('orderPaymentCtrl', ['$scope', 'resolveParams', 'orderService',
        function (s, resolveParams, orderService) {

            s.checkCart();

            s.paymentEnd = false;
            s.$on('updatePaymentEnd', function (event, args) {
                s.paymentEnd = args;
                l(args)
                s.paymentEnd = args.paymentEnd;
                s.orderNumber = args.orderNumber;
                s.trackingNumber = args.trackingNumber;
            });
            s.noCards = resolveParams.noCards; //check if have cards

            s.loadMore_1 = function(){
                s.CardNumber = ["6543", "2109", "8765", "4321"];

                s.card = {
                    number : '6543210987654321',
                    cvv : '567',
                    expirationDate : {
                        month : '04',
                        year : '2016'
                    },
                    name: 'James T. Kirk',
                }
            }
            s.CardNumber = ["6543", "2109", "8765", "4321"];

            s.card = {
                number : '6543210987654321',
                cvv : '567',
                expirationDate : {
                    month : '04',
                    year : '2016'
                },
                name: 'James T. Kirk',
            }

            s.user = resolveParams.user
            s.shipping = resolveParams.shippingCost;
            s.shippingCost = resolveParams.shippingCost ? resolveParams.shippingCost.amount : null;
            s.itemsPaid = s.cart ? s.cart.productsInCart.length : 0;

            var d = new Date();
            s.Date_Ordered = [ d.getDate(),(d.getMonth()+1), d.getFullYear()].join('/');

            s.$watch("userCookie.response", function(n){
                if(n + "" != "undefined"){
                    orderService.getAccountById().
                    then(function (user) {
                        if(user)
                        {
                            orderService.getShippingCost(user).
                            then(function (shippingCost) {
                                s.shipping = shippingCost;
                                s.shippingCost = shippingCost ? shippingCost.amount : null;
                                s.user = user
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




