/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('productService', ['$http', '$q', 'resHandleService', '$timeout', 'categoryService',
        function ($http, $q, responseService, $timeout, categoryService) {
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

                var allData = categoryService.getExistingData();
                var response = $q.defer();
                var found = false;
                if (allData) {
                    for (var i = 0; i < allData.length; i++) {
                        for (var j = 0; j < allData[i].products.length; j++) {
                            var product = allData[i].products[j];
                            if (product.productId == id) {
                                found = true;
                                response.resolve(product);
                                break;
                            }
                        }
                        if (found) {
                            break;
                        }
                    }
                }
                if (!found) {
                    Helper.enableLoader();
                    var request = $http({
                        method: "get",
                        url: server.catalog.getProductById(id),
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
                }
                return response.promise;
            }

            function getProductsBySearch(word, quantity) {

                var allData = categoryService.getExistingData();
                var response = $q.defer();
                if (allData) {
                    var arr = [];
                    for (var i = 0; i < allData.length; i++) {
                        var data = allData[i];
                        var category = {
                            categoryId: data.categoryId,
                            categoryImageId: data.categoryImageId,
                            categoryName: data.categoryName,
                            products: [],
                        };
                        for (var j = 0; j < data.products.length; j++) {
                            var product = allData[i].products[j];
                            if (product.productName.indexOf(word) != -1) {
                                category.products.push(product);
                            }
                        }
                        arr.push(category);
                    }
                    response.resolve(arr);
                }
                else {
                    Helper.enableLoader();
                    var request = $http({
                        method: "get",
                        url: server.catalog.getProductsBySearch(word, quantity),
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
                }
                return response.promise;
            }

            var allCategoriesAttributes;

            function getAllCategoriesAttributes() {

                var response = $q.defer();
                if (allCategoriesAttributes) {
                    response.resolve(allCategoriesAttributes);
                }
                else {

                    Helper.enableLoader();
                    var request = $http({
                        method: "get",
                        url: server.catalog.getAllCategoriesAttributes()
                    });
                    request.then(function (res) {
                            Helper.disableLoader();
                            Loger.Received(res);
                            allCategoriesAttributes = res.data;
                            response.resolve(allCategoriesAttributes);
                        },
                        function (res) {
                            Helper.disableLoader();
                            Loger.Received(res);
                            response.resolve(res);
                        });
                }
                return response.promise;
            }

        }]);
});