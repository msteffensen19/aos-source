/**
 * Created by kubany on 10/18/2015.
 */
/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoryCtrl', ['$scope', '$stateParams', 'categoryService', 'category',
        function ($scope, $stateParams, categoryService, category) {


            $scope.catId = $stateParams.id;
            $scope.category = category;

            $scope.deal = "";
            // I contain the ngModel values for form interaction.
            $scope.form = {
                name: ""
            };

            applyRemoteData(category);

            function loadRemoteData() {

                categoryService.getCategoryById($scope.catId)
                    .then(function( category ) {
                        applyRemoteData( category );
                    });
            }

            function applyRemoteData(category) {

                $scope.categoryData = category;

                $scope.categoryAttributes = category.attributes;

                $scope.products = category.products;

                $scope.categoryName = category.categoryName;

                console.log($scope.category)
                console.log($scope.products)
                console.log($scope.categoryName)
                console.log($scope.attributes)

                $("nav .navLinks").css("display" , "none");

            }

            Helper.forAllPage();

    }]);
});