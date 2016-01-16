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
            return responseService.getAllCountries();
        }

        function register(model) {
            console.log("model")
            console.log(model)
            console.log(JSON.stringify(model))
            console.log(model.country)
            console.log(model.country.id)
            console.log(model.country.id || 0 )

            var expectToReceive = {
                "address": model.address ,
                "allowOffersPromotion":  model.offers_promotion ? 'Y' : 'N',
                "appUserType": 20,
                "cityName": model.city,
                "country": model.country.id || 0 ,
                "email": model.email,
                "firstName": model.firstName,
                "id": 0,
                "internalLastSuccesssulLogin": 0,
                "internalUnsuccessfulLoginAttempts": 0,
                "internalUserBlockedFromLoginUntil": 0,
                "lastName": model.lastName,
                "loginName": model.username,
                "password": model.password,
                "phoneNumber": model.phoneNumber,
                "stateProvince": model.state,
                "zipcode": model.postalCode,
            }
            console.log(expectToReceive)
            console.log(JSON.stringify(expectToReceive))

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

            //var request = $http({
            //    method: "post",
            //    url: server.account.register(),
            //    data: expectToReceive,
            //});
            //return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

    }]);
});