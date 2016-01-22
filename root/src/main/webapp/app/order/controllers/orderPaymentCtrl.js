/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('orderPaymentCtrl', ['$scope', '$rootScope','resolveParams',
        'orderService', 'productsCartService', '$filter',
        function (s, rs, resolveParams, orderService, cartService, $filter) {

            s.checkCart();

            ///// PAYMENT SUCCESS ////
            s.paymentEnd = false;
            s.$on('updatePaymentEnd', function (event, args) {
                s.paymentEnd = args.paymentEnd;
                s.orderNumber = args.orderNumber;
                s.trackingNumber = args.trackingNumber;
                s.cardNumber = ['0000', '0000', '0000', args.cardNumber.substring(args.cardNumber.length - 4)];
                l(s.cardNumber)
                s.subTotal = ($filter("productsCartSum")(s.cart));
                s.total = ($filter("productsCartSum")(s.cart, s.shippingCost));
                rs.$broadcast('clearCartEvent');
            });
            ///// END PAYMENT SUCCESS ////



            s.noCards = resolveParams.noCards; //check if have cards

            s.cardNumber = resolveParams.CardNumber;
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




