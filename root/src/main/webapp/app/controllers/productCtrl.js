/**
 * Created by correnti on 21/11/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('productCtrl', ['$scope', 'resolveParams',

        function ($scope, resolveParams) {

            console.log(resolveParams)

            var selectedColor = resolveParams.selectedColor;
            var pageState = resolveParams.pageState;
            $scope.quantity = 1, //resolveParams.quantity || 1;
            $scope.categoryName = resolveParams.categoryName;
            $scope.product = resolveParams.product;


            for(var i = 0; i < $scope.product.colors.length; i++){
                if($scope.product.colors[i].code == selectedColor)
                {
                    $scope.colorSelected = $scope.product.colors[i];
                    break;
                }
            }
            $scope.colorSelected = $scope.colorSelected || $scope.product.colors[0];


            $scope.quantityOptions = [1,2,3,4,5,6,7,8,9];
            if($scope.quantity && $scope.quantity > 9)
            {
                for(var i = 10; i <= $scope.quantity; i++)
                {
                    $scope.quantityOptions.push(i)
                }
            }


            $scope.addToCart = function(){
                if(pageState == 'edit')
                {

                }
                else
                {
                    var productToAdd = angular.copy($scope.product);
                    productToAdd.colors = [$scope.colorSelected];
                    $scope.$parent.addProduct(productToAdd, $scope.quantity);
                }
            }

            $scope.changeImage = function(img){
                $scope.product.imageUrl = img;
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
