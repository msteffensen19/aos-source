/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('productService', ['$http', '$q', 'resHandleService', '$timeout',
        function ($http, $q, responseService, $timeout) {
            // Return public API.
            return ({
                getProducts: getProducts,
                getProductById: getProductById,
                getProductsBySearch: getProductsBySearch,
                getAllCategoriesAttributes: getAllCategoriesAttributes,
            });

            function getProducts() {
                var request = $http({
                    method: "get",
                    url: server.catalog.getProducts()
                });
                return ( request.then(responseService.handleSuccess, responseService.handleError) );
            }

            function getProductById(id) {

                var response = $q.defer();
                Helper.enableLoader();
                $timeout(function () {
                    var request = $http({
                        method: "get",
                        url: server.catalog.getProductById(id)
                    });
                    request.then(function (res) {
                            Helper.disableLoader();
                            Loger.Received(res);
                            response.resolve(res.data);
                        },
                        function (res) {
                            Helper.disableLoader();
                            Loger.Received(res);
                            response.resolve(res);
                        })
                }, Helper.defaultTimeLoaderToEnable);

                return response.promise;
            }

            function getProductsBySearch(word, quantity) {

                var response = $q.defer();
                Helper.enableLoader();
                $timeout(function () {
                    var request = $http({
                        method: "get",
                        url: server.catalog.getProductsBySearch(word, quantity)
                    });
                    request.then(function (res) {
                            Helper.disableLoader();
                            Loger.Received(res);
                            response.resolve(res.data);
                        },
                        function (res) {
                            Helper.disableLoader();
                            Loger.Received(res);
                            response.resolve(res);
                        })
                }, Helper.defaultTimeLoaderToEnable);

                return response.promise;
            }

            function getAllCategoriesAttributes() {

                var response = $q.defer();
                Helper.enableLoader();
                $timeout(function () {
                    var request = $http({
                        method: "get",
                        url: server.catalog.getAllCategoriesAttributes()
                    });
                    request.then(function (res) {
                            Helper.disableLoader();
                            Loger.Received(res);
                            response.resolve(res.data);
                        },
                        function (res) {
                            Helper.disableLoader();
                            Loger.Received(res);
                            response.resolve(res);
                        })
                }, Helper.defaultTimeLoaderToEnable);

                return response.promise;
            }

        }]);
});