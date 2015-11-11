/**
 * Created by kubany on 10/13/2015.
 */
'use strict';

define([
    'angular',
    'jquery',
    'bootstrap',
    'jPushMenu',
    './app/controllers/index',
    './app/services/index',
    './app/directives/index',
    './app/templates/module',
], function(angular, templates) {
    // Declare app level module which depends on views, and components
    return angular.module('aos', ['aos.controllers', 'aos.services', 'aos.directives', 'aos.templates', 'pascalprecht.translate', 'ngRoute', 'ngAnimate'])
        .config(function($translateProvider, $routeProvider, $locationProvider, $httpProvider) {
            $httpProvider.defaults.cache = true;
        $translateProvider.useSanitizeValueStrategy('escapeParameters');
        $translateProvider.translations('en', {
            OUR_PRODUCTS : 'OUR PRODUCTS',
            HOT_PRODUCTS : 'Hot Products',
            DEAL_OF_THE_DAY : 'Deal of the Day',
            CONTACT_US : 'Contact Us',
            AOS : 'AOS',
            LOGIN: 'Login',
            LOGOUT: 'This is a paragraph.',
            SPACIAL_OFFER : 'SPECIAL OFFER',
            SPECIAL_OFFER_ITEMS : 'SPECIAL OFFER ITEMS',
            'SHOP_NOW' : 'SHOP NOW',
            POPULAR_ITEMS : 'POPULAR ITEMS',
            FOLLOW_US : 'FOLLOW US',
            'LEGALS_FOOTER' : '© Advantage Inc, 2016.',
            'BY' : 'BY',
            'ADD_TO_CART' : 'ADD TO CART',
            'HOME' : 'HOME',
            'BUY_NOW' : 'BUY NOW',
            'STARTING_AT' : 'Starting at',
            'SEE_OFFER' : 'SEE OFFER'
        });
            $routeProvider.
                when('/AddNewOrder', {
                    templateUrl: 'templates/add_order.html',
                    controller: 'AddOrderController'
                }).
                when('/category/:id', {
                    controller: 'categoryCtrl',
                    templateUrl: './app/views/category-page.html',
                }).
                when('/', {
                    controller: 'categoriesCtrl',
                    templateUrl: 'app/views/home-page.html',
                }).
                when('/welcome', {
                    templateUrl: 'app/views/welcome.html',
                }).
                otherwise({
                    redirectTo: '/',
                    controller: 'categoriesCtrl',
                    templateUrl: './app/views/home-page.html',
                });
        $translateProvider.preferredLanguage('en');
    });
});