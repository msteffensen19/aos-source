/**
 * Created by correnti on 31/12/2015.
 */


define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('accountDetailsCtrl', ['$scope', '$timeout',
        '$location', 'resolveParams',
        function (s, $timeout, $location, resolveParams) {

            l(resolveParams)
            l($location)

            var _i = 0;
            checkLogin();
            function checkLogin(){
                console.log(++_i);
                s.checkLogin();
                if($location.path().indexOf('/accountDetails') != -1){
                    $timeout(checkLogin, 2000);
                }
            }



        }]);
});




