/**
 * Created by correnti on 28/01/2016.
 */

define(['./module'], function (services) {
    'use strict';
    services.service('accountService',['$rootScope', '$q', 'mini_soap', '$http', '$filter', 'productsCartService',

        function ($rootScope, $q, mini_soap, $http, $filter, productsCartService) {

            return {
                //getAccountById: getAccountById,
                //getShippingCost: getShippingCost,
                //SafePay: SafePay,
                //accountUpdate : accountUpdate
            };
        }]);
});

