/**
 * Created by kubany on 10/18/2015.
 */
/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoryCtrl', ['$scope', '$routeParams', 'categoryService',
        function ($scope, $routeParams, categoryService) {
            $scope.catId = $routeParams.id;
            $scope.category = "";
            $scope.deal = "";
            // I contain the ngModel values for form interaction.
            $scope.form = {
                name: ""
            };

            loadRemoteData();

            function loadRemoteData() {
                categoryService.getCategoryProducts($scope.catId)
                    .then(function( products ) {
                        applyRemoteData( products );
                    });
            }

            function applyRemoteData( products ) {
                $scope.products = products;
                $scope.categoryName = products.length > 0 ? products[0].category.categoryName : "";
                $scope.catImg = 'data:image/jpeg;base64,' + ( products.length > 0 ? products[0].category.image : "");
            }

    }]);
});