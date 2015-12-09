/**
 * Created by kubany on 10/13/2015.
 */
'use strict';

define([

    'angular',
    'jquery',
    'bootstrap',
    'jPushMenu',
    './app/catalog/config/catalogConfig',
    './app/user/userConfig',
    './app/controllers/index',
    './app/user/controllers/index',
    './app/user/services/index',
    './app/services/index',
    './app/directives/index',
    './app/filters/index',
    './app/user/directives/index',
    './app/templates/module',

], function(angular, templates, bootstrap, jPushMenu, catalogConfig, userConfig) {
    // Declare app level module which depends on views, and components
    return angular.module('aos', ['aos.controllers', 'aos.services', 'aos.directives','aos.filters',
        'aos.templates', 'pascalprecht.translate', 'ui.router', 'ui.bootstrap', 'ipCookie',
        'ngAnimate','aos.user.controllers', 'aos.user.services', 'aos.user.directives'])

        .config(catalogConfig).config(userConfig)

        .run(function ($rootScope, $state, ipCookie, productsCartService) {

            $rootScope.cartProducts = null;

            var pcBlocked = ipCookie("pcBlocked");
            if(pcBlocked)
            {
                if(new Date().getTime() > pcBlocked) {
                    ipCookie.remove("pcBlocked");
                }
                else{
                    $state.go('404');
                }
            }

            $rootScope.userCookieLastEntry = ipCookie('lastlogin');
            if($rootScope.userCookieLastEntry)
            {
                var cookie = ipCookie($rootScope.userCookieLastEntry);
                if(cookie)
                {
                    $rootScope.userCookie = cookie;
                }
            }


            productsCartService.loadCartProducts().then(function(response){
                $rootScope.cartProducts = response;
                console.log($rootScope.cartProducts);
                console.log(JSON.stringify($rootScope.cartProducts));
            });



            $rootScope.$on('$stateChangeError', function(event) {
                $state.go('404');
            });


            $rootScope.$on('$stateChangeStart', function (event, toState, toParams) {
                var requireLogin = toState.data.requireLogin;
                var showWelcome = toState.data.showWelcome;
                var underConstruction = toState.data.underConstruction;

                showWelcome != 'undefined' && showWelcome ? $(document.body).addClass('welcome-page') : $(document.body).removeClass('welcome-page');
                underConstruction != 'undefined' && underConstruction ?
                    $(document.body).addClass('under-construction') :
                    $(document.body).removeClass('under-construction');

                if (requireLogin && typeof $rootScope.currentUser === 'undefined') {
                    event.preventDefault();
                    // get me a login modal!
                }
            });
        });
});