/**
 * Created by correnti on 21/11/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('productCtrl', ['$scope', 'productService', 'product',
        function ($scope, productService, product) {

            console.log(product)

            $scope.Quantity = 1;

            $scope.product = product;

            $scope.colorSelected = '';

            $scope.addToCart = function(){

            }

            $scope.setColor = function(num){
                $scope.colorSelectedId = num;
            }
            $("nav .navLinks").css("display", "none");

        }]);
    });

/**
 * Created by correnti on 21/11/2015.
 */



/*
 var img = $("#imgToBuy").clone();
 var imgPoss = $("#imgToBuy").offset();
 var mainCartPossition = $("#mainCart").offset();

 img.css({
 "position" : "absolute",
 "top": imgPoss.top + "px",
 "left": imgPoss.left + "px",
 "display" : "none",
 })
 img.appendTo("#product-image");
 img.fadeIn(1000, function(){
 img.animate({
 top : mainCartPossition.top + 5,
 left : mainCartPossition.left + 10,
 width: 10,
 height: 10,
 opacity: 0.3
 }, 1000, function(){
 img.remove();
 //$rootScope.user.cart.laptops.push($scope.product) = get user
 });
 })
 */
