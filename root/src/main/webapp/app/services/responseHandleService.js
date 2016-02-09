/**
 * Created by kubany on 10/18/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('resHandleService', ['$http', '$q', '$timeout', function ($http, $q, $timeout) {

        return({

            handleSuccess : function ( response ) {
                Helper.loaderHandler(false);
                return( response.data );
            },


            handleError: function ( response ) {
                Helper.loaderHandler(false);
                if ( ! angular.isObject( response.data ) || ! response.data.message ) {
                    return( response /*$q.reject( "An unknown error occurred." ) */);
                }
                return( $q.reject( response.data.message ) );
            },

            getAllCountries : function () {
                var request = $http({
                    method: "get",
                    url: server.account.getAllCountries(),
                });
                return( request.then( this.handleSuccess, this.handleError ) );
            },

        });




        // I transform the successful response, unwrapping the application data
        // from the API response payload.

    }]);
});