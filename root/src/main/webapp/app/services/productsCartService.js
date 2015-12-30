/**
 * Created by correnti on 06/12/2015.
 */

define(['./module'], function (services) {
    'use strict';
    services.service('productsCartService', ['$http', '$q', 'resHandleService', 'ipCookie', '$rootScope',
        function ($http, $q, responseService, $cookie, $rootScope) {

            var responce = $q.defer();
            var cart = null;
            function getTempCart(){
                return {
                    "userId": -1,
                    "productsInCart": [],
                }
            }

            return({
                addProduct : addProduct,
                loadCartProducts : loadCartProducts,
                joinCartProducts : joinCartProducts,
                updateRemovedProducts : updateRemovedProducts,
                removeProduct: removeProduct,
                checkout: checkout,
            });

            /* returned functions */

            function checkout() {
                console.log('cart');
                console.log(cart);

                // check if user is login

                var responce = $q.defer();
                responce.resolve(false);
                return responce.promise;
            }

            function removeProduct(index){
                var responce = $q.defer();
                cart.productsInCart.splice(index, 1);
                updateCart(cart);
                responce.resolve(cart);
                return responce.promise;
            }

            function updateRemovedProducts(cart) {
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
                var validUser = false;
                if(user && user.response) {
                    if (user.response.userId != -1) {
                        var responce = $http({
                            method: "get",
                            async: false,
                            url: server.order.loadCartProducts(user.response.userId)
                        })
                        var request = $http({
                            method: "get",
                            url: server.catalog.getDeals(),
                        });
                        return( request.then(
                        function(res){
                            console.log("data")
                            console.log(res)
                            cart = res;
                            responce.resolve(cart);
                        },
                        function(err){
                            alert('err')
                            responce.reject('error in load cart (productCartService - loadCartProducts)');
                        }));

                        return responce.promise;
                    }
                }
                cart = loadGuestCartProducts();
                responce.resolve(cart);
                return responce.promise;
            }

            function loadGuestCartProducts(){

                var guestCart = $cookie("userCart");
                if(!guestCart){
                    guestCart = getTempCart();
                    updateCart(guestCart);
                }
                return guestCart;
            }

            function joinCartProducts() {

                if (!cart) {
                    loadCartProducts().then(function (_cart) {
                            cart = _cart.data;
                        return joinCarts();
                    })
                    return null;
                }
                return joinCarts();
            }

            function joinCarts(){

                var guestCart = loadGuestCartProducts();
                var tempCart = [];
                angular.forEach(cart.productsInCart, function(userProduct){
                    var find = false;
                    angular.forEach(guestCart.productsInCart, function(guestProduct) {
                        if(userProduct.productId == guestProduct.productId && userProduct.color == guestProduct.color)
                        {
                            find = true;
                        }
                    });
                    if(!find){
                        tempCart.push(userProduct);
                    }
                });
                angular.forEach(guestCart.productsInCart, function(guestProduct) {
                    tempCart.push(guestProduct);
                });

                cart.productsInCart = tempCart;
                updateUserCart(cart.productsInCart);
                $cookie.remove("userCart");
                return cart;
            }

            function loadCartProducts(){

                var responce = $q.defer();
                var user = $rootScope.userCookie;
                if(user && user.response) {
                    if (user.response.userId != -1) {
                        $http({
                            method: "get",
                            url: server.order.loadCartProducts(user.response.userId)

                        }).then(function(cartRes){
                                alert('cartRes')
                                console.log('cartRes')
                                console.log(cartRes)
                                cart = cartRes;
                                responce.resolve(cart);
                            },
                            function(err){
                                alert('err')
                                console.log('error in load cart (productCartService - loadCartProducts)');
                            });
                    }
                }
                else {
                    cart = loadGuestCartProducts();
                    responce.resolve(cart);
                }
                return responce.promise;
            }
            function updateUserCart(){
                var user = $rootScope.userCookie;
                if(user && user.response) {
                    if (user.response.userId != -1) {
                        $http({
                            method: "get",
                            //data : JSON.stringify(cart);
                            url: "app/cartProducts.json"
                        });
                        return true;
                    }
                }
                return false;
            }

            function updateCart(guestCart){
                $cookie("userCart", guestCart, { expires: 365*5 });
            }

            function addProduct(product, quantity) {

                var response = $q.defer();
                var user = $rootScope.userCookie;
                if (user && user.response) {
                    if (user.response.userId != -1) {
                        var request = $http({
                            method: "post",
                            async: false,
                            url: server.order.addProductToUser(user.response.userId,
                                product.productId, product.colors[0].code, quantity),
                        });
                        request.then(function (newCart) {
                            console.log("newCart")
                            console.log(newCart)
                            cart = newCart.data;
                            response.resolve(cart);
                            return response.promise;
                        })

                        return response.promise;
                    }
                }
                else {
                    var find = null;
                    var productIndex = 0;
                    angular.forEach(cart.productsInCart, function (productInCart, index) {
                        if (product.productId == productInCart.productId) {
                            angular.forEach(product.colors, function (color) {
                                if (productInCart.color.code == color.code) {
                                    productIndex = index;
                                    productInCart.quantity += quantity;
                                    find = product;
                                }
                            });
                        }
                    });

                    if (!find) {
                        cart.productsInCart.unshift({
                            "productId": product.productId,
                            "imageUrl": product.imageUrl,
                            "productName": product.productName,
                            "color": product.colors.length > 0 ? product.colors[0] : 'FFF',
                            "quantity": quantity,
                            "price": product.price
                        });
                    }
                    else {
                        cart.productsInCart.splice(0, 0, cart.productsInCart.splice(productIndex, 1)[0]);
                    }
                    updateCart(cart);
                    response.resolve(cart);
                    return response.promise;
                }
            }
        }]);



});