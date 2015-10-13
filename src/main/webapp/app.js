/**
 * Created by kubany on 10/13/2015.
 */
'use strict';

define([
    'angular',
    './app/controllers/index',
    './app/services/index'
], function(angular) {
    // Declare app level module which depends on views, and components
    return angular.module('aos', ['aos.controllers', 'aos.services']);
});
