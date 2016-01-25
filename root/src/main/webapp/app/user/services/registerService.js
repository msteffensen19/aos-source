/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('registerService', ['mini_soap', '$http', '$q', 'resHandleService',
        function (mini_soap, $http, $q, responseService) {
        // Return public API.
        return({
            register: register,
            getAllCountries: getAllCountries,
        });


        function getAllCountries() {
            //return responseService.getAllCountries();

            var defer = $q.defer();
            var params = server.account.getAllCountries();

            mini_soap.post(params.path, params.method).
            then(function(response){
                    console.log("response")
                    console.log(response)
                    console.log("response")
                    var countries = [];
                    angular.forEach(response, function(country){
                        countries.push({
                            id: country.ID,
                            isoName: country.ISONAME,
                            name: country.NAME,
                            phonePrefix: country.PHONEPREFIX,
                        });
                    });
                    defer.resolve(countries);
                },
                function(response){
                    console.log(response);
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

            mini_soap.post(params.path, params.method, expectToReceive).
            then(function(response){
                    defer.resolve(response);
                },
                function(response){
                    console.log(response);
                    defer.reject("Request failed! ");
                });
            return defer.promise;
        }

    }]);
});