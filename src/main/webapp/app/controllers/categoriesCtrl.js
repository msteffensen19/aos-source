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
            loadRemoteData();
            function applyRemoteData( categories ) {
                angular.forEach(categories, function(value, key){
                    value.image = 'data:image/jpeg;base64,' + value.image;
                });
                $scope.categories = categories;
            }

            function loadRemoteData() {
                categoryService.getCategories()
                    .then(function( categories ) {
                        applyRemoteData( categories );
                    });
                dealService.getDealOfTheDay().then(function( deal ) {
                    $scope.deal = deal;
                });
            }

            $scope.goToCategory = function(id) {
                $location.path('/category/' + id);
            };
        }]);
});