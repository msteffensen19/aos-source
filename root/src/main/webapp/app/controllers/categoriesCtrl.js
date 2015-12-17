/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoriesCtrl', ['$scope', 'categoryService',
        'dealService', '$location',
        function ($scope, categoryService, dealService, $location) {

            $scope.categories = [];
            $scope.specialOffer= {};
            $scope.form = {
                name: ""
            };
            $scope.popularProducts = ""

            loadRemoteData();
            function loadRemoteData() {
                categoryService.getCategories().then(function( categories ) {
                    $scope.categories = categories;
                });
                dealService.getDealOfTheDay().then(function(deal) {
                    console.log("-----------------deal-----------------")
                    console.log(deal)
                    $scope.specialOffer = deal;
                    console.log("-----------------deal-----------------")
                });
                categoryService.getPopularProducts().then(function(popularProducts){
                    $scope.popularProducts = popularProducts;
                })
            }

            $scope.goToCategory = function(id) {
                $location.path('/category/' + id);
            };

            Slider.AddSliderListener();
            $("nav .navLinks").css("display" , "block");

            Helper.forAllPage();
        }]);
});