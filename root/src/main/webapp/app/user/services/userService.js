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
                nv_slowPage: nv_slowPage,
                haveConfiguration: haveConfiguration,
                facebookLogin : facebookLogin,
                getWarrantyProperties: getWarrantyProperties,
                getVoltageProperties: getVoltageProperties

            });


            function haveConfiguration(){
                var defer = $q.defer();
                if(appConfiguration && appConfiguration.slowPage){
                    defer.resolve();
                }
                else{
                    getConfiguration().then(function(){
                        defer.resolve();
                    });
                }
                return defer.promise;
            }

            function getCartIncrement() {
                return appConfiguration && appConfiguration.cartIncrement ? appConfiguration.cartIncrement : 0;
            }

            function getDuplicateProductPrice() {
                return appConfiguration && appConfiguration.duplicateProductPrice ? appConfiguration.duplicateProductPrice : 1;
            }

            function getWarrantyProperties() {
                return appConfiguration && appConfiguration.enableWarranty ?
                    {
                        enableWarranty: appConfiguration.enableWarranty == 2 ? true : false,
                        warrantyUrl: appConfiguration.warrantyUrl
                    } : {};
            }

            function getVoltageProperties() {
                return appConfiguration && appConfiguration.enableVoltage ?
                    {
                        enableVoltage: appConfiguration.enableVoltage == 2 ? true : false,
                        voltageUrl: appConfiguration.voltageUrl + '/vibesimple/rest/v1/protect-fields'
                    } : {};
            }

            function singOut() {

                var defer = $q.defer();
                var user = $rootScope.userCookie;
                if (user && user.response && user.response.userId != -1) {

                    var paramsToSend = server.account.accountLogout();
                    var expectToReceive = {
                        loginUser: user.response.userId,
                        base64Token: "Basic " + user.response.t_authorization,
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

            function nv_slowPage(){
                if(appConfiguration== undefined){
                    return;
                }else{
                    return appConfiguration.slowPage;
                }

            }

            function getConfiguration() {

                var config = {};

                var defer = $q.defer();
                if (appConfiguration) {
                    defer.resolve(appConfiguration);
                }
                else {
                    //Helper.enableLoader();
                    $http({
                        method: "get",
                        url: server.catalog.getConfigurations(),
                    }).
                    then(function (res) {
                        Loger.Received(res);

                        config.showChangePassword = true;
                        if (res && res.data && res.data.parameters) {
                            for (var i = 0; i < res.data.parameters.length; i++) {

                                switch (res.data.parameters[i].parameterName) {
                                    case "Email_in_login":
                                        config.emailAddressInLogin = res.data.parameters[i].parameterValue &&
                                            res.data.parameters[i].parameterValue.toLowerCase() == "yes";
                                        break;
                                    case "Show_slow_pages":
                                        config.slowPage = res.data.parameters[i].parameterValue &&
                                            res.data.parameters[i].parameterValue.toLowerCase() == "yes";
                                        break;
                                    case "Implement_DevOps_Process":
                                        config.showChangePassword = res.data.parameters[i].parameterValue &&
                                            res.data.parameters[i].parameterValue.toLowerCase() == "yes";
                                        break;
                                    case "Typos_on_order_payment":
                                        config.spellingMistakes = res.data.parameters[i].parameterValue &&
                                            res.data.parameters[i].parameterValue.toLowerCase() == "yes";
                                        break;
                                    case "Sum_added_to_cart":
                                        config.cartIncrement = res.data.parameters[i].parameterValue || "0";
                                        config.cartIncrement = parseInt(config.cartIncrement);
                                        if (!config.cartIncrement || config.cartIncrement < 0) {
                                            config.cartIncrement = 0;
                                        }
                                        break;
                                    case "Price_diffs_UI_vs_API":
                                        config.duplicateProductPrice = res.data.parameters[i].parameterValue &&
                                        res.data.parameters[i].parameterValue.toLowerCase() == "yes" ? 2 : 1;
                                        break;
                                    case "Enable_Warranty":
                                        config.enableWarranty = res.data.parameters[i].parameterValue &&
                                        res.data.parameters[i].parameterValue.toLowerCase() == "yes" ? 2 : 1;
                                        config.warrantyUrl = '';
                                        break;
                                    case "Enable_Voltage_Encryption":
                                        config.enableVoltage = res.data.parameters[i].parameterValue &&
                                        res.data.parameters[i].parameterValue.toLowerCase() == "yes" ? 2 : 1;
                                        break;
                                    case "Voltage_Service_URL":
                                        config.voltageUrl = res.data.parameters[i].parameterValue;
                                        break;
                                    // case "Warranty_location":
                                    //     config.warrantyUrl = res.data.parameters[i].parameterValue == 'default' ? '' : res.data.parameters[i].parameterValue;
                                    //     break;
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
                               // Helper.disableLoader();
                                //BUG: #161001 - Remove duplicate requests
                                $rootScope.$broadcast('configurationLoaded', {
                                    response: config
                                });
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
                // HTTPHeaders: {
                //     'Authorization': 'Basic ' + btoa(user.loginUser + ":" + user.loginPassword)
                // },
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

            function facebookLogin(){
                var defer = $q.defer();
                var paramsToSend = {};
                var expectToReceive = {}

                Helper.enableLoader();

                Loger.Params(expectToReceive, paramsToSend.method);
                $http({
                    method: "post",
                    url: server.account.facebookLogin(),
                    data: {},
                    headers: {
                        "content-type": "application/json",
                    },
                }).
                then(function (res) {
                    Helper.disableLoader();
                    Loger.Received(res);
                    defer.resolve(res.data)
                }, function (err) {
                    Helper.disableLoader();
                    Loger.Received(err);
                    defer.reject(err)
                })
                return defer.promise;
            }

        }]);
});


