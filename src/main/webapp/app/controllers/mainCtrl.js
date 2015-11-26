/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {


    'use strict';
    controllers.controller('mainCtrl', ['$scope', 'productService', 'smoothScroll', '$location', 'ipCookie',
        '$rootScope', function ($scope, productService, smoothScroll, $location, $cookie, $rootScope) {



            $scope.accountSection = function(){
                alert("user account section! --- Method not done yet!");
            }

            $scope.signOut = function(){
                $cookie.remove("userCookie" + $rootScope.userCookie.id);
                $rootScope.userCookie = undefined;
            }

            $scope.openOptions = function(){
            }

            $scope.login = function (size) {

                $("body").css({
                    "overflow": "hidden",
                    "left": "0px",
                })
                $(".PopUp").fadeIn(100, function () {
                    $(".PopUp > div:nth-child(1)").animate({
                        "top": $(window).height() < 700 ? "5%" : "18%"
                    }, 600);
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

                // call $anchorScroll()
                //smoothScroll.scrollTo(id);
                console.log(($("#" + id).offset().top) + "px");
                $("body").animate({
                    scrollTop: ($("#" + id).offset().top) + "px",
                }, 1000)

            };



            $rootScope.$on('$locationChangeSuccess', function (event) {
                $scope.welcome = $location.path().indexOf('/welcome') <= -1 &&  $location.path().indexOf('/404') <= -1;
                $scope.showCategoryHeader = $location.path().indexOf('/category') <= -1;
            });



            $("#mobile-section").css("left", "-" + $("#mobile-section").css("width"));


        }]);
});








