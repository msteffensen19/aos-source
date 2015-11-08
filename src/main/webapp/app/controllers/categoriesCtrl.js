/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoriesCtrl', ['$scope', 'categoryService', 'dealService', '$sce', '$location',
        function ($scope, categoryService, dealService, $sce, $location) {
            $scope.categories = [];
            $scope.deal = "";
            // I contain the ngModel values for form interaction.
            $scope.form = {
                name: ""
            };
            $scope.popularProducts = ""

            loadRemoteData();
            function applyRemoteData( categories ) {
                $scope.categories = categories;
                $('.carousel').carousel({
                    interval: 5000
                });
            }

            function loadRemoteData() {
                categoryService.getCategories()
                    .then(function( categories ) {
                        applyRemoteData( categories );
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

            $scope.uri = "http://localhost:8080/";
            $scope.images = [0, 1, 2];

        }]);
});