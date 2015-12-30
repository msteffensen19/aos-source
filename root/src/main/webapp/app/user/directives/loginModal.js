/**
 * Created by kubany on 10/29/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('loginModal', ['$rootScope', 'userService', 'ipCookie',
        '$templateCache', '$location', '$timeout', 'productsCartService',
        function($rootScope, userService, $cookie, $templateCache, $location, $timeout, productsCartService) {
            return {
                restrict: 'E',
                replace:false,
                template: $templateCache.get('app/user/partials/login.html'),
                controller: function ($scope) {


                    /* VARIABLES */
                    //$scope.user = {  email: 'a@b.com',loginPassword: 'Avraham1', loginUser: 'avinu.avraham', }
                    $scope.user = {  email: '',loginPassword: '', loginUser: '', }
                    $scope.rememberMe = false;
                    $scope.message = "";
                    $scope.config = null;
                    /*================================ END VARIABLES ======================================*/


                    /* Get configuration */
                    userService.getConfiguration().then(function(response){
                        $scope.config = response;
                    });
                    /*===========================  end Get configuration ============================*/



                    /* Sign in */
                    $scope.signIn = function(user, rememberMe) {

                        console.log(user)
                        console.log(JSON.stringify(user))
                        userService.login(user).then(function (response) {

                                if(response.userId != -1) {

                                    if(response.userId === undefined)
                                    {
                                        if(response.data !== undefined)
                                        {
                                            $timeout(function(){
                                                $scope.message = "";
                                            }, 2000)
                                            $scope.message = response.data.reason;

                                            var count = incrementLogins();
                                            if(count >= 3) {
                                                console.log(count);
                                                $cookie("pcBlocked", new Date(new Date()).getTime() + (10*60000));
                                                //question: Ask maria what to show!
                                                return;
                                            }
                                        }
                                        return;
                                    }

                                    $cookie.remove("loginsCounter");
                                    userCookie.fillParams($scope.user.loginUser, $scope.user.email, response);
                                    $rootScope.userCookie = userCookie;

                                    if(rememberMe){

                                         $cookie(userCookie.getKey(userCookie), userCookie, { expirationUnit: 'minutes', expires: 60 });
                                        $cookie('lastlogin', userCookie.getKey(userCookie));
                                        console.log($cookie(userCookie.getKey(userCookie)));
                                    }
                                    else{
                                        $cookie.remove("userCookie" + $scope.user.email);
                                    }

                                    productsCartService.joinCartProducts().then(function(cart){
                                        $scope.cart = cart;
                                    });;

                                    wellcome();
                                }
                                else {
                                    wrongFields();
                                }
                            });
                    }
                    /*=============================== end Sign in ===============================*/





                    /* increment logins */
                    var incrementLogins = function (){
                        var test = $cookie("loginsCounter");
                        var loginsCounter = test === undefined ? -1 : test;

                        return function(){
                            if(loginsCounter == -1)
                            {
                                var test = $cookie("loginsCounter");
                                if(test === undefined)
                                { test = 0; }

                                loginsCounter = test;
                            }
                            var count = ++loginsCounter;
                            $cookie("loginsCounter", count, { expires: 365 });
                            //console.log(count);
                            return count;
                        }
                    }();
                    /*=============================== end increment logins ===============================*/


                    $scope.forgotPassword = function() {
                        console.log("forgotPassword");
                        $location.path('404');
                    }



                    $scope.createNewAccount = function(user) {
                        wellcome();
                        $location.path('register');
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



function wellcome() {
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