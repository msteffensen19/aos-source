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
                loadServer: loadServer,
                getAllData: getAllData,
                getCategories: getCategories,
                getCategoryById: getCategoryById,
                getPopularProducts: getPopularProducts,
                getExistingData: getExistingData,
                haveInternet: haveInternet,
            }

            function haveInternet() {

                var response = $q.defer();
                $http({
                    method: "get",
                    url: "https://www.youtube.com/watch?v=GW93cz-3MRU&feature=youtu.be"
                }).success(function () {
                    response.resolve(true);
                }).error(function () {
                    response.resolve(false);
                });
                return response.promise;
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




            function loadServer(){
                var response = $q.defer();

                var file ='services.properties'
                console.log("Extracting file: " + file);

                var rawFile = new XMLHttpRequest();

                rawFile.open("GET", file, false);
                rawFile.onreadystatechange = function () {

                    if (rawFile.readyState === 4) {
                        if (rawFile.status === 200 || rawFile.status == 0) {
                            var fileText = rawFile.responseText;
                            var rawFile_responseText = fileText;
                            fileText = fileText.split('');
                            var _param = '';
                            var _value = '';
                            var attr = true;
                            var arrayApi = [];
                            var invalidChars = '#';
                            fileText.forEach(function (a) {
                                switch (a.charCodeAt(0)) {
                                    case 10:
                                    case 13:
                                        var validParam = true;
                                        for (var i = 0; i < invalidChars.length; i++) {
                                            if (_param.indexOf(invalidChars[i]) != -1) {
                                                validParam = false;
                                                break;
                                            }
                                        }
                                        if (validParam && _param != '' && _value != '') {
                                            arrayApi.push("{\"" + _param.split(".").join("_") + "\":\"" + _value + "\"}");
                                            _param = '';
                                            _value = '';
                                        }
                                        attr = true;
                                        break;
                                    case 61:
                                        attr = false;
                                        break;
                                    default:
                                        if (attr) {
                                            _param += a;
                                        } else {
                                            _value += a;
                                        }
                                        break;
                                }
                            });

                            arrayApi.forEach(function (a) {
                                var jsonObj = JSON.parse(a);
                                services_properties[Object.keys(jsonObj)] = jsonObj[Object.keys(jsonObj)];
                            });

                            server.setKey("http://" + services_properties['catalog_service_url_host'] + ":" +
                                services_properties['catalog_service_url_port'] + "/");

                            server.setCatalogKey("http://" + services_properties['catalog_service_url_host'] + ":" +
                                services_properties['catalog_service_url_port'] + "/" + services_properties['catalog_service_url_suffix'] + "/");

                            server.setOrderKey("http://" + services_properties['order_service_url_host'] + ":" +
                                services_properties['order_service_url_port'] + "/" + services_properties['order_service_url_suffix'] + "/");

                            server.setWsdlPath("http://" +
                                services_properties['account_soapservice_url_host'] + ":" +
                                services_properties['account_soapservice_url_port'] + "/" +
                                services_properties['account_soapservice_url_suffix'] + "/");

                            response.resolve("OK");

                        }
                    }
                }
                rawFile.send(null)




                //$http({
                //    method: "get",
                //    url: server.catalog.getAllData()
                //}).success(function (res) {
                //    Loger.Received(res)
                //    var duplicateProductPrice = userService.getDuplicateProductPrice();
                //    for(var index1= 0 ; index1 < res.length; index1++){
                //        for(var index2= 0 ; index2 < res[index1].products.length; index2++){
                //            res[index1].products[index2].price *= duplicateProductPrice;
                //        }
                //    }
                //    allData = res;
                //    response.resolve(allData);
                //}).error(function (err) {
                //    alert('An error occurred, please try again')
                //    Loger.Received(err);
                //    response.reject('error in load cart (productCartService - loadCartProducts)');
                //});


                return response.promise;
            }


        }]);
});
