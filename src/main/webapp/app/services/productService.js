/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('productService', ['$http', '$q', 'resHandleService', function ($http, $q, responseService) {
        // Return public API.
        return({
            getProducts: getProducts,
            getProductById : getProductById
        });

        function getProducts() {
            var request = $http({
                method: "get",
                url: "api/catalog/products"
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

        function getProductById(id) {

            var request = $http({
                method: "get",
                url: 'api/catalog/products/' + id,
                //url: 'app/product.json',
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }
    }]);
});