/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {


    'use strict';
    controllers.controller('mainCtrl', ['$scope', 'productService', 'smoothScroll',
                    '$location', 'ipCookie', '$rootScope', 'productsCartService',
        function ($scope, productService, smoothScroll,
                        $location, $cookie, $rootScope, productsCartService) {

            $scope.toolTipCartProducts = { products: [], total: 0 };


            $scope.removeProduct = function(id){
                var temp = [];
                angular.forEach($rootScope.cartProducts.productsInCart, function(prod){
                    if(prod.id != id) {
                        temp.push(prod);
                    }
                });
                $rootScope.cartProducts.productsInCart = temp;
                buildCart();
                productsCartService.updateRemovedProducts($rootScope.cartProducts);
                $("#product"+id).css("display", 'none');
                if(temp.length == 0)
                {
                    $('#toolTipCart').slideUp();
                }
            }


            $scope.accountSection = function(){
                console.log("user account section! --- Method not done yet!");
                $location.path('404');
            }


            $scope.signOut = function(){
                $cookie.remove('lastlogin');
                $rootScope.userCookie = undefined;
                productsCartService.loadCartProducts().then(function(response){
                    $rootScope.cartProducts = response;
                });
            }

            function buildCart(){
                $scope.toolTipCartProducts = { products: [], total: 0 };
                angular.forEach($rootScope.cartProducts.productsInCart ,function(product){
                    var find = false;
                    angular.forEach($scope.toolTipCartProducts.products, function(tolltipProduct){
                        if(product.id == tolltipProduct.id && product.color == tolltipProduct.color)
                        {
                            tolltipProduct.quantity++;
                            $scope.toolTipCartProducts.total += tolltipProduct.price;
                            find = true;
                        }
                    });
                    if(!find)
                    {
                        $scope.toolTipCartProducts.products.push({
                            id: product.id,
                            imageUrl: product.imageUrl,
                            productName: product.productName,
                            quantity: product.quantity,
                            color: product.color,
                            price: product.price
                        })
                        $scope.toolTipCartProducts.total += product.price;
                    }
                });
            }


            $scope.openCart = function(){


                if($rootScope.cartProducts.productsInCart.length > 0)
                {
                    if($('#toolTipCart').css('display') == 'none') {
                        buildCart();
                    }
                    $('#toolTipCart').slideToggle();
                }
            }


            $scope.enterCart = function(){
                buildCart();
                $('#toolTipCart').stop().slideDown();
            }

            $scope.leaveCart = function(){
                console.log($rootScope.cartProducts);
                $('#toolTipCart').stop().slideUp();
            }


            $scope.openOptions = function(){
            }


            $scope.login = function (size) {

                $('#toolTipCart').css('display', 'none');

                var windowsWidht = $(window).width();
                var top = "5%";

                if(windowsWidht < 480) { top = "0"; }

                else if(windowsWidht < 700) { top = "18%"; }

                $("body").css({ "overflow": "hidden", "left": "0px", })

                $(".PopUp").css({ "overflow-y": "scroll" })

                $(".PopUp").fadeIn(100, function () {
                    $(".PopUp > div:nth-child(1)").animate({ "top": top }, 600);
                });

                $(".PopUp").click(function (e) {
                    $("body").css("overflow", "scroll")
                    $(".PopUp > div:nth-child(1)").animate({
                        "top": "-150%"
                    }, 600, function () {
                        $(".PopUp").fadeOut(100);
                    });
                });

                $(".PopUp > div").click(function (e) {
                    e.stopPropagation();
                });
            }



            $scope.gotoElement = function (id) {

                console.log(($("#" + id).offset().top) + "px");
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








