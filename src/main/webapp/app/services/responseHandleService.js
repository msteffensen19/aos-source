/**
 * Created by kubany on 10/18/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('resHandleService', ['$http', '$q', function ($http, $q) {

        return({
            handleError: handleError,
            handleSuccess : handleSuccess,
            getAllCountries : getAllCountries,
        });

        function getAllCountries() {
            var request = $http({
                method: "get",
                url: "api/account/countries",
            });
            console.log(request);
            return( request.then( this.handleSuccess, this.handleError ) );
        }

        function handleError( response ) {
            // The API response from the server should be returned in a
            // nomralized format. However, if the request was not handled by the
            // server (or what not handles properly - ex. server error), then we
            // may have to normalize it on our end, as best we can.
            if (
                ! angular.isObject( response.data ) ||
                ! response.data.message
            ) {
                return( response /*$q.reject( "An unknown error occurred." ) */);
            }
            // Otherwise, use expected error message.
            return( $q.reject( response.data.message ) );
        }



        // I transform the successful response, unwrapping the application data
        // from the API response payload.
        function handleSuccess( response ) {
            return( response.data );
        }

    }]);
});