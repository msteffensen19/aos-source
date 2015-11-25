/**
 * Created by kubany on 10/29/2015.
 */
/**
 * Created by kubany on 10/14/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('loginModal', ['$rootScope', 'userService', 'ipCookie',
        function($rootScope, userService, $cookie) {
            return {
                restrict: 'E',
                replace:false,
                templateUrl: 'app/user/partials/login.html',
                controller: function ($scope) {

                    $scope.user = {  email: 'a@b.com',loginPassword: 'Avraham1', loginUser: 'avinu.avraham', }
                    $scope.rememberMe = false;

                    $scope.singIn = function(user, rememberMe) {

                        console.log(incrementLogins())

                        return;

                        userService.login(user).then(function (response) {

                                console.log(response);
                                console.log(response.success);
                                console.log(response.userId);
                                console.log(response.reason);

                                if(response.userId != -1) {

                                    userCookie.fillParams($scope.user.loginUser, /*response.id*/ 5,
                                        $scope.user.email, new Date());

                                    $rootScope.userCookie = userCookie;

                                    if(rememberMe)
                                    {
                                        $cookie("userCookie", $rootScope.userCookie, { expires: 21 });
                                    }
                                    wellcome(user.username)
                                }
                                else {
                                    wrongFields();
                                }
                            },
                            function (error) {
                                //alert()
                                console.log(error);
                            });
                    }

                    $scope.forgotPassword = function() {
                        alert("forgotPassword");
                    }

                    $scope.createNewAccount = function(user) {
                        alert("createNewAccount");
                    }

                    $scope.singWithFacebook = function(user) {
                        alert("singWithFacebook");
                    }
                }
            };
        }
    ]);
});
var incrementLogins = function (){
    var loginsCounter = 0;
    return function(){
        return ++loginsCounter;
    }
}();





function wellcome(name) {
    $(".login").css("opacity", "0.2")
    $(".PopUp > div:nth-child(1)").animate({ "top": "-150%" }, 600, function () {

        $(".PopUp").fadeOut(100);
        $("body").css("overflow", "scroll")
        $(".login").css("opacity", "1")
    });
}




function wrongFields(){

    $("#response").css("display", "block");
    setTimeout(function(){
        $("#response").css("display", "none");
    }, 2000);
}