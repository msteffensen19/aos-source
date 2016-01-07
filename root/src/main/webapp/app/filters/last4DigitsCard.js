/**
 * Created by correnti on 10/12/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.filter('last4DigitsCard', function(){
        return function(last) {

            alert(last);

            return "****";
        };
    })
    ;
});

