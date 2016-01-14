/**
 * Created by correnti on 03/01/2016.
 */




define(['./module'], function (directives) {
    'use strict';
    directives.directive('userAreLogin', ['$templateCache', 'orderService', function($templateCache, orderService){
        return {
            replace: true,
            template: $templateCache.get('app/order/partials/user-are-login.html'),
            link: function(s, e, a, ctrl){

                s.firstTag = true;
                s.imgRadioButton = 1;
                s.noCards = false; //check if have cards

                s.savePay = {
                    username : 'abcdefghi', // 1-20 chars
                    password : 'Aa123456' // 1-20 chars
                }

                s.years = [];
                var now = new Date();
                for(var i = 0; i < 10; i++){
                    s.years.push((now.getFullYear() + i) + "");
                }

                s.card = {
                    number : '6543210987654321',
                    cvv : '666',
                    expirationDate : {
                        month : '04',
                        year : '2016'
                    },
                    name: 'James T. Kirk',
                }

                s.payNow_SafePay = function(){

                    var accountNumber = 843200971;
                    var TransPaymentMethod = "SafePay"

                    orderService.SafePay(
                        s.user,
                        s.savePay,
                        s.card,
                        s.shipping,
                        s.cart,
                        accountNumber,
                        TransPaymentMethod
                    ).then(function(res){
                        if(res)
                        {
                            s.paymentEnd = true;
                            return;
                        }
                        s.paymentEnd = false;
                    });
                }

                s.payNow_masterCredit = function(){
                    var accountNumber = 112987298763;
                    s.paymentEnd = true;
                }

                s.shippingDetails_next = function(){
                    s.firstTag = false;
                }

                s.imgRadioButtonClicked = function(num){
                    s.noCards = false;
                    s.imgRadioButton = num;
                }

                s.paymentMethod_edit_method;
                s.paymentMethod_edit = function() {
                    s.noCards = true;
                }

                s.orderNumber;
                s.trackingNumber;

            },
        }
    }]);
});


