/**
 * Created by correnti on 21/11/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('productCtrl', ['$scope', 'productService', 'product',
        'selectedColor', 'quantity', 'pageState', 'categoryService',
        function ($scope, productService, product, selectedColor, quantity, pageState, categoryService) {

            l("product")

            l(product)
            categoryService.getCategoryById(product.categoryId).then(function(res){
                $scope.categoryName = res.categoryName;
            });

            l("")
            l("")

            for(var i = 0; i < product.colors.length; i++){
                if(product.colors[i].code == selectedColor)
                {
                    $scope.colorSelected = product.colors[i];
                    break;
                }
            }

            $scope.quantityOptions = [1,2,3,4,5,6,7,8,9];
            $scope.quantity = quantity || 1;
            if(quantity && quantity > 9)
            {
                for(var i = 10; i <= quantity; i++)
                {
                    $scope.quantityOptions.push(i)
                }
            }

            $scope.product = product;
            $scope.colorSelected = $scope.colorSelected || product.colors[0];

            $scope.addToCart = function(){
                l($scope.cart)
                if(pageState == 'edit')
                {

                }
                else
                {
                var productToAdd = angular.copy(product);
                productToAdd.colors = [$scope.colorSelected];
                    $scope.$parent.addProduct(productToAdd, $scope.quantity);
            }
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
