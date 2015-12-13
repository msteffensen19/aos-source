/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('registerService', ['$http', '$q', 'resHandleService', function ($http, $q, responseService) {
        // Return public API.
        return({
            register: register,
            getAllCountries: getAllCountries,
        });


        function getAllCountries(model) {
            return responseService.getAllCountries();
        }

        function register(model) {
            console.log("model")
            console.log(model)

            var expectToReceive = {
                "address": model.address ,
                "allowOffersPromotion":  model.offers_promotion ? 'Y' : 'N',
                "appUserType": 10,
                "cityName": model.city,
                "country": model.country.id,
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

            var request = $http({
                method: "post",
                url: server.account.register(model),
                data: JSON.stringify(expectToReceive),
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

    }]);
});