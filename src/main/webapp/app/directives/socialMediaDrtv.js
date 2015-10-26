/**
 * Created by kubany on 10/26/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('socialMediaDrtv', [ function () {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: './app/partials/social-media-tpl.html',
            controller: function($scope, $element){

            },
            link: function(scope, el, attr) {

            }
        };
    }]);
});