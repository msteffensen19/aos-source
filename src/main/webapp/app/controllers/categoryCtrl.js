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
                categoryService.getMockData($scope.catId)
                    .then(function( category ) {
                        applyRemoteData( category );
                    });
            }

            function applyRemoteData(category) {

                $scope.categoryData = category;
//                console.log("$scope.categoryData")
  //              console.log($scope.categoryData)
    //            console.log("")


                $scope.categoryAttributes = category.attributes;
//                console.log("$scope.categoryAttributes")
  //              console.log($scope.categoryAttributes)
    //            console.log("")


                $scope.products = category.products;
                console.log("$scope.products")
                console.log($scope.products)
                console.log("")



                $scope.categoryName = category.categoryName;
//                console.log("$scope.categoryName")
  //              console.log($scope.categoryName)
    //            console.log("")
                //$scope.catImg = 'data:image/jpeg;base64,' + ( products.length > 0 ? products[0].category.image : "");

            }

    }]);
});