/**
 * Created by kubany on 11/11/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.factory('userService',['$rootScope', '$q', '$http', "resHandleService", "mini_soap",

        function ($rootScope, $q, $http, responseService, mini_soap) {

            return ({
                login: login,
                getConfiguration: getConfiguration,
                singOut: singOut,
            });


            function singOut(){

                var defer = $q.defer();
                var user = $rootScope.userCookie;
                if (user && user.response && user.response.userId != -1) {
                    var params = server.account.accountLogout();
                    var expectToReceive = {
                        loginUser: user.response.userId,
                        loginPassword: "Aa123",
                    }
                    l(expectToReceive)
                    mini_soap.post(params.path, params.method, expectToReceive).
                    then(function (response) {
                            defer.resolve(response);
                        },
                        function (response) {
                            console.log(response);
                            defer.reject("Request failed! ");
                        });
                }
                else{
                    defer.resolve("no user");
                }
                return defer.promise;
            }


            var appConfiguration;
            function getConfiguration() {

                var defer = $q.defer();
                if(appConfiguration){
                    defer.resolve(appConfiguration);
                }
                else {
                    var params = server.service.getConfiguration();
                    mini_soap.post(params.path, params.method).
                    then(function (res) {
                            appConfiguration = {
                                numberOfFiledLoginAttemptsBeforeBlocking: res.NUMBEROFFAILEDLOGINATTEMPTSBEFOREBLOCKING,
                                loginBlockingInterval: res.LOGINBLOCKINGINTERVALINMILLISECONDS,
                                emailAddressInLogin: res.EMAILADDRESSINLOGIN,
                                productInStockDefaultValue: res.PRODUCTINSTOCKDEFAULTVALUE,
                                userSecondWsdl: res.USERSECONDWSDL,
                                userLoginTimeout: res.USERLOGINTIMEOUT,
                            }
                            defer.resolve(appConfiguration);
                        },
                        function (response) {
                            console.log(response);
                            defer.reject("Request failed! ");
                        });
                }
                return defer.promise;
            }

            function login(user) {

                var defer = $q.defer();
                var params = server.account.login();
                mini_soap.post(params.path, params.method, user).
                then(function (response) {
                        defer.resolve(response);
                    },
                    function (response) {
                        console.log(response);
                        defer.reject("Request failed! ");
                    });

                return defer.promise;
            }

        }]);
});


