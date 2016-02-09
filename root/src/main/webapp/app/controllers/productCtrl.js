/**
 * Created by correnti on 21/11/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('productCtrl', ['$scope', 'resolveParams', '$state',

        function (s, resolveParams, $state) {

            s.pageState = resolveParams.pageState;
            var resolveParams_selectedColor = resolveParams.selectedColor;

            s.message = null;
            s.quantity = resolveParams.quantity || 1;
            s.categoryName = resolveParams.categoryName;
            s.product = resolveParams.product;

            for(var i = 0; i < s.product.colors.length; i++){
                if(s.product.colors[i].code == resolveParams.selectedColor)
                {
                    s.colorSelected = s.product.colors[i];
                    break;
                }
            }
            s.colorSelected = s.colorSelected || s.product.colors[0];

            s.addToCart = function(){
                var productToAdd = angular.copy(s.product);
                productToAdd.colors = [s.colorSelected];
                if(s.pageState == 'edit'){
                    s.$parent.updateProduct(productToAdd, s.colorSelected, s.quantity,resolveParams_selectedColor);
                    $state.go('shoppingCart');
                }
                else {
                    s.$parent.addProduct(productToAdd, s.quantity);
                }
            }

            s.changeImage = function(img){
                s.product.imageUrl = img;
            }

            s.setColor = function(color){
                s.colorSelected = color;
            }

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
