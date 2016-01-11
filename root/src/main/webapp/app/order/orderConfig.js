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
                   // cartService.checkout().then(function (userLogin) {

                    orderService.getAccountById().
                    then(function (user) {

                        orderService.getShippingCost(user).
                        then(function (shippingCost) {

                            var paramsToResolve = {
                                shippingCost : shippingCost,
                                user : user
                            }
                            defer.resolve(paramsToResolve);

                        });
                    });
                    return defer.promise;
                }
            }
        });

    }

    config.$inject=['$stateProvider'];

    return config;

});










//$routeProvider.
//    when('/AddNewOrder', {
//        templateUrl: 'templates/add_order.html',
//        controller: 'AddOrderController'
//    }).
//    when('/category/:id', {
//        controller: 'categoryCtrl',
//        templateUrl: './app/views/category-page.html',
//    }).
//    when('/', {
//        controller: 'categoriesCtrl',
//        templateUrl: 'app/views/home-page.html',
//    }).
//    when('/welcome', {
//        templateUrl: 'app/views/welcome.html',
//    }).
//    otherwise({
//        redirectTo: '/',
//        controller: 'categoriesCtrl',
//        templateUrl: './app/views/home-page.html',
//    });