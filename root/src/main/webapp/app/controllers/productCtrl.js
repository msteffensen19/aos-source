/**
 * Created by correnti on 21/11/2015.
 */

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('productCtrl', ['$scope', 'resolveParams', '$state', '$filter', '$rootScope',

        function (s, resolveParams, $state, $filter, $rootScope) {

            s.pageState = resolveParams.pageState;
            var resolveParams_selectedColor = resolveParams.selectedColor;

            s.message = null;
            s.quantity = resolveParams.quantity || 1;
            s.categoryName = resolveParams.categoryName;
            s.product = resolveParams.product;


            s.imageUrl = angular.copy(s.product.imageUrl);

            s.getFirstImageUrl = function () {
                s.imagesArray = $filter("getAllImagesByColor")(s.product.images, s.colorSelected, s.product.imageUrl);
                s.imagesArray.push(s.product.imageUrl);
                s.imageUrl = s.imagesArray[0];
                return s.imagesArray;
            }

            s.product_attributes = Helper.sortAttributesByName(s.product.attributes);

            if (!resolveParams.selectedColor && s.product.colors.length > 0) {
                var colors = Helper.sortArrayByColorName(s.product.colors);
                s.colorSelected = colors[0];
            }
            else {
                for (var i = 0; i < s.product.colors.length; i++) {
                    if (s.product.colors[i].code == resolveParams.selectedColor) {
                        s.colorSelected = s.product.colors[i];
                        break;
                    }
                }
            }

            s.colorSelected = s.colorSelected || s.product.colors[0];
            s.getFirstImageUrl();

            s.addToCart = function (disable) {

                if (disable) {
                    return;
                }

                var productToAdd = angular.copy(s.product);
                productToAdd.colors = [s.colorSelected];

                if (s.pageState == 'edit') {
                    s.$parent.updateProduct(productToAdd, s.colorSelected, s.quantity, resolveParams_selectedColor, $filter("translate")("toast_Product_Updated_Successfully"));
                    $state.go('shoppingCart');
                }
                else {
                    var quantity = s.quantity;

                    var user = $rootScope.userCookie;
                    if (!(user && user.response && user.response.userId != -1)) {
                        for (var i = 0; i < s.$parent.cart.productsInCart.length; i++) {
                            var prod = s.$parent.cart.productsInCart[i];
                            if (prod.productId == productToAdd.productId && prod.color.code == s.colorSelected.code) {
                                if (prod.quantity + quantity > s.product.colors[0].inStock) {
                                    quantity = s.product.colors[0].inStock - prod.quantity;
                                }
                            }
                        }
                    }
                    if (quantity > 0) {
                        var request = s.$parent.addProduct(productToAdd, quantity, $filter("translate")("toast_Product_Added_Successfully"));
                        request.then(function (res) {
                            console.log(" ======== ===== ===== ==== === res (add product) === ==== ===== ====== ")
                            console.log(res)
                            console.log(" ======== ===== ===== ==== === res (add product) === ==== ===== ====== ")
                        });
                    }
                }
            };

            s.changeImage = function (img) {
                s.imageUrl = img;
            };

            s.setColor = function (color) {
                s.imageUrl = s.product.imageUrl;
                s.colorSelected = color;
                s.getFirstImageUrl();
            };

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
