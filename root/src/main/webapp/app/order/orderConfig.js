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
                requireLogin: true,  // this property will apply to all children of 'app'
                breadcrumbName: "orderPayment",
            },
            resolve : {
                resolveParams: function ($q, orderService) {
                    var defer = $q.defer();

                    orderService.getAccountById()
                        .then(function (user) {
                            var paramsToResolve = {
                                shippingCost : 10, //userLogin
                                user : user
                            }
                            defer.resolve(paramsToResolve);
                    });
                    return defer.promise;
                }
            }
        });

    }

    return ['$stateProvider', config];

});

