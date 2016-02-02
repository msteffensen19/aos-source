/**
 * Created by correnti on 28/01/2016.
 */

define(['./module'], function (services) {
    'use strict';
    services.service('accountService',['$rootScope', '$q', 'mini_soap', '$http', '$filter', 'productsCartService',

        function ($rootScope, $q, mini_soap, $http, $filter, productsCartService) {
            return {


                getAccountDetails: function(){

                    var defer = $q.defer();
                    var params = server.account.getAccountById_new();
                    var user = $rootScope.userCookie;
                    mini_soap.post(params.path, params.method, {
                            accountId: user.response.userId
                        })
                        .then(function (response) {
                                var user = {
                                    "id": response.ID,
                                    "lastName": response.LASTNAME,
                                    "firstName": response.FIRSTNAME,
                                    "loginName": response.LOGINNAME,
                                    "countryId": response.COUNTRYID,
                                    "countryName": response.COUNTRYNAME,
                                    "countryIsoName": response.COUNTRYISONAME,
                                    "stateProvince": response.STATEPROVINCE,
                                    "cityName": response.CITYNAME,
                                    "homeAddress": response.HOMEADDRESS,
                                    "zipcode": response.ZIPCODE,
                                    "mobilePhone": response.MOBILEPHONE,
                                    "email": response.EMAIL,
                                    defaultPaymentMethodId: response.DEFAULTPAYMENTMETHODID,
                                    allowOffersPromotion: response.ALLOWOFFERSPROMOTION == "true",
                                    internalUnsuccessfulLoginAttempts: response.INTERNALUNSUCCESSFULLOGINATTEMPTS,
                                    internalUserBlockedFromLoginUntil: response.INTERNALUSERBLOCKEDFROMLOGINUNTIL,
                                    internalLastSuccesssulLogin: response.INTERNALLASTSUCCESSSULLOGIN,
                                }

                                defer.resolve(user);
                            },
                            function (response) {
                                console.log(response);
                                defer.reject("Request failed! (getAccountDetails)");
                            });

                    return defer.promise;
                },

                getShippingDetails: function() {

                    var defer = $q.defer();
                    var params = server.account.getAddressesByAccountId();
                    var user = $rootScope.userCookie;
                    mini_soap.post(params.path, params.method, {
                            accountId: user.response.userId
                        })
                        .then(function (response) {
                                var shippingDetails = null;
                                if (response.ID) {
                                    shippingDetails = {
                                        "id": response.ID,
                                        "address_line1": response.ADDRESS_LINE1,
                                        "address_line2": response.ADDRESS_LINE2,
                                        "country": response.COUNTRY,
                                        "postalCode": response.POSTALCODE,
                                        "state": response.STATE,
                                        "userId": response.USERID,
                                    }
                                }
                                defer.resolve(shippingDetails);
                            },
                            function (response) {
                                console.log(response);
                                defer.reject("Request failed! (getAccountDetails)");
                            });

                    return defer.promise;
                },

                getAccountPaymentPreferences: function(){

                    var defer = $q.defer();
                    var params = server.account.getAccountPaymentPreferences();
                    var user = $rootScope.userCookie;
                    mini_soap.post(params.path, params.method, {
                            accountId: user.response.userId
                        })
                        .then(function (response) {
                                var paymentPreferences = null;
                                if(response.ID) {
                                    paymentPreferences = {
                                        "id": response.ID,
                                        "cardNumber": response.CARDNUMBER,
                                        "custumerName": response.CUSTUMERNAME,
                                        "cvvNumber": response.CVVNUMBER,
                                        "expirationDate": response.EXPIRATIONDATE,
                                        "paymentMethod": response.PAYMENTMETHOD,
                                    }
                                }
                                defer.resolve(paymentPreferences);
                            },
                            function (response) {
                                console.log(response);
                                defer.reject("Request failed! (getAccountDetails)");
                            });

                    return defer.promise;
                },

                accountUpdate : function(accountDetails){
                    var expectToReceive = {
                            lastName:accountDetails.lastName,
                            firstName:accountDetails.firstName,
                            accountId:accountDetails.id,
                            countryId:accountDetails.countryId,
                            stateProvince:accountDetails.stateProvince,
                            cityName:accountDetails.cityName,
                            address:accountDetails.homeAddress,
                            zipcode:accountDetails.zipcode,
                            phoneNumber:accountDetails.mobilePhone,
                            email: accountDetails.email,
                            accountType: 20,
                            allowOffersPromotion: accountDetails.allowOffersPromotion,
                    }
                    var defer = $q.defer();
                    var params = server.account.accountUpdate();

                    mini_soap.post(params.path, params.method, expectToReceive).
                    then(function(response){
                            defer.resolve(response);
                        },
                        function(response){
                            console.log(response);
                            defer.reject("Request failed! ");
                        });
                    return defer.promise;


                },

                updateMasterCreditMethod : function(card){

                    var expectToReceive = {
                        cardNumber: "4886" + card.number,
                        expirationDate: card.expirationDate.month + card.expirationDate.year,
                        cvvNumber: card.cvv,
                        customerName: card.name,
                        referenceId: "??????",
                    }
                    var defer = $q.defer();
                    var params = server.account.updateMasterCreditMethod();
                    mini_soap.post(params.path, params.method, expectToReceive).
                    then(function(response){
                            defer.resolve(response);
                        },
                        function(response){
                            console.log(response);
                            defer.reject("Request failed! ");
                        });
                    return defer.promise;
                },

                updateSafePayMethod: function(savePay){

                    var expectToReceive = {
                        safePayUsername:safePay.username,
                        referenceId: "???????"
                    }
                    var defer = $q.defer();
                    var params = server.account.updateSafePayMethod();
                    mini_soap.post(params.path, params.method, expectToReceive).
                    then(function(response){
                            defer.resolve(response);
                        },
                        function(response){
                            console.log(response);
                            defer.reject("Request failed! ");
                        });
                    return defer.promise;
                },



            };
        }]);
});

