/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {


    'use strict';
    controllers.controller('mainCtrl', ['$scope', '$q', 'productService', 'smoothScroll', 'userService', 'orderService',
        '$location', 'ipCookie', '$rootScope', 'productsCartService', '$filter', '$state', '$timeout',
        function ($scope, $q, productService, smoothScroll, userService, orderService,
                  $location, $cookie, $rootScope, productsCartService, $filter, $state, $timeout) {


            var connection = navigator.connection || navigator.mozConnection || navigator.webkitConnection;
            //var type = connection.type;

            function updateConnectionStatus() {
                alert("Connection type is change from " + type + " to " + connection.type);
            }
            if(connection){
                connection.addEventListener('typechange', updateConnectionStatus);
            }



            $scope.cart;
            $scope.autoCompleteValue = '';
            $scope.autoCompleteResult = {};

            $scope.go_up = function () {
                $('body, html').animate({scrollTop: 0}, 10, function () {
                    if ($(".autoCompleteCover").width() < 100) {
                        $("nav .navLinks").css("display", "block");
                    }
                });
            }


            /* Get configuration */
            userService.getConfiguration().then(function (response) {
                $scope.config = response;
                $scope.refreshTimeOut();
            });
            /*===========================  end Get configuration ============================*/


            /* Cart section  */

            productsCartService.loadCartProducts().then(function (cart) {
                $scope.cart = cart;
                $timeout(function () {
                    productsCartService.checkOutOfStockProductsInCart().then(function (_cart) {
                        $scope.cart = cart;
                    });
                })
            });

            $scope.removeProduct = function (index, event) {

                if (event) {
                    event.stopPropagation();
                }
                productsCartService.removeProduct(index).then(function (cart) {
                    $scope.cart = cart;
                    $scope.checkCart();
                    fixToolTipCartHeight();

                    if ($(window).width() < 480) {
                        $("#toast a").html($filter("translate")("Product_removed"));
                        $("#toast").stop().fadeIn(500);
                        setTimeout(function () {
                            $("#toast").stop().fadeOut(1500);
                        }, 1500)
                    }

                });
            }

            $scope.addProduct = function (product, quantity, toastMessage) {
                clearInterval(Helper.____closeTooTipCart);
                console.log("addProduct enter")
                productsCartService.addProduct(product, quantity).then(function (cart) {
                    console.log("addProduct back")
                    $scope.cart = cart;
                    animateToolTipCart(toastMessage);
                    fixToolTipCartHeight();
                });
            }

            $scope.updateProduct = function (product, color, quantity, oldColor, toastMessage) {
                productsCartService.updateProduct(product, color, quantity, oldColor)
                    .then(function (cart) {
                        $scope.cart = cart;
                        animateToolTipCart(toastMessage);
                    });
            }

            function animateToolTipCart(toastMessage) {
                clearInterval(Helper.____closeTooTipCart);

                if ($(window).width() < 480) {
                    $("#toast a").html(toastMessage);
                    $("#toast").stop().fadeIn(500);
                    setTimeout(function () {
                        $("#toast").stop().fadeOut(1500);
                    }, 1500)
                }

                $('#toolTipCart').delay(500).slideDown(function () {
                    $('#toolTipCart tbody').stop().animate({
                        scrollTop: 0 + 'px',
                    }, 500, function () {
                        Helper.____closeTooTipCart = setTimeout(function () {
                            $('#toolTipCart').stop().slideUp();
                        }, 8000)
                    });
                });
            }

            $scope.enterCart = function () {
                clearInterval(Helper.____closeTooTipCart); // defined in categoryTypeProductsDrtv -> addProduct
                $('#toolTipCart').stop().slideDown();
                fixToolTipCartHeight();
            }

            $scope.leaveCart = function () {
                Helper.closeToolTipCart();
            }

            function fixToolTipCartHeight() {
                var winHeight = $(window).height() - 200;
                var prodsHeight = ($scope.cart.productsInCart.length * 104);
                var heightToAsign = (Math.round(winHeight / 104) * 104);
                if (winHeight < prodsHeight) {
                    $("#toolTipCart tbody").css({
                        "height": heightToAsign + "px",
                        "overflow-y": "auto"
                    });
                }
                else {
                    $("#toolTipCart tbody").css({
                        "height": prodsHeight + "px",
                        "overflow-y": "hidden"
                    });
                }
            }

            /* END Cart section */


            /* User section */

            $scope.loginUser = {email: '', loginPassword: '', loginUser: '',}

            var _setUser = 0;
            $scope.setUser = function () {
                if (_setUser > 0) {
                    $scope.signIn({email: 'a@b.com', loginPassword: 'Itshak1', loginUser: 'avinu.itshak',}, true);
                }
                _setUser++;
                setTimeout(function () {
                    _setUser = 0;
                }, 1000);
            }


            $scope.accountSection = function () {
                $state.go('myAccount');
            }

            $scope.signOut = function (even) {

                if (even) {
                    even.stopPropagation();
                }
                userService.singOut().then(function () {

                    $cookie.remove('lastlogin');
                    $rootScope.userCookie = undefined;
                    $scope.loginUser = {email: '', loginPassword: '', loginUser: '',}
                    productsCartService.loadCartProducts().then(function (cart) {
                        $scope.cart = cart;
                        $scope.checkCart();
                    });
                    $(".mini-title").css("display", "none");
                });
            }

            $scope.miniTitleIn = function (miniTitleId) {
                if ($rootScope.userCookie) {
                    $("#" + miniTitleId).stop().delay(500).fadeIn(300);
                }
            }

            $scope.miniTitleOut = function (miniTitleId) {
                if ($rootScope.userCookie) {
                    $("#" + miniTitleId).stop().delay(500).fadeOut(300);
                }
            }

            var ____loginInterval;
            $scope.miniTitleIn = function(){
                if(____loginInterval){
                    $timeout.cancel(____loginInterval);
                }
            }

            $scope.miniTitleOut= function(miniTitleId){
                ____loginInterval = $timeout(function(){
                    $("#" + miniTitleId).fadeToggle(300);
                }, 2000);
            }

            $scope.login = function (miniTitleId) {

                if ($rootScope.userCookie) {
                    $("#" + miniTitleId).fadeToggle(300);
                    return;
                }

                Helper.mobileSectionClose();
                $('#toolTipCart').css('display', 'none');
                var windowsWidth = $(window).width();
                var top = "5%";
                if (windowsWidth < 480) {
                    top = "0";
                }
                else if (windowsWidth < 700) {
                    top = "18%";
                }

                $(".PopUp").fadeIn(100, function () {
                    $(".PopUp > div:nth-child(1)").animate({"top": top}, 600);
                    $("body").css({"left": "0px",})
                });

            }

            $(".PopUp, .closePopUpBtn").click(function () {

                $(".PopUp > div:nth-child(1)").animate({
                    "top": "-150%"
                }, 600, function () {
                    $(".PopUp").fadeOut(100, function () {
                        $("body").css("overflow-y", "scroll");
                    });
                });
            });

            $(".PopUp > div").click(function (e) {
                e.stopPropagation();
            });

            /* END User section */


            /* Application helper section */


            $scope.redirect = function (path) {
                Helper.mobileSectionClose();
                $location.path(path);
            }

            $scope.mobileRedirect = function (path) {
                Helper.mobileSectionClose();
                $state.go(path)
            }


            $scope.gotoElement = function (id) {
                $("body, html").animate({
                    scrollTop: ($("#" + id).offset().top - 60) + "px",
                }, 1000)
            };

            $scope.checkCart = function () {

                if ($scope.cart + "" == "undefined" || $scope.cart.productsInCart.length == 0) {
                    switch ($location.$$path) {
                        case '/login':
                        case '/orderPayment':
                            $state.go("default");
                            break;
                    }
                }
            }

            $scope.checkLogin = function () {
                var user = $rootScope.userCookie;
                if (!(user && user.response && user.response.userId != -1 && user.response.token)) {
                    $state.go("default");
                }
            }


            var _____autoLogOut;
            $scope.refreshTimeOut = function () {

                if($scope.config == null){
                    return;
                }
                if (orderService.userIsLogin()) {

                    $timeout.cancel(_____autoLogOut);
                    _____autoLogOut = $timeout(function () {
                        $scope.signOut()
                    }, $scope.config.userLoginTimeOut == 0 ? (60 * 60000)
                        : ($scope.config.userLoginTimeOut * 60000));

                }
            }

            /* END Application helper section */


            $rootScope.$on('clearCartEvent', function (event, args) {
                productsCartService.clearCart().then(function (cart) {
                    $scope.cart = cart;
                });
            });



            $rootScope.$on('$locationChangeStart', function (event, current, previous) {
                //$("html, body").css({opacity: 0});
                    $(".waitBackground").css({opacity: 1, display: "block",});
                    $("div.loader").css({opacity: 1, display: "block", });
            });

            $rootScope.$on('$locationChangeSuccess', function (event, current, previous) {

                $scope.welcome = $location.path().indexOf('/welcome') <= -1 && $location.path().indexOf('/404') <= -1;
                $scope.showCategoryHeader = $location.path().indexOf('/search') <= -1;

                $("#searchSection #output").css("opacity", $location.path().indexOf('/search') == -1 ? 1 : 0);

                Helper.UpdatePageFixed();

                if ($location.path().indexOf('/search') == -1 && $scope.closeSearchForce + "" != "undefined") {
                    $scope.closeSearchForce();
                }
                Helper.mobileSectionClose();

                $timeout(function () {
                    if ($location.path() == '/') {
                        if ($(".autoCompleteCover").width() < 100) {
                            $("nav .navLinks").css("display", "block");
                        }
                    }
                    else {
                        $("nav .navLinks").css("display", "none");
                    }
                }, 1050);
                $scope.refreshTimeOut();
                $scope.miniTitleOut();
            });

            $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));
            Helper.mobile_section_moved = $("#mobile-section").width();

            $scope.openMobileSection = function () {
                Helper.mobileSectionHandler();
            }

            $scope.$on('$viewContentLoaded', function(event) {
                Helper.forAllPage();
            });
        }]);
});








