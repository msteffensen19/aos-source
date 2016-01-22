/**
 * Created by correnti on 03/01/2016.
 */

define(['./module'], function (directives) {
    'use strict';
    directives.directive('userNotLogin', ['$templateCache', function($templateCache){
        return {
            replace: true,
            template: $templateCache.get('app/order/partials/user-not-login.html'),
            link: {
                pre: function (s) {
                    s.validSecValidate = function (invalid) {
                        //alert("invove: " + invalid);
                    }
                },
                post: function () {

                }
            }
        }
    }]);
});


