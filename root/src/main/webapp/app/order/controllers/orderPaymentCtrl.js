/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('orderPaymentCtrl', ['$scope', '$rootScope','resolveParams',
        'orderService', 'productsCartService', '$filter', '$location',
        function (s, rs, resolveParams, orderService, cartService, $filter, $location) {


            s.user = resolveParams.user
            s.masterCredit = resolveParams.paymentPreferences.masterCredit;
            s.safePay = resolveParams.paymentPreferences.safePay;
            if(resolveParams.user == null){
                $location.path('login')
                return;
            }

            s.checkCart();

            ///// PAYMENT SUCCESS ////
            s.paymentEnd = false;
            s.$on('updatePaymentEnd', function (event, args) {
                s.paymentEnd = args.paymentEnd;
                s.orderNumber = args.orderNumber;
                s.trackingNumber = args.trackingNumber;
                s.cardNumber = ['0000', '0000', '0000', args.cardNumber.substring(args.cardNumber.length - 4)];
                s.subTotal = ($filter("productsCartSum")(s.cart));
                s.total = ($filter("productsCartSum")(s.cart, s.shippingCost));
                s.TransPaymentMethod = args.TransPaymentMethod;
                rs.$broadcast('clearCartEvent');
            });
            ///// END PAYMENT SUCCESS ////

            s.noCards = resolveParams.noCards; //check if have cards

            s.cardNumber = resolveParams.CardNumber;

            s.card = { number: '', cvv: '', expirationDate: { month: '', year: '' }, name: '' }
            s.savePay = { username : '', password : '' }

            var masterCredit = resolveParams.paymentPreferences && resolveParams.paymentPreferences.masterCredit ?
                resolveParams.paymentPreferences.masterCredit : null;
            if(masterCredit != null){
                s.card = {
                    number: masterCredit.cardNumber.substring(4),
                    cvv: masterCredit.cvvNumber,
                    expirationDate: {
                        month: masterCredit.expirationDate.substring(0, 2),
                        year: masterCredit.expirationDate.substring(2)
                    },
                    name: "",
                }
            }
            var safePay = resolveParams.paymentPreferences && resolveParams.paymentPreferences.safePay ?
                resolveParams.paymentPreferences.safePay : null;
            if(safePay != null){
                s.savePay.username = safePay.safepayUsername;
            }

/*

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
*/
            s.agree_Agreement = true;

            s.shipping = resolveParams.shippingCost;
            s.shippingCost = resolveParams.shippingCost ? resolveParams.shippingCost.amount : null;
            s.itemsPaid = s.cart ? s.cart.productsInCart.length : 0;

            var d = new Date();
            s.Date_Ordered = [ d.getDate(),(d.getMonth()+1), d.getFullYear()].join('/');

            Helper.forAllPage();

        }]);
});




