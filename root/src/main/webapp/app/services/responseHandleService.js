/**
 * Created by kubany on 10/18/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('resHandleService', ['$http', '$q', '$timeout', function ($http, $q, $timeout) {

        return({

            handleSuccess : function ( response ) { return( response.data ); },


            handleError: function ( response ) {
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


            startWebBuildingWhenServerPropertiesAsLoader : function(){

                var defer = $q.defer();

                $timeout(function(){
                    alert()
                    defer.resolve(null);
                }, 5000);

                return defer.promise;

            }

        });




        // I transform the successful response, unwrapping the application data
        // from the API response payload.

    }]);
});