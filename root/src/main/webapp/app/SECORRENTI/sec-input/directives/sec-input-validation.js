
/**
 * Created by correnti on 19/05/2016.
 */


define(['./../../../directives/module'], function (directives) {
    'use strict';
    directives.directive('secInputValidation', ['$templateCache', function ($templateCache) {
        return {
            restrict: 'E',
            replace: true,
            scope: {
                modelAttr : '=',
                idAttr: '@',
                secChange: '&',
            },
            template: $templateCache.get('app/SECORRENTI/sec-input/views/sec-input-validation.html'),
            link: function (scope, element, attr) {

                element.addClass("sec-input-validation")

                var input;

                //this.setValidation = function(element){
                //    input = element;
                //};

            }
        };
    }]);
});


