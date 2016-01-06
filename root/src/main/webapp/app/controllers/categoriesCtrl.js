/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoriesCtrl', ['$scope', 'categoryService',
        'dealService', '$location', 'resolveParams',
        function ($scope, categoryService, dealService, $location, resolveParams) {

            $scope.categories = resolveParams.categories;
            $scope.specialOffer= resolveParams.specialOffer;
            $scope.popularProducts = resolveParams.popularProducts;

            $scope.form = {
                name: ""
            };

            $scope.goToCategory = function(id) {
                $location.path('/category/' + id);
            };

            $scope.images = [
                { imageName : 'Banner1.jpg', imageId : 0, message : "ALL YOU WANT FROM A TABLET", categoryId : 2 },
                { imageName : 'Banner2.jpg', imageId : 1, message : "EXPLORE OUR LASTEST <br />INNOVATIVE PRODUCTS", categoryId : 3 },
                { imageName : 'Banner3.jpg', imageId : 2, message : "START EXPLORING HP NOTEBOOKS", categoryId : 0 }
            ];

            Slider.AddSliderListener();
            $("nav .navLinks").css("display" , "block");

            Helper.forAllPage();
        }]);
});