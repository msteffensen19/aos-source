/**
 * Created by kubany on 10/13/2015.
 */
define(['angular'], function (angular) {
    'use strict';
    return angular.module('aos.mobile.controllers', [])


        .controller('mainMobCtrl', [ '$scope', 'productService', 'smoothScroll', '$location', 'ipCookie', '$rootScope', 'productsCartService', '$filter', '$state', mainMobCtrl])



        .controllers.controller('categoriesMobCtrl', ['$scope', 'resolveParams', categoriesMobCtrl])






    ;

});