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

            function getAccountById() {

                var defer = $q.defer();
                var params = server.account.getAccountById();
                var user = $rootScope.userCookie;
                if (user && user.response && user.response.userId != -1) {

                    mini_soap.post(params.path, params.method, {
                        accountId: user.response.userId
                    })
                        .then(function (response) {
                            var user = {
                                "id": response.ID,
                                "lastName": response.LASTNAME,
                                "firstName": response.FIRSTNAME,
                                "loginName": response.LOGINNAME,
                                "country": response.COUNTRY,
                                "stateProvince": response.STATEPROVINCE,
                                "cityName": response.CITYNAME,
                                "address": response.ADDRESS,
                                "zipcode": response.ZIPCODE,
                                "phoneNumber": response.PHONENUMBER,
                                "email": response.EMAIL,
                                //"accountType": response.ACCOUNTTYPE,
                                //"allowOffersPromotion": response.ALLOWOFFERSPROMOTION,
                                //"internelUnsuccessfulLoginAttemts": response.INTERNALUNSUCCESSFULLOGINATTEMPTS,
                                //"internalUserBlockedFromLoginUtil": response.INTERNALUSERBLOCKEDFROMLOGINUNTIL,
                                //"internalLastSuccessSulLogin": response.INTERNALLASTSUCCESSSULLOGIN
                            }
                            defer.resolve(user);
                        },
                        function (response) {
                            console.log(response);
                            defer.reject("Request failed! (getAccountById)");
                        });
                }
                else{
                    defer.resolve(null);
                }

                return defer.promise;
            }
        }]);
});

