/**
 * Created by correnti on 28/01/2016.
 */

define(['./module'], function (services) {
    'use strict';
    services.service('accountService', ['$rootScope', '$q',

        function ($rootScope, $q) {

            return {

                getAccountDetails: function () {

                    var defer = $q.defer();
                    var params = server.account.getAccountById_new();
                    var user = $rootScope.userCookie;

                    Helper.enableLoader();

                    $.soap({
                        url: params.path,
                        method: params.method,
                        namespaceURL: server.namespaceURL,
                        SOAPAction: server.namespaceURL + params.method,
                        data: {
                            accountId: user.response.userId
                        },
                        success: function (soapResponse) {
                            var response = soapResponse.toJSON(params.response);
                            if(response && response.AccountResponse && response.AccountResponse.mobilePhone){
                                response.AccountResponse.mobilePhone = response.AccountResponse.mobilePhone + ""; //warning: this field can be a integer, must be converted to string/
                            }
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.resolve(response.AccountResponse);
                        },
                        error: function (response) {
                            Loger.Received(response);
                            Helper.disableLoader();
                            defer.reject("Request failed! (getAccountDetails)");
                        },
                        enableLogging: true
                    });

                    return defer.promise;
                },

                getShippingDetails: function () {

                    var defer = $q.defer();
                    var params = server.account.getAddressesByAccountId();
                    var user = $rootScope.userCookie;
                    Helper.enableLoader(10);

                    $.soap({
                        url: params.path,
                        method: params.method,
                        namespaceURL: server.namespaceURL,
                        SOAPAction: server.namespaceURL + params.method,
                        data: {
                            accountId: user.response.userId
                        },
                        success: function (soapResponse) {
                            var response = soapResponse.toJSON(params.response);
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.resolve(response);
                        },
                        error: function (response) {
                            Loger.Received(response);
                            Helper.disableLoader();
                            defer.reject("Request failed! (getShippingDetails)");
                        },
                        enableLogging: true
                    });

                    return defer.promise;
                },

                getAccountPaymentPreferences: function () {

                    var defer = $q.defer();
                    var params = server.account.getAccountPaymentPreferences();
                    var user = $rootScope.userCookie;

                    Helper.enableLoader(10);


                    $.soap({
                        url: params.path,
                        method: params.method,
                        namespaceURL: server.namespaceURL,
                        SOAPAction: server.namespaceURL + params.method,
                        data: {
                            accountId: user.response.userId
                        },
                        success: function (soapResponse) {
                            var response = soapResponse.toJSON(params.response);

                            var masterCredit = null;
                            var safePay = null;
                            var MCard;
                            var SPay;
                            if (response != null) {

                                if(response.preference){
                                    response = response.preference;
                                }
                                if (response.paymentMethod + "" == "20") {
                                    MCard = response;
                                }
                                if (response.paymentMethod + "" == "10") {
                                    SPay = response;
                                }
                                if (response.length && response.length == 2) {
                                    MCard = response[0].paymentMethod + "" == "20" ? response[0] :
                                        response[1].paymentMethod + "" == "20" ? response[1] : null;
                                    SPay = response[0].paymentMethod + "" == "10" ? response[0] :
                                        response[1].paymentMethod + "" == "10" ? response[1] : null;
                                }
                                if (MCard != null) {
                                    masterCredit = {
                                        "id": MCard.preferenceId,
                                        "cardNumber": MCard.cardNumber,
                                        "cvvNumber": MCard.cvvNumber,
                                        "expirationDate": MCard.expirationDate,
                                        "paymentMethod": MCard.paymentMethod,
                                        "customername": MCard.customerName
                                    }
                                }
                                if (SPay != null) {
                                    safePay = {
                                        "id": SPay.preferenceId,
                                        "safepayUsername": SPay.safePayUsername,
                                        "paymentMethod": SPay.paymentMethod,
                                        "safepayPassword": SPay.safePayPassword,
                                    }
                                }
                            }
                            Helper.disableLoader();
                            Loger.Received({masterCredit: masterCredit, safePay: safePay});
                            defer.resolve({masterCredit: masterCredit, safePay: safePay});
                        },
                        error: function (response) {
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.reject("Request failed! (getAccountPaymentPreferences)");
                        },
                        enableLogging: true
                    });

                    return defer.promise;
                },

                changeUserPassword: function (accountId, passwords) {

                    var defer = $q.defer();
                    if (passwords.new == '' || passwords.old == '' || passwords.confirm_new == '') {
                        defer.resolve({success: 'true'});
                    }
                    else {
                        var expectToReceive = {
                            accountId: accountId,
                            newPassword: passwords.new,
                            oldPassword: passwords.old,
                            base64Token: $rootScope.userCookie.response.token,
                        }
                        var params = server.account.changePassword();
                        Helper.enableLoader();

                        $.soap({
                            url: params.path,
                            method: params.method,
                            namespaceURL: server.namespaceURL,
                            SOAPAction: server.namespaceURL + params.method,
                            data: expectToReceive,
                            success: function (soapResponse) {
                                var response = soapResponse.toJSON(params.response);
                                Helper.disableLoader();
                                Loger.Received(response);
                                defer.resolve(response.StatusMessage);
                            },
                            error: function (response) {
                                Loger.Received(response);
                                Helper.disableLoader();
                                defer.reject("Request failed! ");
                            },
                            enableLogging: true
                        });
                    }
                    return defer.promise;
                },

                accountUpdate: function (accountDetails) {
                    var defer = $q.defer();
                    var expectToReceive = {
                        lastName: accountDetails.lastName,
                        firstName: accountDetails.firstName,
                        accountId: accountDetails.id,
                        countryId: accountDetails.countryId,
                        stateProvince: accountDetails.stateProvince,
                        cityName: accountDetails.cityName,
                        address: accountDetails.homeAddress,
                        zipcode: accountDetails.zipcode,
                        phoneNumber: accountDetails.mobilePhone,
                        email: accountDetails.email,
                        accountType: 20,
                        allowOffersPromotion: accountDetails.allowOffersPromotion,
                    }
                    var params = server.account.accountUpdate();
                    Loger.Params(expectToReceive, params.method);

                    Helper.enableLoader();

                    $.soap({
                        url: params.path,
                        method: params.method,
                        namespaceURL: server.namespaceURL,
                        SOAPAction: server.namespaceURL + params.method,
                        data: expectToReceive,
                        success: function (soapResponse) {
                            var response = soapResponse.toJSON(params.response);
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.resolve(response.StatusMessage);
                        },
                        error: function (response) {
                            Loger.Received(response);
                            Helper.disableLoader();
                            defer.reject("Request failed! ");
                        },
                        enableLogging: true
                    });

                    return defer.promise;
                },

                addMasterCreditMethod: function (card) {

                    var defer = $q.defer();
                    var user = $rootScope.userCookie;

                    var expectToReceive = {
                        cardNumber: "4886" + card.number,
                        expirationDate: card.expirationDate.month + card.expirationDate.year,
                        cvvNumber: card.cvv,
                        customerName: card.name,
                        accountId: user.response.userId,
                    }

                    var params = server.account.addMasterCreditMethod();
                    Loger.Params(expectToReceive, params.method);

                    Helper.enableLoader();
                    $.soap({
                        url: params.path,
                        method: params.method,
                        namespaceURL: server.namespaceURL,
                        SOAPAction: server.namespaceURL + params.method,
                        data: expectToReceive,
                        success: function (soapResponse) {
                            var response = soapResponse.toJSON(params.response);
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.resolve(response.StatusMessage);
                        },
                        error: function (response) {
                            Loger.Received(response);
                            Helper.disableLoader();
                            defer.reject("Request failed! ");
                        },
                        enableLogging: true
                    });

                    return defer.promise;
                },

                addSafePayMethod: function (safePay) {

                    var defer = $q.defer();
                    var user = $rootScope.userCookie;
                    var expectToReceive = {
                        safePayUsername: safePay.username,
                        accountId: user.response.userId,
                        safePayPassword: safePay.password
                    }

                    var params = server.account.addSafePayMethod();
                    Loger.Params(expectToReceive, params.method);

                    Helper.enableLoader();
                    $.soap({
                        url: params.path,
                        method: params.method,
                        namespaceURL: server.namespaceURL,
                        SOAPAction: server.namespaceURL + params.method,
                        data: expectToReceive,
                        success: function (soapResponse) {
                            var response = soapResponse.toJSON(params.response);
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.resolve(response.StatusMessage);
                        },
                        error: function (response) {
                            Loger.Received(response);
                            Helper.disableLoader();
                            defer.reject("Request failed! ");
                        },
                        enableLogging: true
                    });
                    return defer.promise;
                },

                updateSafePayMethod: function (safePay) {

                    var accountId = $rootScope.userCookie.response.userId;
                    var expectToReceive = {
                        userId: accountId,
                        safePayUsername: safePay.username,
                        safePayPassword: safePay.password,
                        referenceId: "1234567890",
                    }

                    var defer = $q.defer();
                    var params = server.account.updateSafePayMethod();
                    Loger.Params(expectToReceive, params.method);

                    Helper.enableLoader();
                    $.soap({
                        url: params.path,
                        method: params.method,
                        namespaceURL: server.namespaceURL,
                        SOAPAction: server.namespaceURL + params.method,
                        data: expectToReceive,
                        success: function (soapResponse) {
                            var response = soapResponse.toJSON(params.response);
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.resolve(response.StatusMessage);
                        },
                        error: function (response) {
                            Loger.Received(response);
                            Helper.disableLoader();
                            defer.reject("Request failed! ");
                        },
                        enableLogging: true
                    });

                    return defer.promise;
                },

                updateMasterCreditMethod: function (card) {

                    var accountId = $rootScope.userCookie.response.userId;
                    var expectToReceive = {
                        userId: accountId,
                        cardNumber: "4886" + card.number,
                        expirationDate: card.expirationDate.month + card.expirationDate.year,
                        cvvNumber: card.cvv,
                        customerName: card.name,
                        referenceId: "1234567890",
                    }

                    var defer = $q.defer();
                    var params = server.account.updateMasterCreditMethod();
                    Loger.Params(expectToReceive, params.method);

                    Helper.enableLoader();
                    $.soap({
                        url: params.path,
                        method: params.method,
                        namespaceURL: server.namespaceURL,
                        SOAPAction: server.namespaceURL + params.method,
                        data: expectToReceive,
                        success: function (soapResponse) {
                            var response = soapResponse.toJSON(params.response);
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.resolve(response.StatusMessage);
                        },
                        error: function (response) {
                            Loger.Received(response);
                            Helper.disableLoader();
                            defer.reject("Request failed! ");
                        },
                        enableLogging: true
                    });

                    return defer.promise;
                },

                updatePrefferedPaymentMethod: function (paymentMethod) {

                    var user = $rootScope.userCookie;
                    var expectToReceive = {
                        accountId: user.response.userId,
                        paymentMethod: paymentMethod,
                    }

                    var defer = $q.defer();
                    var params = server.account.paymentMethodUpdate();
                    Loger.Params(expectToReceive, params.method);

                    Helper.enableLoader();

                    $.soap({
                        url: params.path,
                        method: params.method,
                        namespaceURL: server.namespaceURL,
                        SOAPAction: server.namespaceURL + params.method,
                        data: expectToReceive,
                        success: function (soapResponse) {
                            var response = soapResponse.toJSON(params.response);
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.resolve(response.StatusMessage);
                        },
                        error: function (response) {
                            Loger.Received(response);
                            Helper.disableLoader();
                            defer.reject("Request failed! ");
                        },
                        enableLogging: true
                    });

                    return defer.promise;
                }
            };
        }]);
});

