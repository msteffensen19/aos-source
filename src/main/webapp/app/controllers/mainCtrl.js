/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('mainCtrl', ['$scope', 'productService', 'smoothScroll', '$location',
        function ($scope, productService, smoothScroll, $location) {

        $scope.gotoElement = function (eID){

            // call $anchorScroll()
            smoothScroll.scrollTo(eID);

        };

        angular.element(document).ready(function () {
            $('.toggle-menu').jPushMenu({closeOnClickLink: false});
            $('.dropdown-toggle').dropdown();
            var productSearchInput = $('#product_search');
            productSearchInput.animate({width:'toggle'}, 0);
            $('#search_image').click(function(e) {
                e.preventDefault();
                productSearchInput.animate({width:'toggle'}, 'fast');
            });
        });
    }]);
});