/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {


    'use strict';
    controllers.controller('mainCtrl', ['$scope', 'productService', 'smoothScroll',
                    '$location', 'ipCookie', '$rootScope', 'productsCartService', '$filter', '$state',
        function ($scope, productService, smoothScroll,
                        $location, $cookie, $rootScope, productsCartService, $filter, $state) {

            $scope.cart;

            l('$state')
            l($state)
            $scope.autoCompleteValue = '';
            $scope.autoCompleteResult = {};


            $scope.go_up = function(){
                $('body, html').animate({scrollTop: 0}, 10);
            }




            /* Autocomplete*/
            $scope.checkEnterKey = function(event)
            {
                console.log(event)
                console.log(event.which)
                if(event.which === 13) {

                }
            }

            var lastRequest = '';
            $scope.runAutocomplete = function(){
                lastRequest = $scope.autoCompleteValue;
                if(lastRequest == '') {
                    $scope.autoCompleteResult = {};
                    return;
                }
                productService.getProductsBySearch(lastRequest, 10).then(function(result){
                    $scope.autoCompleteResult = result;
                });
            }


            $scope.openSearchProducts = function(){
                $("nav ul li a.navLinks").stop().animate({ opacity : 0 }, 400);
                setTimeout(function(_this){
                    $("#searchSection").fadeIn(1000);
                    $("#autoComplete").focus();
                    $("#searchSection > div:first-child > div").addClass("searchSectionEnabled");
                    $("#searchSection > div > div > span > img").delay(500).fadeIn(500); // img close
                    $('#openSearch').stop().animate({ opacity : 0 }, 300)
                }, 400);
            }

            $scope.closeSearchSection = function(){
                $('#openSearch').stop().animate({ opacity : 1 }, 300)
                $("#searchSection > div > div > span > img").fadeOut(200); // img close
                setTimeout(function(){
                    $("#searchSection").fadeOut(500);
                    $("#searchSection > div:first-child > div").removeClass("searchSectionEnabled");
                    if($location.$$path == '/')
                    {
                        $("nav ul li a.navLinks").stop().animate({ opacity : 1 }, 400);
                    }
                    $scope.autoCompleteValue = lastRequest = '';
                    $scope.autoCompleteResult = {};
                    $("#autoComplete").focusout();
                }, 200)
            }

            $('#product_search_img').click(function (e) {
                $('#product_search').css("display", "inline-block");
                $('#product_search').animate({ "width": $('#product_search').width() > 0 ? 0 : "150px" },
                    500, function(){
                        if($('#product_search').width() == 0 ){
                            $(this).css("display", "none");
                        }
                    } );
            });

            $scope.searchByCategoryId = function(id){
                console.log(id);
            }
            /* END Autocomplete*/









            /* Cart section  */

            productsCartService.loadCartProducts().then(function(cart){
                $scope.cart = cart;
            });

            $scope.removeProduct = function (index) {
                productsCartService.removeProduct(index).then(function (cart) {
                    $scope.cart = cart;
                });
            }


            var lastIdAdded = '';
            $scope.addProduct = function(product, quantity) {
                clearInterval(Helper.____closeTooTipCart);
                $('#toolTipCart').slideDown(function(){
                    productsCartService.addProduct(product, quantity).then(function(cart){
                        $scope.cart = cart;
                        if (lastIdAdded == ('#product' + product.productId))
                        {
                            setToolTipCartSlideUp()
                        }
                        else {
                            lastIdAdded = '#product' + product.productId;
                            $('#toolTipCart tbody').stop().animate({
                                scrollTop: 0 + 'px',
                            }, 500, function () {
                                setToolTipCartSlideUp()
                            });
                        }
                    });
                });
            };

            function setToolTipCartSlideUp() {
                clearInterval(Helper.____closeTooTipCart);
                Helper.____closeTooTipCart = setTimeout(function(){
                    $('#toolTipCart').stop().slideUp();
                }, 8000)
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







            $scope.gotoElement = function (id) {
                $("body").animate({
                    scrollTop: ($("#" + id).offset().top - 60) + "px",
                }, 1000)
            };


            $rootScope.$on('$locationChangeSuccess', function (event, current, previous) {

                $scope.welcome = $location.path().indexOf('/welcome') <= -1 &&
                    $location.path().indexOf('/404') <= -1;
                $scope.showCategoryHeader = $location.path().indexOf('/category') <= -1;
                Helper.UpdatePageFixed();

            });

            /*
             $rootScope.$on("$stateChangeStart",
             function (event, current, previous, rejection, rejection2) {

             }
             );

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








