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
            });


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
                        url: server.catalog.getEmailConfiguration(),
                    }).
                    then(function (res) {
                        Helper.disableLoader();
                        Loger.Received(res);

                        config.emailAddressInLogin = res && res.data && res.data.parameterValue && res.data.parameterValue.toLowerCase() == "yes";

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


