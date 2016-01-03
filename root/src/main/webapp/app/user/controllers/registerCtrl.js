/**
 * Created by correnti on 29/11/2015.
 */
/**
 * Created by correnti on 29/11/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('registerCtrl', ['$scope', 'registerService', '$location', '$timeout',
        function (s, registerService, $location, $timeout) {

            s.countries = {};
            s.registerAnswer = {
                message: '',
                class: 'invalid'
            }

            registerService.getAllCountries().then(function (response) {
                s.countries = response;
            })

            s.model = {

                username: '', email: '', password: '', confirm_password: '',
                firstName: '', lastName: '', phoneNumber: '', country: {}, address: '',
                city: '', postalCode: '', state: '', offers_promotion: true,
            }


            s.register = function () {

                // check this point - validate-from/to

                //check validateModel(model)

                registerService.register(s.model).then(function (response) {
                    s.registerAnswer.message = response.reason,
                        s.registerAnswer.class = response.success ? 'valid' : 'invalid';
                    $timeout(function (success) {
                        s.registerAnswer = {message: '', class: 'invalid'}
                        if (success) {
                            window.history.back();
                        }
                    }, 4000, response.success)
                });
            }

            $(document).on("keydown", function (event) {
                if (event.keyCode == 13) {
                    alert()
                    s.register();
                    return false;
                }
            });

            $("nav .navLinks").css("display", "none");

            /*
            $scope.inputFocus = function (id) {
                console.log($('#' + id));
                $('#' + id).siblings().not("img").not('.validationInfo').not('.must').animate(
                    {'top': '-10px'}, 800, $.bez([0.62, -0.14, 0.35, 1.34]));
                $('#' + id).siblings(".validationInfo").fadeIn();

            }

            $scope.inputBlur = function (id, validation) {

                if ($('#' + id).val() == '') {
                    $('#' + id).siblings().not("img").not('.validationInfo').animate({'top': '11px'}, 800, $.bez([0.62, -0.14, 0.35, 1.34]));
                }
                $('#' + id).siblings(".validationInfo").delay(200).fadeOut();

            }
            */

        }]);

});