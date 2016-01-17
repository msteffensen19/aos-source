/**
 * Created by kubany on 10/18/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoryCtrl', ['$scope', '$stateParams', 'categoryService', 'category',
        function ($scope, $stateParams, categoryService, category) {

            $scope.catId = $stateParams.id;

            $scope.viewAll = category.viewAll;

            $scope.category = category;

            $scope.noProducts = category.products == undefined || category.products.length == 0;

            $scope.categoryData = category;

            $scope.categoryAttributes = category.attributes;

            $scope.products = category.products;

            $scope.categoryName = category.categoryName;

            $("nav .navLinks").css("display" , "none");

            Helper.forAllPage();

    }]);
});