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
                getMostPopularComments: getMostPopularComments,
            }

            function haveInternet() {

                var response = $q.defer();
                $http({
                    method: "get",
                    url: "http://www.advantageonlineshopping.com/catalog/api/v1/catalog/LastUpdate/timestamp"
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

            function getMostPopularComments(categoryId){

                var response = $q.defer();
                if(categoryId != 2){
                    response.resolve([]);
                }
                var arr=[{"comment":"Great sound. It’s all about the bass.","score":9.3},{"comment":"Very comfortable headphones. Felt very light on the ears.","score":8.9},{"comment":"The noise cancelling feature worked great. I don’t even hear the bus on my commute home.","score":9.7},{"comment":"My ears sweated a lot when wearing the headphones. Next time I won’t wear them in the sauna.","score":7.6},{"comment":"The noise cancelling didn’t work that well. I could still hear my wife yelling at me.","score":3.8},{"comment":"I really wish they came in other colors. Hot pink would match much better with my shirt.","score":9.4},{"comment":"They didn’t fit great. I had to take off my bike helmet to get them on.","score":7.8},{"comment":"The cable wasn’t very long. I couldn’t get to the bathroom with the earphones plugged in to my stereo.","score":6.4},{"comment":"If they go on the ears, shouldn’t they be called earphones?","score":9.9},{"comment":"I don’t get it – is 20 hours how long it takes to recharge the batteries or how long they last?","score":7.1}];
                angular.forEach(arr, function(itm, index){

                    if(itm.score > 9.5){
                        itm.title = "Excellent";
                    }
                    else if(itm.score > 8.5){
                        itm.title = "Very good";
                    }
                    else if(itm.score > 7.5){
                        itm.title = "Good";
                    }
                    else if(itm.score > 5.5){
                        itm.title = "Average";
                    }
                    else if(itm.score > 4.5){
                        itm.title = "Poor";
                    }
                    else if(itm.score > 2.5){
                        itm.title = "Very poor";
                    }
                    else{
                        itm.title = "Shameful";
                    }
                    itm.reviewsCount = (itm.comment.length + itm.title.length) * 2;
                    itm.name = "Tim Perry";
                });

                response.resolve(arr);
                return response.promise;

                //Helper.enableLoader();
                //$http({
                //    method: "get",
                //    url: server.catalog.getMostPopularComments()
                //}).success(function (res) {
                //    Helper.disableLoader();
                //    Loger.Received(res)
                //    response.resolve(res.UserComments);
                //}).error(function (err) {
                //    alert('An error occurred, please try again')
                //    Loger.Received(err);
                //    response.reject('error in load cart (productCartService - loadCartProducts)');
                //});
                //return response.promise;
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
