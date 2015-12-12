/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoriesCtrl', ['$scope', 'categoryService',
        'dealService', '$sce', '$location',
        function ($scope, categoryService, dealService, $sce, $location) {


            $scope.categories = [];
            $scope.deal = "";
            $scope.form = {
                name: ""
            };
            $scope.popularProducts = ""

            loadRemoteData();
            function loadRemoteData() {
                categoryService.getCategories().then(function( categories ) {
                    $scope.categories = categories;
                });
                dealService.getDealOfTheDay().then(function( deal ) {
                    $scope.deal = deal;
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