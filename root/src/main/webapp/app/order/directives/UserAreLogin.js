/**
 * Created by correnti on 03/01/2016.
 */




define(['./module'], function (directives) {
    'use strict';
    directives.directive('userAreLogin', ['$templateCache', function($templateCache){
        return {
            replace: true,
            template: $templateCache.get('app/order/partials/user-are-login.html'),
            link: function(s, e, a, ctrl){

                s.firstTag = false;
                s.imgRadioButton = 1;
                s.noCards = false;
                s.savePay = {
                    username : '',
                    password : ''
                }

                s.years = [];
                var now = new Date();
                for(var i = 0; i < 10; i++){
                    s.years.push(now.getFullYear() + i);
                }

                s.card = {
                    number : '',
                    ccv : '',
                    expirationDate : {
                        month : '',
                        year : ''
                    },
                    name: '',
                }

                s.shippingDetails_next = function(){
                    s.firstTag = false;
                }

                s.imgRadioButtonClicked = function(num){
                    s.noCards = false;
                    s.imgRadioButton = num;
                }

                s.paymentMethod_edit_method;
                s.paymentMethod_edit = function(cart) {
                    s.noCards = true;
                }

                s.CardNumber = [
                    ["6789", "0785", "0785", "0785"],
                    ["6789", "0785", "0785", "1234"],
                    ["6789", "0785", "0785", "5678"],
                    ["6789", "0785", "0785", "9999"],
                    ["6789", "0785", "0785", "8888"]
                ];

                s.orderNumber;
                s.trackingNumber;

                s.payNow_SafePay = function(){
                    s.paymentEnd = true;
                }

            },
        }
    }]);
});


