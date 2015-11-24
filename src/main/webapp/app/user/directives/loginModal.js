/**
 * Created by kubany on 10/29/2015.
 */
/**
 * Created by kubany on 10/14/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('loginModal', ['$rootScope', 'userService', '$cookieStore',
        function($rootScope, userService, $cookie) {
            return {
                restrict: 'E',
                replace:false,
                scope: {},
                templateUrl: 'app/user/partials/login.html',
                controller: function ($scope) {

                    $scope.signIn = function(user) {


                        if(user.username.trim() == '')
                        { alert("Username required!"); return; }
                        if(user.password.trim() == '')
                        { alert("Password required!"); return; }
                        if(user.email.trim() == '')
                        { alert("Email required!"); return; }

                        userService.login(user).then(function (response) {

                                if(response.id != -1) {
                                    userCookie.fillParams(user.username, /*response.id*/ 5, user.email, new Date());
                                    $rootScope.userCookie = userCookie;
                                    $cookie.put("userCookie", $rootScope.userCookie)
                                    if($scope.rememberMe)
                                    {
                                        alert("")
                                    }
                                    wellcome(user.username)
                                }
                                else {
                                    wrongFields();
                                }
                            },
                            function (error) {
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






function wellcome(name) {
    $(".login *:not('#message')").css("opacity", "0.2")
    $("#message").text("Wellcome " + name + "!")
    $("#message").animate({
        "font-size": "38px",
        "opacity": 1,
    }, 500);

    $("#message").animate({ "top":"25%", }, 500, function () {

        $(this).delay(400).animate({ "font-size": "0px", "opacity": 0.2 }, 500, function () {

            $(".PopUp > div:nth-child(1)").animate({ "top": "-150%" }, 600, function () {

                $(".PopUp").fadeOut(100);
                $("body").css("overflow", "scroll")
                $(".login *:not('#message')").css("opacity", "1")
                $("#message").css("top","43%");
            });
        });
    });
}




function wrongFields(){

    $("#response").css("display", "block");
    setTimeout(function(){
        $("#response").css("display", "none");
    }, 2000);
}