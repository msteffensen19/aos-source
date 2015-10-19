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
            $scope.categories = categories;
            $scope.subCategories = categories.slice(1);

            $scope.imageSrc = 'data:image/jpeg;base64,' + categories[0].image;
        }

        function loadRemoteData() {
            categoryService.getCategories()
                .then(function( categories ) {
                    applyRemoteData( categories );
                });
        }
    }]);
});