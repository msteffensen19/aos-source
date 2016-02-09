/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('registerService', ['mini_soap', '$http', '$q', 'resHandleService',
        function (mini_soap, $http, $q) {
        // Return public API.
        return({
            register: register,
            getAllCountries: getAllCountries,
        });

        function getAllCountries() {

            var defer = $q.defer();
            var params = server.account.getAllCountries();

            Helper.loaderHandler(true);
            mini_soap.post(params.path, params.method).
            then(function(response){
                    var countries = [];
                    angular.forEach(response, function(country){
                        countries.push({
                            id: country.ID,
                            isoName: country.ISONAME,
                            name: country.NAME,
                            phonePrefix: country.PHONEPREFIX,
                        });
                    });
                    Helper.loaderHandler(false);
                    defer.resolve(countries);
                },
                function(response){
                    console.log(response);
                    Helper.loaderHandler(false);
                    defer.reject("Request failed! ");
                });
            return defer.promise;
        }

        function register(model) {

            var expectToReceive = {
                "accountType": 20,
                "address": model.address ,
                "allowOffersPromotion":  model.offers_promotion ? 'Y' : 'N',
                "cityName": model.city,
                "countryId": model.country.id || 4,
                "email": model.email,
                "firstName": model.firstName,
                "lastName": model.lastName,
                "loginName": model.username,
                "password": model.password,
                "phoneNumber": model.phoneNumber,
                "stateProvince": model.state,
                "zipcode": model.postalCode,
            }

            var defer = $q.defer();
            var params = server.account.register();

            Helper.loaderHandler(true);
            mini_soap.post(params.path, params.method, expectToReceive).
            then(function(response){
                    Helper.loaderHandler(false);
                    defer.resolve(response);
                },
                function(response){
                    console.log(response);
                    Helper.loaderHandler(false);
                    defer.reject("Request failed! ");
                });
            return defer.promise;
        }

    }]);
});