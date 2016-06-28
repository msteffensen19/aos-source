/**
 * Created by kubany on 10/18/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('categoryService', ['$http', '$q', 'userService',
        'resHandleService', function ($http, $q, userService) {

            var allData;
            var categories;
            var popularProducts;

            return {
                getAllData: getAllData,
                getCategories: getCategories,
                getCategoryById: getCategoryById,
                getPopularProducts: getPopularProducts,
                getExistingData: getExistingData,
            }

            function getExistingData() {
                if (!allData) {
                    this.getAllData();
                }
                return allData;
            }

            function getAllData() {
                var response = $q.defer();
                $http({
                    method: "get",
                    url: server.catalog.getAllData()
                }).success(function (res) {
                    Loger.Received(res)
                    var duplicateProductPrice = userService.getDuplicateProductPrice();
                    for(var index1= 0 ; index1 < res.length; index1++){
                        for(var index2= 0 ; index2 < res[index1].products.length; index2++){
                            res[index1].products[index2].price *= duplicateProductPrice;
                        }
                    }
                    allData = res;
                    response.resolve(allData);
                }).error(function (err) {
                    alert('An error occurred, please try again')
                    Loger.Received(err);
                    response.reject('error in load cart (productCartService - loadCartProducts)');
                });
                return response.promise;
            }

            function getCategories() {

                var defer = $q.defer();
                if (categories) {
                    defer.resolve(categories);
                }
                else {
                    Helper.enableLoader();
                    $http({
                        method: "get",
                        url: server.catalog.getCategories(),
                    }).success(function (res) {
                        Helper.disableLoader();
                        Loger.Received(res)
                        var duplicateProductPrice = userService.getDuplicateProductPrice();
                        for(var index1= 0 ; index1 < res.length; index1++){
                            for(var index2= 0 ; index2 < res[index1].products.length; index2++){
                                res[index1].products[index2].price *= duplicateProductPrice;
                            }
                        }
                        categories = res;
                        defer.resolve(res)
                    }).error(function (err) {
                        Helper.disableLoader();
                        Loger.Received(err)
                        defer.reject(null)
                    });
                }
                return defer.promise;
            }

            function getCategoryById(id) {

                var defer = $q.defer();
                var found = false;
                if (allData) {
                    for (var i = 0; i < allData.length; i++) {
                        var category = allData[i];
                        if (category.categoryId == id) {
                            defer.resolve(category);
                            print("category return from app");
                            found = true;
                        }
                    }
                }
                if (!found) {
                    if (id == '') {
                        defer.resolve(null)
                    }
                    else {
                        Helper.enableLoader();
                        $http({
                            method: "get",
                            url: server.catalog.getCategoryById(id),
                        }).success(function (res) {
                            print("category return from server");
                            Helper.disableLoader();
                            Loger.Received(res)
                            var duplicateProductPrice = userService.getDuplicateProductPrice();
                            for(var index= 0 ; index < res.products.length; index++){
                                res.products[index].price *= duplicateProductPrice;
                            }
                            defer.resolve(res)
                        }).error(function (err) {
                            Helper.disableLoader();
                            Loger.Received(err)
                            defer.reject(null)
                        });
                    }
                }
                return defer.promise;
            };


            function getPopularProducts() {

                var defer = $q.defer();
                if (popularProducts) {
                    print("popularProducts return from app");
                    defer.resolve(popularProducts);
                }
                else {
                    Helper.enableLoader();
                    $http({
                        method: "get",
                        url: server.catalog.getPopularProducts(),
                    }).success(function (res) {
                        Helper.disableLoader();
                        Loger.Received(res)
                        print("popularProducts return from server");
                        var duplicateProductPrice = userService.getDuplicateProductPrice();
                        for(var index= 0 ; index < res.length; index++){
                            res[index].price *= duplicateProductPrice;
                        }
                        popularProducts = res;
                        defer.resolve(res)
                    }).error(function (err) {
                        Helper.disableLoader();
                        Loger.Received(err)
                        defer.reject(null)
                    });
                }
                return defer.promise;
            }

        }]);
});
