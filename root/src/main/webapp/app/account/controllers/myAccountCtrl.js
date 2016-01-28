/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('myAccountCtrl', ['$scope', '$interval',
        function (s, interval) {

            var _i = 0;
            interval(function(){
                console.log(++_i);
                s.checkLogin();
            }, 5000);
            s.checkLogin();

            $("nav .navLinks").css("display" , "none");

            Helper.forAllPage();

        }]);
});




