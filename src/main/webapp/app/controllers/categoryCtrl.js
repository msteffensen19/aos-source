/**
 * Created by kubany on 10/18/2015.
 */
/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoryCtrl', ['$scope', 'categoryService', '$sce', function ($scope, categoryService, $sce) {
        $scope.categories = [];
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
        }
    }]);
});