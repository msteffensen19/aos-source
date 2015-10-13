/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('mainCtrl', ['$scope', 'productService', function ($scope, productService) {
        $scope.title = "Hello"

        $scope.products = [];
        // I contain the ngModel values for form interaction.
        $scope.form = {
            name: ""
        };
        loadRemoteData();
        function applyRemoteData( products ) {
            $scope.products = products;
        }
        // I load the remote data from the server.
        function loadRemoteData() {
            // The friendService returns a promise.
            productService.getProducts()
                .then(function( products ) {
                    applyRemoteData( products );
                });
        }
    }]);
});