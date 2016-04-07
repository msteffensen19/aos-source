/**
 * Created by correnti on 03/01/2016.
 */


define(['./module'], function (directives) {
    'use strict';
    directives.directive('userAreLogin', ['$rootScope', '$templateCache', 'orderService',
        'registerService', '$timeout', 'accountService',
        function (rs, $templateCache, orderService, registerService, $timeout, accountService) {
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

                    var alreadyHaveMasterCreditCart = s.card.number.length > 0;
                    var alreadyHaveSafePayCart = s.savePay.username.length > 0;

                    s.firstTag = true;
                    s.imgRadioButton = s.accountDetails.defaultPaymentMethodId + "" == "20" ? 2
                        : s.savePay.username.length > 0 ? 1
                        : s.card.number.length > 0  ? 2 : 1;

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

                    s.cardNumber

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

                    s.accountUpdate = function(){
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
                        if(s.saveSafePay){
                            if (!alreadyHaveSafePayCart) {
                                accountService.addSafePayMethod(s.savePay)
                            }
                            else {
                                accountService.updateSafePayMethod(s.savePay)
                            }
                        }
                        var TransPaymentMethod = "SafePay";
                        var accountNumber = 843200971;
                        safePay(TransPaymentMethod, accountNumber);

                    }


                    s.payNow_masterCredit = function () {
                        if(s.saveMasterCredit) {
                            if (!alreadyHaveMasterCreditCart) {
                                accountService.addMasterCreditMethod(s.card)
                            }
                            else {
                                accountService.updateMasterCreditMethod(s.card);
                            }
                        }
                        var TransPaymentMethod = "MasterCredit";
                        var accountNumber = 112987298763;
                        safePay(TransPaymentMethod, accountNumber);
                    }

                    s.payNow_manual = function () {
                        s.payNow_masterCredit()
                    }

                    s.shippingDetails_next = function () {
                        s.firstTag = false;
                    }

                    s.imgRadioButtonClicked = function (num) {
                        s.imgRadioButton = num;
                        s.showMasterCart = s.noCards;
                    }

                    s.paymentMethod_edit = function () {
                        s.noCards = true;
                    }

                    s.Back_to_shipping_details = function(){
                        s.firstTag = true;
                        s.showMasterCart = s.noCards;
                    }
                }
            }
        }
    }]);
});


