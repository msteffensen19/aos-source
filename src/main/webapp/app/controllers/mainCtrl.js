/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {


    'use strict';
    controllers.controller('mainCtrl', ['$scope', 'productService', 'smoothScroll',
                    '$location', 'ipCookie', '$rootScope', 'productsCartService', '$filter',
        function ($scope, productService, smoothScroll,
                        $location, $cookie, $rootScope, productsCartService, $filter) {

            $scope.cart;

            productsCartService.getCart().then(function(cart){
                $scope.cart = cart;
            });

            $scope.removeProduct = function (index) {
                productsCartService.removeProduct(index).then(function (cart) {
                    $scope.cart = cart;
                });
            }

            $scope.accountSection = function(){
                console.log("user account section! --- Method not done yet!");
                $location.path('404');
            }

            $scope.signOut = function(){
                $cookie.remove('lastlogin');
                $rootScope.userCookie = undefined;
                productsCartService.loadCartProducts().then(function(cart){
                    $scope.cart = cart;
                });
            }

            $scope.enterCart = function(){
                clearInterval(Helper.____closeTooTipCart); // defined in categoryTypeProductsDrtv -> addProduct
                $('#toolTipCart').stop().slideDown();
            }

            $scope.leaveCart = function(){
                $('#toolTipCart').stop().slideUp(function(){
                    $('#toolTipCart tbody').animate({ scrollTop: 0, }, 500);
                });
            }

            $scope.login = function (size) {

                $('#toolTipCart').css('display', 'none');
                var windowsWidth = $(window).width();
                var top = "5%";
                if(windowsWidth < 480) { top = "0"; } else if(windowsWidth < 700) { top = "18%"; }

                $(".PopUp").css({ "overflow-y": "scroll" })

                $(".PopUp").fadeIn(100, function () {
                    $(".PopUp > div:nth-child(1)").animate({ "top": top }, 600);
                    $("body").css({ "overflow": "hidden", "left": "0px", })
                });
            }

            $(".PopUp").click(function (e) {

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



            $scope.gotoElement = function (id) {

                $("body").animate({
                    scrollTop: ($("#" + id).offset().top) + "px",
                }, 1000)

            };



            $rootScope.$on('$locationChangeSuccess', function (event) {
                $scope.welcome = $location.path().indexOf('/welcome') <= -1 &&  $location.path().indexOf('/404') <= -1;
                $scope.showCategoryHeader = $location.path().indexOf('/category') <= -1;
            });


            Main.miniItemPopUp();
            $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));




        }]);
});








