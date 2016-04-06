/**
 * Created by kubany on 10/29/2015.
 */
define(['./module'], function (directives) {
    'use strict';
    directives.directive('loginModal', ['$rootScope', 'userService', 'ipCookie',
        '$templateCache', '$location', '$timeout', 'productsCartService', '$filter',
        function ($rootScope, userService, $cookie, $templateCache, $location,
                  $timeout, productsCartService, $filter) {
            return {
                restrict: 'E',
                replace: false,
                template: $templateCache.get('app/user/partials/login.html'),
                controller: function ($scope) {


                    /* VARIABLES */
                    //$scope.rememberMe = false;
                    //$scope.message = "";
                    $scope.message = {text: $filter('translate')('OR'), _class: ''};
                    $scope.config = null;
                    /*================================ END VARIABLES ======================================*/

                    /* Sign user in */
                    $scope.signIn = function (user, rememberMe) {

                        var userBlocked = $cookie(user.loginUser);

                        //if (checkUserBlocked()) {
                        //    $cookie("pcBlocked", new Date(new Date()).getTime() + (10 * 60000));
                        //    return;
                        //}

                        userService.login(user).then(function (response) {

                            if (response.userId != -1 && response.success) {

                                if (response.userId === undefined) {

                                    $timeout(function () {
                                        $scope.message.text = $filter('translate')('OR');
                                        $scope.message._class = "";
                                    }, 2000);
                                    $scope.message.text = response.reason;
                                    $scope.message._class = response.success ? '' : 'invalid';

                                    if (1 == 2) {
                                        $cookie(user.loginUser, new Date(new Date()).getTime() + (10 * 60000));
                                        return;
                                    }
                                    return;
                                }

                                $cookie.remove("loginsCounter");
                                userCookie.fillParams(user.loginUser, user.email, response);
                                $rootScope.userCookie = userCookie;

                                if (rememberMe) {
                                    $cookie(userCookie.getKey(userCookie), userCookie, {
                                        expirationUnit: 'minutes',
                                        expires: 60
                                    });
                                    $cookie('lastlogin', userCookie.getKey(userCookie));
                                    $scope.refreshTimeOut();
                                }
                                else {
                                    $cookie.remove("userCookie" + user.email);
                                }

                                productsCartService.joinCartProducts().then(function (cart) {
                                    $scope.cart = cart;
                                });

                                if ($location.path() == '/register') {
                                    $location.path('/')
                                }

                                wellcome();
                            }
                            else {
                                $timeout(function () {
                                    $scope.message.text = $filter('translate')('OR');
                                    $scope.message._class = "";
                                }, 2000);
                                $scope.message.text = response.reason;
                                $scope.message._class = "invalid";
                            }
                            return user;
                        });
                    }
                    /*=============================== end Sign in ===============================*/


                    /* increment wrong user login  */
                    var incrementLogins = function () {
                        var test = $cookie("loginsCounter");
                        var loginsCounter = test === undefined ? -1 : test;
                        return function () {
                            if (loginsCounter == -1) {
                                var test = $cookie("loginsCounter");
                                if (test === undefined) {
                                    test = 0;
                                }

                                loginsCounter = test;
                            }
                            var count = ++loginsCounter;
                            $cookie("loginsCounter", count, {expires: 365});
                            return count;
                        }
                    }();
                    /*=============================== end increment logins ===============================*/


                    /* create to user new account in application */
                    $scope.createNewAccount = function (user) {
                        wellcome();
                        $location.path('register');
                    }
                    /*================ end create to user new account in application  ========================*/


                    $scope.forgotPassword = function () {
                        console.log("forgotPassword");
                        $location.path('404');
                    }


                    $scope.singWithFacebook = function (user) {
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
    $(".PopUp > div:nth-child(1)").animate({"top": "-150%"}, 600, function () {

        $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));
        $(".PopUp").fadeOut(100);
        $("body").css("overflow", "scroll")
        $(".login").css("opacity", "1");
    });
}
