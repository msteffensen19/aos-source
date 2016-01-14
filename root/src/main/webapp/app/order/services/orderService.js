/**
 * Created by correnti on 10/01/2016.
 */

define(['./module'], function (services) {
    'use strict';
    services.service('orderService',['$rootScope', '$q', 'mini_soap', '$http', '$filter', 'productsCartService',

        function ($rootScope, $q, mini_soap, $http, $filter, productsCartService) {

            return ({
                getAccountById: getAccountById,
                getShippingCost: getShippingCost,
                SafePay: SafePay,
                masterCredit: masterCredit,
                manualPayment : manualPayment
        });


            function SafePay(user, savePay, card, shipping, cart, accountNumber, TransPaymentMethod) {
                var defer = $q.defer();

                var purchasedProducts = [];
                angular.forEach(cart.productsInCart, function(product){
                    purchasedProducts.push({
                            "hexColor": product.color.code,
                            "productId": product.productId,
                            "quantity": product.quantity
                        });
                })

                var paramsToPass = {
                    "orderPaymentInformation": {
                        "Transaction_AccountNumber": accountNumber,
                        "Transaction_Currency": shipping.currency,
                        "Transaction_CustomerPhone": user.phoneNumber,
                        "Transaction_MasterCredit_CVVNumber": card.cvv,
                        "Transaction_MasterCredit_CardNumber": card.number,
                        "Transaction_MasterCredit_CustomerName": card.name,
                        "Transaction_MasterCredit_ExpirationDate": card.expirationDate.month + "" + card.expirationDate.year,
                        "Transaction_PaymentMethod": TransPaymentMethod,
                        "Transaction_ReferenceNumber": 0,
                        "Transaction_SafePay_Password": savePay.password,
                        "Transaction_SafePay_UserName": savePay.username,
                        "Transaction_TransactionDate": shipping.transactionDate,
                        "Transaction_Type": "PAYMENT"
                    },
                    "orderShippingInformation": {
                        "Shipping_Address_Address": user.address,
                        "Shipping_Address_City": user.cityName,
                        "Shipping_Address_CountryCode": user.countryId,
                        "Shipping_Address_CustomerName": user.firstName + " " + user.lastName,
                        "Shipping_Address_CustomerPhone": user.phoneNumber,
                        "Shipping_Address_PostalCode": user.zipcode,
                        "Shipping_Address_State": user.stateProvince,
                        "Shipping_Cost":  $filter('productsCartCount')(cart),
                        "Shipping_NumberOfProducts": $filter('productsCartSum')(cart),
                        "Shipping_TrackingNumber": 0
                    },
                    "purchasedProducts": purchasedProducts,
                }

                console.log(paramsToPass);
                console.log(JSON.stringify(paramsToPass));
                console.log(user);

                $http({
                    method: "post",
                    url: server.order.safePay(user.id),
                    data: paramsToPass,
                    //headers: {
                    //    "content-type": "application/json",
                    //    "Authorization": "Bearer " + user.response.token,
                    //},
                }).
                then(function (shippingCost){
                    defer.resolve(shippingCost.data.amount)
                }, function (err){
                    console.log(err); defer.reject("probl.")
                })
                return defer.promise;
            }

            function masterCredit(){

            }

            function manualPayment(){

            }


            function getAccountById() {

                var defer = $q.defer();
                var params = server.account.getAccountById();
                var user = $rootScope.userCookie;
                if (user && user.response && user.response.userId != -1) {

                    mini_soap.post(params.path, params.method, {
                            accountId: user.response.userId
                        })
                        .then(function (response) {
                            console.log(response)
                                var user = {
                                    "id": response.ID,
                                    "lastName": response.LASTNAME,
                                    "firstName": response.FIRSTNAME,
                                    "loginName": response.LOGINNAME,
                                    "countryId": response.COUNTRYID,
                                    "country": response.COUNTRYISONAME,
                                    "stateProvince": response.STATEPROVINCE,
                                    "cityName": response.CITYNAME,
                                    "address": response.ADDRESS,
                                    "zipcode": response.ZIPCODE,
                                    "phoneNumber": response.PHONENUMBER,
                                    "email": response.EMAIL,
                                }
                                defer.resolve(user);
                            },
                            function (response) {
                                console.log(response);
                                defer.reject("Request failed! (getAccountById)");
                            });
                }
                else{
                    defer.resolve(null);
                }

                return defer.promise;
            }

            function getShippingCost(user) {

                var defer = $q.defer();

                productsCartService.getCart().then(function(cart){

                    var paramsToPass = {
                        "seaddress": {
                            "addressLine1": user.address,
                            "addressLine2": "",
                            "city": user.cityName,
                            "country": user.country,
                            "postalCode": user.zipcode,
                            "state": user.stateProvince
                        },
                        "secustomerName": user.firstName + " " + user.lastName ,
                        "secustomerPhone": user.phoneNumber,
                        "senumberOfProducts": $filter('productsCartCount')(cart),
                        "setransactionType": "SHIPPINGCOST"
                    };
                    $http({
                        method: "post",
                        url: server.order.getShippingCost(),
                        data: paramsToPass
                    }).
                    then(function (shippingCost){
                        defer.resolve(shippingCost.data)
                    }, function (err){
                        console.log(err); defer.reject("probl.")
                    })
                })

                return defer.promise;
            }


            //function getShippingCost() {
            //
            //    var defer = $q.defer();
            //
            //    var paramsToPass = {
            //        "seaddress": {
            //            "addressLine1": "string",
            //            "addressLine2": "string",
            //            "city": "string",
            //            "country": "string",
            //            "postalCode": "string",
            //            "state": "string"
            //        },
            //        "secustomerName": "string",
            //        "secustomerPhone": "string",
            //        "senumberOfProducts": 0,
            //        "setransactionType": "string"
            //    }
            //
            //
            //    function getProducts() {
            //        var request = $http({
            //            method: "get",
            //            url: server.order.getShippingCost(),
            //            data: paramsToPass
            //        });
            //        return( request.then( responseService.handleSuccess, responseService.handleError ) );
            //    }
            //
            //    var user = $rootScope.userCookie;
            //    if (user && user.response && user.response.userId != -1) {
            //
            //        mini_soap.post(params.path, params.method, {
            //                accountId: user.response.userId
            //            })
            //            .then(function (response) {
            //                    var user = {
            //                        "id": response.ID,
            //                        "lastName": response.LASTNAME,
            //                        "firstName": response.FIRSTNAME,
            //                        "loginName": response.LOGINNAME,
            //                        "country": response.COUNTRY,
            //                        "stateProvince": response.STATEPROVINCE,
            //                        "cityName": response.CITYNAME,
            //                        "address": response.ADDRESS,
            //                        "zipcode": response.ZIPCODE,
            //                        "phoneNumber": response.PHONENUMBER,
            //                        "email": response.EMAIL,
            //                    }
            //                    defer.resolve(user);
            //                },
            //                function (response) {
            //                    console.log(response);
            //                    defer.reject("Request failed! (getAccountById)");
            //                });
            //    }
            //    else{
            //        defer.resolve(null);
            //    }
            //
            //    return defer.promise;
            //}
            //


        }]);
});

