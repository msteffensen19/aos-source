/**
 * Created by correnti on 19/05/2016.
 */


define(['./../../../directives/module'], function (directives) {
    'use strict';
    directives.directive('secInputValidation', ['$templateCache', "$compile", function ($templateCache, $compile) {

        function throwInvalidObjectFormat(obj) {
            throw "-----------  Invalid object format ! \n\n" +
            "The element must be like this:\n\n" +
            "var obj = {\n" +
            "   error: 'the upper text message', \n" +
            "   info: 'the button information about this requirement', \n" +
            "   min: 'add this param if you use secMinLength directive', \n" +
            "   max: 'add this param if you use secMaxLength directive', \n" +
            "   regex: 'add this param if you use secPattern directive', \n" +
            "   model: 'add this param if you use secCompareTo directive, model have to pass sModelCompareTo model to compare', \n" +
            "}\n" +
            "\nYou object is: " + JSON.stringify(obj) + "\n\n\n\n";
        }

        var Keys = {
            secRequired: 0,
            secMinLength: 1,
            secMaxLength: 2,
            secPattern: 3,
            secCompareTo: 4,
        }

        var invalid = "invalid";
        var animated = "animated";

        return {
            restrict: 'E',
            scope: {
                secModel: '=',
                secRequire: '=',
                secMinLength: '=',
                secMaxLength: '=',
                secPattern: '=',

                secCompareTo: '=',
                sModelCompareTo: '=',

                secIsValid: '&',
            },
            controller: ["$scope", function (s) {

                var input;
                var label;
                var isFieldValid;
                var hint;
                var ul;
                s.compareTo;
                s.validations = [];

                this.getListValidations = function () {
                    var li = "";
                    for (var i = 0; i < s.validations.length; i++) {
                        if (s.validations[i].info) {
                            li += "<li><a>" + s.validations[i].info + " </a></li>"
                        }
                    }
                    return li;
                }


                this.setCompareTo = function (_compareTo) {
                    s.compareTo = _compareTo;
                }


                this.pushValidation = function (validation, key) {
                    validation.key = key;
                    s.validations.push(validation)
                }

                this.change = function (val) {
                    if (val != undefined && val != "") {
                        $(label).addClass(animated);
                        if (val.trim() == "") {
                            $(label).removeClass(animated);
                        }
                    }
                }

                var firstLoader2 = true;
                this.modelCompareToChange = function (val) {
                    if (!firstLoader2) {
                        if (val.trim() == "") {
                            return;
                        }
                        s.compareTo.val(val)

                        this.blur($(input).val());
                    }
                    firstLoader2 = false;
                }

                this.blur = function (val) {
                    if (val.trim() == "") {
                        $(label).removeClass(animated);
                    }
                    var valid = getValidation();
                    if (isFieldValid) {
                        isFieldValid({valid: valid});
                    }
                    ul.stop().slideUp(500);
                }

                this.focus = function () {
                    $(label).addClass(animated);
                    setNormalHint();
                    if (s.validations.length > 0) {
                        ul.stop().slideDown(500);
                    }
                }

                function getValidation() {
                    var validation = null;
                    try {
                        for (var i = 0; i < s.validations.length; i++) {
                            validation = s.validations[i];
                            switch (validation.key) {
                                case Keys.secRequired:
                                    if (input.val().trim() == '') {
                                        return setTextToShow(validation.error);
                                    }
                                    break;
                                case Keys.secMinLength:
                                    if (input.val().length < validation.min && (input.val() + "").length != 0) {
                                        return setTextToShow(validation.error);
                                    }
                                    break;
                                case Keys.secMaxLength:
                                    if (input.val().length > validation.max) {
                                        return setTextToShow(validation.error);
                                    }
                                    break;
                                case Keys.secPattern:
                                    if (!(new RegExp(validation.regex).test(input.val()))) { // && (input.val()+"").length != 0
                                        return setTextToShow(validation.error);
                                    }
                                    break;
                                case Keys.secCompareTo:
                                    if (s.compareTo.val() != input.val() && s.compareTo.val() != "") { // && input.val() != ""
                                        return setTextToShow(validation.error);
                                    }
                                    break;
                            }
                        }
                        setNormalHint();
                        return true;
                    } catch (e) {
                        throwInvalidObjectFormat(validation);
                    }
                }

                function setNormalHint() {
                    input.removeClass(invalid)
                    label.removeClass(invalid)
                    label.text(hint)
                }

                function setTextToShow(error) {
                    label.text(error);
                    label.addClass(invalid);
                    input.addClass(invalid)
                }

                this.setItems = function (_input, _label, _ul, _isFieldValid) {

                    input = _input;
                    label = _label;
                    isFieldValid = _isFieldValid;
                    ul = _ul;
                    hint = label.text();
                    var ctrl = this;

                    $(label).on({
                        click: function () {
                            if (!$(label).hasClass(animated)) {
                                input.focus();
                                ctrl.focus();
                            }
                        }
                    });

                    $(input).on({
                        blur: function () {
                            ctrl.blur($(input).val());
                        },
                        focus: function () {
                            ctrl.focus();
                        },
                    });
                }
            }],

            link: function (s, e, a, ctrl) {

                e.addClass("sec-input-validation");

                var type = "text"
                if (a.aType) {
                    type = a.aType;
                }

                s.$watch('secModel', function (n, o) {
                    ctrl.change(n);
                }, true);

                if (s.secRequire) {
                    ctrl.pushValidation(s.secRequire, Keys.secRequired);
                }
                if (s.secMinLength) {
                    ctrl.pushValidation(s.secMinLength, Keys.secMinLength);
                }
                if (s.secMaxLength) {
                    ctrl.pushValidation(s.secMaxLength, Keys.secMaxLength);
                }
                if (s.secPattern) {
                    ctrl.pushValidation(s.secPattern, Keys.secPattern);
                }

                var div = $("<div></div>")
                var input = $("<input type='" + type + "' data-ng-model='secModel' />")
                var label = $("<label>" + a.aHint + "</label>")

                div.append(input)
                div.append(label)

                $compile(div)(s);
                e.append(div);

                if (s.secCompareTo) {
                    s.$watch('sModelCompareTo', function (n, o) {
                        ctrl.modelCompareToChange(n);
                    }, true);
                    ctrl.pushValidation(s.secCompareTo, Keys.secCompareTo);
                    var compareTo = $("<input style='display: none' type='" + type + "' data-ng-model='sModelCompareTo' />")
                    $compile(compareTo)(s);
                    ctrl.setCompareTo(compareTo);
                    e.append(compareTo);
                }

                var ul = $("<ul>" + ctrl.getListValidations() + "</ul>");
                ctrl.setItems(input, label, ul, s.secIsValid);

                e.append(ul);

            }
        }
    }])


});


