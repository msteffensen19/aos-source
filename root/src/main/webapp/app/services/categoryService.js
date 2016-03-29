/**
 * Created by kubany on 10/18/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('categoryService', ['$http', '$q',
        'resHandleService', function ($http, $q, responseService) {

        return{
            getCategories : getCategories,
            getCategoryProducts : getCategoryProducts,
            getCategoryById : getCategoryById,
            getPopularProducts : getPopularProducts
        }

        function getCategories() {
            var request = $http({
                method: "get",
                url: server.catalog.getCategories()
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

        function getCategoryProducts(id) {
        //      var request = $http({ method: "get", url: "api/categoryProducts?category_id=" + id });
        //      return( request.then( responseService.handleSuccess, responseService.handleError ));
        }

        function getCategoryById(id) {
            var defer = $q.defer();
            if(id == ''){
                defer.resolve(null)
            }
            else{
                $http({
                    method: "get",
                    url: server.catalog.getCategoryById(id)
                }).success(function(res){
                    Loger.Received(res)
                    defer.resolve(res)
                }).error(function(err){
                    Loger.Received(err)
                    defer.reject(null)
                });
            }

            return defer.promise;
        };

        function getPopularProducts() {
            var request = $http({
                method: "get",
                url: server.catalog.getPopularProducts()
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

    }]);
});



//url: "api/catalog/products/categories/" + id + "/products"
//url: "http://localhost:8080/catalog/api/v1/categories/" + id + "/products"
//url: "http://localhost:8080/catalog/api/v1/" + "categories/" + id + "/products"
//url: CatalogURIPrefix + "categories/" + id + "/products"",
