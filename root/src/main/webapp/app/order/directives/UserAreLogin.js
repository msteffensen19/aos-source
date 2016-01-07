/**
 * Created by correnti on 03/01/2016.
 */




define(['./module'], function (directives) {
    'use strict';
    directives.directive('userAreLogin', ['$templateCache', function($templateCache){
        return {
            replace: true,
            template: $templateCache.get('app/order/partials/user-are-login.html'),
            link: function(s, e, a, ctrl){

                s.firstTag = false;
                s.imgRadioButton = 2;
                s.savePay = {
                    username : '',
                    password : ''
                }

                s.CardNumber = ["6789", "0785", "0785", "0785"];
                s.last4DigitsCart = "0785";

            },
        }
    }]);
});


