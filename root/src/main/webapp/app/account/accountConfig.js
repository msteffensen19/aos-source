/**
 * Created by correnti on 31/12/2015.
 */

define([],function(){

    function config($stateProvider) {

        $stateProvider.
        state('myAccount', {
            url: '/myAccount',
            templateUrl: 'app/account/views/myAccount-page.html',
            controller: 'myAccountCtrl',
            controllerAs: 'maCtrl',
            resolve : {
                resolveParams: function ($q) {
                    return "HOLA MUNDO";
                }
            }
        })
    }

    return ['$stateProvider', config];

});




//var defer = $q.defer();
//orderService.getAccountById().
//then(function (user) {
//    if(user)
//    {
//        orderService.getShippingCost(user).
//        then(function (shippingCost) {
//
//            defer.resolve({
//                shippingCost : shippingCost,
//                user : user,
//                noCards: true,
//                CardNumber: [],
//            });
//        });
//    }
//    else {
//        defer.resolve({
//            shippingCost : null,
//            user : null
//        });
//    }
//});
//return defer.promise;




