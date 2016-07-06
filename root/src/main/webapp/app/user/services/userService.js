/**
 * Created by kubany on 11/11/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.factory('userService', ['$rootScope', '$q', '$http',

        function ($rootScope, $q, $http) {

            return ({
                login: login,
                getConfiguration: getConfiguration,
                singOut: singOut,
                getCartIncrement: getCartIncrement,
                getDuplicateProductPrice: getDuplicateProductPrice,
            });

            function getCartIncrement() {
                return appConfiguration && appConfiguration.cartIncrement ? appConfiguration.cartIncrement : 0;
            }

            function getDuplicateProductPrice() {
                return appConfiguration && appConfiguration.duplicateProductPrice ? appConfiguration.duplicateProductPrice : 1;
            }

            function singOut() {

                var defer = $q.defer();
                var user = $rootScope.userCookie;
                if (user && user.response && user.response.userId != -1) {

                    var paramsToSend = server.account.accountLogout();
                    var expectToReceive = {
                        loginUser: user.response.userId,
                        base64Token: "Bearer " + user.response.token,
                    }

                    Helper.enableLoader();

                    Loger.Params(expectToReceive, paramsToSend.method);
                    $.soap({
                        url: paramsToSend.path,
                        method: paramsToSend.method,
                        namespaceURL: server.namespaceURL,
                        SOAPAction: server.namespaceURL + paramsToSend.method,
                        data: expectToReceive,
                        success: function (soapResponse) {
                            var response = soapResponse.toJSON(paramsToSend.response);
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
                else {
                    defer.resolve("no user");
                }
                return defer.promise;
            }


            var appConfiguration;

            function getConfiguration() {

                var config = {};

                var defer = $q.defer();
                if (appConfiguration) {
                    defer.resolve(appConfiguration);
                }
                else {
                    Helper.enableLoader();
                    $http({
                        method: "get",
                        url: server.catalog.getConfigurations(),
                    }).
                    then(function (res) {
                        Loger.Received(res);

                        if (res && res.data && res.data.parameters) {
                            for (var i = 0; i < res.data.parameters.length; i++) {

                                switch (res.data.parameters[i].parameterName) {
                                    case "Email_address_in_login":
                                        config.emailAddressInLogin = res.data.parameters[i].parameterValue &&
                                            res.data.parameters[i].parameterValue.toLowerCase() == "yes";
                                        break;
                                    case "Slow_Page":
                                        config.slowPage = res.data.parameters[i].parameterValue &&
                                            res.data.parameters[i].parameterValue.toLowerCase() == "yes";
                                        break;
                                    case "Spelling_mistakes_in_order_payment_page":
                                        config.spellingMistakes = res.data.parameters[i].parameterValue &&
                                            res.data.parameters[i].parameterValue.toLowerCase() == "yes";
                                        break;
                                    case "Sum_to_add_to_cart_calculation":
                                        config.cartIncrement = res.data.parameters[i].parameterValue || "0";
                                        config.cartIncrement = parseInt(config.cartIncrement);
                                        if (!config.cartIncrement || config.cartIncrement < 0) {
                                            config.cartIncrement = 0;
                                        }
                                        break;
                                    case "Different_price_in_UI_and_API":
                                        config.duplicateProductPrice = res.data.parameters[i].parameterValue &&
                                        res.data.parameters[i].parameterValue.toLowerCase() == "yes" ? 2 : 1;
                                        break;
                                }
                            }
                        }

                        var paramsToSend = server.catalog.getAccountConfiguration();
                        $.soap({
                            url: paramsToSend.path,
                            method: paramsToSend.method,
                            namespaceURL: server.namespaceURL,
                            SOAPAction: server.namespaceURL + paramsToSend.method,
                            data: {},
                            success: function (soapResponse) {
                                var json = soapResponse.toJSON(paramsToSend.response);
                                config.allowUserConfiguration = json.allowUserConfiguration;
                                config.loginBlockingIntervalInSeconds = json.loginBlockingIntervalInSeconds;
                                config.numberOfFailedLoginAttemptsBeforeBlocking = json.numberOfFailedLoginAttemptsBeforeBlocking;
                                config.productInStockDefaultValue = json.productInStockDefaultValue;
                                config.userLoginTimeOut = json.userLoginTimeout;
                                config.userSecondWSDL = json.userSecondWsdl;
                                appConfiguration = config;
                                Helper.disableLoader();
                                defer.resolve(config);
                            },
                            error: function (response) {
                                Loger.Received(response);
                                Helper.disableLoader();
                                defer.reject("Request failed! ");
                            },
                            enableLogging: true
                        });

                    }, function (err) {
                        Helper.disableLoader();
                        Loger.Received(err);
                        defer.reject("probl.")
                    })

                }
                return defer.promise;
            }

            function login(user) {

                var defer = $q.defer();
                var params = server.account.login();

                Helper.enableLoader();

                Loger.Params(user, params.method);
                $.soap({
                    url: params.path,
                    method: params.method,
                    namespaceURL: server.namespaceURL,
                    SOAPAction: server.namespaceURL + params.method,
                    data: user,
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

        }]);
});


