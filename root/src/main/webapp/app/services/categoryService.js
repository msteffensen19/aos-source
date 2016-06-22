/**
 * Created by kubany on 10/18/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('categoryService', ['$http', '$q', '$timeout',
        'resHandleService', function ($http, $q, $timeout) {

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
                    allData = res;
                    Loger.Received(res)
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
