/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('dealService', ['$http', '$q', 'resHandleService', function ($http, $q, responseService) {
        // Return public API.
        return ({
            getDeals: getDeals,
            getDealOfTheDay: getDealOfTheDay
        });

        var deals;
        var dealOfTheDay;

        function getDeals() {
            var defer = $q.defer();
            if (deals) {
                defer.resolve(deals);
            }
            else {
                Helper.enableLoader();
                $http({
                    method: "get",
                    url: server.catalog.getDeals(),
                }).success(function (res) {
                    Helper.disableLoader();
                    Loger.Received(res)
                    deals = res;
                    defer.resolve(res)
                }).error(function (err) {
                    Helper.disableLoader();
                    Loger.Received(err)
                    defer.reject(null)
                });
            }
            return defer.promise;
        }


        function getDealOfTheDay() {

            var defer = $q.defer();
            if (dealOfTheDay) {
                defer.resolve(dealOfTheDay);
            }
            else {
                Helper.enableLoader();
                $http({
                    method: "get",
                    url: server.catalog.getDealOfTheDay(),
                }).success(function (res) {
                    Helper.disableLoader();
                    Loger.Received(res)
                    dealOfTheDay = res;
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