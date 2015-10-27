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
    './app/directives/index'
], function(angular) {
    // Declare app level module which depends on views, and components
    return angular.module('aos', ['aos.controllers', 'aos.services', 'aos.directives', 'pascalprecht.translate', 'ngRoute'])
        .config(function($translateProvider, $routeProvider, $locationProvider) {
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
            SPECIAL_OFFER_ITEMS : 'SPACIAL OFFER ITEMS',
            SHOP_NOW : 'SHOP NOW',
            POPULAR_ITEMS : 'POPULAR ITEMS',
            FOLLOW_US : 'FOLLOW US',
            'LEGALS_FOOTER' : '© 2015 Hewlett-Packard Development Company.'
        });
            $routeProvider.
                when('/AddNewOrder', {
                    templateUrl: 'templates/add_order.html',
                    controller: 'AddOrderController'
                }).
                when('/category/:id', {
                    controller: 'categoryCtrl',
                    templateUrl: './app/views/category.html',
                }).
                when('/', {
                    controller: 'categoriesCtrl',
                    templateUrl: './app/views/home-page.html',
                }).
                otherwise({
                    redirectTo: '/',
                    controller: 'categoriesCtrl',
                    templateUrl: './app/views/main1.html',
                });
        $translateProvider.preferredLanguage('en');
    });
});