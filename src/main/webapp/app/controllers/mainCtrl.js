/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('mainCtrl', ['$scope', 'productService', 'smoothScroll', '$location', '$rootScope',
        function ($scope, productService, smoothScroll, $location, $rootScope) {

            $scope.gotoElement = function (eID) {

                // call $anchorScroll()
                smoothScroll.scrollTo(eID);

            };

            $scope.init = function () {
                var amountScrolled = 300;

                $(window).scroll(function () {
                    if ($(window).scrollTop() > amountScrolled) {
                        $('a.back-to-top').fadeIn('slow');
                    } else {
                        $('a.back-to-top').fadeOut('slow');
                    }
                });
                $('a.back-to-top').click(function () {
                    $('body, html').animate({
                        scrollTop: 0
                    }, 700);
                    return false;
                });
            };

            $rootScope.$on('$locationChangeSuccess', function (event) {
                $scope.welcome = $location.path().indexOf('/welcome') <= -1 &&  $location.path().indexOf('/404') <= -1;
                $scope.showCategoryHeader = $location.path().indexOf('/category') <= -1;
            });

            angular.element(document).ready(function () {
                $('.toggle-menu').jPushMenu({closeOnClickLink: false});
                $('.dropdown-toggle').dropdown();
                var productSearchInput = $('#product_search');
                productSearchInput.animate({width: 'toggle'}, 0);
                $('#search_image').click(function (e) {
                    e.preventDefault();
                    productSearchInput.animate({width: 'toggle'}, 'fast');
                });

            });
        }]);
});