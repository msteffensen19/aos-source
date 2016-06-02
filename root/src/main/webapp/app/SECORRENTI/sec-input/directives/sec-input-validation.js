///**
// * Created by correnti on 19/05/2016.
// */
//
//
//define(['./../../../directives/module'], function (directives) {
//    'use strict';
//
//    directives.directive('secForm', ["$compile", "$timeout", function ($compile, $timeout) {
//
//        var invalid = "invalid";
//        return {
//            restrict: 'E',
//            require: 'secForm',
//            scope: {
//            },
//            controller: ["$scope", function (s) {
//
//                this.models = [];
//                this.senders = [];
//                this.setCtrl = function (ctrl) {
//                    this.senders.push(ctrl);
//                }
//
//                this.updateState = function (id, valid) {
//                    var validForm = true;
//                    for(var i = 0; i < this.models.length; i++){
//                        var model = this.models[i];
//                        if(model.id == id){
//                            model.valid = valid;
//                        }
//                        validForm = !validForm ? false : model.valid;
//                    }
//                    for(var i = 0; i < this.senders.length; i++){
//                        this.senders[i].formIsValid(validForm);
//                    }
//                };
//
//                this.notifyWatcher = function (id, valid) {
//                    this.models.push({
//                        id: id,
//                        valid: valid,
//                    });
//                };
//
//            }],
//            link: {
//                pre: function (s, e, a, ctrl) {
//                    e.addClass("secForm");
//                },
//                post: function (s, e, a, ctrl) {
//                    ctrl.updateState(-1, null);
//                }
//            }
//        }
//
//    }]).
//    directive('secSender', ["$compile", "$timeout", function ($compile, $timeout) {
//
//        var invalid = "invalid";
//
//        return {
//            restrict: 'E',
//            require: ['secSender', '^secForm'],
//            scope: {
//                secSend: '&',
//            },
//            controllerAs: "senderCtrl",
//            controller: ["$scope", function (s) {
//
//                var button;
//                this.setButton = function (_button) {
//                    button = _button;
//                };
//
//                this.send = function () {
//                    if (button.hasClass(invalid)) {
//                    }
//                    else {
//                        s.secSend();
//                    }
//                };
//
//                this.formIsValid = function (valid) {
//
//                    if (valid) {
//                        button.removeClass(invalid);
//                    }
//                    else {
//                        button.addClass(invalid);
//                    }
//                };
//
//            }],
//            link: {
//                pre: function (s, e, attr, ctrls) {
//                    e.addClass("sec-sender");
//                    var button = $("<a class='sec-sender-a invalid' data-ng-click='senderCtrl.send()'>" + attr.aValue + "</a>")
//                    $compile(button)(s);
//                    e.append(button);
//
//                    ctrls[0].setButton(button);
//                    ctrls[1].setCtrl(ctrls[0]);
//                },
//                post: function (s, e, a, ctrl) {
//                }
//            }
//        }
//
//    }]).
//    directive('secView', ['$templateCache', "$compile", "$timeout", function ($templateCache, $compile, $timeout) {
//
//        function throwInvalidObjectFormat(obj, e) {
//
//            console.log(e);
//            console.log(JSON.stringify(e));
//            throw  "-----------  Invalid object format ! \n\n" +
//            "The element must be like this:\n\n" +
//            "var obj = {\n" +
//            "   error: 'the upper text message', \n" +
//            "   info: 'the button information about this requirement', \n" +
//            "   min: 'add this param if you use secMinLength directive', \n" +
//            "   max: 'add this param if you use secMaxLength directive', \n" +
//            "   regex: 'add this param if you use secPattern directive', \n" +
//            "   model: 'add this param if you use secCompareTo directive, model have to pass sModelCompareTo model to compare', \n" +
//            "}\n" +
//            "\nYou object is: " + JSON.stringify(obj) + "\n\n\n\n";
//
//            //a-hint: pass this text has a text ,
//
//            //"   a-type: 'add this param if you wont to display a different control:', \n" +
//            //"         select: 'to show a select design (required directives to this type: sec-select-options, a-show )', \n" +
//
//            //sec-select-options
//            //a-show
//
//            //secModel: '=',
//            //secRequire: '=',
//            //secMinLength: '=',
//            //secMaxLength: '=',
//            //secPattern: '=',
//            //
//            //secCompareTo: '=',
//            //sModelCompareTo: '=',
//            //
//            //sec-send: '&',
//        }
//
//        var Keys = {
//            secRequired: 0,
//            secMinLength: 1,
//            secMaxLength: 2,
//            secPattern: 3,
//            secCompareTo: 4,
//        }
//
//        var Types = {
//            text: "text",
//            select: "select",
//            checkbox: "checkbox"
//        }
//
//        var invalid = "invalid";
//        var animated = "animated";
//        var in_focus = "in-focus";
//        var select_value = "select-value";
//
//        return {
//            restrict: 'E',
//            require: ['secView', '^secForm'],
//            scope: {
//                secModel: '=',
//                secRequire: '=',
//                secMinLength: '=',
//                secMaxLength: '=',
//                secPattern: '=',
//                secSelectOptions: '=',
//
//                secCompareTo: '=',
//                sModelCompareTo: '=',
//
//                secIsValid: '&',
//            },
//            controller: ["$scope", function (s) {
//
//                var id;
//                var input;
//                var label;
//                var isFieldValid;
//                var hint;
//                var ul;
//                var ctrl;
//                var form;
//
//                s.compareTo;
//                s.validations = [];
//
//                this.getListValidations = function () {
//                    var li = "";
//                    for (var i = 0; i < s.validations.length; i++) {
//                        var validation = s.validations[i];
//                        if (validation.info) {
//                            li += "<li><a>" + validation.info + " </a></li>"
//                        }
//                    }
//                    return li;
//                };
//
//
//                this.setCompareTo = function (_compareTo) {
//                    s.compareTo = _compareTo;
//                };
//
//
//                this.pushValidation = function (validation, key) {
//                    validation.key = key;
//                    s.validations.push(validation)
//                };
//
//                var firstLoader2 = true;
//                this.modelCompareToChange = function (val) {
//                    if (!firstLoader2) {
//                        if (val.trim() == "") {
//                            return;
//                        }
//                        s.compareTo.val(val)
//                        this.blur($(input).val());
//                    }
//                    firstLoader2 = false;
//                };
//
//                this.fillSelect = function (arr) {
//                    var selectList = ctrl.getSelectlist();
//                    selectList.empty();
//                    for (var i = 0; i < arr.length; i++) {
//                        var item = arr[i];
//                        var span = $("<span data-ng-click='selectItemChangeModel(" + JSON.stringify(item) + ")'>" + item.name + "</span>");
//                        $compile(span)(s);
//                        selectList.append(span);
//                    }
//                };
//
//                s.selectItemChangeModel = function (validation) {
//
//                    if (!label.hasClass(animated)) {
//                        label.addClass(animated)
//                        $timeout(function () {
//                            s.secModel = validation;
//                        }, 200)
//                    }
//                    else {
//                        s.secModel = validation;
//                    }
//                }
//
//                this.change = function (val) {
//
//                    if (val == undefined) {
//                        return;
//                    }
//                    var valid;
//                    try {
//                        if (isCheckboxDesign()) {
//                            valid = checkCheckboxValidations();
//                            updateTextCheckboxValidations(valid);
//                            return;
//                        }
//                        else if (isSelectedDesign()) {
//                            valid = checkSelectValidations(val);
//                            if (valid) {
//                                if (!$(label).hasClass(animated)) {
//                                    $(label).addClass(animated);
//                                }
//                            }
//                            ctrl.getSelectlist().fadeOut();
//                            return;
//                        }
//                        else if (val.trim() == "") {
//                            if (s.validations.length > 0) {
//                                if (s.secModel.trim() == '' && input.hasClass(in_focus)) {
//                                    ul.find('li').slideDown()
//                                }
//                            }
//                        }
//                        else {
//                            if (!$(label).hasClass(animated)) {
//                                $(label).addClass(animated);
//                            }
//                            valid = checkViewValidations();
//                        }
//
//                    } finally {
//                        form.updateState(id, valid);
//                    }
//                };
//
//                function checkSelectValidations(val){
//                    return val != "" && val != null && val != undefined;
//                }
//
//                this.blur = function (val) {
//                    if (val == undefined || val.trim() == undefined) {
//                        return;
//                    }
//                    if (val.trim() == "") {
//                        label.removeClass(animated);
//                    }
//                    var valid = getValidation();
//                    if (isFieldValid) {
//                        isFieldValid({valid: valid});
//                    }
//                    ul.find('li').slideUp();
//                    input.removeClass(in_focus);
//                };
//
//
//                this.focus = function () {
//                    label.addClass(animated);
//                    input.addClass(in_focus);
//                    this.change(s.secModel);
//                    setNormalHint();
//                };
//
//
//                this.getSelectlist = function () {
//                    return input.find(".selectList");
//                };
//
//
//                s.labelClicked = function () {
//                    if (isSelectedDesign()) {
//                        ctrl.getSelectlist().fadeToggle();
//                        return;
//                    }
//                    if (isCheckboxDesign()) {
//                        s.secModel = !s.secModel;
//                        return;
//                    }
//                    if (!label.hasClass(animated)) {
//                        input.focus();
//                        ctrl.focus();
//                    }
//                };
//
//                this.setItems = function (_input, _label, _ul, _form, _id) {
//
//                    id = _id;
//                    input = _input;
//                    label = _label;
//                    ul = _ul;
//                    hint = label.text();
//                    ctrl = this;
//                    form = _form;
//
//
//                    var valid;
//                    if (isCheckboxDesign()) {
//                        valid = checkCheckboxValidations();
//                    }
//                    else if (isSelectedDesign()) {
//                        valid = checkSelectValidations();
//                    }
//                    else {
//                        valid = checkViewValidations();
//                    }
//                    form.notifyWatcher(id, valid);
//
//
//                    input.on({
//                        blur: function () {
//                            ctrl.blur(s.secModel);
//                        },
//                        focus: function () {
//                            ctrl.focus();
//                        },
//                    });
//                }
//
//
//                ////// FUNCTIONS //////
//
//                function getValidation() {
//                    var validation = null;
//                    try {
//                        for (var i = 0; i < s.validations.length; i++) {
//                            validation = s.validations[i];
//                            switch (validation.key) {
//                                case Keys.secRequired:
//                                    if (s.secModel.trim() == '') {
//                                        return setTextToShow(validation.error);
//                                    }
//                                    break;
//                                case Keys.secMinLength:
//                                    if (s.secModel.length < validation.min && (s.secModel + "").length != 0) {
//                                        return setTextToShow(validation.error);
//                                    }
//                                    break;
//                                case Keys.secMaxLength:
//                                    if (s.secModel.length > validation.max) {
//                                        return setTextToShow(validation.error);
//                                    }
//                                    break;
//                                case Keys.secPattern:
//                                    if (!(new RegExp(validation.regex).test(s.secModel))) { // && (input.val()+"").length != 0
//                                        return setTextToShow(validation.error);
//                                    }
//                                    break;
//                                case Keys.secCompareTo:
//                                    if (s.compareTo.val() != s.secModel && s.compareTo.val() != "") { // && input.val() != ""
//                                        return setTextToShow(validation.error);
//                                    }
//                                    break;
//                            }
//                        }
//                        setNormalHint();
//                        return true;
//                    } catch (e) {
//                        throwInvalidObjectFormat(validation);
//                    }
//                }
//
//
//                function updateTextCheckboxValidations(valid) {
//                    if (s.validations[0].dontChange) {
//                        return;
//                    }
//                    if (valid) {
//                        label.text(s.validations[0].info);
//                        label.removeClass(invalid);
//                    }
//                    else {
//                        label.text(s.validations[0].error);
//                        label.addClass(invalid);
//                    }
//                }
//
//                function checkCheckboxValidations() {
//                    return s.secModel == true;
//                }
//
//                function checkViewValidations() {
//                    var validation = null;
//                    var validInput = true;
//                    try {
//                        for (var i = 0; i < s.validations.length; i++) {
//                            validation = s.validations[i];
//                            switch (validation.key) {
//                                case Keys.secRequired:
//                                    if (s.secModel.trim() == '') {
//                                        showValidation(i)
//                                        validInput = false;
//                                    }
//                                    else {
//                                        hideValidation(i)
//                                    }
//                                    break;
//                                case Keys.secMinLength:
//                                    if (s.secModel.length < validation.min && (s.secModel + "").length != 0) {
//                                        showValidation(i)
//                                        validInput = false;
//                                    }
//                                    else {
//                                        hideValidation(i)
//                                    }
//                                    break;
//                                case Keys.secMaxLength:
//                                    if (s.secModel.length > validation.max) {
//                                        showValidation(i)
//                                        validInput = false;
//                                    }
//                                    else {
//                                        hideValidation(i)
//                                    }
//                                    break;
//                                case Keys.secPattern:
//                                    if (!(new RegExp(validation.regex).test(s.secModel))) { // && (input.val()+"").length != 0
//                                        showValidation(i)
//                                        validInput = false;
//                                    }
//                                    else {
//                                        hideValidation(i)
//                                    }
//                                    break;
//                                case Keys.secCompareTo:
//                                    if (s.compareTo.val() != s.secModel && s.compareTo.val() != "") { // && input.val() != ""
//                                        showValidation(i)
//                                        validInput = false;
//                                    }
//                                    else {
//                                        hideValidation(i)
//                                    }
//                                    break;
//                            }
//                        }
//                        return validInput;
//
//                    } catch (e) {
//                        throwInvalidObjectFormat(validation, e);
//                    }
//                }
//
//                function hideValidation(index) {
//                    ul.find("li:nth-child(" + (index + 1) + ")").slideUp();
//                }
//
//                function showValidation(index) {
//                    ul.find("li:nth-child(" + (index + 1) + ")").slideDown();
//                }
//
//                function setNormalHint() {
//                    input.removeClass(invalid)
//                    label.removeClass(invalid)
//                    label.text(hint)
//                }
//
//                function setTextToShow(error) {
//                    label.text(error);
//                    label.addClass(invalid);
//                    input.addClass(invalid)
//                }
//
//                function isSelectedDesign() {
//                    return input.find("." + select_value).length > 0;
//                }
//
//
//                function isCheckboxDesign() {
//                    return input.attr("type") == Types.checkbox;
//                }
//
//
//            }],
//
//            link: {
//
//                pre: function (s, e, a, ctrls) {
//
//                    var ctrl = ctrls[0];
//                    e.addClass("sec-view");
//
//                    s.$watch('secModel', function (n, o) {
//                        ctrl.change(n);
//                    }, true);
//
//                    if (s.secRequire) {
//                        if (a.aDontChange == "true") {
//                            s.secRequire.dontChange = true;
//                        }
//                        ctrl.pushValidation(s.secRequire, Keys.secRequired);
//                    }
//                    if (s.secMinLength) {
//                        ctrl.pushValidation(s.secMinLength, Keys.secMinLength);
//                    }
//                    if (s.secMaxLength) {
//                        ctrl.pushValidation(s.secMaxLength, Keys.secMaxLength);
//                    }
//                    if (s.secPattern) {
//                        ctrl.pushValidation(s.secPattern, Keys.secPattern);
//                    }
//
//                    var div = $("<div class='inputContainer'></div>");
//                    var type = a.aType || "text"
//                    var input;
//                    var label = $("<label data-ng-click='labelClicked()'>" + a.aHint + "</label>");
//                    switch (type) {
//                        case Types.select:
//                            s.$watch('secSelectOptions', function (n, o) {
//                                ctrl.fillSelect(n);
//                            }, true);
//                            label.css("cursor", "pointer");
//                            input = $("<div><label class='select-value' > {{secModel['" + a.aShow + "']}}</label><div class='selectList'></div></div>");
//                            input.find(".select-value").on({
//                                click: function () {
//                                    ctrl.getSelectlist().fadeToggle(150);
//                                }
//                            });
//                            break;
//                        default:
//                            input = $("<input type='" + type + "' data-ng-model='secModel' />");
//
//                            if (type == Types.checkbox) {
//                                label.text(s.secRequire.info);
//                                label.addClass("checkboxText " + animated);
//                                div.css({
//                                    "padding-top": "1px",
//                                    "height": "auto",
//                                    "margin": "8px 0",
//                                });
//                            }
//                            break;
//                    }
//
//                    div.append(input);
//                    div.append(label);
//
//                    $compile(div)(s);
//                    e.append(div);
//
//                    if (s.secCompareTo) {
//                        s.$watch('sModelCompareTo', function (n, o) {
//                            ctrl.modelCompareToChange(n);
//                        }, true);
//                        ctrl.pushValidation(s.secCompareTo, Keys.secCompareTo);
//                        var compareTo = $("<input style='display: none' type='" + type + "' data-ng-model='sModelCompareTo' />")
//                        $compile(compareTo)(s);
//                        ctrl.setCompareTo(compareTo);
//                        e.append(compareTo);
//                    }
//
//                    var ul = $("<ul>" + ctrl.getListValidations() + "</ul>");
//                    e.append(ul);
//
//                    var _form = ctrls[1];
//                    ctrl.setItems(input, label, ul, _form, s.$id);
//
//                }
//            },
//            post: function (s, e, a, ctrl) {
//            }
//        }
//    }
//    ])
//
//});
//
//
