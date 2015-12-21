/**
 * Created by correnti on 21/11/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('productCtrl', ['$scope', 'productService', 'product',
        function ($scope, productService, product) {

            $scope.Quantity = 1;

            $scope.product = product;

            $scope.colorSelected = product.colors[0];

            $scope.addToCart = function(){
                var productToAdd = angular.copy(product);
                productToAdd.colors = [$scope.colorSelected];
                $scope.$parent.addProduct(productToAdd, $scope.Quantity);
            }

            $scope.setColor = function(color){
                $scope.colorSelected = color;
            }

            $("nav .navLinks").css("display", "none");

            Helper.forAllPage();
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
