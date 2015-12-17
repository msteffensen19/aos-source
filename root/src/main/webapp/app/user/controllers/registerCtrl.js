/**
 * Created by correnti on 29/11/2015.
 */
/**
 * Created by correnti on 29/11/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('registerCtrl', ['$scope', 'registerService', '$location', '$timeout',
        function ($scope, registerService, $location, $timeout) {

            $scope.formSender = false;
            $scope.isFormValid = false;
            $scope.countries = {};
            $scope.registerAnswer = {
                message: '',
                class: 'invalid'
            }

            /*
             $scope.model = { username : 'SECorrenti', email : 'efi.correnti@hpe.com',
             password : 'Aa123456', confirm_password : 'Aa123456', firstName : 'Sergio', lastName : 'Ezequiel',
             phoneNumber : '0507856268', country : {"id":0, "name":"", "isoName":"", "phonePrefix":0} , address : 'Mavo yahalom 3', city : 'Ramat gan',
             postalCode : '5421123', state : 'israel', offers_promotion : true }
             */

            $scope.model = {
                username : '', email : '', password : '', confirm_password : '',
                firstName : '', lastName : '', phoneNumber : '', country : {} , address : '',
                city : '', postalCode : '', state : '', offers_promotion : true,
            }



            $scope.$watch("registerForm.$valid", function (newValue) {
                $scope.isFormValid = newValue;
            });


            registerService.getAllCountries().then(function(response){
                $scope.countries = response;
            });


            $scope.register = function(){

                $scope.formSender = true;
                if($scope.isFormValid && !$('#registerButton').hasClass('opacityButtonDisable'))
                {
                    registerService.register($scope.model).then(function(response){
                        $scope.registerAnswer.message = response.reason,
                        $scope.registerAnswer.class = response.success ? 'valid' : 'invalid';
                        $timeout(function(){
                            $scope.registerAnswer = { message: '', class: 'invalid' }
                            window.history.back();
                        }, 4000)
                    });

                }
            }

            $(document).on("keydown", function(event) {
                if (event.keyCode == 13) {
                    $scope.register();
                    return false;
                }
            });
            Main.addAnimPlaceholderEventListener();
        }])

        .directive("secCompare", function(){
            return {
                require: 'ngModel',
                link: function (scope, elem, attrs, ctrl) {
                    var firstPassword = '#' + attrs.secCompare;
                    elem.on('keyup', function () {
                        scope.$apply(function () {
                            console.info(elem.val() === $(firstPassword).val());
                            ctrl.$setValidity('secCompare', elem.val() === $(firstPassword).val());
                        });
                    });
                }
            }
        })
    ;
});