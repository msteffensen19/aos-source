/**
 * Created by correnti on 29/11/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('registerCtrl', ['$scope', 'registerService', '$location', '$timeout', '$filter',
        function (s, registerService, $location, $timeout, $filter) {

            s.countries = {};
            s.registerAnswer = {
                message: '',
                class: 'invalid'
            }

            registerService.getAllCountries().then(function (countries) {
                s.countries = countries;
            });

            s.model = {
                username: '', email: '', password: '', confirm_password: '',
                firstName: '', lastName: '', phoneNumber: '', country: {}, address: '',
                city: '', postalCode: '', state: '', offers_promotion: true,
            }


            s.register = function () {

                registerService.register(s.model).then(function (response) {
                    s.registerAnswer.message = response.REASON || $filter('translate')('register_faild'),
                    s.registerAnswer.class = response.SUCCESS == 'true' ? 'valid' : 'invalid';
                    $timeout(function () {
                        s.registerAnswer = {message: '', class: 'invalid'}
                        if (response.SUCCESS == 'true') {
                            window.history.back();
                        }
                    }, 4000)
                });
            }

            $(document).on("keydown", function (event) {
                if (event.keyCode == 13) {
                    console.log("keyCode 13 pressed")
                    s.register();
                    return false;
                }
            });

            Helper.forAllPage();

        }]);

});