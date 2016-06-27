/**
 * Created by kubany on 11/11/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.factory('userService', ['$rootScope', '$q', '$http', "resHandleService", "mini_soap", '$timeout',

        function ($rootScope, $q, $http, responseService, mini_soap, $timeout) {

            return ({
                login: login,
                getConfiguration: getConfiguration,
                singOut: singOut,
                getCartIncrement: getCartIncrement,
            });

            function getCartIncrement(){
                return appConfiguration && appConfiguration.cartIncrement ? appConfiguration.cartIncrement : 0;
            }

            function singOut() {

                var defer = $q.defer();
                var user = $rootScope.userCookie;
                if (user && user.response && user.response.userId != -1) {

                    var params = server.account.accountLogout();
                    var expectToReceive = {
                        loginUser: user.response.userId,
                        base64Token: "Bearer " + user.response.token,
                    }

                    Helper.enableLoader();
                    Loger.Params(expectToReceive, params.method);
                    mini_soap.post(params.path, params.method, expectToReceive).
                    then(function (response) {
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.resolve(response);
                        },
                        function (response) {
                            Helper.disableLoader();
                            Loger.Received(response);
                            defer.reject("Request failed! ");
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
                        Helper.disableLoader();
                        Loger.Received(res);

                        if(res && res.data && res.data.parameters) {
                            for (var i = 0; i < res.data.parameters.length; i++) {
                                switch (res.data.parameters[i].parameterName) {
                                    case "Email_address_in_login":
                                        config.emailAddressInLogin = res.data.parameters[i].parameterValue &&
                                            res.data.parameters[i].parameterValue.toLowerCase() == "yes";
                                        break;
                                    case "Sum_to_add_to_cart_calculation":
                                        config.cartIncrement = res.data.parameters[i].parameterValue || "0";
                                        config.cartIncrement = parseInt(config.cartIncrement);
                                        if(!config.cartIncrement || config.cartIncrement < 0){
                                            config.cartIncrement = 0;
                                        }
                                        break;
                                }
                            }
                        }

                        var params = server.catalog.getAccountConfiguration();
                        mini_soap.post(params.path, params.method).
                        then(function (response) {
                                Loger.Received(response);
                                config.allowUserConfiguration = response.ALLOWUSERCONFIGURATION.toLowerCase() == "true";
                                config.loginBlockingIntervalInSeconds = parseInt(response.LOGINBLOCKINGINTERVALINSECONDS);
                                config.numberOfFailedLoginAttemptsBeforeBlocking = parseInt(response.NUMBEROFFAILEDLOGINATTEMPTSBEFOREBLOCKING);
                                config.productInStockDefaultValue = parseInt(response.PRODUCTINSTOCKDEFAULTVALUE);
                                config.userLoginTimeOut = parseInt(response.USERLOGINTIMEOUT);
                                config.userSecondWSDL = response.USERSECONDWSDL.toLowerCase() == "true";
                                appConfiguration = config;
                                defer.resolve(config);
                            },
                            function (response) {
                                Loger.Received(response);
                                defer.reject("Request failed! ");
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
                mini_soap.post(params.path, params.method, user).
                then(function (res) {

                        var response = {
                            userId: parseInt(res.USERID),
                            reason: res.REASON,
                            success: res.SUCCESS == "true",
                            token: res.TOKEN
                        }

                        Loger.Received(response);
                        Helper.disableLoader();
                        defer.resolve(response);
                    },
                    function (response) {
                        Loger.Received(response);
                        Helper.disableLoader();
                        defer.reject("Request failed! ");
                    });

                return defer.promise;
            }

        }]);
});


