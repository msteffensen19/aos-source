/**
 * Created by kubany on 10/18/2015.
 */
/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('categoryCtrl', ['$scope', '$routeParams',
        function ($scope, $routeParams) {
            $scope.catId = $routeParams.id;
    }]);
});