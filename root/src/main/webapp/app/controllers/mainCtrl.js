/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {


    'use strict';
    controllers.controller('mainCtrl', ['$scope', '$q', 'productService', 'smoothScroll', 'userService',
                    '$location', 'ipCookie', '$rootScope', 'productsCartService', '$filter', '$state',
        function ($scope, $q, productService, smoothScroll, userService,
                        $location, $cookie, $rootScope, productsCartService, $filter, $state) {

            $scope.cart;

            $scope.$watch("cart", function(n){
                console.log($scope.cart)
            })

            $scope.autoCompleteValue = '';
            $scope.autoCompleteResult = {};

            $scope.go_up = function(){
                $('body, html').animate({scrollTop: 0}, 10, function(){
                    $("nav .navLinks").css("display", "block");
                });
            }




            /* Get configuration */
            userService.getConfiguration().then(function(response){
                console.log("getConfiguration")
                console.log(response)
                console.log("getConfiguration")
                $scope.config = response;
                console.log(response)
            });
            /*===========================  end Get configuration ============================*/






            /* Cart section  */

            productsCartService.loadCartProducts().then(function(cart){
                $scope.cart = cart;
            });

            $scope.removeProduct = function (index) {
                productsCartService.removeProduct(index).then(function (cart) {
                    $scope.cart = cart;
                    $scope.checkCart();
                });
            }

           $scope.addProduct = function(product, quantity) {
               clearInterval(Helper.____closeTooTipCart);
               productsCartService.addProduct(product, quantity).then(function (cart) {
                   $scope.cart = cart;
                   animateToolTipCart();
               });
           }

            $scope.updateProduct = function(product, color, quantity, oldColor) {
                productsCartService.updateProduct(product, color, quantity, oldColor)
                    .then(function(cart){
                    $scope.cart = cart;
                    animateToolTipCart();
                });
            }

            function animateToolTipCart(){
                clearInterval(Helper.____closeTooTipCart);
                    $('#toolTipCart').slideDown(function () {
                        $('#toolTipCart tbody').stop().animate({
                            scrollTop: 0 + 'px',
                        }, 500, function () {
                        Helper.____closeTooTipCart = setTimeout(function(){
                            $('#toolTipCart').stop().slideUp();
                        }, 8000)
                    });
                });
            }

            $scope.enterCart = function(){
                clearInterval(Helper.____closeTooTipCart); // defined in categoryTypeProductsDrtv -> addProduct
                $('#toolTipCart').stop().slideDown();
            }

            $scope.leaveCart = function(){
                Helper.closeToolTipCart();
            }

            /* END Cart section */









            /* User section */

            $scope.loginUser = {  email: '',loginPassword: '', loginUser: '', }

            $scope.setUser = function(){
                $scope.loginUser = {  email: 'a@b.com',loginPassword: 'Itshak1', loginUser: 'avinu.itshak', }
            }

            $scope.accountSection = function(){
                console.log("user account section! --- Method not done yet!");
                $location.path('404');
            }

            $scope.signOut = function(){
                $cookie.remove('lastlogin');
                $rootScope.userCookie = undefined;
                $scope.loginUser = {  email: '',loginPassword: '', loginUser: '', }
                productsCartService.loadCartProducts().then(function(cart){
                    $scope.cart = cart;
                });
            }

            $scope.login = function (size) {

                $('#toolTipCart').css('display', 'none');
                var windowsWidth = $(window).width();
                var top = "5%";
                if(windowsWidth < 480) {
                    top = "0";
                }
                else if(windowsWidth < 700) { top = "18%"; }

                $(".PopUp").fadeIn(100, function () {
                    $(".PopUp > div:nth-child(1)").animate({ "top": top }, 600);
                    $("body").css({ "left": "0px", })
                });

            }

            $(".PopUp, .closePopUpBtn").click(function (e) {

                $(".PopUp > div:nth-child(1)").animate({
                    "top": "-150%"
                }, 600, function () {
                    $(".PopUp").fadeOut(100, function(){
                        $("body").css("overflow-y", "scroll");
                    });
                });
            });

            $(".PopUp > div").click(function (e) {
                e.stopPropagation();
            });

            /* END User section */










            /* Application helper section */

            $scope.redirect = function(path) {
                if($scope.cart.productsInCart.length == 0 && path == '/shoppingCart'){
                    return;
                }
                $location.path(path);
            };

            $scope.gotoElement = function (id) {
                $("body").animate({
                    scrollTop: ($("#" + id).offset().top - 60) + "px",
                }, 1000)
            };

            $scope.checkCart = function(){

                if($scope.cart + "" == "undefined" || $scope.cart.productsInCart.length == 0)
                {
                    switch($location.$$path) {
                        case '/shoppingCart':
                        case '/orderPayment':
                            window.history.back();
                            break;
                    }
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
                Helper.UpdatePageFixed();

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

             });
             $rootScope.$on("$stateChangeSuccess", function (event, current, previous, rejection, rej2) {
             onBreadcrumbHandler();
             });
             */

            function onBreadcrumbHandler(){
                var existsRoot = $rootScope.breadcrumb;
                var newBreadcrumb = [];
                for(var index = 0; index < existsRoot.length; index++){
                    var existState = existsRoot[index];
                    newBreadcrumb.push({
                        name: $state.current.data.breadcrumbName,
                        path: $location.$$path
                    });
                    if(existState.name == $state.current.data.breadcrumbName)
                    {
                        break;
                    }
                }
                $rootScope.breadcrumb = newBreadcrumb;
            }



            Main.addAnimPlaceholderEventListener();
            $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));
            var mobile_section_moved = $("#mobile-section").width();

            $scope.openMobileSection = function(){
                $("body").animate({
                    left: $("body").css("left") != "0px" ? "0px" : mobile_section_moved
                }, 200);
            }

        }]);
});








