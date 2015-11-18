/**
 * Created by kubany on 10/13/2015.
 */
define(['./module'], function (controllers) {


    'use strict';
    controllers.controller('mainCtrl', ['$scope', 'productService', 'smoothScroll', '$location', '$rootScope',
        function ($scope, productService, smoothScroll, $location, $rootScope) {




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






            //angular.element(document).ready(function () {
              //  $('.toggle-menu').jPushMenu({closeOnClickLink: false});
                //$('.dropdown-toggle').dropdown();
//                var productSearchInput = $('#product_search');
  //              productSearchInput.animate({width: 'toggle'}, 0);
    //            $('#search_image').click(function (e) {
      //              e.preventDefault();
        //            productSearchInput.animate({width: 'toggle'}, 'fast');
          //      });
            //});




        }]);
});








