/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {


    'use strict';
    controllers.controller('mainCtrl', ['$scope', '$q', 'productService', 'smoothScroll', 'userService', 'orderService',
        '$location', 'ipCookie', '$rootScope', 'productsCartService', '$filter', '$state', '$timeout',
        function ($scope, $q, productService, smoothScroll, userService, orderService,
                  $location, $cookie, $rootScope, productsCartService, $filter, $state, $timeout) {

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

            $scope.getConfigUserSecondWsdl = function(){
                return $scope.config.userSecondWsdl == 'true';
            }
            /*===========================  end Get configuration ============================*/


            /* Cart section  */

            productsCartService.loadCartProducts().then(function (cart) {
                $scope.cart = cart;
            });

            $scope.removeProduct = function (index, event) {
                if(event){
                    event.stopPropagation();
                }
                productsCartService.removeProduct(index).then(function (cart) {
                    $scope.cart = cart;
                    $scope.checkCart();
                    fixToolTipCartHeight();
                });
            }

            $scope.addProduct = function (product, quantity) {
                clearInterval(Helper.____closeTooTipCart);
                productsCartService.addProduct(product, quantity).then(function (cart) {
                    $scope.cart = cart;
                    animateToolTipCart();
                    fixToolTipCartHeight();
                });
            }

            $scope.updateProduct = function (product, color, quantity, oldColor) {
                productsCartService.updateProduct(product, color, quantity, oldColor)
                    .then(function (cart) {
                        $scope.cart = cart;
                        animateToolTipCart();
                    });
            }

            function animateToolTipCart() {
                clearInterval(Helper.____closeTooTipCart);
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


            $scope.setUser = function () {
                $scope.loginUser = {email: 'a@b.com', loginPassword: 'Itshak1', loginUser: 'avinu.itshak',}
            }

            $scope.accountSection = function () {
                $state.go('myAccount');
            }

            $scope.signOut = function (even) {

                if(even){
                    even.stopPropagation();
                }
                userService.singOut().then(function(){
                    $cookie.remove('lastlogin');
                    $rootScope.userCookie = undefined;
                    $scope.loginUser = {email: '', loginPassword: '', loginUser: '',}
                    productsCartService.loadCartProducts().then(function (cart) {
                        $scope.cart = cart;
                        $scope.checkCart();
                    });
                    $scope.calculateMobileSection()

                    $(".mini-title").css("display", "none");
                });
            }

            $scope.miniTitleIn = function (miniTitleId) {
                if ($rootScope.userCookie) {
                    $("#" + miniTitleId).stop().delay(500).stop().fadeIn(300);
                }
            }

            $scope.miniTitleOut = function (miniTitleId) {
                if ($rootScope.userCookie) {
                    $("#" + miniTitleId).stop().delay(500).stop().fadeOut(300);
                }
            }

            $scope.login = function (miniTitleId) {

                if ($rootScope.userCookie) {
                    $("#" + miniTitleId).fadeToggle(300);
                    return;
                }
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
                    $scope.closeMobileSection();
                });

            }

            $(".PopUp, .closePopUpBtn").click(function (e) {

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


            $scope.mobileRedirect = function (path) {
                //$scope.openMobileSection();
                $scope.redirect(path);
            };

            $scope.redirect = function (path) {
                $location.path(path);
            };

            $scope.gotoElement = function (id) {
                $("body").animate({
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
                if (orderService.userIsLogin()) {
                    $timeout.cancel(_____autoLogOut);
                    _____autoLogOut = $timeout(function () {
                        $scope.signOut()
                    }, $scope.config.userLoginTimeout == 0 ? (60 * 60000)
                        : ($scope.config.userLoginTimeout * 60000));
                }
            }

            /* END Application helper section */


            $rootScope.$on('clearCartEvent', function (event, args) {
                productsCartService.clearCart().then(function (cart) {
                    $scope.cart = cart;
                });
            });


            $rootScope.$on('$locationChangeSuccess', function (event, current, previous) {

                $scope.welcome = $location.path().indexOf('/welcome') <= -1 && $location.path().indexOf('/404') <= -1;
                $scope.showCategoryHeader = $location.path().indexOf('/category') <= -1;

                $("#searchSection #output").css("opacity", $location.path().indexOf('/category') == -1 ? 1 : 0);

                Helper.UpdatePageFixed();

                if ($location.path().indexOf('/category') == -1)
                    $scope.closeSearchForce();





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
                $scope.closeMobileSection();

            });




            /*
             $rootScope.$on("$stateChangeStart", function (event, current, previous, rejection, rejection2) {

             console.log('==========================start======================================================')
             console.log('$location')
             console.log($location)
             console.log('$state')
             console.log($state)
             console.log('cart')
             console.log($scope.cart)
             //if (to.redirectTo) {
             //    evt.preventDefault();
             //    $state.go("userNotlogin", params)
             //}

             });
             $rootScope.$on("$stateChangeSuccess", function (event, current, previous, rejection, rej2) {
             onBreadcrumbHandler();
             });
             */

            //function onBreadcrumbHandler(){
            //    var existsRoot = $rootScope.breadcrumb;
            //    var newBreadcrumb = [];
            //    for(var index = 0; index < existsRoot.length; index++){
            //        var existState = existsRoot[index];
            //        newBreadcrumb.push({
            //            name: $state.current.data.breadcrumbName,
            //            path: $location.$$path
            //        });
            //        if(existState.name == $state.current.data.breadcrumbName)
            //        {
            //            break;
            //        }
            //    }
            //    $rootScope.breadcrumb = newBreadcrumb;
            //}


            var mobile_section_moved = $("#mobile-section").width();

            Main.addAnimPlaceholderEventListener();

            $scope.calculateMobileSection = function(){
                setTimeout(function(){
                    $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));
                    mobile_section_moved = $("#mobile-section").width();
                    $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));
                    $scope.closeMobileSection()
                }, 100)
            }

            $scope.calculateMobileSection()

            $scope.openMobileSection = function () {
                $("body").animate({
                    left: $("body").css("left") != "0px" ? "0px" : mobile_section_moved
                }, 200);
                $("#mobile-section").animate({
                    left: $("body").css("left") == "0px" ? "0px" : "-" + mobile_section_moved
                }, 200);
            }

            $scope.closeMobileSection = function () {

                $("body").animate({
                    left: "0px"
                }, 200);
                $("#mobile-section").animate({
                    left: "-" + mobile_section_moved
                }, 200);

                $("#loginMobileMiniTitle").fadeOut(300);

            }

            $(document).ready(function() {

                $(window).on({
                    resize: _resize,
                    scroll: _scroll,
                });
                function  _scroll(){
                    Helper.checkPagePossitions();
                }
                _resize();
                function _resize() {
                    $scope.closeMobileSection();
                    $(".mini-title").css("display", "none");
                }

            });

        }]);
});








