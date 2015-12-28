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
                getCart: getCart,
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
                updateCart();
                responce.resolve(cart);
                return responce.promise;
            }

            function getCart(){
                var responce = $q.defer();
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
                        return $http({
                            method: "get",
                            async: false,
                            url: server.order.loadCartProducts(user.response.userId)
                        }).then(function(res){
                            console.log("data")
                            console.log(res)
                            cart = res.data;
                            responce.resolve(cart);
                            return responce.promise;
                        });
                        validUser = true;
                    }
                }
                if(!validUser)
                {
                    loadGuestCartProducts();
                    console.log("");
                    console.log("");
                    console.log("");
                    console.log("cart");
                    console.log(cart);
                    responce.resolve(cart);
                    return responce.promise;
                }
            }

            function loadGuestCartProducts(){

                cart = $cookie("userCart");
                if(!cart){
                    cart = getTempCart();
                    updateCart();
                }
            }

            function joinCartProducts(userCart){
                return;
                var guestCart = loadGuestCartProducts();
                var tempCart = [];
                angular.forEach(userCart.productsInCart, function(userProduct){
                    var find = false;
                    angular.forEach(guestCart.productsInCart, function(guestProduct) {
                        if(userProduct.id == guestProduct.id && userProduct.color == guestProduct.color)
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

                userCart.productsInCart = tempCart;
                cart = userCart;
                updateUserCart(userCart.productsInCart);
                $cookie.remove("userCart");
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
                    loadGuestCartProducts();
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

            function updateCart(){
                $cookie("userCart", cart , { expires: 365*5 });
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
                            console.log(newCart)
                            cart = newCart.data;
                            response.resolve(newCart);
                            return response.promise;
                        })

                    }
                }
                else {
                    var find = null;
                    var productIndex = 0;
                    angular.forEach(cart.productsInCart, function (productInCart, index) {
                        if (product.productId == productInCart.id) {
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
                    updateCart();
                    response.resolve(cart);
                    return response.promise;
                }
            }
        }]);



});