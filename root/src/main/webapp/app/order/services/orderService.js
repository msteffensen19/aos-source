/**
 * Created by correnti on 10/01/2016.
 */

define(['./module'], function (services) {
    'use strict';
    services.service('orderService',['$rootScope', '$q', 'mini_soap',

        function ($rootScope, $q, mini_soap) {

            return ({
                getAccountById: getAccountById,
            });

            function getAccountById(userId) {

                var defer = $q.defer();
                var params = server.account.getAccountById();
                console.log(params)
                var user = $rootScope.userCookie;
                if (user && user.response && user.response.userId != -1) {

                    mini_soap.post(params.path, params.method, {
                        accountId: user.response.userId
                    })
                        .then(function (response) {
                            console.log(response)
                            defer.resolve(response);
                        },
                        function (response) {
                            console.log(response);
                            defer.reject("Request failed! (getAccountById)");
                        });
                }
                else{
                    updateCart(cart);
                }

                return defer.promise;
            }
        }]);
});

