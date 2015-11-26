/**
 * Created by kubany on 10/29/2015.
 */
/**
 * Created by kubany on 10/14/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('loginModal', ['$rootScope', 'userService', 'ipCookie', '$templateCache',
        function($rootScope, userService, $cookie, $templateCache) {
            return {
                restrict: 'E',
                replace:false,
                template: $templateCache.get('app/user/partials/login.html'),
                controller: function ($scope) {

                    $scope.user = {  email: '',loginPassword: '', loginUser: '', }
                    //$scope.user = {  email: 'a@b.com',loginPassword: 'Avraham1', loginUser: 'avinu.avraham', }

                    $scope.rememberMe = false;
                    $scope.failed = "";

                    $scope.singIn = function(user, rememberMe) {


                        userService.login(user).then(function (response) {


                                console.log(JSON.stringify(response));
                                if(response.userId != -1) {

                                    if(response.userId === undefined)
                                    {
                                        if(response.data !== undefined)
                                        {
                                            $scope.failed = response.data.reason;
                                            var count = incrementLogins();
                                            if(count >= 3) {
                                                    $cookie("pcBlocked", new Date(new Date()).getTime() + (10*60000));
                                                    //question: Ask maria what to show!
                                            }
                                        }
                                        return;
                                    }
                                    $cookie.remove("loginsCounter");
                                    userCookie.fillParams($scope.user.loginUser, response.userId,
                                        $scope.user.email, new Date());
                                    $rootScope.userCookie = userCookie;

                                    if(rememberMe){
                                        $cookie("userCookieLastEntry", response.userId, { expirationUnit: 'minutes', expires: 60 });
                                        $cookie("userCookie" + response.userId, $rootScope.userCookie, { expirationUnit: 'minutes', expires: 60 });
                                    }
                                    else{
                                        $cookie.remove("userCookie" + $scope.user.email);
                                    }
                                    wellcome(user.username)
                                }
                                else {
                                    wrongFields();
                                }
                            });
                    }

                    var incrementLogins = function (){
                        var test = $cookie("loginsCounter");
                        var loginsCounter = test === undefined ? -1 : test;

                        return function(){
                            if(loginsCounter == -1)
                            {
                                var test = $cookie("loginsCounter");
                                if(test === undefined)
                                {
                                    test = 0;
                                }
                                loginsCounter = test;
                            }
                            var count = ++loginsCounter;
                            $cookie("loginsCounter", count, { expires: 365 });
                            console.log(count);
                            return count;
                        }
                    }();


                    $scope.forgotPassword = function() {
                        console.log("forgotPassword");
                        $location.path('404');
                    }

                    $scope.createNewAccount = function(user) {
                        console.log("createNewAccount");
                        $location.path('404');
                    }

                    $scope.singWithFacebook = function(user) {
                        console.log("singWithFacebook");
                        $location.path('404');
                    }
                }
            };
        }
    ]);
});



function wellcome(name) {
    $(".login").css("opacity", "0.2")
    $(".PopUp > div:nth-child(1)").animate({ "top": "-150%" }, 600, function () {

        $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));
        $(".PopUp").fadeOut(100);
        $("body").css("overflow", "scroll")
        $(".login").css("opacity", "1");

    });
}


function wrongFields(){

    $("#response").css("display", "block");
    setTimeout(function(){
        $("#response").css("display", "none");
    }, 2000);

}