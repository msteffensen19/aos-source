/**
 * Created by correnti on 29/11/2015.
 */
/**
 * Created by correnti on 29/11/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('registerCtrl', ['$scope', 'registerService', '$location',
        function ($scope, registerService, $location) {



            $scope.model = {
                username : 'SECorrenti',
                email : 'efi.correnti@hpe.com',
                password : '123456',
                confirm_password : '123456',
                firstName : 'Sergio',
                lastName : 'Ezequiel',
                phoneNumber : '0507856268',
                country : 'Israel',
                address : 'Mavo yahalom 3',
                city : 'Ramat gan',
                postalCode : '5421123',
                state : 'israel',
                offers_promotion : true,
            }




        }]);
});