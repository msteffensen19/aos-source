/**
 * Created by correnti on 19/05/2016.
 */


define(['./../../../directives/module'], function (directives) {
    'use strict';
    directives.directive('secInputValidation', ['$templateCache', "$compile", "$timeout", function ($templateCache, $compile, $timeout) {

        function throwInvalidObjectFormat(obj, e) {

            console.log(e);
            console.log(JSON.stringify(e));
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

        var Types = {
            text: "text",
            select: "select",
        }

        var invalid = "invalid";
        var animated = "animated";
        var in_focus = "in-focus";
        var select_value = "select-value";

        return {
            restrict: 'E',
            scope: {
                secModel: '=',
                secRequire: '=',
                secMinLength: '=',
                secMaxLength: '=',
                secPattern: '=',
                secSelectOptions: '=',

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
                var ctrl;
                s.compareTo;
                s.validations = [];

                this.getListValidations = function () {
                    var li = "";
                    for (var i = 0; i < s.validations.length; i++) {
                        var validation = s.validations[i];
                        if (validation.info) {
                            li += "<li><a>" + validation.info + " </a></li>"
                        }
                    }
                    return li;
                };


                this.setCompareTo = function (_compareTo) {
                    s.compareTo = _compareTo;
                };


                this.pushValidation = function (validation, key) {
                    validation.key = key;
                    s.validations.push(validation)
                };

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
                };

                this.fillSelect = function (arr) {
                    var selectList = ctrl.getSelectlist();
                    selectList.empty();
                    for (var i = 0; i < arr.length; i++) {
                        var item = arr[i];
                        var span = $("<span data-ng-click='selectItemChangeModel("+ JSON.stringify(item) + ")'>" + item.name + "</span>");

                        $compile(span)(s);

                        selectList.append(span);
                    }
                };

                s.selectItemChangeModel = function(validation){
                    console.log(validation);
                    if (!label.hasClass(animated)) {
                        label.addClass(animated)
                        $timeout(function(){
                            s.secModel = validation;
                        }, 200)
                    }
                    else{
                        s.secModel = validation;
                    }
                }

                this.change = function (val) {

                    if (input.find(select_value)) {
                        ctrl.getSelectlist().fadeOut();
                        return;
                    }

                    if (val == undefined) {
                        return;
                    }
                    else if (val.trim() == "") {
                        if (s.validations.length > 0) {
                            if (input.val().trim() == '' && input.hasClass(in_focus)) {
                                ul.find('li').slideDown()
                            }
                        }
                    }
                    else {
                        if (!$(label).hasClass(animated)) {
                            $(label).addClass(animated);
                        }
                        checkRightValidations();
                    }
                };

                this.blur = function (val) {
                    if (val.trim() == "") {
                        label.removeClass(animated);
                    }
                    var valid = getValidation();
                    if (isFieldValid) {
                        isFieldValid({valid: valid});
                    }
                    ul.find('li').slideUp();
                    input.removeClass(in_focus);
                };

                this.focus = function () {
                    label.addClass(animated);
                    input.addClass(in_focus);
                    this.change(input.val());
                    setNormalHint();
                };

                 this.getSelectlist = function(){
                    return input.find(".selectList");
                }

                //this.setPropertyToShow = function(aShow){
                //    propertyToShow
                //};

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

                function checkRightValidations() {
                    var validation = null;
                    var invalidInput = false;
                    try {
                        for (var i = 0; i < s.validations.length; i++) {
                            validation = s.validations[i];
                            switch (validation.key) {
                                case Keys.secRequired:
                                    if (input.val().trim() == '') {
                                        showValidation(i)
                                        invalidInput = true;
                                    }
                                    else {
                                        hideValidation(i)
                                    }
                                    break;
                                case Keys.secMinLength:
                                    if (input.val().length < validation.min && (input.val() + "").length != 0) {
                                        showValidation(i)
                                        invalidInput = true;
                                    }
                                    else {
                                        hideValidation(i)
                                    }
                                    break;
                                case Keys.secMaxLength:
                                    if (input.val().length > validation.max) {
                                        showValidation(i)
                                        invalidInput = true;
                                    }
                                    else {
                                        hideValidation(i)
                                    }
                                    break;
                                case Keys.secPattern:
                                    if (!(new RegExp(validation.regex).test(input.val()))) { // && (input.val()+"").length != 0
                                        showValidation(i)
                                        invalidInput = true;
                                    }
                                    else {
                                        hideValidation(i)
                                    }
                                    break;
                                case Keys.secCompareTo:
                                    if (s.compareTo.val() != input.val() && s.compareTo.val() != "") { // && input.val() != ""
                                        showValidation(i)
                                        invalidInput = true;
                                    }
                                    else {
                                        hideValidation(i)
                                    }
                                    break;
                            }
                        }
                        //updateFormValidation(invalidInput);
                        return true;
                    } catch (e) {
                        throwInvalidObjectFormat(validation, e);
                    }
                }

                function hideValidation(index) {
                    ul.find("li:nth-child(" + (index + 1) + ")").slideUp();
                }

                function showValidation(index) {
                    ul.find("li:nth-child(" + (index + 1) + ")").slideDown();
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
                    ctrl = this;

                    label.on({
                        click: function () {
                            if (input.find(select_value)) {
                                ctrl.getSelectlist().fadeToggle();
                                return;
                            }
                            if (!label.hasClass(animated)) {

                                input.focus();
                                ctrl.focus();
                            }
                        }
                    });

                    input.on({
                        blur: function () {
                            ctrl.blur(input.val());
                        },
                        focus: function () {
                            ctrl.focus();
                        },
                    });
                }
            }],

            link: {

                pre: function (s, e, a, ctrl) {

                    e.addClass("sec-input-validation");

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

                    //if (a.aType) {
                    //    ctrl.pushValidation(s.secPattern, Keys.secPattern);
                    //}

                    var div = $("<div class='inputContainer'></div>");
                    var type = a.aType || "text"
                    var input;
                    var label = $("<label>" + a.aHint + "</label>");
                    switch (type) {
                        case Types.select:
                            //ctrl.setPropertyToShow(a.aShow);
                            s.$watch('secSelectOptions', function (n, o) {
                                ctrl.fillSelect(n);
                            }, true);
                            label.css("cursor", "pointer");
                            var property = a.aShow;
                            input = $("<div><label class='select-value'> {{secModel['" + property + "']}}</label><div class='selectList'></div></div>");
                            input.find(".select-value").on({
                                click: function(){
                                    ctrl.getSelectlist().fadeToggle(150);
                                }
                            })
                            break;
                        default:
                            input = $("<input type='" + type + "' data-ng-model='secModel' />");
                            break;
                    }

                    div.append(input);
                    div.append(label);

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
                    e.append(ul);
                    ctrl.setItems(input, label, ul, s.secIsValid);

                }
            },
            post: function (s, e, a, ctrl) {

            }
        }
    }
    ]).animation('.fade', [function () {
        return {
            enter: function (element, doneFn) {
                jQuery(element).slideIn(1000, doneFn);
            }
        }
    }])


});


