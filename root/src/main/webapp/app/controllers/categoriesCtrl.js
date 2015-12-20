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
                    $scope.specialOffer = deal;
                });

                categoryService.getPopularProducts().then(function(popularProducts){
                    $scope.popularProducts = popularProducts;
                    console.log("-----------------popularProducts-----------------")
                    console.log(popularProducts)
                    console.log("-----------------popularProducts-----------------")
                })
            }

            $scope.goToCategory = function(id) {
                $location.path('/category/' + id);
            };

            $scope.images = [{
                imageName : 'Banner1.jpg',
                imageId : 0,
                message : "ALL YOU WANT FROM A TABLET"
            },
            {
                imageName : 'Banner2.jpg',
                imageId : 1,
                message : "EXPLORE OUR LASTEST <br />INNOVATIVE PRODUCTS"
            },
            {
                imageName : 'Banner3.jpg',
                imageId : 2,
                message : "START EXPLORING HP NOTEBOOKS"
            }];

            Slider.AddSliderListener();
            $("nav .navLinks").css("display" , "block");

            Helper.forAllPage();
        }]);
});