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
            });

            function getConfiguration() {

                //var request = $http({
                //    "method": "get",
                //    "url": server.service.getConfiguration(),
                //});
                //return ( request.then(
                //    responseService.handleSuccess,
                //    responseService.handleError
                //));

                var defer = $q.defer();
                var params = server.service.getConfiguration();
                mini_soap.post(params.path, params.method).
                then(function (res) {
                        defer.resolve({
                            numberOfFiledLoginAttemptsBeforeBlocking: res.NUMBEROFFAILEDLOGINATTEMPTSBEFOREBLOCKING,
                            loginBlockingInterval: res.LOGINBLOCKINGINTERVALINMILLISECONDS,
                            emailAddressInLogin : res.EMAILADDRESSINLOGIN,
                            productInStockDefaultValue : res.PRODUCTINSTOCKDEFAULTVALUE,
                            userSecondWsdl : res.USERSECONDWSDL,
                        });
                    },
                    function (response) {
                        console.log(response);
                        defer.reject("Request failed! ");
                    });

                return defer.promise;

            }

            function login(user) {

                var defer = $q.defer();
                var params = server.account.login();
                mini_soap.post(params.path, params.method, user).
                then(function (response) {
                        console.log(response)
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


