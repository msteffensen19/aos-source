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
], function(angular) {
    // Declare app level module which depends on views, and components
    return angular.module('aos', ['aos.controllers', 'aos.services', 'pascalprecht.translate']).config(function($translateProvider) {
        $translateProvider.useSanitizeValueStrategy('escapeParameters');
        $translateProvider.translations('en', {
            OUR_PRODUCTS : 'Our Products',
            HOT_PRODUCTS : 'Hot Products',
            DEAL_OF_THE_DAY : 'Deal of the Day',
            CONTACT_US : 'Contact Us',
            AOS : 'AOS',
            LOGIN: 'Login',
            LOGOUT: 'This is a paragraph.',
            SPACIAL_OFFER : 'SPACIAL OFFER',
            SHOP_NOW : 'SHOP NOW'
        });
        $translateProvider.preferredLanguage('en');
    });
});