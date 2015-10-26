/**
 * Created by kubany on 10/14/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('popularItemsDrtv', [ function () {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: './app/partials/product-tpl.html',
            controller: function($scope, $element){
                $scope.name = $scope.name + "Second ";
            },
            link: function(scope, el, attr) {
                scope.name = scope.name + "Third ";
            }
        };
    }]);
});