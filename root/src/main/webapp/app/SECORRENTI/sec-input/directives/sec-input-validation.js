/**
 * Created by correnti on 19/05/2016.
 */


define(['./../../../directives/module'], function (directives) {
    'use strict';
    directives.directive('secInputValidation', ['$templateCache', function ($templateCache) {

            var l = function (str) {
                console.log(str);
            }

            return {
                restrict: 'E',
                scope: {
                    modelAttr: '=',
                    idAttr: '@',
                    secChange: '&',
                },
                template: $templateCache.get('app/SECORRENTI/sec-input/views/sec-input-validation.html'),
                controller: ["$scope", function (s) {
                    var label;
                    var hint;
                    var aType = "text";

                    this.setLabel = function (label) {

                        this.label = label;
                        this.label.attr("type", aType);
                        $(this.label).on({
                            click: function () {
                                input.focus();
                                $(this).addClass("animated");
                            }
                        });
                    }

                    this.setInput = function (_input) {
                        input = _input;
                        $(input).on({
                            blur: function () {
                                $(label).removeClass("animated");
                            }
                        });
                    }

                    this.setType = function(_aType){
                        aType = _aType;
                    }


                    this.setHint = function (_hint) {
                        hint = _hint;
                    }

                }],

                link: function (s, e, a, ctrl) {

                    e.addClass("sec-input-validation");
                    if (!a.aType) {
                        ctrl.setType(a.aType);
                    }
                    ctrl.setHint(a.aHint)
                }
            }
        }])
        .directive('secLabel', function () {
            return {
                restrict: 'A',
                require: 'secInputValidation',
                link: function (s, e, a, ctrl) {
                    ctrl.setLabel(e);
                }
            }
        })
        .directive('secInput', function () {
            return {
                restrict: 'A',
                require: 'secInputValidation',
                link: function (s, e, a, ctrl) {
                    ctrl.setInput(e);
                }
            }
        })
        //.directive('secRequired', function () {
        //    return {
        //        restrict: 'A',
        //        priority: 0,
        //        require: 'secInputValidation',
        //        link: function (s, e, a, ctrl) {
        //            var warning = a.secRequired || 'This field is required'
        //            ctrl.addWarningInfo({
        //                key: 'secRequired',
        //                warning: warning,
        //                info: '',
        //                show: false
        //            })
        //        }
        //    }
        //});

});


