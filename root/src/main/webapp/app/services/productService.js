/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('productService', ['$http', '$q', 'resHandleService', function ($http, $q, responseService) {
        // Return public API.
        return({
            getProducts: getProducts,
            getProductById : getProductById,
            getProductsBySearch : getProductsBySearch,
            getAllCategoriesAttributes : getAllCategoriesAttributes,
        });

        function getProducts() {
            var request = $http({
                method: "get",
                url: server.catalog.getProducts()
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

        function getProductById(id) {

            var request = $http({
                method: "get",
                url: server.catalog.getProductById(id)
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

        function getProductsBySearch(word, quantity) {

            var request = $http({
                method: "get",
                url: server.catalog.getProductsBySearch(word, quantity)
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

        function getAllCategoriesAttributes() {

            var request = $http({
                method: "get",
                url: server.catalog.getAllCategoriesAttributes()
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }


    }]);
});