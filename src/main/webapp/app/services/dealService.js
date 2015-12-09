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
                url: "api/catalog/deals"
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

        function getDealOfTheDay() {
            var request = $http({
                method: "get",
                //url: "api/catalog/deals/0"
                url: "api/catalog/deals?dealOfTheDay=true"
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }
    }]);
});