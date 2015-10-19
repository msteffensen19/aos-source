/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('mainCtrl', ['$scope', 'productService', function ($scope, productService) {


        angular.element(document).ready(function () {
            $('.toggle-menu').jPushMenu({closeOnClickLink: false});
            $('.dropdown-toggle').dropdown();

        });
    }]);
});