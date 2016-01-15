/**
 * Created by correnti on 27/12/2015.
 */



define(['./module'], function (directives) {
    'use strict';
    directives

        .directive('secSubmitAttr', function(){
            return{
                restrict: 'A',
                scope:{
                    submit : '&secSubmitAttr'
                },
                require: '^secValidate',
                link: function(s, e, a, ctrl){

                    e.bind('click', function(){
                        console.log("ctrl.getInvalidItems()")
                        console.log(ctrl.getInvalidItems())
                        console.log("")
                        if(ctrl.getInvalidItems() == 0)
                        {
                            s.submit()
                        }
                    });
                }
            }
        })
        .directive('secValidate', ['$templateCache', function($templateCache){
            return{
                restrict: 'E',
                require: 'secValidate',
                scope: {},
                controller: ['$scope', function(s){

                    this.addId = function(id){
                        this.id = id;
                    }

                    s.invalidItems = [];
                    this.getInvalidItems = function(){
                        return s.invalidItems.length;
                    }

                    this.addInvalidField = function(invalidInput){
                        if(searchIndexOfInput(invalidInput) == -1)
                        {
                            s.invalidItems.push(invalidInput);
                        }
                    }

                    this.shiftInvalidField = function(invalidInput){

                        var find = searchIndexOfInput(invalidInput);
                        if(find != -1)
                        {
                            s.invalidItems.splice(find, 1);
                        }
                    }

                    function searchIndexOfInput(invalidInput){

                        for(var i = 0 ; i < s.invalidItems.length; i++)
                        {
                            if(s.invalidItems[i] == invalidInput)
                            {
                                return i;
                            }
                        }
                        return -1;
                    }
                }],
                link: function(s, e, a, ctrl){
                    e.addClass('sec-validate');
                    if(a.idAttr)
                    {
                        e.attr('id', a.idAttr);
                        ctrl.addId(a.idAttr);
                    }
                }
            }
        }])
        .directive('secInput', ['$templateCache', function($templateCache){
            return {
                restrict: 'E',
                replace: true,
                transclude: true,
                require: ['secInput', '^secValidate'],
                scope: {
                    modelAttr: '=',
                    idAttr: '@'
                },
                template: $templateCache.get('app/partials/secInput.html'),
                controller: ['$scope', function (s) {

                    s.warnings = [];
                    s.noRedStar = false;
                    s.id;
                    var labelStartPossition;
                    var ctrlFather;

                    s.inputKeyup = function (id) {
                        var input = $('#secInput_' + id);
                        checkValidInput(s.warnings, input, 'keyup');
                    }

                    s.inputFocus = function (id) {
                        var input = $('#secInput_' + id);
                        s.textToShow = {
                            text: s.placeHolder,
                            valid: true,
                        };
                        checkValidInput(s.warnings, input, 'keyup');
                        labelStartPossition = labelStartPossition || input.prev().css('top');
                        input.prev().animate({'top': '-18px'}, 300, 'linear');
                        input.siblings(".validate-info").show(200);
                    }

                    s.inputBlur = function (id) {
                        var input = $('#secInput_' + id);
                        if (input.val().length == 0) {
                            input.prev().animate({'top': labelStartPossition}, 300, 'linear');
                        }
                        checkValidInput(s.warnings, input, 'blur');
                        if (s.textToShow.valid) {
                            ctrlFather.shiftInvalidField(id);
                        }
                        else {
                            ctrlFather.addInvalidField(id);
                        }
                        input.siblings(".validate-info").hide(200);
                    }

                    s.validateLabelClicked = function (id) {
                        $('#secInput_' + id).focus();
                    }

                    this.setCtrlFather = function (_ctrlFather) {
                        ctrlFather = _ctrlFather;
                    }

                    this.addWarningInfo = function (warningInfo) {
                        s.warnings.push(warningInfo);
                        ctrlFather.addInvalidField(s.id);
                    }

                    this.setPlaceHolder = function (placeHolder) {
                        s.placeHolder = placeHolder;
                        s.textToShow = {
                            text: placeHolder,
                            valid: true,
                            key: ''
                        };
                    }

                    this.setId = function (id) {
                        s.id = id;
                    }

                    this.enableNoRedStar = function () {
                        s.noRedStar = true;
                    }

                    this.setInputType = function (inputType) {
                        s.inputType = inputType;
                    }

                    function setTextToShow(warn) {
                        warn.show = true;
                        s.textToShow = {
                            text: warn.warning,
                            valid: false
                        };
                    }

                    function checkValidInput(warnings, input, event) {

                        angular.forEach(s.warnings, function (warn) {
                            if (s.textToShow.valid) {
                                switch (warn.key) {
                                    case 'secRequired':
                                        if(event == 'keyup'){
                                            if (!(input.val() == '')) {
                                                warn.show = false;
                                            }
                                        }
                                        else if (input.val() == '') {
                                            setTextToShow(warn)
                                            return false;
                                        }
                                        break;
                                    case 'secMinLength':
                                        if(event == 'keyup'){
                                            warn.show = input.val().length < warn.min;
                                        }
                                        else if (input.val().length < warn.min) {
                                            setTextToShow(warn)
                                            return false;
                                        }
                                        break;
                                    case 'secMaxLength':
                                        if(event == 'keyup'){
                                            warn.show = input.val().length > warn.max;
                                        }
                                        else if (input.val().length > warn.max) {
                                            setTextToShow(warn)
                                            return false;
                                        }
                                        break;
                                    case 'secPattern':
                                        if(event == 'keyup'){
                                            warn.show = !(new RegExp(warn.regex).test(input.val()));
                                        }
                                        else if (!(new RegExp(warn.regex).test(input.val()))) {
                                            setTextToShow(warn)
                                            return false;
                                        }
                                        break;
                                    case 'secCompareTo':
                                        var comparedInput = $('#secInput_' + warn.compareId);
                                        if(event == 'keyup'){
                                            warn.show = (comparedInput.val() != input.val() && comparedInput.val() != "" && input.val() != "");
                                        }
                                        else if (comparedInput.val() != input.val() && comparedInput.val() != "" && input.val() != "") {
                                            setTextToShow(warn)
                                            return false;
                                        }
                                        break;
                                    default:
                                        throw warn.key + " key is not defined in checkValidInput(warnings) method (directive <validate-input></validate-input>)";
                                }
                            }
                        })
                    }
                }],
                link: {
                    pre: function (s, e, a, ctrls) {

                        var me = ctrls[0];
                        e.addClass('validate-directive');

                        if (a.selectTag) {

                            e.bind('click', function () {
                                var checkbox = $($(this).find("input[type=checkbox]"))
                                if (checkbox.length > 0) {
                                    s.$apply(function () {
                                        if (checkbox[0].checked) {
                                            ctrls[1].shiftInvalidField(a.idAttr);
                                            $(this).find(".validateInvalid").fadeOut()
                                        }
                                        else {
                                            ctrls[1].addInvalidField(a.idAttr);
                                            $(this).find(".validateInvalid").fadeIn();
                                        }
                                    });
                                }
                            })

                            e.bind('change', function () {
                                ctrls[1].shiftInvalidField(a.idAttr);
                                $($(this).find(".validate-label")).html('')
                            })
                        }

                        if (!a.idAttr) {
                            throw "id attribute  in directive <secInput></secInput> is must! "
                        }

                        if (a.noRedStar) {
                            me.enableNoRedStar();
                        }

                        me.setCtrlFather(ctrls[1]);
                        me.setInputType(a.inputTypeAttr || 'text')
                        me.setId(a.idAttr)
                    }
                }

            }
        }])
        .directive('secRequired', function(){
            return{
                restrict: 'A',
                priority: 0,
                require: 'secInput',
                link: function(s, e, a, ctrl){
                   var warning = a.secRequired || 'This field is required'
                    ctrl.addWarningInfo({
                        key : 'secRequired',
                        warning : warning,
                        info: '',
                        show: false
                    })
                }
            }
        })
        .directive('secMinLength', function(){
            return{
                restrict: 'A',
                require: 'secInput',
                priority: 40,
                link: function(s, e, a, ctrl){
                    var min = a.secMinLength - 0;
                    if(!a.idAttr){
                        throw "if secMinLength is used in directive <secInput></secInput>, value is must! "
                    }
                    if(!((min - 0) == min && (''+min).trim().length > 0) || min < 0){
                        throw "Invalid value in attribute secMinLength, the value in directive "+
                        "<secInput></secInput> must be a positive number! "
                    }
                    var warning = 'Use '+ min +' characters or longer'
                    ctrl.addWarningInfo({
                        key : 'secMinLength',
                        warning : warning,
                        info: warning,
                        min : min,
                        show: true
                    })
                }
            }
        })
        .directive('secMaxLength', function(){
            return{
                restrict: 'A',
                priority: 50,
                require: 'secInput',
                link: function(s, e, a, ctrl){
                    var max = a.secMaxLength - 0;
                    if(!a.idAttr){
                        throw "if secMaxLength is used in directive <secInput></secInput>, value is must! "
                    }
                    if(!((max - 0) == max && (''+max).trim().length > 0) || max < 0){
                        throw "Invalid value in attribute secMaxLength, the value in directive "+
                        "<secInput></secInput> must be a positive number! "
                    }
                    var warning = 'Use up to '+ max +' characters'
                    ctrl.addWarningInfo({
                        key : 'secMaxLength',
                        warning : warning,
                        info: warning,
                        show: true,
                        max : max,
                    })
                }
            }
        })
        .directive('secPattern', function(){
            return{
                restrict: 'A',
                priority: 100,
                require: 'secInput',
                link: function(s, e, a, ctrl){

                    if(!a.patternErrorAttr){
                        throw "if secPattern is used in directive <secInput></secInput>, pattern-error-attr attribute is must! "
                    }
                    ctrl.addWarningInfo({
                        key : 'secPattern',
                        warning : a.patternErrorAttr,
                        info: a.patternErrorAttr,
                        show: true,
                        regex : a.secPattern,
                    })
                }
            }
        })
        .directive('secEmail', function(){
            return{
                restrict: 'A',
                require: 'secInput',
                link: function(s, e, a, ctrl){
                    ctrl.addWarningInfo({
                        key : 'secPattern',
                        warning : "Your email address isn’t formatted correctly",
                        info: "Your email address isn’t formatted correctly",
                        show: true,
                        regex : a.secEmail || "^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$"
                        //"^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$",
                    })
                }
            }
        })
        .directive('secCompareTo', function(){
            return{
                restrict: 'A',
                require: 'secInput',
                link: function(s, e, a, ctrl){
                    var max = a.secMaxLength - 0;
                    if(!a.idAttr){
                        throw "if secCompareTo is used in directive <secInput></secInput>, id-attr is must! "
                    }
                    if(!a.secCompareTo){
                        throw "if sec-compare-to is used in directive <secInput></secInput>, must to be a comparable id! "
                    }
                    if(!a.compareToName){
                        throw "if sec-compare-to is used in directive <secInput></secInput>, must to be compare-to-name attribute! "
                    }
                    var warning = 'Same as ' + a.compareToName;
                    ctrl.addWarningInfo({
                        key : 'secCompareTo',
                        warning : warning,
                        info : warning,
                        show: true,
                        compareId : a.secCompareTo,
                    })
                }
            }
        })
        .directive('secPlaceholder', function(){
            return{
                restrict: 'A',
                require: 'secInput',
                link: function(s, e, a, ctrl){
                    ctrl.setPlaceHolder(a.secPlaceholder)
                }
            }
        })
    ;
});

