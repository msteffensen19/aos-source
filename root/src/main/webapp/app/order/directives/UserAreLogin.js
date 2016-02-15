/**
 * Created by correnti on 03/01/2016.
 */


define(['./module'], function (directives) {
    'use strict';
    directives.directive('userAreLogin', ['$rootScope', '$templateCache', 'orderService',
        'registerService', '$timeout',
        function (rs, $templateCache, orderService, registerService, $timeout) {
        return {
            replace: true,
            template: $templateCache.get('app/order/partials/user-are-login.html'),
            link:{
                pre: function(s){
                    s.userDetailsEditMode = false;
                    s.invalidUser = true;
                    s.validSecValidate = function(invalid){
                        s.invalidUser = s.userDetailsEditMode = invalid;
                    }
                },
                post: function(s){

                    s.firstTag = true;
                    s.imgRadioButton = 1;

                    s.countries = null;
                    registerService.getAllCountries().then(function (countries) {
                        for(var i  in countries){
                            if(countries[i].id == s.user.countryId)
                            {
                                s.country = countries[i];
                                break;
                            }
                        }
                        s.countries = countries;
                    });

                    s.countryChange = function(country){
                        s.user.countryId = country.id;
                        s.user.country = country.isoName;
                    }

                    s.backToMainShippingDetails = function(){
                        if(s.invalidUser){
                            return;
                        }
                        s.userDetailsEditMode = false;
                    }

                    var aaaa = 0;
                    s.accountUpdate = function(){
                        l(++aaaa)
                        l(s.agree_Agreement)
                        if(s.agree_Agreement)
                        {
                            orderService.accountUpdate(s.user).then(function(res){
                                s.invalidUser = false;
                                s.userDetailsEditMode = false;
                                s.firstTag = false;
                            });
                        }
                        else{
                            $timeout(function(){
                                s.invalidUser = false;
                                s.userDetailsEditMode = false;
                                s.firstTag = false;
                            }, 100)
                        }
                    }

                    s.setDefaultCard = true;
                    s.years = [];
                    var now = new Date();
                    for (var i = 0; i < 10; i++) {
                        s.years.push((now.getFullYear() + i) + "");
                    }


                    var safePayBussy = false;

                    function safePay(TransPaymentMethod, accountNumber) {

                        if (safePayBussy) {
                            return;
                        }
                        safePayBussy = true;
                        orderService.SafePay(s.user, s.savePay, s.card, s.shipping, s.cart, accountNumber, TransPaymentMethod)
                            .then(function (res) {

                                if (res.success) {
                                    rs.$broadcast('updatePaymentEnd', {
                                        paymentEnd: true,
                                        orderNumber: res.orderNumber,
                                        trackingNumber: res.paymentConfirmationNumber,
                                        cardNumber : + s.card.number + '',
                                        TransPaymentMethod : TransPaymentMethod
                                    });
                                    Helper.scrollPageUp();
                                    safePayBussy = false;
                                    return;
                                }
                                s.paymentEnd = false;
                            });
                    }

                    s.payNow_SafePay = function () {
                        var TransPaymentMethod = "SafePay";
                        var accountNumber = 843200971;
                        safePay(TransPaymentMethod, accountNumber);
                    }

                    s.payNow_masterCredit = function () {
                        var TransPaymentMethod = "MasterCredit";
                        var accountNumber = 112987298763;
                        safePay(TransPaymentMethod, accountNumber);
                    }

                    s.payNow_manual = function () {
                        s.card.number = '';
                        angular.forEach(s.CardNumber, function (fourDigits) {
                            s.card.number += fourDigits + "";
                        })
                        s.payNow_masterCredit()
                    }

                    s.shippingDetails_next = function () {
                        s.firstTag = false;
                    }

                    s.imgRadioButtonClicked = function (num) {
                        s.imgRadioButton = num;
                    }

                    s.paymentMethod_edit = function () {
                        s.noCards = true;
                    }
                }
            }
        }
    }]);
});


