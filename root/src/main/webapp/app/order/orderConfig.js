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
                            alert()
                            var paramsToResolve = {
                                userLogin: true,
                                shippingCost : 10 //userLogin
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

