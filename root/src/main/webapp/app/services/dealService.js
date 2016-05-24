/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('dealService', ['$http', '$q', 'resHandleService', function ($http, $q, responseService) {
        // Return public API.
        return({
            getDeals: getDeals,
            getDealOfTheDay : getDealOfTheDay
        });

        function getDeals() {
            var request = $http({
                method: "get",
                url: server.catalog.getDeals(),
                headers: {
                    "content-type": "application/json; charset=utf-8",
                },
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

        function getDealOfTheDay() {
            var request = $http({
                method: "get",
                url: server.catalog.getDealOfTheDay(),
                headers: {
                    "content-type": "application/json; charset=utf-8",
                },
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }
    }]);
});