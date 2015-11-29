/**
 * Created by correnti on 29/11/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('registerCtrl', ['$scope', 'registerService', '$location',
        function ($scope, registerService, $location) {



            $scope.model = {
                username : '',
                email : '',
                password : '',
                confirm_password : '',
                firstName : '',
                lastName : '',
                phoneNumber : '',
                country : '',
                address : '',
                city : '',
                postalCode : '',
                state : '',
                offers_promotion : '',
            }




        }]);
});