/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('orderPaymentCtrl', ['$scope', '$rootScope','resolveParams', 'orderService', 'productsCartService',
        function (s, rs, resolveParams, orderService, cartService) {

            s.checkCart();

            s.paymentEnd = false;
            s.$on('updatePaymentEnd', function (event, args) {
                s.paymentEnd = args.paymentEnd;
                s.orderNumber = args.orderNumber;
                s.trackingNumber = args.trackingNumber;
                rs.$broadcast('clearCartEvent');
            });
            s.noCards = resolveParams.noCards; //check if have cards

            s.getData = function(){
                s.card = {
                    number : '6543210987654321',
                    cvv : '567',
                    expirationDate : { month : '04', year : '2016' },
                    name: 'James T. Kirk',
                }
                s.CardNumber = ["6543", "2109", "8765", "4321"];
                s.savePay = {
                    username : 'abcdefghi', // 1-20 chars
                    password : 'Aa123456' // 1-20 chars
                }
            }

            s.CardNumber = resolveParams.CardNumber;
            s.card = {
                number : '',
                cvv : '',
                expirationDate : {
                    month : '',
                    year : ''
                },
                name: '',
            }
            s.savePay = {
                username : '',
                password : ''
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




