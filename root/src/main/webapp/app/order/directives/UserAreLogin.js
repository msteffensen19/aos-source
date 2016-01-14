/**
 * Created by correnti on 03/01/2016.
 */




define(['./module'], function (directives) {
    'use strict';
    directives.directive('userAreLogin', ['$rootScope', '$templateCache', 'orderService', function(rs, $templateCache, orderService){
        return {
            replace: true,
            template: $templateCache.get('app/order/partials/user-are-login.html'),
            link: function(s){

                s.firstTag = true;
                s.imgRadioButton = 1;

                s.years = [];
                var now = new Date();
                for(var i = 0; i < 10; i++){
                    s.years.push((now.getFullYear() + i) + "");
                }

                s.payNow_SafePay = function(){
                    var TransPaymentMethod = "SafePay";
                    var accountNumber = 843200971;
                    safePay(TransPaymentMethod, accountNumber);
                }

                function safePay(TransPaymentMethod, accountNumber){
                    orderService.SafePay( s.user, s.savePay, s.card, s.shipping, s.cart, accountNumber, TransPaymentMethod)
                        .then(function(res){
                            if(res.success){

                                rs.$broadcast('updatePaymentEnd', {
                                    paymentEnd: true,
                                    orderNumber : res.orderNumber,
                                    trackingNumber : Helper.getRandom(10)
                                });
                                return;
                            }
                            s.paymentEnd = false;
                        });
                }

                s.payNow_masterCredit = function(){
                    var TransPaymentMethod = "MasterCredit";
                    var accountNumber = 112987298763;
                    safePay(TransPaymentMethod, accountNumber);
                }

                s.payNow_manual = function(){
                    s.card.number = '';
                    angular.forEach(s.CardNumber, function(fourDigits){
                        s.card.number += fourDigits + "";
                    })
                    s.payNow_masterCredit()
                }

                s.shippingDetails_next = function(){
                    s.firstTag = false;
                }

                s.imgRadioButtonClicked = function(num){
                     s.imgRadioButton = num;
                }

                s.paymentMethod_edit_method;
                s.paymentMethod_edit = function() {
                    s.noCards = true;
                }
            },
        }
    }]);
});


