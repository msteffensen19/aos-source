/**
 * Created by correnti on 06/12/2015.
 */

define(['./module'], function (services) {
    'use strict';
    services.service('productsCartService', ['$http', '$q', 'resHandleService', 'ipCookie', '$rootScope',
        function ($http, $q, responseService, $cookie, $rootScope) {

            var responce = $q.defer();
            var cart = null;
            function getTempCart(){ return { "userId": -1, "productsInCart": [], } }

            return({
                addProduct : addProduct,
                updateProduct : updateProduct,
                loadCartProducts : loadCartProducts,
                joinCartProducts : joinCartProducts,
                removeProduct: removeProduct,
                checkout: checkout,
                getCart : getCart
        });

            /* returned functions */

            function getCart() {

                var responce = $q.defer();
                var user = $rootScope.userCookie;
                if (user && user.response) {
                    if (user.response.userId != -1) {
                        $http({
                            method: "get",
                            async: false,
                            headers: {
                                "content-type": "application/json",
                                "Authorization": "Bearer " + user.response.token,
                            },
                            url: server.order.loadCartProducts(user.response.userId)
                        }).success(function (res) {
                            cart = res;
                            responce.resolve(cart);
                        }).error(function (err) {
                            alert('err')
                            responce.reject('error in load cart (productCartService - loadCartProducts)');
                        });
                    }
                }
                else {
                    responce.resolve(null);
                }
                return responce.promise;
            }

            function checkout() {
                // check if user is login

                var responce = $q.defer();
                responce.resolve(false);
                return responce.promise;
            }

            function removeProduct(index){
                var responce = $q.defer();
                var user = $rootScope.userCookie;
                var prod = cart.productsInCart[index];
                cart.productsInCart.splice(index, 1);
                if (user && user.response && user.response.userId != -1) {
                    $http({
                        method: "delete",
                        url: server.order.removeProductToUser(user.response.userId, prod.productId, prod.color.code),
                        headers: {
                            "content-type": "application/json",
                            "Authorization": "Bearer " + user.response.token,
                        }
                    });
                }
                else{
                    updateCart(cart);
                }
                responce.resolve(cart);
                return responce.promise;
            }

            function loadCartProducts() {

                var responce = $q.defer();
                var user = $rootScope.userCookie;
                if (user && user.response) {
                    if (user.response.userId != -1) {
                        $http({
                            method: "get",
                            async: false,
                            headers: {
                                "content-type": "application/json",
                                "Authorization": "Bearer " + user.response.token,
                            },
                            url: server.order.loadCartProducts(user.response.userId)
                        }).success(function (res) {
                            cart = res;
                            responce.resolve(cart);
                        }).error(function (err) {
                            alert('err')
                            responce.reject('error in load cart (productCartService - loadCartProducts)');
                        });
                    }
                }
                else {
                    cart = loadGuestCartProducts();
                    responce.resolve(cart);
                }
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

                var defer = $q.defer();
                loadCartProducts().then(function (_cart) {
                    cart = _cart;
                    var guestCart = loadGuestCartProducts();

                    var tempCart = [];
                    angular.forEach(cart.productsInCart, function(userProduct){
                        var find = false;
                        angular.forEach(guestCart.productsInCart, function(guestProduct) {
                            if(userProduct.productId == guestProduct.productId && userProduct.color.code == guestProduct.color.code)
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
                    updateUserCart(cart);
                    $cookie.remove("userCart");
                    defer.resolve(cart);
                })
                return defer.promise;
            }


            function updateUserCart(){

                var user = $rootScope.userCookie;
                if(user && user.response) {
                    if (user.response.userId != -1) {

                        var cartToReplace = [];
                        angular.forEach(cart.productsInCart, function(product){
                            cartToReplace.push({
                                "hexColor": product.color.code,
                                "productId": product.productId,
                                "quantity": product.quantity,
                            });
                        })
                        if(cartToReplace.length > 0)
                        {
                            $http({
                                method: "put",
                                data : JSON.stringify(cartToReplace),
                                headers: {
                                  "content-type": "application/json",
                                  "Authorization": "Bearer " + user.response.token,
                                },
                                url: server.order.updateUserCart(user.response.userId)
                            }).success(function(res){
                                console.log(res);
                            }).error(function(_err){
                                console.log("updateUserCart() rejected!  ====== " + _err)
                            });
                        }
                    }
                }
            }

            function updateCart(guestCart){
                $cookie("userCart", guestCart, { expires: 365*5 });
            }

            function updateProduct(product) {
               return addProduct(product, product.quantity)
            }

            function addProduct(product, quantity) {
                var response = $q.defer();
                var user = $rootScope.userCookie;
                if (user && user.response) {
                    if (user.response.userId != -1) {
                        var request = $http({
                            method: "post",
                            headers: {
                                "content-type": "application/json",
                                "Authorization": "Bearer " + user.response.token,
                            },
                            async: false,
                            url: server.order.addProductToUser(user.response.userId,
                                product.productId, product.colors[0].code, quantity),
                        });
                        request.then(function (newCart) {
                            cart = newCart.data;
                            response.resolve(cart);
                            return response.promise;
                        })
                        return response.promise;
                    }
                }
                else {
                    var find = null;
                    var thisIsUpdateMode = false;
                    var productIndex = 0;
                    angular.forEach(cart.productsInCart, function (productInCart, index) {
                        if (product.productId == productInCart.productId) {
                            if(product.colors == undefined){
                                thisIsUpdateMode = true;
                                productInCart.quantity = product.quantity;
                            }
                            else{
                                angular.forEach(product.colors, function (color) {
                                    if (productInCart.color.code == color.code) {
                                        productIndex = index;
                                        productInCart.quantity += quantity;
                                        find = product;
                                    }
                                });
                            }
                        }
                    });

                    if(!thisIsUpdateMode) {
                        if (!find) {
                            var color;
                            if (product.colors == undefined) {
                                color = product.color;
                            }
                            else {
                                color = product.colors.length > 0 ? product.colors[0] : 'FFFFFF';
                            }
                            cart.productsInCart.unshift({
                                "productId": product.productId,
                                "imageUrl": product.imageUrl,
                                "productName": product.productName,
                                "color": color,
                                "quantity": quantity,
                                "price": product.price
                            });
                        }
                        else {
                            cart.productsInCart.splice(0, 0, cart.productsInCart.splice(productIndex, 1)[0]);
                        }
                    }
                    updateCart(cart);
                    response.resolve(cart);
                    return response.promise;
                }
            }
        }]);



});