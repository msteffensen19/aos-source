/**
 * Created by kubany on 10/29/2015.
 */
/**
 * Created by kubany on 10/14/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('loginModal', ['$rootScope', 'userService',
        function() {
            return {
                restrict: 'E',
                templateUrl: 'app/user/partials/login.html',
                controller: function ($scope) {
                    $scope.selected = {
                        item: $scope.items[0]
                    };
                }
            };
        }
        ]);
});



