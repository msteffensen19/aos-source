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

            $scope.autoCompleteValue = '';
            $scope.autoCompleteResult = {};


            /* Autocomplete*/

            $scope.checkEnterKey= function(event)
            {
                console.log(event)
                console.log(event.which)
                if(event.which === 13) {

                }
            }

            var lastRequest = '';
            $scope.runAutocomplete = function(){
//                if($scope.autoCompleteValue.indexOf(lastRequest) == -1 && $scope.autoCompleteResult.length > 0)
  //              {
    //                console.log(lastRequest);
      //              return;
        //        }

                lastRequest = $scope.autoCompleteValue;
                if(lastRequest == '')
                {
                    $scope.autoCompleteResult = {};
                    return;
                }

                productService.getProductsBySearch(lastRequest, 10).then(function(result){
                    console.log(result);
                    $scope.autoCompleteResult = result;
                });
            }

            $scope.openSearchProducts = function(){
                $("#searchSection").fadeIn(1000);
                $("nav ul li a").each(function(index){
                    setTimeout(function(_this){
                        _this.addClass("searchSectionEnabled");
                    }, (500 / 6) * index, $(this))
                });
                setTimeout(function(_this){
                    $("#searchSection > div:first-child > div").addClass("searchSectionEnabled");
                    $("#searchSection > div > div > span > img").delay(1000).fadeIn(500); // img close
                }, 400);
            }


            $scope.closeSearchSection = function(){

                $("#searchSection > div > div > span > img").fadeOut(500); // img close
                setTimeout(function(){
                    $("#searchSection").fadeOut(1000);
                    $("#searchSection > div:first-child > div").removeClass("searchSectionEnabled");
                    $("nav ul li a").each(function(index){
                        setTimeout(function(_this){
                            _this.removeClass("searchSectionEnabled");
                        }, (200 / 6) * index, $(this))
                    })
                    $scope.autoCompleteValue = lastRequest = '';
                    $scope.autoCompleteResult = {};
                    $("#autoComplete").focusout();
                }, 500)

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
                alert(id)
            }

            /* END Autocomplete*/







            /* Cart section */
            productsCartService.getCart().then(function(cart){
                $scope.cart = cart;
            });

            $scope.removeProduct = function (index) {
                productsCartService.removeProduct(index).then(function (cart) {
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

            /* END User section */







            $scope.gotoElement = function (id) {
                $("body").animate({
                    scrollTop: ($("#" + id).offset().top) + "px",
                }, 1000)
            };



            $rootScope.$on('$locationChangeSuccess', function (event) {
                $scope.welcome = $location.path().indexOf('/welcome') <= -1 &&  $location.path().indexOf('/404') <= -1;
                $scope.showCategoryHeader = $location.path().indexOf('/category') <= -1;
            });


            Main.addAnimPlaceholderEventListener();
            $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));




        }]);
});








