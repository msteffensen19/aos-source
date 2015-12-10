/**
 * Created by correnti on 06/12/2015.
 */

define(['./module'], function (services) {
    'use strict';
    services.service('productsCartService', ['$http', '$q', 'resHandleService', 'ipCookie', '$rootScope',
        function ($http, $q, responseService, $cookie, $rootScope) {

            var responce = $q.defer();

            return({
                addProduct : addProduct,
                loadCartProducts : loadCartProducts,
                joinCartProducts : joinCartProducts,
                updateRemovedProducts : updateRemovedProducts
            })

            function updateRemovedProducts(cart)
            {
                var success = updateUserCart(cart);
                if(!success)
                {
                    updateCart(cart)
                }

                var response = $q.defer()
                response.resolve('no comments');
                return response.promise;
            }

            function loadCartProducts(){

                var responce = $q.defer();

                var user = $rootScope.userCookie;
                if(user && user.response) {
                    if (user.response.userId != -1) {
                        var request = $http({
                            method: "get",
                            url: "app/cartProducts.json"
                        });
                        return( request.then(responseService.handleSuccess, responseService.handleError ));
                    }
                }

                var guestCart = loadGuestCartProducts();
                responce.resolve(guestCart);
                return responce.promise;
            }

            function loadGuestCartProducts(){

                var usercart = $cookie("userCart");
                if(!usercart){
                    var guestCart = getTempCart();
                    updateCart(guestCart)
                    return guestCart;
                }
                return usercart;
            }

            function joinCartProducts(userCart){
                var guestCart = loadGuestCartProducts();
                var cart = [];
                angular.forEach(userCart.productsInCart, function(userProduct){
                    var find = false;
                    angular.forEach(guestCart.productsInCart, function(guestProduct) {
                        if(userProduct.id == guestProduct.id && userProduct.color == guestProduct.color)
                        {
                            find = true;
                        }
                    });
                    if(!find){
                        cart.push(userProduct);
                    }
                });
                angular.forEach(guestCart.productsInCart, function(guestProduct) {
                    cart.push(guestProduct);
                });

                userCart.productsInCart = cart;
                $rootScope.cartProducts = userCart;
                updateUserCart(userCart.productsInCart)
                $cookie.remove("userCart")
            }


            function updateUserCart(cart){
                var user = $rootScope.userCookie;
                if(user && user.response) {
                    if (user.response.userId != -1) {
                        $http({
                            method: "get",
                            url: "app/cartProducts.json"
                        });
                        return true;
                    }
                }
                return false;
            }


            function updateCart(cart){
                $cookie("userCart", cart , { expires: 365*5 })
                $rootScope.cartProducts = cart;
            }




            function addProduct(product, quantity) {

                var response = $q.defer();
                var user = $rootScope.userCookie;
                if(user)
                {
                    if(user.response)
                    {
                        if(user.response.userId != -1)
                        {
                            var request = $http({
                                method: "get",
                                url: "send to user product"
                            });
                            request.then(function(newCart){
                                updateCart(newCart)
                                response.resolve({reason: '', success: true});
                                return response.promise;
                            })
                            response.resolve(false);
                            return response.promise;
                        }
                        alert("ckeck this point");
                    }
                }

                $rootScope.cartProducts.productsInCart.push({
                    "id": product.productId,
                    "imageUrl": product.imageUrl,
                    "productName": product.productName,
                    "color" : product.colors.length > 0 ? product.colors[0] : 'FFF',
                    "quantity": quantity,
                    "price": product.price
                });
                updateCart($rootScope.cartProducts)

                response.resolve(false);
                return response.promise;
            }
        }]);


    function getTempCart(){
        return {
            "userId": -1,
            "productsInCart": [],
            "you_may_also_be_interested": []
        }
    }
});