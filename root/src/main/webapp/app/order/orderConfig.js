/**
 * Created by correnti on 31/12/2015.
 */

define([],function(){

    function config($stateProvider) {

        $stateProvider.
        state('orderPayment', {
            url: '/orderPayment',
            templateUrl: 'app/order/views/orderPayment-page.html',
            controller: 'orderPaymentCtrl',
            controllerAs: 'opCtrl',
            data: {
                //requireLogin: true,  // this property will apply to all children of 'app'
                breadcrumbName: "orderPayment",
            },
            resolve : {
                resolveParams: function ($q, orderService) {
                    var defer = $q.defer();

                    orderService.getAccountById().
                    then(function (user) {
                        if(user)
                        {
                            orderService.getShippingCost(user).
                            then(function (shippingCost) {

                                defer.resolve({
                                    shippingCost : shippingCost,
                                    user : user,
                                    noCards: true,
                                    CardNumber: [],
                                });
                            });
                        }
                        else {
                            defer.resolve({
                                shippingCost : null,
                                user : null
                            });
                        }
                    });
                    return defer.promise;
                }
            }
        }).
        state('userNotLogin', {
            url: '/userNotLogin',
            templateUrl: 'app/order/views/user-not-login-page.html',
            controller: 'userNotLoginCtrl',
        });
    }

    return ['$stateProvider', config];

});

