/**
 * Created by kubany on 10/18/2015.
 */
define(['./module'], function (services) {
    'use strict';
    services.service('categoryService', ['$http', '$q','resHandleService', function ($http, $q, responseService) {

        return{
            getCategories : getCategories,
            getCategoryProducts : getCategoryProducts
        }

        function getCategories() {
            var request = $http({
                method: "get",
                url: "api/category"
                //params: {
                //    action: "get"
                //}
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

        function getCategoryProducts(id) {
            var request = $http({
                method: "get",
                url: "api/categoryProducts?category_id=" + id
                //params: {
                //    action: "get"
                //}
            });
            return( request.then( responseService.handleSuccess, responseService.handleError ) );
        }

    }]);
});