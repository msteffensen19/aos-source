/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('myAccountCtrl', ['$scope',
        function (s) {

            $("nav .navLinks").css("display" , "none");

            Helper.forAllPage();

        }]);
});




